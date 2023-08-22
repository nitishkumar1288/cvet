package com.fluentcommerce.rule.order.paymentauth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.TaxResponse;
import com.fluentcommerce.dto.fulfillment.TaxResponseItem;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
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
        name = "UpdateAttributeBasedOnSalesTaxResponse",
        description = "fetch the tax response and update the order attribute ",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
public class UpdateAttributeBasedOnSalesTaxResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        TaxResponse taxResponse = CommonUtils.convertObjectToDto(event.getAttributes(),new TypeReference<TaxResponse>(){});
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(orderId);
        if (null != order && null != taxResponse){
            updateOrderItems(taxResponse,order,orderService);
        }
    }

    private void updateOrderItems(TaxResponse taxResponse, GetOrderByIdQuery.OrderById order, OrderService orderService) {
        List<UpdateOrderItemWithOrderInput> updateItems = getOrderItemsForUpdate(order,taxResponse);
        List<AttributeInput> orderAttributeListInputList = createOrderAttributeList(taxResponse);
        if (null != updateItems && !updateItems.isEmpty()){
            orderService.updateOrderAttributeWithOrderItems(updateItems,order.id(),orderAttributeListInputList);
        }
    }

    private List<AttributeInput> createOrderAttributeList(TaxResponse taxResponse) {
        List<AttributeInput> attributeInputList = new ArrayList<>();
        if (null != taxResponse.getTaxStatus()){
            attributeInputList.add(AttributeInput.builder().name(TAX_STATUS)
                    .type(ATTRIBUTE_TYPE_STRING).value(taxResponse.getTaxStatus()).build());
        }
        return attributeInputList;
    }

    private List<UpdateOrderItemWithOrderInput> getOrderItemsForUpdate(GetOrderByIdQuery.OrderById order, TaxResponse taxResponse) {
        List<UpdateOrderItemWithOrderInput> updateItems = null;
        if (Objects.nonNull(order) && null != order.orderItems() && CollectionUtils.isNotEmpty(order.orderItems().orderItemEdge())&&
                Objects.nonNull(taxResponse) && null != taxResponse.getItems() && !taxResponse.getItems().isEmpty()){
            updateItems = new ArrayList<>();
            for(TaxResponseItem item:taxResponse.getItems()){
                Optional<GetOrderByIdQuery.OrderItemEdge> orderLineItem = order.orderItems().orderItemEdge().stream().filter(
                        orderItem -> orderItem.orderItemNode().ref().equalsIgnoreCase(item.getRef())
                ).findFirst();
                if(orderLineItem.isPresent() && null != item.getTotalTax()){
                    Optional<GetOrderByIdQuery.OrderItemAttribute> itemStatus = orderLineItem.get().orderItemNode().
                            orderItemAttributes().stream().filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_LINE_STATUS)).findFirst();
                    if (itemStatus.isPresent() && !itemStatus.get().name().equalsIgnoreCase(CANCELLED)){
                        updateItems.add(UpdateOrderItemWithOrderInput.builder()
                                .id(orderLineItem.get().orderItemNode().id())
                                .attributes(getOrderItemAttribute(item))
                                .totalTaxPrice(Double.parseDouble(item.getTotalTax()))
                                .build());
                    }
                }
            }
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute(TaxResponseItem item) {
        List<AttributeInput> attributeInputList = new ArrayList<>();
        if (null != item.getHandlingTax()){
            attributeInputList.add(AttributeInput.builder().name(HANDLING_FEE_TAX)
                    .type(ATTRIBUTE_TYPE_STRING).value((float)Double.parseDouble(item.getHandlingTax())).build());
        }
        if (null != item.getShippingTax()){
            attributeInputList.add(AttributeInput.builder().name(LINE_ITEM_DELIVERY_COST_TAX)
                    .type(ATTRIBUTE_TYPE_STRING).value((float)Double.parseDouble(item.getShippingTax())).build());
        }
        if (null != item.getItemNonRetailTax()){
            attributeInputList.add(AttributeInput.builder().name(ITEM_NON_RETAIL_TAX)
                    .type(ATTRIBUTE_TYPE_STRING).value((float)Double.parseDouble(item.getItemNonRetailTax())).build());
        }
        if (null != item.getTransactionFeePercentage()){
            attributeInputList.add(AttributeInput.builder().name(TRANSACTION_FEE_PERCENTAGE)
                    .type(ATTRIBUTE_TYPE_STRING).value((float)Double.parseDouble(item.getTransactionFeePercentage())).build());
        }
        if (null != item.getPriceListCode()){
            attributeInputList.add(AttributeInput.builder().name(PRICE_LIST_CODE)
                    .type(ATTRIBUTE_TYPE_STRING).value(item.getPriceListCode()).build());
        }
        if (null != item.getPracticeCost()){
            attributeInputList.add(AttributeInput.builder().name(PRACTICE_COST)
                    .type(ATTRIBUTE_TYPE_STRING).value((float)Double.parseDouble(item.getPracticeCost())).build());
        }
        if (null != item.getDefaultPracticeCost()){
            attributeInputList.add(AttributeInput.builder().name(DEFAULT_PRACTICE_COST)
                    .type(ATTRIBUTE_TYPE_STRING).value((float)Double.parseDouble(item.getDefaultPracticeCost())).build());
        }
        if (null != item.getTransactionFee()){
            attributeInputList.add(AttributeInput.builder().name(TRANSACTION_FEE)
                    .type(ATTRIBUTE_TYPE_STRING).value((float)Double.parseDouble(item.getTransactionFee())).build());
        }
        return attributeInputList;
    }
}
