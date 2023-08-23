package com.fluentcommerce.dto.orderitem;

import com.fluentcommerce.model.product.KitItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author nandhakumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class DropShipProductDto {
    String productRef;
    String orderItemId;
    int requestedQuantity;
    String foundLocation;
    String locationId;
    List<KitItem> kitItemList;
    boolean isKitProduct;
}
