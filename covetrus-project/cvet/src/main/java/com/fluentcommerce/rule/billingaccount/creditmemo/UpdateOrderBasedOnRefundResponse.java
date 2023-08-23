package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.billingaccount.creditmemo.RefundCreditResponse;
import com.fluentcommerce.dto.billingaccount.creditmemo.ReturnOrderDetails;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateOrderBasedOnRefundResponse",
        description = "update the sales order based on refund response .If previously sales order is not updated as successful" +
                " call {" + PROP_EVENT_NAME + "} else call {" + PROP_NO_MATCHING_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_CREDIT_MEMO)
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "forward to next Event name"
)

@Slf4j
public class UpdateOrderBasedOnRefundResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,PROP_NO_MATCHING_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);
        Event event = context.getEvent();
        RefundCreditResponse refundResponse = CommonUtils.convertObjectToDto(event.getAttributes(),
                new TypeReference<RefundCreditResponse>(){});
        if (null != refundResponse && null != refundResponse.getRefundKey()){
            String refundKey = refundResponse.getRefundKey();
            CreditMemoService creditMemoService = new CreditMemoService(context);
            GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(refundKey);
            if (null != creditMemo && null != creditMemo.order()){
            String orderRef = creditMemo.order().ref();
            OrderService orderService = new OrderService(context);
            GetOrdersByRefQuery.Edge orderEdge = orderService.getOrdersByRef(orderRef);
            if (null != orderEdge && null != orderEdge.node() && CollectionUtils.isNotEmpty(orderEdge.node().attributes()))
                fetchReturnOrderToUpdateAndForwardEvent(context, eventName, noMatchEventName, refundResponse, orderEdge);
            }else {
                context.addLog("Refund key is not the valid one::"+ refundKey);
            }
        }
    }

    private void fetchReturnOrderToUpdateAndForwardEvent(ContextWrapper context, String eventName, String noMatchEventName,
                                                         RefundCreditResponse refundResponse, GetOrdersByRefQuery.Edge orderEdge) {
        Optional<GetOrdersByRefQuery.Attribute> returnOrderDetailsAttribute = orderEdge.node().attributes().stream().
                filter(attribute -> attribute.name().equalsIgnoreCase(RETURN_ORDER_DETAILS)).findFirst();
        List<ReturnOrderDetails> returnOrderDetailsList;
        if (returnOrderDetailsAttribute.isPresent()) {
            returnOrderDetailsList = fetchReturnOrderListToUpdate(returnOrderDetailsAttribute.get(), refundResponse, context);
            if (CollectionUtils.isNotEmpty(returnOrderDetailsList)){
                updateOrderAttribute(context, orderEdge.node().id(), returnOrderDetailsList, eventName);
            }else {
                EventUtils.forwardInline(context, noMatchEventName);
            }
        }
    }

    private List<ReturnOrderDetails> fetchReturnOrderListToUpdate(GetOrdersByRefQuery.Attribute returnOrderDetailsAttribute,
                                                                  RefundCreditResponse refundResponse,ContextWrapper context) {
        List<ReturnOrderDetails>  returnOrderDetailsList = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            returnOrderDetailsList = mapper.readValue(returnOrderDetailsAttribute.value().toString(),
                    new TypeReference<List<ReturnOrderDetails>>() {});
            if (CollectionUtils.isNotEmpty(returnOrderDetailsList)) {
                Optional<ReturnOrderDetails> optionalReturnOrderDetails = returnOrderDetailsList.stream().
                        filter(data->data.getRefundKey().equalsIgnoreCase(refundResponse.getRefundKey())).findFirst();
                if (optionalReturnOrderDetails.isPresent() && !optionalReturnOrderDetails.get().getStatus().equalsIgnoreCase(SUCCESSFUL)){
                    optionalReturnOrderDetails.get().setStatus(refundResponse.getStatus());
                }else {
                    return new ArrayList<>();
                }
            }
        } catch (IOException exception) {
            context.addLog("Exception Occurred when fetching returnOrderDetails attribute" + exception.getMessage());
        }
        return returnOrderDetailsList;
    }

    private void updateOrderAttribute(ContextWrapper context, String orderId, List<ReturnOrderDetails> returnOrderDetailsList,String eventName) {

        List<AttributeInput> attributeInputList = createOrderAttributes(RETURN_ORDER_DETAILS, ATTRIBUTE_TYPE_JSON,
                returnOrderDetailsList);
        OrderService orderService = new OrderService(context);
        orderService.upsertOrderAttributeList(orderId,attributeInputList);
        forwardToNextEvent(context,eventName);
    }

    private void forwardToNextEvent(ContextWrapper context, String eventName) {
        EventUtils.forwardInline(context,eventName);
    }

    private List<AttributeInput> createOrderAttributes(String attributeName, String type,
                                                       List<ReturnOrderDetails> returnOrderDetailsList) {
        List<AttributeInput> attributeInputList = new ArrayList<>();
        AttributeInput attrInput = AttributeInput.builder().name(attributeName).type(type).value(returnOrderDetailsList).build();
        attributeInputList.add(attrInput);
        return attributeInputList;
    }
}