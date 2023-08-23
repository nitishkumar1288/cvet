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
        name = "CheckNonPreAssignedFacilityItemsInAllocation",
        description = "check non pre assigned items are available in allocation and send event {" + PROP_EVENT_NAME + "} else  {"+ PROP_NO_MATCHING_EVENT_NAME +"}" ,
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
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "No match Event Name"
)

@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@Slf4j
public class CheckNonPreAssignedFacilityItemsInAllocation extends BaseRule {

    private static final String CLASS_NAME = CheckNonPreAssignedFacilityItemsInAllocation.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);

        List<PharmacyAllocationForOrderItems> remainingOrderItems =
                pharmacyAllocationForOrderItemsList.stream().filter(
                        allocationItems -> StringUtils.isEmpty(allocationItems.getAssignedFacilityId())
                ).collect(Collectors.toList());

        if(CollectionUtils.isNotEmpty(remainingOrderItems)){
            context.addLog("Non pre assigned items available in allocation");
            EventUtils.forwardInline(
                    context,
                    eventName
            );

        }else{
            context.addLog("Only pre assigned items available in allocation");
            EventUtils.forwardInline(
                    context,
                    noMatchEventName
            );
        }
    }
}