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
public class PaymentAuthDetail {
    private String amount;
    private String status;
    private String cardType;
    private String authNumber;
    private String createdDate;
    private String processorId;
    private String paymentToken;
    private String paymentMethod;
    private String transactionKey;
    private String transactionType;
}
