package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.rule.order.creditcard.UpdateCreditCard;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.PROP_EVENT_NAME;
import static com.fluentcommerce.util.Constants.PAYMENT_INFO;
import static org.mockito.Mockito.when;

/**
 * @author Yamini Kukreja
 */

public class UpdateCreditCardTest extends BaseTest {
    private UpdateCreditCard rule;
    private static String filePath = PATH_PREFIX + UPDATE_CREDIT_CARD + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateCreditCard();
    }

    @Test
    public void testClass() throws Exception {
        String className = UpdateCreditCardTest.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters(className);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyMutationClass(UpdateOrderMutation.class, context, 1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attributeJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        JSONObject paymentInfoObject = (JSONObject) attributeJsonObject.get(PAYMENT_INFO);
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(PAYMENT_INFO,paymentInfoObject);

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
    }
}
