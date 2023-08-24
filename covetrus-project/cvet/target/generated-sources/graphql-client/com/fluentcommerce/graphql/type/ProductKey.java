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
public final class ProductKey {
  private final @Nonnull String ref;

  private final @Nonnull ProductCatalogueKey catalogue;

  ProductKey(@Nonnull String ref, @Nonnull ProductCatalogueKey catalogue) {
    this.ref = ref;
    this.catalogue = catalogue;
  }

  /**
   *  Product reference identifier. <br/>
   *  Max character limit: 100.
   */
  public @Nonnull String ref() {
    return this.ref;
  }

  /**
   *  The Product Catalogue in which this Product resides
   */
  public @Nonnull ProductCatalogueKey catalogue() {
    return this.catalogue;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("ref", ref);
        writer.writeObject("catalogue", catalogue.marshaller());
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private @Nonnull ProductCatalogueKey catalogue;

    Builder() {
    }

    /**
     *  Product reference identifier. <br/>
     *  Max character limit: 100.
     */
    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    /**
     *  The Product Catalogue in which this Product resides
     */
    public Builder catalogue(@Nonnull ProductCatalogueKey catalogue) {
      this.catalogue = catalogue;
      return this;
    }

    public ProductKey build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(catalogue, "catalogue == null");
      return new ProductKey(ref, catalogue);
    }
  }
}
