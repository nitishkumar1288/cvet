package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetBillingAccountsByCustomerRefQuery;
import com.fluentcommerce.graphql.query.billingaccount.creditmemo.GetCreditMemoByRefQuery;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.graphql.query.returns.GetReturnOrderByRefQuery;
import com.fluentcommerce.graphql.type.RetailerId;
import com.fluentcommerce.rule.order.returnorder.VerifyBillingAccount;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.*;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.fluentcommerce.test.utils.TestConstants.ATTRIBUTES;
import static com.fluentcommerce.test.utils.TestConstants.DEFAULT_PAGE_SIZE;
import static com.fluentcommerce.test.utils.TestConstants.DEFAULT_PAGINATION_PAGE_SIZE;
import static com.fluentcommerce.test.utils.TestConstants.ENTITY_TYPE_ORDER;
import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.*;
import static org.mockito.Mockito.when;

public class VerifyBillingAccountTest extends BaseTest {
    private VerifyBillingAccount rule;
    private static  String filePath = PATH_PREFIX_CREDIT_MEMO + VERIFY_BILLING_ACCOUNT + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new VerifyBillingAccount();
    }

    @Test
    public void testClass() throws Exception{
        String className =  VerifyBillingAccount.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        mockOrderByRef(filePath + ORDER_BY_REF, ZERO);
        mockReturnOrderByRef(filePath + RETURN_ORDER_BY_REF, ZERO);
        mockBillingAccountByCustomerRef(filePath + BILLING_ACCOUNT_BY_CUSTOMER_REF, ZERO);
        // EXERCISE
        rule.run(context);
        //VERIFICATION
        VerificationUtils.verifyEventCalledNumberOfTimes(className,context,1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        Map<String, Object> outgoingAppeasementInfo = new HashMap<>();
            outgoingAppeasementInfo = new HashMap<>();
            outgoingAppeasementInfo.put(APPEASEMENT_AMOUNT, attJsonObject.get(APPEASEMENT_AMOUNT));
            outgoingAppeasementInfo.put(APPEASEMENT_REASON, attJsonObject.get(APPEASEMENT_REASON));
            outgoingAppeasementInfo.put(APPEASEMENT_COMMENT, attJsonObject.get(APPEASEMENT_COMMENT));
            outgoingAppeasementInfo.put(USER_ID, attJsonObject.get(USER_ID));

        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_ORDER)
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_ORDER)
                .attributes(outgoingAppeasementInfo)
                .build();
        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_BILLING_ACCOUNT_EXISTS_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_NO_BILLING_ACCOUNT_EXISTS_EVENT_NAME)).thenReturn(className);

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

    private void mockBillingAccountByCustomerRef(String filePath, String ref) {
        sceneBuilder.mock(
                GetBillingAccountsByCustomerRefQuery.builder()
                        .customerRef(ref)
                        .build(),
                new GetBillingAccountByCustomerRefComparator(),
                filePath,
                context
        );
    }
    private void mockOrderByID(String filePath, String orderId) {
        sceneBuilder.mock(
                GetOrderByIdQuery.builder()
                        .id(orderId)
                        .includeCustomer(true)
                        .includeAttributes(true)
                        .includeFulfilmentChoice(true)
                        .includeOrderItems(true)
                        .includeFulfilments(true)
                        .fulfilmentRef(new ArrayList<>())
                        .includeAttributes(true)
                        .orderItemCount(DEFAULT_PAGE_SIZE)
                        .build(),
                new GetOrderByIdComparator(),
                filePath,
                context
        );
    }
}
