package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.billingaccount.creditmemo.ReturnOrderDetails;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.*;

import static com.fluentcommerce.util.Constants.*;
import static java.lang.String.format;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateOrderAttributeAfterAppeasementCreated",
        description = "update order attribute once appeasement created  and send the event {" + PROP_EVENT_NAME + "}" ,
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_CREDIT_MEMO)
        }
)

@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)

@Slf4j
public class UpdateOrderAttributeAfterAppeasementCreated extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        Event event = context.getEvent();
        String eventName = context.getProp(PROP_EVENT_NAME);
        String creditMemoRef = event.getEntityRef();
        String orderRef = null;
        CreditMemoService creditMemoService = new CreditMemoService(context);
        GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(creditMemoRef);
        if (null != creditMemo){
            double appeasementAmount = creditMemo.totalAmount().amount();
            double maxRefundableAmountForOrder = 0;
            orderRef = creditMemo.order().ref();
            OrderService orderService = new OrderService(context);
            GetOrdersByRefQuery.Edge orderEdge = orderService.getOrdersByRef(orderRef);
            Optional<GetOrdersByRefQuery.Attribute> returnOrderDetailsAttribute = Optional.empty();
            if (null != orderEdge && null != orderEdge.node() && CollectionUtils.isNotEmpty(orderEdge.node().attributes())){
                Optional<GetOrdersByRefQuery.Attribute> maxRefundableAmountForOrderAtt = orderEdge.node().attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(AVAILABLE_AMOUNT_TO_REFUND)).findFirst();
                returnOrderDetailsAttribute = orderEdge.node().attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(RETURN_ORDER_DETAILS)).findFirst();
                if (maxRefundableAmountForOrderAtt.isPresent()){
                    maxRefundableAmountForOrder = Double.parseDouble(maxRefundableAmountForOrderAtt.get().value().toString());
                }
            }
            if (appeasementAmount >0 && maxRefundableAmountForOrder > 0)
                upsertReturnOrderAndForwardEvent(context, eventName, creditMemo, appeasementAmount, maxRefundableAmountForOrder, orderEdge, returnOrderDetailsAttribute);
            else {
                context.addLog("Total refundable amount is 0 .Hence could not proceed with the appeasement request for the orderRef:: "+ orderRef);
                throw new IllegalArgumentException(format("Total refundable amount is 0 ." +
                        "Hence could not proceed with the appeasement request for the orderRef %s ", orderRef));
            }
        }
    }

    private void upsertReturnOrderAndForwardEvent(ContextWrapper context, String eventName,  GetCreditMemoByRefQuery.CreditMemo creditMemo, double appeasementAmount,
                                                  double maxRefundableAmountForOrder, GetOrdersByRefQuery.Edge orderEdge,
                                                  Optional<GetOrdersByRefQuery.Attribute> returnOrderDetailsAttribute) {
        ReturnOrderDetails returnOrderDetails;
        List<ReturnOrderDetails> returnOrderDetailsList;
        double updatedMaxAmount;
        if (maxRefundableAmountForOrder >= appeasementAmount){
            updatedMaxAmount = (maxRefundableAmountForOrder - appeasementAmount);
        }else{
            updatedMaxAmount = 0;
            appeasementAmount = maxRefundableAmountForOrder;
        }
        context.addLog("UpdateOrderAttributeAfterAppeasementCreated::updatedMaxAmount::"+updatedMaxAmount);
        context.addLog("UpdateOrderAttributeAfterAppeasementCreated::appeasementAmount::"+ appeasementAmount);
        if (returnOrderDetailsAttribute.isPresent()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                returnOrderDetailsList = mapper.readValue(returnOrderDetailsAttribute.get().value().toString(),
                        new TypeReference<List<ReturnOrderDetails>>() {});
                if (CollectionUtils.isNotEmpty(returnOrderDetailsList)) {
                    returnOrderDetails = createReturnOrderDetails((float) appeasementAmount, creditMemo);
                    returnOrderDetailsList.add(returnOrderDetails);
                    updateOrderAttribute(context, orderEdge.node().id(), returnOrderDetailsList,updatedMaxAmount);
                }
            } catch (IOException exception) {
                context.addLog("Exception Occurred when fetching returnOrderDetails attribute" + exception.getMessage());
            }
        } else {
            returnOrderDetails = createReturnOrderDetails((float) appeasementAmount, creditMemo);
            returnOrderDetailsList = new ArrayList<>();
            returnOrderDetailsList.add(returnOrderDetails);
            updateOrderAttribute(context, orderEdge.node().id(), returnOrderDetailsList,updatedMaxAmount);
        }
        Map<String, Object> attributes = new HashMap<>();
        attributes.putAll(context.getEvent().getAttributes());
        attributes.put(AMOUNT_TO_BE_REFUND, appeasementAmount);
        EventUtils.forwardEventInlineWithAttributes(
                context,
                eventName,
                attributes);
    }

    private ReturnOrderDetails createReturnOrderDetails(float appeasementAmount,GetCreditMemoByRefQuery.CreditMemo creditMemo) {
        ReturnOrderDetails returnOrderDetails  =  new ReturnOrderDetails();
        returnOrderDetails.setRefundKey(creditMemo.ref());
        returnOrderDetails.setAmount(appeasementAmount);
        returnOrderDetails.setStatus(INITIATED);
        returnOrderDetails.setReasonForReturn(creditMemo.items().edges().get(0).node().creditReasonCode().value());

        // Fetch the user id from the attributes and add it to the return order details
        Optional<GetCreditMemoByRefQuery.Attribute> optionalAttributeName =
                creditMemo.attributes().stream().filter(
                        attribute -> attribute.name().equalsIgnoreCase(USER_ID)
                ).findFirst();
        if(optionalAttributeName.isPresent()){
            returnOrderDetails.setUserId(optionalAttributeName.get().value().toString());
        }

        return returnOrderDetails;
    }
    private void updateOrderAttribute(ContextWrapper context, String orderId, List<ReturnOrderDetails> returnOrderDetailsList,
                                      double updatedMaxAmount) {
        List<AttributeInput> attributeInputList = createOrderAttributes(RETURN_ORDER_DETAILS, ATTRIBUTE_TYPE_JSON,
                returnOrderDetailsList,(float) updatedMaxAmount);
        OrderService orderService = new OrderService(context);
        orderService.upsertOrderAttributeList(orderId,attributeInputList);
    }

    public static List<AttributeInput> createOrderAttributes(String attributeName, String type, List<ReturnOrderDetails> returnOrderDetailsList,
                                                             float updatedMaxAmount) {
        List<AttributeInput> attributeInputList = new ArrayList<>();
        AttributeInput attrInput = AttributeInput.builder().name(attributeName).type(type).value(returnOrderDetailsList).build();
        attributeInputList.add(attrInput);
        AttributeInput attrInput2 = AttributeInput.builder().name(AVAILABLE_AMOUNT_TO_REFUND).type(ATTRIBUTE_TYPE_FLOAT).value(updatedMaxAmount).build();
        attributeInputList.add(attrInput2);
        return attributeInputList;
    }
}