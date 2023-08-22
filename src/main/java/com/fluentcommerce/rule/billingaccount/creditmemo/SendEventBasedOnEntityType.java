package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendEventBasedOnEntityType",
        description = "check for the entity type  and send the event {" + PROP_EVENT_NAME + "} if match the condition " +
                "else send the event {" + PROP_NO_MATCHING_EVENT_NAME + "} ",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_CREDIT_MEMO)
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "forward to next no match Event name"
)
@Slf4j
public class SendEventBasedOnEntityType extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,PROP_NO_MATCHING_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);
        Event event = context.getEvent();
        String entitySubtypeType = event.getEntitySubtype();
        if (null != entitySubtypeType && entitySubtypeType.equalsIgnoreCase(RETURN)){
            EventUtils.forwardInline(context,eventName);
        }else if (null != entitySubtypeType && entitySubtypeType.equalsIgnoreCase(CREDIT_MEMO)){
            EventUtils.forwardInline(context,noMatchEventName);
        }else {
            context.addLog("SendEventBasedOnEntityType::Entity subType is missing or not relevant for the " +
                    "creditMemoRef::"+event.getEntityRef());
        }
    }
}
