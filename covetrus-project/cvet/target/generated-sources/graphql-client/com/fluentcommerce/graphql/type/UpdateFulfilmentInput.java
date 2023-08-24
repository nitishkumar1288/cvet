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
public final class UpdateFulfilmentInput {
  private final @Nonnull String id;

  private final Input<String> status;

  private final Input<List<AttributeInput>> attributes;

  private final Input<String> deliveryType;

  private final Input<String> type;

  private final Input<String> eta;

  private final Input<Object> expiryTime;

  private final Input<List<UpdateFulfilmentItemWithFulfilmentInput>> items;

  UpdateFulfilmentInput(@Nonnull String id, Input<String> status,
      Input<List<AttributeInput>> attributes, Input<String> deliveryType, Input<String> type,
      Input<String> eta, Input<Object> expiryTime,
      Input<List<UpdateFulfilmentItemWithFulfilmentInput>> items) {
    this.id = id;
    this.status = status;
    this.attributes = attributes;
    this.deliveryType = deliveryType;
    this.type = type;
    this.eta = eta;
    this.expiryTime = expiryTime;
    this.items = items;
  }

  /**
   *  ID of the object
   */
  public @Nonnull String id() {
    return this.id;
  }

  /**
   *  The current status of the `Fulfilment`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
   */
  public @Nullable String status() {
    return this.status.value;
  }

  /**
   *  List of fulfilment `attribute`s
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
  public @Nullable String type() {
    return this.type.value;
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
   *  List of `FulfilmentItem`s
   */
  public @Nullable List<UpdateFulfilmentItemWithFulfilmentInput> items() {
    return this.items.value;
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
        if (type.defined) {
          writer.writeString("type", type.value);
        }
        if (eta.defined) {
          writer.writeString("eta", eta.value);
        }
        if (expiryTime.defined) {
          writer.writeCustom("expiryTime", com.fluentretail.graphql.type.CustomType.DATETIME, expiryTime.value != null ? expiryTime.value : null);
        }
        if (items.defined) {
          writer.writeList("items", items.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (UpdateFulfilmentItemWithFulfilmentInput $item : items.value) {
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

    private Input<String> status = Input.absent();

    private Input<List<AttributeInput>> attributes = Input.absent();

    private Input<String> deliveryType = Input.absent();

    private Input<String> type = Input.absent();

    private Input<String> eta = Input.absent();

    private Input<Object> expiryTime = Input.absent();

    private Input<List<UpdateFulfilmentItemWithFulfilmentInput>> items = Input.absent();

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
     *  The current status of the `Fulfilment`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
     */
    public Builder status(@Nullable String status) {
      this.status = Input.fromNullable(status);
      return this;
    }

    /**
     *  List of fulfilment `attribute`s
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
    public Builder type(@Nullable String type) {
      this.type = Input.fromNullable(type);
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
     *  List of `FulfilmentItem`s
     */
    public Builder items(@Nullable List<UpdateFulfilmentItemWithFulfilmentInput> items) {
      this.items = Input.fromNullable(items);
      return this;
    }

    public UpdateFulfilmentInput build() {
      Utils.checkNotNull(id, "id == null");
      return new UpdateFulfilmentInput(id, status, attributes, deliveryType, type, eta, expiryTime, items);
    }
  }
}
