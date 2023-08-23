package com.fluentcommerce.rule.fulfilment;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import static com.fluentcommerce.util.Constants.*;
import static com.fluentcommerce.util.EventUtils.forwardInline;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendEventOnVerifyingFulfillmentAttribute",
        description = "fetch the fulfillment assigned status  {" + PROP_ASSIGNED_STATUS + "} and exception status  " +
                "{" + PROP_EXCEPTION_STATUS + "} and call match event {" + PROP_EVENT_NAME + "} OR no matching event {" + PROP_NO_MATCHING_EVENT_NAME + "}" +
                "based on the status",
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
        description = "forward to next Event name"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "forward to next no match Event name"
)
@ParamString(
        name = PROP_ASSIGNED_STATUS,
        description = "assigned status name"
)
@ParamString(
        name = PROP_EXCEPTION_STATUS,
        description = "exception status name"
)

@Slf4j
public class SendEventOnVerifyingFulfillmentAttribute extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,PROP_NO_MATCHING_EVENT_NAME,PROP_ASSIGNED_STATUS,PROP_EXCEPTION_STATUS);
        Event event = context.getEvent();
        String matchingEvent = context.getProp(PROP_EVENT_NAME);
        String noMatchingEvent = context.getProp(PROP_NO_MATCHING_EVENT_NAME);
        String assignedStatus = context.getProp(PROP_ASSIGNED_STATUS);
        String exceptionStatus = context.getProp(PROP_EXCEPTION_STATUS);
        String fulfillmentId = event.getEntityId();
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfillmentId);
        for (GetFulfilmentByIdQuery.Attribute1 attribute :fulfillmentById.attributes()){
            if(attribute.name().equalsIgnoreCase(INITIAL_FULFILMENT_STATUS)){
                if(attribute.value().toString().equalsIgnoreCase(assignedStatus)){
                    forwardInline(context, matchingEvent);
                }else if(attribute.value().toString().equalsIgnoreCase(exceptionStatus)){
                    forwardInline(context, noMatchingEvent);
                }
                break;
            }
        }
    }
}
