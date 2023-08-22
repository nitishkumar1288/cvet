package com.fluentcommerce.rule.order.common.sendevent;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */


@RuleInfo(
        name = "SendEventOnVerifyingOrderItemAttributeAndStatus",
        description = "If all the order line status matches {" + PROP_ORDER_LINE_STATUS + "} or" +
                " part of excluded list {" + PROP_EXCLUDE_STATUS_LIST + "} match attribute name {" + PROP_ATTRIBUTE_NAME + "} and value {" + ATTRIBUTE_VALUE + "} call event" +
                "{" + PROP_ALL_MATCH_EVENT_NAME + "}  else if it matches partial condition call {" + PROP_PARTIAL_MATCH_EVENT_NAME + "} else call " +
                "{" + PROP_NO_MATCHING_EVENT_NAME + "}",
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
        name = PROP_ORDER_LINE_STATUS,
        description = "order line status"
)
@ParamString(
        name = PROP_EXCLUDE_STATUS_LIST,
        description = "Excluded status list"
)
@ParamString(
        name = PROP_ALL_MATCH_EVENT_NAME,
        description = "All match event name"
)
@ParamString(
        name = PROP_PARTIAL_MATCH_EVENT_NAME,
        description = "partial match event name"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "No match event name"
)
@ParamString(
        name = PROP_ATTRIBUTE_NAME,
        description = "attribute name"
)
@ParamString(
        name = ATTRIBUTE_VALUE,
        description = "attribute value"
)
@Slf4j
public class SendEventOnVerifyingOrderItemAttributeAndStatus extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_ALL_MATCH_EVENT_NAME,PROP_PARTIAL_MATCH_EVENT_NAME,PROP_NO_MATCHING_EVENT_NAME,
                PROP_ATTRIBUTE_NAME,ATTRIBUTE_VALUE);
        String allMatchEventName = context.getProp(PROP_ALL_MATCH_EVENT_NAME);
        String partialMatchEventName = context.getProp(PROP_PARTIAL_MATCH_EVENT_NAME);
        String noMatchingEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);
        String attributeName = context.getProp(PROP_ATTRIBUTE_NAME);
        String attributeValue = context.getProp(ATTRIBUTE_VALUE);
        String orderLineStatus = context.getProp(PROP_ORDER_LINE_STATUS);
        List<String> excludeStatusList = context.getPropList(PROP_EXCLUDE_STATUS_LIST, String.class);

        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(orderId);

        int allItemCount = 0;
        int partialItemCount = 0;
        boolean isItemStatusMatching = false;

        for (GetOrderByIdQuery.OrderItemEdge orderItem : order.orderItems().orderItemEdge()) {

            Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemStatusAttribute = orderItem.orderItemNode().orderItemAttributes().stream().filter(
                    orderItemAttribute -> (orderItemAttribute.name().equalsIgnoreCase(ORDER_LINE_STATUS))
            ).findFirst();
            Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemAttribute = orderItem.orderItemNode().orderItemAttributes().stream().filter(
                    orderItemAttribute -> (orderItemAttribute.name().equalsIgnoreCase(attributeName))
            ).findFirst();

            if (optionalOrderItemStatusAttribute.isPresent()) {
                String lineStatus = optionalOrderItemStatusAttribute.get().value().toString();
                if (lineStatus.equalsIgnoreCase(orderLineStatus) && optionalOrderItemAttribute.isPresent()
                        && optionalOrderItemAttribute.get().value().toString().equalsIgnoreCase(attributeValue)) {
                    allItemCount = allItemCount + 1;
                    partialItemCount = partialItemCount + 1;
                    isItemStatusMatching = true;
                } else if (excludeStatusList.contains(lineStatus)) {
                    allItemCount = allItemCount + 1;
                }
            }
        }

        context.addLog("Total Number of line items " + order.orderItems().orderItemEdge().size());
        context.addLog("allItemCount " + allItemCount);
        context.addLog("partialItemCount " + partialItemCount);

        // all match event name -> All statuses can not be part of ignored status alone  , minimum of 1 item status should match with the required status
        if (allItemCount == order.orderItems().orderItemEdge().size() && isItemStatusMatching) {
            EventUtils.forwardInline(context, allMatchEventName);
        } else if (partialItemCount > 0) {
            EventUtils.forwardInline(context, partialMatchEventName);
        } else {
            EventUtils.forwardInline(context, noMatchingEventName);
        }
    }
}
