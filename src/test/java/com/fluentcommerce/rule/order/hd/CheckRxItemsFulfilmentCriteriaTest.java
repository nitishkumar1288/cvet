package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.graphql.query.location.GetLocationByIdQuery;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.order.pv1.releaseitems.CheckRxItemsFulfilmentCriteria;
import com.fluentcommerce.test.BaseTest;
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
import java.util.HashMap;
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.PROP_CANCELLED_ORDER_ITEM_STATUS;
import static com.fluentcommerce.util.Constants.PROP_PV1_VERIFIED_ORDER_ITEM_STATUS;
import static com.fluentcommerce.util.Constants.PROP_ACCEPTED_STATUSES;
import static com.fluentcommerce.util.Constants.PROP_ATTRIBUTE_NAME;

import static org.mockito.Mockito.when;

/**
 * @author Yamini Kukreja
 */

public class CheckRxItemsFulfilmentCriteriaTest extends BaseTest {
    private CheckRxItemsFulfilmentCriteria rule;
    private static String filePath = PATH_PREFIX + CHECK_RX_ITEMS_FULFILMENT_CRITERIA + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CheckRxItemsFulfilmentCriteria();
    }

    @Test
    public void testClass() throws Exception {
        String className = CheckRxItemsFulfilmentCriteriaTest.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        mockLocationById(filePath + LOCATION_BY_ID_FILE_NAME, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalledNumberOfTimes(className,context,1);
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
        when(context.getProp(PROP_CANCELLED_ORDER_ITEM_STATUS)).thenReturn("CANCELLED");
        when(context.getProp(PROP_PV1_VERIFIED_ORDER_ITEM_STATUS)).thenReturn("PV1_VERIFIED");

        List<String> acceptedStatuses = new ArrayList<>();
        acceptedStatuses.add(STATUS_PV1_VERIFIED);
        when(context.getPropList(PROP_ACCEPTED_STATUSES, String.class)).thenReturn(acceptedStatuses);
        when(context.getProp(PROP_ATTRIBUTE_NAME)).thenReturn(IS_PV1_VERIFIED_AND_REQUESTED);
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
