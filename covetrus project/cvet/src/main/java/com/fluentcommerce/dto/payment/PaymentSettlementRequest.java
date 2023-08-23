package com.fluentcommerce.dto.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 @author nitishKumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
public class PaymentSettlementRequest {
    private String fulfillmentRef;
    private String paymentToken;
    private String paymentType;
    private float grandTotal;
    private String orderKey;
}
