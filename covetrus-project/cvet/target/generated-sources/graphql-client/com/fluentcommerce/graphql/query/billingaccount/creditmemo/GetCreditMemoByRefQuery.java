package com.fluentcommerce.graphql.query.billingaccount.creditmemo;

import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.OperationName;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.api.ResponseFieldMapper;
import com.apollographql.apollo.api.ResponseFieldMarshaller;
import com.apollographql.apollo.api.ResponseReader;
import com.apollographql.apollo.api.ResponseWriter;
import com.apollographql.apollo.api.internal.Mutator;
import com.apollographql.apollo.api.internal.UnmodifiableMapBuilder;
import com.apollographql.apollo.api.internal.Utils;
import com.fluentretail.graphql.type.CustomType;
import java.io.IOException;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class GetCreditMemoByRefQuery implements Query<GetCreditMemoByRefQuery.Data, GetCreditMemoByRefQuery.Data, GetCreditMemoByRefQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query GetCreditMemoByRef($ref: String!, $includeItems: Boolean!, $returnOrderItemCount: Int, $returnOrderItemCursor: String) {\n"
      + "  creditMemo(ref: $ref) {\n"
      + "    __typename\n"
      + "    ref\n"
      + "    id\n"
      + "    attributes {\n"
      + "      __typename\n"
      + "      name\n"
      + "      value\n"
      + "      type\n"
      + "    }\n"
      + "    totalAmount {\n"
      + "      __typename\n"
      + "      amount\n"
      + "    }\n"
      + "    subTotalAmount {\n"
      + "      __typename\n"
      + "      amount\n"
      + "    }\n"
      + "    totalBalance {\n"
      + "      __typename\n"
      + "      amount\n"
      + "    }\n"
      + "    defaultTaxType {\n"
      + "      __typename\n"
      + "      tariff\n"
      + "      group\n"
      + "      country\n"
      + "    }\n"
      + "    createdOn\n"
      + "    status\n"
      + "    currency {\n"
      + "      __typename\n"
      + "      alphabeticCode\n"
      + "    }\n"
      + "    type\n"
      + "    returnOrder {\n"
      + "      __typename\n"
      + "      ref\n"
      + "      retailer {\n"
      + "        __typename\n"
      + "        id\n"
      + "      }\n"
      + "    }\n"
      + "    items(first: $returnOrderItemCount, after: $returnOrderItemCursor) @include(if: $includeItems) {\n"
      + "      __typename\n"
      + "      edges {\n"
      + "        __typename\n"
      + "        node {\n"
      + "          __typename\n"
      + "          id\n"
      + "          ref\n"
      + "          type\n"
      + "          createdOn\n"
      + "          amount {\n"
      + "            __typename\n"
      + "            amount\n"
      + "          }\n"
      + "          creditReasonCode {\n"
      + "            __typename\n"
      + "            value\n"
      + "          }\n"
      + "          description\n"
      + "          orderItem {\n"
      + "            __typename\n"
      + "            ref\n"
      + "            order {\n"
      + "              __typename\n"
      + "              ref\n"
      + "              retailer {\n"
      + "                __typename\n"
      + "                id\n"
      + "              }\n"
      + "            }\n"
      + "          }\n"
      + "          taxAmount {\n"
      + "            __typename\n"
      + "            amount\n"
      + "          }\n"
      + "          product {\n"
      + "            __typename\n"
      + "            ref\n"
      + "            catalogue {\n"
      + "              __typename\n"
      + "              ref\n"
      + "            }\n"
      + "          }\n"
      + "          unitAmount {\n"
      + "            __typename\n"
      + "            amount\n"
      + "          }\n"
      + "          unitCostAmount {\n"
      + "            __typename\n"
      + "            amount\n"
      + "          }\n"
      + "          unitQuantity {\n"
      + "            __typename\n"
      + "            quantity\n"
      + "          }\n"
      + "          unitTaxType {\n"
      + "            __typename\n"
      + "            country\n"
      + "            group\n"
      + "            tariff\n"
      + "          }\n"
      + "        }\n"
      + "      }\n"
      + "    }\n"
      + "    returnOrder {\n"
      + "      __typename\n"
      + "      ref\n"
      + "      retailer {\n"
      + "        __typename\n"
      + "        id\n"
      + "      }\n"
      + "    }\n"
      + "    order {\n"
      + "      __typename\n"
      + "      ref\n"
      + "      retailer {\n"
      + "        __typename\n"
      + "        id\n"
      + "      }\n"
      + "    }\n"
      + "    billingAccount {\n"
      + "      __typename\n"
      + "      ref\n"
      + "      id\n"
      + "    }\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "GetCreditMemoByRef";
    }
  };

  private final GetCreditMemoByRefQuery.Variables variables;

  public GetCreditMemoByRefQuery(@Nonnull String ref, boolean includeItems,
      @Nullable Integer returnOrderItemCount, @Nullable String returnOrderItemCursor) {
    Utils.checkNotNull(ref, "ref == null");
    variables = new GetCreditMemoByRefQuery.Variables(ref, includeItems, returnOrderItemCount, returnOrderItemCursor);
  }

  @Override
  public String operationId() {
    return "3623f7ef549f1ba628414ac2ec7f7f6ffc93e7ea5c019dbc9fa41d0cb519bc5d";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public GetCreditMemoByRefQuery.Data wrapData(GetCreditMemoByRefQuery.Data data) {
    return data;
  }

  @Override
  public GetCreditMemoByRefQuery.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<GetCreditMemoByRefQuery.Data> responseFieldMapper() {
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
    private @Nonnull String ref;

    private boolean includeItems;

    private @Nullable Integer returnOrderItemCount;

    private @Nullable String returnOrderItemCursor;

    Builder() {
    }

    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    public Builder includeItems(boolean includeItems) {
      this.includeItems = includeItems;
      return this;
    }

    public Builder returnOrderItemCount(@Nullable Integer returnOrderItemCount) {
      this.returnOrderItemCount = returnOrderItemCount;
      return this;
    }

    public Builder returnOrderItemCursor(@Nullable String returnOrderItemCursor) {
      this.returnOrderItemCursor = returnOrderItemCursor;
      return this;
    }

    public GetCreditMemoByRefQuery build() {
      Utils.checkNotNull(ref, "ref == null");
      return new GetCreditMemoByRefQuery(ref, includeItems, returnOrderItemCount, returnOrderItemCursor);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nonnull String ref;

    private final boolean includeItems;

    private final @Nullable Integer returnOrderItemCount;

    private final @Nullable String returnOrderItemCursor;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nonnull String ref, boolean includeItems, @Nullable Integer returnOrderItemCount,
        @Nullable String returnOrderItemCursor) {
      this.ref = ref;
      this.includeItems = includeItems;
      this.returnOrderItemCount = returnOrderItemCount;
      this.returnOrderItemCursor = returnOrderItemCursor;
      this.valueMap.put("ref", ref);
      this.valueMap.put("includeItems", includeItems);
      this.valueMap.put("returnOrderItemCount", returnOrderItemCount);
      this.valueMap.put("returnOrderItemCursor", returnOrderItemCursor);
    }

    public @Nonnull String ref() {
      return ref;
    }

    public boolean includeItems() {
      return includeItems;
    }

    public @Nullable Integer returnOrderItemCount() {
      return returnOrderItemCount;
    }

    public @Nullable String returnOrderItemCursor() {
      return returnOrderItemCursor;
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
          writer.writeString("ref", ref);
          writer.writeBoolean("includeItems", includeItems);
          writer.writeInt("returnOrderItemCount", returnOrderItemCount);
          writer.writeString("returnOrderItemCursor", returnOrderItemCursor);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("creditMemo", "creditMemo", new UnmodifiableMapBuilder<String, Object>(1)
        .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "ref")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable CreditMemo creditMemo;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable CreditMemo creditMemo) {
      this.creditMemo = creditMemo;
    }

    /**
     *  Find a CreditMemo entity
     */
    public @Nullable CreditMemo creditMemo() {
      return this.creditMemo;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], creditMemo != null ? creditMemo.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "creditMemo=" + creditMemo
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
        return ((this.creditMemo == null) ? (that.creditMemo == null) : this.creditMemo.equals(that.creditMemo));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (creditMemo == null) ? 0 : creditMemo.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.creditMemo = creditMemo;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final CreditMemo.Mapper creditMemoFieldMapper = new CreditMemo.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final CreditMemo creditMemo = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<CreditMemo>() {
          @Override
          public CreditMemo read(ResponseReader reader) {
            return creditMemoFieldMapper.map(reader);
          }
        });
        return new Data(creditMemo);
      }
    }

    public static final class Builder {
      private @Nullable CreditMemo creditMemo;

      Builder() {
      }

      public Builder creditMemo(@Nullable CreditMemo creditMemo) {
        this.creditMemo = creditMemo;
        return this;
      }

      public Builder creditMemo(@Nonnull Mutator<CreditMemo.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        CreditMemo.Builder builder = this.creditMemo != null ? this.creditMemo.toBuilder() : CreditMemo.builder();
        mutator.accept(builder);
        this.creditMemo = builder.build();
        return this;
      }

      public Data build() {
        return new Data(creditMemo);
      }
    }
  }

  public static class CreditMemo {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("totalAmount", "totalAmount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("subTotalAmount", "subTotalAmount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("totalBalance", "totalBalance", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("defaultTaxType", "defaultTaxType", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("currency", "currency", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("returnOrder", "returnOrder", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("items", "items", new UnmodifiableMapBuilder<String, Object>(2)
        .put("after", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "returnOrderItemCursor")
        .build())
        .put("first", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "returnOrderItemCount")
        .build())
      .build(), true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeItems", false))),
      ResponseField.forObject("order", "order", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("billingAccount", "billingAccount", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String ref;

    final @Nonnull String id;

    final @Nullable List<Attribute> attributes;

    final @Nullable TotalAmount totalAmount;

    final @Nullable SubTotalAmount subTotalAmount;

    final @Nullable TotalBalance totalBalance;

    final @Nullable DefaultTaxType defaultTaxType;

    final @Nullable Object createdOn;

    final @Nullable String status;

    final @Nullable Currency currency;

    final @Nullable String type;

    final @Nullable ReturnOrder returnOrder;

    final @Nullable Items items;

    final @Nullable Order1 order;

    final @Nullable BillingAccount billingAccount;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public CreditMemo(@Nonnull String __typename, @Nonnull String ref, @Nonnull String id,
        @Nullable List<Attribute> attributes, @Nullable TotalAmount totalAmount,
        @Nullable SubTotalAmount subTotalAmount, @Nullable TotalBalance totalBalance,
        @Nullable DefaultTaxType defaultTaxType, @Nullable Object createdOn,
        @Nullable String status, @Nullable Currency currency, @Nullable String type,
        @Nullable ReturnOrder returnOrder, @Nullable Items items, @Nullable Order1 order,
        @Nullable BillingAccount billingAccount) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.attributes = attributes;
      this.totalAmount = totalAmount;
      this.subTotalAmount = subTotalAmount;
      this.totalBalance = totalBalance;
      this.defaultTaxType = defaultTaxType;
      this.createdOn = createdOn;
      this.status = status;
      this.currency = currency;
      this.type = type;
      this.returnOrder = returnOrder;
      this.items = items;
      this.order = order;
      this.billingAccount = billingAccount;
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

    /**
     *  ID of the object.
     */
    public @Nonnull String id() {
      return this.id;
    }

    /**
     *  A list of attributes associated with the `CreditMemo`. This can be used to extend the existing data structure with additional data.
     */
    public @Nullable List<Attribute> attributes() {
      return this.attributes;
    }

    /**
     *  Total amount
     */
    public @Nullable TotalAmount totalAmount() {
      return this.totalAmount;
    }

    /**
     *  Sub-total amount
     */
    public @Nullable SubTotalAmount subTotalAmount() {
      return this.subTotalAmount;
    }

    /**
     *  Total balance
     */
    public @Nullable TotalBalance totalBalance() {
      return this.totalBalance;
    }

    /**
     *  Default tax type
     */
    public @Nullable DefaultTaxType defaultTaxType() {
      return this.defaultTaxType;
    }

    /**
     *  Date and time of creation.
     */
    public @Nullable Object createdOn() {
      return this.createdOn;
    }

    /**
     *  Status of the `CreditMemo`.
     */
    public @Nullable String status() {
      return this.status;
    }

    /**
     *  Reference to the currency type. Generally, the standard ISO-4217 is used.
     */
    public @Nullable Currency currency() {
      return this.currency;
    }

    /**
     *  Type of the `CreditMemo`, typically used by the Orchestration Engine to determine the workflow that should be applied.
     */
    public @Nullable String type() {
      return this.type;
    }

    /**
     *  Reference to a `ReturnOrder` associated with the `CreditMemo`.
     */
    public @Nullable ReturnOrder returnOrder() {
      return this.returnOrder;
    }

    /**
     *  The `CreditMemoItem`s associated with this `CreditMemo`.
     */
    public @Nullable Items items() {
      return this.items;
    }

    /**
     *  Reference to an `Order` associated with the `CreditMemo`.
     */
    public @Nullable Order1 order() {
      return this.order;
    }

    /**
     *  `BillingAccount` associated with the `CreditMemo`.
     */
    public @Nullable BillingAccount billingAccount() {
      return this.billingAccount;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], id);
          writer.writeList($responseFields[3], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute) value).marshaller());
            }
          });
          writer.writeObject($responseFields[4], totalAmount != null ? totalAmount.marshaller() : null);
          writer.writeObject($responseFields[5], subTotalAmount != null ? subTotalAmount.marshaller() : null);
          writer.writeObject($responseFields[6], totalBalance != null ? totalBalance.marshaller() : null);
          writer.writeObject($responseFields[7], defaultTaxType != null ? defaultTaxType.marshaller() : null);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[8], createdOn);
          writer.writeString($responseFields[9], status);
          writer.writeObject($responseFields[10], currency != null ? currency.marshaller() : null);
          writer.writeString($responseFields[11], type);
          writer.writeObject($responseFields[12], returnOrder != null ? returnOrder.marshaller() : null);
          writer.writeObject($responseFields[13], items != null ? items.marshaller() : null);
          writer.writeObject($responseFields[14], order != null ? order.marshaller() : null);
          writer.writeObject($responseFields[15], billingAccount != null ? billingAccount.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "CreditMemo{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "id=" + id + ", "
          + "attributes=" + attributes + ", "
          + "totalAmount=" + totalAmount + ", "
          + "subTotalAmount=" + subTotalAmount + ", "
          + "totalBalance=" + totalBalance + ", "
          + "defaultTaxType=" + defaultTaxType + ", "
          + "createdOn=" + createdOn + ", "
          + "status=" + status + ", "
          + "currency=" + currency + ", "
          + "type=" + type + ", "
          + "returnOrder=" + returnOrder + ", "
          + "items=" + items + ", "
          + "order=" + order + ", "
          + "billingAccount=" + billingAccount
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof CreditMemo) {
        CreditMemo that = (CreditMemo) o;
        return this.__typename.equals(that.__typename)
         && this.ref.equals(that.ref)
         && this.id.equals(that.id)
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes))
         && ((this.totalAmount == null) ? (that.totalAmount == null) : this.totalAmount.equals(that.totalAmount))
         && ((this.subTotalAmount == null) ? (that.subTotalAmount == null) : this.subTotalAmount.equals(that.subTotalAmount))
         && ((this.totalBalance == null) ? (that.totalBalance == null) : this.totalBalance.equals(that.totalBalance))
         && ((this.defaultTaxType == null) ? (that.defaultTaxType == null) : this.defaultTaxType.equals(that.defaultTaxType))
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.currency == null) ? (that.currency == null) : this.currency.equals(that.currency))
         && ((this.type == null) ? (that.type == null) : this.type.equals(that.type))
         && ((this.returnOrder == null) ? (that.returnOrder == null) : this.returnOrder.equals(that.returnOrder))
         && ((this.items == null) ? (that.items == null) : this.items.equals(that.items))
         && ((this.order == null) ? (that.order == null) : this.order.equals(that.order))
         && ((this.billingAccount == null) ? (that.billingAccount == null) : this.billingAccount.equals(that.billingAccount));
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
        h ^= id.hashCode();
        h *= 1000003;
        h ^= (attributes == null) ? 0 : attributes.hashCode();
        h *= 1000003;
        h ^= (totalAmount == null) ? 0 : totalAmount.hashCode();
        h *= 1000003;
        h ^= (subTotalAmount == null) ? 0 : subTotalAmount.hashCode();
        h *= 1000003;
        h ^= (totalBalance == null) ? 0 : totalBalance.hashCode();
        h *= 1000003;
        h ^= (defaultTaxType == null) ? 0 : defaultTaxType.hashCode();
        h *= 1000003;
        h ^= (createdOn == null) ? 0 : createdOn.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        h *= 1000003;
        h ^= (currency == null) ? 0 : currency.hashCode();
        h *= 1000003;
        h ^= (type == null) ? 0 : type.hashCode();
        h *= 1000003;
        h ^= (returnOrder == null) ? 0 : returnOrder.hashCode();
        h *= 1000003;
        h ^= (items == null) ? 0 : items.hashCode();
        h *= 1000003;
        h ^= (order == null) ? 0 : order.hashCode();
        h *= 1000003;
        h ^= (billingAccount == null) ? 0 : billingAccount.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.ref = ref;
      builder.id = id;
      builder.attributes = attributes;
      builder.totalAmount = totalAmount;
      builder.subTotalAmount = subTotalAmount;
      builder.totalBalance = totalBalance;
      builder.defaultTaxType = defaultTaxType;
      builder.createdOn = createdOn;
      builder.status = status;
      builder.currency = currency;
      builder.type = type;
      builder.returnOrder = returnOrder;
      builder.items = items;
      builder.order = order;
      builder.billingAccount = billingAccount;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<CreditMemo> {
      final Attribute.Mapper attributeFieldMapper = new Attribute.Mapper();

      final TotalAmount.Mapper totalAmountFieldMapper = new TotalAmount.Mapper();

      final SubTotalAmount.Mapper subTotalAmountFieldMapper = new SubTotalAmount.Mapper();

      final TotalBalance.Mapper totalBalanceFieldMapper = new TotalBalance.Mapper();

      final DefaultTaxType.Mapper defaultTaxTypeFieldMapper = new DefaultTaxType.Mapper();

      final Currency.Mapper currencyFieldMapper = new Currency.Mapper();

      final ReturnOrder.Mapper returnOrderFieldMapper = new ReturnOrder.Mapper();

      final Items.Mapper itemsFieldMapper = new Items.Mapper();

      final Order1.Mapper order1FieldMapper = new Order1.Mapper();

      final BillingAccount.Mapper billingAccountFieldMapper = new BillingAccount.Mapper();

      @Override
      public CreditMemo map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        final List<Attribute> attributes = reader.readList($responseFields[3], new ResponseReader.ListReader<Attribute>() {
          @Override
          public Attribute read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<Attribute>() {
              @Override
              public Attribute read(ResponseReader reader) {
                return attributeFieldMapper.map(reader);
              }
            });
          }
        });
        final TotalAmount totalAmount = reader.readObject($responseFields[4], new ResponseReader.ObjectReader<TotalAmount>() {
          @Override
          public TotalAmount read(ResponseReader reader) {
            return totalAmountFieldMapper.map(reader);
          }
        });
        final SubTotalAmount subTotalAmount = reader.readObject($responseFields[5], new ResponseReader.ObjectReader<SubTotalAmount>() {
          @Override
          public SubTotalAmount read(ResponseReader reader) {
            return subTotalAmountFieldMapper.map(reader);
          }
        });
        final TotalBalance totalBalance = reader.readObject($responseFields[6], new ResponseReader.ObjectReader<TotalBalance>() {
          @Override
          public TotalBalance read(ResponseReader reader) {
            return totalBalanceFieldMapper.map(reader);
          }
        });
        final DefaultTaxType defaultTaxType = reader.readObject($responseFields[7], new ResponseReader.ObjectReader<DefaultTaxType>() {
          @Override
          public DefaultTaxType read(ResponseReader reader) {
            return defaultTaxTypeFieldMapper.map(reader);
          }
        });
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[8]);
        final String status = reader.readString($responseFields[9]);
        final Currency currency = reader.readObject($responseFields[10], new ResponseReader.ObjectReader<Currency>() {
          @Override
          public Currency read(ResponseReader reader) {
            return currencyFieldMapper.map(reader);
          }
        });
        final String type = reader.readString($responseFields[11]);
        final ReturnOrder returnOrder = reader.readObject($responseFields[12], new ResponseReader.ObjectReader<ReturnOrder>() {
          @Override
          public ReturnOrder read(ResponseReader reader) {
            return returnOrderFieldMapper.map(reader);
          }
        });
        final Items items = reader.readObject($responseFields[13], new ResponseReader.ObjectReader<Items>() {
          @Override
          public Items read(ResponseReader reader) {
            return itemsFieldMapper.map(reader);
          }
        });
        final Order1 order = reader.readObject($responseFields[14], new ResponseReader.ObjectReader<Order1>() {
          @Override
          public Order1 read(ResponseReader reader) {
            return order1FieldMapper.map(reader);
          }
        });
        final BillingAccount billingAccount = reader.readObject($responseFields[15], new ResponseReader.ObjectReader<BillingAccount>() {
          @Override
          public BillingAccount read(ResponseReader reader) {
            return billingAccountFieldMapper.map(reader);
          }
        });
        return new CreditMemo(__typename, ref, id, attributes, totalAmount, subTotalAmount, totalBalance, defaultTaxType, createdOn, status, currency, type, returnOrder, items, order, billingAccount);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String ref;

      private @Nonnull String id;

      private @Nullable List<Attribute> attributes;

      private @Nullable TotalAmount totalAmount;

      private @Nullable SubTotalAmount subTotalAmount;

      private @Nullable TotalBalance totalBalance;

      private @Nullable DefaultTaxType defaultTaxType;

      private @Nullable Object createdOn;

      private @Nullable String status;

      private @Nullable Currency currency;

      private @Nullable String type;

      private @Nullable ReturnOrder returnOrder;

      private @Nullable Items items;

      private @Nullable Order1 order;

      private @Nullable BillingAccount billingAccount;

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

      public Builder id(@Nonnull String id) {
        this.id = id;
        return this;
      }

      public Builder attributes(@Nullable List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
      }

      public Builder totalAmount(@Nullable TotalAmount totalAmount) {
        this.totalAmount = totalAmount;
        return this;
      }

      public Builder subTotalAmount(@Nullable SubTotalAmount subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
        return this;
      }

      public Builder totalBalance(@Nullable TotalBalance totalBalance) {
        this.totalBalance = totalBalance;
        return this;
      }

      public Builder defaultTaxType(@Nullable DefaultTaxType defaultTaxType) {
        this.defaultTaxType = defaultTaxType;
        return this;
      }

      public Builder createdOn(@Nullable Object createdOn) {
        this.createdOn = createdOn;
        return this;
      }

      public Builder status(@Nullable String status) {
        this.status = status;
        return this;
      }

      public Builder currency(@Nullable Currency currency) {
        this.currency = currency;
        return this;
      }

      public Builder type(@Nullable String type) {
        this.type = type;
        return this;
      }

      public Builder returnOrder(@Nullable ReturnOrder returnOrder) {
        this.returnOrder = returnOrder;
        return this;
      }

      public Builder items(@Nullable Items items) {
        this.items = items;
        return this;
      }

      public Builder order(@Nullable Order1 order) {
        this.order = order;
        return this;
      }

      public Builder billingAccount(@Nullable BillingAccount billingAccount) {
        this.billingAccount = billingAccount;
        return this;
      }

      public Builder attributes(@Nonnull Mutator<List<Attribute.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<Attribute.Builder> builders = new ArrayList<>();
        if (this.attributes != null) {
          for (Attribute item : this.attributes) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<Attribute> attributes = new ArrayList<>();
        for (Attribute.Builder item : builders) {
          attributes.add(item != null ? item.build() : null);
        }
        this.attributes = attributes;
        return this;
      }

      public Builder totalAmount(@Nonnull Mutator<TotalAmount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        TotalAmount.Builder builder = this.totalAmount != null ? this.totalAmount.toBuilder() : TotalAmount.builder();
        mutator.accept(builder);
        this.totalAmount = builder.build();
        return this;
      }

      public Builder subTotalAmount(@Nonnull Mutator<SubTotalAmount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        SubTotalAmount.Builder builder = this.subTotalAmount != null ? this.subTotalAmount.toBuilder() : SubTotalAmount.builder();
        mutator.accept(builder);
        this.subTotalAmount = builder.build();
        return this;
      }

      public Builder totalBalance(@Nonnull Mutator<TotalBalance.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        TotalBalance.Builder builder = this.totalBalance != null ? this.totalBalance.toBuilder() : TotalBalance.builder();
        mutator.accept(builder);
        this.totalBalance = builder.build();
        return this;
      }

      public Builder defaultTaxType(@Nonnull Mutator<DefaultTaxType.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        DefaultTaxType.Builder builder = this.defaultTaxType != null ? this.defaultTaxType.toBuilder() : DefaultTaxType.builder();
        mutator.accept(builder);
        this.defaultTaxType = builder.build();
        return this;
      }

      public Builder currency(@Nonnull Mutator<Currency.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Currency.Builder builder = this.currency != null ? this.currency.toBuilder() : Currency.builder();
        mutator.accept(builder);
        this.currency = builder.build();
        return this;
      }

      public Builder returnOrder(@Nonnull Mutator<ReturnOrder.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ReturnOrder.Builder builder = this.returnOrder != null ? this.returnOrder.toBuilder() : ReturnOrder.builder();
        mutator.accept(builder);
        this.returnOrder = builder.build();
        return this;
      }

      public Builder items(@Nonnull Mutator<Items.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Items.Builder builder = this.items != null ? this.items.toBuilder() : Items.builder();
        mutator.accept(builder);
        this.items = builder.build();
        return this;
      }

      public Builder order(@Nonnull Mutator<Order1.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Order1.Builder builder = this.order != null ? this.order.toBuilder() : Order1.builder();
        mutator.accept(builder);
        this.order = builder.build();
        return this;
      }

      public Builder billingAccount(@Nonnull Mutator<BillingAccount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        BillingAccount.Builder builder = this.billingAccount != null ? this.billingAccount.toBuilder() : BillingAccount.builder();
        mutator.accept(builder);
        this.billingAccount = builder.build();
        return this;
      }

      public CreditMemo build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(ref, "ref == null");
        Utils.checkNotNull(id, "id == null");
        return new CreditMemo(__typename, ref, id, attributes, totalAmount, subTotalAmount, totalBalance, defaultTaxType, createdOn, status, currency, type, returnOrder, items, order, billingAccount);
      }
    }
  }

  public static class Attribute {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("value", "value", null, false, CustomType.JSON, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String name;

    final @Nonnull Object value;

    final @Nonnull String type;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Attribute(@Nonnull String __typename, @Nonnull String name, @Nonnull Object value,
        @Nonnull String type) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.name = Utils.checkNotNull(name, "name == null");
      this.value = Utils.checkNotNull(value, "value == null");
      this.type = Utils.checkNotNull(type, "type == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  Name of the `attribute`
     */
    public @Nonnull String name() {
      return this.name;
    }

    /**
     *  Value of the `attribute`
     */
    public @Nonnull Object value() {
      return this.value;
    }

    /**
     *  Type of the attribute's `value`. This is a free string and can be used by the client to interpret and cast the `value` into the appropriate type.
     */
    public @Nonnull String type() {
      return this.type;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], name);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], value);
          writer.writeString($responseFields[3], type);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Attribute{"
          + "__typename=" + __typename + ", "
          + "name=" + name + ", "
          + "value=" + value + ", "
          + "type=" + type
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Attribute) {
        Attribute that = (Attribute) o;
        return this.__typename.equals(that.__typename)
         && this.name.equals(that.name)
         && this.value.equals(that.value)
         && this.type.equals(that.type);
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
        h ^= name.hashCode();
        h *= 1000003;
        h ^= value.hashCode();
        h *= 1000003;
        h ^= type.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.name = name;
      builder.value = value;
      builder.type = type;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Attribute> {
      @Override
      public Attribute map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String name = reader.readString($responseFields[1]);
        final Object value = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        return new Attribute(__typename, name, value, type);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String name;

      private @Nonnull Object value;

      private @Nonnull String type;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder name(@Nonnull String name) {
        this.name = name;
        return this;
      }

      public Builder value(@Nonnull Object value) {
        this.value = value;
        return this;
      }

      public Builder type(@Nonnull String type) {
        this.type = type;
        return this;
      }

      public Attribute build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(value, "value == null");
        Utils.checkNotNull(type, "type == null");
        return new Attribute(__typename, name, value, type);
      }
    }
  }

  public static class TotalAmount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public TotalAmount(@Nonnull String __typename, @Nullable Double amount) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "TotalAmount{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof TotalAmount) {
        TotalAmount that = (TotalAmount) o;
        return this.__typename.equals(that.__typename)
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount));
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
        h ^= (amount == null) ? 0 : amount.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<TotalAmount> {
      @Override
      public TotalAmount map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Double amount = reader.readDouble($responseFields[1]);
        return new TotalAmount(__typename, amount);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder amount(@Nullable Double amount) {
        this.amount = amount;
        return this;
      }

      public TotalAmount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new TotalAmount(__typename, amount);
      }
    }
  }

  public static class SubTotalAmount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public SubTotalAmount(@Nonnull String __typename, @Nullable Double amount) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "SubTotalAmount{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof SubTotalAmount) {
        SubTotalAmount that = (SubTotalAmount) o;
        return this.__typename.equals(that.__typename)
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount));
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
        h ^= (amount == null) ? 0 : amount.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<SubTotalAmount> {
      @Override
      public SubTotalAmount map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Double amount = reader.readDouble($responseFields[1]);
        return new SubTotalAmount(__typename, amount);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder amount(@Nullable Double amount) {
        this.amount = amount;
        return this;
      }

      public SubTotalAmount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new SubTotalAmount(__typename, amount);
      }
    }
  }

  public static class TotalBalance {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public TotalBalance(@Nonnull String __typename, @Nullable Double amount) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "TotalBalance{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof TotalBalance) {
        TotalBalance that = (TotalBalance) o;
        return this.__typename.equals(that.__typename)
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount));
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
        h ^= (amount == null) ? 0 : amount.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<TotalBalance> {
      @Override
      public TotalBalance map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Double amount = reader.readDouble($responseFields[1]);
        return new TotalBalance(__typename, amount);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder amount(@Nullable Double amount) {
        this.amount = amount;
        return this;
      }

      public TotalBalance build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new TotalBalance(__typename, amount);
      }
    }
  }

  public static class DefaultTaxType {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("tariff", "tariff", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("group", "group", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("country", "country", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String tariff;

    final @Nonnull String group;

    final @Nonnull String country;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public DefaultTaxType(@Nonnull String __typename, @Nullable String tariff,
        @Nonnull String group, @Nonnull String country) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.tariff = tariff;
      this.group = Utils.checkNotNull(group, "group == null");
      this.country = Utils.checkNotNull(country, "country == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The tariff of the Tax Type
     */
    public @Nullable String tariff() {
      return this.tariff;
    }

    /**
     *  A group field which can be used to further identify the Tax Tariff applicable
     */
    public @Nonnull String group() {
      return this.group;
    }

    /**
     *  The country in which this Tax Type applies
     */
    public @Nonnull String country() {
      return this.country;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], tariff);
          writer.writeString($responseFields[2], group);
          writer.writeString($responseFields[3], country);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "DefaultTaxType{"
          + "__typename=" + __typename + ", "
          + "tariff=" + tariff + ", "
          + "group=" + group + ", "
          + "country=" + country
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof DefaultTaxType) {
        DefaultTaxType that = (DefaultTaxType) o;
        return this.__typename.equals(that.__typename)
         && ((this.tariff == null) ? (that.tariff == null) : this.tariff.equals(that.tariff))
         && this.group.equals(that.group)
         && this.country.equals(that.country);
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
        h ^= (tariff == null) ? 0 : tariff.hashCode();
        h *= 1000003;
        h ^= group.hashCode();
        h *= 1000003;
        h ^= country.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.tariff = tariff;
      builder.group = group;
      builder.country = country;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<DefaultTaxType> {
      @Override
      public DefaultTaxType map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String tariff = reader.readString($responseFields[1]);
        final String group = reader.readString($responseFields[2]);
        final String country = reader.readString($responseFields[3]);
        return new DefaultTaxType(__typename, tariff, group, country);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String tariff;

      private @Nonnull String group;

      private @Nonnull String country;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder tariff(@Nullable String tariff) {
        this.tariff = tariff;
        return this;
      }

      public Builder group(@Nonnull String group) {
        this.group = group;
        return this;
      }

      public Builder country(@Nonnull String country) {
        this.country = country;
        return this;
      }

      public DefaultTaxType build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(group, "group == null");
        Utils.checkNotNull(country, "country == null");
        return new DefaultTaxType(__typename, tariff, group, country);
      }
    }
  }

  public static class Currency {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("alphabeticCode", "alphabeticCode", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String alphabeticCode;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Currency(@Nonnull String __typename, @Nullable String alphabeticCode) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.alphabeticCode = alphabeticCode;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String alphabeticCode() {
      return this.alphabeticCode;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], alphabeticCode);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Currency{"
          + "__typename=" + __typename + ", "
          + "alphabeticCode=" + alphabeticCode
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Currency) {
        Currency that = (Currency) o;
        return this.__typename.equals(that.__typename)
         && ((this.alphabeticCode == null) ? (that.alphabeticCode == null) : this.alphabeticCode.equals(that.alphabeticCode));
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
        h ^= (alphabeticCode == null) ? 0 : alphabeticCode.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.alphabeticCode = alphabeticCode;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Currency> {
      @Override
      public Currency map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String alphabeticCode = reader.readString($responseFields[1]);
        return new Currency(__typename, alphabeticCode);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String alphabeticCode;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder alphabeticCode(@Nullable String alphabeticCode) {
        this.alphabeticCode = alphabeticCode;
        return this;
      }

      public Currency build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Currency(__typename, alphabeticCode);
      }
    }
  }

  public static class ReturnOrder {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("retailer", "retailer", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    final @Nullable Retailer retailer;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ReturnOrder(@Nonnull String __typename, @Nullable String ref,
        @Nullable Retailer retailer) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
      this.retailer = retailer;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String ref() {
      return this.ref;
    }

    public @Nullable Retailer retailer() {
      return this.retailer;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeObject($responseFields[2], retailer != null ? retailer.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ReturnOrder{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "retailer=" + retailer
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof ReturnOrder) {
        ReturnOrder that = (ReturnOrder) o;
        return this.__typename.equals(that.__typename)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && ((this.retailer == null) ? (that.retailer == null) : this.retailer.equals(that.retailer));
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
        h *= 1000003;
        h ^= (retailer == null) ? 0 : retailer.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.ref = ref;
      builder.retailer = retailer;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<ReturnOrder> {
      final Retailer.Mapper retailerFieldMapper = new Retailer.Mapper();

      @Override
      public ReturnOrder map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final Retailer retailer = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<Retailer>() {
          @Override
          public Retailer read(ResponseReader reader) {
            return retailerFieldMapper.map(reader);
          }
        });
        return new ReturnOrder(__typename, ref, retailer);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      private @Nullable Retailer retailer;

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

      public Builder retailer(@Nullable Retailer retailer) {
        this.retailer = retailer;
        return this;
      }

      public Builder retailer(@Nonnull Mutator<Retailer.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Retailer.Builder builder = this.retailer != null ? this.retailer.toBuilder() : Retailer.builder();
        mutator.accept(builder);
        this.retailer = builder.build();
        return this;
      }

      public ReturnOrder build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new ReturnOrder(__typename, ref, retailer);
      }
    }
  }

  public static class Retailer {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, true, CustomType.ID, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String id;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Retailer(@Nonnull String __typename, @Nullable String id) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = id;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String id() {
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
        $toString = "Retailer{"
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
      if (o instanceof Retailer) {
        Retailer that = (Retailer) o;
        return this.__typename.equals(that.__typename)
         && ((this.id == null) ? (that.id == null) : this.id.equals(that.id));
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
        h ^= (id == null) ? 0 : id.hashCode();
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

    public static final class Mapper implements ResponseFieldMapper<Retailer> {
      @Override
      public Retailer map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        return new Retailer(__typename, id);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String id;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder id(@Nullable String id) {
        this.id = id;
        return this;
      }

      public Retailer build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Retailer(__typename, id);
      }
    }
  }

  public static class Items {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("edges", "edges", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<Edge> edges;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Items(@Nonnull String __typename, @Nullable List<Edge> edges) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.edges = edges;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to CreditMemoItem type node
     */
    public @Nullable List<Edge> edges() {
      return this.edges;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeList($responseFields[1], edges, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Edge) value).marshaller());
            }
          });
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Items{"
          + "__typename=" + __typename + ", "
          + "edges=" + edges
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Items) {
        Items that = (Items) o;
        return this.__typename.equals(that.__typename)
         && ((this.edges == null) ? (that.edges == null) : this.edges.equals(that.edges));
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
        h ^= (edges == null) ? 0 : edges.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.edges = edges;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Items> {
      final Edge.Mapper edgeFieldMapper = new Edge.Mapper();

      @Override
      public Items map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final List<Edge> edges = reader.readList($responseFields[1], new ResponseReader.ListReader<Edge>() {
          @Override
          public Edge read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<Edge>() {
              @Override
              public Edge read(ResponseReader reader) {
                return edgeFieldMapper.map(reader);
              }
            });
          }
        });
        return new Items(__typename, edges);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable List<Edge> edges;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder edges(@Nullable List<Edge> edges) {
        this.edges = edges;
        return this;
      }

      public Builder edges(@Nonnull Mutator<List<Edge.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<Edge.Builder> builders = new ArrayList<>();
        if (this.edges != null) {
          for (Edge item : this.edges) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<Edge> edges = new ArrayList<>();
        for (Edge.Builder item : builders) {
          edges.add(item != null ? item.build() : null);
        }
        this.edges = edges;
        return this;
      }

      public Items build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Items(__typename, edges);
      }
    }
  }

  public static class Edge {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("node", "node", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Node node;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Edge(@Nonnull String __typename, @Nullable Node node) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.node = node;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The item at the end of the CreditMemoItem edge
     */
    public @Nullable Node node() {
      return this.node;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeObject($responseFields[1], node != null ? node.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Edge{"
          + "__typename=" + __typename + ", "
          + "node=" + node
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Edge) {
        Edge that = (Edge) o;
        return this.__typename.equals(that.__typename)
         && ((this.node == null) ? (that.node == null) : this.node.equals(that.node));
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
        h ^= (node == null) ? 0 : node.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.node = node;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Edge> {
      final Node.Mapper nodeFieldMapper = new Node.Mapper();

      @Override
      public Edge map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Node node = reader.readObject($responseFields[1], new ResponseReader.ObjectReader<Node>() {
          @Override
          public Node read(ResponseReader reader) {
            return nodeFieldMapper.map(reader);
          }
        });
        return new Edge(__typename, node);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Node node;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder node(@Nullable Node node) {
        this.node = node;
        return this;
      }

      public Builder node(@Nonnull Mutator<Node.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Node.Builder builder = this.node != null ? this.node.toBuilder() : Node.builder();
        mutator.accept(builder);
        this.node = builder.build();
        return this;
      }

      public Edge build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Edge(__typename, node);
      }
    }
  }

  public static class Node {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("creditReasonCode", "creditReasonCode", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("description", "description", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("orderItem", "orderItem", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("taxAmount", "taxAmount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("product", "product", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("unitAmount", "unitAmount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("unitCostAmount", "unitCostAmount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("unitQuantity", "unitQuantity", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("unitTaxType", "unitTaxType", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nonnull String ref;

    final @Nullable String type;

    final @Nullable Object createdOn;

    final @Nullable Amount amount;

    final @Nullable CreditReasonCode creditReasonCode;

    final @Nullable String description;

    final @Nullable OrderItem orderItem;

    final @Nullable TaxAmount taxAmount;

    final @Nullable Product product;

    final @Nullable UnitAmount unitAmount;

    final @Nullable UnitCostAmount unitCostAmount;

    final @Nullable UnitQuantity unitQuantity;

    final @Nullable UnitTaxType unitTaxType;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Node(@Nonnull String __typename, @Nonnull String id, @Nonnull String ref,
        @Nullable String type, @Nullable Object createdOn, @Nullable Amount amount,
        @Nullable CreditReasonCode creditReasonCode, @Nullable String description,
        @Nullable OrderItem orderItem, @Nullable TaxAmount taxAmount, @Nullable Product product,
        @Nullable UnitAmount unitAmount, @Nullable UnitCostAmount unitCostAmount,
        @Nullable UnitQuantity unitQuantity, @Nullable UnitTaxType unitTaxType) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.type = type;
      this.createdOn = createdOn;
      this.amount = amount;
      this.creditReasonCode = creditReasonCode;
      this.description = description;
      this.orderItem = orderItem;
      this.taxAmount = taxAmount;
      this.product = product;
      this.unitAmount = unitAmount;
      this.unitCostAmount = unitCostAmount;
      this.unitQuantity = unitQuantity;
      this.unitTaxType = unitTaxType;
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
     *  External reference to the `CreditMemoItem`. Must be unique.
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    /**
     *  Type of the `CreditMemoItem`, typically used by the Orchestration Engine to determine the workflow that should be applied.
     */
    public @Nullable String type() {
      return this.type;
    }

    /**
     *  Date and time of creation.
     */
    public @Nullable Object createdOn() {
      return this.createdOn;
    }

    /**
     *  The item amount for this item excluding tax. This is a calculated value based on business rules that does not necessarily have to take into account the unit quantity or amounts.
     */
    public @Nullable Amount amount() {
      return this.amount;
    }

    /**
     *  Credit reason code of the `CreditMemoItem`.
     */
    public @Nullable CreditReasonCode creditReasonCode() {
      return this.creditReasonCode;
    }

    /**
     * # Description of the `CreditMemoItem`.
     */
    public @Nullable String description() {
      return this.description;
    }

    /**
     *  Reference to an `OrderItem` associated with the `CreditMemoItem`.
     */
    public @Nullable OrderItem orderItem() {
      return this.orderItem;
    }

    /**
     *  The tax amount for this item. If not present at the item level, tax amount should be generated based on the tax type set at the invoice parent level.
     */
    public @Nullable TaxAmount taxAmount() {
      return this.taxAmount;
    }

    /**
     *  Reference to a `Product` associated with the `CreditMemoItem`.
     */
    public @Nullable Product product() {
      return this.product;
    }

    /**
     *  The unit sale price at time of sale or exchange
     */
    public @Nullable UnitAmount unitAmount() {
      return this.unitAmount;
    }

    /**
     *  The unit cost price at time of sale or exchange.
     */
    public @Nullable UnitCostAmount unitCostAmount() {
      return this.unitCostAmount;
    }

    /**
     *  `unitQuantity` holds separately the amount and the unit associated.
     */
    public @Nullable UnitQuantity unitQuantity() {
      return this.unitQuantity;
    }

    /**
     *  The tax type of this item. Should only be provided if different to the default credit memo tax type.
     */
    public @Nullable UnitTaxType unitTaxType() {
      return this.unitTaxType;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeString($responseFields[3], type);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[4], createdOn);
          writer.writeObject($responseFields[5], amount != null ? amount.marshaller() : null);
          writer.writeObject($responseFields[6], creditReasonCode != null ? creditReasonCode.marshaller() : null);
          writer.writeString($responseFields[7], description);
          writer.writeObject($responseFields[8], orderItem != null ? orderItem.marshaller() : null);
          writer.writeObject($responseFields[9], taxAmount != null ? taxAmount.marshaller() : null);
          writer.writeObject($responseFields[10], product != null ? product.marshaller() : null);
          writer.writeObject($responseFields[11], unitAmount != null ? unitAmount.marshaller() : null);
          writer.writeObject($responseFields[12], unitCostAmount != null ? unitCostAmount.marshaller() : null);
          writer.writeObject($responseFields[13], unitQuantity != null ? unitQuantity.marshaller() : null);
          writer.writeObject($responseFields[14], unitTaxType != null ? unitTaxType.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Node{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "type=" + type + ", "
          + "createdOn=" + createdOn + ", "
          + "amount=" + amount + ", "
          + "creditReasonCode=" + creditReasonCode + ", "
          + "description=" + description + ", "
          + "orderItem=" + orderItem + ", "
          + "taxAmount=" + taxAmount + ", "
          + "product=" + product + ", "
          + "unitAmount=" + unitAmount + ", "
          + "unitCostAmount=" + unitCostAmount + ", "
          + "unitQuantity=" + unitQuantity + ", "
          + "unitTaxType=" + unitTaxType
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Node) {
        Node that = (Node) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && this.ref.equals(that.ref)
         && ((this.type == null) ? (that.type == null) : this.type.equals(that.type))
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount))
         && ((this.creditReasonCode == null) ? (that.creditReasonCode == null) : this.creditReasonCode.equals(that.creditReasonCode))
         && ((this.description == null) ? (that.description == null) : this.description.equals(that.description))
         && ((this.orderItem == null) ? (that.orderItem == null) : this.orderItem.equals(that.orderItem))
         && ((this.taxAmount == null) ? (that.taxAmount == null) : this.taxAmount.equals(that.taxAmount))
         && ((this.product == null) ? (that.product == null) : this.product.equals(that.product))
         && ((this.unitAmount == null) ? (that.unitAmount == null) : this.unitAmount.equals(that.unitAmount))
         && ((this.unitCostAmount == null) ? (that.unitCostAmount == null) : this.unitCostAmount.equals(that.unitCostAmount))
         && ((this.unitQuantity == null) ? (that.unitQuantity == null) : this.unitQuantity.equals(that.unitQuantity))
         && ((this.unitTaxType == null) ? (that.unitTaxType == null) : this.unitTaxType.equals(that.unitTaxType));
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
        h ^= ref.hashCode();
        h *= 1000003;
        h ^= (type == null) ? 0 : type.hashCode();
        h *= 1000003;
        h ^= (createdOn == null) ? 0 : createdOn.hashCode();
        h *= 1000003;
        h ^= (amount == null) ? 0 : amount.hashCode();
        h *= 1000003;
        h ^= (creditReasonCode == null) ? 0 : creditReasonCode.hashCode();
        h *= 1000003;
        h ^= (description == null) ? 0 : description.hashCode();
        h *= 1000003;
        h ^= (orderItem == null) ? 0 : orderItem.hashCode();
        h *= 1000003;
        h ^= (taxAmount == null) ? 0 : taxAmount.hashCode();
        h *= 1000003;
        h ^= (product == null) ? 0 : product.hashCode();
        h *= 1000003;
        h ^= (unitAmount == null) ? 0 : unitAmount.hashCode();
        h *= 1000003;
        h ^= (unitCostAmount == null) ? 0 : unitCostAmount.hashCode();
        h *= 1000003;
        h ^= (unitQuantity == null) ? 0 : unitQuantity.hashCode();
        h *= 1000003;
        h ^= (unitTaxType == null) ? 0 : unitTaxType.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.id = id;
      builder.ref = ref;
      builder.type = type;
      builder.createdOn = createdOn;
      builder.amount = amount;
      builder.creditReasonCode = creditReasonCode;
      builder.description = description;
      builder.orderItem = orderItem;
      builder.taxAmount = taxAmount;
      builder.product = product;
      builder.unitAmount = unitAmount;
      builder.unitCostAmount = unitCostAmount;
      builder.unitQuantity = unitQuantity;
      builder.unitTaxType = unitTaxType;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Node> {
      final Amount.Mapper amountFieldMapper = new Amount.Mapper();

      final CreditReasonCode.Mapper creditReasonCodeFieldMapper = new CreditReasonCode.Mapper();

      final OrderItem.Mapper orderItemFieldMapper = new OrderItem.Mapper();

      final TaxAmount.Mapper taxAmountFieldMapper = new TaxAmount.Mapper();

      final Product.Mapper productFieldMapper = new Product.Mapper();

      final UnitAmount.Mapper unitAmountFieldMapper = new UnitAmount.Mapper();

      final UnitCostAmount.Mapper unitCostAmountFieldMapper = new UnitCostAmount.Mapper();

      final UnitQuantity.Mapper unitQuantityFieldMapper = new UnitQuantity.Mapper();

      final UnitTaxType.Mapper unitTaxTypeFieldMapper = new UnitTaxType.Mapper();

      @Override
      public Node map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[4]);
        final Amount amount = reader.readObject($responseFields[5], new ResponseReader.ObjectReader<Amount>() {
          @Override
          public Amount read(ResponseReader reader) {
            return amountFieldMapper.map(reader);
          }
        });
        final CreditReasonCode creditReasonCode = reader.readObject($responseFields[6], new ResponseReader.ObjectReader<CreditReasonCode>() {
          @Override
          public CreditReasonCode read(ResponseReader reader) {
            return creditReasonCodeFieldMapper.map(reader);
          }
        });
        final String description = reader.readString($responseFields[7]);
        final OrderItem orderItem = reader.readObject($responseFields[8], new ResponseReader.ObjectReader<OrderItem>() {
          @Override
          public OrderItem read(ResponseReader reader) {
            return orderItemFieldMapper.map(reader);
          }
        });
        final TaxAmount taxAmount = reader.readObject($responseFields[9], new ResponseReader.ObjectReader<TaxAmount>() {
          @Override
          public TaxAmount read(ResponseReader reader) {
            return taxAmountFieldMapper.map(reader);
          }
        });
        final Product product = reader.readObject($responseFields[10], new ResponseReader.ObjectReader<Product>() {
          @Override
          public Product read(ResponseReader reader) {
            return productFieldMapper.map(reader);
          }
        });
        final UnitAmount unitAmount = reader.readObject($responseFields[11], new ResponseReader.ObjectReader<UnitAmount>() {
          @Override
          public UnitAmount read(ResponseReader reader) {
            return unitAmountFieldMapper.map(reader);
          }
        });
        final UnitCostAmount unitCostAmount = reader.readObject($responseFields[12], new ResponseReader.ObjectReader<UnitCostAmount>() {
          @Override
          public UnitCostAmount read(ResponseReader reader) {
            return unitCostAmountFieldMapper.map(reader);
          }
        });
        final UnitQuantity unitQuantity = reader.readObject($responseFields[13], new ResponseReader.ObjectReader<UnitQuantity>() {
          @Override
          public UnitQuantity read(ResponseReader reader) {
            return unitQuantityFieldMapper.map(reader);
          }
        });
        final UnitTaxType unitTaxType = reader.readObject($responseFields[14], new ResponseReader.ObjectReader<UnitTaxType>() {
          @Override
          public UnitTaxType read(ResponseReader reader) {
            return unitTaxTypeFieldMapper.map(reader);
          }
        });
        return new Node(__typename, id, ref, type, createdOn, amount, creditReasonCode, description, orderItem, taxAmount, product, unitAmount, unitCostAmount, unitQuantity, unitTaxType);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nonnull String ref;

      private @Nullable String type;

      private @Nullable Object createdOn;

      private @Nullable Amount amount;

      private @Nullable CreditReasonCode creditReasonCode;

      private @Nullable String description;

      private @Nullable OrderItem orderItem;

      private @Nullable TaxAmount taxAmount;

      private @Nullable Product product;

      private @Nullable UnitAmount unitAmount;

      private @Nullable UnitCostAmount unitCostAmount;

      private @Nullable UnitQuantity unitQuantity;

      private @Nullable UnitTaxType unitTaxType;

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

      public Builder ref(@Nonnull String ref) {
        this.ref = ref;
        return this;
      }

      public Builder type(@Nullable String type) {
        this.type = type;
        return this;
      }

      public Builder createdOn(@Nullable Object createdOn) {
        this.createdOn = createdOn;
        return this;
      }

      public Builder amount(@Nullable Amount amount) {
        this.amount = amount;
        return this;
      }

      public Builder creditReasonCode(@Nullable CreditReasonCode creditReasonCode) {
        this.creditReasonCode = creditReasonCode;
        return this;
      }

      public Builder description(@Nullable String description) {
        this.description = description;
        return this;
      }

      public Builder orderItem(@Nullable OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
      }

      public Builder taxAmount(@Nullable TaxAmount taxAmount) {
        this.taxAmount = taxAmount;
        return this;
      }

      public Builder product(@Nullable Product product) {
        this.product = product;
        return this;
      }

      public Builder unitAmount(@Nullable UnitAmount unitAmount) {
        this.unitAmount = unitAmount;
        return this;
      }

      public Builder unitCostAmount(@Nullable UnitCostAmount unitCostAmount) {
        this.unitCostAmount = unitCostAmount;
        return this;
      }

      public Builder unitQuantity(@Nullable UnitQuantity unitQuantity) {
        this.unitQuantity = unitQuantity;
        return this;
      }

      public Builder unitTaxType(@Nullable UnitTaxType unitTaxType) {
        this.unitTaxType = unitTaxType;
        return this;
      }

      public Builder amount(@Nonnull Mutator<Amount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Amount.Builder builder = this.amount != null ? this.amount.toBuilder() : Amount.builder();
        mutator.accept(builder);
        this.amount = builder.build();
        return this;
      }

      public Builder creditReasonCode(@Nonnull Mutator<CreditReasonCode.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        CreditReasonCode.Builder builder = this.creditReasonCode != null ? this.creditReasonCode.toBuilder() : CreditReasonCode.builder();
        mutator.accept(builder);
        this.creditReasonCode = builder.build();
        return this;
      }

      public Builder orderItem(@Nonnull Mutator<OrderItem.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        OrderItem.Builder builder = this.orderItem != null ? this.orderItem.toBuilder() : OrderItem.builder();
        mutator.accept(builder);
        this.orderItem = builder.build();
        return this;
      }

      public Builder taxAmount(@Nonnull Mutator<TaxAmount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        TaxAmount.Builder builder = this.taxAmount != null ? this.taxAmount.toBuilder() : TaxAmount.builder();
        mutator.accept(builder);
        this.taxAmount = builder.build();
        return this;
      }

      public Builder product(@Nonnull Mutator<Product.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Product.Builder builder = this.product != null ? this.product.toBuilder() : Product.builder();
        mutator.accept(builder);
        this.product = builder.build();
        return this;
      }

      public Builder unitAmount(@Nonnull Mutator<UnitAmount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        UnitAmount.Builder builder = this.unitAmount != null ? this.unitAmount.toBuilder() : UnitAmount.builder();
        mutator.accept(builder);
        this.unitAmount = builder.build();
        return this;
      }

      public Builder unitCostAmount(@Nonnull Mutator<UnitCostAmount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        UnitCostAmount.Builder builder = this.unitCostAmount != null ? this.unitCostAmount.toBuilder() : UnitCostAmount.builder();
        mutator.accept(builder);
        this.unitCostAmount = builder.build();
        return this;
      }

      public Builder unitQuantity(@Nonnull Mutator<UnitQuantity.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        UnitQuantity.Builder builder = this.unitQuantity != null ? this.unitQuantity.toBuilder() : UnitQuantity.builder();
        mutator.accept(builder);
        this.unitQuantity = builder.build();
        return this;
      }

      public Builder unitTaxType(@Nonnull Mutator<UnitTaxType.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        UnitTaxType.Builder builder = this.unitTaxType != null ? this.unitTaxType.toBuilder() : UnitTaxType.builder();
        mutator.accept(builder);
        this.unitTaxType = builder.build();
        return this;
      }

      public Node build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(ref, "ref == null");
        return new Node(__typename, id, ref, type, createdOn, amount, creditReasonCode, description, orderItem, taxAmount, product, unitAmount, unitCostAmount, unitQuantity, unitTaxType);
      }
    }
  }

  public static class Amount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Amount(@Nonnull String __typename, @Nullable Double amount) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Amount{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Amount) {
        Amount that = (Amount) o;
        return this.__typename.equals(that.__typename)
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount));
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
        h ^= (amount == null) ? 0 : amount.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Amount> {
      @Override
      public Amount map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Double amount = reader.readDouble($responseFields[1]);
        return new Amount(__typename, amount);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder amount(@Nullable Double amount) {
        this.amount = amount;
        return this;
      }

      public Amount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Amount(__typename, amount);
      }
    }
  }

  public static class CreditReasonCode {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("value", "value", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String value;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public CreditReasonCode(@Nonnull String __typename, @Nullable String value) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.value = value;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String value() {
      return this.value;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], value);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "CreditReasonCode{"
          + "__typename=" + __typename + ", "
          + "value=" + value
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof CreditReasonCode) {
        CreditReasonCode that = (CreditReasonCode) o;
        return this.__typename.equals(that.__typename)
         && ((this.value == null) ? (that.value == null) : this.value.equals(that.value));
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
        h ^= (value == null) ? 0 : value.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.value = value;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<CreditReasonCode> {
      @Override
      public CreditReasonCode map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String value = reader.readString($responseFields[1]);
        return new CreditReasonCode(__typename, value);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String value;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder value(@Nullable String value) {
        this.value = value;
        return this;
      }

      public CreditReasonCode build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new CreditReasonCode(__typename, value);
      }
    }
  }

  public static class OrderItem {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("order", "order", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    final @Nullable Order order;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public OrderItem(@Nonnull String __typename, @Nullable String ref, @Nullable Order order) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
      this.order = order;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String ref() {
      return this.ref;
    }

    public @Nullable Order order() {
      return this.order;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeObject($responseFields[2], order != null ? order.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "OrderItem{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "order=" + order
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof OrderItem) {
        OrderItem that = (OrderItem) o;
        return this.__typename.equals(that.__typename)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && ((this.order == null) ? (that.order == null) : this.order.equals(that.order));
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
        h *= 1000003;
        h ^= (order == null) ? 0 : order.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.ref = ref;
      builder.order = order;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<OrderItem> {
      final Order.Mapper orderFieldMapper = new Order.Mapper();

      @Override
      public OrderItem map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final Order order = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<Order>() {
          @Override
          public Order read(ResponseReader reader) {
            return orderFieldMapper.map(reader);
          }
        });
        return new OrderItem(__typename, ref, order);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      private @Nullable Order order;

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

      public Builder order(@Nullable Order order) {
        this.order = order;
        return this;
      }

      public Builder order(@Nonnull Mutator<Order.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Order.Builder builder = this.order != null ? this.order.toBuilder() : Order.builder();
        mutator.accept(builder);
        this.order = builder.build();
        return this;
      }

      public OrderItem build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new OrderItem(__typename, ref, order);
      }
    }
  }

  public static class Order {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("retailer", "retailer", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    final @Nullable Retailer1 retailer;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Order(@Nonnull String __typename, @Nullable String ref, @Nullable Retailer1 retailer) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
      this.retailer = retailer;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String ref() {
      return this.ref;
    }

    public @Nullable Retailer1 retailer() {
      return this.retailer;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeObject($responseFields[2], retailer != null ? retailer.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Order{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "retailer=" + retailer
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Order) {
        Order that = (Order) o;
        return this.__typename.equals(that.__typename)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && ((this.retailer == null) ? (that.retailer == null) : this.retailer.equals(that.retailer));
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
        h *= 1000003;
        h ^= (retailer == null) ? 0 : retailer.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.ref = ref;
      builder.retailer = retailer;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Order> {
      final Retailer1.Mapper retailer1FieldMapper = new Retailer1.Mapper();

      @Override
      public Order map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final Retailer1 retailer = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<Retailer1>() {
          @Override
          public Retailer1 read(ResponseReader reader) {
            return retailer1FieldMapper.map(reader);
          }
        });
        return new Order(__typename, ref, retailer);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      private @Nullable Retailer1 retailer;

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

      public Builder retailer(@Nullable Retailer1 retailer) {
        this.retailer = retailer;
        return this;
      }

      public Builder retailer(@Nonnull Mutator<Retailer1.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Retailer1.Builder builder = this.retailer != null ? this.retailer.toBuilder() : Retailer1.builder();
        mutator.accept(builder);
        this.retailer = builder.build();
        return this;
      }

      public Order build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Order(__typename, ref, retailer);
      }
    }
  }

  public static class Retailer1 {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, true, CustomType.ID, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String id;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Retailer1(@Nonnull String __typename, @Nullable String id) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = id;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String id() {
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
        $toString = "Retailer1{"
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
      if (o instanceof Retailer1) {
        Retailer1 that = (Retailer1) o;
        return this.__typename.equals(that.__typename)
         && ((this.id == null) ? (that.id == null) : this.id.equals(that.id));
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
        h ^= (id == null) ? 0 : id.hashCode();
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

    public static final class Mapper implements ResponseFieldMapper<Retailer1> {
      @Override
      public Retailer1 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        return new Retailer1(__typename, id);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String id;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder id(@Nullable String id) {
        this.id = id;
        return this;
      }

      public Retailer1 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Retailer1(__typename, id);
      }
    }
  }

  public static class TaxAmount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public TaxAmount(@Nonnull String __typename, @Nullable Double amount) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "TaxAmount{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof TaxAmount) {
        TaxAmount that = (TaxAmount) o;
        return this.__typename.equals(that.__typename)
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount));
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
        h ^= (amount == null) ? 0 : amount.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<TaxAmount> {
      @Override
      public TaxAmount map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Double amount = reader.readDouble($responseFields[1]);
        return new TaxAmount(__typename, amount);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder amount(@Nullable Double amount) {
        this.amount = amount;
        return this;
      }

      public TaxAmount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new TaxAmount(__typename, amount);
      }
    }
  }

  public static class Product {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("catalogue", "catalogue", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    final @Nullable Catalogue catalogue;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Product(@Nonnull String __typename, @Nullable String ref,
        @Nullable Catalogue catalogue) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
      this.catalogue = catalogue;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String ref() {
      return this.ref;
    }

    public @Nullable Catalogue catalogue() {
      return this.catalogue;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeObject($responseFields[2], catalogue != null ? catalogue.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Product{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "catalogue=" + catalogue
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Product) {
        Product that = (Product) o;
        return this.__typename.equals(that.__typename)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && ((this.catalogue == null) ? (that.catalogue == null) : this.catalogue.equals(that.catalogue));
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
        h *= 1000003;
        h ^= (catalogue == null) ? 0 : catalogue.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.ref = ref;
      builder.catalogue = catalogue;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Product> {
      final Catalogue.Mapper catalogueFieldMapper = new Catalogue.Mapper();

      @Override
      public Product map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final Catalogue catalogue = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<Catalogue>() {
          @Override
          public Catalogue read(ResponseReader reader) {
            return catalogueFieldMapper.map(reader);
          }
        });
        return new Product(__typename, ref, catalogue);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      private @Nullable Catalogue catalogue;

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

      public Builder catalogue(@Nullable Catalogue catalogue) {
        this.catalogue = catalogue;
        return this;
      }

      public Builder catalogue(@Nonnull Mutator<Catalogue.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Catalogue.Builder builder = this.catalogue != null ? this.catalogue.toBuilder() : Catalogue.builder();
        mutator.accept(builder);
        this.catalogue = builder.build();
        return this;
      }

      public Product build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Product(__typename, ref, catalogue);
      }
    }
  }

  public static class Catalogue {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Catalogue(@Nonnull String __typename, @Nullable String ref) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

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
        $toString = "Catalogue{"
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
      if (o instanceof Catalogue) {
        Catalogue that = (Catalogue) o;
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

    public static final class Mapper implements ResponseFieldMapper<Catalogue> {
      @Override
      public Catalogue map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        return new Catalogue(__typename, ref);
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

      public Catalogue build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Catalogue(__typename, ref);
      }
    }
  }

  public static class UnitAmount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public UnitAmount(@Nonnull String __typename, @Nullable Double amount) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "UnitAmount{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof UnitAmount) {
        UnitAmount that = (UnitAmount) o;
        return this.__typename.equals(that.__typename)
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount));
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
        h ^= (amount == null) ? 0 : amount.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<UnitAmount> {
      @Override
      public UnitAmount map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Double amount = reader.readDouble($responseFields[1]);
        return new UnitAmount(__typename, amount);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder amount(@Nullable Double amount) {
        this.amount = amount;
        return this;
      }

      public UnitAmount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new UnitAmount(__typename, amount);
      }
    }
  }

  public static class UnitCostAmount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public UnitCostAmount(@Nonnull String __typename, @Nullable Double amount) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "UnitCostAmount{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof UnitCostAmount) {
        UnitCostAmount that = (UnitCostAmount) o;
        return this.__typename.equals(that.__typename)
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount));
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
        h ^= (amount == null) ? 0 : amount.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<UnitCostAmount> {
      @Override
      public UnitCostAmount map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Double amount = reader.readDouble($responseFields[1]);
        return new UnitCostAmount(__typename, amount);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder amount(@Nullable Double amount) {
        this.amount = amount;
        return this;
      }

      public UnitCostAmount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new UnitCostAmount(__typename, amount);
      }
    }
  }

  public static class UnitQuantity {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("quantity", "quantity", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Integer quantity;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public UnitQuantity(@Nonnull String __typename, @Nullable Integer quantity) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.quantity = quantity;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The quantity itself.
     */
    public @Nullable Integer quantity() {
      return this.quantity;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeInt($responseFields[1], quantity);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "UnitQuantity{"
          + "__typename=" + __typename + ", "
          + "quantity=" + quantity
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof UnitQuantity) {
        UnitQuantity that = (UnitQuantity) o;
        return this.__typename.equals(that.__typename)
         && ((this.quantity == null) ? (that.quantity == null) : this.quantity.equals(that.quantity));
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
        h ^= (quantity == null) ? 0 : quantity.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.quantity = quantity;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<UnitQuantity> {
      @Override
      public UnitQuantity map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Integer quantity = reader.readInt($responseFields[1]);
        return new UnitQuantity(__typename, quantity);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Integer quantity;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder quantity(@Nullable Integer quantity) {
        this.quantity = quantity;
        return this;
      }

      public UnitQuantity build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new UnitQuantity(__typename, quantity);
      }
    }
  }

  public static class UnitTaxType {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("country", "country", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("group", "group", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("tariff", "tariff", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String country;

    final @Nonnull String group;

    final @Nullable String tariff;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public UnitTaxType(@Nonnull String __typename, @Nonnull String country, @Nonnull String group,
        @Nullable String tariff) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.country = Utils.checkNotNull(country, "country == null");
      this.group = Utils.checkNotNull(group, "group == null");
      this.tariff = tariff;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The country in which this Tax Type applies
     */
    public @Nonnull String country() {
      return this.country;
    }

    /**
     *  A group field which can be used to further identify the Tax Tariff applicable
     */
    public @Nonnull String group() {
      return this.group;
    }

    /**
     *  The tariff of the Tax Type
     */
    public @Nullable String tariff() {
      return this.tariff;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], country);
          writer.writeString($responseFields[2], group);
          writer.writeString($responseFields[3], tariff);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "UnitTaxType{"
          + "__typename=" + __typename + ", "
          + "country=" + country + ", "
          + "group=" + group + ", "
          + "tariff=" + tariff
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof UnitTaxType) {
        UnitTaxType that = (UnitTaxType) o;
        return this.__typename.equals(that.__typename)
         && this.country.equals(that.country)
         && this.group.equals(that.group)
         && ((this.tariff == null) ? (that.tariff == null) : this.tariff.equals(that.tariff));
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
        h ^= country.hashCode();
        h *= 1000003;
        h ^= group.hashCode();
        h *= 1000003;
        h ^= (tariff == null) ? 0 : tariff.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.country = country;
      builder.group = group;
      builder.tariff = tariff;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<UnitTaxType> {
      @Override
      public UnitTaxType map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String country = reader.readString($responseFields[1]);
        final String group = reader.readString($responseFields[2]);
        final String tariff = reader.readString($responseFields[3]);
        return new UnitTaxType(__typename, country, group, tariff);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String country;

      private @Nonnull String group;

      private @Nullable String tariff;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder country(@Nonnull String country) {
        this.country = country;
        return this;
      }

      public Builder group(@Nonnull String group) {
        this.group = group;
        return this;
      }

      public Builder tariff(@Nullable String tariff) {
        this.tariff = tariff;
        return this;
      }

      public UnitTaxType build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(country, "country == null");
        Utils.checkNotNull(group, "group == null");
        return new UnitTaxType(__typename, country, group, tariff);
      }
    }
  }

  public static class Order1 {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("retailer", "retailer", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    final @Nullable Retailer2 retailer;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Order1(@Nonnull String __typename, @Nullable String ref, @Nullable Retailer2 retailer) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
      this.retailer = retailer;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String ref() {
      return this.ref;
    }

    public @Nullable Retailer2 retailer() {
      return this.retailer;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeObject($responseFields[2], retailer != null ? retailer.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Order1{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "retailer=" + retailer
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Order1) {
        Order1 that = (Order1) o;
        return this.__typename.equals(that.__typename)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && ((this.retailer == null) ? (that.retailer == null) : this.retailer.equals(that.retailer));
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
        h *= 1000003;
        h ^= (retailer == null) ? 0 : retailer.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.ref = ref;
      builder.retailer = retailer;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Order1> {
      final Retailer2.Mapper retailer2FieldMapper = new Retailer2.Mapper();

      @Override
      public Order1 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final Retailer2 retailer = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<Retailer2>() {
          @Override
          public Retailer2 read(ResponseReader reader) {
            return retailer2FieldMapper.map(reader);
          }
        });
        return new Order1(__typename, ref, retailer);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      private @Nullable Retailer2 retailer;

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

      public Builder retailer(@Nullable Retailer2 retailer) {
        this.retailer = retailer;
        return this;
      }

      public Builder retailer(@Nonnull Mutator<Retailer2.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Retailer2.Builder builder = this.retailer != null ? this.retailer.toBuilder() : Retailer2.builder();
        mutator.accept(builder);
        this.retailer = builder.build();
        return this;
      }

      public Order1 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Order1(__typename, ref, retailer);
      }
    }
  }

  public static class Retailer2 {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, true, CustomType.ID, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String id;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Retailer2(@Nonnull String __typename, @Nullable String id) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = id;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String id() {
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
        $toString = "Retailer2{"
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
      if (o instanceof Retailer2) {
        Retailer2 that = (Retailer2) o;
        return this.__typename.equals(that.__typename)
         && ((this.id == null) ? (that.id == null) : this.id.equals(that.id));
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
        h ^= (id == null) ? 0 : id.hashCode();
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

    public static final class Mapper implements ResponseFieldMapper<Retailer2> {
      @Override
      public Retailer2 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        return new Retailer2(__typename, id);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String id;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder id(@Nullable String id) {
        this.id = id;
        return this;
      }

      public Retailer2 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Retailer2(__typename, id);
      }
    }
  }

  public static class BillingAccount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String ref;

    final @Nonnull String id;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public BillingAccount(@Nonnull String __typename, @Nonnull String ref, @Nonnull String id) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.id = Utils.checkNotNull(id, "id == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  External reference to the `BillingAccount`. Must be unique.
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    /**
     *  ID of the object.
     */
    public @Nonnull String id() {
      return this.id;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], id);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "BillingAccount{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
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
      if (o instanceof BillingAccount) {
        BillingAccount that = (BillingAccount) o;
        return this.__typename.equals(that.__typename)
         && this.ref.equals(that.ref)
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
        h ^= ref.hashCode();
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
      builder.ref = ref;
      builder.id = id;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<BillingAccount> {
      @Override
      public BillingAccount map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        return new BillingAccount(__typename, ref, id);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String ref;

      private @Nonnull String id;

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

      public Builder id(@Nonnull String id) {
        this.id = id;
        return this;
      }

      public BillingAccount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(ref, "ref == null");
        Utils.checkNotNull(id, "id == null");
        return new BillingAccount(__typename, ref, id);
      }
    }
  }
}
