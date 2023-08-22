package com.fluentcommerce.dto.fulfillment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 @author nitishKumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
public class FulfilmentExceptionData {
    private String ref;
    private String id;
    private String status;
    private String exceptionReason;
    private String exceptionResolutionType;
    private String exceptionResolutionCode;
    private String facilityId;
    private List<FulfilmentItems> items;
    private String userKey;
}
