package com.fluentcommerce.rule.order.pv1.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.OrderItemDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "UpdateOrderLineItemsInOrderForRxItems",
        description = "Update order items in order to {" + PROP_TO_LINE_ITEM_STATUS + "}" +
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
        name = PROP_TO_LINE_ITEM_STATUS,
        description = "order line item status to be modified to value"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "Event name to be triggered"
)

@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@Slf4j
public class UpdateOrderLineItemsInOrderForRxItems extends BaseRule {

    private static final String CLASS_NAME = UpdateOrderLineItemsInOrderForRxItems.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);
        String toLineItemStatus = context.getProp(PROP_TO_LINE_ITEM_STATUS);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems>  pharmacyAllocationForOrderItemsList = CommonUtils.convertObjectToDto(eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS),new TypeReference<List<PharmacyAllocationForOrderItems>>() {});

        List<UpdateOrderItemWithOrderInput> updateOrderItems = new ArrayList<>();
        for( PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems: pharmacyAllocationForOrderItemsList) {
            for(OrderItemDto orderItemDto : pharmacyAllocationForOrderItems.getOrderItemDtoList()) {
                List<AttributeInput> itemAttributes = new ArrayList<>();
                itemAttributes.add(
                        AttributeInput.builder().name(ORDER_LINE_STATUS).type(ATTRIBUTE_TYPE_STRING).value(toLineItemStatus).build()
                );

                // update the order item builder
                updateOrderItems.add(
                        UpdateOrderItemWithOrderInput
                                .builder()
                                .id(orderItemDto.getId())
                                .attributes(itemAttributes)
                                .build());
            }
        }

        if(CollectionUtils.isNotEmpty(updateOrderItems)){
            OrderService orderService = new OrderService(context);
            orderService.updateOrderItems(updateOrderItems,context.getEvent().getEntityId());
            EventUtils.forwardInline(context,eventName);
        }
    }
}
