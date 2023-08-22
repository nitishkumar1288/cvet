package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.orderitem.DropShipProductDto;
import com.fluentcommerce.graphql.query.location.GetLocationsByRefsQuery;
import com.fluentcommerce.rule.order.allocation.dropshipitems.AssignDefaultLocationToDropShipProducts;
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
import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */
public class AssignDefaultLocationToDropShipProductsTest extends BaseTest {
    private AssignDefaultLocationToDropShipProducts rule;
    private static String filePath = PATH_PREFIX + ASSIGN_DEFAULT_LOCATION_TO_DROP_SHIP_PRODUCTS + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new AssignDefaultLocationToDropShipProducts();
    }

    @Test
    public void testClass() throws Exception {
        String className = AssignDefaultLocationToDropShipProducts.class.getSimpleName();
        mockEvent();
        mockParameters(className);
        mockLocationByRefs(filePath + LOCATION_BY_REFS_FILE_NAME);
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

            List<DropShipProductDto> dropShipProductDtoList = mapper.readValue(eventAttributes.get(EVENT_DROP_SHIP_ORDER_ITEMS_ALLOCATION).toString(), new TypeReference<List<DropShipProductDto>>(){});
            attributeMap.put(EVENT_DROP_SHIP_ORDER_ITEMS_ALLOCATION, dropShipProductDtoList);

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
        when(context.getProp(PROP_DEFAULT_DROP_SHIP_LOCATION)).thenReturn(VFC_WAREHOUSE);
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