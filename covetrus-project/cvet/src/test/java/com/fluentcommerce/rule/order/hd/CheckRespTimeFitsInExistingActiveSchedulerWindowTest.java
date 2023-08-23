package com.fluentcommerce.rule.order.hd;


import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.common.schedulerwindow.CheckRespTimeFitsInExistingActiveSchedulerWindow;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.PROP_ATTRIBUTE_NAME;
import static org.mockito.Mockito.when;

public class CheckRespTimeFitsInExistingActiveSchedulerWindowTest extends BaseTest {
    private CheckRespTimeFitsInExistingActiveSchedulerWindow rule;
    private static  String filePath = PATH_PREFIX+CHECK_RESP_TIME_FITS_IN_EXISTING_ACTIVE_SCHEDULER_WINDOW +
            BACK_SLASH+SCENARIO_1+BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CheckRespTimeFitsInExistingActiveSchedulerWindow();
    }

    @Test
    public void testClass() throws Exception{
        String className =  CheckRespTimeFitsInExistingActiveSchedulerWindow.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalledNumberOfTimes(DO_NOTHING,context,1);
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
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(DO_NOTHING);
        when(context.getProp(PROP_ATTRIBUTE_NAME)).thenReturn(ATTR_SCHEDULER_WINDOW);
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
