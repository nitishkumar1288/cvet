package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.model.rx.Items;
import com.fluentcommerce.rule.order.rxservice.approval.CheckOrderItemStatusMatchingTheList;
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
import static com.fluentcommerce.util.Constants.PROP_STATUS_LIST;
import static org.mockito.Mockito.when;

/**
@author Nandhakumar
*/

public class CheckOrderItemStatusMatchingTheListTest extends BaseTest {
    private CheckOrderItemStatusMatchingTheList rule;
    private static String filePath = PATH_PREFIX + CHECK_ORDER_ITEMS_STATUS_MATCHING_THE_LIST + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CheckOrderItemStatusMatchingTheList();
    }

    @Test
    public void testClass() throws Exception {
        String className = CheckOrderItemStatusMatchingTheList.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);

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
        Items rxServiceRespItems = mapper.readValue(itemJsonData.get(0).toString(), Items.class);

        List<Items> itemsList = new ArrayList<>();
        itemsList.add(rxServiceRespItems);

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

        List<String> acceptedStatuses = new ArrayList<>();
        acceptedStatuses.add(RX_MATCH_ORDER_STATUS_RX_PENDING);

        when(context.getPropList(PROP_STATUS_LIST, String.class)).thenReturn(acceptedStatuses);
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
