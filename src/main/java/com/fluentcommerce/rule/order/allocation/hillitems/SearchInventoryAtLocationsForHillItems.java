package com.fluentcommerce.rule.order.allocation.hillitems;


import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.location.LocationDto;
import com.fluentcommerce.model.virtual.VirtualPosition;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.virtual.VirtualService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "SearchInventoryAtLocationsForHillItems",
        description = "Searches inventory based on the incoming event information using virtual catalogue with ref {" +
                PROP_VIRTUAL_CATALOGUE_REF + "}. Sends event {" + PROP_EVENT_NAME + "}.",
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
@ParamString(name = PROP_VIRTUAL_CATALOGUE_REF, description = "The virtual catalogue which will be searched")

@EventAttribute(name = PRODUCT_REF_LIST_HILL_ITEMS)
@EventAttribute(name = EVENT_FIELD_LOCATIONS)
@Slf4j
public class SearchInventoryAtLocationsForHillItems extends BaseRule {

    @Override
    public void run(ContextWrapper context) {

        final String eventName = context.getProp(PROP_EVENT_NAME);
        final String virtualCatalogueRef = context.getProp(PROP_VIRTUAL_CATALOGUE_REF);

        Map<String,Object> eventAttribute = context.getEvent().getAttributes();

        Map<String, LocationDto> locations = (Map<String, LocationDto>) eventAttribute.get(EVENT_FIELD_LOCATIONS);
        List<String> productRefsOfHillItems = ( List<String>)eventAttribute.get(PRODUCT_REF_LIST_HILL_ITEMS);

        VirtualService virtualService = new VirtualService(context);

        List<VirtualPosition> virtualPositions;
        if(locations.size() == 0){
            virtualPositions = new ArrayList<>();
        } else {
            virtualPositions = virtualService.searchVirtualPositions(virtualCatalogueRef, new ArrayList<>(locations.keySet()), productRefsOfHillItems);
        }

        eventAttribute.put(EVENT_FIELD_VIRTUAL_POSITIONS,virtualPositions);

        EventUtils.forwardInline(
                context,
                eventName
        );
    }
}
