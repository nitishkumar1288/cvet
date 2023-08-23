package com.fluentcommerce.model.order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.BaseEntity;
import com.fluentcommerce.model.address.Address;
import com.fluentcommerce.model.customer.Customer;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = Order.Builder.class)
public class Order {

    String id;
    String ref;
    String type;
    String status;
    Date createdOn;

    List<OrderItem> items;

    @Nullable
    String pickupLocationRef;
    @Nullable
    Address deliveryAddress;
    @Nullable
    String deliveryType;
    @Nullable
    Map<String, Object> attributes;
    @Nullable
    Customer customer;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {}

    public static Order from(GetOrderByIdQuery.OrderById orderById) {
        Builder builder = Order.builder()
            .id(orderById.id())
            .ref(orderById.ref())
            .status(orderById.status())
            .type(orderById.type())
            .createdOn((Date)orderById.createdOn())
            .attributes(mapAttributes(orderById))
            .items(mapItems(orderById.orderItems()))
            .customer(mapCustomer(orderById));
        if (orderById.fulfilmentChoice() != null) {
            mapFulfilmentChoice(builder, orderById.fulfilmentChoice());
        }
        return builder.build();
    }

    private static Customer mapCustomer(GetOrderByIdQuery.OrderById orderById) {
        if (orderById == null || orderById.customer() == null) return null;
        return Customer.from(orderById.customer());
    }

    private static List<OrderItem> mapItems(GetOrderByIdQuery.OrderItems items) {
        if (items == null) return new ArrayList<>();
        if (CollectionUtils.isEmpty(items.orderItemEdge())) return new ArrayList<>();
        return items.orderItemEdge()
            .stream()
            .map(GetOrderByIdQuery.OrderItemEdge::orderItemNode)
            .filter(Objects::nonNull)
            .map(OrderItem::from)
            .collect(Collectors.toList());
    }

    private static void mapFulfilmentChoice(Builder order, GetOrderByIdQuery.FulfilmentChoice fulfilmentChoice) {
        order.deliveryType(fulfilmentChoice.deliveryType());
        order.pickupLocationRef(fulfilmentChoice.pickupLocationRef() != null ? fulfilmentChoice.pickupLocationRef() : null);
        if (fulfilmentChoice.deliveryAddress() != null) {
            order.deliveryAddress(Address.getOrderDeliveryAddress(fulfilmentChoice.deliveryAddress()));
        }
    }

    private static Map<String, Object> mapAttributes(GetOrderByIdQuery.OrderById orderById) {
        if (orderById == null || CollectionUtils.isEmpty(orderById.attributes())) return new HashMap<>();
        return orderById.attributes().stream()
                .collect(Collectors.toMap(GetOrderByIdQuery.Attribute::name, GetOrderByIdQuery.Attribute::value));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order that = (Order) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
