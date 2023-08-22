package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.paymentauth.UpdateAttributeBasedOnOrderPaymentAuthReversalResponse;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentcommerce.util.CommonUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.FAILURE_PAYMENT_STATUS;
import static com.fluentcommerce.util.Constants.SUCCESS_PAYMENT_STATUS;
import static com.fluentcommerce.util.Constants.PROP_EVENT_NAME;
import static org.mockito.Mockito.when;

/**
 * @author Yamini Kukreja
 */

public class UpdateAttributeBasedOnOrderPaymentAuthReversalResponseTest extends BaseTest {
    private UpdateAttributeBasedOnOrderPaymentAuthReversalResponse rule;
    private static String filePath = PATH_PREFIX + UPDATE_ATTRIBUTE_BASED_ON_ORDER_PAYMENT_AUTH_REVERSAL_RESPONSE + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateAttributeBasedOnOrderPaymentAuthReversalResponse();
    }

    @Test
    public void testClass() throws Exception {
        String className = UpdateAttributeBasedOnOrderPaymentAuthReversalResponseTest.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyMutationClass(UpdateOrderMutation.class, context, 1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attributeJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        Map<String, Object> attributeMap = CommonUtils.convertJsonToMap(attributeJsonObject);

        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_ORDER)
                .rootEntityId((String) jsonObject.get(ROOT_ENTITY_ID))
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_ORDER)
                .entityStatus((String)jsonObject.get(ENTITY_STATUS))
                .attributes(attributeMap)
                .build();

        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(SUCCESS_PAYMENT_STATUS)).thenReturn("SUCCESSFUL");
        when(context.getProp(FAILURE_PAYMENT_STATUS)).thenReturn("FAILED");
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
