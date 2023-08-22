package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.mutation.billingaccount.creditmemo.CreateCreditMemoMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.graphql.type.*;
import com.fluentcommerce.model.billingaccount.AppeasementAttributes;
import com.fluentcommerce.model.billingaccount.EntityReference;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.service.settings.SettingsService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.exceptions.RubixException;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import com.fluentretail.rubix.v2.context.Context;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;
import static com.fluentcommerce.util.returnordervalidator.CreateReturnOrderFromOrderValidators.isEmptyOrBlank;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;


@RuleInfo(
        name = "CreateCreditMemoFromAppeasement",
        description = "Creates a CreditMemo for the BillingAccount using the supplied Order details. This rule has the following two props: {"
                + CREDIT_MEMO_TYPE + "} and {" + CREDIT_MEMO_ITEM_TYPE + "}. If they are not provided it will take the default values: '"
                + ENTITY_TYPE_CREDIT_MEMO + "' and '" + CREDIT_MEMO_ITEM + "'.",
        accepts = {
                @EventInfo(
                        entityType = BILLING_ACCOUNT
                )})

@ParamString(name = CREDIT_MEMO_TYPE, description = "CREDIT_MEMO_TYPE", defaultValue = ENTITY_TYPE_CREDIT_MEMO)
@ParamString(name = CREDIT_MEMO_ITEM_TYPE, description = "CREDIT_MEMO_ITEM_TYPE", defaultValue = CREDIT_MEMO_ITEM)

@EventAttribute(name = EVENT_ATTR_ENTITY_REFERENCE)
@EventAttribute(name = EVENT_ATTR_APPEASEMENT_INFO)
@Slf4j
public class CreateCreditMemoFromAppeasement extends BaseRule {

    public static final String RULE_NAME = CreateCreditMemoFromAppeasement.class.getSimpleName();

    /**
     * Validate the incoming event and props
     * Do the mutation to create a credit memo
     * @param context the Context passed into the rule
     */
    @Override
    public void run(ContextWrapper context) {
        final Event incomingEvent = context.getEvent();
        try {

            AppeasementAttributes appeasement = CommonUtils.convertObjectToDto(incomingEvent.getAttributes().get(EVENT_ATTR_APPEASEMENT_INFO),
                    new TypeReference<AppeasementAttributes>() {});
            validateEvent(context.getEvent(),appeasement);
            EntityReference entryReference = CommonUtils.convertObjectToDto(incomingEvent.getAttributes().get(EVENT_ATTR_ENTITY_REFERENCE),
                    new TypeReference<EntityReference>() {});
            String orderId = entryReference.getId();
            OrderService orderService = new OrderService(context);
            GetOrderByIdQuery.Data getOrderByIdData = orderService.getOrderByIdData(orderId);
            validateOrder(getOrderByIdData, orderId);
            SettingsService settingsService = new SettingsService(context);
            List<String> settingsNameList = new ArrayList<>();
            settingsNameList.add(DEFAULT_TAX_TYPE);
            List<GetSettingsByNameQuery.Edge> settingsList =
                    settingsService.getSettingsByName(
                            context,
                            settingsNameList
                    );

            TaxTypeInput taxTypeSettings = generateTaxTypeInputFromSetting(settingsList);
            final double tariff = getTariffPercentageForTariff(context, taxTypeSettings.tariff());
            context.action().mutation(mutationCreateCreditMemo(context, getOrderByIdData.orderById(), taxTypeSettings, tariff,appeasement));
        }
        catch(Exception e){
            logException(context.getEvent(), e);
            throw new RubixException(422, String.format("Rule: %s - exception message: %s. Stacktrace: %s",
                    RULE_NAME, e.getMessage(), Arrays.toString(e.getStackTrace())));
        }
    }


    /**
     * This method will query the taxType from the setting and wrap it in the TaxTypeInput object.
     * @param settingsList: OPTIONAL, if not provided will take default value of "DEFAULT_TAX_TYPE"
     * @return TaxTypeInput object, for the 'settingName' introduced or DEFAULT_TAX_TYPE in case was not provided.
     */
    public static TaxTypeInput generateTaxTypeInputFromSetting(List<GetSettingsByNameQuery.Edge> settingsList){

        Optional<GetSettingsByNameQuery.Edge> optionalSettingsValue  =
                settingsList.stream().filter(
                        edge -> edge.node().name().equalsIgnoreCase(DEFAULT_TAX_TYPE)
                ).findFirst();

        if (optionalSettingsValue.isPresent()) {
            try {
                ObjectNode lob = (ObjectNode) optionalSettingsValue.get().node().lobValue();

                String country = lob.get(COUNTRY).asText();
                String group = lob.get(GROUP).asText();
                String tariff = lob.get(TARIFF).asText();

                if (isEmptyOrBlank(country) || isEmptyOrBlank(group) || isEmptyOrBlank(tariff)) {
                    throw new IllegalArgumentException("country, group and tariff values must all have at least 1 character");
                }
                return TaxTypeInput.builder().country(country).group(group).tariff(tariff).build();

            } catch (Exception e) {
                throw new IllegalArgumentException(
                        format("setting '%s' incorrectly configured, expect LOB value with 3 keys - country, group, tariff. exception message: %s", DEFAULT_TAX_TYPE, e.getMessage())
                );
            }
        }else{
            throw new IllegalArgumentException(String.format("The setting [%s] was not found. Value found: %s", DEFAULT_TAX_TYPE,optionalSettingsValue));
        }
    }

    public static double getTariffPercentageForTariff(ContextWrapper context, String tariff) {
        SettingsService settingsService = new SettingsService(context);
        List<String> settingsNameList = new ArrayList<>();
        settingsNameList.add(tariff.toUpperCase());
        List<GetSettingsByNameQuery.Edge> settingsList =
                settingsService.getSettingsByName(
                        context,
                        settingsNameList
                );
        GetSettingsByNameQuery.Edge edge  = settingsList.get(0);
        if(edge == null || edge.node() == null || edge.node().name() == null || edge.node().value() == null)
            throw new IllegalArgumentException(String.format("Missing setting for tariff = [%s]", "defaultTariff"));

        try {
            return (Double.parseDouble(edge.node().value()) * 100);
        } catch (Exception ex) {
            throw new IllegalArgumentException(String.format("Error Double.parsing(tariff). For tariff = %s, " +
                    "string value of percentage = %s. Exception = %s", "defaultTariff", edge.node().value(), ex.getMessage()));
        }
    }
    /**
     * Builds the mutation to create a credit memo
     * @param context the Context passed into the rule
     * @return a CreateCreditMemoMutation object
     */
    private CreateCreditMemoMutation mutationCreateCreditMemo(Context context, GetOrderByIdQuery.OrderById order,
                                                              TaxTypeInput taxTypeSettings, double tariff,AppeasementAttributes appeasement){

        GetOrderByIdQuery.OrderItemNode node = order.orderItems().orderItemEdge().get(0).orderItemNode();
        String creditMemoType = StringUtils.defaultIfEmpty(context.getProp(CREDIT_MEMO_TYPE), ENTITY_TYPE_CREDIT_MEMO);
        String creditMemoItemType = StringUtils.defaultIfEmpty(context.getProp(CREDIT_MEMO_ITEM_TYPE), CREDIT_MEMO_ITEM);
        Event event = context.getEvent();
        TaxData taxData = calculateTaxData(appeasement, tariff);
        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(AttributeInput.builder().name(USER_ID)
                .type(ATTRIBUTE_TYPE_STRING).value(appeasement.getUserId()).build());

        CreateCreditMemoInput.Builder createCreditMemoInput = CreateCreditMemoInput.builder()
                .ref(UUID.randomUUID().toString())
                .type(creditMemoType)
                .attributes(attributeInputList)
                .billingAccount(
                        BillingAccountKey.builder()
                                .ref(event.getEntityRef())
                                .build())
                .items(Arrays.asList(CreateCreditMemoItemWithCreditMemoInput.builder()
                        .ref(UUID.randomUUID().toString())
                        .type(creditMemoItemType)
                        .unitQuantity(QuantityTypeInput.builder()
                                .quantity(0)
                                .build())
                        .unitAmount(AmountTypeInput.builder()
                                .amount(0.0)
                                .build())
                        .amount(AmountTypeInput.builder()
                                .amount(taxData.getSubTotalAmount())
                                .build())
                        .taxAmount(AmountTypeInput.builder()
                                .amount(taxData.getTotalTax())
                                .build())
                        .creditReasonCode(SettingValueTypeInput.builder()
                                .value(appeasement.getAppeasementReason())
                                .label("Appeasement Reason")
                                .build())
                        .description((appeasement.getComment()))
                        .build()))
                .order(OrderLinkInput.builder()
                        .ref(order.ref())
                        .retailer(RetailerId.builder()
                                .id(event.getRetailerId())
                                .build())
                        .build())
                .issueDate(new Date())
                .currency(CurrencyKey.builder()
                        .alphabeticCode(node.currency())
                        .build())
                .defaultTaxType(TaxTypeInput.builder()
                        .country(taxTypeSettings.country())
                        .group(taxTypeSettings.group())
                        .tariff(taxTypeSettings.tariff())
                        .build())
                .totalAmount(AmountTypeInput.builder()
                        .amount(taxData.getTotalAmount())
                        .build())
                .subTotalAmount(AmountTypeInput.builder()
                        .amount(taxData.getSubTotalAmount())
                        .build())
                .totalTax(AmountTypeInput.builder()
                        .amount(taxData.getTotalTax())
                        .build())
                .totalBalance(AmountTypeInput.builder()
                        .amount(taxData.getTotalBalance())
                        .build());
        return CreateCreditMemoMutation.builder().input(createCreditMemoInput.build()).build();
    }


    /**
     * Validates the incoming Event by checking for null or empty fields
     * If any fields are missing throw an exception with an appropriate message
     * @param event the incoming Event
     */
    private void validateEvent(Event event,AppeasementAttributes appeasement) {
        final String message = "%s is missing in the incoming event info";
        if (StringUtils.isBlank(event.getEntityRef())) {
            throw new IllegalArgumentException(format(message, "entityRef"));
        }
        if (StringUtils.isBlank(event.getRetailerId())) {
            throw new IllegalArgumentException(format(message, "retailerId"));
        }
        if (event.getAttribute("entityReference", EntityReference.class) == null) {
            throw new IllegalArgumentException(format(message, "entityReference"));
        }
        if (appeasement == null) {
            throw new IllegalArgumentException(format(message, "appeasementInfo"));
        }
        if (appeasement.getUserId() == null) {
            throw new IllegalArgumentException(format(message, "userId"));
        }
    }

    private void validateOrder(GetOrderByIdQuery.Data data, String orderId) {
        if (data== null || data.orderById() == null) {
            throw new IllegalArgumentException(format("Order was not found by id '%s'", orderId));
        }
        GetOrderByIdQuery.OrderById order = data.orderById();
        if (order.orderItems() == null || null == order.orderItems().orderItemEdge() || order.orderItems().orderItemEdge().isEmpty()) {
            throw new IllegalArgumentException(format("Order Items not found by order id '%s'", orderId));
        }
        GetOrderByIdQuery.OrderItemNode node = order.orderItems().orderItemEdge().get(0).orderItemNode();
        if (isBlank(node.currency())){
            throw new IllegalArgumentException(format("Order Item ref '%s' for order id '%s' has no currency", node.ref(), orderId));
        }
    }

    /**
     * Writes an error message to the log stating the exception message
     * @param event the incoming event, used to get the accountId
     * @param e the exception thrown
     */
    private void logException(Event event, Exception e){
        String exceptionMessage = (e.getMessage() == null) ? e.toString() : e.getMessage();

        log.error("[" + event.getAccountId() + "] - rule: " + RULE_NAME + " exception message: " + exceptionMessage + ", stackTrace: " + Arrays.toString(e.getStackTrace()));
    }

    /**
     * TAX CALCULATIONS
     *
     * ASSUMPTION:
     * tariff is a percentage (%).
     *
     * Example:
     * let tariff be 5%
     *
     * let totalAmount be $500.00 (TAX INCLUSIVE)
     *
     * subTotalAmount can be found by the formula:
     * totalAmount / (100.0 + tariff) * 100
     * Which is:
     * 500.00 / (100.0 + 5) * 100
     * Therefore subTotalAmount = 476.19047619
     *
     * totalTax is the difference between totalAmount and subTotalAmount
     * Which is:
     * 500.00 - 476.19047619
     * Therefore totalTax = 23.80952381
     *
     * NOTE: take care when formatting money values as rounding to 2 decimal places can affect these end values,
     * If done incorrectly a small percentage of money could go missing.
     * As of right now, the number of decimal places will not be formatted prior to storing in database
     */
    protected TaxData calculateTaxData(AppeasementAttributes outgoingAppeasementInfo, double tariff){
        // totalAmount - The total amount of this credit memo including tax
        double totalAmount = Double.parseDouble(outgoingAppeasementInfo.getAppeasementAmount());
        // subTotalAmount - The total amount of this credit memo excluding tax
        double subTotalAmount = totalAmount / (100.0 + tariff) * 100;
        // totalTax - The total amount of tax for this credit memo
        double totalTax = totalAmount - subTotalAmount;
        // totalBalance for the moment will be the tax inclusive value from the incoming event
        // totalBalance - The total amount of this credit memo yet to be paid. (This caters for multi-part payments and payment milestones)
        double totalBalance = Double.parseDouble(outgoingAppeasementInfo.getAppeasementAmount());
        return TaxData.builder()
                .tariff(tariff)
                .totalAmount(totalAmount)
                .subTotalAmount(subTotalAmount)
                .totalTax(totalTax)
                .totalBalance(totalBalance)
                .build();
    }

    @AllArgsConstructor
    @Builder(builderClassName = "Builder", toBuilder = true)
    @Getter
    public static class TaxData {

        private double tariff;
        private double totalAmount;
        private double subTotalAmount;
        private double totalTax;
        private double totalBalance;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {}
    }
}