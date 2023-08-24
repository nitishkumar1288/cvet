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
public final class CreateCreditMemoInput {
  private final @Nonnull String ref;

  private final @Nonnull String type;

  private final Input<List<AttributeInput>> attributes;

  private final @Nonnull BillingAccountKey billingAccount;

  private final Input<List<CreateCreditMemoItemWithCreditMemoInput>> items;

  private final Input<InvoiceKey> invoice;

  private final Input<OrderLinkInput> order;

  private final Input<ReturnOrderKey> returnOrder;

  private final @Nonnull Object issueDate;

  private final @Nonnull CurrencyKey currency;

  private final @Nonnull TaxTypeInput defaultTaxType;

  private final @Nonnull AmountTypeInput subTotalAmount;

  private final @Nonnull AmountTypeInput totalTax;

  private final @Nonnull AmountTypeInput totalAmount;

  private final @Nonnull AmountTypeInput totalBalance;

  CreateCreditMemoInput(@Nonnull String ref, @Nonnull String type,
      Input<List<AttributeInput>> attributes, @Nonnull BillingAccountKey billingAccount,
      Input<List<CreateCreditMemoItemWithCreditMemoInput>> items, Input<InvoiceKey> invoice,
      Input<OrderLinkInput> order, Input<ReturnOrderKey> returnOrder, @Nonnull Object issueDate,
      @Nonnull CurrencyKey currency, @Nonnull TaxTypeInput defaultTaxType,
      @Nonnull AmountTypeInput subTotalAmount, @Nonnull AmountTypeInput totalTax,
      @Nonnull AmountTypeInput totalAmount, @Nonnull AmountTypeInput totalBalance) {
    this.ref = ref;
    this.type = type;
    this.attributes = attributes;
    this.billingAccount = billingAccount;
    this.items = items;
    this.invoice = invoice;
    this.order = order;
    this.returnOrder = returnOrder;
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
   *  Type of the `CreditMemo`, typically used by the Orchestration Engine to determine the workflow that should be applied. <br/>
   *  Max character limit: 50.
   */
  public @Nonnull String type() {
    return this.type;
  }

  /**
   *  A list of attributes associated with the `CreditMemo`. This can be used to extend the existing data structure with additional data.
   */
  public @Nullable List<AttributeInput> attributes() {
    return this.attributes.value;
  }

  /**
   *  `BillingAccount` associated with the `CreditMemo`.
   */
  public @Nonnull BillingAccountKey billingAccount() {
    return this.billingAccount;
  }

  /**
   *  The `CreditMemoItem`s associated with this `CreditMemo`.
   */
  public @Nullable List<CreateCreditMemoItemWithCreditMemoInput> items() {
    return this.items.value;
  }

  /**
   *  `Invoice` associated with this `CreditMemo`.
   */
  public @Nullable InvoiceKey invoice() {
    return this.invoice.value;
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
   *  Issue date
   */
  public @Nonnull Object issueDate() {
    return this.issueDate;
  }

  /**
   *  Reference to the `Currency`.
   */
  public @Nonnull CurrencyKey currency() {
    return this.currency;
  }

  /**
   *  The default Tax Type for this credit memo. Individual credit memo items can override
   */
  public @Nonnull TaxTypeInput defaultTaxType() {
    return this.defaultTaxType;
  }

  /**
   *  The total amount of this credit memo excluding tax
   */
  public @Nonnull AmountTypeInput subTotalAmount() {
    return this.subTotalAmount;
  }

  /**
   *  The total amount of tax for this credit memo
   */
  public @Nonnull AmountTypeInput totalTax() {
    return this.totalTax;
  }

  /**
   *  The total amount of this credit memo including tax
   */
  public @Nonnull AmountTypeInput totalAmount() {
    return this.totalAmount;
  }

  /**
   *  The total amount of this credit memo yet to be paid. (This caters for multi-part payments and payment milestones)
   */
  public @Nonnull AmountTypeInput totalBalance() {
    return this.totalBalance;
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
        writer.writeObject("billingAccount", billingAccount.marshaller());
        if (items.defined) {
          writer.writeList("items", items.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (CreateCreditMemoItemWithCreditMemoInput $item : items.value) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          } : null);
        }
        if (invoice.defined) {
          writer.writeObject("invoice", invoice.value != null ? invoice.value.marshaller() : null);
        }
        if (order.defined) {
          writer.writeObject("order", order.value != null ? order.value.marshaller() : null);
        }
        if (returnOrder.defined) {
          writer.writeObject("returnOrder", returnOrder.value != null ? returnOrder.value.marshaller() : null);
        }
        writer.writeCustom("issueDate", com.fluentretail.graphql.type.CustomType.DATETIME, issueDate);
        writer.writeObject("currency", currency.marshaller());
        writer.writeObject("defaultTaxType", defaultTaxType.marshaller());
        writer.writeObject("subTotalAmount", subTotalAmount.marshaller());
        writer.writeObject("totalTax", totalTax.marshaller());
        writer.writeObject("totalAmount", totalAmount.marshaller());
        writer.writeObject("totalBalance", totalBalance.marshaller());
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull String type;

    private Input<List<AttributeInput>> attributes = Input.absent();

    private @Nonnull BillingAccountKey billingAccount;

    private Input<List<CreateCreditMemoItemWithCreditMemoInput>> items = Input.absent();

    private Input<InvoiceKey> invoice = Input.absent();

    private Input<OrderLinkInput> order = Input.absent();

    private Input<ReturnOrderKey> returnOrder = Input.absent();

    private @Nonnull Object issueDate;

    private @Nonnull CurrencyKey currency;

    private @Nonnull TaxTypeInput defaultTaxType;

    private @Nonnull AmountTypeInput subTotalAmount;

    private @Nonnull AmountTypeInput totalTax;

    private @Nonnull AmountTypeInput totalAmount;

    private @Nonnull AmountTypeInput totalBalance;

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
     *  Type of the `CreditMemo`, typically used by the Orchestration Engine to determine the workflow that should be applied. <br/>
     *  Max character limit: 50.
     */
    public Builder type(@Nonnull String type) {
      this.type = type;
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
     *  `BillingAccount` associated with the `CreditMemo`.
     */
    public Builder billingAccount(@Nonnull BillingAccountKey billingAccount) {
      this.billingAccount = billingAccount;
      return this;
    }

    /**
     *  The `CreditMemoItem`s associated with this `CreditMemo`.
     */
    public Builder items(@Nullable List<CreateCreditMemoItemWithCreditMemoInput> items) {
      this.items = Input.fromNullable(items);
      return this;
    }

    /**
     *  `Invoice` associated with this `CreditMemo`.
     */
    public Builder invoice(@Nullable InvoiceKey invoice) {
      this.invoice = Input.fromNullable(invoice);
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
     *  Issue date
     */
    public Builder issueDate(@Nonnull Object issueDate) {
      this.issueDate = issueDate;
      return this;
    }

    /**
     *  Reference to the `Currency`.
     */
    public Builder currency(@Nonnull CurrencyKey currency) {
      this.currency = currency;
      return this;
    }

    /**
     *  The default Tax Type for this credit memo. Individual credit memo items can override
     */
    public Builder defaultTaxType(@Nonnull TaxTypeInput defaultTaxType) {
      this.defaultTaxType = defaultTaxType;
      return this;
    }

    /**
     *  The total amount of this credit memo excluding tax
     */
    public Builder subTotalAmount(@Nonnull AmountTypeInput subTotalAmount) {
      this.subTotalAmount = subTotalAmount;
      return this;
    }

    /**
     *  The total amount of tax for this credit memo
     */
    public Builder totalTax(@Nonnull AmountTypeInput totalTax) {
      this.totalTax = totalTax;
      return this;
    }

    /**
     *  The total amount of this credit memo including tax
     */
    public Builder totalAmount(@Nonnull AmountTypeInput totalAmount) {
      this.totalAmount = totalAmount;
      return this;
    }

    /**
     *  The total amount of this credit memo yet to be paid. (This caters for multi-part payments and payment milestones)
     */
    public Builder totalBalance(@Nonnull AmountTypeInput totalBalance) {
      this.totalBalance = totalBalance;
      return this;
    }

    public CreateCreditMemoInput build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(type, "type == null");
      Utils.checkNotNull(billingAccount, "billingAccount == null");
      Utils.checkNotNull(issueDate, "issueDate == null");
      Utils.checkNotNull(currency, "currency == null");
      Utils.checkNotNull(defaultTaxType, "defaultTaxType == null");
      Utils.checkNotNull(subTotalAmount, "subTotalAmount == null");
      Utils.checkNotNull(totalTax, "totalTax == null");
      Utils.checkNotNull(totalAmount, "totalAmount == null");
      Utils.checkNotNull(totalBalance, "totalBalance == null");
      return new CreateCreditMemoInput(ref, type, attributes, billingAccount, items, invoice, order, returnOrder, issueDate, currency, defaultTaxType, subTotalAmount, totalTax, totalAmount, totalBalance);
    }
  }
}
