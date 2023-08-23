package com.fluentcommerce.rule.order.creditcard;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentInfo;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Yamini Kukreja
 */

@RuleInfo(
        name = "UpdateCreditCard",
        description = "Update credit card details if payment auth was failure, this update can happen within some days. Send event " +
                PROP_EVENT_NAME + "to update order with the credit card.",
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

@EventAttribute(name = PAYMENT_INFO)
@Slf4j
public class UpdateCreditCard extends BaseRule {

    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        Map<String, Object> eventAttributes = context.getEvent().getAttributes();
        PaymentInfo creditCardResponse = CommonUtils.convertObjectToDto(eventAttributes.get(PAYMENT_INFO),
                new TypeReference<PaymentInfo>() {});
        List<AttributeInput> orderAttributes = new ArrayList<>();
        orderAttributes.add(AttributeInput.builder()
                .name(PAYMENT_INFO)
                .type(ATTRIBUTE_TYPE_JSON)
                .value(creditCardResponse)
                .build());
        OrderService orderService = new OrderService(context);
        orderService.upsertOrderAttributeList(context.getEvent().getEntityId(),orderAttributes);
        EventUtils.forwardEventInlineWithAttributes(context, eventName, eventAttributes);
    }
}
