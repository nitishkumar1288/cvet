package com.fluentcommerce.rule.order.common.sendevent;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import static com.fluentcommerce.util.Constants.*;

@RuleInfo(
        name = "SendEventForFulfillment",
        description = "Send an {" + PROP_EVENT_NAME + "} event for the associated Order",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = ENTITY_TYPE_FULFILMENT
                )
        },
        accepts = {
                @EventInfo(
                        entityType = ENTITY_TYPE_ORDER
                )
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "The name of event to be triggered"
)

@EventAttribute(name = FULFILLMENT_ID)
@EventAttribute(name = FULFILLMENT_REF)
@Slf4j
public class SendEventForFulfillment extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        Event event = context.getEvent();
        String eventName = context.getProp(PROP_EVENT_NAME);
        String fulfillmentId = context.getEvent().getAttribute(FULFILLMENT_ID, String.class);
        String fulfillmentRef = context.getEvent().getAttribute(FULFILLMENT_REF, String.class);
        if (null != fulfillmentId && null != fulfillmentRef){
            context.addLog("forwarding to next event name with fulfillmentId::"+ fulfillmentId);
            EventUtils.forwardEventToChildEntity(context, event, eventName, fulfillmentId, fulfillmentRef, ENTITY_TYPE_FULFILMENT,
                    ENTITY_TYPE_FULFILMENT);
        }
    }
}
