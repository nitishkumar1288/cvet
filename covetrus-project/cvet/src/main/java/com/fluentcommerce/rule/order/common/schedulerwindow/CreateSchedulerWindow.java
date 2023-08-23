package com.fluentcommerce.rule.order.common.schedulerwindow;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.schedulerwindow.SchedulerWindow;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.SettingUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
@author Nandhakumar
*/

@RuleInfo(
        name = "CreateSchedulerWindow",
        description = "create the scheduler window based on allocation trigger waiting time {" + PROP_SETTING_NAME + "}" +
                "and measurement unit{" + TIME_MEASURMENT_UNIT + "}. Add as order attribute {" + PROP_ATTRIBUTE_NAME + "}.",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_SETTING_NAME,
        description = "allocation trigger waiting time"
)
@ParamString(
        name = TIME_MEASURMENT_UNIT,
        description = "time measurement unit"
)
@ParamString(
        name = PROP_ATTRIBUTE_NAME,
        description = "adding as order attribute"
)

@EventAttribute(name = WEBHOOK_UUID)
@Slf4j
public class CreateSchedulerWindow extends BaseRule {

    @Override
    public void run(ContextWrapper context) {
        String allocatedWaitingTime = context.getProp(PROP_SETTING_NAME);
        String measurementUnit = context.getProp(TIME_MEASURMENT_UNIT);
        String attributeName = context.getProp(PROP_ATTRIBUTE_NAME);

        Event event = context.getEvent();
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderById = orderService.getOrderWithOrderAttributes(orderId);
        Optional<String> waitingTime = SettingUtils.getSettingValue(context,allocatedWaitingTime);

        context.addLog("waiting time" + waitingTime.isPresent());

        Optional<GetOrderByIdQuery.Attribute> optionalAttribute =
                orderById.attributes().stream().filter(
                        attribute -> attribute.name().equalsIgnoreCase(attributeName)
                ).findFirst();

        if(!optionalAttribute.isPresent() && waitingTime.isPresent()){
            Map<String,Object> eventAttributes = context.getEvent().getAttributes();
            if(eventAttributes.containsKey(WEBHOOK_UUID)){
                String uuid = (String) eventAttributes.get(WEBHOOK_UUID);
                context.addLog("UUID value " + uuid);

                // Create Scheduler Window
                SchedulerWindow schedulerWindow = creteScheduleWindowData(
                        context,
                        uuid,
                        measurementUnit,
                        waitingTime.get()
                );

                // Add the scheduler window to the order attributes
                List<AttributeInput> attributeInputList = createNewAttributeInputAsList(
                        attributeName,
                        ATTRIBUTE_TYPE_JSON,
                        schedulerWindow);

                orderService.upsertOrderAttributeList(orderId,attributeInputList);
            }
        }
    }

    private List<AttributeInput> createNewAttributeInputAsList(String schedulerWindow, String type, SchedulerWindow schedulerWindowList) {
        AttributeInput attrInput = AttributeInput.builder().name(schedulerWindow).type(type).value(schedulerWindowList).build();
        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(attrInput);
        return attributeInputList;
    }

    private SchedulerWindow creteScheduleWindowData(
            ContextWrapper context,
            String uuid,
            String measurementUnit,
            String waitingTime
    ) {
        Instant currentTime = Instant.now();

        String currentTimeUtc = CommonUtils.getTimeInUtcStringFormat(currentTime);
        context.addLog("currentTime " + currentTimeUtc);

        SchedulerWindow schedulerWindow = new SchedulerWindow();
        schedulerWindow.setFrom(currentTimeUtc);
        schedulerWindow.setTo(CommonUtils.getScheduledTime(currentTime,waitingTime,measurementUnit));


        context.addLog("schedulerWindow.getTo " + schedulerWindow.getTo());
        context.addLog("UUID " + uuid);
        return schedulerWindow;
    }
}
