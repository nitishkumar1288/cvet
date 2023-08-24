package com.fluentcommerce.graphql.mutation.fulfillment;

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
import com.fluentcommerce.graphql.type.UpdateFulfilmentInput;
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
public final class UpdateFulfilmentMutation implements Mutation<UpdateFulfilmentMutation.Data, UpdateFulfilmentMutation.Data, UpdateFulfilmentMutation.Variables> {
  public static final String OPERATION_DEFINITION = "mutation UpdateFulfilment($input: UpdateFulfilmentInput) {\n"
      + "  updateFulfilment(input: $input) {\n"
      + "    __typename\n"
      + "    id\n"
      + "    status\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "UpdateFulfilment";
    }
  };

  private final UpdateFulfilmentMutation.Variables variables;

  public UpdateFulfilmentMutation(@Nullable UpdateFulfilmentInput input) {
    variables = new UpdateFulfilmentMutation.Variables(input);
  }

  @Override
  public String operationId() {
    return "7f407c06f7dacb11462b859a4e90c64c6942acf7c1b1bb1060234fa62a5d4132";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public UpdateFulfilmentMutation.Data wrapData(UpdateFulfilmentMutation.Data data) {
    return data;
  }

  @Override
  public UpdateFulfilmentMutation.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<UpdateFulfilmentMutation.Data> responseFieldMapper() {
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
    private @Nullable UpdateFulfilmentInput input;

    Builder() {
    }

    public Builder input(@Nullable UpdateFulfilmentInput input) {
      this.input = input;
      return this;
    }

    public UpdateFulfilmentMutation build() {
      return new UpdateFulfilmentMutation(input);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable UpdateFulfilmentInput input;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable UpdateFulfilmentInput input) {
      this.input = input;
      this.valueMap.put("input", input);
    }

    public @Nullable UpdateFulfilmentInput input() {
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
      ResponseField.forObject("updateFulfilment", "updateFulfilment", new UnmodifiableMapBuilder<String, Object>(1)
        .put("input", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "input")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable UpdateFulfilment updateFulfilment;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable UpdateFulfilment updateFulfilment) {
      this.updateFulfilment = updateFulfilment;
    }

    public @Nullable UpdateFulfilment updateFulfilment() {
      return this.updateFulfilment;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], updateFulfilment != null ? updateFulfilment.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "updateFulfilment=" + updateFulfilment
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
        return ((this.updateFulfilment == null) ? (that.updateFulfilment == null) : this.updateFulfilment.equals(that.updateFulfilment));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (updateFulfilment == null) ? 0 : updateFulfilment.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.updateFulfilment = updateFulfilment;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final UpdateFulfilment.Mapper updateFulfilmentFieldMapper = new UpdateFulfilment.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final UpdateFulfilment updateFulfilment = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<UpdateFulfilment>() {
          @Override
          public UpdateFulfilment read(ResponseReader reader) {
            return updateFulfilmentFieldMapper.map(reader);
          }
        });
        return new Data(updateFulfilment);
      }
    }

    public static final class Builder {
      private @Nullable UpdateFulfilment updateFulfilment;

      Builder() {
      }

      public Builder updateFulfilment(@Nullable UpdateFulfilment updateFulfilment) {
        this.updateFulfilment = updateFulfilment;
        return this;
      }

      public Builder updateFulfilment(@Nonnull Mutator<UpdateFulfilment.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        UpdateFulfilment.Builder builder = this.updateFulfilment != null ? this.updateFulfilment.toBuilder() : UpdateFulfilment.builder();
        mutator.accept(builder);
        this.updateFulfilment = builder.build();
        return this;
      }

      public Data build() {
        return new Data(updateFulfilment);
      }
    }
  }

  public static class UpdateFulfilment {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String status;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public UpdateFulfilment(@Nonnull String __typename, @Nonnull String id,
        @Nullable String status) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.status = status;
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

    /**
     *  The current status of the `Fulfilment`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
     */
    public @Nullable String status() {
      return this.status;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], status);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "UpdateFulfilment{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
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
      if (o instanceof UpdateFulfilment) {
        UpdateFulfilment that = (UpdateFulfilment) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status));
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
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.id = id;
      builder.status = status;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<UpdateFulfilment> {
      @Override
      public UpdateFulfilment map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String status = reader.readString($responseFields[2]);
        return new UpdateFulfilment(__typename, id, status);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String status;

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

      public Builder status(@Nullable String status) {
        this.status = status;
        return this;
      }

      public UpdateFulfilment build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new UpdateFulfilment(__typename, id, status);
      }
    }
  }
}
