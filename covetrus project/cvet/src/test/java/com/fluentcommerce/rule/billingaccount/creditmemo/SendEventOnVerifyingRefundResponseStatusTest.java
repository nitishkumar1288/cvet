package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetCreditMemoByRefComparator;
import com.fluentcommerce.test.mocking.comparator.GetOrderByRefComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

public class SendEventOnVerifyingRefundResponseStatusTest extends BaseTest {
    private SendEventOnVerifyingRefundResponseStatus rule;
    private static  String filePath = PATH_PREFIX_CREDIT_MEMO + SEND_EVENT_ON_VERIFYING_REFUND_RESPONSE_STATUS + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendEventOnVerifyingRefundResponseStatus();
    }

    @Test
    public void testClass() throws Exception{
        String className =  SendEventOnVerifyingRefundResponseStatus.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockCreditMemoByRef(filePath + CREDIT_MEMO_BY_REF, ZERO);
        mockOrderByRef(filePath + ORDER_BY_REF, ZERO);
        // EXERCISE
        rule.run(context);
        //VERIFICATION
        VerificationUtils.verifyEventCalledNumberOfTimes(className, context, 1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ORDER_REF,attJsonObject.get(ORDER_REF));
        attributeMap.put(STATUS,attJsonObject.get(STATUS));
        attributeMap.put(PROCESSOR_ID,attJsonObject.get(PROCESSOR_ID));
        attributeMap.put(CREATED_DATE,attJsonObject.get(CREATED_DATE));
        attributeMap.put(AMOUNT,attJsonObject.get(AMOUNT));
        attributeMap.put(ERROR_CODE,attJsonObject.get(ERROR_CODE));
        attributeMap.put(TRANSACTION_TYPE,attJsonObject.get(TRANSACTION_TYPE));
        attributeMap.put(PAYMENT_AUTH,attJsonObject.get(PAYMENT_AUTH));
        attributeMap.put(TRANSACTION_KEY,attJsonObject.get(TRANSACTION_KEY));
        attributeMap.put(PAYMENT_METHOD,attJsonObject.get(PAYMENT_METHOD));
        attributeMap.put(PAYMENT_TOKEN,attJsonObject.get(PAYMENT_TOKEN));
        attributeMap.put(REFUND_KEY,attJsonObject.get(REFUND_KEY));
        attributeMap.put(CARD_TYPE,attJsonObject.get(CARD_TYPE));
        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_CREDIT_MEMO)
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_CREDIT_MEMO)
                .attributes(attributeMap)
                .build();
        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(PROP_NO_MATCHING_EVENT_NAME);
        when(context.getProp(RESPONSE_STATUS)).thenReturn(PAYMENT_AUTH_STATUS_SUCCESSFUL);


    }

    private void mockCreditMemoByRef(String filePath, String ref) {
        sceneBuilder.mock(
                GetCreditMemoByRefQuery.builder()
                        .ref(ref)
                        .includeItems(true)
                        .returnOrderItemCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .build(),
                new GetCreditMemoByRefComparator(),
                filePath,
                context
        );
    }

    private void mockOrderByRef(String filePath, String ref) {
        sceneBuilder.mock(
                GetOrdersByRefQuery.builder()
                        .ref(Arrays.asList(ref))
                        .includeOrderItems(true)
                        .orderItemCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .includeCustomer(true)
                        .build(),
                new GetOrderByRefComparator(),
                filePath,
                context
        );
    }
}
