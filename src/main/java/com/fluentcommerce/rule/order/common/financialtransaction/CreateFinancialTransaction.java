package com.fluentcommerce.rule.order.common.financialtransaction;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentAuthResponse;
import com.fluentcommerce.dto.payment.PaymentAuthReversalResponse;
import com.fluentcommerce.dto.payment.PaymentSettlementResponse;
import com.fluentcommerce.graphql.mutation.payment.CreateFinancialTransactionMutation;
import com.fluentcommerce.graphql.type.CreateFinancialTransactionInput;
import com.fluentcommerce.graphql.type.OrderId;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author nitishKumar
 */

@RuleInfo(
        name = "CreateFinancialTransaction",
        description = "take the currency type {" + CURRENCY + "} and financial type {" + TYPE + "} of the financial transaction " +
                "and create the financial transaction data based on payment status {" + PAYMENT_STATUS + "} with" +
                " {" + SUCCESS_PAYMENT_STATUS + "} or failure {" + FAILURE_PAYMENT_STATUS + "}",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER),
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)

        }
)
@ParamString(
        name = CURRENCY,
        description = "currency name"
)
@ParamString(
        name = PAYMENT_STATUS,
        description = "payment type"
)
@ParamString(
        name = TYPE,
        description = "payment type"
)
@ParamString(
        name = SUCCESS_PAYMENT_STATUS,
        description = "success payment status"
)
@ParamString(
        name = FAILURE_PAYMENT_STATUS,
        description = "failure payment status"
)

@EventAttribute(name = PAYMENT_AUTH_RESPONSE)
@EventAttribute(name = PAYMENT_SETTLEMENT_RESPONSE)
@EventAttribute(name = PAYMENT_AUTH_REVERSAL_RESPONSE)
@Slf4j
public class CreateFinancialTransaction extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, CURRENCY, TYPE, PAYMENT_STATUS, SUCCESS_PAYMENT_STATUS, FAILURE_PAYMENT_STATUS);
        String currency = context.getProp(CURRENCY);
        String paymentStatus = context.getProp(PAYMENT_STATUS);
        String successPaymentStatus = context.getProp(SUCCESS_PAYMENT_STATUS);
        String failurePaymentStatus = context.getProp(FAILURE_PAYMENT_STATUS);
        String type = context.getProp(TYPE);
        Event event = context.getEvent();
        String orderId = event.getRootEntityId();
        String ref = null;
        String externalTransactionId = null;
        String paymentMethod = null;
        String paymentProvider = null;
        String externalTransactionCode = null;
        String cardType = null;
        float amount = 0;
        if (paymentStatus.equalsIgnoreCase(AUTH)) {
            PaymentAuthResponse response = CommonUtils.convertObjectToDto(event.getAttributes().get(PAYMENT_AUTH_RESPONSE),
                    new TypeReference<PaymentAuthResponse>() {
                    });
            ref = response.getOrderRef();
            externalTransactionId = response.getTransactionKey();
            paymentMethod = response.getPaymentMethod();
            externalTransactionCode = response.getTransactionType();
            paymentProvider = findPaymentAuthStatus(successPaymentStatus, failurePaymentStatus, paymentProvider, response);
            amount = (float) Double.parseDouble(response.getAmount());
            cardType = null != response.getCardType() ? response.getCardType().toUpperCase() : "";
        }

        if (paymentStatus.equalsIgnoreCase(SETTLEMENT)) {
            PaymentSettlementResponse response = CommonUtils.convertObjectToDto(event.getAttributes().get(PAYMENT_SETTLEMENT_RESPONSE),
                    new TypeReference<PaymentSettlementResponse>() {
                    });
            ref = response.getFulfilmentRef();
            externalTransactionId = response.getTransactionKey();
            paymentMethod = response.getPaymentMethod();
            externalTransactionCode = response.getTransactionType();
            paymentProvider = findPaymentSettlementStatus(successPaymentStatus, failurePaymentStatus, paymentProvider, response);
            amount = (float) Double.parseDouble(response.getAmount());
            cardType = null != response.getCardType() ? response.getCardType().toUpperCase() : "";
        }

        if (paymentStatus.equalsIgnoreCase(AUTH_REVERSAL)) {
            PaymentAuthReversalResponse response = CommonUtils.convertObjectToDto(event.getAttributes().get(PAYMENT_AUTH_REVERSAL_RESPONSE),
                    new TypeReference<PaymentAuthReversalResponse>() {
                    });
            ref = response.getOrderRef();
            externalTransactionId = response.getTransactionKey();
            paymentMethod = response.getPaymentMethod();
            externalTransactionCode = response.getTransactionType();
            cardType = null != response.getCardType() ? response.getCardType().toUpperCase() : "";
            if (successPaymentStatus.equalsIgnoreCase(response.getStatus())) {
                paymentProvider = PAYMENT_AUTH_REVISED;
            } else if (failurePaymentStatus.equalsIgnoreCase(response.getStatus()) || PAYMENT_AUTH_STATUS_NOT_ALLOWED.equalsIgnoreCase(response.getStatus())) {
                paymentProvider = PAYMENT_AUTH_REVISED_FAILED;
            }
            amount = (float) Double.parseDouble(response.getAmount());
        }

        if (ref != null) {
            CreateFinancialTransactionInput createFinancialTransactionInput = CreateFinancialTransactionInput.builder()
                    .ref(ref)
                    .type(type)
                    .amount(amount)
                    .currency(currency)
                    .externalTransactionId(externalTransactionId)
                    .paymentMethod(paymentMethod)
                    .paymentProvider(paymentProvider)
                    .order(OrderId.builder().id(orderId).build())
                    .externalTransactionCode(externalTransactionCode)
                    .cardType(cardType)
                    .build();
            CreateFinancialTransactionMutation createFinancialTransactionMutation = CreateFinancialTransactionMutation.builder()
                    .input(createFinancialTransactionInput).build();
            context.action().mutation(createFinancialTransactionMutation);
        }
    }

    private String findPaymentAuthStatus(String successPaymentStatus, String failurePaymentStatus, String paymentProvider, PaymentAuthResponse response) {
        if (successPaymentStatus.equalsIgnoreCase(response.getStatus())) {
            paymentProvider = PAYMENT_AUTH_STATUS_AUTHORIZED;
        } else if (failurePaymentStatus.equalsIgnoreCase(response.getStatus()) || PAYMENT_AUTH_STATUS_NOT_ALLOWED.equalsIgnoreCase(response.getStatus())) {
            paymentProvider = PAYMENT_AUTH_STATUS_AUTH_FAILED;
        }
        return paymentProvider;
    }

    private String findPaymentSettlementStatus(String successPaymentStatus, String failurePaymentStatus, String paymentProvider, PaymentSettlementResponse response) {
        if (successPaymentStatus.equalsIgnoreCase(response.getStatus())) {
            paymentProvider = PAYMENT_SETTLED_STATUS_SUCCESS;
        } else if (failurePaymentStatus.equalsIgnoreCase(response.getStatus()) || PAYMENT_AUTH_STATUS_NOT_ALLOWED.equalsIgnoreCase(response.getStatus())) {
            paymentProvider = PAYMENT_SETTLED_STATUS_FAILED;
        }
        return paymentProvider;
    }
}
