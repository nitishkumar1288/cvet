package com.fluentcommerce.rule.order.fulfilmentshipmentresponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.Line;
import com.fluentcommerce.dto.inventory.Items;
import com.fluentcommerce.dto.inventory.ReservationItemsInformation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.product.KitItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.OperationType;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.exceptions.RubixException;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author nitishKumar
 */

@RuleInfo(
        name = "SendEventToUpdateInventoryQuantityAfterShipmentResponse",
        description = "Send an event {" + PROP_EVENT_NAME + "} to inventory catalogue with ref {"
                + PROP_INVENTORY_CATALOGUE_REF + "} "
                + "with retailer id {" + PROP_RETAILER_ID + "} (default: event.retailerId) "
                + "to {" + PROP_OPERATION_NAME + "}  the fulfilment items quantity. ",
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
@ParamString(name = PROP_OPERATION_NAME, description = "Operation type RESERVE/UNRESERVE or RESET_RESERVE")
@ParamString(name = PROP_EVENT_NAME, description = "The name of event to be triggered")
@ParamString(name = PROP_RETAILER_ID, description = "The retailer id")
@Slf4j
public class SendEventToUpdateInventoryQuantityAfterShipmentResponse extends BaseRule {
    static final String ERROR_MESSAGE_OPERATION_TYPE_NOT_SUPPORTED = "Operation Type is not supported. Supported types are RESERVE/UNRESERVE or RESET_RESERVE.";

    @Override
    public void run(ContextWrapper context) {

        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME, PROP_OPERATION_NAME, PROP_INVENTORY_CATALOGUE_REF);
        Event incomingEvent = context.getEvent();
        String responseEvent = context.getProp(PROP_EVENT_NAME);
        String operationType = context.getProp(PROP_OPERATION_NAME);
        String inventoryCatalogueRef = context.getProp(PROP_INVENTORY_CATALOGUE_REF);
        String retailerId = StringUtils.defaultIfEmpty(context.getProp(PROP_RETAILER_ID), incomingEvent.getRetailerId());
        String orderId = incomingEvent.getEntityId();
        List<Line> itemRefList = null;
        itemRefList = getItemRefList(context, operationType, itemRefList);

        context.addLog("itemRefList::" + itemRefList);
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderItems(orderId);

        List<ReservationItemsInformation> reservationItemsInformationList = new ArrayList<>();
        for (Line lineData : itemRefList) {

            Optional<GetOrderByIdQuery.OrderItemEdge> optionalOrderItem = orderData.orderItems().orderItemEdge().stream()
                    .filter(orderItem -> (orderItem.orderItemNode().ref().equalsIgnoreCase(lineData.getRef()))).findFirst();

            if (optionalOrderItem.isPresent()) {
                GetOrderByIdQuery.OrderItemEdge itemData = optionalOrderItem.get();
                try {
                    Optional<GetOrderByIdQuery.VariantProductAttribute> optionalKitProduct =
                            itemData.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                                    variantProductAttribute -> (variantProductAttribute.name().equalsIgnoreCase(IS_KIT) &&
                                            variantProductAttribute.value().toString().equalsIgnoreCase(YES_VALUE))
                            ).findFirst();

                    constructData(context, reservationItemsInformationList, lineData, itemData, optionalKitProduct);
                } catch (Exception ex) {
                    context.addLog("Exception Occurred when constructing item information ");
                    return;
                }
            }
        }

        if (CollectionUtils.isNotEmpty(reservationItemsInformationList)) {
            try {
                List<Items> items = Lists.newArrayList();
                Map<String, Object> attributes = new HashMap<>();
                if (OperationType.UNRESERVE.name().equalsIgnoreCase(operationType)) {
                    reservationItemsInformationList.stream().forEach(
                            item -> items.add(Items.builder()
                                    .orderId(incomingEvent.getEntityId())
                                    .orderItemId(item.getOrderItemId())
                                    .skuRef(item.getSkuRef())
                                    .cancelQty(item.getQuantity())
                                    .locationRef(item.getLocationRef())
                                    .build()));
                }

                if (OperationType.RESET_RESERVE.name().equalsIgnoreCase(operationType)) {
                    reservationItemsInformationList.stream().forEach(
                            item -> items.add(Items.builder()
                                    .orderId(incomingEvent.getEntityId())
                                    .orderItemId(item.getOrderItemId())
                                    .skuRef(item.getSkuRef())
                                    .saleQty(-(item.getQuantity() - item.getRejectedQuantity()))
                                    .locationRef(item.getLocationRef())
                                    .build()));
                }

                attributes.put(ITEMS, items);
                Event event = Event.builder()
                        .name(responseEvent)
                        .accountId(incomingEvent.getAccountId())
                        .retailerId(retailerId)
                        .attributes(attributes)
                        .entityRef(inventoryCatalogueRef)
                        .entityType(ENTITY_TYPE_INVENTORY_CATALOGUE)
                        .rootEntityRef(inventoryCatalogueRef)
                        .rootEntityType(ENTITY_TYPE_INVENTORY_CATALOGUE)
                        .entitySubtype(DEFAULT)
                        .scheduledOn(new Date())
                        .build();
                context.action().sendEvent(event);
            } catch (IllegalArgumentException iae) {
                log.error(ERROR_MESSAGE_OPERATION_TYPE_NOT_SUPPORTED + " : " + operationType);
                throw new RubixException(500, ERROR_MESSAGE_OPERATION_TYPE_NOT_SUPPORTED + " : " + operationType);
            }
        }
    }

    private void constructData(ContextWrapper context, List<ReservationItemsInformation> reservationItemsInformationList, Line lineData, GetOrderByIdQuery.OrderItemEdge itemData, Optional<GetOrderByIdQuery.VariantProductAttribute> optionalKitProduct) throws IOException {
        if (optionalKitProduct.isPresent()) {

            // Kit product is present
            context.addLog("Order item id : " + itemData.orderItemNode().id() + " is a kit product");

            Optional<Object> optionalKitProductChildInfo =
                    itemData.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                            variantProductAttribute -> variantProductAttribute.name().equalsIgnoreCase(KIT_PRODUCT)
                    ).map(GetOrderByIdQuery.VariantProductAttribute::value).findAny();

            if (optionalKitProductChildInfo.isPresent()) {

                List<KitItem> itemList = null;
                ObjectMapper mapper = new ObjectMapper();
                itemList = mapper.readValue(optionalKitProductChildInfo.get().toString(), new TypeReference<List<KitItem>>() {
                });

                // change the required quantity based on the number of kit product requested
                for (KitItem kitItem : itemList) {
                    ReservationItemsInformation reservationItemsInformation = new ReservationItemsInformation();
                    reservationItemsInformation.setOrderItemId(itemData.orderItemNode().id());
                    reservationItemsInformation.setSkuRef(kitItem.getSku());
                    reservationItemsInformation.setQuantity(kitItem.getProductQty() * lineData.getRequestedQuantity());
                    reservationItemsInformation.setLocationRef(lineData.getLocationRef());
                    reservationItemsInformation.setRejectedQuantity(lineData.getRejectedQuantity());
                    reservationItemsInformationList.add(reservationItemsInformation);
                }
            } else {
                context.addLog("Kit product does not have child information");
            }
        } else {
            ReservationItemsInformation reservationItemsInformation = new ReservationItemsInformation();
            reservationItemsInformation.setOrderItemId(itemData.orderItemNode().id());
            reservationItemsInformation.setSkuRef(itemData.orderItemNode().product().asVariantProduct().ref());
            reservationItemsInformation.setQuantity(lineData.getRequestedQuantity());
            reservationItemsInformation.setLocationRef(lineData.getLocationRef());
            reservationItemsInformation.setRejectedQuantity(lineData.getRejectedQuantity());
            reservationItemsInformationList.add(reservationItemsInformation);
        }
    }

    private List<Line> getItemRefList(ContextWrapper context, String operationType, List<Line> itemRefList) {
        if (operationType.equalsIgnoreCase(RESET_RESERVE)) {
            itemRefList = context.getEvent().getAttributeList(SHIP_CONFIRMED_ITEM_REF_LIST, Line.class);
        } else if (operationType.equalsIgnoreCase(UNRESERVE)) {
            itemRefList = context.getEvent().getAttributeList(SHIP_CANCELLED_ITEM_REF_LIST, Line.class);
        }
        return itemRefList;
    }
}
