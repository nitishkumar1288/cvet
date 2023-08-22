package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.createfulfilment.UpdateOrderItemStatusAfterFulfillmentCreation;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.PROP_TO_LINE_ITEM_STATUS;
import static org.mockito.Mockito.when;

public class UpdateOrderItemStatusAfterFulfillmentCreationTest extends BaseTest {
    private UpdateOrderItemStatusAfterFulfillmentCreation rule;
    private static  String filePath = PATH_PREFIX+UPDATE_ORDER_ITEMS_STATUS_AFTER_FULFILLMENT_CREATION +
            BACK_SLASH+SCENARIO_1+BACK_SLASH;
    @Before
    public void setUp() {
        rule = new UpdateOrderItemStatusAfterFulfillmentCreation();
    }

    @Test
    public void testClass() throws Exception{
        String className =  UpdateOrderItemStatusAfterFulfillmentCreation.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyMutationClass(UpdateOrderMutation.class,context,1);
    }
    private void mockEvent(){
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONArray itemJsonData = (JSONArray) jsonObject.get(ATTRIBUTES);
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(FULFILLED_ITEM_REF_LIST,itemJsonData.toList());
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
        when(context.getProp(PROP_TO_LINE_ITEM_STATUS)).thenReturn(className);
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
