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
public class PickReadyInstruction {
    private String orderKey;
    private String orderRef;
    private String shippingOrderKey;
    private String fulfilmentId;
    private String status;
}
