package com.fluentcommerce.model.virtual;

import com.fluentcommerce.graphql.query.virtual.GetVirtualPositionsQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
     @author Nandhakumar
*/

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class VirtualPosition {
    String ref;
    String productRef;
    int quantity;
    String groupRef;

    public static VirtualPosition from(GetVirtualPositionsQuery.VirtualPosition virtualPosition) {
        return VirtualPosition.builder()
                .ref(virtualPosition.ref())
                .quantity(virtualPosition.quantity())
                .productRef(virtualPosition.productRef())
                .groupRef(virtualPosition.groupRef())
                .build();
    }

    public static VirtualPosition from(
            String ref,
            String productRef,
            int quantity,
            String groupRef
    ) {
        return VirtualPosition.builder()
                .ref(ref)
                .quantity(quantity)
                .productRef(productRef)
                .groupRef(groupRef)
                .build();
    }


}

