package com.fluentcommerce.rule.order.common.orderstatus;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "CheckOrderStatusMatchesList",
        description = "If the current order status matches the order list {" + PROP_STATUS_LIST + "} ,call event" +
                "{" + PROP_EVENT_NAME + "}  else call no match event {" + PROP_NO_MATCHING_EVENT_NAME + "}",
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
        description = "Outgoing event name to be created and sent if the attribute value matches"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "Outgoing event name to be created and sent if the attribute value not matches"
)
@ParamString(
        name = PROP_STATUS_LIST,
        description = "list of order status"
)

@Slf4j
public class CheckOrderStatusMatchesList extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchingEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);

        List<String> statusList = context.getPropList(PROP_STATUS_LIST,String.class);
        if(statusList.contains(context.getEvent().getEntityStatus())){
            context.addLog("status matches ");
            EventUtils.forwardInline(
                    context,
                    eventName
            );
        }else{
            context.addLog("status does not match ");
            EventUtils.forwardInline(
                    context,
                    noMatchingEventName
            );
        }
    }
}
