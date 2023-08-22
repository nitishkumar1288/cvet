package com.fluentcommerce.rule.order.common;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import static com.fluentcommerce.util.Constants.*;
import static java.lang.String.format;

@RuleInfo(
        name = "CreateExceptionMessage",
        description = "Receive exception message  {" + EXCEPTION_MESSAGE + "} and create the exception message",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER),
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)

@ParamString(
        name = EXCEPTION_MESSAGE,
        description = "exception message"
)
@Slf4j
public class CreateExceptionMessage extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, EXCEPTION_MESSAGE);
        String eventId = context.getEvent().getEntityId();
        String message = context.getProp(EXCEPTION_MESSAGE);
        if (null != message){
            throw new IllegalArgumentException(format(message, eventId));
        }
    }
}