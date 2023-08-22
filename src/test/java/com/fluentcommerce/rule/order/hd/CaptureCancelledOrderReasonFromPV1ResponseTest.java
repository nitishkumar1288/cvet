package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.order.pv1.exceptionresolution.CaptureCancelledOrderReasonFromPV1Response;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.PROP_CANCEL_STATUS_RESPONSE;
import static org.mockito.Mockito.when;

/**
 @author Yamini Kukreja
 */

public class CaptureCancelledOrderReasonFromPV1ResponseTest extends BaseTest {
    private CaptureCancelledOrderReasonFromPV1Response rule;
    private static String filePath = PATH_PREFIX + CAPTURE_CANCELLED_ORDER_REASON_FROM_PV1_RESPONSE + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CaptureCancelledOrderReasonFromPV1Response();
    }

    @Test
    public void testClass() throws Exception {
        String className = CaptureCancelledOrderReasonFromPV1ResponseTest.class.getSimpleName();
        mockEvent();
        mockParameters(className);
        rule.run(context);
        VerificationUtils.verifyEventCalledNumberOfTimes(className, context, 1);
    }

    private void mockEvent() throws Exception{
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
        when(context.getProp(PROP_CANCEL_STATUS_RESPONSE)).thenReturn(STATUS_CANCEL_PENDING);
    }
}
