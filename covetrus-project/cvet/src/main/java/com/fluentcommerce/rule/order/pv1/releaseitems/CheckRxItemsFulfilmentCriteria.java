package com.fluentcommerce.rule.order.pv1.releaseitems;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.LocationData;
import com.fluentcommerce.dto.fulfillment.OrderItemData;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.location.LocationService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author Nandhakumar
 */

@RuleInfo(
        name = "CheckRxItemsFulfilmentCriteria",
        description = "Filter the Rx items based on {" + PROP_ACCEPTED_STATUSES + "} and {"
                + PROP_ATTRIBUTE_NAME + "} using {" + PROP_PV1_VERIFIED_ORDER_ITEM_STATUS + "} and {" + PROP_CANCELLED_ORDER_ITEM_STATUS + "} " +
                "and Send event {" + PROP_EVENT_NAME + "}",
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
        name = PROP_ACCEPTED_STATUSES,
        description = "Accepted line status for items"
)
@ParamString(
        name = PROP_ATTRIBUTE_NAME,
        description = "Item attribute name"
)
@ParamString(
        name = PROP_PV1_VERIFIED_ORDER_ITEM_STATUS,
        description = "pv1 verified order item status"
)
@ParamString(
        name = PROP_CANCELLED_ORDER_ITEM_STATUS,
        description = "cancelled order item status"
)

@EventAttribute(name = ITEMS)
@Slf4j
public class CheckRxItemsFulfilmentCriteria extends BaseRule {

    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        String attributeName = context.getProp(PROP_ATTRIBUTE_NAME);
        String cancelledOrderItemStatus = context.getProp(PROP_CANCELLED_ORDER_ITEM_STATUS);
        String pv1VerifiedOrderItemStatus = context.getProp(PROP_PV1_VERIFIED_ORDER_ITEM_STATUS);
        List<String> acceptedStatuses = context.getPropList(PROP_ACCEPTED_STATUSES, String.class);

        Map<String, Object> eventAttributes = context.getEvent().getAttributes();
        List<PV1Items> items = CommonUtils.convertObjectToDto(eventAttributes.get(ITEMS), new TypeReference<List<PV1Items>>() {
        });
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);

        // locationDataList will contain the list of items which are dependent on Rx location
        List<LocationData> locationDataList = new ArrayList<>();

        List<String> locationIdList = new ArrayList<>();
        if (!items.isEmpty()) {

            getLocationIDList(cancelledOrderItemStatus, pv1VerifiedOrderItemStatus, order, locationIdList);

            context.addLog("locationIdList " + locationIdList);

            List<String> uniqueLocationIdList = locationIdList.stream().distinct().collect(Collectors.toList());

            context.addLog("uniqueLocationIdList " + uniqueLocationIdList);


            for (String locationId : uniqueLocationIdList) {

                // order item data list is created for each location ID's
                ArrayList<OrderItemData> orderItemDataList = new ArrayList<>();

                getOrderItemDataList(context, attributeName, acceptedStatuses, order, locationId, orderItemDataList);

                // If the order item list is not empty add it to the location data
                if (CollectionUtils.isNotEmpty(orderItemDataList)) {
                    LocationData locationData = new LocationData();
                    locationData.setLocationID(locationId);
                    LocationService locationService = new LocationService(context);
                    locationData.setLocationRef(locationService.getLocationById(locationData.getLocationID()).ref());
                    locationData.setItems(orderItemDataList);
                    locationDataList.add(locationData);
                }
            }
        }

        Map<String, Object> attributes = new HashMap<>(context.getEvent().getAttributes());
        attributes.put(EVENT_LOCATION_DATA_LIST, locationDataList);
        EventUtils.forwardEventInlineWithAttributes(
                context,
                eventName,
                attributes);
    }

    private void getOrderItemDataList(ContextWrapper context, String attributeName, List<String> acceptedStatuses, Order order, String locationId, ArrayList<OrderItemData> orderItemDataList) {
        for (OrderItem orderItem : order.getItems()) {
            String orderItemStatus = orderItem.getAttributes().get(ORDER_LINE_STATUS).toString();
            // Apply all conditions for non cancelled items
            if (!orderItemStatus.equalsIgnoreCase(CANCELLED) && (orderItem.getAttributes().containsKey(FACILITY_ID)
                    && StringUtils.equalsIgnoreCase(orderItem.getAttributes().get(FACILITY_ID).toString(), locationId))) {
                if (orderItem.getAttributes().containsKey(attributeName)
                        && StringUtils.equalsIgnoreCase(orderItem.getAttributes().get(attributeName).toString(), TRUE)) {
                    context.addLog("Skipping the processed item , item id " + orderItem.getId());
                } else if (acceptedStatuses.contains(orderItemStatus)) {
                    OrderItemData orderItemData = new OrderItemData();
                    orderItemData.setLineItemRef(orderItem.getRef());
                    orderItemData.setLineItemId(orderItem.getId());
                    orderItemDataList.add(orderItemData);
                } else {
                    orderItemDataList.clear();
                    break;
                }

            }
        }
    }

    private void getLocationIDList(String cancelledOrderItemStatus, String pv1VerifiedOrderItemStatus, Order order, List<String> locationIdList) {
        for (OrderItem orderItem : order.getItems()) {
            String orderItemStatus = orderItem.getAttributes().get(ORDER_LINE_STATUS).toString();

            // Add the location of Rx items which are not cancelled and in pv1 verified status
            if (!orderItemStatus.equalsIgnoreCase(cancelledOrderItemStatus) &&
                    orderItemStatus.equalsIgnoreCase(pv1VerifiedOrderItemStatus)) {
                locationIdList.add(orderItem.getAttributes().get(FACILITY_ID).toString());
            }
        }
    }
}
