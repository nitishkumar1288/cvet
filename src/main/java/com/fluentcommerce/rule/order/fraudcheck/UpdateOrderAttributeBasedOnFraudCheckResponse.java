package com.fluentcommerce.rule.order.fraudcheck;

import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.model.fraudcheck.FraudCheckResponse;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import com.fluentretail.rubix.v2.context.Context;
import com.fluentretail.rubix.v2.rule.Rule;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateOrderAttributeBasedOnFraudCheckResponse",
        description = "based on fraud check response call to approval event {" + FRAUD_CHECK_APPROVED_RESPONSE_EVENT+"} " +
                "OR declined event {" + FRAUD_CHECK_APPROVED_DECLINED_EVENT+"} OR in review event {" + FRAUD_CHECK_APPROVED_IN_REVIEW_EVENT+"}",
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
        name = FRAUD_CHECK_APPROVED_RESPONSE_EVENT,
        description = "approval event name"
)
@ParamString(
        name = FRAUD_CHECK_APPROVED_DECLINED_EVENT,
        description = "declined event name"
)
@ParamString(
        name = FRAUD_CHECK_APPROVED_IN_REVIEW_EVENT,
        description = "in review event name"
)
@EventAttribute(name = "attributes")
@Slf4j
public class UpdateOrderAttributeBasedOnFraudCheckResponse implements Rule {

    private static final String CLASS_NAME = UpdateOrderAttributeBasedOnFraudCheckResponse.class.getSimpleName();
    @Override
    public void run(Context context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,FRAUD_CHECK_APPROVED_RESPONSE_EVENT,
                FRAUD_CHECK_APPROVED_DECLINED_EVENT,FRAUD_CHECK_APPROVED_IN_REVIEW_EVENT);
        String approvedEvent = context.getProp(FRAUD_CHECK_APPROVED_RESPONSE_EVENT);
        String declinedEvent = context.getProp(FRAUD_CHECK_APPROVED_DECLINED_EVENT);
        String inReviewEvent = context.getProp(FRAUD_CHECK_APPROVED_IN_REVIEW_EVENT);
        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", context.getEvent().getAccountId(), CLASS_NAME, context.getEvent());
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        FraudCheckResponse fraudCheckResponse = CommonUtils.convertMapToDto(event.getAttributes(), FraudCheckResponse.class);
        updateOrderAndForwardEvent(context,order,fraudCheckResponse,orderService,approvedEvent,
                declinedEvent,inReviewEvent);

    }

    private void updateOrderAndForwardEvent(Context context, Order order, FraudCheckResponse fraudCheckResponse
            , OrderService orderService, String approvedEvent, String declinedEvent, String inReviewEvent) {
        if (Objects.nonNull(order)){
            List<AttributeInput> attributeInputList = getOrderAttributeForUpdate(fraudCheckResponse);
            orderService.upsertOrderAttributeList(context.getEvent().getEntityId(),attributeInputList);
            if (fraudCheckResponse.getFraudRiskResult() !=null ){
                if (fraudCheckResponse.getFraudRiskResult().equalsIgnoreCase(FRAUD_CHECK_RESPONSE_APPROVED)){
                    EventUtils.forwardInline(context,approvedEvent);
                }else if( fraudCheckResponse.getFraudRiskResult().equalsIgnoreCase(FRAUD_CHECK_RESPONSE_DECLINED)){
                    EventUtils.forwardInline(context,declinedEvent);
                }else if(fraudCheckResponse.getFraudRiskResult().equalsIgnoreCase(FRAUD_CHECK_RESPONSE_IN_REVIEW)){
                    EventUtils.forwardInline(context,inReviewEvent);
                }
            }
        }
    }

    private List<AttributeInput> getOrderAttributeForUpdate(FraudCheckResponse responseAttributes) {
        List<AttributeInput> attributeList = new ArrayList<>();
        AttributeInput orderSubTypeAttribute = AttributeInput.builder().name(ORDER_ATTRIBUTE_SUB_TYPE)
                .type(ATTRIBUTE_TYPE_STRING).value(responseAttributes.getEventSubTypeCodes()).build();
        attributeList.add(orderSubTypeAttribute);
        return attributeList;
    }
}