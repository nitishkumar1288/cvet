package com.fluentcommerce.rule.order.allocation.otcitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.OtcProductsDto;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */


@RuleInfo(
        name = "UpdateOtcOrderLineItemsStatusAndLocationInformation",
        description = "Update order items involved in allocation to {" + PROP_TO_LINE_ITEM_STATUS + "}" +
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

@EventAttribute(name = EVENT_OTC_ORDER_ITEMS_ALLOCATION)
@Slf4j
public class UpdateOtcOrderLineItemsStatusAndLocationInformation extends BaseRule {

    private static final String CLASS_NAME = UpdateOtcOrderLineItemsStatusAndLocationInformation.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);
        String toLineItemStatus = context.getProp(PROP_TO_LINE_ITEM_STATUS);

        Map<String,Object> eventAttribute = context.getEvent().getAttributes();
        List<OtcProductsDto> otcProductsDtoList = (List<OtcProductsDto>) eventAttribute.get(EVENT_OTC_ORDER_ITEMS_ALLOCATION);


        List<UpdateOrderItemWithOrderInput> updateOrderItems = new ArrayList<>();
        for( OtcProductsDto otcProductsDto: otcProductsDtoList) {

            List<AttributeInput> itemAttributes = new ArrayList<>();
            itemAttributes.add(
                    AttributeInput.builder().name(ORDER_LINE_STATUS).type(ATTRIBUTE_TYPE_STRING).value(toLineItemStatus).build()
            );

            itemAttributes.add(
                    AttributeInput.builder().name(FACILITY_ID).type(ATTRIBUTE_TYPE_STRING).value(otcProductsDto.getLocationId()).build()
            );

            if (otcProductsDto.isDefaultLocationSelected()) {

                itemAttributes.add(
                        AttributeInput.builder().name(DEFAULT_SELECTED_LOCATION).type(ATTRIBUTE_TYPE_STRING).value(otcProductsDto.getFoundLocation()).build()
                );

            } else {

                itemAttributes.add(
                        AttributeInput.builder().name(SELECTED_LOCATION).type(ATTRIBUTE_TYPE_STRING).value(otcProductsDto.getFoundLocation()).build()
                );

            }

            // update the order item builder
            updateOrderItems.add(
                    UpdateOrderItemWithOrderInput
                            .builder()
                            .id(otcProductsDto.getOrderItemId())
                            .attributes(itemAttributes)
                            .build());

        }

        if(CollectionUtils.isNotEmpty(updateOrderItems)){
            OrderService orderService = new OrderService(context);
            orderService.updateOrderItems(updateOrderItems,context.getEvent().getEntityId());

            EventUtils.forwardInline(context,eventName);
        }
    }
}
