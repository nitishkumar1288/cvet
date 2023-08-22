package com.fluentcommerce.rule.order.hd;


import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.cancellationfromconsole.UpdateEventAttributesToOrderAttributes;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentcommerce.util.CommonUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.PROP_EVENT_ATTRIBUTE_LIST;
import static org.mockito.Mockito.when;

public class UpdateEventAttributesToOrderAttributesTest extends BaseTest {
    private UpdateEventAttributesToOrderAttributes rule;
    private static  String filePath = PATH_PREFIX + UPDATE_EVENT_ATTRIBUTES_TO_ORDER_ATTRIBUTES +
            BACK_SLASH + SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateEventAttributesToOrderAttributes();
    }

    @Test
    public void testClass() throws Exception{
        // SETUP
        mockEvent();
        mockParameters();
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyMutationClass(UpdateOrderMutation.class,context,1);
    }
    private void mockEvent() {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        Map<String,Object> attributesMap = CommonUtils.convertJsonToMap(jsonObject.get(ATTRIBUTES));

        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_ORDER)
                .rootEntityId((String) jsonObject.get(ROOT_ENTITY_ID))
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_ORDER)
                .attributes(attributesMap)
                .build();

        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters() {
        List<String> eventAttributes = new ArrayList<>();
        eventAttributes.add(CANCEL_REASON);
        eventAttributes.add(CANCELLED_BY);

        when(context.getPropList(PROP_EVENT_ATTRIBUTE_LIST,String.class)).thenReturn(eventAttributes);
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
