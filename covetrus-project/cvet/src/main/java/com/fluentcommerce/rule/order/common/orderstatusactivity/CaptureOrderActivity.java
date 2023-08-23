package com.fluentcommerce.rule.order.common.orderstatusactivity;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.OrderUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CaptureOrderActivity",
        description = "Update the orderActivity attribute with from current status to status{" + PROP_TO_LINE_ITEM_STATUS + "}",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_TO_LINE_ITEM_STATUS,
        description = "next order status name"
)
@Slf4j
public class CaptureOrderActivity extends BaseRule {
    private static final String CLASS_NAME = CaptureOrderActivity.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_TO_LINE_ITEM_STATUS);
        String toOrderStatus = context.getProp(PROP_TO_LINE_ITEM_STATUS);
        Event event = context.getEvent();
        String fromOrderStatus = event.getEntityStatus();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);
        Optional<GetOrderByIdQuery.Attribute> orderActivityAttribute =
                orderData.attributes().stream()
                        .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_STATUS_ACTIVITY))
                        .findFirst();
        OrderUtils.checkAndUpdateOrderActivityAttribute(orderActivityAttribute,toOrderStatus,
                fromOrderStatus,orderId,context);
    }
}