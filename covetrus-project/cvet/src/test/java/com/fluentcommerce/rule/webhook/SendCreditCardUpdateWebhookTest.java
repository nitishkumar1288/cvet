package com.fluentcommerce.rule.webhook;

import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.rule.webhook.order.SendCreditCardUpdateWebhook;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.mocking.comparator.GetSettingsByNameComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.api.v2.model.Entity;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.PROP_WEBHOOK_SETTING_NAME;
import static org.mockito.Mockito.when;

/**
 @author nitishKumar
 */
public class SendCreditCardUpdateWebhookTest extends BaseTest {
    private SendCreditCardUpdateWebhook rule;
    private static  String filePath = PATH_PREFIX_WEBHOOK + SEND_CREDIT_CARD_UPDATE_WEBHOOK + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendCreditCardUpdateWebhook();
    }

    @Test
    public void testClass() throws Exception{
        String className =  SendCreditCardUpdateWebhook.class.getSimpleName();
        // SETUP
        mockEvent();
        mockGetSettingsByName(filePath + SETTING_BY_NAME_FILE, ZERO);
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        // EXERCISE
        rule.run(context);
        //VERIFICATION
        VerificationUtils.verifyEventCalledNever(className, context);
    }

    private void mockEvent() {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        HashMap<String, Object> attributeMap = new HashMap<>();
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
        when(context.getProp(PROP_WEBHOOK_SETTING_NAME)).thenReturn(className);

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
