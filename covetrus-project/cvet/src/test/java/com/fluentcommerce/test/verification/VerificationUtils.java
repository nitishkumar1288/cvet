package com.fluentcommerce.test.verification;

import com.apollographql.apollo.api.Mutation;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.test.mocking.matcher.PredicateMatcher;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.v2.context.Context;

import java.util.function.Predicate;

import static org.mockito.Mockito.*;

public class VerificationUtils {
    public static void verifyEventCalled(String expectedEventName, Context context) {
        verifyEvent(expectedEventName, context, 1);
    }

    public static void verifyEventCalledNumberOfTimes(String expectedEventName, Context context, int numberOfInvocations) {
        verifyEvent(expectedEventName, context, numberOfInvocations);
    }

    public static <T> void verifyMutationClass(Class<? extends Mutation> mutationClass, Context context, int numberOfInvocations) {
        verify(context.action(), times(numberOfInvocations)).mutation(any(mutationClass));
    }

    public static void verifyEventCalledNumberOfTimesWithScheduler(String expectedEventName, Context context,
                                                       int numberOfInvocations) {
        verifyEventWithScheduler(expectedEventName, context, numberOfInvocations);
    }

    private static void verifyEvent(String expectedEventName, Context context, int numberOfInvocations) {
        verify(context.action(), times(numberOfInvocations)).sendEvent(
                argThat(PredicateMatcher.of(Event.class, event -> {
                    if (event.getScheduledOn() != null) return false;
                    if (!expectedEventName.equals(event.getName())) return false;
                    return true;
                }))
        );
    }

    private static void verifyEventWithScheduler(String expectedEventName, Context context, int numberOfInvocations) {
        verify(context.action(), times(numberOfInvocations)).sendEvent(
                argThat(PredicateMatcher.of(Event.class, event -> {
                    if (event.getScheduledOn() == null) return false;
                    if (!expectedEventName.equals(event.getName())) return false;
                    return true;
                }))
        );
    }


    public static void verifyEventCalledTimes(String expectedEventName, Context context, int times) {
        verify(context.action(), times(times)).sendEvent(
                argThat(PredicateMatcher.of(Event.class, event -> {
                    if (!expectedEventName.equals(event.getName())) return false;
                    return true;
                }))
        );
    }

    public static void verifyEventCalledNever(String expectedEventName, ContextWrapper context) {
        verify(context.action(), never()).sendEvent(
                argThat(PredicateMatcher.of(Event.class, event -> {
                    if (!expectedEventName.equals(event.getName())) return false;
                    return true;
                }))
        );
    }

    public static void verifyEventCalled(String expectedEventName, Context context, Predicate<Event> predicate) {
        verify(context.action(), times(1)).sendEvent(
                argThat(PredicateMatcher.of(Event.class, event -> {
                    if (!expectedEventName.equals(event.getName())) return false;
                    if (!predicate.test(event)) return false;
                    return true;
                }))
        );
    }

    public static void verifyScheduledEventCalledNumberOfTimes(String expectedEventName, Context context, int numberOfInvocations) {
        verifyScheduledEvent(expectedEventName, context, numberOfInvocations);
    }

    public static void verifyScheduledEventCalled(String expectedEventName, Context context) {
        verifyScheduledEvent(expectedEventName, context, 1);
    }

    private static void verifyScheduledEvent(String expectedEventName, Context context, int numberOfInvocations) {
        verify(context.action(), times(numberOfInvocations)).sendEvent(
                argThat(PredicateMatcher.of(Event.class, event -> {
                    if (event.getScheduledOn() == null) return false;
                    if (!expectedEventName.equals(event.getName())) return false;
                    return true;
                }))
        );
    }

    public static void verifySendEventNeverCalled(Context context) {
        verify(context.action(), never()).sendEvent(any());
    }
}
