package com.fluentcommerce.rule.order.paymentauth;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.payment.PaymentAuthRequest;
import com.fluentcommerce.dto.payment.PaymentInfo;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.OrderUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@RuleInfo(
        name = "CreatePaymentAuthRequestForOrder",
        description = "create the payment auth request and call to next event {" + PROP_EVENT_NAME + "}",
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
        name = PROP_EVENT_NAME,
        description = "forward to next Event name"
)
public class CreatePaymentAuthRequestForOrder extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String orderId = context.getEvent().getEntityId();
        String orderRef = context.getEvent().getEntityRef();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById order = orderService.getOrderWithOrderItems(orderId);
        if (null != order && CollectionUtils.isNotEmpty(order.attributes())){
            double totalPrice = order.totalPrice();
            Optional<GetOrderByIdQuery.Attribute> optionalOrderKey = order.attributes().stream()
                    .filter(attribute -> attribute.name().equalsIgnoreCase(ORDER_KEY))
                    .findFirst();
            Optional<GetOrderByIdQuery.Attribute> optionalPaymentInfo = order.attributes().
                    stream().filter(attribute -> attribute.name().equalsIgnoreCase(PAYMENT_INFO)).findFirst();
            PaymentInfo paymentInfo =  OrderUtils.fetchPaymentInfoAttribute(optionalPaymentInfo,context);
            PaymentAuthRequest paymentAuthRequest = createPaymentAuthRequest(totalPrice,optionalOrderKey,
                    paymentInfo,orderRef,context);
            if (null != paymentAuthRequest){
                Map<String, Object> attributes = new HashMap<>();
                attributes.put(PAYMENT_AUTH_REQUEST_FOR_ORDER, paymentAuthRequest);
                attributes.putAll(context.getEvent().getAttributes());
                EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
            }
        }
    }

    private PaymentAuthRequest createPaymentAuthRequest(double totalPrice, Optional<GetOrderByIdQuery.Attribute> optionalOrderKey,
                                                        PaymentInfo paymentInfo, String orderRef,ContextWrapper context) {
        PaymentAuthRequest paymentAuthRequest = null;
        if (null != paymentInfo && optionalOrderKey.isPresent()){
            paymentAuthRequest = new PaymentAuthRequest();
            paymentAuthRequest.setOrderRef(orderRef);
            paymentAuthRequest.setPaymentToken(paymentInfo.getPaymentToken());
            paymentAuthRequest.setPaymentType(paymentInfo.getPaymentType());
            paymentAuthRequest.setGrandTotal((float)totalPrice);
            paymentAuthRequest.setOrderKey(optionalOrderKey.get().value().toString());
        }else {
            context.addLog("CreatePaymentAuthRequestForOrder::Required data is missing for create payment auth " +
                    "request for the orderRef::"+ orderRef);
        }
        return paymentAuthRequest;
    }
}
