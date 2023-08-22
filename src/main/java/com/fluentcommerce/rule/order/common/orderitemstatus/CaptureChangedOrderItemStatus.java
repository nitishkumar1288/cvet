package com.fluentcommerce.rule.order.common.orderitemstatus;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.order.status.OrderItemStatus;
import com.fluentcommerce.dto.order.status.OrderItemStatusPayload;
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
import org.apache.commons.collections4.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;
import static com.fluentcommerce.util.EventUtils.forwardInline;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CaptureChangedOrderItemStatus",
        description = "in case of order item status change capture the details and " +
                "forward  to next webhook event {" + PROP_EVENT_NAME + "} else call to no matching event" +
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
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "no matching event name"
)
@Slf4j
public class CaptureChangedOrderItemStatus extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME, PROP_NO_MATCHING_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);

        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderItems(orderId);
        ArrayList<OrderItemStatus> differItemList = null;
        differItemList = findDifferenceInOrderItemStatus(orderData, context);
        if (CollectionUtils.isNotEmpty(differItemList)) {
            Map<String, Object> attributes = createAttributeMap(differItemList, orderData,orderId);
            attributes.putAll(context.getEvent().getAttributes());
            context.addLog("targetAttribute:::" + attributes);
            EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
        } else {
            context.addLog("no found difference:::");
            forwardInline(context, noMatchEventName);
        }
    }
    private ArrayList<OrderItemStatus> findDifferenceInOrderItemStatus(GetOrderByIdQuery.OrderById orderData, ContextWrapper context) {
        ArrayList<OrderItemStatus> differItemList = new ArrayList<>();
        if (orderData != null && orderData.orderItems() != null
                && CollectionUtils.isNotEmpty(orderData.orderItems().orderItemEdge())
        ) {
            for (GetOrderByIdQuery.OrderItemEdge itemEdge : orderData.orderItems().orderItemEdge()) {
                Optional<GetOrderByIdQuery.OrderItemAttribute> orderLineAtt = itemEdge.orderItemNode().orderItemAttributes().stream().filter(
                        itemAttr -> itemAttr.name().equalsIgnoreCase(ORDER_LINE_STATUS)).findFirst();
                Optional<GetOrderByIdQuery.OrderItemAttribute> orderLineAtt1 = itemEdge.orderItemNode().orderItemAttributes().stream().filter(
                        itemAttr -> itemAttr.name().equalsIgnoreCase(OLD_LINE_STATUS)).findFirst();
                if (orderLineAtt.isPresent()
                        && orderLineAtt1.isPresent()
                            && (!orderLineAtt.get().value().toString().equalsIgnoreCase(orderLineAtt1.get().value().toString())
                                    && !orderLineAtt.get().value().toString().equalsIgnoreCase(CANCELLED))) {
                    OrderItemStatus orderItem = new OrderItemStatus();
                    orderItem.setOrderItemKey(itemEdge.orderItemNode().ref());
                    orderItem.setLineStatus(orderLineAtt.get().value().toString());
                    Optional<GetOrderByIdQuery.OrderItemAttribute> orderLineAtt2 = itemEdge.orderItemNode().orderItemAttributes().stream().filter(
                            itemAttr -> itemAttr.name().equalsIgnoreCase(PRESCRIPTION_KEY)).findFirst();
                    if (orderLineAtt2.isPresent()) {
                        orderItem.setPrescriptionId(orderLineAtt2.get().value().toString());
                    }
                    orderItem.setExceptionTypeCode("");
                    orderItem.setExceptionResolutionCode("");
                    differItemList.add(orderItem);
                    context.addLog("capture changed order item status for orderItemRef::"+ itemEdge.orderItemNode().ref());
                }
            }
        }
        return differItemList;
    }

    private Map<String, Object> createAttributeMap(ArrayList<OrderItemStatus> differItemList, GetOrderByIdQuery.OrderById orderData,
                                                   String orderId) {
        OrderItemStatusPayload payload = new OrderItemStatusPayload();
        payload.setOrderId(orderId);
        payload.setOrderRef(orderData.ref());
        payload.setUpdateOn(Instant.now().toString());
        payload.setItems(differItemList);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(ORDER_ITEM_STATUS_PAYLOAD, payload);
        return attributes;
    }
}