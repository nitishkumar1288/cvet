package com.fluentcommerce.rule.order.common.schedulerwindow;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.schedulerwindow.SchedulerWindow;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import static com.fluentcommerce.util.Constants.*;

/**
@author Nandhakumar
*/
@RuleInfo(
        name = "CheckRespTimeFitsInExistingActiveSchedulerWindow",
        description = "check approval response time fits in the active scheduled window in order attribute {" + PROP_ATTRIBUTE_NAME
                + "} ,if match call to   {" + PROP_EVENT_NAME + "} else call to no matching event{" + PROP_NO_MATCHING_EVENT_NAME + "}",
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
        description = "event name"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "no matching event name"
)
@ParamString(
        name = PROP_ATTRIBUTE_NAME,
        description = "as order attribute"
)
@Slf4j
public class CheckRespTimeFitsInExistingActiveSchedulerWindow extends BaseRule {
        @Override
        public void run(ContextWrapper context) {
                RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,PROP_NO_MATCHING_EVENT_NAME, PROP_ATTRIBUTE_NAME);
                String eventName = context.getProp(PROP_EVENT_NAME);
                String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);
                String attributeName = context.getProp(PROP_ATTRIBUTE_NAME);
                Event event = context.getEvent();
                String orderId = event.getEntityId();
                OrderService orderService = new OrderService(context);
                GetOrderByIdQuery.OrderById orderById = orderService.getOrderWithOrderAttributes(orderId);

                Optional<GetOrderByIdQuery.Attribute> optionalAttribute =
                        orderById.attributes().stream().filter(
                                attribute -> attribute.name().equalsIgnoreCase(attributeName)
                        ).findFirst();

                if(optionalAttribute.isPresent()){
                        SchedulerWindow schedulerWindow = null;
                        ObjectMapper mapper = new ObjectMapper();
                        try{
                                schedulerWindow = mapper.readValue(optionalAttribute.get().value().toString(), new TypeReference<SchedulerWindow>(){});
                        } catch (IOException exception){
                                context.addLog("Exception Occurred when mapping schedulerWindowList " + exception.getMessage());
                                return;
                        }

                        boolean inBetween = false;
                        Date from = CommonUtils.formatDate(schedulerWindow.getFrom(),UTC_DATE_FORMATTER);
                        Date to = CommonUtils.formatDate(schedulerWindow.getTo(),UTC_DATE_FORMATTER);

                        Instant currentTime = Instant.now();
                        String currentTimeUtc = CommonUtils.getTimeInUtcStringFormat(currentTime);
                        context.addLog("currentTime " + currentTimeUtc);
                        Date current = CommonUtils.formatDate(currentTimeUtc,UTC_DATE_FORMATTER);

                        if(current.after(from) && current.before(to)){
                                inBetween = true;
                        }

                        // check whether the response time fits in the active scheduler window
                        if(inBetween){
                                context.addLog("Scheduler window is active");
                                EventUtils.forwardEventInlineWithAttributes(context, eventName, event.getAttributes());
                        }else{
                                EventUtils.forwardInline(context,noMatchEventName);
                        }

                }
        }
}
