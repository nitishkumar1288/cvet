package com.fluentcommerce.rule.location;

import com.fluentcommerce.test.BaseTest;
import com.fluentcommerce.test.verification.VerificationUtils;
import com.fluentretail.rubix.event.Event;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.fluentcommerce.test.utils.TestConstants.*;
import static org.mockito.Mockito.when;

public class NotifyInventoryCatalogueForLocationTest extends BaseTest {
    private NotifyInventoryCatalogueForLocation rule;
    private static  String filePath = PATH_PREFIX_LOCATION + NOTIFY_INVENTORY_CATALOGUE_FOR_LOCATION + BACK_SLASH +
            SCENARIO_1 + BACK_SLASH;

    @Before
    public void setUp() {
        rule = new NotifyInventoryCatalogueForLocation();
    }

    @Test
    public void testClass() throws Exception{
        String className =  NotifyInventoryCatalogueForLocation.class.getSimpleName();
        // SETUP
        mockEvent();
        mockParameters(className);
        // EXERCISE
        rule.run(context);
        //VERIFICATION
        VerificationUtils.verifyScheduledEventCalledNumberOfTimes(className,context,1);
    }

    private void mockEvent() throws IOException {
        String inputEvent = sceneBuilder.readTestResource(filePath + INPUT_EVENT_FILE_NAME);
        JSONObject jsonObject = new JSONObject(inputEvent);
        Map<String, Object> attributes = new HashMap<>();
        Event event = Event.builder()
                .accountId((String) jsonObject.get(ACCOUNT_ID))
                .retailerId((String) jsonObject.get(RETAILER_ID))
                .entityId((String) jsonObject.get(ENTITY_ID))
                .entityRef((String) jsonObject.get(ENTITY_REF))
                .entityType(ENTITY_TYPE_INVENTORY_CATALOGUE)
                .entitySubtype(DEFAULT)
                .rootEntityType(ENTITY_TYPE_INVENTORY_CATALOGUE)
                .rootEntityRef((String) jsonObject.get(ENTITY_REF))
                .attributes(attributes)
                .scheduledOn(new Date())
                .build();
        when(context.getEvent()).thenReturn(event);
    }

    private void mockParameters(String className) {
        when(context.getProp(PROP_EVENT_NAME)).thenReturn(className);
        when(context.getProp(PROP_INVENTORY_CATALOGUE_REF)).thenReturn("DEFAULT:1");
        when(context.getProp(PROP_RETAILER_ID)).thenReturn("1");
    }
}
