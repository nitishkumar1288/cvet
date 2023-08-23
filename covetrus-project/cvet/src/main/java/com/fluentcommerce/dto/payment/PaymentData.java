package com.fluentcommerce.dto.payment;

/**
 @author nitishKumar
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class PaymentData {
    String orderRef;
    String fulfilmentRef;
    String status;
    String processorId;
    float totalPrice;
    String transactionType;
    String authNumber;
    String transactionKey;
    String paymentToken;
    String paymentMethod;
    String paymentType;
    String createdOn;
}
