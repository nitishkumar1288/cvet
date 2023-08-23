package com.fluentcommerce.rule.order.rxservice.approval;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.model.rx.Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

@RuleInfo(
        name = "SendEventIfAnyLineItemStatusMatchesInServResp",
        description = "Send event if any line item status {" + PROP_RX_SERVICE_RESPONSE_ORDER_LINE_STATUS + "} matches from service response  , call event  {" + PROP_EVENT_NAME + "} else call no matching event{" + PROP_NO_MATCHING_EVENT_NAME + "}",
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
        name = PROP_RX_SERVICE_RESPONSE_ORDER_LINE_STATUS,
        description = "order line item status"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "Event name to be called if status matches"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "no matching event name"
)

@EventAttribute(name = ITEMS)
@Slf4j
public class SendEventIfAnyLineItemStatusMatchesInServResp extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_RX_SERVICE_RESPONSE_ORDER_LINE_STATUS,PROP_EVENT_NAME,PROP_NO_MATCHING_EVENT_NAME);

        String rxServRespOrderLineStatus = context.getProp(PROP_RX_SERVICE_RESPONSE_ORDER_LINE_STATUS);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);

        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<Items> rxServiceRespItems = CommonUtils.convertObjectToDto(eventAttributes.get(ITEMS),new TypeReference<List<Items>>(){});

        boolean isStatusMatching = checkForStatusMatchingItems(
                rxServiceRespItems,
                rxServRespOrderLineStatus);

        context.addLog("isStatusMatching " + isStatusMatching);

        if (isStatusMatching){
            EventUtils.forwardInline(context,eventName);
        }else{
            EventUtils.forwardInline(context,noMatchEventName);
        }
    }
    private boolean checkForStatusMatchingItems(
            List<Items> itemList,
            String rxServRespOrderLineStatus
    ) {
        return itemList.stream().anyMatch(itemData -> itemData.getStatus() != null && itemData.getStatus().equalsIgnoreCase(rxServRespOrderLineStatus));
    }
}
