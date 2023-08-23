package com.fluentcommerce.rule.order.cancellationfromconsole;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;


@RuleInfo(
        name = "UpdateEventAttributesToOrderAttributes",
        description = "Update Order attribute from the event attribute list {" + PROP_EVENT_ATTRIBUTE_LIST + "}",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_EVENT_ATTRIBUTE_LIST,
        description = "Event Attribute list to be updated to order"
)

@Slf4j
public class UpdateEventAttributesToOrderAttributes extends BaseRule {

    private static final String CLASS_NAME = UpdateEventAttributesToOrderAttributes.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        List<String> eventAttributeListToBeUpdated = context.getPropList(PROP_EVENT_ATTRIBUTE_LIST,String.class);

        context.addLog("eventAttributeListToBeUpdated " + eventAttributeListToBeUpdated);

        if(CollectionUtils.isNotEmpty(eventAttributeListToBeUpdated)){
            List<AttributeInput> attributeInputList = new ArrayList<>();

            Map<String,Object> eventAttributeMap = event.getAttributes();
            for(String eventAttribute : eventAttributeListToBeUpdated){
                if(eventAttributeMap.containsKey(eventAttribute)){
                    attributeInputList.add(AttributeInput.builder().name(eventAttribute)
                            .type(ATTRIBUTE_TYPE_STRING).value(eventAttributeMap.get(eventAttribute).toString()).build());
                }
            }

            if(CollectionUtils.isNotEmpty(attributeInputList)){
                OrderService orderService = new OrderService(context);
                orderService.upsertOrderAttributeList(event.getEntityId(),attributeInputList);

            }
        }
    }
}