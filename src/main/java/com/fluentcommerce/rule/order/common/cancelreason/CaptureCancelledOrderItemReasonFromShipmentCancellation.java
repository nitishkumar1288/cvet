package com.fluentcommerce.rule.order.common.cancelreason;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.CancelledOrderItem;
import com.fluentcommerce.dto.CancelledOrderItemReasonPayload;
import com.fluentcommerce.dto.fulfillment.Line;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
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
import org.apache.commons.collections4.CollectionUtils;

import java.time.Instant;
import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CaptureCancelledOrderItemReasonFromShipmentCancellation",
        description = "fetch the cancelled items from the shipment response ,get the cancellation reason {" + CANCELLATION_REASON + "}  and " +
                "forward  to next webhook event {" + PROP_EVENT_NAME + "}",
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
        name = CANCELLATION_REASON,
        description = "order cancellation reason"
)

@EventAttribute(name = SHIP_CANCELLED_ITEM_REF_LIST)
@Slf4j
public class CaptureCancelledOrderItemReasonFromShipmentCancellation extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,CANCELLATION_REASON,PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String cancellationReason = context.getProp(CANCELLATION_REASON);
        Event event = context.getEvent();
        List<Line> cancelledItemRefList = context.getEvent().getAttributeList(SHIP_CANCELLED_ITEM_REF_LIST, Line.class);
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        if (CollectionUtils.isNotEmpty(cancelledItemRefList)){
            CancelledOrderItemReasonPayload payload = findCancelledItemsAndCreatePayload(cancelledItemRefList,order,cancellationReason);
            if (CollectionUtils.isNotEmpty(payload.getItems())){
                Map<String, Object> attributes = new HashMap<>();
                attributes.putAll(context.getEvent().getAttributes());
                attributes.put(ORDER_ITEM_CANCELLED_REASON_PAYLOAD,payload);
                EventUtils.forwardEventInlineWithAttributes(
                        context,
                        eventName,
                        attributes);
            }
        }
    }

    private CancelledOrderItemReasonPayload findCancelledItemsAndCreatePayload(List<Line> cancelledItemRefList, Order order, String cancellationReason) {
        ArrayList<CancelledOrderItem> cancelledOrderItemList = new ArrayList<>();
        CancelledOrderItemReasonPayload orderItemPayload = new CancelledOrderItemReasonPayload();
        orderItemPayload.setUpdateOn(Instant.now().toString());
        orderItemPayload.setOrderRef(order.getRef());
        for (Line lineData : cancelledItemRefList) {
            Optional<OrderItem> orderLineItem = order.getItems().stream().filter(
                    orderItem -> orderItem.getRef().equalsIgnoreCase(lineData.getRef())
            ).findFirst();
            if (orderLineItem.isPresent()){
                CancelledOrderItem cancelledOrderItem = new CancelledOrderItem();
                cancelledOrderItem.setOrderItemKey(orderLineItem.get().getRef());
                cancelledOrderItem.setCancellationReasonText(cancellationReason);
                Map<String, Object> itemAttributes = orderLineItem.get().getAttributes();
                cancelledOrderItem.setLineStatus((String) itemAttributes.get(ORDER_LINE_STATUS));
                if(null != itemAttributes.get(AUTO_SHIP_ID)){
                    cancelledOrderItem.setAutoshipKey(itemAttributes.get(AUTO_SHIP_ID).toString());
                }

                // This logic will differ when the partial line item cancellation is introduced
                if( null != itemAttributes.get(ORDERED_LINE_ITEM_QUANTITY)){
                    cancelledOrderItem.setOriginalOrderQty(itemAttributes.get(ORDERED_LINE_ITEM_QUANTITY).toString());
                    cancelledOrderItem.setCancelledQty(itemAttributes.get(ORDERED_LINE_ITEM_QUANTITY).toString());
                }
                cancelledOrderItemList.add(cancelledOrderItem);
            }
        }
        orderItemPayload.setItems(cancelledOrderItemList);
        return orderItemPayload;
    }
}
