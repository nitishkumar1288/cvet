package com.fluentcommerce.rule.order.pv1.releaseitems;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.location.LocationData;
import com.fluentcommerce.dto.orderitem.OrderItemData;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
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
        name = "CheckNonRxItemsFulfilmentCriteria",
        description = "Filter the products based on the approval type {" + PROP_APPROVAL_TYPE_LIST + "} " +
                "and check whether non rx items are eligible for fulfilment and call event {" + PROP_EVENT_NAME + "}",
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
@ParamString(
        name = PROP_APPROVAL_TYPE_LIST,
        description = "List of approval type values for rx items"
)

@EventAttribute(name = EVENT_LOCATION_DATA_LIST)
@Slf4j
public class CheckNonRxItemsFulfilmentCriteria extends BaseRule {

    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String eventName = context.getProp(PROP_EVENT_NAME);
        List<String> approvalTypeList = context.getPropList(PROP_APPROVAL_TYPE_LIST, String.class);
        List<String> matchingStatusList = context.getPropList(PROP_MATCHING_STATUS_LIST, String.class);
        List<String> ignoreRxItemsOnStatusList = context.getPropList(IGNORE_RX_ITEMS_ON_STATUS_LIST, String.class);
        List<LocationData> locationDataList = CommonUtils.convertObjectToDto(event.getAttributes().get(EVENT_LOCATION_DATA_LIST), new TypeReference<List<LocationData>>() {
        });

        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(orderId);

        List<String> rxLineItemIdList = new ArrayList<>();
        List<String> rxLineItemFacilityIdList = new ArrayList<>();

        // collect all the rx line item id and facility id list
        for (GetOrderByIdQuery.OrderItemEdge orderItem : order.orderItems().orderItemEdge()) {
            Optional<Object> approvalTypeValue =
                    orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                            variantProductAttribute -> variantProductAttribute.name().equalsIgnoreCase(APPROVAL_TYPE)
                    ).map(GetOrderByIdQuery.VariantProductAttribute::value).findFirst();

            boolean rxItem = isRxItem(
                    approvalTypeList,
                    false,
                    approvalTypeValue);

            updateRxItemRelatedInformation(
                    ignoreRxItemsOnStatusList,
                    rxLineItemIdList,
                    rxLineItemFacilityIdList,
                    orderItem,
                    rxItem);
        }

        // if the location data list is empty create a new list
        if (CollectionUtils.isEmpty(locationDataList)) {
            locationDataList = new ArrayList<>();
        }

        for (GetOrderByIdQuery.OrderItemEdge orderItem : order.orderItems().orderItemEdge()) {
            // check whether the non rx line items facility id belongs to the facility id of rx line items
            if (!rxLineItemIdList.contains(orderItem.orderItemNode().id())) {

                Optional<Object> optionalLineItemStatus =
                        orderItem.orderItemNode().orderItemAttributes().stream().filter(
                                        itemAttr -> itemAttr.name().equalsIgnoreCase(ORDER_LINE_STATUS))
                                .map(GetOrderByIdQuery.OrderItemAttribute::value)
                                .findFirst();

                Optional<Object> optionalFacilityId =
                        orderItem.orderItemNode().orderItemAttributes().stream().filter(
                                orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(FACILITY_ID)
                        ).map(GetOrderByIdQuery.OrderItemAttribute::value).findFirst();


                // Order line item , facility id  should not be a rx item facility id , and it should match the status list
                if (optionalFacilityId.isPresent() && optionalLineItemStatus.isPresent()
                        && !rxLineItemFacilityIdList.contains(optionalFacilityId.get().toString())
                        && (matchingStatusList.contains(optionalLineItemStatus.get().toString()))) {
                    Optional<LocationData> optionalLocationData =
                            locationDataList.stream().filter(
                                    locationData -> locationData.getLocationID().equalsIgnoreCase(optionalFacilityId.get().toString())
                            ).findFirst();

                    addItemInfoToLocationDataList(
                            locationDataList,
                            orderItem,
                            optionalFacilityId.get(),
                            optionalLocationData);

                }
            }
        }

        // if the locationDataList is present call the next event
        if (CollectionUtils.isNotEmpty(locationDataList)) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.putAll(context.getEvent().getAttributes());
            attributes.put(EVENT_LOCATION_DATA_LIST, locationDataList);
            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    attributes);
        }
    }


    private static boolean isRxItem(
            List<String> approvalTypeList,
            boolean rxItem,
            Optional<Object> approvalTypeValue) {
        if (approvalTypeValue.isPresent() && approvalTypeList.contains(approvalTypeValue.get().toString().toUpperCase())) {
            rxItem = true;
        }
        return rxItem;
    }

    private static void updateRxItemRelatedInformation(
            List<String> ignoreRxItemsOnStatusList,
            List<String> rxLineItemIdList,
            List<String> rxLineItemFacilityIdList,
            GetOrderByIdQuery.OrderItemEdge orderItem,
            boolean rxItem) {
        if (rxItem) {
            rxLineItemIdList.add(orderItem.orderItemNode().id());

            // facility id has to be added if the rx item is not in cancelled status
            Optional<GetOrderByIdQuery.OrderItemAttribute> orderLineItemStatus = orderItem.orderItemNode().orderItemAttributes().stream().filter(
                    itemAttr -> itemAttr.name().equalsIgnoreCase(ORDER_LINE_STATUS) && (!ignoreRxItemsOnStatusList.contains(itemAttr.value().toString()))
            ).findFirst();

            if (orderLineItemStatus.isPresent()) {
                Optional<Object> optionalFacilityId =
                        orderItem.orderItemNode().orderItemAttributes().stream().filter(
                                orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(FACILITY_ID)
                        ).map(GetOrderByIdQuery.OrderItemAttribute::value).findFirst();
                if (optionalFacilityId.isPresent()) {
                    rxLineItemFacilityIdList.add(optionalFacilityId.get().toString());
                }
            }
        }
    }

    private void addItemInfoToLocationDataList(
            List<LocationData> locationDataList,
            GetOrderByIdQuery.OrderItemEdge orderItem,
            Object optionalFacilityId,
            Optional<LocationData> optionalLocationData) {

        if (optionalLocationData.isPresent()) {

            OrderItemData orderItemData = createOrderItemData(orderItem);
            optionalLocationData.get().getItems().add(orderItemData);

        } else {
            LocationData locationData = new LocationData();

            Optional<Object> optionalFacilityRef =
                    orderItem.orderItemNode().orderItemAttributes().stream().filter(
                            orderItemAttribute -> (orderItemAttribute.name().equalsIgnoreCase(SELECTED_LOCATION) ||
                                    orderItemAttribute.name().equalsIgnoreCase(DEFAULT_SELECTED_LOCATION))
                    ).map(GetOrderByIdQuery.OrderItemAttribute::value).findFirst();
            if (optionalFacilityRef.isPresent()) {
                locationData.setLocationRef(optionalFacilityRef.get().toString());
            }
            locationData.setLocationID(optionalFacilityId.toString());

            ArrayList<OrderItemData> orderItemDataList = new ArrayList<>();
            OrderItemData orderItemData = createOrderItemData(orderItem);
            orderItemDataList.add(orderItemData);

            // add the list in the location data
            locationData.setItems(orderItemDataList);

            //add the location data to the list
            locationDataList.add(locationData);
        }
    }

    private OrderItemData createOrderItemData(GetOrderByIdQuery.OrderItemEdge orderItem) {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setLineItemRef(orderItem.orderItemNode().ref());
        orderItemData.setLineItemId(orderItem.orderItemNode().id());
        return orderItemData;
    }
}
