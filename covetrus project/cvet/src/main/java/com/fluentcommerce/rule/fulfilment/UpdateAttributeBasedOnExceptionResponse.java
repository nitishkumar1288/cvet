package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.FulfilmentExceptionData;
import com.fluentcommerce.graphql.mutation.fulfillment.UpdateFulfilmentMutation;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateFulfilmentInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import static com.fluentcommerce.util.Constants.*;

/**
 * @author Yamini Kukreja
 */

@RuleInfo(
        name = "UpdateAttributeBasedOnExceptionResponse",
        description = "Fetch the exception response and update the fulfilment status and attribute with respect to " +
                "response and call {" + PROP_EVENT_NAME + "} when status response is exception with status {" +
                PROP_EXCEPTION_STATUS_RESPONSE + "}.",
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
        description = "call event if status response is exception"
)
@ParamString(
        name = PROP_EXCEPTION_STATUS_RESPONSE,
        description = "item fulfilled status while resolution"
)
@Slf4j
public class UpdateAttributeBasedOnExceptionResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME, PROP_EXCEPTION_STATUS_RESPONSE);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String exceptionStatusResponse = context.getProp(PROP_EXCEPTION_STATUS_RESPONSE);
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        FulfilmentExceptionData fulfilmentExceptionResponse = CommonUtils.convertObjectToDto(eventAttributes.get(FULFILMENT),
                new TypeReference<FulfilmentExceptionData>(){});
        HashMap<String, Object> attributeMap = new HashMap<>(context.getEvent().getAttributes());

        if(StringUtils.equalsIgnoreCase(fulfilmentExceptionResponse.getStatus(), exceptionStatusResponse)) {
            List<AttributeInput> fulfillmentAttributes = new ArrayList<>();
            fulfillmentAttributes.add(AttributeInput.builder()
                    .name(EXCEPTION_REASON)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(fulfilmentExceptionResponse.getExceptionReason())
                    .build());
            fulfillmentAttributes.add(AttributeInput.builder()
                    .name(IS_EXCEPTION)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(PICK_PACK)
                    .build());
            UpdateFulfilmentInput updateFulfilmentInput = UpdateFulfilmentInput.builder().id(event.getEntityId())
                    .attributes(fulfillmentAttributes).build();
            UpdateFulfilmentMutation updateFulfillmentMutation = UpdateFulfilmentMutation.builder().input(updateFulfilmentInput).build();
            context.action().mutation(updateFulfillmentMutation);
            EventUtils.forwardEventInlineWithAttributes(context, eventName, attributeMap);
        }
    }
}