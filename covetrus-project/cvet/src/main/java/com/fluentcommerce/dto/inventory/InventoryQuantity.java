package com.fluentcommerce.dto.inventory;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Date;

/**
 * @author Alex L.
 */
@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@AllArgsConstructor
@JsonDeserialize(builder = InventoryQuantity.Builder.class)
public class InventoryQuantity {

    private String ref;

    private String type;

    private String status;

    @NonNull
    private Integer quantity;

    private String condition;

    private Date expectedOn;

    private String storageAreaRef;

    private String action;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

    }
}
