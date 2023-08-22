package com.fluentcommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 @author nitishKumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class CancelledOrderItemReasonPayload {
    private String orderRef;
    private String updateOn;
    private ArrayList<CancelledOrderItem> items;
}
