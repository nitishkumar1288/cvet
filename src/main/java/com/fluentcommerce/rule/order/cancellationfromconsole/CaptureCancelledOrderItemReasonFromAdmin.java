package com.fluentcommerce.rule.order.cancellationfromconsole;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.CancelledOrderItem;
import com.fluentcommerce.dto.CancelledOrderItemReasonPayload;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
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
        name = "CaptureCancelledOrderItemReasonFromAdmin",
        description = "in case of order item cancellation get the cancellation reason from event attribute and  " +
                "forward to next webhook event {" + PROP_EVENT_NAME + "}",
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
@Slf4j
public class CaptureCancelledOrderItemReasonFromAdmin extends BaseRule {
    private static final String CLASS_NAME = CaptureCancelledOrderItemReasonFromAdmin.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        Map<String,Object> eventAttributeMap = event.getAttributes();
        String cancellationReason = (String) eventAttributeMap.get(CANCEL_REASON);
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        String orderId = context.getEvent().getEntityId();
        String orderRef = context.getEvent().getEntityRef();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(orderId);
        CancelledOrderItemReasonPayload payload = createPayloadForCancelledOrderItem(order,cancellationReason,orderRef);
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

    public static CancelledOrderItemReasonPayload createPayloadForCancelledOrderItem(GetOrderByIdQuery.OrderById order,
                                                                                     String cancellationReason,String orderRef) {
        CancelledOrderItemReasonPayload orderItemPayload = new CancelledOrderItemReasonPayload();
        orderItemPayload.setUpdateOn(Instant.now().toString());
        orderItemPayload.setOrderRef(orderRef);
        ArrayList<CancelledOrderItem> cancelledOrderItemList = new ArrayList<>();
        for (GetOrderByIdQuery.OrderItemEdge itemEdge:order.orderItems().orderItemEdge()) {
            if (Objects.nonNull(itemEdge.orderItemNode()) && Objects.nonNull(itemEdge.orderItemNode().orderItemAttributes())) {
                for (GetOrderByIdQuery.OrderItemAttribute itemAttributes : itemEdge.orderItemNode().orderItemAttributes()) {
                    if (itemAttributes.name().equalsIgnoreCase(ORDER_LINE_STATUS) &&
                            !itemAttributes.value().toString().equalsIgnoreCase(CANCELLED)) {
                        CancelledOrderItem cancelledOrderItem = getCancelledOrderItem(cancellationReason, itemEdge);
                        cancelledOrderItemList.add(cancelledOrderItem);
                    }
                }
            }
        }
        orderItemPayload.setItems(cancelledOrderItemList);
        return orderItemPayload;
    }

    private static CancelledOrderItem getCancelledOrderItem(String cancellationReason, GetOrderByIdQuery.OrderItemEdge itemEdge) {
        CancelledOrderItem cancelledOrderItem = new CancelledOrderItem();
        cancelledOrderItem.setOrderItemKey(itemEdge.orderItemNode().ref());
        cancelledOrderItem.setCancellationReasonText(cancellationReason);
        cancelledOrderItem.setLineStatus(CANCELLED);
        Optional<GetOrderByIdQuery.OrderItemAttribute> autoShipId = itemEdge.orderItemNode().orderItemAttributes().stream()
                .filter(itemAttribute->itemAttribute.name().equalsIgnoreCase(AUTO_SHIP_ID)).findFirst();
        if (autoShipId.isPresent()){
            cancelledOrderItem.setAutoshipKey(autoShipId.get().value().toString());
        }

        // This logic will differ when the partial line item cancellation is introduced
        Optional<GetOrderByIdQuery.OrderItemAttribute> cancelledLineItemQuantity = itemEdge.orderItemNode().orderItemAttributes().stream()
                .filter(itemAttribute -> itemAttribute.name().equalsIgnoreCase(ORDERED_LINE_ITEM_QUANTITY)).findFirst();
        if (cancelledLineItemQuantity.isPresent()) {
            cancelledOrderItem.setOriginalOrderQty(cancelledLineItemQuantity.get().value().toString());
            cancelledOrderItem.setCancelledQty(cancelledLineItemQuantity.get().value().toString());
        }
        return cancelledOrderItem;
    }

}
