package com.fluentcommerce.graphql.query.fulfilment;

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
public final class GetFulfillmentByRefQuery implements Query<GetFulfillmentByRefQuery.Data, GetFulfillmentByRefQuery.Data, GetFulfillmentByRefQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query GetFulfillmentByRef($ref: [String!], $fulfilmentCount: Int, $fulfilmentCursor: String, $includeAttributes: Boolean!, $includeItems: Boolean!, $includefromAddress: Boolean!, $includeOrder: Boolean!, $includeToAddress: Boolean!, $fulfilmentItemsCount: Int, $fulfilmentItemsCursor: String, $withOrderItem: Boolean!) {\n"
      + "  fulfilments(ref: $ref, first: $fulfilmentCount, after: $fulfilmentCursor) {\n"
      + "    __typename\n"
      + "    edges {\n"
      + "      __typename\n"
      + "      node {\n"
      + "        __typename\n"
      + "        id\n"
      + "        ref\n"
      + "        status\n"
      + "        createdOn\n"
      + "        updatedOn\n"
      + "        deliveryType\n"
      + "        type\n"
      + "        eta\n"
      + "        expiryTime\n"
      + "        order @include(if: $includeOrder) {\n"
      + "          __typename\n"
      + "          id\n"
      + "          ref\n"
      + "          type\n"
      + "          status\n"
      + "        }\n"
      + "        user {\n"
      + "          __typename\n"
      + "          firstName\n"
      + "          lastName\n"
      + "          username\n"
      + "          primaryEmail\n"
      + "          primaryPhone\n"
      + "          title\n"
      + "          ref\n"
      + "        }\n"
      + "        attributes @include(if: $includeAttributes) {\n"
      + "          __typename\n"
      + "          name\n"
      + "          type\n"
      + "          value\n"
      + "        }\n"
      + "        fromAddress @include(if: $includefromAddress) {\n"
      + "          __typename\n"
      + "          id\n"
      + "          type\n"
      + "          createdOn\n"
      + "          name\n"
      + "          street\n"
      + "          city\n"
      + "          state\n"
      + "          postcode\n"
      + "          region\n"
      + "          country\n"
      + "          latitude\n"
      + "          longitude\n"
      + "        }\n"
      + "        toAddress @include(if: $includeToAddress) {\n"
      + "          __typename\n"
      + "          id\n"
      + "          type\n"
      + "          createdOn\n"
      + "          companyName\n"
      + "          name\n"
      + "          street\n"
      + "          city\n"
      + "          state\n"
      + "          postcode\n"
      + "          region\n"
      + "          country\n"
      + "          latitude\n"
      + "          longitude\n"
      + "          ref\n"
      + "        }\n"
      + "        items(first: $fulfilmentItemsCount, after: $fulfilmentItemsCursor) @include(if: $includeItems) {\n"
      + "          __typename\n"
      + "          edges {\n"
      + "            __typename\n"
      + "            item: node {\n"
      + "              __typename\n"
      + "              id\n"
      + "              ref\n"
      + "              status\n"
      + "              requestedQuantity\n"
      + "              filledQuantity\n"
      + "              rejectedQuantity\n"
      + "              orderItem @include(if: $withOrderItem) {\n"
      + "                __typename\n"
      + "                id\n"
      + "                createdOn\n"
      + "                updatedOn\n"
      + "                ref\n"
      + "                quantity\n"
      + "                paidPrice\n"
      + "                currency\n"
      + "                totalPrice\n"
      + "                totalTaxPrice\n"
      + "                price\n"
      + "                taxPrice\n"
      + "                attributes {\n"
      + "                  __typename\n"
      + "                  name\n"
      + "                  type\n"
      + "                  value\n"
      + "                }\n"
      + "                product {\n"
      + "                  __typename\n"
      + "                  name\n"
      + "                  ... on VariantProduct {\n"
      + "                    ref\n"
      + "                  }\n"
      + "                }\n"
      + "              }\n"
      + "            }\n"
      + "            cursor\n"
      + "          }\n"
      + "          pageInfo {\n"
      + "            __typename\n"
      + "            hasNextPage\n"
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
      return "GetFulfillmentByRef";
    }
  };

  private final GetFulfillmentByRefQuery.Variables variables;

  public GetFulfillmentByRefQuery(@Nullable List<String> ref, @Nullable Integer fulfilmentCount,
      @Nullable String fulfilmentCursor, boolean includeAttributes, boolean includeItems,
      boolean includefromAddress, boolean includeOrder, boolean includeToAddress,
      @Nullable Integer fulfilmentItemsCount, @Nullable String fulfilmentItemsCursor,
      boolean withOrderItem) {
    variables = new GetFulfillmentByRefQuery.Variables(ref, fulfilmentCount, fulfilmentCursor, includeAttributes, includeItems, includefromAddress, includeOrder, includeToAddress, fulfilmentItemsCount, fulfilmentItemsCursor, withOrderItem);
  }

  @Override
  public String operationId() {
    return "bde1d2539dcd794c81dac0849c5dbd97b3ef4be7f1a5c9158c87a4b46c8fb891";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public GetFulfillmentByRefQuery.Data wrapData(GetFulfillmentByRefQuery.Data data) {
    return data;
  }

  @Override
  public GetFulfillmentByRefQuery.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<GetFulfillmentByRefQuery.Data> responseFieldMapper() {
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

    private @Nullable Integer fulfilmentCount;

    private @Nullable String fulfilmentCursor;

    private boolean includeAttributes;

    private boolean includeItems;

    private boolean includefromAddress;

    private boolean includeOrder;

    private boolean includeToAddress;

    private @Nullable Integer fulfilmentItemsCount;

    private @Nullable String fulfilmentItemsCursor;

    private boolean withOrderItem;

    Builder() {
    }

    public Builder ref(@Nullable List<String> ref) {
      this.ref = ref;
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

    public Builder includeAttributes(boolean includeAttributes) {
      this.includeAttributes = includeAttributes;
      return this;
    }

    public Builder includeItems(boolean includeItems) {
      this.includeItems = includeItems;
      return this;
    }

    public Builder includefromAddress(boolean includefromAddress) {
      this.includefromAddress = includefromAddress;
      return this;
    }

    public Builder includeOrder(boolean includeOrder) {
      this.includeOrder = includeOrder;
      return this;
    }

    public Builder includeToAddress(boolean includeToAddress) {
      this.includeToAddress = includeToAddress;
      return this;
    }

    public Builder fulfilmentItemsCount(@Nullable Integer fulfilmentItemsCount) {
      this.fulfilmentItemsCount = fulfilmentItemsCount;
      return this;
    }

    public Builder fulfilmentItemsCursor(@Nullable String fulfilmentItemsCursor) {
      this.fulfilmentItemsCursor = fulfilmentItemsCursor;
      return this;
    }

    public Builder withOrderItem(boolean withOrderItem) {
      this.withOrderItem = withOrderItem;
      return this;
    }

    public GetFulfillmentByRefQuery build() {
      return new GetFulfillmentByRefQuery(ref, fulfilmentCount, fulfilmentCursor, includeAttributes, includeItems, includefromAddress, includeOrder, includeToAddress, fulfilmentItemsCount, fulfilmentItemsCursor, withOrderItem);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable List<String> ref;

    private final @Nullable Integer fulfilmentCount;

    private final @Nullable String fulfilmentCursor;

    private final boolean includeAttributes;

    private final boolean includeItems;

    private final boolean includefromAddress;

    private final boolean includeOrder;

    private final boolean includeToAddress;

    private final @Nullable Integer fulfilmentItemsCount;

    private final @Nullable String fulfilmentItemsCursor;

    private final boolean withOrderItem;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable List<String> ref, @Nullable Integer fulfilmentCount,
        @Nullable String fulfilmentCursor, boolean includeAttributes, boolean includeItems,
        boolean includefromAddress, boolean includeOrder, boolean includeToAddress,
        @Nullable Integer fulfilmentItemsCount, @Nullable String fulfilmentItemsCursor,
        boolean withOrderItem) {
      this.ref = ref;
      this.fulfilmentCount = fulfilmentCount;
      this.fulfilmentCursor = fulfilmentCursor;
      this.includeAttributes = includeAttributes;
      this.includeItems = includeItems;
      this.includefromAddress = includefromAddress;
      this.includeOrder = includeOrder;
      this.includeToAddress = includeToAddress;
      this.fulfilmentItemsCount = fulfilmentItemsCount;
      this.fulfilmentItemsCursor = fulfilmentItemsCursor;
      this.withOrderItem = withOrderItem;
      this.valueMap.put("ref", ref);
      this.valueMap.put("fulfilmentCount", fulfilmentCount);
      this.valueMap.put("fulfilmentCursor", fulfilmentCursor);
      this.valueMap.put("includeAttributes", includeAttributes);
      this.valueMap.put("includeItems", includeItems);
      this.valueMap.put("includefromAddress", includefromAddress);
      this.valueMap.put("includeOrder", includeOrder);
      this.valueMap.put("includeToAddress", includeToAddress);
      this.valueMap.put("fulfilmentItemsCount", fulfilmentItemsCount);
      this.valueMap.put("fulfilmentItemsCursor", fulfilmentItemsCursor);
      this.valueMap.put("withOrderItem", withOrderItem);
    }

    public @Nullable List<String> ref() {
      return ref;
    }

    public @Nullable Integer fulfilmentCount() {
      return fulfilmentCount;
    }

    public @Nullable String fulfilmentCursor() {
      return fulfilmentCursor;
    }

    public boolean includeAttributes() {
      return includeAttributes;
    }

    public boolean includeItems() {
      return includeItems;
    }

    public boolean includefromAddress() {
      return includefromAddress;
    }

    public boolean includeOrder() {
      return includeOrder;
    }

    public boolean includeToAddress() {
      return includeToAddress;
    }

    public @Nullable Integer fulfilmentItemsCount() {
      return fulfilmentItemsCount;
    }

    public @Nullable String fulfilmentItemsCursor() {
      return fulfilmentItemsCursor;
    }

    public boolean withOrderItem() {
      return withOrderItem;
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
          writer.writeInt("fulfilmentCount", fulfilmentCount);
          writer.writeString("fulfilmentCursor", fulfilmentCursor);
          writer.writeBoolean("includeAttributes", includeAttributes);
          writer.writeBoolean("includeItems", includeItems);
          writer.writeBoolean("includefromAddress", includefromAddress);
          writer.writeBoolean("includeOrder", includeOrder);
          writer.writeBoolean("includeToAddress", includeToAddress);
          writer.writeInt("fulfilmentItemsCount", fulfilmentItemsCount);
          writer.writeString("fulfilmentItemsCursor", fulfilmentItemsCursor);
          writer.writeBoolean("withOrderItem", withOrderItem);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("fulfilments", "fulfilments", new UnmodifiableMapBuilder<String, Object>(3)
        .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "ref")
        .build())
        .put("after", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "fulfilmentCursor")
        .build())
        .put("first", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "fulfilmentCount")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable Fulfilments fulfilments;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable Fulfilments fulfilments) {
      this.fulfilments = fulfilments;
    }

    /**
     *  Search for Fulfilment entities
     */
    public @Nullable Fulfilments fulfilments() {
      return this.fulfilments;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], fulfilments != null ? fulfilments.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
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
      if (o instanceof Data) {
        Data that = (Data) o;
        return ((this.fulfilments == null) ? (that.fulfilments == null) : this.fulfilments.equals(that.fulfilments));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (fulfilments == null) ? 0 : fulfilments.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.fulfilments = fulfilments;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final Fulfilments.Mapper fulfilmentsFieldMapper = new Fulfilments.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final Fulfilments fulfilments = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<Fulfilments>() {
          @Override
          public Fulfilments read(ResponseReader reader) {
            return fulfilmentsFieldMapper.map(reader);
          }
        });
        return new Data(fulfilments);
      }
    }

    public static final class Builder {
      private @Nullable Fulfilments fulfilments;

      Builder() {
      }

      public Builder fulfilments(@Nullable Fulfilments fulfilments) {
        this.fulfilments = fulfilments;
        return this;
      }

      public Builder fulfilments(@Nonnull Mutator<Fulfilments.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Fulfilments.Builder builder = this.fulfilments != null ? this.fulfilments.toBuilder() : Fulfilments.builder();
        mutator.accept(builder);
        this.fulfilments = builder.build();
        return this;
      }

      public Data build() {
        return new Data(fulfilments);
      }
    }
  }

  public static class Fulfilments {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("edges", "edges", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<Edge> edges;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Fulfilments(@Nonnull String __typename, @Nullable List<Edge> edges) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.edges = edges;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to Fulfilment type node
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
        $toString = "Fulfilments{"
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
      if (o instanceof Fulfilments) {
        Fulfilments that = (Fulfilments) o;
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

    public static final class Mapper implements ResponseFieldMapper<Fulfilments> {
      final Edge.Mapper edgeFieldMapper = new Edge.Mapper();

      @Override
      public Fulfilments map(ResponseReader reader) {
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
        return new Fulfilments(__typename, edges);
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

      public Fulfilments build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Fulfilments(__typename, edges);
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
     *  The item at the end of the Fulfilment edge
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
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("updatedOn", "updatedOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("deliveryType", "deliveryType", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("eta", "eta", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("expiryTime", "expiryTime", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("order", "order", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeOrder", false))),
      ResponseField.forObject("user", "user", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeAttributes", false))),
      ResponseField.forObject("fromAddress", "fromAddress", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includefromAddress", false))),
      ResponseField.forObject("toAddress", "toAddress", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeToAddress", false))),
      ResponseField.forObject("items", "items", new UnmodifiableMapBuilder<String, Object>(2)
        .put("after", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "fulfilmentItemsCursor")
        .build())
        .put("first", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "fulfilmentItemsCount")
        .build())
      .build(), true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeItems", false)))
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final @Nullable String status;

    final @Nullable Object createdOn;

    final @Nullable Object updatedOn;

    final @Nullable String deliveryType;

    final @Nonnull String type;

    final @Nullable String eta;

    final @Nullable Object expiryTime;

    final @Nullable Order order;

    final @Nullable User user;

    final @Nullable List<Attribute> attributes;

    final @Nullable FromAddress fromAddress;

    final @Nullable ToAddress toAddress;

    final @Nullable Items items;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Node(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        @Nullable String status, @Nullable Object createdOn, @Nullable Object updatedOn,
        @Nullable String deliveryType, @Nonnull String type, @Nullable String eta,
        @Nullable Object expiryTime, @Nullable Order order, @Nullable User user,
        @Nullable List<Attribute> attributes, @Nullable FromAddress fromAddress,
        @Nullable ToAddress toAddress, @Nullable Items items) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.status = status;
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.deliveryType = deliveryType;
      this.type = Utils.checkNotNull(type, "type == null");
      this.eta = eta;
      this.expiryTime = expiryTime;
      this.order = order;
      this.user = user;
      this.attributes = attributes;
      this.fromAddress = fromAddress;
      this.toAddress = toAddress;
      this.items = items;
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
     *  Type of delivery. Supported values are _STANDARD_, _OVERNIGHT_ and _EXPRESS_.
     */
    public @Nullable String deliveryType() {
      return this.deliveryType;
    }

    /**
     *  Type of the `Fulfilment`, typically used by the Orchestration Engine to determine the workflow that should be applied. Unless stated otherwise, no values are enforced by the platform.<br/>
     *  Type of the Fulfilment. Supports all values.
     */
    public @Nonnull String type() {
      return this.type;
    }

    /**
     *  The estimated time of completing the fulfilment.
     */
    public @Nullable String eta() {
      return this.eta;
    }

    /**
     *  Expiry time for the fulfilment
     */
    public @Nullable Object expiryTime() {
      return this.expiryTime;
    }

    /**
     *  The associated `Order`
     */
    public @Nullable Order order() {
      return this.order;
    }

    /**
     *  The associated `Customer`
     */
    public @Nullable User user() {
      return this.user;
    }

    /**
     *  Attributes of fulfilment
     */
    public @Nullable List<Attribute> attributes() {
      return this.attributes;
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
     *  Connection representing a list of `FulfilmentItem`s
     */
    public @Nullable Items items() {
      return this.items;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeString($responseFields[3], status);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[4], createdOn);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[5], updatedOn);
          writer.writeString($responseFields[6], deliveryType);
          writer.writeString($responseFields[7], type);
          writer.writeString($responseFields[8], eta);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[9], expiryTime);
          writer.writeObject($responseFields[10], order != null ? order.marshaller() : null);
          writer.writeObject($responseFields[11], user != null ? user.marshaller() : null);
          writer.writeList($responseFields[12], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute) value).marshaller());
            }
          });
          writer.writeObject($responseFields[13], fromAddress != null ? fromAddress.marshaller() : null);
          writer.writeObject($responseFields[14], toAddress != null ? toAddress.marshaller() : null);
          writer.writeObject($responseFields[15], items != null ? items.marshaller() : null);
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
          + "status=" + status + ", "
          + "createdOn=" + createdOn + ", "
          + "updatedOn=" + updatedOn + ", "
          + "deliveryType=" + deliveryType + ", "
          + "type=" + type + ", "
          + "eta=" + eta + ", "
          + "expiryTime=" + expiryTime + ", "
          + "order=" + order + ", "
          + "user=" + user + ", "
          + "attributes=" + attributes + ", "
          + "fromAddress=" + fromAddress + ", "
          + "toAddress=" + toAddress + ", "
          + "items=" + items
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
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.updatedOn == null) ? (that.updatedOn == null) : this.updatedOn.equals(that.updatedOn))
         && ((this.deliveryType == null) ? (that.deliveryType == null) : this.deliveryType.equals(that.deliveryType))
         && this.type.equals(that.type)
         && ((this.eta == null) ? (that.eta == null) : this.eta.equals(that.eta))
         && ((this.expiryTime == null) ? (that.expiryTime == null) : this.expiryTime.equals(that.expiryTime))
         && ((this.order == null) ? (that.order == null) : this.order.equals(that.order))
         && ((this.user == null) ? (that.user == null) : this.user.equals(that.user))
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes))
         && ((this.fromAddress == null) ? (that.fromAddress == null) : this.fromAddress.equals(that.fromAddress))
         && ((this.toAddress == null) ? (that.toAddress == null) : this.toAddress.equals(that.toAddress))
         && ((this.items == null) ? (that.items == null) : this.items.equals(that.items));
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
        h ^= (status == null) ? 0 : status.hashCode();
        h *= 1000003;
        h ^= (createdOn == null) ? 0 : createdOn.hashCode();
        h *= 1000003;
        h ^= (updatedOn == null) ? 0 : updatedOn.hashCode();
        h *= 1000003;
        h ^= (deliveryType == null) ? 0 : deliveryType.hashCode();
        h *= 1000003;
        h ^= type.hashCode();
        h *= 1000003;
        h ^= (eta == null) ? 0 : eta.hashCode();
        h *= 1000003;
        h ^= (expiryTime == null) ? 0 : expiryTime.hashCode();
        h *= 1000003;
        h ^= (order == null) ? 0 : order.hashCode();
        h *= 1000003;
        h ^= (user == null) ? 0 : user.hashCode();
        h *= 1000003;
        h ^= (attributes == null) ? 0 : attributes.hashCode();
        h *= 1000003;
        h ^= (fromAddress == null) ? 0 : fromAddress.hashCode();
        h *= 1000003;
        h ^= (toAddress == null) ? 0 : toAddress.hashCode();
        h *= 1000003;
        h ^= (items == null) ? 0 : items.hashCode();
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
      builder.status = status;
      builder.createdOn = createdOn;
      builder.updatedOn = updatedOn;
      builder.deliveryType = deliveryType;
      builder.type = type;
      builder.eta = eta;
      builder.expiryTime = expiryTime;
      builder.order = order;
      builder.user = user;
      builder.attributes = attributes;
      builder.fromAddress = fromAddress;
      builder.toAddress = toAddress;
      builder.items = items;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Node> {
      final Order.Mapper orderFieldMapper = new Order.Mapper();

      final User.Mapper userFieldMapper = new User.Mapper();

      final Attribute.Mapper attributeFieldMapper = new Attribute.Mapper();

      final FromAddress.Mapper fromAddressFieldMapper = new FromAddress.Mapper();

      final ToAddress.Mapper toAddressFieldMapper = new ToAddress.Mapper();

      final Items.Mapper itemsFieldMapper = new Items.Mapper();

      @Override
      public Node map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String status = reader.readString($responseFields[3]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[4]);
        final Object updatedOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[5]);
        final String deliveryType = reader.readString($responseFields[6]);
        final String type = reader.readString($responseFields[7]);
        final String eta = reader.readString($responseFields[8]);
        final Object expiryTime = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[9]);
        final Order order = reader.readObject($responseFields[10], new ResponseReader.ObjectReader<Order>() {
          @Override
          public Order read(ResponseReader reader) {
            return orderFieldMapper.map(reader);
          }
        });
        final User user = reader.readObject($responseFields[11], new ResponseReader.ObjectReader<User>() {
          @Override
          public User read(ResponseReader reader) {
            return userFieldMapper.map(reader);
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
        final FromAddress fromAddress = reader.readObject($responseFields[13], new ResponseReader.ObjectReader<FromAddress>() {
          @Override
          public FromAddress read(ResponseReader reader) {
            return fromAddressFieldMapper.map(reader);
          }
        });
        final ToAddress toAddress = reader.readObject($responseFields[14], new ResponseReader.ObjectReader<ToAddress>() {
          @Override
          public ToAddress read(ResponseReader reader) {
            return toAddressFieldMapper.map(reader);
          }
        });
        final Items items = reader.readObject($responseFields[15], new ResponseReader.ObjectReader<Items>() {
          @Override
          public Items read(ResponseReader reader) {
            return itemsFieldMapper.map(reader);
          }
        });
        return new Node(__typename, id, ref, status, createdOn, updatedOn, deliveryType, type, eta, expiryTime, order, user, attributes, fromAddress, toAddress, items);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String ref;

      private @Nullable String status;

      private @Nullable Object createdOn;

      private @Nullable Object updatedOn;

      private @Nullable String deliveryType;

      private @Nonnull String type;

      private @Nullable String eta;

      private @Nullable Object expiryTime;

      private @Nullable Order order;

      private @Nullable User user;

      private @Nullable List<Attribute> attributes;

      private @Nullable FromAddress fromAddress;

      private @Nullable ToAddress toAddress;

      private @Nullable Items items;

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

      public Builder deliveryType(@Nullable String deliveryType) {
        this.deliveryType = deliveryType;
        return this;
      }

      public Builder type(@Nonnull String type) {
        this.type = type;
        return this;
      }

      public Builder eta(@Nullable String eta) {
        this.eta = eta;
        return this;
      }

      public Builder expiryTime(@Nullable Object expiryTime) {
        this.expiryTime = expiryTime;
        return this;
      }

      public Builder order(@Nullable Order order) {
        this.order = order;
        return this;
      }

      public Builder user(@Nullable User user) {
        this.user = user;
        return this;
      }

      public Builder attributes(@Nullable List<Attribute> attributes) {
        this.attributes = attributes;
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

      public Builder items(@Nullable Items items) {
        this.items = items;
        return this;
      }

      public Builder order(@Nonnull Mutator<Order.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Order.Builder builder = this.order != null ? this.order.toBuilder() : Order.builder();
        mutator.accept(builder);
        this.order = builder.build();
        return this;
      }

      public Builder user(@Nonnull Mutator<User.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        User.Builder builder = this.user != null ? this.user.toBuilder() : User.builder();
        mutator.accept(builder);
        this.user = builder.build();
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

      public Builder items(@Nonnull Mutator<Items.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Items.Builder builder = this.items != null ? this.items.toBuilder() : Items.builder();
        mutator.accept(builder);
        this.items = builder.build();
        return this;
      }

      public Node build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(type, "type == null");
        return new Node(__typename, id, ref, status, createdOn, updatedOn, deliveryType, type, eta, expiryTime, order, user, attributes, fromAddress, toAddress, items);
      }
    }
  }

  public static class Order {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final @Nonnull String type;

    final @Nullable String status;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Order(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        @Nonnull String type, @Nullable String status) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.type = Utils.checkNotNull(type, "type == null");
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

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeString($responseFields[3], type);
          writer.writeString($responseFields[4], status);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Order{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "type=" + type + ", "
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
      if (o instanceof Order) {
        Order that = (Order) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && this.type.equals(that.type)
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
        h ^= (ref == null) ? 0 : ref.hashCode();
        h *= 1000003;
        h ^= type.hashCode();
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
      builder.ref = ref;
      builder.type = type;
      builder.status = status;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Order> {
      @Override
      public Order map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        return new Order(__typename, id, ref, type, status);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String ref;

      private @Nonnull String type;

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

      public Order build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(type, "type == null");
        return new Order(__typename, id, ref, type, status);
      }
    }
  }

  public static class User {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("firstName", "firstName", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("lastName", "lastName", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("username", "username", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("primaryEmail", "primaryEmail", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("primaryPhone", "primaryPhone", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("title", "title", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String firstName;

    final @Nullable String lastName;

    final @Nullable String username;

    final @Nullable String primaryEmail;

    final @Nullable String primaryPhone;

    final @Nullable String title;

    final @Nullable String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public User(@Nonnull String __typename, @Nullable String firstName, @Nullable String lastName,
        @Nullable String username, @Nullable String primaryEmail, @Nullable String primaryPhone,
        @Nullable String title, @Nullable String ref) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.firstName = firstName;
      this.lastName = lastName;
      this.username = username;
      this.primaryEmail = primaryEmail;
      this.primaryPhone = primaryPhone;
      this.title = title;
      this.ref = ref;
    }

    public @Nonnull String __typename() {
      return this.__typename;
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

    /**
     *  The customer's title
     */
    public @Nullable String title() {
      return this.title;
    }

    /**
     *  Username of the customer
     */
    public @Nullable String ref() {
      return this.ref;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], firstName);
          writer.writeString($responseFields[2], lastName);
          writer.writeString($responseFields[3], username);
          writer.writeString($responseFields[4], primaryEmail);
          writer.writeString($responseFields[5], primaryPhone);
          writer.writeString($responseFields[6], title);
          writer.writeString($responseFields[7], ref);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "User{"
          + "__typename=" + __typename + ", "
          + "firstName=" + firstName + ", "
          + "lastName=" + lastName + ", "
          + "username=" + username + ", "
          + "primaryEmail=" + primaryEmail + ", "
          + "primaryPhone=" + primaryPhone + ", "
          + "title=" + title + ", "
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
      if (o instanceof User) {
        User that = (User) o;
        return this.__typename.equals(that.__typename)
         && ((this.firstName == null) ? (that.firstName == null) : this.firstName.equals(that.firstName))
         && ((this.lastName == null) ? (that.lastName == null) : this.lastName.equals(that.lastName))
         && ((this.username == null) ? (that.username == null) : this.username.equals(that.username))
         && ((this.primaryEmail == null) ? (that.primaryEmail == null) : this.primaryEmail.equals(that.primaryEmail))
         && ((this.primaryPhone == null) ? (that.primaryPhone == null) : this.primaryPhone.equals(that.primaryPhone))
         && ((this.title == null) ? (that.title == null) : this.title.equals(that.title))
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
        h ^= (firstName == null) ? 0 : firstName.hashCode();
        h *= 1000003;
        h ^= (lastName == null) ? 0 : lastName.hashCode();
        h *= 1000003;
        h ^= (username == null) ? 0 : username.hashCode();
        h *= 1000003;
        h ^= (primaryEmail == null) ? 0 : primaryEmail.hashCode();
        h *= 1000003;
        h ^= (primaryPhone == null) ? 0 : primaryPhone.hashCode();
        h *= 1000003;
        h ^= (title == null) ? 0 : title.hashCode();
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
      builder.firstName = firstName;
      builder.lastName = lastName;
      builder.username = username;
      builder.primaryEmail = primaryEmail;
      builder.primaryPhone = primaryPhone;
      builder.title = title;
      builder.ref = ref;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<User> {
      @Override
      public User map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String firstName = reader.readString($responseFields[1]);
        final String lastName = reader.readString($responseFields[2]);
        final String username = reader.readString($responseFields[3]);
        final String primaryEmail = reader.readString($responseFields[4]);
        final String primaryPhone = reader.readString($responseFields[5]);
        final String title = reader.readString($responseFields[6]);
        final String ref = reader.readString($responseFields[7]);
        return new User(__typename, firstName, lastName, username, primaryEmail, primaryPhone, title, ref);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String firstName;

      private @Nullable String lastName;

      private @Nullable String username;

      private @Nullable String primaryEmail;

      private @Nullable String primaryPhone;

      private @Nullable String title;

      private @Nullable String ref;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
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

      public Builder title(@Nullable String title) {
        this.title = title;
        return this;
      }

      public Builder ref(@Nullable String ref) {
        this.ref = ref;
        return this;
      }

      public User build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new User(__typename, firstName, lastName, username, primaryEmail, primaryPhone, title, ref);
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

  public static class FromAddress {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("street", "street", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("city", "city", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("state", "state", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("postcode", "postcode", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("region", "region", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("country", "country", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("latitude", "latitude", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("longitude", "longitude", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String type;

    final @Nullable Object createdOn;

    final @Nullable String name;

    final @Nullable String street;

    final @Nullable String city;

    final @Nullable String state;

    final @Nullable String postcode;

    final @Nullable String region;

    final @Nullable String country;

    final @Nullable Double latitude;

    final @Nullable Double longitude;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public FromAddress(@Nonnull String __typename, @Nonnull String id, @Nullable String type,
        @Nullable Object createdOn, @Nullable String name, @Nullable String street,
        @Nullable String city, @Nullable String state, @Nullable String postcode,
        @Nullable String region, @Nullable String country, @Nullable Double latitude,
        @Nullable Double longitude) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.type = type;
      this.createdOn = createdOn;
      this.name = name;
      this.street = street;
      this.city = city;
      this.state = state;
      this.postcode = postcode;
      this.region = region;
      this.country = country;
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
     *  Time of creation
     */
    public @Nullable Object createdOn() {
      return this.createdOn;
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
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], createdOn);
          writer.writeString($responseFields[4], name);
          writer.writeString($responseFields[5], street);
          writer.writeString($responseFields[6], city);
          writer.writeString($responseFields[7], state);
          writer.writeString($responseFields[8], postcode);
          writer.writeString($responseFields[9], region);
          writer.writeString($responseFields[10], country);
          writer.writeDouble($responseFields[11], latitude);
          writer.writeDouble($responseFields[12], longitude);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "FromAddress{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "type=" + type + ", "
          + "createdOn=" + createdOn + ", "
          + "name=" + name + ", "
          + "street=" + street + ", "
          + "city=" + city + ", "
          + "state=" + state + ", "
          + "postcode=" + postcode + ", "
          + "region=" + region + ", "
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
         && this.id.equals(that.id)
         && ((this.type == null) ? (that.type == null) : this.type.equals(that.type))
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
         && ((this.street == null) ? (that.street == null) : this.street.equals(that.street))
         && ((this.city == null) ? (that.city == null) : this.city.equals(that.city))
         && ((this.state == null) ? (that.state == null) : this.state.equals(that.state))
         && ((this.postcode == null) ? (that.postcode == null) : this.postcode.equals(that.postcode))
         && ((this.region == null) ? (that.region == null) : this.region.equals(that.region))
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
        h ^= id.hashCode();
        h *= 1000003;
        h ^= (type == null) ? 0 : type.hashCode();
        h *= 1000003;
        h ^= (createdOn == null) ? 0 : createdOn.hashCode();
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
      builder.createdOn = createdOn;
      builder.name = name;
      builder.street = street;
      builder.city = city;
      builder.state = state;
      builder.postcode = postcode;
      builder.region = region;
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
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String type = reader.readString($responseFields[2]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        final String name = reader.readString($responseFields[4]);
        final String street = reader.readString($responseFields[5]);
        final String city = reader.readString($responseFields[6]);
        final String state = reader.readString($responseFields[7]);
        final String postcode = reader.readString($responseFields[8]);
        final String region = reader.readString($responseFields[9]);
        final String country = reader.readString($responseFields[10]);
        final Double latitude = reader.readDouble($responseFields[11]);
        final Double longitude = reader.readDouble($responseFields[12]);
        return new FromAddress(__typename, id, type, createdOn, name, street, city, state, postcode, region, country, latitude, longitude);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String type;

      private @Nullable Object createdOn;

      private @Nullable String name;

      private @Nullable String street;

      private @Nullable String city;

      private @Nullable String state;

      private @Nullable String postcode;

      private @Nullable String region;

      private @Nullable String country;

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

      public Builder createdOn(@Nullable Object createdOn) {
        this.createdOn = createdOn;
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
        return new FromAddress(__typename, id, type, createdOn, name, street, city, state, postcode, region, country, latitude, longitude);
      }
    }
  }

  public static class ToAddress {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("companyName", "companyName", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("street", "street", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("city", "city", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("state", "state", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("postcode", "postcode", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("region", "region", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("country", "country", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("latitude", "latitude", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("longitude", "longitude", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String type;

    final @Nullable Object createdOn;

    final @Nullable String companyName;

    final @Nullable String name;

    final @Nullable String street;

    final @Nullable String city;

    final @Nullable String state;

    final @Nullable String postcode;

    final @Nullable String region;

    final @Nullable String country;

    final @Nullable Double latitude;

    final @Nullable Double longitude;

    final @Nullable String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ToAddress(@Nonnull String __typename, @Nonnull String id, @Nullable String type,
        @Nullable Object createdOn, @Nullable String companyName, @Nullable String name,
        @Nullable String street, @Nullable String city, @Nullable String state,
        @Nullable String postcode, @Nullable String region, @Nullable String country,
        @Nullable Double latitude, @Nullable Double longitude, @Nullable String ref) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.type = type;
      this.createdOn = createdOn;
      this.companyName = companyName;
      this.name = name;
      this.street = street;
      this.city = city;
      this.state = state;
      this.postcode = postcode;
      this.region = region;
      this.country = country;
      this.latitude = latitude;
      this.longitude = longitude;
      this.ref = ref;
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
     *  Time of creation
     */
    public @Nullable Object createdOn() {
      return this.createdOn;
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
     *  Location reference
     */
    public @Nullable String ref() {
      return this.ref;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], type);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], createdOn);
          writer.writeString($responseFields[4], companyName);
          writer.writeString($responseFields[5], name);
          writer.writeString($responseFields[6], street);
          writer.writeString($responseFields[7], city);
          writer.writeString($responseFields[8], state);
          writer.writeString($responseFields[9], postcode);
          writer.writeString($responseFields[10], region);
          writer.writeString($responseFields[11], country);
          writer.writeDouble($responseFields[12], latitude);
          writer.writeDouble($responseFields[13], longitude);
          writer.writeString($responseFields[14], ref);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ToAddress{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "type=" + type + ", "
          + "createdOn=" + createdOn + ", "
          + "companyName=" + companyName + ", "
          + "name=" + name + ", "
          + "street=" + street + ", "
          + "city=" + city + ", "
          + "state=" + state + ", "
          + "postcode=" + postcode + ", "
          + "region=" + region + ", "
          + "country=" + country + ", "
          + "latitude=" + latitude + ", "
          + "longitude=" + longitude + ", "
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
      if (o instanceof ToAddress) {
        ToAddress that = (ToAddress) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.type == null) ? (that.type == null) : this.type.equals(that.type))
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.companyName == null) ? (that.companyName == null) : this.companyName.equals(that.companyName))
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
         && ((this.street == null) ? (that.street == null) : this.street.equals(that.street))
         && ((this.city == null) ? (that.city == null) : this.city.equals(that.city))
         && ((this.state == null) ? (that.state == null) : this.state.equals(that.state))
         && ((this.postcode == null) ? (that.postcode == null) : this.postcode.equals(that.postcode))
         && ((this.region == null) ? (that.region == null) : this.region.equals(that.region))
         && ((this.country == null) ? (that.country == null) : this.country.equals(that.country))
         && ((this.latitude == null) ? (that.latitude == null) : this.latitude.equals(that.latitude))
         && ((this.longitude == null) ? (that.longitude == null) : this.longitude.equals(that.longitude))
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
        h ^= id.hashCode();
        h *= 1000003;
        h ^= (type == null) ? 0 : type.hashCode();
        h *= 1000003;
        h ^= (createdOn == null) ? 0 : createdOn.hashCode();
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
        h ^= (latitude == null) ? 0 : latitude.hashCode();
        h *= 1000003;
        h ^= (longitude == null) ? 0 : longitude.hashCode();
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
      builder.id = id;
      builder.type = type;
      builder.createdOn = createdOn;
      builder.companyName = companyName;
      builder.name = name;
      builder.street = street;
      builder.city = city;
      builder.state = state;
      builder.postcode = postcode;
      builder.region = region;
      builder.country = country;
      builder.latitude = latitude;
      builder.longitude = longitude;
      builder.ref = ref;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<ToAddress> {
      @Override
      public ToAddress map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String type = reader.readString($responseFields[2]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        final String companyName = reader.readString($responseFields[4]);
        final String name = reader.readString($responseFields[5]);
        final String street = reader.readString($responseFields[6]);
        final String city = reader.readString($responseFields[7]);
        final String state = reader.readString($responseFields[8]);
        final String postcode = reader.readString($responseFields[9]);
        final String region = reader.readString($responseFields[10]);
        final String country = reader.readString($responseFields[11]);
        final Double latitude = reader.readDouble($responseFields[12]);
        final Double longitude = reader.readDouble($responseFields[13]);
        final String ref = reader.readString($responseFields[14]);
        return new ToAddress(__typename, id, type, createdOn, companyName, name, street, city, state, postcode, region, country, latitude, longitude, ref);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String type;

      private @Nullable Object createdOn;

      private @Nullable String companyName;

      private @Nullable String name;

      private @Nullable String street;

      private @Nullable String city;

      private @Nullable String state;

      private @Nullable String postcode;

      private @Nullable String region;

      private @Nullable String country;

      private @Nullable Double latitude;

      private @Nullable Double longitude;

      private @Nullable String ref;

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

      public Builder createdOn(@Nullable Object createdOn) {
        this.createdOn = createdOn;
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

      public Builder latitude(@Nullable Double latitude) {
        this.latitude = latitude;
        return this;
      }

      public Builder longitude(@Nullable Double longitude) {
        this.longitude = longitude;
        return this;
      }

      public Builder ref(@Nullable String ref) {
        this.ref = ref;
        return this;
      }

      public ToAddress build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new ToAddress(__typename, id, type, createdOn, companyName, name, street, city, state, postcode, region, country, latitude, longitude, ref);
      }
    }
  }

  public static class Items {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("edges", "edges", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("pageInfo", "pageInfo", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<Edge1> edges;

    final @Nullable PageInfo pageInfo;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Items(@Nonnull String __typename, @Nullable List<Edge1> edges,
        @Nullable PageInfo pageInfo) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.edges = edges;
      this.pageInfo = pageInfo;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to FulfilmentItem type node
     */
    public @Nullable List<Edge1> edges() {
      return this.edges;
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
          writer.writeList($responseFields[1], edges, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Edge1) value).marshaller());
            }
          });
          writer.writeObject($responseFields[2], pageInfo != null ? pageInfo.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Items{"
          + "__typename=" + __typename + ", "
          + "edges=" + edges + ", "
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
      if (o instanceof Items) {
        Items that = (Items) o;
        return this.__typename.equals(that.__typename)
         && ((this.edges == null) ? (that.edges == null) : this.edges.equals(that.edges))
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
        h ^= (edges == null) ? 0 : edges.hashCode();
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
      builder.edges = edges;
      builder.pageInfo = pageInfo;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Items> {
      final Edge1.Mapper edge1FieldMapper = new Edge1.Mapper();

      final PageInfo.Mapper pageInfoFieldMapper = new PageInfo.Mapper();

      @Override
      public Items map(ResponseReader reader) {
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
        final PageInfo pageInfo = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<PageInfo>() {
          @Override
          public PageInfo read(ResponseReader reader) {
            return pageInfoFieldMapper.map(reader);
          }
        });
        return new Items(__typename, edges, pageInfo);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable List<Edge1> edges;

      private @Nullable PageInfo pageInfo;

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

      public Builder pageInfo(@Nullable PageInfo pageInfo) {
        this.pageInfo = pageInfo;
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

      public Builder pageInfo(@Nonnull Mutator<PageInfo.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        PageInfo.Builder builder = this.pageInfo != null ? this.pageInfo.toBuilder() : PageInfo.builder();
        mutator.accept(builder);
        this.pageInfo = builder.build();
        return this;
      }

      public Items build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Items(__typename, edges, pageInfo);
      }
    }
  }

  public static class Edge1 {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("item", "node", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("cursor", "cursor", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Item item;

    final @Nullable String cursor;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Edge1(@Nonnull String __typename, @Nullable Item item, @Nullable String cursor) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.item = item;
      this.cursor = cursor;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The item at the end of the FulfilmentItem edge
     */
    public @Nullable Item item() {
      return this.item;
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
          writer.writeObject($responseFields[1], item != null ? item.marshaller() : null);
          writer.writeString($responseFields[2], cursor);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Edge1{"
          + "__typename=" + __typename + ", "
          + "item=" + item + ", "
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
      if (o instanceof Edge1) {
        Edge1 that = (Edge1) o;
        return this.__typename.equals(that.__typename)
         && ((this.item == null) ? (that.item == null) : this.item.equals(that.item))
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
        h ^= (item == null) ? 0 : item.hashCode();
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
      builder.item = item;
      builder.cursor = cursor;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Edge1> {
      final Item.Mapper itemFieldMapper = new Item.Mapper();

      @Override
      public Edge1 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Item item = reader.readObject($responseFields[1], new ResponseReader.ObjectReader<Item>() {
          @Override
          public Item read(ResponseReader reader) {
            return itemFieldMapper.map(reader);
          }
        });
        final String cursor = reader.readString($responseFields[2]);
        return new Edge1(__typename, item, cursor);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Item item;

      private @Nullable String cursor;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder item(@Nullable Item item) {
        this.item = item;
        return this;
      }

      public Builder cursor(@Nullable String cursor) {
        this.cursor = cursor;
        return this;
      }

      public Builder item(@Nonnull Mutator<Item.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Item.Builder builder = this.item != null ? this.item.toBuilder() : Item.builder();
        mutator.accept(builder);
        this.item = builder.build();
        return this;
      }

      public Edge1 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Edge1(__typename, item, cursor);
      }
    }
  }

  public static class Item {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("requestedQuantity", "requestedQuantity", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("filledQuantity", "filledQuantity", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("rejectedQuantity", "rejectedQuantity", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("orderItem", "orderItem", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("withOrderItem", false)))
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final @Nonnull String status;

    final @Nullable Integer requestedQuantity;

    final int filledQuantity;

    final int rejectedQuantity;

    final @Nullable OrderItem orderItem;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Item(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        @Nonnull String status, @Nullable Integer requestedQuantity, int filledQuantity,
        int rejectedQuantity, @Nullable OrderItem orderItem) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.status = Utils.checkNotNull(status, "status == null");
      this.requestedQuantity = requestedQuantity;
      this.filledQuantity = filledQuantity;
      this.rejectedQuantity = rejectedQuantity;
      this.orderItem = orderItem;
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
     *  Status
     */
    public @Nonnull String status() {
      return this.status;
    }

    /**
     *  Number of `OrderItem`s assigned to the fulfilment
     */
    public @Nullable Integer requestedQuantity() {
      return this.requestedQuantity;
    }

    /**
     *  Number of `OrderItem`s confirmed
     */
    public int filledQuantity() {
      return this.filledQuantity;
    }

    /**
     *  Number of `OrderItem`s rejected
     */
    public int rejectedQuantity() {
      return this.rejectedQuantity;
    }

    /**
     *  The associated `OrderItem`
     */
    public @Nullable OrderItem orderItem() {
      return this.orderItem;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeString($responseFields[3], status);
          writer.writeInt($responseFields[4], requestedQuantity);
          writer.writeInt($responseFields[5], filledQuantity);
          writer.writeInt($responseFields[6], rejectedQuantity);
          writer.writeObject($responseFields[7], orderItem != null ? orderItem.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Item{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "status=" + status + ", "
          + "requestedQuantity=" + requestedQuantity + ", "
          + "filledQuantity=" + filledQuantity + ", "
          + "rejectedQuantity=" + rejectedQuantity + ", "
          + "orderItem=" + orderItem
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Item) {
        Item that = (Item) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && this.status.equals(that.status)
         && ((this.requestedQuantity == null) ? (that.requestedQuantity == null) : this.requestedQuantity.equals(that.requestedQuantity))
         && this.filledQuantity == that.filledQuantity
         && this.rejectedQuantity == that.rejectedQuantity
         && ((this.orderItem == null) ? (that.orderItem == null) : this.orderItem.equals(that.orderItem));
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
        h ^= status.hashCode();
        h *= 1000003;
        h ^= (requestedQuantity == null) ? 0 : requestedQuantity.hashCode();
        h *= 1000003;
        h ^= filledQuantity;
        h *= 1000003;
        h ^= rejectedQuantity;
        h *= 1000003;
        h ^= (orderItem == null) ? 0 : orderItem.hashCode();
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
      builder.status = status;
      builder.requestedQuantity = requestedQuantity;
      builder.filledQuantity = filledQuantity;
      builder.rejectedQuantity = rejectedQuantity;
      builder.orderItem = orderItem;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Item> {
      final OrderItem.Mapper orderItemFieldMapper = new OrderItem.Mapper();

      @Override
      public Item map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String status = reader.readString($responseFields[3]);
        final Integer requestedQuantity = reader.readInt($responseFields[4]);
        final int filledQuantity = reader.readInt($responseFields[5]);
        final int rejectedQuantity = reader.readInt($responseFields[6]);
        final OrderItem orderItem = reader.readObject($responseFields[7], new ResponseReader.ObjectReader<OrderItem>() {
          @Override
          public OrderItem read(ResponseReader reader) {
            return orderItemFieldMapper.map(reader);
          }
        });
        return new Item(__typename, id, ref, status, requestedQuantity, filledQuantity, rejectedQuantity, orderItem);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String ref;

      private @Nonnull String status;

      private @Nullable Integer requestedQuantity;

      private int filledQuantity;

      private int rejectedQuantity;

      private @Nullable OrderItem orderItem;

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

      public Builder status(@Nonnull String status) {
        this.status = status;
        return this;
      }

      public Builder requestedQuantity(@Nullable Integer requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
        return this;
      }

      public Builder filledQuantity(int filledQuantity) {
        this.filledQuantity = filledQuantity;
        return this;
      }

      public Builder rejectedQuantity(int rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
        return this;
      }

      public Builder orderItem(@Nullable OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
      }

      public Builder orderItem(@Nonnull Mutator<OrderItem.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        OrderItem.Builder builder = this.orderItem != null ? this.orderItem.toBuilder() : OrderItem.builder();
        mutator.accept(builder);
        this.orderItem = builder.build();
        return this;
      }

      public Item build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(status, "status == null");
        return new Item(__typename, id, ref, status, requestedQuantity, filledQuantity, rejectedQuantity, orderItem);
      }
    }
  }

  public static class OrderItem {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("updatedOn", "updatedOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("quantity", "quantity", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("paidPrice", "paidPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("currency", "currency", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("totalPrice", "totalPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("totalTaxPrice", "totalTaxPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("price", "price", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("taxPrice", "taxPrice", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("product", "product", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable Object createdOn;

    final @Nullable Object updatedOn;

    final @Nullable String ref;

    final int quantity;

    final @Nullable Double paidPrice;

    final @Nullable String currency;

    final @Nullable Double totalPrice;

    final @Nullable Double totalTaxPrice;

    final @Nullable Double price;

    final @Nullable Double taxPrice;

    final @Nullable List<Attribute1> attributes;

    final @Nonnull Product product;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public OrderItem(@Nonnull String __typename, @Nonnull String id, @Nullable Object createdOn,
        @Nullable Object updatedOn, @Nullable String ref, int quantity, @Nullable Double paidPrice,
        @Nullable String currency, @Nullable Double totalPrice, @Nullable Double totalTaxPrice,
        @Nullable Double price, @Nullable Double taxPrice, @Nullable List<Attribute1> attributes,
        @Nonnull Product product) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.ref = ref;
      this.quantity = quantity;
      this.paidPrice = paidPrice;
      this.currency = currency;
      this.totalPrice = totalPrice;
      this.totalTaxPrice = totalTaxPrice;
      this.price = price;
      this.taxPrice = taxPrice;
      this.attributes = attributes;
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
     *  List of `OrderItem` `attribute`s.
     */
    public @Nullable List<Attribute1> attributes() {
      return this.attributes;
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
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], createdOn);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], updatedOn);
          writer.writeString($responseFields[4], ref);
          writer.writeInt($responseFields[5], quantity);
          writer.writeDouble($responseFields[6], paidPrice);
          writer.writeString($responseFields[7], currency);
          writer.writeDouble($responseFields[8], totalPrice);
          writer.writeDouble($responseFields[9], totalTaxPrice);
          writer.writeDouble($responseFields[10], price);
          writer.writeDouble($responseFields[11], taxPrice);
          writer.writeList($responseFields[12], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute1) value).marshaller());
            }
          });
          writer.writeObject($responseFields[13], product.marshaller());
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "OrderItem{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "createdOn=" + createdOn + ", "
          + "updatedOn=" + updatedOn + ", "
          + "ref=" + ref + ", "
          + "quantity=" + quantity + ", "
          + "paidPrice=" + paidPrice + ", "
          + "currency=" + currency + ", "
          + "totalPrice=" + totalPrice + ", "
          + "totalTaxPrice=" + totalTaxPrice + ", "
          + "price=" + price + ", "
          + "taxPrice=" + taxPrice + ", "
          + "attributes=" + attributes + ", "
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
      if (o instanceof OrderItem) {
        OrderItem that = (OrderItem) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.updatedOn == null) ? (that.updatedOn == null) : this.updatedOn.equals(that.updatedOn))
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && this.quantity == that.quantity
         && ((this.paidPrice == null) ? (that.paidPrice == null) : this.paidPrice.equals(that.paidPrice))
         && ((this.currency == null) ? (that.currency == null) : this.currency.equals(that.currency))
         && ((this.totalPrice == null) ? (that.totalPrice == null) : this.totalPrice.equals(that.totalPrice))
         && ((this.totalTaxPrice == null) ? (that.totalTaxPrice == null) : this.totalTaxPrice.equals(that.totalTaxPrice))
         && ((this.price == null) ? (that.price == null) : this.price.equals(that.price))
         && ((this.taxPrice == null) ? (that.taxPrice == null) : this.taxPrice.equals(that.taxPrice))
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes))
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
        h ^= (createdOn == null) ? 0 : createdOn.hashCode();
        h *= 1000003;
        h ^= (updatedOn == null) ? 0 : updatedOn.hashCode();
        h *= 1000003;
        h ^= (ref == null) ? 0 : ref.hashCode();
        h *= 1000003;
        h ^= quantity;
        h *= 1000003;
        h ^= (paidPrice == null) ? 0 : paidPrice.hashCode();
        h *= 1000003;
        h ^= (currency == null) ? 0 : currency.hashCode();
        h *= 1000003;
        h ^= (totalPrice == null) ? 0 : totalPrice.hashCode();
        h *= 1000003;
        h ^= (totalTaxPrice == null) ? 0 : totalTaxPrice.hashCode();
        h *= 1000003;
        h ^= (price == null) ? 0 : price.hashCode();
        h *= 1000003;
        h ^= (taxPrice == null) ? 0 : taxPrice.hashCode();
        h *= 1000003;
        h ^= (attributes == null) ? 0 : attributes.hashCode();
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
      builder.createdOn = createdOn;
      builder.updatedOn = updatedOn;
      builder.ref = ref;
      builder.quantity = quantity;
      builder.paidPrice = paidPrice;
      builder.currency = currency;
      builder.totalPrice = totalPrice;
      builder.totalTaxPrice = totalTaxPrice;
      builder.price = price;
      builder.taxPrice = taxPrice;
      builder.attributes = attributes;
      builder.product = product;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<OrderItem> {
      final Attribute1.Mapper attribute1FieldMapper = new Attribute1.Mapper();

      final Product.Mapper productFieldMapper = new Product.Mapper();

      @Override
      public OrderItem map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        final Object updatedOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        final String ref = reader.readString($responseFields[4]);
        final int quantity = reader.readInt($responseFields[5]);
        final Double paidPrice = reader.readDouble($responseFields[6]);
        final String currency = reader.readString($responseFields[7]);
        final Double totalPrice = reader.readDouble($responseFields[8]);
        final Double totalTaxPrice = reader.readDouble($responseFields[9]);
        final Double price = reader.readDouble($responseFields[10]);
        final Double taxPrice = reader.readDouble($responseFields[11]);
        final List<Attribute1> attributes = reader.readList($responseFields[12], new ResponseReader.ListReader<Attribute1>() {
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
        final Product product = reader.readObject($responseFields[13], new ResponseReader.ObjectReader<Product>() {
          @Override
          public Product read(ResponseReader reader) {
            return productFieldMapper.map(reader);
          }
        });
        return new OrderItem(__typename, id, createdOn, updatedOn, ref, quantity, paidPrice, currency, totalPrice, totalTaxPrice, price, taxPrice, attributes, product);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable Object createdOn;

      private @Nullable Object updatedOn;

      private @Nullable String ref;

      private int quantity;

      private @Nullable Double paidPrice;

      private @Nullable String currency;

      private @Nullable Double totalPrice;

      private @Nullable Double totalTaxPrice;

      private @Nullable Double price;

      private @Nullable Double taxPrice;

      private @Nullable List<Attribute1> attributes;

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

      public Builder createdOn(@Nullable Object createdOn) {
        this.createdOn = createdOn;
        return this;
      }

      public Builder updatedOn(@Nullable Object updatedOn) {
        this.updatedOn = updatedOn;
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

      public Builder totalPrice(@Nullable Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
      }

      public Builder totalTaxPrice(@Nullable Double totalTaxPrice) {
        this.totalTaxPrice = totalTaxPrice;
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

      public Builder attributes(@Nullable List<Attribute1> attributes) {
        this.attributes = attributes;
        return this;
      }

      public Builder product(@Nonnull Product product) {
        this.product = product;
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

      public Builder product(@Nonnull Mutator<Product.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Product.Builder builder = this.product != null ? this.product.toBuilder() : Product.builder();
        mutator.accept(builder);
        this.product = builder.build();
        return this;
      }

      public OrderItem build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(product, "product == null");
        return new OrderItem(__typename, id, createdOn, updatedOn, ref, quantity, paidPrice, currency, totalPrice, totalTaxPrice, price, taxPrice, attributes, product);
      }
    }
  }

  public static class Attribute1 {
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

    public Attribute1(@Nonnull String __typename, @Nonnull String name, @Nonnull String type,
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
        $toString = "Attribute1{"
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
      if (o instanceof Attribute1) {
        Attribute1 that = (Attribute1) o;
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

    public static final class Mapper implements ResponseFieldMapper<Attribute1> {
      @Override
      public Attribute1 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String name = reader.readString($responseFields[1]);
        final String type = reader.readString($responseFields[2]);
        final Object value = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        return new Attribute1(__typename, name, type, value);
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

      public Attribute1 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(value, "value == null");
        return new Attribute1(__typename, name, type, value);
      }
    }
  }

  public static class Product {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInlineFragment("__typename", "__typename", Arrays.asList("VariantProduct"))
    };

    final @Nonnull String __typename;

    final @Nonnull String name;

    final @Nullable AsVariantProduct asVariantProduct;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Product(@Nonnull String __typename, @Nonnull String name,
        @Nullable AsVariantProduct asVariantProduct) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.name = Utils.checkNotNull(name, "name == null");
      this.asVariantProduct = asVariantProduct;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The name of the product
     */
    public @Nonnull String name() {
      return this.name;
    }

    public @Nullable AsVariantProduct asVariantProduct() {
      return this.asVariantProduct;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], name);
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
          + "name=" + name + ", "
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
         && this.name.equals(that.name)
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
        h ^= name.hashCode();
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
      builder.name = name;
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
        final String name = reader.readString($responseFields[1]);
        final AsVariantProduct asVariantProduct = reader.readConditional($responseFields[2], new ResponseReader.ConditionalTypeReader<AsVariantProduct>() {
          @Override
          public AsVariantProduct read(String conditionalType, ResponseReader reader) {
            return asVariantProductFieldMapper.map(reader);
          }
        });
        return new Product(__typename, name, asVariantProduct);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String name;

      private @Nullable AsVariantProduct asVariantProduct;

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
        Utils.checkNotNull(name, "name == null");
        return new Product(__typename, name, asVariantProduct);
      }
    }
  }

  public static class AsVariantProduct {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String name;

    final @Nonnull String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public AsVariantProduct(@Nonnull String __typename, @Nonnull String name, @Nonnull String ref) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.name = Utils.checkNotNull(name, "name == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The name of the Product
     */
    public @Nonnull String name() {
      return this.name;
    }

    /**
     *  The unique reference identifier for the Product
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], name);
          writer.writeString($responseFields[2], ref);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "AsVariantProduct{"
          + "__typename=" + __typename + ", "
          + "name=" + name + ", "
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
      if (o instanceof AsVariantProduct) {
        AsVariantProduct that = (AsVariantProduct) o;
        return this.__typename.equals(that.__typename)
         && this.name.equals(that.name)
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
        h ^= name.hashCode();
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
      builder.name = name;
      builder.ref = ref;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<AsVariantProduct> {
      @Override
      public AsVariantProduct map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String name = reader.readString($responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        return new AsVariantProduct(__typename, name, ref);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String name;

      private @Nonnull String ref;

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

      public Builder ref(@Nonnull String ref) {
        this.ref = ref;
        return this;
      }

      public AsVariantProduct build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(ref, "ref == null");
        return new AsVariantProduct(__typename, name, ref);
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
}
