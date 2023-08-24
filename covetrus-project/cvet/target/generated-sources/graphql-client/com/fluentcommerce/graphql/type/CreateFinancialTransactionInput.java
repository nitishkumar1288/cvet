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
public final class CreateFinancialTransactionInput {
  private final @Nonnull String ref;

  private final @Nonnull String type;

  private final double amount;

  private final @Nonnull String currency;

  private final Input<String> externalTransactionCode;

  private final Input<String> externalTransactionId;

  private final Input<String> cardType;

  private final @Nonnull String paymentMethod;

  private final Input<String> paymentProvider;

  private final @Nonnull OrderId order;

  CreateFinancialTransactionInput(@Nonnull String ref, @Nonnull String type, double amount,
      @Nonnull String currency, Input<String> externalTransactionCode,
      Input<String> externalTransactionId, Input<String> cardType, @Nonnull String paymentMethod,
      Input<String> paymentProvider, @Nonnull OrderId order) {
    this.ref = ref;
    this.type = type;
    this.amount = amount;
    this.currency = currency;
    this.externalTransactionCode = externalTransactionCode;
    this.externalTransactionId = externalTransactionId;
    this.cardType = cardType;
    this.paymentMethod = paymentMethod;
    this.paymentProvider = paymentProvider;
    this.order = order;
  }

  public @Nonnull String ref() {
    return this.ref;
  }

  /**
   *  Type of the `FinancialTransaction`, typically used by the Orchestration Engine to determine the workflow that should be applied. Unless stated otherwise, no values are enforced by the platform.<br/>
   *  Max character limit: 25.
   */
  public @Nonnull String type() {
    return this.type;
  }

  public double amount() {
    return this.amount;
  }

  /**
   *  Max character limit: 3.
   */
  public @Nonnull String currency() {
    return this.currency;
  }

  public @Nullable String externalTransactionCode() {
    return this.externalTransactionCode.value;
  }

  public @Nullable String externalTransactionId() {
    return this.externalTransactionId.value;
  }

  /**
   *  The card type used for the payment. Possible values are 'MASTERCARD', 'VISA', 'AMEX', 'DINERS', 'SPAN', 'DISCOVER', 'UNIONPAY', 'JCB', 'MAESTRO', 'INTERAC'.
   */
  public @Nullable String cardType() {
    return this.cardType.value;
  }

  public @Nonnull String paymentMethod() {
    return this.paymentMethod;
  }

  public @Nullable String paymentProvider() {
    return this.paymentProvider.value;
  }

  public @Nonnull OrderId order() {
    return this.order;
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
        writer.writeDouble("amount", amount);
        writer.writeString("currency", currency);
        if (externalTransactionCode.defined) {
          writer.writeString("externalTransactionCode", externalTransactionCode.value);
        }
        if (externalTransactionId.defined) {
          writer.writeString("externalTransactionId", externalTransactionId.value);
        }
        if (cardType.defined) {
          writer.writeString("cardType", cardType.value);
        }
        writer.writeString("paymentMethod", paymentMethod);
        if (paymentProvider.defined) {
          writer.writeString("paymentProvider", paymentProvider.value);
        }
        writer.writeObject("order", order.marshaller());
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull String type;

    private double amount;

    private @Nonnull String currency;

    private Input<String> externalTransactionCode = Input.absent();

    private Input<String> externalTransactionId = Input.absent();

    private Input<String> cardType = Input.absent();

    private @Nonnull String paymentMethod;

    private Input<String> paymentProvider = Input.absent();

    private @Nonnull OrderId order;

    Builder() {
    }

    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    /**
     *  Type of the `FinancialTransaction`, typically used by the Orchestration Engine to determine the workflow that should be applied. Unless stated otherwise, no values are enforced by the platform.<br/>
     *  Max character limit: 25.
     */
    public Builder type(@Nonnull String type) {
      this.type = type;
      return this;
    }

    public Builder amount(double amount) {
      this.amount = amount;
      return this;
    }

    /**
     *  Max character limit: 3.
     */
    public Builder currency(@Nonnull String currency) {
      this.currency = currency;
      return this;
    }

    public Builder externalTransactionCode(@Nullable String externalTransactionCode) {
      this.externalTransactionCode = Input.fromNullable(externalTransactionCode);
      return this;
    }

    public Builder externalTransactionId(@Nullable String externalTransactionId) {
      this.externalTransactionId = Input.fromNullable(externalTransactionId);
      return this;
    }

    /**
     *  The card type used for the payment. Possible values are 'MASTERCARD', 'VISA', 'AMEX', 'DINERS', 'SPAN', 'DISCOVER', 'UNIONPAY', 'JCB', 'MAESTRO', 'INTERAC'.
     */
    public Builder cardType(@Nullable String cardType) {
      this.cardType = Input.fromNullable(cardType);
      return this;
    }

    public Builder paymentMethod(@Nonnull String paymentMethod) {
      this.paymentMethod = paymentMethod;
      return this;
    }

    public Builder paymentProvider(@Nullable String paymentProvider) {
      this.paymentProvider = Input.fromNullable(paymentProvider);
      return this;
    }

    public Builder order(@Nonnull OrderId order) {
      this.order = order;
      return this;
    }

    public CreateFinancialTransactionInput build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(type, "type == null");
      Utils.checkNotNull(currency, "currency == null");
      Utils.checkNotNull(paymentMethod, "paymentMethod == null");
      Utils.checkNotNull(order, "order == null");
      return new CreateFinancialTransactionInput(ref, type, amount, currency, externalTransactionCode, externalTransactionId, cardType, paymentMethod, paymentProvider, order);
    }
  }
}
