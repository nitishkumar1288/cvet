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
public class PaymentSettlementResponse {
    String orderRef;
    String fulfilmentRef;
    String status;
    String processorId;
    String createdDate;
    String amount;
    String errorCode;
    String transactionType;
    String authNumber;
    String transactionKey;
    String paymentToken;
    String paymentMethod;
    String cardType;
}
