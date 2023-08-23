package com.fluentcommerce.rule.inventorycatalogue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.inventory.Items;
import com.fluentcommerce.graphql.query.inventory.GetInventoryCatalogueQuery;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetInventoryCatalogueByRefComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import com.google.common.collect.Lists;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.fluentcommerce.rule.inventorycatalogue.SendEventOnInventoryUpdate.*;
import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */

public class SendEventOnInventoryUpdateTest extends BaseTest {
    private SendEventOnInventoryUpdate rule;
    private static String filePath = PATH_PREFIX_INVENTORY_CATALOGUE + SEND_EVENT_ON_INVENTORY_UPDATE +BACK_SLASH + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendEventOnInventoryUpdate();
    }

    @Test
    public void testClass() throws Exception {
        String className = SendEventOnInventoryUpdate.class.getSimpleName();

        //mock data
        mockEvent();
        mockParameters(className);
        mockInventoryCatalogueByRef(filePath + INVENTORY_CATALOGUE_BY_REF_FILE_NAME, INVENTORY_CATALOGUE_DEFAULT);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyScheduledEventCalledNumberOfTimes(className, context, 1);
    }
    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        JSONObject attributeJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        JSONArray itemJsonData = (JSONArray) attributeJsonObject.get(ITEMS);
        ObjectMapper mapper = new ObjectMapper();
        Items items = mapper.readValue(itemJsonData.get(0).toString(), Items.class);
        List<Items> itemsList = new ArrayList<>();
        itemsList.add(items);
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ITEMS,itemsList);

        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId(null)
                .entityRef(INVENTORY_CATALOGUE_DEFAULT)
                .entityType(ENTITY_TYPE_INVENTORY_POSITION)
                .entitySubtype(DEFAULT)
                .rootEntityId(null)
                .rootEntityRef(INVENTORY_CATALOGUE_DEFAULT)
                .rootEntityType(ENTITY_TYPE_INVENTORY_POSITION)
                .attributes(attributeMap)
                .scheduledOn(new Date())
                .build();

        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_INVENTORY_CONFIRM_EVENT)).thenReturn(className);
        when(context.getProp(PROP_INVENTORY_CANCEL_EVENT)).thenReturn(className);
        when(context.getProp(PROP_INVENTORY_RESERVE_EVENT)).thenReturn(className);

        List<String> quanTypes = new ArrayList<>();
        quanTypes.add(RESERVED);
        quanTypes.add(SALE);
        quanTypes.add(CORRECTION);

        when(context.getPropList(PROP_INVENTORY_QUANTITY_TYPES,String.class)).thenReturn(quanTypes);
    }

    private void mockInventoryCatalogueByRef(String filePath, String inventoryCatalogueRef) {
        sceneBuilder.mock(
                GetInventoryCatalogueQuery.builder()
                        .inventoryCatalogueRef(inventoryCatalogueRef)
                        .build(),
                new GetInventoryCatalogueByRefComparator(),
                filePath,
                context
        );
    }
}