package com.fluentcommerce.rule.webhook.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.order.status.OrderStatusPayload;
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
        name = "SendWebhookForOrderStatus",
        description = "get endpoint url from setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke",
        accepts = {
                @EventInfo(entityType = "ORDER")
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "integration base URL"
)

@EventAttribute(name = ORDER_STATUS_PAYLOAD)
@Slf4j
public class SendWebhookForOrderStatus extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_WEBHOOK_SETTING_NAME);
        Event event = context.getEvent();
        String cancelledOrderStatus = event.getEntityStatus();
        OrderStatusPayload  payload = CommonUtils.convertObjectToDto(event.getAttributes().get(ORDER_STATUS_PAYLOAD),
                new TypeReference<OrderStatusPayload>(){});

        if (null != payload && !CANCELLED.equalsIgnoreCase(cancelledOrderStatus)){
            Map<String,Object> attributeMap = constructAttributeMap(payload);
            String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);
            Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
            endPointURL.ifPresent(e -> context.action().postWebhook(e,
                    WebhookUtils.constructWebhookEventWithAttribute(event,context,attributeMap)));
        }
    }
    private Map<String, Object> constructAttributeMap(OrderStatusPayload payload) {
        Map<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ORDER_KEY,payload.getOrderKey());
        attributeMap.put(ANIMAL_CARE_BUSINESS,payload.getAnimalCareBusiness());
        return attributeMap;
    }
}
