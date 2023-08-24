package com.fluentcommerce.graphql.query.returns;

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
import com.fluentcommerce.graphql.type.RetailerId;
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
public final class GetReturnOrderByRefQuery implements Query<GetReturnOrderByRefQuery.Data, GetReturnOrderByRefQuery.Data, GetReturnOrderByRefQuery.Variables> {
  public static final String OPERATION_DEFINITION = "query GetReturnOrderByRef($ref: String!, $retailer: RetailerId!, $includePickupLocation: Boolean!, $includeAttributes: Boolean!, $includeReturnOrderItems: Boolean!, $includeReturnFulfilments: Boolean!, $returnOrderItemCount: Int, $returnOrderItemCursor: String) {\n"
      + "  returnOrder(ref: $ref, retailer: $retailer) {\n"
      + "    __typename\n"
      + "    ref\n"
      + "    id\n"
      + "    type\n"
      + "    status\n"
      + "    createdOn\n"
      + "    updatedOn\n"
      + "    order {\n"
      + "      __typename\n"
      + "      ref\n"
      + "    }\n"
      + "    pickupAddress @include(if: $includePickupLocation) {\n"
      + "      __typename\n"
      + "      companyName\n"
      + "      name\n"
      + "      street\n"
      + "      city\n"
      + "      state\n"
      + "      postcode\n"
      + "      region\n"
      + "      country\n"
      + "      latitude\n"
      + "      longitude\n"
      + "      timeZone\n"
      + "    }\n"
      + "    returnOrderFulfilments {\n"
      + "      __typename\n"
      + "      edges {\n"
      + "        __typename\n"
      + "        node {\n"
      + "          __typename\n"
      + "          id\n"
      + "          ref\n"
      + "          status\n"
      + "          attributes {\n"
      + "            __typename\n"
      + "            name\n"
      + "            type\n"
      + "            value\n"
      + "          }\n"
      + "          returnFulfilmentItems @include(if: $includeReturnFulfilments) {\n"
      + "            __typename\n"
      + "            edges {\n"
      + "              __typename\n"
      + "              node {\n"
      + "                __typename\n"
      + "                id\n"
      + "                ref\n"
      + "                returnOrderItem {\n"
      + "                  __typename\n"
      + "                  ref\n"
      + "                }\n"
      + "                unitQuantity {\n"
      + "                  __typename\n"
      + "                  quantity\n"
      + "                }\n"
      + "              }\n"
      + "            }\n"
      + "          }\n"
      + "        }\n"
      + "      }\n"
      + "    }\n"
      + "    lodgedLocation {\n"
      + "      __typename\n"
      + "      ref\n"
      + "    }\n"
      + "    destinationLocation {\n"
      + "      __typename\n"
      + "      ref\n"
      + "    }\n"
      + "    retailer {\n"
      + "      __typename\n"
      + "      id\n"
      + "    }\n"
      + "    workflow {\n"
      + "      __typename\n"
      + "      ref\n"
      + "      version\n"
      + "    }\n"
      + "    customer {\n"
      + "      __typename\n"
      + "      ref\n"
      + "    }\n"
      + "    currency {\n"
      + "      __typename\n"
      + "      alphabeticCode\n"
      + "    }\n"
      + "    defaultTaxType {\n"
      + "      __typename\n"
      + "      country\n"
      + "      group\n"
      + "      tariff\n"
      + "    }\n"
      + "    subTotalAmount {\n"
      + "      __typename\n"
      + "      amount\n"
      + "      scale\n"
      + "      unscaledValue\n"
      + "    }\n"
      + "    totalTax {\n"
      + "      __typename\n"
      + "      amount\n"
      + "      scale\n"
      + "      unscaledValue\n"
      + "    }\n"
      + "    totalAmount {\n"
      + "      __typename\n"
      + "      amount\n"
      + "      scale\n"
      + "      unscaledValue\n"
      + "    }\n"
      + "    attributes @include(if: $includeAttributes) {\n"
      + "      __typename\n"
      + "      name\n"
      + "      type\n"
      + "      value\n"
      + "    }\n"
      + "    returnOrderItems(first: $returnOrderItemCount, after: $returnOrderItemCursor) @include(if: $includeReturnOrderItems) {\n"
      + "      __typename\n"
      + "      edges {\n"
      + "        __typename\n"
      + "        node {\n"
      + "          __typename\n"
      + "          id\n"
      + "          ref\n"
      + "          attributes {\n"
      + "            __typename\n"
      + "            name\n"
      + "            type\n"
      + "            value\n"
      + "          }\n"
      + "          returnReason {\n"
      + "            __typename\n"
      + "            value\n"
      + "            label\n"
      + "          }\n"
      + "          returnReasonComment\n"
      + "          returnConditionComment\n"
      + "          returnCondition {\n"
      + "            __typename\n"
      + "            value\n"
      + "            label\n"
      + "          }\n"
      + "          product {\n"
      + "            __typename\n"
      + "            ref\n"
      + "            catalogue {\n"
      + "              __typename\n"
      + "              ref\n"
      + "            }\n"
      + "          }\n"
      + "          unitQuantity {\n"
      + "            __typename\n"
      + "            quantity\n"
      + "            unit\n"
      + "          }\n"
      + "          orderItem {\n"
      + "            __typename\n"
      + "            ref\n"
      + "            order {\n"
      + "              __typename\n"
      + "              ref\n"
      + "            }\n"
      + "          }\n"
      + "          unitTaxType {\n"
      + "            __typename\n"
      + "            country\n"
      + "            group\n"
      + "            tariff\n"
      + "          }\n"
      + "          unitAmount {\n"
      + "            __typename\n"
      + "            amount\n"
      + "            scale\n"
      + "            unscaledValue\n"
      + "          }\n"
      + "          itemAmount {\n"
      + "            __typename\n"
      + "            amount\n"
      + "            scale\n"
      + "            unscaledValue\n"
      + "          }\n"
      + "          itemTaxAmount {\n"
      + "            __typename\n"
      + "            amount\n"
      + "            scale\n"
      + "            unscaledValue\n"
      + "          }\n"
      + "        }\n"
      + "        cursor\n"
      + "      }\n"
      + "      pageInfo {\n"
      + "        __typename\n"
      + "        hasPreviousPage\n"
      + "        hasNextPage\n"
      + "      }\n"
      + "    }\n"
      + "  }\n"
      + "}";

  public static final String QUERY_DOCUMENT = OPERATION_DEFINITION;

  private static final OperationName OPERATION_NAME = new OperationName() {
    @Override
    public String name() {
      return "GetReturnOrderByRef";
    }
  };

  private final GetReturnOrderByRefQuery.Variables variables;

  public GetReturnOrderByRefQuery(@Nonnull String ref, @Nonnull RetailerId retailer,
      boolean includePickupLocation, boolean includeAttributes, boolean includeReturnOrderItems,
      boolean includeReturnFulfilments, @Nullable Integer returnOrderItemCount,
      @Nullable String returnOrderItemCursor) {
    Utils.checkNotNull(ref, "ref == null");
    Utils.checkNotNull(retailer, "retailer == null");
    variables = new GetReturnOrderByRefQuery.Variables(ref, retailer, includePickupLocation, includeAttributes, includeReturnOrderItems, includeReturnFulfilments, returnOrderItemCount, returnOrderItemCursor);
  }

  @Override
  public String operationId() {
    return "3ea86d3a84c01a0b9d97f8131ef86cc762248fb953b788e5a70b594108d3d9da";
  }

  @Override
  public String queryDocument() {
    return QUERY_DOCUMENT;
  }

  @Override
  public GetReturnOrderByRefQuery.Data wrapData(GetReturnOrderByRefQuery.Data data) {
    return data;
  }

  @Override
  public GetReturnOrderByRefQuery.Variables variables() {
    return variables;
  }

  @Override
  public ResponseFieldMapper<GetReturnOrderByRefQuery.Data> responseFieldMapper() {
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

    private @Nonnull RetailerId retailer;

    private boolean includePickupLocation;

    private boolean includeAttributes;

    private boolean includeReturnOrderItems;

    private boolean includeReturnFulfilments;

    private @Nullable Integer returnOrderItemCount;

    private @Nullable String returnOrderItemCursor;

    Builder() {
    }

    public Builder ref(@Nonnull String ref) {
      this.ref = ref;
      return this;
    }

    public Builder retailer(@Nonnull RetailerId retailer) {
      this.retailer = retailer;
      return this;
    }

    public Builder includePickupLocation(boolean includePickupLocation) {
      this.includePickupLocation = includePickupLocation;
      return this;
    }

    public Builder includeAttributes(boolean includeAttributes) {
      this.includeAttributes = includeAttributes;
      return this;
    }

    public Builder includeReturnOrderItems(boolean includeReturnOrderItems) {
      this.includeReturnOrderItems = includeReturnOrderItems;
      return this;
    }

    public Builder includeReturnFulfilments(boolean includeReturnFulfilments) {
      this.includeReturnFulfilments = includeReturnFulfilments;
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

    public GetReturnOrderByRefQuery build() {
      Utils.checkNotNull(ref, "ref == null");
      Utils.checkNotNull(retailer, "retailer == null");
      return new GetReturnOrderByRefQuery(ref, retailer, includePickupLocation, includeAttributes, includeReturnOrderItems, includeReturnFulfilments, returnOrderItemCount, returnOrderItemCursor);
    }
  }

  public static final class Variables extends Operation.Variables {
    private final @Nonnull String ref;

    private final @Nonnull RetailerId retailer;

    private final boolean includePickupLocation;

    private final boolean includeAttributes;

    private final boolean includeReturnOrderItems;

    private final boolean includeReturnFulfilments;

    private final @Nullable Integer returnOrderItemCount;

    private final @Nullable String returnOrderItemCursor;

    private final transient Map<String, Object> valueMap = new LinkedHashMap<>();

    Variables(@Nonnull String ref, @Nonnull RetailerId retailer, boolean includePickupLocation,
        boolean includeAttributes, boolean includeReturnOrderItems,
        boolean includeReturnFulfilments, @Nullable Integer returnOrderItemCount,
        @Nullable String returnOrderItemCursor) {
      this.ref = ref;
      this.retailer = retailer;
      this.includePickupLocation = includePickupLocation;
      this.includeAttributes = includeAttributes;
      this.includeReturnOrderItems = includeReturnOrderItems;
      this.includeReturnFulfilments = includeReturnFulfilments;
      this.returnOrderItemCount = returnOrderItemCount;
      this.returnOrderItemCursor = returnOrderItemCursor;
      this.valueMap.put("ref", ref);
      this.valueMap.put("retailer", retailer);
      this.valueMap.put("includePickupLocation", includePickupLocation);
      this.valueMap.put("includeAttributes", includeAttributes);
      this.valueMap.put("includeReturnOrderItems", includeReturnOrderItems);
      this.valueMap.put("includeReturnFulfilments", includeReturnFulfilments);
      this.valueMap.put("returnOrderItemCount", returnOrderItemCount);
      this.valueMap.put("returnOrderItemCursor", returnOrderItemCursor);
    }

    public @Nonnull String ref() {
      return ref;
    }

    public @Nonnull RetailerId retailer() {
      return retailer;
    }

    public boolean includePickupLocation() {
      return includePickupLocation;
    }

    public boolean includeAttributes() {
      return includeAttributes;
    }

    public boolean includeReturnOrderItems() {
      return includeReturnOrderItems;
    }

    public boolean includeReturnFulfilments() {
      return includeReturnFulfilments;
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
          writer.writeObject("retailer", retailer.marshaller());
          writer.writeBoolean("includePickupLocation", includePickupLocation);
          writer.writeBoolean("includeAttributes", includeAttributes);
          writer.writeBoolean("includeReturnOrderItems", includeReturnOrderItems);
          writer.writeBoolean("includeReturnFulfilments", includeReturnFulfilments);
          writer.writeInt("returnOrderItemCount", returnOrderItemCount);
          writer.writeString("returnOrderItemCursor", returnOrderItemCursor);
        }
      };
    }
  }

  public static class Data implements Operation.Data {
    static final ResponseField[] $responseFields = {
      ResponseField.forObject("returnOrder", "returnOrder", new UnmodifiableMapBuilder<String, Object>(2)
        .put("ref", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "ref")
        .build())
        .put("retailer", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "retailer")
        .build())
      .build(), true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nullable ReturnOrder returnOrder;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Data(@Nullable ReturnOrder returnOrder) {
      this.returnOrder = returnOrder;
    }

    /**
     *  Find a ReturnOrder entity
     */
    public @Nullable ReturnOrder returnOrder() {
      return this.returnOrder;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeObject($responseFields[0], returnOrder != null ? returnOrder.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Data{"
          + "returnOrder=" + returnOrder
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
        return ((this.returnOrder == null) ? (that.returnOrder == null) : this.returnOrder.equals(that.returnOrder));
      }
      return false;
    }

    @Override
    public int hashCode() {
      if (!$hashCodeMemoized) {
        int h = 1;
        h *= 1000003;
        h ^= (returnOrder == null) ? 0 : returnOrder.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.returnOrder = returnOrder;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Data> {
      final ReturnOrder.Mapper returnOrderFieldMapper = new ReturnOrder.Mapper();

      @Override
      public Data map(ResponseReader reader) {
        final ReturnOrder returnOrder = reader.readObject($responseFields[0], new ResponseReader.ObjectReader<ReturnOrder>() {
          @Override
          public ReturnOrder read(ResponseReader reader) {
            return returnOrderFieldMapper.map(reader);
          }
        });
        return new Data(returnOrder);
      }
    }

    public static final class Builder {
      private @Nullable ReturnOrder returnOrder;

      Builder() {
      }

      public Builder returnOrder(@Nullable ReturnOrder returnOrder) {
        this.returnOrder = returnOrder;
        return this;
      }

      public Builder returnOrder(@Nonnull Mutator<ReturnOrder.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ReturnOrder.Builder builder = this.returnOrder != null ? this.returnOrder.toBuilder() : ReturnOrder.builder();
        mutator.accept(builder);
        this.returnOrder = builder.build();
        return this;
      }

      public Data build() {
        return new Data(returnOrder);
      }
    }
  }

  public static class ReturnOrder {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("type", "type", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("status", "status", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("createdOn", "createdOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("updatedOn", "updatedOn", null, true, CustomType.DATETIME, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("order", "order", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("pickupAddress", "pickupAddress", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includePickupLocation", false))),
      ResponseField.forObject("returnOrderFulfilments", "returnOrderFulfilments", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("lodgedLocation", "lodgedLocation", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("destinationLocation", "destinationLocation", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("retailer", "retailer", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("workflow", "workflow", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("customer", "customer", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("currency", "currency", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("defaultTaxType", "defaultTaxType", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("subTotalAmount", "subTotalAmount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("totalTax", "totalTax", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("totalAmount", "totalAmount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeAttributes", false))),
      ResponseField.forObject("returnOrderItems", "returnOrderItems", new UnmodifiableMapBuilder<String, Object>(2)
        .put("after", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "returnOrderItemCursor")
        .build())
        .put("first", new UnmodifiableMapBuilder<String, Object>(2)
          .put("kind", "Variable")
          .put("variableName", "returnOrderItemCount")
        .build())
      .build(), true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeReturnOrderItems", false)))
    };

    final @Nonnull String __typename;

    final @Nonnull String ref;

    final @Nonnull String id;

    final @Nonnull String type;

    final @Nonnull String status;

    final @Nullable Object createdOn;

    final @Nullable Object updatedOn;

    final @Nullable Order order;

    final @Nullable PickupAddress pickupAddress;

    final @Nullable ReturnOrderFulfilments returnOrderFulfilments;

    final @Nullable LodgedLocation lodgedLocation;

    final @Nullable DestinationLocation destinationLocation;

    final @Nullable Retailer retailer;

    final @Nullable Workflow workflow;

    final @Nullable Customer customer;

    final @Nullable Currency currency;

    final @Nullable DefaultTaxType defaultTaxType;

    final @Nullable SubTotalAmount subTotalAmount;

    final @Nullable TotalTax totalTax;

    final @Nullable TotalAmount totalAmount;

    final @Nullable List<Attribute1> attributes;

    final @Nullable ReturnOrderItems returnOrderItems;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ReturnOrder(@Nonnull String __typename, @Nonnull String ref, @Nonnull String id,
        @Nonnull String type, @Nonnull String status, @Nullable Object createdOn,
        @Nullable Object updatedOn, @Nullable Order order, @Nullable PickupAddress pickupAddress,
        @Nullable ReturnOrderFulfilments returnOrderFulfilments,
        @Nullable LodgedLocation lodgedLocation, @Nullable DestinationLocation destinationLocation,
        @Nullable Retailer retailer, @Nullable Workflow workflow, @Nullable Customer customer,
        @Nullable Currency currency, @Nullable DefaultTaxType defaultTaxType,
        @Nullable SubTotalAmount subTotalAmount, @Nullable TotalTax totalTax,
        @Nullable TotalAmount totalAmount, @Nullable List<Attribute1> attributes,
        @Nullable ReturnOrderItems returnOrderItems) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.type = Utils.checkNotNull(type, "type == null");
      this.status = Utils.checkNotNull(status, "status == null");
      this.createdOn = createdOn;
      this.updatedOn = updatedOn;
      this.order = order;
      this.pickupAddress = pickupAddress;
      this.returnOrderFulfilments = returnOrderFulfilments;
      this.lodgedLocation = lodgedLocation;
      this.destinationLocation = destinationLocation;
      this.retailer = retailer;
      this.workflow = workflow;
      this.customer = customer;
      this.currency = currency;
      this.defaultTaxType = defaultTaxType;
      this.subTotalAmount = subTotalAmount;
      this.totalTax = totalTax;
      this.totalAmount = totalAmount;
      this.attributes = attributes;
      this.returnOrderItems = returnOrderItems;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  External reference for `ReturnOrder`. Must be unique.
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    /**
     *  ID of the object
     */
    public @Nonnull String id() {
      return this.id;
    }

    /**
     *  Type of the return order
     */
    public @Nonnull String type() {
      return this.type;
    }

    /**
     *  Status of the `Return Order`
     */
    public @Nonnull String status() {
      return this.status;
    }

    /**
     *  Date and time of creation
     */
    public @Nullable Object createdOn() {
      return this.createdOn;
    }

    /**
     *  Date and time of last update
     */
    public @Nullable Object updatedOn() {
      return this.updatedOn;
    }

    /**
     *  The associated order for this return order.
     */
    public @Nullable Order order() {
      return this.order;
    }

    /**
     *  The pickup address in the cases of return orders that are being picked up by a carrier.
     */
    public @Nullable PickupAddress pickupAddress() {
      return this.pickupAddress;
    }

    /**
     *  The list of associated return fulfilments.
     */
    public @Nullable ReturnOrderFulfilments returnOrderFulfilments() {
      return this.returnOrderFulfilments;
    }

    /**
     *  The lodged location in cases where the return order was directly returned to a store or DC.
     */
    public @Nullable LodgedLocation lodgedLocation() {
      return this.lodgedLocation;
    }

    /**
     *  The destination of the return order items.
     */
    public @Nullable DestinationLocation destinationLocation() {
      return this.destinationLocation;
    }

    /**
     *  The associated retailer for this return order
     */
    public @Nullable Retailer retailer() {
      return this.retailer;
    }

    /**
     *  Workflow version of the return order
     */
    public @Nullable Workflow workflow() {
      return this.workflow;
    }

    /**
     *  The associated customer for this return order.
     */
    public @Nullable Customer customer() {
      return this.customer;
    }

    /**
     *  Reference to the currency type. Generally, the standard ISO-4217 is used.
     */
    public @Nullable Currency currency() {
      return this.currency;
    }

    /**
     *  The default Tax Type for this return order. Individual return order items can override.
     */
    public @Nullable DefaultTaxType defaultTaxType() {
      return this.defaultTaxType;
    }

    /**
     *  The total amount of this return order excluding tax.
     */
    public @Nullable SubTotalAmount subTotalAmount() {
      return this.subTotalAmount;
    }

    /**
     *  The total amount of tax for this return order.
     */
    public @Nullable TotalTax totalTax() {
      return this.totalTax;
    }

    /**
     *  The total amount of this return order including tax
     */
    public @Nullable TotalAmount totalAmount() {
      return this.totalAmount;
    }

    /**
     *  List of attributes.
     */
    public @Nullable List<Attribute1> attributes() {
      return this.attributes;
    }

    /**
     *  The list of associated return order items.
     */
    public @Nullable ReturnOrderItems returnOrderItems() {
      return this.returnOrderItems;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[2], id);
          writer.writeString($responseFields[3], type);
          writer.writeString($responseFields[4], status);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[5], createdOn);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[6], updatedOn);
          writer.writeObject($responseFields[7], order != null ? order.marshaller() : null);
          writer.writeObject($responseFields[8], pickupAddress != null ? pickupAddress.marshaller() : null);
          writer.writeObject($responseFields[9], returnOrderFulfilments != null ? returnOrderFulfilments.marshaller() : null);
          writer.writeObject($responseFields[10], lodgedLocation != null ? lodgedLocation.marshaller() : null);
          writer.writeObject($responseFields[11], destinationLocation != null ? destinationLocation.marshaller() : null);
          writer.writeObject($responseFields[12], retailer != null ? retailer.marshaller() : null);
          writer.writeObject($responseFields[13], workflow != null ? workflow.marshaller() : null);
          writer.writeObject($responseFields[14], customer != null ? customer.marshaller() : null);
          writer.writeObject($responseFields[15], currency != null ? currency.marshaller() : null);
          writer.writeObject($responseFields[16], defaultTaxType != null ? defaultTaxType.marshaller() : null);
          writer.writeObject($responseFields[17], subTotalAmount != null ? subTotalAmount.marshaller() : null);
          writer.writeObject($responseFields[18], totalTax != null ? totalTax.marshaller() : null);
          writer.writeObject($responseFields[19], totalAmount != null ? totalAmount.marshaller() : null);
          writer.writeList($responseFields[20], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute1) value).marshaller());
            }
          });
          writer.writeObject($responseFields[21], returnOrderItems != null ? returnOrderItems.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ReturnOrder{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "id=" + id + ", "
          + "type=" + type + ", "
          + "status=" + status + ", "
          + "createdOn=" + createdOn + ", "
          + "updatedOn=" + updatedOn + ", "
          + "order=" + order + ", "
          + "pickupAddress=" + pickupAddress + ", "
          + "returnOrderFulfilments=" + returnOrderFulfilments + ", "
          + "lodgedLocation=" + lodgedLocation + ", "
          + "destinationLocation=" + destinationLocation + ", "
          + "retailer=" + retailer + ", "
          + "workflow=" + workflow + ", "
          + "customer=" + customer + ", "
          + "currency=" + currency + ", "
          + "defaultTaxType=" + defaultTaxType + ", "
          + "subTotalAmount=" + subTotalAmount + ", "
          + "totalTax=" + totalTax + ", "
          + "totalAmount=" + totalAmount + ", "
          + "attributes=" + attributes + ", "
          + "returnOrderItems=" + returnOrderItems
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
         && this.ref.equals(that.ref)
         && this.id.equals(that.id)
         && this.type.equals(that.type)
         && this.status.equals(that.status)
         && ((this.createdOn == null) ? (that.createdOn == null) : this.createdOn.equals(that.createdOn))
         && ((this.updatedOn == null) ? (that.updatedOn == null) : this.updatedOn.equals(that.updatedOn))
         && ((this.order == null) ? (that.order == null) : this.order.equals(that.order))
         && ((this.pickupAddress == null) ? (that.pickupAddress == null) : this.pickupAddress.equals(that.pickupAddress))
         && ((this.returnOrderFulfilments == null) ? (that.returnOrderFulfilments == null) : this.returnOrderFulfilments.equals(that.returnOrderFulfilments))
         && ((this.lodgedLocation == null) ? (that.lodgedLocation == null) : this.lodgedLocation.equals(that.lodgedLocation))
         && ((this.destinationLocation == null) ? (that.destinationLocation == null) : this.destinationLocation.equals(that.destinationLocation))
         && ((this.retailer == null) ? (that.retailer == null) : this.retailer.equals(that.retailer))
         && ((this.workflow == null) ? (that.workflow == null) : this.workflow.equals(that.workflow))
         && ((this.customer == null) ? (that.customer == null) : this.customer.equals(that.customer))
         && ((this.currency == null) ? (that.currency == null) : this.currency.equals(that.currency))
         && ((this.defaultTaxType == null) ? (that.defaultTaxType == null) : this.defaultTaxType.equals(that.defaultTaxType))
         && ((this.subTotalAmount == null) ? (that.subTotalAmount == null) : this.subTotalAmount.equals(that.subTotalAmount))
         && ((this.totalTax == null) ? (that.totalTax == null) : this.totalTax.equals(that.totalTax))
         && ((this.totalAmount == null) ? (that.totalAmount == null) : this.totalAmount.equals(that.totalAmount))
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes))
         && ((this.returnOrderItems == null) ? (that.returnOrderItems == null) : this.returnOrderItems.equals(that.returnOrderItems));
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
        h ^= type.hashCode();
        h *= 1000003;
        h ^= status.hashCode();
        h *= 1000003;
        h ^= (createdOn == null) ? 0 : createdOn.hashCode();
        h *= 1000003;
        h ^= (updatedOn == null) ? 0 : updatedOn.hashCode();
        h *= 1000003;
        h ^= (order == null) ? 0 : order.hashCode();
        h *= 1000003;
        h ^= (pickupAddress == null) ? 0 : pickupAddress.hashCode();
        h *= 1000003;
        h ^= (returnOrderFulfilments == null) ? 0 : returnOrderFulfilments.hashCode();
        h *= 1000003;
        h ^= (lodgedLocation == null) ? 0 : lodgedLocation.hashCode();
        h *= 1000003;
        h ^= (destinationLocation == null) ? 0 : destinationLocation.hashCode();
        h *= 1000003;
        h ^= (retailer == null) ? 0 : retailer.hashCode();
        h *= 1000003;
        h ^= (workflow == null) ? 0 : workflow.hashCode();
        h *= 1000003;
        h ^= (customer == null) ? 0 : customer.hashCode();
        h *= 1000003;
        h ^= (currency == null) ? 0 : currency.hashCode();
        h *= 1000003;
        h ^= (defaultTaxType == null) ? 0 : defaultTaxType.hashCode();
        h *= 1000003;
        h ^= (subTotalAmount == null) ? 0 : subTotalAmount.hashCode();
        h *= 1000003;
        h ^= (totalTax == null) ? 0 : totalTax.hashCode();
        h *= 1000003;
        h ^= (totalAmount == null) ? 0 : totalAmount.hashCode();
        h *= 1000003;
        h ^= (attributes == null) ? 0 : attributes.hashCode();
        h *= 1000003;
        h ^= (returnOrderItems == null) ? 0 : returnOrderItems.hashCode();
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
      builder.status = status;
      builder.createdOn = createdOn;
      builder.updatedOn = updatedOn;
      builder.order = order;
      builder.pickupAddress = pickupAddress;
      builder.returnOrderFulfilments = returnOrderFulfilments;
      builder.lodgedLocation = lodgedLocation;
      builder.destinationLocation = destinationLocation;
      builder.retailer = retailer;
      builder.workflow = workflow;
      builder.customer = customer;
      builder.currency = currency;
      builder.defaultTaxType = defaultTaxType;
      builder.subTotalAmount = subTotalAmount;
      builder.totalTax = totalTax;
      builder.totalAmount = totalAmount;
      builder.attributes = attributes;
      builder.returnOrderItems = returnOrderItems;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<ReturnOrder> {
      final Order.Mapper orderFieldMapper = new Order.Mapper();

      final PickupAddress.Mapper pickupAddressFieldMapper = new PickupAddress.Mapper();

      final ReturnOrderFulfilments.Mapper returnOrderFulfilmentsFieldMapper = new ReturnOrderFulfilments.Mapper();

      final LodgedLocation.Mapper lodgedLocationFieldMapper = new LodgedLocation.Mapper();

      final DestinationLocation.Mapper destinationLocationFieldMapper = new DestinationLocation.Mapper();

      final Retailer.Mapper retailerFieldMapper = new Retailer.Mapper();

      final Workflow.Mapper workflowFieldMapper = new Workflow.Mapper();

      final Customer.Mapper customerFieldMapper = new Customer.Mapper();

      final Currency.Mapper currencyFieldMapper = new Currency.Mapper();

      final DefaultTaxType.Mapper defaultTaxTypeFieldMapper = new DefaultTaxType.Mapper();

      final SubTotalAmount.Mapper subTotalAmountFieldMapper = new SubTotalAmount.Mapper();

      final TotalTax.Mapper totalTaxFieldMapper = new TotalTax.Mapper();

      final TotalAmount.Mapper totalAmountFieldMapper = new TotalAmount.Mapper();

      final Attribute1.Mapper attribute1FieldMapper = new Attribute1.Mapper();

      final ReturnOrderItems.Mapper returnOrderItemsFieldMapper = new ReturnOrderItems.Mapper();

      @Override
      public ReturnOrder map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[2]);
        final String type = reader.readString($responseFields[3]);
        final String status = reader.readString($responseFields[4]);
        final Object createdOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[5]);
        final Object updatedOn = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[6]);
        final Order order = reader.readObject($responseFields[7], new ResponseReader.ObjectReader<Order>() {
          @Override
          public Order read(ResponseReader reader) {
            return orderFieldMapper.map(reader);
          }
        });
        final PickupAddress pickupAddress = reader.readObject($responseFields[8], new ResponseReader.ObjectReader<PickupAddress>() {
          @Override
          public PickupAddress read(ResponseReader reader) {
            return pickupAddressFieldMapper.map(reader);
          }
        });
        final ReturnOrderFulfilments returnOrderFulfilments = reader.readObject($responseFields[9], new ResponseReader.ObjectReader<ReturnOrderFulfilments>() {
          @Override
          public ReturnOrderFulfilments read(ResponseReader reader) {
            return returnOrderFulfilmentsFieldMapper.map(reader);
          }
        });
        final LodgedLocation lodgedLocation = reader.readObject($responseFields[10], new ResponseReader.ObjectReader<LodgedLocation>() {
          @Override
          public LodgedLocation read(ResponseReader reader) {
            return lodgedLocationFieldMapper.map(reader);
          }
        });
        final DestinationLocation destinationLocation = reader.readObject($responseFields[11], new ResponseReader.ObjectReader<DestinationLocation>() {
          @Override
          public DestinationLocation read(ResponseReader reader) {
            return destinationLocationFieldMapper.map(reader);
          }
        });
        final Retailer retailer = reader.readObject($responseFields[12], new ResponseReader.ObjectReader<Retailer>() {
          @Override
          public Retailer read(ResponseReader reader) {
            return retailerFieldMapper.map(reader);
          }
        });
        final Workflow workflow = reader.readObject($responseFields[13], new ResponseReader.ObjectReader<Workflow>() {
          @Override
          public Workflow read(ResponseReader reader) {
            return workflowFieldMapper.map(reader);
          }
        });
        final Customer customer = reader.readObject($responseFields[14], new ResponseReader.ObjectReader<Customer>() {
          @Override
          public Customer read(ResponseReader reader) {
            return customerFieldMapper.map(reader);
          }
        });
        final Currency currency = reader.readObject($responseFields[15], new ResponseReader.ObjectReader<Currency>() {
          @Override
          public Currency read(ResponseReader reader) {
            return currencyFieldMapper.map(reader);
          }
        });
        final DefaultTaxType defaultTaxType = reader.readObject($responseFields[16], new ResponseReader.ObjectReader<DefaultTaxType>() {
          @Override
          public DefaultTaxType read(ResponseReader reader) {
            return defaultTaxTypeFieldMapper.map(reader);
          }
        });
        final SubTotalAmount subTotalAmount = reader.readObject($responseFields[17], new ResponseReader.ObjectReader<SubTotalAmount>() {
          @Override
          public SubTotalAmount read(ResponseReader reader) {
            return subTotalAmountFieldMapper.map(reader);
          }
        });
        final TotalTax totalTax = reader.readObject($responseFields[18], new ResponseReader.ObjectReader<TotalTax>() {
          @Override
          public TotalTax read(ResponseReader reader) {
            return totalTaxFieldMapper.map(reader);
          }
        });
        final TotalAmount totalAmount = reader.readObject($responseFields[19], new ResponseReader.ObjectReader<TotalAmount>() {
          @Override
          public TotalAmount read(ResponseReader reader) {
            return totalAmountFieldMapper.map(reader);
          }
        });
        final List<Attribute1> attributes = reader.readList($responseFields[20], new ResponseReader.ListReader<Attribute1>() {
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
        final ReturnOrderItems returnOrderItems = reader.readObject($responseFields[21], new ResponseReader.ObjectReader<ReturnOrderItems>() {
          @Override
          public ReturnOrderItems read(ResponseReader reader) {
            return returnOrderItemsFieldMapper.map(reader);
          }
        });
        return new ReturnOrder(__typename, ref, id, type, status, createdOn, updatedOn, order, pickupAddress, returnOrderFulfilments, lodgedLocation, destinationLocation, retailer, workflow, customer, currency, defaultTaxType, subTotalAmount, totalTax, totalAmount, attributes, returnOrderItems);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String ref;

      private @Nonnull String id;

      private @Nonnull String type;

      private @Nonnull String status;

      private @Nullable Object createdOn;

      private @Nullable Object updatedOn;

      private @Nullable Order order;

      private @Nullable PickupAddress pickupAddress;

      private @Nullable ReturnOrderFulfilments returnOrderFulfilments;

      private @Nullable LodgedLocation lodgedLocation;

      private @Nullable DestinationLocation destinationLocation;

      private @Nullable Retailer retailer;

      private @Nullable Workflow workflow;

      private @Nullable Customer customer;

      private @Nullable Currency currency;

      private @Nullable DefaultTaxType defaultTaxType;

      private @Nullable SubTotalAmount subTotalAmount;

      private @Nullable TotalTax totalTax;

      private @Nullable TotalAmount totalAmount;

      private @Nullable List<Attribute1> attributes;

      private @Nullable ReturnOrderItems returnOrderItems;

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

      public Builder type(@Nonnull String type) {
        this.type = type;
        return this;
      }

      public Builder status(@Nonnull String status) {
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

      public Builder order(@Nullable Order order) {
        this.order = order;
        return this;
      }

      public Builder pickupAddress(@Nullable PickupAddress pickupAddress) {
        this.pickupAddress = pickupAddress;
        return this;
      }

      public Builder returnOrderFulfilments(@Nullable ReturnOrderFulfilments returnOrderFulfilments) {
        this.returnOrderFulfilments = returnOrderFulfilments;
        return this;
      }

      public Builder lodgedLocation(@Nullable LodgedLocation lodgedLocation) {
        this.lodgedLocation = lodgedLocation;
        return this;
      }

      public Builder destinationLocation(@Nullable DestinationLocation destinationLocation) {
        this.destinationLocation = destinationLocation;
        return this;
      }

      public Builder retailer(@Nullable Retailer retailer) {
        this.retailer = retailer;
        return this;
      }

      public Builder workflow(@Nullable Workflow workflow) {
        this.workflow = workflow;
        return this;
      }

      public Builder customer(@Nullable Customer customer) {
        this.customer = customer;
        return this;
      }

      public Builder currency(@Nullable Currency currency) {
        this.currency = currency;
        return this;
      }

      public Builder defaultTaxType(@Nullable DefaultTaxType defaultTaxType) {
        this.defaultTaxType = defaultTaxType;
        return this;
      }

      public Builder subTotalAmount(@Nullable SubTotalAmount subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
        return this;
      }

      public Builder totalTax(@Nullable TotalTax totalTax) {
        this.totalTax = totalTax;
        return this;
      }

      public Builder totalAmount(@Nullable TotalAmount totalAmount) {
        this.totalAmount = totalAmount;
        return this;
      }

      public Builder attributes(@Nullable List<Attribute1> attributes) {
        this.attributes = attributes;
        return this;
      }

      public Builder returnOrderItems(@Nullable ReturnOrderItems returnOrderItems) {
        this.returnOrderItems = returnOrderItems;
        return this;
      }

      public Builder order(@Nonnull Mutator<Order.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Order.Builder builder = this.order != null ? this.order.toBuilder() : Order.builder();
        mutator.accept(builder);
        this.order = builder.build();
        return this;
      }

      public Builder pickupAddress(@Nonnull Mutator<PickupAddress.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        PickupAddress.Builder builder = this.pickupAddress != null ? this.pickupAddress.toBuilder() : PickupAddress.builder();
        mutator.accept(builder);
        this.pickupAddress = builder.build();
        return this;
      }

      public Builder returnOrderFulfilments(@Nonnull Mutator<ReturnOrderFulfilments.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ReturnOrderFulfilments.Builder builder = this.returnOrderFulfilments != null ? this.returnOrderFulfilments.toBuilder() : ReturnOrderFulfilments.builder();
        mutator.accept(builder);
        this.returnOrderFulfilments = builder.build();
        return this;
      }

      public Builder lodgedLocation(@Nonnull Mutator<LodgedLocation.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        LodgedLocation.Builder builder = this.lodgedLocation != null ? this.lodgedLocation.toBuilder() : LodgedLocation.builder();
        mutator.accept(builder);
        this.lodgedLocation = builder.build();
        return this;
      }

      public Builder destinationLocation(@Nonnull Mutator<DestinationLocation.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        DestinationLocation.Builder builder = this.destinationLocation != null ? this.destinationLocation.toBuilder() : DestinationLocation.builder();
        mutator.accept(builder);
        this.destinationLocation = builder.build();
        return this;
      }

      public Builder retailer(@Nonnull Mutator<Retailer.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Retailer.Builder builder = this.retailer != null ? this.retailer.toBuilder() : Retailer.builder();
        mutator.accept(builder);
        this.retailer = builder.build();
        return this;
      }

      public Builder workflow(@Nonnull Mutator<Workflow.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Workflow.Builder builder = this.workflow != null ? this.workflow.toBuilder() : Workflow.builder();
        mutator.accept(builder);
        this.workflow = builder.build();
        return this;
      }

      public Builder customer(@Nonnull Mutator<Customer.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Customer.Builder builder = this.customer != null ? this.customer.toBuilder() : Customer.builder();
        mutator.accept(builder);
        this.customer = builder.build();
        return this;
      }

      public Builder currency(@Nonnull Mutator<Currency.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Currency.Builder builder = this.currency != null ? this.currency.toBuilder() : Currency.builder();
        mutator.accept(builder);
        this.currency = builder.build();
        return this;
      }

      public Builder defaultTaxType(@Nonnull Mutator<DefaultTaxType.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        DefaultTaxType.Builder builder = this.defaultTaxType != null ? this.defaultTaxType.toBuilder() : DefaultTaxType.builder();
        mutator.accept(builder);
        this.defaultTaxType = builder.build();
        return this;
      }

      public Builder subTotalAmount(@Nonnull Mutator<SubTotalAmount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        SubTotalAmount.Builder builder = this.subTotalAmount != null ? this.subTotalAmount.toBuilder() : SubTotalAmount.builder();
        mutator.accept(builder);
        this.subTotalAmount = builder.build();
        return this;
      }

      public Builder totalTax(@Nonnull Mutator<TotalTax.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        TotalTax.Builder builder = this.totalTax != null ? this.totalTax.toBuilder() : TotalTax.builder();
        mutator.accept(builder);
        this.totalTax = builder.build();
        return this;
      }

      public Builder totalAmount(@Nonnull Mutator<TotalAmount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        TotalAmount.Builder builder = this.totalAmount != null ? this.totalAmount.toBuilder() : TotalAmount.builder();
        mutator.accept(builder);
        this.totalAmount = builder.build();
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

      public Builder returnOrderItems(@Nonnull Mutator<ReturnOrderItems.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ReturnOrderItems.Builder builder = this.returnOrderItems != null ? this.returnOrderItems.toBuilder() : ReturnOrderItems.builder();
        mutator.accept(builder);
        this.returnOrderItems = builder.build();
        return this;
      }

      public ReturnOrder build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(ref, "ref == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(status, "status == null");
        return new ReturnOrder(__typename, ref, id, type, status, createdOn, updatedOn, order, pickupAddress, returnOrderFulfilments, lodgedLocation, destinationLocation, retailer, workflow, customer, currency, defaultTaxType, subTotalAmount, totalTax, totalAmount, attributes, returnOrderItems);
      }
    }
  }

  public static class Order {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Order(@Nonnull String __typename, @Nullable String ref) {
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
        $toString = "Order{"
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
      if (o instanceof Order) {
        Order that = (Order) o;
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

    public static final class Mapper implements ResponseFieldMapper<Order> {
      @Override
      public Order map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        return new Order(__typename, ref);
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

      public Order build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Order(__typename, ref);
      }
    }
  }

  public static class PickupAddress {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
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

    public PickupAddress(@Nonnull String __typename, @Nullable String companyName,
        @Nullable String name, @Nullable String street, @Nullable String city,
        @Nullable String state, @Nullable String postcode, @Nullable String region,
        @Nullable String country, @Nullable Double latitude, @Nullable Double longitude,
        @Nullable String timeZone) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
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
          writer.writeString($responseFields[1], companyName);
          writer.writeString($responseFields[2], name);
          writer.writeString($responseFields[3], street);
          writer.writeString($responseFields[4], city);
          writer.writeString($responseFields[5], state);
          writer.writeString($responseFields[6], postcode);
          writer.writeString($responseFields[7], region);
          writer.writeString($responseFields[8], country);
          writer.writeDouble($responseFields[9], latitude);
          writer.writeDouble($responseFields[10], longitude);
          writer.writeString($responseFields[11], timeZone);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "PickupAddress{"
          + "__typename=" + __typename + ", "
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
      if (o instanceof PickupAddress) {
        PickupAddress that = (PickupAddress) o;
        return this.__typename.equals(that.__typename)
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

    public static final class Mapper implements ResponseFieldMapper<PickupAddress> {
      @Override
      public PickupAddress map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String companyName = reader.readString($responseFields[1]);
        final String name = reader.readString($responseFields[2]);
        final String street = reader.readString($responseFields[3]);
        final String city = reader.readString($responseFields[4]);
        final String state = reader.readString($responseFields[5]);
        final String postcode = reader.readString($responseFields[6]);
        final String region = reader.readString($responseFields[7]);
        final String country = reader.readString($responseFields[8]);
        final Double latitude = reader.readDouble($responseFields[9]);
        final Double longitude = reader.readDouble($responseFields[10]);
        final String timeZone = reader.readString($responseFields[11]);
        return new PickupAddress(__typename, companyName, name, street, city, state, postcode, region, country, latitude, longitude, timeZone);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

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

      public PickupAddress build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new PickupAddress(__typename, companyName, name, street, city, state, postcode, region, country, latitude, longitude, timeZone);
      }
    }
  }

  public static class ReturnOrderFulfilments {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("edges", "edges", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<Edge> edges;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ReturnOrderFulfilments(@Nonnull String __typename, @Nullable List<Edge> edges) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.edges = edges;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to ReturnFulfilment type node
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
        $toString = "ReturnOrderFulfilments{"
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
      if (o instanceof ReturnOrderFulfilments) {
        ReturnOrderFulfilments that = (ReturnOrderFulfilments) o;
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

    public static final class Mapper implements ResponseFieldMapper<ReturnOrderFulfilments> {
      final Edge.Mapper edgeFieldMapper = new Edge.Mapper();

      @Override
      public ReturnOrderFulfilments map(ResponseReader reader) {
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
        return new ReturnOrderFulfilments(__typename, edges);
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

      public ReturnOrderFulfilments build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new ReturnOrderFulfilments(__typename, edges);
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
     *  The item at the end of the ReturnFulfilment edge
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
      ResponseField.forString("status", "status", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("returnFulfilmentItems", "returnFulfilmentItems", null, true, Arrays.<ResponseField.Condition>asList(ResponseField.Condition.booleanCondition("includeReturnFulfilments", false)))
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nonnull String ref;

    final @Nonnull String status;

    final @Nullable List<Attribute> attributes;

    final @Nullable ReturnFulfilmentItems returnFulfilmentItems;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Node(@Nonnull String __typename, @Nonnull String id, @Nonnull String ref,
        @Nonnull String status, @Nullable List<Attribute> attributes,
        @Nullable ReturnFulfilmentItems returnFulfilmentItems) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.status = Utils.checkNotNull(status, "status == null");
      this.attributes = attributes;
      this.returnFulfilmentItems = returnFulfilmentItems;
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
     *  External reference for `Return Fulfilment`. Must be unique.
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    /**
     *  Status of the `Return Fulfilment`
     */
    public @Nonnull String status() {
      return this.status;
    }

    /**
     *  List of attributes associated with the return fulfilment
     */
    public @Nullable List<Attribute> attributes() {
      return this.attributes;
    }

    /**
     *  List of return fulfilment item associated with the return fulfilment
     */
    public @Nullable ReturnFulfilmentItems returnFulfilmentItems() {
      return this.returnFulfilmentItems;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeString($responseFields[3], status);
          writer.writeList($responseFields[4], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute) value).marshaller());
            }
          });
          writer.writeObject($responseFields[5], returnFulfilmentItems != null ? returnFulfilmentItems.marshaller() : null);
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
          + "attributes=" + attributes + ", "
          + "returnFulfilmentItems=" + returnFulfilmentItems
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
         && this.status.equals(that.status)
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes))
         && ((this.returnFulfilmentItems == null) ? (that.returnFulfilmentItems == null) : this.returnFulfilmentItems.equals(that.returnFulfilmentItems));
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
        h ^= status.hashCode();
        h *= 1000003;
        h ^= (attributes == null) ? 0 : attributes.hashCode();
        h *= 1000003;
        h ^= (returnFulfilmentItems == null) ? 0 : returnFulfilmentItems.hashCode();
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
      builder.attributes = attributes;
      builder.returnFulfilmentItems = returnFulfilmentItems;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Node> {
      final Attribute.Mapper attributeFieldMapper = new Attribute.Mapper();

      final ReturnFulfilmentItems.Mapper returnFulfilmentItemsFieldMapper = new ReturnFulfilmentItems.Mapper();

      @Override
      public Node map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final String status = reader.readString($responseFields[3]);
        final List<Attribute> attributes = reader.readList($responseFields[4], new ResponseReader.ListReader<Attribute>() {
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
        final ReturnFulfilmentItems returnFulfilmentItems = reader.readObject($responseFields[5], new ResponseReader.ObjectReader<ReturnFulfilmentItems>() {
          @Override
          public ReturnFulfilmentItems read(ResponseReader reader) {
            return returnFulfilmentItemsFieldMapper.map(reader);
          }
        });
        return new Node(__typename, id, ref, status, attributes, returnFulfilmentItems);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nonnull String ref;

      private @Nonnull String status;

      private @Nullable List<Attribute> attributes;

      private @Nullable ReturnFulfilmentItems returnFulfilmentItems;

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

      public Builder status(@Nonnull String status) {
        this.status = status;
        return this;
      }

      public Builder attributes(@Nullable List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
      }

      public Builder returnFulfilmentItems(@Nullable ReturnFulfilmentItems returnFulfilmentItems) {
        this.returnFulfilmentItems = returnFulfilmentItems;
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

      public Builder returnFulfilmentItems(@Nonnull Mutator<ReturnFulfilmentItems.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ReturnFulfilmentItems.Builder builder = this.returnFulfilmentItems != null ? this.returnFulfilmentItems.toBuilder() : ReturnFulfilmentItems.builder();
        mutator.accept(builder);
        this.returnFulfilmentItems = builder.build();
        return this;
      }

      public Node build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(ref, "ref == null");
        Utils.checkNotNull(status, "status == null");
        return new Node(__typename, id, ref, status, attributes, returnFulfilmentItems);
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

  public static class ReturnFulfilmentItems {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("edges", "edges", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<Edge1> edges;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ReturnFulfilmentItems(@Nonnull String __typename, @Nullable List<Edge1> edges) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.edges = edges;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to ReturnFulfilmentItem type node
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
        $toString = "ReturnFulfilmentItems{"
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
      if (o instanceof ReturnFulfilmentItems) {
        ReturnFulfilmentItems that = (ReturnFulfilmentItems) o;
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

    public static final class Mapper implements ResponseFieldMapper<ReturnFulfilmentItems> {
      final Edge1.Mapper edge1FieldMapper = new Edge1.Mapper();

      @Override
      public ReturnFulfilmentItems map(ResponseReader reader) {
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
        return new ReturnFulfilmentItems(__typename, edges);
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

      public ReturnFulfilmentItems build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new ReturnFulfilmentItems(__typename, edges);
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
     *  The item at the end of the ReturnFulfilmentItem edge
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
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("returnOrderItem", "returnOrderItem", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("unitQuantity", "unitQuantity", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nonnull String ref;

    final @Nullable ReturnOrderItem returnOrderItem;

    final @Nullable UnitQuantity unitQuantity;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Node1(@Nonnull String __typename, @Nonnull String id, @Nonnull String ref,
        @Nullable ReturnOrderItem returnOrderItem, @Nullable UnitQuantity unitQuantity) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.returnOrderItem = returnOrderItem;
      this.unitQuantity = unitQuantity;
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
     *  External reference for `Return Order`. Must be unique.
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    /**
     * Return order item associated with the fulfilment item
     */
    public @Nullable ReturnOrderItem returnOrderItem() {
      return this.returnOrderItem;
    }

    /**
     *  Quantity of return fulfilment item
     */
    public @Nullable UnitQuantity unitQuantity() {
      return this.unitQuantity;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeObject($responseFields[3], returnOrderItem != null ? returnOrderItem.marshaller() : null);
          writer.writeObject($responseFields[4], unitQuantity != null ? unitQuantity.marshaller() : null);
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
          + "returnOrderItem=" + returnOrderItem + ", "
          + "unitQuantity=" + unitQuantity
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
         && this.ref.equals(that.ref)
         && ((this.returnOrderItem == null) ? (that.returnOrderItem == null) : this.returnOrderItem.equals(that.returnOrderItem))
         && ((this.unitQuantity == null) ? (that.unitQuantity == null) : this.unitQuantity.equals(that.unitQuantity));
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
        h ^= (returnOrderItem == null) ? 0 : returnOrderItem.hashCode();
        h *= 1000003;
        h ^= (unitQuantity == null) ? 0 : unitQuantity.hashCode();
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
      builder.returnOrderItem = returnOrderItem;
      builder.unitQuantity = unitQuantity;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Node1> {
      final ReturnOrderItem.Mapper returnOrderItemFieldMapper = new ReturnOrderItem.Mapper();

      final UnitQuantity.Mapper unitQuantityFieldMapper = new UnitQuantity.Mapper();

      @Override
      public Node1 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final ReturnOrderItem returnOrderItem = reader.readObject($responseFields[3], new ResponseReader.ObjectReader<ReturnOrderItem>() {
          @Override
          public ReturnOrderItem read(ResponseReader reader) {
            return returnOrderItemFieldMapper.map(reader);
          }
        });
        final UnitQuantity unitQuantity = reader.readObject($responseFields[4], new ResponseReader.ObjectReader<UnitQuantity>() {
          @Override
          public UnitQuantity read(ResponseReader reader) {
            return unitQuantityFieldMapper.map(reader);
          }
        });
        return new Node1(__typename, id, ref, returnOrderItem, unitQuantity);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nonnull String ref;

      private @Nullable ReturnOrderItem returnOrderItem;

      private @Nullable UnitQuantity unitQuantity;

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

      public Builder returnOrderItem(@Nullable ReturnOrderItem returnOrderItem) {
        this.returnOrderItem = returnOrderItem;
        return this;
      }

      public Builder unitQuantity(@Nullable UnitQuantity unitQuantity) {
        this.unitQuantity = unitQuantity;
        return this;
      }

      public Builder returnOrderItem(@Nonnull Mutator<ReturnOrderItem.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ReturnOrderItem.Builder builder = this.returnOrderItem != null ? this.returnOrderItem.toBuilder() : ReturnOrderItem.builder();
        mutator.accept(builder);
        this.returnOrderItem = builder.build();
        return this;
      }

      public Builder unitQuantity(@Nonnull Mutator<UnitQuantity.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        UnitQuantity.Builder builder = this.unitQuantity != null ? this.unitQuantity.toBuilder() : UnitQuantity.builder();
        mutator.accept(builder);
        this.unitQuantity = builder.build();
        return this;
      }

      public Node1 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(ref, "ref == null");
        return new Node1(__typename, id, ref, returnOrderItem, unitQuantity);
      }
    }
  }

  public static class ReturnOrderItem {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ReturnOrderItem(@Nonnull String __typename, @Nonnull String ref) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  External reference for `Return Order`. Must be unique.
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
        $toString = "ReturnOrderItem{"
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
      if (o instanceof ReturnOrderItem) {
        ReturnOrderItem that = (ReturnOrderItem) o;
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

    public static final class Mapper implements ResponseFieldMapper<ReturnOrderItem> {
      @Override
      public ReturnOrderItem map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        return new ReturnOrderItem(__typename, ref);
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

      public ReturnOrderItem build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(ref, "ref == null");
        return new ReturnOrderItem(__typename, ref);
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

  public static class LodgedLocation {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public LodgedLocation(@Nonnull String __typename, @Nullable String ref) {
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
        $toString = "LodgedLocation{"
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
      if (o instanceof LodgedLocation) {
        LodgedLocation that = (LodgedLocation) o;
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

    public static final class Mapper implements ResponseFieldMapper<LodgedLocation> {
      @Override
      public LodgedLocation map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        return new LodgedLocation(__typename, ref);
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

      public LodgedLocation build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new LodgedLocation(__typename, ref);
      }
    }
  }

  public static class DestinationLocation {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public DestinationLocation(@Nonnull String __typename, @Nullable String ref) {
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
        $toString = "DestinationLocation{"
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
      if (o instanceof DestinationLocation) {
        DestinationLocation that = (DestinationLocation) o;
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

    public static final class Mapper implements ResponseFieldMapper<DestinationLocation> {
      @Override
      public DestinationLocation map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        return new DestinationLocation(__typename, ref);
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

      public DestinationLocation build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new DestinationLocation(__typename, ref);
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

  public static class Workflow {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("version", "version", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    final @Nullable Integer version;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Workflow(@Nonnull String __typename, @Nullable String ref, @Nullable Integer version) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.ref = ref;
      this.version = version;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The reference used for workflow identification. This is defined by a combination of the entity name and the type, in the format [EntityName]::[Type]. For example, an Order of type CC will have the workflowRef "ORDER::CC".<br/>
     */
    public @Nullable String ref() {
      return this.ref;
    }

    /**
     *  The version of the workflow assigned to the entity and used for workflow identification. It comprises a major version and minor version number.<br/>
     */
    public @Nullable Integer version() {
      return this.version;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], ref);
          writer.writeInt($responseFields[2], version);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Workflow{"
          + "__typename=" + __typename + ", "
          + "ref=" + ref + ", "
          + "version=" + version
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Workflow) {
        Workflow that = (Workflow) o;
        return this.__typename.equals(that.__typename)
         && ((this.ref == null) ? (that.ref == null) : this.ref.equals(that.ref))
         && ((this.version == null) ? (that.version == null) : this.version.equals(that.version));
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
        h ^= (version == null) ? 0 : version.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.ref = ref;
      builder.version = version;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Workflow> {
      @Override
      public Workflow map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final Integer version = reader.readInt($responseFields[2]);
        return new Workflow(__typename, ref, version);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      private @Nullable Integer version;

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

      public Builder version(@Nullable Integer version) {
        this.version = version;
        return this;
      }

      public Workflow build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Workflow(__typename, ref, version);
      }
    }
  }

  public static class Customer {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Customer(@Nonnull String __typename, @Nullable String ref) {
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
        $toString = "Customer{"
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
      if (o instanceof Customer) {
        Customer that = (Customer) o;
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

    public static final class Mapper implements ResponseFieldMapper<Customer> {
      @Override
      public Customer map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        return new Customer(__typename, ref);
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

      public Customer build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Customer(__typename, ref);
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

  public static class DefaultTaxType {
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

    public DefaultTaxType(@Nonnull String __typename, @Nonnull String country,
        @Nonnull String group, @Nullable String tariff) {
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
        $toString = "DefaultTaxType{"
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
      if (o instanceof DefaultTaxType) {
        DefaultTaxType that = (DefaultTaxType) o;
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

    public static final class Mapper implements ResponseFieldMapper<DefaultTaxType> {
      @Override
      public DefaultTaxType map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String country = reader.readString($responseFields[1]);
        final String group = reader.readString($responseFields[2]);
        final String tariff = reader.readString($responseFields[3]);
        return new DefaultTaxType(__typename, country, group, tariff);
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

      public DefaultTaxType build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(country, "country == null");
        Utils.checkNotNull(group, "group == null");
        return new DefaultTaxType(__typename, country, group, tariff);
      }
    }
  }

  public static class SubTotalAmount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("scale", "scale", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("unscaledValue", "unscaledValue", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    final @Nullable Integer scale;

    final @Nullable Integer unscaledValue;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public SubTotalAmount(@Nonnull String __typename, @Nullable Double amount,
        @Nullable Integer scale, @Nullable Integer unscaledValue) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
      this.scale = scale;
      this.unscaledValue = unscaledValue;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public @Nullable Integer scale() {
      return this.scale;
    }

    public @Nullable Integer unscaledValue() {
      return this.unscaledValue;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
          writer.writeInt($responseFields[2], scale);
          writer.writeInt($responseFields[3], unscaledValue);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "SubTotalAmount{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount + ", "
          + "scale=" + scale + ", "
          + "unscaledValue=" + unscaledValue
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
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount))
         && ((this.scale == null) ? (that.scale == null) : this.scale.equals(that.scale))
         && ((this.unscaledValue == null) ? (that.unscaledValue == null) : this.unscaledValue.equals(that.unscaledValue));
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
        h *= 1000003;
        h ^= (scale == null) ? 0 : scale.hashCode();
        h *= 1000003;
        h ^= (unscaledValue == null) ? 0 : unscaledValue.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      builder.scale = scale;
      builder.unscaledValue = unscaledValue;
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
        final Integer scale = reader.readInt($responseFields[2]);
        final Integer unscaledValue = reader.readInt($responseFields[3]);
        return new SubTotalAmount(__typename, amount, scale, unscaledValue);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      private @Nullable Integer scale;

      private @Nullable Integer unscaledValue;

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

      public Builder scale(@Nullable Integer scale) {
        this.scale = scale;
        return this;
      }

      public Builder unscaledValue(@Nullable Integer unscaledValue) {
        this.unscaledValue = unscaledValue;
        return this;
      }

      public SubTotalAmount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new SubTotalAmount(__typename, amount, scale, unscaledValue);
      }
    }
  }

  public static class TotalTax {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("scale", "scale", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("unscaledValue", "unscaledValue", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    final @Nullable Integer scale;

    final @Nullable Integer unscaledValue;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public TotalTax(@Nonnull String __typename, @Nullable Double amount, @Nullable Integer scale,
        @Nullable Integer unscaledValue) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
      this.scale = scale;
      this.unscaledValue = unscaledValue;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public @Nullable Integer scale() {
      return this.scale;
    }

    public @Nullable Integer unscaledValue() {
      return this.unscaledValue;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
          writer.writeInt($responseFields[2], scale);
          writer.writeInt($responseFields[3], unscaledValue);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "TotalTax{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount + ", "
          + "scale=" + scale + ", "
          + "unscaledValue=" + unscaledValue
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof TotalTax) {
        TotalTax that = (TotalTax) o;
        return this.__typename.equals(that.__typename)
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount))
         && ((this.scale == null) ? (that.scale == null) : this.scale.equals(that.scale))
         && ((this.unscaledValue == null) ? (that.unscaledValue == null) : this.unscaledValue.equals(that.unscaledValue));
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
        h *= 1000003;
        h ^= (scale == null) ? 0 : scale.hashCode();
        h *= 1000003;
        h ^= (unscaledValue == null) ? 0 : unscaledValue.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      builder.scale = scale;
      builder.unscaledValue = unscaledValue;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<TotalTax> {
      @Override
      public TotalTax map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Double amount = reader.readDouble($responseFields[1]);
        final Integer scale = reader.readInt($responseFields[2]);
        final Integer unscaledValue = reader.readInt($responseFields[3]);
        return new TotalTax(__typename, amount, scale, unscaledValue);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      private @Nullable Integer scale;

      private @Nullable Integer unscaledValue;

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

      public Builder scale(@Nullable Integer scale) {
        this.scale = scale;
        return this;
      }

      public Builder unscaledValue(@Nullable Integer unscaledValue) {
        this.unscaledValue = unscaledValue;
        return this;
      }

      public TotalTax build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new TotalTax(__typename, amount, scale, unscaledValue);
      }
    }
  }

  public static class TotalAmount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("scale", "scale", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("unscaledValue", "unscaledValue", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    final @Nullable Integer scale;

    final @Nullable Integer unscaledValue;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public TotalAmount(@Nonnull String __typename, @Nullable Double amount, @Nullable Integer scale,
        @Nullable Integer unscaledValue) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
      this.scale = scale;
      this.unscaledValue = unscaledValue;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public @Nullable Integer scale() {
      return this.scale;
    }

    public @Nullable Integer unscaledValue() {
      return this.unscaledValue;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
          writer.writeInt($responseFields[2], scale);
          writer.writeInt($responseFields[3], unscaledValue);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "TotalAmount{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount + ", "
          + "scale=" + scale + ", "
          + "unscaledValue=" + unscaledValue
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
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount))
         && ((this.scale == null) ? (that.scale == null) : this.scale.equals(that.scale))
         && ((this.unscaledValue == null) ? (that.unscaledValue == null) : this.unscaledValue.equals(that.unscaledValue));
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
        h *= 1000003;
        h ^= (scale == null) ? 0 : scale.hashCode();
        h *= 1000003;
        h ^= (unscaledValue == null) ? 0 : unscaledValue.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      builder.scale = scale;
      builder.unscaledValue = unscaledValue;
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
        final Integer scale = reader.readInt($responseFields[2]);
        final Integer unscaledValue = reader.readInt($responseFields[3]);
        return new TotalAmount(__typename, amount, scale, unscaledValue);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      private @Nullable Integer scale;

      private @Nullable Integer unscaledValue;

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

      public Builder scale(@Nullable Integer scale) {
        this.scale = scale;
        return this;
      }

      public Builder unscaledValue(@Nullable Integer unscaledValue) {
        this.unscaledValue = unscaledValue;
        return this;
      }

      public TotalAmount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new TotalAmount(__typename, amount, scale, unscaledValue);
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

  public static class ReturnOrderItems {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("edges", "edges", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("pageInfo", "pageInfo", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable List<Edge2> edges;

    final @Nullable PageInfo pageInfo;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ReturnOrderItems(@Nonnull String __typename, @Nullable List<Edge2> edges,
        @Nullable PageInfo pageInfo) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.edges = edges;
      this.pageInfo = pageInfo;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  A list of edges that links to ReturnOrderItem type node
     */
    public @Nullable List<Edge2> edges() {
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
              listItemWriter.writeObject(((Edge2) value).marshaller());
            }
          });
          writer.writeObject($responseFields[2], pageInfo != null ? pageInfo.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ReturnOrderItems{"
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
      if (o instanceof ReturnOrderItems) {
        ReturnOrderItems that = (ReturnOrderItems) o;
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

    public static final class Mapper implements ResponseFieldMapper<ReturnOrderItems> {
      final Edge2.Mapper edge2FieldMapper = new Edge2.Mapper();

      final PageInfo.Mapper pageInfoFieldMapper = new PageInfo.Mapper();

      @Override
      public ReturnOrderItems map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final List<Edge2> edges = reader.readList($responseFields[1], new ResponseReader.ListReader<Edge2>() {
          @Override
          public Edge2 read(ResponseReader.ListItemReader listItemReader) {
            return listItemReader.readObject(new ResponseReader.ObjectReader<Edge2>() {
              @Override
              public Edge2 read(ResponseReader reader) {
                return edge2FieldMapper.map(reader);
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
        return new ReturnOrderItems(__typename, edges, pageInfo);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable List<Edge2> edges;

      private @Nullable PageInfo pageInfo;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder edges(@Nullable List<Edge2> edges) {
        this.edges = edges;
        return this;
      }

      public Builder pageInfo(@Nullable PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
      }

      public Builder edges(@Nonnull Mutator<List<Edge2.Builder>> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        List<Edge2.Builder> builders = new ArrayList<>();
        if (this.edges != null) {
          for (Edge2 item : this.edges) {
            builders.add(item != null ? item.toBuilder() : null);
          }
        }
        mutator.accept(builders);
        List<Edge2> edges = new ArrayList<>();
        for (Edge2.Builder item : builders) {
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

      public ReturnOrderItems build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new ReturnOrderItems(__typename, edges, pageInfo);
      }
    }
  }

  public static class Edge2 {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("node", "node", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("cursor", "cursor", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Node2 node;

    final @Nullable String cursor;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Edge2(@Nonnull String __typename, @Nullable Node2 node, @Nullable String cursor) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.node = node;
      this.cursor = cursor;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  The item at the end of the ReturnOrderItem edge
     */
    public @Nullable Node2 node() {
      return this.node;
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
          writer.writeObject($responseFields[1], node != null ? node.marshaller() : null);
          writer.writeString($responseFields[2], cursor);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Edge2{"
          + "__typename=" + __typename + ", "
          + "node=" + node + ", "
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
      if (o instanceof Edge2) {
        Edge2 that = (Edge2) o;
        return this.__typename.equals(that.__typename)
         && ((this.node == null) ? (that.node == null) : this.node.equals(that.node))
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
        h ^= (node == null) ? 0 : node.hashCode();
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
      builder.node = node;
      builder.cursor = cursor;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Edge2> {
      final Node2.Mapper node2FieldMapper = new Node2.Mapper();

      @Override
      public Edge2 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Node2 node = reader.readObject($responseFields[1], new ResponseReader.ObjectReader<Node2>() {
          @Override
          public Node2 read(ResponseReader reader) {
            return node2FieldMapper.map(reader);
          }
        });
        final String cursor = reader.readString($responseFields[2]);
        return new Edge2(__typename, node, cursor);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Node2 node;

      private @Nullable String cursor;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder node(@Nullable Node2 node) {
        this.node = node;
        return this;
      }

      public Builder cursor(@Nullable String cursor) {
        this.cursor = cursor;
        return this;
      }

      public Builder node(@Nonnull Mutator<Node2.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Node2.Builder builder = this.node != null ? this.node.toBuilder() : Node2.builder();
        mutator.accept(builder);
        this.node = builder.build();
        return this;
      }

      public Edge2 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Edge2(__typename, node, cursor);
      }
    }
  }

  public static class Node2 {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forCustomType("id", "id", null, false, CustomType.ID, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forList("attributes", "attributes", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("returnReason", "returnReason", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("returnReasonComment", "returnReasonComment", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("returnConditionComment", "returnConditionComment", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("returnCondition", "returnCondition", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("product", "product", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("unitQuantity", "unitQuantity", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("orderItem", "orderItem", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("unitTaxType", "unitTaxType", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("unitAmount", "unitAmount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("itemAmount", "itemAmount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forObject("itemTaxAmount", "itemTaxAmount", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nonnull String id;

    final @Nonnull String ref;

    final @Nullable List<Attribute2> attributes;

    final @Nullable ReturnReason returnReason;

    final @Nullable String returnReasonComment;

    final @Nullable String returnConditionComment;

    final @Nullable ReturnCondition returnCondition;

    final @Nullable Product product;

    final @Nullable UnitQuantity1 unitQuantity;

    final @Nullable OrderItem orderItem;

    final @Nullable UnitTaxType unitTaxType;

    final @Nullable UnitAmount unitAmount;

    final @Nullable ItemAmount itemAmount;

    final @Nullable ItemTaxAmount itemTaxAmount;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Node2(@Nonnull String __typename, @Nonnull String id, @Nonnull String ref,
        @Nullable List<Attribute2> attributes, @Nullable ReturnReason returnReason,
        @Nullable String returnReasonComment, @Nullable String returnConditionComment,
        @Nullable ReturnCondition returnCondition, @Nullable Product product,
        @Nullable UnitQuantity1 unitQuantity, @Nullable OrderItem orderItem,
        @Nullable UnitTaxType unitTaxType, @Nullable UnitAmount unitAmount,
        @Nullable ItemAmount itemAmount, @Nullable ItemTaxAmount itemTaxAmount) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.id = Utils.checkNotNull(id, "id == null");
      this.ref = Utils.checkNotNull(ref, "ref == null");
      this.attributes = attributes;
      this.returnReason = returnReason;
      this.returnReasonComment = returnReasonComment;
      this.returnConditionComment = returnConditionComment;
      this.returnCondition = returnCondition;
      this.product = product;
      this.unitQuantity = unitQuantity;
      this.orderItem = orderItem;
      this.unitTaxType = unitTaxType;
      this.unitAmount = unitAmount;
      this.itemAmount = itemAmount;
      this.itemTaxAmount = itemTaxAmount;
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
     *  External reference for `Return Order`. Must be unique.
     */
    public @Nonnull String ref() {
      return this.ref;
    }

    /**
     *  List of attributes associated with the return order
     */
    public @Nullable List<Attribute2> attributes() {
      return this.attributes;
    }

    /**
     *  The reason for returning this return order item.
     */
    public @Nullable ReturnReason returnReason() {
      return this.returnReason;
    }

    /**
     *  An optional comment. Required if the reason code required further information such as 'Other'.
     */
    public @Nullable String returnReasonComment() {
      return this.returnReasonComment;
    }

    /**
     *  An optional comment. Required if the condition code required further information such as 'Other'.
     */
    public @Nullable String returnConditionComment() {
      return this.returnConditionComment;
    }

    /**
     *  The condition in which the return item was received.
     */
    public @Nullable ReturnCondition returnCondition() {
      return this.returnCondition;
    }

    /**
     *  Associated product with the return order
     */
    public @Nullable Product product() {
      return this.product;
    }

    /**
     *  Unit Quantity
     */
    public @Nullable UnitQuantity1 unitQuantity() {
      return this.unitQuantity;
    }

    /**
     *  Associated order item of the return order
     */
    public @Nullable OrderItem orderItem() {
      return this.orderItem;
    }

    /**
     *  Unit TaxType
     */
    public @Nullable UnitTaxType unitTaxType() {
      return this.unitTaxType;
    }

    /**
     *  Unit Amount
     */
    public @Nullable UnitAmount unitAmount() {
      return this.unitAmount;
    }

    /**
     *  Item TaxAmount
     */
    public @Nullable ItemAmount itemAmount() {
      return this.itemAmount;
    }

    /**
     *  Item Amount
     */
    public @Nullable ItemTaxAmount itemTaxAmount() {
      return this.itemTaxAmount;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeCustom((ResponseField.CustomTypeField) $responseFields[1], id);
          writer.writeString($responseFields[2], ref);
          writer.writeList($responseFields[3], attributes, new ResponseWriter.ListWriter() {
            @Override
            public void write(Object value, ResponseWriter.ListItemWriter listItemWriter) {
              listItemWriter.writeObject(((Attribute2) value).marshaller());
            }
          });
          writer.writeObject($responseFields[4], returnReason != null ? returnReason.marshaller() : null);
          writer.writeString($responseFields[5], returnReasonComment);
          writer.writeString($responseFields[6], returnConditionComment);
          writer.writeObject($responseFields[7], returnCondition != null ? returnCondition.marshaller() : null);
          writer.writeObject($responseFields[8], product != null ? product.marshaller() : null);
          writer.writeObject($responseFields[9], unitQuantity != null ? unitQuantity.marshaller() : null);
          writer.writeObject($responseFields[10], orderItem != null ? orderItem.marshaller() : null);
          writer.writeObject($responseFields[11], unitTaxType != null ? unitTaxType.marshaller() : null);
          writer.writeObject($responseFields[12], unitAmount != null ? unitAmount.marshaller() : null);
          writer.writeObject($responseFields[13], itemAmount != null ? itemAmount.marshaller() : null);
          writer.writeObject($responseFields[14], itemTaxAmount != null ? itemTaxAmount.marshaller() : null);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "Node2{"
          + "__typename=" + __typename + ", "
          + "id=" + id + ", "
          + "ref=" + ref + ", "
          + "attributes=" + attributes + ", "
          + "returnReason=" + returnReason + ", "
          + "returnReasonComment=" + returnReasonComment + ", "
          + "returnConditionComment=" + returnConditionComment + ", "
          + "returnCondition=" + returnCondition + ", "
          + "product=" + product + ", "
          + "unitQuantity=" + unitQuantity + ", "
          + "orderItem=" + orderItem + ", "
          + "unitTaxType=" + unitTaxType + ", "
          + "unitAmount=" + unitAmount + ", "
          + "itemAmount=" + itemAmount + ", "
          + "itemTaxAmount=" + itemTaxAmount
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof Node2) {
        Node2 that = (Node2) o;
        return this.__typename.equals(that.__typename)
         && this.id.equals(that.id)
         && this.ref.equals(that.ref)
         && ((this.attributes == null) ? (that.attributes == null) : this.attributes.equals(that.attributes))
         && ((this.returnReason == null) ? (that.returnReason == null) : this.returnReason.equals(that.returnReason))
         && ((this.returnReasonComment == null) ? (that.returnReasonComment == null) : this.returnReasonComment.equals(that.returnReasonComment))
         && ((this.returnConditionComment == null) ? (that.returnConditionComment == null) : this.returnConditionComment.equals(that.returnConditionComment))
         && ((this.returnCondition == null) ? (that.returnCondition == null) : this.returnCondition.equals(that.returnCondition))
         && ((this.product == null) ? (that.product == null) : this.product.equals(that.product))
         && ((this.unitQuantity == null) ? (that.unitQuantity == null) : this.unitQuantity.equals(that.unitQuantity))
         && ((this.orderItem == null) ? (that.orderItem == null) : this.orderItem.equals(that.orderItem))
         && ((this.unitTaxType == null) ? (that.unitTaxType == null) : this.unitTaxType.equals(that.unitTaxType))
         && ((this.unitAmount == null) ? (that.unitAmount == null) : this.unitAmount.equals(that.unitAmount))
         && ((this.itemAmount == null) ? (that.itemAmount == null) : this.itemAmount.equals(that.itemAmount))
         && ((this.itemTaxAmount == null) ? (that.itemTaxAmount == null) : this.itemTaxAmount.equals(that.itemTaxAmount));
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
        h ^= (attributes == null) ? 0 : attributes.hashCode();
        h *= 1000003;
        h ^= (returnReason == null) ? 0 : returnReason.hashCode();
        h *= 1000003;
        h ^= (returnReasonComment == null) ? 0 : returnReasonComment.hashCode();
        h *= 1000003;
        h ^= (returnConditionComment == null) ? 0 : returnConditionComment.hashCode();
        h *= 1000003;
        h ^= (returnCondition == null) ? 0 : returnCondition.hashCode();
        h *= 1000003;
        h ^= (product == null) ? 0 : product.hashCode();
        h *= 1000003;
        h ^= (unitQuantity == null) ? 0 : unitQuantity.hashCode();
        h *= 1000003;
        h ^= (orderItem == null) ? 0 : orderItem.hashCode();
        h *= 1000003;
        h ^= (unitTaxType == null) ? 0 : unitTaxType.hashCode();
        h *= 1000003;
        h ^= (unitAmount == null) ? 0 : unitAmount.hashCode();
        h *= 1000003;
        h ^= (itemAmount == null) ? 0 : itemAmount.hashCode();
        h *= 1000003;
        h ^= (itemTaxAmount == null) ? 0 : itemTaxAmount.hashCode();
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
      builder.attributes = attributes;
      builder.returnReason = returnReason;
      builder.returnReasonComment = returnReasonComment;
      builder.returnConditionComment = returnConditionComment;
      builder.returnCondition = returnCondition;
      builder.product = product;
      builder.unitQuantity = unitQuantity;
      builder.orderItem = orderItem;
      builder.unitTaxType = unitTaxType;
      builder.unitAmount = unitAmount;
      builder.itemAmount = itemAmount;
      builder.itemTaxAmount = itemTaxAmount;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<Node2> {
      final Attribute2.Mapper attribute2FieldMapper = new Attribute2.Mapper();

      final ReturnReason.Mapper returnReasonFieldMapper = new ReturnReason.Mapper();

      final ReturnCondition.Mapper returnConditionFieldMapper = new ReturnCondition.Mapper();

      final Product.Mapper productFieldMapper = new Product.Mapper();

      final UnitQuantity1.Mapper unitQuantity1FieldMapper = new UnitQuantity1.Mapper();

      final OrderItem.Mapper orderItemFieldMapper = new OrderItem.Mapper();

      final UnitTaxType.Mapper unitTaxTypeFieldMapper = new UnitTaxType.Mapper();

      final UnitAmount.Mapper unitAmountFieldMapper = new UnitAmount.Mapper();

      final ItemAmount.Mapper itemAmountFieldMapper = new ItemAmount.Mapper();

      final ItemTaxAmount.Mapper itemTaxAmountFieldMapper = new ItemTaxAmount.Mapper();

      @Override
      public Node2 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String id = reader.readCustomType((ResponseField.CustomTypeField) $responseFields[1]);
        final String ref = reader.readString($responseFields[2]);
        final List<Attribute2> attributes = reader.readList($responseFields[3], new ResponseReader.ListReader<Attribute2>() {
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
        final ReturnReason returnReason = reader.readObject($responseFields[4], new ResponseReader.ObjectReader<ReturnReason>() {
          @Override
          public ReturnReason read(ResponseReader reader) {
            return returnReasonFieldMapper.map(reader);
          }
        });
        final String returnReasonComment = reader.readString($responseFields[5]);
        final String returnConditionComment = reader.readString($responseFields[6]);
        final ReturnCondition returnCondition = reader.readObject($responseFields[7], new ResponseReader.ObjectReader<ReturnCondition>() {
          @Override
          public ReturnCondition read(ResponseReader reader) {
            return returnConditionFieldMapper.map(reader);
          }
        });
        final Product product = reader.readObject($responseFields[8], new ResponseReader.ObjectReader<Product>() {
          @Override
          public Product read(ResponseReader reader) {
            return productFieldMapper.map(reader);
          }
        });
        final UnitQuantity1 unitQuantity = reader.readObject($responseFields[9], new ResponseReader.ObjectReader<UnitQuantity1>() {
          @Override
          public UnitQuantity1 read(ResponseReader reader) {
            return unitQuantity1FieldMapper.map(reader);
          }
        });
        final OrderItem orderItem = reader.readObject($responseFields[10], new ResponseReader.ObjectReader<OrderItem>() {
          @Override
          public OrderItem read(ResponseReader reader) {
            return orderItemFieldMapper.map(reader);
          }
        });
        final UnitTaxType unitTaxType = reader.readObject($responseFields[11], new ResponseReader.ObjectReader<UnitTaxType>() {
          @Override
          public UnitTaxType read(ResponseReader reader) {
            return unitTaxTypeFieldMapper.map(reader);
          }
        });
        final UnitAmount unitAmount = reader.readObject($responseFields[12], new ResponseReader.ObjectReader<UnitAmount>() {
          @Override
          public UnitAmount read(ResponseReader reader) {
            return unitAmountFieldMapper.map(reader);
          }
        });
        final ItemAmount itemAmount = reader.readObject($responseFields[13], new ResponseReader.ObjectReader<ItemAmount>() {
          @Override
          public ItemAmount read(ResponseReader reader) {
            return itemAmountFieldMapper.map(reader);
          }
        });
        final ItemTaxAmount itemTaxAmount = reader.readObject($responseFields[14], new ResponseReader.ObjectReader<ItemTaxAmount>() {
          @Override
          public ItemTaxAmount read(ResponseReader reader) {
            return itemTaxAmountFieldMapper.map(reader);
          }
        });
        return new Node2(__typename, id, ref, attributes, returnReason, returnReasonComment, returnConditionComment, returnCondition, product, unitQuantity, orderItem, unitTaxType, unitAmount, itemAmount, itemTaxAmount);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nonnull String id;

      private @Nonnull String ref;

      private @Nullable List<Attribute2> attributes;

      private @Nullable ReturnReason returnReason;

      private @Nullable String returnReasonComment;

      private @Nullable String returnConditionComment;

      private @Nullable ReturnCondition returnCondition;

      private @Nullable Product product;

      private @Nullable UnitQuantity1 unitQuantity;

      private @Nullable OrderItem orderItem;

      private @Nullable UnitTaxType unitTaxType;

      private @Nullable UnitAmount unitAmount;

      private @Nullable ItemAmount itemAmount;

      private @Nullable ItemTaxAmount itemTaxAmount;

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

      public Builder attributes(@Nullable List<Attribute2> attributes) {
        this.attributes = attributes;
        return this;
      }

      public Builder returnReason(@Nullable ReturnReason returnReason) {
        this.returnReason = returnReason;
        return this;
      }

      public Builder returnReasonComment(@Nullable String returnReasonComment) {
        this.returnReasonComment = returnReasonComment;
        return this;
      }

      public Builder returnConditionComment(@Nullable String returnConditionComment) {
        this.returnConditionComment = returnConditionComment;
        return this;
      }

      public Builder returnCondition(@Nullable ReturnCondition returnCondition) {
        this.returnCondition = returnCondition;
        return this;
      }

      public Builder product(@Nullable Product product) {
        this.product = product;
        return this;
      }

      public Builder unitQuantity(@Nullable UnitQuantity1 unitQuantity) {
        this.unitQuantity = unitQuantity;
        return this;
      }

      public Builder orderItem(@Nullable OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
      }

      public Builder unitTaxType(@Nullable UnitTaxType unitTaxType) {
        this.unitTaxType = unitTaxType;
        return this;
      }

      public Builder unitAmount(@Nullable UnitAmount unitAmount) {
        this.unitAmount = unitAmount;
        return this;
      }

      public Builder itemAmount(@Nullable ItemAmount itemAmount) {
        this.itemAmount = itemAmount;
        return this;
      }

      public Builder itemTaxAmount(@Nullable ItemTaxAmount itemTaxAmount) {
        this.itemTaxAmount = itemTaxAmount;
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

      public Builder returnReason(@Nonnull Mutator<ReturnReason.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ReturnReason.Builder builder = this.returnReason != null ? this.returnReason.toBuilder() : ReturnReason.builder();
        mutator.accept(builder);
        this.returnReason = builder.build();
        return this;
      }

      public Builder returnCondition(@Nonnull Mutator<ReturnCondition.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ReturnCondition.Builder builder = this.returnCondition != null ? this.returnCondition.toBuilder() : ReturnCondition.builder();
        mutator.accept(builder);
        this.returnCondition = builder.build();
        return this;
      }

      public Builder product(@Nonnull Mutator<Product.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Product.Builder builder = this.product != null ? this.product.toBuilder() : Product.builder();
        mutator.accept(builder);
        this.product = builder.build();
        return this;
      }

      public Builder unitQuantity(@Nonnull Mutator<UnitQuantity1.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        UnitQuantity1.Builder builder = this.unitQuantity != null ? this.unitQuantity.toBuilder() : UnitQuantity1.builder();
        mutator.accept(builder);
        this.unitQuantity = builder.build();
        return this;
      }

      public Builder orderItem(@Nonnull Mutator<OrderItem.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        OrderItem.Builder builder = this.orderItem != null ? this.orderItem.toBuilder() : OrderItem.builder();
        mutator.accept(builder);
        this.orderItem = builder.build();
        return this;
      }

      public Builder unitTaxType(@Nonnull Mutator<UnitTaxType.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        UnitTaxType.Builder builder = this.unitTaxType != null ? this.unitTaxType.toBuilder() : UnitTaxType.builder();
        mutator.accept(builder);
        this.unitTaxType = builder.build();
        return this;
      }

      public Builder unitAmount(@Nonnull Mutator<UnitAmount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        UnitAmount.Builder builder = this.unitAmount != null ? this.unitAmount.toBuilder() : UnitAmount.builder();
        mutator.accept(builder);
        this.unitAmount = builder.build();
        return this;
      }

      public Builder itemAmount(@Nonnull Mutator<ItemAmount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ItemAmount.Builder builder = this.itemAmount != null ? this.itemAmount.toBuilder() : ItemAmount.builder();
        mutator.accept(builder);
        this.itemAmount = builder.build();
        return this;
      }

      public Builder itemTaxAmount(@Nonnull Mutator<ItemTaxAmount.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        ItemTaxAmount.Builder builder = this.itemTaxAmount != null ? this.itemTaxAmount.toBuilder() : ItemTaxAmount.builder();
        mutator.accept(builder);
        this.itemTaxAmount = builder.build();
        return this;
      }

      public Node2 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        Utils.checkNotNull(id, "id == null");
        Utils.checkNotNull(ref, "ref == null");
        return new Node2(__typename, id, ref, attributes, returnReason, returnReasonComment, returnConditionComment, returnCondition, product, unitQuantity, orderItem, unitTaxType, unitAmount, itemAmount, itemTaxAmount);
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

  public static class ReturnReason {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("value", "value", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("label", "label", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String value;

    final @Nullable String label;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ReturnReason(@Nonnull String __typename, @Nullable String value,
        @Nullable String label) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.value = value;
      this.label = label;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String value() {
      return this.value;
    }

    public @Nullable String label() {
      return this.label;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], value);
          writer.writeString($responseFields[2], label);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ReturnReason{"
          + "__typename=" + __typename + ", "
          + "value=" + value + ", "
          + "label=" + label
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof ReturnReason) {
        ReturnReason that = (ReturnReason) o;
        return this.__typename.equals(that.__typename)
         && ((this.value == null) ? (that.value == null) : this.value.equals(that.value))
         && ((this.label == null) ? (that.label == null) : this.label.equals(that.label));
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
        h *= 1000003;
        h ^= (label == null) ? 0 : label.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.value = value;
      builder.label = label;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<ReturnReason> {
      @Override
      public ReturnReason map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String value = reader.readString($responseFields[1]);
        final String label = reader.readString($responseFields[2]);
        return new ReturnReason(__typename, value, label);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String value;

      private @Nullable String label;

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

      public Builder label(@Nullable String label) {
        this.label = label;
        return this;
      }

      public ReturnReason build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new ReturnReason(__typename, value, label);
      }
    }
  }

  public static class ReturnCondition {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("value", "value", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("label", "label", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String value;

    final @Nullable String label;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ReturnCondition(@Nonnull String __typename, @Nullable String value,
        @Nullable String label) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.value = value;
      this.label = label;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable String value() {
      return this.value;
    }

    public @Nullable String label() {
      return this.label;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeString($responseFields[1], value);
          writer.writeString($responseFields[2], label);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ReturnCondition{"
          + "__typename=" + __typename + ", "
          + "value=" + value + ", "
          + "label=" + label
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof ReturnCondition) {
        ReturnCondition that = (ReturnCondition) o;
        return this.__typename.equals(that.__typename)
         && ((this.value == null) ? (that.value == null) : this.value.equals(that.value))
         && ((this.label == null) ? (that.label == null) : this.label.equals(that.label));
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
        h *= 1000003;
        h ^= (label == null) ? 0 : label.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.value = value;
      builder.label = label;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<ReturnCondition> {
      @Override
      public ReturnCondition map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String value = reader.readString($responseFields[1]);
        final String label = reader.readString($responseFields[2]);
        return new ReturnCondition(__typename, value, label);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String value;

      private @Nullable String label;

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

      public Builder label(@Nullable String label) {
        this.label = label;
        return this;
      }

      public ReturnCondition build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new ReturnCondition(__typename, value, label);
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

  public static class UnitQuantity1 {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("quantity", "quantity", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("unit", "unit", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Integer quantity;

    final @Nullable String unit;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public UnitQuantity1(@Nonnull String __typename, @Nullable Integer quantity,
        @Nullable String unit) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.quantity = quantity;
      this.unit = unit;
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

    /**
     *  The unit associated.
     */
    public @Nullable String unit() {
      return this.unit;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeInt($responseFields[1], quantity);
          writer.writeString($responseFields[2], unit);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "UnitQuantity1{"
          + "__typename=" + __typename + ", "
          + "quantity=" + quantity + ", "
          + "unit=" + unit
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof UnitQuantity1) {
        UnitQuantity1 that = (UnitQuantity1) o;
        return this.__typename.equals(that.__typename)
         && ((this.quantity == null) ? (that.quantity == null) : this.quantity.equals(that.quantity))
         && ((this.unit == null) ? (that.unit == null) : this.unit.equals(that.unit));
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
        h *= 1000003;
        h ^= (unit == null) ? 0 : unit.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.quantity = quantity;
      builder.unit = unit;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<UnitQuantity1> {
      @Override
      public UnitQuantity1 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Integer quantity = reader.readInt($responseFields[1]);
        final String unit = reader.readString($responseFields[2]);
        return new UnitQuantity1(__typename, quantity, unit);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Integer quantity;

      private @Nullable String unit;

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

      public Builder unit(@Nullable String unit) {
        this.unit = unit;
        return this;
      }

      public UnitQuantity1 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new UnitQuantity1(__typename, quantity, unit);
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

    final @Nullable Order1 order;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public OrderItem(@Nonnull String __typename, @Nullable String ref, @Nullable Order1 order) {
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

    public @Nullable Order1 order() {
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
      final Order1.Mapper order1FieldMapper = new Order1.Mapper();

      @Override
      public OrderItem map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        final Order1 order = reader.readObject($responseFields[2], new ResponseReader.ObjectReader<Order1>() {
          @Override
          public Order1 read(ResponseReader reader) {
            return order1FieldMapper.map(reader);
          }
        });
        return new OrderItem(__typename, ref, order);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable String ref;

      private @Nullable Order1 order;

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

      public Builder order(@Nullable Order1 order) {
        this.order = order;
        return this;
      }

      public Builder order(@Nonnull Mutator<Order1.Builder> mutator) {
        Utils.checkNotNull(mutator, "mutator == null");
        Order1.Builder builder = this.order != null ? this.order.toBuilder() : Order1.builder();
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

  public static class Order1 {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forString("ref", "ref", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable String ref;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public Order1(@Nonnull String __typename, @Nullable String ref) {
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
        $toString = "Order1{"
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
      if (o instanceof Order1) {
        Order1 that = (Order1) o;
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

    public static final class Mapper implements ResponseFieldMapper<Order1> {
      @Override
      public Order1 map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final String ref = reader.readString($responseFields[1]);
        return new Order1(__typename, ref);
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

      public Order1 build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new Order1(__typename, ref);
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

  public static class UnitAmount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("scale", "scale", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("unscaledValue", "unscaledValue", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    final @Nullable Integer scale;

    final @Nullable Integer unscaledValue;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public UnitAmount(@Nonnull String __typename, @Nullable Double amount, @Nullable Integer scale,
        @Nullable Integer unscaledValue) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
      this.scale = scale;
      this.unscaledValue = unscaledValue;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public @Nullable Integer scale() {
      return this.scale;
    }

    public @Nullable Integer unscaledValue() {
      return this.unscaledValue;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
          writer.writeInt($responseFields[2], scale);
          writer.writeInt($responseFields[3], unscaledValue);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "UnitAmount{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount + ", "
          + "scale=" + scale + ", "
          + "unscaledValue=" + unscaledValue
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
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount))
         && ((this.scale == null) ? (that.scale == null) : this.scale.equals(that.scale))
         && ((this.unscaledValue == null) ? (that.unscaledValue == null) : this.unscaledValue.equals(that.unscaledValue));
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
        h *= 1000003;
        h ^= (scale == null) ? 0 : scale.hashCode();
        h *= 1000003;
        h ^= (unscaledValue == null) ? 0 : unscaledValue.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      builder.scale = scale;
      builder.unscaledValue = unscaledValue;
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
        final Integer scale = reader.readInt($responseFields[2]);
        final Integer unscaledValue = reader.readInt($responseFields[3]);
        return new UnitAmount(__typename, amount, scale, unscaledValue);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      private @Nullable Integer scale;

      private @Nullable Integer unscaledValue;

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

      public Builder scale(@Nullable Integer scale) {
        this.scale = scale;
        return this;
      }

      public Builder unscaledValue(@Nullable Integer unscaledValue) {
        this.unscaledValue = unscaledValue;
        return this;
      }

      public UnitAmount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new UnitAmount(__typename, amount, scale, unscaledValue);
      }
    }
  }

  public static class ItemAmount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("scale", "scale", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("unscaledValue", "unscaledValue", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    final @Nullable Integer scale;

    final @Nullable Integer unscaledValue;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ItemAmount(@Nonnull String __typename, @Nullable Double amount, @Nullable Integer scale,
        @Nullable Integer unscaledValue) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
      this.scale = scale;
      this.unscaledValue = unscaledValue;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public @Nullable Integer scale() {
      return this.scale;
    }

    public @Nullable Integer unscaledValue() {
      return this.unscaledValue;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
          writer.writeInt($responseFields[2], scale);
          writer.writeInt($responseFields[3], unscaledValue);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ItemAmount{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount + ", "
          + "scale=" + scale + ", "
          + "unscaledValue=" + unscaledValue
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof ItemAmount) {
        ItemAmount that = (ItemAmount) o;
        return this.__typename.equals(that.__typename)
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount))
         && ((this.scale == null) ? (that.scale == null) : this.scale.equals(that.scale))
         && ((this.unscaledValue == null) ? (that.unscaledValue == null) : this.unscaledValue.equals(that.unscaledValue));
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
        h *= 1000003;
        h ^= (scale == null) ? 0 : scale.hashCode();
        h *= 1000003;
        h ^= (unscaledValue == null) ? 0 : unscaledValue.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      builder.scale = scale;
      builder.unscaledValue = unscaledValue;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<ItemAmount> {
      @Override
      public ItemAmount map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Double amount = reader.readDouble($responseFields[1]);
        final Integer scale = reader.readInt($responseFields[2]);
        final Integer unscaledValue = reader.readInt($responseFields[3]);
        return new ItemAmount(__typename, amount, scale, unscaledValue);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      private @Nullable Integer scale;

      private @Nullable Integer unscaledValue;

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

      public Builder scale(@Nullable Integer scale) {
        this.scale = scale;
        return this;
      }

      public Builder unscaledValue(@Nullable Integer unscaledValue) {
        this.unscaledValue = unscaledValue;
        return this;
      }

      public ItemAmount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new ItemAmount(__typename, amount, scale, unscaledValue);
      }
    }
  }

  public static class ItemTaxAmount {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forDouble("amount", "amount", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("scale", "scale", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forInt("unscaledValue", "unscaledValue", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Double amount;

    final @Nullable Integer scale;

    final @Nullable Integer unscaledValue;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public ItemTaxAmount(@Nonnull String __typename, @Nullable Double amount,
        @Nullable Integer scale, @Nullable Integer unscaledValue) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.amount = amount;
      this.scale = scale;
      this.unscaledValue = unscaledValue;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    public @Nullable Double amount() {
      return this.amount;
    }

    public @Nullable Integer scale() {
      return this.scale;
    }

    public @Nullable Integer unscaledValue() {
      return this.unscaledValue;
    }

    public ResponseFieldMarshaller marshaller() {
      return new ResponseFieldMarshaller() {
        @Override
        public void marshal(ResponseWriter writer) {
          writer.writeString($responseFields[0], __typename);
          writer.writeDouble($responseFields[1], amount);
          writer.writeInt($responseFields[2], scale);
          writer.writeInt($responseFields[3], unscaledValue);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "ItemTaxAmount{"
          + "__typename=" + __typename + ", "
          + "amount=" + amount + ", "
          + "scale=" + scale + ", "
          + "unscaledValue=" + unscaledValue
          + "}";
      }
      return $toString;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (o instanceof ItemTaxAmount) {
        ItemTaxAmount that = (ItemTaxAmount) o;
        return this.__typename.equals(that.__typename)
         && ((this.amount == null) ? (that.amount == null) : this.amount.equals(that.amount))
         && ((this.scale == null) ? (that.scale == null) : this.scale.equals(that.scale))
         && ((this.unscaledValue == null) ? (that.unscaledValue == null) : this.unscaledValue.equals(that.unscaledValue));
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
        h *= 1000003;
        h ^= (scale == null) ? 0 : scale.hashCode();
        h *= 1000003;
        h ^= (unscaledValue == null) ? 0 : unscaledValue.hashCode();
        $hashCode = h;
        $hashCodeMemoized = true;
      }
      return $hashCode;
    }

    public Builder toBuilder() {
      Builder builder = new Builder();
      builder.__typename = __typename;
      builder.amount = amount;
      builder.scale = scale;
      builder.unscaledValue = unscaledValue;
      return builder;
    }

    public static Builder builder() {
      return new Builder();
    }

    public static final class Mapper implements ResponseFieldMapper<ItemTaxAmount> {
      @Override
      public ItemTaxAmount map(ResponseReader reader) {
        final String __typename = reader.readString($responseFields[0]);
        final Double amount = reader.readDouble($responseFields[1]);
        final Integer scale = reader.readInt($responseFields[2]);
        final Integer unscaledValue = reader.readInt($responseFields[3]);
        return new ItemTaxAmount(__typename, amount, scale, unscaledValue);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Double amount;

      private @Nullable Integer scale;

      private @Nullable Integer unscaledValue;

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

      public Builder scale(@Nullable Integer scale) {
        this.scale = scale;
        return this;
      }

      public Builder unscaledValue(@Nullable Integer unscaledValue) {
        this.unscaledValue = unscaledValue;
        return this;
      }

      public ItemTaxAmount build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new ItemTaxAmount(__typename, amount, scale, unscaledValue);
      }
    }
  }

  public static class PageInfo {
    static final ResponseField[] $responseFields = {
      ResponseField.forString("__typename", "__typename", null, false, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forBoolean("hasPreviousPage", "hasPreviousPage", null, true, Collections.<ResponseField.Condition>emptyList()),
      ResponseField.forBoolean("hasNextPage", "hasNextPage", null, true, Collections.<ResponseField.Condition>emptyList())
    };

    final @Nonnull String __typename;

    final @Nullable Boolean hasPreviousPage;

    final @Nullable Boolean hasNextPage;

    private volatile String $toString;

    private volatile int $hashCode;

    private volatile boolean $hashCodeMemoized;

    public PageInfo(@Nonnull String __typename, @Nullable Boolean hasPreviousPage,
        @Nullable Boolean hasNextPage) {
      this.__typename = Utils.checkNotNull(__typename, "__typename == null");
      this.hasPreviousPage = hasPreviousPage;
      this.hasNextPage = hasNextPage;
    }

    public @Nonnull String __typename() {
      return this.__typename;
    }

    /**
     *  true if there are one or more pages of items before the current page
     */
    public @Nullable Boolean hasPreviousPage() {
      return this.hasPreviousPage;
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
          writer.writeBoolean($responseFields[1], hasPreviousPage);
          writer.writeBoolean($responseFields[2], hasNextPage);
        }
      };
    }

    @Override
    public String toString() {
      if ($toString == null) {
        $toString = "PageInfo{"
          + "__typename=" + __typename + ", "
          + "hasPreviousPage=" + hasPreviousPage + ", "
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
         && ((this.hasPreviousPage == null) ? (that.hasPreviousPage == null) : this.hasPreviousPage.equals(that.hasPreviousPage))
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
        h ^= (hasPreviousPage == null) ? 0 : hasPreviousPage.hashCode();
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
      builder.hasPreviousPage = hasPreviousPage;
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
        final Boolean hasPreviousPage = reader.readBoolean($responseFields[1]);
        final Boolean hasNextPage = reader.readBoolean($responseFields[2]);
        return new PageInfo(__typename, hasPreviousPage, hasNextPage);
      }
    }

    public static final class Builder {
      private @Nonnull String __typename;

      private @Nullable Boolean hasPreviousPage;

      private @Nullable Boolean hasNextPage;

      Builder() {
      }

      public Builder __typename(@Nonnull String __typename) {
        this.__typename = __typename;
        return this;
      }

      public Builder hasPreviousPage(@Nullable Boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
        return this;
      }

      public Builder hasNextPage(@Nullable Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
        return this;
      }

      public PageInfo build() {
        Utils.checkNotNull(__typename, "__typename == null");
        return new PageInfo(__typename, hasPreviousPage, hasNextPage);
      }
    }
  }
}
