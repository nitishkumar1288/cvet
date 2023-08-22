package com.fluentcommerce.test.verification;

import com.fluentcommerce.test.mocking.matcher.PredicateMatcher;
import com.fluentcommerce.test.utils.TestUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.v2.context.Context;
import lombok.Builder;
import org.apache.commons.collections4.MapUtils;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;

@Builder
public class EventVerification {
    private Event expectedEvent;

    public void verify(Context context) {
        Mockito.verify(context.action(), Mockito.times(1)).sendEvent(argThat(
            PredicateMatcher.of(Event.class, foundEvent -> {
                boolean verifyEvent = verifyEvent(expectedEvent, foundEvent);
                if (!verifyEvent) return false;

                // VERIFY ATTRIBUTE PRESENCE
                Map<String, Object> expectedAttributes = expectedEvent.getAttributes();
                Map<String, Object> foundAttributes = foundEvent.getAttributes();

                if (MapUtils.isEmpty(expectedAttributes) && MapUtils.isEmpty(foundAttributes)) return true;
                if (expectedAttributes == null || foundAttributes == null) return false;

                for (String expectedKey : expectedAttributes.keySet()) {
                    // ONLY CHECK FOR PRESENCE NOT VALUE
                    if (!foundAttributes.containsKey(expectedKey)) return false;
                }

                return true;
            })
        ));
    }

    public static boolean verifyEvent(Event expectedEvent, Event foundEvent) {
        if (TestUtils.notEquals(expectedEvent.getName(), foundEvent.getName())) return false;
        if (TestUtils.notEquals(expectedEvent.getEntityId(), foundEvent.getEntityId())) return false;
        if (TestUtils.notEquals(expectedEvent.getEntityType(), foundEvent.getEntityType())) return false;
        if (TestUtils.notEquals(expectedEvent.getEntityRef(), foundEvent.getEntityRef())) return false;
        if (TestUtils.notEquals(expectedEvent.getEntitySubtype(), foundEvent.getEntitySubtype())) return false;
        if (TestUtils.notEquals(expectedEvent.getRootEntityId(), foundEvent.getRootEntityId())) return false;
        if (TestUtils.notEquals(expectedEvent.getRootEntityType(), foundEvent.getRootEntityType())) return false;
        if (TestUtils.notEquals(expectedEvent.getRootEntityRef(), foundEvent.getRootEntityRef())) return false;

        // VERIFY IF BOTH EVENT ARE SCHEDULED OR NOT
        // DONT COMPARE DATES JUST THE PRESENCE
        if (expectedEvent.getScheduledOn() == null && foundEvent.getScheduledOn() == null) return true;
        if (expectedEvent.getScheduledOn() == null || foundEvent.getScheduledOn() == null) return false;

        return true;
    }

    public void verifyNever(Context context) {
        Mockito.verify(context.action(), Mockito.never()).sendEvent(any());
    }
}
