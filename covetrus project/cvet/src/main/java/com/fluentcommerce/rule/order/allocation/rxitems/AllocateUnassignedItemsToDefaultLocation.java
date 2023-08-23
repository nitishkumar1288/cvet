package com.fluentcommerce.rule.order.allocation.rxitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */

@RuleInfo(
        name = "AllocateUnassignedItemsToDefaultLocation",
        description = "Allocate unassigned items to default location and send event {" + PROP_EVENT_NAME + "}" ,
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
@Slf4j
public class AllocateUnassignedItemsToDefaultLocation extends BaseRule {

    private static final String CLASS_NAME = AllocateUnassignedItemsToDefaultLocation.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);


        List<PharmacyAllocationForOrderItems> unassignedAllocationItems =
        pharmacyAllocationForOrderItemsList.stream().filter(
                pharmacyAllocationForOrderItems -> StringUtils.isEmpty(pharmacyAllocationForOrderItems.getAssignedFacilityRef())
        ).collect(Collectors.toList());

        // assign the default location to the allocation items
        if(CollectionUtils.isNotEmpty(unassignedAllocationItems)){

            for(PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems : unassignedAllocationItems){
                pharmacyAllocationForOrderItems.setAssignedFacilityRef(pharmacyAllocationForOrderItems.getDefaultLocationRefInNetwork());
                pharmacyAllocationForOrderItems.setDefaultLocationSelected(true);
            }
        }

        EventUtils.forwardInline(
                context,
                eventName
        );
    }
}