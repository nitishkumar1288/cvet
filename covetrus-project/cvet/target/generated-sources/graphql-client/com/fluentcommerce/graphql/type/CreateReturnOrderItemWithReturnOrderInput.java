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
public final class CreateReturnOrderItemWithReturnOrderInput {
  private final @Nonnull String ref;

  private final @Nonnull String type;

  private final Input<List<AttributeInput>> attributes;

  private final @Nonnull ProductKey product;

  private final Input<OrderItemLinkInput> orderItem;

  private final Input<SettingValueTypeInput> returnReason;

  private final Input<String> returnReasonComment;

  private final Input<SettingValueTypeInput> returnCondition;

  private final Input<String> returnConditionComment;

  private final Input<SettingValueTypeInput> returnPaymentAction;

  private final Input<SettingValueTypeInput> returnDispositionAction;

  private final @Nonnull QuantityTypeInput unitQuantity;

  private final @Nonnull AmountTypeInput unitAmount;

  private final Input<TaxTypeInput> unitTaxType;

  private final @Nonnull AmountTypeInput itemTaxAmount;

  private final @Nonnull AmountTypeInput itemAmount;

  CreateReturnOrderItemWithReturnOrderInput(@Nonnull String ref, @Nonnull String type,
      Input<List<AttributeInput>> attributes, @Nonnull ProductKey product,
      Input<OrderItemLinkInput> orderItem, Input<SettingValueTypeInput> returnReason,
      Input<String> returnReasonComment, Input<SettingValueTypeInput> returnCondition,
      Input<String> returnConditionComment, Input<SettingValueTypeInput> returnPaymentAction,
      Input<SettingValueTypeInput> returnDispositionAction, @Nonnull QuantityTypeInput unitQuantity,
      @Nonnull AmountTypeInput unitAmount, Input<TaxTypeInput> unitTaxType,
      @Nonnull AmountTypeInput itemTaxAmount, @Nonnull AmountTypeInput itemAmount) {
    this.ref = ref;
    this.type = type;
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
   *  Type. <br/>
   *  Max character limit: 50.
   */
  public @Nonnull String type() {
    return this.type;
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
  public @Nonnull ProductKey product() {
    return this.product;
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
  public @Nonnull QuantityTypeInput unitQuantity() {
    return this.unitQuantity;
  }

  /**
   *  Unit price of the returning item
   */
  public @Nonnull AmountTypeInput unitAmount() {
    return this.unitAmount;
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
  public @Nonnull AmountTypeInput itemTaxAmount() {
    return this.itemTaxAmount;
  }

  /**
   *  Item total amount of returning item
   */
  public @Nonnull AmountTypeInput itemAmount() {
    return this.itemAmount;
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
        writer.writeObject("product", product.marshaller());
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
        writer.writeObject("unitQuantity", unitQuantity.marshaller());
        writer.writeObject("unitAmount", unitAmount.marshaller());
        if (unitTaxType.defined) {
          writer.writeObject("unitTaxType", unitTaxType.value != null ? unitTaxType.value.marshaller() : null);
        }
        writer.writeObject("itemTaxAmount", itemTaxAmount.marshaller());
        writer.writeObject("itemAmount", itemAmount.marshaller());
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull String type;

    private Input<List<AttributeInput>> attributes = Input.absent();

    private @Nonnull ProductKey product;

    private Input<OrderItemLinkInput> orderItem = Input.absent();

    private Input<SettingValueTypeInput> returnReason = Input.absent();

    private Input<String> returnReasonComment = Input.absent();

    private Input<SettingValueTypeInput> returnCondition = Input.absent();

    private Input<String> returnConditionComment = Input.absent();

    private Input<SettingValueTypeInput> returnPaymentAction = Input.absent();

    private Input<SettingValueTypeInput> returnDispositionAction = Input.absent();

    private @Nonnull QuantityTypeInput unitQuantity;

    private @Nonnull AmountTypeInput unitAmount;

    private Input<TaxTypeInput> unitTaxType = Input.absent();

    private @Nonnull AmountTypeInput itemTaxAmount;

    private @Nonnull AmountTypeInput itemAmount;

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
     *  Type. <br/>
     *  Max character limit: 50.
     */
    public Builder type(@Nonnull String type) {
      this.type = type;
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
    public Builder product(@Nonnull ProductKey product) {
      this.product = product;
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
    public Builder unitQuantity(@Nonnull QuantityTypeInput unitQuantity) {
      this.unitQuantity = unitQuantity;
      return this;
    }

    /**
     *  Unit price of the returning item
     */
    public Builder unitAmount(@Nonnull AmountTypeInput unitAmount) {
      this.unitAmount = unitAmount;
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
    public Builder itemTaxAmount(@Nonnull AmountTypeInput itemTaxAmount) {
      this.itemTaxAmount = itemTaxAmount;
      return this;
    }

    /**
     *  Item total amount of returning item
     */
    public Builder itemAmount(@Nonnull AmountTypeInput itemAmount) {
      this.itemAmount = itemAmount;
      return this;
    }

    public CreateReturnOrderItemWithReturnOrderInput build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(type, "type == null");
      Utils.checkNotNull(product, "product == null");
      Utils.checkNotNull(unitQuantity, "unitQuantity == null");
      Utils.checkNotNull(unitAmount, "unitAmount == null");
      Utils.checkNotNull(itemTaxAmount, "itemTaxAmount == null");
      Utils.checkNotNull(itemAmount, "itemAmount == null");
      return new CreateReturnOrderItemWithReturnOrderInput(ref, type, attributes, product, orderItem, returnReason, returnReasonComment, returnCondition, returnConditionComment, returnPaymentAction, returnDispositionAction, unitQuantity, unitAmount, unitTaxType, itemTaxAmount, itemAmount);
    }
  }
}
