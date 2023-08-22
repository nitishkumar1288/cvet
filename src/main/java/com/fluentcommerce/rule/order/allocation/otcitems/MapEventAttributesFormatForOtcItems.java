package com.fluentcommerce.rule.order.allocation.otcitems;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.location.LocationDto;
import com.fluentcommerce.dto.orderitem.OtcProductsDto;
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

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "MapEventAttributesFormatForOtcItems",
        description = "Map the event attributes format for otc items . Sends event {" + PROP_EVENT_NAME + "}.",
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

@EventAttribute(name = EVENT_OTC_ORDER_ITEMS_ALLOCATION)
@EventAttribute(name = EVENT_FIELD_LOCATIONS)
@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@Slf4j
public class MapEventAttributesFormatForOtcItems extends BaseRule {
    @Override
    public void run(ContextWrapper context) {

        final String eventName = context.getProp(PROP_EVENT_NAME);

        Map<String,Object> eventAttribute = context.getEvent().getAttributes();
        List<OtcProductsDto> otcProductsDtoList =
                CommonUtils.convertObjectToDto(eventAttribute.get(EVENT_OTC_ORDER_ITEMS_ALLOCATION),new TypeReference<List<OtcProductsDto>>(){});

        Map<String, LocationDto> locations =
                CommonUtils.convertObjectToDto(eventAttribute.get(EVENT_FIELD_LOCATIONS),new TypeReference<Map<String, LocationDto>>(){});

        // Event Map structure is updated
        Map<String, Object> attributes = new HashMap<>();
        // Put all the attributes first and then over write that
        attributes.putAll(context.getEvent().getAttributes());
        attributes.put(EVENT_FIELD_LOCATIONS,locations);
        attributes.put(EVENT_OTC_ORDER_ITEMS_ALLOCATION,otcProductsDtoList);

        // this is done to prevent the class cast exception
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
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
