package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author nitishKumar
 */

@RuleInfo(
        name = "TaxRecalculationForReturnOrder",
        description = "recalculate the tax amount for the return order items and send the event {" + PROP_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_CREDIT_MEMO)
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)
@Slf4j
public class TaxRecalculationForReturnOrder extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        String creditMemoRef = event.getEntityRef();
        CreditMemoService creditMemoService = new CreditMemoService(context);
        GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(creditMemoRef);
        double cosmeticSubtotalAtOrderLevel = 0;
        double subTotalAtOrderLevel = 0;
        double totalTaxPriceAtOrderLevel = 0;
        double totalItemDiscountsAtOrderLevel = 0;
        double totalDiscountsAtorderLevel = 0;
        double handlingFeeAtOrderLevel = 0;
        double handlingFeeTaxAtOrderLevel = 0;
        double deliveryCostAtOrderLevel = 0;
        double deliveryCostTaxAtOrderLevel = 0;
        if (null != creditMemo) {
            String orderRef = creditMemo.order().ref();
            OrderService orderService = new OrderService(context);
            GetOrdersByRefQuery.Edge orderEdge = orderService.getOrdersByRef(orderRef);
            for (GetCreditMemoByRefQuery.Edge edge : creditMemo.items().edges()) {
                Optional<GetOrdersByRefQuery.ItemEdge> orderItem = orderEdge.node().items().itemEdges().stream().
                        filter(itemEdge -> itemEdge.itemNode().ref().equalsIgnoreCase(edge.node().orderItem().ref())).findFirst();
                if (orderItem.isPresent()) {
                    GetOrdersByRefQuery.ItemEdge orderItemEdge = orderItem.get();
                    double totalPriceAtItemLevel = orderItemEdge.itemNode().totalPrice();
                    double totalTaxPriceAtItemLevel = orderItemEdge.itemNode().totalTaxPrice();
                    Optional<GetOrdersByRefQuery.Attribute1> optionalTotalPaidPrice =
                            orderItemEdge.itemNode().attributes().stream().filter(
                                    orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(LINE_ITEM_TOTAL_PAID_PRICE)
                            ).findFirst();

                    Optional<GetOrdersByRefQuery.Attribute1> optionalTotalProratedOrderDiscount =
                            orderItemEdge.itemNode().attributes().stream().filter(
                                    orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(LINE_ITEM_TOTAL_PRORATED_ORDER_DISCOUNT)
                            ).findFirst();

                    Optional<GetOrdersByRefQuery.Attribute1> optionalHandlingFee =
                            orderItemEdge.itemNode().attributes().stream().filter(
                                    orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(HANDLING_FEE)
                            ).findFirst();

                    Optional<GetOrdersByRefQuery.Attribute1> optionalHandlingFeeTax =
                            orderItemEdge.itemNode().attributes().stream().filter(
                                    orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(HANDLING_FEE_TAX)
                            ).findFirst();

                    Optional<GetOrdersByRefQuery.Attribute1> optionalDeliveryCost =
                            orderItemEdge.itemNode().attributes().stream().filter(
                                    orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(LINE_ITEM_DELIVERY_COST)
                            ).findFirst();

                    Optional<GetOrdersByRefQuery.Attribute1> optionalDeliveryCostTax =
                            orderItemEdge.itemNode().attributes().stream().filter(
                                    orderItemAttribute -> orderItemAttribute.name().equalsIgnoreCase(LINE_ITEM_DELIVERY_COST_TAX)
                            ).findFirst();
                    if (optionalTotalPaidPrice.isPresent()) {

                        // 1) Cumulate all item level actual price
                        cosmeticSubtotalAtOrderLevel = cosmeticSubtotalAtOrderLevel + totalPriceAtItemLevel;

                        // 2) Cumulate all item level paid price
                        subTotalAtOrderLevel = subTotalAtOrderLevel + Double.parseDouble(optionalTotalPaidPrice.get().value().toString());

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

            double totalTaxPriceAtOrderLevelIncludingOtherTax = totalTaxPriceAtOrderLevel + handlingFeeTaxAtOrderLevel + deliveryCostTaxAtOrderLevel;

            // 11) calculate total price of the order
            double totalPriceAtOrderLevel = cosmeticSubtotalAtOrderLevel + totalTaxPriceAtOrderLevelIncludingOtherTax + handlingFeeAtOrderLevel + deliveryCostAtOrderLevel - (totalItemDiscountsAtOrderLevel + totalDiscountsAtorderLevel);
            context.addLog("TaxRecalculationForReturnOrder::totalRefundAmountRequested" + totalPriceAtOrderLevel);
            if (totalPriceAtOrderLevel != 0) {
                Map<String, Object> attributes = new HashMap<>();
                attributes.putAll(context.getEvent().getAttributes());
                attributes.put(TOTAL_REFUND_AMOUNT_REQUESTED, totalPriceAtOrderLevel);
                EventUtils.forwardEventInlineWithAttributes(
                        context,
                        eventName,
                        attributes);
            }

        }
    }

    private static double getDeliveryCostTaxAtOrderLevel(double deliveryCostTaxAtOrderLevel, Optional<GetOrdersByRefQuery.Attribute1> optionalDeliveryCostTax) {
        if (optionalDeliveryCostTax.isPresent()) {
            deliveryCostTaxAtOrderLevel = deliveryCostTaxAtOrderLevel + Double.parseDouble(optionalDeliveryCostTax.get().value().toString());
        }
        return deliveryCostTaxAtOrderLevel;
    }

    private static double getDeliveryCostAtOrderLevel(double deliveryCostAtOrderLevel, Optional<GetOrdersByRefQuery.Attribute1> optionalDeliveryCost) {
        if (optionalDeliveryCost.isPresent()) {
            deliveryCostAtOrderLevel = deliveryCostAtOrderLevel + Double.parseDouble(optionalDeliveryCost.get().value().toString());
        }
        return deliveryCostAtOrderLevel;
    }

    private static double getHandlingFeeTaxAtOrderLevel(double handlingFeeTaxAtOrderLevel, Optional<GetOrdersByRefQuery.Attribute1> optionalHandlingFeeTax) {
        if (optionalHandlingFeeTax.isPresent()) {
            handlingFeeTaxAtOrderLevel = handlingFeeTaxAtOrderLevel + Double.parseDouble(optionalHandlingFeeTax.get().value().toString());
        }
        return handlingFeeTaxAtOrderLevel;
    }

    private static double getHandlingFeeAtOrderLevel(double handlingFeeAtOrderLevel, Optional<GetOrdersByRefQuery.Attribute1> optionalHandlingFee) {
        if (optionalHandlingFee.isPresent()) {
            handlingFeeAtOrderLevel = handlingFeeAtOrderLevel + Double.parseDouble(optionalHandlingFee.get().value().toString());
        }
        return handlingFeeAtOrderLevel;
    }

    private static double getTotalDiscountsAtorderLevel(double totalDiscountsAtorderLevel, Optional<GetOrdersByRefQuery.Attribute1> optionalTotalProratedOrderDiscount) {
        if (optionalTotalProratedOrderDiscount.isPresent()) {
            totalDiscountsAtorderLevel = totalDiscountsAtorderLevel + Double.parseDouble(optionalTotalProratedOrderDiscount.get().value().toString());
        }
        return totalDiscountsAtorderLevel;
    }
}
