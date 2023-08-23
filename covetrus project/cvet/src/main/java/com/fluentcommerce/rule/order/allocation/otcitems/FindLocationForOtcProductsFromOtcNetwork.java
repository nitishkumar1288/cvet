package com.fluentcommerce.rule.order.allocation.otcitems;


import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.location.LocationDto;
import com.fluentcommerce.dto.orderitem.OtcProductsDto;
import com.fluentcommerce.model.product.KitItem;
import com.fluentcommerce.model.virtual.VirtualPosition;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "FindLocationForOtcProductsFromOtcNetwork",
        description = "Find the locations to fulfil the otc items and sends event {" + PROP_EVENT_NAME + "}.",
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

@ParamString(name = PROP_EVENT_NAME, description = "The event name triggered by this rule")

@EventAttribute(name = EVENT_FIELD_LOCATIONS)
@EventAttribute(name = EVENT_CAPACITY_FOR_LOCATION)
@EventAttribute(name = EVENT_FIELD_VIRTUAL_POSITIONS)
@EventAttribute(name = EVENT_OTC_ORDER_ITEMS_ALLOCATION)

@Slf4j
public class FindLocationForOtcProductsFromOtcNetwork extends BaseRule {

    @Override
    public void run(ContextWrapper context) {

        final String eventName = context.getProp(PROP_EVENT_NAME);

        Map<String,Object> eventAttribute = context.getEvent().getAttributes();

        Map<String, LocationDto> locations = (Map<String, LocationDto>) eventAttribute.get(EVENT_FIELD_LOCATIONS);
        Map<String,Integer> locationRefWithCapacity = (Map<String, Integer>) eventAttribute.get(EVENT_CAPACITY_FOR_LOCATION);
        List<VirtualPosition> virtualPositionList = (List<VirtualPosition>) eventAttribute.get(EVENT_FIELD_VIRTUAL_POSITIONS);
        List<OtcProductsDto> otcProductsDtoList = (List<OtcProductsDto>) eventAttribute.get(EVENT_OTC_ORDER_ITEMS_ALLOCATION);

        // segregate kit and non kit products and move the kit products to the end of the array to increase the efficiency of the algorithm
        List<OtcProductsDto> otcProductsWithKit = otcProductsDtoList.stream().filter(
                otcProductsDto -> (otcProductsDto.isKitProduct())
        ).collect(Collectors.toList());

        List<OtcProductsDto> otcProductsWithoutKit = otcProductsDtoList.stream().filter(
                hillProductsDto -> !(hillProductsDto.isKitProduct())
        ).collect(Collectors.toList());

        List<OtcProductsDto> combinedOtcProducts = new ArrayList<>();
        combinedOtcProducts.addAll(otcProductsWithoutKit);
        combinedOtcProducts.addAll(otcProductsWithKit);

        otcProductsDtoList = combinedOtcProducts;

        // step 1 : Sorting the locations based on the distance
        List<LocationDto> locationDtoList = locations.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        locationDtoList = locationDtoList.stream().sorted(Comparator.comparingDouble(LocationDto::getDistance)).collect(Collectors.toList());

        // step 2: find the location which can satisfy the maximum number of products
        boolean iterateTillLocationsAreFound = true;

        // orderItemIdsToFulfil will contain the order item ids to be fulfilled
        List<String> orderItemIdsToFulfil = otcProductsDtoList.stream().map(
                OtcProductsDto::getOrderItemId
        ) .collect(Collectors.toList());

        context.addLog("orderItemIdsToFulfil " + orderItemIdsToFulfil);

        while(iterateTillLocationsAreFound){
            int maximumProductFulfilledInLocation = 0;
            List<VirtualPosition> selectLocationVpList = null;
            List<String> selectedOrderItemIdList = null;

            // iterate all the location
            for(LocationDto location : locationDtoList) {

                List<VirtualPosition> locationSpecificVpList =
                        virtualPositionList.stream().filter(
                                virtualPosition -> virtualPosition.getGroupRef().equalsIgnoreCase(location.getRef())
                        ).collect(Collectors.toList());

                if (CollectionUtils.isNotEmpty(locationSpecificVpList)) {
                    // Copy the virtual position information specific to location into a location specific map to avoid reducing the Vp count on the original list
                    Map<String, Integer> vpMapForLocation = new HashMap<>();

                    constructVpMapForLocation(
                            locationSpecificVpList,
                            vpMapForLocation);

                    List<String> orderItemIdList = new ArrayList<>();
                    int productsFulfilledInLocation = 0;

                    productsFulfilledInLocation = handleRemainingItems(
                            context,
                            otcProductsDtoList,
                            orderItemIdsToFulfil,
                            vpMapForLocation,
                            orderItemIdList,
                            productsFulfilledInLocation);

                    context.addLog("locationRefWithCapacity.get(location.getRef()) " + locationRefWithCapacity.get(location.getRef()));
                    context.addLog("productsFulfilledInLocation " + productsFulfilledInLocation);


                    productsFulfilledInLocation = getProductsCountPostCapacityCheck(
                            context,
                            locationRefWithCapacity,
                            location,
                            productsFulfilledInLocation);

                    // check max products that can be fulfilled from a location
                    if(productsFulfilledInLocation > maximumProductFulfilledInLocation){
                        maximumProductFulfilledInLocation = productsFulfilledInLocation;

                        // point the vp list to the selected vp list
                        selectLocationVpList =  locationSpecificVpList;

                        // point the Order item list to the selectedOrderItemList
                        selectedOrderItemIdList = orderItemIdList.stream().limit(productsFulfilledInLocation).collect(Collectors.toList());
                    }
                }
            }

            if(maximumProductFulfilledInLocation > 0){

                // reduce the actual Vp to handle the duplicated items
                handleVpForKitAndNonKitItems(
                        context,
                        locationRefWithCapacity,
                        otcProductsDtoList,
                        locationDtoList,
                        orderItemIdsToFulfil,
                        selectLocationVpList,
                        selectedOrderItemIdList);

                context.addLog("AFTER : orderItemIdsToFulfil " + orderItemIdsToFulfil);
            }
            else{
                context.addLog("No inventory is available for the remaining products " + orderItemIdsToFulfil);
                iterateTillLocationsAreFound = false;
            }

            if(CollectionUtils.isEmpty(orderItemIdsToFulfil)){
                context.addLog("All the products are handled");
                iterateTillLocationsAreFound = false;
            }

        }

        EventUtils.forwardInline(
                context,
                eventName
        );

    }

    private static void constructVpMapForLocation(
            List<VirtualPosition> locationSpecificVpList,
            Map<String, Integer> vpMapForLocation) {
        for (VirtualPosition vpForLocation : locationSpecificVpList) {
            vpMapForLocation.put(vpForLocation.getProductRef(),vpForLocation.getQuantity());
        }
    }

    private static int handleRemainingItems(
            ContextWrapper context,
            List<OtcProductsDto> otcProductsDtoList,
            List<String> orderItemIdsToFulfil,
            Map<String, Integer> vpMapForLocation,
            List<String> orderItemIdList,
            int productsFulfilledInLocation) {
        // Find how many products the iterated location can fulfil
        for(OtcProductsDto otcProductsDto : otcProductsDtoList) {

            // orderItemIdsToFulfil will contain the remaining products to be fulfilled
            if(orderItemIdsToFulfil.contains(otcProductsDto.getOrderItemId())) {
                productsFulfilledInLocation = getProductsFulfilledInLocation(
                        context,
                        vpMapForLocation,
                        orderItemIdList,
                        productsFulfilledInLocation,
                        otcProductsDto);
            }
        }
        return productsFulfilledInLocation;
    }

    private static int getProductsFulfilledInLocation(
            ContextWrapper context,
            Map<String, Integer> vpMapForLocation,
            List<String> orderItemIdList,
            int productsFulfilledInLocation,
            OtcProductsDto otcProductsDto) {

        // check for the kit product
        if(otcProductsDto.isKitProduct()){
            context.addLog("order item id : " + otcProductsDto.getOrderItemId() + " is a kit product");

            List<KitItem> kitItemList = otcProductsDto.getKitItemList();

            boolean locationSelected = isLocationSelectedForKitItem(
                    vpMapForLocation,
                    kitItemList,
                    true);

            // if the location is selected then reduce the vp count
            if (locationSelected) {
                //reduce the VP
                for (KitItem kitItem : kitItemList) {
                    vpMapForLocation.put(
                            kitItem.getSku(),
                            vpMapForLocation.get(
                                    kitItem.getSku()) - kitItem.getProductQty()
                    );
                }

                // increment the product found count
                productsFulfilledInLocation = productsFulfilledInLocation + 1;

                // add the order item id to the list to handle the duplicated line item logic
                orderItemIdList.add(otcProductsDto.getOrderItemId());
            }
        }
        else{
            // check whether the product is part of the Vp map and check whether the requested quantity is present
            if (vpMapForLocation.containsKey(otcProductsDto.getProductRef()) &&
                    vpMapForLocation.get(otcProductsDto.getProductRef()) >= otcProductsDto.getRequestedQuantity()) {
                productsFulfilledInLocation = productsFulfilledInLocation + 1;

                //reduce the VP
                vpMapForLocation.put(otcProductsDto.getProductRef(), vpMapForLocation.get(otcProductsDto.getProductRef()) - otcProductsDto.getRequestedQuantity());

                // add the order item id to the list to handle the duplicated line item logic
                orderItemIdList.add(otcProductsDto.getOrderItemId());
            }
        }
        return productsFulfilledInLocation;
    }

    private static boolean isLocationSelectedForKitItem(
            Map<String, Integer> vpMapForLocation,
            List<KitItem> kitItemList,
            boolean locationSelected) {
        for (KitItem kitItem : kitItemList) {
            if (vpMapForLocation.containsKey(kitItem.getSku())) {
                int availableCount = vpMapForLocation.get(kitItem.getSku()) / kitItem.getProductQty();
                if (availableCount == 0) {
                    locationSelected = false;
                }
            } else {
                locationSelected = false;
            }
        }
        return locationSelected;
    }

    private static int getProductsCountPostCapacityCheck(
            ContextWrapper context,
            Map<String, Integer> locationRefWithCapacity,
            LocationDto location,
            int productsFulfilledInLocation) {
        // this check is required to reduce the number of products to be fulfilled in a location based on capacity
        if((locationRefWithCapacity.get(location.getRef())) < productsFulfilledInLocation){
            productsFulfilledInLocation = locationRefWithCapacity.get(location.getRef());
            context.addLog("modified productsFulfilledInLocation " + productsFulfilledInLocation);
        }
        return productsFulfilledInLocation;
    }

    private static void handleVpForKitAndNonKitItems(
            ContextWrapper context,
            Map<String, Integer> locationRefWithCapacity,
            List<OtcProductsDto> otcProductsDtoList,
            List<LocationDto> locationDtoList,
            List<String> orderItemIdsToFulfil,
            List<VirtualPosition> selectLocationVpList,
            List<String> selectedOrderItemIdList) {
        for(OtcProductsDto otcProductsDto : otcProductsDtoList) {
            if(selectedOrderItemIdList.contains(otcProductsDto.getOrderItemId())){

                if(otcProductsDto.isKitProduct()){
                    // reduce the actual vp for the kit products
                    List<KitItem> kitItemList = otcProductsDto.getKitItemList();
                    handleVpInKitItem(
                            context,
                            selectLocationVpList,
                            kitItemList);
                }
                else{
                    // reduce the VP for normal product
                    handleVpInNonKitItem(
                            context,
                            selectLocationVpList,
                            otcProductsDto);
                }

                // modify the capacity count ,
                String locationRef = selectLocationVpList.get(0).getGroupRef();
                int modifiedCapacityCount = locationRefWithCapacity.get(locationRef) - 1;
                locationRefWithCapacity.put(
                        locationRef,
                        modifiedCapacityCount
                        );
                context.addLog("locationRefWithCapacity after reducing count " + locationRefWithCapacity);

                // Remove the order item id
                orderItemIdsToFulfil.remove(otcProductsDto.getOrderItemId());

                // add the location information to the dto
                otcProductsDto.setFoundLocation(selectLocationVpList.get(0).getGroupRef());

                // set the location id as part of the dto
                List<VirtualPosition> finalSelectLocationVpList = selectLocationVpList;
                Optional<String> optionalFacilityId =
                        locationDtoList.stream().filter(
                                locationDto -> locationDto.getRef().equalsIgnoreCase(finalSelectLocationVpList.get(0).getGroupRef())
                        ).map(LocationDto::getId).findFirst();

                if(optionalFacilityId.isPresent()){
                    otcProductsDto.setLocationId(optionalFacilityId.get());
                }
            }
        }
    }

    private static void handleVpInKitItem(
            ContextWrapper context,
            List<VirtualPosition> selectLocationVpList,
            List<KitItem> kitItemList) {
        for (KitItem kitItem : kitItemList) {

            Optional<VirtualPosition> optionalVirtualPosition =
                    selectLocationVpList.stream().filter(
                            virtualPosition -> virtualPosition.getProductRef().equalsIgnoreCase(kitItem.getSku())
                    ).findFirst();
            if(optionalVirtualPosition.isPresent()){
                optionalVirtualPosition.get().setQuantity(
                        optionalVirtualPosition.get().getQuantity() - kitItem.getProductQty());
            }else{
                context.addLog("logical error since vp checks are already made for kit product : " + kitItem.getSku());
            }
        }
    }

    private static void handleVpInNonKitItem(
            ContextWrapper context,
            List<VirtualPosition> selectLocationVpList,
            OtcProductsDto otcProductsDto) {
        Optional<VirtualPosition> optionalVirtualPosition =
                selectLocationVpList.stream().filter(
                        virtualPosition -> virtualPosition.getProductRef().equalsIgnoreCase(otcProductsDto.getProductRef())
                ).findFirst();
        if(optionalVirtualPosition.isPresent()){
            optionalVirtualPosition.get().setQuantity(
                    optionalVirtualPosition.get().getQuantity() - otcProductsDto.getRequestedQuantity()
            );
        }else{
            context.addLog("logical error since vp checks are already made for normal product : " + otcProductsDto.getProductRef());
        }
    }

}

