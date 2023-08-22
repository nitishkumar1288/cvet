package com.fluentcommerce.test.mocking.comparator;

import com.fluentcommerce.graphql.query.inventory.GetInventoryCatalogueQuery;

import java.util.Comparator;

public class GetInventoryCatalogueByRefComparator implements Comparator<GetInventoryCatalogueQuery> {
    @Override
    public int compare(GetInventoryCatalogueQuery o1, GetInventoryCatalogueQuery o2) {
        return 0;
    }
}
