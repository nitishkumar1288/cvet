package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.location.LocationDto;
import com.fluentcommerce.dto.orderitem.OtcProductsDto;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.virtual.GetVirtualPositionsQuery;
import com.fluentcommerce.rule.order.allocation.otcitems.SearchInventoryAtLocationsForOtcItems;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.mocking.comparator.GetVirtualPositionsQueryComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.EVENT_FIELD_LOCATIONS;
import static com.fluentcommerce.util.Constants.EVENT_OTC_ORDER_ITEMS_ALLOCATION;
import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */
public class SearchInventoryAtLocationsForOtcItemsTest extends BaseTest {
    private SearchInventoryAtLocationsForOtcItems rule;
    private static String filePath = PATH_PREFIX + SEARCH_INVENTORY_AT_LOCATIONS_FOR_OTC_ITEMS + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SearchInventoryAtLocationsForOtcItems();
    }

    @Test
    public void testClass() throws Exception {
        String className = SearchInventoryAtLocationsForOtcItems.class.getSimpleName();
        mockEvent();
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        mockVirtualPositions(filePath + GET_VIRTUAL_POSITIONS_FILE_NAME);
        mockParameters(className);
        rule.run(context);
        VerificationUtils.verifyEventCalledNumberOfTimes(className, context, 1);
    }

    private void mockEvent() {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        HashMap<String,Object> attributeMap = new HashMap<>();

        String otcOrderItemAllocation = sceneBuilder.readTestResource(filePath + OTC_ORDER_ITEMS_ALLOCATION);
        ObjectMapper mapper = new ObjectMapper();
        try{

            List<OtcProductsDto> otcProductsDtoList = mapper.readValue(otcOrderItemAllocation, new TypeReference<List<OtcProductsDto>>(){});
            attributeMap.put(EVENT_OTC_ORDER_ITEMS_ALLOCATION, otcProductsDtoList);

        } catch (Exception exception){
            context.addLog("Exception Occurred when mapping " + exception.getMessage());
            return;
        }

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
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_VIRTUAL_CATALOGUE_REF)).thenReturn(BASE_CATALOGUE);
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

    private void mockVirtualPositions(String filePath) {
        sceneBuilder.mock(
                GetVirtualPositionsQuery.builder()
                        .catalogueRef(BASE_CATALOGUE)
                        .groupRef(null)
                        .productRef(null)
                        .build(),
                new GetVirtualPositionsQueryComparator(),
                filePath,
                context
        );
    }
}