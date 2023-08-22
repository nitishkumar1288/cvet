package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.fulfillment.LocationData;
import com.fluentcommerce.dto.fulfillment.OrderItemData;
import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.rule.order.pv1.releaseitems.UpdateLineAttributeForProcessedPV1Items;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.EVENT_LOCATION_DATA_LIST;
import static org.mockito.Mockito.when;

/**
 * @author Yamini Kukreja
 */

public class UpdateLineAttributeForProcessedPV1ItemsTest extends BaseTest {
    private UpdateLineAttributeForProcessedPV1Items rule;
    private static String filePath = PATH_PREFIX + UPDATE_LINE_ATTRIBUTE_FOR_PROCESSED_PV1_ITEMS + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateLineAttributeForProcessedPV1Items();
    }

    @Test
    public void testClass() throws Exception {
        String className = UpdateLineAttributeForProcessedPV1ItemsTest.class.getSimpleName();

        // SETUP
        mockEvent();

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyMutationClass(UpdateOrderMutation.class, context, 1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        JSONObject attributeJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        JSONArray locationDataList = (JSONArray) attributeJsonObject.get(EVENT_LOCATION_DATA_LIST);
        ObjectMapper mapper = new ObjectMapper();
        LocationData locationData = mapper.readValue(locationDataList.get(0).toString(), LocationData.class);
        ArrayList<OrderItemData> items = locationData.getItems();
        Map<String, Object> locationDataMap = new HashMap<>();
        locationDataMap.put("locationID", locationData.getLocationID());
        locationDataMap.put("locationRef", locationData.getLocationRef());
        locationDataMap.put("items", items);

        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(EVENT_LOCATION_DATA_LIST,Arrays.asList(locationDataMap));

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
}
