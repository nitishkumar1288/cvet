package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.location.LocationDto;
import com.fluentcommerce.dto.orderitem.OtcProductsDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.model.virtual.VirtualPosition;
import com.fluentcommerce.rule.order.allocation.otcitems.CheckRemainingOtcItemsAndLocationExists;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.EVENT_FIELD_LOCATIONS;
import static com.fluentcommerce.util.Constants.EVENT_CAPACITY_FOR_LOCATION;
import static com.fluentcommerce.util.Constants.EVENT_FIELD_VIRTUAL_POSITIONS;

import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */
public class CheckRemainingOtcItemsAndLocationExistsTest extends BaseTest {
    private CheckRemainingOtcItemsAndLocationExists rule;
    private static String filePath = PATH_PREFIX + CHECK_REMAINING_OTC_ITEMS_AND_LOCATION_EXISTS + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CheckRemainingOtcItemsAndLocationExists();
    }

    @Test
    public void testClass() throws Exception {
        String className = CheckRemainingOtcItemsAndLocationExists.class.getSimpleName();
        mockEvent();
        mockParameters(className);
        rule.run(context);
        VerificationUtils.verifyEventCalledNumberOfTimes(className, context, 1);
    }

    private void mockEvent() {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        HashMap<String,Object> attributeMap = new HashMap<>();

        String eventAttributesString = sceneBuilder.readTestResource(filePath + EVENT_ATTRIBUTES);
        JSONObject eventAttributes = new JSONObject(eventAttributesString);

        ObjectMapper mapper = new ObjectMapper();
        try{

            List<OtcProductsDto> otcProductsDtoList = mapper.readValue(eventAttributes.get(EVENT_OTC_ORDER_ITEMS_ALLOCATION).toString(), new TypeReference<List<OtcProductsDto>>(){});
            attributeMap.put(EVENT_OTC_ORDER_ITEMS_ALLOCATION, otcProductsDtoList);

            Map<String, LocationDto> locations = mapper.readValue(eventAttributes.get(EVENT_FIELD_LOCATIONS).toString(), new TypeReference<Map<String, LocationDto>>(){});
            attributeMap.put(EVENT_FIELD_LOCATIONS, locations);

            Map<String,Integer> capacityOfOtc = mapper.readValue(eventAttributes.get(EVENT_CAPACITY_FOR_LOCATION).toString(), new TypeReference<Map<String,Integer>>(){});
            attributeMap.put(EVENT_CAPACITY_FOR_LOCATION, capacityOfOtc);

            List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = mapper.readValue(eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS).toString(), new TypeReference<List<PharmacyAllocationForOrderItems>>(){});
            attributeMap.put(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS,pharmacyAllocationForOrderItemsList);

            List<VirtualPosition> virtualPositionList = mapper.readValue(eventAttributes.get(EVENT_FIELD_VIRTUAL_POSITIONS).toString(), new TypeReference< List<VirtualPosition>>(){});
            attributeMap.put(EVENT_FIELD_VIRTUAL_POSITIONS,virtualPositionList);

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
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(className);
    }
}