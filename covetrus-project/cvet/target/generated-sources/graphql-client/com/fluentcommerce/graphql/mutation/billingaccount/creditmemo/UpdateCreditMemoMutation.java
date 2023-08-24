package com.fluentcommerce.graphql.mutation.billingaccount.creditmemo;

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
import com.fluentcommerce.graphql.type.UpdateCreditMemoInput;
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
public final class UpdateCreditMemoMutation implements Mutation<UpdateCreditMemoMutation.Data, UpdateCreditMemoMutation.Data, UpdateCreditMemoMutation.Variables> {
  public static final String OPERATION_DEFINITION = "mutation UpdateCreditMemo($input: UpdateCreditMemoInput) {\n"
      + "  updateCreditMemo(input: $input) {\n"
      + "    __typename\n"
      + "    id\n"
      + "    status\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "UpdateCreditMemo";
    }
  };

  private final UpdateCreditMemoMutation.Variables variables;

  public UpdateCreditMemoMutation(@Nullable UpdateCreditMemoInput input) {
    variables = new UpdateCreditMemoMutation.Variables(input);
  }

  @Override
  public String operationId() {
    return "43ebbe0c5ce9b4ec2b39a83367e56cb3f12dae7f773e0a69e273f9ac415ecf46";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public UpdateCreditMemoMutation.Data wrapData(UpdateCreditMemoMutation.Data data) {
    return data;
  }

  @Override
  public UpdateCreditMemoMutation.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<UpdateCreditMemoMutation.Data> responseFieldMapper() {
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
    private @Nullable UpdateCreditMemoInput input;

    Builder() {
    }

    public Builder input(@Nullable UpdateCreditMemoInput input) {
      this.input = input;
      return this;
    }

    public UpdateCreditMemoMutation build() {
      return new UpdateCreditMemoMutation(input);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable UpdateCreditMemoInput input;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable UpdateCreditMemoInput input) {
      this.input = input;
      this.valueMap.put("input", input);
    }

    public @Nullable UpdateCreditMemoInput input() {
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
      ResponseField.forObject("updateCreditMemo", "updateCreditMemo", new UnmodifiableMapBuilder<String, Object>(1)
        .put("input", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "input")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable UpdateCreditMemo updateCreditMemo;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable UpdateCreditMemo updateCreditMemo) {
      this.updateCreditMemo = updateCreditMemo;
    }

    /**
     * Updates a 'Credit memo'
     */
    public @Nullable UpdateCreditMemo updateCreditMemo() {
      return this.updateCreditMemo;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], updateCreditMemo != null ? updateCreditMemo.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "updateCreditMemo=" + updateCreditMemo
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
        return ((this.updateCreditMemo == null) ? (that.updateCreditMemo == null) : this.updateCreditMemo.equals(that.updateCreditMemo));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (updateCreditMemo == null) ? 0 : updateCreditMemo.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.updateCreditMemo = updateCreditMemo;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final UpdateCreditMemo.Mapper updateCreditMemoFieldMapper = new UpdateCreditMemo.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final UpdateCreditMemo updateCreditMemo = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<UpdateCreditMemo>() {
          @Override
          public UpdateCreditMemo read(ResponseReader reader) {
            return updateCreditMemoFieldMapper.map(reader);
          }
        });
        return new Data(updateCreditMemo);
      }
    }

    public static final class Builder {
      private @Nullable UpdateCreditMemo updateCreditMemo;

      Builder() {
      }

      public Builder updateCreditMemo(@Nullable UpdateCreditMemo updateCreditMemo) {
        this.updateCreditMemo = updateCreditMemo;
        return this;
      }

      public Builder updateCreditMemo(@Nonnull Mutator<UpdateCreditMemo.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        UpdateCreditMemo.Builder builder = this.updateCreditMemo != null ? this.updateCreditMemo.toBuilder() : UpdateCreditMemo.builder();
        mutator.accept(builder);
        this.updateCreditMemo = builder.build();
        return this;
      }

      public Data build() {
        return new Data(updateCreditMemo);
      }
    }
  }

  public static class UpdateCreditMemo {
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

    public UpdateCreditMemo(@Nonnull String __typename, @Nonnull String id,
        @Nullable String status) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.status = status;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  ID of the object.
     */
    public @Nonnull String id() {
      return this.id;
    }

    /**
     *  Status of the `CreditMemo`.
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
        $toString = "UpdateCreditMemo{"
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
      if (o instanceof UpdateCreditMemo) {
        UpdateCreditMemo that = (UpdateCreditMemo) o;
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

    public static final class Mapper implements ResponseFieldMapper<UpdateCreditMemo> {
      @Override
      public UpdateCreditMemo map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String status = reader.readString($responseFields[2]);
        return new UpdateCreditMemo(__typename, id, status);
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

      public UpdateCreditMemo build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new UpdateCreditMemo(__typename, id, status);
      }
    }
  }
}
