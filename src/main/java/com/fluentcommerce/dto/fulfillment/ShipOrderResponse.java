package com.fluentcommerce.dto.fulfillment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 @author nitishKumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class ShipOrderResponse {
    private String orderRef;
    private String orderKey;
    private String shippingOrderKey;
    private String fulfilmentId;
    private ArrayList<Line> lines;
    private String carrierCode;
    private String trackingNumber;
    private String shippingMethod;
    private String trackingURL;
    private String shippedDate;
}
