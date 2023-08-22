package com.fluentcommerce.rule.order.pv1.releaseitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.LocationData;
import com.fluentcommerce.dto.fulfillment.OrderItemData;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
@author Nandhakumar
*/

@RuleInfo(
        name = "HandleProcessedPv1Items",
        description = "Handle processed PV1 items and Send event {" + PROP_EVENT_NAME + "}",
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
        description = "Outgoing event name to be created and sent if the attribute value matches"
)
@Slf4j
public class HandleProcessedPv1Items extends BaseRule {

    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);

        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData =
        orderService.getOrderWithOrderItems(context.getEvent().getEntityId());

        List<LocationData> locationDataList = new ArrayList<>();
        for(GetOrderByIdQuery.OrderItemEdge orderItem : orderData.orderItems().orderItemEdge()){
            Optional<GetOrderByIdQuery.OrderItemAttribute> optionalPV1Attribute =
            orderItem.orderItemNode().orderItemAttributes().stream().filter(
                    orderItemAttribute -> (orderItemAttribute.name().equalsIgnoreCase(IS_PV1_VERIFIED_AND_REQUESTED) &&
                            orderItemAttribute.value().toString().equalsIgnoreCase(TRUE))
            ).findFirst();

            if(optionalPV1Attribute.isPresent()){
                Optional<GetOrderByIdQuery.OrderItemAttribute> optionalFacilityIdAttribute =
                        orderItem.orderItemNode().orderItemAttributes().stream().filter(
                                orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(FACILITY_ID)
                        ).findFirst();

                constructLocationDataList(
                        locationDataList,
                        orderItem,
                        optionalFacilityIdAttribute);
            }
        }

        if(CollectionUtils.isNotEmpty(locationDataList)){
            Map<String,Object> eventAttributes = new HashMap<>();
            eventAttributes.putAll(context.getEvent().getAttributes());
            eventAttributes.put(EVENT_LOCATION_DATA_LIST,locationDataList);

            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    eventAttributes
            );
        }

    }

    private static void constructLocationDataList(
            List<LocationData> locationDataList,
            GetOrderByIdQuery.OrderItemEdge orderItem,
            Optional<GetOrderByIdQuery.OrderItemAttribute> optionalFacilityIdAttribute) {

        if(optionalFacilityIdAttribute.isPresent()){

            Optional<LocationData> optionalLocationData =
            locationDataList.stream().filter(
                    locationData -> locationData.getLocationID().equalsIgnoreCase(optionalFacilityIdAttribute.get().value().toString())
            ).findFirst();

            if(optionalLocationData.isPresent()){

                optionalLocationData.get().getItems().add(constructItemData(orderItem));

            }else{
                LocationData locationData = new LocationData();
                locationData.setLocationID(optionalFacilityIdAttribute.get().value().toString());

                Optional<GetOrderByIdQuery.OrderItemAttribute> optionalSelectedLocationAttribute =
                        orderItem.orderItemNode().orderItemAttributes().stream().filter(
                                orderItemAttribute -> ( orderItemAttribute.name().equalsIgnoreCase(SELECTED_LOCATION) ||
                                        orderItemAttribute.name().equalsIgnoreCase(DEFAULT_SELECTED_LOCATION) )
                        ).findFirst();

                if(optionalSelectedLocationAttribute.isPresent()){
                    locationData.setLocationRef(optionalSelectedLocationAttribute.get().value().toString());
                }

                ArrayList<OrderItemData> orderItemDataList = new ArrayList<>();
                orderItemDataList.add(constructItemData(orderItem));

                // append the order items and location
                locationData.setItems(orderItemDataList);
                locationDataList.add(locationData);
            }
        }
    }

    private static OrderItemData constructItemData(
            GetOrderByIdQuery.OrderItemEdge orderItem) {

        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setLineItemId(orderItem.orderItemNode().id());
        orderItemData.setLineItemRef(orderItem.orderItemNode().ref());

        return orderItemData;

    }
}
