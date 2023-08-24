package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("Apollo GraphQL")
public final class AttributeInput {
  private final @Nonnull String name;

  private final @Nonnull String type;

  private final @Nonnull Object value;

  AttributeInput(@Nonnull String name, @Nonnull String type, @Nonnull Object value) {
    this.name = name;
    this.type = type;
    this.value = value;
  }

  public @Nonnull String name() {
    return this.name;
  }

  public @Nonnull String type() {
    return this.type;
  }

  public @Nonnull Object value() {
    return this.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("name", name);
        writer.writeString("type", type);
        writer.writeCustom("value", com.fluentretail.graphql.type.CustomType.JSON, value);
      }
    };
  }

  public static final class Builder {
    private @Nonnull String name;

    private @Nonnull String type;

    private @Nonnull Object value;

    Builder() {
    }

    public Builder name(@Nonnull String name) {
      this.name = name;
      return this;
    }

    public Builder type(@Nonnull String type) {
      this.type = type;
      return this;
    }

    public Builder value(@Nonnull Object value) {
      this.value = value;
      return this;
    }

    public AttributeInput build() {
      Utils.checkNotNull(name, "name == null");
      Utils.checkNotNull(type, "type == null");
      Utils.checkNotNull(value, "value == null");
      return new AttributeInput(name, type, value);
    }
  }
}
