package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.order.pv1.exceptionresolution.SendEventIfAnyLineItemCancelledAsPartOfPV1Response;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */
public class SendEventIfAnyLineItemCancelledAsPartOfPV1ResponseTest extends BaseTest {
    private SendEventIfAnyLineItemCancelledAsPartOfPV1Response rule;
    private static String filePath = PATH_PREFIX + SEND_EVENT_IF_ANY_LINE_ITEM_CANCELLED_AS_PART_OF_PV1_RESPONSE + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendEventIfAnyLineItemCancelledAsPartOfPV1Response();
    }

    @Test
    public void testClass() throws Exception {
        String className = SendEventIfAnyLineItemCancelledAsPartOfPV1Response.class.getSimpleName();
        mockEvent();
        mockParameters(className);
        rule.run(context);
        VerificationUtils.verifyEventCalledNever(className, context);
    }

    private void mockEvent() {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        HashMap<String,Object> attributeMap = new HashMap<>();
        String eventAttributesString = sceneBuilder.readTestResource(filePath + EVENT_ATTRIBUTES);

        JSONObject eventAttributes = new JSONObject(eventAttributesString);
        ObjectMapper mapper = new ObjectMapper();
        try{
            List<PV1Items> itemsList = mapper.readValue(eventAttributes.get(ITEMS).toString(), List.class);
            attributeMap.put(ITEMS,itemsList);

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
    }
}