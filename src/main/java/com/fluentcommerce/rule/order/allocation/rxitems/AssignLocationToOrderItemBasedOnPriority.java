package com.fluentcommerce.rule.order.allocation.rxitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.location.LocationPriority;
import com.fluentcommerce.dto.orderitem.OrderItemDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.graphql.query.networks.GetNetworksByRefAndStatusQuery;
import com.fluentcommerce.model.product.KitItem;
import com.fluentcommerce.model.virtual.VirtualPosition;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.network.NetworkService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "AssignLocationToOrderItemBasedOnPriority",
        description = "Assign the location to the order item based on the priority and send event {" + PROP_EVENT_NAME + "}" ,
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
@EventAttribute(name = EVENT_ALL_VP_INFORMATION)
@Slf4j
public class AssignLocationToOrderItemBasedOnPriority extends BaseRule {

    private static final String CLASS_NAME = AssignLocationToOrderItemBasedOnPriority.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);
        List<VirtualPosition> virtualPositionList = (List<VirtualPosition>) eventAttributes.get(EVENT_ALL_VP_INFORMATION);

        List<PharmacyAllocationForOrderItems> remainingOrderItems =
                pharmacyAllocationForOrderItemsList.stream().filter(
                        allocationItems -> StringUtils.isEmpty(allocationItems.getAssignedFacilityId())
                ).collect(Collectors.toList());

        List<String> networkRefList =
                remainingOrderItems.stream().map(
                        PharmacyAllocationForOrderItems::getNetworkRef
                ).collect(Collectors.toList());

        NetworkService networkService = new NetworkService(context);
        GetNetworksByRefAndStatusQuery.Data networkData = networkService.getNetworksByRefAndStatus(
                networkRefList, null,null);

        for(PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems : remainingOrderItems) {
            // Check whether all the locations got rejected as part of pharmacy authentication or kit product selection , ignore those products because those will be assigned to the default location
            if(CollectionUtils.isNotEmpty(pharmacyAllocationForOrderItems.getLocationRefList())) {
                Map<String,Integer> productWithRequestQuantity = new HashMap<>();

                constructProductWithRequestQuantity(
                        pharmacyAllocationForOrderItems,
                        productWithRequestQuantity);

                context.addLog("productWithRequestQuantity " + productWithRequestQuantity);

                Optional<GetNetworksByRefAndStatusQuery.Edge> optionalNetworkData =
                        networkData.networks().edges().stream().filter(
                                edge -> edge.node().ref().equalsIgnoreCase(pharmacyAllocationForOrderItems.getNetworkRef())
                        ).findFirst();

                if(optionalNetworkData.isPresent()){

                    List<LocationPriority> locationPriorityLists = new ArrayList<>();
                    constructLocationPriorityList(
                            pharmacyAllocationForOrderItems,
                            optionalNetworkData.get(),
                            locationPriorityLists);

                    locationPriorityLists = locationPriorityLists.stream().sorted(Comparator.comparing(LocationPriority::getSortPosition)).
                            collect(Collectors.toList());

                    String foundLocation = findLocation(
                            context,
                            virtualPositionList,
                            productWithRequestQuantity,
                            locationPriorityLists);

                    if(StringUtils.isNotEmpty(foundLocation)){
                        String finalFoundLocation = foundLocation;
                        List<VirtualPosition> vpSpecificToLocation =
                                virtualPositionList.stream().filter(
                                        virtualPosition -> virtualPosition.getGroupRef().equalsIgnoreCase(finalFoundLocation)
                                ).collect(Collectors.toList());

                        reduceVpForProducts(
                                productWithRequestQuantity,
                                vpSpecificToLocation);

                        // assign the found location to the allocation object
                        context.addLog("Location found : " + foundLocation);
                        pharmacyAllocationForOrderItems.setAssignedFacilityRef(foundLocation);
                    }
                    else{
                        // if the location is not found then make the location ref list as null
                        pharmacyAllocationForOrderItems.setLocationRefList(null);
                    }
                }
            }
        }

        EventUtils.forwardInline(
                context,
                eventName
        );
    }

    private static void constructProductWithRequestQuantity(
            PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems,
            Map<String, Integer> productWithRequestQuantity) {
        for (OrderItemDto orderItemDto : pharmacyAllocationForOrderItems.getOrderItemDtoList()) {
            // Get the cumulative value if the item is repeated multiple times with the pharmacy allocation object
            if(orderItemDto.isKitProduct()){
                captureKitProductInformation(
                        productWithRequestQuantity,
                        orderItemDto);
            }else {
                if (productWithRequestQuantity.containsKey(orderItemDto.getProductRef())) {
                    productWithRequestQuantity.put(orderItemDto.getProductRef(), (productWithRequestQuantity.get(orderItemDto.getProductRef()) + orderItemDto.getRequestedQuantity()));
                } else {
                    productWithRequestQuantity.put(orderItemDto.getProductRef(), orderItemDto.getRequestedQuantity());
                }
            }
        }
    }

    private static void captureKitProductInformation(
            Map<String, Integer> productWithRequestQuantity,
            OrderItemDto orderItemDto) {
        for(KitItem kitItem : orderItemDto.getKitItemList()){
            if (productWithRequestQuantity.containsKey(kitItem.getSku())) {
                productWithRequestQuantity.put(kitItem.getSku(), (productWithRequestQuantity.get(kitItem.getSku()) + kitItem.getProductQty()));
            } else {
                productWithRequestQuantity.put(kitItem.getSku(), kitItem.getProductQty());
            }
        }
    }

    private static void constructLocationPriorityList(
            PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems,
            GetNetworksByRefAndStatusQuery.Edge optionalNetworkData,
            List<LocationPriority> locationPriorityLists) {
        for (GetNetworksByRefAndStatusQuery.Attribute locationRef : optionalNetworkData.node().attributes()) {
            if (pharmacyAllocationForOrderItems.getLocationRefList().contains(locationRef.name())) {
                LocationPriority locationPriority = new LocationPriority();
                locationPriority.setLocationRef(locationRef.name());
                locationPriority.setSortPosition( Integer.parseInt(locationRef.value().toString()));
                locationPriorityLists.add(locationPriority);
            }
        }
    }

    private static String findLocation(
            ContextWrapper context,
            List<VirtualPosition> virtualPositionList,
            Map<String, Integer> productWithRequestQuantity,
            List<LocationPriority> locationPriorityLists) {
        String foundLocation = null;
        for(LocationPriority location : locationPriorityLists){

            List<VirtualPosition> vpSpecificToLocation =
                    virtualPositionList.stream().filter(
                            virtualPosition -> virtualPosition.getGroupRef().equalsIgnoreCase(location.getLocationRef())
                    ).collect(Collectors.toList());

            boolean locationFound = true;
            if(CollectionUtils.isNotEmpty(vpSpecificToLocation)){
                locationFound = checkAllProductsAvailableInLocation(
                        context,
                        productWithRequestQuantity,
                        vpSpecificToLocation,
                        locationFound);

                if(locationFound){
                    foundLocation = location.getLocationRef();
                    break;
                }
            }
        }
        return foundLocation;
    }

    private static boolean checkAllProductsAvailableInLocation(
            ContextWrapper context,
            Map<String, Integer> productWithRequestQuantity,
            List<VirtualPosition> vpSpecificToLocation,
            boolean locationFound) {
        context.addLog("Check all products are available in the location");

        // check whether all the products are available
        for (Map.Entry<String, Integer> entry : productWithRequestQuantity.entrySet()) {
            Optional<VirtualPosition> optionalVirtualPosition =
                    vpSpecificToLocation.stream().filter(
                            virtualPosition -> virtualPosition.getProductRef().equalsIgnoreCase(entry.getKey())
                    ).findFirst();

            if(optionalVirtualPosition.isPresent()){
                if( (entry.getValue() > optionalVirtualPosition.get().getQuantity()) ){
                    locationFound = false;
                }
            }else{
                locationFound = false;
            }
        }
        return locationFound;
    }

    private static void reduceVpForProducts(
            Map<String, Integer> productWithRequestQuantity,
            List<VirtualPosition> vpSpecificToLocation) {
        for (Map.Entry<String, Integer> entry : productWithRequestQuantity.entrySet()) {

            Optional<VirtualPosition> optionalVirtualPosition =
                    vpSpecificToLocation.stream().filter(
                            virtualPosition -> virtualPosition.getProductRef().equalsIgnoreCase(entry.getKey())
                    ).findFirst();

            // Reduce the inventory for the products
            if(optionalVirtualPosition.isPresent()){
                optionalVirtualPosition.get().setQuantity(optionalVirtualPosition.get().getQuantity() - entry.getValue());
            }
        }
    }
}