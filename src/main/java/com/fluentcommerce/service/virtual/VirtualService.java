package com.fluentcommerce.service.virtual;

import com.fluentcommerce.graphql.query.virtual.GetVirtualPositionsQuery;
import com.fluentcommerce.model.virtual.VirtualPosition;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.fluentcommerce.util.Constants.*;

/**
     @author Nandhakumar
*/
@Slf4j
public class VirtualService {
    private Context context;

    public VirtualService(Context context) {
        this.context = context;
    }

    public List<VirtualPosition> searchVirtualPositions(
            String virtualCatalogueRef,
            List<String> locationRefs,
            List<String> productRefs
    ) {
        String cursor = null;
        GetVirtualPositionsQuery.Data virtualPositionData = queryVirtualPositions(virtualCatalogueRef, locationRefs, productRefs, cursor, DEFAULT_PAGE_SIZE);
        if (virtualPositionData == null
                || virtualPositionData.virtualPositions() == null
                || CollectionUtils.isEmpty(virtualPositionData.virtualPositions().virtualPositionEdge())
        ) {
            return new ArrayList<>();
        }

        List<VirtualPosition> virtualPositions = new ArrayList<>();
        mapResult(virtualPositionData, virtualPositions);
        boolean hasNextPage = virtualPositionData.virtualPositions().pageInfo().hasNextPage();
        if (!hasNextPage) {
            return virtualPositions;
        }

        while (virtualPositionData.virtualPositions().pageInfo() != null && hasNextPage) {
            List<GetVirtualPositionsQuery.VirtualPositionEdge> virtualPositionEdges = virtualPositionData.virtualPositions().virtualPositionEdge();
            cursor = virtualPositionEdges.get(virtualPositionEdges.size() - 1).cursor();
            virtualPositionData = queryVirtualPositions(virtualCatalogueRef, locationRefs, productRefs, cursor, DEFAULT_PAGE_SIZE);
            hasNextPage = virtualPositionData.virtualPositions().pageInfo().hasNextPage();
            mapResult(virtualPositionData, virtualPositions);
        }
        return virtualPositions;
    }

    private void mapResult(GetVirtualPositionsQuery.Data virtualPositionData, List<VirtualPosition> virtualPositions) {
        for (GetVirtualPositionsQuery.VirtualPositionEdge virtualPositionEdge : virtualPositionData.virtualPositions().virtualPositionEdge()) {
            GetVirtualPositionsQuery.VirtualPosition virtualPositionNode = virtualPositionEdge.virtualPosition();
            VirtualPosition virtualPosition = VirtualPosition.from(virtualPositionNode);
            // only add if there's stock available
            if (virtualPosition.getQuantity() > 0) {
                virtualPositions.add(virtualPosition);
            }
        }
    }

    private GetVirtualPositionsQuery.Data queryVirtualPositions(String virtualCatalogueRef,
                                                                List<String> locationRefs,
                                                                List<String> productRefs,
                                                                String cursor,
                                                                int count) {
        GetVirtualPositionsQuery virtualPositionsQuery = GetVirtualPositionsQuery.builder()
                .catalogueRef(virtualCatalogueRef)
                .groupRef(locationRefs)
                .productRef(productRefs)
                .virtualPositionCount(count)
                .virtualPositionCursor(cursor)
                .build();
        return  (GetVirtualPositionsQuery.Data) context.api().query(virtualPositionsQuery);
    }

}
