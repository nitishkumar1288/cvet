package com.fluentcommerce.dto.inventory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
     @author Nandhakumar
*/

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class ReservationItemsInformation {
    private String orderItemId;
    private String skuRef;
    private String locationRef;
    private int quantity;
    private int rejectedQuantity;
}
