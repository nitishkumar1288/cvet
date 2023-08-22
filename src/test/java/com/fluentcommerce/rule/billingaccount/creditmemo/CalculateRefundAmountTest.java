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
import java.util.Map;

import static com.fluentcommerce.test.utils.TestConstants.*;

import static org.mockito.Mockito.when;

public class CalculateRefundAmountTest extends BaseTest {
    private CalculateRefundAmount rule;
    private static  String filePath = PATH_PREFIX_CREDIT_MEMO + CALCULATE_REFUND_AMOUNT + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CalculateRefundAmount();
    }

    @Test
    public void testClass() throws Exception{
        String className =  CalculateRefundAmount.class.getSimpleName();
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
        Map<String, Object> attributes = new HashMap<>();
        double data = Double.parseDouble((String )attJsonObject.get("totalRefundAmountRequested"));
        attributes.put("totalRefundAmountRequested",data);
        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_CREDIT_MEMO)
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_CREDIT_MEMO)
                .attributes(attributes)
                .build();
        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);

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
