package com.fluentcommerce.rule.order.common.ordernotes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.order.OrderNotes;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.OrderUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.ENTITY_TYPE_ORDER;
import static com.fluentcommerce.util.Constants.ORDER_NOTES;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateOrderNotes",
        description = "fetch the order note information from the other system and update the order attribute ",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)

@EventAttribute(name = ORDER_NOTES)
@Slf4j
public class UpdateOrderNotes extends BaseRule {
    private static final String CLASS_NAME = UpdateOrderNotes.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String orderId = context.getEvent().getEntityId();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<OrderNotes> orderNotesList = CommonUtils.convertObjectToDto(eventAttributes.get(ORDER_NOTES),new TypeReference<List<OrderNotes>>(){});
        if (CollectionUtils.isNotEmpty(orderNotesList)){
            OrderService orderService = new OrderService(context);
            GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);
            Optional<GetOrderByIdQuery.Attribute> orderNotesAttribute =
                    orderData.attributes().stream()
                            .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_NOTES))
                            .findFirst();
            OrderUtils.upsertOrderNotesAttribute(orderNotesAttribute,orderNotesList,orderId,context);
        }
    }
}