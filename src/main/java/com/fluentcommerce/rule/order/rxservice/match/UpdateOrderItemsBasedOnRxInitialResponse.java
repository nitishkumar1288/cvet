package com.fluentcommerce.rule.order.rxservice.match;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.model.rx.Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;
import static com.fluentcommerce.util.EventUtils.forwardInline;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateOrderItemsBasedOnRxInitialResponse",
        description = "Update the order items status with respect to Rx match initial response and call {" + PROP_EVENT_NAME + "}",
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
        description = "event name"
)

@EventAttribute(name = ITEMS)
@Slf4j
public class UpdateOrderItemsBasedOnRxInitialResponse extends BaseRule {

    private static final String CLASS_NAME = UpdateOrderItemsBasedOnRxInitialResponse.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);

        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<Items> items = CommonUtils.convertObjectToDto(eventAttributes.get(ITEMS),new TypeReference<List<Items>>(){});

        List<UpdateOrderItemWithOrderInput> updateInputOrderItems = getOrderItemsForUpdate(order,items);
        if (CollectionUtils.isNotEmpty(updateInputOrderItems)){
            log.info("[{}][{}]Updating orderItem with input {}", event.getAccountId(), CLASS_NAME, updateInputOrderItems);
            orderService.updateOrderItems(updateInputOrderItems,orderId);
            log.info("[{}] - rule: {} forwarding to next eventName {}", event.getAccountId(), CLASS_NAME, eventName);
            forwardInline(context,eventName);
        }
    }

    private List<UpdateOrderItemWithOrderInput> getOrderItemsForUpdate(Order order, List<Items> items) {
        List<UpdateOrderItemWithOrderInput> updateItems = new ArrayList<>();
        if (Objects.nonNull(order) && Objects.nonNull(items)){
            for(Items item:items){
                Optional<OrderItem> orderLineItem = order.getItems().stream().filter(
                        orderItem -> orderItem.getRef().equalsIgnoreCase(item.getLineNumber())
                ).findFirst();
                if(orderLineItem.isPresent()){
                        updateItems.add(UpdateOrderItemWithOrderInput.builder()
                                .id(orderLineItem.get().getId())
                                .attributes(getOrderItemAttribute(item))
                                .build());
                }
            }
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute(Items item) {
        List<AttributeInput> itemAttributes = new ArrayList<>();

        String status = null;
        if (item.getStatus().equalsIgnoreCase(ORDER_ITEM_STATUS_ACTIVE) || item.getStatus().equalsIgnoreCase(ORDER_ITEM_STATUS_APPROVED)) {
            status = RX_MATCH_ORDER_STATUS_RX_ACTIVE;
        } else if (item.getStatus().equalsIgnoreCase(ORDER_ITEM_STATUS_PENDING)) {
            status = RX_MATCH_ORDER_STATUS_RX_PENDING;
        } else {
            status = LINE_ITEM_CANCELLED;
        }

        itemAttributes.add(AttributeInput.builder()
                .name(ORDER_LINE_STATUS)
                .type(ATTRIBUTE_TYPE_STRING)
                .value(status)
                .build());
        if (item.getPrescriptionId() != null) {
            itemAttributes.add(AttributeInput.builder()
                    .name(PRESCRIPTION_ID)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(item.getPrescriptionId())
                    .build());
        }
        if (item.getFacilityId() != null) {
            itemAttributes.add(AttributeInput.builder()
                    .name(FACILITY_ID)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(item.getFacilityId())
                    .build());
        }
        if (item.getRefillsRemaining() != null) {
            itemAttributes.add(AttributeInput.builder()
                    .name(REFILLS_REMAINING)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(item.getRefillsRemaining())
                    .build());
        }
        if (item.getIsPrescriptionRequired() != null) {
            itemAttributes.add(AttributeInput.builder()
                    .name(IS_PRESCRIPTION_REQUIRED)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(item.getIsPrescriptionRequired())
                    .build());
        }
        return itemAttributes;
    }
}
