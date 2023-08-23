package com.fluentcommerce.rule.fulfilment;

import com.fluentcommerce.common.ContextWrapper;
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

import java.util.HashMap;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendEventForOrder",
        description = "Send an {" + PROP_EVENT_NAME + "} event for the associated Order",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(
                        entityType = ENTITY_TYPE_FULFILMENT
                )
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "The name of event to be triggered"
)

@Slf4j
public class SendEventForOrder extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        Event event = context.getEvent();
        String eventName = context.getProp(PROP_EVENT_NAME);
        String orderId = event.getRootEntityId();
        String orderRef = event.getRootEntityRef();
        String fulfillmentId = event.getEntityId();
        String fulfillmentRef = event.getEntityRef();
        Map<String, Object> attributes = new HashMap<>();
        attributes.putAll(context.getEvent().getAttributes());
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);
        String orderStatus = orderData.status();
        attributes.put(FULFILLMENT_ID,fulfillmentId);
        attributes.put(FULFILLMENT_REF,fulfillmentRef);
        EventUtils.forwardEventToParentEntity(context, eventName, orderId, orderRef, ENTITY_TYPE_ORDER, attributes,orderStatus);
    }
}
