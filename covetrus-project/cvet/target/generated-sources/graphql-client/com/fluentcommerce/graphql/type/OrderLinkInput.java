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
public final class OrderLinkInput {
  private final @Nonnull String ref;

  private final @Nonnull RetailerId retailer;

  OrderLinkInput(@Nonnull String ref, @Nonnull RetailerId retailer) {
    this.ref = ref;
    this.retailer = retailer;
  }

  public @Nonnull String ref() {
    return this.ref;
  }

  public @Nonnull RetailerId retailer() {
    return this.retailer;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("ref", ref);
        writer.writeObject("retailer", retailer.marshaller());
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull RetailerId retailer;

    Builder() {
    }

    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    public Builder retailer(@Nonnull RetailerId retailer) {
      this.retailer = retailer;
      return this;
    }

    public OrderLinkInput build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(retailer, "retailer == null");
      return new OrderLinkInput(ref, retailer);
    }
  }
}
