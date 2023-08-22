package com.fluentcommerce.rule.webhook.fulfilment;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.SettingUtils;
import com.fluentcommerce.util.WebhookUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.fluentcommerce.util.Constants.FULFILMENT;
import static com.fluentcommerce.util.Constants.PROP_WEBHOOK_SETTING_NAME;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendFulfillmentWebhook",
        description = "get endpoint url from setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke",
        accepts = {
                @EventInfo(entityType = FULFILMENT)
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "integration base URL"
)
@Slf4j
public class SendFulfillmentWebhook extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);
        Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
        endPointURL.ifPresent(e -> context.action().postWebhook(e, WebhookUtils.constructWebhookEvent(event,context)));
    }
}
