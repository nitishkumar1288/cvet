package com.fluentcommerce.service.billingaccount.creditmemo;


import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@Slf4j
public class CreditMemoService {
    private Context context;

    public CreditMemoService(Context context) {
        this.context = context;
    }

    public GetCreditMemoByRefQuery.CreditMemo getCreditMemoByRef(String ref){

        GetCreditMemoByRefQuery.Builder creditMemoByRefBuilder = GetCreditMemoByRefQuery.builder()
                .ref(ref)
                .includeItems(true)
                .returnOrderItemCount(DEFAULT_PAGINATION_PAGE_SIZE);

        GetCreditMemoByRefQuery.Data creditMemoData = (GetCreditMemoByRefQuery.Data) context.api().query(creditMemoByRefBuilder.build());
        return creditMemoData.creditMemo();
    }
}
