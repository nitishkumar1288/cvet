package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.billingaccount.creditmemo.RefundCreditResponse;
import com.fluentcommerce.graphql.mutation.payment.CreateFinancialTransactionMutation;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.graphql.type.CreateFinancialTransactionInput;
import com.fluentcommerce.graphql.type.OrderId;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CreateFinancialTransactionAfterRefundResponse",
        description = "take the financial type {" + TYPE + "} ,currency type {" + CURRENCY + "} " +
                "and create the financial transaction once get refund response" +
                " and send the event {" + PROP_EVENT_NAME + "}",
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
@ParamString(
        name = TYPE,
        description = "financial type refund"
)
@ParamString(
        name = CURRENCY,
        description = "currency name"
)
@Slf4j
public class CreateFinancialTransactionAfterRefundResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,TYPE,CURRENCY);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String type = context.getProp(TYPE);
        String currency = context.getProp(CURRENCY);
        Event event = context.getEvent();
        RefundCreditResponse refundResponse = CommonUtils.convertObjectToDto(event.getAttributes(),
                new TypeReference<RefundCreditResponse>(){});
        if (null != refundResponse && null != refundResponse.getRefundKey()) {
            String refundKey = refundResponse.getRefundKey();
            CreditMemoService creditMemoService = new CreditMemoService(context);
            GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(refundKey);
            if (null != creditMemo && null != creditMemo.order()) {
                String orderRef = creditMemo.order().ref();
                OrderService orderService = new OrderService(context);
                GetOrdersByRefQuery.Edge orderEdge = orderService.getOrdersByRef(orderRef);
                if (null != orderEdge && null != refundResponse.getAmount()
                        && null != refundResponse.getPaymentMethod() && null != refundResponse.getStatus()){
                    createFinancialTransaction(refundResponse,orderEdge.node().id(),type,currency,context);
                    EventUtils.forwardInline(context,eventName);
                }
            }
        }
    }

    private void createFinancialTransaction(RefundCreditResponse refundResponse, String orderId, String type,
                                            String currency, ContextWrapper context) {
        CreateFinancialTransactionInput createFinancialTransactionInput = CreateFinancialTransactionInput.builder()
                .ref(refundResponse.getRefundKey())
                .type(type)
                .amount(Double.parseDouble(refundResponse.getAmount()))
                .currency(currency)
                .externalTransactionId(refundResponse.getTransactionKey())
                .paymentMethod(refundResponse.getPaymentMethod())
                .paymentProvider(refundResponse.getStatus().equalsIgnoreCase(SUCCESSFUL)?CREDITED:FAILED)
                .order(OrderId.builder().id(orderId).build())
                .externalTransactionCode(refundResponse.getTransactionType())
                .cardType(null != refundResponse.getCardType() ? refundResponse.getCardType().toUpperCase() : "")
                .build();
        CreateFinancialTransactionMutation createFinancialTransactionMutation = CreateFinancialTransactionMutation.builder()
                .input(createFinancialTransactionInput).build();
        context.action().mutation(createFinancialTransactionMutation);
    }
}
