package com.fluentcommerce.rule.webhook.order;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentAuthReversalRequest;
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
        name = "SendPaymentAuthReversalWebhook",
        description = "get endpoint url from setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "integration base URL"
)

@EventAttribute(name = PAYMENT_AUTH_REVERSAL_REQUEST_FOR_ORDER)
@Slf4j
public class SendPaymentAuthReversalWebhook extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_WEBHOOK_SETTING_NAME);
        Event event = context.getEvent();
        PaymentAuthReversalRequest requestData = context.getEvent().getAttribute(PAYMENT_AUTH_REVERSAL_REQUEST_FOR_ORDER,
                PaymentAuthReversalRequest.class);
        if (null != requestData ){
            String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);
            Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
            Map<String, Object> attributeMap = constructAttributeMap(requestData);
            endPointURL.ifPresent(e -> context.action().postWebhook(e, WebhookUtils.constructWebhookEventWithAttribute(event,context,attributeMap)));
        }
    }
    private Map<String, Object> constructAttributeMap(PaymentAuthReversalRequest requestData) {
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ORDER_REF,requestData.getOrderRef());
        attributeMap.put(ORDER_KEY,requestData.getOrderKey());
        return attributeMap;
    }
}
