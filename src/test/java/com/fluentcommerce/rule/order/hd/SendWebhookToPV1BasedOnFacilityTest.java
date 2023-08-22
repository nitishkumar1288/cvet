package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.rule.webhook.order.SendWebhookToPV1BasedOnFacility;
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
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS;
import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */
public class SendWebhookToPV1BasedOnFacilityTest extends BaseTest {
    private SendWebhookToPV1BasedOnFacility rule;
    private static String filePath = PATH_PREFIX + SEND_WEBHOOK_TO_PV1_BASED_ON_FACILITY + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendWebhookToPV1BasedOnFacility();
    }

    @Test
    public void testClass() throws Exception {
        String className = SendWebhookToPV1BasedOnFacilityTest.class.getSimpleName();
        mockEvent();
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        mockGetSettingByName(filePath + GET_SETTINGS_BY_NAME_FILE_NAME);

        mockParameters(className);
        rule.run(context);

        VerificationUtils.verifyEventCalledNever(className, context);
    }

    private void mockEvent() {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        HashMap<String,Object> attributeMap = new HashMap<>();

        String pharmacyAllocationJson = sceneBuilder.readTestResource(filePath + PHARMACY_ALLOCATION_FOR_ITEMS);
        ObjectMapper mapper = new ObjectMapper();
        try{
            List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = mapper.readValue(pharmacyAllocationJson, new TypeReference<List<PharmacyAllocationForOrderItems>>(){});

            attributeMap.put(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS, pharmacyAllocationForOrderItemsList);
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
        when(context.getProp(WEBHOOK_SETTING_NAME)).thenReturn(PV1_WEBHOOK_END_POINT_URL);

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
    }

    private void mockGetSettingByName(String filePath) {
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