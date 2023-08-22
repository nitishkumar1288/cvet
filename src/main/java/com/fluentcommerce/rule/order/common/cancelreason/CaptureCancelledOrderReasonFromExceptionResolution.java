package com.fluentcommerce.rule.order.common.cancelreason;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.CancelledOrderReasonPayload;
import com.fluentcommerce.dto.fulfillment.FulfilmentExceptionData;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
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
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

@RuleInfo(
        name = "CaptureCancelledOrderReasonFromExceptionResolution",
        description = "In case of order cancellation get cancel reason from fulfilment exception resolution response " +
                "and construct the cancel order payload data" +
                "and forward to next webhook event {" + PROP_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE
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

@EventAttribute(name = FULFILMENT)
@Slf4j
public class CaptureCancelledOrderReasonFromExceptionResolution extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        String orderId = context.getEvent().getEntityId();
        String orderRef = context.getEvent().getEntityRef();
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderItems(orderId);
        FulfilmentExceptionData fulfilmentExceptionResolutionResponse = CommonUtils.convertObjectToDto(eventAttributes.get(FULFILMENT),
                new TypeReference<FulfilmentExceptionData>() {});
        CancelledOrderReasonPayload payload= findItemsAndCreatePayload(fulfilmentExceptionResolutionResponse,orderData,orderRef);
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
    private CancelledOrderReasonPayload findItemsAndCreatePayload(FulfilmentExceptionData fulfilmentExceptionResolutionResponse,
                                                                  GetOrderByIdQuery.OrderById orderData,String orderRef) {
        CancelledOrderReasonPayload payload = new CancelledOrderReasonPayload();
        if (orderData != null && orderData.orderItems() != null
                && CollectionUtils.isNotEmpty(orderData.orderItems().orderItemEdge())
                && null != fulfilmentExceptionResolutionResponse && CollectionUtils.isNotEmpty(fulfilmentExceptionResolutionResponse.getItems())){
            fulfilmentExceptionResolutionResponse.getItems().forEach(resItem -> {
                if (null != resItem.getStatus() && resItem.getStatus().equalsIgnoreCase(ORDER_ITEM_STATUS_CANCEL_PENDING)) {
                    Optional<GetOrderByIdQuery.OrderItemEdge> item = orderData.orderItems().orderItemEdge().stream().filter(orderItemEdge ->
                            orderItemEdge.orderItemNode().ref().equalsIgnoreCase(resItem.getRef())).findFirst();
                    if (item.isPresent()){
                        Instant currentTime = Instant.now();
                        payload.setOrderRef(orderRef);
                        payload.setStatus(CANCELLED);
                        payload.setCancellationReasonText(resItem.getCancelReason());
                        payload.setUpdateOn(currentTime.toString());
                    }
                }
            });
        }
        return payload;
    }
}
