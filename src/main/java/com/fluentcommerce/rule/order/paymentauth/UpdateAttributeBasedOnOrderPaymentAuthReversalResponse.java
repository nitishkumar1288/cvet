package com.fluentcommerce.rule.order.paymentauth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentAuthReversalResponse;
import com.fluentcommerce.dto.payment.PaymentTransaction;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
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

import java.io.IOException;
import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateAttributeBasedOnOrderPaymentAuthReversalResponse",
        description = "Update the order attribute with respect to payment auth reversal response .fetch the payment status " +
                "{" + SUCCESS_PAYMENT_STATUS + "} or failure {" + FAILURE_PAYMENT_STATUS + "}" + "and match with response to update the payment " +
                "transaction status and call to next event{" + PROP_EVENT_NAME + "}",
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
        name = PROP_EVENT_NAME,
        description = "event name"
)
@ParamString(
        name = SUCCESS_PAYMENT_STATUS,
        description = "success payment status"
)
@ParamString(
        name = FAILURE_PAYMENT_STATUS,
        description = "failure payment status"
)

@EventAttribute(name = "attributes")
@Slf4j
public class UpdateAttributeBasedOnOrderPaymentAuthReversalResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,SUCCESS_PAYMENT_STATUS,FAILURE_PAYMENT_STATUS);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String successPaymentStatus = context.getProp(SUCCESS_PAYMENT_STATUS);
        String failurePaymentStatus = context.getProp(FAILURE_PAYMENT_STATUS);
        Event event = context.getEvent();
        String orderId = event.getEntityId();
        PaymentAuthReversalResponse paymentAuthReversalResponse = CommonUtils.convertObjectToDto(event.getAttributes(),
                new TypeReference<PaymentAuthReversalResponse>(){});
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);
        if (null != orderData && CollectionUtils.isNotEmpty(orderData.attributes())){
            List<PaymentTransaction> paymentTransactionList = createPaymentTransactionData(orderData,
                    paymentAuthReversalResponse, context,successPaymentStatus,failurePaymentStatus);
            if (CollectionUtils.isNotEmpty(paymentTransactionList)){
                updateOrderAttribute(paymentTransactionList,orderId,orderService);
                Map<String, Object> attributes = new HashMap<>();
                attributes.put(PAYMENT_AUTH_STATUS, paymentAuthReversalResponse.getStatus());
                attributes.put(PAYMENT_AUTH_REVERSAL_RESPONSE, paymentAuthReversalResponse);
                attributes.putAll(context.getEvent().getAttributes());
                EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
            }
        }
    }

    private List<PaymentTransaction> createPaymentTransactionData(GetOrderByIdQuery.OrderById orderData, PaymentAuthReversalResponse
            paymentAuthReversalResponse, ContextWrapper context, String successPaymentStatus, String failurePaymentStatus) {

        List<PaymentTransaction> paymentTransactionList = null;
        if (Objects.nonNull(orderData) && CollectionUtils.isNotEmpty(orderData.attributes())){
                Optional<GetOrderByIdQuery.Attribute> optionalPaymentTransactionAttribute = orderData.attributes()
                        .stream().filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_TRANSACTION)).findFirst();
                if (optionalPaymentTransactionAttribute.isPresent()){
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        paymentTransactionList = mapper.readValue(optionalPaymentTransactionAttribute.get().value().toString(),
                                new TypeReference<List<PaymentTransaction>>() {});
                        if(null != paymentTransactionList && !paymentTransactionList.isEmpty()){
                            PaymentTransaction paymentTransaction = createPaymentTransaction(paymentAuthReversalResponse,
                                    successPaymentStatus,failurePaymentStatus);
                            paymentTransactionList.add(paymentTransaction);
                        }
                    } catch (IOException exception) {
                        context.addLog("Exception Occurred when fetching payment transaction attribute " + exception.getMessage());
                        return Collections.emptyList();
                    }
                }else{
                    PaymentTransaction paymentTransaction = createPaymentTransaction(paymentAuthReversalResponse,
                            successPaymentStatus,failurePaymentStatus);
                    paymentTransactionList = new ArrayList<>();
                    paymentTransactionList.add(paymentTransaction);
                }
        }
        return paymentTransactionList;
    }

    private PaymentTransaction createPaymentTransaction(PaymentAuthReversalResponse paymentAuthReversalResponse,
                                                        String successPaymentStatus, String failurePaymentStatus) {
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setTransactionKey(paymentAuthReversalResponse.getTransactionKey());
        transaction.setTransactionType(paymentAuthReversalResponse.getTransactionType());
        transaction.setAuthNumber(paymentAuthReversalResponse.getAuthNumber());
        transaction.setPaymentMethod(paymentAuthReversalResponse.getPaymentMethod());
        transaction.setProcessorId(paymentAuthReversalResponse.getProcessorId());
        transaction.setAmount((float) Double.parseDouble(paymentAuthReversalResponse.getAmount()));
        transaction.setPaymentToken(paymentAuthReversalResponse.getPaymentToken());
        transaction.setCardType(paymentAuthReversalResponse.getCardType());
        if (successPaymentStatus.equalsIgnoreCase(paymentAuthReversalResponse.getStatus())){
            transaction.setStatus(PAYMENT_AUTH_REVISED);
        }
        if (failurePaymentStatus.equalsIgnoreCase(paymentAuthReversalResponse.getStatus())
                || PAYMENT_AUTH_STATUS_NOT_ALLOWED.equalsIgnoreCase(paymentAuthReversalResponse.getStatus())){
            transaction.setStatus(PAYMENT_AUTH_REVISED_FAILED);
        }
        if (null != paymentAuthReversalResponse.getErrorCode()){
            transaction.setErrorCode(paymentAuthReversalResponse.getErrorCode());
        }
        transaction.setCreatedDate(paymentAuthReversalResponse.getCreatedDate());
        return transaction;
    }

    private void updateOrderAttribute(List<PaymentTransaction> paymentTransactionList, String orderId, OrderService orderService) {
        List<AttributeInput> attributeInputList = getOrderAttributeForUpdate(paymentTransactionList);
        orderService.upsertOrderAttributeList(orderId,attributeInputList);
    }

    private List<AttributeInput> getOrderAttributeForUpdate(List<PaymentTransaction> paymentTransactionList) {
        List<AttributeInput> attributeList = new ArrayList<>();
        AttributeInput orderSubTypeAttribute = AttributeInput.builder().name(PAYMENT_TRANSACTION)
                .type(ATTRIBUTE_TYPE_JSON).value(paymentTransactionList).build();
        attributeList.add(orderSubTypeAttribute);
        return attributeList;
    }
}
