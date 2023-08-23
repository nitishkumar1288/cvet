package com.fluentcommerce.rule.webhook.order;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentInfo;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.OrderUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentcommerce.util.SettingUtils;
import com.fluentcommerce.util.WebhookUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;
/**
 @author nitishKumar
 */
@RuleInfo(
        name = "SendCreditCardUpdateWebhook",
        description = "get endpoint url from setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke",
        accepts = {
                @EventInfo(entityType = "ORDER")
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "integration base URL"
)
public class SendCreditCardUpdateWebhook extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_WEBHOOK_SETTING_NAME);
        Event event = context.getEvent();
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderItems(orderId);
        String orderKey = null;
        String animalBusinessCare = null;
        Optional<GetOrderByIdQuery.Attribute> orderKeyAttribute =
                orderData.attributes().stream()
                        .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY))
                        .findFirst();
        if (orderKeyAttribute.isPresent() && null != orderKeyAttribute.get().value().toString()){
            orderKey = orderKeyAttribute.get().value().toString();
        }
        Optional<GetOrderByIdQuery.Attribute> animalBusinessCareAttribute =
                orderData.attributes().stream()
                        .filter(attribute -> attribute.name().equalsIgnoreCase(ANIMAL_CARE_BUSINESS))
                        .findFirst();
        if (animalBusinessCareAttribute.isPresent() && null != animalBusinessCareAttribute.get().value().toString()){
            animalBusinessCare = animalBusinessCareAttribute.get().value().toString();
        }
        Optional<GetOrderByIdQuery.Attribute> optionalPaymentInfo = orderData.attributes().
                stream().filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_INFO)).findFirst();
        PaymentInfo paymentInfo =  OrderUtils.fetchPaymentInfoAttribute(optionalPaymentInfo,context);
        if (null != paymentInfo){
            Map<String,Object> attributeMap = constructAttributeMap(paymentInfo,animalBusinessCare,orderKey,orderData);
            String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);
            Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
            endPointURL.ifPresent(e -> context.action().postWebhook(e,
                    WebhookUtils.constructWebhookEventWithAttribute(event,context,attributeMap)));
        }
    }

    private Map<String, Object> constructAttributeMap(PaymentInfo paymentInfo, String animalBusinessCare, String orderKey,
                                                      GetOrderByIdQuery.OrderById orderData) {
        Map<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ORDER_REF,orderData.ref());
        attributeMap.put(ORDER_KEY,orderKey);
        if (null != animalBusinessCare){
            attributeMap.put(ANIMAL_CARE_BUSINESS,animalBusinessCare);
        }
        attributeMap.put(PAYMENT_INFO,paymentInfo);
        return attributeMap;
    }
}