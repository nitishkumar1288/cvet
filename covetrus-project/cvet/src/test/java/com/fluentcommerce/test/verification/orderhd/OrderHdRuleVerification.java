package com.fluentcommerce.test.verification.orderhd;

import com.fluentcommerce.test.mocking.matcher.PredicateMatcher;
import com.fluentcommerce.test.verification.EventVerification;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.v2.context.Context;
import lombok.Builder;
import org.mockito.Mockito;

import static org.mockito.Matchers.argThat;

@Builder
public class OrderHdRuleVerification {
    private Event expectedEvent;

    public void verify(Context context) {
        Mockito.verify(context.action(), Mockito.times(1)).sendEvent(argThat(
                PredicateMatcher.of(Event.class, foundEvent -> {
                    boolean verifyEvent = EventVerification.verifyEvent(expectedEvent, foundEvent);
                    if (!verifyEvent) return false;
                    return true;
                })
        ));
    }
}
