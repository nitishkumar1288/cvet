package com.fluentcommerce.rule.order.pv1.exception;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author Yamini Kukreja
 */

@RuleInfo(
        name = "UpdateOrderItemBasedOnPV1ExceptionResponse",
        description = "Update the order items status with respect to pv1 exception status response {" +
                PROP_EXCEPTION_STATUS_RESPONSE + "} followed by line status.",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_EXCEPTION_STATUS_RESPONSE,
        description = "exception status response from pv1:exception line status"
)

@EventAttribute(name = ITEMS)
@Slf4j
public class UpdateOrderItemBasedOnPV1ExceptionResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        List<PV1Items> items = event.getAttributeList(ITEMS, PV1Items.class);
        String[] exceptionStatusResponse = context.getProp(PROP_EXCEPTION_STATUS_RESPONSE).split(COLON);

        if(!items.isEmpty()){
            List<UpdateOrderItemWithOrderInput> updateInputOrderItems = new ArrayList<>();
            for(PV1Items item: items){
                order.getItems().stream().forEach(orderItem -> {
                    if(StringUtils.equalsIgnoreCase(orderItem.getRef(), item.getRef()) &&
                            StringUtils.equalsIgnoreCase(item.getStatus(), exceptionStatusResponse[0])) {
                            List<AttributeInput> attributes = new ArrayList<>();
                            attributes.add(getOrderItemAttribute(OLD_LINE_STATUS, orderItem.getAttributes().get(ORDER_LINE_STATUS).toString()));
                            attributes.add(getOrderItemAttribute(ORDER_LINE_STATUS, exceptionStatusResponse[1]));
                            attributes.add(getOrderItemAttribute(EVENT_DISPENSE_REQUEST_ID, item.getDispenseRequestId()));
                            attributes.add(getOrderItemAttribute(EVENT_EXCEPTION_REASON, item.getExceptionReason()));
                            updateInputOrderItems.add(UpdateOrderItemWithOrderInput.builder().id(orderItem.getId())
                                .attributes(attributes).build());
                    }
                });
            }
            orderService.updateOrderItems(updateInputOrderItems, orderId);
        }
    }

    private AttributeInput getOrderItemAttribute(String name, String value) {
        return AttributeInput.builder()
                .name(name)
                .type(ATTRIBUTE_TYPE_STRING)
                .value(value)
                .build();
    }
}
