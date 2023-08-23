package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fluentcommerce.graphql.mutation.returns.UpdateReturnOrderMutation;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.graphql.query.returns.GetReturnOrderByRefQuery;
import com.fluentcommerce.graphql.type.RetailerId;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetCreditMemoByRefComparator;
import com.fluentcommerce.test.mocking.comparator.GetOrderByRefComparator;
import com.fluentcommerce.test.mocking.comparator.GetReturnOrderByRefComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

public class UpdateReturnOrderAttributeTest extends BaseTest {
    private UpdateReturnOrderAttribute rule;
    private static  String filePath = PATH_PREFIX_CREDIT_MEMO + UPDATE_RETURN_ORDER_ATTRIBUTE + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateReturnOrderAttribute();
    }

    @Test
    public void testClass() throws Exception{
        String className =  UpdateReturnOrderAttribute.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockCreditMemoByRef(filePath + CREDIT_MEMO_BY_REF, ZERO);
        mockOrderByRef(filePath + ORDER_BY_REF, ZERO);
        mockReturnOrderByRef(filePath + RETURN_ORDER_BY_REF, ZERO);
        // EXERCISE
        rule.run(context);
        //VERIFICATION
        VerificationUtils.verifyMutationClass(UpdateReturnOrderMutation.class,context,1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_CREDIT_MEMO)
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_CREDIT_MEMO)
                .attributes(null)
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
