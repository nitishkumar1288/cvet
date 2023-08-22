package com.fluentcommerce.rule.order.paymentauth;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentAuthReversalRequest;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CreateOrderPaymentAuthReversalRequest",
        description = "create payment auth reversal request in case of order cancellation and call to next event {" + PROP_EVENT_NAME + "}",
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
        description = "event name"
)

@Slf4j
public class CreateOrderPaymentAuthReversalRequest extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);
        if (null != orderData && CollectionUtils.isNotEmpty(orderData.attributes())){
            Optional<GetOrderByIdQuery.Attribute> orderKeyAttribute =
                    orderData.attributes().stream()
                            .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY))
                            .findFirst();
            if (orderKeyAttribute.isPresent()){
                PaymentAuthReversalRequest request = createRequest(orderData,orderKeyAttribute.get().value().toString());
                Map<String, Object> attributes = new HashMap<>();
                attributes.put(PAYMENT_AUTH_REVERSAL_REQUEST_FOR_ORDER, request);
                attributes.putAll(context.getEvent().getAttributes());
                EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
            }
        }
    }

    private PaymentAuthReversalRequest createRequest(GetOrderByIdQuery.OrderById orderData, String orderKey) {
        PaymentAuthReversalRequest request = new PaymentAuthReversalRequest();
        request.setOrderKey(orderKey);
        request.setOrderRef(orderData.ref());
        return request;
    }
}
