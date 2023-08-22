package com.fluentcommerce.model.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.BaseEntity;
import lombok.Builder;
import lombok.Value;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = Customer.Builder.class)
public class Customer{

    private String id;
    private String ref;

    @Nullable
    private String title;

    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    @Nullable
    private String username;

    @Nullable
    private String primaryEmail;

    @Nullable
    private String primaryPhone;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {}

    public static Customer from(GetOrderByIdQuery.Customer customer) {
        return Customer.builder()
            .id(customer.id())
            .ref(customer.ref())
            .title(customer.title())
            .firstName(customer.firstName())
            .lastName(customer.lastName())
            .username(customer.username())
            .primaryEmail(customer.primaryEmail())
            .primaryPhone(customer.primaryPhone())
            .build();
    }

    public static Customer fromFulfilment(GetFulfilmentByIdQuery.Data data) {
        return Customer.builder()
                .ref(data.fulfilmentById().user().ref())
                .title(data.fulfilmentById().user().title())
                .firstName(data.fulfilmentById().user().firstName())
                .lastName(data.fulfilmentById().user().lastName())
                .username(data.fulfilmentById().user().username())
                .primaryEmail(data.fulfilmentById().user().primaryEmail())
                .primaryPhone(data.fulfilmentById().user().primaryPhone())
                .build();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer that = (Customer) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
