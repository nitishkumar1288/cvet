package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class UpdateCreditMemoItemWithCreditMemoInput {
  private final Input<String> ref;

  private final Input<String> description;

  private final Input<SettingValueTypeInput> creditReasonCode;

  private final Input<OrderItemLinkInput> orderItem;

  private final Input<ReturnOrderItemKey> returnOrderItem;

  private final Input<ProductKey> product;

  private final Input<QuantityTypeInput> unitQuantity;

  private final Input<TaxTypeInput> unitTaxType;

  private final Input<AmountTypeInput> unitAmount;

  private final Input<AmountTypeInput> unitCostAmount;

  private final Input<AmountTypeInput> amount;

  private final Input<AmountTypeInput> taxAmount;

  UpdateCreditMemoItemWithCreditMemoInput(Input<String> ref, Input<String> description,
      Input<SettingValueTypeInput> creditReasonCode, Input<OrderItemLinkInput> orderItem,
      Input<ReturnOrderItemKey> returnOrderItem, Input<ProductKey> product,
      Input<QuantityTypeInput> unitQuantity, Input<TaxTypeInput> unitTaxType,
      Input<AmountTypeInput> unitAmount, Input<AmountTypeInput> unitCostAmount,
      Input<AmountTypeInput> amount, Input<AmountTypeInput> taxAmount) {
    this.ref = ref;
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
  public @Nullable String ref() {
    return this.ref.value;
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
  public @Nullable QuantityTypeInput unitQuantity() {
    return this.unitQuantity.value;
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
  public @Nullable AmountTypeInput unitAmount() {
    return this.unitAmount.value;
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
  public @Nullable AmountTypeInput amount() {
    return this.amount.value;
  }

  /**
   *  The tax amount for this item. If not present at the item level, tax amount should be generated based on the tax type set at the invoice parent level.
   */
  public @Nullable AmountTypeInput taxAmount() {
    return this.taxAmount.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        if (ref.defined) {
          writer.writeString("ref", ref.value);
        }
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
        if (unitQuantity.defined) {
          writer.writeObject("unitQuantity", unitQuantity.value != null ? unitQuantity.value.marshaller() : null);
        }
        if (unitTaxType.defined) {
          writer.writeObject("unitTaxType", unitTaxType.value != null ? unitTaxType.value.marshaller() : null);
        }
        if (unitAmount.defined) {
          writer.writeObject("unitAmount", unitAmount.value != null ? unitAmount.value.marshaller() : null);
        }
        if (unitCostAmount.defined) {
          writer.writeObject("unitCostAmount", unitCostAmount.value != null ? unitCostAmount.value.marshaller() : null);
        }
        if (amount.defined) {
          writer.writeObject("amount", amount.value != null ? amount.value.marshaller() : null);
        }
        if (taxAmount.defined) {
          writer.writeObject("taxAmount", taxAmount.value != null ? taxAmount.value.marshaller() : null);
        }
      }
    };
  }

  public static final class Builder {
    private Input<String> ref = Input.absent();

    private Input<String> description = Input.absent();

    private Input<SettingValueTypeInput> creditReasonCode = Input.absent();

    private Input<OrderItemLinkInput> orderItem = Input.absent();

    private Input<ReturnOrderItemKey> returnOrderItem = Input.absent();

    private Input<ProductKey> product = Input.absent();

    private Input<QuantityTypeInput> unitQuantity = Input.absent();

    private Input<TaxTypeInput> unitTaxType = Input.absent();

    private Input<AmountTypeInput> unitAmount = Input.absent();

    private Input<AmountTypeInput> unitCostAmount = Input.absent();

    private Input<AmountTypeInput> amount = Input.absent();

    private Input<AmountTypeInput> taxAmount = Input.absent();

    Builder() {
    }

    /**
     *  External reference to the `CreditMemoItem`. Must be unique. <br/>
     *  Max character limit: 100.
     */
    public Builder ref(@Nullable String ref) {
      this.ref = Input.fromNullable(ref);
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
    public Builder unitQuantity(@Nullable QuantityTypeInput unitQuantity) {
      this.unitQuantity = Input.fromNullable(unitQuantity);
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
    public Builder unitAmount(@Nullable AmountTypeInput unitAmount) {
      this.unitAmount = Input.fromNullable(unitAmount);
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
    public Builder amount(@Nullable AmountTypeInput amount) {
      this.amount = Input.fromNullable(amount);
      return this;
    }

    /**
     *  The tax amount for this item. If not present at the item level, tax amount should be generated based on the tax type set at the invoice parent level.
     */
    public Builder taxAmount(@Nullable AmountTypeInput taxAmount) {
      this.taxAmount = Input.fromNullable(taxAmount);
      return this;
    }

    public UpdateCreditMemoItemWithCreditMemoInput build() {
      return new UpdateCreditMemoItemWithCreditMemoInput(ref, description, creditReasonCode, orderItem, returnOrderItem, product, unitQuantity, unitTaxType, unitAmount, unitCostAmount, amount, taxAmount);
    }
  }
}
