package com.fluentcommerce.dto.orderitem;

import com.fluentcommerce.model.product.KitItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author nandhakumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class OrderItemDto {
    String id;
    String orderItemRef;
    String productRef;
    int requestedQuantity;
    Map<String,String> itemTypeMap;
    boolean isKitProduct;
    List<KitItem> kitItemList;
    String prescriptionId;
}
