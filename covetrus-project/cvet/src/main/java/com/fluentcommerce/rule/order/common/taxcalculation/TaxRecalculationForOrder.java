package com.fluentcommerce.rule.order.common.taxcalculation;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author Nandhakumar
 */
@RuleInfo(
        name = "TaxRecalculationForOrder",
        description = "Recalculate the tax for the items which are not in status of {" + PROP_IGNORE_ORDER_ITEM_STATUS_LIST + "} and send the event {" + PROP_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_IGNORE_ORDER_ITEM_STATUS_LIST,
        description = "skip the item stats matching the list"
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)

@Slf4j
public class TaxRecalculationForOrder extends BaseRule {
    @Override
    public void run(ContextWrapper context) {

        String eventName = context.getProp(PROP_EVENT_NAME);
        List<String> ignoreOrderItemStatusList = context.getPropList(PROP_IGNORE_ORDER_ITEM_STATUS_LIST,String.class);

        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(orderId);

        // this is used for reduce the number of values after the decimal point
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        // Order level attributes
        double cosmeticSubtotalAtOrderLevel = 0;
        double subTotalAtOrderLevel = 0;
        double totalTaxPriceAtOrderLevel = 0;
        double totalItemDiscountsAtOrderLevel = 0;
        double totalDiscountsAtorderLevel = 0;
        double handlingFeeAtOrderLevel = 0;
        double handlingFeeTaxAtOrderLevel = 0;
        double deliveryCostAtOrderLevel = 0;
        double deliveryCostTaxAtOrderLevel = 0;

        boolean isTaxCalculationRequired = false;
        for(GetOrderByIdQuery.OrderItemEdge orderItemEdge : order.orderItems().orderItemEdge()){

            Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemStatus = orderItemEdge.orderItemNode().orderItemAttributes().stream().filter(
                    itemAttr -> itemAttr.name().equalsIgnoreCase(ORDER_LINE_STATUS)).findFirst();

            if(optionalOrderItemStatus.isPresent() && !ignoreOrderItemStatusList.contains(optionalOrderItemStatus.get().value().toString())) {
                isTaxCalculationRequired = true;

                double totalPriceAtItemLevel = orderItemEdge.orderItemNode().totalPrice();
                double totalTaxPriceAtItemLevel = orderItemEdge.orderItemNode().totalTaxPrice();

                Optional<GetOrderByIdQuery.OrderItemAttribute> optionalTotalPaidPrice =
                        orderItemEdge.orderItemNode().orderItemAttributes().stream().filter(
                                orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(LINE_ITEM_TOTAL_PAID_PRICE)
                        ).findFirst();

                Optional<GetOrderByIdQuery.OrderItemAttribute> optionalTotalProratedOrderDiscount =
                        orderItemEdge.orderItemNode().orderItemAttributes().stream().filter(
                                orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(LINE_ITEM_TOTAL_PRORATED_ORDER_DISCOUNT)
                        ).findFirst();

                Optional<GetOrderByIdQuery.OrderItemAttribute> optionalHandlingFee =
                        orderItemEdge.orderItemNode().orderItemAttributes().stream().filter(
                                orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(HANDLING_FEE)
                        ).findFirst();

                Optional<GetOrderByIdQuery.OrderItemAttribute> optionalHandlingFeeTax =
                        orderItemEdge.orderItemNode().orderItemAttributes().stream().filter(
                                orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(HANDLING_FEE_TAX)
                        ).findFirst();

                Optional<GetOrderByIdQuery.OrderItemAttribute> optionalDeliveryCost =
                        orderItemEdge.orderItemNode().orderItemAttributes().stream().filter(
                                orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(LINE_ITEM_DELIVERY_COST)
                        ).findFirst();

                Optional<GetOrderByIdQuery.OrderItemAttribute> optionalDeliveryCostTax =
                        orderItemEdge.orderItemNode().orderItemAttributes().stream().filter(
                                orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(LINE_ITEM_DELIVERY_COST_TAX)
                        ).findFirst();


                if (optionalTotalPaidPrice.isPresent()) {

                    // 1) Cumulate all item level actual price
                    cosmeticSubtotalAtOrderLevel = cosmeticSubtotalAtOrderLevel + totalPriceAtItemLevel;

                    // 2) Cumulate all item level paid price
                    subTotalAtOrderLevel = subTotalAtOrderLevel +  Double.parseDouble(optionalTotalPaidPrice.get().value().toString());

                    // 3) Cumulate all item level discounts
                    totalItemDiscountsAtOrderLevel = totalItemDiscountsAtOrderLevel + (totalPriceAtItemLevel - Double.parseDouble(optionalTotalPaidPrice.get().value().toString()));

                    // 4) Cumulate all order level discounts prorated at item level
                    totalDiscountsAtorderLevel = getTotalDiscountsAtorderLevel(totalDiscountsAtorderLevel, optionalTotalProratedOrderDiscount);

                    // 5) Cumulate all item level handling fee
                    handlingFeeAtOrderLevel = getHandlingFeeAtOrderLevel(handlingFeeAtOrderLevel, optionalHandlingFee);

                    // 6) Cumulate all item level handling fee tax
                    handlingFeeTaxAtOrderLevel = getHandlingFeeTaxAtOrderLevel(handlingFeeTaxAtOrderLevel, optionalHandlingFeeTax);

                    // 7) Cumulate all item level delivery cost
                    deliveryCostAtOrderLevel = getDeliveryCostAtOrderLevel(deliveryCostAtOrderLevel, optionalDeliveryCost);

                    // 8) Cumulate all item level delivery cost tax
                    deliveryCostTaxAtOrderLevel = getDeliveryCostTaxAtOrderLevel(deliveryCostTaxAtOrderLevel, optionalDeliveryCostTax);

                    // 9) Cumulate all item level tax
                    totalTaxPriceAtOrderLevel = totalTaxPriceAtOrderLevel + totalTaxPriceAtItemLevel;

                } else {
                    context.addLog("Mandatory Attribute is not present and hence tax can not be calculated");
                    return;
                }
            }
        }

        if(isTaxCalculationRequired) {

            // 10) calculate total order tax
            double totalTaxPriceAtOrderLevelIncludingOtherTax = totalTaxPriceAtOrderLevel + handlingFeeTaxAtOrderLevel + deliveryCostTaxAtOrderLevel;

            // 11) calculate total price of the order
            double totalPriceAtOrderLevel = cosmeticSubtotalAtOrderLevel + totalTaxPriceAtOrderLevelIncludingOtherTax  + handlingFeeAtOrderLevel + deliveryCostAtOrderLevel - (totalItemDiscountsAtOrderLevel + totalDiscountsAtorderLevel);

            // 12)  Cumulate all item level productDiscounts and totalDiscountsAtorderLevel
            double cumulativeDiscount = totalItemDiscountsAtOrderLevel + totalDiscountsAtorderLevel;


            // add all the values calculated to the order attributes
            List<AttributeInput> attributeInputList = new ArrayList<>();

            attributeInputList.add(AttributeInput.builder().name(COSMETIC_SUB_TOTAL_ORDER_LEVEL)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float) cosmeticSubtotalAtOrderLevel).build());

            attributeInputList.add(AttributeInput.builder().name(SUB_TOTAL_ORDER_LEVEL)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float) subTotalAtOrderLevel).build());

            attributeInputList.add(AttributeInput.builder().name(PRODUCT_DISCOUNTS_ORDER_LEVEL)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float) totalItemDiscountsAtOrderLevel).build());

            attributeInputList.add(AttributeInput.builder().name(ORDER_DISCOUNTS_ORDER_LEVEL)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float) totalDiscountsAtorderLevel).build());

            attributeInputList.add(AttributeInput.builder().name(TOTAL_DISCOUNTS_AT_ORDER_LEVEL)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float) cumulativeDiscount).build());

            attributeInputList.add(AttributeInput.builder().name(HANDLING_FEE)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float) handlingFeeAtOrderLevel).build());

            attributeInputList.add(AttributeInput.builder().name(HANDLING_FEE_TAX)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float) handlingFeeTaxAtOrderLevel).build());

            attributeInputList.add(AttributeInput.builder().name(DELIVER_COST_ORDER_LEVEL)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float) deliveryCostAtOrderLevel).build());

            attributeInputList.add(AttributeInput.builder().name(DELIVER_COST_TAX_ORDER_LEVEL)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float) deliveryCostTaxAtOrderLevel).build());

            attributeInputList.add(AttributeInput.builder().name(TOTAL_LINE_ITEM_TAX_ORDER_LEVEL)
                    .type(ATTRIBUTE_TYPE_FLOAT).value((float) totalTaxPriceAtOrderLevel).build());


            UpdateOrderInput orderInput = UpdateOrderInput.builder()
                    .id(orderId)
                    .totalTaxPrice(totalTaxPriceAtOrderLevelIncludingOtherTax)
                    .totalPrice(Double.valueOf(decimalFormat.format(totalPriceAtOrderLevel)))
                    .attributes(attributeInputList)
                    .build();

            UpdateOrderMutation orderMutation = UpdateOrderMutation.builder().input(orderInput).build();
            context.action().mutation(orderMutation);
        }else{
            context.addLog("Order is cancelled , tax calculation can not be done");
        }

        EventUtils.forwardInline(
                context,
                eventName
        );

    }

    private static double getDeliveryCostTaxAtOrderLevel(double deliveryCostTaxAtOrderLevel, Optional<GetOrderByIdQuery.OrderItemAttribute> optionalDeliveryCostTax) {
        if(optionalDeliveryCostTax.isPresent()){
            deliveryCostTaxAtOrderLevel = deliveryCostTaxAtOrderLevel + Double.parseDouble(optionalDeliveryCostTax.get().value().toString());
        }
        return deliveryCostTaxAtOrderLevel;
    }

    private static double getDeliveryCostAtOrderLevel(double deliveryCostAtOrderLevel, Optional<GetOrderByIdQuery.OrderItemAttribute> optionalDeliveryCost) {
        if(optionalDeliveryCost.isPresent()){
            deliveryCostAtOrderLevel = deliveryCostAtOrderLevel + Double.parseDouble(optionalDeliveryCost.get().value().toString());
        }
        return deliveryCostAtOrderLevel;
    }

    private static double getHandlingFeeTaxAtOrderLevel(double handlingFeeTaxAtOrderLevel, Optional<GetOrderByIdQuery.OrderItemAttribute> optionalHandlingFeeTax) {
        if(optionalHandlingFeeTax.isPresent()){
            handlingFeeTaxAtOrderLevel = handlingFeeTaxAtOrderLevel + Double.parseDouble(optionalHandlingFeeTax.get().value().toString());
        }
        return handlingFeeTaxAtOrderLevel;
    }

    private static double getHandlingFeeAtOrderLevel(double handlingFeeAtOrderLevel, Optional<GetOrderByIdQuery.OrderItemAttribute> optionalHandlingFee) {
        if(optionalHandlingFee.isPresent()){
            handlingFeeAtOrderLevel = handlingFeeAtOrderLevel + Double.parseDouble(optionalHandlingFee.get().value().toString());
        }
        return handlingFeeAtOrderLevel;
    }

    private static double getTotalDiscountsAtorderLevel(double totalDiscountsAtorderLevel, Optional<GetOrderByIdQuery.OrderItemAttribute> optionalTotalProratedOrderDiscount) {
        if(optionalTotalProratedOrderDiscount.isPresent()) {
            totalDiscountsAtorderLevel = totalDiscountsAtorderLevel + Double.parseDouble(optionalTotalProratedOrderDiscount.get().value().toString());
        }
        return totalDiscountsAtorderLevel;
    }
}