package com.fluentcommerce.rule.order.createfulfilment;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateOrderItemStatusAfterFulfillmentCreation",
        description = "fetch the fulfilled item list from attribute and update order item status with {" + PROP_TO_LINE_ITEM_STATUS + "}",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_TO_LINE_ITEM_STATUS,
        description = "order line item status to be modified to value"
)

@EventAttribute(name = FULFILLED_ITEM_REF_LIST)
@Slf4j
public class UpdateOrderItemStatusAfterFulfillmentCreation extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_TO_LINE_ITEM_STATUS);
        Event event = context.getEvent();
        String toLineItemStatus = context.getProp(PROP_TO_LINE_ITEM_STATUS);
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        List<String> fulfilledItemList =
                context.getEvent().getAttributeList(FULFILLED_ITEM_REF_LIST, String.class);
        List<UpdateOrderItemWithOrderInput> updateInputOrderItems = getItemForUpdate(fulfilledItemList,order,toLineItemStatus);
        if (CollectionUtils.isNotEmpty(updateInputOrderItems)){
            orderService.updateOrderItems(updateInputOrderItems,orderId);
        }
    }

    private List<UpdateOrderItemWithOrderInput> getItemForUpdate(List<String> fulfilledItemList,
                                                                 Order order,String status) {
        List<UpdateOrderItemWithOrderInput> updateItems = new ArrayList<>();
        if (Objects.nonNull(order) && Objects.nonNull(fulfilledItemList)){
            for(String itemRef:fulfilledItemList){
                Optional<OrderItem> orderLineItem = order.getItems().stream().filter(
                        orderItem -> orderItem.getRef().equalsIgnoreCase(itemRef)
                ).findFirst();
                if(orderLineItem.isPresent()){
                    updateItems.add(UpdateOrderItemWithOrderInput.builder()
                            .id(orderLineItem.get().getId())
                            .attributes(getOrderItemAttribute(status))
                            .build());
                }
            }
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute(String status) {
        List<AttributeInput> itemAttributes = new ArrayList<>();
        itemAttributes.add(AttributeInput.builder()
                .name(ORDER_LINE_STATUS)
                .type(ATTRIBUTE_TYPE_STRING)
                .value(status)
                .build());
        return itemAttributes;
    }
}
