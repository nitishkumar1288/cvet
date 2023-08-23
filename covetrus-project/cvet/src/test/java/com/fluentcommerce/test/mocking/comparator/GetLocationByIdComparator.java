package com.fluentcommerce.test.mocking.comparator;


import com.fluentcommerce.graphql.query.location.GetLocationByIdQuery;

import java.util.Comparator;

public class GetLocationByIdComparator implements Comparator<GetLocationByIdQuery> {
    @Override
    public int compare(GetLocationByIdQuery o1, GetLocationByIdQuery o2) {
        return 0;
    }
}
