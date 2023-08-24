package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Double;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class UpdateOrderInput {
  private final @Nonnull String id;

  private final Input<String> status;

  private final Input<List<AttributeInput>> attributes;

  private final Input<Double> totalPrice;

  private final Input<Double> totalTaxPrice;

  private final Input<List<UpdateOrderItemWithOrderInput>> items;

  UpdateOrderInput(@Nonnull String id, Input<String> status, Input<List<AttributeInput>> attributes,
      Input<Double> totalPrice, Input<Double> totalTaxPrice,
      Input<List<UpdateOrderItemWithOrderInput>> items) {
    this.id = id;
    this.status = status;
    this.attributes = attributes;
    this.totalPrice = totalPrice;
    this.totalTaxPrice = totalTaxPrice;
    this.items = items;
  }

  /**
   *  ID of the `Order`
   */
  public @Nonnull String id() {
    return this.id;
  }

  /**
   *  The current status of the `Order`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
   */
  public @Nullable String status() {
    return this.status.value;
  }

  /**
   *  List of order's `attribute`s
   */
  public @Nullable List<AttributeInput> attributes() {
    return this.attributes.value;
  }

  /**
   *  Tax price
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
   *  List of `OrderItem`s to be updated
   */
  public @Nullable List<UpdateOrderItemWithOrderInput> items() {
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
        if (totalPrice.defined) {
          writer.writeDouble("totalPrice", totalPrice.value);
        }
        if (totalTaxPrice.defined) {
          writer.writeDouble("totalTaxPrice", totalTaxPrice.value);
        }
        if (items.defined) {
          writer.writeList("items", items.value != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (UpdateOrderItemWithOrderInput $item : items.value) {
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

    private Input<Double> totalPrice = Input.absent();

    private Input<Double> totalTaxPrice = Input.absent();

    private Input<List<UpdateOrderItemWithOrderInput>> items = Input.absent();

    Builder() {
    }

    /**
     *  ID of the `Order`
     */
    public Builder id(@Nonnull String id) {
      this.id = id;
      return this;
    }

    /**
     *  The current status of the `Order`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
     */
    public Builder status(@Nullable String status) {
      this.status = Input.fromNullable(status);
      return this;
    }

    /**
     *  List of order's `attribute`s
     */
    public Builder attributes(@Nullable List<AttributeInput> attributes) {
      this.attributes = Input.fromNullable(attributes);
      return this;
    }

    /**
     *  Tax price
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
     *  List of `OrderItem`s to be updated
     */
    public Builder items(@Nullable List<UpdateOrderItemWithOrderInput> items) {
      this.items = Input.fromNullable(items);
      return this;
    }

    public UpdateOrderInput build() {
      Utils.checkNotNull(id, "id == null");
      return new UpdateOrderInput(id, status, attributes, totalPrice, totalTaxPrice, items);
    }
  }
}
