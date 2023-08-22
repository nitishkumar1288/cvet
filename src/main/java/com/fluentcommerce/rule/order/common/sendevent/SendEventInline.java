package com.fluentcommerce.rule.order.common.sendevent;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */

@RuleInfo(
        name = "SendEventInline",
        description = "send event {" + PROP_EVENT_NAME + "} as inline event" ,
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
        description = "Event Name"
)
@Slf4j
public class SendEventInline extends BaseRule {

    private static final String CLASS_NAME = SendEventInline.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);

        EventUtils.forwardInline(
                context,
                eventName
        );
    }
}