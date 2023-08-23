package com.fluentcommerce.test.mocking.comparator;

import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetBillingAccountsByCustomerRefQuery;

import java.util.Comparator;

public class GetBillingAccountByCustomerRefComparator implements Comparator<GetBillingAccountsByCustomerRefQuery> {
    @Override
    public int compare(GetBillingAccountsByCustomerRefQuery o1, GetBillingAccountsByCustomerRefQuery o2) {
        return 0;
    }
}
