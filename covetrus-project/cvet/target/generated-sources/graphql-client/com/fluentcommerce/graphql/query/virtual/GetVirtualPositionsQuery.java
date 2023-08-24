package com.fluentcommerce.graphql.query.virtual;

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
public final class GetVirtualPositionsQuery implements Query<GetVirtualPositionsQuery.Data, GetVirtualPositionsQuery.Data, GetVirtualPositionsQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query GetVirtualPositions($catalogueRef: String!, $groupRef: [String], $productRef: [String!], $virtualPositionCount: Int, $virtualPositionCursor: String) {\n"
      + "  virtualPositions(catalogue: {ref: $catalogueRef}, groupRef: $groupRef, productRef: $productRef, first: $virtualPositionCount, after: $virtualPositionCursor) {\n"
      + "    __typename\n"
      + "    virtualPositionEdge: edges {\n"
      + "      __typename\n"
      + "      virtualPosition: node {\n"
      + "        __typename\n"
      + "        id\n"
      + "        ref\n"
      + "        type\n"
      + "        status\n"
      + "        productRef\n"
      + "        groupRef\n"
      + "        quantity\n"
      + "      }\n"
      + "      cursor\n"
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
      return "GetVirtualPositions";
    }
  };

  private final GetVirtualPositionsQuery.Variables variables;

  public GetVirtualPositionsQuery(@Nonnull String catalogueRef, @Nullable List<String> groupRef,
      @Nullable List<String> productRef, @Nullable Integer virtualPositionCount,
      @Nullable String virtualPositionCursor) {
    Utils.checkNotNull(catalogueRef, "catalogueRef == null");
    variables = new GetVirtualPositionsQuery.Variables(catalogueRef, groupRef, productRef, virtualPositionCount, virtualPositionCursor);
  }

  @Override
  public String operationId() {
    return "cdae0f9fc24d26cc3f18400820e04cc9ea2d12733c6e8cea121d362da34449e2";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public GetVirtualPositionsQuery.Data wrapData(GetVirtualPositionsQuery.Data data) {
    return data;
  }

  @Override
  public GetVirtualPositionsQuery.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<GetVirtualPositionsQuery.Data> responseFieldMapper() {
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
    private @Nonnull String catalogueRef;

    private @Nullable List<String> groupRef;

    private @Nullable List<String> productRef;

    private @Nullable Integer virtualPositionCount;

    private @Nullable String virtualPositionCursor;

    Builder() {
    }

    public Builder catalogueRef(@Nonnull String catalogueRef) {
      this.catalogueRef = catalogueRef;
      return this;
    }

    public Builder groupRef(@Nullable List<String> groupRef) {
      this.groupRef = groupRef;
      return this;
    }

    public Builder productRef(@Nullable List<String> productRef) {
      this.productRef = productRef;
      return this;
    }

    public Builder virtualPositionCount(@Nullable Integer virtualPositionCount) {
      this.virtualPositionCount = virtualPositionCount;
      return this;
    }

    public Builder virtualPositionCursor(@Nullable String virtualPositionCursor) {
      this.virtualPositionCursor = virtualPositionCursor;
      return this;
    }

    public GetVirtualPositionsQuery build() {
      Utils.checkNotNull(catalogueRef, "catalogueRef == null");
      return new GetVirtualPositionsQuery(catalogueRef, groupRef, productRef, virtualPositionCount, virtualPositionCursor);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nonnull String catalogueRef;

    private final @Nullable List<String> groupRef;

    private final @Nullable List<String> productRef;

    private final @Nullable Integer virtualPositionCount;

    private final @Nullable String virtualPositionCursor;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nonnull String catalogueRef, @Nullable List<String> groupRef,
        @Nullable List<String> productRef, @Nullable Integer virtualPositionCount,
        @Nullable String virtualPositionCursor) {
      this.catalogueRef = catalogueRef;
      this.groupRef = groupRef;
      this.productRef = productRef;
      this.virtualPositionCount = virtualPositionCount;
      this.virtualPositionCursor = virtualPositionCursor;
      this.valueMap.put("catalogueRef", catalogueRef);
      this.valueMap.put("groupRef", groupRef);
      this.valueMap.put("productRef", productRef);
      this.valueMap.put("virtualPositionCount", virtualPositionCount);
      this.valueMap.put("virtualPositionCursor", virtualPositionCursor);
    }

    public @Nonnull String catalogueRef() {
      return catalogueRef;
    }

    public @Nullable List<String> groupRef() {
      return groupRef;
    }

    public @Nullable List<String> productRef() {
      return productRef;
    }

    public @Nullable Integer virtualPositionCount() {
      return virtualPositionCount;
    }

    public @Nullable String virtualPositionCursor() {
      return virtualPositionCursor;
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
          writer.writeString("catalogueRef", catalogueRef);
          writer.writeList("groupRef", groupRef != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : groupRef) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
          writer.writeList("productRef", productRef != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : productRef) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
          writer.writeInt("virtualPositionCount", virtualPositionCount);
          writer.writeString("virtualPositionCursor", virtualPositionCursor);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("virtualPositions", "virtualPositions", new UnmodifiableMapBuilder<String, Object>(5)
        .put("productRef", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "productRef")
        .build())
        .put("groupRef", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "groupRef")
        .build())
        .put("after", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "virtualPositionCursor")
        .build())
        .put("catalogue", new UnmodifiableMapBuilder<String, Object>(1)
          .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "catalogueRef")
          .build())
        .build())
        .put("first", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "virtualPositionCount")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable VirtualPositions virtualPositions;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable VirtualPositions virtualPositions) {
      this.virtualPositions = virtualPositions;
    }

    /**
     *  Search for VirtualPosition entities
     */
    public @Nullable VirtualPositions virtualPositions() {
      return this.virtualPositions;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], virtualPositions != null ? virtualPositions.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "virtualPositions=" + virtualPositions
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
        return ((this.virtualPositions == null) ? (that.virtualPositions == null) : this.virtualPositions.equals(that.virtualPositions));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (virtualPositions == null) ? 0 : virtualPositions.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.virtualPositions = virtualPositions;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final VirtualPositions.Mapper virtualPositionsFieldMapper = new VirtualPositions.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final VirtualPositions virtualPositions = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<VirtualPositions>() {
          @Override
          public VirtualPositions read(ResponseReader reader) {
            return virtualPositionsFieldMapper.map(reader);
          }
        });
        return new Data(virtualPositions);
      }
    }

    public static final class Builder {
      private @Nullable VirtualPositions virtualPositions;

      Builder() {
      }

      public Builder virtualPositions(@Nullable VirtualPositions virtualPositions) {
        this.virtualPositions = virtualPositions;
        return this;
      }

      public Builder virtualPositions(@Nonnull Mutator<VirtualPositions.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        VirtualPositions.Builder builder = this.virtualPositions != null ? this.virtualPositions.toBuilder() : VirtualPositions.builder();
        mutator.accept(builder);
        this.virtualPositions = builder.build();
        return this;
      }

      public Data build() {
        return new Data(virtualPositions);
      }
    }
  }

  public static class VirtualPositions {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("virtualPositionEdge", "edges", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("pageInfo", "pageInfo", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<VirtualPositionEdge> virtualPositionEdge;

    final @Nullable PageInfo pageInfo;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public VirtualPositions(@Nonnull String __typename,
        @Nullable List<VirtualPositionEdge> virtualPositionEdge, @Nullable PageInfo pageInfo) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.virtualPositionEdge = virtualPositionEdge;
      this.pageInfo = pageInfo;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to VirtualPosition type node
     */
    public @Nullable List<VirtualPositionEdge> virtualPositionEdge() {
      return this.virtualPositionEdge;
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
          writer.writeList($responseFields[1], virtualPositionEdge, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((VirtualPositionEdge) value).marshaller());
            }
          });
          writer.writeObject($responseFields[2], pageInfo != null ? pageInfo.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "VirtualPositions{"
          + "__typename=" + __typename + ", "
          + "virtualPositionEdge=" + virtualPositionEdge + ", "
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
      if (o instanceof VirtualPositions) {
        VirtualPositions that = (VirtualPositions) o;
        return this.__typename.equals(that.__typename)
         && ((this.virtualPositionEdge == null) ? (that.virtualPositionEdge == null) : this.virtualPositionEdge.equals(that.virtualPositionEdge))
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
        h ^= (virtualPositionEdge == null) ? 0 : virtualPositionEdge.hashCode();
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
      builder.virtualPositionEdge = virtualPositionEdge;
      builder.pageInfo = pageInfo;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<VirtualPositions> {
      final VirtualPositionEdge.Mapper virtualPositionEdgeFieldMapper = new VirtualPositionEdge.Mapper();

      final PageInfo.Mapper pageInfoFieldMapper = new PageInfo.Mapper();

      @Override
      public VirtualPositions map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final List<VirtualPositionEdge> virtualPositionEdge = reader.readList($responseFields[1], new ResponseReader.ListReader<VirtualPositionEdge>() {
          @Override
          public VirtualPositionEdge read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<VirtualPositionEdge>() {
              @Override
              public VirtualPositionEdge read(ResponseReader reader) {
                return virtualPositionEdgeFieldMapper.map(reader);
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
        return new VirtualPositions(__typename, virtualPositionEdge, pageInfo);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable List<VirtualPositionEdge> virtualPositionEdge;

      private @Nullable PageInfo pageInfo;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder virtualPositionEdge(@Nullable List<VirtualPositionEdge> virtualPositionEdge) {
        this.virtualPositionEdge = virtualPositionEdge;
        return this;
      }

      public Builder pageInfo(@Nullable PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
      }

      public Builder virtualPositionEdge(@Nonnull Mutator<List<VirtualPositionEdge.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<VirtualPositionEdge.Builder> builders = new ArrayList<>();
        if (this.virtualPositionEdge != null) {
          for (VirtualPositionEdge item : this.virtualPositionEdge) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<VirtualPositionEdge> virtualPositionEdge = new ArrayList<>();
        for (VirtualPositionEdge.Builder item : builders) {
          virtualPositionEdge.add(item != null ? item.build() : null);
        }
        this.virtualPositionEdge = virtualPositionEdge;
        return this;
      }

      public Builder pageInfo(@Nonnull Mutator<PageInfo.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        PageInfo.Builder builder = this.pageInfo != null ? this.pageInfo.toBuilder() : PageInfo.builder();
        mutator.accept(builder);
        this.pageInfo = builder.build();
        return this;
      }

      public VirtualPositions build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new VirtualPositions(__typename, virtualPositionEdge, pageInfo);
      }
    }
  }

  public static class VirtualPositionEdge {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("virtualPosition", "node", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("cursor", "cursor", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable VirtualPosition virtualPosition;

    final @Nullable String cursor;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public VirtualPositionEdge(@Nonnull String __typename,
        @Nullable VirtualPosition virtualPosition, @Nullable String cursor) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.virtualPosition = virtualPosition;
      this.cursor = cursor;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The item at the end of the VirtualPosition edge
     */
    public @Nullable VirtualPosition virtualPosition() {
      return this.virtualPosition;
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
          writer.writeObject($responseFields[1], virtualPosition != null ? virtualPosition.marshaller() : null);
          writer.writeString($responseFields[2], cursor);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "VirtualPositionEdge{"
          + "__typename=" + __typename + ", "
          + "virtualPosition=" + virtualPosition + ", "
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
      if (o instanceof VirtualPositionEdge) {
        VirtualPositionEdge that = (VirtualPositionEdge) o;
        return this.__typename.equals(that.__typename)
         && ((this.virtualPosition == null) ? (that.virtualPosition == null) : this.virtualPosition.equals(that.virtualPosition))
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
        h ^= (virtualPosition == null) ? 0 : virtualPosition.hashCode();
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
      builder.virtualPosition = virtualPosition;
      builder.cursor = cursor;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<VirtualPositionEdge> {
      final VirtualPosition.Mapper virtualPositionFieldMapper = new VirtualPosition.Mapper();

      @Override
      public VirtualPositionEdge map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final VirtualPosition virtualPosition = reader.readObject($responseFields[1], new ResponseReader.ObjectReader<VirtualPosition>() {
          @Override
          public VirtualPosition read(ResponseReader reader) {
            return virtualPositionFieldMapper.map(reader);
          }
        });
        final String cursor = reader.readString($responseFields[2]);
        return new VirtualPositionEdge(__typename, virtualPosition, cursor);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable VirtualPosition virtualPosition;

      private @Nullable String cursor;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder virtualPosition(@Nullable VirtualPosition virtualPosition) {
        this.virtualPosition = virtualPosition;
        return this;
      }

      public Builder cursor(@Nullable String cursor) {
        this.cursor = cursor;
        return this;
      }

      public Builder virtualPosition(@Nonnull Mutator<VirtualPosition.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        VirtualPosition.Builder builder = this.virtualPosition != null ? this.virtualPosition.toBuilder() : VirtualPosition.builder();
        mutator.accept(builder);
        this.virtualPosition = builder.build();
        return this;
      }

      public VirtualPositionEdge build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new VirtualPositionEdge(__typename, virtualPosition, cursor);
      }
    }
  }

  public static class VirtualPosition {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("productRef", "productRef", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("groupRef", "groupRef", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("quantity", "quantity", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nonnull String ref;

    final @Nonnull String type;

    final @Nullable String status;

    final @Nullable String productRef;

    final @Nullable String groupRef;

    final @Nullable Integer quantity;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public VirtualPosition(@Nonnull String __typename, @Nonnull String id, @Nonnull String ref,
        @Nonnull String type, @Nullable String status, @Nullable String productRef,
        @Nullable String groupRef, @Nullable Integer quantity) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.type = Utils.checkNotNull(type, "type == null");
      this.status = status;
      this.productRef = productRef;
      this.groupRef = groupRef;
      this.quantity = quantity;
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
     *  The reference identifier used to identify an entity
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    /**
     *  Type of the `VirtualPosition`, typically used by the Orchestration Engine to determine the workflow that should be applied. Unless stated otherwise, no values are enforced by the platform.<br/>
     */
    public @Nonnull String type() {
      return this.type;
    }

    /**
     *  The current status of the `VirtualPosition`.<br/>By default, the initial value will be CREATED, however no other status values are enforced by the platform.<br/>The status field is also used within ruleset selection during orchestration. For more info, see <a href="https://lingo.fluentcommerce.com/ORCHESTRATION-PLATFORM/" target="_blank">Orchestration</a><br/>
     */
    public @Nullable String status() {
      return this.status;
    }

    /**
     *  Product reference
     */
    public @Nullable String productRef() {
      return this.productRef;
    }

    /**
     *  Group - reference to Location or Category
     */
    public @Nullable String groupRef() {
      return this.groupRef;
    }

    /**
     *  On hand quantity
     */
    public @Nullable Integer quantity() {
      return this.quantity;
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
          writer.writeString($responseFields[5], productRef);
          writer.writeString($responseFields[6], groupRef);
          writer.writeInt($responseFields[7], quantity);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "VirtualPosition{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "type=" + type + ", "
          + "status=" + status + ", "
          + "productRef=" + productRef + ", "
          + "groupRef=" + groupRef + ", "
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
      if (o instanceof VirtualPosition) {
        VirtualPosition that = (VirtualPosition) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && this.ref.equals(that.ref)
         && this.type.equals(that.type)
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.productRef == null) ? (that.productRef == null) : this.productRef.equals(that.productRef))
         && ((this.groupRef == null) ? (that.groupRef == null) : this.groupRef.equals(that.groupRef))
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
        h ^= id.hashCode();
        h *= 1000003;
        h ^= ref.hashCode();
        h *= 1000003;
        h ^= type.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        h *= 1000003;
        h ^= (productRef == null) ? 0 : productRef.hashCode();
        h *= 1000003;
        h ^= (groupRef == null) ? 0 : groupRef.hashCode();
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
      builder.id = id;
      builder.ref = ref;
      builder.type = type;
      builder.status = status;
      builder.productRef = productRef;
      builder.groupRef = groupRef;
      builder.quantity = quantity;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<VirtualPosition> {
      @Override
      public VirtualPosition map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        final String productRef = reader.readString($responseFields[5]);
        final String groupRef = reader.readString($responseFields[6]);
        final Integer quantity = reader.readInt($responseFields[7]);
        return new VirtualPosition(__typename, id, ref, type, status, productRef, groupRef, quantity);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nonnull String ref;

      private @Nonnull String type;

      private @Nullable String status;

      private @Nullable String productRef;

      private @Nullable String groupRef;

      private @Nullable Integer quantity;

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

      public Builder productRef(@Nullable String productRef) {
        this.productRef = productRef;
        return this;
      }

      public Builder groupRef(@Nullable String groupRef) {
        this.groupRef = groupRef;
        return this;
      }

      public Builder quantity(@Nullable Integer quantity) {
        this.quantity = quantity;
        return this;
      }

      public VirtualPosition build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(ref, "ref == null");
        Utils.checkNotNull(type, "type == null");
        return new VirtualPosition(__typename, id, ref, type, status, productRef, groupRef, quantity);
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
