package com.fluentcommerce.rule.order.pv1.exceptionresolution;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
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
        name = "UpdateOrderItemBasedOnPV1ExceptionResolutionResponse",
        description = "Update the order items status with respect to pv1 exception resolution status response {" +
                PROP_EXCEPTION_RESOLUTION_STATUS_RESPONSE + "} and cancel status response {" + PROP_CANCEL_STATUS_RESPONSE +
                "} followed by line status. Send event {" + PROP_EVENT_NAME + "}.",
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
        name = PROP_EXCEPTION_RESOLUTION_STATUS_RESPONSE,
        description = "exception resolution status response from pv1:exception resolution line status"
)
@ParamString(
        name = PROP_CANCEL_STATUS_RESPONSE,
        description = "cancel status response from pv1:cancel line status"
)
@EventAttribute(name = ITEMS)
@Slf4j
public class UpdateOrderItemBasedOnPV1ExceptionResolutionResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String eventName = context.getProp(PROP_EVENT_NAME);
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        List<PV1Items> items = event.getAttributeList(ITEMS, PV1Items.class);
        String[] exceptionResolutionStatusResponse = context.getProp(PROP_EXCEPTION_RESOLUTION_STATUS_RESPONSE).split(COLON);
        String[] cancelStatusResponse = context.getProp(PROP_CANCEL_STATUS_RESPONSE).split(COLON);

        if(!items.isEmpty()){
            List<UpdateOrderItemWithOrderInput> updateInputOrderItems = new ArrayList<>();
            List<Map<String, String>> unreserve = new ArrayList<>();
            getOrderItemForUpdate(order, items, exceptionResolutionStatusResponse, cancelStatusResponse, updateInputOrderItems, unreserve);
            orderService.updateOrderItems(updateInputOrderItems, orderId);
            if(!unreserve.isEmpty()){
                Map<String, Object> attributes = new HashMap<>(event.getAttributes());
                attributes.put(UNRESERVE, unreserve);
                EventUtils.forwardEventInlineWithAttributes(
                        context,
                        eventName,
                        attributes);
            }
        }
    }

    private void getOrderItemForUpdate(Order order, List<PV1Items> items, String[] exceptionResolutionStatusResponse, String[] cancelStatusResponse, List<UpdateOrderItemWithOrderInput> updateInputOrderItems, List<Map<String, String>> unreserve) {
        for(PV1Items item: items){
            order.getItems().stream().forEach(orderItem -> {
                if(StringUtils.equalsIgnoreCase(orderItem.getRef(), item.getRef())) {
                    if (StringUtils.equalsIgnoreCase(item.getStatus(), cancelStatusResponse[0])) {

                        handleCancelledItems(cancelStatusResponse, updateInputOrderItems, unreserve, item, orderItem);

                    } else if(StringUtils.equalsIgnoreCase(item.getStatus(), exceptionResolutionStatusResponse[0])) {

                        handleItemsWithResolution(exceptionResolutionStatusResponse, updateInputOrderItems, item, orderItem);
                    }
                }
            });
        }
    }

    private void handleItemsWithResolution(
            String[] exceptionResolutionStatusResponse,
            List<UpdateOrderItemWithOrderInput> updateInputOrderItems,
            PV1Items item,
            OrderItem orderItem) {

        List<AttributeInput> attributes = new ArrayList<>();
        attributes.add(getOrderItemAttribute(OLD_LINE_STATUS, orderItem.getAttributes().get(ORDER_LINE_STATUS).toString()));
        attributes.add(getOrderItemAttribute(ORDER_LINE_STATUS, exceptionResolutionStatusResponse[1]));
        attributes.add(getOrderItemAttribute(EVENT_DISPENSE_REQUEST_ID, item.getDispenseRequestId()));

        String exceptionResolutionType = StringUtils.isNotEmpty(item.getExceptionResolutionType()) ? item.getExceptionResolutionType() : "" ;
        attributes.add(getOrderItemAttribute(EXCEPTION_RESOLUTION_TYPE, exceptionResolutionType));
        updateInputOrderItems.add(UpdateOrderItemWithOrderInput.builder().id(orderItem.getId())
                .attributes(attributes).build());
    }

    private void handleCancelledItems(
            String[] cancelStatusResponse,
            List<UpdateOrderItemWithOrderInput> updateInputOrderItems,
            List<Map<String, String>> unreserve,
            PV1Items item,
            OrderItem orderItem) {

        List<AttributeInput> attributes = new ArrayList<>();
        attributes.add(getOrderItemAttribute(OLD_LINE_STATUS, orderItem.getAttributes().get(ORDER_LINE_STATUS).toString()));
        attributes.add(getOrderItemAttribute(ORDER_LINE_STATUS, cancelStatusResponse[1]));
        attributes.add(getOrderItemAttribute(EVENT_CANCELLATION_REASON, item.getCancelReason()));
        if(orderItem.getAttributes().containsKey(DEFAULT_SELECTED_LOCATION))
            unreserve.add(getItemToUpdateInventoryQty(orderItem, orderItem.getAttributes().get(DEFAULT_SELECTED_LOCATION).toString()));
        else
            unreserve.add(getItemToUpdateInventoryQty(orderItem, orderItem.getAttributes().get(SELECTED_LOCATION).toString()));
        updateInputOrderItems.add(UpdateOrderItemWithOrderInput.builder().id(orderItem.getId())
            .quantity(0).attributes(attributes).build());
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
