package com.fluentcommerce.rule.order.rxservice.approval;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.rx.Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "CheckOrderItemStatusMatchingTheList",
        description = "Check order item status matching the status list  {"+ PROP_STATUS_LIST +"}  " +
                "and send event {" + PROP_EVENT_NAME + "}",
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
        name = PROP_STATUS_LIST,
        description = "Status list to be compared with"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "Event name to be triggered"
)

@EventAttribute(name = ITEMS)
@Slf4j
public class CheckOrderItemStatusMatchingTheList extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        List<String> orderItemStatusList = context.getPropList(PROP_STATUS_LIST,String.class);
        String eventName = context.getProp(PROP_EVENT_NAME);

        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(context.getEvent().getEntityId());

        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<Items> rxServiceRespItems = CommonUtils.convertObjectToDto(eventAttributes.get(ITEMS),new TypeReference<List<Items>>(){});

        List<Items> modifiedRxServiceRespItems = new ArrayList<>();
        for(Items rxItem : rxServiceRespItems ){
            Optional<GetOrderByIdQuery.OrderItemEdge> optionalOrderItem =
                    order.orderItems().orderItemEdge().stream().filter(
                            orderItemEdge -> orderItemEdge.orderItemNode().ref().equalsIgnoreCase(rxItem.getLineNumber())
                    ).findFirst();

            if(optionalOrderItem.isPresent()){
                Optional<GetOrderByIdQuery.OrderItemAttribute> matchingStatusItem =
                        optionalOrderItem.get().orderItemNode().orderItemAttributes().stream().filter(
                                orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(ORDER_LINE_STATUS) && orderItemStatusList.contains(orderItemAttribute.value().toString())).findFirst();

                // if it is matching the statusList add it to the list
                if(matchingStatusItem.isPresent()){
                    modifiedRxServiceRespItems.add(rxItem);
                }else{
                    context.addLog("un matching line number from response " + rxItem.getLineNumber());
                }
            }else{
                context.addLog("Rx Response line number " + rxItem.getLineNumber() + " is not matching with the order items");
            }
        }

        if(CollectionUtils.isNotEmpty(modifiedRxServiceRespItems)) {

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String,Object> modifiedEventAttributes = new HashMap<>();
                modifiedEventAttributes.putAll(eventAttributes);

                modifiedEventAttributes.put(ITEMS, objectMapper.readValue(
                        objectMapper.writeValueAsString(modifiedRxServiceRespItems), ArrayList.class));

                EventUtils.forwardEventInlineWithAttributes(
                        context,
                        eventName,
                        modifiedEventAttributes);
            } catch (Exception e) {
                context.addLog("Exception while mapping the data to arraylist");
            }
        }else{
            context.addLog("All items in the Response are not matching with the status list");
        }
    }
}
