package com.fluentcommerce.rule.order.rxservice.approval;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.CancelledOrderItem;
import com.fluentcommerce.dto.CancelledOrderItemReasonPayload;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.model.rx.Items;
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

import java.time.Instant;
import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CaptureCancelledOrderItemReasonFromRx",
        description = "In case of order item cancellation get declined response code from Rx approval response and " +
                "add it to the item attributes with prefix {" + RX_CANCELLATION_REASON_PREFIX + "}" +
                "and forward to next webhook event {" + PROP_EVENT_NAME + "}",
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
        name = RX_CANCELLATION_REASON_PREFIX,
        description = "cancellation reason prefix"
)

@EventAttribute(name = ITEMS)
@Slf4j
public class CaptureCancelledOrderItemReasonFromRx extends BaseRule {
    private static final String CLASS_NAME = CaptureCancelledOrderItemReasonFromRx.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String eventName = context.getProp(PROP_EVENT_NAME);
        String cancellationPrefix = context.getProp(RX_CANCELLATION_REASON_PREFIX);
        String orderId = context.getEvent().getEntityId();
        String orderRef = context.getEvent().getEntityRef();
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        List<Items> rxServiceRespItems = CommonUtils.convertObjectToDto(eventAttributes.get(ITEMS),new TypeReference<List<Items>>(){});
        CancelledOrderItemReasonPayload payload = findCancelledItemsAndCreatePayload(rxServiceRespItems,order,orderRef,cancellationPrefix);
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

    private CancelledOrderItemReasonPayload findCancelledItemsAndCreatePayload(List<Items> rxServiceRespItems,
                                                                               Order order,String orderRef,String cancellationPrefix) {
        ArrayList<CancelledOrderItem> cancelledOrderItemList = new ArrayList<>();
        CancelledOrderItemReasonPayload orderItemPayload = new CancelledOrderItemReasonPayload();
        orderItemPayload.setUpdateOn(Instant.now().toString());
        orderItemPayload.setOrderRef(orderRef);
        for (Items item:rxServiceRespItems){
            if(null != item.getStatus() && item.getStatus().equalsIgnoreCase(DECLINED)){
                Optional<OrderItem> orderLineItem = order.getItems().stream().filter(
                        orderItem -> orderItem.getRef().equalsIgnoreCase(item.getLineNumber())
                ).findFirst();
                if (orderLineItem.isPresent()){
                    CancelledOrderItem cancelledOrderItem = new CancelledOrderItem();
                    cancelledOrderItem.setOrderItemKey(orderLineItem.get().getRef());
                    cancelledOrderItem.setCancellationReasonCode(cancellationPrefix+COLON+item.getDeclinedReasonCode());
                    cancelledOrderItem.setCancellationReasonText(cancellationPrefix+COLON+item.getDeclinedReasonCode());
                    Map<String, Object> itemAttributes = orderLineItem.get().getAttributes();
                    cancelledOrderItem.setLineStatus((String) itemAttributes.get(ORDER_LINE_STATUS));
                    if(null != itemAttributes.get(AUTO_SHIP_ID)){
                        cancelledOrderItem.setAutoshipKey(itemAttributes.get(AUTO_SHIP_ID).toString());
                    }
                    if(null != itemAttributes.get(PRESCRIPTION_ID)){
                        cancelledOrderItem.setPrescriptionId(itemAttributes.get(PRESCRIPTION_ID).toString());
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
