package com.fluentcommerce.rule.order.pv1.exceptionresolution;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.CancelledOrderReasonPayload;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.OrderUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Yamini Kukreja
 */

@RuleInfo(
        name = "CaptureCancelledOrderReasonFromPV1Response",
        description = "In case of order cancellation, capture the cancel reason from PV1 response for item with response status as {"
                + PROP_CANCEL_STATUS_RESPONSE + "} and forward  to next webhook event {" + PROP_EVENT_NAME + "}",
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
        name = PROP_CANCEL_STATUS_RESPONSE,
        description = "cancel status response"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)

@EventAttribute(name = ITEMS)
@Slf4j
public class CaptureCancelledOrderReasonFromPV1Response extends BaseRule {
    private static final String CLASS_NAME = CaptureCancelledOrderReasonFromPV1Response.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String cancelStatusResponse = context.getProp(PROP_CANCEL_STATUS_RESPONSE);
        Event event = context.getEvent();
        String orderRef = event.getEntityRef();
        List<PV1Items> items = event.getAttributeList(ITEMS, PV1Items.class);
        String cancellationReason = null;
        if(!items.isEmpty()) {
            for(PV1Items item: items) {
                if(StringUtils.equalsIgnoreCase(cancelStatusResponse, item.getStatus())) {
                    cancellationReason = item.getCancelReason();
                }
            }
        }
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        CancelledOrderReasonPayload payload = OrderUtils.createPayload(cancellationReason,orderRef);
        Map<String, Object> attributes = new HashMap<>(event.getAttributes());
        attributes.put(ORDER_CANCELLED_REASON_PAYLOAD,payload);
        EventUtils.forwardEventInlineWithAttributes(
                context,
                eventName,
                attributes);
    }
}
