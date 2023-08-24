package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;
import javax.annotation.Nonnull;

@Generated("Apollo GraphQL")
public final class OrderItemLinkInput {
  private final @Nonnull String ref;

  private final @Nonnull OrderLinkInput order;

  OrderItemLinkInput(@Nonnull String ref, @Nonnull OrderLinkInput order) {
    this.ref = ref;
    this.order = order;
  }

  public @Nonnull String ref() {
    return this.ref;
  }

  public @Nonnull OrderLinkInput order() {
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
        writer.writeObject("order", order.marshaller());
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull OrderLinkInput order;

    Builder() {
    }

    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    public Builder order(@Nonnull OrderLinkInput order) {
      this.order = order;
      return this;
    }

    public OrderItemLinkInput build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(order, "order == null");
      return new OrderItemLinkInput(ref, order);
    }
  }
}
