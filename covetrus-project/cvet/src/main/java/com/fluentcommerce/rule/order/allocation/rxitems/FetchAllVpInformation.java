package com.fluentcommerce.rule.order.allocation.rxitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.OrderItemDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.model.product.KitItem;
import com.fluentcommerce.model.virtual.VirtualPosition;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.virtual.VirtualService;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "FetchAllVpInformation",
        description = "Fetch all the vp information of all items involved in allocation and send event {" + PROP_EVENT_NAME + "} if no locations found then send event {"+ PROP_EVENT_NAME_IF_NO_LOCATIONS_ARE_FOUND +"} " ,
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
@ParamString(
        name = PROP_EVENT_NAME_IF_NO_LOCATIONS_ARE_FOUND,
        description = "Event Name"
)

@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@Slf4j
public class FetchAllVpInformation extends BaseRule {

    private static final String CLASS_NAME = FetchAllVpInformation.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);
        String eventNameIfNoLocationsAreFound = context.getProp(PROP_EVENT_NAME_IF_NO_LOCATIONS_ARE_FOUND);
        String virtualCatalogueRef = context.getProp(PROP_VIRTUAL_CATALOGUE_REF);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);

        // Vp has to be fetched for all products including the pre-assigned items

        List<String> productRefList = new ArrayList<>();
        List<String> locationRefList = new ArrayList<>();
        for(PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems : pharmacyAllocationForOrderItemsList) {

            // Check whether all the locations got rejected as part of pharmacy authentication , ignore those products because those will be assigned to the default location
            if(CollectionUtils.isNotEmpty(pharmacyAllocationForOrderItems.getLocationRefList()) || StringUtils.isNotEmpty(pharmacyAllocationForOrderItems.getAssignedFacilityRef())) {

                for (OrderItemDto orderItemDto : pharmacyAllocationForOrderItems.getOrderItemDtoList()) {
                    checkForKitProducts(
                            context,
                            productRefList,
                            orderItemDto);
                }
                if (CollectionUtils.isNotEmpty(pharmacyAllocationForOrderItems.getLocationRefList())) {
                    locationRefList.addAll(pharmacyAllocationForOrderItems.getLocationRefList());
                } else if (StringUtils.isNotEmpty(pharmacyAllocationForOrderItems.getAssignedFacilityRef())) {
                    locationRefList.add(pharmacyAllocationForOrderItems.getAssignedFacilityRef());
                }
            }
        }

        sendEventBasedOnLocationList(
                context,
                eventName,
                eventNameIfNoLocationsAreFound,
                virtualCatalogueRef,
                productRefList,
                locationRefList);
    }

    private static void checkForKitProducts(
            ContextWrapper context,
            List<String> productRefList,
            OrderItemDto orderItemDto) {
        if (orderItemDto.isKitProduct()) {
            context.addLog("orderItem is a kit product : " + orderItemDto.getId());
            productRefList.addAll(
                    orderItemDto.getKitItemList().stream().map(KitItem::getSku).collect(Collectors.toList())
            );
        } else {
            productRefList.add(orderItemDto.getProductRef());
        }
    }

    private static void sendEventBasedOnLocationList(
            ContextWrapper context,
            String eventName,
            String eventNameIfNoLocationsAreFound,
            String virtualCatalogueRef,
            List<String> productRefList,
            List<String> locationRefList) {
        if(CollectionUtils.isEmpty(locationRefList)){
            context.addLog("locationRefList is empty");

            EventUtils.forwardInline(
                    context,
                    eventNameIfNoLocationsAreFound);
        }
        else{
            VirtualService virtualService = new VirtualService(context);
            List<VirtualPosition> allVirtualPositions = virtualService.searchVirtualPositions(virtualCatalogueRef, locationRefList, productRefList);

            Map<String, Object> attributes = new HashMap<>();
            attributes.putAll(context.getEvent().getAttributes());
            attributes.put(EVENT_ALL_VP_INFORMATION, allVirtualPositions);

            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    attributes);
        }
    }
}