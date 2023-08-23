package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.fulfillment.Line;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetFulfillmentByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

/**
@author Nandhakumar
*/

public class InventoryUpdateFromFulfilmentShipmentResponseTest extends BaseTest {
    private InventoryUpdateFromFulfilmentShipmentResponse rule;
    private static  String filePath = PATH_PREFIX_FULFILMENT + INVENTORY_UPDATE_FROM_FULFILMENT_SHIPMENT_RESPONSE + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new InventoryUpdateFromFulfilmentShipmentResponse();
    }

    @Test
    public void testClass() throws Exception{
        String className =  InventoryUpdateFromFulfilmentShipmentResponse.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockFulfillmentByID(filePath + FULFILLMENT_BY_ID_FILE_NAME, ZERO);
        // EXERCISE
        rule.run(context);
        //VERIFICATION
        VerificationUtils.verifyScheduledEventCalledNumberOfTimes(className,context,1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        HashMap<String,Object> attributeMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            List<Line> shipConfirmation = mapper.readValue(attJsonObject.get(SHIP_CANCELLED_ITEM_REF_LIST)
                    .toString(), new TypeReference<List<Line>>(){});

            attributeMap.put(SHIP_CANCELLED_ITEM_REF_LIST, shipConfirmation);
        } catch (Exception exception){
            context.addLog("Exception Occurred when mapping " + exception.getMessage());
            return;
        }
        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_FULFILMENT)
                .rootEntityId((String) jsonObject.get(ROOT_ENTITY_ID))
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_FULFILMENT)
                .attributes(attributeMap)
                .build();
        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_RETAILER_ID)).thenReturn("1");
        when(context.getProp(PROP_INVENTORY_CATALOGUE_REF)).thenReturn(INVENTORY_CATALOGUE_DEFAULT);
    }

    private void mockFulfillmentByID(String filePath, String orderId) {
        sceneBuilder.mock(
                GetFulfilmentByIdQuery.builder()
                        .id(orderId)
                        .includeItems(true)
                        .includeOrder(true)
                        .includeAttributes(true)
                        .withOrderItem(true)
                        .fulfilmentItemsCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .build(),
                new GetFulfillmentByIdComparator(),
                filePath,
                context
        );
    }
}
