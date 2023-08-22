package com.fluentcommerce.service.location;

import com.fluentcommerce.graphql.query.location.GetLocationByIdQuery;
import com.fluentcommerce.graphql.query.location.GetLocationsByRefsQuery;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.fluentcommerce.util.Constants.*;

@Slf4j
public class LocationService {
    private Context context;

    public LocationService(Context context) {
        this.context = context;
    }

    /**
     * @param locationRefList  List of location reference
     * @param locationsList   list of the location data
     * @param locationCursor cursor id
     * @return Locations List of location data of the order
     */
    public List<GetLocationsByRefsQuery.Edge> getLocations(List<String> locationRefList,
                                                           List<GetLocationsByRefsQuery.Edge> locationsList,
                                                           String locationCursor) {
        GetLocationsByRefsQuery.Builder locationByRefBuilder = GetLocationsByRefsQuery.builder()
                .locationRef(locationRefList)
                .includeLocations(true)
                .locationCount(DEFAULT_PAGINATION_PAGE_SIZE);
        if (StringUtils.isNotBlank(locationCursor)) {
            locationByRefBuilder.locationCursor(locationCursor).build();
        }
        GetLocationsByRefsQuery.Data locationData = (GetLocationsByRefsQuery.Data) context.api().query(locationByRefBuilder.build());

        if (locationData != null && locationData.locations() != null
                && CollectionUtils.isNotEmpty(locationData.locations().edges())
        ) {
            List<GetLocationsByRefsQuery.Edge> locationEdgeList = locationData.locations().edges();
            assert locationEdgeList != null;
            locationsList.addAll(locationEdgeList);

            boolean hasNextPage = locationData.locations().pageInfo().hasNextPage();
            if (hasNextPage) {
                String lastCursor = locationData.locations().edges().get(
                        locationData.locations().edges().size() - 1).cursor();
                getLocations(locationRefList, locationsList, lastCursor);
            }
        }
        return locationsList;
    }

    public GetLocationByIdQuery.LocationById getLocationById(String locationId){

        GetLocationByIdQuery.Builder locationByIdBuilder = GetLocationByIdQuery.builder()
                .id(locationId)
                .includeNetworks(true)
                .networkCount(DEFAULT_PAGINATION_PAGE_SIZE);

        GetLocationByIdQuery.Data locationData = (GetLocationByIdQuery.Data) context.api().query(locationByIdBuilder.build());
       return locationData.locationById();
    }
}

