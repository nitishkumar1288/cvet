package com.fluentcommerce.rule.fulfilment;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendEventOnVerifyingAllFulfilmentItemStatus",
        description = "check the fulfilment line with status {" + FULFILMENT_LINE_STATUS + "}. If all fulfilment items " +
                "match with this status call event {" + PROP_EVENT_NAME + "} else call event {" + PROP_NO_MATCHING_EVENT_NAME + "}",
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
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "event name"
)
@ParamString(
        name = FULFILMENT_LINE_STATUS,
        description = "fulfilment line status"
)

@Slf4j
public class SendEventOnVerifyingAllFulfilmentItemStatus extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME, PROP_NO_MATCHING_EVENT_NAME,
                FULFILMENT_LINE_STATUS);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEVentName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);
        String fulfilmentItemStatus = context.getProp(FULFILMENT_LINE_STATUS);
        Event event = context.getEvent();
        String fulfillmentId = event.getEntityId();
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfillmentId);
        if (Objects.nonNull(fulfillmentById) && Objects.nonNull(fulfillmentById.items())){
            boolean fulfilmentItemStatusMismatch = checkFulfilmentItemStatus(fulfillmentById,fulfilmentItemStatus);
            if (fulfilmentItemStatusMismatch) {
                EventUtils.forwardInline(context, noMatchEVentName);
            } else {
                EventUtils.forwardInline(context, eventName);
            }
        }
    }

    private boolean checkFulfilmentItemStatus(GetFulfilmentByIdQuery.FulfilmentById fulfillmentById,String fulfilmentItemStatus) {
        boolean fulfilmentItemStatusMismatch = false;
        for (GetFulfilmentByIdQuery.Edge edge:fulfillmentById.items().edges()){
            if(!edge.item().status().equalsIgnoreCase(fulfilmentItemStatus)){
                fulfilmentItemStatusMismatch = true;
                break;
            }
        }
        return fulfilmentItemStatusMismatch;
    }
}
