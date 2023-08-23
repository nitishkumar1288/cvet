package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.orderitem.HillProductsDto;
import com.fluentcommerce.graphql.query.location.GetLocationsByRefsQuery;
import com.fluentcommerce.rule.order.allocation.hillitems.UpdateCapacityCountInSelectedHillLocation;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetLocationsByRefsQueryComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.DEFAULT_PAGINATION_PAGE_SIZE;
import static com.fluentcommerce.util.Constants.EVENT_HILL_ORDER_ITEMS_ALLOCATION;
import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */
public class UpdateCapacityCountInSelectedHillLocationTest extends BaseTest {
    private UpdateCapacityCountInSelectedHillLocation rule;
    private static String filePath = PATH_PREFIX + UPDATE_CAPACITY_COUNT_IN_SELECTED_HILL_LOCATION + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateCapacityCountInSelectedHillLocation();
    }

    @Test
    public void testClass() throws Exception {
        String className = UpdateCapacityCountInSelectedHillLocation.class.getSimpleName();
        mockEvent();
        mockLocationByRefs(filePath + LOCATION_BY_REFS_FILE_NAME);
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
            List<HillProductsDto> hillProductsDtoList = mapper.readValue(eventAttributes.get(EVENT_HILL_ORDER_ITEMS_ALLOCATION).toString(), new TypeReference<List<HillProductsDto>>(){});
            attributeMap.put(EVENT_HILL_ORDER_ITEMS_ALLOCATION, hillProductsDtoList);

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

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
    }
}