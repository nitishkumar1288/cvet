package com.fluentcommerce.service.returns;

import com.fluentcommerce.graphql.query.returns.GetReturnOrderByRefQuery;
import com.fluentcommerce.graphql.type.RetailerId;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@Slf4j
public class ReturnService {
    private Context context;
    public ReturnService(Context context) {
        this.context = context;
    }

    public GetReturnOrderByRefQuery.ReturnOrder getReturnOrderByRef(String ref){
        GetReturnOrderByRefQuery.Builder returnOrderByRefBuilder = GetReturnOrderByRefQuery.builder()
                .ref(ref)
                .retailer(RetailerId.builder().id(context.getEvent().getRetailerId()).build())
                .includeReturnOrderItems(true)
                .includeAttributes(true)
                .returnOrderItemCount(DEFAULT_PAGE_SIZE);

        GetReturnOrderByRefQuery.Data returnOrderData = (GetReturnOrderByRefQuery.Data) context.api().query(returnOrderByRefBuilder.build());
        return returnOrderData.returnOrder();
    }
}
