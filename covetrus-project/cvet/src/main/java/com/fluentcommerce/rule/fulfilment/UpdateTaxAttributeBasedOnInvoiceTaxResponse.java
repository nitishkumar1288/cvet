package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.TaxResponse;
import com.fluentcommerce.dto.fulfillment.TaxResponseItem;
import com.fluentcommerce.graphql.mutation.fulfillment.UpdateFulfilmentMutation;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateFulfilmentInput;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateTaxAttributeBasedOnInvoiceTaxResponse",
        description = "fetch the invoice tax response and update the fulfillment and order attribute ",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)

@EventAttribute(name = FULFILLMENT)
@Slf4j
public class UpdateTaxAttributeBasedOnInvoiceTaxResponse extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        Map<String, Object> eventAttributes = context.getEvent().getAttributes();
        TaxResponse taxResponse = CommonUtils.convertObjectToDto(eventAttributes.get(FULFILLMENT), new TypeReference<TaxResponse>() {
        });
        String fulfillmentId = event.getEntityId();
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfillmentId);
        if (Objects.nonNull(fulfillmentById) && null != taxResponse.getItems() && CollectionUtils.isNotEmpty(taxResponse.getItems())) {
            String orderId = fulfillmentById.order().id();
            OrderService orderService = new OrderService(context);
            Order order = orderService.getOrderById(orderId);
            List<UpdateOrderItemWithOrderInput> orderItemsForUpdate = fetchUpdatedTaxValueOrderItems(fulfillmentById, taxResponse);
            List<AttributeInput> fulfilmentAttributeListInputList = createFulfilmentAttributeList(taxResponse);
            if (CollectionUtils.isNotEmpty(fulfilmentAttributeListInputList)) {
                updateFulfilmentAttribute(fulfilmentAttributeListInputList, fulfillmentId, context);
            }
            if (CollectionUtils.isNotEmpty(orderItemsForUpdate)) {
                orderService.updateOrderItems(orderItemsForUpdate, order.getId());
            }
        }
    }


    private List<UpdateOrderItemWithOrderInput> fetchUpdatedTaxValueOrderItems(GetFulfilmentByIdQuery.FulfilmentById fulfillmentById,
                                                                               TaxResponse taxResponse) {
        List<UpdateOrderItemWithOrderInput> updateItems = new ArrayList<>();
        for (TaxResponseItem item : taxResponse.getItems()) {
            for (GetFulfilmentByIdQuery.Edge edge : fulfillmentById.items().edges()) {
                if (item.getRef().equalsIgnoreCase(edge.item().orderItem().ref()) && !edge.item().status().equalsIgnoreCase(DELETED_STATUS)) {
                    updateItems.add(UpdateOrderItemWithOrderInput.builder()
                            .id(edge.item().orderItem().id())
                            .attributes(getOrderItemAttribute(item))
                            .build());
                }
            }
        }
        return updateItems;
    }

    private List<AttributeInput> getOrderItemAttribute(TaxResponseItem item) {
        List<AttributeInput> attributeInputList = new ArrayList<>();
        if (null != item.getItemNonRetailTax()) {
            attributeInputList.add(AttributeInput.builder().name(ITEM_NON_RETAIL_TAX)
                    .type(ATTRIBUTE_TYPE_STRING).value((float) Double.parseDouble(item.getItemNonRetailTax())).build());
        }
        if (null != item.getTransactionFeePercentage()) {
            attributeInputList.add(AttributeInput.builder().name(TRANSACTION_FEE_PERCENTAGE)
                    .type(ATTRIBUTE_TYPE_STRING).value((float) Double.parseDouble(item.getTransactionFeePercentage())).build());
        }
        if (null != item.getPriceListCode()) {
            attributeInputList.add(AttributeInput.builder().name(PRICE_LIST_CODE)
                    .type(ATTRIBUTE_TYPE_STRING).value(item.getPriceListCode()).build());
        }
        if (null != item.getPracticeCost()) {
            attributeInputList.add(AttributeInput.builder().name(PRACTICE_COST)
                    .type(ATTRIBUTE_TYPE_STRING).value((float) Double.parseDouble(item.getPracticeCost())).build());
        }
        if (null != item.getDefaultPracticeCost()) {
            attributeInputList.add(AttributeInput.builder().name(DEFAULT_PRACTICE_COST)
                    .type(ATTRIBUTE_TYPE_STRING).value((float) Double.parseDouble(item.getDefaultPracticeCost())).build());
        }
        if (null != item.getTransactionFee()){
            attributeInputList.add(AttributeInput.builder().name(TRANSACTION_FEE)
                    .type(ATTRIBUTE_TYPE_STRING).value((float)Double.parseDouble(item.getTransactionFee())).build());
        }
        return attributeInputList;
    }

    private void updateFulfilmentAttribute(List<AttributeInput> fulfilmentAttributeListInputList, String fulfilmentId,
                                           ContextWrapper context) {
        UpdateFulfilmentInput fulfilmentInput = UpdateFulfilmentInput.builder()
                .id(fulfilmentId)
                .attributes(fulfilmentAttributeListInputList)
                .build();
        UpdateFulfilmentMutation fulfilmentMutation = UpdateFulfilmentMutation.builder().input(fulfilmentInput).build();
        context.action().mutation(fulfilmentMutation);
    }

    private List<AttributeInput> createFulfilmentAttributeList(TaxResponse taxResponse) {
        List<AttributeInput> attributeInputList = new ArrayList<>();
        if (null != taxResponse.getTaxStatus()) {
            attributeInputList.add(AttributeInput.builder().name(TAX_STATUS)
                    .type(ATTRIBUTE_TYPE_STRING).value(taxResponse.getTaxStatus()).build());
        }

        return attributeInputList;
    }

}