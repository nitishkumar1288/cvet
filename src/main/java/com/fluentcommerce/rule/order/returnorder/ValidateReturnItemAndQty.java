package com.fluentcommerce.rule.order.returnorder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentTransaction;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfillmentByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.isDigits;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "ValidateReturnItemAndQty",
        description = "Validates the return order item based on order item status and the quantity",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@EventAttribute(name = RETURN_ITEMS)
@Slf4j
public class ValidateReturnItemAndQty extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderById = orderService.getOrderWithFulfilmentItems(orderId);
        List<Map<String, Object>> eventReturnItems = context.getEvent().getAttribute(RETURN_ITEMS, List.class);
        ArrayList<String> orderItemRefList = fetchValidOrderItemRefList(eventReturnItems,orderById);
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfillmentByRefQuery.Fulfilments fulfilments = fulfilmentService.getFulfillments(orderItemRefList);
        validateItemsForReturn(fulfilments,context);
    }


    private void validateItemsForReturn(GetFulfillmentByRefQuery.Fulfilments fulfilments,ContextWrapper context) {
        Optional<GetFulfillmentByRefQuery.Edge> fulfillment = fulfilments.edges().stream().
                filter(fulfilment -> fulfilment.node().status().equalsIgnoreCase(FULFILLED)).findFirst();
        if (fulfillment.isPresent()){
            Optional<GetFulfillmentByRefQuery.Attribute> optionalFulfilmentAttribute = fulfillment.get().node().
                    attributes().stream().filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_TRANSACTION)).findFirst();
            if (optionalFulfilmentAttribute.isPresent()){
                List<PaymentTransaction> paymentTransactionList = null;
                ObjectMapper mapper = new ObjectMapper();
                try {
                    paymentTransactionList = mapper.readValue(optionalFulfilmentAttribute.get().value().toString(), new TypeReference<List<PaymentTransaction>>() {});
                    if (CollectionUtils.isNotEmpty(paymentTransactionList)){
                        Optional<PaymentTransaction> optionalPaymentTransaction = paymentTransactionList.stream().
                                filter(paymentTransaction -> paymentTransaction.getStatus().equalsIgnoreCase(PAYMENT_SETTLED_STATUS_SUCCESS)).findFirst();
                        if (!optionalPaymentTransaction.isPresent()){
                            throw new IllegalArgumentException(format("payment is not settled for the orderItem that is belongs to fulfilment ref %s ", fulfillment.get().node().ref()));
                        }
                    }
                } catch (IOException exception) {
                    context.addLog("Exception Occurred when fetching orderActivity attribute" + exception.getMessage());
                }
            }
        }
    }

    private ArrayList<String> fetchValidOrderItemRefList(List<Map<String, Object>> eventReturnItems, GetOrderByIdQuery.OrderById orderById) {
        ArrayList<String> orderItemRefList = null;
        if (CollectionUtils.isNotEmpty(eventReturnItems)){
            orderItemRefList = new ArrayList<>();
            validateAvailableAmountToRefund(orderById);
            if (null != orderById && null != orderById.orderItems() && CollectionUtils.isNotEmpty(orderById.orderItems().orderItemEdge())){
                validateData(eventReturnItems, orderById, orderItemRefList);
            }

        }
        return orderItemRefList;
    }

    private void validateData(List<Map<String, Object>> eventReturnItems, GetOrderByIdQuery.OrderById orderById, ArrayList<String> orderItemRefList) {
        for (Map<String, Object> returnItem: eventReturnItems){
            String orderItemRef = (String) returnItem.get(ORDER_ITEM_REF);
            Optional<GetOrderByIdQuery.OrderItemEdge> orderItem = orderById.orderItems().orderItemEdge().stream().
                    filter(item -> item.orderItemNode().ref().equalsIgnoreCase(orderItemRef)).findFirst();
            if (orderItem.isPresent()){
                Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemAttribute = orderItem.get().
                        orderItemNode().orderItemAttributes().stream().filter(
                        orderItemAttribute -> ((orderItemAttribute.name().equalsIgnoreCase(ORDER_LINE_STATUS))
                                && (orderItemAttribute.value().toString().equalsIgnoreCase(SHIPPED)))).findFirst();
                validateRefundQuantity(orderItemRefList, returnItem, orderItemRef, orderItem, optionalOrderItemAttribute);
            }else{
                throw new IllegalArgumentException(format("orderItem ref %s does not belongs to a valid order", orderItemRef));
            }
        }
    }

    private void validateRefundQuantity(ArrayList<String> orderItemRefList, Map<String, Object> returnItem, String orderItemRef, Optional<GetOrderByIdQuery.OrderItemEdge> orderItem, Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemAttribute) {
        if (optionalOrderItemAttribute.isPresent()){
            Map<String, Object> unitQuantityMap = (Map<String, Object>) returnItem.get(UNIT_QUANTITY);
            Integer eventAttrQuantity = Integer.valueOf(unitQuantityMap.get(QUANTITY).toString());
            if (orderItem.isPresent() && orderItem.get().orderItemNode().quantity() == eventAttrQuantity && hasAttributeAndValue(orderItem.get().orderItemNode())){
                List<GetOrderByIdQuery.OrderItemAttribute> orderItemAttr = getAttributeByName(orderItem.get().orderItemNode(), RETURNABLE_QTY_ATTR);
                if (CollectionUtils.isNotEmpty(orderItemAttr)){
                    Integer attrReturnableQty = Integer.valueOf(orderItemAttr.get(0).value().toString());
                    if (attrReturnableQty < eventAttrQuantity) {
                        throw new IllegalArgumentException(format("orderItem ref %s has more quantity %s than requested %s",
                                orderItem.get().orderItemNode().ref(), attrReturnableQty, eventAttrQuantity));
                    }
                    orderItemRefList.add(orderItem.get().orderItemNode().ref());
                }
            }else{
                throw new IllegalArgumentException(format("partial quantity is not allowed for the order item ref %s return for the orderItem", orderItemRef));
            }
        }else{
            throw new IllegalArgumentException(format("orderItem ref %s is not in desired status", orderItemRef));
        }
    }

    private void validateAvailableAmountToRefund(GetOrderByIdQuery.OrderById orderById) {
        if (null != orderById && CollectionUtils.isNotEmpty(orderById.attributes())){
            Optional<GetOrderByIdQuery.Attribute> maxRefundableAmountForOrder = orderById.attributes().stream().
                    filter(attribute -> attribute.name().equalsIgnoreCase(AVAILABLE_AMOUNT_TO_REFUND)).findFirst();
            if (maxRefundableAmountForOrder.isPresent() && Double.parseDouble(maxRefundableAmountForOrder.get().value().toString()) == 0){
                throw new IllegalArgumentException(format("Total refundable amount is 0 .Hence could not proceed with the refund request for the orderId  %s ", orderById.id()));
            }
        }
    }

    protected boolean hasAttributeAndValue(GetOrderByIdQuery.OrderItemNode orderItemNode) {
        List<GetOrderByIdQuery.OrderItemAttribute> orderItemAttrList = getAttributeByName(orderItemNode, RETURNABLE_QTY_ATTR);
        if (CollectionUtils.isEmpty(orderItemAttrList)) {
            return false;
        }
        if (orderItemAttrList.size() > 1) {
            throw new IllegalArgumentException(format("More than 1 attributes %s found for order item ref %s",
                    RETURNABLE_QTY_ATTR, orderItemNode.ref()));
        }
        GetOrderByIdQuery.OrderItemAttribute orderItemAttr = orderItemAttrList.get(0);
        if (!isDigits(orderItemAttr.value().toString())) {
            throw new IllegalArgumentException(format("Attribute %s has not digital value for order item ref %s",
                    RETURNABLE_QTY_ATTR, orderItemNode.ref()));
        }
        return true;
    }

    private List<GetOrderByIdQuery.OrderItemAttribute> getAttributeByName(GetOrderByIdQuery.OrderItemNode orderItemNode,
                                                                          String name) {
        if (isEmpty(orderItemNode.orderItemAttributes())) {
            return new ArrayList<>();
        }
        return orderItemNode.orderItemAttributes().stream().filter(attribute -> name.equalsIgnoreCase(attribute.name())).collect(toList());
    }
}