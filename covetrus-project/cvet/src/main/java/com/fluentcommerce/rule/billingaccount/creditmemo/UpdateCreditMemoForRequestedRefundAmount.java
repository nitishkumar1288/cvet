package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.mutation.billingaccount.creditmemo.UpdateCreditMemoMutation;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateCreditMemoInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateCreditMemoForRequestedRefundAmount",
        description = "update credit memo attribute once requested refund amount created" ,
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_CREDIT_MEMO)
        }
)
@EventAttribute(name = AMOUNT_TO_BE_REFUND)
@Slf4j
public class UpdateCreditMemoForRequestedRefundAmount extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String creditMemoRef = event.getEntityRef();
        double amountToBeRefund = context.getEvent().getAttribute(AMOUNT_TO_BE_REFUND, Double.class);
        CreditMemoService creditMemoService = new CreditMemoService(context);
        GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(creditMemoRef);
        if (null != creditMemo && amountToBeRefund >0){
            updateCreditMemoAttribute(creditMemo,amountToBeRefund,context);
        }
    }
    private void updateCreditMemoAttribute(GetCreditMemoByRefQuery.CreditMemo creditMemo, double amountToBeRefund,
                                           ContextWrapper context) {
        List<AttributeInput> attributeInputList = createNewAttributeData(REQUESTED_REFUND_AMOUNT, ATTRIBUTE_TYPE_FLOAT,
                (float) amountToBeRefund);
        UpdateCreditMemoInput updateCreditMemoInput = UpdateCreditMemoInput.builder().
                ref(creditMemo.ref()).
                attributes(attributeInputList).build();
        UpdateCreditMemoMutation updateCreditMemoMutation = UpdateCreditMemoMutation.builder().input(updateCreditMemoInput).build();
        context.action().mutation(updateCreditMemoMutation);
    }

    private List<AttributeInput> createNewAttributeData(String requestedRefundAmount, String attributeTypeFloat, float amountToBeRefund) {
        AttributeInput attrInput = AttributeInput.builder().name(requestedRefundAmount).type(attributeTypeFloat).value(amountToBeRefund).build();
        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(attrInput);
        return attributeInputList;
    }
}
