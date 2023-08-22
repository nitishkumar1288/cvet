package com.fluentcommerce.test.mocking.comparator;

import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;

import java.util.Comparator;

public class GetOrderByRefComparator implements Comparator<GetOrdersByRefQuery> {
    @Override
    public int compare(GetOrdersByRefQuery o1, GetOrdersByRefQuery o2) {
        return 0;
    }
}