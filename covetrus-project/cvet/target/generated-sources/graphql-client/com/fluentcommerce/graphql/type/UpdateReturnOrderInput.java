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
public final class UpdateReturnOrderInput {
  private final @Nonnull String ref;

  private final @Nonnull RetailerId retailer;

  private final Input<String> status;

  private final Input<List<AttributeInput>> attributes;

  private final Input<OrderLinkInput> order;

  private final Input<List<UpdateReturnVerificationWithReturnOrderInput>> returnVerifications;

  private final Input<String> returnAuthorisationKey;

  private final Input<SettingValueTypeInput> returnAuthorisationDisposition;

  private final Input<Object> returnAuthorisationKeyExpiry;

  private final Input<StreetAddressInput> pickupAddress;

  private final Input<LocationLinkInput> lodgedLocation;

  private final Input<LocationLinkInput> destinationLocation;

  private final Input<List<UpdateReturnOrderItemWithReturnOrderInput>> returnOrderItems;

  private final Input<OrderLinkInput> exchangeOrder;

  private final Input<CreditMemoKey> creditMemo;

  private final Input<CurrencyKey> currency;

  private final Input<TaxTypeInput> defaultTaxType;

  private final Input<AmountTypeInput> subTotalAmount;

  private final Input<AmountTypeInput> totalTax;

  private final Input<AmountTypeInput> totalAmount;

  UpdateReturnOrderInput(@Nonnull String ref, @Nonnull RetailerId retailer, Input<String> status,
      Input<List<AttributeInput>> attributes, Input<OrderLinkInput> order,
      Input<List<UpdateReturnVerificationWithReturnOrderInput>> returnVerifications,
      Input<String> returnAuthorisationKey,
      Input<SettingValueTypeInput> returnAuthorisationDisposition,
      Input<Object> returnAuthorisationKeyExpiry, Input<StreetAddressInput> pickupAddress,
      Input<LocationLinkInput> lodgedLocation, Input<LocationLinkInput> destinationLocation,
      Input<List<UpdateReturnOrderItemWithReturnOrderInput>> returnOrderItems,
      Input<OrderLinkInput> exchangeOrder, Input<CreditMemoKey> creditMemo,
      Input<CurrencyKey> currency, Input<TaxTypeInput> defaultTaxType,
      Input<AmountTypeInput> subTotalAmount, Input<AmountTypeInput> totalTax,
      Input<AmountTypeInput> totalAmount) {
    this.ref = ref;
    this.retailer = retailer;
    this.status = status;
    this.attributes = attributes;
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
   *  Status of the return order. <br/>
   *  Max character limit: 50.
   */
  public @Nullable String status() {
    return this.status.value;
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
  public @Nullable OrderLinkInput order() {
    return this.order.value;
  }

  /**
   *  List of return verifications associated with the return order
   */
  public @Nullable List<UpdateReturnVerificationWithReturnOrderInput> returnVerifications() {
    return this.returnVerifications.value;
  }

  /**
   *  The generated key representing an authorised return order which the customer can use to progress through the return order process
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
   *  The destination of the return order items
   */
  public @Nullable List<UpdateReturnOrderItemWithReturnOrderInput> returnOrderItems() {
    return this.returnOrderItems.value;
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
  public @Nullable CurrencyKey currency() {
    return this.currency.value;
  }

  /**
   *  The default Tax Type for this return order. Individual return order items can override
   */
  public @Nullable TaxTypeInput defaultTaxType() {
    return this.defaultTaxType.value;
  }

  /**
   *  The total amount of this return order excluding tax
   */
  public @Nullable AmountTypeInput subTotalAmount() {
    return this.subTotalAmount.value;
  }

  /**
   *  The total amount of tax for this return order
   */
  public @Nullable AmountTypeInput totalTax() {
    return this.totalTax.value;
  }

  /**
   *  The total amount of this return order including tax
   */
  public @Nullable AmountTypeInput totalAmount() {
    return this.totalAmount.value;
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
        if (status.defined) {
          writer.writeString("status", status.value);
        }
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
        if (order.defined) {
          writer.writeObject("order", order.value != null ? order.value.marshaller() : null);
        }
        if (returnVerifications.defined) {
          writer.writeList("returnVerifications", returnVerifications.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (UpdateReturnVerificationWithReturnOrderInput $item : returnVerifications.value) {
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
        if (returnOrderItems.defined) {
          writer.writeList("returnOrderItems", returnOrderItems.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (UpdateReturnOrderItemWithReturnOrderInput $item : returnOrderItems.value) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          } : null);
        }
        if (exchangeOrder.defined) {
          writer.writeObject("exchangeOrder", exchangeOrder.value != null ? exchangeOrder.value.marshaller() : null);
        }
        if (creditMemo.defined) {
          writer.writeObject("creditMemo", creditMemo.value != null ? creditMemo.value.marshaller() : null);
        }
        if (currency.defined) {
          writer.writeObject("currency", currency.value != null ? currency.value.marshaller() : null);
        }
        if (defaultTaxType.defined) {
          writer.writeObject("defaultTaxType", defaultTaxType.value != null ? defaultTaxType.value.marshaller() : null);
        }
        if (subTotalAmount.defined) {
          writer.writeObject("subTotalAmount", subTotalAmount.value != null ? subTotalAmount.value.marshaller() : null);
        }
        if (totalTax.defined) {
          writer.writeObject("totalTax", totalTax.value != null ? totalTax.value.marshaller() : null);
        }
        if (totalAmount.defined) {
          writer.writeObject("totalAmount", totalAmount.value != null ? totalAmount.value.marshaller() : null);
        }
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull RetailerId retailer;

    private Input<String> status = Input.absent();

    private Input<List<AttributeInput>> attributes = Input.absent();

    private Input<OrderLinkInput> order = Input.absent();

    private Input<List<UpdateReturnVerificationWithReturnOrderInput>> returnVerifications = Input.absent();

    private Input<String> returnAuthorisationKey = Input.absent();

    private Input<SettingValueTypeInput> returnAuthorisationDisposition = Input.absent();

    private Input<Object> returnAuthorisationKeyExpiry = Input.absent();

    private Input<StreetAddressInput> pickupAddress = Input.absent();

    private Input<LocationLinkInput> lodgedLocation = Input.absent();

    private Input<LocationLinkInput> destinationLocation = Input.absent();

    private Input<List<UpdateReturnOrderItemWithReturnOrderInput>> returnOrderItems = Input.absent();

    private Input<OrderLinkInput> exchangeOrder = Input.absent();

    private Input<CreditMemoKey> creditMemo = Input.absent();

    private Input<CurrencyKey> currency = Input.absent();

    private Input<TaxTypeInput> defaultTaxType = Input.absent();

    private Input<AmountTypeInput> subTotalAmount = Input.absent();

    private Input<AmountTypeInput> totalTax = Input.absent();

    private Input<AmountTypeInput> totalAmount = Input.absent();

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
     *  Status of the return order. <br/>
     *  Max character limit: 50.
     */
    public Builder status(@Nullable String status) {
      this.status = Input.fromNullable(status);
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
    public Builder order(@Nullable OrderLinkInput order) {
      this.order = Input.fromNullable(order);
      return this;
    }

    /**
     *  List of return verifications associated with the return order
     */
    public Builder returnVerifications(@Nullable List<UpdateReturnVerificationWithReturnOrderInput> returnVerifications) {
      this.returnVerifications = Input.fromNullable(returnVerifications);
      return this;
    }

    /**
     *  The generated key representing an authorised return order which the customer can use to progress through the return order process
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
     *  The destination of the return order items
     */
    public Builder returnOrderItems(@Nullable List<UpdateReturnOrderItemWithReturnOrderInput> returnOrderItems) {
      this.returnOrderItems = Input.fromNullable(returnOrderItems);
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
    public Builder currency(@Nullable CurrencyKey currency) {
      this.currency = Input.fromNullable(currency);
      return this;
    }

    /**
     *  The default Tax Type for this return order. Individual return order items can override
     */
    public Builder defaultTaxType(@Nullable TaxTypeInput defaultTaxType) {
      this.defaultTaxType = Input.fromNullable(defaultTaxType);
      return this;
    }

    /**
     *  The total amount of this return order excluding tax
     */
    public Builder subTotalAmount(@Nullable AmountTypeInput subTotalAmount) {
      this.subTotalAmount = Input.fromNullable(subTotalAmount);
      return this;
    }

    /**
     *  The total amount of tax for this return order
     */
    public Builder totalTax(@Nullable AmountTypeInput totalTax) {
      this.totalTax = Input.fromNullable(totalTax);
      return this;
    }

    /**
     *  The total amount of this return order including tax
     */
    public Builder totalAmount(@Nullable AmountTypeInput totalAmount) {
      this.totalAmount = Input.fromNullable(totalAmount);
      return this;
    }

    public UpdateReturnOrderInput build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(retailer, "retailer == null");
      return new UpdateReturnOrderInput(ref, retailer, status, attributes, order, returnVerifications, returnAuthorisationKey, returnAuthorisationDisposition, returnAuthorisationKeyExpiry, pickupAddress, lodgedLocation, destinationLocation, returnOrderItems, exchangeOrder, creditMemo, currency, defaultTaxType, subTotalAmount, totalTax, totalAmount);
    }
  }
}
