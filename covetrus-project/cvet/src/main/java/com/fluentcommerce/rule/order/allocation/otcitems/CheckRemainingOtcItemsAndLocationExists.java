package com.fluentcommerce.rule.order.allocation.otcitems;


import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.location.LocationDto;
import com.fluentcommerce.dto.orderitem.OtcProductsDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "CheckRemainingOtcItemsAndLocationExists",
        description = "Check Remaining OTC items and location exists and send event {" + PROP_EVENT_NAME + "}.",
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

@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@EventAttribute(name = EVENT_FIELD_LOCATIONS)
@EventAttribute(name = EVENT_OTC_ORDER_ITEMS_ALLOCATION)
@Slf4j
public class CheckRemainingOtcItemsAndLocationExists extends BaseRule {

    @Override
    public void run(ContextWrapper context) {

        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);

        Map<String, Object> eventAttribute = context.getEvent().getAttributes();

        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttribute.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);
        Map<String, LocationDto> locations = (Map<String, LocationDto>) eventAttribute.get(EVENT_FIELD_LOCATIONS);
        List<OtcProductsDto> otcProductsDtoList = (List<OtcProductsDto>) eventAttribute.get(EVENT_OTC_ORDER_ITEMS_ALLOCATION);

        // Get the location list from the Rx items
        List<String> locationRefList =
                pharmacyAllocationForOrderItemsList.stream().map(
                        PharmacyAllocationForOrderItems::getAssignedFacilityRef
                ).distinct().collect(Collectors.toList());

        List<LocationDto> locationDtoList = locations.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        // Remove the rx facility from the list
        locationDtoList =
                locationDtoList.stream().filter(
                        locationDto -> (!locationRefList.contains(locationDto.getRef()))
                ).collect(Collectors.toList());

        // orderItemIdsToFulfil will contain the order item ids to be fulfilled , in this case exclude the items that are already handled
        List<String> orderItemIdsToFulfil =
                otcProductsDtoList.stream().filter(
                        otcProductsDto -> StringUtils.isEmpty(otcProductsDto.getFoundLocation())
                ).map(OtcProductsDto::getOrderItemId).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(locationDtoList) || CollectionUtils.isEmpty(orderItemIdsToFulfil)) {
            context.addLog("locationDtoList or orderItemIdsToFulfil is empty");
            EventUtils.forwardInline(
                    context,
                    noMatchEventName
            );
        } else {
            context.addLog("Both are not empty , so fulfil remaining items from OTC network");
            EventUtils.forwardInline(
                    context,
                    eventName
            );
        }
    }

}

