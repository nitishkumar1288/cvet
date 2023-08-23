package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.location.LocationDto;
import com.fluentcommerce.graphql.query.location.GetLocationsByRefsQuery;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.allocation.rxitems.SendEventWithOrderItemInformation;
import com.fluentcommerce.rule.order.allocation.otcitems.SkipLocationExceedingCapacity;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetLocationsByRefsQueryComparator;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.DEFAULT_PAGINATION_PAGE_SIZE;
import static com.fluentcommerce.util.Constants.EVENT_FIELD_LOCATIONS;
import static com.fluentcommerce.util.Constants.PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_EXCLUDED;
import static com.fluentcommerce.util.Constants.PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_NOT_EXCLUDED;
import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */
public class SkipLocationExceedingCapacityTest extends BaseTest {
    private SkipLocationExceedingCapacity rule;
    private static String filePath = PATH_PREFIX + SKIP_LOCATION_EXCEEDING_CAPACITY + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SkipLocationExceedingCapacity();
    }

    @Test
    public void testClass() throws Exception {
        String className = SendEventWithOrderItemInformation.class.getSimpleName();
        mockEvent();
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        mockLocationByRefs(filePath + LOCATION_BY_REFS_FILE_NAME);
        mockParameters(className);
        rule.run(context);
        VerificationUtils.verifyEventCalledNumberOfTimes(className, context, 1);
    }

    private void mockEvent() {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        HashMap<String,Object> attributeMap = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        String otcLocationsFileName = sceneBuilder.readTestResource(filePath + OTC_LOCATIONS_FILE_NAME);
        try{

            Map<String, LocationDto> locations = mapper.readValue(otcLocationsFileName, new TypeReference<Map<String, LocationDto>>(){});
            attributeMap.put(EVENT_FIELD_LOCATIONS, locations);

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
        when(context.getProp(PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_EXCLUDED)).thenReturn(className);
        when(context.getProp(PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_NOT_EXCLUDED)).thenReturn(className);
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

    private void mockLocationByRefs(String filePath) {
        sceneBuilder.mock(
                GetLocationsByRefsQuery.builder()
                        .locationRef(null)
                        .includeLocations(true)
                        .locationCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .build(),
                new GetLocationsByRefsQueryComparator(),
                filePath,
                context
        );
    }
}