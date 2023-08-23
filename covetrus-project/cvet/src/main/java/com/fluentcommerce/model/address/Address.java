package com.fluentcommerce.model.address;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.Objects;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = Address.Builder.class)
public class Address{

    String id;
    String type;
    Date createdOn;
    Date updatedOn;
    String companyName;
    String name;
    String street;
    String city;
    String state;
    String postCode;
    String region;
    String country;
    Double latitude;
    Double longitude;
    String ref;
    String timeZone;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {}

    public static Address fulfilmentFromAddress(GetFulfilmentByIdQuery.Data data) {
        return Address.builder()
                .id(data.fulfilmentById().fromAddress().id() != null ? data.fulfilmentById().fromAddress().id() : null)
                .ref(data.fulfilmentById().fromAddress().ref() != null ? data.fulfilmentById().fromAddress().ref() : null)
                .type(data.fulfilmentById().fromAddress().type() != null ? data.fulfilmentById().fromAddress().type() : null)
                .createdOn((Date)data.fulfilmentById().fromAddress().createdOn() != null ? (Date)data.fulfilmentById().fromAddress().createdOn() : null)
                .updatedOn((Date)data.fulfilmentById().fromAddress().updatedOn() != null ? (Date)data.fulfilmentById().fromAddress().updatedOn() : null)
                .name(data.fulfilmentById().fromAddress().name() != null ? data.fulfilmentById().fromAddress().name() : null)
                .street(data.fulfilmentById().fromAddress().street() != null ? data.fulfilmentById().fromAddress().street() : null)
                .city(data.fulfilmentById().fromAddress().city() != null ? data.fulfilmentById().fromAddress().city() : null)
                .state(data.fulfilmentById().fromAddress().state() != null ? data.fulfilmentById().fromAddress().state() : null)
                .postCode(data.fulfilmentById().fromAddress().postcode() != null ? data.fulfilmentById().fromAddress().postcode() : null)
                .region(data.fulfilmentById().fromAddress().region() != null ? data.fulfilmentById().fromAddress().region() : null)
                .country(data.fulfilmentById().fromAddress().country() != null ? data.fulfilmentById().fromAddress().country() : null)
                .latitude(data.fulfilmentById().fromAddress().latitude() != null ? data.fulfilmentById().fromAddress().latitude() : null)
                .longitude(data.fulfilmentById().fromAddress().longitude() != null ? data.fulfilmentById().fromAddress().longitude() : null)
                .timeZone(data.fulfilmentById().fromAddress().timeZone() != null ? data.fulfilmentById().fromAddress().timeZone() : null)
                .build();
    }

    public static Address fulfilmentToAddress(GetFulfilmentByIdQuery.Data data) {
        return Address.builder()
                .id(data.fulfilmentById().toAddress().id() != null ? data.fulfilmentById().toAddress().id() : null)
                .ref(data.fulfilmentById().toAddress().ref() != null ? data.fulfilmentById().toAddress().ref() : null)
                .type(data.fulfilmentById().toAddress().type() != null ? data.fulfilmentById().toAddress().type() : null)
                .createdOn((Date)data.fulfilmentById().toAddress().createdOn() != null ? (Date)data.fulfilmentById().toAddress().createdOn() : null)
                .updatedOn((Date)data.fulfilmentById().toAddress().updatedOn() != null ? (Date)data.fulfilmentById().toAddress().updatedOn() : null)
                .name(data.fulfilmentById().toAddress().name() != null ? data.fulfilmentById().toAddress().name() : null)
                .street(data.fulfilmentById().toAddress().street() != null ? data.fulfilmentById().toAddress().street() : null)
                .city(data.fulfilmentById().toAddress().city() != null ? data.fulfilmentById().toAddress().city() : null)
                .state(data.fulfilmentById().toAddress().state() != null ? data.fulfilmentById().toAddress().state() : null)
                .postCode(data.fulfilmentById().toAddress().postcode() != null ? data.fulfilmentById().toAddress().postcode() : null)
                .region(data.fulfilmentById().toAddress().region() != null ? data.fulfilmentById().toAddress().region() : null)
                .country(data.fulfilmentById().toAddress().country() != null ? data.fulfilmentById().toAddress().country() : null)
                .latitude(data.fulfilmentById().toAddress().latitude() != null ? data.fulfilmentById().toAddress().latitude() : null)
                .longitude(data.fulfilmentById().toAddress().longitude() != null ? data.fulfilmentById().toAddress().longitude() : null)
                .timeZone(data.fulfilmentById().toAddress().timeZone() != null ? data.fulfilmentById().toAddress().timeZone() : null)
                .build();
    }

    public static Address getOrderDeliveryAddress(GetOrderByIdQuery.DeliveryAddress deliveryAddress) {
        return Address.builder()
                .id(deliveryAddress.id() != null ? deliveryAddress.id() : null)
                .ref(deliveryAddress.ref() != null ? deliveryAddress.ref() : null)
                .type(deliveryAddress.type() != null ? deliveryAddress.type() : null)
                .name(deliveryAddress.name() != null ? deliveryAddress.name() : null)
                .companyName(deliveryAddress.companyName() != null ? deliveryAddress.companyName() : null)
                .street(deliveryAddress.street() != null ? deliveryAddress.street() : null)
                .city(deliveryAddress.city() != null ? deliveryAddress.city() : null)
                .state(deliveryAddress.state() != null ? deliveryAddress.state() : null)
                .postCode(deliveryAddress.postcode() != null ? deliveryAddress.postcode() : null)
                .region(deliveryAddress.region() != null ? deliveryAddress.region() : null)
                .country(deliveryAddress.country() != null ? deliveryAddress.country() : null)
                .longitude(deliveryAddress.longitude() != null ? deliveryAddress.longitude() : null)
                .latitude(deliveryAddress.latitude() != null ? deliveryAddress.latitude() : null)
                .createdOn((Date)deliveryAddress.createdOn() != null ? (Date)deliveryAddress.createdOn() : null)
                .updatedOn((Date)deliveryAddress.updatedOn() != null ? (Date)deliveryAddress.updatedOn() : null)
                .build();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Address that = (Address) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

}
