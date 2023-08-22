package com.fluentcommerce.rule.order.common;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static com.fluentcommerce.util.Constants.*;

@RuleInfo(
        name = "UpdateOrderLineItemsInOrder",
        description = "Update order items in order from {" + PROP_FROM_LINE_ITEM_STATUS + "} to {" + PROP_TO_LINE_ITEM_STATUS + "}" +
                "and call event {" + PROP_EVENT_NAME + "}",
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
        name = PROP_FROM_LINE_ITEM_STATUS,
        description = "order line item status to be modified from value"
)
@ParamString(
        name = PROP_TO_LINE_ITEM_STATUS,
        description = "order line item status to be modified to value"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "Event name to be triggered"
)
@Slf4j
public class UpdateOrderLineItemsInOrder extends BaseRule {

    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        String fromLineItemStatus = context.getProp(PROP_FROM_LINE_ITEM_STATUS);
        String toLineItemStatus = context.getProp(PROP_TO_LINE_ITEM_STATUS);

        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(context.getEvent().getEntityId());

        // Iterate the order items and change the status "from" to the "to" value

        List<GetOrderByIdQuery.OrderItemEdge> orderItemList =
        order.orderItems().orderItemEdge().stream().filter(
                orderItemEdge -> orderItemEdge.orderItemNode().orderItemAttributes().stream().anyMatch(
                        orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(ORDER_LINE_STATUS) &&
                                orderItemAttribute.value().toString().equalsIgnoreCase(fromLineItemStatus)
                )
        ).collect(Collectors.toList());

        context.addLog("orderItemList which are matching line item status " + orderItemList);

        List<UpdateOrderItemWithOrderInput> updateOrderItems = new ArrayList<>();
        for( GetOrderByIdQuery.OrderItemEdge orderItem: orderItemList) {

            List<AttributeInput> itemAttributes = new ArrayList<>();
            itemAttributes.add(
            AttributeInput.builder().name(ORDER_LINE_STATUS).type(ATTRIBUTE_TYPE_STRING).value(toLineItemStatus).build()
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
            EventUtils.forwardInline(context,eventName);
        }
    }
}
