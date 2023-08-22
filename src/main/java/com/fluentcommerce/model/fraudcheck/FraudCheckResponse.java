package com.fluentcommerce.model.fraudcheck;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 @author nitishKumar
 */

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = FraudCheckResponse.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class FraudCheckResponse {

    private String orderKey;
    private String eventTypeCode;
    private String eventSubTypeCodes;
    private String fraudRiskResult;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

    }
}
