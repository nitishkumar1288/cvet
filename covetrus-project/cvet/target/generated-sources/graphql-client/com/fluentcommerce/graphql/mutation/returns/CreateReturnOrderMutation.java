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
import com.fluentcommerce.graphql.type.CreateReturnOrderInput;
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
public final class CreateReturnOrderMutation implements Mutation<CreateReturnOrderMutation.Data, CreateReturnOrderMutation.Data, CreateReturnOrderMutation.Variables> {
  public static final String OPERATION_DEFINITION = "mutation CreateReturnOrder($input: CreateReturnOrderInput) {\n"
      + "  createReturnOrder(input: $input) {\n"
      + "    __typename\n"
      + "    ref\n"
      + "    status\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "CreateReturnOrder";
    }
  };

  private final CreateReturnOrderMutation.Variables variables;

  public CreateReturnOrderMutation(@Nullable CreateReturnOrderInput input) {
    variables = new CreateReturnOrderMutation.Variables(input);
  }

  @Override
  public String operationId() {
    return "74f58d76f726358ea2a6d2b092ea05d01551d1a2d581c3d1b70cb3ecf2f01880";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public CreateReturnOrderMutation.Data wrapData(CreateReturnOrderMutation.Data data) {
    return data;
  }

  @Override
  public CreateReturnOrderMutation.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<CreateReturnOrderMutation.Data> responseFieldMapper() {
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
    private @Nullable CreateReturnOrderInput input;

    Builder() {
    }

    public Builder input(@Nullable CreateReturnOrderInput input) {
      this.input = input;
      return this;
    }

    public CreateReturnOrderMutation build() {
      return new CreateReturnOrderMutation(input);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable CreateReturnOrderInput input;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable CreateReturnOrderInput input) {
      this.input = input;
      this.valueMap.put("input", input);
    }

    public @Nullable CreateReturnOrderInput input() {
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
      ResponseField.forObject("createReturnOrder", "createReturnOrder", new UnmodifiableMapBuilder<String, Object>(1)
        .put("input", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "input")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable CreateReturnOrder createReturnOrder;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable CreateReturnOrder createReturnOrder) {
      this.createReturnOrder = createReturnOrder;
    }

    /**
     * Creates a 'Return order'
     */
    public @Nullable CreateReturnOrder createReturnOrder() {
      return this.createReturnOrder;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], createReturnOrder != null ? createReturnOrder.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "createReturnOrder=" + createReturnOrder
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
        return ((this.createReturnOrder == null) ? (that.createReturnOrder == null) : this.createReturnOrder.equals(that.createReturnOrder));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (createReturnOrder == null) ? 0 : createReturnOrder.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.createReturnOrder = createReturnOrder;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final CreateReturnOrder.Mapper createReturnOrderFieldMapper = new CreateReturnOrder.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final CreateReturnOrder createReturnOrder = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<CreateReturnOrder>() {
          @Override
          public CreateReturnOrder read(ResponseReader reader) {
            return createReturnOrderFieldMapper.map(reader);
          }
        });
        return new Data(createReturnOrder);
      }
    }

    public static final class Builder {
      private @Nullable CreateReturnOrder createReturnOrder;

      Builder() {
      }

      public Builder createReturnOrder(@Nullable CreateReturnOrder createReturnOrder) {
        this.createReturnOrder = createReturnOrder;
        return this;
      }

      public Builder createReturnOrder(@Nonnull Mutator<CreateReturnOrder.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        CreateReturnOrder.Builder builder = this.createReturnOrder != null ? this.createReturnOrder.toBuilder() : CreateReturnOrder.builder();
        mutator.accept(builder);
        this.createReturnOrder = builder.build();
        return this;
      }

      public Data build() {
        return new Data(createReturnOrder);
      }
    }
  }

  public static class CreateReturnOrder {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String ref;

    final @Nonnull String status;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public CreateReturnOrder(@Nonnull String __typename, @Nonnull String ref,
        @Nonnull String status) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.status = Utils.checkNotNull(status, "status == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  External reference for `ReturnOrder`. Must be unique.
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    /**
     *  Status of the `Return Order`
     */
    public @Nonnull String status() {
      return this.status;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeString($responseFields[2], status);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "CreateReturnOrder{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "status=" + status
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof CreateReturnOrder) {
        CreateReturnOrder that = (CreateReturnOrder) o;
        return this.__typename.equals(that.__typename)
         && this.ref.equals(that.ref)
         && this.status.equals(that.status);
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
        h ^= ref.hashCode();
        h *= 1000003;
        h ^= status.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.ref = ref;
      builder.status = status;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<CreateReturnOrder> {
      @Override
      public CreateReturnOrder map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final String status = reader.readString($responseFields[2]);
        return new CreateReturnOrder(__typename, ref, status);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String ref;

      private @Nonnull String status;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder ref(@Nonnull String ref) {
        this.ref = ref;
        return this;
      }

      public Builder status(@Nonnull String status) {
        this.status = status;
        return this;
      }

      public CreateReturnOrder build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(ref, "ref == null");
        Utils.checkNotNull(status, "status == null");
        return new CreateReturnOrder(__typename, ref, status);
      }
    }
  }
}
