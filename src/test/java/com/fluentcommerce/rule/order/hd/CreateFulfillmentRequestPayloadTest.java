package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.fulfillment.LocationData;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.createfulfilment.CreateFulfillmentRequestPayload;
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

public class CreateFulfillmentRequestPayloadTest extends BaseTest {
    private CreateFulfillmentRequestPayload rule;
    private static  String filePath = PATH_PREFIX + CREATE_FULFILLMENT_REQUEST_PAYLOAD + BACK_SLASH + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CreateFulfillmentRequestPayload();
    }

    @Test
    public void testClass() throws Exception{
        String className = CreateFulfillmentRequestPayload.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalled(className,context);
    }
    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attributeJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        JSONArray itemJsonData = (JSONArray) attributeJsonObject.get(EVENT_LOCATION_DATA_LIST);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LocationData items = mapper.readValue(itemJsonData.get(0).toString(), LocationData.class);
        ArrayList<LocationData> itemsList = new ArrayList<>();
        itemsList.add(items);
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(EVENT_LOCATION_DATA_LIST,itemsList);
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
}
