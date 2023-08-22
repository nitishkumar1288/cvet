package com.fluentcommerce.rule.webhook.fulfilment;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentSettlementRequest;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.RuleUtils;
import com.fluentcommerce.util.SettingUtils;
import com.fluentcommerce.util.WebhookUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendPaymentSettlementRequestWebhook",
        description = "get endpoint url from setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "integration base URL"
)

@EventAttribute(name = PAYMENT_SETTLEMENT_REQUEST)
@Slf4j
public class SendPaymentSettlementRequestWebhook extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
            RuleUtils.validateRulePropsIsNotEmpty(context, PROP_WEBHOOK_SETTING_NAME);
            Event event = context.getEvent();
            String orderRef = event.getRootEntityRef();
            PaymentSettlementRequest requestData = context.getEvent().getAttribute(PAYMENT_SETTLEMENT_REQUEST, PaymentSettlementRequest.class);
            if (null != requestData ){
                String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);
                Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
                Map<String, Object> attributeMap = constructAttributeMap(requestData,orderRef);
                endPointURL.ifPresent(e -> context.action().postWebhook(e, WebhookUtils.constructWebhookEventWithAttribute(event,context,attributeMap)));
            }
    }
    private Map<String, Object> constructAttributeMap(PaymentSettlementRequest requestData,String orderRef) {
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ORDER_REF,orderRef);
        attributeMap.put(FULFILLMENT_REF,requestData.getFulfillmentRef());
        attributeMap.put(PAYMENT_TOKEN,requestData.getPaymentToken());
        attributeMap.put(PAYMENT_TYPE,requestData.getPaymentType());
        attributeMap.put(GRAND_TOTAL,requestData.getGrandTotal());
        attributeMap.put(ORDER_KEY,requestData.getOrderKey());
        return attributeMap;
    }
}
