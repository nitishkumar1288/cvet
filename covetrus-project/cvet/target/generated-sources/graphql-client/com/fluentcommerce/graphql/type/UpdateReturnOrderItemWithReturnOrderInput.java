package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class UpdateReturnOrderItemWithReturnOrderInput {
  private final @Nonnull String ref;

  private final Input<String> status;

  private final Input<List<AttributeInput>> attributes;

  private final Input<ProductKey> product;

  private final Input<OrderItemLinkInput> orderItem;

  private final Input<SettingValueTypeInput> returnReason;

  private final Input<String> returnReasonComment;

  private final Input<SettingValueTypeInput> returnCondition;

  private final Input<String> returnConditionComment;

  private final Input<SettingValueTypeInput> returnPaymentAction;

  private final Input<SettingValueTypeInput> returnDispositionAction;

  private final Input<QuantityTypeInput> unitQuantity;

  private final Input<AmountTypeInput> unitAmount;

  private final Input<TaxTypeInput> unitTaxType;

  private final Input<AmountTypeInput> itemTaxAmount;

  private final Input<AmountTypeInput> itemAmount;

  UpdateReturnOrderItemWithReturnOrderInput(@Nonnull String ref, Input<String> status,
      Input<List<AttributeInput>> attributes, Input<ProductKey> product,
      Input<OrderItemLinkInput> orderItem, Input<SettingValueTypeInput> returnReason,
      Input<String> returnReasonComment, Input<SettingValueTypeInput> returnCondition,
      Input<String> returnConditionComment, Input<SettingValueTypeInput> returnPaymentAction,
      Input<SettingValueTypeInput> returnDispositionAction, Input<QuantityTypeInput> unitQuantity,
      Input<AmountTypeInput> unitAmount, Input<TaxTypeInput> unitTaxType,
      Input<AmountTypeInput> itemTaxAmount, Input<AmountTypeInput> itemAmount) {
    this.ref = ref;
    this.status = status;
    this.attributes = attributes;
    this.product = product;
    this.orderItem = orderItem;
    this.returnReason = returnReason;
    this.returnReasonComment = returnReasonComment;
    this.returnCondition = returnCondition;
    this.returnConditionComment = returnConditionComment;
    this.returnPaymentAction = returnPaymentAction;
    this.returnDispositionAction = returnDispositionAction;
    this.unitQuantity = unitQuantity;
    this.unitAmount = unitAmount;
    this.unitTaxType = unitTaxType;
    this.itemTaxAmount = itemTaxAmount;
    this.itemAmount = itemAmount;
  }

  /**
   *  External reference. Must be unique. <br/>
   *  Max character limit: 100.
   */
  public @Nonnull String ref() {
    return this.ref;
  }

  /**
   *  Status or the return order item. <br/>
   *  Max character limit: 50.
   */
  public @Nullable String status() {
    return this.status.value;
  }

  /**
   *  A list of attributes. Attributes can be used to extend the existing data structure with additional data for use in orchestration rules, etc.
   */
  public @Nullable List<AttributeInput> attributes() {
    return this.attributes.value;
  }

  /**
   *  Product reference of return order item
   */
  public @Nullable ProductKey product() {
    return this.product.value;
  }

  /**
   *  Order item of return order item. This field is optional
   */
  public @Nullable OrderItemLinkInput orderItem() {
    return this.orderItem.value;
  }

  /**
   *  Reason for returning the item
   */
  public @Nullable SettingValueTypeInput returnReason() {
    return this.returnReason.value;
  }

  /**
   *  Additional comments related to the returning item
   */
  public @Nullable String returnReasonComment() {
    return this.returnReasonComment.value;
  }

  /**
   *  The condition in which the return item was received
   */
  public @Nullable SettingValueTypeInput returnCondition() {
    return this.returnCondition.value;
  }

  /**
   *  An optional comment. Required if the condition code required further information such as 'Other'.
   */
  public @Nullable String returnConditionComment() {
    return this.returnConditionComment.value;
  }

  /**
   *  The actual payment action taken for this return item
   */
  public @Nullable SettingValueTypeInput returnPaymentAction() {
    return this.returnPaymentAction.value;
  }

  /**
   *  The actual disposition action taken for this return item
   */
  public @Nullable SettingValueTypeInput returnDispositionAction() {
    return this.returnDispositionAction.value;
  }

  /**
   *  Return quantity
   */
  public @Nullable QuantityTypeInput unitQuantity() {
    return this.unitQuantity.value;
  }

  /**
   *  Unit price of the returning item
   */
  public @Nullable AmountTypeInput unitAmount() {
    return this.unitAmount.value;
  }

  /**
   *  Unit tax type of the retuning item
   */
  public @Nullable TaxTypeInput unitTaxType() {
    return this.unitTaxType.value;
  }

  /**
   *  Item tax amount of the returning item
   */
  public @Nullable AmountTypeInput itemTaxAmount() {
    return this.itemTaxAmount.value;
  }

  /**
   *  Item total amount of returning item
   */
  public @Nullable AmountTypeInput itemAmount() {
    return this.itemAmount.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("ref", ref);
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
        if (product.defined) {
          writer.writeObject("product", product.value != null ? product.value.marshaller() : null);
        }
        if (orderItem.defined) {
          writer.writeObject("orderItem", orderItem.value != null ? orderItem.value.marshaller() : null);
        }
        if (returnReason.defined) {
          writer.writeObject("returnReason", returnReason.value != null ? returnReason.value.marshaller() : null);
        }
        if (returnReasonComment.defined) {
          writer.writeString("returnReasonComment", returnReasonComment.value);
        }
        if (returnCondition.defined) {
          writer.writeObject("returnCondition", returnCondition.value != null ? returnCondition.value.marshaller() : null);
        }
        if (returnConditionComment.defined) {
          writer.writeString("returnConditionComment", returnConditionComment.value);
        }
        if (returnPaymentAction.defined) {
          writer.writeObject("returnPaymentAction", returnPaymentAction.value != null ? returnPaymentAction.value.marshaller() : null);
        }
        if (returnDispositionAction.defined) {
          writer.writeObject("returnDispositionAction", returnDispositionAction.value != null ? returnDispositionAction.value.marshaller() : null);
        }
        if (unitQuantity.defined) {
          writer.writeObject("unitQuantity", unitQuantity.value != null ? unitQuantity.value.marshaller() : null);
        }
        if (unitAmount.defined) {
          writer.writeObject("unitAmount", unitAmount.value != null ? unitAmount.value.marshaller() : null);
        }
        if (unitTaxType.defined) {
          writer.writeObject("unitTaxType", unitTaxType.value != null ? unitTaxType.value.marshaller() : null);
        }
        if (itemTaxAmount.defined) {
          writer.writeObject("itemTaxAmount", itemTaxAmount.value != null ? itemTaxAmount.value.marshaller() : null);
        }
        if (itemAmount.defined) {
          writer.writeObject("itemAmount", itemAmount.value != null ? itemAmount.value.marshaller() : null);
        }
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private Input<String> status = Input.absent();

    private Input<List<AttributeInput>> attributes = Input.absent();

    private Input<ProductKey> product = Input.absent();

    private Input<OrderItemLinkInput> orderItem = Input.absent();

    private Input<SettingValueTypeInput> returnReason = Input.absent();

    private Input<String> returnReasonComment = Input.absent();

    private Input<SettingValueTypeInput> returnCondition = Input.absent();

    private Input<String> returnConditionComment = Input.absent();

    private Input<SettingValueTypeInput> returnPaymentAction = Input.absent();

    private Input<SettingValueTypeInput> returnDispositionAction = Input.absent();

    private Input<QuantityTypeInput> unitQuantity = Input.absent();

    private Input<AmountTypeInput> unitAmount = Input.absent();

    private Input<TaxTypeInput> unitTaxType = Input.absent();

    private Input<AmountTypeInput> itemTaxAmount = Input.absent();

    private Input<AmountTypeInput> itemAmount = Input.absent();

    Builder() {
    }

    /**
     *  External reference. Must be unique. <br/>
     *  Max character limit: 100.
     */
    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    /**
     *  Status or the return order item. <br/>
     *  Max character limit: 50.
     */
    public Builder status(@Nullable String status) {
      this.status = Input.fromNullable(status);
      return this;
    }

    /**
     *  A list of attributes. Attributes can be used to extend the existing data structure with additional data for use in orchestration rules, etc.
     */
    public Builder attributes(@Nullable List<AttributeInput> attributes) {
      this.attributes = Input.fromNullable(attributes);
      return this;
    }

    /**
     *  Product reference of return order item
     */
    public Builder product(@Nullable ProductKey product) {
      this.product = Input.fromNullable(product);
      return this;
    }

    /**
     *  Order item of return order item. This field is optional
     */
    public Builder orderItem(@Nullable OrderItemLinkInput orderItem) {
      this.orderItem = Input.fromNullable(orderItem);
      return this;
    }

    /**
     *  Reason for returning the item
     */
    public Builder returnReason(@Nullable SettingValueTypeInput returnReason) {
      this.returnReason = Input.fromNullable(returnReason);
      return this;
    }

    /**
     *  Additional comments related to the returning item
     */
    public Builder returnReasonComment(@Nullable String returnReasonComment) {
      this.returnReasonComment = Input.fromNullable(returnReasonComment);
      return this;
    }

    /**
     *  The condition in which the return item was received
     */
    public Builder returnCondition(@Nullable SettingValueTypeInput returnCondition) {
      this.returnCondition = Input.fromNullable(returnCondition);
      return this;
    }

    /**
     *  An optional comment. Required if the condition code required further information such as 'Other'.
     */
    public Builder returnConditionComment(@Nullable String returnConditionComment) {
      this.returnConditionComment = Input.fromNullable(returnConditionComment);
      return this;
    }

    /**
     *  The actual payment action taken for this return item
     */
    public Builder returnPaymentAction(@Nullable SettingValueTypeInput returnPaymentAction) {
      this.returnPaymentAction = Input.fromNullable(returnPaymentAction);
      return this;
    }

    /**
     *  The actual disposition action taken for this return item
     */
    public Builder returnDispositionAction(@Nullable SettingValueTypeInput returnDispositionAction) {
      this.returnDispositionAction = Input.fromNullable(returnDispositionAction);
      return this;
    }

    /**
     *  Return quantity
     */
    public Builder unitQuantity(@Nullable QuantityTypeInput unitQuantity) {
      this.unitQuantity = Input.fromNullable(unitQuantity);
      return this;
    }

    /**
     *  Unit price of the returning item
     */
    public Builder unitAmount(@Nullable AmountTypeInput unitAmount) {
      this.unitAmount = Input.fromNullable(unitAmount);
      return this;
    }

    /**
     *  Unit tax type of the retuning item
     */
    public Builder unitTaxType(@Nullable TaxTypeInput unitTaxType) {
      this.unitTaxType = Input.fromNullable(unitTaxType);
      return this;
    }

    /**
     *  Item tax amount of the returning item
     */
    public Builder itemTaxAmount(@Nullable AmountTypeInput itemTaxAmount) {
      this.itemTaxAmount = Input.fromNullable(itemTaxAmount);
      return this;
    }

    /**
     *  Item total amount of returning item
     */
    public Builder itemAmount(@Nullable AmountTypeInput itemAmount) {
      this.itemAmount = Input.fromNullable(itemAmount);
      return this;
    }

    public UpdateReturnOrderItemWithReturnOrderInput build() {
      Utils.checkNotNull(ref, "ref == null");
      return new UpdateReturnOrderItemWithReturnOrderInput(ref, status, attributes, product, orderItem, returnReason, returnReasonComment, returnCondition, returnConditionComment, returnPaymentAction, returnDispositionAction, unitQuantity, unitAmount, unitTaxType, itemTaxAmount, itemAmount);
    }
  }
}
