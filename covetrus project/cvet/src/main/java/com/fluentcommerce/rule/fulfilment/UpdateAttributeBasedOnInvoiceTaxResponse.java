package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.TaxResponse;
import com.fluentcommerce.dto.fulfillment.TaxResponseItem;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateAttributeBasedOnInvoiceTaxResponse",
        description = "fetch the invoice tax response and update the fulfillment and order attribute ",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)

@EventAttribute(name = FULFILLMENT)
@Slf4j
public class UpdateAttributeBasedOnInvoiceTaxResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        TaxResponse taxResponse = CommonUtils.convertObjectToDto(eventAttributes.get(FULFILLMENT),new TypeReference<TaxResponse>(){});
        String fulfillmentId = event.getEntityId();
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfillmentId);
        if (Objects.nonNull(fulfillmentById)){
            String orderId = fulfillmentById.order().id();
            OrderService orderService = new OrderService(context);
            Order order = orderService.getOrderById(orderId);
            List<UpdateOrderItemWithOrderInput> orderItemsForUpdate = fetchUpdatedTaxValueOrderItems(fulfillmentById,taxResponse);
            if (CollectionUtils.isNotEmpty(orderItemsForUpdate)){
                orderService.updateOrderItems(orderItemsForUpdate,order.getId());
            }
        }
    }
    private List<UpdateOrderItemWithOrderInput> fetchUpdatedTaxValueOrderItems(GetFulfilmentByIdQuery.FulfilmentById fulfillmentById,
                                                                               TaxResponse taxResponse) {
        List<UpdateOrderItemWithOrderInput> orderItemList = new ArrayList<>();
        for(TaxResponseItem item:taxResponse.getItems()){
            boolean toUpdate = false;
            for(GetFulfilmentByIdQuery.Edge edge :fulfillmentById.items().edges()){
                if(item.getRef().equalsIgnoreCase(edge.item().orderItem().ref()) && !edge.item().status().equalsIgnoreCase(DELETED_STATUS)){
                    UpdateOrderItemWithOrderInput.Builder builder = UpdateOrderItemWithOrderInput.builder();
                    toUpdate = isToUpdate(
                            item,
                            toUpdate,
                            edge,
                            builder);

                    if (toUpdate){
                        builder.id(edge.item().orderItem().id());
                        orderItemList.add(builder.build());
                    }
                }
            }
        }
        return orderItemList;
    }

    private boolean isToUpdate(
            TaxResponseItem item,
            boolean toUpdate,
            GetFulfilmentByIdQuery.Edge edge,
            UpdateOrderItemWithOrderInput.Builder builder) {
        if (Double.parseDouble(item.getTotalTax()) <
                Double.parseDouble(edge.item().orderItem().totalTaxPrice().toString())){
            builder.totalTaxPrice(Double.parseDouble(item.getTotalTax()));
            toUpdate = true;
        }
        List<AttributeInput> attributes = fetchUpdatedOrderItemAttribute(item, edge);
        if (CollectionUtils.isNotEmpty(attributes)){
            builder.attributes(attributes);
            toUpdate = true;
        }
        return toUpdate;
    }

    private List<AttributeInput> fetchUpdatedOrderItemAttribute(TaxResponseItem item, GetFulfilmentByIdQuery.Edge edge) {
        List<AttributeInput> attributeInputList = new ArrayList<>();
        if (Double.parseDouble(item.getHandlingTax())<getAttributeValue(HANDLING_FEE_TAX,edge)){
            attributeInputList.add(AttributeInput.builder().name(HANDLING_FEE_TAX)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float)Double.parseDouble(item.getHandlingTax())).build());
        }
        if (Double.parseDouble(item.getShippingTax())<getAttributeValue(LINE_ITEM_DELIVERY_COST_TAX,edge)){
            attributeInputList.add(AttributeInput.builder().name(LINE_ITEM_DELIVERY_COST_TAX)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float)Double.parseDouble(item.getShippingTax())).build());
        }
        return attributeInputList;
    }

    private double getAttributeValue(String attributeName, GetFulfilmentByIdQuery.Edge edge) {
        double attributeValue = 0;
        Optional<GetFulfilmentByIdQuery.Attribute2> attribute = edge.item().orderItem().attributes().stream().
                filter(attribute2 -> attribute2.name().equalsIgnoreCase(attributeName)).findFirst();
        if (attribute.isPresent()){
            attributeValue = Double.parseDouble(attribute.get().value().toString());
        }
        return attributeValue;
    }
}