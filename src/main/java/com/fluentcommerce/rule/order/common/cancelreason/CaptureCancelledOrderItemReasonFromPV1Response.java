package com.fluentcommerce.rule.order.common.cancelreason;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.CancelledOrderItem;
import com.fluentcommerce.dto.CancelledOrderItemReasonPayload;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
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
 * @author Yamini Kukreja
 */

@RuleInfo(
        name = "CaptureCancelledOrderItemReasonFromPV1Response",
        description = "In case of order item cancellation get cancel reason code from PV1 response with cancelled status as {"
                + PROP_CANCEL_STATUS + "} " + "and forward to next webhook event {" + PROP_EVENT_NAME + "}",
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
        name = PROP_CANCEL_STATUS,
        description = "cancel reason from Pv1 response for cancelled item"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)

@EventAttribute(name = ITEMS)
@Slf4j
public class CaptureCancelledOrderItemReasonFromPV1Response extends BaseRule {
    private static final String CLASS_NAME = CaptureCancelledOrderItemReasonFromPV1Response.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_CANCEL_STATUS,PROP_EVENT_NAME);
        Event event = context.getEvent();
        String eventName = context.getProp(PROP_EVENT_NAME);
        String cancelStatus = context.getProp(PROP_CANCEL_STATUS);
        String orderRef = context.getEvent().getEntityRef();
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        List<PV1Items> pv1ItemsList = CommonUtils.convertObjectToDto(event.getAttributes().get(ITEMS),
                new TypeReference<List<PV1Items>>(){});
        CancelledOrderItemReasonPayload payload = findCancelledItemsAndCreatePayload(pv1ItemsList, orderRef, cancelStatus,order);
        if (CollectionUtils.isNotEmpty(payload.getItems())){
            Map<String, Object> attributes = new HashMap<>(context.getEvent().getAttributes());
            attributes.put(ORDER_ITEM_CANCELLED_REASON_PAYLOAD,payload);
            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    attributes);
        }
    }

    private CancelledOrderItemReasonPayload findCancelledItemsAndCreatePayload(List<PV1Items> pv1ItemsList, String orderRef, String cancelStatus,Order order) {
        ArrayList<CancelledOrderItem> cancelledOrderItemList = new ArrayList<>();
        CancelledOrderItemReasonPayload orderItemPayload = new CancelledOrderItemReasonPayload();
        orderItemPayload.setUpdateOn(Instant.now().toString());
        orderItemPayload.setOrderRef(orderRef);
        for(PV1Items item: pv1ItemsList){
            if(null != item.getStatus() && item.getStatus().equalsIgnoreCase(cancelStatus) &&
                    null !=item.getCancelReason()){
                Optional<OrderItem> optionalOrderItem = order.getItems().stream().filter(orderItem -> orderItem.getRef().
                        equalsIgnoreCase(item.getRef())).findFirst();
                if (optionalOrderItem.isPresent()){
                    CancelledOrderItem cancelledOrderItem = new CancelledOrderItem();
                    cancelledOrderItem.setOrderItemKey(item.getRef());
                    cancelledOrderItem.setCancellationReasonCode(item.getCancelReason());
                    cancelledOrderItem.setCancellationReasonText(item.getCancelReason());
                    cancelledOrderItem.setLineStatus(CANCELLED);
                    Map<String, Object> itemAttributes = optionalOrderItem.get().getAttributes();
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
        }
        orderItemPayload.setItems(cancelledOrderItemList);
        return orderItemPayload;
    }
}
