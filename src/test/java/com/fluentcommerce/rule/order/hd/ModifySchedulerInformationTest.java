package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.rule.order.common.schedulerwindow.ModifySchedulerInformation;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.mocking.comparator.GetSettingsByNameComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

/**
 * @author nitishkumar
 */
public class ModifySchedulerInformationTest extends BaseTest {
    private ModifySchedulerInformation rule;
    private static  String filePath = PATH_PREFIX+MODIFY_SCHEDULER_INFORMATION+BACK_SLASH
            +BACK_SLASH+SCENARIO_1+BACK_SLASH;


    @Before
    public void setUp() {
        rule = new ModifySchedulerInformation();
    }

    @Test
    public void testClass() throws Exception{

        String className =  ModifySchedulerInformation.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters();
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        mockGetSettingByName(filePath + SETTING_BY_NAME_FILE, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyMutationClass(UpdateOrderMutation.class,context,1);
    }
    private void mockEvent()  {
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

    private void mockParameters() {
        when(context.getProp(PROP_SETTING_NAME)).thenReturn(RX_APPROVAL_RESPONSE_ALLOCATION_WAITING_TIME);
        when(context.getProp(TIME_MEASURMENT_UNIT)).thenReturn(TIME_MEASURMENT_UNIT_IN_SECONDS);
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

    private void mockGetSettingByName(String filePath, String zero) {
        sceneBuilder.mock(
                GetSettingsByNameQuery.builder()
                        .includeSettigns(true)
                        .settingCount(DEFAULT_PAGE_SIZE)
                        .build(),
                new GetSettingsByNameComparator(),
                filePath,
                context
        );
    }
}
