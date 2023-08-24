package com.fluentcommerce.graphql.query.settings;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class GetSettingsByNameQuery implements Query<GetSettingsByNameQuery.Data, GetSettingsByNameQuery.Data, GetSettingsByNameQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query GetSettingsByName($name: [String], $context: [String!], $contextId: [Int!], $settingCount: Int, $settingCursor: String, $includeSettigns: Boolean!) {\n"
      + "  settings(name: $name, context: $context, contextId: $contextId, first: $settingCount, after: $settingCursor) @include(if: $includeSettigns) {\n"
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
      + "        name\n"
      + "        valueType\n"
      + "        value\n"
      + "        context\n"
      + "        contextId\n"
      + "        lobValue\n"
      + "      }\n"
      + "    }\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "GetSettingsByName";
    }
  };

  private final GetSettingsByNameQuery.Variables variables;

  public GetSettingsByNameQuery(@Nullable List<String> name, @Nullable List<String> context,
      @Nullable List<Integer> contextId, @Nullable Integer settingCount,
      @Nullable String settingCursor, boolean includeSettigns) {
    variables = new GetSettingsByNameQuery.Variables(name, context, contextId, settingCount, settingCursor, includeSettigns);
  }

  @Override
  public String operationId() {
    return "5844aff9bc1b34aaf449a6390fc2fbbe1bc6410cf7a1765adb4295c3ddb8fdb1";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public GetSettingsByNameQuery.Data wrapData(GetSettingsByNameQuery.Data data) {
    return data;
  }

  @Override
  public GetSettingsByNameQuery.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<GetSettingsByNameQuery.Data> responseFieldMapper() {
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
    private @Nullable List<String> name;

    private @Nullable List<String> context;

    private @Nullable List<Integer> contextId;

    private @Nullable Integer settingCount;

    private @Nullable String settingCursor;

    private boolean includeSettigns;

    Builder() {
    }

    public Builder name(@Nullable List<String> name) {
      this.name = name;
      return this;
    }

    public Builder context(@Nullable List<String> context) {
      this.context = context;
      return this;
    }

    public Builder contextId(@Nullable List<Integer> contextId) {
      this.contextId = contextId;
      return this;
    }

    public Builder settingCount(@Nullable Integer settingCount) {
      this.settingCount = settingCount;
      return this;
    }

    public Builder settingCursor(@Nullable String settingCursor) {
      this.settingCursor = settingCursor;
      return this;
    }

    public Builder includeSettigns(boolean includeSettigns) {
      this.includeSettigns = includeSettigns;
      return this;
    }

    public GetSettingsByNameQuery build() {
      return new GetSettingsByNameQuery(name, context, contextId, settingCount, settingCursor, includeSettigns);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nullable List<String> name;

    private final @Nullable List<String> context;

    private final @Nullable List<Integer> contextId;

    private final @Nullable Integer settingCount;

    private final @Nullable String settingCursor;

    private final boolean includeSettigns;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nullable List<String> name, @Nullable List<String> context,
        @Nullable List<Integer> contextId, @Nullable Integer settingCount,
        @Nullable String settingCursor, boolean includeSettigns) {
      this.name = name;
      this.context = context;
      this.contextId = contextId;
      this.settingCount = settingCount;
      this.settingCursor = settingCursor;
      this.includeSettigns = includeSettigns;
      this.valueMap.put("name", name);
      this.valueMap.put("context", context);
      this.valueMap.put("contextId", contextId);
      this.valueMap.put("settingCount", settingCount);
      this.valueMap.put("settingCursor", settingCursor);
      this.valueMap.put("includeSettigns", includeSettigns);
    }

    public @Nullable List<String> name() {
      return name;
    }

    public @Nullable List<String> context() {
      return context;
    }

    public @Nullable List<Integer> contextId() {
      return contextId;
    }

    public @Nullable Integer settingCount() {
      return settingCount;
    }

    public @Nullable String settingCursor() {
      return settingCursor;
    }

    public boolean includeSettigns() {
      return includeSettigns;
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
          writer.writeList("name", name != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : name) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
          writer.writeList("context", context != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (String $item : context) {
                listItemWriter.writeString($item);
              }
            }
          } : null);
          writer.writeList("contextId", contextId != null ? new InputFieldWriter.ListWriter() {
            @Override
            public void write(InputFieldWriter.ListItemWriter listItemWriter) throws IOException {
              for (Integer $item : contextId) {
                listItemWriter.writeInt($item);
              }
            }
          } : null);
          writer.writeInt("settingCount", settingCount);
          writer.writeString("settingCursor", settingCursor);
          writer.writeBoolean("includeSettigns", includeSettigns);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("settings", "settings", new UnmodifiableMapBuilder<String, Object>(5)
        .put("name", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "name")
        .build())
        .put("context", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "context")
        .build())
        .put("contextId", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "contextId")
        .build())
        .put("after", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "settingCursor")
        .build())
        .put("first", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "settingCount")
        .build())
      .build(), true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeSettigns", false)))
    };

    final @Nullable Settings settings;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable Settings settings) {
      this.settings = settings;
    }

    /**
     *  Search for Setting entities
     */
    public @Nullable Settings settings() {
      return this.settings;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], settings != null ? settings.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "settings=" + settings
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
        return ((this.settings == null) ? (that.settings == null) : this.settings.equals(that.settings));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (settings == null) ? 0 : settings.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.settings = settings;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final Settings.Mapper settingsFieldMapper = new Settings.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final Settings settings = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<Settings>() {
          @Override
          public Settings read(ResponseReader reader) {
            return settingsFieldMapper.map(reader);
          }
        });
        return new Data(settings);
      }
    }

    public static final class Builder {
      private @Nullable Settings settings;

      Builder() {
      }

      public Builder settings(@Nullable Settings settings) {
        this.settings = settings;
        return this;
      }

      public Builder settings(@Nonnull Mutator<Settings.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Settings.Builder builder = this.settings != null ? this.settings.toBuilder() : Settings.builder();
        mutator.accept(builder);
        this.settings = builder.build();
        return this;
      }

      public Data build() {
        return new Data(settings);
      }
    }
  }

  public static class Settings {
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

    public Settings(@Nonnull String __typename, @Nullable PageInfo pageInfo,
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
     *  A list of edges that links to Setting type node
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
        $toString = "Settings{"
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
      if (o instanceof Settings) {
        Settings that = (Settings) o;
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

    public static final class Mapper implements ResponseFieldMapper<Settings> {
      final PageInfo.Mapper pageInfoFieldMapper = new PageInfo.Mapper();

      final Edge.Mapper edgeFieldMapper = new Edge.Mapper();

      @Override
      public Settings map(ResponseReader reader) {
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
        return new Settings(__typename, pageInfo, edges);
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

      public Settings build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Settings(__typename, pageInfo, edges);
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
     *  The item at the end of the Setting edge
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
      ResponseField.forString("name", "name", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("valueType", "valueType", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("value", "value", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("context", "context", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("contextId", "contextId", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("lobValue", "lobValue", null, true, CustomType.JSON, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nullable String name;

    final @Nullable String valueType;

    final @Nullable String value;

    final @Nonnull String context;

    final int contextId;

    final @Nullable Object lobValue;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Node(@Nonnull String __typename, @Nonnull String id, @Nullable String name,
        @Nullable String valueType, @Nullable String value, @Nonnull String context, int contextId,
        @Nullable Object lobValue) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.name = name;
      this.valueType = valueType;
      this.value = value;
      this.context = Utils.checkNotNull(context, "context == null");
      this.contextId = contextId;
      this.lobValue = lobValue;
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
     *  Name of the setting.
     */
    public @Nullable String name() {
      return this.name;
    }

    /**
     *  Data type of the setting's value. Supported types are _LOB_, _STRING_, _INTEGER_, _BOOLEAN_ and _JSON_.
     */
    public @Nullable String valueType() {
      return this.valueType;
    }

    /**
     *  Value of the setting. Use this if the value is NOT a JSON.
     */
    public @Nullable String value() {
      return this.value;
    }

    /**
     *  The context of the setting. Supported values are ACCOUNT, RETAILER, AGENT or CUSTOMER
     */
    public @Nonnull String context() {
      return this.context;
    }

    /**
     *  `ID` of the context. For instance, use a retailer's ID when using _RETAILER_ context.
     */
    public int contextId() {
      return this.contextId;
    }

    /**
     *  Value of the setting. Use this if the value is a JSON.
     */
    public @Nullable Object lobValue() {
      return this.lobValue;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], name);
          writer.writeString($responseFields[3], valueType);
          writer.writeString($responseFields[4], value);
          writer.writeString($responseFields[5], context);
          writer.writeInt($responseFields[6], contextId);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[7], lobValue);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Node{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "name=" + name + ", "
          + "valueType=" + valueType + ", "
          + "value=" + value + ", "
          + "context=" + context + ", "
          + "contextId=" + contextId + ", "
          + "lobValue=" + lobValue
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
         && ((this.name == null) ? (that.name == null) : this.name.equals(that.name))
         && ((this.valueType == null) ? (that.valueType == null) : this.valueType.equals(that.valueType))
         && ((this.value == null) ? (that.value == null) : this.value.equals(that.value))
         && this.context.equals(that.context)
         && this.contextId == that.contextId
         && ((this.lobValue == null) ? (that.lobValue == null) : this.lobValue.equals(that.lobValue));
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
        h ^= (name == null) ? 0 : name.hashCode();
        h *= 1000003;
        h ^= (valueType == null) ? 0 : valueType.hashCode();
        h *= 1000003;
        h ^= (value == null) ? 0 : value.hashCode();
        h *= 1000003;
        h ^= context.hashCode();
        h *= 1000003;
        h ^= contextId;
        h *= 1000003;
        h ^= (lobValue == null) ? 0 : lobValue.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.id = id;
      builder.name = name;
      builder.valueType = valueType;
      builder.value = value;
      builder.context = context;
      builder.contextId = contextId;
      builder.lobValue = lobValue;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Node> {
      @Override
      public Node map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String name = reader.readString($responseFields[2]);
        final String valueType = reader.readString($responseFields[3]);
        final String value = reader.readString($responseFields[4]);
        final String context = reader.readString($responseFields[5]);
        final int contextId = reader.readInt($responseFields[6]);
        final Object lobValue = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[7]);
        return new Node(__typename, id, name, valueType, value, context, contextId, lobValue);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nullable String name;

      private @Nullable String valueType;

      private @Nullable String value;

      private @Nonnull String context;

      private int contextId;

      private @Nullable Object lobValue;

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

      public Builder name(@Nullable String name) {
        this.name = name;
        return this;
      }

      public Builder valueType(@Nullable String valueType) {
        this.valueType = valueType;
        return this;
      }

      public Builder value(@Nullable String value) {
        this.value = value;
        return this;
      }

      public Builder context(@Nonnull String context) {
        this.context = context;
        return this;
      }

      public Builder contextId(int contextId) {
        this.contextId = contextId;
        return this;
      }

      public Builder lobValue(@Nullable Object lobValue) {
        this.lobValue = lobValue;
        return this;
      }

      public Node build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(context, "context == null");
        return new Node(__typename, id, name, valueType, value, context, contextId, lobValue);
      }
    }
  }
}
