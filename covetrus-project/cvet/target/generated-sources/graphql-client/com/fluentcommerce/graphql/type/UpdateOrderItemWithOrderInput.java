package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class UpdateOrderItemWithOrderInput {
  private final @Nonnull String id;

  private final Input<String> ref;

  private final Input<String> productRef;

  private final Input<String> productCatalogueRef;

  private final Input<String> fulfilmentChoiceRef;

  private final Input<String> status;

  private final Input<Integer> quantity;

  private final Input<Double> paidPrice;

  private final Input<String> currency;

  private final Input<Double> price;

  private final Input<Double> taxPrice;

  private final Input<String> taxType;

  private final Input<Double> totalPrice;

  private final Input<Double> totalTaxPrice;

  private final Input<List<AttributeInput>> attributes;

  UpdateOrderItemWithOrderInput(@Nonnull String id, Input<String> ref, Input<String> productRef,
      Input<String> productCatalogueRef, Input<String> fulfilmentChoiceRef, Input<String> status,
      Input<Integer> quantity, Input<Double> paidPrice, Input<String> currency, Input<Double> price,
      Input<Double> taxPrice, Input<String> taxType, Input<Double> totalPrice,
      Input<Double> totalTaxPrice, Input<List<AttributeInput>> attributes) {
    this.id = id;
    this.ref = ref;
    this.productRef = productRef;
    this.productCatalogueRef = productCatalogueRef;
    this.fulfilmentChoiceRef = fulfilmentChoiceRef;
    this.status = status;
    this.quantity = quantity;
    this.paidPrice = paidPrice;
    this.currency = currency;
    this.price = price;
    this.taxPrice = taxPrice;
    this.taxType = taxType;
    this.totalPrice = totalPrice;
    this.totalTaxPrice = totalTaxPrice;
    this.attributes = attributes;
  }

  /**
   *  ID of the `OrderItem`
   */
  public @Nonnull String id() {
    return this.id;
  }

  /**
   *  External reference for the object. Recommended to be unique.
   */
  public @Nullable String ref() {
    return this.ref.value;
  }

  /**
   *  External reference for the associated product.
   */
  public @Nullable String productRef() {
    return this.productRef.value;
  }

  /**
   *  External reference for the product catalogue containing the associated product (specified using the `productRef`in this object).
   *  When `productCatalogueRef` is not passed, the compatibility catalogue for this product is used.
   */
  public @Nullable String productCatalogueRef() {
    return this.productCatalogueRef.value;
  }

  /**
   *  Represents the `FulfilmentChoice` corresponding to this object
   */
  public @Nullable String fulfilmentChoiceRef() {
    return this.fulfilmentChoiceRef.value;
  }

  /**
   *  Status of the OrderItem. Currently supported values are limited to _CREATED_, _NEW_, _COMPLETE_.
   */
  public @Nullable String status() {
    return this.status.value;
  }

  /**
   *  Quantity ordered
   */
  public @Nullable Integer quantity() {
    return this.quantity.value;
  }

  /**
   *  Price paid. Excludes tax.
   */
  public @Nullable Double paidPrice() {
    return this.paidPrice.value;
  }

  /**
   *  Currency. Should ideally be a 3 letter ISO currency code. For instance _AUD_.
   */
  public @Nullable String currency() {
    return this.currency.value;
  }

  /**
   *  Price
   */
  public @Nullable Double price() {
    return this.price.value;
  }

  /**
   *  Tax price
   */
  public @Nullable Double taxPrice() {
    return this.taxPrice.value;
  }

  /**
   *  Tax type. Supported values are _GST_, _VAT_, _EXCLTAX_.
   */
  public @Nullable String taxType() {
    return this.taxType.value;
  }

  /**
   *  Total price
   */
  public @Nullable Double totalPrice() {
    return this.totalPrice.value;
  }

  /**
   *  Total tax price
   */
  public @Nullable Double totalTaxPrice() {
    return this.totalTaxPrice.value;
  }

  /**
   *  List of `OrderItem` `attribute`s. There's expected a JSON object
   */
  public @Nullable List<AttributeInput> attributes() {
    return this.attributes.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeCustom("id", com.fluentretail.graphql.type.CustomType.ID, id);
        if (ref.defined) {
          writer.writeString("ref", ref.value);
        }
        if (productRef.defined) {
          writer.writeString("productRef", productRef.value);
        }
        if (productCatalogueRef.defined) {
          writer.writeString("productCatalogueRef", productCatalogueRef.value);
        }
        if (fulfilmentChoiceRef.defined) {
          writer.writeString("fulfilmentChoiceRef", fulfilmentChoiceRef.value);
        }
        if (status.defined) {
          writer.writeString("status", status.value);
        }
        if (quantity.defined) {
          writer.writeInt("quantity", quantity.value);
        }
        if (paidPrice.defined) {
          writer.writeDouble("paidPrice", paidPrice.value);
        }
        if (currency.defined) {
          writer.writeString("currency", currency.value);
        }
        if (price.defined) {
          writer.writeDouble("price", price.value);
        }
        if (taxPrice.defined) {
          writer.writeDouble("taxPrice", taxPrice.value);
        }
        if (taxType.defined) {
          writer.writeString("taxType", taxType.value);
        }
        if (totalPrice.defined) {
          writer.writeDouble("totalPrice", totalPrice.value);
        }
        if (totalTaxPrice.defined) {
          writer.writeDouble("totalTaxPrice", totalTaxPrice.value);
        }
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
      }
    };
  }

  public static final class Builder {
    private @Nonnull String id;

    private Input<String> ref = Input.absent();

    private Input<String> productRef = Input.absent();

    private Input<String> productCatalogueRef = Input.absent();

    private Input<String> fulfilmentChoiceRef = Input.absent();

    private Input<String> status = Input.absent();

    private Input<Integer> quantity = Input.absent();

    private Input<Double> paidPrice = Input.absent();

    private Input<String> currency = Input.absent();

    private Input<Double> price = Input.absent();

    private Input<Double> taxPrice = Input.absent();

    private Input<String> taxType = Input.absent();

    private Input<Double> totalPrice = Input.absent();

    private Input<Double> totalTaxPrice = Input.absent();

    private Input<List<AttributeInput>> attributes = Input.absent();

    Builder() {
    }

    /**
     *  ID of the `OrderItem`
     */
    public Builder id(@Nonnull String id) {
      this.id = id;
      return this;
    }

    /**
     *  External reference for the object. Recommended to be unique.
     */
    public Builder ref(@Nullable String ref) {
      this.ref = Input.fromNullable(ref);
      return this;
    }

    /**
     *  External reference for the associated product.
     */
    public Builder productRef(@Nullable String productRef) {
      this.productRef = Input.fromNullable(productRef);
      return this;
    }

    /**
     *  External reference for the product catalogue containing the associated product (specified using the `productRef`in this object).
     *  When `productCatalogueRef` is not passed, the compatibility catalogue for this product is used.
     */
    public Builder productCatalogueRef(@Nullable String productCatalogueRef) {
      this.productCatalogueRef = Input.fromNullable(productCatalogueRef);
      return this;
    }

    /**
     *  Represents the `FulfilmentChoice` corresponding to this object
     */
    public Builder fulfilmentChoiceRef(@Nullable String fulfilmentChoiceRef) {
      this.fulfilmentChoiceRef = Input.fromNullable(fulfilmentChoiceRef);
      return this;
    }

    /**
     *  Status of the OrderItem. Currently supported values are limited to _CREATED_, _NEW_, _COMPLETE_.
     */
    public Builder status(@Nullable String status) {
      this.status = Input.fromNullable(status);
      return this;
    }

    /**
     *  Quantity ordered
     */
    public Builder quantity(@Nullable Integer quantity) {
      this.quantity = Input.fromNullable(quantity);
      return this;
    }

    /**
     *  Price paid. Excludes tax.
     */
    public Builder paidPrice(@Nullable Double paidPrice) {
      this.paidPrice = Input.fromNullable(paidPrice);
      return this;
    }

    /**
     *  Currency. Should ideally be a 3 letter ISO currency code. For instance _AUD_.
     */
    public Builder currency(@Nullable String currency) {
      this.currency = Input.fromNullable(currency);
      return this;
    }

    /**
     *  Price
     */
    public Builder price(@Nullable Double price) {
      this.price = Input.fromNullable(price);
      return this;
    }

    /**
     *  Tax price
     */
    public Builder taxPrice(@Nullable Double taxPrice) {
      this.taxPrice = Input.fromNullable(taxPrice);
      return this;
    }

    /**
     *  Tax type. Supported values are _GST_, _VAT_, _EXCLTAX_.
     */
    public Builder taxType(@Nullable String taxType) {
      this.taxType = Input.fromNullable(taxType);
      return this;
    }

    /**
     *  Total price
     */
    public Builder totalPrice(@Nullable Double totalPrice) {
      this.totalPrice = Input.fromNullable(totalPrice);
      return this;
    }

    /**
     *  Total tax price
     */
    public Builder totalTaxPrice(@Nullable Double totalTaxPrice) {
      this.totalTaxPrice = Input.fromNullable(totalTaxPrice);
      return this;
    }

    /**
     *  List of `OrderItem` `attribute`s. There's expected a JSON object
     */
    public Builder attributes(@Nullable List<AttributeInput> attributes) {
      this.attributes = Input.fromNullable(attributes);
      return this;
    }

    public UpdateOrderItemWithOrderInput build() {
      Utils.checkNotNull(id, "id == null");
      return new UpdateOrderItemWithOrderInput(id, ref, productRef, productCatalogueRef, fulfilmentChoiceRef, status, quantity, paidPrice, currency, price, taxPrice, taxType, totalPrice, totalTaxPrice, attributes);
    }
  }
}
