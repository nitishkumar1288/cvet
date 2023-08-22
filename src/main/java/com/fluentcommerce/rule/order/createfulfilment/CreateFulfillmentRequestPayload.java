package com.fluentcommerce.rule.order.createfulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.FulfillmentRequestData;
import com.fluentcommerce.dto.fulfillment.LocationData;
import com.fluentcommerce.dto.orderitem.OrderItem;
import com.fluentcommerce.dto.fulfillment.OrderItemData;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CreateFulfillmentRequestPayload",
        description = "fetch the event attribute and create fulfillment request payload and call to next {" + PROP_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)

@EventAttribute(name = EVENT_LOCATION_DATA_LIST)
@Slf4j
public class CreateFulfillmentRequestPayload extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        String orderId = event.getEntityId();
        String orderRef = event.getEntityRef();
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<LocationData> locationDataList = CommonUtils.convertObjectToDto(eventAttributes.get(EVENT_LOCATION_DATA_LIST),new TypeReference<List<LocationData>>(){});
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);
        Optional<GetOrderByIdQuery.Attribute> orderKeyAttribute =
                orderData.attributes().stream()
                        .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY))
                        .findFirst();
        if (orderKeyAttribute.isPresent() && null !=orderKeyAttribute.get().value().toString()
                && CollectionUtils.isNotEmpty(locationDataList)){
            FulfillmentRequestData fulfillmentRequestData = createFulfilmentRequestData(orderKeyAttribute.get().value().toString(),orderRef,locationDataList);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put(FULFILLMENT_REQUEST_PAYLOAD, fulfillmentRequestData);
            attributes.putAll(context.getEvent().getAttributes());
            EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
        }

    }
    private FulfillmentRequestData createFulfilmentRequestData(String orderKeyAttribute,
                                                               String orderRef,List<LocationData> locationDataList) {
        FulfillmentRequestData fulfillmentRequestData = new FulfillmentRequestData();
        fulfillmentRequestData.setOrderRef(orderRef);
        fulfillmentRequestData.setOrderKey(orderKeyAttribute);
        ArrayList<OrderItem> itemList = new ArrayList<>();
        for (LocationData listData:locationDataList){
            for (OrderItemData itemData:listData.getItems()){
                OrderItem item = new OrderItem();
                item.setFacilityId(listData.getLocationRef());
                item.setRef(itemData.getLineItemRef());
                itemList.add(item);
            }
        }
        fulfillmentRequestData.setItems(itemList);
        return fulfillmentRequestData;
    }
}