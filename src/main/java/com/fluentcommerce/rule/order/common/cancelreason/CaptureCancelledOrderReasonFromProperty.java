package com.fluentcommerce.rule.order.common.cancelreason;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.CancelledOrderReasonPayload;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.OrderUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CaptureCancelledOrderReasonFromProperty",
        description = "in case of order cancellation capture the reason {" + CANCELLATION_REASON + "}  and " +
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
        name = CANCELLATION_REASON,
        description = "order cancellation reason"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)
@Slf4j
public class CaptureCancelledOrderReasonFromProperty extends BaseRule {
    private static final String CLASS_NAME = CaptureCancelledOrderReasonFromProperty.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME,CANCELLATION_REASON);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String cancellationReason = context.getProp(CANCELLATION_REASON);
        Event event = context.getEvent();
        String orderRef = context.getEvent().getEntityRef();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        CancelledOrderReasonPayload payload = OrderUtils.createPayload(cancellationReason,orderRef);
        Map<String, Object> attributes = new HashMap<>();
        attributes.putAll(context.getEvent().getAttributes());
        attributes.put(ORDER_CANCELLED_REASON_PAYLOAD,payload);
        EventUtils.forwardEventInlineWithAttributes(
                context,
                eventName,
                attributes);
    }
}
