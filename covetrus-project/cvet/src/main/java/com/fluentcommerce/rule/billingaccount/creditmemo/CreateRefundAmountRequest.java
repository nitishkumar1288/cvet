package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentInfo;
import com.fluentcommerce.dto.billingaccount.creditmemo.RefundCreditRequest;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CreateRefundAmountRequest",
        description = "create the requested refund amount request data and send the event {" + PROP_EVENT_NAME + "}",
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
@EventAttribute(name = AMOUNT_TO_BE_REFUND)
@Slf4j
public class CreateRefundAmountRequest extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        double amountToBeRefund = context.getEvent().getAttribute(AMOUNT_TO_BE_REFUND, Double.class);
        String creditMemoRef = event.getEntityRef();
        CreditMemoService creditMemoService = new CreditMemoService(context);
        GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(creditMemoRef);
        if (null != creditMemo && null != creditMemo.order() && CollectionUtils.isNotEmpty(creditMemo.attributes()) ){
            String orderRef = creditMemo.order().ref();
            Optional<GetCreditMemoByRefQuery.Attribute> optionalPaymentInfo = creditMemo.attributes().stream().
                    filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_INFO)).findFirst();
            PaymentInfo paymentInfo = fetchPaymentInfoAttribute(optionalPaymentInfo,context);
            if(null != paymentInfo){
                RefundCreditRequest refundCreditRequest = createRefundCreditRequest(paymentInfo,orderRef,creditMemoRef,amountToBeRefund);
                Map<String, Object> attributes = new HashMap<>();
                attributes.putAll(context.getEvent().getAttributes());
                attributes.put(REFUND_CREDIT_REQUEST_PAYLOAD,refundCreditRequest);
                EventUtils.forwardEventInlineWithAttributes(
                        context,
                        eventName,
                        attributes,creditMemo.billingAccount().id(),creditMemo.id());
            }
        }
    }

    private RefundCreditRequest createRefundCreditRequest(PaymentInfo paymentInfo, String orderRef, String creditMemoRef,double amountToBeRefund) {
        RefundCreditRequest refundCreditRequest = new RefundCreditRequest();
        refundCreditRequest.setOrderRef(orderRef);
        refundCreditRequest.setPaymentToken(paymentInfo.getPaymentToken());
        refundCreditRequest.setPaymentType( paymentInfo.getPaymentType());
        refundCreditRequest.setGrandTotal((float) amountToBeRefund);
        refundCreditRequest.setRefundKey(creditMemoRef);
        return refundCreditRequest;
    }

    private PaymentInfo fetchPaymentInfoAttribute(Optional<GetCreditMemoByRefQuery.Attribute> optionalPaymentInfo, ContextWrapper context) {
        PaymentInfo paymentInfo = null;
        if (optionalPaymentInfo.isPresent()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                paymentInfo = mapper.readValue(optionalPaymentInfo.get().value().toString(),
                        new TypeReference<PaymentInfo>() {});
            } catch (IOException exception) {
                context.addLog("CreateRefundAmountRequest::Exception Occurred when fetching payment info attribute " + exception.getMessage());
                return null;
            }
        }
        return paymentInfo;
    }
}
