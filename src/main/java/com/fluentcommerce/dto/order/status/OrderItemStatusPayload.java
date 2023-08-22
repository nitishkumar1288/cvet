package com.fluentcommerce.dto.order.status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;

/**
 @author nitishKumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class OrderItemStatusPayload {
    private String orderRef;
    private String orderId;
    private String updateOn;
    private ArrayList<OrderItemStatus> items;
    private HashMap<String,OrderItemStatus> itemMap;
}
