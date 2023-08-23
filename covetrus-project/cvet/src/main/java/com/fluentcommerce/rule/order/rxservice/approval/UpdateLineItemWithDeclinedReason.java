package com.fluentcommerce.rule.order.rxservice.approval;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.rx.Items;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import com.fluentretail.rubix.v2.context.Context;
import com.fluentretail.rubix.v2.rule.Rule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author nitishKumar
 */

@RuleInfo(
        name = "UpdateLineItemWithDeclinedReason",
        description = "update the order items with this declined reason ",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)

@EventAttribute(name = ITEMS)
@Slf4j
public class UpdateLineItemWithDeclinedReason implements Rule {
    @Override
    public void run(Context context) {
        Map<String, Object> eventAttributes = context.getEvent().getAttributes();
        String orderId = context.getEvent().getEntityId();
        List<Items> rxServiceRespItems = CommonUtils.convertObjectToDto(eventAttributes.get(ITEMS), new TypeReference<List<Items>>() {
        });
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderItems(orderId);
        List<UpdateOrderItemWithOrderInput> updateItems = getOrderItemForUpdate(orderData, rxServiceRespItems);
        if (CollectionUtils.isNotEmpty(updateItems)) {
            orderService.updateOrderItems(updateItems, orderId);
        }
    }

    private List<UpdateOrderItemWithOrderInput> getOrderItemForUpdate(GetOrderByIdQuery.OrderById orderData,
                                                                      List<Items> rxServiceRespItems) {
        List<UpdateOrderItemWithOrderInput> updateItems = new ArrayList<>();
        if (orderData != null && orderData.orderItems() != null
                && Objects.nonNull(orderData.orderItems().orderItemEdge())
                && CollectionUtils.isNotEmpty(rxServiceRespItems)
        ) {
            rxServiceRespItems.forEach(resItem -> {
                if (resItem.getStatus().equalsIgnoreCase(ORDER_ITEM_STATUS_DECLINED)) {
                    orderData.orderItems().orderItemEdge().forEach(orderItemEdge -> {
                        if (orderItemEdge.orderItemNode().ref().equalsIgnoreCase(resItem.getLineNumber())) {
                            updateItems.add(UpdateOrderItemWithOrderInput.builder()
                                    .id(orderItemEdge.orderItemNode().id())
                                    .attributes(getOrderItemAttribute(resItem))
                                    .build());
                        }
                    });
                }
            });
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute(Items item) {
        List<AttributeInput> itemAttributes = new ArrayList<>();
        itemAttributes.add(AttributeInput.builder()
                .name(REASON_FOR_CANCELLATION)
                .type(ATTRIBUTE_TYPE_STRING)
                .value(item.getDeclinedReasonCode())
                .build());
        return itemAttributes;
    }
}
