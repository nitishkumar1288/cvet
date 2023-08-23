package com.fluentcommerce.rule.order.fulfilmentshipmentresponse;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.Line;
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
        name = "UpdateLineItemCancellationReasonFromShipConfirmation",
        description = "if order item is " +
                "for cancellation update the order item with reason {" + REASON_FOR_CANCELLATION + "}",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)

@ParamString(
        name = REASON_FOR_CANCELLATION,
        description = "reason for cancellation"
)
@EventAttribute(name = SHIP_CANCELLED_ITEM_REF_LIST)
@Slf4j
public class UpdateLineItemCancellationReasonFromShipConfirmation extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,REASON_FOR_CANCELLATION);
        String reasonForCancellationMsg = context.getProp(REASON_FOR_CANCELLATION);
        Event event = context.getEvent();
        List<Line> cancelledItemRefList = context.getEvent().getAttributeList(SHIP_CANCELLED_ITEM_REF_LIST, Line.class);
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        List<UpdateOrderItemWithOrderInput> updateInputOrderItems = getOrderItemForUpdate(cancelledItemRefList,order,reasonForCancellationMsg);
        if (CollectionUtils.isNotEmpty(updateInputOrderItems)){
            orderService.updateOrderItems(updateInputOrderItems,orderId);
        }
    }
    private List<UpdateOrderItemWithOrderInput> getOrderItemForUpdate(List<Line> cancelledItemRefList,
                                                                 Order order, String reasonForCancellationMsg) {
        List<UpdateOrderItemWithOrderInput> updateItems = new ArrayList<>();
        if (Objects.nonNull(order) && Objects.nonNull(cancelledItemRefList)){
            for(Line lineData:cancelledItemRefList){
                Optional<OrderItem> orderLineItem = order.getItems().stream().filter(
                        orderItem -> orderItem.getRef().equalsIgnoreCase(lineData.getRef())
                ).findFirst();
                if(orderLineItem.isPresent()){
                        updateItems.add(UpdateOrderItemWithOrderInput.builder()
                                .id(orderLineItem.get().getId())
                                .attributes(getOrderItemAttribute(reasonForCancellationMsg))
                                .build());
                }
            }
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute(String message) {
        List<AttributeInput> itemAttributes = new ArrayList<>();
        itemAttributes.add(AttributeInput.builder()
                .name(REASON_FOR_CANCELLATION)
                .type(ATTRIBUTE_TYPE_STRING)
                .value(message)
                .build());
        return itemAttributes;

    }
}
