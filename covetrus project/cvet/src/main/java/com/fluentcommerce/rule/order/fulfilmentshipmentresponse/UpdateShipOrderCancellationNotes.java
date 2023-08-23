package com.fluentcommerce.rule.order.fulfilmentshipmentresponse;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.Line;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.order.OrderNotes;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.OrderUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author nitishKumar
 */

@RuleInfo(
        name = "UpdateShipOrderCancellationNotes",
        description = "in case of order or order item cancellation from ship confirmation response" +
                " update the order attribute with cancellation notes " +
                "{" + NOTE_TEXT + "}" + "and forward to event {" + PROP_EVENT_NAME + "}",
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
        name = NOTE_TEXT,
        description = "forward to next Event name"
)

@EventAttribute(name = SHIP_CANCELLED_ITEM_REF_LIST)
@Slf4j
public class UpdateShipOrderCancellationNotes extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME, NOTE_TEXT);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noteText = context.getProp(NOTE_TEXT);
        Event event = context.getEvent();
        List<Line> cancelledItemRefList = context.getEvent().getAttributeList(SHIP_CANCELLED_ITEM_REF_LIST, Line.class);
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(orderId);
        if (Objects.nonNull(order) && Objects.nonNull(cancelledItemRefList)) {
            Optional<GetOrderByIdQuery.OrderItemEdge> optionalOrderItemEdge = order.orderItems().orderItemEdge().stream().filter(
                    orderItem -> orderItem.orderItemNode().ref().equalsIgnoreCase(cancelledItemRefList.get(0).getRef())
            ).findFirst();
            if (optionalOrderItemEdge.isPresent()) {
                Optional<GetOrderByIdQuery.Attribute> orderNoteAttribute =
                        order.attributes().stream()
                                .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_NOTES))
                                .findFirst();
                context.addLog("UpdateShipOrderCancellationNotes::orderNoteAttribute" + orderNoteAttribute);
                boolean isItemStatusChanged = OrderUtils.isItemStatusChanged(order, cancelledItemRefList.get(0).getRef());
                context.addLog("UpdateShipOrderCancellationNotes::isItemStatusChanged" + isItemStatusChanged);
                if (null != noteText && isItemStatusChanged) {
                    OrderNotes orderNote = OrderUtils.checkAndUpdateOrderNotesAttribute(orderNoteAttribute, noteText, EMPTY, orderId, context);
                    context.addLog("UpdateShipOrderCancellationNotes::orderNote" + orderNote);
                    if (null != orderNote) {
                        ArrayList<OrderNotes> orderNoteList = new ArrayList<>();
                        orderNoteList.add(orderNote);
                        Map<String, Object> attributes = new HashMap<>();
                        attributes.put(ORDER_NOTES, orderNoteList);
                        attributes.putAll(context.getEvent().getAttributes());
                        EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
                    }
                }
            }
        }
    }
}
