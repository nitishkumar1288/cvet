package com.fluentcommerce.util;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Slf4j
/**
 * Utils class for events providing the various mechanisms to send events.
 */
public class EventUtils {

    private EventUtils() {}

    public static void forwardInline(Context context, String eventName) {
        Event newEvent = context.getEvent().toBuilder()
            .name(eventName)
            .scheduledOn(null)
            .build();
        context.action().sendEvent(newEvent);
    }

    public static void forwardEventInlineWithAttributes(Context context, String eventName, Map<String, Object> attributes) {
        Event newEvent = context.getEvent().toBuilder()
            .name(eventName)
            .scheduledOn(null)
            .attributes(attributes)
            .build();
        context.action().sendEvent(newEvent);
    }

    public static void forwardEventInlineWithSubtypeAndAttributes(
            Context context,
            String eventName,
            String subType,
            Map<String, Object> attributes) {
        Event newEvent = context.getEvent().toBuilder()
                .name(eventName)
                .scheduledOn(null)
                .entitySubtype(subType)
                .attributes(attributes)
                .build();
        context.action().sendEvent(newEvent);
    }

    public static void downInline(Context context, String eventName, String ref, String type, String subType) {
        Event newEvent = context.getEvent().toBuilder()
            .name(eventName)
            .entityRef(ref)
            .entityType(type)
            .entitySubtype(subType)
            .entityStatus(null)
            .build();
        context.action().sendEvent(newEvent);
    }

    public static void downInlineWithAttributes(Context context, String eventName, String ref, String type, String subType, Map<String, Object> attributes) {
        Event newEvent = context.getEvent().toBuilder()
            .name(eventName)
            .entityRef(ref)
            .entityType(type)
            .entitySubtype(subType)
            .entityStatus(null)
            .attributes(attributes)
            .build();
        context.action().sendEvent(newEvent);
    }

    public static void forwardInlineWithSchedule(Context context, String eventName) {
        Event newEvent = context.getEvent().toBuilder()
                .name(eventName)
                .scheduledOn(new Date())
                .build();
        context.action().sendEvent(newEvent);
    }

    public static void sendScheduledEventWithAttributes(
            Context context,
            String eventName,
            Event incomingEvent,
            Map<String,Object> webHookAttributesMap,
            Integer delayedSeconds,
            String className) {
        Event scheduledEvent = Event.builder()
                .name(eventName)
                .accountId(incomingEvent.getAccountId())
                .retailerId(incomingEvent.getRetailerId())
                .entityRef(incomingEvent.getEntityRef())
                .entityId(incomingEvent.getEntityId())
                .entityType(incomingEvent.getEntityType())
                .rootEntityId(incomingEvent.getRootEntityId())
                .rootEntityRef(incomingEvent.getRootEntityRef())
                .rootEntityType(incomingEvent.getRootEntityType())
                .attributes(webHookAttributesMap)
                .scheduledOn(new Date(LocalDateTime.now().plus(delayedSeconds, ChronoUnit.SECONDS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()))
                .build();
        log.info("[{}] [{}] - Incoming Event: {} sending scheduled event {}",
                incomingEvent.getAccountId(), className, incomingEvent, scheduledEvent);
        context.action().sendEvent(scheduledEvent);
    }

    public static void sendScheduledEventWithAttributes(
            Context context,
            String eventName,
            Event incomingEvent,
            Map<String,Object> webHookAttributesMap,
            Date scheduledDate,
            String className) {
        Event scheduledEvent = Event.builder()
                .name(eventName)
                .accountId(incomingEvent.getAccountId())
                .retailerId(incomingEvent.getRetailerId())
                .entityRef(incomingEvent.getEntityRef())
                .entityId(incomingEvent.getEntityId())
                .entityType(incomingEvent.getEntityType())
                .rootEntityId(incomingEvent.getRootEntityId())
                .rootEntityRef(incomingEvent.getRootEntityRef())
                .rootEntityType(incomingEvent.getRootEntityType())
                .attributes(webHookAttributesMap)
                .scheduledOn(scheduledDate)
                .build();
        log.info("[{}] [{}] - Incoming Event: {} sending scheduled event {}",
                incomingEvent.getAccountId(), className, incomingEvent, scheduledEvent);
        context.action().sendEvent(scheduledEvent);
    }
    public static void forwardEventToParentEntity(ContextWrapper context,String eventName, String orderId,
                                                  String orderRef, String entityType, Map<String, Object> attributes,String orderStatus) {
        Event newEvent = Event.builder()
                .id(UUID.randomUUID())
                .rootEntityRef(orderRef)
                .rootEntityType(entityType)
                .rootEntityId(orderId)
                .entityRef(orderRef)
                .entityStatus(orderStatus)
                .entityId(orderId)
                .entityType(entityType)
                .entitySubtype(null)
                .entitySubtype(context.getEvent().getEntitySubtype())
                .retailerId(context.getEvent().getRetailerId())
                .name(eventName)
                .attributes(attributes)
                .scheduledOn(new Date())
                .build();
        context.action().sendEvent(newEvent);
    }
    public static void forwardEventToChildEntity(ContextWrapper context, Event event, String eventName, String fulfillmentId,
                                           String fulfillmentRef, String entityType, String entityTypeFulfilment1) {
        Event newEvent = event.toBuilder()
                .name(eventName)
                .entityId(fulfillmentId)
                .entityRef(fulfillmentRef)
                .entityType(entityType)
                .entitySubtype(entityTypeFulfilment1)
                .scheduledOn(null)
                .build();
        context.action().sendEvent(newEvent);
    }
    public static void forwardEventInlineWithAttributes(Context context, String eventName, Map<String, Object> attributes,
                                                        String rootEntityId,String entityId) {
        Event newEvent = context.getEvent().toBuilder()
                .name(eventName)
                .entityId(entityId)
                .rootEntityId(rootEntityId)
                .scheduledOn(null)
                .attributes(attributes)
                .build();
        context.action().sendEvent(newEvent);
    }
}
