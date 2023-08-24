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
public final class UpdateCreditMemoInput {
  private final @Nonnull String ref;

  private final Input<String> status;

  private final Input<List<AttributeInput>> attributes;

  private final Input<OrderLinkInput> order;

  private final Input<ReturnOrderKey> returnOrder;

  private final Input<List<UpdateCreditMemoItemWithCreditMemoInput>> items;

  private final Input<Object> issueDate;

  private final Input<CurrencyKey> currency;

  private final Input<TaxTypeInput> defaultTaxType;

  private final Input<AmountTypeInput> subTotalAmount;

  private final Input<AmountTypeInput> totalTax;

  private final Input<AmountTypeInput> totalAmount;

  private final Input<AmountTypeInput> totalBalance;

  UpdateCreditMemoInput(@Nonnull String ref, Input<String> status,
      Input<List<AttributeInput>> attributes, Input<OrderLinkInput> order,
      Input<ReturnOrderKey> returnOrder, Input<List<UpdateCreditMemoItemWithCreditMemoInput>> items,
      Input<Object> issueDate, Input<CurrencyKey> currency, Input<TaxTypeInput> defaultTaxType,
      Input<AmountTypeInput> subTotalAmount, Input<AmountTypeInput> totalTax,
      Input<AmountTypeInput> totalAmount, Input<AmountTypeInput> totalBalance) {
    this.ref = ref;
    this.status = status;
    this.attributes = attributes;
    this.order = order;
    this.returnOrder = returnOrder;
    this.items = items;
    this.issueDate = issueDate;
    this.currency = currency;
    this.defaultTaxType = defaultTaxType;
    this.subTotalAmount = subTotalAmount;
    this.totalTax = totalTax;
    this.totalAmount = totalAmount;
    this.totalBalance = totalBalance;
  }

  /**
   *  External reference to the `CreditMemo`. Must be unique. <br/>
   *  Max character limit: 100.
   */
  public @Nonnull String ref() {
    return this.ref;
  }

  /**
   *  Status of the `CreditMemo`. <br/>
   *  Max character limit: 50.
   */
  public @Nullable String status() {
    return this.status.value;
  }

  /**
   *  A list of attributes associated with the `CreditMemo`. This can be used to extend the existing data structure with additional data.
   */
  public @Nullable List<AttributeInput> attributes() {
    return this.attributes.value;
  }

  /**
   *  Reference to an `Order` associated with the `CreditMemo`.
   */
  public @Nullable OrderLinkInput order() {
    return this.order.value;
  }

  /**
   *  Reference to a `ReturnOrder` associated with the `CreditMemo`.
   */
  public @Nullable ReturnOrderKey returnOrder() {
    return this.returnOrder.value;
  }

  /**
   *  The `CreditMemoItem`s associated with this `CreditMemo`.
   */
  public @Nullable List<UpdateCreditMemoItemWithCreditMemoInput> items() {
    return this.items.value;
  }

  /**
   *  Issue date
   */
  public @Nullable Object issueDate() {
    return this.issueDate.value;
  }

  /**
   *  Reference to the `Currency`
   */
  public @Nullable CurrencyKey currency() {
    return this.currency.value;
  }

  /**
   *  The default Tax Type for this credit memo. Individual credit memo items can override.
   */
  public @Nullable TaxTypeInput defaultTaxType() {
    return this.defaultTaxType.value;
  }

  /**
   *  The total amount of this credit memo excluding tax.
   */
  public @Nullable AmountTypeInput subTotalAmount() {
    return this.subTotalAmount.value;
  }

  /**
   *  The total amount of tax for this credit memo
   */
  public @Nullable AmountTypeInput totalTax() {
    return this.totalTax.value;
  }

  /**
   *  The total amount of this credit memo including tax
   */
  public @Nullable AmountTypeInput totalAmount() {
    return this.totalAmount.value;
  }

  /**
   *  The total amount of this credit memo yet to be paid. (This caters for multi-part payments and payment milestones)
   */
  public @Nullable AmountTypeInput totalBalance() {
    return this.totalBalance.value;
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
        if (order.defined) {
          writer.writeObject("order", order.value != null ? order.value.marshaller() : null);
        }
        if (returnOrder.defined) {
          writer.writeObject("returnOrder", returnOrder.value != null ? returnOrder.value.marshaller() : null);
        }
        if (items.defined) {
          writer.writeList("items", items.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (UpdateCreditMemoItemWithCreditMemoInput $item : items.value) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          } : null);
        }
        if (issueDate.defined) {
          writer.writeCustom("issueDate", com.fluentretail.graphql.type.CustomType.DATETIME, issueDate.value != null ? issueDate.value : null);
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
        if (totalBalance.defined) {
          writer.writeObject("totalBalance", totalBalance.value != null ? totalBalance.value.marshaller() : null);
        }
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private Input<String> status = Input.absent();

    private Input<List<AttributeInput>> attributes = Input.absent();

    private Input<OrderLinkInput> order = Input.absent();

    private Input<ReturnOrderKey> returnOrder = Input.absent();

    private Input<List<UpdateCreditMemoItemWithCreditMemoInput>> items = Input.absent();

    private Input<Object> issueDate = Input.absent();

    private Input<CurrencyKey> currency = Input.absent();

    private Input<TaxTypeInput> defaultTaxType = Input.absent();

    private Input<AmountTypeInput> subTotalAmount = Input.absent();

    private Input<AmountTypeInput> totalTax = Input.absent();

    private Input<AmountTypeInput> totalAmount = Input.absent();

    private Input<AmountTypeInput> totalBalance = Input.absent();

    Builder() {
    }

    /**
     *  External reference to the `CreditMemo`. Must be unique. <br/>
     *  Max character limit: 100.
     */
    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    /**
     *  Status of the `CreditMemo`. <br/>
     *  Max character limit: 50.
     */
    public Builder status(@Nullable String status) {
      this.status = Input.fromNullable(status);
      return this;
    }

    /**
     *  A list of attributes associated with the `CreditMemo`. This can be used to extend the existing data structure with additional data.
     */
    public Builder attributes(@Nullable List<AttributeInput> attributes) {
      this.attributes = Input.fromNullable(attributes);
      return this;
    }

    /**
     *  Reference to an `Order` associated with the `CreditMemo`.
     */
    public Builder order(@Nullable OrderLinkInput order) {
      this.order = Input.fromNullable(order);
      return this;
    }

    /**
     *  Reference to a `ReturnOrder` associated with the `CreditMemo`.
     */
    public Builder returnOrder(@Nullable ReturnOrderKey returnOrder) {
      this.returnOrder = Input.fromNullable(returnOrder);
      return this;
    }

    /**
     *  The `CreditMemoItem`s associated with this `CreditMemo`.
     */
    public Builder items(@Nullable List<UpdateCreditMemoItemWithCreditMemoInput> items) {
      this.items = Input.fromNullable(items);
      return this;
    }

    /**
     *  Issue date
     */
    public Builder issueDate(@Nullable Object issueDate) {
      this.issueDate = Input.fromNullable(issueDate);
      return this;
    }

    /**
     *  Reference to the `Currency`
     */
    public Builder currency(@Nullable CurrencyKey currency) {
      this.currency = Input.fromNullable(currency);
      return this;
    }

    /**
     *  The default Tax Type for this credit memo. Individual credit memo items can override.
     */
    public Builder defaultTaxType(@Nullable TaxTypeInput defaultTaxType) {
      this.defaultTaxType = Input.fromNullable(defaultTaxType);
      return this;
    }

    /**
     *  The total amount of this credit memo excluding tax.
     */
    public Builder subTotalAmount(@Nullable AmountTypeInput subTotalAmount) {
      this.subTotalAmount = Input.fromNullable(subTotalAmount);
      return this;
    }

    /**
     *  The total amount of tax for this credit memo
     */
    public Builder totalTax(@Nullable AmountTypeInput totalTax) {
      this.totalTax = Input.fromNullable(totalTax);
      return this;
    }

    /**
     *  The total amount of this credit memo including tax
     */
    public Builder totalAmount(@Nullable AmountTypeInput totalAmount) {
      this.totalAmount = Input.fromNullable(totalAmount);
      return this;
    }

    /**
     *  The total amount of this credit memo yet to be paid. (This caters for multi-part payments and payment milestones)
     */
    public Builder totalBalance(@Nullable AmountTypeInput totalBalance) {
      this.totalBalance = Input.fromNullable(totalBalance);
      return this;
    }

    public UpdateCreditMemoInput build() {
      Utils.checkNotNull(ref, "ref == null");
      return new UpdateCreditMemoInput(ref, status, attributes, order, returnOrder, items, issueDate, currency, defaultTaxType, subTotalAmount, totalTax, totalAmount, totalBalance);
    }
  }
}
