package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentInfo;
import com.fluentcommerce.graphql.mutation.returns.UpdateReturnOrderMutation;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.graphql.query.returns.GetReturnOrderByRefQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.RetailerId;
import com.fluentcommerce.graphql.type.UpdateReturnOrderInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.service.returns.ReturnService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "UpdateReturnOrderAttribute",
        description = "update the return order attribute",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_CREDIT_MEMO)
        }
)

@Slf4j
public class UpdateReturnOrderAttribute extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String creditMemoRef = event.getEntityRef();
        CreditMemoService creditMemoService = new CreditMemoService(context);
        GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(creditMemoRef);
        if (null != creditMemo){
            String returnOrderRef = creditMemo.returnOrder().ref();
            ReturnService service = new ReturnService(context);
            GetReturnOrderByRefQuery.ReturnOrder returnOrder = service.getReturnOrderByRef(returnOrderRef);
            String orderRef = creditMemo.order().ref();
            OrderService orderService = new OrderService(context);
            GetOrdersByRefQuery.Edge orderEdge = orderService.getOrdersByRef(orderRef);
            if(null != orderEdge && null != orderEdge.node() && CollectionUtils.isNotEmpty(orderEdge.node().attributes())){
                Optional<GetOrdersByRefQuery.Attribute> optionalPaymentInfo = orderEdge.node().attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_INFO)).findFirst();
                PaymentInfo paymentInfo = fetchPaymentInfoAttribute(optionalPaymentInfo,context);
                if (null != paymentInfo){
                    updateReturnOrderAttribute(paymentInfo,returnOrder,context);
                }
            }
        }
    }

    private PaymentInfo fetchPaymentInfoAttribute(Optional<GetOrdersByRefQuery.Attribute> optionalPaymentInfo, ContextWrapper context) {
        PaymentInfo paymentInfo = null;
        if (optionalPaymentInfo.isPresent()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                paymentInfo = mapper.readValue(optionalPaymentInfo.get().value().toString(),
                        new TypeReference<PaymentInfo>() {});
            } catch (IOException exception) {
                context.addLog("UpdateReturnOrderAttribute :: Exception Occurred when fetching payment info attribute " + exception.getMessage());
                return null;
            }
        }
        return paymentInfo;
    }

    private void updateReturnOrderAttribute(PaymentInfo paymentInfo, GetReturnOrderByRefQuery.ReturnOrder returnOrder,
                                            ContextWrapper context) {
        List<AttributeInput> attributeInputList = createNewAttributeInputAsListData(PAYMENT_INFO, ATTRIBUTE_TYPE_JSON,
                paymentInfo);
        UpdateReturnOrderInput updateReturnOrderInput = UpdateReturnOrderInput.builder().
                ref(returnOrder.ref()).
                retailer(RetailerId.builder().id(context.getEvent().getRetailerId()).build()).
                attributes(attributeInputList).
                build();
        UpdateReturnOrderMutation updateReturnOrderMutation = UpdateReturnOrderMutation.builder().input(updateReturnOrderInput).build();
        context.action().mutation(updateReturnOrderMutation);
    }
    public static List<AttributeInput> createNewAttributeInputAsListData(String attributeName, String type, PaymentInfo paymentInfo) {
        AttributeInput attrInput = AttributeInput.builder().name(attributeName).type(type).value(paymentInfo).build();
        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(attrInput);
        return attributeInputList;
    }
}