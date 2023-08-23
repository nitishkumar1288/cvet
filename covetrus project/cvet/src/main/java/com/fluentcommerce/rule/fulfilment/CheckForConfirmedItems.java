package com.fluentcommerce.rule.fulfilment;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.Line;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */
@RuleInfo(
        name = "CheckForConfirmedItems",
        description = "fetch the confirmed item list from the attribute and if available call to next event " +
                "{" + PROP_EVENT_NAME + "}",
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
        description = "event name"
)
@EventAttribute(name = SHIP_CONFIRMED_ITEM_REF_LIST)
@Slf4j
public class CheckForConfirmedItems extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        List<Line> confirmedItemRefList = context.getEvent().getAttributeList(SHIP_CONFIRMED_ITEM_REF_LIST, Line.class);
        if (null != confirmedItemRefList && !confirmedItemRefList.isEmpty()){
            EventUtils.forwardEventInlineWithAttributes(context, eventName, context.getEvent().getAttributes());
        }
    }
}
