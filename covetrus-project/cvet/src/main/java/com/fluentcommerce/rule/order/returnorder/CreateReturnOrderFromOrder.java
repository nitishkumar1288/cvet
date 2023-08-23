package com.fluentcommerce.rule.order.returnorder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.mutation.returnOrder.CreateReturnOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.graphql.type.*;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.returnorderhelper.GqlQueryHelper;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.service.settings.SettingsService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import com.fluentretail.rubix.v2.context.Context;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.fluentcommerce.util.returnorderhelper.CreateReturnOrderFromOrderRefGenerator.generateUniqueOrderRefSuffix;
import static com.fluentcommerce.util.returnorderhelper.GqlQueryHelper.isEmptyMap;
import static com.fluentcommerce.util.returnordervalidator.CreateReturnOrderFromOrderValidators.*;
import static com.fluentcommerce.util.Constants.*;
import static java.lang.String.format;
import static java.lang.String.join;

@RuleInfo(
        name = "CreateReturnOrderFromOrder",
        description = "Creates a return entity to begin the process of refunding the returned items.",
        produces = {
                @EventInfo(
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@EventAttribute(name = "userId")
@EventAttribute(name = "returnItems")
@EventAttribute(name = "returnReason")
@Slf4j
public class CreateReturnOrderFromOrder extends BaseRule {

    private static final String CLASS_NAME = CreateReturnOrderFromOrder.class.getSimpleName();

    public static final String DEFAULT_RETURN_ITEM_TYPE = "RETURN_ITEM";

    private static final ISO8601DateFormat DATE_TIME_FORMATTER = new ISO8601DateFormat();

    private  static final String RETURN_REASON = "returnReason";

    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String orderId = event.getEntityId();

        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        validateBasicContext(context);

        String userId = tryGetValueForKeyOrNull(event.getAttributes(), USER_ID, "", String.class);

        String returnReason = event.getAttributes().get(RETURN_REASON).toString();

        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.Data getOrderByIdData = orderService.getOrderByIdData(event.getEntityId());

        validateOrderExists(getOrderByIdData, orderId);

        GqlQueryHelper gqlQueryHelper = new GqlQueryHelper(context);
        Map<String, GetOrderByIdQuery.OrderItemNode> allOrderItems =
                gqlQueryHelper.getAllOrderItemsByOrderItemRef(getOrderByIdData);

        SettingsService settingsService = new SettingsService(context);
        CreateReturnOrderSettings settings = getSettings(
                settingsService,
                context
        );

        CreateReturnOrderMutation.Builder builder = CreateReturnOrderMutation.builder();

        addRetailerId(builder, event);
        addRef(builder, event, getOrderByIdData.orderById().ref());
        addType(builder);
        addCustomerRef(builder, getOrderByIdData);
        addOrderLink(builder, event, getOrderByIdData);
        addLocations(builder, event, settings);
        addCurrency(builder, event, getOrderByIdData);
        addDefaultTaxType(builder, event, settings);
        addReturnMetaData(builder, event);
        addReturnItemsWithAggregateTotals(builder, event, settings, getOrderByIdData, allOrderItems);

        // add user id and return reason to the attribute list of return
        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(
                AttributeInput.builder().name(RETURN_ORDER_CREATED_USER_ID).type(ATTRIBUTE_TYPE_STRING).value(userId).build());
        attributeInputList.add(
                AttributeInput.builder().name(RETURN_REASON).type(ATTRIBUTE_TYPE_STRING).value(returnReason).build());
        builder.attributes(attributeInputList);

        context.action().mutation(builder.build());
    }

    /**
     * Return a simple type containing settings to use as fallback values should they be missing in the event.
     */
    private CreateReturnOrderSettings getSettings(
            SettingsService settingsService,
            ContextWrapper context) {

        List<String> settingsList = new ArrayList<>();
        settingsList.add(DEFAULT_TAX_TYPE);
        settingsList.add(DEFAULT_RETURN_DESTINATION_LOCATION);

        List<GetSettingsByNameQuery.Edge> settingsDataList =
                settingsService.getSettingsByName(
                        context,
                        settingsList
                );

        Optional<GetSettingsByNameQuery.Edge> optionalSettingsValue =
                settingsDataList.stream().filter(
                        edge -> edge.node().name().equalsIgnoreCase(DEFAULT_RETURN_DESTINATION_LOCATION)
                ).findFirst();

        String destinationLocation = null;
        if (optionalSettingsValue.isPresent()) {
            destinationLocation = optionalSettingsValue.get().node().value();
        }

        context.addLog("destinationLocation " + destinationLocation);
        if (isEmptyOrBlank(destinationLocation)) {
            throw new IllegalArgumentException(format("setting '%s' is missing, please provide a valid 'destinationLocation' in the event attributes or configure the setting.",
                    DEFAULT_RETURN_DESTINATION_LOCATION));
        }

        TaxTypeInput taxTypeInput = null;

        optionalSettingsValue =
                settingsDataList.stream().filter(
                        edge -> edge.node().name().equalsIgnoreCase(DEFAULT_TAX_TYPE)
                ).findFirst();

        if (optionalSettingsValue.isPresent()) {
            try {
                ObjectNode lob = (ObjectNode) optionalSettingsValue.get().node().lobValue();

                context.addLog("lob " + lob.toString());

                String country = lob.get(COUNTRY).asText();
                String group = lob.get(GROUP).asText();
                String tariff = lob.get(TARIFF).asText();

                if (isEmptyOrBlank(country) || isEmptyOrBlank(group) || isEmptyOrBlank(tariff)) {
                    throw new IllegalArgumentException("country, group and tariff values must all have at least 1 character");
                }

                taxTypeInput = TaxTypeInput.builder()
                        .country(country)
                        .group(group)
                        .tariff(tariff)
                        .build();

            } catch (Exception e) {
                throw new IllegalArgumentException(
                        format("setting '%s' incorrectly configured, expect LOB value with 3 keys - country, group, tariff. exception message: %s", DEFAULT_TAX_TYPE, e.getMessage())
                );
            }
        }

        return CreateReturnOrderSettings.builder()
                .defaultTaxType(taxTypeInput)
                .destinationLocation(destinationLocation)
                .build();
    }

    /**
     * Try get the product ref from within an OrderItem.
     */
    public static String getProductRef(String rootPath, GetOrderByIdQuery.Product product) {
        if (product == null) {
            throw new IllegalArgumentException(format("Corresponding OrderItem for %s contains missing product", rootPath));
        }
        try {
            if (product.asVariantProduct() != null) {
                return product.asVariantProduct().ref();
            }
        } catch (Exception ignored) {
            log.info(String.valueOf(ignored));
        }

        throw new IllegalArgumentException(format("Error retrieving OrderItem.product.ref for %s", rootPath));
    }

    /**
     * Try get the product catalogue ref from within an OrderItem.
     */
    public static String getCatalogueRef(String rootPath, GetOrderByIdQuery.Product product) {
        if (product == null) {
            throw new IllegalArgumentException(format("Corresponding OrderItem for %s contains missing product", rootPath));
        }
        try {
            if (product.asVariantProduct() != null) {
                return product.asVariantProduct().catalogue().ref();
            }
        } catch (Exception ignored) {
            log.info("ignored " + ignored);
        }

        throw new IllegalArgumentException(format("Error retrieving OrderItem.product.catalogue.ref for %s", rootPath));
    }

    private void validateOrderExists(GetOrderByIdQuery.Data data, String orderId) {
        GetOrderByIdQuery.OrderById order = data.orderById();

        if (order == null) {
            throw new IllegalArgumentException(format("Order was not found by id '%s'", orderId));
        }
    }

    private void addCustomerRef(CreateReturnOrderMutation.Builder builder, GetOrderByIdQuery.Data data) {
        GetOrderByIdQuery.OrderById order = data.orderById();
        GetOrderByIdQuery.Customer customer = order.customer();
        if (customer == null) {
            throw new IllegalArgumentException(format("Order id '%s' has no Customer which is required to create a valid ReturnOrder entity", order.id()));
        }

        String customerRefFromOrder = customer.ref();
        if (StringUtils.isEmpty(customerRefFromOrder)) {
            throw new IllegalArgumentException(format("Order id '%s' has no Customer.ref which is required to create a valid ReturnOrder entity", order.id()));
        }

        builder.customer(CustomerKey.builder().ref(customerRefFromOrder).build());
    }

    private void addRetailerId(CreateReturnOrderMutation.Builder builder, Event event) {
        builder.retailer(RetailerId.builder().id(event.getRetailerId()).build());
    }

    private AmountTypeInput createAmountType(Map<String, Object> amountTypeMap, String rootPath) {
        if (!amountTypeMap.containsKey(AMOUNT)) {
            throw new IllegalArgumentException(format("amount key is missing from '%s'", rootPath));
        }

        return AmountTypeInput.builder().amount(tryGetMoneyValue(amountTypeMap, AMOUNT, rootPath)).build();
    }

    /**
     * Add return order meta data fields if they exist - returnVerifications, returnAuthorisationKey, returnAuthorisationKeyExpiry, returnAuthorisationDisposition
     */
    private void addReturnMetaData(CreateReturnOrderMutation.Builder builder, Event event) {
        addReturnVerifications(builder, event);
        addReturnAuthorisationDisposition(builder, event);

        builder.returnAuthorisationKey(tryGetValueForKeyOrNull(event.getAttributes(), "returnAuthorisationKey", "", String.class));

        String returnAuthorisationKeyExpiry = tryGetValueForKeyOrNull(event.getAttributes(), "returnAuthorisationKeyExpiry", "", String.class);
        if (!isEmptyOrBlank(returnAuthorisationKeyExpiry)) {
            try {
                Date parsedKeyExpiryDate = DATE_TIME_FORMATTER.parse(returnAuthorisationKeyExpiry);
                builder.returnAuthorisationKeyExpiry(parsedKeyExpiryDate);
            } catch (Exception e) {
                throw new IllegalArgumentException("returnAuthorisationKeyExpiry is not in UTC date format, must comply with ISO8601");
            }
        }
    }

    private SettingValueTypeInput createReturnItemSettingValue(Map<String, Object> returnItemMap, String key, String rootPath) {
        Map<String, Object> maybeSettingsMap = tryGetValueForKeyOrNull(returnItemMap, key, rootPath, Map.class);
        if (isEmptyMap(maybeSettingsMap)) {
            return null;
        }

        String path = StringUtils.isEmpty(rootPath) ? key : (rootPath + "." + key);
        Map<String, Object> settingsMap = maybeSettingsMap;

        SettingValueTypeInput.Builder builder = SettingValueTypeInput.builder();
        builder.value(tryGetMandatoryStringOrThrow(settingsMap, "value", path));
        builder.label(tryGetValueForKeyOrNull(settingsMap, "label", path, String.class));

        return builder.build();
    }

    private void addOrderLink(CreateReturnOrderMutation.Builder builder, Event event, GetOrderByIdQuery.Data data) {
        String orderRef = data.orderById().ref();

        if (StringUtils.isEmpty(orderRef)) {
            // Order.ref will always be available as its mandatory for a createOrder gql mutation.
            throw new IllegalArgumentException("Order.ref is empty");
        }

        builder.order(OrderLinkInput.builder()
                .ref(orderRef)
                .retailer(RetailerId.builder().id(event.getRetailerId()).build())
                .build());
    }

    /**
     * if ref is not in the event then auto generate.
     */
    private void addRef(CreateReturnOrderMutation.Builder builder, Event event, String orderRef) {
        String ref = tryGetValueForKeyOrNull(event.getAttributes(), "ref", "", String.class);
        if (isEmptyOrBlank(ref)) {
            ref = format("%s-R%s", orderRef, generateUniqueOrderRefSuffix());
        }
        builder.ref(ref);
    }

    /**
     * type is mandatory
     */
    private void addType(CreateReturnOrderMutation.Builder builder) {
        builder.type(DEFAULT);
    }

    /**
     * Since Order does not store currency, by convention the first OrderItem.currency is used. It's assumed all items
     * will be using the same currency. However, if the event contains a currency, its alphabeticCode must contain at least 1 character otherwise
     * the currency in the first order item will be used.
     */
    private void addCurrency(CreateReturnOrderMutation.Builder builder, Event event, GetOrderByIdQuery.Data getOrderByIdQuery) {
        Map<String, Object> maybeCurrencyMap = tryGetValueForKeyOrNull(event.getAttributes(), "currency", "", Map.class);
        if (!isEmptyMap(maybeCurrencyMap)) {
            String alphabeticCode = tryGetValueForKeyOrNull((maybeCurrencyMap), "alphabeticCode", "currency", String.class);

            if (!isEmptyOrBlank(alphabeticCode)) {
                builder.currency(CurrencyKey.builder().alphabeticCode(alphabeticCode).build());
                return;
            }
        }

        String currency = null;
        try {
            currency = getOrderByIdQuery.orderById().orderItems().orderItemEdge().get(0).orderItemNode().currency();
        } catch (Exception ignored) {
            log.info("ignored " + ignored);
        }
        if (isEmptyOrBlank(currency)) {
            throw new IllegalArgumentException("No currency provided in event and unable to resolve a valid currency from the first order item");
        }
        if(StringUtils.isNotEmpty(currency))
            builder.currency(CurrencyKey.builder().alphabeticCode(currency).build());
    }

    /**
     * returnVerifications is optional, but if provided all 3 keys are mandatory.
     */
    private void addReturnVerifications(CreateReturnOrderMutation.Builder builder, Event event) {
        CreateReturnVerificationWithReturnOrderInput.Builder verificationBuilder = CreateReturnVerificationWithReturnOrderInput.builder();

        Map<String, Object> verificationsMap = event.getAttribute(RETURN_VERIFICATIONS, Map.class);
        if (isEmptyMap(verificationsMap)) {
            return;
        }

        verificationBuilder.ref(tryGetMandatoryStringOrThrow(verificationsMap, "ref", RETURN_VERIFICATIONS));
        verificationBuilder.type(tryGetMandatoryStringOrThrow(verificationsMap, "type", RETURN_VERIFICATIONS));
        verificationBuilder.verificationDetails(tryGetMandatoryStringOrThrow(verificationsMap, "verificationDetails", RETURN_VERIFICATIONS));

        builder.returnVerifications(ImmutableList.of(verificationBuilder.build()));
    }

    /**
     * returnAuthorisationDisposition is optional, but if provided only the 'value' key is mandatory.
     */
    private void addReturnAuthorisationDisposition(CreateReturnOrderMutation.Builder builder, Event event) {
        SettingValueTypeInput.Builder settingBuilder = SettingValueTypeInput.builder();

        Map<String, Object> dispositionMap = event.getAttribute(RETURN_AUTHORISATION_DISPOSITION, Map.class);
        if (isEmptyMap(dispositionMap)) {
            return;
        }

        settingBuilder.value(tryGetMandatoryStringOrThrow(dispositionMap, "value", RETURN_AUTHORISATION_DISPOSITION));
        settingBuilder.label(tryGetValueForKeyOrNull(dispositionMap, "label", RETURN_AUTHORISATION_DISPOSITION, String.class));

        builder.returnAuthorisationDisposition(settingBuilder.build());
    }

    /**
     * Either a {@code pickupLocation} or {@code lodgedLocation} is supplied, not both. No locations are validated.
     * {@code destinationLocation} is resolved from the event, then try use a default setting otherwise the rule fails with an exception.
     */
    private void addLocations(CreateReturnOrderMutation.Builder builder, Event event, CreateReturnOrderSettings settings) {
        String destinationLocation = tryGetValueForKeyOrNull(event.getAttributes(), "destinationLocation", "", String.class);
        if (isEmptyOrBlank(destinationLocation)) {
            destinationLocation = settings.getDestinationLocation();
        }
        builder.destinationLocation(LocationLinkInput.builder().ref(destinationLocation).build());


        String lodgedLocation = settings.getDestinationLocation();
        boolean hasLodgedLocation = !isEmptyOrBlank(lodgedLocation);

        if (!hasLodgedLocation) {
            throw new IllegalArgumentException("Either supply pickupLocation or lodgedLocation, both are missing");
        }

        builder.lodgedLocation(LocationLinkInput.builder().ref(lodgedLocation).build());
    }

    /**
     * event.taxType maps to GQL mutation defaultTaxType
     * <ol>
     * <li>Try get taxType from the event</li>
     * <li>Try lookup the default tax type from settings</li>
     * <li>Throw exception since this field is mandatory in gql mutation</li>
     * </ol>
     */
    private void addDefaultTaxType(CreateReturnOrderMutation.Builder builder, Event event, CreateReturnOrderSettings settings) {
        Map<String, Object> maybeTaxType = tryGetValueForKeyOrNull(event.getAttributes(), TAX_TYPE, "", Map.class);
        if (!isEmptyMap(maybeTaxType)) {
            Map<String, Object> taxTypeMap = maybeTaxType;
            String country = tryGetValueForKeyOrNull(taxTypeMap, COUNTRY, TAX_TYPE, String.class);
            String group = tryGetValueForKeyOrNull(taxTypeMap, GROUP, TAX_TYPE, String.class);
            String tariff = tryGetValueForKeyOrNull(taxTypeMap, TARIFF, TAX_TYPE, String.class);

            if (!isEmptyOrBlank(country) && !isEmptyOrBlank(group) && !isEmptyOrBlank(tariff)) {
                builder.defaultTaxType(
                        TaxTypeInput.builder()
                                .country(country)
                                .group(group)
                                .tariff(tariff)
                                .build()
                );
                return;
            }
        }

        if (settings.getDefaultTaxType() == null) {
            throw new IllegalArgumentException(format("Invalid or missing 'taxType' attribute in ReturnOrder event and there is no fallback setting '%s'", DEFAULT_TAX_TYPE));
        }

        builder.defaultTaxType(settings.getDefaultTaxType());
    }

    /**
     * Either supply all 3 aggregate totals (not some random combination), otherwise if none are supplied it will trigger the return item calculation
     */
    private boolean addReturnOrderAggregateAmounts(CreateReturnOrderMutation.Builder builder, Event event) {
        Map<String, Object> subTotalAmountMap = tryGetValueForKeyOrNull(event.getAttributes(), SUB_TOTAL_AMOUNT, "", Map.class);
        Map<String, Object> totalTaxMap = tryGetValueForKeyOrNull(event.getAttributes(), TOTAL_TAX, "", Map.class);
        Map<String, Object> totalAmountMap = tryGetValueForKeyOrNull(event.getAttributes(), TOTAL_AMOUNT, "", Map.class);

        List<String> foundValidAmount = new ArrayList<>();
        if (!isEmptyMap(subTotalAmountMap)) {
            foundValidAmount.add(SUB_TOTAL_AMOUNT);
        }
        if (!isEmptyMap(totalTaxMap)) {
            foundValidAmount.add(TOTAL_TAX);
        }
        if (!isEmptyMap(totalAmountMap)) {
            foundValidAmount.add(TOTAL_AMOUNT);
        }

        if (foundValidAmount.isEmpty()) {
            // Calculate aggregate totals based on Return OrderItem(s).
            return false;
        }

        if (foundValidAmount.size() != 3) {
            throw new IllegalArgumentException(format("Detected missing aggregate totals, expect all 3 attributes to be provided (subTotalAmount, totalTax, totalAmount) but "
                    + "got (%s)", join(", ", foundValidAmount)));
        }

        builder.subTotalAmount(createAmountType(subTotalAmountMap, "subTotalAmount"));
        builder.totalTax(createAmountType(totalTaxMap, "totalTax"));
        builder.totalAmount(createAmountType(totalAmountMap, "totalAmount"));

        return true;
    }

    private Double calculateReturnItemUnitAmount(Map<String, Object> returnItemMap, GetOrderByIdQuery.OrderItemNode orderItem, String rootPath) {
        Map<String, Object> maybeUnitAmountMap = tryGetValueForKeyOrNull(returnItemMap, "unitAmount", rootPath, Map.class);
        if (!isEmptyMap(maybeUnitAmountMap)) {
            Map<String, Object> unitAmountMap = maybeUnitAmountMap;
            Double unitAmount = tryGetMoneyValue(unitAmountMap, AMOUNT, rootPath + ".unitAmount");
            if (unitAmount != null) {
                return unitAmount;
            }
        }

        Double price = orderItem.price();
        if (price != null && price > 0) {
            return price;
        }

        Double totalPrice = orderItem.totalPrice();
        if (totalPrice != null && totalPrice > 0) {
            return totalPrice / orderItem.quantity();
        }

        throw new IllegalArgumentException(format("%s cannot be calculated due to missing pricing information in the corresponding OrderItem, "
                + "please supply a unitAmount in the event", rootPath + ".unitAmount.amount"));
    }

    private Double calculateReturnItemTaxAmount(Map<String, Object> returnItemMap, GetOrderByIdQuery.OrderItemNode orderItem, int returnItemQuantity, String rootPath) {
        Map<String, Object> maybeItemTaxAmountMap = tryGetValueForKeyOrNull(returnItemMap, "itemTaxAmount", rootPath, Map.class);
        if (!isEmptyMap(maybeItemTaxAmountMap)) {
            Map<String, Object> itemTaxAmountMap = maybeItemTaxAmountMap;
            Double unitTaxAmount = tryGetMoneyValue(itemTaxAmountMap, "amount", rootPath + ".itemTaxAmount");
            if (unitTaxAmount != null) {
                return unitTaxAmount;
            }
        }

        Double taxPrice = orderItem.taxPrice();
        if (taxPrice != null && taxPrice > 0) {
            return taxPrice;
        }

        Double totalTaxPrice = orderItem.totalTaxPrice();
        if (totalTaxPrice != null && totalTaxPrice > 0) {
            return (totalTaxPrice / orderItem.quantity()) * returnItemQuantity;
        }

        return 0.0;
    }

    /**
     * use itemAmount from the event otherwise multiply unitAmount by unitQuantity which are both derived values.
     */
    private Double calculateReturnItemAmount(Map<String, Object> returnItemMap, int unitQuantity, double unitAmount, String rootPath) {
        Map<String, Object> maybeItemAmountMap = tryGetValueForKeyOrNull(returnItemMap, "itemAmount", rootPath, Map.class);
        if (!isEmptyMap(maybeItemAmountMap)) {
            Map<String, Object> itemAmountMap = maybeItemAmountMap;
            Double itemAmount = tryGetMoneyValue(itemAmountMap, AMOUNT, rootPath + ".itemAmount");
            if (itemAmount != null) {
                return itemAmount;
            }
        }
        return unitAmount * unitQuantity;
    }

    /**
     * quantity is mandatory. no other validation is performed as its assumed to have been done in ValidateReturnQty rule.
     */
    private QuantityTypeInput calculateReturnItemUnitQuantity(Map<String, Object> returnItemMap, String rootPath) {
        Map<String, Object> unitQuantityMap = tryGetMandatoryValueForKeyOrThrow(returnItemMap, "unitQuantity", rootPath, Map.class);
        QuantityTypeInput.Builder quantityTypeBuilder = QuantityTypeInput.builder();
        quantityTypeBuilder.quantity(tryGetMandatoryValueForKeyOrThrow(unitQuantityMap, "quantity", rootPath + ".unitQuantity", Integer.class));
        quantityTypeBuilder.unit(tryGetValueForKeyOrNull(unitQuantityMap, "unit", rootPath + ".unitQuantity", String.class));
        return quantityTypeBuilder.build();
    }

    /**
     * Within the return item, taxType maps to unitTaxType. Use the event value at the return item level else fallback to default
     * tax type setting.
     */
    private TaxTypeInput getReturnItemTaxType(Map<String, Object> returnItemMap, CreateReturnOrderSettings settings, String rootPath) {
        Map<String, Object> maybeTaxType = tryGetValueForKeyOrNull(returnItemMap, TAX_TYPE, "", Map.class);
        if (!isEmptyMap(maybeTaxType)) {
            Map<String, Object> taxTypeMap = maybeTaxType;
            String country = tryGetValueForKeyOrNull(taxTypeMap, COUNTRY, TAX_TYPE, String.class);
            String group = tryGetValueForKeyOrNull(taxTypeMap, GROUP, TAX_TYPE, String.class);
            String tariff = tryGetValueForKeyOrNull(taxTypeMap, TARIFF, TAX_TYPE, String.class);

            if (!isEmptyOrBlank(country) && !isEmptyOrBlank(group) && !isEmptyOrBlank(tariff)) {
                return TaxTypeInput.builder().country(country).group(group).tariff(tariff).build();
            }
        }

        if (settings.getDefaultTaxType() == null) {
            throw new IllegalArgumentException(format("Invalid or missing '%s' attribute in event and there is no fallback setting '%s'", rootPath + ".taxType",
                    DEFAULT_TAX_TYPE));
        }

        return settings.getDefaultTaxType();
    }

    /**
     * Add the return items to to the main return order. Return order aggregate totals are calculated from the return items should they be
     * missing in the event.
     */
    private void addReturnItemsWithAggregateTotals(
            CreateReturnOrderMutation.Builder builder,
            Event event, CreateReturnOrderSettings settings,
            GetOrderByIdQuery.Data getOrderByIdData,
            Map<String, GetOrderByIdQuery.OrderItemNode> allOrderItems
    ) {
        ArrayList<Map<String, Object>> maybeRawReturnItems = tryGetValueForKeyOrNull(event.getAttributes(), "returnItems", "", ArrayList.class);
        if (maybeRawReturnItems == null || maybeRawReturnItems.isEmpty()) {
            throw new IllegalArgumentException("returnItems is missing or contains 0 return items");
        }
        ArrayList<Map<String, Object>> rawReturnItems = maybeRawReturnItems;

        List<CreateReturnOrderItemWithReturnOrderInput> returnItems = new ArrayList<>();
        for (int i = 0; i < rawReturnItems.size(); i++) {
            Map<String, Object> returnItem = rawReturnItems.get(i);
            returnItems.add(createReturnItem(i, event, settings, returnItem, getOrderByIdData, allOrderItems));
        }

        builder.returnOrderItems(returnItems);

        // The following line evaluates if the incoming event has the 3 values (totalTax, totalAmount and subTotal)
        // if not, proceeds to calculate individually...
        boolean hasAggregateTotalsSupplied = addReturnOrderAggregateAmounts(builder, event);

        if (!hasAggregateTotalsSupplied) {
            double subTotalAmount = 0.0;
            double totalTaxAmount = 0.0;

            for (CreateReturnOrderItemWithReturnOrderInput returnItem : returnItems) {
                subTotalAmount += returnItem.itemAmount().amount();
                totalTaxAmount += returnItem.itemTaxAmount().amount() * returnItem.unitQuantity().quantity();
            }
            builder.subTotalAmount(AmountTypeInput.builder().amount(subTotalAmount).build());
            builder.totalTax(AmountTypeInput.builder().amount(totalTaxAmount).build());
            builder.totalAmount(AmountTypeInput.builder().amount(subTotalAmount + totalTaxAmount).build());
            log.info(String.format("Manual calculation of: subTotalAmount = '%s', totalTaxAmount = '%s' and totalAmount = '%s'"
                    , subTotalAmount, totalTaxAmount, subTotalAmount + totalTaxAmount));
        }
    }

    private CreateReturnOrderItemWithReturnOrderInput createReturnItem(
            int index,
            Event event,
            CreateReturnOrderSettings settings,
            Map<String, Object> returnItemMap,
            GetOrderByIdQuery.Data getOrderByIdData,
            Map<String, GetOrderByIdQuery.OrderItemNode> allOrderItems
    ) {
        CreateReturnOrderItemWithReturnOrderInput.Builder returnItemBuilder = CreateReturnOrderItemWithReturnOrderInput.builder();

        String rootPath = format("%s[%d]", "returnItems", index);

        // orderItemRef is mandatory, its used to lookup the corresponding OrderItem to perform derived subtotal calculations
        String orderItemRef = tryGetMandatoryValidatedStringOrThrow(returnItemMap, "orderItemRef", rootPath);
        GetOrderByIdQuery.OrderItemNode orderItem = allOrderItems.get(orderItemRef);

        if (orderItem == null) {
            throw new IllegalArgumentException(format("%s.orderItemRef '%s' does not exist in any of the original order items", rootPath, orderItemRef));
        }

        returnItemBuilder.orderItem(
                OrderItemLinkInput.builder()
                        .ref(orderItemRef)
                        .order(OrderLinkInput.builder()
                                .ref(getOrderByIdData.orderById().ref())
                                .retailer(RetailerId.builder().id(event.getRetailerId()).build())
                                .build())
                        .build()
        );

        // use ref if provided, else fallback to using the mandatory orderItemRef
        String ref = tryGetValueForKeyOrNull(returnItemMap, "ref", rootPath, String.class);
        returnItemBuilder.ref(isEmptyOrBlank(ref) ? orderItemRef : ref);

        String type = tryGetValueForKeyOrNull(returnItemMap, "type", rootPath, String.class);
        returnItemBuilder.type(isEmptyOrBlank(type) ? DEFAULT_RETURN_ITEM_TYPE : type);

        // Build the ProductKey from the OrderItem.
        returnItemBuilder.product(
                ProductKey.builder()
                        .ref(getProductRef(rootPath, orderItem.product()))
                        .catalogue(ProductCatalogueKey.builder().ref(getCatalogueRef(rootPath, orderItem.product())).build())
                        .build()
        );

        // unitQuantity
        QuantityTypeInput unitQuantity = calculateReturnItemUnitQuantity(returnItemMap, rootPath);
        returnItemBuilder.unitQuantity(unitQuantity);

        // unitAmount
        Double unitAmount = calculateReturnItemUnitAmount(returnItemMap, orderItem, rootPath);
        returnItemBuilder.unitAmount(AmountTypeInput.builder().amount(unitAmount).build());

        // itemAmount
        Double itemAmount = calculateReturnItemAmount(returnItemMap, unitQuantity.quantity(), unitAmount, rootPath);
        returnItemBuilder.itemAmount(AmountTypeInput.builder().amount(itemAmount).build());

        // itemTaxAmount
        returnItemBuilder.itemTaxAmount(AmountTypeInput.builder().amount(calculateReturnItemTaxAmount(returnItemMap, orderItem, unitQuantity.quantity(), rootPath)).build());

        // unitTaxType
        returnItemBuilder.unitTaxType(getReturnItemTaxType(returnItemMap, settings, rootPath));

        // return item meta data
        returnItemBuilder.returnReasonComment(tryGetValueForKeyOrNull(returnItemMap, "returnReasonComment", rootPath, String.class));
        returnItemBuilder.returnConditionComment(tryGetValueForKeyOrNull(returnItemMap, "returnConditionComment", rootPath, String.class));

        returnItemBuilder.returnReason(createReturnItemSettingValue(returnItemMap, RETURN_REASON, rootPath));
        returnItemBuilder.returnCondition(createReturnItemSettingValue(returnItemMap, "returnCondition", rootPath));
        returnItemBuilder.returnPaymentAction(createReturnItemSettingValue(returnItemMap, "returnPaymentAction", rootPath));
        returnItemBuilder.returnDispositionAction(createReturnItemSettingValue(returnItemMap, "returnDispositionAction", rootPath));

        return returnItemBuilder.build();
    }

    private void validateBasicContext(Context context) {
        Event event = context.getEvent();

        if (isEmptyOrBlank(event.getAccountId())) {
            throw new IllegalArgumentException("accountId is empty");
        }
        if (isEmptyOrBlank(event.getRetailerId())) {
            throw new IllegalArgumentException("retailerId is empty");
        }
        if (isEmptyOrBlank(event.getEntityId())) {
            throw new IllegalArgumentException("entityId is empty");
        }
        if (isEmptyOrBlank(event.getEntityType())) {
            throw new IllegalArgumentException("entityType is empty");
        }
        if (isEmptyOrBlank(event.getEntitySubtype())) {
            throw new IllegalArgumentException("entitySubtype is empty");
        }
        if (isEmptyOrBlank(event.getEntityStatus())) {
            throw new IllegalArgumentException("entityStatus is empty");
        }
        if (isEmptyOrBlank(event.getName())) {
            throw new IllegalArgumentException("event name is empty");
        }
        if (isEmptyMap(event.getAttributes())) {
            throw new IllegalArgumentException("attributes is empty");
        }
    }

    @Builder(builderClassName = "Builder", toBuilder = true)
    @Getter
    public static class CreateReturnOrderSettings {

        /**
         * Contains the configured DB setting or null if no setting exists and attempt to extract values from the inbound event.
         */
        private TaxTypeInput defaultTaxType;

        /**
         * Contains the value from the inbound event, otherwise the db setting should one be configured. This will never be null to
         * simplify consuming code.
         */
        private String destinationLocation;
    }
}

