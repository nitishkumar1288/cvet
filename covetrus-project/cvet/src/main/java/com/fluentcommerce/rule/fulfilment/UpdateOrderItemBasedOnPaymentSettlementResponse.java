package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentSettlementResponse;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;
/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateOrderItemBasedOnPaymentSettlementResponse",
        description = "Update the order item attribute  with respect to payment settlement response .fetch the payment status " +
                "{" + SUCCESS_PAYMENT_STATUS + "} or failure {" + FAILURE_PAYMENT_STATUS + "}" + "and match with response to update the payment " +
                "transaction status and call to next event{" + PROP_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "event name"
)
@ParamString(
        name = SUCCESS_PAYMENT_STATUS,
        description = "success payment status"
)
@ParamString(
        name = FAILURE_PAYMENT_STATUS,
        description = "failure payment status"
)
@Slf4j
public class UpdateOrderItemBasedOnPaymentSettlementResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME,SUCCESS_PAYMENT_STATUS,FAILURE_PAYMENT_STATUS);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String successPaymentStatus = context.getProp(SUCCESS_PAYMENT_STATUS);
        String failurePaymentStatus = context.getProp(FAILURE_PAYMENT_STATUS);
        Event event = context.getEvent();
        String fulfilmentId = event.getEntityId();
        String orderId = event.getRootEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        String paymentSettlementCompleted = null;
        PaymentSettlementResponse paymentSettlementResponse = CommonUtils.convertObjectToDto(event.getAttributes(),
                new TypeReference<PaymentSettlementResponse>(){});
        if (Objects.nonNull(paymentSettlementResponse) && null != paymentSettlementResponse.getFulfilmentRef()) {
            if(successPaymentStatus.equalsIgnoreCase(paymentSettlementResponse.getStatus())){
                paymentSettlementCompleted = TRUE;
            }else if(failurePaymentStatus.equalsIgnoreCase(paymentSettlementResponse.getStatus())
                    || PAYMENT_AUTH_STATUS_NOT_ALLOWED.equalsIgnoreCase(paymentSettlementResponse.getStatus())){
                paymentSettlementCompleted = FALSE;
            }
            ArrayList<String> fulfilmentRefList = new ArrayList<>();
            fulfilmentRefList.add(paymentSettlementResponse.getFulfilmentRef());
            FulfilmentService fulfilmentService = new FulfilmentService(context);
            GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfilmentId);
            List<UpdateOrderItemWithOrderInput> updateItems = getOrderItemForUpdate(order,paymentSettlementCompleted,fulfillmentById);
            if (CollectionUtils.isNotEmpty(updateItems)){
                orderService.updateOrderItems(updateItems,orderId);
                EventUtils.forwardInline(context,eventName);
            }
        }
    }

    private List<UpdateOrderItemWithOrderInput> getOrderItemForUpdate(Order order,String paymentSettlementCompleted,
                                                                      GetFulfilmentByIdQuery.FulfilmentById fulfillmentById) {
        List<UpdateOrderItemWithOrderInput> updateItems = null;
        if (Objects.nonNull(fulfillmentById) && null != fulfillmentById.items() && CollectionUtils.isNotEmpty(fulfillmentById.items().edges())){
            updateItems = new ArrayList<>();
            for (GetFulfilmentByIdQuery.Edge itemEdge :fulfillmentById.items().edges()) {
                    Optional<OrderItem> optionalOrderItem = order.getItems().stream().filter(orderItem ->
                            orderItem.getRef().equalsIgnoreCase(itemEdge.item().ref())).findFirst();
                    if (optionalOrderItem.isPresent()){
                        String orderLineStatus = optionalOrderItem.get().getAttributes().get(ORDER_LINE_STATUS).toString();
                        if (!orderLineStatus.equalsIgnoreCase(CANCELLED)){
                            updateItems.add(UpdateOrderItemWithOrderInput.builder()
                                    .id(optionalOrderItem.get().getId())
                                    .attributes(getOrderItemAttribute(paymentSettlementCompleted))
                                    .build());
                        }
                    }
            }
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute(String status) {
        List<AttributeInput> itemAttributes = new ArrayList<>();
        itemAttributes.add(AttributeInput.builder()
                .name(IS_PAYMENT_SETTLEMENT_COMPLETED)
                .type(ATTRIBUTE_TYPE_STRING)
                .value(status)
                .build());
        return itemAttributes;
    }
}
