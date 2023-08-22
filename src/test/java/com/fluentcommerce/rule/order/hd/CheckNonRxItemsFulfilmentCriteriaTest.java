package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.location.LocationDto;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.pv1.releaseitems.CheckNonRxItemsFulfilmentCriteria;
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
import static com.fluentcommerce.util.Constants.PROP_EVENT_NAME;
import static com.fluentcommerce.util.Constants.EVENT_FIELD_LOCATIONS;
import static com.fluentcommerce.util.Constants.PROP_APPROVAL_TYPE_LIST;
import static com.fluentcommerce.util.Constants.PROP_MATCHING_STATUS_LIST;
import static com.fluentcommerce.util.Constants.IGNORE_RX_ITEMS_ON_STATUS_LIST;
import static org.mockito.Mockito.when;

/**
@author Nandhakumar
*/

public class CheckNonRxItemsFulfilmentCriteriaTest extends BaseTest {
    private CheckNonRxItemsFulfilmentCriteria rule;
    private static String filePath = PATH_PREFIX + CHECK_NON_RX_ITEMS_FULFILMENT_CRITERIA + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CheckNonRxItemsFulfilmentCriteria();
    }

    @Test
    public void testClass() throws Exception {
        String className = CheckNonRxItemsFulfilmentCriteria.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalledNumberOfTimes(className,context,1);
    }

    private void mockEvent() {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        HashMap<String,Object> attributeMap = new HashMap<>();

        String eventAttributesString = sceneBuilder.readTestResource(filePath + EVENT_ATTRIBUTES);
        JSONObject eventAttributes = new JSONObject(eventAttributesString);

        ObjectMapper mapper = new ObjectMapper();
        try{
            Map<String, LocationDto> locations = mapper.readValue(eventAttributes.get(EVENT_FIELD_LOCATIONS).toString(), new TypeReference<Map<String, LocationDto>>(){});
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
                .entityStatus((String)jsonObject.get(ENTITY_STATUS))
                .attributes(attributeMap)
                .build();

        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);

        List<String> approvalTypeList = new ArrayList<>();
        approvalTypeList.add(RX);
        when(context.getPropList(PROP_APPROVAL_TYPE_LIST,String.class)).thenReturn(approvalTypeList);

        List<String> matchingStatusList = new ArrayList<>();
        matchingStatusList.add(STATUS_ALLOCATED);
        when(context.getPropList(PROP_MATCHING_STATUS_LIST,String.class)).thenReturn(matchingStatusList);

        List<String> ignoreRxItemsOnStatusList = new ArrayList<>();
        ignoreRxItemsOnStatusList.add(CANCELLED);
        when(context.getPropList(IGNORE_RX_ITEMS_ON_STATUS_LIST,String.class)).thenReturn(ignoreRxItemsOnStatusList);
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
