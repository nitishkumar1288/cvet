package com.fluentcommerce.rule.order.pv1.releaseitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.LocationData;
import com.fluentcommerce.dto.fulfillment.OrderItemData;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Yamini Kukreja
 */

@RuleInfo(
        name = "ReleaseRxAndNonRxItemsForFulfilment",
        description = "Create fulfilment for the items which are in the list of accepted line statuses {" + PROP_ACCEPTED_STATUSES + "} " +
                "and call event to create fulfilment {" + PROP_EVENT_NAME + "}",
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
        description = "Approval status for rx items"
)
@Slf4j
public class ReleaseRxAndNonRxItemsForFulfilment extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        List<String> acceptedStatuses = context.getPropList(PROP_ACCEPTED_STATUSES, String.class);
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        List<LocationData> locationDataList = new ArrayList<>();
        Map<String, String> locIdToRef = new HashMap<>();

        for(OrderItem orderItem: order.getItems()){
                if (acceptedStatuses.contains(orderItem.getAttributes().get(ORDER_LINE_STATUS).toString())) {
                    fetchLocationIdToRef(locIdToRef, orderItem);
                }
        }
        for(Map.Entry<String, String> location: locIdToRef.entrySet()){
            ArrayList<OrderItemData> orderItemDataList = new ArrayList<>();
            for(OrderItem orderItem: order.getItems()){
                OrderItemData orderItemData = new OrderItemData();
                if (StringUtils.equalsIgnoreCase(orderItem.getAttributes().get(FACILITY_ID).toString(), location.getKey()) &&
                        acceptedStatuses.contains(orderItem.getAttributes().get(ORDER_LINE_STATUS).toString())) {
                    orderItemData.setLineItemRef(orderItem.getRef());
                    orderItemData.setLineItemId(orderItem.getId());
                    orderItemDataList.add(orderItemData);
                }
            }
            if(CollectionUtils.isNotEmpty(orderItemDataList)){
                LocationData locationData = new LocationData();
                locationData.setLocationID(location.getKey());
                locationData.setLocationRef(location.getValue());
                locationData.setItems(orderItemDataList);
                locationDataList.add(locationData);
            }
        }

        if(!locationDataList.isEmpty()){
            Map<String, Object> attributes = new HashMap<>(context.getEvent().getAttributes());
            attributes.put(EVENT_LOCATION_DATA_LIST, locationDataList);
            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    attributes);
        }
    }

    private void fetchLocationIdToRef(Map<String, String> locIdToRef, OrderItem orderItem) {
        String facilityRef = orderItem.getAttributes().containsKey(DEFAULT_SELECTED_LOCATION) ?
                orderItem.getAttributes().get(DEFAULT_SELECTED_LOCATION).toString() :
                orderItem.getAttributes().get(SELECTED_LOCATION).toString();
        locIdToRef.put(orderItem.getAttributes().get(FACILITY_ID).toString(), facilityRef);
    }
}
