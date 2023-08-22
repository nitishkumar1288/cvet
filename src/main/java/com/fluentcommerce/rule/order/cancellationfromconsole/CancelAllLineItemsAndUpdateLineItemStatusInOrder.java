package com.fluentcommerce.rule.order.cancellationfromconsole;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "CancelAllLineItemsAndUpdateLineItemStatusInOrder",
        description = "Update all the line item status to {" + PROP_TO_LINE_ITEM_STATUS + "} and move the requested quantity to zero",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_TO_LINE_ITEM_STATUS,
        description = "line item status to be updated"
)

@EventAttribute(name = EVENT_CANCELLATION_REASON)
@EventAttribute(name = EVENT_CANCELLED_BY)
@Slf4j
public class CancelAllLineItemsAndUpdateLineItemStatusInOrder extends BaseRule {

    private static final String CLASS_NAME = CancelAllLineItemsAndUpdateLineItemStatusInOrder.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        String toLineItemStatus = context.getProp(PROP_TO_LINE_ITEM_STATUS);

        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        String cancelReason = event.getAttributes().get(EVENT_CANCELLATION_REASON).toString();
        List<UpdateOrderItemWithOrderInput> updateInputOrderItems = getOrderItemForCancellation(order,toLineItemStatus,cancelReason);
        if (CollectionUtils.isNotEmpty(updateInputOrderItems)){
            orderService.updateOrderItems(updateInputOrderItems,orderId);
        }
    }

    private List<UpdateOrderItemWithOrderInput> getOrderItemForCancellation(Order order, String toLineItemStatus, String cancelReason) {
        List<UpdateOrderItemWithOrderInput> updateItems = new ArrayList<>();

        order.getItems().forEach(items->{
            // Update the order line item status and the quantity for non-cancelled items
            if(!items.getAttributes().get(ORDER_LINE_STATUS).toString().equalsIgnoreCase(toLineItemStatus)){
                List<AttributeInput> itemAttributes = new ArrayList<>();
                itemAttributes.add(AttributeInput.builder().name(ORDER_LINE_STATUS).type(ATTRIBUTE_TYPE_STRING).value(toLineItemStatus).build());
                itemAttributes.add(AttributeInput.builder().name(REASON_FOR_CANCELLATION).type(ATTRIBUTE_TYPE_STRING).value(cancelReason).build());

                updateItems.add(UpdateOrderItemWithOrderInput.builder()
                        .id(items.getId())
                        .quantity(0)
                        .attributes(itemAttributes)
                        .build());
            }
        });

        return updateItems;
    }
}