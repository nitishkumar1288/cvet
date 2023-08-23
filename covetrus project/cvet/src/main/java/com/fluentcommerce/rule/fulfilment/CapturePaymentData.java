package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentData;
import com.fluentcommerce.dto.payment.PaymentTransaction;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
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
        name = "CapturePaymentData",
        description = "fetch the payment transaction data based on payment status {" + PAYMENT_STATUS + "} " +
                "from the fulfilment to create payment data and " +
                "call to next event failure {" + PROP_EVENT_NAME + "}",
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
        name = PAYMENT_STATUS,
        description = "payment status"
)
@Slf4j
public class CapturePaymentData extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,PAYMENT_STATUS);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String paymentStatus = context.getProp(PAYMENT_STATUS);
        Event event = context.getEvent();
        String fulfilmentId = event.getEntityId();
        String orderRef = event.getRootEntityRef();
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfilmentId);
        List<PaymentTransaction> paymentTransactionList = fetchPaymentTransactionList(fulfillmentById,context);
        if (CollectionUtils.isNotEmpty(paymentTransactionList)){
            Optional<PaymentTransaction> settledPaymentTransaction = paymentTransactionList.stream().filter
                    (paymentTransaction -> paymentTransaction.getStatus().equalsIgnoreCase(paymentStatus)).findFirst();
            if (settledPaymentTransaction.isPresent()){
                PaymentData data = createPaymentData(settledPaymentTransaction.get(),orderRef,fulfillmentById);
                Map<String, Object> attributes = new HashMap<>();
                attributes.put(CAPTURED_PAYMENT_DATA, data);
                attributes.putAll(context.getEvent().getAttributes());
                EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
            }
        }
    }

    private List<PaymentTransaction> fetchPaymentTransactionList(GetFulfilmentByIdQuery.FulfilmentById fulfillmentById,ContextWrapper context) {
        Optional<GetFulfilmentByIdQuery.Attribute1> optionalPaymentTransactionAttribute = fulfillmentById.attributes().
                stream().filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_TRANSACTION)).findFirst();
        List<PaymentTransaction> paymentTransactionList = null;
        if (optionalPaymentTransactionAttribute.isPresent()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                paymentTransactionList = mapper.readValue(optionalPaymentTransactionAttribute.get().value().toString(),
                        new TypeReference<List<PaymentTransaction>>() {});

            } catch (IOException exception) {
                context.addLog("Exception Occurred when fetching payment transaction attribute " + exception.getMessage());
                return new ArrayList<>();
            }
        }
        return paymentTransactionList;
    }

    private PaymentData createPaymentData(PaymentTransaction settledPaymentTransaction, String orderRef,
                                          GetFulfilmentByIdQuery.FulfilmentById fulfillmentById) {
        PaymentData data = new PaymentData();
        data.setOrderRef(orderRef);
        data.setTransactionKey(settledPaymentTransaction.getTransactionKey());
        data.setTransactionType(settledPaymentTransaction.getTransactionType());
        data.setStatus(settledPaymentTransaction.getStatus());
        data.setPaymentType(settledPaymentTransaction.getPaymentMethod());
        data.setTotalPrice(settledPaymentTransaction.getAmount());
        data.setCreatedOn(settledPaymentTransaction.getCreatedDate());
        data.setPaymentToken(settledPaymentTransaction.getPaymentToken());
        data.setFulfilmentRef(fulfillmentById.ref());
        data.setAuthNumber(settledPaymentTransaction.getAuthNumber());
        data.setProcessorId(settledPaymentTransaction.getProcessorId());
        return data;
    }
}
