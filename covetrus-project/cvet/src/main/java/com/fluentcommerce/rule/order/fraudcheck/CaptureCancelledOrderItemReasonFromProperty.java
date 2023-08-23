package com.fluentcommerce.rule.order.fraudcheck;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.CancelledOrderItemReasonPayload;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.OrderUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CaptureCancelledOrderItemReasonFromProperty",
        description = "in case of order item cancellation capture the reason {" + CANCELLATION_REASON + "}  and " +
                "forward  to next webhook event {" + PROP_EVENT_NAME + "}",
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
@ParamString(
        name = CANCELLATION_REASON,
        description = "order cancellation reason"
)
@Slf4j
public class CaptureCancelledOrderItemReasonFromProperty extends BaseRule {
    private static final String CLASS_NAME = CaptureCancelledOrderItemReasonFromProperty.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME,CANCELLATION_REASON);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String cancellationReason = context.getProp(CANCELLATION_REASON);
        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        String orderId = context.getEvent().getEntityId();
        String orderRef = context.getEvent().getEntityRef();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(orderId);
        CancelledOrderItemReasonPayload payload = OrderUtils.createPayloadForCancelledOrderItem(order,cancellationReason,orderRef);
        if (CollectionUtils.isNotEmpty(payload.getItems())){
            Map<String, Object> attributes = new HashMap<>();
            attributes.putAll(context.getEvent().getAttributes());
            attributes.put(ORDER_ITEM_CANCELLED_REASON_PAYLOAD,payload);
            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    attributes);
        }
    }
}