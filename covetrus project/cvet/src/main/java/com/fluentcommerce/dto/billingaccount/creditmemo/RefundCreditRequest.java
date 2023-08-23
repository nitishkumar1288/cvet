package com.fluentcommerce.dto.billingaccount.creditmemo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 @author nitishKumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class RefundCreditRequest {
    private String orderRef;
    private String paymentType;
    private String paymentToken;
    private float grandTotal;
    private String refundKey;
    private String orderKey;
}
