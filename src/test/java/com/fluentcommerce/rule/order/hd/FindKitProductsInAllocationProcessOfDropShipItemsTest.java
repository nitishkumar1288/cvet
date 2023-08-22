package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.orderitem.DropShipProductDto;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.allocation.dropshipitems.FindKitProductsInAllocationProcessOfDropShipItems;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
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
import static com.fluentcommerce.util.Constants.EVENT_DROP_SHIP_ORDER_ITEMS_ALLOCATION;
import static com.fluentcommerce.util.Constants.DROP_SHIP_ITEM;
import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */
public class FindKitProductsInAllocationProcessOfDropShipItemsTest extends BaseTest {
    private FindKitProductsInAllocationProcessOfDropShipItems rule;
    private static String filePath = PATH_PREFIX + FIND_KIT_PRODUCTS_IN_ALLOCATION_PROCESS_OF_DROP_SHIP_ITEMS + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new FindKitProductsInAllocationProcessOfDropShipItems();
    }

    @Test
    public void testClass() throws Exception {
        String className = FindKitProductsInAllocationProcessOfDropShipItems.class.getSimpleName();
        mockEvent();
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
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

            List<DropShipProductDto> dropShipProductDtoList = mapper.readValue(
                    eventAttributes.get(EVENT_DROP_SHIP_ORDER_ITEMS_ALLOCATION).toString(),
                    new TypeReference<List<DropShipProductDto>>(){});
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
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(className);

        List<String> fulfilmentVendorList = new ArrayList<>();
        fulfilmentVendorList.add(ROYAL_CANIN);
        Map<String,List<String>> mapData = new HashMap<>();
        mapData.put(FULFILMENT_VENDOR_SHORT_NAME,fulfilmentVendorList);

        when(context.getProp(DROP_SHIP_ITEM,Object.class)).thenReturn(mapData);
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