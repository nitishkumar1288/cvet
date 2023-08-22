package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.billingaccount.creditmemo.ReturnItem;
import com.fluentcommerce.dto.billingaccount.creditmemo.ReturnOrderDetails;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.graphql.query.returns.GetReturnOrderByRefQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.service.returns.ReturnService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
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
        name = "UpdateOrderAttributeAfterReturnOrderCreated",
        description = "update order attribute once return order created",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_CREDIT_MEMO)
        }
)
@EventAttribute(name = AMOUNT_TO_BE_REFUND)
@Slf4j
public class UpdateOrderAttributeAfterReturnOrderCreated extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String creditMemoRef = event.getEntityRef();
        double amountToBeRefund = context.getEvent().getAttribute(AMOUNT_TO_BE_REFUND, Double.class);
        context.addLog("UpdateOrderAttributeAfterReturnOrderCreated::amountToBeRefund"+amountToBeRefund);
        CreditMemoService creditMemoService = new CreditMemoService(context);
        GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(creditMemoRef);
        if (null != creditMemo){
            double maxRefundableAmountForOrder = 0;
            String returnOrderRef = creditMemo.returnOrder().ref();
            ReturnService service = new ReturnService(context);
            GetReturnOrderByRefQuery.ReturnOrder returnOrder = service.getReturnOrderByRef(returnOrderRef);
            String orderRef = creditMemo.order().ref();
            String orderId = null;
            OrderService orderService = new OrderService(context);
            GetOrdersByRefQuery.Edge orderEdge = orderService.getOrdersByRef(orderRef);
            Optional<GetOrdersByRefQuery.Attribute> returnOrderDetailsAttribute = Optional.empty();
            if (null != orderEdge && null != orderEdge.node() && CollectionUtils.isNotEmpty(orderEdge.node().attributes())){
                Optional<GetOrdersByRefQuery.Attribute> maxRefundableAmountForOrderAtt = orderEdge.node().attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(AVAILABLE_AMOUNT_TO_REFUND)).findFirst();
                returnOrderDetailsAttribute = orderEdge.node().attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(RETURN_ORDER_DETAILS)).findFirst();
                if (maxRefundableAmountForOrderAtt.isPresent()){
                    maxRefundableAmountForOrder =  Double.parseDouble(maxRefundableAmountForOrderAtt.get().value().toString());
                }
                orderId = orderEdge.node().id();
            }
            List<ReturnOrderDetails> returnOrderDetailsList = null;
            ReturnOrderDetails returnOrderDetails = null;
            if (returnOrderDetailsAttribute.isPresent()) {
                fetchReturnOrderDetailsAndUpdateOrder(context, creditMemoRef, amountToBeRefund, maxRefundableAmountForOrder, returnOrder, orderId, returnOrderDetailsAttribute);
            } else {
                returnOrderDetails = createReturnOrderDetails(returnOrder,amountToBeRefund,creditMemoRef);
                returnOrderDetailsList = new ArrayList<>();
                returnOrderDetailsList.add(returnOrderDetails);
                updateOrderAttribute(context, orderId, returnOrderDetailsList,maxRefundableAmountForOrder,amountToBeRefund);
            }
        }
    }

    private void fetchReturnOrderDetailsAndUpdateOrder(ContextWrapper context, String creditMemoRef, double amountToBeRefund,
                                                       double maxRefundableAmountForOrder, GetReturnOrderByRefQuery.ReturnOrder returnOrder,
                                                       String orderId, Optional<GetOrdersByRefQuery.Attribute> returnOrderDetailsAttribute) {
        List<ReturnOrderDetails> returnOrderDetailsList = null;
        ReturnOrderDetails returnOrderDetails;
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (returnOrderDetailsAttribute.isPresent()){
                returnOrderDetailsList = mapper.readValue(returnOrderDetailsAttribute.get().value().toString(),
                        new TypeReference<List<ReturnOrderDetails>>() {});
            }
            if (CollectionUtils.isNotEmpty(returnOrderDetailsList)) {
                returnOrderDetails = createReturnOrderDetails(returnOrder, amountToBeRefund, creditMemoRef);
                returnOrderDetailsList.add(returnOrderDetails);
                updateOrderAttribute(context, orderId, returnOrderDetailsList, maxRefundableAmountForOrder, amountToBeRefund);
            }
        } catch (IOException exception) {
            context.addLog("Exception Occurred when fetching returnOrderDetails attribute" + exception.getMessage());
        }
    }

    private ReturnOrderDetails createReturnOrderDetails(GetReturnOrderByRefQuery.ReturnOrder returnOrder,double amountToBeRefund,String creditMemoRef) {
            ReturnOrderDetails returnOrderDetails  =  new ReturnOrderDetails();
            Optional<GetReturnOrderByRefQuery.Attribute1> returnOrderCreatedByUserId = returnOrder.attributes().stream().
                    filter(attribute1 -> attribute1.name().equalsIgnoreCase(RETURN_ORDER_CREATED_USER_ID)).findFirst();
            Optional<GetReturnOrderByRefQuery.Attribute1> returnReason = returnOrder.attributes().stream().
                filter(attribute1 -> attribute1.name().equalsIgnoreCase(RETURN_REASON)).findFirst();

            if (returnOrderCreatedByUserId.isPresent()){
                returnOrderDetails.setUserId(returnOrderCreatedByUserId.get().value().toString());
            }
            if (returnReason.isPresent()){
                returnOrderDetails.setReasonForReturn(returnReason.get().value().toString());
            }
            returnOrderDetails.setRefundKey(creditMemoRef);
            returnOrderDetails.setAmount((float) amountToBeRefund);
            returnOrderDetails.setStatus(INITIATED);
            returnOrderDetails.setReturnOrderRef(returnOrder.ref());
            ArrayList<ReturnItem> returnItemList = new ArrayList<>();
            for(GetReturnOrderByRefQuery.Edge2 edge:returnOrder.returnOrderItems().edges()){
                ReturnItem returnItem = new ReturnItem();
                returnItem.setOrderItemKey(edge.node().ref());
                returnItem.setProductRef(edge.node().product().ref());
                returnItem.setQuantity(edge.node().unitQuantity().quantity());
                returnItemList.add(returnItem);
            }
            returnOrderDetails.setRefundedItems(returnItemList);

        return returnOrderDetails;
    }

    private void updateOrderAttribute(ContextWrapper context, String orderId, List<ReturnOrderDetails> returnOrderDetailsList,
                                      double maxRefundableAmountForOrder,double amountToBeRefund) {
        List<AttributeInput> attributeInputList = createOrderAttributes(RETURN_ORDER_DETAILS, ATTRIBUTE_TYPE_JSON,
                returnOrderDetailsList,maxRefundableAmountForOrder,amountToBeRefund);
        OrderService orderService = new OrderService(context);
        orderService.upsertOrderAttributeList(orderId,attributeInputList);
    }

    public static List<AttributeInput> createOrderAttributes(String attributeName, String type, List<ReturnOrderDetails> returnOrderDetailsList,
                                                                         double maxRefundableAmount,double amountToBeRefund) {
        List<AttributeInput> attributeInputList = new ArrayList<>();
        AttributeInput attrInput = AttributeInput.builder().name(attributeName).type(type).value(returnOrderDetailsList).build();
        attributeInputList.add(attrInput);
        float maxRefundableAmountForOrder = (float) (maxRefundableAmount - amountToBeRefund);
        AttributeInput attrInput2 = AttributeInput.builder().name(AVAILABLE_AMOUNT_TO_REFUND).type(ATTRIBUTE_TYPE_FLOAT)
                .value(maxRefundableAmountForOrder).build();
        attributeInputList.add(attrInput2);
        return attributeInputList;
    }
}