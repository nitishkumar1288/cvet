package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.graphql.query.networks.GetNetworksByRefAndStatusQuery;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.graphql.query.virtual.GetVirtualPositionsQuery;
import com.fluentcommerce.rule.order.allocation.rxitems.CheckNonPreAssignedFacilityItemsInAllocation;
import com.fluentcommerce.rule.order.allocation.rxitems.FetchAllVpInformation;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetNetworksByRefAndStatusComparator;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.mocking.comparator.GetSettingsByNameComparator;
import com.fluentcommerce.test.mocking.comparator.GetVirtualPositionsQueryComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS;
import static com.fluentcommerce.util.Constants.ORDER_ITEM_STATUS_ACTIVE;
import static com.fluentcommerce.util.Constants.PROP_EVENT_NAME_IF_NO_LOCATIONS_ARE_FOUND;

import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */
public class FetchAllVpInformationTest extends BaseTest {
    private FetchAllVpInformation rule;
    private static String filePath = PATH_PREFIX + FETCH_ALL_VP_INFORMATION + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new FetchAllVpInformation();
    }

    @Test
    public void testClass() throws Exception {
        String className = CheckNonPreAssignedFacilityItemsInAllocation.class.getSimpleName();
        mockEvent();
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        mockGetSettingsByName(filePath + SETTING_BY_NAME_FILE, ZERO);
        mockNetworkByRefAndStatus(filePath + NETWORK_BY_REF_AND_STATUS);
        mockParameters(className);
        mockVirtualPositions(filePath + GET_VIRTUAL_POSITIONS_FILE_NAME);
        rule.run(context);
        VerificationUtils.verifyEventCalledNumberOfTimes(className, context, 1);
    }

    private void mockEvent() {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        HashMap<String,Object> attributeMap = new HashMap<>();

        String pharmacyAllocationJson = sceneBuilder.readTestResource(filePath + PHARMACY_ALLOCATION_FOR_ITEMS);
        ObjectMapper mapper = new ObjectMapper();
        try{
            List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = mapper.readValue(pharmacyAllocationJson, new TypeReference<List<PharmacyAllocationForOrderItems>>(){});

            attributeMap.put(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS, pharmacyAllocationForOrderItemsList);
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
        when(context.getProp(PROP_EVENT_NAME_IF_NO_LOCATIONS_ARE_FOUND)).thenReturn(className);

        List<String> networkStatusList = new ArrayList<>();
        networkStatusList.add(ORDER_ITEM_STATUS_ACTIVE);

        when(context.getPropList(PROP_NETWORK_STATUS,String.class)).thenReturn(networkStatusList);
        when(context.getProp(PROP_SETTING_NAME_FOR_NETWORK_DETERMINATION)).thenReturn(NETWORK_INFORMATION_FOR_ORDER_ITEMS);
        when(context.getProp(PROP_VIRTUAL_CATALOGUE_REF)).thenReturn("BASE:1");
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

    private void mockGetSettingsByName(String filePath, String zero) {
        sceneBuilder.mock(
                GetSettingsByNameQuery.builder()
                        .includeSettigns(true)
                        .settingCount(DEFAULT_PAGE_SIZE)
                        .build(),
                new GetSettingsByNameComparator(),
                filePath,
                context
        );
    }

    private void mockNetworkByRefAndStatus(String filePath) {
        sceneBuilder.mock(
                GetNetworksByRefAndStatusQuery.builder()
                        .ref(null)
                        .status(null)
                        .build(),
                new GetNetworksByRefAndStatusComparator(),
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