package com.fluentcommerce.rule.order.cancellationfromconsole;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.inventory.Items;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.product.KitItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Yamini Kukreja
 */

@RuleInfo(
        name = "SendEventToUpdateInventoryQuantityForOrderCancel",
        description = "Send an event {" + PROP_EVENT_NAME + "} to inventory catalogue with ref {"
                + PROP_INVENTORY_CATALOGUE_REF + "} to un-reserve the fulfilment items quantity." +
                " which are not in status {" + LINE_STATUS + "}.",
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

@ParamString(name = PROP_INVENTORY_CATALOGUE_REF, description = "The ref of Inventory catalogue")
@ParamString(name = PROP_EVENT_NAME, description = "The name of event to be triggered")
@ParamString(name = LINE_STATUS, description = "Line item status")
@Slf4j
public class SendEventToUpdateInventoryQuantityForOrderCancel extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event incomingEvent = context.getEvent();
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME, PROP_INVENTORY_CATALOGUE_REF);
        String responseEvent = context.getProp(PROP_EVENT_NAME);
        String inventoryCatalogueRef = context.getProp(PROP_INVENTORY_CATALOGUE_REF);
        String lineStatus = context.getProp(LINE_STATUS);

        List<Items> items = Lists.newArrayList();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(incomingEvent.getEntityId());

        for (GetOrderByIdQuery.OrderItemEdge itemEdge : order.orderItems().orderItemEdge()) {
            Optional<GetOrderByIdQuery.OrderItemAttribute> orderLineAtt = itemEdge.orderItemNode().orderItemAttributes().stream().filter(
                    itemAttr -> itemAttr.name().equalsIgnoreCase(ORDER_LINE_STATUS)).findFirst();
            if (orderLineAtt.isPresent()) {
                if (!orderLineAtt.get().value().toString().equalsIgnoreCase(lineStatus)) {
                    Optional<GetOrderByIdQuery.OrderItemAttribute> defaultLocation = itemEdge.orderItemNode().orderItemAttributes()
                            .stream().filter(itemAttr -> itemAttr.name().equalsIgnoreCase(DEFAULT_SELECTED_LOCATION)).findFirst();
                    Optional<GetOrderByIdQuery.OrderItemAttribute> selectedLocation = itemEdge.orderItemNode().orderItemAttributes()
                            .stream().filter(itemAttr -> itemAttr.name().equalsIgnoreCase(SELECTED_LOCATION)).findFirst();
                    String facilityRef = "";
                    facilityRef = getFacilityRef(defaultLocation, selectedLocation, facilityRef);

                    if (!facilityRef.isEmpty()) {
                        Optional<GetOrderByIdQuery.VariantProductAttribute> optionalKitProduct =
                                itemEdge.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                                        variantProductAttribute -> (variantProductAttribute.name().equalsIgnoreCase(IS_KIT) &&
                                                variantProductAttribute.value().toString().equalsIgnoreCase(YES_VALUE))
                                ).findAny();
                        constructData(incomingEvent, items, itemEdge, facilityRef, optionalKitProduct);

                    }
                }

                if (CollectionUtils.isNotEmpty(items)) {
                    Map<String, Object> attributes = new HashMap<>();
                    attributes.put(ITEMS, items);

                    Event event = Event.builder()
                            .name(responseEvent)
                            .accountId(incomingEvent.getAccountId())
                            .retailerId(incomingEvent.getRetailerId())
                            .attributes(attributes)
                            .entityRef(inventoryCatalogueRef)
                            .entityType(ENTITY_TYPE_INVENTORY_CATALOGUE)
                            .rootEntityRef(inventoryCatalogueRef)
                            .rootEntityType(ENTITY_TYPE_INVENTORY_CATALOGUE)
                            .entitySubtype(DEFAULT)
                            .scheduledOn(new Date())
                            .build();
                    context.action().sendEvent(event);
                }

            }
        }
    }

    private String getFacilityRef(Optional<GetOrderByIdQuery.OrderItemAttribute> defaultLocation, Optional<GetOrderByIdQuery.OrderItemAttribute> selectedLocation, String facilityRef) {
        if (defaultLocation.isPresent()) {
            facilityRef = defaultLocation.get().value().toString();
        } else if (selectedLocation.isPresent()) {
            facilityRef = selectedLocation.get().value().toString();
        }
        return facilityRef;
    }

    private void constructData(Event incomingEvent, List<Items> items, GetOrderByIdQuery.OrderItemEdge itemEdge, String facilityRef, Optional<GetOrderByIdQuery.VariantProductAttribute> optionalKitProduct) {
        if (optionalKitProduct.isPresent()) {
            Optional<Object> optionalKitItemInfo =
                    itemEdge.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                            variantProductAttribute -> variantProductAttribute.name().equalsIgnoreCase(KIT_PRODUCT)
                    ).map(GetOrderByIdQuery.VariantProductAttribute::value).findAny();
            if (optionalKitItemInfo.isPresent()) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<KitItem> itemList = mapper.readValue(optionalKitItemInfo.get().toString(),
                            new TypeReference<List<KitItem>>() {
                            });
                    for (KitItem kitItem : itemList) {
                        items.add(Items.builder()
                                .orderId(incomingEvent.getEntityId())
                                .orderItemId(itemEdge.orderItemNode().id())
                                .skuRef(kitItem.getSku())
                                .cancelQty(kitItem.getProductQty() * itemEdge.orderItemNode().quantity())
                                .locationRef(facilityRef)
                                .build());
                    }
                } catch (IOException e) {
                    log.info("exception " + e);
                }
            }
        } else {
            items.add(Items.builder()
                    .orderId(incomingEvent.getEntityId())
                    .orderItemId(itemEdge.orderItemNode().id())
                    .skuRef(itemEdge.orderItemNode().product().asVariantProduct().ref())
                    .cancelQty(itemEdge.orderItemNode().quantity())
                    .locationRef(facilityRef)
                    .build());
        }
    }
}

