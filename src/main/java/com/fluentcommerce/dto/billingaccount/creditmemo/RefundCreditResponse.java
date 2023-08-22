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
public class RefundCreditResponse {
    private String orderRef;
    private String status;
    private String processorId;
    private String createdDate;
    private String amount;
    private String errorCode;
    private String transactionType;
    private String authNumber;
    private String transactionKey;
    private String paymentToken;
    private String paymentMethod;
    private String cardType;
    private String refundKey;
}
