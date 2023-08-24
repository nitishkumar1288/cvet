package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class CreateCreditMemoItemWithCreditMemoInput {
  private final @Nonnull String ref;

  private final @Nonnull String type;

  private final Input<String> description;

  private final Input<SettingValueTypeInput> creditReasonCode;

  private final Input<OrderItemLinkInput> orderItem;

  private final Input<ReturnOrderItemKey> returnOrderItem;

  private final Input<ProductKey> product;

  private final @Nonnull QuantityTypeInput unitQuantity;

  private final Input<TaxTypeInput> unitTaxType;

  private final @Nonnull AmountTypeInput unitAmount;

  private final Input<AmountTypeInput> unitCostAmount;

  private final @Nonnull AmountTypeInput amount;

  private final @Nonnull AmountTypeInput taxAmount;

  CreateCreditMemoItemWithCreditMemoInput(@Nonnull String ref, @Nonnull String type,
      Input<String> description, Input<SettingValueTypeInput> creditReasonCode,
      Input<OrderItemLinkInput> orderItem, Input<ReturnOrderItemKey> returnOrderItem,
      Input<ProductKey> product, @Nonnull QuantityTypeInput unitQuantity,
      Input<TaxTypeInput> unitTaxType, @Nonnull AmountTypeInput unitAmount,
      Input<AmountTypeInput> unitCostAmount, @Nonnull AmountTypeInput amount,
      @Nonnull AmountTypeInput taxAmount) {
    this.ref = ref;
    this.type = type;
    this.description = description;
    this.creditReasonCode = creditReasonCode;
    this.orderItem = orderItem;
    this.returnOrderItem = returnOrderItem;
    this.product = product;
    this.unitQuantity = unitQuantity;
    this.unitTaxType = unitTaxType;
    this.unitAmount = unitAmount;
    this.unitCostAmount = unitCostAmount;
    this.amount = amount;
    this.taxAmount = taxAmount;
  }

  /**
   *  External reference to the `CreditMemoItem`. Must be unique. <br/>
   *  Max character limit: 100.
   */
  public @Nonnull String ref() {
    return this.ref;
  }

  /**
   *  Type of the `CreditMemoItem`, typically used by the Orchestration Engine to determine the workflow that should be applied. <br/>
   *  Max character limit: 50.
   */
  public @Nonnull String type() {
    return this.type;
  }

  /**
   *  Description of the `CreditMemoItem`.
   */
  public @Nullable String description() {
    return this.description.value;
  }

  /**
   *  Credit reason code of the `CreditMemoItem`.
   */
  public @Nullable SettingValueTypeInput creditReasonCode() {
    return this.creditReasonCode.value;
  }

  /**
   *  Reference to an `OrderItem` associated with the `CreditMemoItem`.
   */
  public @Nullable OrderItemLinkInput orderItem() {
    return this.orderItem.value;
  }

  /**
   *  Reference to a `ReturnOrderItem` associated with the `CreditMemoItem`.
   */
  public @Nullable ReturnOrderItemKey returnOrderItem() {
    return this.returnOrderItem.value;
  }

  /**
   *  Reference to a `Product` associated with the `CreditMemoItem`.
   */
  public @Nullable ProductKey product() {
    return this.product.value;
  }

  /**
   *  `unitQuantity` holds separately the amount and the unit associated.
   */
  public @Nonnull QuantityTypeInput unitQuantity() {
    return this.unitQuantity;
  }

  /**
   *  The tax type of this item. Should only be provided if different to the default credit memo tax type.
   */
  public @Nullable TaxTypeInput unitTaxType() {
    return this.unitTaxType.value;
  }

  /**
   *  The unit sale price at time of sale or exchange
   */
  public @Nonnull AmountTypeInput unitAmount() {
    return this.unitAmount;
  }

  /**
   *  The unit cost price at time of sale or exchange.
   */
  public @Nullable AmountTypeInput unitCostAmount() {
    return this.unitCostAmount.value;
  }

  /**
   *  The item amount for this item excluding tax. This is a calculated value based on business rules that does not necessarily have to take into account the unit quantity or amounts.
   */
  public @Nonnull AmountTypeInput amount() {
    return this.amount;
  }

  /**
   *  The tax amount for this item. If not present at the item level, tax amount should be generated based on the tax type set at the invoice parent level.
   */
  public @Nonnull AmountTypeInput taxAmount() {
    return this.taxAmount;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("ref", ref);
        writer.writeString("type", type);
        if (description.defined) {
          writer.writeString("description", description.value);
        }
        if (creditReasonCode.defined) {
          writer.writeObject("creditReasonCode", creditReasonCode.value != null ? creditReasonCode.value.marshaller() : null);
        }
        if (orderItem.defined) {
          writer.writeObject("orderItem", orderItem.value != null ? orderItem.value.marshaller() : null);
        }
        if (returnOrderItem.defined) {
          writer.writeObject("returnOrderItem", returnOrderItem.value != null ? returnOrderItem.value.marshaller() : null);
        }
        if (product.defined) {
          writer.writeObject("product", product.value != null ? product.value.marshaller() : null);
        }
        writer.writeObject("unitQuantity", unitQuantity.marshaller());
        if (unitTaxType.defined) {
          writer.writeObject("unitTaxType", unitTaxType.value != null ? unitTaxType.value.marshaller() : null);
        }
        writer.writeObject("unitAmount", unitAmount.marshaller());
        if (unitCostAmount.defined) {
          writer.writeObject("unitCostAmount", unitCostAmount.value != null ? unitCostAmount.value.marshaller() : null);
        }
        writer.writeObject("amount", amount.marshaller());
        writer.writeObject("taxAmount", taxAmount.marshaller());
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull String type;

    private Input<String> description = Input.absent();

    private Input<SettingValueTypeInput> creditReasonCode = Input.absent();

    private Input<OrderItemLinkInput> orderItem = Input.absent();

    private Input<ReturnOrderItemKey> returnOrderItem = Input.absent();

    private Input<ProductKey> product = Input.absent();

    private @Nonnull QuantityTypeInput unitQuantity;

    private Input<TaxTypeInput> unitTaxType = Input.absent();

    private @Nonnull AmountTypeInput unitAmount;

    private Input<AmountTypeInput> unitCostAmount = Input.absent();

    private @Nonnull AmountTypeInput amount;

    private @Nonnull AmountTypeInput taxAmount;

    Builder() {
    }

    /**
     *  External reference to the `CreditMemoItem`. Must be unique. <br/>
     *  Max character limit: 100.
     */
    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    /**
     *  Type of the `CreditMemoItem`, typically used by the Orchestration Engine to determine the workflow that should be applied. <br/>
     *  Max character limit: 50.
     */
    public Builder type(@Nonnull String type) {
      this.type = type;
      return this;
    }

    /**
     *  Description of the `CreditMemoItem`.
     */
    public Builder description(@Nullable String description) {
      this.description = Input.fromNullable(description);
      return this;
    }

    /**
     *  Credit reason code of the `CreditMemoItem`.
     */
    public Builder creditReasonCode(@Nullable SettingValueTypeInput creditReasonCode) {
      this.creditReasonCode = Input.fromNullable(creditReasonCode);
      return this;
    }

    /**
     *  Reference to an `OrderItem` associated with the `CreditMemoItem`.
     */
    public Builder orderItem(@Nullable OrderItemLinkInput orderItem) {
      this.orderItem = Input.fromNullable(orderItem);
      return this;
    }

    /**
     *  Reference to a `ReturnOrderItem` associated with the `CreditMemoItem`.
     */
    public Builder returnOrderItem(@Nullable ReturnOrderItemKey returnOrderItem) {
      this.returnOrderItem = Input.fromNullable(returnOrderItem);
      return this;
    }

    /**
     *  Reference to a `Product` associated with the `CreditMemoItem`.
     */
    public Builder product(@Nullable ProductKey product) {
      this.product = Input.fromNullable(product);
      return this;
    }

    /**
     *  `unitQuantity` holds separately the amount and the unit associated.
     */
    public Builder unitQuantity(@Nonnull QuantityTypeInput unitQuantity) {
      this.unitQuantity = unitQuantity;
      return this;
    }

    /**
     *  The tax type of this item. Should only be provided if different to the default credit memo tax type.
     */
    public Builder unitTaxType(@Nullable TaxTypeInput unitTaxType) {
      this.unitTaxType = Input.fromNullable(unitTaxType);
      return this;
    }

    /**
     *  The unit sale price at time of sale or exchange
     */
    public Builder unitAmount(@Nonnull AmountTypeInput unitAmount) {
      this.unitAmount = unitAmount;
      return this;
    }

    /**
     *  The unit cost price at time of sale or exchange.
     */
    public Builder unitCostAmount(@Nullable AmountTypeInput unitCostAmount) {
      this.unitCostAmount = Input.fromNullable(unitCostAmount);
      return this;
    }

    /**
     *  The item amount for this item excluding tax. This is a calculated value based on business rules that does not necessarily have to take into account the unit quantity or amounts.
     */
    public Builder amount(@Nonnull AmountTypeInput amount) {
      this.amount = amount;
      return this;
    }

    /**
     *  The tax amount for this item. If not present at the item level, tax amount should be generated based on the tax type set at the invoice parent level.
     */
    public Builder taxAmount(@Nonnull AmountTypeInput taxAmount) {
      this.taxAmount = taxAmount;
      return this;
    }

    public CreateCreditMemoItemWithCreditMemoInput build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(type, "type == null");
      Utils.checkNotNull(unitQuantity, "unitQuantity == null");
      Utils.checkNotNull(unitAmount, "unitAmount == null");
      Utils.checkNotNull(amount, "amount == null");
      Utils.checkNotNull(taxAmount, "taxAmount == null");
      return new CreateCreditMemoItemWithCreditMemoInput(ref, type, description, creditReasonCode, orderItem, returnOrderItem, product, unitQuantity, unitTaxType, unitAmount, unitCostAmount, amount, taxAmount);
    }
  }
}
