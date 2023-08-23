package com.fluentcommerce.rule.webhook.fulfilment;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.util.RuleUtils;
import com.fluentcommerce.util.SettingUtils;
import com.fluentcommerce.util.WebhookUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendCreatePickReadyInstructionWebhook",
        description = "create the pick ready instruction , get endpoint url from " +
                "setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "integration base URL"
)
@Slf4j
public class SendCreatePickReadyInstructionWebhook extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_WEBHOOK_SETTING_NAME);
        Event event = context.getEvent();
        String fulfilmentId = event.getEntityId();
        String fulfilmentRef = event.getEntityRef();
        String orderRef = event.getRootEntityRef();
        String status = event.getEntityStatus();
        String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);
        Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfilmentId);
        String animalBusinessCare = null;
        if (Objects.nonNull(fulfillmentById)){
            Optional<GetFulfilmentByIdQuery.Attribute> optionalOrderKeyAttribute = fulfillmentById.order().attributes().
                    stream().filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY)).findFirst();
            Optional<GetFulfilmentByIdQuery.Attribute> animalBusinessCareAttribute = fulfillmentById.order().attributes().
                    stream().filter(attribute -> attribute.name().equalsIgnoreCase(ANIMAL_CARE_BUSINESS)).findFirst();
            if (animalBusinessCareAttribute.isPresent() && null != animalBusinessCareAttribute.get().value().toString()){
                animalBusinessCare = animalBusinessCareAttribute.get().value().toString();
            }
            if (optionalOrderKeyAttribute.isPresent()){
                String orderKey =  optionalOrderKeyAttribute.get().value().toString();
                Map<String, Object> attributeMap = constructAttributeMap(orderKey,fulfilmentId,fulfilmentRef,orderRef,status,animalBusinessCare);
                endPointURL.ifPresent(e -> context.action().postWebhook(e, WebhookUtils.constructWebhookEventWithAttribute(event,context,attributeMap)));
            }
        }
    }

    private Map<String, Object> constructAttributeMap(String orderKey, String fulfilmentId,
                                                      String fulfilmentRef, String orderRef, String status,String animalBusinessCare) {
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ORDER_KEY,orderKey);
        attributeMap.put(ORDER_REF,orderRef);
        attributeMap.put(FULFILLMENT_ID,fulfilmentId);
        attributeMap.put(SHIPPING_ORDER_KEY,fulfilmentRef);
        if (null != animalBusinessCare){
            attributeMap.put(ANIMAL_CARE_BUSINESS,animalBusinessCare);
        }
        attributeMap.put(STATUS,status);
        return attributeMap;
    }
}
