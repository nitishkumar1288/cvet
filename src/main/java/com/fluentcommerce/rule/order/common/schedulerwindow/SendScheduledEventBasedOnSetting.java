package com.fluentcommerce.rule.order.common.schedulerwindow;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.SettingUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.fluentcommerce.util.Constants.*;
import static com.fluentcommerce.util.EventUtils.sendScheduledEventWithAttributes;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendScheduledEventBasedOnSetting",
        description = "Get the waiting time from settingName {" + PROP_SETTING_NAME + "} and time measurement in unit " +
                "{" + TIME_MEASURMENT_UNIT + "} and scheduled the event  {" + PROP_SCHEDULED_EVENT_NAME + "} and call the next event {" + PROP_EVENT_NAME + "} ",
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
        description = "setting name for waiting time"
)
@ParamString(
        name = PROP_SCHEDULED_EVENT_NAME,
        description = "Event to be scheduled"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "event name"
)
@ParamString(
        name = TIME_MEASURMENT_UNIT,
        description = "time measurement unit"
)
@Slf4j
public class SendScheduledEventBasedOnSetting extends BaseRule {
    private static final String CLASS_NAME = SendScheduledEventBasedOnSetting.class.getSimpleName();

    @Override
    public void run(ContextWrapper context) {
        String scheduledEventName  = context.getProp(PROP_SCHEDULED_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String settingNameForWaitingTime = context.getProp(PROP_SETTING_NAME);
        String measurementUnit = context.getProp(TIME_MEASURMENT_UNIT);
        Event incomingEvent = context.getEvent();

        Optional<String> waitingTime = SettingUtils.getSettingValue(context, settingNameForWaitingTime);
        if(waitingTime.isPresent()){
            context.addLog("measurementUnit " + measurementUnit);

            Integer delayInSeconds = SettingUtils.getWaitingTimePeriod(measurementUnit, Integer.parseInt(waitingTime.get()));

            // Generating a UUID for the webhook
            UUID webHookUUID = UUID.randomUUID();
            Map<String,Object> webHookAttributesMap = new HashMap<>();
            webHookAttributesMap.put(WEBHOOK_UUID, webHookUUID.toString());

            sendScheduledEventWithAttributes(
                    context,
                    scheduledEventName,
                    incomingEvent,
                    webHookAttributesMap,
                    delayInSeconds,
                    CLASS_NAME);

            // call the next event and UUID is added to the attribute list
            Map<String, Object> eventAttributes = new HashMap<>();
            eventAttributes.putAll(context.getEvent().getAttributes());
            eventAttributes.put(WEBHOOK_UUID,webHookUUID.toString());

            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    eventAttributes
            );
        }
    }
}