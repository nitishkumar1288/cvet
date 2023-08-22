package com.fluentcommerce.rule.fulfilment;

import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.rule.fulfilment.SendEventOnVerifyingAllFulfilmentItemStatus;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetFulfillmentByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

public class SendEventOnVerifyingAllFulfilmentItemStatusTest extends BaseTest {
    private SendEventOnVerifyingAllFulfilmentItemStatus rule;
    private static String filePath = PATH_PREFIX_FULFILMENT+SEND_EVENT_ON_VERIFYING_FULFILMENT_ITEM_STATUS+BACK_SLASH +SCENARIO_1
            +BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendEventOnVerifyingAllFulfilmentItemStatus();
    }

    @Test
    public void testClass() throws Exception {
        String className = SendEventOnVerifyingAllFulfilmentItemStatus.class.getSimpleName();
        //mock data
        mockEvent();
        mockParameters(className);
        mockFulfillmentByID(filePath + FULFILLMENT_BY_ID_FILE_NAME, ZERO);
        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalled(className,context);
    }
    private void mockEvent() {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_ORDER)
                .rootEntityId((String) jsonObject.get(ROOT_ENTITY_ID))
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_ORDER)
                .attributes(null)
                .build();

        when(context.getEvent()).thenReturn(event);
    }
    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(className);
        when(context.getProp(FULFILMENT_LINE_STATUS)).thenReturn(SHIPPED);
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
