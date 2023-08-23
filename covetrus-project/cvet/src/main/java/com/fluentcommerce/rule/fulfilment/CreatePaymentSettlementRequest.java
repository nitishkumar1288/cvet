package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentInfo;
import com.fluentcommerce.dto.payment.PaymentSettlementRequest;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CreatePaymentSettlementRequest",
        description = "create the payment settlement request and call to next event {" + PROP_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)

@Slf4j
public class CreatePaymentSettlementRequest extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        String fulfillmentId = event.getEntityId();
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfillmentId);
        PaymentSettlementRequest paymentSettlementRequest = createPaymentAuthRequest(fulfillmentById,context,fulfillmentId);
        if (null != paymentSettlementRequest){
            Map<String, Object> attributes = new HashMap<>();
            attributes.put(PAYMENT_SETTLEMENT_REQUEST, paymentSettlementRequest);
            attributes.putAll(context.getEvent().getAttributes());
            EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
        }
    }
    private PaymentSettlementRequest createPaymentAuthRequest(GetFulfilmentByIdQuery.FulfilmentById fulfillmentById,
                                                              ContextWrapper context,String fulfillmentId) {
        PaymentSettlementRequest paymentSettlementRequest = null;
        String orderKey = null;
        if (Objects.nonNull(fulfillmentById) && null != fulfillmentById.order() && null != fulfillmentById.attributes()) {
            Optional<GetFulfilmentByIdQuery.Attribute1> optionalTotalPrice = fulfillmentById.attributes().stream()
                    .filter(attribute -> attribute.name().equalsIgnoreCase(TOTAL_PRICE_AT_FULFILMENT_LEVEL)).findFirst();
            Optional<GetFulfilmentByIdQuery.Attribute> optionalPaymentInfoAttribute = fulfillmentById.order().attributes().
                    stream().filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_INFO)).findFirst();
            Optional<GetFulfilmentByIdQuery.Attribute> optionalOrderKeyAttribute = fulfillmentById.order().attributes().
                    stream().filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY)).findFirst();
            if (optionalOrderKeyAttribute.isPresent() && null != optionalOrderKeyAttribute.get().value().toString()){
                orderKey = optionalOrderKeyAttribute.get().value().toString();
            }
            PaymentInfo paymentInfo = fetchPaymentInfoAttribute(optionalPaymentInfoAttribute,context);
            if (null != fulfillmentById.ref() && optionalTotalPrice.isPresent() && null != paymentInfo &&
                    null != paymentInfo.getPaymentType()  && null != paymentInfo.getPaymentToken()) {
                paymentSettlementRequest = new PaymentSettlementRequest();
                paymentSettlementRequest.setFulfillmentRef(fulfillmentById.ref());
                paymentSettlementRequest.setPaymentToken(paymentInfo.getPaymentToken());
                paymentSettlementRequest.setPaymentType(paymentInfo.getPaymentType());
                paymentSettlementRequest.setGrandTotal((float)Double.parseDouble(optionalTotalPrice.get().value().toString()));
                paymentSettlementRequest.setOrderKey(orderKey);
            }else {
                context.addLog("Required data is missing for create payment settlement request for the fulfilment id::"+ fulfillmentId);
            }
        }
        return paymentSettlementRequest;
    }
    private PaymentInfo fetchPaymentInfoAttribute(Optional<GetFulfilmentByIdQuery.Attribute> optionalPaymentInfoAttribute, ContextWrapper context) {
        PaymentInfo paymentInfo = null;
        if (optionalPaymentInfoAttribute.isPresent()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                paymentInfo = mapper.readValue(optionalPaymentInfoAttribute.get().value().toString(),
                        new TypeReference<PaymentInfo>() {});
            } catch (IOException exception) {
                context.addLog("Exception Occurred when fetching payment info attribute " + exception.getMessage());
                return null;
            }
        }
        return paymentInfo;
    }
}