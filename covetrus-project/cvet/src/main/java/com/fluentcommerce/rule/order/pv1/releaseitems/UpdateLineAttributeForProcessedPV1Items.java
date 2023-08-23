package com.fluentcommerce.rule.order.pv1.releaseitems;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.LocationData;
import com.fluentcommerce.dto.fulfillment.OrderItemData;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "UpdateLineAttributeForProcessedPV1Items",
        description = "Update order items attribute which is PV1 verified",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)

@EventAttribute(name = EVENT_LOCATION_DATA_LIST)
@Slf4j
public class UpdateLineAttributeForProcessedPV1Items extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<LocationData> locationDataList = CommonUtils.convertObjectToDto(eventAttributes.get(EVENT_LOCATION_DATA_LIST), new TypeReference<List<LocationData>>() {});
        List<UpdateOrderItemWithOrderInput> updateOrderItems = new ArrayList<>();
        for(LocationData locationData : locationDataList){
            for(OrderItemData orderItemData : locationData.getItems()){
                List<AttributeInput> itemAttributes = new ArrayList<>();
                itemAttributes.add(
                        AttributeInput.builder().name(IS_PV1_VERIFIED_AND_REQUESTED).type(ATTRIBUTE_TYPE_STRING).value(TRUE).build()
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
