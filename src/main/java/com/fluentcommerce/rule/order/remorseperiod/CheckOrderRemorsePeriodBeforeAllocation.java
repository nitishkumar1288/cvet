package com.fluentcommerce.rule.order.remorseperiod;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.SettingUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "CheckOrderRemorsePeriodBeforeAllocation",
        description = "Check the order remorse period window before allocation and call the {" + PROP_EVENT_NAME + "} as inline or scheduled event using time measurement in unit {" + TIME_MEASURMENT_UNIT + "} and setting {" + PROP_SETTING_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = ENTITY_TYPE_INVENTORY_CATALOGUE,
                        entitySubtype = DEFAULT
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)

@ParamString(
        name = PROP_EVENT_NAME,
        description = "event name"
)
@ParamString(
        name = TIME_MEASURMENT_UNIT,
        description = "time measurement unit for the scheduled event"
)
@ParamString(
        name = PROP_SETTING_NAME,
        description = "setting name for waiting time"
)
@Slf4j
public class CheckOrderRemorsePeriodBeforeAllocation extends BaseRule {

    private static final String CLASS_NAME = CheckOrderRemorsePeriodBeforeAllocation.class.getSimpleName();

    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        String settingNameForWaitingTime = context.getProp(PROP_SETTING_NAME);
        String measurementUnit = context.getProp(TIME_MEASURMENT_UNIT);

        Event event = context.getEvent();
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderById = orderService.getOrderWithOrderAttributes(orderId);

        // Check whether the allocation logic falls between the buyer remorse period and call the scheduled event accordingly
        Date currentTime = new Date(System.currentTimeMillis());
        Date fromTime = (Date) orderById.createdOn();

        long toTimeInMilliSeconds = fromTime.getTime();

        Optional<String> waitingTime = SettingUtils.getSettingValue(context, settingNameForWaitingTime);
        if(waitingTime.isPresent()) {
            context.addLog("measurementUnit " + measurementUnit);

            Integer delayInSeconds = SettingUtils.getWaitingTimePeriod(measurementUnit, Integer.parseInt(waitingTime.get()));

            long delayInMilliSeconds = (long)delayInSeconds * 1000 ;

            toTimeInMilliSeconds = toTimeInMilliSeconds + delayInMilliSeconds;
        }

        Date toTime = new Date(toTimeInMilliSeconds);

        context.addLog("currentTime : " + currentTime);
        context.addLog("fromTime : " + fromTime);
        context.addLog("toTime : " + toTime);

        boolean inBetween = false;
        if(currentTime.after(fromTime) && currentTime.before(toTime)){
            inBetween = true;
        }

        if(inBetween){
            context.addLog("Event will be executed at " +  toTime);
            EventUtils.sendScheduledEventWithAttributes(
                    context,
                    eventName,
                    event,
                    context.getEvent().getAttributes(),
                    toTime,
                    CLASS_NAME);
        }else{
            EventUtils.forwardInline(context,eventName);
        }
    }
}
