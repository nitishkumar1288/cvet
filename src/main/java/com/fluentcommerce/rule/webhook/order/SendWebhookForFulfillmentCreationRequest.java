package com.fluentcommerce.rule.webhook.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.FulfillmentRequestData;
import com.fluentcommerce.rule.BaseRule;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;
/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendWebhookForFulfillmentCreationRequest",
        description = "get endpoint url from setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "integration base URL"
)

@EventAttribute(name = FULFILLMENT_REQUEST_PAYLOAD)
@Slf4j
public class SendWebhookForFulfillmentCreationRequest extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_WEBHOOK_SETTING_NAME);
        Event event = context.getEvent();
        FulfillmentRequestData requestData = CommonUtils.convertObjectToDto(event.getAttributes().get(FULFILLMENT_REQUEST_PAYLOAD),
                new TypeReference<FulfillmentRequestData>(){});
        if (null != requestData){
            String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);
            Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
            Map<String, Object> attributeMap = constructAttributeMap(requestData);
            endPointURL.ifPresent(e -> context.action().postWebhook(e, WebhookUtils.constructWebhookEventWithAttribute(event,context,attributeMap)));
        }
    }

    private Map<String, Object> constructAttributeMap(FulfillmentRequestData requestData) {
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ORDER_KEY,requestData.getOrderKey());
        attributeMap.put(ORDER_REF,requestData.getOrderRef());
        attributeMap.put(ITEMS,requestData.getItems());
        return attributeMap;
    }
}
