package com.fluentcommerce.model.product;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.BaseEntity;
import com.fluentretail.api.model.attribute.Attribute;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Objects;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = VariantProduct.Builder.class)
public class VariantProduct{

    String id;
    String ref;
    String type;
    String status;
    String gtin;
    String name;
    String summary;
    List<String> categoryRefs;
    String standardProductRef;
    List<Attribute> attributes;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {}

    public static VariantProduct from(GetOrderByIdQuery.AsVariantProduct variantProduct) {
        return VariantProduct.builder()
            .id(variantProduct.id())
            .ref(variantProduct.ref())
            .type(variantProduct.type())
            .status(variantProduct.status())
        .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VariantProduct that = (VariantProduct) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
