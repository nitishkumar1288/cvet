package com.fluentcommerce.rule.order.pv1.verified;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.location.LocationService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
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
        name = "UpdateOrderItemBasedOnPV1VerifiedResponse",
        description = "Update the order items status with respect to pv1 approval status response {" +
                PROP_APPROVAL_STATUS_RESPONSE + "} followed by line status. Send event {" + PROP_EVENT_NAME + "}.",
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
        description = "forward to next Event name"
)
@ParamString(
        name = PROP_APPROVAL_STATUS_RESPONSE,
        description = "approval status response from pv1:approval line status"
)
@Slf4j
public class UpdateOrderItemBasedOnPV1VerifiedResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String eventName = context.getProp(PROP_EVENT_NAME);
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        List<PV1Items> items = event.getAttributeList(ITEMS, PV1Items.class);
        String[] approvalStatusResponse = context.getProp(PROP_APPROVAL_STATUS_RESPONSE).split(COLON);

        if(!items.isEmpty()){
            List<UpdateOrderItemWithOrderInput> updateInputOrderItems = new ArrayList<>();
            List<Map<String, String>> reserve = new ArrayList<>();
            List<Map<String, String>> unreserve = new ArrayList<>();
            for(PV1Items item: items){
                getOrderItemForUpdate(context, order, approvalStatusResponse, updateInputOrderItems, reserve, unreserve, item);
            }
            orderService.updateOrderItems(updateInputOrderItems, orderId);
            if(!unreserve.isEmpty() || !reserve.isEmpty()){
                Map<String, Object> attributes = new HashMap<>(event.getAttributes());
                attributes.put(RESERVE, reserve);
                attributes.put(UNRESERVE, unreserve);
                EventUtils.forwardEventInlineWithAttributes(
                        context,
                        eventName,
                        attributes);

            }
        }
    }

    private void getOrderItemForUpdate(ContextWrapper context, Order order, String[] approvalStatusResponse, List<UpdateOrderItemWithOrderInput> updateInputOrderItems, List<Map<String, String>> reserve, List<Map<String, String>> unreserve, PV1Items item) {
        order.getItems().stream().forEach(orderItem -> {
            if(StringUtils.equalsIgnoreCase(orderItem.getRef(), item.getRef()) &&
                    StringUtils.equalsIgnoreCase(item.getStatus(), approvalStatusResponse[0])) {
                        List<AttributeInput> attributes = new ArrayList<>();
                        attributes.add(getOrderItemAttribute(OLD_LINE_STATUS, orderItem.getAttributes().get(ORDER_LINE_STATUS).toString()));
                        attributes.add(getOrderItemAttribute(ORDER_LINE_STATUS, approvalStatusResponse[1]));
                        attributes.add(getOrderItemAttribute(EVENT_DISPENSE_REQUEST_ID, item.getDispenseRequestId()));
                        attributes.add(getOrderItemAttribute(PRESCRIPTION_ID, item.getPrescriptionId()));
                        if(!StringUtils.equalsIgnoreCase(orderItem.getAttributes().get(FACILITY_ID).toString(), item.getFacilityId())){
                            attributes.add(getOrderItemAttribute(FACILITY_ID, item.getFacilityId()));
                            LocationService locationService = new LocationService(context);
                            String oldFacilityRef = "";
                            String newFacilityRef = locationService.getLocationById(item.getFacilityId()).ref();
                            oldFacilityRef = getOldFacilityRef(orderItem, attributes, newFacilityRef);
                            unreserve.add(getItemToUpdateInventoryQty(orderItem, oldFacilityRef));
                            reserve.add(getItemToUpdateInventoryQty(orderItem, newFacilityRef));
                        }
                        updateInputOrderItems.add(UpdateOrderItemWithOrderInput.builder().id(orderItem.getId())
                                .attributes(attributes).build());
            }
        });
    }

    private String getOldFacilityRef(OrderItem orderItem, List<AttributeInput> attributes, String newFacilityRef) {
        String oldFacilityRef;
        if(orderItem.getAttributes().containsKey(DEFAULT_SELECTED_LOCATION)){
            attributes.add(getOrderItemAttribute(DEFAULT_SELECTED_LOCATION, newFacilityRef));
            oldFacilityRef = orderItem.getAttributes().get(DEFAULT_SELECTED_LOCATION).toString();
        } else {
            attributes.add(getOrderItemAttribute(SELECTED_LOCATION, newFacilityRef));
            oldFacilityRef = orderItem.getAttributes().get(SELECTED_LOCATION).toString();
        }
        return oldFacilityRef;
    }

    private AttributeInput getOrderItemAttribute(String name, String value) {
        return AttributeInput.builder()
                .name(name)
                .type(ATTRIBUTE_TYPE_STRING)
                .value(value)
                .build();
    }

    private Map<String, String> getItemToUpdateInventoryQty(OrderItem orderItem, String facilityId) {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put(SKU_REF, orderItem.getProductRef());
        returnMap.put(ORDER_ITEM_ID, orderItem.getId());
        returnMap.put(QUANTITY, String.valueOf(orderItem.getQuantity()));
        returnMap.put(LOCATION_REF, facilityId);
        return returnMap;
    }
}
