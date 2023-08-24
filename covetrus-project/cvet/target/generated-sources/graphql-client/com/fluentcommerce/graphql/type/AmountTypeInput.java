package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import java.io.IOException;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Override;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class AmountTypeInput {
  private final Input<Double> amount;

  private final Input<Integer> scale;

  private final Input<Integer> unscaledValue;

  AmountTypeInput(Input<Double> amount, Input<Integer> scale, Input<Integer> unscaledValue) {
    this.amount = amount;
    this.scale = scale;
    this.unscaledValue = unscaledValue;
  }

  public @Nullable Double amount() {
    return this.amount.value;
  }

  public @Nullable Integer scale() {
    return this.scale.value;
  }

  public @Nullable Integer unscaledValue() {
    return this.unscaledValue.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        if (amount.defined) {
          writer.writeDouble("amount", amount.value);
        }
        if (scale.defined) {
          writer.writeInt("scale", scale.value);
        }
        if (unscaledValue.defined) {
          writer.writeInt("unscaledValue", unscaledValue.value);
        }
      }
    };
  }

  public static final class Builder {
    private Input<Double> amount = Input.absent();

    private Input<Integer> scale = Input.absent();

    private Input<Integer> unscaledValue = Input.absent();

    Builder() {
    }

    public Builder amount(@Nullable Double amount) {
      this.amount = Input.fromNullable(amount);
      return this;
    }

    public Builder scale(@Nullable Integer scale) {
      this.scale = Input.fromNullable(scale);
      return this;
    }

    public Builder unscaledValue(@Nullable Integer unscaledValue) {
      this.unscaledValue = Input.fromNullable(unscaledValue);
      return this;
    }

    public AmountTypeInput build() {
      return new AmountTypeInput(amount, scale, unscaledValue);
    }
  }
}
