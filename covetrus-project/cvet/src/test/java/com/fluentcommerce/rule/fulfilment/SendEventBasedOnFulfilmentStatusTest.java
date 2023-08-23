package com.fluentcommerce.rule.fulfilment;


import com.fluentcommerce.rule.fulfilment.SendEventBasedOnFulfilmentStatus;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

public class SendEventBasedOnFulfilmentStatusTest extends BaseTest {
    private SendEventBasedOnFulfilmentStatus rule;
    private static  String filePath = PATH_PREFIX_FULFILMENT+SEND_EVENT_BASED_ON_FULFILMENT_STATUS+ BACK_SLASH+SCENARIO_1
            +BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendEventBasedOnFulfilmentStatus();
    }

    @Test
    public void testClass() throws Exception {
        String className = SendEventBasedOnFulfilmentStatus.class.getSimpleName();
        //mock data
        mockEvent();
        mockParameters(className);
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
        attributeMap.put(PAYMENT_AUTH_STATUS,attJsonObject.get(PAYMENT_AUTH_STATUS));
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
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(className);
        when(context.getProp(AUTHORIZED_FULFILMENT_STATUS)).thenReturn(PAYMENT_AUTH_STATUS_SUCCESSFUL);
        when(context.getProp(FAILED_FULFILMENT_STATUS)).thenReturn(className);

    }

}
