package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.FulfilmentExceptionData;
import com.fluentcommerce.dto.fulfillment.FulfilmentItems;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import static com.fluentcommerce.util.Constants.*;

/**
 * @author Yamini Kukreja
 */

@RuleInfo(
        name = "UpdateAndSendEventForCancelledItems",
        description = "Fetch the exception resolution response and update the cancelled item line status for item with status {" +
                PROP_CANCEL_STATUS_RESPONSE + "} in response and call {" + PROP_EVENT_NAME + "} for un-reserving the inventory.",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "call event if any cancelled item to call webhooks"
)
@ParamString(
        name = PROP_CANCEL_STATUS_RESPONSE,
        description = "cancel status response of item"
)

@EventAttribute(name = FULFILMENT)
@Slf4j
public class UpdateAndSendEventForCancelledItems extends BaseRule {
        @Override
        public void run(ContextWrapper context) {
                RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME, PROP_CANCEL_STATUS_RESPONSE);
                Event event = context.getEvent();
                String eventName = context.getProp(PROP_EVENT_NAME);
                String cancelStatusResponse = context.getProp(PROP_CANCEL_STATUS_RESPONSE);
                Map<String, Object> eventAttributes = event.getAttributes();
                FulfilmentExceptionData fulfilmentException = CommonUtils.convertObjectToDto(eventAttributes.get(FULFILMENT),
                        new TypeReference<FulfilmentExceptionData>() {});
                String orderId = event.getRootEntityId();
                OrderService orderService = new OrderService(context);
                Order order = orderService.getOrderById(orderId);

                List<UpdateOrderItemWithOrderInput> updateInputOrderItems = new ArrayList<>();
                for (FulfilmentItems fulfilmentItem : fulfilmentException.getItems()) {
                        List<AttributeInput> attributes = new ArrayList<>();
                        if (StringUtils.equalsIgnoreCase(fulfilmentItem.getStatus(), cancelStatusResponse)) {
                                Optional<OrderItem> orderItem = order.getItems().stream().filter(i -> i.getRef()
                                        .equalsIgnoreCase(fulfilmentItem.getRef())).findFirst();
                                if(orderItem.isPresent()) {
                                        attributes.add(AttributeInput.builder().name(OLD_LINE_STATUS).type(ATTRIBUTE_TYPE_STRING)
                                                .value(orderItem.get().getAttributes().get(ORDER_LINE_STATUS).toString()).build());
                                        attributes.add(AttributeInput.builder().name(ORDER_LINE_STATUS).type(ATTRIBUTE_TYPE_STRING)
                                                .value(CANCELLED).build());
                                        attributes.add(AttributeInput.builder().name(REASON_FOR_CANCELLATION).type(ATTRIBUTE_TYPE_STRING)
                                                .value(fulfilmentItem.getCancelReason()).build());
                                        updateInputOrderItems.add(UpdateOrderItemWithOrderInput.builder().id(orderItem.get().getId())
                                                .quantity(0).attributes(attributes).build());
                                }
                        }
                }
                if(!updateInputOrderItems.isEmpty()){
                        orderService.updateOrderItems(updateInputOrderItems, orderId);
                        EventUtils.forwardEventInlineWithAttributes(
                                context,
                                eventName,
                                eventAttributes);
                }
        }
}
