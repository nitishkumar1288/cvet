package com.fluentcommerce.rule.order.common.orderstatus;

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

import java.util.List;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author nitishKumar
 */

@RuleInfo(
        name = "SendEventOnVerifyingAllOrderLineStatus",
        description = "If all the order line status matches {" + PROP_ORDER_LINE_STATUS + "} or" +
                " part of excluded list {" + PROP_EXCLUDE_STATUS_LIST + "} call event" +
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
@Slf4j
public class SendEventOnVerifyingAllOrderLineStatus extends BaseRule {
    @Override
    public void run(ContextWrapper context) {

        String allMatchEventName = context.getProp(PROP_ALL_MATCH_EVENT_NAME);
        String partialMatchEventName = context.getProp(PROP_PARTIAL_MATCH_EVENT_NAME);
        String noMatchingEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);

        List<String> orderLineStatusList = context.getPropList(PROP_ORDER_LINE_STATUS, String.class);
        List<String> excludeStatusList = context.getPropList(PROP_EXCLUDE_STATUS_LIST, String.class);

        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(orderId);

        int allItemCount = 0;
        int partialItemCount = 0;
        boolean isItemStatusMatching = false;

        for (GetOrderByIdQuery.OrderItemEdge orderItem : order.orderItems().orderItemEdge()) {

            Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemAttribute = orderItem.orderItemNode().orderItemAttributes().stream().filter(
                    orderItemAttribute -> (orderItemAttribute.name().equalsIgnoreCase(ORDER_LINE_STATUS))
            ).findFirst();

            if (optionalOrderItemAttribute.isPresent()) {
                String lineStatus = optionalOrderItemAttribute.get().value().toString();
                if (orderLineStatusList.contains(lineStatus)) {
                    allItemCount = allItemCount + 1;
                    partialItemCount = partialItemCount + 1;
                    isItemStatusMatching = true;
                } else if (excludeStatusList.contains(lineStatus)) {
                    allItemCount = allItemCount + 1;
                }
            }
        }

        context.addLog("Total Number of line items " + order.orderItems().orderItemEdge());
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
