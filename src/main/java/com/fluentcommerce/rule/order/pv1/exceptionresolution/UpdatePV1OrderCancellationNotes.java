package com.fluentcommerce.rule.order.pv1.exceptionresolution;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.order.OrderNotes;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
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
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdatePV1OrderCancellationNotes",
        description = "in case of order/order item cancellation from PV1 response fetch the cancel reason and " +
                "update the order attribute with cancellation notes and " +
                "forward to event {" + PROP_EVENT_NAME + "}",
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
@EventAttribute(name = ITEMS)
@Slf4j
public class UpdatePV1OrderCancellationNotes extends BaseRule {
    private static final String CLASS_NAME = UpdatePV1OrderCancellationNotes.class.getSimpleName();

    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        String orderId = context.getEvent().getEntityId();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        Map<String, Object> eventAttributes = context.getEvent().getAttributes();
        List<PV1Items> cancelledItems = CommonUtils.convertObjectToDto(eventAttributes.get(ITEMS), new TypeReference<List<PV1Items>>() {});
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderItems(orderId);
        Optional<PV1Items> item = cancelledItems.stream().filter(itemData ->
                itemData.getStatus().equalsIgnoreCase(ORDER_ITEM_STATUS_CANCEL_PENDING) && null != itemData.getCancelReason()).findFirst();
        if (item.isPresent()) {
            Optional<GetOrderByIdQuery.Attribute> cancelledOrderNoteAttribute =
                    orderData.attributes().stream()
                            .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_NOTES))
                            .findFirst();
            Optional<GetOrderByIdQuery.Attribute> orderKeyAttribute =
                    orderData.attributes().stream()
                            .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY))
                            .findFirst();
            boolean itemStatusChanged = OrderUtils.isItemStatusChanged(orderData,item.get().getRef());
            String note = item.get().getCancelReason();
            if (null != note && itemStatusChanged && orderKeyAttribute.isPresent() && null != orderKeyAttribute.get().value().toString()) {
                OrderNotes orderNote = OrderUtils.checkAndUpdateOrderNotesAttribute(cancelledOrderNoteAttribute, note, EMPTY, orderId, context);
                if (null != orderNote) {
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
    }
}

