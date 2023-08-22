package com.fluentcommerce.rule.order.allocation.hillitems;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.location.LocationDto;
import com.fluentcommerce.dto.orderitem.HillProductsDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.util.Constants.ENTITY_TYPE_ORDER;
import static com.fluentcommerce.util.Constants.*;
import static com.fluentcommerce.util.Constants.PROP_EVENT_NAME;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "MapEventAttributesFormatForHillItems",
        description = "Map the event attributes format for hill items . Sends event {" + PROP_EVENT_NAME + "}.",
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
        description = "Event name to be called"
)

@EventAttribute(name = EVENT_HILL_ORDER_ITEMS_ALLOCATION)
@EventAttribute(name = EVENT_FIELD_LOCATIONS)
@Slf4j
public class MapEventAttributesFormatForHillItems extends BaseRule {

    @Override
    public void run(ContextWrapper context) {

        final String eventName = context.getProp(PROP_EVENT_NAME);

        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<HillProductsDto> hillProductsDtoList =
                CommonUtils.convertObjectToDto(eventAttributes.get(EVENT_HILL_ORDER_ITEMS_ALLOCATION),new TypeReference<List<HillProductsDto>>(){});

        Map<String, LocationDto> locations =
                CommonUtils.convertObjectToDto(eventAttributes.get(EVENT_FIELD_LOCATIONS),new TypeReference<Map<String, LocationDto>>(){});

        // Event Map structure is updated
        Map<String, Object> attributes = new HashMap<>();
        // Put all the attributes first and then over write that
        attributes.putAll(context.getEvent().getAttributes());
        attributes.put(EVENT_FIELD_LOCATIONS,locations);
        attributes.put(EVENT_HILL_ORDER_ITEMS_ALLOCATION,hillProductsDtoList);

        // this is done to prevent the class cast exception
        if(eventAttributes.containsKey(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)) {
            List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = CommonUtils.convertObjectToDto(eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS),new TypeReference<List<PharmacyAllocationForOrderItems>>(){});
            attributes.put(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS,pharmacyAllocationForOrderItemsList);
        }

        EventUtils.forwardEventInlineWithAttributes(
                context,
                eventName,
                attributes
        );
    }
}
