package com.fluentcommerce.dto.order.status;

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
public class OrderItemStatus {
    private String orderItemKey;
    private String lineStatus;
    private String prescriptionId;
    private String exceptionTypeCode;
    private String exceptionResolutionCode;
}
