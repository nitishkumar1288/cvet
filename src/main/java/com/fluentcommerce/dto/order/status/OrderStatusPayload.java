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
public class OrderStatusPayload {
    private String orderId;
    private String orderRef;
    private String status;
    private String updateOn;
    private String orderKey;
    private String animalCareBusiness;

}
