package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.graphql.query.returns.GetReturnOrderByRefQuery;
import com.fluentcommerce.graphql.type.RetailerId;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetCreditMemoByRefComparator;
import com.fluentcommerce.test.mocking.comparator.GetOrderByRefComparator;
import com.fluentcommerce.test.mocking.comparator.GetReturnOrderByRefComparator;
import com.fluentcommerce.test.utils.TestConstants;
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

public class CreateRefundIssuedDataTest extends BaseTest {
    private CreateRefundIssuedData rule;
    private static  String filePath = PATH_PREFIX_CREDIT_MEMO + CREATE_REFUND_ISSUED_DATA + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CreateRefundIssuedData();
    }

    @Test
    public void testClass() throws Exception{
        String className =  CreateRefundIssuedData.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockCreditMemoByRef(filePath + CREDIT_MEMO_BY_REF, ZERO);
        mockReturnOrderByRef(filePath + RETURN_ORDER_BY_REF, ZERO);
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
        when(context.getProp(TYPE)).thenReturn(TYPE);
        when(context.getProp(CURRENCY)).thenReturn(CURRENCY);


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

    private void mockReturnOrderByRef(String filePath, String ref) {
        sceneBuilder.mock(
                GetReturnOrderByRefQuery.builder()
                        .ref(ref)
                        .retailer(RetailerId.builder().id("1").build())
                        .includeReturnOrderItems(true)
                        .includeAttributes(true)
                        .returnOrderItemCount(DEFAULT_PAGE_SIZE)
                        .build(),
                new GetReturnOrderByRefComparator(),
                filePath,
                context
        );
    }
}
