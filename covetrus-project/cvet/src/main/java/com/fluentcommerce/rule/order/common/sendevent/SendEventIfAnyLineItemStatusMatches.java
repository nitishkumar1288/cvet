package com.fluentcommerce.rule.order.common.sendevent;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendEventIfAnyLineItemStatusMatches",
        description = "Send event if any line item status {" + PROP_ORDER_LINE_STATUS + "} matches , call event  {" + PROP_EVENT_NAME + "} else call no matching event{" + PROP_NO_MATCHING_EVENT_NAME + "}",
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
        description = "order line item status"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "Event name to be called if status matches"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "no matching event name"
)
@Slf4j
public class SendEventIfAnyLineItemStatusMatches extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_ORDER_LINE_STATUS,PROP_EVENT_NAME,PROP_NO_MATCHING_EVENT_NAME);

        String orderLineStatus = context.getProp(PROP_ORDER_LINE_STATUS);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);

        Event event = context.getEvent();
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        List<GetOrderByIdQuery.OrderItemEdge> orderItemEdgeList = new ArrayList<>();
        List<GetOrderByIdQuery.OrderItemEdge> orderItemList = orderService.getOrderItems(orderId,orderItemEdgeList,null);
        boolean isStatusMatching = checkForStatusMatchingItems(
                orderItemList,
                orderLineStatus);

        context.addLog("isStatusMatching " + isStatusMatching);
        if (isStatusMatching){
            EventUtils.forwardInline(context,eventName);
        }else{
            EventUtils.forwardInline(context,noMatchEventName);
        }
    }

    private boolean checkForStatusMatchingItems(
            List<GetOrderByIdQuery.OrderItemEdge> orderItemList,
            String orderLineItemStatus
            ) {
        boolean isAvailable = false;

        for (GetOrderByIdQuery.OrderItemEdge edge : orderItemList) {
            if (Objects.nonNull(edge.orderItemNode()) && Objects.nonNull(edge.orderItemNode().orderItemAttributes()) ){
                for (GetOrderByIdQuery.OrderItemAttribute itemAttributes: edge.orderItemNode().orderItemAttributes()){
                    if(itemAttributes.name().equalsIgnoreCase(ORDER_LINE_STATUS)
                            && itemAttributes.value().equals(orderLineItemStatus)){
                        isAvailable = true;
                        break;
                    }
                }
            }
        }
        return isAvailable;
    }
}
