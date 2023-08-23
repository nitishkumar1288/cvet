package com.fluentcommerce.model.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Yamini Kukreja
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class PV1Items {
    private String ref;
    private String prescriptionId;
    private String facilityId;
    private String status;
    private String dispenseRequestId;
    private String exceptionReason;
    private String cancelReason;
    private String exceptionResolutionType;
}
