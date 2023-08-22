package com.fluentcommerce.rule.order.autocancellation;

import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import com.fluentretail.rubix.v2.context.Context;
import com.fluentretail.rubix.v2.rule.Rule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateLineItemCancellationReason",
        description = "get the order line item status {" + PROP_ORDER_LINE_STATUS + "} and if order item is " +
                "for cancellation update the order item with reason {" + REASON_FOR_CANCELLATION + "}",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_ORDER_LINE_STATUS,
        description = "order line item status"
)
@ParamString(
        name = REASON_FOR_CANCELLATION,
        description = "reason for cancellation"
)
@Slf4j
public class UpdateLineItemCancellationReason implements Rule {
    private static final String CLASS_NAME = UpdateLineItemCancellationReason.class.getSimpleName();
    @Override
    public void run(Context context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_ORDER_LINE_STATUS,REASON_FOR_CANCELLATION);
        String orderLineItemStatus = context.getProp(PROP_ORDER_LINE_STATUS);
        String reasonForCancellationMsg = context.getProp(REASON_FOR_CANCELLATION);
        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderItems(orderId);
        List<UpdateOrderItemWithOrderInput> updateItems = getOrderItemForUpdate(orderData,reasonForCancellationMsg,orderLineItemStatus);
        if (CollectionUtils.isNotEmpty(updateItems)){
            orderService.updateOrderItems(updateItems,orderId);
        }
    }

    private List<UpdateOrderItemWithOrderInput> getOrderItemForUpdate(GetOrderByIdQuery.OrderById orderData,
                                                      String reasonForCancellationMsg,String orderLineItemStatus) {
        List<UpdateOrderItemWithOrderInput> updateItems = null;
        if (orderData != null && orderData.orderItems() != null
                && CollectionUtils.isNotEmpty(orderData.orderItems().orderItemEdge())
        ){
            updateItems = new ArrayList<>();
            for (GetOrderByIdQuery.OrderItemEdge itemEdge:orderData.orderItems().orderItemEdge()){
                Optional<GetOrderByIdQuery.OrderItemAttribute> orderLineItem = itemEdge.orderItemNode().orderItemAttributes().stream().filter(
                        itemAttr -> itemAttr.name().equalsIgnoreCase(ORDER_LINE_STATUS) && itemAttr.value().toString().equals(orderLineItemStatus)
                ).findFirst();
                if (orderLineItem.isPresent()){
                    updateItems.add(UpdateOrderItemWithOrderInput.builder()
                            .id(itemEdge.orderItemNode().id())
                            .attributes(getOrderItemAttribute(reasonForCancellationMsg))
                            .build());
                }
            }
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute(String message) {
        List<AttributeInput> itemAttributes = new ArrayList<>();
        itemAttributes.add(AttributeInput.builder()
                .name(REASON_FOR_CANCELLATION)
                .type(ATTRIBUTE_TYPE_STRING)
                .value(message)
                .build());
        return itemAttributes;

    }
}
