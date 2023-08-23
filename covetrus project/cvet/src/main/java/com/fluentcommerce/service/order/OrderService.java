package com.fluentcommerce.service.order;

import com.fluentcommerce.dto.Attribute;
import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import static com.fluentcommerce.util.Constants.*;

@Slf4j
public class OrderService {
    private Context context;

    public OrderService(Context context) {
        this.context = context;
    }

    /**
     * Retrieves the order via GQL and maps it to an Order dto.
     *
     * @param orderId the order id
     * @return the order dto
     */
    public Order getOrderById(String orderId) {
        GetOrderByIdQuery query = GetOrderByIdQuery.builder()
                .id(orderId)
                .includeOrderItems(true)
                .includeFulfilmentChoice(true)
                .includeCustomer(true)
                .includeAttributes(true)
                .orderItemCount(DEFAULT_PAGE_SIZE)
                .build();
        GetOrderByIdQuery.Data data = (GetOrderByIdQuery.Data) context.api().query(query);
        return Order.from(data.orderById());
    }

    /**
     * Updates the order with the provided attribute.
     *
     * @param orderId   the order id
     * @param attribute the attribute
     */
    public void updateOrderAttributes(String orderId, Attribute attribute) {
        UpdateOrderInput orderInput = UpdateOrderInput.builder()
                .id(orderId)
                .attributes(Arrays.asList(attribute.toAttributeInput()))
                .build();
        UpdateOrderMutation orderMutation = UpdateOrderMutation.builder().input(orderInput).build();
        context.action().mutation(orderMutation);
    }

    /**
     * Updates the order with the provided orderItems.
     *
     * @param orderId   the order id
     * @param items list of orderItemInput
     */
    public void updateOrderItems(List<UpdateOrderItemWithOrderInput> items, String orderId) {
        UpdateOrderInput orderInput = UpdateOrderInput.builder()
                .id(orderId)
                .items(items)
                .build();
        UpdateOrderMutation orderMutation = UpdateOrderMutation.builder().input(orderInput).build();
        context.action().mutation(orderMutation);
    }
    /**
     * @param orderAttributesMap Map of order attributes
     * @param attributeName      The attribute name to match
     * @return
     */
    public boolean ifOrderAttributeExists(final Map<String, Object> orderAttributesMap, String attributeName) {
        if (orderAttributesMap == null)
            return false;
        return orderAttributesMap.entrySet().stream().anyMatch(e -> e.getKey().equalsIgnoreCase(attributeName));
    }

    /**
     * @param orderId          order id
     * @param fulfilmentList   list of the fufilment data
     * @param fulfilmentCursor cursor id
     * @return FulfilmentsEdge List of fulfilment data of the order
     */
    public List<GetOrderByIdQuery.FulfilmentsEdge> getOrderFulfilments(String orderId,
                                                                       List<GetOrderByIdQuery.FulfilmentsEdge> fulfilmentList,
                                                                       String fulfilmentCursor) {
        GetOrderByIdQuery.Builder orderByIdBuilder = GetOrderByIdQuery.builder()
                .id(orderId)
                .includeFulfilments(true)
                .fulfilmentCount(DEFAULT_PAGINATION_PAGE_SIZE);
        if (StringUtils.isNotBlank(fulfilmentCursor)) {
            orderByIdBuilder.fulfilmentCursor(fulfilmentCursor).build();
        }
        GetOrderByIdQuery.Data orderFulfilmentData = (GetOrderByIdQuery.Data) context.api().query(orderByIdBuilder.build());

        if (orderFulfilmentData != null && orderFulfilmentData.orderById() != null
                && orderFulfilmentData.orderById().fulfilments() != null
                && CollectionUtils.isNotEmpty(orderFulfilmentData.orderById().fulfilments().fulfilmentsEdges())){
            List<GetOrderByIdQuery.FulfilmentsEdge> fulfilments = orderFulfilmentData.orderById().fulfilments().fulfilmentsEdges();
            assert fulfilments != null;
            fulfilmentList.addAll(fulfilments);
            boolean hasNextPage = orderFulfilmentData.orderById().fulfilments().pageInfo().hasNextPage();
            if (hasNextPage) {
                String lastCursor = orderFulfilmentData.orderById().fulfilments().fulfilmentsEdges().get(
                        orderFulfilmentData.orderById().fulfilments().fulfilmentsEdges().size() - 1).cursor();
                getOrderFulfilments(orderId, fulfilmentList, lastCursor);
            }
        }
        return fulfilmentList;
    }

    /**
     * @param orderId order id
     * @param orderItemEdgeList list of the order item data
     * @param orderItemCursor  cursor id for the order item
     * @return OrderItemEdge List of order items data
     */
    public List<GetOrderByIdQuery.OrderItemEdge> getOrderItems(String orderId,
                                                               List<GetOrderByIdQuery.OrderItemEdge> orderItemEdgeList,
                                                               String orderItemCursor) {
        GetOrderByIdQuery.Builder orderByIdBuilder = GetOrderByIdQuery.builder().
                id(orderId).
                includeOrderItems(true).orderItemCount(DEFAULT_PAGINATION_PAGE_SIZE);
        if (StringUtils.isNotBlank(orderItemCursor)) {
            orderByIdBuilder.orderItemCursor(orderItemCursor).build();
        }
        GetOrderByIdQuery.Data orderItemData = (GetOrderByIdQuery.Data) context.api().query(orderByIdBuilder.build());

        if (orderItemData != null && orderItemData.orderById() != null
                && orderItemData.orderById().orderItems() != null
                && CollectionUtils.isNotEmpty(orderItemData.orderById().orderItems().orderItemEdge())
        ) {
            List<GetOrderByIdQuery.OrderItemEdge> orderItemList = orderItemData.orderById().orderItems().orderItemEdge();
            assert orderItemList != null;
            orderItemEdgeList.addAll(orderItemList);
            boolean hasNextPage = orderItemData.orderById().orderItems().pageInfo().hasNextPage();
            if (hasNextPage) {
                String lastCursor = orderItemData.orderById().orderItems().orderItemEdge().get(
                        orderItemData.orderById().orderItems().orderItemEdge().size() - 1).cursor();
                getOrderItems(orderId, orderItemEdgeList, lastCursor);
            }
        }
        return orderItemEdgeList;
    }

    public GetOrderByIdQuery.OrderById getOrderWithOrderAttributes(String orderId) {
        GetOrderByIdQuery.Builder orderByIdBuilder = GetOrderByIdQuery.builder().
                id(orderId).
                orderItemCount(DEFAULT_PAGE_SIZE).
                includeAttributes(true);

        GetOrderByIdQuery.Data orderItemData = (GetOrderByIdQuery.Data) context.api().query(orderByIdBuilder.build());

        return orderItemData.orderById();
    }
    public GetOrderByIdQuery.OrderById getOrderWithOrderItems(String orderId) {
        GetOrderByIdQuery.Builder orderByIdBuilder = GetOrderByIdQuery.builder().
                id(orderId).
                includeAttributes(true).
                orderItemCount(DEFAULT_PAGE_SIZE).
                includeOrderItems(true);

        GetOrderByIdQuery.Data orderItemData = (GetOrderByIdQuery.Data) context.api().query(orderByIdBuilder.build());

        return orderItemData.orderById();
    }

    public GetOrderByIdQuery.Data getOrderByIdData(String orderId) {
        GetOrderByIdQuery.Builder queryBuilder = GetOrderByIdQuery.builder()
                .id(orderId)
                .includeOrderItems(true)
                .includeFulfilments(true)
                .includeFulfilmentChoice(true)
                .includeCustomer(true)
                .includeAttributes(true)
                .fulfilmentCount(DEFAULT_PAGINATION_PAGE_SIZE)
                .orderItemCount(DEFAULT_PAGE_SIZE);

        return (GetOrderByIdQuery.Data) context.api().query(queryBuilder.build());
    }

    /**
     * Updates the order with the provided attribute.
     *
     * @param orderId   the order id
     * @param attributeInputList  of the order
     */
    public void upsertOrderAttributeList(String orderId, List<AttributeInput> attributeInputList) {
        UpdateOrderInput orderInput = UpdateOrderInput.builder()
                .id(orderId)
                .attributes(attributeInputList)
                .build();
        UpdateOrderMutation orderMutation = UpdateOrderMutation.builder().input(orderInput).build();
        context.action().mutation(orderMutation);
    }

    public GetOrderByIdQuery.OrderById getOrderWithFulfilmentItems(String orderId) {
        GetOrderByIdQuery.Builder orderByIdBuilder = GetOrderByIdQuery.builder().
                id(orderId).
                orderItemCount(DEFAULT_PAGE_SIZE).
                includeOrderItems(true).
                includeAttributes(true).
                includeFulfilments(true).
                fulfilmentCount(DEFAULT_PAGINATION_PAGE_SIZE).
                includeFulfilmentChoice(true);

        GetOrderByIdQuery.Data orderItemData = (GetOrderByIdQuery.Data) context.api().query(orderByIdBuilder.build());

        return orderItemData.orderById();
    }

    public GetOrdersByRefQuery.Edge getOrdersByRef(String orderRef) {
        GetOrdersByRefQuery.Builder orderByRefBuilder = GetOrdersByRefQuery.builder().
                ref(Arrays.asList(orderRef)).
                includeOrderItems(true).
                orderItemCount(DEFAULT_PAGE_SIZE);
        GetOrdersByRefQuery.Data orderItemData = (GetOrdersByRefQuery.Data) context.api().query(orderByRefBuilder.build());
        return orderItemData.orders().edges().get(0);
    }

    /**
     * Updates the order attribute along with the provided orderItems.
     *
     * @param orderId   the order id
     * @param items list of orderItemInput
     * @param attributeInputList list of attributeInput
     */
    public void updateOrderAttributeWithOrderItems(List<UpdateOrderItemWithOrderInput> items, String orderId,List<AttributeInput> attributeInputList) {
        UpdateOrderInput orderInput = UpdateOrderInput.builder()
                .id(orderId)
                .attributes(attributeInputList)
                .items(items)
                .build();
        UpdateOrderMutation orderMutation = UpdateOrderMutation.builder().input(orderInput).build();
        context.action().mutation(orderMutation);
    }
}

