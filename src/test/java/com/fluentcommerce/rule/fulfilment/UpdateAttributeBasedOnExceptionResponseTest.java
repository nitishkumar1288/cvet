package com.fluentcommerce.rule.fulfilment;

import com.fluentcommerce.dto.fulfillment.FulfilmentExceptionData;
import com.fluentcommerce.graphql.mutation.fulfillment.UpdateFulfilmentMutation;
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

/**
 * @author Yamini Kukreja
 */

public class UpdateAttributeBasedOnExceptionResponseTest extends BaseTest {
    private UpdateAttributeBasedOnExceptionResponse rule;
    private static  String filePath = PATH_PREFIX_FULFILMENT + UPDATE_ATTRIBUTE_BASED_ON_EXCEPTION_RESPONSE + BACK_SLASH + SCENARIO_1
            +BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateAttributeBasedOnExceptionResponse();
    }

    @Test
    public void testClass() throws Exception {
        String className = UpdateAttributeBasedOnExceptionResponseTest.class.getSimpleName();
        //mock data
        mockEvent();
        mockParameters(className);
        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyMutationClass(UpdateFulfilmentMutation.class, context, 1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        JSONObject fulfilmentJsonObject = (JSONObject) attJsonObject.get(FULFILMENT);
        FulfilmentExceptionData fulfilment = FulfilmentExceptionData.builder().ref(fulfilmentJsonObject.get(REF).toString())
                .status(fulfilmentJsonObject.get(STATUS).toString()).id(fulfilmentJsonObject.get(ID).toString())
                .exceptionReason(fulfilmentJsonObject.get(EXCEPTION_REASON).toString()).build();
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(REF,attJsonObject.get(REF));
        attributeMap.put(UPDATED_ON,attJsonObject.get(UPDATED_ON));
        attributeMap.put(FULFILMENT,fulfilment);
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
        when(context.getProp(PROP_EXCEPTION_STATUS_RESPONSE)).thenReturn(EXCEPTION);
    }
}
