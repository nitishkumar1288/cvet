package com.fluentcommerce.dto.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
     @author Nandhakumar
*/

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = Items.Builder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class Items {

    private String skuRef;
    private String locationRef;
    private String orderId;
    private String orderItemId;
    private int quantity;
    private int reserveQty;
    private int correctionQty;
    private int saleQty;
    private int cancelQty;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

    }
}
