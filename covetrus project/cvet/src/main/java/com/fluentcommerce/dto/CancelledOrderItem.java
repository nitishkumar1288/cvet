package com.fluentcommerce.dto;

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
public class CancelledOrderItem {
    private String orderItemKey;
    private String lineStatus;
    private String cancellationReasonText;
    private String cancellationReasonCode;
    private String cancelledQty;
    private String originalOrderQty;
    private String autoshipKey;
    private String prescriptionId;
}
