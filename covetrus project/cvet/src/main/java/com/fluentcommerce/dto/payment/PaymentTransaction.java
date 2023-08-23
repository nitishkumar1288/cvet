package com.fluentcommerce.dto.payment;
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
public class PaymentTransaction {
    String orderRef;
    String fulfilmentRef;
    String status;
    String processorId;
    String createdDate;
    float amount;
    String errorCode;
    String transactionType;
    String authNumber;
    String transactionKey;
    String paymentToken;
    String paymentMethod;
    String cardType;
}