package com.fluentcommerce.model.order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.BaseEntity;
import com.fluentcommerce.model.product.VariantProduct;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = OrderItem.Builder.class)
public class OrderItem{

    String id;
    String ref;
    @Nullable
    Integer quantity;
    @Nullable
    String productRef;
    @Nullable
    Map<String, Object> attributes;

    VariantProduct product;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {}

    public int getQuantity() {
        return quantity == null ? 0 : quantity;
    }

    public static OrderItem from(GetOrderByIdQuery.OrderItemNode orderItemNode) {
        Builder builder = OrderItem.builder()
            .id(orderItemNode.id())
            .ref(orderItemNode.ref())
            .quantity(orderItemNode.quantity())
             .attributes(mapAttributes(orderItemNode.orderItemAttributes()));

        if (orderItemNode.product().asVariantProduct() != null) {
            builder.productRef(orderItemNode.product().asVariantProduct().ref());
            builder.product(VariantProduct.from(orderItemNode.product().asVariantProduct()));
        }
        return builder.build();
    }

    private static Map<String, Object> mapAttributes(List<GetOrderByIdQuery.OrderItemAttribute> orderItemAttributes) {
        if (orderItemAttributes == null || CollectionUtils.isEmpty(orderItemAttributes)) return new HashMap<>();
        return orderItemAttributes.stream()
                .collect(Collectors.toMap(GetOrderByIdQuery.OrderItemAttribute::name, GetOrderByIdQuery.OrderItemAttribute::value));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderItem that = (OrderItem) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
