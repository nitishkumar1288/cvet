package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class UpdateStorageAreaWithLocationInput {
  private final @Nonnull String id;

  private final Input<String> status;

  private final Input<String> type;

  private final Input<List<AttributeInput>> attributes;

  UpdateStorageAreaWithLocationInput(@Nonnull String id, Input<String> status, Input<String> type,
      Input<List<AttributeInput>> attributes) {
    this.id = id;
    this.status = status;
    this.type = type;
    this.attributes = attributes;
  }

  /**
   *  ID of the object
   */
  public @Nonnull String id() {
    return this.id;
  }

  public @Nullable String status() {
    return this.status.value;
  }

  /**
   *  Max character limit: 50.
   */
  public @Nullable String type() {
    return this.type.value;
  }

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
        if (status.defined) {
          writer.writeString("status", status.value);
        }
        if (type.defined) {
          writer.writeString("type", type.value);
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

    private Input<String> status = Input.absent();

    private Input<String> type = Input.absent();

    private Input<List<AttributeInput>> attributes = Input.absent();

    Builder() {
    }

    /**
     *  ID of the object
     */
    public Builder id(@Nonnull String id) {
      this.id = id;
      return this;
    }

    public Builder status(@Nullable String status) {
      this.status = Input.fromNullable(status);
      return this;
    }

    /**
     *  Max character limit: 50.
     */
    public Builder type(@Nullable String type) {
      this.type = Input.fromNullable(type);
      return this;
    }

    public Builder attributes(@Nullable List<AttributeInput> attributes) {
      this.attributes = Input.fromNullable(attributes);
      return this;
    }

    public UpdateStorageAreaWithLocationInput build() {
      Utils.checkNotNull(id, "id == null");
      return new UpdateStorageAreaWithLocationInput(id, status, type, attributes);
    }
  }
}
