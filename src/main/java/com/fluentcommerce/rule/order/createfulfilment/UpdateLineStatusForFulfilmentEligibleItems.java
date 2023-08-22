package com.fluentcommerce.rule.order.createfulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.LocationData;
import com.fluentcommerce.dto.fulfillment.OrderItemData;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

@RuleInfo(
        name = "UpdateLineStatusForFulfilmentEligibleItems",
        description = "Update order items which is eligible for fulfilment, status as {" + PROP_TO_LINE_ITEM_STATUS + "}",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_TO_LINE_ITEM_STATUS,
        description = "order line item status to be modified to value"
)

@EventAttribute(name = EVENT_LOCATION_DATA_LIST)
@Slf4j
public class UpdateLineStatusForFulfilmentEligibleItems extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_TO_LINE_ITEM_STATUS);
        String toLineItemStatus = context.getProp(PROP_TO_LINE_ITEM_STATUS);
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<LocationData> locationDataList = CommonUtils.convertObjectToDto(eventAttributes.get(EVENT_LOCATION_DATA_LIST), new TypeReference<List<LocationData>>() {});
        List<UpdateOrderItemWithOrderInput> updateOrderItems = new ArrayList<>();
        for(LocationData locationData : locationDataList){
            for(OrderItemData orderItemData : locationData.getItems()){
                List<AttributeInput> itemAttributes = new ArrayList<>();
                itemAttributes.add(
                        AttributeInput.builder().name(ORDER_LINE_STATUS).type(ATTRIBUTE_TYPE_STRING).value(toLineItemStatus).build()
                );
                updateOrderItems.add(
                        UpdateOrderItemWithOrderInput
                                .builder()
                                .id(orderItemData.getLineItemId())
                                .attributes(itemAttributes)
                                .build());
            }
        }
        if(CollectionUtils.isNotEmpty(updateOrderItems)){
            OrderService orderService = new OrderService(context);
            orderService.updateOrderItems(updateOrderItems,context.getEvent().getEntityId());
        }
    }
}
