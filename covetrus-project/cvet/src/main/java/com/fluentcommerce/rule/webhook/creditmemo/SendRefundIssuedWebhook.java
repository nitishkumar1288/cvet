package com.fluentcommerce.rule.webhook.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.billingaccount.creditmemo.RefundProcessed;
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
        name = "SendRefundIssuedWebhook",
        description = "get endpoint url from setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_CREDIT_MEMO)
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "forward to next Event name"
)

@EventAttribute(name = PROCESSED_REFUND_DATA)
@Slf4j
public class SendRefundIssuedWebhook extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_WEBHOOK_SETTING_NAME);
        Event event = context.getEvent();
        RefundProcessed requestData = CommonUtils.convertObjectToDto(event.getAttributes().get(PROCESSED_REFUND_DATA),
                new TypeReference<RefundProcessed>(){});
        if (null != requestData){
            String animalBusinessCare = null;
            String orderKey = null;
            String orderRef = requestData.getOrderRef();
            OrderService orderService = new OrderService(context);
            GetOrdersByRefQuery.Edge orderEdge = orderService.getOrdersByRef(orderRef);
            if (null != orderEdge && null != orderEdge.node() && CollectionUtils.isNotEmpty(orderEdge.node().attributes())){
                Optional<GetOrdersByRefQuery.Attribute> orderKeyAttribute = orderEdge.node().attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY)).findFirst();
                Optional<GetOrdersByRefQuery.Attribute> animalBusinessCareAttribute = orderEdge.node().attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(ANIMAL_CARE_BUSINESS)).findFirst();
                if (animalBusinessCareAttribute.isPresent() && null != animalBusinessCareAttribute.get().value().toString()){
                    animalBusinessCare = animalBusinessCareAttribute.get().value().toString();
                }
                if (orderKeyAttribute.isPresent() && null != orderKeyAttribute.get().value().toString()){
                    orderKey = orderKeyAttribute.get().value().toString();
                }
            }
            String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);
            Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
            Map<String, Object> attributeMap = constructAttributeMap(requestData,orderKey,animalBusinessCare);
            endPointURL.ifPresent(e -> context.action().postWebhook(e, WebhookUtils.constructWebhookEventWithAttribute(event,context,attributeMap)));
        }
    }

    private Map<String, Object> constructAttributeMap(RefundProcessed requestData,String orderKey,String animalBusinessCare) {
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ORDER_REF,requestData.getOrderRef());
        attributeMap.put(PAYMENT_TYPE,requestData.getPaymentType());
        attributeMap.put(CARD_NUMBER_LAST_FOUR,requestData.getCardNumberLastFour());
        attributeMap.put(STATUS,requestData.getStatus());
        attributeMap.put(AMOUNT,requestData.getAmount());
        attributeMap.put(REFUND_KEY,requestData.getRefundKey());
        attributeMap.put(REFUND_REASON,requestData.getRefundReason());
        attributeMap.put(CREATED_ON,requestData.getCreatedOn());
        attributeMap.put(UPDATED_ON,requestData.getUpdatedOn());
        attributeMap.put(CARD_NUMBER_FIRST_SIX,requestData.getCardNumberFirstSix());
        attributeMap.put(ORDER_KEY,orderKey);
        attributeMap.put(CARD_TYPE,requestData.getCardType());
        if (null != animalBusinessCare){
            attributeMap.put(ANIMAL_CARE_BUSINESS,animalBusinessCare);
        }
        if (CollectionUtils.isNotEmpty(requestData.getItems())){
            attributeMap.put(ITEMS,requestData.getItems());
        }
        return attributeMap;
    }
}
