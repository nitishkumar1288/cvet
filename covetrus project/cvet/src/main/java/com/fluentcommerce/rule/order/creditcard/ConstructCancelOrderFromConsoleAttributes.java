package com.fluentcommerce.rule.order.creditcard;


import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "ConstructCancelOrderFromConsoleAttributes",
        description = "Construct cancel order from console data using {" + CANCEL_REASON + "} and {" + CANCELLED_BY + "} " +
                "and send event {" + PROP_EVENT_NAME + "}",
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
        name = CANCEL_REASON,
        description = "Reason for cancellation"
)

@ParamString(
        name = CANCELLED_BY,
        description = "Cancelled by information"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "Event name to be triggered"
)

@Slf4j
public class ConstructCancelOrderFromConsoleAttributes extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        String cancelledBy = context.getProp(CANCELLED_BY);
        String cancellationReason = context.getProp(CANCEL_REASON);

        Map<String, Object> cancellationAttributes = new HashMap<>();
        cancellationAttributes.put(CANCELLED_BY, cancelledBy);
        cancellationAttributes.put(CANCEL_REASON, cancellationReason);

        EventUtils.forwardEventInlineWithSubtypeAndAttributes(
                context,
                eventName,
                ORDER_HD_SUBTYPE,
                cancellationAttributes);

    }
}
