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
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
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
 * @author Nandhakumar
 */

@RuleInfo(
        name = "UpdateOrderItemAsPaymentSettlementCompleted",
        description = "Update the order item's attribute involved in the fulfilment as payment settlement completed " +
                "and call the next event{" + PROP_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "event name"
)
@Slf4j
public class UpdateOrderItemAsPaymentSettlementCompleted extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        String fulfilmentId = event.getEntityId();
        String orderId = event.getRootEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);

        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfilmentId);
        List<UpdateOrderItemWithOrderInput> updateItems = getOrderItemForUpdate(order, fulfillmentById);
        if (CollectionUtils.isNotEmpty(updateItems)) {
            orderService.updateOrderItems(updateItems, orderId);
            EventUtils.forwardInline(context, eventName);
        }
    }

    private List<UpdateOrderItemWithOrderInput> getOrderItemForUpdate(
            Order order,
            GetFulfilmentByIdQuery.FulfilmentById fulfillmentById) {
        List<UpdateOrderItemWithOrderInput> updateItems = null;
        if (Objects.nonNull(fulfillmentById) && null != fulfillmentById.items() && CollectionUtils.isNotEmpty(fulfillmentById.items().edges())) {
            updateItems = new ArrayList<>();
            for (GetFulfilmentByIdQuery.Edge itemEdge : fulfillmentById.items().edges()) {
                Optional<OrderItem> optionalOrderItem = order.getItems().stream().filter(orderItem ->
                        orderItem.getRef().equalsIgnoreCase(itemEdge.item().ref())).findFirst();
                if (optionalOrderItem.isPresent()) {
                    String orderLineStatus = optionalOrderItem.get().getAttributes().get(ORDER_LINE_STATUS).toString();
                    if (!orderLineStatus.equalsIgnoreCase(CANCELLED)) {
                        updateItems.add(UpdateOrderItemWithOrderInput.builder()
                                .id(optionalOrderItem.get().getId())
                                .attributes(getOrderItemAttribute())
                                .build());
                    }
                }
            }
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute() {
        List<AttributeInput> itemAttributes = new ArrayList<>();
        itemAttributes.add(AttributeInput.builder()
                .name(IS_PAYMENT_SETTLEMENT_COMPLETED)
                .type(ATTRIBUTE_TYPE_STRING)
                .value(TRUE)
                .build());
        return itemAttributes;
    }
}
