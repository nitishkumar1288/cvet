package com.fluentcommerce.rule.order.common.orderstatus.appeasement;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.exceptions.RubixException;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;
import static java.lang.String.format;

@RuleInfo(
        name = "ValidateAppeasementAmount",
        description = "Validate the appeasement amount does not exceed the available amount to refund",
        accepts = {
                @EventInfo(
                        entityType = ENTITY_TYPE_ORDER
                )
        }
)

@EventAttribute(name = "appeasementAmount")
@EventAttribute(name = "appeasementReason")
@EventAttribute(name = "comment")
@EventAttribute(name = "userId")
@Slf4j
public class ValidateAppeasementAmount extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event incomingEvent = context.getEvent();
        String accountId = incomingEvent.getAccountId();
        String orderId = incomingEvent.getEntityId();
        try {
            validateEvent(incomingEvent);
            String appeasementAmount = incomingEvent.getAttributes().get(APPEASEMENT_AMOUNT).toString();
            double requestedAmount = getAppeasementAmount(appeasementAmount, orderId);
            OrderService orderService = new OrderService(context);
            GetOrderByIdQuery.OrderById orderData = orderService.getOrderWithOrderAttributes(orderId);
            if (null != orderData){
                Optional<GetOrderByIdQuery.Attribute> availableAmountToRefund = orderData.attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(AVAILABLE_AMOUNT_TO_REFUND)).findFirst();
                if (availableAmountToRefund.isPresent()){
                    double availableAmount = Double.parseDouble(availableAmountToRefund.get().value().toString());
                    if (requestedAmount > availableAmount){
                        throw new IllegalArgumentException(format("requested amount cannot exceed available amount for refund for orderId %s", orderId));
                    }
                }else{
                    throw new IllegalArgumentException(format("Appeasement Initiated without available amount for refund for order %s",orderId));
                }
                if (orderData.totalPrice() == null) {
                    throw new IllegalArgumentException(format("totalPrice for orderId %s is null", orderId));
                }
            }else{
                throw new IllegalArgumentException(format("orderId %s is not the valid one", orderId));
            }
        } catch (Exception ex) {
            log.error("[{}] - rule: {} exception message: {}, stackTrace: {}",
                    accountId, "ValidateAppeasementAmount", ex.getMessage() == null ? ex.toString() : ex.getMessage(), Arrays.toString(ex.getStackTrace()));
            throw new RubixException(422,
                    format("Rule: " + "ValidateAppeasementAmount" + " - exception message: %s. Stacktrace: %s", ex.getMessage(), Arrays.toString(ex.getStackTrace())));
        }
    }

    private double getAppeasementAmount(String appeasementAmount, String orderId) {
        try {
            double amount = Double.parseDouble(appeasementAmount);
            if (amount > 0) {
                return amount;
            }
        } catch (Exception ignored) {
            log.info("ignored exception " + ignored);
        }
        throw new IllegalArgumentException(format("expect attribute '%s' to be a positive numeric value. got=%s, orderId=%s",
                appeasementAmount, appeasementAmount, orderId));
    }

    private void validateEvent(Event event) {
        if (StringUtils.isEmpty(event.getEntityId())) {
            throw new IllegalArgumentException("entityId is missing in the incoming event info");
        }
    }
}