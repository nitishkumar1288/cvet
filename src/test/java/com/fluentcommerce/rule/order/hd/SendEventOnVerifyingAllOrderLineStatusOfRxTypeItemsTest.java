package com.fluentcommerce.rule.order.hd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.common.orderstatus.SendEventOnVerifyingAllOrderLineStatusOfRxTypeItems;
import com.fluentcommerce.rule.order.allocation.rxitems.SendEventWithOrderItemInformation;
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

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS;
import static com.fluentcommerce.util.Constants.PROP_ORDER_LINE_STATUS;
import static com.fluentcommerce.util.Constants.PROP_APPROVAL_TYPE_LIST;
import static org.mockito.Mockito.when;

/**
 @author Nandhakumar
 */
public class SendEventOnVerifyingAllOrderLineStatusOfRxTypeItemsTest extends BaseTest {
    private SendEventOnVerifyingAllOrderLineStatusOfRxTypeItems rule;
    private static String filePath = PATH_PREFIX + SEND_EVENT_ON_VERIFYING_ALL_ORDER_LINE_STATUS_OF_RX_TYPE_ITEMS + BACK_SLASH
            + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new SendEventOnVerifyingAllOrderLineStatusOfRxTypeItems();
    }

    @Test
    public void testClass() throws Exception {
        String className = SendEventWithOrderItemInformation.class.getSimpleName();
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
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_ORDER_LINE_STATUS)).thenReturn(RX_ACTIVE);

        List<String> approvalTypeList = new ArrayList<>();
        approvalTypeList.add(RX);
        when(context.getPropList(PROP_APPROVAL_TYPE_LIST,String.class)).thenReturn(approvalTypeList);
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