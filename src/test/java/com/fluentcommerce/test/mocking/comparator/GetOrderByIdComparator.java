package com.fluentcommerce.test.mocking.comparator;


import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;

import java.util.Comparator;

public class GetOrderByIdComparator implements Comparator<GetOrderByIdQuery> {
    @Override
    public int compare(GetOrderByIdQuery o1, GetOrderByIdQuery o2) {
        return 0;
    }
}
