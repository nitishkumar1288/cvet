package com.fluentcommerce.service.fulfilment;

import com.fluentcommerce.graphql.query.fulfilment.GetFulfillmentByRefQuery;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.fluentcommerce.util.Constants.*;

@Slf4j
public class FulfilmentService {
    private Context context;

    public FulfilmentService(Context context) {
        this.context = context;
    }

    /**
     * @param fulfilmentId  fulfilment id
     * @param itemList list of the fulfilment item
     * @param fulfilmentCursor cursor id
     * @return Items List of fulfilment item data
     */
    public List<GetFulfilmentByIdQuery.Edge> getFulfilmentItems(String fulfilmentId,
                                                                List<GetFulfilmentByIdQuery.Edge> itemList, String fulfilmentCursor) {
        GetFulfilmentByIdQuery.Builder fulfilmentByIdBuilder = GetFulfilmentByIdQuery.builder()
                .id(fulfilmentId)
                .includeItems(true)
                .fulfilmentItemsCount(DEFAULT_PAGINATION_PAGE_SIZE);
        if (StringUtils.isNotBlank(fulfilmentCursor)) {
            fulfilmentByIdBuilder.fulfilmentItemsCursor(fulfilmentCursor).build();
        }
        GetFulfilmentByIdQuery.Data fulfilmentData = (GetFulfilmentByIdQuery.Data) context.api().query(fulfilmentByIdBuilder.build());

        if (fulfilmentData != null && fulfilmentData.fulfilmentById() != null
                && Objects.nonNull(fulfilmentData.fulfilmentById().items())
                && CollectionUtils.isNotEmpty(fulfilmentData.fulfilmentById().items().edges())
        ) {
            List<GetFulfilmentByIdQuery.Edge> fulfilmentItemEdgeList = fulfilmentData.fulfilmentById().items().edges();
            assert fulfilmentItemEdgeList != null;
            itemList.addAll(fulfilmentItemEdgeList);
            boolean hasNextPage = fulfilmentData.fulfilmentById().items().pageInfo().hasNextPage();
            if (hasNextPage) {
                String lastCursor = fulfilmentData.fulfilmentById().items().edges().get(
                        fulfilmentData.fulfilmentById().items().edges().size() - 1).cursor();
                getFulfilmentItems(fulfilmentId, itemList, lastCursor);
            }
        }
        return itemList;
    }

    public GetFulfilmentByIdQuery.FulfilmentById getFulfillmentById(String locationId){

        GetFulfilmentByIdQuery.Builder fulfillmentByIdBuilder = GetFulfilmentByIdQuery.builder()
                .id(locationId)
                .includeItems(true)
                .includeOrder(true)
                .includeAttributes(true)
                .withOrderItem(true)
                .includefromAddress(true)
                .fulfilmentItemsCount(DEFAULT_PAGINATION_PAGE_SIZE);

        GetFulfilmentByIdQuery.Data fulfillmentData = (GetFulfilmentByIdQuery.Data) context.api().query(fulfillmentByIdBuilder.build());
        return fulfillmentData.fulfilmentById();
    }

    public GetFulfillmentByRefQuery.Fulfilments getFulfillments(List<String> fulfillmentRefList){

        GetFulfillmentByRefQuery.Builder fulfillmentByRefBuilder = GetFulfillmentByRefQuery.builder()
                .ref(fulfillmentRefList)
                .fulfilmentCount(DEFAULT_PAGINATION_PAGE_SIZE_FOR_FULFILMENT)
                .includeItems(true)
                .includeAttributes(true)
                .fulfilmentItemsCount(DEFAULT_PAGINATION_PAGE_SIZE);

        GetFulfillmentByRefQuery.Data fulfillmentData = (GetFulfillmentByRefQuery.Data) context.api().query(fulfillmentByRefBuilder.build());
        return fulfillmentData.fulfilments();
    }
}
