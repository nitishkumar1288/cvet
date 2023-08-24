package com.fluentcommerce.graphql.mutation.payment;

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
import com.fluentcommerce.graphql.type.CreateFinancialTransactionInput;
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
public final class CreateFinancialTransactionMutation implements Mutation<CreateFinancialTransactionMutation.Data, CreateFinancialTransactionMutation.Data, CreateFinancialTransactionMutation.Variables> {
  public static final String OPERATION_DEFINITION = "mutation CreateFinancialTransaction($input: CreateFinancialTransactionInput) {\n"
      + "  createFinancialTransaction(input: $input) {\n"
      + "    __typename\n"
      + "    ref\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "CreateFinancialTransaction";
    }
  };

  private final CreateFinancialTransactionMutation.Variables variables;

  public CreateFinancialTransactionMutation(@Nullable CreateFinancialTransactionInput input) {
    variables = new CreateFinancialTransactionMutation.Variables(input);
  }

  @Override
  public String operationId() {
    return "1a22b1906eae67444048c89bc0ba4aa25d1daef05572979130fe1a9d691d42c3";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public CreateFinancialTransactionMutation.Data wrapData(CreateFinancialTransactionMutation.Data data) {
    return data;
  }

  @Override
  public CreateFinancialTransactionMutation.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<CreateFinancialTransactionMutation.Data> responseFieldMapper() {
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
    private @Nullable CreateFinancialTransactionInput input;

    Builder() {
    }

    public Builder input(@Nullable CreateFinancialTransactionInput input) {
      this.input = input;
      return this;
    }

    public CreateFinancialTransactionMutation build() {
      return new CreateFinancialTransactionMutation(input);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable CreateFinancialTransactionInput input;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable CreateFinancialTransactionInput input) {
      this.input = input;
      this.valueMap.put("input", input);
    }

    public @Nullable CreateFinancialTransactionInput input() {
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
      ResponseField.forObject("createFinancialTransaction", "createFinancialTransaction", new UnmodifiableMapBuilder<String, Object>(1)
        .put("input", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "input")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable CreateFinancialTransaction createFinancialTransaction;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable CreateFinancialTransaction createFinancialTransaction) {
      this.createFinancialTransaction = createFinancialTransaction;
    }

    /**
     *  This mutation creates a `FinancialTransaction`, an orchestratable entity inside the Fluent ecosystem. If the `FinancialTransaction` is successfully created, a CREATE event will be generate associated with the mutation.<br/>A sample of the event generated:<br/>{<br/>&nbsp;&nbsp;&nbsp;&nbsp;"name": "CREATE",<br/>&nbsp;&nbsp;&nbsp;&nbsp;"type": "NORMAL",<br/>&nbsp;&nbsp;&nbsp;&nbsp;"entityRef": "FINANCIALTRANSACTION-001",<br/>&nbsp;&nbsp;&nbsp;&nbsp;"entityType": "FINANCIALTRANSACTION",<br/>&nbsp;&nbsp;&nbsp;&nbsp;"retailerId": "1",<br/>&nbsp;&nbsp;&nbsp;&nbsp;"accountId": "ACCOUNT_ID"<br/>}<br/>
     */
    public @Nullable CreateFinancialTransaction createFinancialTransaction() {
      return this.createFinancialTransaction;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], createFinancialTransaction != null ? createFinancialTransaction.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "createFinancialTransaction=" + createFinancialTransaction
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
        return ((this.createFinancialTransaction == null) ? (that.createFinancialTransaction == null) : this.createFinancialTransaction.equals(that.createFinancialTransaction));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (createFinancialTransaction == null) ? 0 : createFinancialTransaction.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.createFinancialTransaction = createFinancialTransaction;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final CreateFinancialTransaction.Mapper createFinancialTransactionFieldMapper = new CreateFinancialTransaction.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final CreateFinancialTransaction createFinancialTransaction = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<CreateFinancialTransaction>() {
          @Override
          public CreateFinancialTransaction read(ResponseReader reader) {
            return createFinancialTransactionFieldMapper.map(reader);
          }
        });
        return new Data(createFinancialTransaction);
      }
    }

    public static final class Builder {
      private @Nullable CreateFinancialTransaction createFinancialTransaction;

      Builder() {
      }

      public Builder createFinancialTransaction(@Nullable CreateFinancialTransaction createFinancialTransaction) {
        this.createFinancialTransaction = createFinancialTransaction;
        return this;
      }

      public Builder createFinancialTransaction(@Nonnull Mutator<CreateFinancialTransaction.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        CreateFinancialTransaction.Builder builder = this.createFinancialTransaction != null ? this.createFinancialTransaction.toBuilder() : CreateFinancialTransaction.builder();
        mutator.accept(builder);
        this.createFinancialTransaction = builder.build();
        return this;
      }

      public Data build() {
        return new Data(createFinancialTransaction);
      }
    }
  }

  public static class CreateFinancialTransaction {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public CreateFinancialTransaction(@Nonnull String __typename, @Nullable String ref) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The unique transaction reference provided by the Retailer to the payment gateway
     */
    public @Nullable String ref() {
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
        $toString = "CreateFinancialTransaction{"
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
      if (o instanceof CreateFinancialTransaction) {
        CreateFinancialTransaction that = (CreateFinancialTransaction) o;
        return this.__typename.equals(that.__typename)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref));
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
        h ^= (ref == null) ? 0 : ref.hashCode();
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

    public static final class Mapper implements ResponseFieldMapper<CreateFinancialTransaction> {
      @Override
      public CreateFinancialTransaction map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        return new CreateFinancialTransaction(__typename, ref);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder ref(@Nullable String ref) {
        this.ref = ref;
        return this;
      }

      public CreateFinancialTransaction build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new CreateFinancialTransaction(__typename, ref);
      }
    }
  }
}
