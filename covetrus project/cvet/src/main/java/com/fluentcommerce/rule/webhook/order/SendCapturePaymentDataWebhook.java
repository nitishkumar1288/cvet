package com.fluentcommerce.rule.webhook.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentData;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentcommerce.util.SettingUtils;
import com.fluentcommerce.util.WebhookUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendCapturePaymentDataWebhook",
        description = "get endpoint url from setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke",
        accepts = {
                @EventInfo(entityType = "ORDER")
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "integration base URL"
)

@EventAttribute(name = CAPTURED_PAYMENT_DATA)
@Slf4j
public class SendCapturePaymentDataWebhook extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_WEBHOOK_SETTING_NAME);
        Event event = context.getEvent();
        PaymentData requestData = CommonUtils.convertObjectToDto(event.getAttributes().get(CAPTURED_PAYMENT_DATA),
                new TypeReference<PaymentData>(){});
        if (null != requestData){
            String orderKey = null;
            String animalCareBusiness = null;
            String orderRef = requestData.getOrderRef();
            OrderService orderService = new OrderService(context);
            GetOrdersByRefQuery.Edge orderEdge = orderService.getOrdersByRef(orderRef);
            if (null != orderEdge && null != orderEdge.node() && CollectionUtils.isNotEmpty(orderEdge.node().attributes())){
                Optional<GetOrdersByRefQuery.Attribute> orderKeyAttribute = orderEdge.node().attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY)).findFirst();
                if (orderKeyAttribute.isPresent() && null != orderKeyAttribute.get().value().toString()){
                    orderKey = orderKeyAttribute.get().value().toString();
                }
                Optional<GetOrdersByRefQuery.Attribute> animalCareBusinessAttribute = orderEdge.node().attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(ANIMAL_CARE_BUSINESS)).findFirst();
                if (animalCareBusinessAttribute.isPresent() && null != animalCareBusinessAttribute.get().value().toString()){
                    animalCareBusiness = animalCareBusinessAttribute.get().value().toString();
                }
            }
            String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);
            Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
            Map<String, Object> attributeMap = constructAttributeMap(requestData,orderKey,animalCareBusiness);
            endPointURL.ifPresent(e -> context.action().postWebhook(e, WebhookUtils.constructWebhookEventWithAttribute(event,context,attributeMap)));
        }
    }

    private Map<String, Object> constructAttributeMap(PaymentData requestData,String orderKey,String animalCareBusiness) {
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ORDER_REF,requestData.getOrderRef());
        attributeMap.put(TRANSACTION_KEY,requestData.getTransactionKey());
        attributeMap.put(TRANSACTION_TYPE,requestData.getTransactionType());
        attributeMap.put(STATUS,requestData.getStatus());
        attributeMap.put(PAYMENT_TYPE,requestData.getPaymentType());
        attributeMap.put(TOTAL_PRICE,requestData.getTotalPrice());
        attributeMap.put(CREATED_ON,requestData.getCreatedOn());
        attributeMap.put(PAYMENT_TOKEN,requestData.getPaymentToken());
        attributeMap.put(FULFILLMENT_REF,requestData.getFulfilmentRef());
        attributeMap.put(AUTH_NUMBER,requestData.getAuthNumber());
        attributeMap.put(PROCESSOR_ID,requestData.getProcessorId());
        attributeMap.put(ORDER_KEY,orderKey);
        attributeMap.put(ANIMAL_CARE_BUSINESS,animalCareBusiness);
        return attributeMap;
    }
}
