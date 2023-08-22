package com.fluentcommerce.util.returnorderhelper;

import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author Alex L.
 */
@Slf4j
public class GqlQueryHelper {
    private final Context context;

    public GqlQueryHelper(Context context) {
        this.context = context;
    }

    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isEmptyMap(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }


    public Map<String, GetOrderByIdQuery.OrderItemNode> getAllOrderItemsByOrderItemRef(GetOrderByIdQuery.Data getOrderData) {
        return getAllOrderItems(getOrderData).stream().collect(Collectors.toMap(GetOrderByIdQuery.OrderItemNode::ref, Function.identity()));
    }

    public Set<GetOrderByIdQuery.OrderItemNode> getAllOrderItems(GetOrderByIdQuery.Data getOrderData) {
        Set<GetOrderByIdQuery.OrderItemNode> result = new HashSet<>();
        if (getOrderData.orderById() == null || getOrderData.orderById().orderItems() == null ||
                isEmpty(getOrderData.orderById().orderItems().orderItemEdge())) {
            return result;
        }
        getOrderData.orderById().orderItems().orderItemEdge().stream().map(GetOrderByIdQuery.OrderItemEdge::orderItemNode).forEach(result::add);
        return result;
    }
}
