package com.fluentcommerce.dto.billingaccount.creditmemo;

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
public class RefundProcessed {
    private String orderRef;
    private String status;
    private String amount;
    private String refundKey;
    private String paymentType;
    private String cardNumberLastFour;
    private String refundReason;
    private String createdOn;
    private String updatedOn;
    private ArrayList<RefundItem> items;
    private String cardNumberFirstSix;
    private String cardType;
}
