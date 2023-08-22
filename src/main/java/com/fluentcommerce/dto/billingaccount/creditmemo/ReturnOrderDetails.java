package com.fluentcommerce.dto.billingaccount.creditmemo;

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
public class ReturnOrderDetails {
    private String userId;
    private String refundKey;
    private float amount;
    private String status;
    private String returnOrderRef;
    private String reasonForReturn;
    private ArrayList<ReturnItem> refundedItems;
}