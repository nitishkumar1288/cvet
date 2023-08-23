package com.fluentcommerce.rule.webhook.order;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.model.order.OrderNotes;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendWebhookWithAttributes",
        description = "get endpoint url from setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke",
        accepts = {
                @EventInfo(entityType = "ORDER")
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "integration base URL"
)

@EventAttribute(name = ORDER_KEY)
@EventAttribute(name = ORDER_NOTES)
@Slf4j
public class SendWebhookWithAttributes extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_WEBHOOK_SETTING_NAME);
        Event event = context.getEvent();
        String orderId = event.getEntityId();
        String orderRef = event.getEntityRef();
        String orderKey = context.getEvent().getAttribute(ORDER_KEY,String.class);
        context.addLog("eventData::"+"orderKey:"+orderKey);
        List<OrderNotes> orderNoteList = context.getEvent().getAttributeList(ORDER_NOTES, OrderNotes.class);
        if (null != orderNoteList && !orderNoteList.isEmpty()){
            String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);
            Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
            Map<String, Object> attributeMap = constructAttributeMap(orderNoteList,orderId,orderRef,orderKey);
            endPointURL.ifPresent(e -> context.action().postWebhook(e, WebhookUtils.constructWebhookEventWithAttribute(event,context,attributeMap)));
        }
    }

    private Map<String, Object> constructAttributeMap(List<OrderNotes> orderNoteList,String orderId,String orderRef,String orderKey) {
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(REF,orderRef);
        attributeMap.put(ID,orderId);
        attributeMap.put(ORDER_KEY,orderKey);
        attributeMap.put(ORDER_NOTES,orderNoteList);
        return attributeMap;
    }
}