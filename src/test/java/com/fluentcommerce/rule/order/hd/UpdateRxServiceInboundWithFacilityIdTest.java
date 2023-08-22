package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.query.location.GetLocationByIdQuery;
import com.fluentcommerce.rule.order.rxservice.approval.UpdateRxServiceInboundWithFacilityId;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetLocationByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentcommerce.util.CommonUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.DEFAULT_PAGINATION_PAGE_SIZE;
import static com.fluentcommerce.util.Constants.PROP_EVENT_NAME;
import static org.mockito.Mockito.when;

/**
 * @author Yamini Kukreja
 */

public class UpdateRxServiceInboundWithFacilityIdTest extends BaseTest {
    private UpdateRxServiceInboundWithFacilityId rule;
    private static String filePath = PATH_PREFIX + UPDATE_RX_SERVICE_INBOUND_WITH_FACILITY_ID + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateRxServiceInboundWithFacilityId();
    }

    @Test
    public void testClass() throws Exception {
        String className = UpdateRxServiceInboundWithFacilityIdTest.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters(className);
        mockLocationById(filePath + LOCATION_BY_ID_FILE_NAME, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalledNumberOfTimes(className, context, 1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        JSONObject attributeJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        JSONArray itemJsonData = (JSONArray) attributeJsonObject.get(ITEMS);
        Map<String, Object> item = CommonUtils.convertJsonToMap(itemJsonData.get(0).toString());
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ITEMS,Arrays.asList(item));

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
