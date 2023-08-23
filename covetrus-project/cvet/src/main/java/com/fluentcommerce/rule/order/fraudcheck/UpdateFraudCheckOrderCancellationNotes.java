package com.fluentcommerce.rule.order.fraudcheck;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.order.OrderNotes;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateFraudCheckOrderCancellationNotes",
        description = "in case of order cancellation from fraud check system update the order attribute with cancellation notes " +
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
@Slf4j
public class UpdateFraudCheckOrderCancellationNotes extends BaseRule {
    private static final String CLASS_NAME = UpdateFraudCheckOrderCancellationNotes.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME,NOTE_TEXT);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noteText = context.getProp(NOTE_TEXT);
        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);
        Optional<GetOrderByIdQuery.Attribute> cancelledOrderNoteAttribute =
                orderData.attributes().stream()
                        .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_NOTES))
                        .findFirst();
        Optional<GetOrderByIdQuery.Attribute> orderKeyAttribute =
                orderData.attributes().stream()
                        .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY))
                        .findFirst();
        OrderNotes orderNote = OrderUtils.checkAndUpdateOrderNotesAttribute(cancelledOrderNoteAttribute,noteText,EMPTY,orderId,context);
        if (null != orderNote && orderKeyAttribute.isPresent() && null != orderKeyAttribute.get().value().toString()){
            ArrayList<OrderNotes> orderNoteList = new ArrayList<>();
            orderNoteList.add(orderNote);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put(ORDER_KEY,orderKeyAttribute.get().value().toString());
            attributes.put(ORDER_NOTES, orderNoteList);
            attributes.putAll(context.getEvent().getAttributes());
            EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
        }
    }
}
