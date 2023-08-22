package com.fluentcommerce.rule.order.hd;


import com.fluentcommerce.rule.order.common.orderstatus.CheckOrderStatusMatchesList;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.PROP_STATUS_LIST;
import static org.mockito.Mockito.when;

public class CheckOrderStatusMatchesListTest extends BaseTest {
    private CheckOrderStatusMatchesList rule;
    private static  String filePath = PATH_PREFIX + CHECK_ORDER_STATUS_MATCHES_LIST +
            BACK_SLASH + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CheckOrderStatusMatchesList();
    }

    @Test
    public void testClass() throws Exception{
        String className = CheckOrderStatusMatchesList.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters(className);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalledNumberOfTimes(className, context, 1);
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
                .build();

        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(className);

        List<String> statusList = new ArrayList<>();
        statusList.add(Rx_MATCH_COMPLETED);
        statusList.add(PARTIAL_Rx_MATCH_COMPLETED);
        statusList.add(PARTIALLY_PHARMACY_ALLOCATED);

        when(context.getPropList(PROP_STATUS_LIST,String.class)).thenReturn(statusList);
    }

}
