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
public final class SettingValueTypeInput {
  private final @Nonnull String value;

  private final Input<String> label;

  SettingValueTypeInput(@Nonnull String value, Input<String> label) {
    this.value = value;
    this.label = label;
  }

  public @Nonnull String value() {
    return this.value;
  }

  public @Nullable String label() {
    return this.label.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("value", value);
        if (label.defined) {
          writer.writeString("label", label.value);
        }
      }
    };
  }

  public static final class Builder {
    private @Nonnull String value;

    private Input<String> label = Input.absent();

    Builder() {
    }

    public Builder value(@Nonnull String value) {
      this.value = value;
      return this;
    }

    public Builder label(@Nullable String label) {
      this.label = Input.fromNullable(label);
      return this;
    }

    public SettingValueTypeInput build() {
      Utils.checkNotNull(value, "value == null");
      return new SettingValueTypeInput(value, label);
    }
  }
}
