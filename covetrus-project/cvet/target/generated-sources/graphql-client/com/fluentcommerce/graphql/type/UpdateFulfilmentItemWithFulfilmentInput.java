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
public final class UpdateFulfilmentItemWithFulfilmentInput {
  private final @Nonnull String id;

  private final Input<String> status;

  private final Input<Integer> requestedQuantity;

  private final Input<Integer> filledQuantity;

  private final Input<Integer> rejectedQuantity;

  UpdateFulfilmentItemWithFulfilmentInput(@Nonnull String id, Input<String> status,
      Input<Integer> requestedQuantity, Input<Integer> filledQuantity,
      Input<Integer> rejectedQuantity) {
    this.id = id;
    this.status = status;
    this.requestedQuantity = requestedQuantity;
    this.filledQuantity = filledQuantity;
    this.rejectedQuantity = rejectedQuantity;
  }

  /**
   *  ID of the object
   */
  public @Nonnull String id() {
    return this.id;
  }

  /**
   *  Status. Supported value is _DELETED_.
   */
  public @Nullable String status() {
    return this.status.value;
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
  public @Nullable Integer filledQuantity() {
    return this.filledQuantity.value;
  }

  /**
   *  Number of `OrderItem`s rejected
   */
  public @Nullable Integer rejectedQuantity() {
    return this.rejectedQuantity.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeCustom("id", com.fluentretail.graphql.type.CustomType.ID, id);
        if (status.defined) {
          writer.writeString("status", status.value);
        }
        if (requestedQuantity.defined) {
          writer.writeInt("requestedQuantity", requestedQuantity.value);
        }
        if (filledQuantity.defined) {
          writer.writeInt("filledQuantity", filledQuantity.value);
        }
        if (rejectedQuantity.defined) {
          writer.writeInt("rejectedQuantity", rejectedQuantity.value);
        }
      }
    };
  }

  public static final class Builder {
    private @Nonnull String id;

    private Input<String> status = Input.absent();

    private Input<Integer> requestedQuantity = Input.absent();

    private Input<Integer> filledQuantity = Input.absent();

    private Input<Integer> rejectedQuantity = Input.absent();

    Builder() {
    }

    /**
     *  ID of the object
     */
    public Builder id(@Nonnull String id) {
      this.id = id;
      return this;
    }

    /**
     *  Status. Supported value is _DELETED_.
     */
    public Builder status(@Nullable String status) {
      this.status = Input.fromNullable(status);
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
    public Builder filledQuantity(@Nullable Integer filledQuantity) {
      this.filledQuantity = Input.fromNullable(filledQuantity);
      return this;
    }

    /**
     *  Number of `OrderItem`s rejected
     */
    public Builder rejectedQuantity(@Nullable Integer rejectedQuantity) {
      this.rejectedQuantity = Input.fromNullable(rejectedQuantity);
      return this;
    }

    public UpdateFulfilmentItemWithFulfilmentInput build() {
      Utils.checkNotNull(id, "id == null");
      return new UpdateFulfilmentItemWithFulfilmentInput(id, status, requestedQuantity, filledQuantity, rejectedQuantity);
    }
  }
}
