package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.common.orderstatus.SendEventOnVerifyingAllOrderLineStatus;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.PROP_ALL_MATCH_EVENT_NAME;
import static com.fluentcommerce.util.Constants.PROP_PARTIAL_MATCH_EVENT_NAME;
import static org.mockito.Mockito.when;

/**
 * @author nitishkumar
 */
public class SendEventOnVerifyingAllOrderLineStatusTest extends BaseTest {
    private SendEventOnVerifyingAllOrderLineStatus rule;
    private static String filePath = PATH_PREFIX+SEND_EVENT_ON_VERIFYING_ALL_ORDER_LINE_STATUS+BACK_SLASH +SCENARIO_1
            +BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendEventOnVerifyingAllOrderLineStatus();
    }

    @Test
    public void testClass() throws Exception {
        String className = SendEventOnVerifyingAllOrderLineStatus.class.getSimpleName();
        //mock data
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
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
                .build();

        when(context.getEvent()).thenReturn(event);
    }
    private void mockParameters(String className) {
        when(context.getProp(PROP_ALL_MATCH_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_PARTIAL_MATCH_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(DO_NOTHING);

        List<String> statusList = new ArrayList<>();
        statusList.add(RX_MATCH_ORDER_STATUS_RX_ACTIVE);
        when(context.getPropList(PROP_ORDER_LINE_STATUS,String.class)).thenReturn(statusList);
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
                        .orderItemCount(DEFAULT_PAGE_SIZE)
                        .build(),
                new GetOrderByIdComparator(),
                filePath,
                context
        );
    }
}

