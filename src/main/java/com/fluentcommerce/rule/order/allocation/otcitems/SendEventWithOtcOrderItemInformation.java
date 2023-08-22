package com.fluentcommerce.rule.order.allocation.otcitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.OtcProductsDto;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */

@RuleInfo(
        name = "SendEventWithOtcOrderItemInformation",
        description = "For order line items in status {" + PROP_ORDER_LINE_STATUS + "} check it matches with the otc item information {" + OTC_ITEM  + "} and send event {" + PROP_EVENT_NAME + "} else send event {"+PROP_NO_MATCHING_EVENT_NAME+"} ",
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
        name = OTC_ITEM,
        description = "OTC item"
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
public class SendEventWithOtcOrderItemInformation extends BaseRule {

    private static final String CLASS_NAME = SendEventWithOtcOrderItemInformation.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName =  context.getProp(PROP_NO_MATCHING_EVENT_NAME);
        String orderLineStatus = context.getProp(PROP_ORDER_LINE_STATUS);

        String orderId = context.getEvent().getEntityId();

        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderById = orderService.getOrderWithOrderItems(orderId);

        // Construct the item allocation dto based on the criteria's
        List<OtcProductsDto> otcProductsDtoList = new ArrayList<>();

        // Iterate all the order items
        for(GetOrderByIdQuery.OrderItemEdge orderItem :  orderById.orderItems().orderItemEdge()) {

            Optional<Object> emptyVendorName =
                    orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                            variantProductAttribute -> (variantProductAttribute.name().equalsIgnoreCase(FULFILMENT_VENDOR_SHORT_NAME)
                                    && StringUtils.isEmpty(variantProductAttribute.value().toString()))
                    ).map(GetOrderByIdQuery.VariantProductAttribute::value).findFirst();

            Optional<Object> rxProduct =
                    orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                            variantProductAttribute -> (variantProductAttribute.name().equalsIgnoreCase(APPROVAL_TYPE)
                                    && (variantProductAttribute.value().toString().equalsIgnoreCase(RX)))
                    ).map(GetOrderByIdQuery.VariantProductAttribute::value).findFirst();

            // if the vendor name is empty and if it is not a rx product then proceed further
            if(emptyVendorName.isPresent() && (!rxProduct.isPresent())) {
                    Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemAttribute = orderItem.orderItemNode().orderItemAttributes().stream().filter(
                            orderItemAttribute -> ((orderItemAttribute.name().equalsIgnoreCase(ORDER_LINE_STATUS)) && (orderItemAttribute.value().toString().equalsIgnoreCase(orderLineStatus)))
                    ).findFirst();

                    // if the order item matches the line status then proceed further
                    if (optionalOrderItemAttribute.isPresent()) {

                        context.addLog(String.valueOf(System.currentTimeMillis()));
                        context.addLog("line status is matching , order item id : " + orderItem.orderItemNode().id() + " and order item status value : " + optionalOrderItemAttribute.get().value());

                        // Construct the otc DTO
                        OtcProductsDto otcProductsDto = new OtcProductsDto();
                        otcProductsDto.setProductRef(orderItem.orderItemNode().product().asVariantProduct().ref());
                        otcProductsDto.setOrderItemId(orderItem.orderItemNode().id());
                        otcProductsDto.setRequestedQuantity(orderItem.orderItemNode().quantity());
                        otcProductsDto.setDefaultLocationSelected(false);
                        otcProductsDto.setKitProduct(false);

                        otcProductsDtoList.add(otcProductsDto);

                    }
            }
        }

        if(CollectionUtils.isNotEmpty(otcProductsDtoList)){
            Map<String, Object> attributes = new HashMap<>();
            attributes.putAll(context.getEvent().getAttributes());
            attributes.put(EVENT_OTC_ORDER_ITEMS_ALLOCATION, otcProductsDtoList);

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