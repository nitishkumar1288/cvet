package com.fluentcommerce.rule.inventorycatalogue;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.inventory.InventoryQuantity;
import com.fluentcommerce.dto.inventory.Items;
import com.fluentcommerce.graphql.query.inventory.GetInventoryCatalogueQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.exceptions.PropertyNotFoundException;
import com.fluentretail.rubix.exceptions.RubixException;
import com.fluentretail.rubix.rule.meta.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.rule.inventorycatalogue.SendEventOnInventoryUpdate.PROP_INVENTORY_CANCEL_EVENT;
import static com.fluentcommerce.rule.inventorycatalogue.SendEventOnInventoryUpdate.PROP_INVENTORY_CONFIRM_EVENT;
import static com.fluentcommerce.util.Constants.*;

/**
 * @author Nandhakumar
 */

@RuleInfo(
        name = "SendEventOnInventoryUpdate",
        description = "Send event {" + PROP_INVENTORY_CONFIRM_EVENT + "} if invenory is confirmed, "
                + "or send {" + SendEventOnInventoryUpdate.PROP_INVENTORY_RESERVE_EVENT + "} if inventory is reserved, or {" + PROP_INVENTORY_CANCEL_EVENT + "} "
                + "if inventory is cancelled, to inventory position and include inventory quantities of "
                + "type {" + PROP_INVENTORY_QUANTITY_TYPES + "} in event attribute",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = ENTITY_TYPE_INVENTORY_POSITION
                )
        },
        accepts = {
                @EventInfo(
                        entityType = ENTITY_TYPE_INVENTORY_CATALOGUE)
        }
)
@ParamString(
        name = PROP_INVENTORY_CONFIRM_EVENT,
        description = "The name of event to be triggered if inventory is confirmed"
)
@ParamString(
        name = PROP_INVENTORY_CANCEL_EVENT,
        description = "The name of event to be triggered if inventory is cancelled"
)
@ParamString(
        name = SendEventOnInventoryUpdate.PROP_INVENTORY_RESERVE_EVENT,
        description = "The name of event to be triggered if inventory is reserved"
)
@ParamString(
        name = PROP_INVENTORY_QUANTITY_TYPES,
        description = "Inventory quantity types"
)

@EventAttribute(name = ITEMS)
@Slf4j
public class SendEventOnInventoryUpdate extends BaseRule {

    public static final String PROP_INVENTORY_CONFIRM_EVENT = "inventoryConfirmationEvent";
    public static final String PROP_INVENTORY_CANCEL_EVENT = "inventoryCancelEvent";
    public static final String PROP_INVENTORY_RESERVE_EVENT = "inventoryReserveEvent";

    @Override
    public void run(ContextWrapper context) {
        Event incomingEvent = context.getEvent();
        log.info("[COMMON-GI] - rule: SendEventOnFulfilmentInventoryUpdate, incoming event {} ", incomingEvent.toString());
        // validate rule params
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_INVENTORY_CONFIRM_EVENT, PROP_INVENTORY_CANCEL_EVENT, PROP_INVENTORY_RESERVE_EVENT);

        checkMandatoryData(context);

        // get rule params
        final String confirmEvent = context.getProp(PROP_INVENTORY_CONFIRM_EVENT);
        final String cancelEvent = context.getProp(PROP_INVENTORY_CANCEL_EVENT);
        final String reserveEvent = context.getProp(PROP_INVENTORY_RESERVE_EVENT);
        final List<String> quantityTypes = context.getPropList(PROP_INVENTORY_QUANTITY_TYPES, String.class);

        final String catalogueReference = incomingEvent.getEntityRef();
        final String catalogueType = incomingEvent.getEntitySubtype();

        // get event attributes
        List<Items> itemsList = incomingEvent.getAttributeList(ITEMS, Items.class);

        checkMandatoryData(itemsList);

        for (Items item : itemsList) {
            String skuRef = item.getSkuRef();
            String locationRef = item.getLocationRef();
            String orderId = item.getOrderId();
            String orderItemId = item.getOrderItemId();
            String inventoryPositionRef;

            List<InventoryQuantity> inventoryQuantityList = new ArrayList<>();
            GetInventoryCatalogueQuery queryInventoryPositions = GetInventoryCatalogueQuery.builder()
                    .inventoryCatalogueRef(incomingEvent.getEntityRef())
                    .locationRef(Lists.newArrayList(locationRef))
                    .productRef(Lists.newArrayList(skuRef)).build();
            GetInventoryCatalogueQuery.Data queryInventoryPositionsData = (GetInventoryCatalogueQuery.Data) context.api()
                    .query(queryInventoryPositions);

            boolean isValidInventoryData = true;

            isValidInventoryData = isValidInventoryData(
                    skuRef,
                    locationRef,
                    queryInventoryPositionsData,
                    isValidInventoryData);


            if (isValidInventoryData) {

                GetInventoryCatalogueQuery.Node inventoryPositionsNode = queryInventoryPositionsData.inventoryCatalogue().inventoryPositions().edges().get(0)
                        .node();

                isValidInventoryData = isValidInventoryData(
                        skuRef,
                        locationRef,
                        isValidInventoryData,
                        inventoryPositionsNode);


                if (isValidInventoryData) {
                    inventoryPositionRef = inventoryPositionsNode.ref();

                    // Always add reserved quantity, which will be either created or de-activated
                    inventoryQuantityList.add(InventoryQuantity.builder().quantity(item.getReserveQty()).type(RESERVED).ref(inventoryPositionRef + COLON + RESERVED + COLON + orderId + COLON + orderItemId).build());

                    //build event attribute
                    Map<String, Object> attributesMap = buildEventAttribute(inventoryPositionRef, catalogueType, skuRef,
                            locationRef, inventoryQuantityList);

                    if (item.getReserveQty() != 0 && quantityTypes.contains(RESERVED)) {

                        //Send reserve event
                        sendEventToInventoryPosition(context, incomingEvent, reserveEvent, inventoryPositionRef, catalogueType, catalogueReference, attributesMap);
                    } else if (item.getCancelQty() != 0 && quantityTypes.contains(RESERVED)) {

                        // Send cancel event
                        sendEventToInventoryPosition(context, incomingEvent, cancelEvent, inventoryPositionRef, catalogueType, catalogueReference, attributesMap);
                    } else if (item.getSaleQty() != 0 || item.getCorrectionQty() != 0) {
                        buildInventoryDataForSaleAndCorrect(
                                quantityTypes,
                                item,
                                orderId,
                                orderItemId,
                                inventoryPositionRef,
                                inventoryQuantityList);

                        // Send event for confirmation
                        sendEventToInventoryPosition(context, incomingEvent, confirmEvent, inventoryPositionRef, catalogueType, catalogueReference, attributesMap);
                    }
                }
            }
        }
    }

    private static boolean isValidInventoryData(
            String skuRef,
            String locationRef,
            boolean isValidInventoryData,
            GetInventoryCatalogueQuery.Node inventoryPositionsNode) {
        if (inventoryPositionsNode.ref().isEmpty()) {
            log.warn("[COMMON-GI] - SendEventOnFulfilmentInventoryUpdate - inventory position ref for product ref: {} and locationRef: {} were is invalid",
                    skuRef, locationRef);
            isValidInventoryData = false;
        }
        return isValidInventoryData;
    }

    private static boolean isValidInventoryData(
            String skuRef,
            String locationRef,
            GetInventoryCatalogueQuery.Data queryInventoryPositionsData,
            boolean isValidInventoryData) {
        if (queryInventoryPositionsData == null || queryInventoryPositionsData.inventoryCatalogue() == null
                || (queryInventoryPositionsData.inventoryCatalogue().inventoryPositions() == null)) {
            log.warn("[COMMON-GI] - SendEventOnFulfilmentInventoryUpdate - inventoryPositions with product ref: {} and locationRef: {} were not found",
                    skuRef, locationRef);
            isValidInventoryData = false;
        } else if (queryInventoryPositionsData.inventoryCatalogue().inventoryPositions().edges().size() != 1) {
            log.warn(
                    "[COMMON-GI] - SendEventOnFulfilmentInventoryUpdate - found more than 1 inventoryPositions with product ref: {} and locationRef: {}, count: {}",
                    skuRef, locationRef, queryInventoryPositionsData.inventoryCatalogue().inventoryPositions().edges().size());
            isValidInventoryData = false;
        }
        return isValidInventoryData;
    }

    private static void checkMandatoryData(List<Items> itemsList) {
        if (CollectionUtils.isEmpty(itemsList)) {
            log.error("[COMMON-GI] - SendEventOnFulfilmentInventoryUpdate - No Items found in the input event");
            throw new RubixException(500, "[COMMON-GI] - SendEventOnFulfilmentInventoryUpdate - No Items found in the input event.");
        }
    }

    private static void checkMandatoryData(ContextWrapper context) {
        if (CollectionUtils.isEmpty(context.getPropList(PROP_INVENTORY_QUANTITY_TYPES, String.class))) {
            throw new PropertyNotFoundException(400, PROP_INVENTORY_QUANTITY_TYPES);
        }
    }

    private static void buildInventoryDataForSaleAndCorrect(
            List<String> quantityTypes,
            Items item,
            String orderId,
            String orderItemId,
            String inventoryPositionRef,
            List<InventoryQuantity> inventoryQuantityList) {
        if (item.getSaleQty() != 0 && quantityTypes.contains(SALE)) {
            // build inventory quantity for Sale
            inventoryQuantityList.add(InventoryQuantity.builder().quantity(item.getSaleQty()).type(
                    SALE).ref(inventoryPositionRef + COLON + SALE + COLON + orderId + COLON + orderItemId).build());

        }
        if (item.getCorrectionQty() != 0 && quantityTypes.contains(CORRECTION)) {
            // build inventory quantity for correction
            inventoryQuantityList.add(InventoryQuantity.builder().quantity(item.getCorrectionQty()).type(CORRECTION).ref(inventoryPositionRef + COLON + CORRECTION + COLON + orderId + COLON + orderItemId).build());
        }
    }

    private Map<String, Object> buildEventAttribute(String inventoryPositionRefOnDefault, String catalogueType, String productRef, String locationRef, List<InventoryQuantity> inventoryQuantityList) {

        return ImmutableMap.of(INVENTORY_POSITION_ATTRIBUTE, ImmutableMap.<String, Object>builder()
                .put(INVENTORY_POSITION_REF, inventoryPositionRefOnDefault)
                .put(PROP_TYPE, catalogueType)
                .put(PRODUCT_REF, productRef)
                .put(LOCATION_REF, locationRef)
                .put(INVENTORY_QUANTITIES, inventoryQuantityList)
                .build());
    }

    private void sendEventToInventoryPosition(ContextWrapper context,
                                              Event incomingEvent,
                                              String inventoryPositionCreateEvent,
                                              String inventoryPositionRefOnDefault,
                                              String catalogueType,
                                              String catalogueReference,
                                              Map<String, Object> attributesMap) {
        Event.Builder eventBuilder = Event.builder()
                .name(inventoryPositionCreateEvent)
                .accountId(incomingEvent.getAccountId())
                .retailerId(incomingEvent.getRetailerId())
                .entityRef(inventoryPositionRefOnDefault)
                .entityType(ENTITY_TYPE_INVENTORY_POSITION)
                .entitySubtype(catalogueType)
                .rootEntityType(incomingEvent.getEntityType())
                .rootEntityRef(catalogueReference)
                .attributes(attributesMap)
                .scheduledOn(new Date());
        log.info("[COMMON-GI] - rule: SendEventOnFulfilmentInventoryUpdate - sending event {}", eventBuilder);
        context.action().sendEvent(eventBuilder.build());
    }

}
