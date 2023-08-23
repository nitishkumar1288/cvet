package com.fluentcommerce.rule.order.common.cancelreason;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.CancelledOrderItem;
import com.fluentcommerce.dto.CancelledOrderItemReasonPayload;
import com.fluentcommerce.dto.fulfillment.FulfilmentExceptionData;
import com.fluentcommerce.dto.fulfillment.FulfilmentItems;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CaptureCancelledOrderItemReasonFromExceptionResolution",
        description = "Match the order line status {" + PROP_CANCEL_STATUS_RESPONSE + "} ,if match fetch the cancellation reason from the order item and " +
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
        name = PROP_CANCEL_STATUS_RESPONSE,
        description = "order line item status"
)

@EventAttribute(name = FULFILMENT)
@Slf4j
public class CaptureCancelledOrderItemReasonFromExceptionResolution extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME,PROP_CANCEL_STATUS_RESPONSE);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String orderItemStatus = context.getProp(PROP_CANCEL_STATUS_RESPONSE);
        String orderId = context.getEvent().getEntityId();
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        FulfilmentExceptionData fulfilmentExceptionResolutionResponse = CommonUtils.convertObjectToDto(eventAttributes.get(FULFILMENT),
                new TypeReference<FulfilmentExceptionData>() {});
        CancelledOrderItemReasonPayload payload = findCancelledItemsAndCreatePayload(fulfilmentExceptionResolutionResponse,order,
                orderItemStatus);
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

    private CancelledOrderItemReasonPayload findCancelledItemsAndCreatePayload(FulfilmentExceptionData exceptionResolutionResponse,
                                                                               Order order, String orderItemStatus) {
        ArrayList<CancelledOrderItem> cancelledOrderItemList = new ArrayList<>();
        CancelledOrderItemReasonPayload orderItemPayload = new CancelledOrderItemReasonPayload();
        orderItemPayload.setUpdateOn(Instant.now().toString());
        orderItemPayload.setOrderRef(order.getRef());
        for (FulfilmentItems item:exceptionResolutionResponse.getItems()){
            if(null != item.getStatus() && item.getStatus().equalsIgnoreCase(orderItemStatus)){
                Optional<OrderItem> orderLineItem = order.getItems().stream().filter(
                        orderItem -> orderItem.getRef().equalsIgnoreCase(item.getRef())
                ).findFirst();
                if (orderLineItem.isPresent()){
                    CancelledOrderItem cancelledOrderItem = new CancelledOrderItem();
                    Map<String, Object> itemAttributes = orderLineItem.get().getAttributes();
                    cancelledOrderItem.setOrderItemKey(orderLineItem.get().getRef());
                    cancelledOrderItem.setCancellationReasonText((String) itemAttributes.get(REASON_FOR_CANCELLATION));
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
        }
        orderItemPayload.setItems(cancelledOrderItemList);
        return orderItemPayload;
    }
}
