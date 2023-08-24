package com.fluentcommerce.graphql.query.product;

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
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class GetVariantProductsQuery implements Query<GetVariantProductsQuery.Data, GetVariantProductsQuery.Data, GetVariantProductsQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query GetVariantProducts($refs: [String!], $productCatalogueRef: String!, $productNames: [String!], $variantProductCursor: String, $productCount: Int, $productStatus: [String], $productType: [String!]) {\n"
      + "  variantProducts(catalogue: {ref: $productCatalogueRef}, name: $productNames, ref: $refs, after: $variantProductCursor, first: $productCount, status: $productStatus, type: $productType) {\n"
      + "    __typename\n"
      + "    edges {\n"
      + "      __typename\n"
      + "      cursor\n"
      + "      node {\n"
      + "        __typename\n"
      + "        ref\n"
      + "        attributes {\n"
      + "          __typename\n"
      + "          name\n"
      + "          type\n"
      + "          value\n"
      + "        }\n"
      + "        id\n"
      + "        name\n"
      + "        status\n"
      + "        type\n"
      + "        product {\n"
      + "          __typename\n"
      + "          ref\n"
      + "          name\n"
      + "          status\n"
      + "        }\n"
      + "        catalogue {\n"
      + "          __typename\n"
      + "          ref\n"
      + "        }\n"
      + "      }\n"
      + "    }\n"
      + "    pageInfo {\n"
      + "      __typename\n"
      + "      hasNextPage\n"
      + "    }\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "GetVariantProducts";
    }
  };

  private final GetVariantProductsQuery.Variables variables;

  public GetVariantProductsQuery(@Nullable List<String> refs, @Nonnull String productCatalogueRef,
      @Nullable List<String> productNames, @Nullable String variantProductCursor,
      @Nullable Integer productCount, @Nullable List<String> productStatus,
      @Nullable List<String> productType) {
    Utils.checkNotNull(productCatalogueRef, "productCatalogueRef == null");
    variables = new GetVariantProductsQuery.Variables(refs, productCatalogueRef, productNames, variantProductCursor, productCount, productStatus, productType);
  }

  @Override
  public String operationId() {
    return "6b6cd76e3c7571ffb98c6890b65205e43102beb93a0cf5135e72d4e5b8a9e68d";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public GetVariantProductsQuery.Data wrapData(GetVariantProductsQuery.Data data) {
    return data;
  }

  @Override
  public GetVariantProductsQuery.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<GetVariantProductsQuery.Data> responseFieldMapper() {
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
    private @Nullable List<String> refs;

    private @Nonnull String productCatalogueRef;

    private @Nullable List<String> productNames;

    private @Nullable String variantProductCursor;

    private @Nullable Integer productCount;

    private @Nullable List<String> productStatus;

    private @Nullable List<String> productType;

    Builder() {
    }

    public Builder refs(@Nullable List<String> refs) {
      this.refs = refs;
      return this;
    }

    public Builder productCatalogueRef(@Nonnull String productCatalogueRef) {
      this.productCatalogueRef = productCatalogueRef;
      return this;
    }

    public Builder productNames(@Nullable List<String> productNames) {
      this.productNames = productNames;
      return this;
    }

    public Builder variantProductCursor(@Nullable String variantProductCursor) {
      this.variantProductCursor = variantProductCursor;
      return this;
    }

    public Builder productCount(@Nullable Integer productCount) {
      this.productCount = productCount;
      return this;
    }

    public Builder productStatus(@Nullable List<String> productStatus) {
      this.productStatus = productStatus;
      return this;
    }

    public Builder productType(@Nullable List<String> productType) {
      this.productType = productType;
      return this;
    }

    public GetVariantProductsQuery build() {
      Utils.checkNotNull(productCatalogueRef, "productCatalogueRef == null");
      return new GetVariantProductsQuery(refs, productCatalogueRef, productNames, variantProductCursor, productCount, productStatus, productType);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable List<String> refs;

    private final @Nonnull String productCatalogueRef;

    private final @Nullable List<String> productNames;

    private final @Nullable String variantProductCursor;

    private final @Nullable Integer productCount;

    private final @Nullable List<String> productStatus;

    private final @Nullable List<String> productType;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable List<String> refs, @Nonnull String productCatalogueRef,
        @Nullable List<String> productNames, @Nullable String variantProductCursor,
        @Nullable Integer productCount, @Nullable List<String> productStatus,
        @Nullable List<String> productType) {
      this.refs = refs;
      this.productCatalogueRef = productCatalogueRef;
      this.productNames = productNames;
      this.variantProductCursor = variantProductCursor;
      this.productCount = productCount;
      this.productStatus = productStatus;
      this.productType = productType;
      this.valueMap.put("refs", refs);
      this.valueMap.put("productCatalogueRef", productCatalogueRef);
      this.valueMap.put("productNames", productNames);
      this.valueMap.put("variantProductCursor", variantProductCursor);
      this.valueMap.put("productCount", productCount);
      this.valueMap.put("productStatus", productStatus);
      this.valueMap.put("productType", productType);
    }

    public @Nullable List<String> refs() {
      return refs;
    }

    public @Nonnull String productCatalogueRef() {
      return productCatalogueRef;
    }

    public @Nullable List<String> productNames() {
      return productNames;
    }

    public @Nullable String variantProductCursor() {
      return variantProductCursor;
    }

    public @Nullable Integer productCount() {
      return productCount;
    }

    public @Nullable List<String> productStatus() {
      return productStatus;
    }

    public @Nullable List<String> productType() {
      return productType;
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
          writer.writeList("refs", refs != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : refs) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
          writer.writeString("productCatalogueRef", productCatalogueRef);
          writer.writeList("productNames", productNames != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : productNames) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
          writer.writeString("variantProductCursor", variantProductCursor);
          writer.writeInt("productCount", productCount);
          writer.writeList("productStatus", productStatus != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : productStatus) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
          writer.writeList("productType", productType != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : productType) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("variantProducts", "variantProducts", new UnmodifiableMapBuilder<String, Object>(7)
        .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "refs")
        .build())
        .put("name", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "productNames")
        .build())
        .put("after", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "variantProductCursor")
        .build())
        .put("type", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "productType")
        .build())
        .put("catalogue", new UnmodifiableMapBuilder<String, Object>(1)
          .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "productCatalogueRef")
          .build())
        .build())
        .put("first", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "productCount")
        .build())
        .put("status", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "productStatus")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable VariantProducts variantProducts;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable VariantProducts variantProducts) {
      this.variantProducts = variantProducts;
    }

    /**
     *  Search for VariantProduct entities
     */
    public @Nullable VariantProducts variantProducts() {
      return this.variantProducts;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], variantProducts != null ? variantProducts.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "variantProducts=" + variantProducts
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
        return ((this.variantProducts == null) ? (that.variantProducts == null) : this.variantProducts.equals(that.variantProducts));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (variantProducts == null) ? 0 : variantProducts.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.variantProducts = variantProducts;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final VariantProducts.Mapper variantProductsFieldMapper = new VariantProducts.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final VariantProducts variantProducts = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<VariantProducts>() {
          @Override
          public VariantProducts read(ResponseReader reader) {
            return variantProductsFieldMapper.map(reader);
          }
        });
        return new Data(variantProducts);
      }
    }

    public static final class Builder {
      private @Nullable VariantProducts variantProducts;

      Builder() {
      }

      public Builder variantProducts(@Nullable VariantProducts variantProducts) {
        this.variantProducts = variantProducts;
        return this;
      }

      public Builder variantProducts(@Nonnull Mutator<VariantProducts.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        VariantProducts.Builder builder = this.variantProducts != null ? this.variantProducts.toBuilder() : VariantProducts.builder();
        mutator.accept(builder);
        this.variantProducts = builder.build();
        return this;
      }

      public Data build() {
        return new Data(variantProducts);
      }
    }
  }

  public static class VariantProducts {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("edges", "edges", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("pageInfo", "pageInfo", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<Edge> edges;

    final @Nullable PageInfo pageInfo;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public VariantProducts(@Nonnull String __typename, @Nullable List<Edge> edges,
        @Nullable PageInfo pageInfo) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.edges = edges;
      this.pageInfo = pageInfo;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to VariantProduct type node
     */
    public @Nullable List<Edge> edges() {
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
              listItemWriter.writeObject(((Edge) value).marshaller());
            }
          });
          writer.writeObject($responseFields[2], pageInfo != null ? pageInfo.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "VariantProducts{"
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
      if (o instanceof VariantProducts) {
        VariantProducts that = (VariantProducts) o;
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

    public static final class Mapper implements ResponseFieldMapper<VariantProducts> {
      final Edge.Mapper edgeFieldMapper = new Edge.Mapper();

      final PageInfo.Mapper pageInfoFieldMapper = new PageInfo.Mapper();

      @Override
      public VariantProducts map(ResponseReader reader) {
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
        final PageInfo pageInfo = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<PageInfo>() {
          @Override
          public PageInfo read(ResponseReader reader) {
            return pageInfoFieldMapper.map(reader);
          }
        });
        return new VariantProducts(__typename, edges, pageInfo);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable List<Edge> edges;

      private @Nullable PageInfo pageInfo;

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

      public Builder pageInfo(@Nullable PageInfo pageInfo) {
        this.pageInfo = pageInfo;
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

      public Builder pageInfo(@Nonnull Mutator<PageInfo.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        PageInfo.Builder builder = this.pageInfo != null ? this.pageInfo.toBuilder() : PageInfo.builder();
        mutator.accept(builder);
        this.pageInfo = builder.build();
        return this;
      }

      public VariantProducts build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new VariantProducts(__typename, edges, pageInfo);
      }
    }
  }

  public static class Edge {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("cursor", "cursor", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("node", "node", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String cursor;

    final @Nullable Node node;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Edge(@Nonnull String __typename, @Nullable String cursor, @Nullable Node node) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.cursor = cursor;
      this.node = node;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A cursor for use in pagination
     */
    public @Nullable String cursor() {
      return this.cursor;
    }

    /**
     *  The item at the end of the VariantProduct edge
     */
    public @Nullable Node node() {
      return this.node;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], cursor);
          writer.writeObject($responseFields[2], node != null ? node.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Edge{"
          + "__typename=" + __typename + ", "
          + "cursor=" + cursor + ", "
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
         && ((this.cursor == null) ? (that.cursor == null) : this.cursor.equals(that.cursor))
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
        h ^= (cursor == null) ? 0 : cursor.hashCode();
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
      builder.cursor = cursor;
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
        final String cursor = reader.readString($responseFields[1]);
        final Node node = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<Node>() {
          @Override
          public Node read(ResponseReader reader) {
            return nodeFieldMapper.map(reader);
          }
        });
        return new Edge(__typename, cursor, node);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String cursor;

      private @Nullable Node node;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder cursor(@Nullable String cursor) {
        this.cursor = cursor;
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
        return new Edge(__typename, cursor, node);
      }
    }
  }

  public static class Node {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("product", "product", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("catalogue", "catalogue", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String ref;

    final @Nullable List<Attribute> attributes;

    final @Nonnull String id;

    final @Nonnull String name;

    final @Nullable String status;

    final @Nonnull String type;

    final @Nonnull Product product;

    final @Nonnull Catalogue catalogue;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Node(@Nonnull String __typename, @Nonnull String ref,
        @Nullable List<Attribute> attributes, @Nonnull String id, @Nonnull String name,
        @Nullable String status, @Nonnull String type, @Nonnull Product product,
        @Nonnull Catalogue catalogue) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.attributes = attributes;
      this.id = Utils.checkNotNull(id, "id == null");
      this.name = Utils.checkNotNull(name, "name == null");
      this.status = status;
      this.type = Utils.checkNotNull(type, "type == null");
      this.product = Utils.checkNotNull(product, "product == null");
      this.catalogue = Utils.checkNotNull(catalogue, "catalogue == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The unique reference identifier for the Product
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    /**
     *  A list of attributes associated with this Product. This can be used to extend the existing data structure with additional data for use in orchestration rules, etc.
     */
    public @Nullable List<Attribute> attributes() {
      return this.attributes;
    }

    /**
     *  ID of the object. For internal use, should not be used externally or by any business logic
     */
    public @Nonnull String id() {
      return this.id;
    }

    /**
     *  The name of the Product
     */
    public @Nonnull String name() {
      return this.name;
    }

    /**
     *  The current status of the `VariantProduct`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
     */
    public @Nullable String status() {
      return this.status;
    }

    /**
     *  Type of the `VariantProduct`, typically used by the Orchestration Engine to determine the workflow that should be applied. Unless stated otherwise, no values are enforced by the platform.<br/>
     */
    public @Nonnull String type() {
      return this.type;
    }

    /**
     *  The associated Standard Product for this Variant Product
     */
    public @Nonnull Product product() {
      return this.product;
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
          writer.writeString($responseFields[1], ref);
          writer.writeList($responseFields[2], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute) value).marshaller());
            }
          });
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], id);
          writer.writeString($responseFields[4], name);
          writer.writeString($responseFields[5], status);
          writer.writeString($responseFields[6], type);
          writer.writeObject($responseFields[7], product.marshaller());
          writer.writeObject($responseFields[8], catalogue.marshaller());
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Node{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "attributes=" + attributes + ", "
          + "id=" + id + ", "
          + "name=" + name + ", "
          + "status=" + status + ", "
          + "type=" + type + ", "
          + "product=" + product + ", "
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
      if (o instanceof Node) {
        Node that = (Node) o;
        return this.__typename.equals(that.__typename)
         && this.ref.equals(that.ref)
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes))
         && this.id.equals(that.id)
         && this.name.equals(that.name)
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && this.type.equals(that.type)
         && this.product.equals(that.product)
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
        h ^= ref.hashCode();
        h *= 1000003;
        h ^= (attributes == null) ? 0 : attributes.hashCode();
        h *= 1000003;
        h ^= id.hashCode();
        h *= 1000003;
        h ^= name.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        h *= 1000003;
        h ^= type.hashCode();
        h *= 1000003;
        h ^= product.hashCode();
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
      builder.ref = ref;
      builder.attributes = attributes;
      builder.id = id;
      builder.name = name;
      builder.status = status;
      builder.type = type;
      builder.product = product;
      builder.catalogue = catalogue;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Node> {
      final Attribute.Mapper attributeFieldMapper = new Attribute.Mapper();

      final Product.Mapper productFieldMapper = new Product.Mapper();

      final Catalogue.Mapper catalogueFieldMapper = new Catalogue.Mapper();

      @Override
      public Node map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final List<Attribute> attributes = reader.readList($responseFields[2], new ResponseReader.ListReader<Attribute>() {
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
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        final String name = reader.readString($responseFields[4]);
        final String status = reader.readString($responseFields[5]);
        final String type = reader.readString($responseFields[6]);
        final Product product = reader.readObject($responseFields[7], new ResponseReader.ObjectReader<Product>() {
          @Override
          public Product read(ResponseReader reader) {
            return productFieldMapper.map(reader);
          }
        });
        final Catalogue catalogue = reader.readObject($responseFields[8], new ResponseReader.ObjectReader<Catalogue>() {
          @Override
          public Catalogue read(ResponseReader reader) {
            return catalogueFieldMapper.map(reader);
          }
        });
        return new Node(__typename, ref, attributes, id, name, status, type, product, catalogue);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String ref;

      private @Nullable List<Attribute> attributes;

      private @Nonnull String id;

      private @Nonnull String name;

      private @Nullable String status;

      private @Nonnull String type;

      private @Nonnull Product product;

      private @Nonnull Catalogue catalogue;

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

      public Builder attributes(@Nullable List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
      }

      public Builder id(@Nonnull String id) {
        this.id = id;
        return this;
      }

      public Builder name(@Nonnull String name) {
        this.name = name;
        return this;
      }

      public Builder status(@Nullable String status) {
        this.status = status;
        return this;
      }

      public Builder type(@Nonnull String type) {
        this.type = type;
        return this;
      }

      public Builder product(@Nonnull Product product) {
        this.product = product;
        return this;
      }

      public Builder catalogue(@Nonnull Catalogue catalogue) {
        this.catalogue = catalogue;
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

      public Builder product(@Nonnull Mutator<Product.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Product.Builder builder = this.product != null ? this.product.toBuilder() : Product.builder();
        mutator.accept(builder);
        this.product = builder.build();
        return this;
      }

      public Builder catalogue(@Nonnull Mutator<Catalogue.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Catalogue.Builder builder = this.catalogue != null ? this.catalogue.toBuilder() : Catalogue.builder();
        mutator.accept(builder);
        this.catalogue = builder.build();
        return this;
      }

      public Node build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(ref, "ref == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(product, "product == null");
        Utils.checkNotNull(catalogue, "catalogue == null");
        return new Node(__typename, ref, attributes, id, name, status, type, product, catalogue);
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

  public static class Product {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String ref;

    final @Nonnull String name;

    final @Nullable String status;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Product(@Nonnull String __typename, @Nonnull String ref, @Nonnull String name,
        @Nullable String status) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.name = Utils.checkNotNull(name, "name == null");
      this.status = status;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The unique reference identifier for the Product
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    /**
     *  The name of the Product
     */
    public @Nonnull String name() {
      return this.name;
    }

    /**
     *  The current status of the `StandardProduct`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
     */
    public @Nullable String status() {
      return this.status;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeString($responseFields[2], name);
          writer.writeString($responseFields[3], status);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Product{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "name=" + name + ", "
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
      if (o instanceof Product) {
        Product that = (Product) o;
        return this.__typename.equals(that.__typename)
         && this.ref.equals(that.ref)
         && this.name.equals(that.name)
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
        h ^= ref.hashCode();
        h *= 1000003;
        h ^= name.hashCode();
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
      builder.ref = ref;
      builder.name = name;
      builder.status = status;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Product> {
      @Override
      public Product map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final String name = reader.readString($responseFields[2]);
        final String status = reader.readString($responseFields[3]);
        return new Product(__typename, ref, name, status);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String ref;

      private @Nonnull String name;

      private @Nullable String status;

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

      public Builder name(@Nonnull String name) {
        this.name = name;
        return this;
      }

      public Builder status(@Nullable String status) {
        this.status = status;
        return this;
      }

      public Product build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(ref, "ref == null");
        Utils.checkNotNull(name, "name == null");
        return new Product(__typename, ref, name, status);
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
}
