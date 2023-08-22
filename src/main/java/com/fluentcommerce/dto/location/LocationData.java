package com.fluentcommerce.dto.location;

import com.fluentcommerce.dto.orderitem.OrderItemData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
public class LocationData {
    private String locationID;
    private String locationRef;
    private ArrayList<OrderItemData> items;
}
