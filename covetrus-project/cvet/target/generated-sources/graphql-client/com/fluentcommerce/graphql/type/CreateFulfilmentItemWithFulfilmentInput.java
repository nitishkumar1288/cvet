package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class CreateFulfilmentItemWithFulfilmentInput {
  private final @Nonnull String ref;

  private final Input<Integer> requestedQuantity;

  private final int filledQuantity;

  private final int rejectedQuantity;

  private final @Nonnull OrderItemId orderItem;

  CreateFulfilmentItemWithFulfilmentInput(@Nonnull String ref, Input<Integer> requestedQuantity,
      int filledQuantity, int rejectedQuantity, @Nonnull OrderItemId orderItem) {
    this.ref = ref;
    this.requestedQuantity = requestedQuantity;
    this.filledQuantity = filledQuantity;
    this.rejectedQuantity = rejectedQuantity;
    this.orderItem = orderItem;
  }

  /**
   *  External reference. Recommended to be unique.
   */
  public @Nonnull String ref() {
    return this.ref;
  }

  /**
   *  Number of `OrderItem`s assigned to the fulfilment
   */
  public @Nullable Integer requestedQuantity() {
    return this.requestedQuantity.value;
  }

  /**
   *  Number of `OrderItem`s confirmed
   */
  public int filledQuantity() {
    return this.filledQuantity;
  }

  /**
   *  Number of `OrderItem`s rejected
   */
  public int rejectedQuantity() {
    return this.rejectedQuantity;
  }

  /**
   *  Associated `OrderItem`
   */
  public @Nonnull OrderItemId orderItem() {
    return this.orderItem;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeString("ref", ref);
        if (requestedQuantity.defined) {
          writer.writeInt("requestedQuantity", requestedQuantity.value);
        }
        writer.writeInt("filledQuantity", filledQuantity);
        writer.writeInt("rejectedQuantity", rejectedQuantity);
        writer.writeObject("orderItem", orderItem.marshaller());
      }
    };
  }

  public static final class Builder {
    private @Nonnull String ref;

    private Input<Integer> requestedQuantity = Input.absent();

    private int filledQuantity;

    private int rejectedQuantity;

    private @Nonnull OrderItemId orderItem;

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
     *  Number of `OrderItem`s assigned to the fulfilment
     */
    public Builder requestedQuantity(@Nullable Integer requestedQuantity) {
      this.requestedQuantity = Input.fromNullable(requestedQuantity);
      return this;
    }

    /**
     *  Number of `OrderItem`s confirmed
     */
    public Builder filledQuantity(int filledQuantity) {
      this.filledQuantity = filledQuantity;
      return this;
    }

    /**
     *  Number of `OrderItem`s rejected
     */
    public Builder rejectedQuantity(int rejectedQuantity) {
      this.rejectedQuantity = rejectedQuantity;
      return this;
    }

    /**
     *  Associated `OrderItem`
     */
    public Builder orderItem(@Nonnull OrderItemId orderItem) {
      this.orderItem = orderItem;
      return this;
    }

    public CreateFulfilmentItemWithFulfilmentInput build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(orderItem, "orderItem == null");
      return new CreateFulfilmentItemWithFulfilmentInput(ref, requestedQuantity, filledQuantity, rejectedQuantity, orderItem);
    }
  }
}
