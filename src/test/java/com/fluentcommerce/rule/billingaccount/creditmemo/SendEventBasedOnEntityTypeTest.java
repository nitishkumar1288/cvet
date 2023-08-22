package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

public class SendEventBasedOnEntityTypeTest extends BaseTest {
    private SendEventBasedOnEntityType rule;
    private static  String filePath = PATH_PREFIX_CREDIT_MEMO + SEND_EVENT_BASED_ON_ENTITY_TYPE + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendEventBasedOnEntityType();
    }

    @Test
    public void testClass() throws Exception{
        String className =  SendEventBasedOnEntityType.class.getSimpleName();
        // SETUP
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
        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_CREDIT_MEMO)
                .entitySubtype((String) jsonObject.get(ENTITY_SUB_TYPE))
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_CREDIT_MEMO)
                .attributes(null)
                .build();
        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(className);
    }
}
