package com.fluentcommerce.rule.order.pv1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.product.KitItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */

@RuleInfo(
        name = "UpdateKitItemInformationForPV1Items",
        description = "Update kit item information for PV1 items and Send event {" + PROP_EVENT_NAME + "}.",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = ENTITY_TYPE_INVENTORY_CATALOGUE,
                        entitySubtype = DEFAULT
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
@Slf4j
public class UpdateKitItemInformationForPV1Items extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<Map<String, String>> reservedItems =  (List<Map<String, String>>) eventAttributes.get(RESERVE);
        List<Map<String, String>> unReservedItems =  (List<Map<String, String>>) eventAttributes.get(UNRESERVE);

        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderInfo = orderService.getOrderWithOrderItems(context.getEvent().getEntityId());

        List<Map<String, String>> modifiedReservedItemMapList = new ArrayList<>();
        List<Map<String, String>> modifiedUnReservedItemMapList = new ArrayList<>();
        for(GetOrderByIdQuery.OrderItemEdge orderItem : orderInfo.orderItems().orderItemEdge()) {

            if(CollectionUtils.isNotEmpty(reservedItems)){
                Optional<Map<String, String>> optionalReservedMatchingItem =
                        reservedItems.stream().filter(
                                map -> map.get(ORDER_ITEM_ID).equalsIgnoreCase(orderItem.orderItemNode().id())
                        ).findFirst();

                // apply the criteria only for the matching items
                // construct reserved item map
                optionalReservedMatchingItem.ifPresent(stringStringMap -> constructItemInformation(
                        context,
                        modifiedReservedItemMapList,
                        orderItem,
                        stringStringMap));
            }

            if(CollectionUtils.isNotEmpty(unReservedItems)){
                Optional<Map<String, String>> optionalUnReservedMatchingItem =
                        unReservedItems.stream().filter(
                                map -> map.get(ORDER_ITEM_ID).equalsIgnoreCase(orderItem.orderItemNode().id())
                        ).findFirst();

                // construct the unreserved item map
                optionalUnReservedMatchingItem.ifPresent(stringStringMap -> constructItemInformation(
                        context,
                        modifiedUnReservedItemMapList,
                        orderItem,
                        stringStringMap));
            }
        }

        eventAttributes.put(RESERVE,modifiedReservedItemMapList);
        eventAttributes.put(UNRESERVE,modifiedUnReservedItemMapList);

        EventUtils.forwardEventInlineWithAttributes(
                context,
                eventName,
                eventAttributes);
    }

    private static void constructItemInformation(
            ContextWrapper context,
            List<Map<String, String>> itemMapList,
            GetOrderByIdQuery.OrderItemEdge orderItem,
            Map<String, String> matchingItem) {

        Optional<GetOrderByIdQuery.VariantProductAttribute> optionalKitProduct =
                orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                        variantProductAttribute -> (variantProductAttribute.name().equalsIgnoreCase(IS_KIT) &&
                                variantProductAttribute.value().toString().equalsIgnoreCase(YES_VALUE))
                ).findAny();
        if(optionalKitProduct.isPresent()) {
            Optional<Object> optionalKitItemInfo =
                    orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                            variantProductAttribute -> variantProductAttribute.name().equalsIgnoreCase(KIT_PRODUCT)
                    ).map(GetOrderByIdQuery.VariantProductAttribute::value).findAny();


            if (optionalKitItemInfo.isPresent()) {

                List<KitItem> itemList = null;
                ObjectMapper mapper = new ObjectMapper();
                try {
                    itemList = mapper.readValue(optionalKitItemInfo.get().toString(), new TypeReference<List<KitItem>>() {
                    });

                    // change the required quantity based on the number of kit product requested
                    for (KitItem kitItem : itemList) {
                        Map<String,String> modifiedItemMap = new HashMap<>();

                        modifiedItemMap.put(SKU_REF, kitItem.getSku());
                        modifiedItemMap.put(ORDER_ITEM_ID, orderItem.orderItemNode().id());
                        modifiedItemMap.put(QUANTITY, String.valueOf(kitItem.getProductQty() * Integer.parseInt(matchingItem.get(QUANTITY))));
                        modifiedItemMap.put(LOCATION_REF, matchingItem.get(LOCATION_REF));

                        // adding it to the list
                        itemMapList.add(modifiedItemMap);
                    }

                } catch (IOException exception) {
                    context.addLog("Exception Occurred when mapping product information " + exception.getMessage());
                }
            }else{
                context.addLog("kit product does not have kit item information");
            }
        }
        else{
            // if it is not a kit product add it directly to the list
            itemMapList.add(matchingItem);
        }
    }
}
