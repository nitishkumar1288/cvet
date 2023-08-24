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
public final class QuantityTypeInput {
  private final int quantity;

  private final Input<String> unit;

  QuantityTypeInput(int quantity, Input<String> unit) {
    this.quantity = quantity;
    this.unit = unit;
  }

  public int quantity() {
    return this.quantity;
  }

  public @Nullable String unit() {
    return this.unit.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeInt("quantity", quantity);
        if (unit.defined) {
          writer.writeString("unit", unit.value);
        }
      }
    };
  }

  public static final class Builder {
    private int quantity;

    private Input<String> unit = Input.absent();

    Builder() {
    }

    public Builder quantity(int quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder unit(@Nullable String unit) {
      this.unit = Input.fromNullable(unit);
      return this;
    }

    public QuantityTypeInput build() {
      return new QuantityTypeInput(quantity, unit);
    }
  }
}
