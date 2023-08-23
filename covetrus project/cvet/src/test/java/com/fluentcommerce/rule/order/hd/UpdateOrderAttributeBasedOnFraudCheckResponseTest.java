package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.fraudcheck.UpdateOrderAttributeBasedOnFraudCheckResponse;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

/**
 * @author nitishkumar
 */
public class UpdateOrderAttributeBasedOnFraudCheckResponseTest extends BaseTest {
    private UpdateOrderAttributeBasedOnFraudCheckResponse rule;
    private static  String filePath = PATH_PREFIX+UPDATE_ORDER_ATTRIBUTE_BASED_ON_FRAUD_CHECK_RESPONSE +
            BACK_SLASH+SCENARIO_1+BACK_SLASH;
    @Before
    public void setUp() {
        rule = new UpdateOrderAttributeBasedOnFraudCheckResponse();
    }

    @Test
    public void testClass() throws Exception{
        String className =  UpdateOrderAttributeBasedOnFraudCheckResponse.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);
        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalledNumberOfTimes(className,context,1);
    }
    private void mockEvent(){
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        JSONObject attJsonObject = (JSONObject) jsonObject.get(ATTRIBUTES);
        HashMap<String,Object> attributeMap = new HashMap<>();
        attributeMap.put(ENTITY_SUB_TYPE_CODES,attJsonObject.get(ENTITY_SUB_TYPE_CODES));
        attributeMap.put(FRAUD_RISK_RESULT,attJsonObject.get(FRAUD_RISK_RESULT));
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
        when(context.getProp(FRAUD_CHECK_APPROVED_RESPONSE_EVENT)).thenReturn(className);
        when(context.getProp(FRAUD_CHECK_APPROVED_DECLINED_EVENT)).thenReturn(DO_NOTHING);
        when(context.getProp(FRAUD_CHECK_APPROVED_IN_REVIEW_EVENT)).thenReturn(DO_NOTHING);
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

