package com.fluentcommerce.model.rx;

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
public class Items {
    private String productKey;
    private String productRef;
    private String lineNumber;
    private String prescriptionId;
    private String facilityId;
    private String status;
    private String isPrescriptionRequired;
    private String refillsRemaining;
    private String declinedReasonCode;
    private String rxStatus;
}