package com.fluentcommerce.dto.fulfillment;

import com.fluentcommerce.dto.orderitem.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

/**
 @author nitishKumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
public class FulfillmentRequestData {
    String orderKey;
    String orderRef;
    ArrayList<OrderItem> items;
}
