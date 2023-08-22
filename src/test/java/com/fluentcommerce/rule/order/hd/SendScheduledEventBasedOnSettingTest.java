package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.rule.order.common.schedulerwindow.SendScheduledEventBasedOnSetting;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetSettingsByNameComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

/**
 * @author nitishkumar
 */
public class SendScheduledEventBasedOnSettingTest extends BaseTest {
    private SendScheduledEventBasedOnSetting rule;
    private static String filePath = PATH_PREFIX + SEND_SCHEDULED_EVENT_BASED_ON_SETTING + BACK_SLASH
             + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendScheduledEventBasedOnSetting();
    }

    @Test
    public void testClass() throws Exception {
        String className = SendScheduledEventBasedOnSetting.class.getSimpleName();
        mockEvent();
        mockParameters(className);
        mockGetSettingByName(filePath + SETTING_BY_NAME_FILE, ZERO);
        rule.run(context);
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
        when(context.getProp(PROP_SETTING_NAME)).thenReturn(RX_MATCH_RESPONSE_ALLOCATION_WAITING_TIME);
        when(context.getProp(PROP_SCHEDULED_EVENT_NAME)).thenReturn(SCHEDULER_WINDOW);
        when(context.getProp(TIME_MEASURMENT_UNIT)).thenReturn(TIME_MEASURMENT_UNIT_IN_SECONDS);
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