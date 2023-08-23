package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.rule.order.paymentauth.UpdateDefaultOriginAddressInOrder;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetSettingsByNameComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.api.v2.model.Entity;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

/**
@author Nandhakumar
*/
public class UpdateDefaultOriginAddressInOrderTest extends BaseTest {
    private UpdateDefaultOriginAddressInOrder rule;
    private static  String filePath = PATH_PREFIX + UPDATE_DEFAULT_ORIGIN_ADDRESS_IN_ORDER_TEST + BACK_SLASH+SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateDefaultOriginAddressInOrder();
    }

    @Test
    public void testClass() throws Exception{

        String className =  UpdateDefaultOriginAddressInOrder.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters();
        mockGetSettingByName(filePath + SETTING_BY_NAME_FILE, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        // also covered in the below
        VerificationUtils.verifyEventCalledNever(className,context);
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

        Entity entity = new Entity() {
            @Override
            public String getEntityType() {
                return null;
            }

            @Override
            public String getId() {
                return "2645";
            }

            @Override
            public String getRef() {
                return null;
            }

            @Override
            public String getType() {
                return null;
            }

            @Override
            public String getStatus() {
                return null;
            }

            @Override
            public String getFlexType() {
                return null;
            }

            @Override
            public int getFlexVersion() {
                return 0;
            }
        };

        when(context.getEntity()).thenReturn(entity);
        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters() {
        when(context.getProp(PROP_SETTING_NAME)).thenReturn(DEFAULT_ORIGIN_ADDRESS);
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
