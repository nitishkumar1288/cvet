package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.FulfilmentExceptionData;
import com.fluentcommerce.dto.fulfillment.FulfilmentItems;
import com.fluentcommerce.dto.inventory.Items;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.model.product.KitItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.exceptions.RubixException;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
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
 * @author Nandhakumar
 */

@RuleInfo(
        name = "InventoryUpdateFromFulfilmentExceptionResolutionResponse",
        description = "Send an event {" + PROP_EVENT_NAME + "} with items matching the  status {" + PROP_REJECTED_STATUS + "} to inventory catalogue with ref {"
                + PROP_INVENTORY_CATALOGUE_REF + "} "
                + "with retailer id {" + PROP_RETAILER_ID + "} (default: event.retailerId) "
                + "to Correct  the Order items quantity. ",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)

@ParamString(name = PROP_INVENTORY_CATALOGUE_REF, description = "The ref of Inventory catalogue")
@ParamString(name = PROP_REJECTED_STATUS, description = "Rejected status of the fulfilment item")
@ParamString(name = PROP_EVENT_NAME, description = "The name of event to be triggered")
@ParamString(name = PROP_RETAILER_ID, description = "The retailer id")

@EventAttribute(name = FULFILLMENT)
@Slf4j
public class InventoryUpdateFromFulfilmentExceptionResolutionResponse extends BaseRule {

    static final String ERROR_MESSAGE_OPERATION_TYPE_NOT_SUPPORTED = "Operation Type is not supported. Supported types are RESERVE/UNRESERVE or RESET_RESERVE.";

    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();

        String responseEvent = context.getProp(PROP_EVENT_NAME);
        String rejectedStatus = context.getProp(PROP_REJECTED_STATUS);
        String inventoryCatalogueRef = context.getProp(PROP_INVENTORY_CATALOGUE_REF);
        String retailerId = StringUtils.defaultIfEmpty(context.getProp(PROP_RETAILER_ID), event.getRetailerId());

        Map<String, Object> eventAttributes = context.getEvent().getAttributes();
        FulfilmentExceptionData fulfilmentExceptionResolutionResponse = CommonUtils.convertObjectToDto(eventAttributes.get(FULFILMENT),
                new TypeReference<FulfilmentExceptionData>() {
                });

        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(event.getEntityId());

        Map<String, Object> attributes = new HashMap<>();
        List<Items> items = Lists.newArrayList();

        // collect the items information and send the event for correction
        if (Objects.nonNull(fulfilmentExceptionResolutionResponse)) {
            for (FulfilmentItems fulfilmentItem : fulfilmentExceptionResolutionResponse.getItems()) {
                Optional<GetFulfilmentByIdQuery.Edge> itemData = fulfillmentById.items().edges().stream().filter(edge ->
                        edge.item().ref().equalsIgnoreCase(fulfilmentItem.getRef())).findFirst();

                if (itemData.isPresent()
                        && StringUtils.equalsIgnoreCase(fulfilmentItem.getStatus(), rejectedStatus)) {
                    try {
                        Optional<GetFulfilmentByIdQuery.Attribute3> optionalKitProduct =
                                itemData.get().item().orderItem().product().asVariantProduct().attributes().stream().filter(
                                        variantProductAttribute -> (variantProductAttribute.name().equalsIgnoreCase(IS_KIT) &&
                                                variantProductAttribute.value().toString().equalsIgnoreCase(YES_VALUE))
                                ).findAny();

                        constructInventoryData(
                                context,
                                fulfillmentById,
                                items,
                                itemData.get(),
                                optionalKitProduct
                        );
                    } catch (Exception ex) {
                        context.addLog("Exception occurred during data parsing");
                        throw new RubixException(500, ERROR_MESSAGE_OPERATION_TYPE_NOT_SUPPORTED);
                    }
                }
            }
        }

        checkItemListAndSendEvent(
                context,
                event,
                responseEvent,
                inventoryCatalogueRef,
                retailerId,
                attributes,
                items);
    }

    private static void constructInventoryData(
            ContextWrapper context,
            GetFulfilmentByIdQuery.FulfilmentById fulfillmentById,
            List<Items> items,
            GetFulfilmentByIdQuery.Edge itemData,
            Optional<GetFulfilmentByIdQuery.Attribute3> optionalKitProduct) throws IOException {
        if (optionalKitProduct.isPresent()) {
            // Kit product is present
            context.addLog("Order item id : " + itemData.item().orderItem().id() + " is a kit product");

            Optional<Object> optionalKitProductChildInfo =
                    itemData.item().orderItem().product().asVariantProduct().attributes().stream().filter(
                            variantProductAttribute -> variantProductAttribute.name().equalsIgnoreCase(KIT_PRODUCT)
                    ).map(GetFulfilmentByIdQuery.Attribute3::value).findAny();

            if (optionalKitProductChildInfo.isPresent()) {

                List<KitItem> itemList = null;
                ObjectMapper mapper = new ObjectMapper();
                itemList = mapper.readValue(optionalKitProductChildInfo.get().toString(), new TypeReference<List<KitItem>>() {
                });
                getKitItemInformation(fulfillmentById, items, itemData.item(), itemList);
            } else {
                context.addLog("Kit product does not have child information");
            }
        } else {
            items.add(
                    Items.builder()
                            .orderId(fulfillmentById.order().id())
                            .orderItemId(itemData.item().orderItem().id())
                            .skuRef(itemData.item().orderItem().product().asVariantProduct().ref())
                            .correctionQty(-itemData.item().requestedQuantity())
                            .locationRef(fulfillmentById.fromLocation().ref())
                            .build());
        }
    }

    private static void getKitItemInformation(
            GetFulfilmentByIdQuery.FulfilmentById fulfillmentById,
            List<Items> items,
            GetFulfilmentByIdQuery.Item itemData,
            List<KitItem> itemList) {
        for (KitItem kitItem : itemList) {
            items.add(
                    Items.builder()
                            .orderId(fulfillmentById.order().id())
                            .orderItemId(itemData.orderItem().id())
                            .skuRef(kitItem.getSku())
                            .correctionQty(-(kitItem.getProductQty() * itemData.requestedQuantity()))
                            .locationRef(fulfillmentById.fromLocation().ref())
                            .build());
        }
    }

    private static void checkItemListAndSendEvent(
            ContextWrapper context,
            Event event,
            String responseEvent,
            String inventoryCatalogueRef,
            String retailerId,
            Map<String, Object> attributes,
            List<Items> items) {
        if (CollectionUtils.isNotEmpty(items)) {
            attributes.put(ITEMS, items);

            Event eventInfo = Event.builder()
                    .name(responseEvent)
                    .accountId(event.getAccountId())
                    .retailerId(retailerId)
                    .attributes(attributes)
                    .entityRef(inventoryCatalogueRef)
                    .entityType(ENTITY_TYPE_INVENTORY_CATALOGUE)
                    .rootEntityRef(inventoryCatalogueRef)
                    .rootEntityType(ENTITY_TYPE_INVENTORY_CATALOGUE)
                    .entitySubtype(DEFAULT)
                    .scheduledOn(new Date())
                    .build();
            context.action().sendEvent(eventInfo);
        }
    }
}
