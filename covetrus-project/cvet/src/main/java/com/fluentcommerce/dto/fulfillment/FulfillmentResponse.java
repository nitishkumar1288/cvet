package com.fluentcommerce.dto.fulfillment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

/**
 @author nitishKumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
public class FulfillmentResponse {
    String ref;
    String facilityId;
    String shippingMethod;
    String exceptionReason;
    String status;
    ArrayList<FulfilmentOrderItem> items;
}
