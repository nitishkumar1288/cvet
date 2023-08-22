package com.fluentcommerce.rule.order.common.orderitemstatus;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;
import static com.fluentcommerce.util.EventUtils.forwardInline;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CaptureOrderItemStatusBeforeUpdateItem",
        description = "before update the order item capture the current order line status at item attribute level and " +
                "forward to event {" + PROP_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)
@Slf4j
public class CaptureOrderItemStatusBeforeUpdateItem extends BaseRule {
    private static final String CLASS_NAME = CaptureOrderItemStatusBeforeUpdateItem.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderItems(orderId);
        List<UpdateOrderItemWithOrderInput> updateInputOrderItems = getOrderItemForUpdate(orderData,context);
        if (CollectionUtils.isNotEmpty(updateInputOrderItems)){
            log.info("[{}][{}]Updating orderItem with input {}", event.getAccountId(), CLASS_NAME, updateInputOrderItems);
            context.addLog("order item status change and move for update order item with old status::"+updateInputOrderItems);
            orderService.updateOrderItems(updateInputOrderItems,orderId);
            log.info("[{}] - rule: {} forwarding to next eventName {}", event.getAccountId(), CLASS_NAME, eventName);
            forwardInline(context,eventName);
        }
    }

    private List<UpdateOrderItemWithOrderInput> getOrderItemForUpdate(GetOrderByIdQuery.OrderById orderData,ContextWrapper context) {
        List<UpdateOrderItemWithOrderInput> updateItems = null;
        if (orderData != null && orderData.orderItems() != null
                && CollectionUtils.isNotEmpty(orderData.orderItems().orderItemEdge())
        ){
            updateItems = new ArrayList<>();
            for (GetOrderByIdQuery.OrderItemEdge itemEdge:orderData.orderItems().orderItemEdge()){
                Optional<GetOrderByIdQuery.OrderItemAttribute> orderLineItem = itemEdge.orderItemNode().orderItemAttributes().stream().filter(
                        itemAttr -> itemAttr.name().equalsIgnoreCase(ORDER_LINE_STATUS)).findFirst();
                if (orderLineItem.isPresent()){
                    context.addLog("order item update with old status::"+ orderLineItem);
                    updateItems.add(UpdateOrderItemWithOrderInput.builder()
                            .id(itemEdge.orderItemNode().id())
                            .attributes(getOrderItemAttribute(orderLineItem.get().value().toString()))
                            .build());
                }
            }
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute(String message) {
        List<AttributeInput> itemAttributes = new ArrayList<>();
        itemAttributes.add(AttributeInput.builder()
                .name(OLD_LINE_STATUS)
                .type(ATTRIBUTE_TYPE_STRING)
                .value(message)
                .build());
        return itemAttributes;
    }
}
