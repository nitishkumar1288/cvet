package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CalculateRefundAmount",
        description = "calculate the final refundable amount and send the event {" + PROP_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_CREDIT_MEMO)
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)
@EventAttribute(name = TOTAL_REFUND_AMOUNT_REQUESTED)
@Slf4j
public class CalculateRefundAmount extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        String creditMemoRef = event.getEntityRef();
        CreditMemoService creditMemoService = new CreditMemoService(context);
        GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(creditMemoRef);
        double totalRefundAmountRequested =  context.getEvent().getAttribute(TOTAL_REFUND_AMOUNT_REQUESTED, Double.class);
        context.addLog("CalculateRefundAmount::totalRefundAmountRequested"+totalRefundAmountRequested);
        double maxRefundableAmountForOrder = 0;
        double amountToBeRefund = 0;
        if (null != creditMemo) {
            String orderRef = creditMemo.order().ref();
            OrderService orderService = new OrderService(context);
            GetOrdersByRefQuery.Edge orderEdge = orderService.getOrdersByRef(orderRef);
            if (null != orderEdge && null != orderEdge.node() && CollectionUtils.isNotEmpty(orderEdge.node().attributes())){
                Optional<GetOrdersByRefQuery.Attribute> maxRefundableAmountAttribute = orderEdge.node().attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(AVAILABLE_AMOUNT_TO_REFUND)).findFirst();
                if (maxRefundableAmountAttribute.isPresent()){
                    maxRefundableAmountForOrder = Double.parseDouble(maxRefundableAmountAttribute.get().value().toString());
                }
            }
            context.addLog("CalculateRefundAmount::maxRefundableAmountForOrder"+maxRefundableAmountForOrder);
            if (maxRefundableAmountForOrder >= totalRefundAmountRequested){
                amountToBeRefund = totalRefundAmountRequested;
            }else{
                amountToBeRefund = maxRefundableAmountForOrder;
            }
            if (amountToBeRefund !=0){
                Map<String, Object> attributes = new HashMap<>();
                attributes.putAll(context.getEvent().getAttributes());
                attributes.put(AMOUNT_TO_BE_REFUND,amountToBeRefund);
                EventUtils.forwardEventInlineWithAttributes(
                        context,
                        eventName,
                        attributes);
            }
        }
    }
}
