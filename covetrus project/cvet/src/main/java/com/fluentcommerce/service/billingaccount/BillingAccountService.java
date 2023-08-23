package com.fluentcommerce.service.billingaccount;

import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetBillingAccountsByCustomerRefQuery;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;

/**
 @author nitishKumar
 */

@Slf4j
public class BillingAccountService {
    private Context context;

    public BillingAccountService(Context context) {
        this.context = context;
    }

    public GetBillingAccountsByCustomerRefQuery.BillingAccounts getBillingAccountByRef(String ref){

        GetBillingAccountsByCustomerRefQuery.Builder billingAccountByRefBuilder = GetBillingAccountsByCustomerRefQuery.builder()
                .customerRef(ref);

        GetBillingAccountsByCustomerRefQuery.Data billingAccountData = (GetBillingAccountsByCustomerRefQuery.Data) context.api().query(billingAccountByRefBuilder.build());
        return billingAccountData.billingAccounts();
    }
}
