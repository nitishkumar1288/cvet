package com.fluentcommerce.rule.order.common;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentInfo;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.OrderUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author nitishkumar
 */
@RuleInfo(
        name = "CheckOrderPaymentTypeMatch",
        description = "Check if order's payment type match with payment type list{" + PAYMENT_TYPE_LIST + "} , " +
                " send event {" + PROP_EVENT_NAME + "} else send event {" + PROP_NO_MATCHING_EVENT_NAME + "}",
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
        name = PAYMENT_TYPE_LIST,
        description = "payment type list"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "Event name on condition matches"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "No match event name"
)
@Slf4j
public class CheckOrderPaymentTypeMatch extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,PROP_NO_MATCHING_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);
        List<String> paymentTypeList = context.getPropList(PAYMENT_TYPE_LIST, String.class);
        boolean paymentTypeAvailable = false;
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(context.getEvent().getEntityId());
        Optional<GetOrderByIdQuery.Attribute> optionalPaymentInfo = orderData.attributes().
                stream().filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_INFO)).findFirst();
        PaymentInfo paymentInfo =  OrderUtils.fetchPaymentInfoAttribute(optionalPaymentInfo,context);
        if (CollectionUtils.isNotEmpty(paymentTypeList) && null != paymentInfo
                && paymentTypeList.contains(paymentInfo.getPaymentType().toUpperCase())){
            paymentTypeAvailable = true;
        }
        if (paymentTypeAvailable) {
            EventUtils.forwardInline(context, eventName);
        } else {
            EventUtils.forwardInline(context, noMatchEventName);
        }
    }
}