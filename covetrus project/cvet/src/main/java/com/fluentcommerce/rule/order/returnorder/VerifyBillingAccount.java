package com.fluentcommerce.rule.order.returnorder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetBillingAccountsByCustomerRefQuery;
import com.fluentcommerce.graphql.query.returns.GetReturnOrderByRefQuery;
import com.fluentcommerce.model.billingaccount.AppeasementAttributes;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.BillingAccountService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.service.returns.ReturnService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.exceptions.RubixException;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import com.fluentretail.rubix.v2.context.Context;
import com.fluentretail.rubix.v2.util.RuleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

@RuleInfo(
        name = "VerifyBillingAccount",
        description = "Does a lookup of billing account for the customer, if there is a billing account already exist then send event with the name " +
                "{" + PROP_BILLING_ACCOUNT_EXISTS_EVENT_NAME + "} to billing account workflow to ProcessCredit, otherwise send an event with the name " +
                "{" + PROP_NO_BILLING_ACCOUNT_EXISTS_EVENT_NAME + "} to NewBillingAccount in return workflow",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = ENTITY_TYPE_RETURN_ORDER
                )
        },
        accepts = {
                @EventInfo(
                        entityType = ENTITY_TYPE_RETURN_ORDER
                ),
                @EventInfo(
                        entityType = ENTITY_TYPE_ORDER
                )
        }
)

@ParamString(
        name = PROP_BILLING_ACCOUNT_EXISTS_EVENT_NAME,
        description = "The state to apply to the entity"
)

@ParamString(
        name = PROP_NO_BILLING_ACCOUNT_EXISTS_EVENT_NAME,
        description = "The state to apply to the entity"
)

@Slf4j
public class VerifyBillingAccount extends BaseRule {
    public static final String RULE_NAME = VerifyBillingAccount.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        try {
            Event event = context.getEvent();
            validateEvent(event);
            RuleUtils.validateAllRuleParameterValuesExist(context, PROP_BILLING_ACCOUNT_EXISTS_EVENT_NAME, PROP_NO_BILLING_ACCOUNT_EXISTS_EVENT_NAME);
            Event outgoingEvent;

            switch (context.getEvent().getEntityType()) {
                case ENTITY_TYPE_RETURN_ORDER:
                    outgoingEvent = verifyBillingAccountForReturnOrder(context);
                    break;
                case ENTITY_TYPE_ORDER:
                    outgoingEvent = verifyBillingAccountForOrder(context);
                    break;
                default:
                    throw new IllegalArgumentException("This rule is not compatible with the incoming event's entity type.");
            }
            log.info("[{}] - rule: {} outgoing event {}", context.getEvent().getAccountId(), RULE_NAME, outgoingEvent);
            context.action().sendEvent(outgoingEvent);
        } catch (Exception e) {
            logException(context.getEvent(), e);
            throw new RubixException(422, String.format("Rule: %s - exception message: %s. Stacktrace: %s", RULE_NAME, e.getMessage(), Arrays.toString(e.getStackTrace())));
        }
    }

    private Event verifyBillingAccountForReturnOrder(ContextWrapper context){
        Event event = context.getEvent();

        String customerRef = getReturnOrder(context).customer().ref();
        GetBillingAccountsByCustomerRefQuery.Node billingAccounts = getBillingAccounts(context, customerRef);

        // has billing account, process it
        if (billingAccounts != null) {
            logProcessingCredit(event);
            return buildEventProcessCredit(event, context.getProp(PROP_BILLING_ACCOUNT_EXISTS_EVENT_NAME), billingAccounts);
        }
        // no billing account, create one
        logCreatingBillingAccount(event);
        return buildEventNewBillingAccount(event, context.getProp(PROP_NO_BILLING_ACCOUNT_EXISTS_EVENT_NAME));
    }

    private Event verifyBillingAccountForOrder(ContextWrapper context){
        Event event = context.getEvent();
        String customerRef = getOrder(context).getCustomer().getRef();
        GetBillingAccountsByCustomerRefQuery.Node billingAccounts = getBillingAccounts(context, customerRef);

        // has billing account, process it
        if (billingAccounts != null) {
            logProcessingCredit(event);
            return buildEventWithAppeasementInfoProcessBillingAccount(event, context.getProp(PROP_BILLING_ACCOUNT_EXISTS_EVENT_NAME), billingAccounts);
        }
        // no billing account, create one
        logCreatingBillingAccount(event);
        return buildEventWithAppeasementInfoNewBillingAccount(event, context.getProp(PROP_NO_BILLING_ACCOUNT_EXISTS_EVENT_NAME));
    }

    private void validateEvent(Event event) {
        if (StringUtils.isBlank(event.getRetailerId())) {
            throw new IllegalArgumentException("retailerId is missing in the incoming event info");
        }
        if (StringUtils.isBlank(event.getEntityRef())) {
            throw new IllegalArgumentException("entityRef is missing in the incoming event info");
        }
        if (StringUtils.isBlank(event.getAccountId())) {
            throw new IllegalArgumentException("accountId is missing in the incoming event info");
        }

        switch(event.getEntityType()){
            case ENTITY_TYPE_ORDER:
                if (StringUtils.isBlank(event.getEntityId())) {
                    throw new IllegalArgumentException("entityId is missing in the incoming event info");
                }
                break;
        }
    }

    private Event buildEventProcessCredit(Event event, String eventName, GetBillingAccountsByCustomerRefQuery.Node billingAccounts) {
        Map<String, Object> entityReference = new HashMap<>();
        entityReference.put("ref", billingAccounts.ref());
        entityReference.put("type", ENTITY_TYPE_BILLING_ACCOUNT);
        entityReference.put("subType", billingAccounts.type());

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("entityReference", entityReference);

        return Event.builder()
                .name(eventName)
                .entityRef(event.getEntityRef())
                .entityType(event.getEntityType())
                .entitySubtype(event.getEntitySubtype())
                .accountId(event.getAccountId())
                .retailerId(event.getRetailerId())
                .attributes(attributes)
                .build();
    }


    private Event buildEventWithAppeasementInfoProcessBillingAccount(Event event, String eventName, GetBillingAccountsByCustomerRefQuery.Node billingAccounts){
        Event outgoingEvent = buildEventProcessCredit(event, eventName, billingAccounts);

        Map<String, Object> currentAttributes = outgoingEvent.getAttributes();
        Map<String, Object> appeasementInfoMap = getAppeasementInfo(event);
        if (appeasementInfoMap != null){
            currentAttributes.put(EVENT_ATTR_APPEASEMENT_INFO, appeasementInfoMap);
        }

        return outgoingEvent.toBuilder()
                .entityId(event.getEntityId())
                .attributes(currentAttributes)
                .build();
    }

    private Event buildEventNewBillingAccount(Event event, String eventName) {
        return Event.builder()
                .name(eventName)
                .retailerId(event.getRetailerId())
                .entityRef(event.getEntityRef())
                .accountId(event.getAccountId())
                .entityType(event.getEntityType())
                .entitySubtype(event.getEntitySubtype())
                .build();
    }

    private Event buildEventWithAppeasementInfoNewBillingAccount(Event event, String eventName){
        Event outgoingEvent = buildEventNewBillingAccount(event, eventName);

        Map<String, Object> attributes = new HashMap<>();
        Map<String, Object> appeasementInfoMap = getAppeasementInfo(event);
        if (appeasementInfoMap != null){
            attributes.put(EVENT_ATTR_APPEASEMENT_INFO, appeasementInfoMap);
        }

        return outgoingEvent.toBuilder()
                .entityId(event.getEntityId())
                .attributes(attributes)
                .build();
    }

    private Map<String, Object> getAppeasementInfo(Event event) {
        AppeasementAttributes appeasement = CommonUtils.convertObjectToDto(event.getAttributes(), new TypeReference<AppeasementAttributes>() {});
        Map<String, Object> outgoingAppeasementInfo = null;
        if (appeasement != null) {
            outgoingAppeasementInfo = new HashMap<>();
            outgoingAppeasementInfo.put(APPEASEMENT_AMOUNT, appeasement.getAppeasementAmount());
            outgoingAppeasementInfo.put(APPEASEMENT_REASON, appeasement.getAppeasementReason());
            outgoingAppeasementInfo.put(APPEASEMENT_COMMENT, appeasement.getComment());
            outgoingAppeasementInfo.put(USER_ID, appeasement.getUserId());
            return outgoingAppeasementInfo;
        }
        return outgoingAppeasementInfo;
    }

    private GetBillingAccountsByCustomerRefQuery.Node getBillingAccounts(ContextWrapper context, String customerRef){

        BillingAccountService service = new BillingAccountService(context);
        GetBillingAccountsByCustomerRefQuery.BillingAccounts billingAccounts = service.getBillingAccountByRef(customerRef);
        if(billingAccounts== null || billingAccounts.edges().isEmpty()){
            return null;
        }
        else if(billingAccounts.edges().size() > 1){
            throw new IllegalArgumentException("more than one billing account found");
        }
        else{
            return billingAccounts.edges().get(0).node();
        }
    }

    private GetReturnOrderByRefQuery.ReturnOrder getReturnOrder(Context context){
        Event event = context.getEvent();
        String returnOrderRef = event.getEntityRef();
        ReturnService service = new ReturnService(context);
        return service.getReturnOrderByRef(returnOrderRef);
    }

    private Order getOrder(Context context){
        Event event = context.getEvent();
        String orderId = event.getEntityId();
        OrderService service = new OrderService(context);
        return service.getOrderById(orderId);
    }

    private void logCreatingBillingAccount(Event event){
        log.info("[" + event.getAccountId() + RULE_STATEMENT + VerifyBillingAccount.RULE_NAME + " will attempt to create billing account ");
    }

    private void logProcessingCredit(Event event){
        log.info("[" + event.getAccountId() + RULE_STATEMENT + VerifyBillingAccount.RULE_NAME + " will attempt to process credit ");
    }

    private void logException(Event event, Exception e){
        String exceptionMessage = (e.getMessage() == null) ? e.toString() : e.getMessage();

        log.error("[" + event.getAccountId() + RULE_STATEMENT  + VerifyBillingAccount.RULE_NAME + " exception message: " + exceptionMessage + ", stackTrace: " + Arrays.toString(e.getStackTrace()));
    }

}