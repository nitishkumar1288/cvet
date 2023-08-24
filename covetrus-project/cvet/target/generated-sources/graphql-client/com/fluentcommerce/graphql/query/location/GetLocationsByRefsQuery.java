package com.fluentcommerce.graphql.query.location;

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
public final class GetLocationsByRefsQuery implements Query<GetLocationsByRefsQuery.Data, GetLocationsByRefsQuery.Data, GetLocationsByRefsQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query GetLocationsByRefs($locationRef: [String], $locationCount: Int, $locationCursor: String, $includeLocations: Boolean!) {\n"
      + "  locations(ref: $locationRef, first: $locationCount, after: $locationCursor) @include(if: $includeLocations) {\n"
      + "    __typename\n"
      + "    pageInfo {\n"
      + "      __typename\n"
      + "      hasNextPage\n"
      + "    }\n"
      + "    edges {\n"
      + "      __typename\n"
      + "      cursor\n"
      + "      node {\n"
      + "        __typename\n"
      + "        id\n"
      + "        ref\n"
      + "        type\n"
      + "        status\n"
      + "        name\n"
      + "        primaryAddress {\n"
      + "          __typename\n"
      + "          id\n"
      + "          ref\n"
      + "          type\n"
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
      + "          timeZone\n"
      + "        }\n"
      + "        attributes {\n"
      + "          __typename\n"
      + "          type\n"
      + "          name\n"
      + "          value\n"
      + "        }\n"
      + "        openingSchedule {\n"
      + "          __typename\n"
      + "          id\n"
      + "          createdOn\n"
      + "          updatedOn\n"
      + "          allHours\n"
      + "          monEnd\n"
      + "          monStart\n"
      + "          tueEnd\n"
      + "          tueStart\n"
      + "          wedEnd\n"
      + "          wedStart\n"
      + "          thuEnd\n"
      + "          thuStart\n"
      + "          friEnd\n"
      + "          friStart\n"
      + "          satEnd\n"
      + "          satStart\n"
      + "          sunEnd\n"
      + "          sunStart\n"
      + "        }\n"
      + "      }\n"
      + "    }\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "GetLocationsByRefs";
    }
  };

  private final GetLocationsByRefsQuery.Variables variables;

  public GetLocationsByRefsQuery(@Nullable List<String> locationRef,
      @Nullable Integer locationCount, @Nullable String locationCursor, boolean includeLocations) {
    variables = new GetLocationsByRefsQuery.Variables(locationRef, locationCount, locationCursor, includeLocations);
  }

  @Override
  public String operationId() {
    return "40c6b7f74cd5107cbd42ac0eb4ca0da03d044dc74287490ab7c408ac4c1710bf";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public GetLocationsByRefsQuery.Data wrapData(GetLocationsByRefsQuery.Data data) {
    return data;
  }

  @Override
  public GetLocationsByRefsQuery.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<GetLocationsByRefsQuery.Data> responseFieldMapper() {
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
    private @Nullable List<String> locationRef;

    private @Nullable Integer locationCount;

    private @Nullable String locationCursor;

    private boolean includeLocations;

    Builder() {
    }

    public Builder locationRef(@Nullable List<String> locationRef) {
      this.locationRef = locationRef;
      return this;
    }

    public Builder locationCount(@Nullable Integer locationCount) {
      this.locationCount = locationCount;
      return this;
    }

    public Builder locationCursor(@Nullable String locationCursor) {
      this.locationCursor = locationCursor;
      return this;
    }

    public Builder includeLocations(boolean includeLocations) {
      this.includeLocations = includeLocations;
      return this;
    }

    public GetLocationsByRefsQuery build() {
      return new GetLocationsByRefsQuery(locationRef, locationCount, locationCursor, includeLocations);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable List<String> locationRef;

    private final @Nullable Integer locationCount;

    private final @Nullable String locationCursor;

    private final boolean includeLocations;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable List<String> locationRef, @Nullable Integer locationCount,
        @Nullable String locationCursor, boolean includeLocations) {
      this.locationRef = locationRef;
      this.locationCount = locationCount;
      this.locationCursor = locationCursor;
      this.includeLocations = includeLocations;
      this.valueMap.put("locationRef", locationRef);
      this.valueMap.put("locationCount", locationCount);
      this.valueMap.put("locationCursor", locationCursor);
      this.valueMap.put("includeLocations", includeLocations);
    }

    public @Nullable List<String> locationRef() {
      return locationRef;
    }

    public @Nullable Integer locationCount() {
      return locationCount;
    }

    public @Nullable String locationCursor() {
      return locationCursor;
    }

    public boolean includeLocations() {
      return includeLocations;
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
          writer.writeList("locationRef", locationRef != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : locationRef) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
          writer.writeInt("locationCount", locationCount);
          writer.writeString("locationCursor", locationCursor);
          writer.writeBoolean("includeLocations", includeLocations);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("locations", "locations", new UnmodifiableMapBuilder<String, Object>(3)
        .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "locationRef")
        .build())
        .put("after", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "locationCursor")
        .build())
        .put("first", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "locationCount")
        .build())
      .build(), true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeLocations", false)))
    };

    final @Nullable Locations locations;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable Locations locations) {
      this.locations = locations;
    }

    /**
     *  Search for Location entities
     */
    public @Nullable Locations locations() {
      return this.locations;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], locations != null ? locations.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "locations=" + locations
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
        return ((this.locations == null) ? (that.locations == null) : this.locations.equals(that.locations));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (locations == null) ? 0 : locations.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.locations = locations;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final Locations.Mapper locationsFieldMapper = new Locations.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final Locations locations = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<Locations>() {
          @Override
          public Locations read(ResponseReader reader) {
            return locationsFieldMapper.map(reader);
          }
        });
        return new Data(locations);
      }
    }

    public static final class Builder {
      private @Nullable Locations locations;

      Builder() {
      }

      public Builder locations(@Nullable Locations locations) {
        this.locations = locations;
        return this;
      }

      public Builder locations(@Nonnull Mutator<Locations.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Locations.Builder builder = this.locations != null ? this.locations.toBuilder() : Locations.builder();
        mutator.accept(builder);
        this.locations = builder.build();
        return this;
      }

      public Data build() {
        return new Data(locations);
      }
    }
  }

  public static class Locations {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("pageInfo", "pageInfo", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("edges", "edges", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable PageInfo pageInfo;

    final @Nullable List<Edge> edges;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Locations(@Nonnull String __typename, @Nullable PageInfo pageInfo,
        @Nullable List<Edge> edges) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.pageInfo = pageInfo;
      this.edges = edges;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  Information to aid in pagination
     */
    public @Nullable PageInfo pageInfo() {
      return this.pageInfo;
    }

    /**
     *  A list of edges that links to Location type node
     */
    public @Nullable List<Edge> edges() {
      return this.edges;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeObject($responseFields[1], pageInfo != null ? pageInfo.marshaller() : null);
          writer.writeList($responseFields[2], edges, new ResponseWriter.ListWriter() {
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
        $toString = "Locations{"
          + "__typename=" + __typename + ", "
          + "pageInfo=" + pageInfo + ", "
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
      if (o instanceof Locations) {
        Locations that = (Locations) o;
        return this.__typename.equals(that.__typename)
         && ((this.pageInfo == null) ? (that.pageInfo == null) : this.pageInfo.equals(that.pageInfo))
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
        h ^= (pageInfo == null) ? 0 : pageInfo.hashCode();
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
      builder.pageInfo = pageInfo;
      builder.edges = edges;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Locations> {
      final PageInfo.Mapper pageInfoFieldMapper = new PageInfo.Mapper();

      final Edge.Mapper edgeFieldMapper = new Edge.Mapper();

      @Override
      public Locations map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final PageInfo pageInfo = reader.readObject($responseFields[1], new ResponseReader.ObjectReader<PageInfo>() {
          @Override
          public PageInfo read(ResponseReader reader) {
            return pageInfoFieldMapper.map(reader);
          }
        });
        final List<Edge> edges = reader.readList($responseFields[2], new ResponseReader.ListReader<Edge>() {
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
        return new Locations(__typename, pageInfo, edges);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable PageInfo pageInfo;

      private @Nullable List<Edge> edges;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder pageInfo(@Nullable PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
      }

      public Builder edges(@Nullable List<Edge> edges) {
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

      public Locations build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Locations(__typename, pageInfo, edges);
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
     *  The item at the end of the Location edge
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
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("primaryAddress", "primaryAddress", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("openingSchedule", "openingSchedule", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final @Nullable String type;

    final @Nullable String status;

    final @Nullable String name;

    final @Nullable PrimaryAddress primaryAddress;

    final @Nullable List<Attribute> attributes;

    final @Nullable OpeningSchedule openingSchedule;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Node(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        @Nullable String type, @Nullable String status, @Nullable String name,
        @Nullable PrimaryAddress primaryAddress, @Nullable List<Attribute> attributes,
        @Nullable OpeningSchedule openingSchedule) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.type = type;
      this.status = status;
      this.name = name;
      this.primaryAddress = primaryAddress;
      this.attributes = attributes;
      this.openingSchedule = openingSchedule;
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
     *  The location (i.e. store) id provided by the retailer
     */
    public @Nullable String ref() {
      return this.ref;
    }

    /**
     *  Type
     */
    public @Nullable String type() {
      return this.type;
    }

    /**
     *  The location status. Possible values are 'ACTIVE', 'INACTIVE'.
     */
    public @Nullable String status() {
      return this.status;
    }

    /**
     *  Name of location
     */
    public @Nullable String name() {
      return this.name;
    }

    /**
     *  Store location of the the location
     */
    public @Nullable PrimaryAddress primaryAddress() {
      return this.primaryAddress;
    }

    /**
     *  Attributes
     */
    public @Nullable List<Attribute> attributes() {
      return this.attributes;
    }

    /**
     *  Opening Schedule
     */
    public @Nullable OpeningSchedule openingSchedule() {
      return this.openingSchedule;
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
          writer.writeString($responseFields[5], name);
          writer.writeObject($responseFields[6], primaryAddress != null ? primaryAddress.marshaller() : null);
          writer.writeList($responseFields[7], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute) value).marshaller());
            }
          });
          writer.writeObject($responseFields[8], openingSchedule != null ? openingSchedule.marshaller() : null);
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
          + "name=" + name + ", "
          + "primaryAddress=" + primaryAddress + ", "
          + "attributes=" + attributes + ", "
          + "openingSchedule=" + openingSchedule
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
         && ((this.type == null) ? (that.type == null) : this.type.equals(that.type))
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
         && ((this.primaryAddress == null) ? (that.primaryAddress == null) : this.primaryAddress.equals(that.primaryAddress))
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes))
         && ((this.openingSchedule == null) ? (that.openingSchedule == null) : this.openingSchedule.equals(that.openingSchedule));
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
        h ^= (type == null) ? 0 : type.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        h *= 1000003;
        h ^= (name == null) ? 0 : name.hashCode();
        h *= 1000003;
        h ^= (primaryAddress == null) ? 0 : primaryAddress.hashCode();
        h *= 1000003;
        h ^= (attributes == null) ? 0 : attributes.hashCode();
        h *= 1000003;
        h ^= (openingSchedule == null) ? 0 : openingSchedule.hashCode();
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
      builder.name = name;
      builder.primaryAddress = primaryAddress;
      builder.attributes = attributes;
      builder.openingSchedule = openingSchedule;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Node> {
      final PrimaryAddress.Mapper primaryAddressFieldMapper = new PrimaryAddress.Mapper();

      final Attribute.Mapper attributeFieldMapper = new Attribute.Mapper();

      final OpeningSchedule.Mapper openingScheduleFieldMapper = new OpeningSchedule.Mapper();

      @Override
      public Node map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        final String name = reader.readString($responseFields[5]);
        final PrimaryAddress primaryAddress = reader.readObject($responseFields[6], new ResponseReader.ObjectReader<PrimaryAddress>() {
          @Override
          public PrimaryAddress read(ResponseReader reader) {
            return primaryAddressFieldMapper.map(reader);
          }
        });
        final List<Attribute> attributes = reader.readList($responseFields[7], new ResponseReader.ListReader<Attribute>() {
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
        final OpeningSchedule openingSchedule = reader.readObject($responseFields[8], new ResponseReader.ObjectReader<OpeningSchedule>() {
          @Override
          public OpeningSchedule read(ResponseReader reader) {
            return openingScheduleFieldMapper.map(reader);
          }
        });
        return new Node(__typename, id, ref, type, status, name, primaryAddress, attributes, openingSchedule);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String ref;

      private @Nullable String type;

      private @Nullable String status;

      private @Nullable String name;

      private @Nullable PrimaryAddress primaryAddress;

      private @Nullable List<Attribute> attributes;

      private @Nullable OpeningSchedule openingSchedule;

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

      public Builder type(@Nullable String type) {
        this.type = type;
        return this;
      }

      public Builder status(@Nullable String status) {
        this.status = status;
        return this;
      }

      public Builder name(@Nullable String name) {
        this.name = name;
        return this;
      }

      public Builder primaryAddress(@Nullable PrimaryAddress primaryAddress) {
        this.primaryAddress = primaryAddress;
        return this;
      }

      public Builder attributes(@Nullable List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
      }

      public Builder openingSchedule(@Nullable OpeningSchedule openingSchedule) {
        this.openingSchedule = openingSchedule;
        return this;
      }

      public Builder primaryAddress(@Nonnull Mutator<PrimaryAddress.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        PrimaryAddress.Builder builder = this.primaryAddress != null ? this.primaryAddress.toBuilder() : PrimaryAddress.builder();
        mutator.accept(builder);
        this.primaryAddress = builder.build();
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

      public Builder openingSchedule(@Nonnull Mutator<OpeningSchedule.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        OpeningSchedule.Builder builder = this.openingSchedule != null ? this.openingSchedule.toBuilder() : OpeningSchedule.builder();
        mutator.accept(builder);
        this.openingSchedule = builder.build();
        return this;
      }

      public Node build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new Node(__typename, id, ref, type, status, name, primaryAddress, attributes, openingSchedule);
      }
    }
  }

  public static class PrimaryAddress {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, true, Collections.<ResponseField.Condition>emptyList()),
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
      ResponseField.forString("timeZone", "timeZone", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String ref;

    final @Nullable String type;

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

    final @Nullable String timeZone;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public PrimaryAddress(@Nonnull String __typename, @Nonnull String id, @Nullable String ref,
        @Nullable String type, @Nullable String companyName, @Nullable String name,
        @Nullable String street, @Nullable String city, @Nullable String state,
        @Nullable String postcode, @Nullable String region, @Nullable String country,
        @Nullable Double latitude, @Nullable Double longitude, @Nullable String timeZone) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = ref;
      this.type = type;
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
      this.timeZone = timeZone;
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
     *  Timezone
     */
    public @Nullable String timeZone() {
      return this.timeZone;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeString($responseFields[3], type);
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
          writer.writeString($responseFields[14], timeZone);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "PrimaryAddress{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "type=" + type + ", "
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
          + "timeZone=" + timeZone
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof PrimaryAddress) {
        PrimaryAddress that = (PrimaryAddress) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && ((this.type == null) ? (that.type == null) : this.type.equals(that.type))
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
         && ((this.timeZone == null) ? (that.timeZone == null) : this.timeZone.equals(that.timeZone));
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
        h ^= (latitude == null) ? 0 : latitude.hashCode();
        h *= 1000003;
        h ^= (longitude == null) ? 0 : longitude.hashCode();
        h *= 1000003;
        h ^= (timeZone == null) ? 0 : timeZone.hashCode();
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
      builder.timeZone = timeZone;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<PrimaryAddress> {
      @Override
      public PrimaryAddress map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String type = reader.readString($responseFields[3]);
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
        final String timeZone = reader.readString($responseFields[14]);
        return new PrimaryAddress(__typename, id, ref, type, companyName, name, street, city, state, postcode, region, country, latitude, longitude, timeZone);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String ref;

      private @Nullable String type;

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

      private @Nullable String timeZone;

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

      public Builder latitude(@Nullable Double latitude) {
        this.latitude = latitude;
        return this;
      }

      public Builder longitude(@Nullable Double longitude) {
        this.longitude = longitude;
        return this;
      }

      public Builder timeZone(@Nullable String timeZone) {
        this.timeZone = timeZone;
        return this;
      }

      public PrimaryAddress build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new PrimaryAddress(__typename, id, ref, type, companyName, name, street, city, state, postcode, region, country, latitude, longitude, timeZone);
      }
    }
  }

  public static class Attribute {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("value", "value", null, false, CustomType.JSON, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String type;

    final @Nonnull String name;

    final @Nonnull Object value;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Attribute(@Nonnull String __typename, @Nonnull String type, @Nonnull String name,
        @Nonnull Object value) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.type = Utils.checkNotNull(type, "type == null");
      this.name = Utils.checkNotNull(name, "name == null");
      this.value = Utils.checkNotNull(value, "value == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  Type of the attribute's `value`. This is a free string and can be used by the client to interpret and cast the `value` into the appropriate type.
     */
    public @Nonnull String type() {
      return this.type;
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

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], type);
          writer.writeString($responseFields[2], name);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], value);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Attribute{"
          + "__typename=" + __typename + ", "
          + "type=" + type + ", "
          + "name=" + name + ", "
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
         && this.type.equals(that.type)
         && this.name.equals(that.name)
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
        h ^= type.hashCode();
        h *= 1000003;
        h ^= name.hashCode();
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
      builder.type = type;
      builder.name = name;
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
        final String type = reader.readString($responseFields[1]);
        final String name = reader.readString($responseFields[2]);
        final Object value = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        return new Attribute(__typename, type, name, value);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String type;

      private @Nonnull String name;

      private @Nonnull Object value;

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

      public Builder name(@Nonnull String name) {
        this.name = name;
        return this;
      }

      public Builder value(@Nonnull Object value) {
        this.value = value;
        return this;
      }

      public Attribute build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(value, "value == null");
        return new Attribute(__typename, type, name, value);
      }
    }
  }

  public static class OpeningSchedule {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("updatedOn", "updatedOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forBoolean("allHours", "allHours", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("monEnd", "monEnd", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("monStart", "monStart", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("tueEnd", "tueEnd", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("tueStart", "tueStart", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("wedEnd", "wedEnd", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("wedStart", "wedStart", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("thuEnd", "thuEnd", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("thuStart", "thuStart", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("friEnd", "friEnd", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("friStart", "friStart", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("satEnd", "satEnd", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("satStart", "satStart", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("sunEnd", "sunEnd", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("sunStart", "sunStart", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable Object createdOn;

    final @Nullable Object updatedOn;

    final boolean allHours;

    final int monEnd;

    final int monStart;

    final int tueEnd;

    final int tueStart;

    final int wedEnd;

    final int wedStart;

    final int thuEnd;

    final int thuStart;

    final int friEnd;

    final int friStart;

    final int satEnd;

    final int satStart;

    final int sunEnd;

    final int sunStart;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public OpeningSchedule(@Nonnull String __typename, @Nonnull String id,
        @Nullable Object createdOn, @Nullable Object updatedOn, boolean allHours, int monEnd,
        int monStart, int tueEnd, int tueStart, int wedEnd, int wedStart, int thuEnd, int thuStart,
        int friEnd, int friStart, int satEnd, int satStart, int sunEnd, int sunStart) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.allHours = allHours;
      this.monEnd = monEnd;
      this.monStart = monStart;
      this.tueEnd = tueEnd;
      this.tueStart = tueStart;
      this.wedEnd = wedEnd;
      this.wedStart = wedStart;
      this.thuEnd = thuEnd;
      this.thuStart = thuStart;
      this.friEnd = friEnd;
      this.friStart = friStart;
      this.satEnd = satEnd;
      this.satStart = satStart;
      this.sunEnd = sunEnd;
      this.sunStart = sunStart;
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
     *  Is the location open 24hrs
     */
    public boolean allHours() {
      return this.allHours;
    }

    /**
     *  Closing time on Monday (HHmm)
     */
    public int monEnd() {
      return this.monEnd;
    }

    /**
     *  Opening time on Monday (HHmm).
     */
    public int monStart() {
      return this.monStart;
    }

    /**
     *  Closing time on Tuesday (HHmm)
     */
    public int tueEnd() {
      return this.tueEnd;
    }

    /**
     *  Opening time on Tuesday (HHmm)
     */
    public int tueStart() {
      return this.tueStart;
    }

    /**
     *  Closing time on Wednesday (HHmm)
     */
    public int wedEnd() {
      return this.wedEnd;
    }

    /**
     *  Opening time on Wednesday (HHmm)
     */
    public int wedStart() {
      return this.wedStart;
    }

    /**
     *  Closing time on Thursday (HHmm)
     */
    public int thuEnd() {
      return this.thuEnd;
    }

    /**
     *  Opening time on Thursday (HHmm)
     */
    public int thuStart() {
      return this.thuStart;
    }

    /**
     *  Closing time on Friday (HHmm)
     */
    public int friEnd() {
      return this.friEnd;
    }

    /**
     *  Opening time on Friday (HHmm)
     */
    public int friStart() {
      return this.friStart;
    }

    /**
     *  Closing time on Saturday (HHmm)
     */
    public int satEnd() {
      return this.satEnd;
    }

    /**
     *  Opening time on Saturday (HHmm)
     */
    public int satStart() {
      return this.satStart;
    }

    /**
     *  Closing time on Sunday (HHmm)
     */
    public int sunEnd() {
      return this.sunEnd;
    }

    /**
     *  Opening time on Sunday (HHmm)
     */
    public int sunStart() {
      return this.sunStart;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], createdOn);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[3], updatedOn);
          writer.writeBoolean($responseFields[4], allHours);
          writer.writeInt($responseFields[5], monEnd);
          writer.writeInt($responseFields[6], monStart);
          writer.writeInt($responseFields[7], tueEnd);
          writer.writeInt($responseFields[8], tueStart);
          writer.writeInt($responseFields[9], wedEnd);
          writer.writeInt($responseFields[10], wedStart);
          writer.writeInt($responseFields[11], thuEnd);
          writer.writeInt($responseFields[12], thuStart);
          writer.writeInt($responseFields[13], friEnd);
          writer.writeInt($responseFields[14], friStart);
          writer.writeInt($responseFields[15], satEnd);
          writer.writeInt($responseFields[16], satStart);
          writer.writeInt($responseFields[17], sunEnd);
          writer.writeInt($responseFields[18], sunStart);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "OpeningSchedule{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "createdOn=" + createdOn + ", "
          + "updatedOn=" + updatedOn + ", "
          + "allHours=" + allHours + ", "
          + "monEnd=" + monEnd + ", "
          + "monStart=" + monStart + ", "
          + "tueEnd=" + tueEnd + ", "
          + "tueStart=" + tueStart + ", "
          + "wedEnd=" + wedEnd + ", "
          + "wedStart=" + wedStart + ", "
          + "thuEnd=" + thuEnd + ", "
          + "thuStart=" + thuStart + ", "
          + "friEnd=" + friEnd + ", "
          + "friStart=" + friStart + ", "
          + "satEnd=" + satEnd + ", "
          + "satStart=" + satStart + ", "
          + "sunEnd=" + sunEnd + ", "
          + "sunStart=" + sunStart
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof OpeningSchedule) {
        OpeningSchedule that = (OpeningSchedule) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.updatedOn == null) ? (that.updatedOn == null) : this.updatedOn.equals(that.updatedOn))
         && this.allHours == that.allHours
         && this.monEnd == that.monEnd
         && this.monStart == that.monStart
         && this.tueEnd == that.tueEnd
         && this.tueStart == that.tueStart
         && this.wedEnd == that.wedEnd
         && this.wedStart == that.wedStart
         && this.thuEnd == that.thuEnd
         && this.thuStart == that.thuStart
         && this.friEnd == that.friEnd
         && this.friStart == that.friStart
         && this.satEnd == that.satEnd
         && this.satStart == that.satStart
         && this.sunEnd == that.sunEnd
         && this.sunStart == that.sunStart;
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
        h ^= Boolean.valueOf(allHours).hashCode();
        h *= 1000003;
        h ^= monEnd;
        h *= 1000003;
        h ^= monStart;
        h *= 1000003;
        h ^= tueEnd;
        h *= 1000003;
        h ^= tueStart;
        h *= 1000003;
        h ^= wedEnd;
        h *= 1000003;
        h ^= wedStart;
        h *= 1000003;
        h ^= thuEnd;
        h *= 1000003;
        h ^= thuStart;
        h *= 1000003;
        h ^= friEnd;
        h *= 1000003;
        h ^= friStart;
        h *= 1000003;
        h ^= satEnd;
        h *= 1000003;
        h ^= satStart;
        h *= 1000003;
        h ^= sunEnd;
        h *= 1000003;
        h ^= sunStart;
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
      builder.allHours = allHours;
      builder.monEnd = monEnd;
      builder.monStart = monStart;
      builder.tueEnd = tueEnd;
      builder.tueStart = tueStart;
      builder.wedEnd = wedEnd;
      builder.wedStart = wedStart;
      builder.thuEnd = thuEnd;
      builder.thuStart = thuStart;
      builder.friEnd = friEnd;
      builder.friStart = friStart;
      builder.satEnd = satEnd;
      builder.satStart = satStart;
      builder.sunEnd = sunEnd;
      builder.sunStart = sunStart;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<OpeningSchedule> {
      @Override
      public OpeningSchedule map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        final Object updatedOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[3]);
        final boolean allHours = reader.readBoolean($responseFields[4]);
        final int monEnd = reader.readInt($responseFields[5]);
        final int monStart = reader.readInt($responseFields[6]);
        final int tueEnd = reader.readInt($responseFields[7]);
        final int tueStart = reader.readInt($responseFields[8]);
        final int wedEnd = reader.readInt($responseFields[9]);
        final int wedStart = reader.readInt($responseFields[10]);
        final int thuEnd = reader.readInt($responseFields[11]);
        final int thuStart = reader.readInt($responseFields[12]);
        final int friEnd = reader.readInt($responseFields[13]);
        final int friStart = reader.readInt($responseFields[14]);
        final int satEnd = reader.readInt($responseFields[15]);
        final int satStart = reader.readInt($responseFields[16]);
        final int sunEnd = reader.readInt($responseFields[17]);
        final int sunStart = reader.readInt($responseFields[18]);
        return new OpeningSchedule(__typename, id, createdOn, updatedOn, allHours, monEnd, monStart, tueEnd, tueStart, wedEnd, wedStart, thuEnd, thuStart, friEnd, friStart, satEnd, satStart, sunEnd, sunStart);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable Object createdOn;

      private @Nullable Object updatedOn;

      private boolean allHours;

      private int monEnd;

      private int monStart;

      private int tueEnd;

      private int tueStart;

      private int wedEnd;

      private int wedStart;

      private int thuEnd;

      private int thuStart;

      private int friEnd;

      private int friStart;

      private int satEnd;

      private int satStart;

      private int sunEnd;

      private int sunStart;

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

      public Builder allHours(boolean allHours) {
        this.allHours = allHours;
        return this;
      }

      public Builder monEnd(int monEnd) {
        this.monEnd = monEnd;
        return this;
      }

      public Builder monStart(int monStart) {
        this.monStart = monStart;
        return this;
      }

      public Builder tueEnd(int tueEnd) {
        this.tueEnd = tueEnd;
        return this;
      }

      public Builder tueStart(int tueStart) {
        this.tueStart = tueStart;
        return this;
      }

      public Builder wedEnd(int wedEnd) {
        this.wedEnd = wedEnd;
        return this;
      }

      public Builder wedStart(int wedStart) {
        this.wedStart = wedStart;
        return this;
      }

      public Builder thuEnd(int thuEnd) {
        this.thuEnd = thuEnd;
        return this;
      }

      public Builder thuStart(int thuStart) {
        this.thuStart = thuStart;
        return this;
      }

      public Builder friEnd(int friEnd) {
        this.friEnd = friEnd;
        return this;
      }

      public Builder friStart(int friStart) {
        this.friStart = friStart;
        return this;
      }

      public Builder satEnd(int satEnd) {
        this.satEnd = satEnd;
        return this;
      }

      public Builder satStart(int satStart) {
        this.satStart = satStart;
        return this;
      }

      public Builder sunEnd(int sunEnd) {
        this.sunEnd = sunEnd;
        return this;
      }

      public Builder sunStart(int sunStart) {
        this.sunStart = sunStart;
        return this;
      }

      public OpeningSchedule build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        return new OpeningSchedule(__typename, id, createdOn, updatedOn, allHours, monEnd, monStart, tueEnd, tueStart, wedEnd, wedStart, thuEnd, thuStart, friEnd, friStart, satEnd, satStart, sunEnd, sunStart);
      }
    }
  }
}
