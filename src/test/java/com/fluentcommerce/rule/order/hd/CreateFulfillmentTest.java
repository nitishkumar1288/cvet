package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.fulfillment.FulfillmentResponse;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfillmentByRefQuery;
import com.fluentcommerce.graphql.query.location.GetLocationByIdQuery;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.createfulfilment.CreateFulfillment;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetFulfillmentByRefComparator;
import com.fluentcommerce.test.mocking.comparator.GetLocationByIdComparator;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.DEFAULT_PAGINATION_PAGE_SIZE;
import static com.fluentcommerce.util.Constants.PROP_EVENT_NAME;
import static org.mockito.Mockito.when;

public class CreateFulfillmentTest extends BaseTest {
    private CreateFulfillment rule;
    private static  String filePath = PATH_PREFIX + CREATE_FULFILLMENT + BACK_SLASH + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CreateFulfillment();
    }

    @Test
    public void testClass() throws Exception{
        String className =  CreateFulfillment.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        mockFulfillmentByRef(filePath + FULFILLMENT_BY_REF_FILE_NAME, ZERO);
        mockLocationById(filePath + LOCATION_BY_ID_FILE_NAME, ZERO);
        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalledTimes(className,context,1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attributeJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        JSONArray itemJsonData = (JSONArray) attributeJsonObject.get(FULFILLMENT);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        FulfillmentResponse items = mapper.readValue(itemJsonData.get(0).toString(), FulfillmentResponse.class);
        ArrayList<FulfillmentResponse> itemsList = new ArrayList<>();
        itemsList.add(items);
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(FULFILLMENT,itemsList);
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

    private void mockFulfillmentByRef(String filePath, String fulfillmentRef) {
        sceneBuilder.mock(
                GetFulfillmentByRefQuery.builder()
                        .ref(Arrays.asList(fulfillmentRef))
                        .includeItems(true)
                        .includeAttributes(true)
                        .fulfilmentItemsCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .build(),
                new GetFulfillmentByRefComparator(),
                filePath,
                context
        );
    }

    private void mockLocationById(String filePath, String locationId) {
        sceneBuilder.mock(
                GetLocationByIdQuery.builder()
                        .id(locationId)
                        .includeNetworks(true)
                        .networkCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .build(),
                new GetLocationByIdComparator(),
                filePath,
                context
        );
    }
}
