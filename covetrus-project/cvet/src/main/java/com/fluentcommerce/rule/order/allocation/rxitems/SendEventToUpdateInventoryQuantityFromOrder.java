package com.fluentcommerce.rule.order.allocation.rxitems;


import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.inventory.Items;
import com.fluentcommerce.dto.inventory.ReservationItemsInformation;
import com.fluentcommerce.dto.orderitem.OrderItemDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.model.product.KitItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.OperationType;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.exceptions.RubixException;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
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
        name = "SendEventToUpdateInventoryQuantityFromOrder",
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

@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@Slf4j
public class SendEventToUpdateInventoryQuantityFromOrder extends BaseRule {

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
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);

        for(PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems : pharmacyAllocationForOrderItemsList) {

            List<ReservationItemsInformation> reservationItemsInformationList = new ArrayList<>();
            for(OrderItemDto orderItemDto : pharmacyAllocationForOrderItems.getOrderItemDtoList()){
                if(orderItemDto.isKitProduct()){
                    for(KitItem kitItem : orderItemDto.getKitItemList()){
                        createReservationItemFromKit(pharmacyAllocationForOrderItems, reservationItemsInformationList, orderItemDto, kitItem);
                    }
                }else{
                    createReservationItem(pharmacyAllocationForOrderItems, reservationItemsInformationList, orderItemDto);
                }
            }

            context.addLog("reservationItemsInformationList " +reservationItemsInformationList);

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

    private static void createReservationItem(
            PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems,
            List<ReservationItemsInformation> reservationItemsInformationList,
            OrderItemDto orderItemDto) {
        ReservationItemsInformation reservationItemsInformation = new ReservationItemsInformation();
        reservationItemsInformation.setOrderItemId(orderItemDto.getId());
        reservationItemsInformation.setSkuRef(orderItemDto.getProductRef());
        reservationItemsInformation.setLocationRef(pharmacyAllocationForOrderItems.getAssignedFacilityRef());
        reservationItemsInformation.setQuantity(orderItemDto.getRequestedQuantity());
        reservationItemsInformationList.add(reservationItemsInformation);
    }

    private static void createReservationItemFromKit(
            PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems,
            List<ReservationItemsInformation> reservationItemsInformationList,
            OrderItemDto orderItemDto,
            KitItem kitItem) {
        ReservationItemsInformation reservationItemsInformation = new ReservationItemsInformation();
        reservationItemsInformation.setOrderItemId(orderItemDto.getId());
        reservationItemsInformation.setSkuRef(kitItem.getSku());
        reservationItemsInformation.setLocationRef(pharmacyAllocationForOrderItems.getAssignedFacilityRef());
        reservationItemsInformation.setQuantity(kitItem.getProductQty());
        reservationItemsInformationList.add(reservationItemsInformation);
    }
}
