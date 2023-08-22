package com.fluentcommerce.rule.order.allocation.rxitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.OrderItemDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.model.product.KitItem;
import com.fluentcommerce.model.virtual.VirtualPosition;
import com.fluentcommerce.rule.BaseRule;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fluentcommerce.util.Constants.*;
/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "SoftReserveForPreAssignedFacilityOrderItems",
        description = "Soft reserve the Vp for the pre-assigned facilities and send event {" + PROP_EVENT_NAME + "}",
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
public class SoftReserveForPreAssignedFacilityOrderItems extends BaseRule {

    private static final String CLASS_NAME = SoftReserveForPreAssignedFacilityOrderItems.class.getSimpleName();

    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);

        // fetch the event attributes
        Map<String, Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);
        List<VirtualPosition> virtualPositionList = (List<VirtualPosition>) eventAttributes.get(EVENT_ALL_VP_INFORMATION);

        List<PharmacyAllocationForOrderItems> preAssignedFacilityOrderItems =
                pharmacyAllocationForOrderItemsList.stream().filter(
                        allocationItems -> StringUtils.isNotEmpty(allocationItems.getAssignedFacilityRef())
                ).collect(Collectors.toList());


        //Soft reserve the VP for the pre-assigned kit and normal products
        for (PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems : preAssignedFacilityOrderItems) {

            // fetch the VP specific to locations of allocation items
            List<VirtualPosition> locationsSpecificVpList = virtualPositionList.stream().filter(
                    virtualPosition -> pharmacyAllocationForOrderItems.getAssignedFacilityRef().equalsIgnoreCase(virtualPosition.getGroupRef())
            ).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(locationsSpecificVpList)) {
                for (OrderItemDto orderItemDto : pharmacyAllocationForOrderItems.getOrderItemDtoList()) {

                    // handle kit and normal product
                    if (orderItemDto.isKitProduct()) {
                        List<KitItem> kitItemList = orderItemDto.getKitItemList();

                        List<String> productRefList = kitItemList.stream().map(
                                KitItem::getSku
                        ).collect(Collectors.toList());

                        List<VirtualPosition> kitItemsAvailableInLocation = locationsSpecificVpList.stream().filter(
                                vp -> productRefList.contains(vp.getProductRef())
                        ).collect(Collectors.toList());

                        context.addLog("kitItemsAvailableInLocation " + kitItemsAvailableInLocation);

                        //  block the vp for the available items in the kit and normal product
                        context.addLog("Kit item ref : " + orderItemDto.getProductRef());

                        handleKitItemsVp(
                                kitItemList,
                                kitItemsAvailableInLocation
                        );

                    } else {
                        handleNonKitItemsVp(
                                locationsSpecificVpList,
                                orderItemDto);
                    }
                }
            }
        }

        EventUtils.forwardInline(
                context,
                eventName
        );
    }

    private static void handleNonKitItemsVp(
            List<VirtualPosition> locationsSpecificVpList,
            OrderItemDto orderItemDto) {
        Optional<VirtualPosition> optionalVirtualPosition =
                locationsSpecificVpList.stream().filter(
                        virtualPosition -> virtualPosition.getProductRef().equalsIgnoreCase(orderItemDto.getProductRef())
                ).findFirst();

        if (optionalVirtualPosition.isPresent()) {
            optionalVirtualPosition.get().setQuantity(optionalVirtualPosition.get().getQuantity() - orderItemDto.getRequestedQuantity());
        }
    }

    private static void handleKitItemsVp(
            List<KitItem> kitItemList,
            List<VirtualPosition> kitItemsAvailableInLocation) {
        if (CollectionUtils.isNotEmpty(kitItemsAvailableInLocation)) {
            for (KitItem kitItem : kitItemList) {
                Optional<VirtualPosition> optionalKitItemVp =
                        kitItemsAvailableInLocation.stream().filter(
                                virtualPosition -> virtualPosition.getProductRef().equalsIgnoreCase(kitItem.getSku())
                        ).findFirst();

                if (optionalKitItemVp.isPresent()) {
                    optionalKitItemVp.get().setQuantity(optionalKitItemVp.get().getQuantity() - kitItem.getProductQty());
                }
            }
        }
    }
}