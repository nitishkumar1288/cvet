package com.fluentcommerce.rule.fulfilment;

import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetFulfillmentByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

public class CapturePaymentDataTest extends BaseTest {
    private CapturePaymentData rule;
    private static  String filePath = PATH_PREFIX_FULFILMENT + CAPTURE_PAYMENT_DATA + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CapturePaymentData();
    }

    @Test
    public void testClass() throws Exception{
        String className =  CapturePaymentData.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockFulfillmentByID(filePath + FULFILLMENT_BY_ID_FILE_NAME, ZERO);
        // EXERCISE
        rule.run(context);
        //VERIFICATION
        VerificationUtils.verifyEventCalledNumberOfTimes(className, context, 1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        Map<String, Object> attributes = new HashMap<>();
        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_ORDER)
                .rootEntityId((String) jsonObject.get(ROOT_ENTITY_ID))
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_ORDER)
                .attributes(attributes)
                .build();
        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PAYMENT_STATUS)).thenReturn(SETTLED);
    }

    private void mockFulfillmentByID(String filePath, String orderId) {
        sceneBuilder.mock(
                GetFulfilmentByIdQuery.builder()
                        .id(orderId)
                        .includeItems(true)
                        .includeOrder(true)
                        .includeAttributes(true)
                        .withOrderItem(true)
                        .fulfilmentItemsCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .build(),
                new GetFulfillmentByIdComparator(),
                filePath,
                context
        );
    }
}