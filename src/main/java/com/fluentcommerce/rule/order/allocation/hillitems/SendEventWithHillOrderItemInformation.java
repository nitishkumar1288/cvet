package com.fluentcommerce.rule.order.allocation.hillitems;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.HillProductsDto;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONObject;

import java.util.*;
import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "SendEventWithHillOrderItemInformation",
        description = "For order line items in status {" + PROP_ORDER_LINE_STATUS + "} check it matches with the otc item information {" + HILL_ITEM  + "} and send event {" + PROP_EVENT_NAME + "} else send event {"+PROP_NO_MATCHING_EVENT_NAME+"} ",
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
        name = HILL_ITEM,
        description = "Hill item"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "Event name to be triggered"
)
@ParamString(
        name = PROP_ORDER_LINE_STATUS,
        description = "order line status"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "Event name to be triggered"
)
@Slf4j
public class SendEventWithHillOrderItemInformation extends BaseRule {

    private static final String CLASS_NAME = SendEventWithHillOrderItemInformation.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName =  context.getProp(PROP_NO_MATCHING_EVENT_NAME);
        String orderLineStatus = context.getProp(PROP_ORDER_LINE_STATUS);
        Object hillItem = context.getProp(HILL_ITEM,Object.class);

        String orderId = context.getEvent().getEntityId();

        Map<String, List<String>> hillMap= CommonUtils.convertObjectToDto(hillItem,new TypeReference<Map<String, List<String>>>(){});
        Map.Entry<String, List<String>> hillEntry =  hillMap.entrySet().iterator().next();


        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderById = orderService.getOrderWithOrderItems(orderId);

        // Construct the item allocation dto based on the criteria's
        List<HillProductsDto> hillProductsDtoList = new ArrayList<>();

        // Iterate all the order items
        for(GetOrderByIdQuery.OrderItemEdge orderItem :  orderById.orderItems().orderItemEdge()) {

            Optional<Object> attribute  =
                    orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                            variantProductAttribute -> variantProductAttribute.name().equalsIgnoreCase(hillEntry.getKey())
                    ).map(GetOrderByIdQuery.VariantProductAttribute::value).findFirst();

            if(attribute.isPresent() &&
                    hillEntry.getValue().contains(attribute.get().toString().toUpperCase())) {
                Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemAttribute = orderItem.orderItemNode().orderItemAttributes().stream().filter(
                        orderItemAttribute -> ( (orderItemAttribute.name().equalsIgnoreCase(ORDER_LINE_STATUS)) && (orderItemAttribute.value().toString().equalsIgnoreCase(orderLineStatus)))
                ).findFirst();

                // if the order item matches the line status then proceed further
                if (optionalOrderItemAttribute.isPresent()) {

                    context.addLog(String.valueOf(System.currentTimeMillis()));
                    context.addLog("line status is matching , order item id : " + orderItem.orderItemNode().id() + " and order item status value : " +optionalOrderItemAttribute.get().value());

                    // Construct the hill DTO
                    HillProductsDto hillProductsDto = new HillProductsDto();
                    hillProductsDto.setProductRef(orderItem.orderItemNode().product().asVariantProduct().ref());
                    hillProductsDto.setOrderItemId(orderItem.orderItemNode().id());
                    hillProductsDto.setRequestedQuantity(orderItem.orderItemNode().quantity());
                    hillProductsDto.setDefaultLocationSelected(false);
                    hillProductsDto.setKitProduct(false);

                    hillProductsDtoList.add(hillProductsDto);
                }
            }
        }

        if(CollectionUtils.isNotEmpty(hillProductsDtoList)){
            Map<String, Object> attributes = new HashMap<>();
            attributes.putAll(context.getEvent().getAttributes());

            attributes.put(EVENT_HILL_ORDER_ITEMS_ALLOCATION, hillProductsDtoList);

            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    attributes);
        }else{
            context.addLog("No match event Name has been invoked");
            EventUtils.forwardInline(
                    context,
                    noMatchEventName);
        }
    }
}