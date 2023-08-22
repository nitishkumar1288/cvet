package com.fluentcommerce.rule.order.allocation.hillitems;


import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.inventory.Items;
import com.fluentcommerce.dto.inventory.ReservationItemsInformation;
import com.fluentcommerce.dto.orderitem.HillProductsDto;
import com.fluentcommerce.model.product.KitItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.OperationType;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.exceptions.RubixException;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;


/**
 @author Nandhakumar
 */

@RuleInfo(
        name = "SendEventToUpdateInventoryQuantityFromHillOrderItems",
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


@EventAttribute(name = EVENT_HILL_ORDER_ITEMS_ALLOCATION)
@Slf4j
public class SendEventToUpdateInventoryQuantityFromHillOrderItems extends BaseRule {

    static final String ERROR_MESSAGE_OPERATION_TYPE_NOT_SUPPORTED = "Operation Type is not supported. Supported types are RESERVE/UNRESERVE or RESET_RESERVE.";

    @Override
    public void run(ContextWrapper context) {
        Event incomingEvent = context.getEvent();

        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME, PROP_OPERATION_NAME, PROP_INVENTORY_CATALOGUE_REF);
        String responseEvent = context.getProp(PROP_EVENT_NAME);
        String operationType = context.getProp(PROP_OPERATION_NAME);
        String inventoryCatalogueRef = context.getProp(PROP_INVENTORY_CATALOGUE_REF);
        String retailerId = StringUtils.defaultIfEmpty(context.getProp(PROP_RETAILER_ID), incomingEvent.getRetailerId());

        // fetch the event attributes
        Map<String,Object> eventAttribute = context.getEvent().getAttributes();
        List<HillProductsDto> hillProductsDtoList = (List<HillProductsDto>) eventAttribute.get(EVENT_HILL_ORDER_ITEMS_ALLOCATION);


        Map<String,List<ReservationItemsInformation>> locationWithItemList = new HashMap<>();
        for(HillProductsDto hillProductsDto : hillProductsDtoList){
            if(locationWithItemList.containsKey(hillProductsDto.getFoundLocation())){

                List<ReservationItemsInformation> reservationItemsInformationList = locationWithItemList.get(hillProductsDto.getFoundLocation());
                checkKitCriteria(hillProductsDto, reservationItemsInformationList);

            }else{
                List<ReservationItemsInformation> reservationItemsInformationList = new ArrayList<>();
                checkKitCriteria(hillProductsDto, reservationItemsInformationList);

                // insert it into the map
                locationWithItemList.put(hillProductsDto.getFoundLocation(),reservationItemsInformationList);
            }
        }

        context.addLog("locationWithItemList " +locationWithItemList);

        for(Map.Entry<String,List<ReservationItemsInformation>> mapEntry : locationWithItemList.entrySet()){
            List<ReservationItemsInformation> reservationItemsInformationList = mapEntry.getValue();

            try {
                Map<String, Object> attributes = new HashMap<>();
                List<Items> items = Lists.newArrayList();
                if (OperationType.RESERVE.name().equalsIgnoreCase(operationType)) {
                    // Reserve when fulfilment is created
                    reservationItemsInformationList.stream().forEach(
                            item -> items.add(
                                    Items.builder()
                                            .orderId(incomingEvent.getEntityId())
                                            .orderItemId(item.getOrderItemId())
                                            .skuRef(item.getSkuRef())
                                            .reserveQty(-item.getQuantity())
                                            .locationRef(item.getLocationRef())
                                            .build())
                    );
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

    private static void checkKitCriteria(
            HillProductsDto hillProductsDto,
            List<ReservationItemsInformation> reservationItemsInformationList) {
        if(hillProductsDto.isKitProduct()){
            for(KitItem kitItem : hillProductsDto.getKitItemList()){
                createReservationItemFromKit(hillProductsDto, kitItem, reservationItemsInformationList);
            }
        }else{
            createReservationItem(hillProductsDto, reservationItemsInformationList);
        }
    }

    private static void createReservationItemFromKit(
            HillProductsDto hillProductsDto,
            KitItem kitItem,
            List<ReservationItemsInformation> reservationItemsInformationList) {
        // Create the object with required data
        ReservationItemsInformation reservationItemsInformation = new ReservationItemsInformation();
        reservationItemsInformation.setOrderItemId(hillProductsDto.getOrderItemId());
        reservationItemsInformation.setSkuRef(kitItem.getSku());
        reservationItemsInformation.setLocationRef(hillProductsDto.getFoundLocation());
        reservationItemsInformation.setQuantity(kitItem.getProductQty());

        reservationItemsInformationList.add(reservationItemsInformation);
    }

    private static void createReservationItem (
            HillProductsDto hillProductsDto,
            List<ReservationItemsInformation> reservationItemsInformationList) {
        // Create the object with required data
        ReservationItemsInformation reservationItemsInformation = new ReservationItemsInformation();
        reservationItemsInformation.setOrderItemId(hillProductsDto.getOrderItemId());
        reservationItemsInformation.setSkuRef(hillProductsDto.getProductRef());
        reservationItemsInformation.setLocationRef(hillProductsDto.getFoundLocation());
        reservationItemsInformation.setQuantity(hillProductsDto.getRequestedQuantity());

        reservationItemsInformationList.add(reservationItemsInformation);
    }
}
