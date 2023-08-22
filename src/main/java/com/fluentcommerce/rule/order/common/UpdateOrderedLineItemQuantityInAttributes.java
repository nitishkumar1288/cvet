package com.fluentcommerce.rule.order.common;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.fluentcommerce.util.Constants.*;

@RuleInfo(
        name = "UpdateOrderedLineItemQuantityInAttributes",
        description = "Update order line item attribute with the original ordered quantity",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@Slf4j
public class UpdateOrderedLineItemQuantityInAttributes extends BaseRule {

    @Override
    public void run(ContextWrapper context) {

        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(context.getEvent().getEntityId());

        List<UpdateOrderItemWithOrderInput> updateOrderItems = new ArrayList<>();
        for( GetOrderByIdQuery.OrderItemEdge orderItem: order.orderItems().orderItemEdge()) {

            List<AttributeInput> itemAttributes = new ArrayList<>();
            itemAttributes.add(
                    AttributeInput.builder().name(ORDERED_LINE_ITEM_QUANTITY).type(ATTRIBUTE_TYPE_STRING).value(orderItem.orderItemNode().quantity()).build()
            );

            updateOrderItems.add(
                    UpdateOrderItemWithOrderInput
                            .builder()
                            .id(orderItem.orderItemNode().id())
                            .attributes(itemAttributes)
                            .build());
        }

        if(CollectionUtils.isNotEmpty(updateOrderItems)){
            orderService.updateOrderItems(updateOrderItems,context.getEvent().getEntityId());
        }
    }
}
