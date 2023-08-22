package com.fluentcommerce.rule.order.pv1.exceptionresolution;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.order.status.OrderItemStatus;
import com.fluentcommerce.dto.order.status.OrderItemStatusPayload;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author Yamini Kukreja
 */

@RuleInfo(
        name = "CaptureChangedOrderItemStatusFromPV1Response",
        description = "In case of order item status change capture the details and status list {" + PROP_STATUS_LIST +
                "} forward  to next webhook event {" + PROP_EVENT_NAME + "}. Send exceptionTypeCode or exceptionResolutionCode when " +
                "item status is {" + PROP_EXCEPTION_STATUS_RESPONSE + "} or {" + PROP_EXCEPTION_RESOLUTION_STATUS_RESPONSE +
                "} respectively.",
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
        name = PROP_STATUS_LIST,
        description = "forward to next Event name"
)
@ParamString(
        name = PROP_EXCEPTION_STATUS_RESPONSE,
        description = "exception status response"
)
@ParamString(
        name = PROP_EXCEPTION_RESOLUTION_STATUS_RESPONSE,
        description = "exception resolution status response"
)

@EventAttribute(name = PROP_STATUS_LIST)
@Slf4j
public class CaptureChangedOrderItemStatusFromPV1Response extends BaseRule {

    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String exceptionStatusResponse = context.getProp(PROP_EXCEPTION_STATUS_RESPONSE);
        String exceptionResolutionStatusResponse = context.getProp(PROP_EXCEPTION_RESOLUTION_STATUS_RESPONSE);
        List<String> statusList = context.getProp(PROP_STATUS_LIST, List.class);
        List<PV1Items> items = event.getAttributeList(ITEMS, PV1Items.class);
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        ArrayList<OrderItemStatus> differItemList = new ArrayList<>();

        if(!items.isEmpty()){
            for(PV1Items item: items){
                if(statusList.contains(item.getStatus())){
                    OrderItemStatus orderItem = new OrderItemStatus();
                    orderItem.setOrderItemKey(item.getRef());
                    Optional<OrderItem> getOrderItem = order.getItems().stream().filter(i -> i.getRef().equalsIgnoreCase(item.getRef()))
                            .findFirst();
                    getOrderItem.ifPresent(value -> orderItem.setLineStatus(value.getAttributes().get(ORDER_LINE_STATUS).toString()));
                    orderItem.setPrescriptionId(item.getPrescriptionId());
                    getOrderItemStatusList(exceptionStatusResponse, exceptionResolutionStatusResponse, differItemList, item, orderItem);
                }
            }
        }

        if (!differItemList.isEmpty()) {
            Map<String, Object> attributes = createAttributeMap(differItemList, order, orderId);
            attributes.putAll(context.getEvent().getAttributes());
            EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
        }

    }

    private void getOrderItemStatusList(String exceptionStatusResponse, String exceptionResolutionStatusResponse, ArrayList<OrderItemStatus> differItemList, PV1Items item, OrderItemStatus orderItem) {
        if(StringUtils.equalsIgnoreCase(exceptionStatusResponse, item.getStatus())) {
            orderItem.setExceptionTypeCode(item.getExceptionReason());
        }
        else{
            orderItem.setExceptionTypeCode("");
        }

        if(StringUtils.equalsIgnoreCase(exceptionResolutionStatusResponse, item.getStatus())){
            String exceptionResolutionType = StringUtils.isNotEmpty(item.getExceptionResolutionType()) ? item.getExceptionResolutionType() : "" ;
            orderItem.setExceptionResolutionCode(exceptionResolutionType);
        }
        else{
            orderItem.setExceptionResolutionCode("");
        }
        differItemList.add(orderItem);
    }

    private Map<String, Object> createAttributeMap(ArrayList<OrderItemStatus> differItemList, Order order,
                                                   String orderId) {
        OrderItemStatusPayload payload = new OrderItemStatusPayload();
        payload.setOrderId(orderId);
        payload.setOrderRef(order.getRef());
        payload.setUpdateOn(Instant.now().toString());
        payload.setItems(differItemList);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(ORDER_ITEM_STATUS_PAYLOAD, payload);
        return attributes;
    }
}
