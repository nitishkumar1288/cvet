package com.fluentcommerce.rule.fulfilment;

import com.fluentcommerce.graphql.query.fulfilment.GetFulfillmentByRefQuery;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetFulfillmentByRefComparator;
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

public class UpdateAttributeBasedOnPaymentSettlementResponseTest extends BaseTest {
    private UpdateAttributeBasedOnPaymentSettlementResponse rule;
    private static  String filePath = PATH_PREFIX_FULFILMENT+UPDATE_ATTRIBUTE_BASED_ON_PAYMENT_SETTLEMENT_RESPONSE+ BACK_SLASH+SCENARIO_1
            +BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateAttributeBasedOnPaymentSettlementResponse();
    }

    @Test
    public void testClass() throws Exception {
        String className = UpdateAttributeBasedOnPaymentSettlementResponse.class.getSimpleName();
        //mock data
        mockEvent();
        mockParameters(className);
        mockFulfillmentByRef(filePath + FULFILLMENT_BY_REF_FILE_NAME, ZERO);
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
        attributeMap.put(FULFILLMENT_REF,attJsonObject.get(FULFILLMENT_REF));
        attributeMap.put(TRANSACTION_KEY,attJsonObject.get(TRANSACTION_KEY));
        attributeMap.put(TRANSACTION_TYPE,attJsonObject.get(TRANSACTION_TYPE));
        attributeMap.put(PAYMENT_METHOD,attJsonObject.get(PAYMENT_METHOD));
        attributeMap.put(AMOUNT,attJsonObject.get(AMOUNT));
        attributeMap.put(CREATED_DATE,attJsonObject.get(CREATED_DATE));
        attributeMap.put(PAYMENT_TOKEN,attJsonObject.get(PAYMENT_TOKEN));
        attributeMap.put(STATUS,attJsonObject.get(STATUS));
        attributeMap.put(PAYMENT_AUTH,attJsonObject.get(PAYMENT_AUTH));
        attributeMap.put(PROCESSOR_ID,attJsonObject.get(PROCESSOR_ID));
        attributeMap.put(CARD_TYPE,attJsonObject.get(CARD_TYPE));
        attributeMap.put(ERROR_CODE,attJsonObject.get(ERROR_CODE));
        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_ORDER)
                .rootEntityId((String) jsonObject.get(ROOT_ENTITY_ID))
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_ORDER)
                .attributes(attributeMap)
                .build();

        when(context.getEvent()).thenReturn(event);
    }
    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(SUCCESS_PAYMENT_STATUS)).thenReturn(PAYMENT_AUTH_STATUS_SUCCESSFUL);
        when(context.getProp(FAILURE_PAYMENT_STATUS)).thenReturn(PAYMENT_STATUS_FAILED);
    }

    private void mockFulfillmentByRef(String filePath, String fulfillmentRef) {
        sceneBuilder.mock(
                GetFulfillmentByRefQuery.builder()
                        .ref(Arrays.asList(fulfillmentRef))
                        .includeItems(true)
                        .includeAttributes(true)
                        .fulfilmentItemsCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .build(),
                new GetFulfillmentByRefComparator(),
                filePath,
                context
        );
    }
}
