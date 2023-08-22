package com.fluentcommerce.rule.order.allocation.otcitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.location.LocationDto;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.SettingUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

@RuleInfo(
        name = "CheckCapacityIsEnabledForAllocation",
        description = "check whether the allocation is enabled or not based on the setting {" + PROP_SETTING_NAME + "}" +
                "and call the event name {" + PROP_EVENT_NAME +"} if enabled else call the no match event name {"+ PROP_NO_MATCHING_EVENT_NAME +"}",
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
        name = PROP_SETTING_NAME,
        description = "settings name"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "event name to be called"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "no match event name"
)

@EventAttribute(name = EVENT_FIELD_LOCATIONS)
@Slf4j
public class CheckCapacityIsEnabledForAllocation extends BaseRule {

    @Override
    public void run(ContextWrapper context) {
        String settingName = context.getProp(PROP_SETTING_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);

        boolean isCapacityEnabled = false;
        Optional<String> optionalIsCapacityEnabled = SettingUtils.getSettingValue(context, settingName);
        if (optionalIsCapacityEnabled.isPresent()) {
            context.addLog("setting value is present");
            if (optionalIsCapacityEnabled.get().equalsIgnoreCase(TRUE)) {
                context.addLog("Capacity is enabled");
                isCapacityEnabled = true;
            }
        }
        if (isCapacityEnabled) {
            context.addLog("Capacity is enabled , so no changes in the parameter");
            EventUtils.forwardInline(
                    context,
                    eventName
            );
        } else {
            context.addLog("Capacity is not enabled , parameter has to be modified");

            Map<String,Object> eventAttribute = context.getEvent().getAttributes();
            Map<String, LocationDto> locations = (Map<String, LocationDto>) eventAttribute.get(EVENT_FIELD_LOCATIONS);

            Map<String,Integer> locationRefWithCapacity = new HashMap<>();
            for(Map.Entry<String,LocationDto> location : locations.entrySet()){
                locationRefWithCapacity.put(location.getKey(),Integer.MAX_VALUE);
            }
            // append the capacity map to the param list
            eventAttribute.put(EVENT_CAPACITY_FOR_LOCATION, locationRefWithCapacity);

            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    noMatchEventName,
                    eventAttribute
            );
        }
    }
}
