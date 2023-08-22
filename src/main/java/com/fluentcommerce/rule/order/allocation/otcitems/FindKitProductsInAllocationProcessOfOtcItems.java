package com.fluentcommerce.rule.order.allocation.otcitems;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.OtcProductsDto;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.product.KitItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.exceptions.RubixException;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "FindKitProductsInAllocationProcessOfOtcItems",
        description = "Find kit products are involved in the allocation process and send event {" + PROP_EVENT_NAME + "}" ,
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
        description = "Event Name"
)

@EventAttribute(name = EVENT_OTC_ORDER_ITEMS_ALLOCATION)
@Slf4j
public class FindKitProductsInAllocationProcessOfOtcItems extends BaseRule {

    static final String ERROR_MESSAGE_OPERATION_NOT_SUPPORTED = "Operation not supported.";
    @Override
    public void run(ContextWrapper context) {

        String eventName = context.getProp(PROP_EVENT_NAME);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<OtcProductsDto> otcProductsDtoList = (List<OtcProductsDto>) eventAttributes.get(EVENT_OTC_ORDER_ITEMS_ALLOCATION);

        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderInfo = orderService.getOrderWithOrderItems(context.getEvent().getEntityId());

        List<String> productRefsOfOtcItems = new ArrayList<>();
        for(GetOrderByIdQuery.OrderItemEdge orderItem : orderInfo.orderItems().orderItemEdge()) {
            Optional<GetOrderByIdQuery.VariantProductAttribute> optionalKitProduct =
                    orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                            variantProductAttribute -> (variantProductAttribute.name().equalsIgnoreCase(IS_KIT) &&
                                    variantProductAttribute.value().toString().equalsIgnoreCase(YES_VALUE))
                    ).findAny();

            if (optionalKitProduct.isPresent()) {

                // Kit product is present
                context.addLog("Order item id : " + orderItem.orderItemNode().id() + " is a kit product");

                Optional<OtcProductsDto> optionalOtcProductsDto =
                        otcProductsDtoList.stream().filter(
                                otcProductsDto -> otcProductsDto.getOrderItemId().equalsIgnoreCase(orderItem.orderItemNode().id())
                        ).findFirst();

                getKitProductInformation(
                        context,
                        productRefsOfOtcItems,
                        orderItem,
                        optionalOtcProductsDto);
            }
            else{
                // add the item information to the products involved list
                Optional<OtcProductsDto> optionalOtcProductsDto =
                        otcProductsDtoList.stream().filter(
                                otcProductsDto -> otcProductsDto.getOrderItemId().equalsIgnoreCase(orderItem.orderItemNode().id())
                        ).findFirst();

                if(optionalOtcProductsDto.isPresent()){
                    productRefsOfOtcItems.add(orderItem.orderItemNode().product().asVariantProduct().ref());
                }
            }
        }

        eventAttributes.put(PRODUCT_REF_LIST_OTC_ITEMS,productRefsOfOtcItems);

        EventUtils.forwardInline(
                context,
                eventName
        );
    }

    private static void getKitProductInformation(
            ContextWrapper context,
            List<String> productRefsOfOtcItems,
            GetOrderByIdQuery.OrderItemEdge orderItem,
            Optional<OtcProductsDto> optionalOtcProductsDto) {
        if (optionalOtcProductsDto.isPresent()) {
            context.addLog("kit is part of the otc item allocation");

            Optional<Object> optionalKitProductChildInfo =
                    orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                            variantProductAttribute -> variantProductAttribute.name().equalsIgnoreCase(KIT_PRODUCT)
                    ).map(GetOrderByIdQuery.VariantProductAttribute::value).findAny();

            if (optionalKitProductChildInfo.isPresent()) {
                // Mark the product as a kit product
                OtcProductsDto otcProductsDto = optionalOtcProductsDto.get();
                otcProductsDto.setKitProduct(true);

                List<KitItem> itemList = null;
                ObjectMapper mapper = new ObjectMapper();
                try {
                    itemList = mapper.readValue(optionalKitProductChildInfo.get().toString(), new TypeReference<List<KitItem>>() {
                    });

                    // change the required quantity based on the number of kit product requested
                    for (KitItem kitItem : itemList) {
                        kitItem.setProductQty(kitItem.getProductQty() * orderItem.orderItemNode().quantity());

                        // add the item information to the products involved list
                        productRefsOfOtcItems.add(kitItem.getSku());
                    }
                    otcProductsDto.setKitItemList(itemList);

                } catch (IOException exception) {
                    context.addLog("Exception Occurred when mapping product information " + exception.getMessage());
                    throw new RubixException(500, ERROR_MESSAGE_OPERATION_NOT_SUPPORTED);
                }
            } else {
                context.addLog("Kit product is missing the Array structure of child");
            }
        }
    }
}