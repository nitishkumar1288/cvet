package com.fluentcommerce.rule.order.common.orderstatusactivity;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.order.status.OrderStatusPayload;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.OrderUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CaptureOrderStatus",
        description = "in case of order status change capture the details and " +
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
@Slf4j
public class CaptureOrderStatus extends BaseRule {
    private static final String CLASS_NAME = CaptureOrderStatus.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        String orderStatus = event.getEntityStatus();
        context.addLog("event order status::"+ orderStatus);
        String orderId = context.getEvent().getEntityId();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);
        Optional<GetOrderByIdQuery.Attribute> orderKeyAttribute =
                orderData.attributes().stream()
                        .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY))
                        .findFirst();
        Optional<GetOrderByIdQuery.Attribute> animalCareBusinessKeyAttribute =
                orderData.attributes().stream()
                        .filter(attribute -> attribute.name().equalsIgnoreCase(ANIMAL_CARE_BUSINESS))
                        .findFirst();
        String orderKey = null;
        String animalBussiCare = null;
        if (orderKeyAttribute.isPresent() && null != orderKeyAttribute.get().value().toString()){
            orderKey = orderKeyAttribute.get().value().toString();
        }
        if (animalCareBusinessKeyAttribute.isPresent() && null != animalCareBusinessKeyAttribute.get().value().toString()){
            animalBussiCare = animalCareBusinessKeyAttribute.get().value().toString();
        }
        OrderStatusPayload payload = OrderUtils.createOrderStatusPayload(orderKey,animalBussiCare);
            Map<String, Object> attributes = new HashMap<>();
            attributes.putAll(context.getEvent().getAttributes());
            attributes.put(ORDER_STATUS_PAYLOAD,payload);
            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    attributes);
    }
}