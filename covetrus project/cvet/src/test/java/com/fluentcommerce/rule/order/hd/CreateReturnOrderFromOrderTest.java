package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.graphql.mutation.returnOrder.CreateReturnOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.rule.order.returnorder.CreateReturnOrderFromOrder;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.mocking.comparator.GetSettingsByNameComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.DEFAULT;
import static com.fluentcommerce.util.Constants.USER_ID;
import static com.fluentcommerce.util.Constants.RETURN_ITEMS;
import static com.fluentcommerce.util.Constants.RETURN_REASON;
import static org.mockito.Mockito.when;

public class CreateReturnOrderFromOrderTest extends BaseTest {
    private CreateReturnOrderFromOrder rule;
    private static String filePath = PATH_PREFIX + CREATE_RETURN_ORDER_FROM_ORDER + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CreateReturnOrderFromOrder();
    }

    @Test
    public void testClass() throws Exception {
        String className = CreateReturnOrderFromOrderTest.class.getSimpleName();

        // SETUP
        mockEvent();
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        mockGetSettingByName(filePath + SETTING_BY_NAME_FILE, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyMutationClass(CreateReturnOrderMutation.class,context,1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        JSONObject attributeJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        String userId = attributeJsonObject.get(USER_ID).toString();
        String returnReason = attributeJsonObject.get(RETURN_REASON).toString();
        JSONArray returnItems = (JSONArray) attributeJsonObject.get(RETURN_ITEMS);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> returnItemMap = mapper.readValue(returnItems.get(0).toString(), HashMap.class);
        ArrayList<Map<String, Object>> itemsList = new ArrayList<>();
        itemsList.add(returnItemMap);
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(RETURN_ITEMS,itemsList);
        attributeMap.put(USER_ID,userId);
        attributeMap.put(RETURN_REASON,returnReason);

        Event event = Event.builder()
                .name(CREATE_RETURN_ORDER_FROM_ORDER)
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_ORDER)
                .entitySubtype(DEFAULT)
                .rootEntityId((String) jsonObject.get(ROOT_ENTITY_ID))
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_ORDER)
                .entityStatus((String)jsonObject.get(ENTITY_STATUS))
                .attributes(attributeMap)
                .build();

        when(context.getEvent()).thenReturn(event);
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
