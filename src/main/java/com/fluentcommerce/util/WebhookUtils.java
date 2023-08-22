package com.fluentcommerce.util;

import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.v2.context.Context;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

/**
 @author nitishKumar
 */

@Slf4j
public class WebhookUtils {

    private WebhookUtils() {}

    public static Event constructWebhookEvent(Event eventData,Context context){
        return Event.builder()
                            .id(UUID.randomUUID())
                            .name(eventData.getName())
                            .accountId(context.getEvent().getAccountId())
                            .retailerId(context.getEvent().getRetailerId())
                            .entityId(context.getEvent().getEntityId())
                            .entityRef(context.getEvent().getEntityRef())
                            .entityType(eventData.getEntityType())
                            .entitySubtype(context.getEvent().getEntitySubtype())
                            .entityStatus(context.getEntity().getStatus())
                            .rootEntityId(eventData.getRootEntityId())
                            .rootEntityType(eventData.getRootEntityType())
                            .rootEntityRef(eventData.getRootEntityRef())
                            .type(context.getEvent().getType())
                            .attributes(null)
                            .scheduledOn(null)
                            .build();
    }

    public static Event constructWebhookEventWithAttribute(Event eventData, Context context, Map<String, Object> attributes){
        return Event.builder()
                .id(UUID.randomUUID())
                .name(eventData.getName())
                .accountId(context.getEvent().getAccountId())
                .retailerId(context.getEvent().getRetailerId())
                .entityId(context.getEvent().getEntityId())
                .entityRef(context.getEvent().getEntityRef())
                .entityType(eventData.getEntityType())
                .entitySubtype(context.getEvent().getEntitySubtype())
                .entityStatus(context.getEntity().getStatus())
                .rootEntityId(eventData.getRootEntityId())
                .rootEntityType(eventData.getRootEntityType())
                .rootEntityRef(eventData.getRootEntityRef())
                .type(context.getEvent().getType())
                .attributes(attributes)
                .scheduledOn(null)
                .build();
    }
}
