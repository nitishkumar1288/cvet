package com.fluentcommerce.dto.orderitem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
public class OrderItemData {
    private String lineItemRef;
    private String lineItemId;
}
