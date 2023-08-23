package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.FulfilmentExceptionData;
import com.fluentcommerce.dto.fulfillment.FulfilmentItems;
import com.fluentcommerce.graphql.mutation.fulfillment.UpdateFulfilmentMutation;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateFulfilmentInput;
import com.fluentcommerce.graphql.type.UpdateFulfilmentItemWithFulfilmentInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
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
        name = "UpdateAttributeBasedOnExceptionResolutionResponse",
        description = "Fetch the fulfilment exception resolution response and update the fulfilment status and attribute with respect to " +
                "response and call {" + PROP_EVENT_NAME_FOR_RESOLUTION_WHILE_PICK_PACK + "} when resolution response is at PICK_PACK exception, " +
                "and call {" + PROP_EVENT_NAME_FOR_RESOLUTION_WHILE_FULFILMENT_CREATION + "} when resolution response is at fulfilment " +
                "creation exception." + "Update filled qty when line status response is {" + PROP_FILLED_STATUS + "} or rejected quantity when " +
                "line status response is {" + PROP_REJECTED_STATUS + "}. when all items are cancelled call event if exception raised from fulfilment creation {" + PROP_EVENT_NAME_FOR_REJECTED_FULFILMENT
                + "} else call {"+ PROP_EVENT_NAME_FOR_REJECTED_FULFILMENT_AFTER_PICK_READY +"} if exception raised after pick ready status",
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
        name = PROP_EVENT_NAME_FOR_RESOLUTION_WHILE_PICK_PACK,
        description = "call event if resolution response wile pick pack"
)
@ParamString(
        name = PROP_EVENT_NAME_FOR_RESOLUTION_WHILE_FULFILMENT_CREATION,
        description = "call event if resolution response while fulfilment creation"
)
@ParamString(
        name = PROP_EVENT_NAME_FOR_REJECTED_FULFILMENT,
        description = "call event if resolution response while fulfilment creation"
)
@ParamString(
        name = PROP_FILLED_STATUS,
        description = "item fulfilled status while resolution"
)
@ParamString(
        name = PROP_REJECTED_STATUS,
        description = "item rejected status while resolution"
)
@ParamString(
        name = PROP_EVENT_NAME_FOR_REJECTED_FULFILMENT_AFTER_PICK_READY,
        description = "call event if resolution response while fulfilment creation"
)
@Slf4j
public class UpdateAttributeBasedOnExceptionResolutionResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME_FOR_RESOLUTION_WHILE_PICK_PACK,
                PROP_EVENT_NAME_FOR_RESOLUTION_WHILE_PICK_PACK, PROP_FILLED_STATUS, PROP_REJECTED_STATUS);
        String eventNameForResolutionWhilePickPack = context.getProp(PROP_EVENT_NAME_FOR_RESOLUTION_WHILE_PICK_PACK);
        String eventNameForResolutionWhileFulfilmentCreation = context.getProp(PROP_EVENT_NAME_FOR_RESOLUTION_WHILE_FULFILMENT_CREATION);
        String eventNameForRejectedFulfilment = context.getProp(PROP_EVENT_NAME_FOR_REJECTED_FULFILMENT);
        String eventNameForRejectedFulfilmentAfterPickReady = context.getProp(PROP_EVENT_NAME_FOR_REJECTED_FULFILMENT_AFTER_PICK_READY);
        String filledStatus = context.getProp(PROP_FILLED_STATUS);
        String rejectedStatus = context.getProp(PROP_REJECTED_STATUS);
        Map<String, Object> eventAttributes = context.getEvent().getAttributes();
        FulfilmentExceptionData fulfilmentExceptionResolutionResponse = CommonUtils.convertObjectToDto(eventAttributes.get(FULFILMENT),
                new TypeReference<FulfilmentExceptionData>() {});
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(event.getEntityId());
        Optional<GetFulfilmentByIdQuery.Attribute1> optionalExceptionAttr = fulfillmentById.attributes().stream().filter(a -> StringUtils.equalsIgnoreCase(a.name(), IS_EXCEPTION))
                .findFirst();

        if(optionalExceptionAttr.isPresent()) {
            String isExceptionAttr = optionalExceptionAttr.get().value().toString();
            int count = 0;
            List<UpdateFulfilmentItemWithFulfilmentInput> fulfilmentItems = new ArrayList<>();
            for (FulfilmentItems fulfilmentItem : fulfilmentExceptionResolutionResponse.getItems()) {
                Optional<GetFulfilmentByIdQuery.Edge> itemData = fulfillmentById.items().edges().stream().filter(edge ->
                        edge.item().ref().equalsIgnoreCase(fulfilmentItem.getRef())).findFirst();
                if (itemData.isPresent()) {
                    if (StringUtils.equalsIgnoreCase(fulfilmentItem.getStatus(), filledStatus)) {
                        count++;
                    } else if (StringUtils.equalsIgnoreCase(fulfilmentItem.getStatus(), rejectedStatus)) {
                        fulfilmentItems.add(UpdateFulfilmentItemWithFulfilmentInput.builder()
                                .id(itemData.get().item().id())
                                .rejectedQuantity(itemData.get().item().requestedQuantity())
                                .status(DELETED_STATUS)
                                .build());
                    }
                }
            }

            List<AttributeInput> fulfillmentAttributes = new ArrayList<>();
            fulfillmentAttributes.add(AttributeInput.builder()
                    .name(IS_EXCEPTION)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(NONE)
                    .build());
            fulfillmentAttributes.add(AttributeInput.builder()
                    .name(EXCEPTION_RESOLUTION_TYPE)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(fulfilmentExceptionResolutionResponse.getExceptionResolutionType())
                    .build());

            UpdateFulfilmentInput updateFulfilmentInput = UpdateFulfilmentInput.builder().id(event.getEntityId())
                    .items(fulfilmentItems)
                    .attributes(fulfillmentAttributes).build();
            UpdateFulfilmentMutation updateFulfillmentMutation = UpdateFulfilmentMutation.builder().input(updateFulfilmentInput).build();
            context.action().mutation(updateFulfillmentMutation);

            HashMap<String, Object> attributeMap = new HashMap<>(context.getEvent().getAttributes());

            if (count > 0) {
                forwardToResolutionEvent(
                        context,
                        eventNameForResolutionWhilePickPack,
                        eventNameForResolutionWhileFulfilmentCreation,
                        isExceptionAttr,
                        attributeMap);
            } else {
                forwardToRejectedEvent(
                        context,
                        eventNameForRejectedFulfilment,
                        eventNameForRejectedFulfilmentAfterPickReady,
                        isExceptionAttr,
                        attributeMap);
            }
        }
    }

    private static void forwardToResolutionEvent(
            ContextWrapper context,
            String eventNameForResolutionWhilePickPack,
            String eventNameForResolutionWhileFulfilmentCreation,
            String isExceptionAttr,
            HashMap<String, Object> attributeMap) {
        if (StringUtils.equalsIgnoreCase(isExceptionAttr, PICK_PACK)) {
            EventUtils.forwardEventInlineWithAttributes(context, eventNameForResolutionWhilePickPack, attributeMap);
        } else if (StringUtils.equalsIgnoreCase(isExceptionAttr, FULFILMENT_CREATION)) {
            EventUtils.forwardEventInlineWithAttributes(context, eventNameForResolutionWhileFulfilmentCreation, attributeMap);
        }
    }

    private static void forwardToRejectedEvent(
            ContextWrapper context,
            String eventNameForRejectedFulfilment,
            String eventNameForRejectedFulfilmentAfterPickReady,
            String isExceptionAttr,
            HashMap<String, Object> attributeMap) {
        if (StringUtils.equalsIgnoreCase(isExceptionAttr, PICK_PACK)) {
            EventUtils.forwardEventInlineWithAttributes(context, eventNameForRejectedFulfilmentAfterPickReady, attributeMap);
        } else if (StringUtils.equalsIgnoreCase(isExceptionAttr, FULFILMENT_CREATION)) {
            EventUtils.forwardEventInlineWithAttributes(context, eventNameForRejectedFulfilment, attributeMap);
        }
    }
}
