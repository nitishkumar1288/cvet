package com.fluentcommerce.rule.order.returnorder;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fluentcommerce.util.Constants.*;


/**
 @author nitishKumar
 */

@RuleInfo(
        name = "AddReturnableQtyAttribute",
        description = "at the time of order creation add the returnable quantity attribute at orderItem level ",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@Slf4j
public class AddReturnableQtyAttribute extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        List<UpdateOrderItemWithOrderInput> updateInputOrderItems = getOrderItemsForUpdate(order);
        if (CollectionUtils.isNotEmpty(updateInputOrderItems)){
            orderService.updateOrderItems(updateInputOrderItems,orderId);
        }
    }
    private List<UpdateOrderItemWithOrderInput> getOrderItemsForUpdate(Order order) {
        List<UpdateOrderItemWithOrderInput> updateItems = new ArrayList<>();
        if (Objects.nonNull(order) && CollectionUtils.isNotEmpty(order.getItems())){
            for(OrderItem item:order.getItems()){
                    updateItems.add(UpdateOrderItemWithOrderInput.builder()
                            .id(item.getId())
                            .attributes(getOrderItemAttribute())
                            .build());
            }
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute() {
        List<AttributeInput> attributeInputList = new ArrayList<>();
            attributeInputList.add(AttributeInput.builder().name(RETURNABLE_QTY_ATTR)
                    .type(ATTRIBUTE_TYPE_FLOAT).value(0).build());
        return attributeInputList;
    }
}