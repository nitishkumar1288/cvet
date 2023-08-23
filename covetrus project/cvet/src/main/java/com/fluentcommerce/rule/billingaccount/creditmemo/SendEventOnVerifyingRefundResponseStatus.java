package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.billingaccount.creditmemo.RefundCreditResponse;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
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
        name = "SendEventOnVerifyingRefundResponseStatus",
        description = "compare the status {" + RESPONSE_STATUS + "} from the refund response and send the event " +
                "{" + PROP_EVENT_NAME + "} if match else call no match event {" + PROP_NO_MATCHING_EVENT_NAME + "}",
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
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "forward to next Event name"
)
@ParamString(
        name = RESPONSE_STATUS,
        description = "forward to next Event name"
)
@Slf4j
public class SendEventOnVerifyingRefundResponseStatus extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,PROP_NO_MATCHING_EVENT_NAME,RESPONSE_STATUS);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);
        String responseStatus = context.getProp(RESPONSE_STATUS);
        Event event = context.getEvent();
        RefundCreditResponse refundResponse = CommonUtils.convertObjectToDto(event.getAttributes(),
                new TypeReference<RefundCreditResponse>(){});
        if (null != refundResponse && null != refundResponse.getRefundKey()) {
            String refundKey = refundResponse.getRefundKey();
            CreditMemoService creditMemoService = new CreditMemoService(context);
            GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(refundKey);
            if (null != creditMemo && null != refundResponse.getStatus() && responseStatus.equalsIgnoreCase(refundResponse.getStatus())){
                EventUtils.forwardInline(context,eventName);
            }else {
                EventUtils.forwardInline(context,noMatchEventName);
            }
        }
    }
}
