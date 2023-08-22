package com.fluentcommerce.rule.webhook;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.payment.PaymentData;
import com.fluentcommerce.graphql.query.order.GetOrdersByRefQuery;
import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.rule.webhook.order.SendCapturePaymentDataWebhook;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByRefComparator;
import com.fluentcommerce.test.mocking.comparator.GetSettingsByNameComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.api.v2.model.Entity;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

public class SendCapturePaymentDataWebhookTest extends BaseTest {
    private SendCapturePaymentDataWebhook rule;
    private static  String filePath = PATH_PREFIX_WEBHOOK + SEND_CAPTURE_PAYMENT_DATA_WEBHOOK + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendCapturePaymentDataWebhook();
    }

    @Test
    public void testClass() throws Exception{
        String className =  SendCapturePaymentDataWebhook.class.getSimpleName();
        // SETUP
        mockEvent();
        mockGetSettingsByName(filePath + SETTING_BY_NAME_FILE, ZERO);
        mockParameters(className);
        mockOrderByRef(filePath + ORDER_BY_REF, ZERO);
        // EXERCISE
        rule.run(context);
        //VERIFICATION
        VerificationUtils.verifyEventCalledNever(className, context);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        HashMap<String, Object> attributeMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            PaymentData paymentData = mapper.readValue(attJsonObject.get(CAPTURED_PAYMENT_DATA)
                    .toString(), new TypeReference<PaymentData>(){});
            attributeMap.put(CAPTURED_PAYMENT_DATA, paymentData);
        } catch (Exception exception){
            context.addLog("Exception Occurred when mapping " + exception.getMessage());
            return;
        }
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
    private void mockOrderByRef(String filePath, String ref) {
        sceneBuilder.mock(
                GetOrdersByRefQuery.builder()
                        .ref(Arrays.asList(ref))
                        .includeOrderItems(true)
                        .orderItemCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .includeCustomer(true)
                        .build(),
                new GetOrderByRefComparator(),
                filePath,
                context
        );
    }
}