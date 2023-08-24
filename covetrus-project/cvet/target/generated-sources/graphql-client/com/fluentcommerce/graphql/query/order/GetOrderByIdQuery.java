package com.fluentcommerce.graphql.query.order;

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
import java.lang.Boolean;
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
public final class GetOrderByIdQuery implements Query<GetOrderByIdQuery.Data, GetOrderByIdQuery.Data, GetOrderByIdQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query GetOrderById($id: ID!, $includeCustomer: Boolean!, $includeAttributes: Boolean!, $includeFulfilmentChoice: Boolean!, $includeOrderItems: Boolean!, $includeFulfilments: Boolean!, $fulfilmentRef: [String], $orderItemCount: Int, $orderItemCursor: String, $fulfilmentCount: Int, $fulfilmentCursor: String) {\n"
      + "  orderById(id: $id) {\n"
      + "    __typename\n"
      + "    id\n"
      + "    ref\n"
      + "    type\n"
      + "    status\n"
      + "    createdOn\n"
      + "    updatedOn\n"
      + "    totalPrice\n"
      + "    totalTaxPrice\n"
      + "    orderItems: items(first: $orderItemCount, after: $orderItemCursor) @include(if: $includeOrderItems) {\n"
      + "      __typename\n"
      + "      orderItemEdge: edges {\n"
      + "        __typename\n"
      + "        orderItemNode: node {\n"
      + "          __typename\n"
      + "          id\n"
      + "          ref\n"
      + "          quantity\n"
      + "          paidPrice\n"
      + "          currency\n"
      + "          price\n"
      + "          taxType\n"
      + "          taxPrice\n"
      + "          totalPrice\n"
      + "          totalTaxPrice\n"
      + "          status\n"
      + "          taxType\n"
      + "          orderItemAttributes: attributes {\n"
      + "            __typename\n"
      + "            name\n"
      + "            type\n"
      + "            value\n"
      + "          }\n"
      + "          product {\n"
      + "            __typename\n"
      + "            ... on VariantProduct {\n"
      + "              id\n"
      + "              ref\n"
      + "              type\n"
      + "              status\n"
      + "              variantProductAttributes: attributes {\n"
      + "                __typename\n"
      + "                name\n"
      + "                type\n"
      + "                value\n"
      + "              }\n"
      + "              gtin\n"
      + "              name\n"
      + "              summary\n"
      + "              prices {\n"
      + "                __typename\n"
      + "                type\n"
      + "                currency\n"
      + "                value\n"
      + "              }\n"
      + "              tax {\n"
      + "                __typename\n"
      + "                country\n"
      + "                group\n"
      + "                tariff\n"
      + "              }\n"
      + "              catalogue {\n"
      + "                __typename\n"
      + "                ref\n"
      + "              }\n"
      + "            }\n"
      + "          }\n"
      + "        }\n"
      + "        cursor\n"
      + "      }\n"
      + "      pageInfo {\n"
      + "        __typename\n"
      + "        hasNextPage\n"
      + "      }\n"
      + "    }\n"
      + "    fulfilments(first: $fulfilmentCount, after: $fulfilmentCursor, ref: $fulfilmentRef) @include(if: $includeFulfilments) {\n"
      + "      __typename\n"
      + "      fulfilmentsEdges: edges {\n"
      + "        __typename\n"
      + "        fulfilmentsNode: node {\n"
      + "          __typename\n"
      + "          id\n"
      + "          ref\n"
      + "          type\n"
      + "          status\n"
      + "          type\n"
      + "          createdOn\n"
      + "          updatedOn\n"
      + "          fulfilmentAttributes: attributes {\n"
      + "            __typename\n"
      + "            name\n"
      + "            value\n"
      + "            type\n"
      + "          }\n"
      + "          deliveryType\n"
      + "          fromLocation {\n"
      + "            __typename\n"
      + "            ref\n"
      + "          }\n"
      + "          fromAddress {\n"
      + "            __typename\n"
      + "            ref\n"
      + "            id\n"
      + "            name\n"
      + "          }\n"
      + "          toAddress {\n"
      + "            __typename\n"
      + "            ref\n"
      + "            id\n"
      + "          }\n"
      + "        }\n"
      + "        cursor\n"
      + "      }\n"
      + "      pageInfo {\n"
      + "        __typename\n"
      + "        hasNextPage\n"
      + "      }\n"
      + "    }\n"
      + "    customer @include(if: $includeCustomer) {\n"
      + "      __typename\n"
      + "      id\n"
      + "      ref\n"
      + "      title\n"
      + "      country\n"
      + "      firstName\n"
      + "      lastName\n"
      + "      username\n"
      + "      primaryEmail\n"
      + "      primaryPhone\n"
      + "    }\n"
      + "    attributes @include(if: $includeAttributes) {\n"
      + "      __typename\n"
      + "      name\n"
      + "      value\n"
      + "      type\n"
      + "    }\n"
      + "    fulfilmentChoice @include(if: $includeFulfilmentChoice) {\n"
      + "      __typename\n"
      + "      id\n"
      + "      createdOn\n"
      + "      updatedOn\n"
      + "      currency\n"
      + "      deliveryInstruction\n"
      + "      fulfilmentPrice\n"
      + "      deliveryType\n"
      + "      fulfilmentPrice\n"
      + "      fulfilmentType\n"
      + "      fulfilmentTaxPrice\n"
      + "      pickupLocationRef\n"
      + "      deliveryAddress {\n"
      + "        __typename\n"
      + "        id\n"
      + "        type\n"
      + "        companyName\n"
      + "        name\n"
      + "        street\n"
      + "        city\n"
      + "        state\n"
      + "        postcode\n"
      + "        region\n"
      + "        country\n"
      + "        region\n"
      + "        ref\n"
      + "        latitude\n"
      + "        longitude\n"
      + "        createdOn\n"
      + "        updatedOn\n"
      + "      }\n"
      + "    }\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "GetOrderById";
    }
  };

  private final GetOrderByIdQuery.Variables variables;

  public GetOrderByIdQuery(@Nonnull String id, boolean includeCustomer, boolean includeAttributes,
      boolean includeFulfilmentChoice, boolean includeOrderItems, boolean includeFulfilments,
      @Nullable List<String> fulfilmentRef, @Nullable Integer orderItemCount,
      @Nullable String orderItemCursor, @Nullable Integer fulfilmentCount,
      @Nullable String fulfilmentCursor) {
    Utils.checkNotNull(id, "id == null");
    variables = new GetOrderByIdQuery.Variables(id, includeCustomer, includeAttributes, includeFulfilmentChoice, includeOrderItems, includeFulfilments, fulfilmentRef, orderItemCount, orderItemCursor, fulfilmentCount, fulfilmentCursor);
  }

  @Override
  public String operationId() {
    return "3d126cde352d21eb49fc4fa92dcb840a8b25735d0432ae9e9ea6b32a7e9499e5";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public GetOrderByIdQuery.Data wrapData(GetOrderByIdQuery.Data data) {
    return data;
  }

  @Override
  public GetOrderByIdQuery.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<GetOrderByIdQuery.Data> responseFieldMapper() {
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
    private @Nonnull String id;

    private boolean includeCustomer;

    private boolean includeAttributes;

    private boolean includeFulfilmentChoice;

    private boolean includeOrderItems;

    private boolean includeFulfilments;

    private @Nullable List<String> fulfilmentRef;

    private @Nullable Integer orderItemCount;

    private @Nullable String orderItemCursor;

    private @Nullable Integer fulfilmentCount;

    private @Nullable String fulfilmentCursor;

    Builder() {
    }

    public Builder id(@Nonnull String id) {
      this.id = id;
      return this;
    }

    public Builder includeCustomer(boolean includeCustomer) {
      this.includeCustomer = includeCustomer;
      return this;
    }

    public Builder includeAttributes(boolean includeAttributes) {
      this.includeAttributes = includeAttributes;
      return this;
    }

    public Builder includeFulfilmentChoice(boolean includeFulfilmentChoice) {
      this.includeFulfilmentChoice = includeFulfilmentChoice;
      return this;
    }

    public Builder includeOrderItems(boolean includeOrderItems) {
      this.includeOrderItems = includeOrderItems;
      return this;
    }

    public Builder includeFulfilments(boolean includeFulfilments) {
      this.includeFulfilments = includeFulfilments;
      return this;
    }

    public Builder fulfilmentRef(@Nullable List<String> fulfilmentRef) {
      this.fulfilmentRef = fulfilmentRef;
      return this;
    }

    public Builder orderItemCount(@Nullable Integer orderItemCount) {
      this.orderItemCount = orderItemCount;
      return this;
    }

    public Builder orderItemCursor(@Nullable String orderItemCursor) {
      this.orderItemCursor = orderItemCursor;
      return this;
    }

    public Builder fulfilmentCount(@Nullable Integer fulfilmentCount) {
      this.fulfilmentCount = fulfilmentCount;
      return this;
    }

    public Builder fulfilmentCursor(@Nullable String fulfilmentCursor) {
      this.fulfilmentCursor = fulfilmentCursor;
      return this;
    }

    public GetOrderByIdQuery build() {
      Utils.checkNotNull(id, "id == null");
      return new GetOrderByIdQuery(id, includeCustomer, includeAttributes, includeFulfilmentChoice, includeOrderItems, includeFulfilments, fulfilmentRef, orderItemCount, orderItemCursor, fulfilmentCount, fulfilmentCursor);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nonnull String id;

    private final boolean includeCustomer;

    private final boolean includeAttributes;

    private final boolean includeFulfilmentChoice;

    private final boolean includeOrderItems;

    private final boolean includeFulfilments;

    private final @Nullable List<String> fulfilmentRef;

    private final @Nullable Integer orderItemCount;

    private final @Nullable String orderItemCursor;

    private final @Nullable Integer fulfilmentCount;

    private final @Nullable String fulfilmentCursor;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nonnull String id, boolean includeCustomer, boolean includeAttributes,
        boolean includeFulfilmentChoice, boolean includeOrderItems, boolean includeFulfilments,
        @Nullable List<String> fulfilmentRef, @Nullable Integer orderItemCount,
        @Nullable String orderItemCursor, @Nullable Integer fulfilmentCount,
        @Nullable String fulfilmentCursor) {
      this.id = id;
      this.includeCustomer = includeCustomer;
      this.includeAttributes = includeAttributes;
      this.includeFulfilmentChoice = includeFulfilmentChoice;
      this.includeOrderItems = includeOrderItems;
      this.includeFulfilments = includeFulfilments;
      this.fulfilmentRef = fulfilmentRef;
      this.orderItemCount = orderItemCount;
      this.orderItemCursor = orderItemCursor;
      this.fulfilmentCount = fulfilmentCount;
      this.fulfilmentCursor = fulfilmentCursor;
      this.valueMap.put("id", id);
      this.valueMap.put("includeCustomer", includeCustomer);
      this.valueMap.put("includeAttributes", includeAttributes);
      this.valueMap.put("includeFulfilmentChoice", includeFulfilmentChoice);
      this.valueMap.put("includeOrderItems", includeOrderItems);
      this.valueMap.put("includeFulfilments", includeFulfilments);
      this.valueMap.put("fulfilmentRef", fulfilmentRef);
      this.valueMap.put("orderItemCount", orderItemCount);
      this.valueMap.put("orderItemCursor", orderItemCursor);
      this.valueMap.put("fulfilmentCount", fulfilmentCount);
      this.valueMap.put("fulfilmentCursor", fulfilmentCursor);
    }

    public @Nonnull String id() {
      return id;
    }

    public boolean includeCustomer() {
      return includeCustomer;
    }

    public boolean includeAttributes() {
      return includeAttributes;
    }

    public boolean includeFulfilmentChoice() {
      return includeFulfilmentChoice;
    }

    public boolean includeOrderItems() {
      return includeOrderItems;
    }

    public boolean includeFulfilments() {
      return includeFulfilments;
    }

    public @Nullable List<String> fulfilmentRef() {
      return fulfilmentRef;
    }

    public @Nullable Integer orderItemCount() {
      return orderItemCount;
    }

    public @Nullable String orderItemCursor() {
      return orderItemCursor;
    }

    public @Nullable Integer fulfilmentCount() {
      return fulfilmentCount;
    }

    public @Nullable String fulfilmentCursor() {
      return fulfilmentCursor;
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
          writer.writeCustom("id", com.fluentretail.graphql.type.CustomType.ID, id);
          writer.writeBoolean("includeCustomer", includeCustomer);
          writer.writeBoolean("includeAttributes", includeAttributes);
          writer.writeBoolean("includeFulfilmentChoice", includeFulfilmentChoice);
          writer.writeBoolean("includeOrderItems", includeOrderItems);
          writer.writeBoolean("includeFulfilments", includeFulfilments);
          writer.writeList("fulfilmentRef", fulfilmentRef != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : fulfilmentRef) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
          writer.writeInt("orderItemCount", orderItemCount);
          writer.writeString("orderItemCursor", orderItemCursor);
          writer.writeInt("fulfilmentCount", fulfilmentCount);
          writer.writeString("fulfilmentCursor", fulfilmentCursor);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("orderById", "orderById", new UnmodifiableMapBuilder<String, Object>(1)
        .put("id", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "id")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable OrderById orderById;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable OrderById orderById) {
      this.orderById = orderById;
    }

    /**
     *  Find a Order entity
     */
    public @Nullable OrderById orderById() {
      return this.orderById;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], orderById != null ? orderById.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "orderById=" + orderById
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
        return ((this.orderById == null) ? (that.orderById == null) : this.orderById.equals(that.orderById));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (orderById == null) ? 0 : orderById.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.orderById = orderById;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final OrderById.Mapper orderByIdFieldMapper = new OrderById.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final OrderById orderById = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<OrderById>() {
          @Override
          public OrderById read(ResponseReader reader) {
            return orderByIdFieldMapper.map(reader);
          }
        });
        return new Data(orderById);
      }
    }

    public static final class Builder {
      private @Nullable OrderById orderById;

      Builder() {
      }

      public Builder orderById(@Nullable OrderById orderById) {
        this.orderById = orderById;
        return this;
      }

      public Builder orderById(@Nonnull Mutator<OrderById.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        OrderById.Builder builder = this.orderById != null ? this.orderById.toBuilder() : OrderById.builder();
        mutator.accept(builder);
        this.orderById = builder.build();
        return this;
      }

      public Data build() {
        return new Data(orderById);
      }
    }
  }

  public static class OrderById {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("updatedOn", "updatedOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("totalPrice", "totalPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("totalTaxPrice", "totalTaxPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("orderItems", "items", new UnmodifiableMapBuilder<String, Object>(2)
        .put("after", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "orderItemCursor")
        .build())
        .put("first", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "orderItemCount")
        .build())
      .build(), true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeOrderItems", false))),
      ResponseField.forObject("fulfilments", "fulfilments", new UnmodifiableMapBuilder<String, Object>(3)
        .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "fulfilmentRef")
        .build())
        .put("after", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "fulfilmentCursor")
        .build())
        .put("first", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "fulfilmentCount")
        .build())
      .build(), true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeFulfilments", false))),
      ResponseField.forObject("customer", "customer", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeCustomer", false))),
      ResponseField.forList("attributes", "attributes", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeAttributes", false))),
      ResponseField.forObject("fulfilmentChoice", "fulfilmentChoice", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeFulfilmentChoice", false)))
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final @Nonnull String type;

    final @Nullable String status;

    final @Nullable Object createdOn;

    final @Nullable Object updatedOn;

    final @Nullable Double totalPrice;

    final @Nullable Double totalTaxPrice;

    final @Nullable OrderItems orderItems;

    final @Nullable Fulfilments fulfilments;

    final @Nullable Customer customer;

    final @Nullable List<Attribute> attributes;

    final @Nullable FulfilmentChoice fulfilmentChoice;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public OrderById(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        @Nonnull String type, @Nullable String status, @Nullable Object createdOn,
        @Nullable Object updatedOn, @Nullable Double totalPrice, @Nullable Double totalTaxPrice,
        @Nullable OrderItems orderItems, @Nullable Fulfilments fulfilments,
        @Nullable Customer customer, @Nullable List<Attribute> attributes,
        @Nullable FulfilmentChoice fulfilmentChoice) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.type = Utils.checkNotNull(type, "type == null");
      this.status = status;
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.totalPrice = totalPrice;
      this.totalTaxPrice = totalTaxPrice;
      this.orderItems = orderItems;
      this.fulfilments = fulfilments;
      this.customer = customer;
      this.attributes = attributes;
      this.fulfilmentChoice = fulfilmentChoice;
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
     *  External reference of the object. Must be unique.
     */
    public @Nullable String ref() {
      return this.ref;
    }

    /**
     *  Type of the `Order`, typically used by the Orchestration Engine to determine the workflow that should be applied. Unless stated otherwise, no values are enforced by the platform.<br/>
     *  Type of the Order, typically used by the Orchestration Engine to determine the workflow
     *  that should be applied. Unless stated otherwise, no values are enforced by the platform. Currently supports all values.
     */
    public @Nonnull String type() {
      return this.type;
    }

    /**
     *  The current status of the `Order`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
     *  The current status of the Order.
     */
    public @Nullable String status() {
      return this.status;
    }

    /**
     *  Time of creation
     */
    public @Nullable Object createdOn() {
      return this.createdOn;
    }

    /**
     *  Time of last update
     */
    public @Nullable Object updatedOn() {
      return this.updatedOn;
    }

    /**
     *  Total price
     */
    public @Nullable Double totalPrice() {
      return this.totalPrice;
    }

    /**
     *  Total tax price
     */
    public @Nullable Double totalTaxPrice() {
      return this.totalTaxPrice;
    }

    /**
     *  Connection representing a list of `OrderItem`s
     */
    public @Nullable OrderItems orderItems() {
      return this.orderItems;
    }

    /**
     *  Connection representing a list of `Fulfilment`s
     */
    public @Nullable Fulfilments fulfilments() {
      return this.fulfilments;
    }

    /**
     *  `Customer` of the order
     */
    public @Nullable Customer customer() {
      return this.customer;
    }

    /**
     *  List of order `attribute`s
     */
    public @Nullable List<Attribute> attributes() {
      return this.attributes;
    }

    /**
     *  The `FulfilmentChoice` specified when booking the order
     */
    public @Nullable FulfilmentChoice fulfilmentChoice() {
      return this.fulfilmentChoice;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeString($responseFields[3], type);
          writer.writeString($responseFields[4], status);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[5], createdOn);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[6], updatedOn);
          writer.writeDouble($responseFields[7], totalPrice);
          writer.writeDouble($responseFields[8], totalTaxPrice);
          writer.writeObject($responseFields[9], orderItems != null ? orderItems.marshaller() : null);
          writer.writeObject($responseFields[10], fulfilments != null ? fulfilments.marshaller() : null);
          writer.writeObject($responseFields[11], customer != null ? customer.marshaller() : null);
          writer.writeList($responseFields[12], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute) value).marshaller());
            }
          });
          writer.writeObject($responseFields[13], fulfilmentChoice != null ? fulfilmentChoice.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "OrderById{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "type=" + type + ", "
          + "status=" + status + ", "
          + "createdOn=" + createdOn + ", "
          + "updatedOn=" + updatedOn + ", "
          + "totalPrice=" + totalPrice + ", "
          + "totalTaxPrice=" + totalTaxPrice + ", "
          + "orderItems=" + orderItems + ", "
          + "fulfilments=" + fulfilments + ", "
          + "customer=" + customer + ", "
          + "attributes=" + attributes + ", "
          + "fulfilmentChoice=" + fulfilmentChoice
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof OrderById) {
        OrderById that = (OrderById) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && this.type.equals(that.type)
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.updatedOn == null) ? (that.updatedOn == null) : this.updatedOn.equals(that.updatedOn))
         && ((this.totalPrice == null) ? (that.totalPrice == null) : this.totalPrice.equals(that.totalPrice))
         && ((this.totalTaxPrice == null) ? (that.totalTaxPrice == null) : this.totalTaxPrice.equals(that.totalTaxPrice))
         && ((this.orderItems == null) ? (that.orderItems == null) : this.orderItems.equals(that.orderItems))
         && ((this.fulfilments == null) ? (that.fulfilments == null) : this.fulfilments.equals(that.fulfilments))
         && ((this.customer == null) ? (that.customer == null) : this.customer.equals(that.customer))
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes))
         && ((this.fulfilmentChoice == null) ? (that.fulfilmentChoice == null) : this.fulfilmentChoice.equals(that.fulfilmentChoice));
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
        h ^= (ref == null) ? 0 : ref.hashCode();
        h *= 1000003;
        h ^= type.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        h *= 1000003;
        h ^= (createdOn == null) ? 0 : createdOn.hashCode();
        h *= 1000003;
        h ^= (updatedOn == null) ? 0 : updatedOn.hashCode();
        h *= 1000003;
        h ^= (totalPrice == null) ? 0 : totalPrice.hashCode();
        h *= 1000003;
        h ^= (totalTaxPrice == null) ? 0 : totalTaxPrice.hashCode();
        h *= 1000003;
        h ^= (orderItems == null) ? 0 : orderItems.hashCode();
        h *= 1000003;
        h ^= (fulfilments == null) ? 0 : fulfilments.hashCode();
        h *= 1000003;
        h ^= (customer == null) ? 0 : customer.hashCode();
        h *= 1000003;
        h ^= (attributes == null) ? 0 : attributes.hashCode();
        h *= 1000003;
        h ^= (fulfilmentChoice == null) ? 0 : fulfilmentChoice.hashCode();
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
      builder.status = status;
      builder.createdOn = createdOn;
      builder.updatedOn = updatedOn;
      builder.totalPrice = totalPrice;
      builder.totalTaxPrice = totalTaxPrice;
      builder.orderItems = orderItems;
      builder.fulfilments = fulfilments;
      builder.customer = customer;
      builder.attributes = attributes;
      builder.fulfilmentChoice = fulfilmentChoice;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<OrderById> {
      final OrderItems.Mapper orderItemsFieldMapper = new OrderItems.Mapper();

      final Fulfilments.Mapper fulfilmentsFieldMapper = new Fulfilments.Mapper();

      final Customer.Mapper customerFieldMapper = new Customer.Mapper();

      final Attribute.Mapper attributeFieldMapper = new Attribute.Mapper();

      final FulfilmentChoice.Mapper fulfilmentChoiceFieldMapper = new FulfilmentChoice.Mapper();

      @Override
      public OrderById map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[5]);
        final Object updatedOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[6]);
        final Double totalPrice = reader.readDouble($responseFields[7]);
        final Double totalTaxPrice = reader.readDouble($responseFields[8]);
        final OrderItems orderItems = reader.readObject($responseFields[9], new ResponseReader.ObjectReader<OrderItems>() {
          @Override
          public OrderItems read(ResponseReader reader) {
            return orderItemsFieldMapper.map(reader);
          }
        });
        final Fulfilments fulfilments = reader.readObject($responseFields[10], new ResponseReader.ObjectReader<Fulfilments>() {
          @Override
          public Fulfilments read(ResponseReader reader) {
            return fulfilmentsFieldMapper.map(reader);
          }
        });
        final Customer customer = reader.readObject($responseFields[11], new ResponseReader.ObjectReader<Customer>() {
          @Override
          public Customer read(ResponseReader reader) {
            return customerFieldMapper.map(reader);
          }
        });
        final List<Attribute> attributes = reader.readList($responseFields[12], new ResponseReader.ListReader<Attribute>() {
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
        final FulfilmentChoice fulfilmentChoice = reader.readObject($responseFields[13], new ResponseReader.ObjectReader<FulfilmentChoice>() {
          @Override
          public FulfilmentChoice read(ResponseReader reader) {
            return fulfilmentChoiceFieldMapper.map(reader);
          }
        });
        return new OrderById(__typename, id, ref, type, status, createdOn, updatedOn, totalPrice, totalTaxPrice, orderItems, fulfilments, customer, attributes, fulfilmentChoice);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String ref;

      private @Nonnull String type;

      private @Nullable String status;

      private @Nullable Object createdOn;

      private @Nullable Object updatedOn;

      private @Nullable Double totalPrice;

      private @Nullable Double totalTaxPrice;

      private @Nullable OrderItems orderItems;

      private @Nullable Fulfilments fulfilments;

      private @Nullable Customer customer;

      private @Nullable List<Attribute> attributes;

      private @Nullable FulfilmentChoice fulfilmentChoice;

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

      public Builder ref(@Nullable String ref) {
        this.ref = ref;
        return this;
      }

      public Builder type(@Nonnull String type) {
        this.type = type;
        return this;
      }

      public Builder status(@Nullable String status) {
        this.status = status;
        return this;
      }

      public Builder createdOn(@Nullable Object createdOn) {
        this.createdOn = createdOn;
        return this;
      }

      public Builder updatedOn(@Nullable Object updatedOn) {
        this.updatedOn = updatedOn;
        return this;
      }

      public Builder totalPrice(@Nullable Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
      }

      public Builder totalTaxPrice(@Nullable Double totalTaxPrice) {
        this.totalTaxPrice = totalTaxPrice;
        return this;
      }

      public Builder orderItems(@Nullable OrderItems orderItems) {
        this.orderItems = orderItems;
        return this;
      }

      public Builder fulfilments(@Nullable Fulfilments fulfilments) {
        this.fulfilments = fulfilments;
        return this;
      }

      public Builder customer(@Nullable Customer customer) {
        this.customer = customer;
        return this;
      }

      public Builder attributes(@Nullable List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
      }

      public Builder fulfilmentChoice(@Nullable FulfilmentChoice fulfilmentChoice) {
        this.fulfilmentChoice = fulfilmentChoice;
        return this;
      }

      public Builder orderItems(@Nonnull Mutator<OrderItems.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        OrderItems.Builder builder = this.orderItems != null ? this.orderItems.toBuilder() : OrderItems.builder();
        mutator.accept(builder);
        this.orderItems = builder.build();
        return this;
      }

      public Builder fulfilments(@Nonnull Mutator<Fulfilments.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Fulfilments.Builder builder = this.fulfilments != null ? this.fulfilments.toBuilder() : Fulfilments.builder();
        mutator.accept(builder);
        this.fulfilments = builder.build();
        return this;
      }

      public Builder customer(@Nonnull Mutator<Customer.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Customer.Builder builder = this.customer != null ? this.customer.toBuilder() : Customer.builder();
        mutator.accept(builder);
        this.customer = builder.build();
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

      public Builder fulfilmentChoice(@Nonnull Mutator<FulfilmentChoice.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        FulfilmentChoice.Builder builder = this.fulfilmentChoice != null ? this.fulfilmentChoice.toBuilder() : FulfilmentChoice.builder();
        mutator.accept(builder);
        this.fulfilmentChoice = builder.build();
        return this;
      }

      public OrderById build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(type, "type == null");
        return new OrderById(__typename, id, ref, type, status, createdOn, updatedOn, totalPrice, totalTaxPrice, orderItems, fulfilments, customer, attributes, fulfilmentChoice);
      }
    }
  }

  public static class OrderItems {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("orderItemEdge", "edges", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("pageInfo", "pageInfo", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<OrderItemEdge> orderItemEdge;

    final @Nullable PageInfo pageInfo;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public OrderItems(@Nonnull String __typename, @Nullable List<OrderItemEdge> orderItemEdge,
        @Nullable PageInfo pageInfo) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.orderItemEdge = orderItemEdge;
      this.pageInfo = pageInfo;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to OrderItem type node
     */
    public @Nullable List<OrderItemEdge> orderItemEdge() {
      return this.orderItemEdge;
    }

    /**
     *  Information to aid in pagination
     */
    public @Nullable PageInfo pageInfo() {
      return this.pageInfo;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeList($responseFields[1], orderItemEdge, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((OrderItemEdge) value).marshaller());
            }
          });
          writer.writeObject($responseFields[2], pageInfo != null ? pageInfo.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "OrderItems{"
          + "__typename=" + __typename + ", "
          + "orderItemEdge=" + orderItemEdge + ", "
          + "pageInfo=" + pageInfo
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof OrderItems) {
        OrderItems that = (OrderItems) o;
        return this.__typename.equals(that.__typename)
         && ((this.orderItemEdge == null) ? (that.orderItemEdge == null) : this.orderItemEdge.equals(that.orderItemEdge))
         && ((this.pageInfo == null) ? (that.pageInfo == null) : this.pageInfo.equals(that.pageInfo));
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
        h ^= (orderItemEdge == null) ? 0 : orderItemEdge.hashCode();
        h *= 1000003;
        h ^= (pageInfo == null) ? 0 : pageInfo.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.orderItemEdge = orderItemEdge;
      builder.pageInfo = pageInfo;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<OrderItems> {
      final OrderItemEdge.Mapper orderItemEdgeFieldMapper = new OrderItemEdge.Mapper();

      final PageInfo.Mapper pageInfoFieldMapper = new PageInfo.Mapper();

      @Override
      public OrderItems map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final List<OrderItemEdge> orderItemEdge = reader.readList($responseFields[1], new ResponseReader.ListReader<OrderItemEdge>() {
          @Override
          public OrderItemEdge read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<OrderItemEdge>() {
              @Override
              public OrderItemEdge read(ResponseReader reader) {
                return orderItemEdgeFieldMapper.map(reader);
              }
            });
          }
        });
        final PageInfo pageInfo = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<PageInfo>() {
          @Override
          public PageInfo read(ResponseReader reader) {
            return pageInfoFieldMapper.map(reader);
          }
        });
        return new OrderItems(__typename, orderItemEdge, pageInfo);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable List<OrderItemEdge> orderItemEdge;

      private @Nullable PageInfo pageInfo;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder orderItemEdge(@Nullable List<OrderItemEdge> orderItemEdge) {
        this.orderItemEdge = orderItemEdge;
        return this;
      }

      public Builder pageInfo(@Nullable PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
      }

      public Builder orderItemEdge(@Nonnull Mutator<List<OrderItemEdge.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<OrderItemEdge.Builder> builders = new ArrayList<>();
        if (this.orderItemEdge != null) {
          for (OrderItemEdge item : this.orderItemEdge) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<OrderItemEdge> orderItemEdge = new ArrayList<>();
        for (OrderItemEdge.Builder item : builders) {
          orderItemEdge.add(item != null ? item.build() : null);
        }
        this.orderItemEdge = orderItemEdge;
        return this;
      }

      public Builder pageInfo(@Nonnull Mutator<PageInfo.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        PageInfo.Builder builder = this.pageInfo != null ? this.pageInfo.toBuilder() : PageInfo.builder();
        mutator.accept(builder);
        this.pageInfo = builder.build();
        return this;
      }

      public OrderItems build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new OrderItems(__typename, orderItemEdge, pageInfo);
      }
    }
  }

  public static class OrderItemEdge {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("orderItemNode", "node", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("cursor", "cursor", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable OrderItemNode orderItemNode;

    final @Nullable String cursor;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public OrderItemEdge(@Nonnull String __typename, @Nullable OrderItemNode orderItemNode,
        @Nullable String cursor) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.orderItemNode = orderItemNode;
      this.cursor = cursor;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The item at the end of the OrderItem edge
     */
    public @Nullable OrderItemNode orderItemNode() {
      return this.orderItemNode;
    }

    /**
     *  A cursor for use in pagination
     */
    public @Nullable String cursor() {
      return this.cursor;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeObject($responseFields[1], orderItemNode != null ? orderItemNode.marshaller() : null);
          writer.writeString($responseFields[2], cursor);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "OrderItemEdge{"
          + "__typename=" + __typename + ", "
          + "orderItemNode=" + orderItemNode + ", "
          + "cursor=" + cursor
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof OrderItemEdge) {
        OrderItemEdge that = (OrderItemEdge) o;
        return this.__typename.equals(that.__typename)
         && ((this.orderItemNode == null) ? (that.orderItemNode == null) : this.orderItemNode.equals(that.orderItemNode))
         && ((this.cursor == null) ? (that.cursor == null) : this.cursor.equals(that.cursor));
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
        h ^= (orderItemNode == null) ? 0 : orderItemNode.hashCode();
        h *= 1000003;
        h ^= (cursor == null) ? 0 : cursor.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.orderItemNode = orderItemNode;
      builder.cursor = cursor;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<OrderItemEdge> {
      final OrderItemNode.Mapper orderItemNodeFieldMapper = new OrderItemNode.Mapper();

      @Override
      public OrderItemEdge map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final OrderItemNode orderItemNode = reader.readObject($responseFields[1], new ResponseReader.ObjectReader<OrderItemNode>() {
          @Override
          public OrderItemNode read(ResponseReader reader) {
            return orderItemNodeFieldMapper.map(reader);
          }
        });
        final String cursor = reader.readString($responseFields[2]);
        return new OrderItemEdge(__typename, orderItemNode, cursor);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable OrderItemNode orderItemNode;

      private @Nullable String cursor;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder orderItemNode(@Nullable OrderItemNode orderItemNode) {
        this.orderItemNode = orderItemNode;
        return this;
      }

      public Builder cursor(@Nullable String cursor) {
        this.cursor = cursor;
        return this;
      }

      public Builder orderItemNode(@Nonnull Mutator<OrderItemNode.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        OrderItemNode.Builder builder = this.orderItemNode != null ? this.orderItemNode.toBuilder() : OrderItemNode.builder();
        mutator.accept(builder);
        this.orderItemNode = builder.build();
        return this;
      }

      public OrderItemEdge build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new OrderItemEdge(__typename, orderItemNode, cursor);
      }
    }
  }

  public static class OrderItemNode {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("quantity", "quantity", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("paidPrice", "paidPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("currency", "currency", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("price", "price", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("taxType", "taxType", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("taxPrice", "taxPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("totalPrice", "totalPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("totalTaxPrice", "totalTaxPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("orderItemAttributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("product", "product", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final int quantity;

    final @Nullable Double paidPrice;

    final @Nullable String currency;

    final @Nullable Double price;

    final @Nullable String taxType;

    final @Nullable Double taxPrice;

    final @Nullable Double totalPrice;

    final @Nullable Double totalTaxPrice;

    final @Nonnull String status;

    final @Nullable List<OrderItemAttribute> orderItemAttributes;

    final @Nonnull Product product;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public OrderItemNode(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        int quantity, @Nullable Double paidPrice, @Nullable String currency, @Nullable Double price,
        @Nullable String taxType, @Nullable Double taxPrice, @Nullable Double totalPrice,
        @Nullable Double totalTaxPrice, @Nonnull String status,
        @Nullable List<OrderItemAttribute> orderItemAttributes, @Nonnull Product product) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.quantity = quantity;
      this.paidPrice = paidPrice;
      this.currency = currency;
      this.price = price;
      this.taxType = taxType;
      this.taxPrice = taxPrice;
      this.totalPrice = totalPrice;
      this.totalTaxPrice = totalTaxPrice;
      this.status = Utils.checkNotNull(status, "status == null");
      this.orderItemAttributes = orderItemAttributes;
      this.product = Utils.checkNotNull(product, "product == null");
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
     *  External reference of the object. Recommended to be unique.
     */
    public @Nullable String ref() {
      return this.ref;
    }

    /**
     *  Quantity ordered
     */
    public int quantity() {
      return this.quantity;
    }

    /**
     *  Price paid. Excludes tax.
     */
    public @Nullable Double paidPrice() {
      return this.paidPrice;
    }

    /**
     *  Currency. Should ideally be a 3 letter ISO currency code. For instance _AUD_.
     */
    public @Nullable String currency() {
      return this.currency;
    }

    /**
     *  Price
     */
    public @Nullable Double price() {
      return this.price;
    }

    /**
     *  Tax type. Supported values are _GST_, _VAT_, _EXCLTAX_
     */
    public @Nullable String taxType() {
      return this.taxType;
    }

    /**
     *  Tax price
     */
    public @Nullable Double taxPrice() {
      return this.taxPrice;
    }

    /**
     *  Total price
     */
    public @Nullable Double totalPrice() {
      return this.totalPrice;
    }

    /**
     *  Total tax price
     */
    public @Nullable Double totalTaxPrice() {
      return this.totalTaxPrice;
    }

    /**
     *  Status of the OrderItem. Currently supported values are limited to _CREATED_, _NEW_, _COMPLETE_.
     */
    public @Nonnull String status() {
      return this.status;
    }

    /**
     *  List of `OrderItem` `attribute`s.
     */
    public @Nullable List<OrderItemAttribute> orderItemAttributes() {
      return this.orderItemAttributes;
    }

    /**
     *  Represents the `Product` corresponding to this object
     */
    public @Nonnull Product product() {
      return this.product;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeInt($responseFields[3], quantity);
          writer.writeDouble($responseFields[4], paidPrice);
          writer.writeString($responseFields[5], currency);
          writer.writeDouble($responseFields[6], price);
          writer.writeString($responseFields[7], taxType);
          writer.writeDouble($responseFields[8], taxPrice);
          writer.writeDouble($responseFields[9], totalPrice);
          writer.writeDouble($responseFields[10], totalTaxPrice);
          writer.writeString($responseFields[11], status);
          writer.writeList($responseFields[12], orderItemAttributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((OrderItemAttribute) value).marshaller());
            }
          });
          writer.writeObject($responseFields[13], product.marshaller());
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "OrderItemNode{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "quantity=" + quantity + ", "
          + "paidPrice=" + paidPrice + ", "
          + "currency=" + currency + ", "
          + "price=" + price + ", "
          + "taxType=" + taxType + ", "
          + "taxPrice=" + taxPrice + ", "
          + "totalPrice=" + totalPrice + ", "
          + "totalTaxPrice=" + totalTaxPrice + ", "
          + "status=" + status + ", "
          + "orderItemAttributes=" + orderItemAttributes + ", "
          + "product=" + product
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof OrderItemNode) {
        OrderItemNode that = (OrderItemNode) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && this.quantity == that.quantity
         && ((this.paidPrice == null) ? (that.paidPrice == null) : this.paidPrice.equals(that.paidPrice))
         && ((this.currency == null) ? (that.currency == null) : this.currency.equals(that.currency))
         && ((this.price == null) ? (that.price == null) : this.price.equals(that.price))
         && ((this.taxType == null) ? (that.taxType == null) : this.taxType.equals(that.taxType))
         && ((this.taxPrice == null) ? (that.taxPrice == null) : this.taxPrice.equals(that.taxPrice))
         && ((this.totalPrice == null) ? (that.totalPrice == null) : this.totalPrice.equals(that.totalPrice))
         && ((this.totalTaxPrice == null) ? (that.totalTaxPrice == null) : this.totalTaxPrice.equals(that.totalTaxPrice))
         && this.status.equals(that.status)
         && ((this.orderItemAttributes == null) ? (that.orderItemAttributes == null) : this.orderItemAttributes.equals(that.orderItemAttributes))
         && this.product.equals(that.product);
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
        h ^= (ref == null) ? 0 : ref.hashCode();
        h *= 1000003;
        h ^= quantity;
        h *= 1000003;
        h ^= (paidPrice == null) ? 0 : paidPrice.hashCode();
        h *= 1000003;
        h ^= (currency == null) ? 0 : currency.hashCode();
        h *= 1000003;
        h ^= (price == null) ? 0 : price.hashCode();
        h *= 1000003;
        h ^= (taxType == null) ? 0 : taxType.hashCode();
        h *= 1000003;
        h ^= (taxPrice == null) ? 0 : taxPrice.hashCode();
        h *= 1000003;
        h ^= (totalPrice == null) ? 0 : totalPrice.hashCode();
        h *= 1000003;
        h ^= (totalTaxPrice == null) ? 0 : totalTaxPrice.hashCode();
        h *= 1000003;
        h ^= status.hashCode();
        h *= 1000003;
        h ^= (orderItemAttributes == null) ? 0 : orderItemAttributes.hashCode();
        h *= 1000003;
        h ^= product.hashCode();
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
      builder.quantity = quantity;
      builder.paidPrice = paidPrice;
      builder.currency = currency;
      builder.price = price;
      builder.taxType = taxType;
      builder.taxPrice = taxPrice;
      builder.totalPrice = totalPrice;
      builder.totalTaxPrice = totalTaxPrice;
      builder.status = status;
      builder.orderItemAttributes = orderItemAttributes;
      builder.product = product;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<OrderItemNode> {
      final OrderItemAttribute.Mapper orderItemAttributeFieldMapper = new OrderItemAttribute.Mapper();

      final Product.Mapper productFieldMapper = new Product.Mapper();

      @Override
      public OrderItemNode map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final int quantity = reader.readInt($responseFields[3]);
        final Double paidPrice = reader.readDouble($responseFields[4]);
        final String currency = reader.readString($responseFields[5]);
        final Double price = reader.readDouble($responseFields[6]);
        final String taxType = reader.readString($responseFields[7]);
        final Double taxPrice = reader.readDouble($responseFields[8]);
        final Double totalPrice = reader.readDouble($responseFields[9]);
        final Double totalTaxPrice = reader.readDouble($responseFields[10]);
        final String status = reader.readString($responseFields[11]);
        final List<OrderItemAttribute> orderItemAttributes = reader.readList($responseFields[12], new ResponseReader.ListReader<OrderItemAttribute>() {
          @Override
          public OrderItemAttribute read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<OrderItemAttribute>() {
              @Override
              public OrderItemAttribute read(ResponseReader reader) {
                return orderItemAttributeFieldMapper.map(reader);
              }
            });
          }
        });
        final Product product = reader.readObject($responseFields[13], new ResponseReader.ObjectReader<Product>() {
          @Override
          public Product read(ResponseReader reader) {
            return productFieldMapper.map(reader);
          }
        });
        return new OrderItemNode(__typename, id, ref, quantity, paidPrice, currency, price, taxType, taxPrice, totalPrice, totalTaxPrice, status, orderItemAttributes, product);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String ref;

      private int quantity;

      private @Nullable Double paidPrice;

      private @Nullable String currency;

      private @Nullable Double price;

      private @Nullable String taxType;

      private @Nullable Double taxPrice;

      private @Nullable Double totalPrice;

      private @Nullable Double totalTaxPrice;

      private @Nonnull String status;

      private @Nullable List<OrderItemAttribute> orderItemAttributes;

      private @Nonnull Product product;

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

      public Builder ref(@Nullable String ref) {
        this.ref = ref;
        return this;
      }

      public Builder quantity(int quantity) {
        this.quantity = quantity;
        return this;
      }

      public Builder paidPrice(@Nullable Double paidPrice) {
        this.paidPrice = paidPrice;
        return this;
      }

      public Builder currency(@Nullable String currency) {
        this.currency = currency;
        return this;
      }

      public Builder price(@Nullable Double price) {
        this.price = price;
        return this;
      }

      public Builder taxType(@Nullable String taxType) {
        this.taxType = taxType;
        return this;
      }

      public Builder taxPrice(@Nullable Double taxPrice) {
        this.taxPrice = taxPrice;
        return this;
      }

      public Builder totalPrice(@Nullable Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
      }

      public Builder totalTaxPrice(@Nullable Double totalTaxPrice) {
        this.totalTaxPrice = totalTaxPrice;
        return this;
      }

      public Builder status(@Nonnull String status) {
        this.status = status;
        return this;
      }

      public Builder orderItemAttributes(@Nullable List<OrderItemAttribute> orderItemAttributes) {
        this.orderItemAttributes = orderItemAttributes;
        return this;
      }

      public Builder product(@Nonnull Product product) {
        this.product = product;
        return this;
      }

      public Builder orderItemAttributes(@Nonnull Mutator<List<OrderItemAttribute.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<OrderItemAttribute.Builder> builders = new ArrayList<>();
        if (this.orderItemAttributes != null) {
          for (OrderItemAttribute item : this.orderItemAttributes) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<OrderItemAttribute> orderItemAttributes = new ArrayList<>();
        for (OrderItemAttribute.Builder item : builders) {
          orderItemAttributes.add(item != null ? item.build() : null);
        }
        this.orderItemAttributes = orderItemAttributes;
        return this;
      }

      public Builder product(@Nonnull Mutator<Product.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Product.Builder builder = this.product != null ? this.product.toBuilder() : Product.builder();
        mutator.accept(builder);
        this.product = builder.build();
        return this;
      }

      public OrderItemNode build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(status, "status == null");
        Utils.checkNotNull(product, "product == null");
        return new OrderItemNode(__typename, id, ref, quantity, paidPrice, currency, price, taxType, taxPrice, totalPrice, totalTaxPrice, status, orderItemAttributes, product);
      }
    }
  }

  public static class OrderItemAttribute {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("value", "value", null, false, CustomType.JSON, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String name;

    final @Nonnull String type;

    final @Nonnull Object value;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public OrderItemAttribute(@Nonnull String __typename, @Nonnull String name,
        @Nonnull String type, @Nonnull Object value) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.name = Utils.checkNotNull(name, "name == null");
      this.type = Utils.checkNotNull(type, "type == null");
      this.value = Utils.checkNotNull(value, "value == null");
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
     *  Type of the attribute's `value`. This is a free string and can be used by the client to interpret and cast the `value` into the appropriate type.
     */
    public @Nonnull String type() {
      return this.type;
    }

    /**
     *  Value of the `attribute`
     */
    public @Nonnull Object value() {
      return this.value;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], name);
          writer.writeString($responseFields[2], type);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], value);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "OrderItemAttribute{"
          + "__typename=" + __typename + ", "
          + "name=" + name + ", "
          + "type=" + type + ", "
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
      if (o instanceof OrderItemAttribute) {
        OrderItemAttribute that = (OrderItemAttribute) o;
        return this.__typename.equals(that.__typename)
         && this.name.equals(that.name)
         && this.type.equals(that.type)
         && this.value.equals(that.value);
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
        h ^= type.hashCode();
        h *= 1000003;
        h ^= value.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.name = name;
      builder.type = type;
      builder.value = value;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<OrderItemAttribute> {
      @Override
      public OrderItemAttribute map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String name = reader.readString($responseFields[1]);
        final String type = reader.readString($responseFields[2]);
        final Object value = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        return new OrderItemAttribute(__typename, name, type, value);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String name;

      private @Nonnull String type;

      private @Nonnull Object value;

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

      public Builder type(@Nonnull String type) {
        this.type = type;
        return this;
      }

      public Builder value(@Nonnull Object value) {
        this.value = value;
        return this;
      }

      public OrderItemAttribute build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(value, "value == null");
        return new OrderItemAttribute(__typename, name, type, value);
      }
    }
  }

  public static class Product {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInlineFragment("__typename", "__typename", Arrays.asList("VariantProduct"))
    };

    final @Nonnull String __typename;

    final @Nullable AsVariantProduct asVariantProduct;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Product(@Nonnull String __typename, @Nullable AsVariantProduct asVariantProduct) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.asVariantProduct = asVariantProduct;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable AsVariantProduct asVariantProduct() {
      return this.asVariantProduct;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          final AsVariantProduct $asVariantProduct = asVariantProduct;
          if ($asVariantProduct != null) {
            $asVariantProduct.marshaller().marshal(writer);
          }
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Product{"
          + "__typename=" + __typename + ", "
          + "asVariantProduct=" + asVariantProduct
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
         && ((this.asVariantProduct == null) ? (that.asVariantProduct == null) : this.asVariantProduct.equals(that.asVariantProduct));
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
        h ^= (asVariantProduct == null) ? 0 : asVariantProduct.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.asVariantProduct = asVariantProduct;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Product> {
      final AsVariantProduct.Mapper asVariantProductFieldMapper = new AsVariantProduct.Mapper();

      @Override
      public Product map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final AsVariantProduct asVariantProduct = reader.readConditional($responseFields[1], new ResponseReader.ConditionalTypeReader<AsVariantProduct>() {
          @Override
          public AsVariantProduct read(String conditionalType, ResponseReader reader) {
            return asVariantProductFieldMapper.map(reader);
          }
        });
        return new Product(__typename, asVariantProduct);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable AsVariantProduct asVariantProduct;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder asVariantProduct(@Nullable AsVariantProduct asVariantProduct) {
        this.asVariantProduct = asVariantProduct;
        return this;
      }

      public Builder asVariantProduct(@Nonnull Mutator<AsVariantProduct.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        AsVariantProduct.Builder builder = this.asVariantProduct != null ? this.asVariantProduct.toBuilder() : AsVariantProduct.builder();
        mutator.accept(builder);
        this.asVariantProduct = builder.build();
        return this;
      }

      public Product build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Product(__typename, asVariantProduct);
      }
    }
  }

  public static class AsVariantProduct {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("variantProductAttributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("gtin", "gtin", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("summary", "summary", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("prices", "prices", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("tax", "tax", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("catalogue", "catalogue", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nonnull String ref;

    final @Nonnull String type;

    final @Nullable String status;

    final @Nullable List<VariantProductAttribute> variantProductAttributes;

    final @Nonnull String gtin;

    final @Nonnull String name;

    final @Nullable String summary;

    final @Nullable List<Price> prices;

    final @Nullable Tax tax;

    final @Nonnull Catalogue catalogue;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public AsVariantProduct(@Nonnull String __typename, @Nonnull String id, @Nonnull String ref,
        @Nonnull String type, @Nullable String status,
        @Nullable List<VariantProductAttribute> variantProductAttributes, @Nonnull String gtin,
        @Nonnull String name, @Nullable String summary, @Nullable List<Price> prices,
        @Nullable Tax tax, @Nonnull Catalogue catalogue) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.type = Utils.checkNotNull(type, "type == null");
      this.status = status;
      this.variantProductAttributes = variantProductAttributes;
      this.gtin = Utils.checkNotNull(gtin, "gtin == null");
      this.name = Utils.checkNotNull(name, "name == null");
      this.summary = summary;
      this.prices = prices;
      this.tax = tax;
      this.catalogue = Utils.checkNotNull(catalogue, "catalogue == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  ID of the object. For internal use, should not be used externally or by any business logic
     */
    public @Nonnull String id() {
      return this.id;
    }

    /**
     *  The unique reference identifier for the Product
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    /**
     *  Type of the `VariantProduct`, typically used by the Orchestration Engine to determine the workflow that should be applied. Unless stated otherwise, no values are enforced by the platform.<br/>
     */
    public @Nonnull String type() {
      return this.type;
    }

    /**
     *  The current status of the `VariantProduct`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
     */
    public @Nullable String status() {
      return this.status;
    }

    /**
     *  A list of attributes associated with this Product. This can be used to extend the existing data structure with additional data for use in orchestration rules, etc.
     */
    public @Nullable List<VariantProductAttribute> variantProductAttributes() {
      return this.variantProductAttributes;
    }

    /**
     *  The Global Trade Item Number (GTIN) for this Product
     */
    public @Nonnull String gtin() {
      return this.gtin;
    }

    /**
     *  The name of the Product
     */
    public @Nonnull String name() {
      return this.name;
    }

    /**
     *  A short description of the Product (max 255 chars)
     */
    public @Nullable String summary() {
      return this.summary;
    }

    /**
     *  A list of Prices for this Product
     */
    public @Nullable List<Price> prices() {
      return this.prices;
    }

    /**
     *  The tax information for this Product
     */
    public @Nullable Tax tax() {
      return this.tax;
    }

    /**
     *  The Product Catalogue in which this Product is managed
     */
    public @Nonnull Catalogue catalogue() {
      return this.catalogue;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeString($responseFields[3], type);
          writer.writeString($responseFields[4], status);
          writer.writeList($responseFields[5], variantProductAttributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((VariantProductAttribute) value).marshaller());
            }
          });
          writer.writeString($responseFields[6], gtin);
          writer.writeString($responseFields[7], name);
          writer.writeString($responseFields[8], summary);
          writer.writeList($responseFields[9], prices, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Price) value).marshaller());
            }
          });
          writer.writeObject($responseFields[10], tax != null ? tax.marshaller() : null);
          writer.writeObject($responseFields[11], catalogue.marshaller());
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "AsVariantProduct{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "type=" + type + ", "
          + "status=" + status + ", "
          + "variantProductAttributes=" + variantProductAttributes + ", "
          + "gtin=" + gtin + ", "
          + "name=" + name + ", "
          + "summary=" + summary + ", "
          + "prices=" + prices + ", "
          + "tax=" + tax + ", "
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
      if (o instanceof AsVariantProduct) {
        AsVariantProduct that = (AsVariantProduct) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && this.ref.equals(that.ref)
         && this.type.equals(that.type)
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.variantProductAttributes == null) ? (that.variantProductAttributes == null) : this.variantProductAttributes.equals(that.variantProductAttributes))
         && this.gtin.equals(that.gtin)
         && this.name.equals(that.name)
         && ((this.summary == null) ? (that.summary == null) : this.summary.equals(that.summary))
         && ((this.prices == null) ? (that.prices == null) : this.prices.equals(that.prices))
         && ((this.tax == null) ? (that.tax == null) : this.tax.equals(that.tax))
         && this.catalogue.equals(that.catalogue);
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
        h ^= type.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        h *= 1000003;
        h ^= (variantProductAttributes == null) ? 0 : variantProductAttributes.hashCode();
        h *= 1000003;
        h ^= gtin.hashCode();
        h *= 1000003;
        h ^= name.hashCode();
        h *= 1000003;
        h ^= (summary == null) ? 0 : summary.hashCode();
        h *= 1000003;
        h ^= (prices == null) ? 0 : prices.hashCode();
        h *= 1000003;
        h ^= (tax == null) ? 0 : tax.hashCode();
        h *= 1000003;
        h ^= catalogue.hashCode();
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
      builder.status = status;
      builder.variantProductAttributes = variantProductAttributes;
      builder.gtin = gtin;
      builder.name = name;
      builder.summary = summary;
      builder.prices = prices;
      builder.tax = tax;
      builder.catalogue = catalogue;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<AsVariantProduct> {
      final VariantProductAttribute.Mapper variantProductAttributeFieldMapper = new VariantProductAttribute.Mapper();

      final Price.Mapper priceFieldMapper = new Price.Mapper();

      final Tax.Mapper taxFieldMapper = new Tax.Mapper();

      final Catalogue.Mapper catalogueFieldMapper = new Catalogue.Mapper();

      @Override
      public AsVariantProduct map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        final List<VariantProductAttribute> variantProductAttributes = reader.readList($responseFields[5], new ResponseReader.ListReader<VariantProductAttribute>() {
          @Override
          public VariantProductAttribute read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<VariantProductAttribute>() {
              @Override
              public VariantProductAttribute read(ResponseReader reader) {
                return variantProductAttributeFieldMapper.map(reader);
              }
            });
          }
        });
        final String gtin = reader.readString($responseFields[6]);
        final String name = reader.readString($responseFields[7]);
        final String summary = reader.readString($responseFields[8]);
        final List<Price> prices = reader.readList($responseFields[9], new ResponseReader.ListReader<Price>() {
          @Override
          public Price read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<Price>() {
              @Override
              public Price read(ResponseReader reader) {
                return priceFieldMapper.map(reader);
              }
            });
          }
        });
        final Tax tax = reader.readObject($responseFields[10], new ResponseReader.ObjectReader<Tax>() {
          @Override
          public Tax read(ResponseReader reader) {
            return taxFieldMapper.map(reader);
          }
        });
        final Catalogue catalogue = reader.readObject($responseFields[11], new ResponseReader.ObjectReader<Catalogue>() {
          @Override
          public Catalogue read(ResponseReader reader) {
            return catalogueFieldMapper.map(reader);
          }
        });
        return new AsVariantProduct(__typename, id, ref, type, status, variantProductAttributes, gtin, name, summary, prices, tax, catalogue);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nonnull String ref;

      private @Nonnull String type;

      private @Nullable String status;

      private @Nullable List<VariantProductAttribute> variantProductAttributes;

      private @Nonnull String gtin;

      private @Nonnull String name;

      private @Nullable String summary;

      private @Nullable List<Price> prices;

      private @Nullable Tax tax;

      private @Nonnull Catalogue catalogue;

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

      public Builder type(@Nonnull String type) {
        this.type = type;
        return this;
      }

      public Builder status(@Nullable String status) {
        this.status = status;
        return this;
      }

      public Builder variantProductAttributes(@Nullable List<VariantProductAttribute> variantProductAttributes) {
        this.variantProductAttributes = variantProductAttributes;
        return this;
      }

      public Builder gtin(@Nonnull String gtin) {
        this.gtin = gtin;
        return this;
      }

      public Builder name(@Nonnull String name) {
        this.name = name;
        return this;
      }

      public Builder summary(@Nullable String summary) {
        this.summary = summary;
        return this;
      }

      public Builder prices(@Nullable List<Price> prices) {
        this.prices = prices;
        return this;
      }

      public Builder tax(@Nullable Tax tax) {
        this.tax = tax;
        return this;
      }

      public Builder catalogue(@Nonnull Catalogue catalogue) {
        this.catalogue = catalogue;
        return this;
      }

      public Builder variantProductAttributes(@Nonnull Mutator<List<VariantProductAttribute.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<VariantProductAttribute.Builder> builders = new ArrayList<>();
        if (this.variantProductAttributes != null) {
          for (VariantProductAttribute item : this.variantProductAttributes) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<VariantProductAttribute> variantProductAttributes = new ArrayList<>();
        for (VariantProductAttribute.Builder item : builders) {
          variantProductAttributes.add(item != null ? item.build() : null);
        }
        this.variantProductAttributes = variantProductAttributes;
        return this;
      }

      public Builder prices(@Nonnull Mutator<List<Price.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<Price.Builder> builders = new ArrayList<>();
        if (this.prices != null) {
          for (Price item : this.prices) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<Price> prices = new ArrayList<>();
        for (Price.Builder item : builders) {
          prices.add(item != null ? item.build() : null);
        }
        this.prices = prices;
        return this;
      }

      public Builder tax(@Nonnull Mutator<Tax.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Tax.Builder builder = this.tax != null ? this.tax.toBuilder() : Tax.builder();
        mutator.accept(builder);
        this.tax = builder.build();
        return this;
      }

      public Builder catalogue(@Nonnull Mutator<Catalogue.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Catalogue.Builder builder = this.catalogue != null ? this.catalogue.toBuilder() : Catalogue.builder();
        mutator.accept(builder);
        this.catalogue = builder.build();
        return this;
      }

      public AsVariantProduct build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(ref, "ref == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(gtin, "gtin == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(catalogue, "catalogue == null");
        return new AsVariantProduct(__typename, id, ref, type, status, variantProductAttributes, gtin, name, summary, prices, tax, catalogue);
      }
    }
  }

  public static class VariantProductAttribute {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("value", "value", null, false, CustomType.JSON, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String name;

    final @Nonnull String type;

    final @Nonnull Object value;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public VariantProductAttribute(@Nonnull String __typename, @Nonnull String name,
        @Nonnull String type, @Nonnull Object value) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.name = Utils.checkNotNull(name, "name == null");
      this.type = Utils.checkNotNull(type, "type == null");
      this.value = Utils.checkNotNull(value, "value == null");
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
     *  Type of the attribute's `value`. This is a free string and can be used by the client to interpret and cast the `value` into the appropriate type.
     */
    public @Nonnull String type() {
      return this.type;
    }

    /**
     *  Value of the `attribute`
     */
    public @Nonnull Object value() {
      return this.value;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], name);
          writer.writeString($responseFields[2], type);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], value);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "VariantProductAttribute{"
          + "__typename=" + __typename + ", "
          + "name=" + name + ", "
          + "type=" + type + ", "
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
      if (o instanceof VariantProductAttribute) {
        VariantProductAttribute that = (VariantProductAttribute) o;
        return this.__typename.equals(that.__typename)
         && this.name.equals(that.name)
         && this.type.equals(that.type)
         && this.value.equals(that.value);
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
        h ^= type.hashCode();
        h *= 1000003;
        h ^= value.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.name = name;
      builder.type = type;
      builder.value = value;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<VariantProductAttribute> {
      @Override
      public VariantProductAttribute map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String name = reader.readString($responseFields[1]);
        final String type = reader.readString($responseFields[2]);
        final Object value = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        return new VariantProductAttribute(__typename, name, type, value);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String name;

      private @Nonnull String type;

      private @Nonnull Object value;

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

      public Builder type(@Nonnull String type) {
        this.type = type;
        return this;
      }

      public Builder value(@Nonnull Object value) {
        this.value = value;
        return this;
      }

      public VariantProductAttribute build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(value, "value == null");
        return new VariantProductAttribute(__typename, name, type, value);
      }
    }
  }

  public static class Price {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("currency", "currency", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("value", "value", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String type;

    final @Nonnull String currency;

    final double value;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Price(@Nonnull String __typename, @Nonnull String type, @Nonnull String currency,
        double value) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.type = Utils.checkNotNull(type, "type == null");
      this.currency = Utils.checkNotNull(currency, "currency == null");
      this.value = value;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The type field is used to identify different types of prices, for example 'RRP', 'SALE', etc. No Price type values are enforced by the platform.
     */
    public @Nonnull String type() {
      return this.type;
    }

    /**
     *  The currency of the Price, for example 'USD', 'GBP', 'AUD', etc.
     */
    public @Nonnull String currency() {
      return this.currency;
    }

    /**
     *  The price value itself
     */
    public double value() {
      return this.value;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], type);
          writer.writeString($responseFields[2], currency);
          writer.writeDouble($responseFields[3], value);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Price{"
          + "__typename=" + __typename + ", "
          + "type=" + type + ", "
          + "currency=" + currency + ", "
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
      if (o instanceof Price) {
        Price that = (Price) o;
        return this.__typename.equals(that.__typename)
         && this.type.equals(that.type)
         && this.currency.equals(that.currency)
         && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(that.value);
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
        h ^= type.hashCode();
        h *= 1000003;
        h ^= currency.hashCode();
        h *= 1000003;
        h ^= Double.valueOf(value).hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.type = type;
      builder.currency = currency;
      builder.value = value;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Price> {
      @Override
      public Price map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String type = reader.readString($responseFields[1]);
        final String currency = reader.readString($responseFields[2]);
        final double value = reader.readDouble($responseFields[3]);
        return new Price(__typename, type, currency, value);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String type;

      private @Nonnull String currency;

      private double value;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder type(@Nonnull String type) {
        this.type = type;
        return this;
      }

      public Builder currency(@Nonnull String currency) {
        this.currency = currency;
        return this;
      }

      public Builder value(double value) {
        this.value = value;
        return this;
      }

      public Price build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(currency, "currency == null");
        return new Price(__typename, type, currency, value);
      }
    }
  }

  public static class Tax {
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

    public Tax(@Nonnull String __typename, @Nonnull String country, @Nonnull String group,
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
        $toString = "Tax{"
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
      if (o instanceof Tax) {
        Tax that = (Tax) o;
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

    public static final class Mapper implements ResponseFieldMapper<Tax> {
      @Override
      public Tax map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String country = reader.readString($responseFields[1]);
        final String group = reader.readString($responseFields[2]);
        final String tariff = reader.readString($responseFields[3]);
        return new Tax(__typename, country, group, tariff);
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

      public Tax build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(country, "country == null");
        Utils.checkNotNull(group, "group == null");
        return new Tax(__typename, country, group, tariff);
      }
    }
  }

  public static class Catalogue {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Catalogue(@Nonnull String __typename, @Nonnull String ref) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The unique reference identifier for the Product Catalogue
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

      public Catalogue build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(ref, "ref == null");
        return new Catalogue(__typename, ref);
      }
    }
  }

  public static class PageInfo {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forBoolean("hasNextPage", "hasNextPage", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Boolean hasNextPage;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public PageInfo(@Nonnull String __typename, @Nullable Boolean hasNextPage) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.hasNextPage = hasNextPage;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  true if there are one or more pages of items beyond the current page
     */
    public @Nullable Boolean hasNextPage() {
      return this.hasNextPage;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeBoolean($responseFields[1], hasNextPage);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "PageInfo{"
          + "__typename=" + __typename + ", "
          + "hasNextPage=" + hasNextPage
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof PageInfo) {
        PageInfo that = (PageInfo) o;
        return this.__typename.equals(that.__typename)
         && ((this.hasNextPage == null) ? (that.hasNextPage == null) : this.hasNextPage.equals(that.hasNextPage));
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
        h ^= (hasNextPage == null) ? 0 : hasNextPage.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.hasNextPage = hasNextPage;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<PageInfo> {
      @Override
      public PageInfo map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Boolean hasNextPage = reader.readBoolean($responseFields[1]);
        return new PageInfo(__typename, hasNextPage);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Boolean hasNextPage;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder hasNextPage(@Nullable Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
        return this;
      }

      public PageInfo build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new PageInfo(__typename, hasNextPage);
      }
    }
  }

  public static class Fulfilments {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("fulfilmentsEdges", "edges", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("pageInfo", "pageInfo", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<FulfilmentsEdge> fulfilmentsEdges;

    final @Nullable PageInfo1 pageInfo;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Fulfilments(@Nonnull String __typename, @Nullable List<FulfilmentsEdge> fulfilmentsEdges,
        @Nullable PageInfo1 pageInfo) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.fulfilmentsEdges = fulfilmentsEdges;
      this.pageInfo = pageInfo;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to Fulfilment type node
     */
    public @Nullable List<FulfilmentsEdge> fulfilmentsEdges() {
      return this.fulfilmentsEdges;
    }

    /**
     *  Information to aid in pagination
     */
    public @Nullable PageInfo1 pageInfo() {
      return this.pageInfo;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeList($responseFields[1], fulfilmentsEdges, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((FulfilmentsEdge) value).marshaller());
            }
          });
          writer.writeObject($responseFields[2], pageInfo != null ? pageInfo.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Fulfilments{"
          + "__typename=" + __typename + ", "
          + "fulfilmentsEdges=" + fulfilmentsEdges + ", "
          + "pageInfo=" + pageInfo
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Fulfilments) {
        Fulfilments that = (Fulfilments) o;
        return this.__typename.equals(that.__typename)
         && ((this.fulfilmentsEdges == null) ? (that.fulfilmentsEdges == null) : this.fulfilmentsEdges.equals(that.fulfilmentsEdges))
         && ((this.pageInfo == null) ? (that.pageInfo == null) : this.pageInfo.equals(that.pageInfo));
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
        h ^= (fulfilmentsEdges == null) ? 0 : fulfilmentsEdges.hashCode();
        h *= 1000003;
        h ^= (pageInfo == null) ? 0 : pageInfo.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.fulfilmentsEdges = fulfilmentsEdges;
      builder.pageInfo = pageInfo;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Fulfilments> {
      final FulfilmentsEdge.Mapper fulfilmentsEdgeFieldMapper = new FulfilmentsEdge.Mapper();

      final PageInfo1.Mapper pageInfo1FieldMapper = new PageInfo1.Mapper();

      @Override
      public Fulfilments map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final List<FulfilmentsEdge> fulfilmentsEdges = reader.readList($responseFields[1], new ResponseReader.ListReader<FulfilmentsEdge>() {
          @Override
          public FulfilmentsEdge read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<FulfilmentsEdge>() {
              @Override
              public FulfilmentsEdge read(ResponseReader reader) {
                return fulfilmentsEdgeFieldMapper.map(reader);
              }
            });
          }
        });
        final PageInfo1 pageInfo = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<PageInfo1>() {
          @Override
          public PageInfo1 read(ResponseReader reader) {
            return pageInfo1FieldMapper.map(reader);
          }
        });
        return new Fulfilments(__typename, fulfilmentsEdges, pageInfo);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable List<FulfilmentsEdge> fulfilmentsEdges;

      private @Nullable PageInfo1 pageInfo;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder fulfilmentsEdges(@Nullable List<FulfilmentsEdge> fulfilmentsEdges) {
        this.fulfilmentsEdges = fulfilmentsEdges;
        return this;
      }

      public Builder pageInfo(@Nullable PageInfo1 pageInfo) {
        this.pageInfo = pageInfo;
        return this;
      }

      public Builder fulfilmentsEdges(@Nonnull Mutator<List<FulfilmentsEdge.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<FulfilmentsEdge.Builder> builders = new ArrayList<>();
        if (this.fulfilmentsEdges != null) {
          for (FulfilmentsEdge item : this.fulfilmentsEdges) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<FulfilmentsEdge> fulfilmentsEdges = new ArrayList<>();
        for (FulfilmentsEdge.Builder item : builders) {
          fulfilmentsEdges.add(item != null ? item.build() : null);
        }
        this.fulfilmentsEdges = fulfilmentsEdges;
        return this;
      }

      public Builder pageInfo(@Nonnull Mutator<PageInfo1.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        PageInfo1.Builder builder = this.pageInfo != null ? this.pageInfo.toBuilder() : PageInfo1.builder();
        mutator.accept(builder);
        this.pageInfo = builder.build();
        return this;
      }

      public Fulfilments build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Fulfilments(__typename, fulfilmentsEdges, pageInfo);
      }
    }
  }

  public static class FulfilmentsEdge {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("fulfilmentsNode", "node", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("cursor", "cursor", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable FulfilmentsNode fulfilmentsNode;

    final @Nullable String cursor;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public FulfilmentsEdge(@Nonnull String __typename, @Nullable FulfilmentsNode fulfilmentsNode,
        @Nullable String cursor) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.fulfilmentsNode = fulfilmentsNode;
      this.cursor = cursor;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The item at the end of the Fulfilment edge
     */
    public @Nullable FulfilmentsNode fulfilmentsNode() {
      return this.fulfilmentsNode;
    }

    /**
     *  A cursor for use in pagination
     */
    public @Nullable String cursor() {
      return this.cursor;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeObject($responseFields[1], fulfilmentsNode != null ? fulfilmentsNode.marshaller() : null);
          writer.writeString($responseFields[2], cursor);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "FulfilmentsEdge{"
          + "__typename=" + __typename + ", "
          + "fulfilmentsNode=" + fulfilmentsNode + ", "
          + "cursor=" + cursor
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof FulfilmentsEdge) {
        FulfilmentsEdge that = (FulfilmentsEdge) o;
        return this.__typename.equals(that.__typename)
         && ((this.fulfilmentsNode == null) ? (that.fulfilmentsNode == null) : this.fulfilmentsNode.equals(that.fulfilmentsNode))
         && ((this.cursor == null) ? (that.cursor == null) : this.cursor.equals(that.cursor));
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
        h ^= (fulfilmentsNode == null) ? 0 : fulfilmentsNode.hashCode();
        h *= 1000003;
        h ^= (cursor == null) ? 0 : cursor.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.fulfilmentsNode = fulfilmentsNode;
      builder.cursor = cursor;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<FulfilmentsEdge> {
      final FulfilmentsNode.Mapper fulfilmentsNodeFieldMapper = new FulfilmentsNode.Mapper();

      @Override
      public FulfilmentsEdge map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final FulfilmentsNode fulfilmentsNode = reader.readObject($responseFields[1], new ResponseReader.ObjectReader<FulfilmentsNode>() {
          @Override
          public FulfilmentsNode read(ResponseReader reader) {
            return fulfilmentsNodeFieldMapper.map(reader);
          }
        });
        final String cursor = reader.readString($responseFields[2]);
        return new FulfilmentsEdge(__typename, fulfilmentsNode, cursor);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable FulfilmentsNode fulfilmentsNode;

      private @Nullable String cursor;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder fulfilmentsNode(@Nullable FulfilmentsNode fulfilmentsNode) {
        this.fulfilmentsNode = fulfilmentsNode;
        return this;
      }

      public Builder cursor(@Nullable String cursor) {
        this.cursor = cursor;
        return this;
      }

      public Builder fulfilmentsNode(@Nonnull Mutator<FulfilmentsNode.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        FulfilmentsNode.Builder builder = this.fulfilmentsNode != null ? this.fulfilmentsNode.toBuilder() : FulfilmentsNode.builder();
        mutator.accept(builder);
        this.fulfilmentsNode = builder.build();
        return this;
      }

      public FulfilmentsEdge build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new FulfilmentsEdge(__typename, fulfilmentsNode, cursor);
      }
    }
  }

  public static class FulfilmentsNode {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("updatedOn", "updatedOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("fulfilmentAttributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("deliveryType", "deliveryType", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("fromLocation", "fromLocation", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("fromAddress", "fromAddress", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("toAddress", "toAddress", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final @Nonnull String type;

    final @Nullable String status;

    final @Nullable Object createdOn;

    final @Nullable Object updatedOn;

    final @Nullable List<FulfilmentAttribute> fulfilmentAttributes;

    final @Nullable String deliveryType;

    final @Nullable FromLocation fromLocation;

    final @Nullable FromAddress fromAddress;

    final @Nullable ToAddress toAddress;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public FulfilmentsNode(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        @Nonnull String type, @Nullable String status, @Nullable Object createdOn,
        @Nullable Object updatedOn, @Nullable List<FulfilmentAttribute> fulfilmentAttributes,
        @Nullable String deliveryType, @Nullable FromLocation fromLocation,
        @Nullable FromAddress fromAddress, @Nullable ToAddress toAddress) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.type = Utils.checkNotNull(type, "type == null");
      this.status = status;
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.fulfilmentAttributes = fulfilmentAttributes;
      this.deliveryType = deliveryType;
      this.fromLocation = fromLocation;
      this.fromAddress = fromAddress;
      this.toAddress = toAddress;
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
     *  External reference of the object. Recommended to be unique.
     */
    public @Nullable String ref() {
      return this.ref;
    }

    /**
     *  Type of the `Fulfilment`, typically used by the Orchestration Engine to determine the workflow that should be applied. Unless stated otherwise, no values are enforced by the platform.<br/>
     *  Type of the Fulfilment. Supports all values.
     */
    public @Nonnull String type() {
      return this.type;
    }

    /**
     *  The current status of the `Fulfilment`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
     */
    public @Nullable String status() {
      return this.status;
    }

    /**
     *  Time of creation
     */
    public @Nullable Object createdOn() {
      return this.createdOn;
    }

    /**
     *  Time of last update
     */
    public @Nullable Object updatedOn() {
      return this.updatedOn;
    }

    /**
     *  Attributes of fulfilment
     */
    public @Nullable List<FulfilmentAttribute> fulfilmentAttributes() {
      return this.fulfilmentAttributes;
    }

    /**
     *  Type of delivery. Supported values are _STANDARD_, _OVERNIGHT_ and _EXPRESS_.
     */
    public @Nullable String deliveryType() {
      return this.deliveryType;
    }

    /**
     *  The `Location` responsible for processing outbound `Fulfilment`s
     */
    public @Nullable FromLocation fromLocation() {
      return this.fromLocation;
    }

    /**
     *  `Address` of the fulfilment location
     */
    public @Nullable FromAddress fromAddress() {
      return this.fromAddress;
    }

    /**
     *  `Address` of the delivery location
     */
    public @Nullable ToAddress toAddress() {
      return this.toAddress;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeString($responseFields[3], type);
          writer.writeString($responseFields[4], status);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[5], createdOn);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[6], updatedOn);
          writer.writeList($responseFields[7], fulfilmentAttributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((FulfilmentAttribute) value).marshaller());
            }
          });
          writer.writeString($responseFields[8], deliveryType);
          writer.writeObject($responseFields[9], fromLocation != null ? fromLocation.marshaller() : null);
          writer.writeObject($responseFields[10], fromAddress != null ? fromAddress.marshaller() : null);
          writer.writeObject($responseFields[11], toAddress != null ? toAddress.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "FulfilmentsNode{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "type=" + type + ", "
          + "status=" + status + ", "
          + "createdOn=" + createdOn + ", "
          + "updatedOn=" + updatedOn + ", "
          + "fulfilmentAttributes=" + fulfilmentAttributes + ", "
          + "deliveryType=" + deliveryType + ", "
          + "fromLocation=" + fromLocation + ", "
          + "fromAddress=" + fromAddress + ", "
          + "toAddress=" + toAddress
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof FulfilmentsNode) {
        FulfilmentsNode that = (FulfilmentsNode) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && this.type.equals(that.type)
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.updatedOn == null) ? (that.updatedOn == null) : this.updatedOn.equals(that.updatedOn))
         && ((this.fulfilmentAttributes == null) ? (that.fulfilmentAttributes == null) : this.fulfilmentAttributes.equals(that.fulfilmentAttributes))
         && ((this.deliveryType == null) ? (that.deliveryType == null) : this.deliveryType.equals(that.deliveryType))
         && ((this.fromLocation == null) ? (that.fromLocation == null) : this.fromLocation.equals(that.fromLocation))
         && ((this.fromAddress == null) ? (that.fromAddress == null) : this.fromAddress.equals(that.fromAddress))
         && ((this.toAddress == null) ? (that.toAddress == null) : this.toAddress.equals(that.toAddress));
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
        h ^= (ref == null) ? 0 : ref.hashCode();
        h *= 1000003;
        h ^= type.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        h *= 1000003;
        h ^= (createdOn == null) ? 0 : createdOn.hashCode();
        h *= 1000003;
        h ^= (updatedOn == null) ? 0 : updatedOn.hashCode();
        h *= 1000003;
        h ^= (fulfilmentAttributes == null) ? 0 : fulfilmentAttributes.hashCode();
        h *= 1000003;
        h ^= (deliveryType == null) ? 0 : deliveryType.hashCode();
        h *= 1000003;
        h ^= (fromLocation == null) ? 0 : fromLocation.hashCode();
        h *= 1000003;
        h ^= (fromAddress == null) ? 0 : fromAddress.hashCode();
        h *= 1000003;
        h ^= (toAddress == null) ? 0 : toAddress.hashCode();
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
      builder.status = status;
      builder.createdOn = createdOn;
      builder.updatedOn = updatedOn;
      builder.fulfilmentAttributes = fulfilmentAttributes;
      builder.deliveryType = deliveryType;
      builder.fromLocation = fromLocation;
      builder.fromAddress = fromAddress;
      builder.toAddress = toAddress;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<FulfilmentsNode> {
      final FulfilmentAttribute.Mapper fulfilmentAttributeFieldMapper = new FulfilmentAttribute.Mapper();

      final FromLocation.Mapper fromLocationFieldMapper = new FromLocation.Mapper();

      final FromAddress.Mapper fromAddressFieldMapper = new FromAddress.Mapper();

      final ToAddress.Mapper toAddressFieldMapper = new ToAddress.Mapper();

      @Override
      public FulfilmentsNode map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[5]);
        final Object updatedOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[6]);
        final List<FulfilmentAttribute> fulfilmentAttributes = reader.readList($responseFields[7], new ResponseReader.ListReader<FulfilmentAttribute>() {
          @Override
          public FulfilmentAttribute read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<FulfilmentAttribute>() {
              @Override
              public FulfilmentAttribute read(ResponseReader reader) {
                return fulfilmentAttributeFieldMapper.map(reader);
              }
            });
          }
        });
        final String deliveryType = reader.readString($responseFields[8]);
        final FromLocation fromLocation = reader.readObject($responseFields[9], new ResponseReader.ObjectReader<FromLocation>() {
          @Override
          public FromLocation read(ResponseReader reader) {
            return fromLocationFieldMapper.map(reader);
          }
        });
        final FromAddress fromAddress = reader.readObject($responseFields[10], new ResponseReader.ObjectReader<FromAddress>() {
          @Override
          public FromAddress read(ResponseReader reader) {
            return fromAddressFieldMapper.map(reader);
          }
        });
        final ToAddress toAddress = reader.readObject($responseFields[11], new ResponseReader.ObjectReader<ToAddress>() {
          @Override
          public ToAddress read(ResponseReader reader) {
            return toAddressFieldMapper.map(reader);
          }
        });
        return new FulfilmentsNode(__typename, id, ref, type, status, createdOn, updatedOn, fulfilmentAttributes, deliveryType, fromLocation, fromAddress, toAddress);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String ref;

      private @Nonnull String type;

      private @Nullable String status;

      private @Nullable Object createdOn;

      private @Nullable Object updatedOn;

      private @Nullable List<FulfilmentAttribute> fulfilmentAttributes;

      private @Nullable String deliveryType;

      private @Nullable FromLocation fromLocation;

      private @Nullable FromAddress fromAddress;

      private @Nullable ToAddress toAddress;

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

      public Builder ref(@Nullable String ref) {
        this.ref = ref;
        return this;
      }

      public Builder type(@Nonnull String type) {
        this.type = type;
        return this;
      }

      public Builder status(@Nullable String status) {
        this.status = status;
        return this;
      }

      public Builder createdOn(@Nullable Object createdOn) {
        this.createdOn = createdOn;
        return this;
      }

      public Builder updatedOn(@Nullable Object updatedOn) {
        this.updatedOn = updatedOn;
        return this;
      }

      public Builder fulfilmentAttributes(@Nullable List<FulfilmentAttribute> fulfilmentAttributes) {
        this.fulfilmentAttributes = fulfilmentAttributes;
        return this;
      }

      public Builder deliveryType(@Nullable String deliveryType) {
        this.deliveryType = deliveryType;
        return this;
      }

      public Builder fromLocation(@Nullable FromLocation fromLocation) {
        this.fromLocation = fromLocation;
        return this;
      }

      public Builder fromAddress(@Nullable FromAddress fromAddress) {
        this.fromAddress = fromAddress;
        return this;
      }

      public Builder toAddress(@Nullable ToAddress toAddress) {
        this.toAddress = toAddress;
        return this;
      }

      public Builder fulfilmentAttributes(@Nonnull Mutator<List<FulfilmentAttribute.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<FulfilmentAttribute.Builder> builders = new ArrayList<>();
        if (this.fulfilmentAttributes != null) {
          for (FulfilmentAttribute item : this.fulfilmentAttributes) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<FulfilmentAttribute> fulfilmentAttributes = new ArrayList<>();
        for (FulfilmentAttribute.Builder item : builders) {
          fulfilmentAttributes.add(item != null ? item.build() : null);
        }
        this.fulfilmentAttributes = fulfilmentAttributes;
        return this;
      }

      public Builder fromLocation(@Nonnull Mutator<FromLocation.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        FromLocation.Builder builder = this.fromLocation != null ? this.fromLocation.toBuilder() : FromLocation.builder();
        mutator.accept(builder);
        this.fromLocation = builder.build();
        return this;
      }

      public Builder fromAddress(@Nonnull Mutator<FromAddress.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        FromAddress.Builder builder = this.fromAddress != null ? this.fromAddress.toBuilder() : FromAddress.builder();
        mutator.accept(builder);
        this.fromAddress = builder.build();
        return this;
      }

      public Builder toAddress(@Nonnull Mutator<ToAddress.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ToAddress.Builder builder = this.toAddress != null ? this.toAddress.toBuilder() : ToAddress.builder();
        mutator.accept(builder);
        this.toAddress = builder.build();
        return this;
      }

      public FulfilmentsNode build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(type, "type == null");
        return new FulfilmentsNode(__typename, id, ref, type, status, createdOn, updatedOn, fulfilmentAttributes, deliveryType, fromLocation, fromAddress, toAddress);
      }
    }
  }

  public static class FulfilmentAttribute {
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

    public FulfilmentAttribute(@Nonnull String __typename, @Nonnull String name,
        @Nonnull Object value, @Nonnull String type) {
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
        $toString = "FulfilmentAttribute{"
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
      if (o instanceof FulfilmentAttribute) {
        FulfilmentAttribute that = (FulfilmentAttribute) o;
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

    public static final class Mapper implements ResponseFieldMapper<FulfilmentAttribute> {
      @Override
      public FulfilmentAttribute map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String name = reader.readString($responseFields[1]);
        final Object value = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        return new FulfilmentAttribute(__typename, name, value, type);
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

      public FulfilmentAttribute build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(value, "value == null");
        Utils.checkNotNull(type, "type == null");
        return new FulfilmentAttribute(__typename, name, value, type);
      }
    }
  }

  public static class FromLocation {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public FromLocation(@Nonnull String __typename, @Nullable String ref) {
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
        $toString = "FromLocation{"
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
      if (o instanceof FromLocation) {
        FromLocation that = (FromLocation) o;
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

    public static final class Mapper implements ResponseFieldMapper<FromLocation> {
      @Override
      public FromLocation map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        return new FromLocation(__typename, ref);
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

      public FromLocation build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new FromLocation(__typename, ref);
      }
    }
  }

  public static class FromAddress {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    final @Nonnull String id;

    final @Nullable String name;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public FromAddress(@Nonnull String __typename, @Nullable String ref, @Nonnull String id,
        @Nullable String name) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
      this.id = Utils.checkNotNull(id, "id == null");
      this.name = name;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  Location reference
     */
    public @Nullable String ref() {
      return this.ref;
    }

    /**
     *  ID of the object
     */
    public @Nonnull String id() {
      return this.id;
    }

    /**
     *  Name
     */
    public @Nullable String name() {
      return this.name;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], id);
          writer.writeString($responseFields[3], name);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "FromAddress{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "id=" + id + ", "
          + "name=" + name
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof FromAddress) {
        FromAddress that = (FromAddress) o;
        return this.__typename.equals(that.__typename)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && this.id.equals(that.id)
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name));
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
        h ^= id.hashCode();
        h *= 1000003;
        h ^= (name == null) ? 0 : name.hashCode();
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
      builder.name = name;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<FromAddress> {
      @Override
      public FromAddress map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        final String name = reader.readString($responseFields[3]);
        return new FromAddress(__typename, ref, id, name);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      private @Nonnull String id;

      private @Nullable String name;

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

      public Builder id(@Nonnull String id) {
        this.id = id;
        return this;
      }

      public Builder name(@Nullable String name) {
        this.name = name;
        return this;
      }

      public FromAddress build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new FromAddress(__typename, ref, id, name);
      }
    }
  }

  public static class ToAddress {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    final @Nonnull String id;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ToAddress(@Nonnull String __typename, @Nullable String ref, @Nonnull String id) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
      this.id = Utils.checkNotNull(id, "id == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  Location reference
     */
    public @Nullable String ref() {
      return this.ref;
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
          writer.writeString($responseFields[1], ref);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], id);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ToAddress{"
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
      if (o instanceof ToAddress) {
        ToAddress that = (ToAddress) o;
        return this.__typename.equals(that.__typename)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
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
        h ^= (ref == null) ? 0 : ref.hashCode();
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

    public static final class Mapper implements ResponseFieldMapper<ToAddress> {
      @Override
      public ToAddress map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        return new ToAddress(__typename, ref, id);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      private @Nonnull String id;

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

      public Builder id(@Nonnull String id) {
        this.id = id;
        return this;
      }

      public ToAddress build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new ToAddress(__typename, ref, id);
      }
    }
  }

  public static class PageInfo1 {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forBoolean("hasNextPage", "hasNextPage", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Boolean hasNextPage;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public PageInfo1(@Nonnull String __typename, @Nullable Boolean hasNextPage) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.hasNextPage = hasNextPage;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  true if there are one or more pages of items beyond the current page
     */
    public @Nullable Boolean hasNextPage() {
      return this.hasNextPage;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeBoolean($responseFields[1], hasNextPage);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "PageInfo1{"
          + "__typename=" + __typename + ", "
          + "hasNextPage=" + hasNextPage
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof PageInfo1) {
        PageInfo1 that = (PageInfo1) o;
        return this.__typename.equals(that.__typename)
         && ((this.hasNextPage == null) ? (that.hasNextPage == null) : this.hasNextPage.equals(that.hasNextPage));
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
        h ^= (hasNextPage == null) ? 0 : hasNextPage.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.hasNextPage = hasNextPage;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<PageInfo1> {
      @Override
      public PageInfo1 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Boolean hasNextPage = reader.readBoolean($responseFields[1]);
        return new PageInfo1(__typename, hasNextPage);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Boolean hasNextPage;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder hasNextPage(@Nullable Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
        return this;
      }

      public PageInfo1 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new PageInfo1(__typename, hasNextPage);
      }
    }
  }

  public static class Customer {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("title", "title", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("country", "country", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("firstName", "firstName", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("lastName", "lastName", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("username", "username", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("primaryEmail", "primaryEmail", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("primaryPhone", "primaryPhone", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final @Nullable String title;

    final @Nullable String country;

    final @Nullable String firstName;

    final @Nullable String lastName;

    final @Nullable String username;

    final @Nullable String primaryEmail;

    final @Nullable String primaryPhone;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Customer(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        @Nullable String title, @Nullable String country, @Nullable String firstName,
        @Nullable String lastName, @Nullable String username, @Nullable String primaryEmail,
        @Nullable String primaryPhone) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.title = title;
      this.country = country;
      this.firstName = firstName;
      this.lastName = lastName;
      this.username = username;
      this.primaryEmail = primaryEmail;
      this.primaryPhone = primaryPhone;
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
     *  Username of the customer
     */
    public @Nullable String ref() {
      return this.ref;
    }

    /**
     *  The customer's title
     */
    public @Nullable String title() {
      return this.title;
    }

    /**
     *  The country the customer is operating from
     */
    public @Nullable String country() {
      return this.country;
    }

    /**
     *  The customer's first name
     */
    public @Nullable String firstName() {
      return this.firstName;
    }

    /**
     *  The customer's last name
     */
    public @Nullable String lastName() {
      return this.lastName;
    }

    /**
     *  Username
     */
    public @Nullable String username() {
      return this.username;
    }

    /**
     *  Email
     */
    public @Nullable String primaryEmail() {
      return this.primaryEmail;
    }

    /**
     *  Phone number
     */
    public @Nullable String primaryPhone() {
      return this.primaryPhone;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeString($responseFields[3], title);
          writer.writeString($responseFields[4], country);
          writer.writeString($responseFields[5], firstName);
          writer.writeString($responseFields[6], lastName);
          writer.writeString($responseFields[7], username);
          writer.writeString($responseFields[8], primaryEmail);
          writer.writeString($responseFields[9], primaryPhone);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Customer{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "title=" + title + ", "
          + "country=" + country + ", "
          + "firstName=" + firstName + ", "
          + "lastName=" + lastName + ", "
          + "username=" + username + ", "
          + "primaryEmail=" + primaryEmail + ", "
          + "primaryPhone=" + primaryPhone
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Customer) {
        Customer that = (Customer) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && ((this.title == null) ? (that.title == null) : this.title.equals(that.title))
         && ((this.country == null) ? (that.country == null) : this.country.equals(that.country))
         && ((this.firstName == null) ? (that.firstName == null) : this.firstName.equals(that.firstName))
         && ((this.lastName == null) ? (that.lastName == null) : this.lastName.equals(that.lastName))
         && ((this.username == null) ? (that.username == null) : this.username.equals(that.username))
         && ((this.primaryEmail == null) ? (that.primaryEmail == null) : this.primaryEmail.equals(that.primaryEmail))
         && ((this.primaryPhone == null) ? (that.primaryPhone == null) : this.primaryPhone.equals(that.primaryPhone));
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
        h ^= (ref == null) ? 0 : ref.hashCode();
        h *= 1000003;
        h ^= (title == null) ? 0 : title.hashCode();
        h *= 1000003;
        h ^= (country == null) ? 0 : country.hashCode();
        h *= 1000003;
        h ^= (firstName == null) ? 0 : firstName.hashCode();
        h *= 1000003;
        h ^= (lastName == null) ? 0 : lastName.hashCode();
        h *= 1000003;
        h ^= (username == null) ? 0 : username.hashCode();
        h *= 1000003;
        h ^= (primaryEmail == null) ? 0 : primaryEmail.hashCode();
        h *= 1000003;
        h ^= (primaryPhone == null) ? 0 : primaryPhone.hashCode();
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
      builder.title = title;
      builder.country = country;
      builder.firstName = firstName;
      builder.lastName = lastName;
      builder.username = username;
      builder.primaryEmail = primaryEmail;
      builder.primaryPhone = primaryPhone;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Customer> {
      @Override
      public Customer map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String title = reader.readString($responseFields[3]);
        final String country = reader.readString($responseFields[4]);
        final String firstName = reader.readString($responseFields[5]);
        final String lastName = reader.readString($responseFields[6]);
        final String username = reader.readString($responseFields[7]);
        final String primaryEmail = reader.readString($responseFields[8]);
        final String primaryPhone = reader.readString($responseFields[9]);
        return new Customer(__typename, id, ref, title, country, firstName, lastName, username, primaryEmail, primaryPhone);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String ref;

      private @Nullable String title;

      private @Nullable String country;

      private @Nullable String firstName;

      private @Nullable String lastName;

      private @Nullable String username;

      private @Nullable String primaryEmail;

      private @Nullable String primaryPhone;

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

      public Builder ref(@Nullable String ref) {
        this.ref = ref;
        return this;
      }

      public Builder title(@Nullable String title) {
        this.title = title;
        return this;
      }

      public Builder country(@Nullable String country) {
        this.country = country;
        return this;
      }

      public Builder firstName(@Nullable String firstName) {
        this.firstName = firstName;
        return this;
      }

      public Builder lastName(@Nullable String lastName) {
        this.lastName = lastName;
        return this;
      }

      public Builder username(@Nullable String username) {
        this.username = username;
        return this;
      }

      public Builder primaryEmail(@Nullable String primaryEmail) {
        this.primaryEmail = primaryEmail;
        return this;
      }

      public Builder primaryPhone(@Nullable String primaryPhone) {
        this.primaryPhone = primaryPhone;
        return this;
      }

      public Customer build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new Customer(__typename, id, ref, title, country, firstName, lastName, username, primaryEmail, primaryPhone);
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

  public static class FulfilmentChoice {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("updatedOn", "updatedOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("currency", "currency", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("deliveryInstruction", "deliveryInstruction", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("fulfilmentPrice", "fulfilmentPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("deliveryType", "deliveryType", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("fulfilmentType", "fulfilmentType", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("fulfilmentTaxPrice", "fulfilmentTaxPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("pickupLocationRef", "pickupLocationRef", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("deliveryAddress", "deliveryAddress", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable Object createdOn;

    final @Nullable Object updatedOn;

    final @Nullable String currency;

    final @Nullable String deliveryInstruction;

    final @Nullable Double fulfilmentPrice;

    final @Nonnull String deliveryType;

    final @Nullable String fulfilmentType;

    final @Nullable Double fulfilmentTaxPrice;

    final @Nullable String pickupLocationRef;

    final @Nullable DeliveryAddress deliveryAddress;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public FulfilmentChoice(@Nonnull String __typename, @Nonnull String id,
        @Nullable Object createdOn, @Nullable Object updatedOn, @Nullable String currency,
        @Nullable String deliveryInstruction, @Nullable Double fulfilmentPrice,
        @Nonnull String deliveryType, @Nullable String fulfilmentType,
        @Nullable Double fulfilmentTaxPrice, @Nullable String pickupLocationRef,
        @Nullable DeliveryAddress deliveryAddress) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.currency = currency;
      this.deliveryInstruction = deliveryInstruction;
      this.fulfilmentPrice = fulfilmentPrice;
      this.deliveryType = Utils.checkNotNull(deliveryType, "deliveryType == null");
      this.fulfilmentType = fulfilmentType;
      this.fulfilmentTaxPrice = fulfilmentTaxPrice;
      this.pickupLocationRef = pickupLocationRef;
      this.deliveryAddress = deliveryAddress;
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
     *  Time of creation.
     */
    public @Nullable Object createdOn() {
      return this.createdOn;
    }

    /**
     *  Time of last update.
     */
    public @Nullable Object updatedOn() {
      return this.updatedOn;
    }

    /**
     *  The type of currency, 3 letter ISO currency code.
     */
    public @Nullable String currency() {
      return this.currency;
    }

    /**
     *  Instruction provided by the customer (250 character limit).
     */
    public @Nullable String deliveryInstruction() {
      return this.deliveryInstruction;
    }

    /**
     *  FulfilmentPrice refers to shipping and C&C fees.
     */
    public @Nullable Double fulfilmentPrice() {
      return this.fulfilmentPrice;
    }

    /**
     *  The type of delivery determined by retailers' shipping options. Example values are STANDARD, EXPRESS, OVERNIGHT, 3HOURS
     */
    public @Nonnull String deliveryType() {
      return this.deliveryType;
    }

    /**
     *  Indicates the type of fulfilment.
     */
    public @Nullable String fulfilmentType() {
      return this.fulfilmentType;
    }

    /**
     *  This refers to the tax cost associated with the fulfilment price.
     */
    public @Nullable Double fulfilmentTaxPrice() {
      return this.fulfilmentTaxPrice;
    }

    /**
     *  Pickup location. Required if it is a click & collect.
     */
    public @Nullable String pickupLocationRef() {
      return this.pickupLocationRef;
    }

    /**
     *  Delivery address. Required if it is a home delivery.
     */
    public @Nullable DeliveryAddress deliveryAddress() {
      return this.deliveryAddress;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], createdOn);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], updatedOn);
          writer.writeString($responseFields[4], currency);
          writer.writeString($responseFields[5], deliveryInstruction);
          writer.writeDouble($responseFields[6], fulfilmentPrice);
          writer.writeString($responseFields[7], deliveryType);
          writer.writeString($responseFields[8], fulfilmentType);
          writer.writeDouble($responseFields[9], fulfilmentTaxPrice);
          writer.writeString($responseFields[10], pickupLocationRef);
          writer.writeObject($responseFields[11], deliveryAddress != null ? deliveryAddress.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "FulfilmentChoice{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "createdOn=" + createdOn + ", "
          + "updatedOn=" + updatedOn + ", "
          + "currency=" + currency + ", "
          + "deliveryInstruction=" + deliveryInstruction + ", "
          + "fulfilmentPrice=" + fulfilmentPrice + ", "
          + "deliveryType=" + deliveryType + ", "
          + "fulfilmentType=" + fulfilmentType + ", "
          + "fulfilmentTaxPrice=" + fulfilmentTaxPrice + ", "
          + "pickupLocationRef=" + pickupLocationRef + ", "
          + "deliveryAddress=" + deliveryAddress
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof FulfilmentChoice) {
        FulfilmentChoice that = (FulfilmentChoice) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.updatedOn == null) ? (that.updatedOn == null) : this.updatedOn.equals(that.updatedOn))
         && ((this.currency == null) ? (that.currency == null) : this.currency.equals(that.currency))
         && ((this.deliveryInstruction == null) ? (that.deliveryInstruction == null) : this.deliveryInstruction.equals(that.deliveryInstruction))
         && ((this.fulfilmentPrice == null) ? (that.fulfilmentPrice == null) : this.fulfilmentPrice.equals(that.fulfilmentPrice))
         && this.deliveryType.equals(that.deliveryType)
         && ((this.fulfilmentType == null) ? (that.fulfilmentType == null) : this.fulfilmentType.equals(that.fulfilmentType))
         && ((this.fulfilmentTaxPrice == null) ? (that.fulfilmentTaxPrice == null) : this.fulfilmentTaxPrice.equals(that.fulfilmentTaxPrice))
         && ((this.pickupLocationRef == null) ? (that.pickupLocationRef == null) : this.pickupLocationRef.equals(that.pickupLocationRef))
         && ((this.deliveryAddress == null) ? (that.deliveryAddress == null) : this.deliveryAddress.equals(that.deliveryAddress));
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
        h ^= (createdOn == null) ? 0 : createdOn.hashCode();
        h *= 1000003;
        h ^= (updatedOn == null) ? 0 : updatedOn.hashCode();
        h *= 1000003;
        h ^= (currency == null) ? 0 : currency.hashCode();
        h *= 1000003;
        h ^= (deliveryInstruction == null) ? 0 : deliveryInstruction.hashCode();
        h *= 1000003;
        h ^= (fulfilmentPrice == null) ? 0 : fulfilmentPrice.hashCode();
        h *= 1000003;
        h ^= deliveryType.hashCode();
        h *= 1000003;
        h ^= (fulfilmentType == null) ? 0 : fulfilmentType.hashCode();
        h *= 1000003;
        h ^= (fulfilmentTaxPrice == null) ? 0 : fulfilmentTaxPrice.hashCode();
        h *= 1000003;
        h ^= (pickupLocationRef == null) ? 0 : pickupLocationRef.hashCode();
        h *= 1000003;
        h ^= (deliveryAddress == null) ? 0 : deliveryAddress.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.id = id;
      builder.createdOn = createdOn;
      builder.updatedOn = updatedOn;
      builder.currency = currency;
      builder.deliveryInstruction = deliveryInstruction;
      builder.fulfilmentPrice = fulfilmentPrice;
      builder.deliveryType = deliveryType;
      builder.fulfilmentType = fulfilmentType;
      builder.fulfilmentTaxPrice = fulfilmentTaxPrice;
      builder.pickupLocationRef = pickupLocationRef;
      builder.deliveryAddress = deliveryAddress;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<FulfilmentChoice> {
      final DeliveryAddress.Mapper deliveryAddressFieldMapper = new DeliveryAddress.Mapper();

      @Override
      public FulfilmentChoice map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        final Object updatedOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        final String currency = reader.readString($responseFields[4]);
        final String deliveryInstruction = reader.readString($responseFields[5]);
        final Double fulfilmentPrice = reader.readDouble($responseFields[6]);
        final String deliveryType = reader.readString($responseFields[7]);
        final String fulfilmentType = reader.readString($responseFields[8]);
        final Double fulfilmentTaxPrice = reader.readDouble($responseFields[9]);
        final String pickupLocationRef = reader.readString($responseFields[10]);
        final DeliveryAddress deliveryAddress = reader.readObject($responseFields[11], new ResponseReader.ObjectReader<DeliveryAddress>() {
          @Override
          public DeliveryAddress read(ResponseReader reader) {
            return deliveryAddressFieldMapper.map(reader);
          }
        });
        return new FulfilmentChoice(__typename, id, createdOn, updatedOn, currency, deliveryInstruction, fulfilmentPrice, deliveryType, fulfilmentType, fulfilmentTaxPrice, pickupLocationRef, deliveryAddress);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable Object createdOn;

      private @Nullable Object updatedOn;

      private @Nullable String currency;

      private @Nullable String deliveryInstruction;

      private @Nullable Double fulfilmentPrice;

      private @Nonnull String deliveryType;

      private @Nullable String fulfilmentType;

      private @Nullable Double fulfilmentTaxPrice;

      private @Nullable String pickupLocationRef;

      private @Nullable DeliveryAddress deliveryAddress;

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

      public Builder createdOn(@Nullable Object createdOn) {
        this.createdOn = createdOn;
        return this;
      }

      public Builder updatedOn(@Nullable Object updatedOn) {
        this.updatedOn = updatedOn;
        return this;
      }

      public Builder currency(@Nullable String currency) {
        this.currency = currency;
        return this;
      }

      public Builder deliveryInstruction(@Nullable String deliveryInstruction) {
        this.deliveryInstruction = deliveryInstruction;
        return this;
      }

      public Builder fulfilmentPrice(@Nullable Double fulfilmentPrice) {
        this.fulfilmentPrice = fulfilmentPrice;
        return this;
      }

      public Builder deliveryType(@Nonnull String deliveryType) {
        this.deliveryType = deliveryType;
        return this;
      }

      public Builder fulfilmentType(@Nullable String fulfilmentType) {
        this.fulfilmentType = fulfilmentType;
        return this;
      }

      public Builder fulfilmentTaxPrice(@Nullable Double fulfilmentTaxPrice) {
        this.fulfilmentTaxPrice = fulfilmentTaxPrice;
        return this;
      }

      public Builder pickupLocationRef(@Nullable String pickupLocationRef) {
        this.pickupLocationRef = pickupLocationRef;
        return this;
      }

      public Builder deliveryAddress(@Nullable DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
      }

      public Builder deliveryAddress(@Nonnull Mutator<DeliveryAddress.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        DeliveryAddress.Builder builder = this.deliveryAddress != null ? this.deliveryAddress.toBuilder() : DeliveryAddress.builder();
        mutator.accept(builder);
        this.deliveryAddress = builder.build();
        return this;
      }

      public FulfilmentChoice build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(deliveryType, "deliveryType == null");
        return new FulfilmentChoice(__typename, id, createdOn, updatedOn, currency, deliveryInstruction, fulfilmentPrice, deliveryType, fulfilmentType, fulfilmentTaxPrice, pickupLocationRef, deliveryAddress);
      }
    }
  }

  public static class DeliveryAddress {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("companyName", "companyName", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("street", "street", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("city", "city", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("state", "state", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("postcode", "postcode", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("region", "region", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("country", "country", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("latitude", "latitude", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("longitude", "longitude", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("updatedOn", "updatedOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String type;

    final @Nullable String companyName;

    final @Nullable String name;

    final @Nullable String street;

    final @Nullable String city;

    final @Nullable String state;

    final @Nullable String postcode;

    final @Nullable String region;

    final @Nullable String country;

    final @Nullable String ref;

    final @Nullable Double latitude;

    final @Nullable Double longitude;

    final @Nullable Object createdOn;

    final @Nullable Object updatedOn;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public DeliveryAddress(@Nonnull String __typename, @Nonnull String id, @Nullable String type,
        @Nullable String companyName, @Nullable String name, @Nullable String street,
        @Nullable String city, @Nullable String state, @Nullable String postcode,
        @Nullable String region, @Nullable String country, @Nullable String ref,
        @Nullable Double latitude, @Nullable Double longitude, @Nullable Object createdOn,
        @Nullable Object updatedOn) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.type = type;
      this.companyName = companyName;
      this.name = name;
      this.street = street;
      this.city = city;
      this.state = state;
      this.postcode = postcode;
      this.region = region;
      this.country = country;
      this.ref = ref;
      this.latitude = latitude;
      this.longitude = longitude;
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
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
     *  Type of Address, to support legacy address, the value can be AGENT and ORDER
     */
    public @Nullable String type() {
      return this.type;
    }

    /**
     *  Company name
     */
    public @Nullable String companyName() {
      return this.companyName;
    }

    /**
     *  Name
     */
    public @Nullable String name() {
      return this.name;
    }

    /**
     *  Street
     */
    public @Nullable String street() {
      return this.street;
    }

    /**
     *  City
     */
    public @Nullable String city() {
      return this.city;
    }

    /**
     *  State
     */
    public @Nullable String state() {
      return this.state;
    }

    /**
     *  Postcode
     */
    public @Nullable String postcode() {
      return this.postcode;
    }

    /**
     *  Region
     */
    public @Nullable String region() {
      return this.region;
    }

    /**
     *  Country
     */
    public @Nullable String country() {
      return this.country;
    }

    /**
     *  Location reference
     */
    public @Nullable String ref() {
      return this.ref;
    }

    /**
     *  Latitude
     */
    public @Nullable Double latitude() {
      return this.latitude;
    }

    /**
     *  Longitude
     */
    public @Nullable Double longitude() {
      return this.longitude;
    }

    /**
     *  Time of creation
     */
    public @Nullable Object createdOn() {
      return this.createdOn;
    }

    /**
     *  Time of last update
     */
    public @Nullable Object updatedOn() {
      return this.updatedOn;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], type);
          writer.writeString($responseFields[3], companyName);
          writer.writeString($responseFields[4], name);
          writer.writeString($responseFields[5], street);
          writer.writeString($responseFields[6], city);
          writer.writeString($responseFields[7], state);
          writer.writeString($responseFields[8], postcode);
          writer.writeString($responseFields[9], region);
          writer.writeString($responseFields[10], country);
          writer.writeString($responseFields[11], ref);
          writer.writeDouble($responseFields[12], latitude);
          writer.writeDouble($responseFields[13], longitude);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[14], createdOn);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[15], updatedOn);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "DeliveryAddress{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "type=" + type + ", "
          + "companyName=" + companyName + ", "
          + "name=" + name + ", "
          + "street=" + street + ", "
          + "city=" + city + ", "
          + "state=" + state + ", "
          + "postcode=" + postcode + ", "
          + "region=" + region + ", "
          + "country=" + country + ", "
          + "ref=" + ref + ", "
          + "latitude=" + latitude + ", "
          + "longitude=" + longitude + ", "
          + "createdOn=" + createdOn + ", "
          + "updatedOn=" + updatedOn
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof DeliveryAddress) {
        DeliveryAddress that = (DeliveryAddress) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.type == null) ? (that.type == null) : this.type.equals(that.type))
         && ((this.companyName == null) ? (that.companyName == null) : this.companyName.equals(that.companyName))
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
         && ((this.street == null) ? (that.street == null) : this.street.equals(that.street))
         && ((this.city == null) ? (that.city == null) : this.city.equals(that.city))
         && ((this.state == null) ? (that.state == null) : this.state.equals(that.state))
         && ((this.postcode == null) ? (that.postcode == null) : this.postcode.equals(that.postcode))
         && ((this.region == null) ? (that.region == null) : this.region.equals(that.region))
         && ((this.country == null) ? (that.country == null) : this.country.equals(that.country))
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && ((this.latitude == null) ? (that.latitude == null) : this.latitude.equals(that.latitude))
         && ((this.longitude == null) ? (that.longitude == null) : this.longitude.equals(that.longitude))
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.updatedOn == null) ? (that.updatedOn == null) : this.updatedOn.equals(that.updatedOn));
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
        h ^= (type == null) ? 0 : type.hashCode();
        h *= 1000003;
        h ^= (companyName == null) ? 0 : companyName.hashCode();
        h *= 1000003;
        h ^= (name == null) ? 0 : name.hashCode();
        h *= 1000003;
        h ^= (street == null) ? 0 : street.hashCode();
        h *= 1000003;
        h ^= (city == null) ? 0 : city.hashCode();
        h *= 1000003;
        h ^= (state == null) ? 0 : state.hashCode();
        h *= 1000003;
        h ^= (postcode == null) ? 0 : postcode.hashCode();
        h *= 1000003;
        h ^= (region == null) ? 0 : region.hashCode();
        h *= 1000003;
        h ^= (country == null) ? 0 : country.hashCode();
        h *= 1000003;
        h ^= (ref == null) ? 0 : ref.hashCode();
        h *= 1000003;
        h ^= (latitude == null) ? 0 : latitude.hashCode();
        h *= 1000003;
        h ^= (longitude == null) ? 0 : longitude.hashCode();
        h *= 1000003;
        h ^= (createdOn == null) ? 0 : createdOn.hashCode();
        h *= 1000003;
        h ^= (updatedOn == null) ? 0 : updatedOn.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.id = id;
      builder.type = type;
      builder.companyName = companyName;
      builder.name = name;
      builder.street = street;
      builder.city = city;
      builder.state = state;
      builder.postcode = postcode;
      builder.region = region;
      builder.country = country;
      builder.ref = ref;
      builder.latitude = latitude;
      builder.longitude = longitude;
      builder.createdOn = createdOn;
      builder.updatedOn = updatedOn;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<DeliveryAddress> {
      @Override
      public DeliveryAddress map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String type = reader.readString($responseFields[2]);
        final String companyName = reader.readString($responseFields[3]);
        final String name = reader.readString($responseFields[4]);
        final String street = reader.readString($responseFields[5]);
        final String city = reader.readString($responseFields[6]);
        final String state = reader.readString($responseFields[7]);
        final String postcode = reader.readString($responseFields[8]);
        final String region = reader.readString($responseFields[9]);
        final String country = reader.readString($responseFields[10]);
        final String ref = reader.readString($responseFields[11]);
        final Double latitude = reader.readDouble($responseFields[12]);
        final Double longitude = reader.readDouble($responseFields[13]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[14]);
        final Object updatedOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[15]);
        return new DeliveryAddress(__typename, id, type, companyName, name, street, city, state, postcode, region, country, ref, latitude, longitude, createdOn, updatedOn);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String type;

      private @Nullable String companyName;

      private @Nullable String name;

      private @Nullable String street;

      private @Nullable String city;

      private @Nullable String state;

      private @Nullable String postcode;

      private @Nullable String region;

      private @Nullable String country;

      private @Nullable String ref;

      private @Nullable Double latitude;

      private @Nullable Double longitude;

      private @Nullable Object createdOn;

      private @Nullable Object updatedOn;

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

      public Builder type(@Nullable String type) {
        this.type = type;
        return this;
      }

      public Builder companyName(@Nullable String companyName) {
        this.companyName = companyName;
        return this;
      }

      public Builder name(@Nullable String name) {
        this.name = name;
        return this;
      }

      public Builder street(@Nullable String street) {
        this.street = street;
        return this;
      }

      public Builder city(@Nullable String city) {
        this.city = city;
        return this;
      }

      public Builder state(@Nullable String state) {
        this.state = state;
        return this;
      }

      public Builder postcode(@Nullable String postcode) {
        this.postcode = postcode;
        return this;
      }

      public Builder region(@Nullable String region) {
        this.region = region;
        return this;
      }

      public Builder country(@Nullable String country) {
        this.country = country;
        return this;
      }

      public Builder ref(@Nullable String ref) {
        this.ref = ref;
        return this;
      }

      public Builder latitude(@Nullable Double latitude) {
        this.latitude = latitude;
        return this;
      }

      public Builder longitude(@Nullable Double longitude) {
        this.longitude = longitude;
        return this;
      }

      public Builder createdOn(@Nullable Object createdOn) {
        this.createdOn = createdOn;
        return this;
      }

      public Builder updatedOn(@Nullable Object updatedOn) {
        this.updatedOn = updatedOn;
        return this;
      }

      public DeliveryAddress build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new DeliveryAddress(__typename, id, type, companyName, name, street, city, state, postcode, region, country, ref, latitude, longitude, createdOn, updatedOn);
      }
    }
  }
}
