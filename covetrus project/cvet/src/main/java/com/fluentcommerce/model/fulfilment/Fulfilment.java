package com.fluentcommerce.model.fulfilment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fluentcommerce.model.BaseEntity;
import com.fluentcommerce.model.address.Address;
import com.fluentcommerce.model.customer.Customer;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = Fulfilment.Builder.class)
public class Fulfilment{

    String id;
    String ref;
    String status;
    Date createdOn;
    Date updatedOn;
    String deliveryType;
    String type;
    String eta;
    Date expiryTime;
    Order order;
    Address fromAddress;
    Address toAddress;
    List<FulfilmentItem> items;
    @Nullable
    Customer user;
    @Nullable
    Map<String, Object> attributes;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {}

    public static Fulfilment from(GetFulfilmentByIdQuery.Data data) {
        Fulfilment.Builder builder = Fulfilment.builder()
                .id(data.fulfilmentById().id())
                .ref(data.fulfilmentById().ref())
                .status(data.fulfilmentById().status())
                .deliveryType(data.fulfilmentById().deliveryType())
                .type(data.fulfilmentById().type())
                .eta(data.fulfilmentById().eta())
                .expiryTime((Date)data.fulfilmentById().expiryTime())
                .createdOn((Date)data.fulfilmentById().createdOn())
                .updatedOn((Date)data.fulfilmentById().updatedOn())
                .fromAddress(Address.fulfilmentFromAddress(data))
                .toAddress(Address.fulfilmentToAddress(data))
                .items(mapItems(data.fulfilmentById().items()))
                .user(Customer.fromFulfilment(data))
                .attributes(mapAttributes(data)
                );
        return builder.build();
    }

    private static List<FulfilmentItem> mapItems(GetFulfilmentByIdQuery.Items items) {
        if (items == null) return new ArrayList<>();
        if (CollectionUtils.isEmpty(items.edges())) return new ArrayList<>();
        return items.edges()
                .stream()
                .map(GetFulfilmentByIdQuery.Edge::item)
                .filter(Objects::nonNull)
                .map(FulfilmentItem::from)
                .collect(Collectors.toList());
    }
    private static Map<String, Object> mapAttributes(GetFulfilmentByIdQuery.Data data) {
        if (data == null || CollectionUtils.isEmpty(data.fulfilmentById().attributes())) return new HashMap<>();
        return data.fulfilmentById().attributes().stream()
                .collect(Collectors.toMap(GetFulfilmentByIdQuery.Attribute1::name, GetFulfilmentByIdQuery.Attribute1::value));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Fulfilment that = (Fulfilment) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
