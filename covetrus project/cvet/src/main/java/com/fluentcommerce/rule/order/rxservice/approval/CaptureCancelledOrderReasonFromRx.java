package com.fluentcommerce.rule.order.rxservice.approval;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.CancelledOrderReasonPayload;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
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
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CaptureCancelledOrderReasonFromRx",
        description = "In case of order cancellation get declined response code from Rx approval response and add it to the " +
                "item attributes with prefix {" + RX_CANCELLATION_REASON_PREFIX + "}" +
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
public class CaptureCancelledOrderReasonFromRx extends BaseRule {
    private static final String CLASS_NAME = CaptureCancelledOrderReasonFromRx.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String eventName = context.getProp(PROP_EVENT_NAME);
        String cancellationPrefix = context.getProp(RX_CANCELLATION_REASON_PREFIX);
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        String orderId = context.getEvent().getEntityId();
        String orderRef = context.getEvent().getEntityRef();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderItems(orderId);
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<Items> rxServiceRespItems = CommonUtils.convertObjectToDto(eventAttributes.get(ITEMS),new TypeReference<List<Items>>(){});
        CancelledOrderReasonPayload payload= findItemsAndCreatePayload(rxServiceRespItems,orderData,orderRef,cancellationPrefix);
        if (StringUtils.isNotEmpty(payload.getStatus())){
            Map<String, Object> attributes = new HashMap<>();
            attributes.putAll(context.getEvent().getAttributes());
            attributes.put(ORDER_CANCELLED_REASON_PAYLOAD,payload);
            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    attributes);
        }
    }
    private CancelledOrderReasonPayload findItemsAndCreatePayload(List<Items> rxServiceRespItems,
                                                                  GetOrderByIdQuery.OrderById orderData,String orderRef,String cancellationPrefix) {
        CancelledOrderReasonPayload payload = new CancelledOrderReasonPayload();
        if (orderData != null && orderData.orderItems() != null
                && CollectionUtils.isNotEmpty(orderData.orderItems().orderItemEdge())
                && CollectionUtils.isNotEmpty(rxServiceRespItems)){
            rxServiceRespItems.forEach(resItem -> {
                if (null != resItem.getStatus() && resItem.getStatus().equalsIgnoreCase(ORDER_ITEM_STATUS_DECLINED)) {
                    Optional<GetOrderByIdQuery.OrderItemEdge> item = orderData.orderItems().orderItemEdge().stream().filter(orderItemEdge ->
                        orderItemEdge.orderItemNode().ref().equalsIgnoreCase(resItem.getLineNumber())).findFirst();
                    if (item.isPresent()){
                        Instant currentTime = Instant.now();
                        payload.setOrderRef(orderRef);
                        payload.setStatus(CANCELLED);
                        payload.setCancellationReasonText(cancellationPrefix+COLON+resItem.getDeclinedReasonCode());
                        payload.setCancellationReasonCode(cancellationPrefix+COLON+resItem.getDeclinedReasonCode());
                        payload.setUpdateOn(currentTime.toString());
                    }
                }
            });
        }
        return payload;
    }
}