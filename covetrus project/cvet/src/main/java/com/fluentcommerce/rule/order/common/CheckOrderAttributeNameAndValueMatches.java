package com.fluentcommerce.rule.order.common;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import static com.fluentcommerce.util.Constants.*;

/**
 * @author Nandhakumar
 */
@RuleInfo(
        name = "CheckOrderAttributeNameAndValueMatches",
        description = "Check order attribute name {" + ATTRIBUTE_NAME + "} and value {" + ATTRIBUTE_VALUE + "} Matches " +
                "and send event {" + PROP_EVENT_NAME + "} else send event {" + PROP_NO_MATCHING_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER),
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)
@ParamString(
        name = ATTRIBUTE_NAME,
        description = "Order Attribute name"
)
@ParamString(
        name = ATTRIBUTE_VALUE,
        description = "Order attribute value"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "Event name on condition matches"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "No match event name"
)
@Slf4j
public class CheckOrderAttributeNameAndValueMatches extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);

        String attributeName = context.getProp(ATTRIBUTE_NAME);
        String attributeValue = context.getProp(ATTRIBUTE_VALUE);

        String orderId = null;
        boolean isNameAndValueMatching = false;
        String entityType = context.getEntity().getEntityType();
        switch (entityType){
            case ENTITY_TYPE_ORDER:
                context.addLog("In order");
                orderId = context.getEvent().getEntityId();
                break;

            case ENTITY_TYPE_FULFILMENT:
                context.addLog("In Fulfilment");
                orderId = context.getEvent().getRootEntityId();
                break;

            default:
                break;
        }


        context.addLog("Order ID " + orderId);
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);

        Optional<GetOrderByIdQuery.Attribute> optionalAttribute =
                orderData.attributes().stream().filter(
                        attribute -> attribute.name().equalsIgnoreCase(attributeName)
                ).findFirst();

        if (optionalAttribute.isPresent() &&
                optionalAttribute.get().value().toString().equalsIgnoreCase(attributeValue)) {
            isNameAndValueMatching = true;
        }

        if (isNameAndValueMatching) {
            context.addLog("Name and Value matches");
            EventUtils.forwardInline(
                    context,
                    eventName
            );
        } else {
            context.addLog("Name and Value does not match");
            EventUtils.forwardInline(
                    context,
                    noMatchEventName
            );
        }
    }
}
