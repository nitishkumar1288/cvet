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
import com.fluentcommerce.graphql.type.CreateCreditMemoInput;
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
public final class CreateCreditMemoMutation implements Mutation<CreateCreditMemoMutation.Data, CreateCreditMemoMutation.Data, CreateCreditMemoMutation.Variables> {
  public static final String OPERATION_DEFINITION = "mutation CreateCreditMemo($input: CreateCreditMemoInput) {\n"
      + "  createCreditMemo(input: $input) {\n"
      + "    __typename\n"
      + "    ref\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "CreateCreditMemo";
    }
  };

  private final CreateCreditMemoMutation.Variables variables;

  public CreateCreditMemoMutation(@Nullable CreateCreditMemoInput input) {
    variables = new CreateCreditMemoMutation.Variables(input);
  }

  @Override
  public String operationId() {
    return "9cbe36fb89ce144629c3fc610facaaa9002f15289c2aa025971e9e1292d2c5a0";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public CreateCreditMemoMutation.Data wrapData(CreateCreditMemoMutation.Data data) {
    return data;
  }

  @Override
  public CreateCreditMemoMutation.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<CreateCreditMemoMutation.Data> responseFieldMapper() {
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
    private @Nullable CreateCreditMemoInput input;

    Builder() {
    }

    public Builder input(@Nullable CreateCreditMemoInput input) {
      this.input = input;
      return this;
    }

    public CreateCreditMemoMutation build() {
      return new CreateCreditMemoMutation(input);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable CreateCreditMemoInput input;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable CreateCreditMemoInput input) {
      this.input = input;
      this.valueMap.put("input", input);
    }

    public @Nullable CreateCreditMemoInput input() {
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
      ResponseField.forObject("createCreditMemo", "createCreditMemo", new UnmodifiableMapBuilder<String, Object>(1)
        .put("input", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "input")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable CreateCreditMemo createCreditMemo;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable CreateCreditMemo createCreditMemo) {
      this.createCreditMemo = createCreditMemo;
    }

    /**
     * Creates a 'Credit memo'
     */
    public @Nullable CreateCreditMemo createCreditMemo() {
      return this.createCreditMemo;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], createCreditMemo != null ? createCreditMemo.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "createCreditMemo=" + createCreditMemo
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
        return ((this.createCreditMemo == null) ? (that.createCreditMemo == null) : this.createCreditMemo.equals(that.createCreditMemo));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (createCreditMemo == null) ? 0 : createCreditMemo.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.createCreditMemo = createCreditMemo;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final CreateCreditMemo.Mapper createCreditMemoFieldMapper = new CreateCreditMemo.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final CreateCreditMemo createCreditMemo = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<CreateCreditMemo>() {
          @Override
          public CreateCreditMemo read(ResponseReader reader) {
            return createCreditMemoFieldMapper.map(reader);
          }
        });
        return new Data(createCreditMemo);
      }
    }

    public static final class Builder {
      private @Nullable CreateCreditMemo createCreditMemo;

      Builder() {
      }

      public Builder createCreditMemo(@Nullable CreateCreditMemo createCreditMemo) {
        this.createCreditMemo = createCreditMemo;
        return this;
      }

      public Builder createCreditMemo(@Nonnull Mutator<CreateCreditMemo.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        CreateCreditMemo.Builder builder = this.createCreditMemo != null ? this.createCreditMemo.toBuilder() : CreateCreditMemo.builder();
        mutator.accept(builder);
        this.createCreditMemo = builder.build();
        return this;
      }

      public Data build() {
        return new Data(createCreditMemo);
      }
    }
  }

  public static class CreateCreditMemo {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public CreateCreditMemo(@Nonnull String __typename, @Nonnull String ref) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  External reference to the `CreditMemo`. Must be unique.
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "CreateCreditMemo{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof CreateCreditMemo) {
        CreateCreditMemo that = (CreateCreditMemo) o;
        return this.__typename.equals(that.__typename)
         && this.ref.equals(that.ref);
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
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.ref = ref;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<CreateCreditMemo> {
      @Override
      public CreateCreditMemo map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        return new CreateCreditMemo(__typename, ref);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String ref;

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

      public CreateCreditMemo build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(ref, "ref == null");
        return new CreateCreditMemo(__typename, ref);
      }
    }
  }
}
