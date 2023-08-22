package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentSettlementResponse;
import com.fluentcommerce.dto.payment.PaymentTransaction;
import com.fluentcommerce.graphql.mutation.fulfillment.UpdateFulfilmentMutation;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfillmentByRefQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateFulfilmentInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
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
        name = "UpdateAttributeBasedOnPaymentSettlementResponse",
        description = "Update the fulfilment attribute with respect to payment settlement response .fetch the payment status " +
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
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
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
@Slf4j
public class UpdateAttributeBasedOnPaymentSettlementResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,SUCCESS_PAYMENT_STATUS,FAILURE_PAYMENT_STATUS);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String successPaymentStatus = context.getProp(SUCCESS_PAYMENT_STATUS);
        String failurePaymentStatus = context.getProp(FAILURE_PAYMENT_STATUS);
        Event event = context.getEvent();
        String fulfilmentId = event.getEntityId();
        PaymentSettlementResponse paymentSettlementResponse = CommonUtils.convertObjectToDto(event.getAttributes(),
                new TypeReference<PaymentSettlementResponse>(){});
        if (Objects.nonNull(paymentSettlementResponse) && null != paymentSettlementResponse.getFulfilmentRef()){
            ArrayList<String> fulfilmentRefList = new ArrayList<>();
            fulfilmentRefList.add(paymentSettlementResponse.getFulfilmentRef());
            FulfilmentService fulfilmentService = new FulfilmentService(context);
            GetFulfillmentByRefQuery.Fulfilments fulfilments = fulfilmentService.getFulfillments(fulfilmentRefList);
            List<PaymentTransaction> paymentTransactionList = createPaymentTransactionData(fulfilments,paymentSettlementResponse,context,successPaymentStatus,failurePaymentStatus);
            if (CollectionUtils.isNotEmpty(paymentTransactionList)){
                updateFulfilmentAttribute(paymentTransactionList,fulfilmentId,context);
                Map<String, Object> attributes = new HashMap<>();
                attributes.put(PAYMENT_AUTH_STATUS, paymentSettlementResponse.getStatus());
                attributes.put(PAYMENT_SETTLEMENT_RESPONSE, paymentSettlementResponse);
                attributes.putAll(context.getEvent().getAttributes());
                EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
            }
        }
    }

    private List<PaymentTransaction> createPaymentTransactionData(GetFulfillmentByRefQuery.Fulfilments fulfilments,
                                                                  PaymentSettlementResponse paymentSettlementResponse,ContextWrapper context,
                                                                  String successPaymentStatus,String failurePaymentStatus ) {
        List<PaymentTransaction> paymentTransactionList = null;
        if (Objects.nonNull(fulfilments) && null != fulfilments.edges() && !fulfilments.edges().isEmpty()){
            for (GetFulfillmentByRefQuery.Edge itemEdge :fulfilments.edges()){
                Optional<GetFulfillmentByRefQuery.Attribute> optionalPaymentTransactionAttribute = itemEdge.node()
                        .attributes().stream().filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_TRANSACTION)).findFirst();
                if (optionalPaymentTransactionAttribute.isPresent()){
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        paymentTransactionList = mapper.readValue(optionalPaymentTransactionAttribute.get().value().toString(),
                                new TypeReference<List<PaymentTransaction>>() {});
                        updatePaymentTransactionList(paymentSettlementResponse, successPaymentStatus, failurePaymentStatus, paymentTransactionList);
                    } catch (IOException exception) {
                        context.addLog("Exception Occurred when fetching payment transaction attribute " + exception.getMessage());
                        return Collections.emptyList();
                    }
                }else{
                    PaymentTransaction paymentTransaction = createFulfilmentAttribute(paymentSettlementResponse,successPaymentStatus,failurePaymentStatus);
                    paymentTransactionList = new ArrayList<>();
                    paymentTransactionList.add(paymentTransaction);
                }
            }
        }
        return paymentTransactionList;
    }

    private void updatePaymentTransactionList(PaymentSettlementResponse paymentSettlementResponse, String successPaymentStatus, String failurePaymentStatus, List<PaymentTransaction> paymentTransactionList) {
        if(null != paymentTransactionList && !paymentTransactionList.isEmpty()){
            PaymentTransaction paymentTransaction = createFulfilmentAttribute(paymentSettlementResponse, successPaymentStatus, failurePaymentStatus);
            paymentTransactionList.add(paymentTransaction);
        }
    }

    private void updateFulfilmentAttribute(List<PaymentTransaction> paymentTransactionList, String fulfilmentId,
                                           ContextWrapper context) {

        List<AttributeInput> attributeInputList = new ArrayList<>();
        if (null != paymentTransactionList && !paymentTransactionList.isEmpty()){
            attributeInputList.add(AttributeInput.builder().name(PAYMENT_TRANSACTION)
                    .type(ATTRIBUTE_TYPE_JSON).value(paymentTransactionList).build());
        }
        // update the fulfillment attribute
        UpdateFulfilmentInput fulfilmentInput = UpdateFulfilmentInput.builder()
                .id(fulfilmentId)
                .attributes(attributeInputList)
                .build();
        UpdateFulfilmentMutation fulfilmentMutation = UpdateFulfilmentMutation.builder().input(fulfilmentInput).build();
        context.action().mutation(fulfilmentMutation);
    }

    private PaymentTransaction createFulfilmentAttribute(PaymentSettlementResponse paymentSettlementResponse,
                                                         String successPaymentStatus,String failurePaymentStatus) {
        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setTransactionKey(paymentSettlementResponse.getTransactionKey());
        transaction.setTransactionType(paymentSettlementResponse.getTransactionType());
        transaction.setAuthNumber(paymentSettlementResponse.getAuthNumber());
        transaction.setPaymentMethod(paymentSettlementResponse.getPaymentMethod());
        transaction.setProcessorId(paymentSettlementResponse.getProcessorId());
        transaction.setAmount((float) Double.parseDouble(paymentSettlementResponse.getAmount()));
        transaction.setPaymentToken(paymentSettlementResponse.getPaymentToken());
        transaction.setCardType(paymentSettlementResponse.getCardType());
        if (null != paymentSettlementResponse.getErrorCode()){
            transaction.setErrorCode(paymentSettlementResponse.getErrorCode());
        }
        if (successPaymentStatus.equalsIgnoreCase(paymentSettlementResponse.getStatus())){
            transaction.setStatus(PAYMENT_SETTLED_STATUS_SUCCESS);
        }else if (failurePaymentStatus.equalsIgnoreCase(paymentSettlementResponse.getStatus()) ||
                PAYMENT_AUTH_STATUS_NOT_ALLOWED.equalsIgnoreCase(paymentSettlementResponse.getStatus())){
            transaction.setStatus(PAYMENT_SETTLED_STATUS_FAILED);
        }
        transaction.setCreatedDate(paymentSettlementResponse.getCreatedDate());
        return transaction;
    }
}
