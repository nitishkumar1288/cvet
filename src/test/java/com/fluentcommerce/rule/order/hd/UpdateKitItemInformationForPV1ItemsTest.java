package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.pv1.UpdateKitItemInformationForPV1Items;
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
import static com.fluentcommerce.util.Constants.LOCATION_REF;
import static com.fluentcommerce.util.Constants.UNRESERVE;
import static com.fluentcommerce.util.Constants.SKU_REF;
import static com.fluentcommerce.util.Constants.QUANTITY;
import static com.fluentcommerce.util.Constants.ORDER_ITEM_ID;
import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */
public class UpdateKitItemInformationForPV1ItemsTest extends BaseTest {
    private UpdateKitItemInformationForPV1Items rule;
    private static String filePath = PATH_PREFIX + UPDATE_KIT_ITEM_INFORMATION_FOR_PV1_TRANSFER + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateKitItemInformationForPV1Items();
    }

    @Test
    public void testClass() throws Exception {
        String className = UpdateKitItemInformationForPV1ItemsTest.class.getSimpleName();
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        rule.run(context);
        VerificationUtils.verifyEventCalledNumberOfTimes(className, context, 1);
    }

    private void mockEvent() {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        HashMap<String,Object> attributeMap = new HashMap<>();

        Map<String, String> reserveMap = new HashMap<>();
        reserveMap.put(SKU_REF, "DP002");
        reserveMap.put(ORDER_ITEM_ID, "1045");
        reserveMap.put(QUANTITY, String.valueOf(0));
        reserveMap.put(LOCATION_REF, "VFC_WAREHOUSE");

        Map<String, String> unReserveMap = new HashMap<>();
        unReserveMap.put(SKU_REF, "NK002");
        unReserveMap.put(ORDER_ITEM_ID, "1044");
        unReserveMap.put(QUANTITY, String.valueOf(0));
        unReserveMap.put(LOCATION_REF, "VFC_WAREHOUSE");

        List<Map<String, String>> reserveMapList = new ArrayList<>();
        reserveMapList.add(reserveMap);

        List<Map<String, String>> unReserveMapList = new ArrayList<>();
        unReserveMapList.add(unReserveMap);

        attributeMap.put(RESERVE, reserveMapList);
        attributeMap.put(UNRESERVE, unReserveMapList);

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

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
    }
}