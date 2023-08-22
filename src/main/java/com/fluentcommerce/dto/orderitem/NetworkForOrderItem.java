package com.fluentcommerce.dto.orderitem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author nandhakumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class NetworkForOrderItem {
    String itemType;
    String itemValue;
    String networkRef;
    String defaultLocationInNetwork;
}
