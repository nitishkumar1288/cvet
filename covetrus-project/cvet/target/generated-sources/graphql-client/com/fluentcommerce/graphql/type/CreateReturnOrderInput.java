package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class CreateReturnOrderInput {
  private final @Nonnull String ref;

  private final @Nonnull RetailerId retailer;

  private final @Nonnull String type;

  private final Input<List<AttributeInput>> attributes;

  private final @Nonnull CustomerKey customer;

  private final Input<OrderLinkInput> order;

  private final Input<List<CreateReturnVerificationWithReturnOrderInput>> returnVerifications;

  private final Input<String> returnAuthorisationKey;

  private final Input<SettingValueTypeInput> returnAuthorisationDisposition;

  private final Input<Object> returnAuthorisationKeyExpiry;

  private final Input<StreetAddressInput> pickupAddress;

  private final Input<LocationLinkInput> lodgedLocation;

  private final Input<LocationLinkInput> destinationLocation;

  private final @Nonnull List<CreateReturnOrderItemWithReturnOrderInput> returnOrderItems;

  private final Input<OrderLinkInput> exchangeOrder;

  private final Input<CreditMemoKey> creditMemo;

  private final @Nonnull CurrencyKey currency;

  private final @Nonnull TaxTypeInput defaultTaxType;

  private final @Nonnull AmountTypeInput subTotalAmount;

  private final @Nonnull AmountTypeInput totalTax;

  private final @Nonnull AmountTypeInput totalAmount;

  CreateReturnOrderInput(@Nonnull String ref, @Nonnull RetailerId retailer, @Nonnull String type,
      Input<List<AttributeInput>> attributes, @Nonnull CustomerKey customer,
      Input<OrderLinkInput> order,
      Input<List<CreateReturnVerificationWithReturnOrderInput>> returnVerifications,
      Input<String> returnAuthorisationKey,
      Input<SettingValueTypeInput> returnAuthorisationDisposition,
      Input<Object> returnAuthorisationKeyExpiry, Input<StreetAddressInput> pickupAddress,
      Input<LocationLinkInput> lodgedLocation, Input<LocationLinkInput> destinationLocation,
      @Nonnull List<CreateReturnOrderItemWithReturnOrderInput> returnOrderItems,
      Input<OrderLinkInput> exchangeOrder, Input<CreditMemoKey> creditMemo,
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
  }

  /**
   *  External reference of the return order. Must be unique. <br/>
   *  Max character limit: 100.
   */
  public @Nonnull String ref() {
    return this.ref;
  }

  /**
   *  Retailer reference of return orders
   */
  public @Nonnull RetailerId retailer() {
    return this.retailer;
  }

  /**
   *  Type of the return order. <br/>
   *  Max character limit: 50.
   */
  public @Nonnull String type() {
    return this.type;
  }

  /**
   *  A list of attributes of the return order. Attributes can be used to extend the existing data structure with additional data for use in orchestration rules, etc.
   */
  public @Nullable List<AttributeInput> attributes() {
    return this.attributes.value;
  }

  /**
   *  Customer reference of the return order. This links the customer to return order
   */
  public @Nonnull CustomerKey customer() {
    return this.customer;
  }

  /**
   *  Linked order for this return order
   */
  public @Nullable OrderLinkInput order() {
    return this.order.value;
  }

  /**
   *  List of return verifications associated with the return order
   */
  public @Nullable List<CreateReturnVerificationWithReturnOrderInput> returnVerifications() {
    return this.returnVerifications.value;
  }

  /**
   *  The generated key representing an authorised return order which the customer can use to progress through the return order proces
   */
  public @Nullable String returnAuthorisationKey() {
    return this.returnAuthorisationKey.value;
  }

  /**
   *  The authorised disposition for this return order. This can be different to the return disposition action which reflects the actual action once an item has been inspected
   */
  public @Nullable SettingValueTypeInput returnAuthorisationDisposition() {
    return this.returnAuthorisationDisposition.value;
  }

  /**
   *  The time at which the return authorisation expires
   */
  public @Nullable Object returnAuthorisationKeyExpiry() {
    return this.returnAuthorisationKeyExpiry.value;
  }

  /**
   *  The pickup address in the cases of return orders that are being picked up by a carrier
   */
  public @Nullable StreetAddressInput pickupAddress() {
    return this.pickupAddress.value;
  }

  /**
   *  The lodged location in cases where the return order was directly returned to a store or DC
   */
  public @Nullable LocationLinkInput lodgedLocation() {
    return this.lodgedLocation.value;
  }

  /**
   *  The destination of the return order items
   */
  public @Nullable LocationLinkInput destinationLocation() {
    return this.destinationLocation.value;
  }

  /**
   *  The list of associated return order items
   */
  public @Nonnull List<CreateReturnOrderItemWithReturnOrderInput> returnOrderItems() {
    return this.returnOrderItems;
  }

  /**
   *  The associated exchange order managed the exchange item
   */
  public @Nullable OrderLinkInput exchangeOrder() {
    return this.exchangeOrder.value;
  }

  /**
   *  The associated credit memo for this return order
   */
  public @Nullable CreditMemoKey creditMemo() {
    return this.creditMemo.value;
  }

  /**
   *  The currency of this return
   */
  public @Nonnull CurrencyKey currency() {
    return this.currency;
  }

  /**
   *  The default Tax Type for this return order. Individual return order items can override
   */
  public @Nonnull TaxTypeInput defaultTaxType() {
    return this.defaultTaxType;
  }

  /**
   *  The total amount of this return order excluding tax
   */
  public @Nonnull AmountTypeInput subTotalAmount() {
    return this.subTotalAmount;
  }

  /**
   *  The total amount of tax for this return order
   */
  public @Nonnull AmountTypeInput totalTax() {
    return this.totalTax;
  }

  /**
   *  The total amount of this return order including tax
   */
  public @Nonnull AmountTypeInput totalAmount() {
    return this.totalAmount;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("ref", ref);
        writer.writeObject("retailer", retailer.marshaller());
        writer.writeString("type", type);
        if (attributes.defined) {
          writer.writeList("attributes", attributes.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (AttributeInput $item : attributes.value) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          } : null);
        }
        writer.writeObject("customer", customer.marshaller());
        if (order.defined) {
          writer.writeObject("order", order.value != null ? order.value.marshaller() : null);
        }
        if (returnVerifications.defined) {
          writer.writeList("returnVerifications", returnVerifications.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (CreateReturnVerificationWithReturnOrderInput $item : returnVerifications.value) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          } : null);
        }
        if (returnAuthorisationKey.defined) {
          writer.writeString("returnAuthorisationKey", returnAuthorisationKey.value);
        }
        if (returnAuthorisationDisposition.defined) {
          writer.writeObject("returnAuthorisationDisposition", returnAuthorisationDisposition.value != null ? returnAuthorisationDisposition.value.marshaller() : null);
        }
        if (returnAuthorisationKeyExpiry.defined) {
          writer.writeCustom("returnAuthorisationKeyExpiry", com.fluentretail.graphql.type.CustomType.DATETIME, returnAuthorisationKeyExpiry.value != null ? returnAuthorisationKeyExpiry.value : null);
        }
        if (pickupAddress.defined) {
          writer.writeObject("pickupAddress", pickupAddress.value != null ? pickupAddress.value.marshaller() : null);
        }
        if (lodgedLocation.defined) {
          writer.writeObject("lodgedLocation", lodgedLocation.value != null ? lodgedLocation.value.marshaller() : null);
        }
        if (destinationLocation.defined) {
          writer.writeObject("destinationLocation", destinationLocation.value != null ? destinationLocation.value.marshaller() : null);
        }
        writer.writeList("returnOrderItems", new InputFieldWriter.ListWriter() {
          @Override
          public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
            for (CreateReturnOrderItemWithReturnOrderInput $item : returnOrderItems) {
              listItemWriter.writeObject($item.marshaller());
            }
          }
        });
        if (exchangeOrder.defined) {
          writer.writeObject("exchangeOrder", exchangeOrder.value != null ? exchangeOrder.value.marshaller() : null);
        }
        if (creditMemo.defined) {
          writer.writeObject("creditMemo", creditMemo.value != null ? creditMemo.value.marshaller() : null);
        }
        writer.writeObject("currency", currency.marshaller());
        writer.writeObject("defaultTaxType", defaultTaxType.marshaller());
        writer.writeObject("subTotalAmount", subTotalAmount.marshaller());
        writer.writeObject("totalTax", totalTax.marshaller());
        writer.writeObject("totalAmount", totalAmount.marshaller());
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull RetailerId retailer;

    private @Nonnull String type;

    private Input<List<AttributeInput>> attributes = Input.absent();

    private @Nonnull CustomerKey customer;

    private Input<OrderLinkInput> order = Input.absent();

    private Input<List<CreateReturnVerificationWithReturnOrderInput>> returnVerifications = Input.absent();

    private Input<String> returnAuthorisationKey = Input.absent();

    private Input<SettingValueTypeInput> returnAuthorisationDisposition = Input.absent();

    private Input<Object> returnAuthorisationKeyExpiry = Input.absent();

    private Input<StreetAddressInput> pickupAddress = Input.absent();

    private Input<LocationLinkInput> lodgedLocation = Input.absent();

    private Input<LocationLinkInput> destinationLocation = Input.absent();

    private @Nonnull List<CreateReturnOrderItemWithReturnOrderInput> returnOrderItems;

    private Input<OrderLinkInput> exchangeOrder = Input.absent();

    private Input<CreditMemoKey> creditMemo = Input.absent();

    private @Nonnull CurrencyKey currency;

    private @Nonnull TaxTypeInput defaultTaxType;

    private @Nonnull AmountTypeInput subTotalAmount;

    private @Nonnull AmountTypeInput totalTax;

    private @Nonnull AmountTypeInput totalAmount;

    Builder() {
    }

    /**
     *  External reference of the return order. Must be unique. <br/>
     *  Max character limit: 100.
     */
    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    /**
     *  Retailer reference of return orders
     */
    public Builder retailer(@Nonnull RetailerId retailer) {
      this.retailer = retailer;
      return this;
    }

    /**
     *  Type of the return order. <br/>
     *  Max character limit: 50.
     */
    public Builder type(@Nonnull String type) {
      this.type = type;
      return this;
    }

    /**
     *  A list of attributes of the return order. Attributes can be used to extend the existing data structure with additional data for use in orchestration rules, etc.
     */
    public Builder attributes(@Nullable List<AttributeInput> attributes) {
      this.attributes = Input.fromNullable(attributes);
      return this;
    }

    /**
     *  Customer reference of the return order. This links the customer to return order
     */
    public Builder customer(@Nonnull CustomerKey customer) {
      this.customer = customer;
      return this;
    }

    /**
     *  Linked order for this return order
     */
    public Builder order(@Nullable OrderLinkInput order) {
      this.order = Input.fromNullable(order);
      return this;
    }

    /**
     *  List of return verifications associated with the return order
     */
    public Builder returnVerifications(@Nullable List<CreateReturnVerificationWithReturnOrderInput> returnVerifications) {
      this.returnVerifications = Input.fromNullable(returnVerifications);
      return this;
    }

    /**
     *  The generated key representing an authorised return order which the customer can use to progress through the return order proces
     */
    public Builder returnAuthorisationKey(@Nullable String returnAuthorisationKey) {
      this.returnAuthorisationKey = Input.fromNullable(returnAuthorisationKey);
      return this;
    }

    /**
     *  The authorised disposition for this return order. This can be different to the return disposition action which reflects the actual action once an item has been inspected
     */
    public Builder returnAuthorisationDisposition(@Nullable SettingValueTypeInput returnAuthorisationDisposition) {
      this.returnAuthorisationDisposition = Input.fromNullable(returnAuthorisationDisposition);
      return this;
    }

    /**
     *  The time at which the return authorisation expires
     */
    public Builder returnAuthorisationKeyExpiry(@Nullable Object returnAuthorisationKeyExpiry) {
      this.returnAuthorisationKeyExpiry = Input.fromNullable(returnAuthorisationKeyExpiry);
      return this;
    }

    /**
     *  The pickup address in the cases of return orders that are being picked up by a carrier
     */
    public Builder pickupAddress(@Nullable StreetAddressInput pickupAddress) {
      this.pickupAddress = Input.fromNullable(pickupAddress);
      return this;
    }

    /**
     *  The lodged location in cases where the return order was directly returned to a store or DC
     */
    public Builder lodgedLocation(@Nullable LocationLinkInput lodgedLocation) {
      this.lodgedLocation = Input.fromNullable(lodgedLocation);
      return this;
    }

    /**
     *  The destination of the return order items
     */
    public Builder destinationLocation(@Nullable LocationLinkInput destinationLocation) {
      this.destinationLocation = Input.fromNullable(destinationLocation);
      return this;
    }

    /**
     *  The list of associated return order items
     */
    public Builder returnOrderItems(@Nonnull List<CreateReturnOrderItemWithReturnOrderInput> returnOrderItems) {
      this.returnOrderItems = returnOrderItems;
      return this;
    }

    /**
     *  The associated exchange order managed the exchange item
     */
    public Builder exchangeOrder(@Nullable OrderLinkInput exchangeOrder) {
      this.exchangeOrder = Input.fromNullable(exchangeOrder);
      return this;
    }

    /**
     *  The associated credit memo for this return order
     */
    public Builder creditMemo(@Nullable CreditMemoKey creditMemo) {
      this.creditMemo = Input.fromNullable(creditMemo);
      return this;
    }

    /**
     *  The currency of this return
     */
    public Builder currency(@Nonnull CurrencyKey currency) {
      this.currency = currency;
      return this;
    }

    /**
     *  The default Tax Type for this return order. Individual return order items can override
     */
    public Builder defaultTaxType(@Nonnull TaxTypeInput defaultTaxType) {
      this.defaultTaxType = defaultTaxType;
      return this;
    }

    /**
     *  The total amount of this return order excluding tax
     */
    public Builder subTotalAmount(@Nonnull AmountTypeInput subTotalAmount) {
      this.subTotalAmount = subTotalAmount;
      return this;
    }

    /**
     *  The total amount of tax for this return order
     */
    public Builder totalTax(@Nonnull AmountTypeInput totalTax) {
      this.totalTax = totalTax;
      return this;
    }

    /**
     *  The total amount of this return order including tax
     */
    public Builder totalAmount(@Nonnull AmountTypeInput totalAmount) {
      this.totalAmount = totalAmount;
      return this;
    }

    public CreateReturnOrderInput build() {
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
      return new CreateReturnOrderInput(ref, retailer, type, attributes, customer, order, returnVerifications, returnAuthorisationKey, returnAuthorisationDisposition, returnAuthorisationKeyExpiry, pickupAddress, lodgedLocation, destinationLocation, returnOrderItems, exchangeOrder, creditMemo, currency, defaultTaxType, subTotalAmount, totalTax, totalAmount);
    }
  }
}
