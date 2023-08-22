package com.fluentcommerce.rule.order.allocation.otcitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "CheckRxItemsAreInvolvedInAllocation",
        description = "Check Rx items are involved in the allocation process and Sends event {" + PROP_EVENT_NAME + "}. else send event {"+ PROP_NO_MATCHING_EVENT_NAME +"}",
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

@ParamString(name = PROP_EVENT_NAME, description = "Event name triggered by this rule")
@ParamString(name = PROP_NO_MATCHING_EVENT_NAME, description = "Event name triggered in case of no match")

@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@Slf4j
public class CheckRxItemsAreInvolvedInAllocation extends BaseRule {
    private static final String CLASS_NAME = CheckRxItemsAreInvolvedInAllocation.class.getSimpleName();

    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);

        Map<String,Object> eventAttributes = event.getAttributes();
        if(eventAttributes.containsKey(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)) {
            EventUtils.forwardInline(
                    context,
                    eventName
            );
        }else{
            context.addLog("attribute is not present");
            EventUtils.forwardInline(
                    context,
                    noMatchEventName
            );
        }

    }

}
