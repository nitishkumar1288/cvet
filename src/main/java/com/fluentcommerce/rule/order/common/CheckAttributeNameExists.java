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
        name = "CheckAttributeNameExists",
        description = "Check order attribute name {" + ATTRIBUTE_NAME + "} Exists " +
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
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = ATTRIBUTE_NAME,
        description = "Order Attribute name"
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
public class CheckAttributeNameExists extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);

        String attributeName = context.getProp(ATTRIBUTE_NAME);

        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(context.getEvent().getEntityId());

        boolean isValid = false;
        Optional<GetOrderByIdQuery.Attribute> optionalAttribute =
                orderData.attributes().stream().filter(
                        attribute -> attribute.name().equalsIgnoreCase(attributeName)
                ).findFirst();

        if (optionalAttribute.isPresent()) {
            isValid = true;
        }

        if (isValid) {
            context.addLog("Name Exists");
            EventUtils.forwardInline(
                    context,
                    eventName
            );
        } else {
            context.addLog("Name does not exists");
            EventUtils.forwardInline(
                    context,
                    noMatchEventName
            );
        }
    }
}
