package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.query.fulfilment.GetFulfilmentByIdQuery;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.common.CheckOrderAttributeNameAndValueMatches;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetFulfillmentByIdComparator;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.api.v2.model.Entity;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static com.fluentcommerce.util.Constants.ATTRIBUTE_VALUE;
import static com.fluentcommerce.util.Constants.ATTRIBUTE_NAME;
import static com.fluentcommerce.util.Constants.PROP_NO_MATCHING_EVENT_NAME;
import static org.mockito.Mockito.when;

public class CheckOrderAttributeNameAndValueMatchesTest extends BaseTest {
    private CheckOrderAttributeNameAndValueMatches rule;
    private static  String filePath = PATH_PREFIX+CHECK_ATTRIBUTE_NAME_EXISTS+BACK_SLASH+SCENARIO_1
            +BACK_SLASH;

    @Before
    public void setUp() {
        rule = new CheckOrderAttributeNameAndValueMatches();
    }

    @Test
    public void testClass() throws Exception{

        String className =  CheckOrderAttributeNameAndValueMatches.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParameters(className,ENTITY_TYPE_ORDER);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalledTimes(className,context,1);
    }


    @Test
    public void testScenario2() throws Exception{

        String className =  CheckOrderAttributeNameAndValueMatches.class.getSimpleName();

        // SETUP
        mockEvent();
        mockParametersScenario2(className,ENTITY_TYPE_FULFILMENT);
        mockFulfillmentByID(filePath + FULFILLMENT_BY_ID_FILE_NAME, ZERO);
        mockOrderByID(filePath + ORDER_BY_ID_FILE_NAME, ZERO);

        // EXERCISE
        rule.run(context);

        //VERIFICATION
        VerificationUtils.verifyEventCalledTimes(className,context,1);
    }
    private void mockEvent()  {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);

        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_ORDER)
                .rootEntityId((String) jsonObject.get(ROOT_ENTITY_ID))
                .rootEntityRef((String) jsonObject.get(ROOT_ENTITY_REF))
                .rootEntityType(ENTITY_TYPE_ORDER)
                .build();

        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className,String entityType) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(className);

        Entity entity = new Entity() {
            @Override
            public String getEntityType() {
                return entityType;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public String getRef() {
                return null;
            }

            @Override
            public String getType() {
                return null;
            }

            @Override
            public String getStatus() {
                return null;
            }

            @Override
            public String getFlexType() {
                return null;
            }

            @Override
            public int getFlexVersion() {
                return 0;
            }
        };

        when(context.getEntity()).thenReturn(entity);
    }

    private void mockParametersScenario2(String className,String entityType) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_NO_MATCHING_EVENT_NAME)).thenReturn(className);

        when(context.getProp(ATTRIBUTE_VALUE)).thenReturn(className);
        when(context.getProp(ATTRIBUTE_NAME)).thenReturn(className);

        Entity entity = new Entity() {
            @Override
            public String getEntityType() {
                return entityType;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public String getRef() {
                return null;
            }

            @Override
            public String getType() {
                return null;
            }

            @Override
            public String getStatus() {
                return null;
            }

            @Override
            public String getFlexType() {
                return null;
            }

            @Override
            public int getFlexVersion() {
                return 0;
            }
        };

        when(context.getEntity()).thenReturn(entity);
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
