package com.fluentcommerce.rule.order.paymentauth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentAuthResponse;
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

@RuleInfo(
        name = "UpdateAttributeBasedOnOrderPaymentAuthResponse",
        description = "Update the fulfilment attribute with respect to payment auth response .fetch the payment status " +
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
public class UpdateAttributeBasedOnOrderPaymentAuthResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String successPaymentStatus = context.getProp(SUCCESS_PAYMENT_STATUS);
        String failurePaymentStatus = context.getProp(FAILURE_PAYMENT_STATUS);
        Event event = context.getEvent();
        String orderId = event.getEntityId();
        PaymentAuthResponse paymentAuthResponse = CommonUtils.convertObjectToDto(event.getAttributes(),new TypeReference<PaymentAuthResponse>(){});
        if (Objects.nonNull(paymentAuthResponse) && null != paymentAuthResponse.getOrderRef()){
            OrderService orderService = new OrderService(context);
            GetOrderByIdQuery.OrderById orderById = orderService.getOrderWithOrderItems(orderId);
            if (null != orderById){
                List<PaymentTransaction> paymentTransactionList = createPaymentTransactionData(orderById,paymentAuthResponse,
                        context,successPaymentStatus,failurePaymentStatus);
                if (CollectionUtils.isNotEmpty(paymentTransactionList)){
                    updateOrderAttribute(paymentTransactionList,orderId,orderService);
                    Map<String, Object> attributes = new HashMap<>();
                    attributes.put(PAYMENT_AUTH_STATUS, paymentAuthResponse.getStatus());
                    attributes.put(PAYMENT_AUTH_RESPONSE, paymentAuthResponse);
                    attributes.putAll(context.getEvent().getAttributes());
                    EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
                }
            }
        }
    }

    private List<PaymentTransaction> createPaymentTransactionData(GetOrderByIdQuery.OrderById orderById,
                                                                  PaymentAuthResponse paymentAuthResponse,
                                                                  ContextWrapper context, String successPaymentStatus,
                                                                  String failurePaymentStatus) {
        List<PaymentTransaction> paymentTransactionList = null;
        if (Objects.nonNull(orderById) && CollectionUtils.isNotEmpty(orderById.attributes())){
            Optional<GetOrderByIdQuery.Attribute> optionalPaymentTransactionAttribute = orderById.attributes().stream().
                    filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_TRANSACTION)).findFirst();
                if (optionalPaymentTransactionAttribute.isPresent()){
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        paymentTransactionList = mapper.readValue(optionalPaymentTransactionAttribute.get().value().toString(),
                                new TypeReference<List<PaymentTransaction>>() {});
                        if(null != paymentTransactionList && !paymentTransactionList.isEmpty()){
                            PaymentTransaction paymentTransaction = createPaymentTransaction(paymentAuthResponse,successPaymentStatus,failurePaymentStatus);
                            paymentTransactionList.add(paymentTransaction);
                        }
                    } catch (IOException exception) {
                        context.addLog("Exception Occurred when fetching payment transaction attribute " + exception.getMessage());
                        return Collections.emptyList();
                    }
                }else{
                    PaymentTransaction paymentTransaction = createPaymentTransaction(paymentAuthResponse,successPaymentStatus,failurePaymentStatus);
                    paymentTransactionList = new ArrayList<>();
                    paymentTransactionList.add(paymentTransaction);
                }
        }
        return paymentTransactionList;
    }

    private PaymentTransaction createPaymentTransaction(PaymentAuthResponse paymentAuthResponse, String successPaymentStatus,
                                                         String failurePaymentStatus) {
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setTransactionKey(paymentAuthResponse.getTransactionKey());
        transaction.setTransactionType(paymentAuthResponse.getTransactionType());
        transaction.setAuthNumber(paymentAuthResponse.getAuthNumber());
        transaction.setPaymentMethod(paymentAuthResponse.getPaymentMethod());
        transaction.setProcessorId(paymentAuthResponse.getProcessorId());
        transaction.setAmount((float) Double.parseDouble(paymentAuthResponse.getAmount()));
        transaction.setPaymentToken(paymentAuthResponse.getPaymentToken());
        transaction.setCardType(paymentAuthResponse.getCardType());
        if (successPaymentStatus.equalsIgnoreCase(paymentAuthResponse.getStatus())){
            transaction.setStatus(PAYMENT_AUTH_STATUS_AUTHORIZED);
        }else if (failurePaymentStatus.equalsIgnoreCase(paymentAuthResponse.getStatus()) ||
                PAYMENT_AUTH_STATUS_NOT_ALLOWED.equalsIgnoreCase(paymentAuthResponse.getStatus())){
            transaction.setStatus(PAYMENT_AUTH_STATUS_AUTH_FAILED);
        }
        if (null != paymentAuthResponse.getErrorCode()){
            transaction.setErrorCode(paymentAuthResponse.getErrorCode());
        }
        transaction.setCreatedDate(paymentAuthResponse.getCreatedDate());
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