package com.fluentcommerce.rule.webhook.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.order.status.OrderItemStatusPayload;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendWebhookForOrderItemStatus",
        description = "get endpoint url from setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke",
        accepts = {
                @EventInfo(entityType = "ORDER")
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "integration base URL"
)

@EventAttribute(name = ORDER_ITEM_STATUS_PAYLOAD)
@Slf4j
public class SendWebhookForOrderItemStatus extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_WEBHOOK_SETTING_NAME);
        Event event = context.getEvent();
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderItems(orderId);
        String animalBusinessCare = null;
        String orderKey = null;
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
        OrderItemStatusPayload payload = CommonUtils.convertObjectToDto(event.getAttributes().get(ORDER_ITEM_STATUS_PAYLOAD),
                        new TypeReference<OrderItemStatusPayload>(){});
        if (null != payload){
            Map<String,Object> attributeMap = constructAttributeMap(payload,orderKey,animalBusinessCare);
            String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);
            Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
            endPointURL.ifPresent(e -> context.action().postWebhook(e,
                    WebhookUtils.constructWebhookEventWithAttribute(event,context,attributeMap)));
        }
    }
    private Map<String, Object> constructAttributeMap(OrderItemStatusPayload payload,String orderKey,String animalBusinessCare) {
        Map<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ORDER_REF,payload.getOrderRef());
        attributeMap.put(UPDATE_ON,payload.getUpdateOn());
        attributeMap.put(ORDER_KEY,orderKey);
        if (null != animalBusinessCare){
            attributeMap.put(ANIMAL_CARE_BUSINESS,animalBusinessCare);
        }
        attributeMap.put(ITEMS,payload.getItems());
        return attributeMap;
    }
}