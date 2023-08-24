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
public final class GetVariantProductByRefQuery implements Query<GetVariantProductByRefQuery.Data, GetVariantProductByRefQuery.Data, GetVariantProductByRefQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query GetVariantProductByRef($ref: String!, $productCatalogueRef: String!) {\n"
      + "  variantProduct(ref: $ref, catalogue: {ref: $productCatalogueRef}) {\n"
      + "    __typename\n"
      + "    attributes {\n"
      + "      __typename\n"
      + "      name\n"
      + "      type\n"
      + "      value\n"
      + "    }\n"
      + "    id\n"
      + "    name\n"
      + "    ref\n"
      + "    status\n"
      + "    type\n"
      + "    summary\n"
      + "    product {\n"
      + "      __typename\n"
      + "      id\n"
      + "      ref\n"
      + "      name\n"
      + "      status\n"
      + "    }\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "GetVariantProductByRef";
    }
  };

  private final GetVariantProductByRefQuery.Variables variables;

  public GetVariantProductByRefQuery(@Nonnull String ref, @Nonnull String productCatalogueRef) {
    Utils.checkNotNull(ref, "ref == null");
    Utils.checkNotNull(productCatalogueRef, "productCatalogueRef == null");
    variables = new GetVariantProductByRefQuery.Variables(ref, productCatalogueRef);
  }

  @Override
  public String operationId() {
    return "e5897afaa31eabe58687715b623639eeab9986ab60a37f545827a449f22cbe65";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public GetVariantProductByRefQuery.Data wrapData(GetVariantProductByRefQuery.Data data) {
    return data;
  }

  @Override
  public GetVariantProductByRefQuery.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<GetVariantProductByRefQuery.Data> responseFieldMapper() {
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

    private @Nonnull String productCatalogueRef;

    Builder() {
    }

    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    public Builder productCatalogueRef(@Nonnull String productCatalogueRef) {
      this.productCatalogueRef = productCatalogueRef;
      return this;
    }

    public GetVariantProductByRefQuery build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(productCatalogueRef, "productCatalogueRef == null");
      return new GetVariantProductByRefQuery(ref, productCatalogueRef);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nonnull String ref;

    private final @Nonnull String productCatalogueRef;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nonnull String ref, @Nonnull String productCatalogueRef) {
      this.ref = ref;
      this.productCatalogueRef = productCatalogueRef;
      this.valueMap.put("ref", ref);
      this.valueMap.put("productCatalogueRef", productCatalogueRef);
    }

    public @Nonnull String ref() {
      return ref;
    }

    public @Nonnull String productCatalogueRef() {
      return productCatalogueRef;
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
          writer.writeString("productCatalogueRef", productCatalogueRef);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("variantProduct", "variantProduct", new UnmodifiableMapBuilder<String, Object>(2)
        .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "ref")
        .build())
        .put("catalogue", new UnmodifiableMapBuilder<String, Object>(1)
          .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
            .put("kind", "Variable")
            .put("variableName", "productCatalogueRef")
          .build())
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable VariantProduct variantProduct;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable VariantProduct variantProduct) {
      this.variantProduct = variantProduct;
    }

    /**
     *  Find a VariantProduct entity
     */
    public @Nullable VariantProduct variantProduct() {
      return this.variantProduct;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], variantProduct != null ? variantProduct.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "variantProduct=" + variantProduct
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
        return ((this.variantProduct == null) ? (that.variantProduct == null) : this.variantProduct.equals(that.variantProduct));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (variantProduct == null) ? 0 : variantProduct.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.variantProduct = variantProduct;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final VariantProduct.Mapper variantProductFieldMapper = new VariantProduct.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final VariantProduct variantProduct = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<VariantProduct>() {
          @Override
          public VariantProduct read(ResponseReader reader) {
            return variantProductFieldMapper.map(reader);
          }
        });
        return new Data(variantProduct);
      }
    }

    public static final class Builder {
      private @Nullable VariantProduct variantProduct;

      Builder() {
      }

      public Builder variantProduct(@Nullable VariantProduct variantProduct) {
        this.variantProduct = variantProduct;
        return this;
      }

      public Builder variantProduct(@Nonnull Mutator<VariantProduct.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        VariantProduct.Builder builder = this.variantProduct != null ? this.variantProduct.toBuilder() : VariantProduct.builder();
        mutator.accept(builder);
        this.variantProduct = builder.build();
        return this;
      }

      public Data build() {
        return new Data(variantProduct);
      }
    }
  }

  public static class VariantProduct {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("summary", "summary", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("product", "product", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<Attribute> attributes;

    final @Nonnull String id;

    final @Nonnull String name;

    final @Nonnull String ref;

    final @Nullable String status;

    final @Nonnull String type;

    final @Nullable String summary;

    final @Nonnull Product product;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public VariantProduct(@Nonnull String __typename, @Nullable List<Attribute> attributes,
        @Nonnull String id, @Nonnull String name, @Nonnull String ref, @Nullable String status,
        @Nonnull String type, @Nullable String summary, @Nonnull Product product) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.attributes = attributes;
      this.id = Utils.checkNotNull(id, "id == null");
      this.name = Utils.checkNotNull(name, "name == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.status = status;
      this.type = Utils.checkNotNull(type, "type == null");
      this.summary = summary;
      this.product = Utils.checkNotNull(product, "product == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
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
     *  The unique reference identifier for the Product
     */
    public @Nonnull String ref() {
      return this.ref;
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
     *  A short description of the Product (max 255 chars)
     */
    public @Nullable String summary() {
      return this.summary;
    }

    /**
     *  The associated Standard Product for this Variant Product
     */
    public @Nonnull Product product() {
      return this.product;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeList($responseFields[1], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute) value).marshaller());
            }
          });
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], id);
          writer.writeString($responseFields[3], name);
          writer.writeString($responseFields[4], ref);
          writer.writeString($responseFields[5], status);
          writer.writeString($responseFields[6], type);
          writer.writeString($responseFields[7], summary);
          writer.writeObject($responseFields[8], product.marshaller());
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "VariantProduct{"
          + "__typename=" + __typename + ", "
          + "attributes=" + attributes + ", "
          + "id=" + id + ", "
          + "name=" + name + ", "
          + "ref=" + ref + ", "
          + "status=" + status + ", "
          + "type=" + type + ", "
          + "summary=" + summary + ", "
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
      if (o instanceof VariantProduct) {
        VariantProduct that = (VariantProduct) o;
        return this.__typename.equals(that.__typename)
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes))
         && this.id.equals(that.id)
         && this.name.equals(that.name)
         && this.ref.equals(that.ref)
         && ((this.status == null) ? (that.status == null) : this.status.equals(that.status))
         && this.type.equals(that.type)
         && ((this.summary == null) ? (that.summary == null) : this.summary.equals(that.summary))
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
        h ^= (attributes == null) ? 0 : attributes.hashCode();
        h *= 1000003;
        h ^= id.hashCode();
        h *= 1000003;
        h ^= name.hashCode();
        h *= 1000003;
        h ^= ref.hashCode();
        h *= 1000003;
        h ^= (status == null) ? 0 : status.hashCode();
        h *= 1000003;
        h ^= type.hashCode();
        h *= 1000003;
        h ^= (summary == null) ? 0 : summary.hashCode();
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
      builder.attributes = attributes;
      builder.id = id;
      builder.name = name;
      builder.ref = ref;
      builder.status = status;
      builder.type = type;
      builder.summary = summary;
      builder.product = product;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<VariantProduct> {
      final Attribute.Mapper attributeFieldMapper = new Attribute.Mapper();

      final Product.Mapper productFieldMapper = new Product.Mapper();

      @Override
      public VariantProduct map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final List<Attribute> attributes = reader.readList($responseFields[1], new ResponseReader.ListReader<Attribute>() {
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
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        final String name = reader.readString($responseFields[3]);
        final String ref = reader.readString($responseFields[4]);
        final String status = reader.readString($responseFields[5]);
        final String type = reader.readString($responseFields[6]);
        final String summary = reader.readString($responseFields[7]);
        final Product product = reader.readObject($responseFields[8], new ResponseReader.ObjectReader<Product>() {
          @Override
          public Product read(ResponseReader reader) {
            return productFieldMapper.map(reader);
          }
        });
        return new VariantProduct(__typename, attributes, id, name, ref, status, type, summary, product);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable List<Attribute> attributes;

      private @Nonnull String id;

      private @Nonnull String name;

      private @Nonnull String ref;

      private @Nullable String status;

      private @Nonnull String type;

      private @Nullable String summary;

      private @Nonnull Product product;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
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

      public Builder ref(@Nonnull String ref) {
        this.ref = ref;
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

      public Builder summary(@Nullable String summary) {
        this.summary = summary;
        return this;
      }

      public Builder product(@Nonnull Product product) {
        this.product = product;
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

      public VariantProduct build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(name, "name == null");
        Utils.checkNotNull(ref, "ref == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(product, "product == null");
        return new VariantProduct(__typename, attributes, id, name, ref, status, type, summary, product);
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
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("name", "name", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nonnull String ref;

    final @Nonnull String name;

    final @Nullable String status;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Product(@Nonnull String __typename, @Nonnull String id, @Nonnull String ref,
        @Nonnull String name, @Nullable String status) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.name = Utils.checkNotNull(name, "name == null");
      this.status = status;
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
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeString($responseFields[3], name);
          writer.writeString($responseFields[4], status);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Product{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
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
         && this.id.equals(that.id)
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
        h ^= id.hashCode();
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
      builder.id = id;
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
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String name = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        return new Product(__typename, id, ref, name, status);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nonnull String ref;

      private @Nonnull String name;

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
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(ref, "ref == null");
        Utils.checkNotNull(name, "name == null");
        return new Product(__typename, id, ref, name, status);
      }
    }
  }
}
