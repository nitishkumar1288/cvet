package com.fluentcommerce.rule.fulfilment;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CalculateMaxRefundAmountAndUpdateOrderAttribute",
        description = "calculate the max refund amount and update at order attribute level",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)

@Slf4j
public class CalculateMaxRefundAmountAndUpdateOrderAttribute extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String fulfilmentId = event.getEntityId();
        double totalPrice = 0;
        double maxRefundableAmountForOrder = 0;
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfilmentId);
        if (null != fulfillmentById && null != fulfillmentById.order() &&CollectionUtils.isNotEmpty(fulfillmentById.attributes())){
            Optional<GetFulfilmentByIdQuery.Attribute1> optionalTotalPriceAttribute = fulfillmentById.attributes().stream().
                    filter(attribute1 -> attribute1.name().equalsIgnoreCase(TOTAL_PRICE_AT_FULFILMENT_LEVEL)).findFirst();
            if (optionalTotalPriceAttribute.isPresent()){
                totalPrice = Double.parseDouble(optionalTotalPriceAttribute.get().value().toString());
            }
            String orderId = event.getRootEntityId();
            Optional<GetFulfilmentByIdQuery.Attribute> maxRefundableAmountForOrderAtt =  fulfillmentById.order().attributes().stream().
                    filter(attribute -> attribute.name().equalsIgnoreCase(AVAILABLE_AMOUNT_TO_REFUND)).findFirst();

            if (maxRefundableAmountForOrderAtt.isPresent()){
                maxRefundableAmountForOrder = totalPrice + Double.parseDouble(maxRefundableAmountForOrderAtt.get().value().toString());
                updateOrderAttribute(orderId,maxRefundableAmountForOrder,context);
            }else{
                maxRefundableAmountForOrder = totalPrice;
                updateOrderAttribute(orderId,maxRefundableAmountForOrder,context);
            }
        }
    }

    private void updateOrderAttribute(String orderId, double maxRefundableAmountForOrder, ContextWrapper context) {

        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(AttributeInput.builder().name(AVAILABLE_AMOUNT_TO_REFUND)
                .type(ATTRIBUTE_TYPE_FLOAT).value((float) maxRefundableAmountForOrder).build());
        OrderService orderService = new OrderService(context);
        orderService.upsertOrderAttributeList(orderId,attributeInputList);
    }
}
