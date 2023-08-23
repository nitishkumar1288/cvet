package com.fluentcommerce.test.mocking.comparator;

import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;

import java.util.Comparator;

public class GetFulfillmentByIdComparator implements Comparator<GetFulfilmentByIdQuery> {
    @Override
    public int compare(GetFulfilmentByIdQuery o1, GetFulfilmentByIdQuery o2) {
        return 0;
    }
}
