package com.fluentcommerce.rule.order.common;

import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
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

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "OrderItemCancellationBasedOnItemStatus",
        description = "Cancel the order item for those item status are {" + PROP_ORDER_LINE_STATUS + "}",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_ORDER_LINE_STATUS,
        description = "order item status"
)
@Slf4j
public class OrderItemCancellationBasedOnItemStatus implements Rule {

    private static final String CLASS_NAME = OrderItemCancellationBasedOnItemStatus.class.getSimpleName();
    @Override
    public void run(Context context) {

        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_ORDER_LINE_STATUS);
        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        String orderId = context.getEvent().getEntityId();
        String orderItemStatus = context.getProp(PROP_ORDER_LINE_STATUS);
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        List<UpdateOrderItemWithOrderInput> updateInputOrderItems = getOrderItemForCancellation(order,orderItemStatus);
        if (CollectionUtils.isNotEmpty(updateInputOrderItems)){
            orderService.updateOrderItems(updateInputOrderItems,orderId);
        }
    }

    private List<UpdateOrderItemWithOrderInput> getOrderItemForCancellation(Order order,String orderItemStatus) {
        List<UpdateOrderItemWithOrderInput> updateItems = new ArrayList<>();
        order.getItems().forEach(items->
            items.getAttributes().forEach((attributeName,attributeValue)->{
                if(attributeName.equalsIgnoreCase(ORDER_LINE_STATUS) && attributeValue.toString().equalsIgnoreCase(orderItemStatus)){
                    updateItems.add(UpdateOrderItemWithOrderInput.builder()
                        .id(items.getId())
                        .quantity(0)
                        .build());
                }
            }));
        return updateItems;
    }
}