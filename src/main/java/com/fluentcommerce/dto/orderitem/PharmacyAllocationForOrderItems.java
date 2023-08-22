package com.fluentcommerce.dto.orderitem;

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
public class PharmacyAllocationForOrderItems {
    String prescriptionId;
    String assignedFacilityId;
    String assignedFacilityRef;
    String networkRef;
    String defaultLocationRefInNetwork;
    boolean isDefaultLocationSelected;
    List<String> locationRefList;
    List<OrderItemDto> orderItemDtoList;
}
