package com.fluentcommerce.graphql.mutation.returns;

import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.Mutation;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.OperationName;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.api.ResponseFieldMapper;
import com.apollographql.apollo.api.ResponseFieldMarshaller;
import com.apollographql.apollo.api.ResponseReader;
import com.apollographql.apollo.api.ResponseWriter;
import com.apollographql.apollo.api.internal.Mutator;
import com.apollographql.apollo.api.internal.UnmodifiableMapBuilder;
import com.apollographql.apollo.api.internal.Utils;
import com.fluentretail.graphql.type.CustomType;
import com.fluentcommerce.graphql.type.UpdateReturnOrderInput;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class UpdateReturnOrderMutation implements Mutation<UpdateReturnOrderMutation.Data, UpdateReturnOrderMutation.Data, UpdateReturnOrderMutation.Variables> {
  public static final String OPERATION_DEFINITION = "mutation UpdateReturnOrder($input: UpdateReturnOrderInput) {\n"
      + "  updateReturnOrder(input: $input) {\n"
      + "    __typename\n"
      + "    id\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "UpdateReturnOrder";
    }
  };

  private final UpdateReturnOrderMutation.Variables variables;

  public UpdateReturnOrderMutation(@Nullable UpdateReturnOrderInput input) {
    variables = new UpdateReturnOrderMutation.Variables(input);
  }

  @Override
  public String operationId() {
    return "b82ee78b621ad6290c85ff7529cea165f358316f84b6c7dde6fb7e3d0e99f9e7";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public UpdateReturnOrderMutation.Data wrapData(UpdateReturnOrderMutation.Data data) {
    return data;
  }

  @Override
  public UpdateReturnOrderMutation.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<UpdateReturnOrderMutation.Data> responseFieldMapper() {
    return new Data.Mapper();
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public OperationName name() {
    return OPERATION_NAME;
  }

  public static final class Builder {
    private @Nullable UpdateReturnOrderInput input;

    Builder() {
    }

    public Builder input(@Nullable UpdateReturnOrderInput input) {
      this.input = input;
      return this;
    }

    public UpdateReturnOrderMutation build() {
      return new UpdateReturnOrderMutation(input);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable UpdateReturnOrderInput input;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable UpdateReturnOrderInput input) {
      this.input = input;
      this.valueMap.put("input", input);
    }

    public @Nullable UpdateReturnOrderInput input() {
      return input;
    }

    @Override
    public Map<String, Object> valueMap() {
      return Collections.unmodifiableMap(valueMap);
    }

    @Override
    public InputFieldMarshaller marshaller() {
      return new InputFieldMarshaller() {
        @Override
        public void marshal(InputFieldWriter writer) throws IOException {
          writer.writeObject("input", input != null ? input.marshaller() : null);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("updateReturnOrder", "updateReturnOrder", new UnmodifiableMapBuilder<String, Object>(1)
        .put("input", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "input")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable UpdateReturnOrder updateReturnOrder;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable UpdateReturnOrder updateReturnOrder) {
      this.updateReturnOrder = updateReturnOrder;
    }

    /**
     * Updates a 'Return order'
     */
    public @Nullable UpdateReturnOrder updateReturnOrder() {
      return this.updateReturnOrder;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], updateReturnOrder != null ? updateReturnOrder.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "updateReturnOrder=" + updateReturnOrder
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Data) {
        Data that = (Data) o;
        return ((this.updateReturnOrder == null) ? (that.updateReturnOrder == null) : this.updateReturnOrder.equals(that.updateReturnOrder));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (updateReturnOrder == null) ? 0 : updateReturnOrder.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.updateReturnOrder = updateReturnOrder;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final UpdateReturnOrder.Mapper updateReturnOrderFieldMapper = new UpdateReturnOrder.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final UpdateReturnOrder updateReturnOrder = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<UpdateReturnOrder>() {
          @Override
          public UpdateReturnOrder read(ResponseReader reader) {
            return updateReturnOrderFieldMapper.map(reader);
          }
        });
        return new Data(updateReturnOrder);
      }
    }

    public static final class Builder {
      private @Nullable UpdateReturnOrder updateReturnOrder;

      Builder() {
      }

      public Builder updateReturnOrder(@Nullable UpdateReturnOrder updateReturnOrder) {
        this.updateReturnOrder = updateReturnOrder;
        return this;
      }

      public Builder updateReturnOrder(@Nonnull Mutator<UpdateReturnOrder.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        UpdateReturnOrder.Builder builder = this.updateReturnOrder != null ? this.updateReturnOrder.toBuilder() : UpdateReturnOrder.builder();
        mutator.accept(builder);
        this.updateReturnOrder = builder.build();
        return this;
      }

      public Data build() {
        return new Data(updateReturnOrder);
      }
    }
  }

  public static class UpdateReturnOrder {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public UpdateReturnOrder(@Nonnull String __typename, @Nonnull String id) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  ID of the object
     */
    public @Nonnull String id() {
      return this.id;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "UpdateReturnOrder{"
          + "__typename=" + __typename + ", "
          + "id=" + id
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof UpdateReturnOrder) {
        UpdateReturnOrder that = (UpdateReturnOrder) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id);
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= __typename.hashCode();
        h *= 1000003;
        h ^= id.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.id = id;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<UpdateReturnOrder> {
      @Override
      public UpdateReturnOrder map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        return new UpdateReturnOrder(__typename, id);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder id(@Nonnull String id) {
        this.id = id;
        return this;
      }

      public UpdateReturnOrder build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new UpdateReturnOrder(__typename, id);
      }
    }
  }
}
