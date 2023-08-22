package com.fluentcommerce.rule.order.pv1.exceptionresolution;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author Nandhakumar
 */

@RuleInfo(
        name = "SendEventIfAnyLineItemCancelledAsPartOfPV1Response",
        description = "Send event {" + PROP_EVENT_NAME + "} if pv1 response contains the status {" + PROP_PV1_RESPONSE_CANCEL_STATUS + "} for any of the line items",
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
        description = "forward to next Event name"
)
@ParamString(
        name = PROP_PV1_RESPONSE_CANCEL_STATUS,
        description = "pv1 response cancel line status"
)

@EventAttribute(name = ITEMS)
@Slf4j
public class SendEventIfAnyLineItemCancelledAsPartOfPV1Response extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        String cancelStatus = context.getProp(PROP_PV1_RESPONSE_CANCEL_STATUS);
        List<PV1Items> items = CommonUtils.convertObjectToDto(context.getEvent().getAttributes().get(ITEMS),new TypeReference<List<PV1Items>>(){});

        boolean isStatusPresent = false;
        if (!items.isEmpty()) {
            for (PV1Items item : items) {

                if (StringUtils.equalsIgnoreCase(item.getStatus(), cancelStatus)) {
                    isStatusPresent = true;
                }
            }
        }

        context.addLog("isStatusPresent " + isStatusPresent);
        if(isStatusPresent){
            EventUtils.forwardInline(
                    context,
                    eventName
            );
        }
    }
}