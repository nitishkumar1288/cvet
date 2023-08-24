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
public final class GetOrdersByRefQuery implements Query<GetOrdersByRefQuery.Data, GetOrdersByRefQuery.Data, GetOrdersByRefQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query GetOrdersByRef($ref: [String], $orderItemCount: Int, $orderItemCursor: String, $includeOrderItems: Boolean!, $includeFulfilmentChoice: Boolean!, $includeCustomer: Boolean!, $includeFulfilments: Boolean!, $fulfilmentRef: [String], $fulfilmentCount: Int, $fulfilmentCursor: String) {\n"
      + "  orders(ref: $ref) {\n"
      + "    __typename\n"
      + "    edges {\n"
      + "      __typename\n"
      + "      node {\n"
      + "        __typename\n"
      + "        id\n"
      + "        ref\n"
      + "        type\n"
      + "        status\n"
      + "        createdOn\n"
      + "        updatedOn\n"
      + "        totalPrice\n"
      + "        totalTaxPrice\n"
      + "        attributes {\n"
      + "          __typename\n"
      + "          name\n"
      + "          type\n"
      + "          value\n"
      + "        }\n"
      + "        items(first: $orderItemCount, after: $orderItemCursor) @include(if: $includeOrderItems) {\n"
      + "          __typename\n"
      + "          itemEdges: edges {\n"
      + "            __typename\n"
      + "            itemNode: node {\n"
      + "              __typename\n"
      + "              id\n"
      + "              ref\n"
      + "              quantity\n"
      + "              paidPrice\n"
      + "              currency\n"
      + "              price\n"
      + "              taxPrice\n"
      + "              totalTaxPrice\n"
      + "              totalPrice\n"
      + "              attributes {\n"
      + "                __typename\n"
      + "                name\n"
      + "                value\n"
      + "                type\n"
      + "              }\n"
      + "            }\n"
      + "          }\n"
      + "        }\n"
      + "        fulfilmentChoice @include(if: $includeFulfilmentChoice) {\n"
      + "          __typename\n"
      + "          id\n"
      + "          createdOn\n"
      + "          updatedOn\n"
      + "          currency\n"
      + "          deliveryInstruction\n"
      + "          deliveryType\n"
      + "          fulfilmentPrice\n"
      + "          fulfilmentTaxPrice\n"
      + "          fulfilmentTaxPrice\n"
      + "          fulfilmentType\n"
      + "          pickupLocationRef\n"
      + "          deliveryAddress {\n"
      + "            __typename\n"
      + "            id\n"
      + "            type\n"
      + "            companyName\n"
      + "            name\n"
      + "            street\n"
      + "            city\n"
      + "            state\n"
      + "            postcode\n"
      + "            region\n"
      + "            country\n"
      + "            region\n"
      + "            ref\n"
      + "            latitude\n"
      + "            longitude\n"
      + "          }\n"
      + "        }\n"
      + "        customer @include(if: $includeCustomer) {\n"
      + "          __typename\n"
      + "          id\n"
      + "          ref\n"
      + "          title\n"
      + "          country\n"
      + "          firstName\n"
      + "          lastName\n"
      + "          username\n"
      + "          primaryEmail\n"
      + "          primaryPhone\n"
      + "        }\n"
      + "        financialTransactions {\n"
      + "          __typename\n"
      + "          edges {\n"
      + "            __typename\n"
      + "            node {\n"
      + "              __typename\n"
      + "              id\n"
      + "              ref\n"
      + "              type\n"
      + "              status\n"
      + "              createdOn\n"
      + "              updatedOn\n"
      + "              total\n"
      + "              currency\n"
      + "              externalTransactionCode\n"
      + "              externalTransactionId\n"
      + "              cardType\n"
      + "              paymentMethod\n"
      + "              paymentProviderName\n"
      + "            }\n"
      + "          }\n"
      + "        }\n"
      + "        fulfilments(first: $fulfilmentCount, after: $fulfilmentCursor, ref: $fulfilmentRef) @include(if: $includeFulfilments) {\n"
      + "          __typename\n"
      + "          fulfilmentEdges: edges {\n"
      + "            __typename\n"
      + "            fulfilmentNode: node {\n"
      + "              __typename\n"
      + "              id\n"
      + "              ref\n"
      + "              type\n"
      + "              status\n"
      + "              type\n"
      + "              createdOn\n"
      + "              updatedOn\n"
      + "              fromAddress {\n"
      + "                __typename\n"
      + "                ref\n"
      + "                id\n"
      + "                type\n"
      + "                name\n"
      + "                street\n"
      + "                city\n"
      + "                state\n"
      + "                postcode\n"
      + "                country\n"
      + "                latitude\n"
      + "                longitude\n"
      + "              }\n"
      + "              toAddress {\n"
      + "                __typename\n"
      + "                ref\n"
      + "                type\n"
      + "                id\n"
      + "                name\n"
      + "                street\n"
      + "                city\n"
      + "                state\n"
      + "                postcode\n"
      + "                country\n"
      + "                latitude\n"
      + "                longitude\n"
      + "              }\n"
      + "              attributes {\n"
      + "                __typename\n"
      + "                name\n"
      + "                type\n"
      + "                value\n"
      + "              }\n"
      + "            }\n"
      + "          }\n"
      + "        }\n"
      + "      }\n"
      + "    }\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "GetOrdersByRef";
    }
  };

  private final GetOrdersByRefQuery.Variables variables;

  public GetOrdersByRefQuery(@Nullable List<String> ref, @Nullable Integer orderItemCount,
      @Nullable String orderItemCursor, boolean includeOrderItems, boolean includeFulfilmentChoice,
      boolean includeCustomer, boolean includeFulfilments, @Nullable List<String> fulfilmentRef,
      @Nullable Integer fulfilmentCount, @Nullable String fulfilmentCursor) {
    variables = new GetOrdersByRefQuery.Variables(ref, orderItemCount, orderItemCursor, includeOrderItems, includeFulfilmentChoice, includeCustomer, includeFulfilments, fulfilmentRef, fulfilmentCount, fulfilmentCursor);
  }

  @Override
  public String operationId() {
    return "6366b01813e27dcfd49b4cce2f624d71f046e171a9a80679ec2ac329daeaac41";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public GetOrdersByRefQuery.Data wrapData(GetOrdersByRefQuery.Data data) {
    return data;
  }

  @Override
  public GetOrdersByRefQuery.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<GetOrdersByRefQuery.Data> responseFieldMapper() {
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
    private @Nullable List<String> ref;

    private @Nullable Integer orderItemCount;

    private @Nullable String orderItemCursor;

    private boolean includeOrderItems;

    private boolean includeFulfilmentChoice;

    private boolean includeCustomer;

    private boolean includeFulfilments;

    private @Nullable List<String> fulfilmentRef;

    private @Nullable Integer fulfilmentCount;

    private @Nullable String fulfilmentCursor;

    Builder() {
    }

    public Builder ref(@Nullable List<String> ref) {
      this.ref = ref;
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

    public Builder includeOrderItems(boolean includeOrderItems) {
      this.includeOrderItems = includeOrderItems;
      return this;
    }

    public Builder includeFulfilmentChoice(boolean includeFulfilmentChoice) {
      this.includeFulfilmentChoice = includeFulfilmentChoice;
      return this;
    }

    public Builder includeCustomer(boolean includeCustomer) {
      this.includeCustomer = includeCustomer;
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

    public Builder fulfilmentCount(@Nullable Integer fulfilmentCount) {
      this.fulfilmentCount = fulfilmentCount;
      return this;
    }

    public Builder fulfilmentCursor(@Nullable String fulfilmentCursor) {
      this.fulfilmentCursor = fulfilmentCursor;
      return this;
    }

    public GetOrdersByRefQuery build() {
      return new GetOrdersByRefQuery(ref, orderItemCount, orderItemCursor, includeOrderItems, includeFulfilmentChoice, includeCustomer, includeFulfilments, fulfilmentRef, fulfilmentCount, fulfilmentCursor);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable List<String> ref;

    private final @Nullable Integer orderItemCount;

    private final @Nullable String orderItemCursor;

    private final boolean includeOrderItems;

    private final boolean includeFulfilmentChoice;

    private final boolean includeCustomer;

    private final boolean includeFulfilments;

    private final @Nullable List<String> fulfilmentRef;

    private final @Nullable Integer fulfilmentCount;

    private final @Nullable String fulfilmentCursor;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable List<String> ref, @Nullable Integer orderItemCount,
        @Nullable String orderItemCursor, boolean includeOrderItems,
        boolean includeFulfilmentChoice, boolean includeCustomer, boolean includeFulfilments,
        @Nullable List<String> fulfilmentRef, @Nullable Integer fulfilmentCount,
        @Nullable String fulfilmentCursor) {
      this.ref = ref;
      this.orderItemCount = orderItemCount;
      this.orderItemCursor = orderItemCursor;
      this.includeOrderItems = includeOrderItems;
      this.includeFulfilmentChoice = includeFulfilmentChoice;
      this.includeCustomer = includeCustomer;
      this.includeFulfilments = includeFulfilments;
      this.fulfilmentRef = fulfilmentRef;
      this.fulfilmentCount = fulfilmentCount;
      this.fulfilmentCursor = fulfilmentCursor;
      this.valueMap.put("ref", ref);
      this.valueMap.put("orderItemCount", orderItemCount);
      this.valueMap.put("orderItemCursor", orderItemCursor);
      this.valueMap.put("includeOrderItems", includeOrderItems);
      this.valueMap.put("includeFulfilmentChoice", includeFulfilmentChoice);
      this.valueMap.put("includeCustomer", includeCustomer);
      this.valueMap.put("includeFulfilments", includeFulfilments);
      this.valueMap.put("fulfilmentRef", fulfilmentRef);
      this.valueMap.put("fulfilmentCount", fulfilmentCount);
      this.valueMap.put("fulfilmentCursor", fulfilmentCursor);
    }

    public @Nullable List<String> ref() {
      return ref;
    }

    public @Nullable Integer orderItemCount() {
      return orderItemCount;
    }

    public @Nullable String orderItemCursor() {
      return orderItemCursor;
    }

    public boolean includeOrderItems() {
      return includeOrderItems;
    }

    public boolean includeFulfilmentChoice() {
      return includeFulfilmentChoice;
    }

    public boolean includeCustomer() {
      return includeCustomer;
    }

    public boolean includeFulfilments() {
      return includeFulfilments;
    }

    public @Nullable List<String> fulfilmentRef() {
      return fulfilmentRef;
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
          writer.writeList("ref", ref != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : ref) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
          writer.writeInt("orderItemCount", orderItemCount);
          writer.writeString("orderItemCursor", orderItemCursor);
          writer.writeBoolean("includeOrderItems", includeOrderItems);
          writer.writeBoolean("includeFulfilmentChoice", includeFulfilmentChoice);
          writer.writeBoolean("includeCustomer", includeCustomer);
          writer.writeBoolean("includeFulfilments", includeFulfilments);
          writer.writeList("fulfilmentRef", fulfilmentRef != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : fulfilmentRef) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
          writer.writeInt("fulfilmentCount", fulfilmentCount);
          writer.writeString("fulfilmentCursor", fulfilmentCursor);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("orders", "orders", new UnmodifiableMapBuilder<String, Object>(1)
        .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "ref")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable Orders orders;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable Orders orders) {
      this.orders = orders;
    }

    /**
     *  Search for Order entities
     */
    public @Nullable Orders orders() {
      return this.orders;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], orders != null ? orders.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "orders=" + orders
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
        return ((this.orders == null) ? (that.orders == null) : this.orders.equals(that.orders));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (orders == null) ? 0 : orders.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.orders = orders;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final Orders.Mapper ordersFieldMapper = new Orders.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final Orders orders = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<Orders>() {
          @Override
          public Orders read(ResponseReader reader) {
            return ordersFieldMapper.map(reader);
          }
        });
        return new Data(orders);
      }
    }

    public static final class Builder {
      private @Nullable Orders orders;

      Builder() {
      }

      public Builder orders(@Nullable Orders orders) {
        this.orders = orders;
        return this;
      }

      public Builder orders(@Nonnull Mutator<Orders.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Orders.Builder builder = this.orders != null ? this.orders.toBuilder() : Orders.builder();
        mutator.accept(builder);
        this.orders = builder.build();
        return this;
      }

      public Data build() {
        return new Data(orders);
      }
    }
  }

  public static class Orders {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("edges", "edges", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<Edge> edges;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Orders(@Nonnull String __typename, @Nullable List<Edge> edges) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.edges = edges;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to Order type node
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
        $toString = "Orders{"
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
      if (o instanceof Orders) {
        Orders that = (Orders) o;
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

    public static final class Mapper implements ResponseFieldMapper<Orders> {
      final Edge.Mapper edgeFieldMapper = new Edge.Mapper();

      @Override
      public Orders map(ResponseReader reader) {
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
        return new Orders(__typename, edges);
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

      public Orders build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Orders(__typename, edges);
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
     *  The item at the end of the Order edge
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
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("updatedOn", "updatedOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("totalPrice", "totalPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("totalTaxPrice", "totalTaxPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("items", "items", new UnmodifiableMapBuilder<String, Object>(2)
        .put("after", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "orderItemCursor")
        .build())
        .put("first", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "orderItemCount")
        .build())
      .build(), true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeOrderItems", false))),
      ResponseField.forObject("fulfilmentChoice", "fulfilmentChoice", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeFulfilmentChoice", false))),
      ResponseField.forObject("customer", "customer", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeCustomer", false))),
      ResponseField.forObject("financialTransactions", "financialTransactions", null, true, Collections.<ResponseField.Condition>emptyList()),
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
      .build(), true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeFulfilments", false)))
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

    final @Nullable List<Attribute> attributes;

    final @Nullable Items items;

    final @Nullable FulfilmentChoice fulfilmentChoice;

    final @Nullable Customer customer;

    final @Nullable FinancialTransactions financialTransactions;

    final @Nullable Fulfilments fulfilments;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Node(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        @Nonnull String type, @Nullable String status, @Nullable Object createdOn,
        @Nullable Object updatedOn, @Nullable Double totalPrice, @Nullable Double totalTaxPrice,
        @Nullable List<Attribute> attributes, @Nullable Items items,
        @Nullable FulfilmentChoice fulfilmentChoice, @Nullable Customer customer,
        @Nullable FinancialTransactions financialTransactions, @Nullable Fulfilments fulfilments) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.type = Utils.checkNotNull(type, "type == null");
      this.status = status;
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.totalPrice = totalPrice;
      this.totalTaxPrice = totalTaxPrice;
      this.attributes = attributes;
      this.items = items;
      this.fulfilmentChoice = fulfilmentChoice;
      this.customer = customer;
      this.financialTransactions = financialTransactions;
      this.fulfilments = fulfilments;
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
     *  List of order `attribute`s
     */
    public @Nullable List<Attribute> attributes() {
      return this.attributes;
    }

    /**
     *  Connection representing a list of `OrderItem`s
     */
    public @Nullable Items items() {
      return this.items;
    }

    /**
     *  The `FulfilmentChoice` specified when booking the order
     */
    public @Nullable FulfilmentChoice fulfilmentChoice() {
      return this.fulfilmentChoice;
    }

    /**
     *  `Customer` of the order
     */
    public @Nullable Customer customer() {
      return this.customer;
    }

    /**
     *  Connection representing a list of `FinancialTransaction`s
     */
    public @Nullable FinancialTransactions financialTransactions() {
      return this.financialTransactions;
    }

    /**
     *  Connection representing a list of `Fulfilment`s
     */
    public @Nullable Fulfilments fulfilments() {
      return this.fulfilments;
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
          writer.writeList($responseFields[9], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute) value).marshaller());
            }
          });
          writer.writeObject($responseFields[10], items != null ? items.marshaller() : null);
          writer.writeObject($responseFields[11], fulfilmentChoice != null ? fulfilmentChoice.marshaller() : null);
          writer.writeObject($responseFields[12], customer != null ? customer.marshaller() : null);
          writer.writeObject($responseFields[13], financialTransactions != null ? financialTransactions.marshaller() : null);
          writer.writeObject($responseFields[14], fulfilments != null ? fulfilments.marshaller() : null);
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
          + "status=" + status + ", "
          + "createdOn=" + createdOn + ", "
          + "updatedOn=" + updatedOn + ", "
          + "totalPrice=" + totalPrice + ", "
          + "totalTaxPrice=" + totalTaxPrice + ", "
          + "attributes=" + attributes + ", "
          + "items=" + items + ", "
          + "fulfilmentChoice=" + fulfilmentChoice + ", "
          + "customer=" + customer + ", "
          + "financialTransactions=" + financialTransactions + ", "
          + "fulfilments=" + fulfilments
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
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && this.type.equals(that.type)
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.updatedOn == null) ? (that.updatedOn == null) : this.updatedOn.equals(that.updatedOn))
         && ((this.totalPrice == null) ? (that.totalPrice == null) : this.totalPrice.equals(that.totalPrice))
         && ((this.totalTaxPrice == null) ? (that.totalTaxPrice == null) : this.totalTaxPrice.equals(that.totalTaxPrice))
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes))
         && ((this.items == null) ? (that.items == null) : this.items.equals(that.items))
         && ((this.fulfilmentChoice == null) ? (that.fulfilmentChoice == null) : this.fulfilmentChoice.equals(that.fulfilmentChoice))
         && ((this.customer == null) ? (that.customer == null) : this.customer.equals(that.customer))
         && ((this.financialTransactions == null) ? (that.financialTransactions == null) : this.financialTransactions.equals(that.financialTransactions))
         && ((this.fulfilments == null) ? (that.fulfilments == null) : this.fulfilments.equals(that.fulfilments));
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
        h ^= (attributes == null) ? 0 : attributes.hashCode();
        h *= 1000003;
        h ^= (items == null) ? 0 : items.hashCode();
        h *= 1000003;
        h ^= (fulfilmentChoice == null) ? 0 : fulfilmentChoice.hashCode();
        h *= 1000003;
        h ^= (customer == null) ? 0 : customer.hashCode();
        h *= 1000003;
        h ^= (financialTransactions == null) ? 0 : financialTransactions.hashCode();
        h *= 1000003;
        h ^= (fulfilments == null) ? 0 : fulfilments.hashCode();
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
      builder.attributes = attributes;
      builder.items = items;
      builder.fulfilmentChoice = fulfilmentChoice;
      builder.customer = customer;
      builder.financialTransactions = financialTransactions;
      builder.fulfilments = fulfilments;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Node> {
      final Attribute.Mapper attributeFieldMapper = new Attribute.Mapper();

      final Items.Mapper itemsFieldMapper = new Items.Mapper();

      final FulfilmentChoice.Mapper fulfilmentChoiceFieldMapper = new FulfilmentChoice.Mapper();

      final Customer.Mapper customerFieldMapper = new Customer.Mapper();

      final FinancialTransactions.Mapper financialTransactionsFieldMapper = new FinancialTransactions.Mapper();

      final Fulfilments.Mapper fulfilmentsFieldMapper = new Fulfilments.Mapper();

      @Override
      public Node map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[5]);
        final Object updatedOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[6]);
        final Double totalPrice = reader.readDouble($responseFields[7]);
        final Double totalTaxPrice = reader.readDouble($responseFields[8]);
        final List<Attribute> attributes = reader.readList($responseFields[9], new ResponseReader.ListReader<Attribute>() {
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
        final Items items = reader.readObject($responseFields[10], new ResponseReader.ObjectReader<Items>() {
          @Override
          public Items read(ResponseReader reader) {
            return itemsFieldMapper.map(reader);
          }
        });
        final FulfilmentChoice fulfilmentChoice = reader.readObject($responseFields[11], new ResponseReader.ObjectReader<FulfilmentChoice>() {
          @Override
          public FulfilmentChoice read(ResponseReader reader) {
            return fulfilmentChoiceFieldMapper.map(reader);
          }
        });
        final Customer customer = reader.readObject($responseFields[12], new ResponseReader.ObjectReader<Customer>() {
          @Override
          public Customer read(ResponseReader reader) {
            return customerFieldMapper.map(reader);
          }
        });
        final FinancialTransactions financialTransactions = reader.readObject($responseFields[13], new ResponseReader.ObjectReader<FinancialTransactions>() {
          @Override
          public FinancialTransactions read(ResponseReader reader) {
            return financialTransactionsFieldMapper.map(reader);
          }
        });
        final Fulfilments fulfilments = reader.readObject($responseFields[14], new ResponseReader.ObjectReader<Fulfilments>() {
          @Override
          public Fulfilments read(ResponseReader reader) {
            return fulfilmentsFieldMapper.map(reader);
          }
        });
        return new Node(__typename, id, ref, type, status, createdOn, updatedOn, totalPrice, totalTaxPrice, attributes, items, fulfilmentChoice, customer, financialTransactions, fulfilments);
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

      private @Nullable List<Attribute> attributes;

      private @Nullable Items items;

      private @Nullable FulfilmentChoice fulfilmentChoice;

      private @Nullable Customer customer;

      private @Nullable FinancialTransactions financialTransactions;

      private @Nullable Fulfilments fulfilments;

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

      public Builder attributes(@Nullable List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
      }

      public Builder items(@Nullable Items items) {
        this.items = items;
        return this;
      }

      public Builder fulfilmentChoice(@Nullable FulfilmentChoice fulfilmentChoice) {
        this.fulfilmentChoice = fulfilmentChoice;
        return this;
      }

      public Builder customer(@Nullable Customer customer) {
        this.customer = customer;
        return this;
      }

      public Builder financialTransactions(@Nullable FinancialTransactions financialTransactions) {
        this.financialTransactions = financialTransactions;
        return this;
      }

      public Builder fulfilments(@Nullable Fulfilments fulfilments) {
        this.fulfilments = fulfilments;
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

      public Builder items(@Nonnull Mutator<Items.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Items.Builder builder = this.items != null ? this.items.toBuilder() : Items.builder();
        mutator.accept(builder);
        this.items = builder.build();
        return this;
      }

      public Builder fulfilmentChoice(@Nonnull Mutator<FulfilmentChoice.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        FulfilmentChoice.Builder builder = this.fulfilmentChoice != null ? this.fulfilmentChoice.toBuilder() : FulfilmentChoice.builder();
        mutator.accept(builder);
        this.fulfilmentChoice = builder.build();
        return this;
      }

      public Builder customer(@Nonnull Mutator<Customer.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Customer.Builder builder = this.customer != null ? this.customer.toBuilder() : Customer.builder();
        mutator.accept(builder);
        this.customer = builder.build();
        return this;
      }

      public Builder financialTransactions(@Nonnull Mutator<FinancialTransactions.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        FinancialTransactions.Builder builder = this.financialTransactions != null ? this.financialTransactions.toBuilder() : FinancialTransactions.builder();
        mutator.accept(builder);
        this.financialTransactions = builder.build();
        return this;
      }

      public Builder fulfilments(@Nonnull Mutator<Fulfilments.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Fulfilments.Builder builder = this.fulfilments != null ? this.fulfilments.toBuilder() : Fulfilments.builder();
        mutator.accept(builder);
        this.fulfilments = builder.build();
        return this;
      }

      public Node build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(type, "type == null");
        return new Node(__typename, id, ref, type, status, createdOn, updatedOn, totalPrice, totalTaxPrice, attributes, items, fulfilmentChoice, customer, financialTransactions, fulfilments);
      }
    }
  }

  public static class Attribute {
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

    public Attribute(@Nonnull String __typename, @Nonnull String name, @Nonnull String type,
        @Nonnull Object value) {
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
        $toString = "Attribute{"
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
      if (o instanceof Attribute) {
        Attribute that = (Attribute) o;
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

    public static final class Mapper implements ResponseFieldMapper<Attribute> {
      @Override
      public Attribute map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String name = reader.readString($responseFields[1]);
        final String type = reader.readString($responseFields[2]);
        final Object value = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        return new Attribute(__typename, name, type, value);
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

      public Attribute build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(value, "value == null");
        return new Attribute(__typename, name, type, value);
      }
    }
  }

  public static class Items {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("itemEdges", "edges", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<ItemEdge> itemEdges;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Items(@Nonnull String __typename, @Nullable List<ItemEdge> itemEdges) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.itemEdges = itemEdges;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to OrderItem type node
     */
    public @Nullable List<ItemEdge> itemEdges() {
      return this.itemEdges;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeList($responseFields[1], itemEdges, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((ItemEdge) value).marshaller());
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
          + "itemEdges=" + itemEdges
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
         && ((this.itemEdges == null) ? (that.itemEdges == null) : this.itemEdges.equals(that.itemEdges));
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
        h ^= (itemEdges == null) ? 0 : itemEdges.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.itemEdges = itemEdges;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Items> {
      final ItemEdge.Mapper itemEdgeFieldMapper = new ItemEdge.Mapper();

      @Override
      public Items map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final List<ItemEdge> itemEdges = reader.readList($responseFields[1], new ResponseReader.ListReader<ItemEdge>() {
          @Override
          public ItemEdge read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<ItemEdge>() {
              @Override
              public ItemEdge read(ResponseReader reader) {
                return itemEdgeFieldMapper.map(reader);
              }
            });
          }
        });
        return new Items(__typename, itemEdges);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable List<ItemEdge> itemEdges;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder itemEdges(@Nullable List<ItemEdge> itemEdges) {
        this.itemEdges = itemEdges;
        return this;
      }

      public Builder itemEdges(@Nonnull Mutator<List<ItemEdge.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<ItemEdge.Builder> builders = new ArrayList<>();
        if (this.itemEdges != null) {
          for (ItemEdge item : this.itemEdges) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<ItemEdge> itemEdges = new ArrayList<>();
        for (ItemEdge.Builder item : builders) {
          itemEdges.add(item != null ? item.build() : null);
        }
        this.itemEdges = itemEdges;
        return this;
      }

      public Items build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Items(__typename, itemEdges);
      }
    }
  }

  public static class ItemEdge {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("itemNode", "node", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable ItemNode itemNode;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ItemEdge(@Nonnull String __typename, @Nullable ItemNode itemNode) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.itemNode = itemNode;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The item at the end of the OrderItem edge
     */
    public @Nullable ItemNode itemNode() {
      return this.itemNode;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeObject($responseFields[1], itemNode != null ? itemNode.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ItemEdge{"
          + "__typename=" + __typename + ", "
          + "itemNode=" + itemNode
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof ItemEdge) {
        ItemEdge that = (ItemEdge) o;
        return this.__typename.equals(that.__typename)
         && ((this.itemNode == null) ? (that.itemNode == null) : this.itemNode.equals(that.itemNode));
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
        h ^= (itemNode == null) ? 0 : itemNode.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.itemNode = itemNode;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<ItemEdge> {
      final ItemNode.Mapper itemNodeFieldMapper = new ItemNode.Mapper();

      @Override
      public ItemEdge map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final ItemNode itemNode = reader.readObject($responseFields[1], new ResponseReader.ObjectReader<ItemNode>() {
          @Override
          public ItemNode read(ResponseReader reader) {
            return itemNodeFieldMapper.map(reader);
          }
        });
        return new ItemEdge(__typename, itemNode);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable ItemNode itemNode;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder itemNode(@Nullable ItemNode itemNode) {
        this.itemNode = itemNode;
        return this;
      }

      public Builder itemNode(@Nonnull Mutator<ItemNode.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ItemNode.Builder builder = this.itemNode != null ? this.itemNode.toBuilder() : ItemNode.builder();
        mutator.accept(builder);
        this.itemNode = builder.build();
        return this;
      }

      public ItemEdge build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new ItemEdge(__typename, itemNode);
      }
    }
  }

  public static class ItemNode {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("quantity", "quantity", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("paidPrice", "paidPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("currency", "currency", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("price", "price", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("taxPrice", "taxPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("totalTaxPrice", "totalTaxPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("totalPrice", "totalPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final int quantity;

    final @Nullable Double paidPrice;

    final @Nullable String currency;

    final @Nullable Double price;

    final @Nullable Double taxPrice;

    final @Nullable Double totalTaxPrice;

    final @Nullable Double totalPrice;

    final @Nullable List<Attribute1> attributes;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ItemNode(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        int quantity, @Nullable Double paidPrice, @Nullable String currency, @Nullable Double price,
        @Nullable Double taxPrice, @Nullable Double totalTaxPrice, @Nullable Double totalPrice,
        @Nullable List<Attribute1> attributes) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.quantity = quantity;
      this.paidPrice = paidPrice;
      this.currency = currency;
      this.price = price;
      this.taxPrice = taxPrice;
      this.totalTaxPrice = totalTaxPrice;
      this.totalPrice = totalPrice;
      this.attributes = attributes;
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
     *  Tax price
     */
    public @Nullable Double taxPrice() {
      return this.taxPrice;
    }

    /**
     *  Total tax price
     */
    public @Nullable Double totalTaxPrice() {
      return this.totalTaxPrice;
    }

    /**
     *  Total price
     */
    public @Nullable Double totalPrice() {
      return this.totalPrice;
    }

    /**
     *  List of `OrderItem` `attribute`s.
     */
    public @Nullable List<Attribute1> attributes() {
      return this.attributes;
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
          writer.writeDouble($responseFields[7], taxPrice);
          writer.writeDouble($responseFields[8], totalTaxPrice);
          writer.writeDouble($responseFields[9], totalPrice);
          writer.writeList($responseFields[10], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute1) value).marshaller());
            }
          });
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ItemNode{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "quantity=" + quantity + ", "
          + "paidPrice=" + paidPrice + ", "
          + "currency=" + currency + ", "
          + "price=" + price + ", "
          + "taxPrice=" + taxPrice + ", "
          + "totalTaxPrice=" + totalTaxPrice + ", "
          + "totalPrice=" + totalPrice + ", "
          + "attributes=" + attributes
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof ItemNode) {
        ItemNode that = (ItemNode) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && this.quantity == that.quantity
         && ((this.paidPrice == null) ? (that.paidPrice == null) : this.paidPrice.equals(that.paidPrice))
         && ((this.currency == null) ? (that.currency == null) : this.currency.equals(that.currency))
         && ((this.price == null) ? (that.price == null) : this.price.equals(that.price))
         && ((this.taxPrice == null) ? (that.taxPrice == null) : this.taxPrice.equals(that.taxPrice))
         && ((this.totalTaxPrice == null) ? (that.totalTaxPrice == null) : this.totalTaxPrice.equals(that.totalTaxPrice))
         && ((this.totalPrice == null) ? (that.totalPrice == null) : this.totalPrice.equals(that.totalPrice))
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes));
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
        h ^= (taxPrice == null) ? 0 : taxPrice.hashCode();
        h *= 1000003;
        h ^= (totalTaxPrice == null) ? 0 : totalTaxPrice.hashCode();
        h *= 1000003;
        h ^= (totalPrice == null) ? 0 : totalPrice.hashCode();
        h *= 1000003;
        h ^= (attributes == null) ? 0 : attributes.hashCode();
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
      builder.taxPrice = taxPrice;
      builder.totalTaxPrice = totalTaxPrice;
      builder.totalPrice = totalPrice;
      builder.attributes = attributes;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<ItemNode> {
      final Attribute1.Mapper attribute1FieldMapper = new Attribute1.Mapper();

      @Override
      public ItemNode map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final int quantity = reader.readInt($responseFields[3]);
        final Double paidPrice = reader.readDouble($responseFields[4]);
        final String currency = reader.readString($responseFields[5]);
        final Double price = reader.readDouble($responseFields[6]);
        final Double taxPrice = reader.readDouble($responseFields[7]);
        final Double totalTaxPrice = reader.readDouble($responseFields[8]);
        final Double totalPrice = reader.readDouble($responseFields[9]);
        final List<Attribute1> attributes = reader.readList($responseFields[10], new ResponseReader.ListReader<Attribute1>() {
          @Override
          public Attribute1 read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<Attribute1>() {
              @Override
              public Attribute1 read(ResponseReader reader) {
                return attribute1FieldMapper.map(reader);
              }
            });
          }
        });
        return new ItemNode(__typename, id, ref, quantity, paidPrice, currency, price, taxPrice, totalTaxPrice, totalPrice, attributes);
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

      private @Nullable Double taxPrice;

      private @Nullable Double totalTaxPrice;

      private @Nullable Double totalPrice;

      private @Nullable List<Attribute1> attributes;

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

      public Builder taxPrice(@Nullable Double taxPrice) {
        this.taxPrice = taxPrice;
        return this;
      }

      public Builder totalTaxPrice(@Nullable Double totalTaxPrice) {
        this.totalTaxPrice = totalTaxPrice;
        return this;
      }

      public Builder totalPrice(@Nullable Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
      }

      public Builder attributes(@Nullable List<Attribute1> attributes) {
        this.attributes = attributes;
        return this;
      }

      public Builder attributes(@Nonnull Mutator<List<Attribute1.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<Attribute1.Builder> builders = new ArrayList<>();
        if (this.attributes != null) {
          for (Attribute1 item : this.attributes) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<Attribute1> attributes = new ArrayList<>();
        for (Attribute1.Builder item : builders) {
          attributes.add(item != null ? item.build() : null);
        }
        this.attributes = attributes;
        return this;
      }

      public ItemNode build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new ItemNode(__typename, id, ref, quantity, paidPrice, currency, price, taxPrice, totalTaxPrice, totalPrice, attributes);
      }
    }
  }

  public static class Attribute1 {
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

    public Attribute1(@Nonnull String __typename, @Nonnull String name, @Nonnull Object value,
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
        $toString = "Attribute1{"
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
      if (o instanceof Attribute1) {
        Attribute1 that = (Attribute1) o;
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

    public static final class Mapper implements ResponseFieldMapper<Attribute1> {
      @Override
      public Attribute1 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String name = reader.readString($responseFields[1]);
        final Object value = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        return new Attribute1(__typename, name, value, type);
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

      public Attribute1 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(value, "value == null");
        Utils.checkNotNull(type, "type == null");
        return new Attribute1(__typename, name, value, type);
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
      ResponseField.forString("deliveryType", "deliveryType", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("fulfilmentPrice", "fulfilmentPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("fulfilmentTaxPrice", "fulfilmentTaxPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("fulfilmentType", "fulfilmentType", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("pickupLocationRef", "pickupLocationRef", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("deliveryAddress", "deliveryAddress", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable Object createdOn;

    final @Nullable Object updatedOn;

    final @Nullable String currency;

    final @Nullable String deliveryInstruction;

    final @Nonnull String deliveryType;

    final @Nullable Double fulfilmentPrice;

    final @Nullable Double fulfilmentTaxPrice;

    final @Nullable String fulfilmentType;

    final @Nullable String pickupLocationRef;

    final @Nullable DeliveryAddress deliveryAddress;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public FulfilmentChoice(@Nonnull String __typename, @Nonnull String id,
        @Nullable Object createdOn, @Nullable Object updatedOn, @Nullable String currency,
        @Nullable String deliveryInstruction, @Nonnull String deliveryType,
        @Nullable Double fulfilmentPrice, @Nullable Double fulfilmentTaxPrice,
        @Nullable String fulfilmentType, @Nullable String pickupLocationRef,
        @Nullable DeliveryAddress deliveryAddress) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.currency = currency;
      this.deliveryInstruction = deliveryInstruction;
      this.deliveryType = Utils.checkNotNull(deliveryType, "deliveryType == null");
      this.fulfilmentPrice = fulfilmentPrice;
      this.fulfilmentTaxPrice = fulfilmentTaxPrice;
      this.fulfilmentType = fulfilmentType;
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
     *  The type of delivery determined by retailers' shipping options. Example values are STANDARD, EXPRESS, OVERNIGHT, 3HOURS
     */
    public @Nonnull String deliveryType() {
      return this.deliveryType;
    }

    /**
     *  FulfilmentPrice refers to shipping and C&C fees.
     */
    public @Nullable Double fulfilmentPrice() {
      return this.fulfilmentPrice;
    }

    /**
     *  This refers to the tax cost associated with the fulfilment price.
     */
    public @Nullable Double fulfilmentTaxPrice() {
      return this.fulfilmentTaxPrice;
    }

    /**
     *  Indicates the type of fulfilment.
     */
    public @Nullable String fulfilmentType() {
      return this.fulfilmentType;
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
          writer.writeString($responseFields[6], deliveryType);
          writer.writeDouble($responseFields[7], fulfilmentPrice);
          writer.writeDouble($responseFields[8], fulfilmentTaxPrice);
          writer.writeString($responseFields[9], fulfilmentType);
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
          + "deliveryType=" + deliveryType + ", "
          + "fulfilmentPrice=" + fulfilmentPrice + ", "
          + "fulfilmentTaxPrice=" + fulfilmentTaxPrice + ", "
          + "fulfilmentType=" + fulfilmentType + ", "
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
         && this.deliveryType.equals(that.deliveryType)
         && ((this.fulfilmentPrice == null) ? (that.fulfilmentPrice == null) : this.fulfilmentPrice.equals(that.fulfilmentPrice))
         && ((this.fulfilmentTaxPrice == null) ? (that.fulfilmentTaxPrice == null) : this.fulfilmentTaxPrice.equals(that.fulfilmentTaxPrice))
         && ((this.fulfilmentType == null) ? (that.fulfilmentType == null) : this.fulfilmentType.equals(that.fulfilmentType))
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
        h ^= deliveryType.hashCode();
        h *= 1000003;
        h ^= (fulfilmentPrice == null) ? 0 : fulfilmentPrice.hashCode();
        h *= 1000003;
        h ^= (fulfilmentTaxPrice == null) ? 0 : fulfilmentTaxPrice.hashCode();
        h *= 1000003;
        h ^= (fulfilmentType == null) ? 0 : fulfilmentType.hashCode();
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
      builder.deliveryType = deliveryType;
      builder.fulfilmentPrice = fulfilmentPrice;
      builder.fulfilmentTaxPrice = fulfilmentTaxPrice;
      builder.fulfilmentType = fulfilmentType;
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
        final String deliveryType = reader.readString($responseFields[6]);
        final Double fulfilmentPrice = reader.readDouble($responseFields[7]);
        final Double fulfilmentTaxPrice = reader.readDouble($responseFields[8]);
        final String fulfilmentType = reader.readString($responseFields[9]);
        final String pickupLocationRef = reader.readString($responseFields[10]);
        final DeliveryAddress deliveryAddress = reader.readObject($responseFields[11], new ResponseReader.ObjectReader<DeliveryAddress>() {
          @Override
          public DeliveryAddress read(ResponseReader reader) {
            return deliveryAddressFieldMapper.map(reader);
          }
        });
        return new FulfilmentChoice(__typename, id, createdOn, updatedOn, currency, deliveryInstruction, deliveryType, fulfilmentPrice, fulfilmentTaxPrice, fulfilmentType, pickupLocationRef, deliveryAddress);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable Object createdOn;

      private @Nullable Object updatedOn;

      private @Nullable String currency;

      private @Nullable String deliveryInstruction;

      private @Nonnull String deliveryType;

      private @Nullable Double fulfilmentPrice;

      private @Nullable Double fulfilmentTaxPrice;

      private @Nullable String fulfilmentType;

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

      public Builder deliveryType(@Nonnull String deliveryType) {
        this.deliveryType = deliveryType;
        return this;
      }

      public Builder fulfilmentPrice(@Nullable Double fulfilmentPrice) {
        this.fulfilmentPrice = fulfilmentPrice;
        return this;
      }

      public Builder fulfilmentTaxPrice(@Nullable Double fulfilmentTaxPrice) {
        this.fulfilmentTaxPrice = fulfilmentTaxPrice;
        return this;
      }

      public Builder fulfilmentType(@Nullable String fulfilmentType) {
        this.fulfilmentType = fulfilmentType;
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
        return new FulfilmentChoice(__typename, id, createdOn, updatedOn, currency, deliveryInstruction, deliveryType, fulfilmentPrice, fulfilmentTaxPrice, fulfilmentType, pickupLocationRef, deliveryAddress);
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
      ResponseField.forDouble("longitude", "longitude", null, true, Collections.<ResponseField.Condition>emptyList())
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

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public DeliveryAddress(@Nonnull String __typename, @Nonnull String id, @Nullable String type,
        @Nullable String companyName, @Nullable String name, @Nullable String street,
        @Nullable String city, @Nullable String state, @Nullable String postcode,
        @Nullable String region, @Nullable String country, @Nullable String ref,
        @Nullable Double latitude, @Nullable Double longitude) {
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
          + "longitude=" + longitude
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
         && ((this.longitude == null) ? (that.longitude == null) : this.longitude.equals(that.longitude));
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
        return new DeliveryAddress(__typename, id, type, companyName, name, street, city, state, postcode, region, country, ref, latitude, longitude);
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

      public DeliveryAddress build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new DeliveryAddress(__typename, id, type, companyName, name, street, city, state, postcode, region, country, ref, latitude, longitude);
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

  public static class FinancialTransactions {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("edges", "edges", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<Edge1> edges;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public FinancialTransactions(@Nonnull String __typename, @Nullable List<Edge1> edges) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.edges = edges;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to FinancialTransaction type node
     */
    public @Nullable List<Edge1> edges() {
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
              listItemWriter.writeObject(((Edge1) value).marshaller());
            }
          });
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "FinancialTransactions{"
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
      if (o instanceof FinancialTransactions) {
        FinancialTransactions that = (FinancialTransactions) o;
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

    public static final class Mapper implements ResponseFieldMapper<FinancialTransactions> {
      final Edge1.Mapper edge1FieldMapper = new Edge1.Mapper();

      @Override
      public FinancialTransactions map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final List<Edge1> edges = reader.readList($responseFields[1], new ResponseReader.ListReader<Edge1>() {
          @Override
          public Edge1 read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<Edge1>() {
              @Override
              public Edge1 read(ResponseReader reader) {
                return edge1FieldMapper.map(reader);
              }
            });
          }
        });
        return new FinancialTransactions(__typename, edges);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable List<Edge1> edges;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder edges(@Nullable List<Edge1> edges) {
        this.edges = edges;
        return this;
      }

      public Builder edges(@Nonnull Mutator<List<Edge1.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<Edge1.Builder> builders = new ArrayList<>();
        if (this.edges != null) {
          for (Edge1 item : this.edges) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<Edge1> edges = new ArrayList<>();
        for (Edge1.Builder item : builders) {
          edges.add(item != null ? item.build() : null);
        }
        this.edges = edges;
        return this;
      }

      public FinancialTransactions build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new FinancialTransactions(__typename, edges);
      }
    }
  }

  public static class Edge1 {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("node", "node", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Node1 node;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Edge1(@Nonnull String __typename, @Nullable Node1 node) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.node = node;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The item at the end of the FinancialTransaction edge
     */
    public @Nullable Node1 node() {
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
        $toString = "Edge1{"
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
      if (o instanceof Edge1) {
        Edge1 that = (Edge1) o;
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

    public static final class Mapper implements ResponseFieldMapper<Edge1> {
      final Node1.Mapper node1FieldMapper = new Node1.Mapper();

      @Override
      public Edge1 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Node1 node = reader.readObject($responseFields[1], new ResponseReader.ObjectReader<Node1>() {
          @Override
          public Node1 read(ResponseReader reader) {
            return node1FieldMapper.map(reader);
          }
        });
        return new Edge1(__typename, node);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Node1 node;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder node(@Nullable Node1 node) {
        this.node = node;
        return this;
      }

      public Builder node(@Nonnull Mutator<Node1.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Node1.Builder builder = this.node != null ? this.node.toBuilder() : Node1.builder();
        mutator.accept(builder);
        this.node = builder.build();
        return this;
      }

      public Edge1 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Edge1(__typename, node);
      }
    }
  }

  public static class Node1 {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("updatedOn", "updatedOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("total", "total", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("currency", "currency", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("externalTransactionCode", "externalTransactionCode", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("externalTransactionId", "externalTransactionId", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("cardType", "cardType", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("paymentMethod", "paymentMethod", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("paymentProviderName", "paymentProviderName", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final @Nonnull String type;

    final @Nullable String status;

    final @Nullable Object createdOn;

    final @Nullable Object updatedOn;

    final double total;

    final @Nonnull String currency;

    final @Nullable String externalTransactionCode;

    final @Nullable String externalTransactionId;

    final @Nullable String cardType;

    final @Nullable String paymentMethod;

    final @Nullable String paymentProviderName;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Node1(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        @Nonnull String type, @Nullable String status, @Nullable Object createdOn,
        @Nullable Object updatedOn, double total, @Nonnull String currency,
        @Nullable String externalTransactionCode, @Nullable String externalTransactionId,
        @Nullable String cardType, @Nullable String paymentMethod,
        @Nullable String paymentProviderName) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.type = Utils.checkNotNull(type, "type == null");
      this.status = status;
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.total = total;
      this.currency = Utils.checkNotNull(currency, "currency == null");
      this.externalTransactionCode = externalTransactionCode;
      this.externalTransactionId = externalTransactionId;
      this.cardType = cardType;
      this.paymentMethod = paymentMethod;
      this.paymentProviderName = paymentProviderName;
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
     *  The unique transaction reference provided by the Retailer to the payment gateway
     */
    public @Nullable String ref() {
      return this.ref;
    }

    /**
     *  Type of the `FinancialTransaction`, typically used by the Orchestration Engine to determine the workflow that should be applied. Unless stated otherwise, no values are enforced by the platform.<br/>
     */
    public @Nonnull String type() {
      return this.type;
    }

    /**
     *  The current status of the `FinancialTransaction`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
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
     *  The total transaction amount
     */
    public double total() {
      return this.total;
    }

    /**
     *  Currency used for the transaction.
     */
    public @Nonnull String currency() {
      return this.currency;
    }

    /**
     *  The unique transaction code or request code provided by the payment gateway
     */
    public @Nullable String externalTransactionCode() {
      return this.externalTransactionCode;
    }

    /**
     *  The unique transaction ID or request ID provided by the payment gateway
     */
    public @Nullable String externalTransactionId() {
      return this.externalTransactionId;
    }

    /**
     *  The card type used for the payment. Possible values are 'MASTERCARD', 'VISA', 'AMEX', 'DINERS', 'SPAN', 'DISCOVER', 'UNIONPAY', 'JCB', 'MAESTRO', 'INTERAC'.
     */
    public @Nullable String cardType() {
      return this.cardType;
    }

    /**
     *  The way in which payment was made. Platform provides support for the following payment methods - 'CREDITCARD', 'PAYPAL', 'GIFTVOUCHER', 'CASH', 'AFTERPAY'. However, these
     *  can be overridden/configured per client as settings
     */
    public @Nullable String paymentMethod() {
      return this.paymentMethod;
    }

    /**
     *  The name of the payment gateway. Platform provided values are 'CYBERSOURCE', 'GIVEX', 'PAYPAL', 'BRAINTREE', 'AFTERPAY'. However, these can be overridden/configured per client as settings using 'PAYMENT.PROVIDER'
     */
    public @Nullable String paymentProviderName() {
      return this.paymentProviderName;
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
          writer.writeDouble($responseFields[7], total);
          writer.writeString($responseFields[8], currency);
          writer.writeString($responseFields[9], externalTransactionCode);
          writer.writeString($responseFields[10], externalTransactionId);
          writer.writeString($responseFields[11], cardType);
          writer.writeString($responseFields[12], paymentMethod);
          writer.writeString($responseFields[13], paymentProviderName);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Node1{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "type=" + type + ", "
          + "status=" + status + ", "
          + "createdOn=" + createdOn + ", "
          + "updatedOn=" + updatedOn + ", "
          + "total=" + total + ", "
          + "currency=" + currency + ", "
          + "externalTransactionCode=" + externalTransactionCode + ", "
          + "externalTransactionId=" + externalTransactionId + ", "
          + "cardType=" + cardType + ", "
          + "paymentMethod=" + paymentMethod + ", "
          + "paymentProviderName=" + paymentProviderName
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Node1) {
        Node1 that = (Node1) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && this.type.equals(that.type)
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.updatedOn == null) ? (that.updatedOn == null) : this.updatedOn.equals(that.updatedOn))
         && Double.doubleToLongBits(this.total) == Double.doubleToLongBits(that.total)
         && this.currency.equals(that.currency)
         && ((this.externalTransactionCode == null) ? (that.externalTransactionCode == null) : this.externalTransactionCode.equals(that.externalTransactionCode))
         && ((this.externalTransactionId == null) ? (that.externalTransactionId == null) : this.externalTransactionId.equals(that.externalTransactionId))
         && ((this.cardType == null) ? (that.cardType == null) : this.cardType.equals(that.cardType))
         && ((this.paymentMethod == null) ? (that.paymentMethod == null) : this.paymentMethod.equals(that.paymentMethod))
         && ((this.paymentProviderName == null) ? (that.paymentProviderName == null) : this.paymentProviderName.equals(that.paymentProviderName));
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
        h ^= Double.valueOf(total).hashCode();
        h *= 1000003;
        h ^= currency.hashCode();
        h *= 1000003;
        h ^= (externalTransactionCode == null) ? 0 : externalTransactionCode.hashCode();
        h *= 1000003;
        h ^= (externalTransactionId == null) ? 0 : externalTransactionId.hashCode();
        h *= 1000003;
        h ^= (cardType == null) ? 0 : cardType.hashCode();
        h *= 1000003;
        h ^= (paymentMethod == null) ? 0 : paymentMethod.hashCode();
        h *= 1000003;
        h ^= (paymentProviderName == null) ? 0 : paymentProviderName.hashCode();
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
      builder.total = total;
      builder.currency = currency;
      builder.externalTransactionCode = externalTransactionCode;
      builder.externalTransactionId = externalTransactionId;
      builder.cardType = cardType;
      builder.paymentMethod = paymentMethod;
      builder.paymentProviderName = paymentProviderName;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Node1> {
      @Override
      public Node1 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[5]);
        final Object updatedOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[6]);
        final double total = reader.readDouble($responseFields[7]);
        final String currency = reader.readString($responseFields[8]);
        final String externalTransactionCode = reader.readString($responseFields[9]);
        final String externalTransactionId = reader.readString($responseFields[10]);
        final String cardType = reader.readString($responseFields[11]);
        final String paymentMethod = reader.readString($responseFields[12]);
        final String paymentProviderName = reader.readString($responseFields[13]);
        return new Node1(__typename, id, ref, type, status, createdOn, updatedOn, total, currency, externalTransactionCode, externalTransactionId, cardType, paymentMethod, paymentProviderName);
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

      private double total;

      private @Nonnull String currency;

      private @Nullable String externalTransactionCode;

      private @Nullable String externalTransactionId;

      private @Nullable String cardType;

      private @Nullable String paymentMethod;

      private @Nullable String paymentProviderName;

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

      public Builder total(double total) {
        this.total = total;
        return this;
      }

      public Builder currency(@Nonnull String currency) {
        this.currency = currency;
        return this;
      }

      public Builder externalTransactionCode(@Nullable String externalTransactionCode) {
        this.externalTransactionCode = externalTransactionCode;
        return this;
      }

      public Builder externalTransactionId(@Nullable String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
        return this;
      }

      public Builder cardType(@Nullable String cardType) {
        this.cardType = cardType;
        return this;
      }

      public Builder paymentMethod(@Nullable String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
      }

      public Builder paymentProviderName(@Nullable String paymentProviderName) {
        this.paymentProviderName = paymentProviderName;
        return this;
      }

      public Node1 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(currency, "currency == null");
        return new Node1(__typename, id, ref, type, status, createdOn, updatedOn, total, currency, externalTransactionCode, externalTransactionId, cardType, paymentMethod, paymentProviderName);
      }
    }
  }

  public static class Fulfilments {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("fulfilmentEdges", "edges", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<FulfilmentEdge> fulfilmentEdges;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Fulfilments(@Nonnull String __typename, @Nullable List<FulfilmentEdge> fulfilmentEdges) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.fulfilmentEdges = fulfilmentEdges;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to Fulfilment type node
     */
    public @Nullable List<FulfilmentEdge> fulfilmentEdges() {
      return this.fulfilmentEdges;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeList($responseFields[1], fulfilmentEdges, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((FulfilmentEdge) value).marshaller());
            }
          });
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Fulfilments{"
          + "__typename=" + __typename + ", "
          + "fulfilmentEdges=" + fulfilmentEdges
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
         && ((this.fulfilmentEdges == null) ? (that.fulfilmentEdges == null) : this.fulfilmentEdges.equals(that.fulfilmentEdges));
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
        h ^= (fulfilmentEdges == null) ? 0 : fulfilmentEdges.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.fulfilmentEdges = fulfilmentEdges;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Fulfilments> {
      final FulfilmentEdge.Mapper fulfilmentEdgeFieldMapper = new FulfilmentEdge.Mapper();

      @Override
      public Fulfilments map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final List<FulfilmentEdge> fulfilmentEdges = reader.readList($responseFields[1], new ResponseReader.ListReader<FulfilmentEdge>() {
          @Override
          public FulfilmentEdge read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<FulfilmentEdge>() {
              @Override
              public FulfilmentEdge read(ResponseReader reader) {
                return fulfilmentEdgeFieldMapper.map(reader);
              }
            });
          }
        });
        return new Fulfilments(__typename, fulfilmentEdges);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable List<FulfilmentEdge> fulfilmentEdges;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder fulfilmentEdges(@Nullable List<FulfilmentEdge> fulfilmentEdges) {
        this.fulfilmentEdges = fulfilmentEdges;
        return this;
      }

      public Builder fulfilmentEdges(@Nonnull Mutator<List<FulfilmentEdge.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<FulfilmentEdge.Builder> builders = new ArrayList<>();
        if (this.fulfilmentEdges != null) {
          for (FulfilmentEdge item : this.fulfilmentEdges) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<FulfilmentEdge> fulfilmentEdges = new ArrayList<>();
        for (FulfilmentEdge.Builder item : builders) {
          fulfilmentEdges.add(item != null ? item.build() : null);
        }
        this.fulfilmentEdges = fulfilmentEdges;
        return this;
      }

      public Fulfilments build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Fulfilments(__typename, fulfilmentEdges);
      }
    }
  }

  public static class FulfilmentEdge {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("fulfilmentNode", "node", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable FulfilmentNode fulfilmentNode;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public FulfilmentEdge(@Nonnull String __typename, @Nullable FulfilmentNode fulfilmentNode) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.fulfilmentNode = fulfilmentNode;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The item at the end of the Fulfilment edge
     */
    public @Nullable FulfilmentNode fulfilmentNode() {
      return this.fulfilmentNode;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeObject($responseFields[1], fulfilmentNode != null ? fulfilmentNode.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "FulfilmentEdge{"
          + "__typename=" + __typename + ", "
          + "fulfilmentNode=" + fulfilmentNode
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof FulfilmentEdge) {
        FulfilmentEdge that = (FulfilmentEdge) o;
        return this.__typename.equals(that.__typename)
         && ((this.fulfilmentNode == null) ? (that.fulfilmentNode == null) : this.fulfilmentNode.equals(that.fulfilmentNode));
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
        h ^= (fulfilmentNode == null) ? 0 : fulfilmentNode.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.fulfilmentNode = fulfilmentNode;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<FulfilmentEdge> {
      final FulfilmentNode.Mapper fulfilmentNodeFieldMapper = new FulfilmentNode.Mapper();

      @Override
      public FulfilmentEdge map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final FulfilmentNode fulfilmentNode = reader.readObject($responseFields[1], new ResponseReader.ObjectReader<FulfilmentNode>() {
          @Override
          public FulfilmentNode read(ResponseReader reader) {
            return fulfilmentNodeFieldMapper.map(reader);
          }
        });
        return new FulfilmentEdge(__typename, fulfilmentNode);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable FulfilmentNode fulfilmentNode;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder fulfilmentNode(@Nullable FulfilmentNode fulfilmentNode) {
        this.fulfilmentNode = fulfilmentNode;
        return this;
      }

      public Builder fulfilmentNode(@Nonnull Mutator<FulfilmentNode.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        FulfilmentNode.Builder builder = this.fulfilmentNode != null ? this.fulfilmentNode.toBuilder() : FulfilmentNode.builder();
        mutator.accept(builder);
        this.fulfilmentNode = builder.build();
        return this;
      }

      public FulfilmentEdge build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new FulfilmentEdge(__typename, fulfilmentNode);
      }
    }
  }

  public static class FulfilmentNode {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("updatedOn", "updatedOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("fromAddress", "fromAddress", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("toAddress", "toAddress", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final @Nonnull String type;

    final @Nullable String status;

    final @Nullable Object createdOn;

    final @Nullable Object updatedOn;

    final @Nullable FromAddress fromAddress;

    final @Nullable ToAddress toAddress;

    final @Nullable List<Attribute2> attributes;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public FulfilmentNode(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        @Nonnull String type, @Nullable String status, @Nullable Object createdOn,
        @Nullable Object updatedOn, @Nullable FromAddress fromAddress,
        @Nullable ToAddress toAddress, @Nullable List<Attribute2> attributes) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.type = Utils.checkNotNull(type, "type == null");
      this.status = status;
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.fromAddress = fromAddress;
      this.toAddress = toAddress;
      this.attributes = attributes;
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

    /**
     *  Attributes of fulfilment
     */
    public @Nullable List<Attribute2> attributes() {
      return this.attributes;
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
          writer.writeObject($responseFields[7], fromAddress != null ? fromAddress.marshaller() : null);
          writer.writeObject($responseFields[8], toAddress != null ? toAddress.marshaller() : null);
          writer.writeList($responseFields[9], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute2) value).marshaller());
            }
          });
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "FulfilmentNode{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "type=" + type + ", "
          + "status=" + status + ", "
          + "createdOn=" + createdOn + ", "
          + "updatedOn=" + updatedOn + ", "
          + "fromAddress=" + fromAddress + ", "
          + "toAddress=" + toAddress + ", "
          + "attributes=" + attributes
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof FulfilmentNode) {
        FulfilmentNode that = (FulfilmentNode) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && this.type.equals(that.type)
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.updatedOn == null) ? (that.updatedOn == null) : this.updatedOn.equals(that.updatedOn))
         && ((this.fromAddress == null) ? (that.fromAddress == null) : this.fromAddress.equals(that.fromAddress))
         && ((this.toAddress == null) ? (that.toAddress == null) : this.toAddress.equals(that.toAddress))
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes));
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
        h ^= (fromAddress == null) ? 0 : fromAddress.hashCode();
        h *= 1000003;
        h ^= (toAddress == null) ? 0 : toAddress.hashCode();
        h *= 1000003;
        h ^= (attributes == null) ? 0 : attributes.hashCode();
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
      builder.fromAddress = fromAddress;
      builder.toAddress = toAddress;
      builder.attributes = attributes;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<FulfilmentNode> {
      final FromAddress.Mapper fromAddressFieldMapper = new FromAddress.Mapper();

      final ToAddress.Mapper toAddressFieldMapper = new ToAddress.Mapper();

      final Attribute2.Mapper attribute2FieldMapper = new Attribute2.Mapper();

      @Override
      public FulfilmentNode map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[5]);
        final Object updatedOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[6]);
        final FromAddress fromAddress = reader.readObject($responseFields[7], new ResponseReader.ObjectReader<FromAddress>() {
          @Override
          public FromAddress read(ResponseReader reader) {
            return fromAddressFieldMapper.map(reader);
          }
        });
        final ToAddress toAddress = reader.readObject($responseFields[8], new ResponseReader.ObjectReader<ToAddress>() {
          @Override
          public ToAddress read(ResponseReader reader) {
            return toAddressFieldMapper.map(reader);
          }
        });
        final List<Attribute2> attributes = reader.readList($responseFields[9], new ResponseReader.ListReader<Attribute2>() {
          @Override
          public Attribute2 read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<Attribute2>() {
              @Override
              public Attribute2 read(ResponseReader reader) {
                return attribute2FieldMapper.map(reader);
              }
            });
          }
        });
        return new FulfilmentNode(__typename, id, ref, type, status, createdOn, updatedOn, fromAddress, toAddress, attributes);
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

      private @Nullable FromAddress fromAddress;

      private @Nullable ToAddress toAddress;

      private @Nullable List<Attribute2> attributes;

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

      public Builder fromAddress(@Nullable FromAddress fromAddress) {
        this.fromAddress = fromAddress;
        return this;
      }

      public Builder toAddress(@Nullable ToAddress toAddress) {
        this.toAddress = toAddress;
        return this;
      }

      public Builder attributes(@Nullable List<Attribute2> attributes) {
        this.attributes = attributes;
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

      public Builder attributes(@Nonnull Mutator<List<Attribute2.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<Attribute2.Builder> builders = new ArrayList<>();
        if (this.attributes != null) {
          for (Attribute2 item : this.attributes) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<Attribute2> attributes = new ArrayList<>();
        for (Attribute2.Builder item : builders) {
          attributes.add(item != null ? item.build() : null);
        }
        this.attributes = attributes;
        return this;
      }

      public FulfilmentNode build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(type, "type == null");
        return new FulfilmentNode(__typename, id, ref, type, status, createdOn, updatedOn, fromAddress, toAddress, attributes);
      }
    }
  }

  public static class FromAddress {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("street", "street", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("city", "city", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("state", "state", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("postcode", "postcode", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("country", "country", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("latitude", "latitude", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("longitude", "longitude", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    final @Nonnull String id;

    final @Nullable String type;

    final @Nullable String name;

    final @Nullable String street;

    final @Nullable String city;

    final @Nullable String state;

    final @Nullable String postcode;

    final @Nullable String country;

    final @Nullable Double latitude;

    final @Nullable Double longitude;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public FromAddress(@Nonnull String __typename, @Nullable String ref, @Nonnull String id,
        @Nullable String type, @Nullable String name, @Nullable String street,
        @Nullable String city, @Nullable String state, @Nullable String postcode,
        @Nullable String country, @Nullable Double latitude, @Nullable Double longitude) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
      this.id = Utils.checkNotNull(id, "id == null");
      this.type = type;
      this.name = name;
      this.street = street;
      this.city = city;
      this.state = state;
      this.postcode = postcode;
      this.country = country;
      this.latitude = latitude;
      this.longitude = longitude;
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
     *  Type of Address, to support legacy address, the value can be AGENT and ORDER
     */
    public @Nullable String type() {
      return this.type;
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
     *  Country
     */
    public @Nullable String country() {
      return this.country;
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

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], id);
          writer.writeString($responseFields[3], type);
          writer.writeString($responseFields[4], name);
          writer.writeString($responseFields[5], street);
          writer.writeString($responseFields[6], city);
          writer.writeString($responseFields[7], state);
          writer.writeString($responseFields[8], postcode);
          writer.writeString($responseFields[9], country);
          writer.writeDouble($responseFields[10], latitude);
          writer.writeDouble($responseFields[11], longitude);
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
          + "type=" + type + ", "
          + "name=" + name + ", "
          + "street=" + street + ", "
          + "city=" + city + ", "
          + "state=" + state + ", "
          + "postcode=" + postcode + ", "
          + "country=" + country + ", "
          + "latitude=" + latitude + ", "
          + "longitude=" + longitude
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
         && ((this.type == null) ? (that.type == null) : this.type.equals(that.type))
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
         && ((this.street == null) ? (that.street == null) : this.street.equals(that.street))
         && ((this.city == null) ? (that.city == null) : this.city.equals(that.city))
         && ((this.state == null) ? (that.state == null) : this.state.equals(that.state))
         && ((this.postcode == null) ? (that.postcode == null) : this.postcode.equals(that.postcode))
         && ((this.country == null) ? (that.country == null) : this.country.equals(that.country))
         && ((this.latitude == null) ? (that.latitude == null) : this.latitude.equals(that.latitude))
         && ((this.longitude == null) ? (that.longitude == null) : this.longitude.equals(that.longitude));
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
        h ^= (type == null) ? 0 : type.hashCode();
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
        h ^= (country == null) ? 0 : country.hashCode();
        h *= 1000003;
        h ^= (latitude == null) ? 0 : latitude.hashCode();
        h *= 1000003;
        h ^= (longitude == null) ? 0 : longitude.hashCode();
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
      builder.type = type;
      builder.name = name;
      builder.street = street;
      builder.city = city;
      builder.state = state;
      builder.postcode = postcode;
      builder.country = country;
      builder.latitude = latitude;
      builder.longitude = longitude;
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
        final String type = reader.readString($responseFields[3]);
        final String name = reader.readString($responseFields[4]);
        final String street = reader.readString($responseFields[5]);
        final String city = reader.readString($responseFields[6]);
        final String state = reader.readString($responseFields[7]);
        final String postcode = reader.readString($responseFields[8]);
        final String country = reader.readString($responseFields[9]);
        final Double latitude = reader.readDouble($responseFields[10]);
        final Double longitude = reader.readDouble($responseFields[11]);
        return new FromAddress(__typename, ref, id, type, name, street, city, state, postcode, country, latitude, longitude);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      private @Nonnull String id;

      private @Nullable String type;

      private @Nullable String name;

      private @Nullable String street;

      private @Nullable String city;

      private @Nullable String state;

      private @Nullable String postcode;

      private @Nullable String country;

      private @Nullable Double latitude;

      private @Nullable Double longitude;

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

      public Builder type(@Nullable String type) {
        this.type = type;
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

      public Builder country(@Nullable String country) {
        this.country = country;
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

      public FromAddress build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new FromAddress(__typename, ref, id, type, name, street, city, state, postcode, country, latitude, longitude);
      }
    }
  }

  public static class ToAddress {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("street", "street", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("city", "city", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("state", "state", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("postcode", "postcode", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("country", "country", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("latitude", "latitude", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("longitude", "longitude", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    final @Nullable String type;

    final @Nonnull String id;

    final @Nullable String name;

    final @Nullable String street;

    final @Nullable String city;

    final @Nullable String state;

    final @Nullable String postcode;

    final @Nullable String country;

    final @Nullable Double latitude;

    final @Nullable Double longitude;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ToAddress(@Nonnull String __typename, @Nullable String ref, @Nullable String type,
        @Nonnull String id, @Nullable String name, @Nullable String street, @Nullable String city,
        @Nullable String state, @Nullable String postcode, @Nullable String country,
        @Nullable Double latitude, @Nullable Double longitude) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
      this.type = type;
      this.id = Utils.checkNotNull(id, "id == null");
      this.name = name;
      this.street = street;
      this.city = city;
      this.state = state;
      this.postcode = postcode;
      this.country = country;
      this.latitude = latitude;
      this.longitude = longitude;
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
     *  Type of Address, to support legacy address, the value can be AGENT and ORDER
     */
    public @Nullable String type() {
      return this.type;
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
     *  Country
     */
    public @Nullable String country() {
      return this.country;
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

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeString($responseFields[2], type);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], id);
          writer.writeString($responseFields[4], name);
          writer.writeString($responseFields[5], street);
          writer.writeString($responseFields[6], city);
          writer.writeString($responseFields[7], state);
          writer.writeString($responseFields[8], postcode);
          writer.writeString($responseFields[9], country);
          writer.writeDouble($responseFields[10], latitude);
          writer.writeDouble($responseFields[11], longitude);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ToAddress{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "type=" + type + ", "
          + "id=" + id + ", "
          + "name=" + name + ", "
          + "street=" + street + ", "
          + "city=" + city + ", "
          + "state=" + state + ", "
          + "postcode=" + postcode + ", "
          + "country=" + country + ", "
          + "latitude=" + latitude + ", "
          + "longitude=" + longitude
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
         && ((this.type == null) ? (that.type == null) : this.type.equals(that.type))
         && this.id.equals(that.id)
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
         && ((this.street == null) ? (that.street == null) : this.street.equals(that.street))
         && ((this.city == null) ? (that.city == null) : this.city.equals(that.city))
         && ((this.state == null) ? (that.state == null) : this.state.equals(that.state))
         && ((this.postcode == null) ? (that.postcode == null) : this.postcode.equals(that.postcode))
         && ((this.country == null) ? (that.country == null) : this.country.equals(that.country))
         && ((this.latitude == null) ? (that.latitude == null) : this.latitude.equals(that.latitude))
         && ((this.longitude == null) ? (that.longitude == null) : this.longitude.equals(that.longitude));
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
        h ^= (type == null) ? 0 : type.hashCode();
        h *= 1000003;
        h ^= id.hashCode();
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
        h ^= (country == null) ? 0 : country.hashCode();
        h *= 1000003;
        h ^= (latitude == null) ? 0 : latitude.hashCode();
        h *= 1000003;
        h ^= (longitude == null) ? 0 : longitude.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.ref = ref;
      builder.type = type;
      builder.id = id;
      builder.name = name;
      builder.street = street;
      builder.city = city;
      builder.state = state;
      builder.postcode = postcode;
      builder.country = country;
      builder.latitude = latitude;
      builder.longitude = longitude;
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
        final String type = reader.readString($responseFields[2]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        final String name = reader.readString($responseFields[4]);
        final String street = reader.readString($responseFields[5]);
        final String city = reader.readString($responseFields[6]);
        final String state = reader.readString($responseFields[7]);
        final String postcode = reader.readString($responseFields[8]);
        final String country = reader.readString($responseFields[9]);
        final Double latitude = reader.readDouble($responseFields[10]);
        final Double longitude = reader.readDouble($responseFields[11]);
        return new ToAddress(__typename, ref, type, id, name, street, city, state, postcode, country, latitude, longitude);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      private @Nullable String type;

      private @Nonnull String id;

      private @Nullable String name;

      private @Nullable String street;

      private @Nullable String city;

      private @Nullable String state;

      private @Nullable String postcode;

      private @Nullable String country;

      private @Nullable Double latitude;

      private @Nullable Double longitude;

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

      public Builder type(@Nullable String type) {
        this.type = type;
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

      public Builder country(@Nullable String country) {
        this.country = country;
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

      public ToAddress build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new ToAddress(__typename, ref, type, id, name, street, city, state, postcode, country, latitude, longitude);
      }
    }
  }

  public static class Attribute2 {
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

    public Attribute2(@Nonnull String __typename, @Nonnull String name, @Nonnull String type,
        @Nonnull Object value) {
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
        $toString = "Attribute2{"
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
      if (o instanceof Attribute2) {
        Attribute2 that = (Attribute2) o;
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

    public static final class Mapper implements ResponseFieldMapper<Attribute2> {
      @Override
      public Attribute2 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String name = reader.readString($responseFields[1]);
        final String type = reader.readString($responseFields[2]);
        final Object value = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        return new Attribute2(__typename, name, type, value);
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

      public Attribute2 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(value, "value == null");
        return new Attribute2(__typename, name, type, value);
      }
    }
  }
}
