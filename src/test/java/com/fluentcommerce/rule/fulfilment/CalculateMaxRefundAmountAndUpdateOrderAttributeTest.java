package com.fluentcommerce.rule.fulfilment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.fulfillment.FulfilmentExceptionData;
import com.fluentcommerce.dto.fulfillment.FulfilmentItems;
import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetFulfillmentByIdComparator;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

public class CalculateMaxRefundAmountAndUpdateOrderAttributeTest extends BaseTest {
    private CalculateMaxRefundAmountAndUpdateOrderAttribute rule;
    private static  String filePath = PATH_PREFIX_FULFILMENT + CALCULATE_MAX_REFUND_AMOUNT_AND_UPDATE_ORDER_ATTRIBUTE + BACK_SLASH + SCENARIO_1
            +BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CalculateMaxRefundAmountAndUpdateOrderAttribute();
    }

    @Test
    public void testClass() throws Exception {
        String className = CalculateMaxRefundAmountAndUpdateOrderAttribute.class.getSimpleName();
        //mock data
        mockEvent();
        mockParameters(className);
        mockFulfillmentByID(filePath + FULFILLMENT_BY_ID_FILE_NAME, ZERO);
       // mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyMutationClass(UpdateOrderMutation.class, context, 1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        JSONObject fulfilmentJsonObject = (JSONObject) attJsonObject.get(FULFILMENT);
        JSONArray itemsJsonArr = (JSONArray) fulfilmentJsonObject.get(ITEMS);
        ObjectMapper mapper = new ObjectMapper();
        FulfilmentItems items = mapper.readValue(itemsJsonArr.get(0).toString(), FulfilmentItems.class);

        List<FulfilmentItems> itemsList = new ArrayList<>();
        itemsList.add(items);

        FulfilmentExceptionData fulfilment = FulfilmentExceptionData.builder().ref(fulfilmentJsonObject.get(REF).toString())
                .status(fulfilmentJsonObject.get(STATUS).toString()).exceptionResolutionType(fulfilmentJsonObject
                        .get(EXCEPTION_RESOLUTION_TYPE).toString()).facilityId(fulfilmentJsonObject.get(FACILITY_ID).toString())
                .items(itemsList).build();

        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(REF,attJsonObject.get(REF));
        attributeMap.put(UPDATED_ON,attJsonObject.get(UPDATED_ON));
        attributeMap.put(FULFILMENT,fulfilment);
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
        when(context.getProp(PROP_CANCEL_STATUS_RESPONSE)).thenReturn(ORDER_ITEM_STATUS_CANCEL_PENDING);
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

    private void mockFulfillmentByID(String filePath, String orderId) {
        sceneBuilder.mock(
                GetFulfilmentByIdQuery.builder()
                        .id(orderId)
                        .includeItems(true)
                        .includeOrder(true)
                        .includeAttributes(true)
                        .withOrderItem(true)
                        .fulfilmentItemsCount(DEFAULT_PAGINATION_PAGE_SIZE)
                        .build(),
                new GetFulfillmentByIdComparator(),
                filePath,
                context
        );
    }
}
