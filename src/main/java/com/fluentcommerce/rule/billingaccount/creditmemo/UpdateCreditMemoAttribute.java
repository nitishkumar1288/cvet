package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentInfo;
import com.fluentcommerce.graphql.mutation.billingaccount.creditmemo.UpdateCreditMemoMutation;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateCreditMemoInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.billingaccount.creditmemo.CreditMemoService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.RuleInfo;
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
        name = "UpdateCreditMemoAttribute",
        description = "update the return order attribute ",
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_CREDIT_MEMO)
        }
)

public class UpdateCreditMemoAttribute extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String creditMemoRef = event.getEntityRef();
        CreditMemoService creditMemoService = new CreditMemoService(context);
        GetCreditMemoByRefQuery.CreditMemo creditMemo = creditMemoService.getCreditMemoByRef(creditMemoRef);
        if (null != creditMemo){
            String orderRef = creditMemo.order().ref();
            OrderService orderService = new OrderService(context);
            GetOrdersByRefQuery.Edge orderEdge = orderService.getOrdersByRef(orderRef);
            if (null != orderEdge && null != orderEdge.node() && CollectionUtils.isNotEmpty(orderEdge.node().attributes())){
                Optional<GetOrdersByRefQuery.Attribute> optionalPaymentInfo = orderEdge.node().attributes().stream().
                        filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_INFO)).findFirst();
                PaymentInfo paymentInfo = fetchPaymentInfoAttribute(optionalPaymentInfo,context);
                if (null != paymentInfo){
                    updateCreditMemoAttribute(paymentInfo,creditMemo,context);
                }
            }
        }
    }

    private void updateCreditMemoAttribute(PaymentInfo paymentInfo, GetCreditMemoByRefQuery.CreditMemo creditMemo,
                                           ContextWrapper context) {
        List<AttributeInput> attributeInputList = createNewAttributeInputAsListData(PAYMENT_INFO, ATTRIBUTE_TYPE_JSON,
                paymentInfo);
        UpdateCreditMemoInput updateCreditMemoInput = UpdateCreditMemoInput.builder().
                ref(creditMemo.ref()).
                attributes(attributeInputList).build();
        UpdateCreditMemoMutation updateCreditMemoMutation = UpdateCreditMemoMutation.builder().input(updateCreditMemoInput).build();
        context.action().mutation(updateCreditMemoMutation);
    }

    private PaymentInfo fetchPaymentInfoAttribute(Optional<GetOrdersByRefQuery.Attribute> optionalPaymentInfo, ContextWrapper context) {
        PaymentInfo paymentInfo = null;
        if (optionalPaymentInfo.isPresent()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                paymentInfo = mapper.readValue(optionalPaymentInfo.get().value().toString(),
                        new TypeReference<PaymentInfo>() {});
            } catch (IOException exception) {
                context.addLog("UpdateCreditMemoAttribute::Exception Occurred when fetching payment info attribute " + exception.getMessage());
                return null;
            }
        }
        return paymentInfo;
    }

    public static List<AttributeInput> createNewAttributeInputAsListData(String attributeName, String type, PaymentInfo paymentInfo) {
        AttributeInput attrInput = AttributeInput.builder().name(attributeName).type(type).value(paymentInfo).build();
        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(attrInput);
        return attributeInputList;
    }
}
