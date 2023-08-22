package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.billingaccount.creditmemo.RefundCreditResponse;
import com.fluentcommerce.dto.billingaccount.creditmemo.RefundItem;
import com.fluentcommerce.dto.billingaccount.creditmemo.RefundProcessed;
import com.fluentcommerce.dto.payment.PaymentInfo;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.returns.GetReturnOrderByRefQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
import com.fluentcommerce.service.returns.ReturnService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CreateRefundIssuedData",
        description = "create the processed refund details and send the event {" + PROP_EVENT_NAME + "}",
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
public class CreateRefundIssuedData extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        RefundCreditResponse refundResponse = CommonUtils.convertObjectToDto(event.getAttributes(),
                new TypeReference<RefundCreditResponse>(){});

        if (null != refundResponse && null != refundResponse.getRefundKey()) {
            String refundKey = refundResponse.getRefundKey();
            CreditMemoService creditMemoService = new CreditMemoService(context);
            GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(refundKey);
            if (null != creditMemo && null != creditMemo.order()){
                RefundProcessed refundData = createRefundData(creditMemo,refundResponse,context);
                Map<String, Object> attributes = new HashMap<>();
                attributes.putAll(context.getEvent().getAttributes());
                attributes.put(PROCESSED_REFUND_DATA,refundData);
                EventUtils.forwardEventInlineWithAttributes(
                        context,
                        eventName,
                        attributes);
            }
        }
    }

    private RefundProcessed createRefundData(GetCreditMemoByRefQuery.CreditMemo creditMemo, RefundCreditResponse refundResponse
            ,ContextWrapper context) {
        RefundProcessed refundProcessed = new RefundProcessed();
        refundProcessed.setOrderRef(creditMemo.order().ref());
        refundProcessed.setRefundKey(creditMemo.ref());
        refundProcessed.setStatus(refundResponse.getStatus());
        refundProcessed.setCreatedOn(CommonUtils.getFormatedDate(creditMemo.createdOn().toString(),DATE_FORMAT_TYPE,DATE_FORMAT_TYPE_1));
        refundProcessed.setUpdatedOn(Instant.now().toString());
        if(CollectionUtils.isNotEmpty(creditMemo.attributes() ) && null != creditMemo.items() &&
                CollectionUtils.isNotEmpty(creditMemo.items().edges())){
            Optional<GetCreditMemoByRefQuery.Attribute> optionalPaymentInfo = creditMemo.attributes().stream().
                    filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_INFO)).findFirst();
            Optional<GetCreditMemoByRefQuery.Attribute> optionalRequestAmount = creditMemo.attributes().stream().
                    filter(attribute -> attribute.name().equalsIgnoreCase(REQUESTED_REFUND_AMOUNT)).findFirst();
            PaymentInfo paymentInfo = fetchPaymentInfoAttribute(optionalPaymentInfo,context);
            if (null != paymentInfo && optionalRequestAmount.isPresent()){
                refundProcessed.setAmount(optionalRequestAmount.get().value().toString());
                refundProcessed.setPaymentType(paymentInfo.getPaymentType());
                refundProcessed.setCardNumberLastFour(paymentInfo.getCardNumberLastFour());
                refundProcessed.setCardNumberFirstSix(paymentInfo.getCardNumberFirstSix());
                refundProcessed.setCardType(paymentInfo.getCardType());
                String returnReason = null;
                if(null != creditMemo.returnOrder()){
                    returnReason = getReturnReason(creditMemo, context, refundProcessed, returnReason);
                }else if(null != creditMemo.items() && CollectionUtils.isNotEmpty(creditMemo.items().edges())){
                    returnReason = creditMemo.items().edges().get(0).node().creditReasonCode().value();
                }
                refundProcessed.setRefundReason(returnReason);
            }
        }
        return refundProcessed;
    }

    private String getReturnReason(GetCreditMemoByRefQuery.CreditMemo creditMemo, ContextWrapper context, RefundProcessed refundProcessed, String returnReason) {
        String returnOrderRef = creditMemo.returnOrder().ref();
        ReturnService service = new ReturnService(context);
        GetReturnOrderByRefQuery.ReturnOrder returnOrderData = service.getReturnOrderByRef(returnOrderRef);
        if (null != returnOrderData && CollectionUtils.isNotEmpty(returnOrderData.attributes())){
            Optional<GetReturnOrderByRefQuery.Attribute1> optionalReturnReason = returnOrderData.attributes().
                    stream().filter(attribute -> attribute.name().equalsIgnoreCase(RETURN_REASON)).findFirst();
            if (optionalReturnReason.isPresent()){
                returnReason = optionalReturnReason.get().value().toString();
            }
        }
        ArrayList<RefundItem> orderItemRefList = new ArrayList<>();
        for (GetCreditMemoByRefQuery.Edge returnOrder: creditMemo.items().edges()){
            RefundItem item = new RefundItem();
            item.setRef(returnOrder.node().orderItem().ref());
            orderItemRefList.add(item);
        }
        refundProcessed.setItems(orderItemRefList);
        return returnReason;
    }


    private PaymentInfo fetchPaymentInfoAttribute(Optional<GetCreditMemoByRefQuery.Attribute> optionalPaymentInfo, ContextWrapper context) {
        PaymentInfo paymentInfo = null;
        if (optionalPaymentInfo.isPresent()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                paymentInfo = mapper.readValue(optionalPaymentInfo.get().value().toString(),
                        new TypeReference<PaymentInfo>() {});
            } catch (IOException exception) {
                context.addLog("CreateRefundIssuedData:: Exception Occurred when fetching payment info attribute " + exception.getMessage());
                return null;
            }
        }
        return paymentInfo;
    }
}
