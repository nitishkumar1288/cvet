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
public final class ReturnOrderItemKey {
  private final @Nonnull String ref;

  private final @Nonnull ReturnOrderKey returnOrder;

  ReturnOrderItemKey(@Nonnull String ref, @Nonnull ReturnOrderKey returnOrder) {
    this.ref = ref;
    this.returnOrder = returnOrder;
  }

  /**
   *  The client's reference identifier for the object
   */
  public @Nonnull String ref() {
    return this.ref;
  }

  /**
   *  Return order associated with the order item
   */
  public @Nonnull ReturnOrderKey returnOrder() {
    return this.returnOrder;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("ref", ref);
        writer.writeObject("returnOrder", returnOrder.marshaller());
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull ReturnOrderKey returnOrder;

    Builder() {
    }

    /**
     *  The client's reference identifier for the object
     */
    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    /**
     *  Return order associated with the order item
     */
    public Builder returnOrder(@Nonnull ReturnOrderKey returnOrder) {
      this.returnOrder = returnOrder;
      return this;
    }

    public ReturnOrderItemKey build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(returnOrder, "returnOrder == null");
      return new ReturnOrderItemKey(ref, returnOrder);
    }
  }
}
