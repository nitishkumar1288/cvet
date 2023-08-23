package com.fluentcommerce.service.network;

import com.fluentcommerce.graphql.query.networks.GetNetworksByRefAndStatusQuery;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.fluentcommerce.util.Constants.DEFAULT_PAGINATION_PAGE_SIZE;

@Slf4j
public class NetworkService {
    private Context context;

    public NetworkService(Context context) {
        this.context = context;
    }

    public GetNetworksByRefAndStatusQuery.Data getNetworksByRefAndStatus(List<String> networkRefList, List<String> networkStatusList, List<String> locationStatus){
        return (GetNetworksByRefAndStatusQuery.Data) context.api().query(
                GetNetworksByRefAndStatusQuery.builder()
                        .ref(networkRefList)
                        .networkCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .locationsCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .status(networkStatusList)
                        .locationStatus(locationStatus)
                        .build());
    }


}
