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
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "FindNetworkIsFoundForAllLineItems",
        description = "Find Network is found for all line items and send event {" + PROP_EVENT_NAME + "}" ,
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
public class FindNetworkIsFoundForAllLineItems extends BaseRule {

    private static final String CLASS_NAME = FindNetworkIsFoundForAllLineItems.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);

        // skip the items for which the location is predetermined
        Optional<PharmacyAllocationForOrderItems> emptyNetworkData =
                pharmacyAllocationForOrderItemsList.stream().filter(
                        dto -> (StringUtils.isEmpty(dto.getAssignedFacilityRef()) &&  StringUtils.isEmpty(dto.getNetworkRef()))
                ).findFirst();

        // if there is no empty network data proceed , else return
       if(!emptyNetworkData.isPresent()){
           EventUtils.forwardInline(
                   context,
                   eventName
           );
       }else{
           context.addLog("Network is not found for one of the items");
       }
    }
}