package com.fluentcommerce.rule.webhook.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.OrderItemDto;
import com.fluentcommerce.dto.orderitem.PV1Items;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.SettingUtils;
import com.fluentcommerce.util.WebhookUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */


@RuleInfo(
        name = "SendWebhookToPV1BasedOnFacility",
        description = "Get endpoint url from setting {" + PROP_WEBHOOK_SETTING_NAME + "} and invoke it",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_WEBHOOK_SETTING_NAME,
        description = "integration base URL"
)

@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@Slf4j
public class SendWebhookToPV1BasedOnFacility extends BaseRule {

    private static final String CLASS_NAME = SendWebhookToPV1BasedOnFacility.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String propWebhookSettingName = context.getProp(PROP_WEBHOOK_SETTING_NAME);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems>  pharmacyAllocationForOrderItemsList = CommonUtils.convertObjectToDto(eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS),new TypeReference<List<PharmacyAllocationForOrderItems>>() {});

        // construct a map with location and order item information
        Map<String, List<PV1Items>> locationWithOrderItemMap = new HashMap<>();
        for( PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems: pharmacyAllocationForOrderItemsList) {

            String locationRef = pharmacyAllocationForOrderItems.getAssignedFacilityRef();

            if (locationWithOrderItemMap.containsKey(locationRef)) {
                getPV1ItemList(locationWithOrderItemMap, pharmacyAllocationForOrderItems, locationRef);
            } else {

                List<PV1Items> pv1ItemsList = new ArrayList<>();
                for( OrderItemDto orderItemDto : pharmacyAllocationForOrderItems.getOrderItemDtoList()) {
                    PV1Items pv1ItemRequest = new PV1Items();
                    pv1ItemRequest.setRef(orderItemDto.getOrderItemRef());
                    pv1ItemRequest.setPrescriptionId(orderItemDto.getPrescriptionId());

                    pv1ItemsList.add(pv1ItemRequest);
                }
                locationWithOrderItemMap.put(locationRef, pv1ItemsList);
            }
        }

        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderById = orderService.getOrderWithOrderItems(event.getEntityId());

        Optional<Object> orderKeyAttribute =
                orderById.attributes().stream()
                        .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY))
                        .map(GetOrderByIdQuery.Attribute::value)
                        .findFirst();

        Optional<String> endPointURL = SettingUtils.getSettingValue(context,propWebhookSettingName);
        if(endPointURL.isPresent()) {
            for (Map.Entry<String, List<PV1Items>>  entry : locationWithOrderItemMap.entrySet()) {

                Map<String, Object> attributes = new HashMap<>();

                attributes.put(ORDER_REF,event.getEntityRef());

                // append order key value if present
                if(orderKeyAttribute.isPresent())
                    attributes.put(ORDER_KEY,orderKeyAttribute.get().toString());

                attributes.put(FACILITY_ID,entry.getKey());

                attributes.put(ITEMS,entry.getValue());
                context.action().postWebhook(endPointURL.get(), WebhookUtils.constructWebhookEventWithAttribute(
                        context.getEvent(),
                        context,
                        attributes
                ));

            }
        }else{
            context.addLog("Settings value is not present");
        }
    }

    private void getPV1ItemList(Map<String, List<PV1Items>> locationWithOrderItemMap, PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems, String locationRef) {
        List<PV1Items> pv1ItemsList = locationWithOrderItemMap.get(locationRef);
        for( OrderItemDto orderItemDto : pharmacyAllocationForOrderItems.getOrderItemDtoList()) {
            PV1Items pv1ItemRequest = new PV1Items();
            pv1ItemRequest.setRef(orderItemDto.getOrderItemRef());
            pv1ItemRequest.setPrescriptionId(orderItemDto.getPrescriptionId());

            pv1ItemsList.add(pv1ItemRequest);
        }
    }
}
