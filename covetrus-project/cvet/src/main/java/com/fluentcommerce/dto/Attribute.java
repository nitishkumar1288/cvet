package com.fluentcommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fluentcommerce.graphql.type.AttributeInput;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = Attribute.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute {
    String attributeName;
    String attributeType;
    String attributeValue;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {}

    public AttributeInput toAttributeInput() {
        return AttributeInput.builder()
            .name(attributeName)
            .type(attributeType)
            .value(attributeValue)
            .build();
    }
}