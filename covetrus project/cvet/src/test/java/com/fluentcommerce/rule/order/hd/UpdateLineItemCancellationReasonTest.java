package com.fluentcommerce.rule.order.hd;

import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.order.autocancellation.UpdateLineItemCancellationReason;
import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.mocking.comparator.GetOrderByIdComparator;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;
/**
 * @author nitishkumar
 */
public class UpdateLineItemCancellationReasonTest extends BaseTest {

    private UpdateLineItemCancellationReason rule;
    private static  String filePath = PATH_PREFIX+UPDATE_LINE_ITEM_CANCELLATION_REASON+BACK_SLASH
            +BACK_SLASH+SCENARIO_1+BACK_SLASH;

    @Before
    public void setUp() {
        rule = new UpdateLineItemCancellationReason();
    }

    @Test
    public void testClass() throws Exception{
        String className =  UpdateLineItemCancellationReasonTest.class.getSimpleName();

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
    private void mockParameters() {
        when(context.getProp(PROP_ORDER_LINE_STATUS)).thenReturn(RX_MATCH_ORDER_STATUS_RX_PENDING);
        when(context.getProp(REASON_FOR_CANCELLATION)).thenReturn(Rx_REQUEST_EXPIRED);
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
