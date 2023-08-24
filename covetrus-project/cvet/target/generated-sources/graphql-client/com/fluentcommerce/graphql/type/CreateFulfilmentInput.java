package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class CreateFulfilmentInput {
  private final @Nonnull String ref;

  private final Input<List<AttributeInput>> attributes;

  private final Input<String> deliveryType;

  private final @Nonnull String type;

  private final Input<String> eta;

  private final Input<Object> expiryTime;

  private final @Nonnull OrderId order;

  private final Input<String> fulfilmentChoiceRef;

  private final Input<AddressId> fromAddress;

  private final Input<AddressId> toAddress;

  private final Input<CustomerId> customer;

  private final Input<List<CreateFulfilmentItemWithFulfilmentInput>> items;

  CreateFulfilmentInput(@Nonnull String ref, Input<List<AttributeInput>> attributes,
      Input<String> deliveryType, @Nonnull String type, Input<String> eta, Input<Object> expiryTime,
      @Nonnull OrderId order, Input<String> fulfilmentChoiceRef, Input<AddressId> fromAddress,
      Input<AddressId> toAddress, Input<CustomerId> customer,
      Input<List<CreateFulfilmentItemWithFulfilmentInput>> items) {
    this.ref = ref;
    this.attributes = attributes;
    this.deliveryType = deliveryType;
    this.type = type;
    this.eta = eta;
    this.expiryTime = expiryTime;
    this.order = order;
    this.fulfilmentChoiceRef = fulfilmentChoiceRef;
    this.fromAddress = fromAddress;
    this.toAddress = toAddress;
    this.customer = customer;
    this.items = items;
  }

  /**
   *  External reference. Recommended to be unique.
   */
  public @Nonnull String ref() {
    return this.ref;
  }

  /**
   *  List of `attribute`s
   */
  public @Nullable List<AttributeInput> attributes() {
    return this.attributes.value;
  }

  /**
   *  Type of delivery. Supported values are _STANDARD_, _OVERNIGHT_ and _EXPRESS_.
   */
  public @Nullable String deliveryType() {
    return this.deliveryType.value;
  }

  /**
   *  Type of the `Fulfilment`, typically used by the Orchestration Engine to determine the workflow that should be applied. Unless stated otherwise, no values are enforced by the platform.<br/>
   *  Type of the Fulfilment. Supports all values.
   */
  public @Nonnull String type() {
    return this.type;
  }

  /**
   *  Estimated time of completing this fulfilment
   */
  public @Nullable String eta() {
    return this.eta.value;
  }

  /**
   *  Expiry time of the fulfilment
   */
  public @Nullable Object expiryTime() {
    return this.expiryTime.value;
  }

  /**
   *  Represents the associated `Order`
   */
  public @Nonnull OrderId order() {
    return this.order;
  }

  /**
   *  Represents the associated `FulfilmentChoice`
   */
  public @Nullable String fulfilmentChoiceRef() {
    return this.fulfilmentChoiceRef.value;
  }

  /**
   *  Represents `Address` fulfilling this fulfilment
   */
  public @Nullable AddressId fromAddress() {
    return this.fromAddress.value;
  }

  /**
   *  Represents the delivery `Address`
   */
  public @Nullable AddressId toAddress() {
    return this.toAddress.value;
  }

  /**
   *  Represents the associated `Customer`
   */
  public @Nullable CustomerId customer() {
    return this.customer.value;
  }

  /**
   *  List of `FulfilmentItem`s
   */
  public @Nullable List<CreateFulfilmentItemWithFulfilmentInput> items() {
    return this.items.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("ref", ref);
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
        if (deliveryType.defined) {
          writer.writeString("deliveryType", deliveryType.value);
        }
        writer.writeString("type", type);
        if (eta.defined) {
          writer.writeString("eta", eta.value);
        }
        if (expiryTime.defined) {
          writer.writeCustom("expiryTime", com.fluentretail.graphql.type.CustomType.DATETIME, expiryTime.value != null ? expiryTime.value : null);
        }
        writer.writeObject("order", order.marshaller());
        if (fulfilmentChoiceRef.defined) {
          writer.writeString("fulfilmentChoiceRef", fulfilmentChoiceRef.value);
        }
        if (fromAddress.defined) {
          writer.writeObject("fromAddress", fromAddress.value != null ? fromAddress.value.marshaller() : null);
        }
        if (toAddress.defined) {
          writer.writeObject("toAddress", toAddress.value != null ? toAddress.value.marshaller() : null);
        }
        if (customer.defined) {
          writer.writeObject("customer", customer.value != null ? customer.value.marshaller() : null);
        }
        if (items.defined) {
          writer.writeList("items", items.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (CreateFulfilmentItemWithFulfilmentInput $item : items.value) {
                listItemWriter.writeObject($item.marshaller());
              }
            }
          } : null);
        }
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private Input<List<AttributeInput>> attributes = Input.absent();

    private Input<String> deliveryType = Input.absent();

    private @Nonnull String type;

    private Input<String> eta = Input.absent();

    private Input<Object> expiryTime = Input.absent();

    private @Nonnull OrderId order;

    private Input<String> fulfilmentChoiceRef = Input.absent();

    private Input<AddressId> fromAddress = Input.absent();

    private Input<AddressId> toAddress = Input.absent();

    private Input<CustomerId> customer = Input.absent();

    private Input<List<CreateFulfilmentItemWithFulfilmentInput>> items = Input.absent();

    Builder() {
    }

    /**
     *  External reference. Recommended to be unique.
     */
    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    /**
     *  List of `attribute`s
     */
    public Builder attributes(@Nullable List<AttributeInput> attributes) {
      this.attributes = Input.fromNullable(attributes);
      return this;
    }

    /**
     *  Type of delivery. Supported values are _STANDARD_, _OVERNIGHT_ and _EXPRESS_.
     */
    public Builder deliveryType(@Nullable String deliveryType) {
      this.deliveryType = Input.fromNullable(deliveryType);
      return this;
    }

    /**
     *  Type of the `Fulfilment`, typically used by the Orchestration Engine to determine the workflow that should be applied. Unless stated otherwise, no values are enforced by the platform.<br/>
     *  Type of the Fulfilment. Supports all values.
     */
    public Builder type(@Nonnull String type) {
      this.type = type;
      return this;
    }

    /**
     *  Estimated time of completing this fulfilment
     */
    public Builder eta(@Nullable String eta) {
      this.eta = Input.fromNullable(eta);
      return this;
    }

    /**
     *  Expiry time of the fulfilment
     */
    public Builder expiryTime(@Nullable Object expiryTime) {
      this.expiryTime = Input.fromNullable(expiryTime);
      return this;
    }

    /**
     *  Represents the associated `Order`
     */
    public Builder order(@Nonnull OrderId order) {
      this.order = order;
      return this;
    }

    /**
     *  Represents the associated `FulfilmentChoice`
     */
    public Builder fulfilmentChoiceRef(@Nullable String fulfilmentChoiceRef) {
      this.fulfilmentChoiceRef = Input.fromNullable(fulfilmentChoiceRef);
      return this;
    }

    /**
     *  Represents `Address` fulfilling this fulfilment
     */
    public Builder fromAddress(@Nullable AddressId fromAddress) {
      this.fromAddress = Input.fromNullable(fromAddress);
      return this;
    }

    /**
     *  Represents the delivery `Address`
     */
    public Builder toAddress(@Nullable AddressId toAddress) {
      this.toAddress = Input.fromNullable(toAddress);
      return this;
    }

    /**
     *  Represents the associated `Customer`
     */
    public Builder customer(@Nullable CustomerId customer) {
      this.customer = Input.fromNullable(customer);
      return this;
    }

    /**
     *  List of `FulfilmentItem`s
     */
    public Builder items(@Nullable List<CreateFulfilmentItemWithFulfilmentInput> items) {
      this.items = Input.fromNullable(items);
      return this;
    }

    public CreateFulfilmentInput build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(type, "type == null");
      Utils.checkNotNull(order, "order == null");
      return new CreateFulfilmentInput(ref, attributes, deliveryType, type, eta, expiryTime, order, fulfilmentChoiceRef, fromAddress, toAddress, customer, items);
    }
  }
}
