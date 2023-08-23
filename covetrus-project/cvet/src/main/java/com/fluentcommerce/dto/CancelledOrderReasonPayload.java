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
public class CancelledOrderReasonPayload {
    private String orderRef;
    private String status;
    private String cancellationReasonText;
    private String cancellationReasonCode;
    private String updateOn;
}
