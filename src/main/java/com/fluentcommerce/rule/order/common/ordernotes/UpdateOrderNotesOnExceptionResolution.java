package com.fluentcommerce.rule.order.common.ordernotes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.FulfilmentExceptionData;
import com.fluentcommerce.dto.fulfillment.FulfilmentItems;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.order.OrderNotes;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.OrderUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
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
 * @author Yamini Kukreja
 */

@RuleInfo(
        name = "UpdateOrderNotesOnExceptionResolution",
        description = "In case of order item cancellation from fulfilment exception response take cancel reason " +
                "and call the event to send order cancellation notes {" + PROP_EVENT_NAME + "}",
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

@EventAttribute(name = FULFILLMENT)
@Slf4j
public class UpdateOrderNotesOnExceptionResolution extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderItems(orderId);
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        FulfilmentExceptionData fulfilmentExceptionResponse = CommonUtils.convertObjectToDto(eventAttributes.get(FULFILMENT),
                new TypeReference<FulfilmentExceptionData>(){});
        String userKey = null != fulfilmentExceptionResponse.getUserKey() ? fulfilmentExceptionResponse.getUserKey() : EMPTY;
        Optional<FulfilmentItems> cancelledLine = fulfilmentExceptionResponse.getItems().stream().filter(lineData ->
                lineData.getStatus().equalsIgnoreCase(ORDER_ITEM_STATUS_CANCEL_PENDING)).findFirst();
        if(cancelledLine.isPresent()){
            String note = cancelledLine.get().getCancelReason();
            Optional<GetOrderByIdQuery.Attribute> cancelledOrderNoteAttribute =
                    orderData.attributes().stream()
                            .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_NOTES))
                            .findFirst();
            Optional<GetOrderByIdQuery.Attribute> orderKeyAttribute =
                    orderData.attributes().stream()
                            .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY))
                            .findFirst();
            boolean itemStatusChanged = OrderUtils.isItemStatusChanged(orderData,cancelledLine.get().getRef());
            if (null != note && itemStatusChanged && orderKeyAttribute.isPresent() && null != orderKeyAttribute.get().value().toString()) {
                OrderNotes orderNote = OrderUtils.checkAndUpdateOrderNotesAttribute(cancelledOrderNoteAttribute, note, userKey, orderId, context);
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
