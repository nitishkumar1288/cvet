package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.rx.Items;
import com.fluentcommerce.rule.order.rxservice.approval.UpdateLineItemWithDeclinedReason;
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

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

public class UpdateLineItemWithDeclinedReasonTest extends BaseTest {
    private UpdateLineItemWithDeclinedReason rule;
    private static  String filePath = PATH_PREFIX+UPDATE_LINE_ITEM_WITH_DECLINED_REASON +BACK_SLASH+SCENARIO_1+BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateLineItemWithDeclinedReason();
    }

    @Test
    public void testClass() throws Exception{

        String className =  UpdateLineItemWithDeclinedReasonTest.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters();
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        // also covered in the below
        VerificationUtils.verifyMutationClass(UpdateOrderMutation.class,context,1);
    }
    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attributeJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        JSONArray itemJsonData = (JSONArray) attributeJsonObject.get(ITEMS);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Items items = mapper.readValue(itemJsonData.get(0).toString(), Items.class);
        ArrayList<Items> itemsList = new ArrayList<>();
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
                .attributes(attributeMap)

                .build();

        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters() {
        when(context.getProp(DECLINED_REASON_CODE_SETTING_NAME)).thenReturn(ORDER_ITEM_DECLINED_REASON_MAP);
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
