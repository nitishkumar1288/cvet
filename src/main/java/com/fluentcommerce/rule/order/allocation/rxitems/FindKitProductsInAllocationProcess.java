package com.fluentcommerce.rule.order.allocation.rxitems;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.OrderItemDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "FindKitProductsInAllocationProcess",
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


@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@Slf4j
public class FindKitProductsInAllocationProcess extends BaseRule {

    static final String ERROR_MESSAGE_OPERATION_NOT_SUPPORTED = "Operation not supported.";
    @Override
    public void run(ContextWrapper context) {

        String eventName = context.getProp(PROP_EVENT_NAME);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);

        // Consider all the products to identify the kit products (including pre-assigned products)

        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderInfo = orderService.getOrderWithOrderItems(context.getEvent().getEntityId());

        for(GetOrderByIdQuery.OrderItemEdge orderItem : orderInfo.orderItems().orderItemEdge()){

            Optional<GetOrderByIdQuery.VariantProductAttribute> optionalKitProduct =
                    orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                            variantProductAttribute -> (variantProductAttribute.name().equalsIgnoreCase(IS_KIT) &&
                                    variantProductAttribute.value().toString().equalsIgnoreCase(YES_VALUE))
                    ).findAny();

            if(optionalKitProduct.isPresent()){

                // Kit product is present
                context.addLog("Order item id : " + orderItem.orderItemNode().id() + " is a kit product");

                getKitProductsInformation(
                        context,
                        pharmacyAllocationForOrderItemsList,
                        orderItem);
            }
        }

        EventUtils.forwardInline(
                context,
                eventName
        );
    }

    private static void getKitProductsInformation(
            ContextWrapper context,
            List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList,
            GetOrderByIdQuery.OrderItemEdge orderItem) {

        Optional<Object> optionalKitItemInfo =
                orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                        variantProductAttribute -> variantProductAttribute.name().equalsIgnoreCase(KIT_PRODUCT)
                ).map(GetOrderByIdQuery.VariantProductAttribute::value).findAny();


        if(optionalKitItemInfo.isPresent()) {
            for (PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems : pharmacyAllocationForOrderItemsList) {
                Optional<OrderItemDto> optionalAllocationItem =
                        pharmacyAllocationForOrderItems.getOrderItemDtoList().stream().filter(
                                orderItemDto -> orderItemDto.getId().equalsIgnoreCase(orderItem.orderItemNode().id())
                        ).findFirst();

                if (optionalAllocationItem.isPresent()) {
                    optionalAllocationItem.get().setKitProduct(true);

                    List<KitItem> itemList = null;
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        itemList = mapper.readValue(optionalKitItemInfo.get().toString(), new TypeReference<List<KitItem>>() {
                        });

                        // change the required quantity based on the number of kit product requested
                        for (KitItem kitItem : itemList) {
                            kitItem.setProductQty(kitItem.getProductQty() * orderItem.orderItemNode().quantity());
                        }
                        optionalAllocationItem.get().setKitItemList(itemList);

                    } catch (IOException exception) {
                        context.addLog("Exception Occurred when mapping product information " + exception.getMessage());
                        throw new RubixException(500, ERROR_MESSAGE_OPERATION_NOT_SUPPORTED);
                    }
                }
            }
        }
    }
}