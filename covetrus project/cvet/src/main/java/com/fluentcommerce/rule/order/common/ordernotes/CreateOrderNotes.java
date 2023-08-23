package com.fluentcommerce.rule.order.common.ordernotes;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.OrderUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;


/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CreateOrderNotes",
        description = "Create Order notes from the event attribute list {" + PROP_EVENT_ATTRIBUTE_LIST + "}",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_EVENT_ATTRIBUTE_LIST,
        description = "Event Attribute list to be updated to order"
)

@EventAttribute(name = USER_KEY)
@EventAttribute(name = NOTE_CONTEXT)
@EventAttribute(name = NOTE)
@Slf4j
public class CreateOrderNotes extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        List<String> eventAttributeList = context.getPropList(PROP_EVENT_ATTRIBUTE_LIST,String.class);
        Event event = context.getEvent();
        if(CollectionUtils.isNotEmpty(eventAttributeList)){
            Map<String,Object> eventAttributeMap = event.getAttributes();
            String userKey = null;
            String noteContext = null;
            String note = null;
            for(String eventAttribute : eventAttributeList){
                userKey = getUserKey(eventAttributeMap, userKey, eventAttribute);
                if(eventAttributeMap.containsKey(eventAttribute) && eventAttribute.equalsIgnoreCase(NOTE_CONTEXT)){
                    noteContext = eventAttributeMap.get(eventAttribute).toString();
                }
                if(eventAttributeMap.containsKey(eventAttribute) && eventAttribute.equalsIgnoreCase(NOTE)){
                    note = eventAttributeMap.get(eventAttribute).toString();
                }
            }
            if (null != note && null != userKey){
                String orderId = event.getEntityId();
                OrderService orderService = new OrderService(context);
                GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);
                Optional<GetOrderByIdQuery.Attribute> orderNoteAttribute =
                        orderData.attributes().stream()
                                .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_NOTES))
                                .findFirst();
                OrderUtils.checkAndUpdateOrderNotesAttributeFromConsole(orderNoteAttribute,note,userKey,orderId,context,noteContext);
            }
        }
    }

    private String getUserKey(Map<String, Object> eventAttributeMap, String userKey, String eventAttribute) {
        if(eventAttributeMap.containsKey(eventAttribute) && eventAttribute.equalsIgnoreCase(USER_KEY)){
            userKey = eventAttributeMap.get(eventAttribute).toString();
        }
        return userKey;
    }
}