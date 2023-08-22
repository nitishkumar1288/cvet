package com.fluentcommerce.rule.order.common.financialtransaction;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentTransaction;
import com.fluentcommerce.graphql.mutation.payment.CreateFinancialTransactionMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.CreateFinancialTransactionInput;
import com.fluentcommerce.graphql.type.OrderId;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author nitishKumar
 */

@RuleInfo(
        name = "CreateFinancialTransactionForApplePayOrder",
        description = "take the currency type {" + CURRENCY + "} and financial type {" + TYPE + "} of the financial transaction " +
                "and create the financial transaction ",
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
        name = CURRENCY,
        description = "currency name"
)
@ParamString(
        name = TYPE,
        description = "payment type"
)
@Slf4j
public class CreateFinancialTransactionForApplePayOrder extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, CURRENCY, TYPE);
        String currency = context.getProp(CURRENCY);
        String type = context.getProp(TYPE);
        Event event = context.getEvent();
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(orderId);
        if (null != order && CollectionUtils.isNotEmpty(order.attributes())){
            Optional<GetOrderByIdQuery.Attribute> optionalPaymentTransactionAttribute = order.attributes()
                    .stream().filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_TRANSACTION)).findFirst();
            List<PaymentTransaction> paymentTransactionList = null;
            if (optionalPaymentTransactionAttribute.isPresent()){
                ObjectMapper mapper = new ObjectMapper();
                try {
                    paymentTransactionList = mapper.readValue(optionalPaymentTransactionAttribute.get().value().toString(),
                            new TypeReference<List<PaymentTransaction>>() {});
                    if(null != paymentTransactionList && CollectionUtils.isNotEmpty(paymentTransactionList)){
                        PaymentTransaction paymentTransaction = paymentTransactionList.get(0);
                        createFinancialTransaction(context,paymentTransaction,order,type,currency);
                    }
                } catch (IOException exception) {
                    context.addLog("Exception Occurred when fetching payment transaction attribute " + exception.getMessage());
                }
            }
        }
    }

    private void createFinancialTransaction(ContextWrapper context, PaymentTransaction paymentTransaction, GetOrderByIdQuery.OrderById order,String type, String currency) {

        CreateFinancialTransactionInput createFinancialTransactionInput = CreateFinancialTransactionInput.builder()
                .ref(order.ref())
                .type(type)
                .amount(paymentTransaction.getAmount())
                .currency(currency)
                .externalTransactionId(paymentTransaction.getTransactionKey())
                .paymentMethod(paymentTransaction.getPaymentMethod())
                .paymentProvider(paymentTransaction.getStatus())
                .order(OrderId.builder().id(order.id()).build())
                .externalTransactionCode(paymentTransaction.getTransactionType())
                .cardType(paymentTransaction.getCardType().toUpperCase())
                .build();
        CreateFinancialTransactionMutation createFinancialTransactionMutation = CreateFinancialTransactionMutation.builder()
                .input(createFinancialTransactionInput).build();
        context.action().mutation(createFinancialTransactionMutation);
    }
}
