package com.fluentcommerce.rule.billingaccount.creditmemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.graphql.mutation.billingaccount.creditmemo.CreateCreditMemoMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.model.billingaccount.AppeasementAttributes;
import com.fluentcommerce.model.billingaccount.EntityReference;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.mocking.comparator.GetSettingsByNameComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.fluentcommerce.test.utils.TestConstants.ATTRIBUTES;
import static com.fluentcommerce.test.utils.TestConstants.DEFAULT_PAGE_SIZE;
import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.*;
import static org.mockito.Mockito.when;

public class CreateCreditMemoFromAppeasementTest extends BaseTest {
    private CreateCreditMemoFromAppeasement rule;
    private static  String filePath = PATH_PREFIX_CREDIT_MEMO + CREATE_CREDIT_MEMO_FROM_APPEASEMENT + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CreateCreditMemoFromAppeasement();
    }

    @Test
    public void testClass() throws Exception{
        String className =  CreateCreditMemoFromAppeasement.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        mockGetSettingByName(filePath + SETTING_BY_NAME_FILE, ZERO);
        // EXERCISE
        rule.run(context);
        //VERIFICATION
        VerificationUtils.verifyMutationClass(CreateCreditMemoMutation.class,context,1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        HashMap<String,Object> attributeMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            AppeasementAttributes appeasement = mapper.readValue(attJsonObject.get(EVENT_ATTR_APPEASEMENT_INFO).toString(),
                    new TypeReference<AppeasementAttributes>() {});

            EntityReference entryReference = mapper.readValue(attJsonObject.get(EVENT_ATTR_ENTITY_REFERENCE).toString(),
                    new TypeReference<EntityReference>() {});

            attributeMap.put(EVENT_ATTR_APPEASEMENT_INFO, appeasement);
            attributeMap.put(EVENT_ATTR_ENTITY_REFERENCE, entryReference);
        } catch (Exception exception){
            context.addLog("Exception Occurred when mapping " + exception.getMessage());
            return;
        }

        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(BILLING_ACCOUNT)
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(BILLING_ACCOUNT)
                .attributes(attributeMap)
                .build();
        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(CREDIT_MEMO_TYPE)).thenReturn(CREDIT_MEMO_TYPE);
        when(context.getProp(CREDIT_MEMO_ITEM_TYPE)).thenReturn(CREDIT_MEMO_ITEM_TYPE);

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
