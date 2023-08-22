package com.fluentcommerce.rule.order.common.financialtransaction;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "SendEventBasedOnTransactionStatus",
        description = "call {" + PROP_EVENT_NAME + "} if status match with{" + AUTHORIZED_TRANSACTION_STATUS + "} else " +
                "call {" + PROP_NO_MATCHING_EVENT_NAME + "} if status match with{" + FAILED_TRANSACTION_STATUS + "}",
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
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "no match event name"
)
@ParamString(
        name = AUTHORIZED_TRANSACTION_STATUS,
        description = "authorized transaction status"
)
@ParamString(
        name = FAILED_TRANSACTION_STATUS,
        description = "failed transaction status"
)

@EventAttribute(name = PAYMENT_AUTH_STATUS)
@Slf4j
public class SendEventBasedOnTransactionStatus extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,PROP_NO_MATCHING_EVENT_NAME, AUTHORIZED_TRANSACTION_STATUS);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);
        String authorizedStatus = context.getProp(AUTHORIZED_TRANSACTION_STATUS);
        List<String> failedStatusList = context.getPropList(FAILED_TRANSACTION_STATUS,String.class);
        String paymentAuthStatus = context.getEvent().getAttribute(PAYMENT_AUTH_STATUS, String.class);
        if (null != paymentAuthStatus && authorizedStatus.equalsIgnoreCase(paymentAuthStatus)){
            EventUtils.forwardInline(context,eventName);
        }else if (null != paymentAuthStatus && failedStatusList.contains(paymentAuthStatus)){
            EventUtils.forwardInline(context,noMatchEventName);
        }
    }
}
