package com.fluentcommerce.rule.webhook;

import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.rule.webhook.fulfilment.SendCreatePickReadyInstructionWebhook;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetFulfillmentByIdComparator;
import com.fluentcommerce.test.mocking.comparator.GetSettingsByNameComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.api.v2.model.Entity;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

public class SendCreatePickReadyInstructionWebhookTest extends BaseTest {
    private SendCreatePickReadyInstructionWebhook rule;
    private static  String filePath = PATH_PREFIX_WEBHOOK + SEND_CREATE_PICK_READY_INSTRUCTION_WEBHOOK + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendCreatePickReadyInstructionWebhook();
    }

    @Test
    public void testClass() throws Exception{
        String className =  SendCreatePickReadyInstructionWebhook.class.getSimpleName();
        // SETUP
        mockEvent(className);
        mockFulfillmentByID(filePath + FULFILLMENT_BY_ID_FILE_NAME, ZERO);
        mockGetSettingsByName(filePath + SETTING_BY_NAME_FILE, ZERO);
        mockParameters(className);
        // EXERCISE
        rule.run(context);
        //VERIFICATION
        VerificationUtils.verifyEventCalledTimes(className,context,0);
    }

    private void mockEvent(String className) throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_FULFILMENT)
                .rootEntityId((String) jsonObject.get(ROOT_ENTITY_ID))
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_ORDER)
                .name(className)
                .attributes(null)
                .build();
        when(context.getEvent()).thenReturn(event);
    }
    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);

        Entity entity = new Entity() {
            @Override
            public String getEntityType() {
                return null;
            }

            @Override
            public String getId() {
                return null;
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
        when(context.getProp(WEBHOOK_SETTING_NAME)).thenReturn(CAPTURED_PAYMENT_DATA_WEBHOOK_END_POINT_URL);

    }
    private void mockGetSettingsByName(String filePath, String zero) {
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

    private void mockFulfillmentByID(String filePath, String orderId) {
        sceneBuilder.mock(
                GetFulfilmentByIdQuery.builder()
                        .id(orderId)
                        .includeItems(true)
                        .includeOrder(true)
                        .includeAttributes(true)
                        .withOrderItem(true)
                        .fulfilmentItemsCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .build(),
                new GetFulfillmentByIdComparator(),
                filePath,
                context
        );
    }
}
