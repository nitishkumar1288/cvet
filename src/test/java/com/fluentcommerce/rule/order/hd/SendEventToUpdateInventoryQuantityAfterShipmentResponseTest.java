package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.fulfillment.Line;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.fulfilmentshipmentresponse.SendEventToUpdateInventoryQuantityAfterShipmentResponse;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.PROP_EVENT_NAME;
import static com.fluentcommerce.util.Constants.PROP_OPERATION_NAME;

import static org.mockito.Mockito.when;

public class SendEventToUpdateInventoryQuantityAfterShipmentResponseTest extends BaseTest {
    private SendEventToUpdateInventoryQuantityAfterShipmentResponse rule;
    private static  String filePath = PATH_PREFIX+SEND_EVENT_TO_UPDATE_INVENTORY_QUANTITY_AFTER_SHIPMENT_RESPONSE+ BACK_SLASH+SCENARIO_1
            +BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendEventToUpdateInventoryQuantityAfterShipmentResponse();
    }

    @Test
    public void testClass() throws Exception {
        String className = SendEventToUpdateInventoryQuantityAfterShipmentResponse.class.getSimpleName();
        //mock data
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalledTimes(className,context,1);
    }
    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        HashMap<String,Object> attributeMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            List<Line> lineList = mapper.readValue(attJsonObject.get(LINE)
                    .toString(), new TypeReference<List<Line>>(){});

            attributeMap.put(SHIP_CANCELLED_ITEM_REF_LIST, lineList);
        } catch (Exception exception){
            context.addLog("Exception Occurred when mapping " + exception.getMessage());
            return;
        }
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
        when(context.getProp(PROP_OPERATION_NAME)).thenReturn("UNRESERVE");
        when(context.getProp(PROP_INVENTORY_CATALOGUE_REF)).thenReturn("DEFAULT:1");
        when(context.getProp(PROP_RETAILER_ID)).thenReturn("1");
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
