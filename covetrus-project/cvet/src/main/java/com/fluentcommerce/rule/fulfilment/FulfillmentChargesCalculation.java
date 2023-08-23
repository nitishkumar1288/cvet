package com.fluentcommerce.rule.fulfilment;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.mutation.fulfillment.UpdateFulfilmentMutation;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateFulfilmentInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;
/**
 @author nitishKumar
 */

@RuleInfo(
        name = "FulfillmentChargesCalculation",
        description = "calculate the fulfillment charges and persist them at attribute level }",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_FULFILMENT)
        }
)

@Slf4j
public class FulfillmentChargesCalculation extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        List<String> ignoreOrderItemStatusList = context.getPropList(IGNORE_ORDER_ITEM_STATUS_LIST,String.class);
        String fulfillmentId = event.getEntityId();
        context.addLog("FulfillmentChargesCalculation :: fulfillmentId from event::"+fulfillmentId);
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfilmentByIdQuery.FulfilmentById fulfillmentById = fulfilmentService.getFulfillmentById(fulfillmentId);

        // this is used for reduce the number of values after the decimal point
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        double subTotalAtFulfilmentLevel = 0;
        double totalTaxPriceAtFulfilmentLevel = 0;
        double totalItemDiscountsAtFulfilmentLevel = 0;
        double totalDiscountsAtFulfilmentLevel = 0;
        double handlingFeeAtFulfilmentLevel = 0;
        double handlingFeeTaxAtFulfilmentLevel = 0;
        double deliveryCostAtFulfilmentLevel = 0;
        double deliveryCostTaxAtFulfilmentLevel = 0;
        boolean isTaxCalculationRequired = false;

        if (Objects.nonNull(fulfillmentById) && null != fulfillmentById.items() && null != fulfillmentById.items().edges()
                && !fulfillmentById.items().edges().isEmpty() ){
            for (GetFulfilmentByIdQuery.Edge itemEdge :fulfillmentById.items().edges()){
                Optional<GetFulfilmentByIdQuery.Attribute2> orderItemStatusAttribute = itemEdge.item().orderItem().
                        attributes().stream().filter(attribute1 -> attribute1.name().equalsIgnoreCase(ORDER_LINE_STATUS)).findFirst();
                if (itemEdge.item().orderItem().quantity()>0 && orderItemStatusAttribute.isPresent() &&
                        !ignoreOrderItemStatusList.contains(orderItemStatusAttribute.get().value().toString())) {
                    isTaxCalculationRequired = true ;
                    double totalPriceAtItemLevel = itemEdge.item().orderItem().quantity() * itemEdge.item().orderItem().price();

                    //1. cumulate all item price to generate subtotal without discount applied at fulfilment level
                    subTotalAtFulfilmentLevel = subTotalAtFulfilmentLevel + totalPriceAtItemLevel;

                    totalTaxPriceAtFulfilmentLevel = totalTaxPriceAtFulfilmentLevel + itemEdge.item().orderItem().totalTaxPrice();

                    Optional<GetFulfilmentByIdQuery.Attribute2> totalPaidPriceAttribute = itemEdge.item().
                            orderItem().attributes().stream().filter(orderItemAttribute -> orderItemAttribute.name()
                                    .equalsIgnoreCase(LINE_ITEM_TOTAL_PAID_PRICE)).findFirst();

                    Optional<GetFulfilmentByIdQuery.Attribute2> handlingFeeAttribute = itemEdge.item().
                            orderItem().attributes().stream().filter(orderItemAttribute -> orderItemAttribute.name()
                                    .equalsIgnoreCase(HANDLING_FEE)).findFirst();

                    Optional<GetFulfilmentByIdQuery.Attribute2> handlingFeeTaxAttribute = itemEdge.item().
                            orderItem().attributes().stream().filter(orderItemAttribute -> orderItemAttribute.name()
                                    .equalsIgnoreCase(HANDLING_FEE_TAX)).findFirst();

                    Optional<GetFulfilmentByIdQuery.Attribute2> deliveryCostAttribute = itemEdge.item().
                            orderItem().attributes().stream().filter(orderItemAttribute -> orderItemAttribute.name()
                                    .equalsIgnoreCase(LINE_ITEM_DELIVERY_COST)).findFirst();

                    Optional<GetFulfilmentByIdQuery.Attribute2> deliveryCostTaxAttribute = itemEdge.item().
                            orderItem().attributes().stream().filter(orderItemAttribute -> orderItemAttribute.name()
                                    .equalsIgnoreCase(LINE_ITEM_DELIVERY_COST_TAX)).findFirst();

                    Optional<GetFulfilmentByIdQuery.Attribute2> totalProratedOrderDiscount = itemEdge.item().
                            orderItem().attributes().stream().filter(orderItemAttribute -> orderItemAttribute.name()
                                    .equalsIgnoreCase(LINE_ITEM_TOTAL_PRORATED_ORDER_DISCOUNT)).findFirst();

                    if (totalPaidPriceAttribute.isPresent()) {
                        //2. subtract total paid price from total price for an item and cumulate them to generate total item discount at fulfilment level
                        totalItemDiscountsAtFulfilmentLevel = totalItemDiscountsAtFulfilmentLevel + (totalPriceAtItemLevel - Double.parseDouble(totalPaidPriceAttribute.get().value().toString()));

                        //5. cumulate all item handling fee to generate handlingFee at fulfilment level
                        handlingFeeAtFulfilmentLevel = getHandlingFeeAtFulfilmentLevel(handlingFeeAtFulfilmentLevel, handlingFeeAttribute);

                        //6. cumulate all item handling fee tax to generate handlingFeeTax at fulfilment level
                        handlingFeeTaxAtFulfilmentLevel = getHandlingFeeTaxAtFulfilmentLevel(handlingFeeTaxAtFulfilmentLevel, handlingFeeTaxAttribute);

                        //7. cumulate all item delivery cost to generate deliveryCost at fulfilment level
                        deliveryCostAtFulfilmentLevel = getDeliveryCostAtFulfilmentLevel(deliveryCostAtFulfilmentLevel, deliveryCostAttribute);

                        //8. cumulate all item delivery cost tax to generate deliveryCostTax at fulfilment level
                        deliveryCostTaxAtFulfilmentLevel = getDeliveryCostTaxAtFulfilmentLevel(deliveryCostTaxAtFulfilmentLevel, deliveryCostTaxAttribute);

                        //4. cumulate all item totalProratedOrderDiscount to generate productDiscounts at fulfilment level
                        totalDiscountsAtFulfilmentLevel = getTotalDiscountsAtFulfilmentLevel(totalDiscountsAtFulfilmentLevel, totalProratedOrderDiscount);
                    } else {
                        context.addLog("Mandatory Attribute is not present and hence fulfillment tax can not be calculated for the orderItem::"
                                + itemEdge.item().toString());
                        return;
                    }
                }
            }
            if (isTaxCalculationRequired) {
                //9. add total tax at fulfilment level + handling fee tax +  shipping tax to generate total tax at fulfilment level
                double totalTaxPriceAtFulfilmentLevelIncludingOtherTax = totalTaxPriceAtFulfilmentLevel + handlingFeeTaxAtFulfilmentLevel + deliveryCostTaxAtFulfilmentLevel;
                //10. add total price at fulfilment level + total tax -( item level discount + order level discount )
                double totalPriceAtFulfilmentLevel = subTotalAtFulfilmentLevel + totalTaxPriceAtFulfilmentLevelIncludingOtherTax + handlingFeeAtFulfilmentLevel + deliveryCostAtFulfilmentLevel - (totalItemDiscountsAtFulfilmentLevel + totalDiscountsAtFulfilmentLevel);
                //11. add item level + order level discount to generate total discount at fulfilment level
                double cumulativeDiscount = totalItemDiscountsAtFulfilmentLevel + totalDiscountsAtFulfilmentLevel;

                // create the fulfillment attribute
                List<AttributeInput> attributeInputList = new ArrayList<>();

                attributeInputList.add(AttributeInput.builder().name(SUB_TOTAL_AT_FULFILMENT_LEVEL)
                        .type(ATTRIBUTE_TYPE_FLOAT).value((float)subTotalAtFulfilmentLevel).build());

                attributeInputList.add(AttributeInput.builder().name(PRODUCT_DISCOUNTS_AT_FULFILMENT_LEVEL)
                        .type(ATTRIBUTE_TYPE_FLOAT).value((float)totalItemDiscountsAtFulfilmentLevel).build());

                attributeInputList.add(AttributeInput.builder().name(ORDER_DISCOUNTS_AT_FULFILMENT_LEVEL)
                        .type(ATTRIBUTE_TYPE_FLOAT).value((float)totalDiscountsAtFulfilmentLevel).build());

                attributeInputList.add(AttributeInput.builder().name(HANDLING_FEE)
                        .type(ATTRIBUTE_TYPE_FLOAT).value((float)handlingFeeAtFulfilmentLevel).build());

                attributeInputList.add(AttributeInput.builder().name(HANDLING_FEE_TAX)
                        .type(ATTRIBUTE_TYPE_FLOAT).value((float)handlingFeeTaxAtFulfilmentLevel).build());

                attributeInputList.add(AttributeInput.builder().name(DELIVERY_COST_AT_FULFILMENT_LEVEL)
                        .type(ATTRIBUTE_TYPE_FLOAT).value((float)deliveryCostAtFulfilmentLevel).build());

                attributeInputList.add(AttributeInput.builder().name(DELIVERY_COST_TAX_AT_FULFILMENT_LEVEL)
                        .type(ATTRIBUTE_TYPE_FLOAT).value((float)deliveryCostTaxAtFulfilmentLevel).build());

                attributeInputList.add(AttributeInput.builder().name(TOTAL_TAX_AT_FULFILMENT_LEVEL)
                        .type(ATTRIBUTE_TYPE_FLOAT).value((float)totalTaxPriceAtFulfilmentLevelIncludingOtherTax).build());

                attributeInputList.add(AttributeInput.builder().name(TOTAL_PRICE_AT_FULFILMENT_LEVEL)
                        .type(ATTRIBUTE_TYPE_FLOAT).value(Double.valueOf(decimalFormat.format(totalPriceAtFulfilmentLevel))).build());

                attributeInputList.add(AttributeInput.builder().name(TOTAL_DISCOUNTS_AT_FULFILMENT_LEVEL)
                        .type(ATTRIBUTE_TYPE_FLOAT).value((float)cumulativeDiscount).build());

                // update the fulfillment attribute
                UpdateFulfilmentInput fulfilmentInput = UpdateFulfilmentInput.builder()
                        .id(fulfillmentId)
                        .attributes(attributeInputList)
                        .build();
                UpdateFulfilmentMutation fulfilmentMutation = UpdateFulfilmentMutation.builder().input(fulfilmentInput).build();
                context.action().mutation(fulfilmentMutation);
            }
        }
    }

    private static double getTotalDiscountsAtFulfilmentLevel(double totalDiscountsAtFulfilmentLevel, Optional<GetFulfilmentByIdQuery.Attribute2> totalProratedOrderDiscount) {
        if (totalProratedOrderDiscount.isPresent()) {
            totalDiscountsAtFulfilmentLevel = totalDiscountsAtFulfilmentLevel + Double.parseDouble(totalProratedOrderDiscount.get().value().toString());
        }
        return totalDiscountsAtFulfilmentLevel;
    }

    private static double getDeliveryCostTaxAtFulfilmentLevel(double deliveryCostTaxAtFulfilmentLevel, Optional<GetFulfilmentByIdQuery.Attribute2> deliveryCostTaxAttribute) {
        if (deliveryCostTaxAttribute.isPresent()) {
            deliveryCostTaxAtFulfilmentLevel = deliveryCostTaxAtFulfilmentLevel + Double.parseDouble(deliveryCostTaxAttribute.get().value().toString());
        }
        return deliveryCostTaxAtFulfilmentLevel;
    }

    private static double getDeliveryCostAtFulfilmentLevel(double deliveryCostAtFulfilmentLevel, Optional<GetFulfilmentByIdQuery.Attribute2> deliveryCostAttribute) {
        if (deliveryCostAttribute.isPresent()) {
            deliveryCostAtFulfilmentLevel = deliveryCostAtFulfilmentLevel + Double.parseDouble(deliveryCostAttribute.get().value().toString());
        }
        return deliveryCostAtFulfilmentLevel;
    }

    private static double getHandlingFeeTaxAtFulfilmentLevel(double handlingFeeTaxAtFulfilmentLevel, Optional<GetFulfilmentByIdQuery.Attribute2> handlingFeeTaxAttribute) {
        if (handlingFeeTaxAttribute.isPresent()) {
            handlingFeeTaxAtFulfilmentLevel = handlingFeeTaxAtFulfilmentLevel + Double.parseDouble(handlingFeeTaxAttribute.get().value().toString());
        }
        return handlingFeeTaxAtFulfilmentLevel;
    }

    private static double getHandlingFeeAtFulfilmentLevel(double handlingFeeAtFulfilmentLevel, Optional<GetFulfilmentByIdQuery.Attribute2> handlingFeeAttribute) {
        if (handlingFeeAttribute.isPresent()) {
            handlingFeeAtFulfilmentLevel = handlingFeeAtFulfilmentLevel + Double.parseDouble(handlingFeeAttribute.get().value().toString());
        }
        return handlingFeeAtFulfilmentLevel;
    }
}