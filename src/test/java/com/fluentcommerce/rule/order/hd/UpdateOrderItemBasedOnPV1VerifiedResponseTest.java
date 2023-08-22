package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.order.pv1.verified.UpdateOrderItemBasedOnPV1VerifiedResponse;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.PROP_EVENT_NAME;
import static com.fluentcommerce.util.Constants.PROP_APPROVAL_STATUS_RESPONSE;
import static org.mockito.Mockito.when;

/**
 * @author Yamini Kukreja
 */


public class UpdateOrderItemBasedOnPV1VerifiedResponseTest extends BaseTest {
    private UpdateOrderItemBasedOnPV1VerifiedResponse rule;
    private static String filePath = PATH_PREFIX + UPDATE_ORDER_ITEM_BASED_ON_PV1_VERIFIED_RESPONSE + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateOrderItemBasedOnPV1VerifiedResponse();
    }

    @Test
    public void testClass() throws Exception {
        String className = UpdateOrderItemBasedOnPV1VerifiedResponseTest.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyMutationClass(UpdateOrderMutation.class,context,1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        JSONObject attributeJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        JSONArray itemJsonData = (JSONArray) attributeJsonObject.get(ITEMS);
        ObjectMapper mapper = new ObjectMapper();
        PV1Items items = mapper.readValue(itemJsonData.get(0).toString(), PV1Items.class);
        List<PV1Items> itemsList = new ArrayList<>();
        itemsList.add(items);
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ITEMS,itemsList);

        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_ORDER)
                .rootEntityId((String) jsonObject.get(ROOT_ENTITY_ID))
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_ORDER)
                .entityStatus((String)jsonObject.get(ENTITY_STATUS))
                .attributes(attributeMap)
                .build();

        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_APPROVAL_STATUS_RESPONSE)).thenReturn(VERIFIED_PV1_VERIFIED);
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
