package com.fluentcommerce.rule.order.common.schedulerwindow;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.schedulerwindow.SchedulerWindow;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.SettingUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.fluentcommerce.util.Constants.*;

@RuleInfo(
        name = "ModifySchedulerInformation",
        description = "Modify scheduler window information using setting {" + PROP_SETTING_NAME + "} and measurement unit{" + TIME_MEASURMENT_UNIT + "}",
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
@Slf4j
public class ModifySchedulerInformation extends BaseRule {

    @Override
    public void run(ContextWrapper context) {
        String allocatedWaitingTime = context.getProp(PROP_SETTING_NAME);
        String measurementUnit = context.getProp(TIME_MEASURMENT_UNIT);

        Event event = context.getEvent();
        String orderId = event.getEntityId();
        Optional<String> waitingTime = SettingUtils.getSettingValue(context,allocatedWaitingTime);

        context.addLog("waiting time" + waitingTime.isPresent());

        if(waitingTime.isPresent()){

            OrderService orderService = new OrderService(context);
            GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);

            Optional<GetOrderByIdQuery.Attribute> optionalAttributeInput =
                    orderData.attributes().stream()
                            .filter(attribute -> attribute.name().equalsIgnoreCase(SCHEDULER_WINDOW))
                            .findFirst();

            // if the attribute is present then proceed further
            if(optionalAttributeInput.isPresent()) {

                SchedulerWindow schedulerWindow = null;
                ObjectMapper mapper = new ObjectMapper();
                try {
                    schedulerWindow = mapper.readValue(optionalAttributeInput.get().value().toString(), new TypeReference<SchedulerWindow>() {
                    });
                } catch (IOException exception) {
                    context.addLog("Exception Occured when mapping schedulerWindowList " + exception.getMessage());
                    return;
                }

                Instant currentTime = Instant.now();

                String currentTimeUtc = CommonUtils.getTimeInUtcStringFormat(currentTime);
                context.addLog("currentTime " + currentTimeUtc);
                schedulerWindow.setFrom(currentTimeUtc);

                schedulerWindow.setTo(CommonUtils.getScheduledTime(currentTime,waitingTime.get(),measurementUnit));


                context.addLog("Modified Scheduler information  " + schedulerWindow);

                // Add the scheduler window to the order attributes
                List<AttributeInput> attributeInputList = createAttributeInputAsList(
                        SCHEDULER_WINDOW,
                        ATTRIBUTE_TYPE_JSON,
                        schedulerWindow);

                orderService.upsertOrderAttributeList(orderId,attributeInputList);
            }
        }
    }

    private List<AttributeInput> createAttributeInputAsList(String schedulerWindow, String type, SchedulerWindow schedulerWindowData) {
        AttributeInput attrInput = AttributeInput.builder()
                .name(schedulerWindow)
                .type(type)
                .value(schedulerWindowData)
                .build();
        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(attrInput);
        return attributeInputList;
    }
}
