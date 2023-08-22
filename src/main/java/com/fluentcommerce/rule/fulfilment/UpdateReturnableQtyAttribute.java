package com.fluentcommerce.rule.fulfilment;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.fluentcommerce.util.Constants.*;


/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateReturnableQtyAttribute",
        description = "update the returnableQty attribute at the orderItem level",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)
@Slf4j
public class UpdateReturnableQtyAttribute extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String fulfilmentId = event.getEntityId();
        String orderId = event.getRootEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfilmentId);
        List<UpdateOrderItemWithOrderInput> updateInputOrderItems = getOrderItemsForUpdate(order,fulfillmentById);
        if (CollectionUtils.isNotEmpty(updateInputOrderItems)){
            orderService.updateOrderItems(updateInputOrderItems,orderId);
        }
    }

    private List<UpdateOrderItemWithOrderInput> getOrderItemsForUpdate(Order order, GetFulfilmentByIdQuery.FulfilmentById fulfillmentById) {
        List<UpdateOrderItemWithOrderInput> updateItems = new ArrayList<>();
        if (null != order && CollectionUtils.isNotEmpty(order.getItems()) && null != fulfillmentById &&
                CollectionUtils.isNotEmpty(fulfillmentById.items().edges())){
            for (GetFulfilmentByIdQuery.Edge edge:fulfillmentById.items().edges()){
                Optional<OrderItem> orderLineItem = order.getItems().stream().filter(
                        orderItem -> orderItem.getRef().equalsIgnoreCase(edge.item().ref())
                ).findFirst();
                if (orderLineItem.isPresent()){
                    updateItems.add(UpdateOrderItemWithOrderInput.builder()
                            .id(orderLineItem.get().getId())
                            .attributes(getOrderItemAttribute(edge.item().filledQuantity()))
                            .build());
                }
            }
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute(int filledQuantity) {
        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(AttributeInput.builder().name(RETURNABLE_QTY_ATTR)
                .type(ATTRIBUTE_TYPE_FLOAT).value(filledQuantity).build());
        return attributeInputList;
    }
}
