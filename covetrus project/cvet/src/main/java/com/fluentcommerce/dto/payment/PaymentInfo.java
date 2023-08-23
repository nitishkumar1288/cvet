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
public class PaymentInfo {
    String amount;
    String cardType;
    String cardNumberLastFour;
    String expiryYear;
    String expiryMonth;
    String paymentType;
    String paymentToken;
    String paymentSequence;
    String accountHolderName;
    BillingAddress billingAddress;
    String cardNumberFirstSix;
    PaymentAuthDetail authDetail;
}
