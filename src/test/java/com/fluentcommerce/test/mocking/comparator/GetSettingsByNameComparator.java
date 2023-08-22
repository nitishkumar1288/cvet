package com.fluentcommerce.test.mocking.comparator;


import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;

import java.util.Comparator;

public class GetSettingsByNameComparator implements Comparator<GetSettingsByNameQuery> {
    @Override
    public int compare(GetSettingsByNameQuery o1, GetSettingsByNameQuery o2) {
        return 0;
    }
}
