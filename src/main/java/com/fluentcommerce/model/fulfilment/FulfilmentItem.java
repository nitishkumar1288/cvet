package com.fluentcommerce.model.fulfilment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fluentcommerce.model.BaseEntity;
import lombok.Builder;
import lombok.Value;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import java.util.Objects;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = FulfilmentItem.Builder.class)
public class FulfilmentItem{

    String ref;
    String status;
    Integer requestedQuantity;
    Integer filledQuantity;
    Integer rejectedQuantity;


    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {}

    public static FulfilmentItem from(GetFulfilmentByIdQuery.Item item) {
        FulfilmentItem.Builder builder = FulfilmentItem.builder()
                .ref(item.ref())
                .status(item.status())
                .requestedQuantity(item.requestedQuantity())
                .filledQuantity(item.filledQuantity())
                .rejectedQuantity(item.rejectedQuantity()) ;
        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FulfilmentItem that = (FulfilmentItem) o;
        return ref.equals(that.ref);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ref);
    }

}
