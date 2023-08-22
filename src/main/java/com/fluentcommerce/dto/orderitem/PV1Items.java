package com.fluentcommerce.dto.orderitem;

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

    //PV1 request
    private String ref;
    private String facilityId;
    private String prescriptionId;

    //PV1 response
    private String lineNumber;
    private String status;
    private String notes;
    private String dispenseRequestId;
    private String exceptionReason;
}
