package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.Line;
import com.fluentcommerce.dto.fulfillment.ShipOrderResponse;
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
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateAttributeBasedOnShipConfirmationResponse",
        description = "fetch the ship confirmation response,Update the fulfilment attribute with respect to " +
                "response and call {" + PROP_EVENT_NAME + "}",
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

@EventAttribute(name = SHIP_CONFIRMATION)
@Slf4j
public class UpdateAttributeBasedOnShipConfirmationResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        ShipOrderResponse shipOrderResponse = CommonUtils.convertObjectToDto(eventAttributes.get(SHIP_CONFIRMATION),
                new TypeReference<ShipOrderResponse>(){});
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(shipOrderResponse.getFulfilmentId());
        if (Objects.nonNull(fulfillmentById)){
            List<UpdateFulfilmentItemWithFulfilmentInput> fulfilmentItems = new ArrayList<>();
            ArrayList<Line> confirmedLineItemRefList = new ArrayList<>();
            ArrayList<Line> cancelledLineItemRefList = new ArrayList<>();
            HashMap<String,Object> attributeMap = new HashMap<>();
            String locationRef = fulfillmentById.fromAddress().ref();
            for(Line lineData:shipOrderResponse.getLines()){
                Optional<GetFulfilmentByIdQuery.Edge> itemData = fulfillmentById.items().edges().stream().filter(edge ->
                        edge.item().ref().equalsIgnoreCase(lineData.getRef())).findFirst();
                if (itemData.isPresent()){
                    if (lineData.getFilledQuantity()>0 && itemData.get().item().requestedQuantity() == lineData.getFilledQuantity()){
                        Line line = new Line();
                        line.setRef(lineData.getRef());
                        line.setRequestedQuantity(itemData.get().item().requestedQuantity());
                        line.setLocationRef(locationRef);

                        fulfilmentItems.add(UpdateFulfilmentItemWithFulfilmentInput.builder()
                                .id(itemData.get().item().id())
                                .filledQuantity(lineData.getFilledQuantity())
                                .build());
                        confirmedLineItemRefList.add(line);
                    }else if (lineData.getFilledQuantity()==0){
                        Line line = new Line();
                        line.setRef(lineData.getRef());
                        line.setRequestedQuantity(itemData.get().item().requestedQuantity());
                        line.setLocationRef(locationRef);
                        fulfilmentItems.add(UpdateFulfilmentItemWithFulfilmentInput.builder()
                                .id(itemData.get().item().id())
                                .filledQuantity(lineData.getFilledQuantity())
                                .rejectedQuantity(lineData.getRejectedQuantity())
                                .status(DELETED)
                                .build());
                        cancelledLineItemRefList.add(line);
                    }
                }
            }
            context.addLog("shipConfirmedItemRefList::"+confirmedLineItemRefList);
            context.addLog("shipCancelledItemRefList::"+cancelledLineItemRefList);
            attributeMap.put(SHIP_CONFIRMED_ITEM_REF_LIST,confirmedLineItemRefList);
            attributeMap.put(SHIP_CANCELLED_ITEM_REF_LIST,cancelledLineItemRefList);
            updateFulfillmentData(fulfilmentItems,shipOrderResponse,context,fulfillmentById);
            attributeMap.putAll(context.getEvent().getAttributes());
            EventUtils.forwardEventInlineWithAttributes(context, eventName, attributeMap);
        }
    }

    private void updateFulfillmentData(List<UpdateFulfilmentItemWithFulfilmentInput> fulfilmentItems,
                                                         ShipOrderResponse shipOrderResponse, ContextWrapper context,
                                            GetFulfilmentByIdQuery.FulfilmentById fulfillmentById) {
        List<AttributeInput> fulfillmentAttributes = createFulfillmentAttributeData(shipOrderResponse);
        UpdateFulfilmentInput updateFulfilmentInput = UpdateFulfilmentInput.builder()
                .id(fulfillmentById.id())
                .items(fulfilmentItems)
                .attributes(fulfillmentAttributes)
                .build();
        UpdateFulfilmentMutation updateFulfillmentMutation = UpdateFulfilmentMutation.builder().input(updateFulfilmentInput).build();
        context.action().mutation(updateFulfillmentMutation);
    }

    private List<AttributeInput> createFulfillmentAttributeData(ShipOrderResponse shipOrderResponse) {
        List<AttributeInput> fulfillmentAttributes = new ArrayList<>();
        if (null != shipOrderResponse.getCarrierCode()){
            fulfillmentAttributes.add(AttributeInput.builder()
                    .name(CARRIER_CODE)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(shipOrderResponse.getCarrierCode())
                    .build());
        }
        if (null != shipOrderResponse.getTrackingNumber()){
            fulfillmentAttributes.add(AttributeInput.builder()
                    .name(TRACKING_NUMBER)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(shipOrderResponse.getTrackingNumber())
                    .build());
        }
        if (null != shipOrderResponse.getShippingMethod()){
            fulfillmentAttributes.add(AttributeInput.builder()
                    .name(SHIPPING_METHOD)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(shipOrderResponse.getShippingMethod())
                    .build());
        }
        if (null != shipOrderResponse.getTrackingURL()){
            fulfillmentAttributes.add(AttributeInput.builder()
                    .name(TRACKING_URL)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(shipOrderResponse.getTrackingURL())
                    .build());
        }
        if (null != shipOrderResponse.getShippedDate()){
            fulfillmentAttributes.add(AttributeInput.builder()
                    .name(SHIPPED_DATE)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(shipOrderResponse.getShippedDate())
                    .build());
        }
        return fulfillmentAttributes;
    }
}
