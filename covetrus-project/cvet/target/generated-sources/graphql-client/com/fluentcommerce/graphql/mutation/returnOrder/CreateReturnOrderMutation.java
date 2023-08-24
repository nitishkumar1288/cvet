package com.fluentcommerce.graphql.mutation.returnOrder;

import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.Mutation;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.OperationName;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.api.ResponseFieldMapper;
import com.apollographql.apollo.api.ResponseFieldMarshaller;
import com.apollographql.apollo.api.ResponseReader;
import com.apollographql.apollo.api.ResponseWriter;
import com.apollographql.apollo.api.internal.Mutator;
import com.apollographql.apollo.api.internal.UnmodifiableMapBuilder;
import com.apollographql.apollo.api.internal.Utils;
import com.fluentcommerce.graphql.type.AmountTypeInput;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.CreateReturnOrderItemWithReturnOrderInput;
import com.fluentcommerce.graphql.type.CreateReturnVerificationWithReturnOrderInput;
import com.fluentcommerce.graphql.type.CreditMemoKey;
import com.fluentcommerce.graphql.type.CurrencyKey;
import com.fluentretail.graphql.type.CustomType;
import com.fluentcommerce.graphql.type.CustomerKey;
import com.fluentcommerce.graphql.type.LocationLinkInput;
import com.fluentcommerce.graphql.type.OrderLinkInput;
import com.fluentcommerce.graphql.type.RetailerId;
import com.fluentcommerce.graphql.type.SettingValueTypeInput;
import com.fluentcommerce.graphql.type.StreetAddressInput;
import com.fluentcommerce.graphql.type.TaxTypeInput;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class CreateReturnOrderMutation implements Mutation<CreateReturnOrderMutation.Data, CreateReturnOrderMutation.Data, CreateReturnOrderMutation.Variables> {
  public static final String OPERATION_DEFINITION = "mutation createReturnOrder($ref: String!, $retailer: RetailerId!, $type: String!, $attributes: [AttributeInput], $customer: CustomerKey!, $order: OrderLinkInput, $returnVerifications: [CreateReturnVerificationWithReturnOrderInput], $returnAuthorisationKey: String, $returnAuthorisationDisposition: SettingValueTypeInput, $returnAuthorisationKeyExpiry: DateTime, $pickupAddress: StreetAddressInput, $lodgedLocation: LocationLinkInput, $destinationLocation: LocationLinkInput, $returnOrderItems: [CreateReturnOrderItemWithReturnOrderInput!]!, $exchangeOrder: OrderLinkInput, $creditMemo: CreditMemoKey, $currency: CurrencyKey!, $defaultTaxType: TaxTypeInput!, $subTotalAmount: AmountTypeInput!, $totalTax: AmountTypeInput!, $totalAmount: AmountTypeInput!) {\n"
      + "  createReturnOrder(input: {ref: $ref, retailer: $retailer, type: $type, attributes: $attributes, customer: $customer, order: $order, returnVerifications: $returnVerifications, returnAuthorisationKey: $returnAuthorisationKey, returnAuthorisationDisposition: $returnAuthorisationDisposition, returnAuthorisationKeyExpiry: $returnAuthorisationKeyExpiry, pickupAddress: $pickupAddress, lodgedLocation: $lodgedLocation, destinationLocation: $destinationLocation, returnOrderItems: $returnOrderItems, exchangeOrder: $exchangeOrder, creditMemo: $creditMemo, currency: $currency, defaultTaxType: $defaultTaxType, subTotalAmount: $subTotalAmount, totalTax: $totalTax, totalAmount: $totalAmount}) {\n"
      + "    __typename\n"
      + "    id\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "createReturnOrder";
    }
  };

  private final CreateReturnOrderMutation.Variables variables;

  public CreateReturnOrderMutation(@Nonnull String ref, @Nonnull RetailerId retailer,
      @Nonnull String type, @Nullable List<AttributeInput> attributes,
      @Nonnull CustomerKey customer, @Nullable OrderLinkInput order,
      @Nullable List<CreateReturnVerificationWithReturnOrderInput> returnVerifications,
      @Nullable String returnAuthorisationKey,
      @Nullable SettingValueTypeInput returnAuthorisationDisposition,
      @Nullable Object returnAuthorisationKeyExpiry, @Nullable StreetAddressInput pickupAddress,
      @Nullable LocationLinkInput lodgedLocation, @Nullable LocationLinkInput destinationLocation,
      @Nonnull List<CreateReturnOrderItemWithReturnOrderInput> returnOrderItems,
      @Nullable OrderLinkInput exchangeOrder, @Nullable CreditMemoKey creditMemo,
      @Nonnull CurrencyKey currency, @Nonnull TaxTypeInput defaultTaxType,
      @Nonnull AmountTypeInput subTotalAmount, @Nonnull AmountTypeInput totalTax,
      @Nonnull AmountTypeInput totalAmount) {
    Utils.checkNotNull(ref, "ref == null");
    Utils.checkNotNull(retailer, "retailer == null");
    Utils.checkNotNull(type, "type == null");
    Utils.checkNotNull(customer, "customer == null");
    Utils.checkNotNull(returnOrderItems, "returnOrderItems == null");
    Utils.checkNotNull(currency, "currency == null");
    Utils.checkNotNull(defaultTaxType, "defaultTaxType == null");
    Utils.checkNotNull(subTotalAmount, "subTotalAmount == null");
    Utils.checkNotNull(totalTax, "totalTax == null");
    Utils.checkNotNull(totalAmount, "totalAmount == null");
    variables = new CreateReturnOrderMutation.Variables(ref, retailer, type, attributes, customer, order, returnVerifications, returnAuthorisationKey, returnAuthorisationDisposition, returnAuthorisationKeyExpiry, pickupAddress, lodgedLocation, destinationLocation, returnOrderItems, exchangeOrder, creditMemo, currency, defaultTaxType, subTotalAmount, totalTax, totalAmount);
  }

  @Override
  public String operationId() {
    return "d3ae81c3f97e2aefc38c7be98d7cead4eef819d1762e50a7fd5d00e654840fcb";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public CreateReturnOrderMutation.Data wrapData(CreateReturnOrderMutation.Data data) {
    return data;
  }

  @Override
  public CreateReturnOrderMutation.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<CreateReturnOrderMutation.Data> responseFieldMapper() {
    return new Data.Mapper();
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public OperationName name() {
    return OPERATION_NAME;
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull RetailerId retailer;

    private @Nonnull String type;

    private @Nullable List<AttributeInput> attributes;

    private @Nonnull CustomerKey customer;

    private @Nullable OrderLinkInput order;

    private @Nullable List<CreateReturnVerificationWithReturnOrderInput> returnVerifications;

    private @Nullable String returnAuthorisationKey;

    private @Nullable SettingValueTypeInput returnAuthorisationDisposition;

    private @Nullable Object returnAuthorisationKeyExpiry;

    private @Nullable StreetAddressInput pickupAddress;

    private @Nullable LocationLinkInput lodgedLocation;

    private @Nullable LocationLinkInput destinationLocation;

    private @Nonnull List<CreateReturnOrderItemWithReturnOrderInput> returnOrderItems;

    private @Nullable OrderLinkInput exchangeOrder;

    private @Nullable CreditMemoKey creditMemo;

    private @Nonnull CurrencyKey currency;

    private @Nonnull TaxTypeInput defaultTaxType;

    private @Nonnull AmountTypeInput subTotalAmount;

    private @Nonnull AmountTypeInput totalTax;

    private @Nonnull AmountTypeInput totalAmount;

    Builder() {
    }

    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    public Builder retailer(@Nonnull RetailerId retailer) {
      this.retailer = retailer;
      return this;
    }

    public Builder type(@Nonnull String type) {
      this.type = type;
      return this;
    }

    public Builder attributes(@Nullable List<AttributeInput> attributes) {
      this.attributes = attributes;
      return this;
    }

    public Builder customer(@Nonnull CustomerKey customer) {
      this.customer = customer;
      return this;
    }

    public Builder order(@Nullable OrderLinkInput order) {
      this.order = order;
      return this;
    }

    public Builder returnVerifications(@Nullable List<CreateReturnVerificationWithReturnOrderInput> returnVerifications) {
      this.returnVerifications = returnVerifications;
      return this;
    }

    public Builder returnAuthorisationKey(@Nullable String returnAuthorisationKey) {
      this.returnAuthorisationKey = returnAuthorisationKey;
      return this;
    }

    public Builder returnAuthorisationDisposition(@Nullable SettingValueTypeInput returnAuthorisationDisposition) {
      this.returnAuthorisationDisposition = returnAuthorisationDisposition;
      return this;
    }

    public Builder returnAuthorisationKeyExpiry(@Nullable Object returnAuthorisationKeyExpiry) {
      this.returnAuthorisationKeyExpiry = returnAuthorisationKeyExpiry;
      return this;
    }

    public Builder pickupAddress(@Nullable StreetAddressInput pickupAddress) {
      this.pickupAddress = pickupAddress;
      return this;
    }

    public Builder lodgedLocation(@Nullable LocationLinkInput lodgedLocation) {
      this.lodgedLocation = lodgedLocation;
      return this;
    }

    public Builder destinationLocation(@Nullable LocationLinkInput destinationLocation) {
      this.destinationLocation = destinationLocation;
      return this;
    }

    public Builder returnOrderItems(@Nonnull List<CreateReturnOrderItemWithReturnOrderInput> returnOrderItems) {
      this.returnOrderItems = returnOrderItems;
      return this;
    }

    public Builder exchangeOrder(@Nullable OrderLinkInput exchangeOrder) {
      this.exchangeOrder = exchangeOrder;
      return this;
    }

    public Builder creditMemo(@Nullable CreditMemoKey creditMemo) {
      this.creditMemo = creditMemo;
      return this;
    }

    public Builder currency(@Nonnull CurrencyKey currency) {
      this.currency = currency;
      return this;
    }

    public Builder defaultTaxType(@Nonnull TaxTypeInput defaultTaxType) {
      this.defaultTaxType = defaultTaxType;
      return this;
    }

    public Builder subTotalAmount(@Nonnull AmountTypeInput subTotalAmount) {
      this.subTotalAmount = subTotalAmount;
      return this;
    }

    public Builder totalTax(@Nonnull AmountTypeInput totalTax) {
      this.totalTax = totalTax;
      return this;
    }

    public Builder totalAmount(@Nonnull AmountTypeInput totalAmount) {
      this.totalAmount = totalAmount;
      return this;
    }

    public CreateReturnOrderMutation build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(retailer, "retailer == null");
      Utils.checkNotNull(type, "type == null");
      Utils.checkNotNull(customer, "customer == null");
      Utils.checkNotNull(returnOrderItems, "returnOrderItems == null");
      Utils.checkNotNull(currency, "currency == null");
      Utils.checkNotNull(defaultTaxType, "defaultTaxType == null");
      Utils.checkNotNull(subTotalAmount, "subTotalAmount == null");
      Utils.checkNotNull(totalTax, "totalTax == null");
      Utils.checkNotNull(totalAmount, "totalAmount == null");
      return new CreateReturnOrderMutation(ref, retailer, type, attributes, customer, order, returnVerifications, returnAuthorisationKey, returnAuthorisationDisposition, returnAuthorisationKeyExpiry, pickupAddress, lodgedLocation, destinationLocation, returnOrderItems, exchangeOrder, creditMemo, currency, defaultTaxType, subTotalAmount, totalTax, totalAmount);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nonnull String ref;

    private final @Nonnull RetailerId retailer;

    private final @Nonnull String type;

    private final @Nullable List<AttributeInput> attributes;

    private final @Nonnull CustomerKey customer;

    private final @Nullable OrderLinkInput order;

    private final @Nullable List<CreateReturnVerificationWithReturnOrderInput> returnVerifications;

    private final @Nullable String returnAuthorisationKey;

    private final @Nullable SettingValueTypeInput returnAuthorisationDisposition;

    private final @Nullable Object returnAuthorisationKeyExpiry;

    private final @Nullable StreetAddressInput pickupAddress;

    private final @Nullable LocationLinkInput lodgedLocation;

    private final @Nullable LocationLinkInput destinationLocation;

    private final @Nonnull List<CreateReturnOrderItemWithReturnOrderInput> returnOrderItems;

    private final @Nullable OrderLinkInput exchangeOrder;

    private final @Nullable CreditMemoKey creditMemo;

    private final @Nonnull CurrencyKey currency;

    private final @Nonnull TaxTypeInput defaultTaxType;

    private final @Nonnull AmountTypeInput subTotalAmount;

    private final @Nonnull AmountTypeInput totalTax;

    private final @Nonnull AmountTypeInput totalAmount;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nonnull String ref, @Nonnull RetailerId retailer, @Nonnull String type,
        @Nullable List<AttributeInput> attributes, @Nonnull CustomerKey customer,
        @Nullable OrderLinkInput order,
        @Nullable List<CreateReturnVerificationWithReturnOrderInput> returnVerifications,
        @Nullable String returnAuthorisationKey,
        @Nullable SettingValueTypeInput returnAuthorisationDisposition,
        @Nullable Object returnAuthorisationKeyExpiry, @Nullable StreetAddressInput pickupAddress,
        @Nullable LocationLinkInput lodgedLocation, @Nullable LocationLinkInput destinationLocation,
        @Nonnull List<CreateReturnOrderItemWithReturnOrderInput> returnOrderItems,
        @Nullable OrderLinkInput exchangeOrder, @Nullable CreditMemoKey creditMemo,
        @Nonnull CurrencyKey currency, @Nonnull TaxTypeInput defaultTaxType,
        @Nonnull AmountTypeInput subTotalAmount, @Nonnull AmountTypeInput totalTax,
        @Nonnull AmountTypeInput totalAmount) {
      this.ref = ref;
      this.retailer = retailer;
      this.type = type;
      this.attributes = attributes;
      this.customer = customer;
      this.order = order;
      this.returnVerifications = returnVerifications;
      this.returnAuthorisationKey = returnAuthorisationKey;
      this.returnAuthorisationDisposition = returnAuthorisationDisposition;
      this.returnAuthorisationKeyExpiry = returnAuthorisationKeyExpiry;
      this.pickupAddress = pickupAddress;
      this.lodgedLocation = lodgedLocation;
      this.destinationLocation = destinationLocation;
      this.returnOrderItems = returnOrderItems;
      this.exchangeOrder = exchangeOrder;
      this.creditMemo = creditMemo;
      this.currency = currency;
      this.defaultTaxType = defaultTaxType;
      this.subTotalAmount = subTotalAmount;
      this.totalTax = totalTax;
      this.totalAmount = totalAmount;
      this.valueMap.put("ref", ref);
      this.valueMap.put("retailer", retailer);
      this.valueMap.put("type", type);
      this.valueMap.put("attributes", attributes);
      this.valueMap.put("customer", customer);
      this.valueMap.put("order", order);
      this.valueMap.put("returnVerifications", returnVerifications);
      this.valueMap.put("returnAuthorisationKey", returnAuthorisationKey);
      this.valueMap.put("returnAuthorisationDisposition", returnAuthorisationDisposition);
      this.valueMap.put("returnAuthorisationKeyExpiry", returnAuthorisationKeyExpiry);
      this.valueMap.put("pickupAddress", pickupAddress);
      this.valueMap.put("lodgedLocation", lodgedLocation);
      this.valueMap.put("destinationLocation", destinationLocation);
      this.valueMap.put("returnOrderItems", returnOrderItems);
      this.valueMap.put("exchangeOrder", exchangeOrder);
      this.valueMap.put("creditMemo", creditMemo);
      this.valueMap.put("currency", currency);
      this.valueMap.put("defaultTaxType", defaultTaxType);
      this.valueMap.put("subTotalAmount", subTotalAmount);
      this.valueMap.put("totalTax", totalTax);
      this.valueMap.put("totalAmount", totalAmount);
    }

    public @Nonnull String ref() {
      return ref;
    }

    public @Nonnull RetailerId retailer() {
      return retailer;
    }

    public @Nonnull String type() {
      return type;
    }

    public @Nullable List<AttributeInput> attributes() {
      return attributes;
    }

    public @Nonnull CustomerKey customer() {
      return customer;
    }

    public @Nullable OrderLinkInput order() {
      return order;
    }

    public @Nullable List<CreateReturnVerificationWithReturnOrderInput> returnVerifications() {
      return returnVerifications;
    }

    public @Nullable String returnAuthorisationKey() {
      return returnAuthorisationKey;
    }

    public @Nullable SettingValueTypeInput returnAuthorisationDisposition() {
      return returnAuthorisationDisposition;
    }

    public @Nullable Object returnAuthorisationKeyExpiry() {
      return returnAuthorisationKeyExpiry;
    }

    public @Nullable StreetAddressInput pickupAddress() {
      return pickupAddress;
    }

    public @Nullable LocationLinkInput lodgedLocation() {
      return lodgedLocation;
    }

    public @Nullable LocationLinkInput destinationLocation() {
      return destinationLocation;
    }

    public @Nonnull List<CreateReturnOrderItemWithReturnOrderInput> returnOrderItems() {
      return returnOrderItems;
    }

    public @Nullable OrderLinkInput exchangeOrder() {
      return exchangeOrder;
    }

    public @Nullable CreditMemoKey creditMemo() {
      return creditMemo;
    }

    public @Nonnull CurrencyKey currency() {
      return currency;
    }

    public @Nonnull TaxTypeInput defaultTaxType() {
      return defaultTaxType;
    }

    public @Nonnull AmountTypeInput subTotalAmount() {
      return subTotalAmount;
    }

    public @Nonnull AmountTypeInput totalTax() {
      return totalTax;
    }

    public @Nonnull AmountTypeInput totalAmount() {
      return totalAmount;
    }

    @Override
    public Map<String, Object> valueMap() {
      return Collections.unmodifiableMap(valueMap);
    }

    @Override
    public InputFieldMarshaller marshaller() {
      return new InputFieldMarshaller() {
        @Override
        public void marshal(InputFieldWriter writer) throws IOException {
          writer.writeString("ref", ref);
          writer.writeObject("retailer", retailer.marshaller());
          writer.writeString("type", type);
          writer.writeList("attributes", attributes != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (AttributeInput $item : attributes) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          } : null);
          writer.writeObject("customer", customer.marshaller());
          writer.writeObject("order", order != null ? order.marshaller() : null);
          writer.writeList("returnVerifications", returnVerifications != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (CreateReturnVerificationWithReturnOrderInput $item : returnVerifications) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          } : null);
          writer.writeString("returnAuthorisationKey", returnAuthorisationKey);
          writer.writeObject("returnAuthorisationDisposition", returnAuthorisationDisposition != null ? returnAuthorisationDisposition.marshaller() : null);
          writer.writeCustom("returnAuthorisationKeyExpiry", com.fluentretail.graphql.type.CustomType.DATETIME, returnAuthorisationKeyExpiry);
          writer.writeObject("pickupAddress", pickupAddress != null ? pickupAddress.marshaller() : null);
          writer.writeObject("lodgedLocation", lodgedLocation != null ? lodgedLocation.marshaller() : null);
          writer.writeObject("destinationLocation", destinationLocation != null ? destinationLocation.marshaller() : null);
          writer.writeList("returnOrderItems", new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (CreateReturnOrderItemWithReturnOrderInput $item : returnOrderItems) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          });
          writer.writeObject("exchangeOrder", exchangeOrder != null ? exchangeOrder.marshaller() : null);
          writer.writeObject("creditMemo", creditMemo != null ? creditMemo.marshaller() : null);
          writer.writeObject("currency", currency.marshaller());
          writer.writeObject("defaultTaxType", defaultTaxType.marshaller());
          writer.writeObject("subTotalAmount", subTotalAmount.marshaller());
          writer.writeObject("totalTax", totalTax.marshaller());
          writer.writeObject("totalAmount", totalAmount.marshaller());
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("createReturnOrder", "createReturnOrder", new UnmodifiableMapBuilder<String, Object>(1)
        .put("input", new UnmodifiableMapBuilder<String, Object>(21)
          .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "ref")
          .build())
          .put("retailer", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "retailer")
          .build())
          .put("type", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "type")
          .build())
          .put("attributes", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "attributes")
          .build())
          .put("customer", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "customer")
          .build())
          .put("order", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "order")
          .build())
          .put("returnVerifications", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "returnVerifications")
          .build())
          .put("returnAuthorisationKey", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "returnAuthorisationKey")
          .build())
          .put("returnAuthorisationDisposition", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "returnAuthorisationDisposition")
          .build())
          .put("returnAuthorisationKeyExpiry", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "returnAuthorisationKeyExpiry")
          .build())
          .put("pickupAddress", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "pickupAddress")
          .build())
          .put("lodgedLocation", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "lodgedLocation")
          .build())
          .put("destinationLocation", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "destinationLocation")
          .build())
          .put("returnOrderItems", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "returnOrderItems")
          .build())
          .put("exchangeOrder", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "exchangeOrder")
          .build())
          .put("creditMemo", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "creditMemo")
          .build())
          .put("currency", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "currency")
          .build())
          .put("defaultTaxType", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "defaultTaxType")
          .build())
          .put("subTotalAmount", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "subTotalAmount")
          .build())
          .put("totalTax", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "totalTax")
          .build())
          .put("totalAmount", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "totalAmount")
          .build())
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable CreateReturnOrder createReturnOrder;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable CreateReturnOrder createReturnOrder) {
      this.createReturnOrder = createReturnOrder;
    }

    /**
     * Creates a 'Return order'
     */
    public @Nullable CreateReturnOrder createReturnOrder() {
      return this.createReturnOrder;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], createReturnOrder != null ? createReturnOrder.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "createReturnOrder=" + createReturnOrder
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Data) {
        Data that = (Data) o;
        return ((this.createReturnOrder == null) ? (that.createReturnOrder == null) : this.createReturnOrder.equals(that.createReturnOrder));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (createReturnOrder == null) ? 0 : createReturnOrder.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.createReturnOrder = createReturnOrder;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final CreateReturnOrder.Mapper createReturnOrderFieldMapper = new CreateReturnOrder.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final CreateReturnOrder createReturnOrder = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<CreateReturnOrder>() {
          @Override
          public CreateReturnOrder read(ResponseReader reader) {
            return createReturnOrderFieldMapper.map(reader);
          }
        });
        return new Data(createReturnOrder);
      }
    }

    public static final class Builder {
      private @Nullable CreateReturnOrder createReturnOrder;

      Builder() {
      }

      public Builder createReturnOrder(@Nullable CreateReturnOrder createReturnOrder) {
        this.createReturnOrder = createReturnOrder;
        return this;
      }

      public Builder createReturnOrder(@Nonnull Mutator<CreateReturnOrder.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        CreateReturnOrder.Builder builder = this.createReturnOrder != null ? this.createReturnOrder.toBuilder() : CreateReturnOrder.builder();
        mutator.accept(builder);
        this.createReturnOrder = builder.build();
        return this;
      }

      public Data build() {
        return new Data(createReturnOrder);
      }
    }
  }

  public static class CreateReturnOrder {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public CreateReturnOrder(@Nonnull String __typename, @Nonnull String id) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  ID of the object
     */
    public @Nonnull String id() {
      return this.id;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "CreateReturnOrder{"
          + "__typename=" + __typename + ", "
          + "id=" + id
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof CreateReturnOrder) {
        CreateReturnOrder that = (CreateReturnOrder) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id);
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= __typename.hashCode();
        h *= 1000003;
        h ^= id.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.id = id;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<CreateReturnOrder> {
      @Override
      public CreateReturnOrder map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        return new CreateReturnOrder(__typename, id);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder id(@Nonnull String id) {
        this.id = id;
        return this;
      }

      public CreateReturnOrder build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new CreateReturnOrder(__typename, id);
      }
    }
  }
}
