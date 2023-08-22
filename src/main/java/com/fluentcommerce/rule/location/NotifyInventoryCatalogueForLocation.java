package com.fluentcommerce.rule.location;

/**
 @author nitishKumar
 */

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static com.fluentcommerce.util.Constants.*;
@RuleInfo(
        name = "NotifyInventoryCatalogueForLocation",
        description = "Send {" + PROP_EVENT_NAME + "} event with locationRef attribute"
                + "to the {" + PROP_INVENTORY_CATALOGUE_REF + "} inventory catalogue  with retailer id {" + PROP_RETAILER_ID + "}.",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = ENTITY_TYPE_INVENTORY_CATALOGUE)
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_LOCATION)
        }

)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "The name of event to be sent to the inventory catalogue."
)
@ParamString(
        name = PROP_INVENTORY_CATALOGUE_REF,
        description = "The reference of the inventory catalogue."
)
@ParamString(name = PROP_RETAILER_ID, description = "The master retailer id")
@Slf4j
public class NotifyInventoryCatalogueForLocation extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,
                PROP_EVENT_NAME, PROP_INVENTORY_CATALOGUE_REF);
        String eventName = context.getProp(PROP_EVENT_NAME);
        String inventoryCatalogueRef = context.getProp(PROP_INVENTORY_CATALOGUE_REF);
        String retailerId = StringUtils.defaultIfEmpty(context.getProp(PROP_RETAILER_ID), context.getEvent().getRetailerId());

        Map<String, Object> attributes = new HashMap<>();
        attributes.put(EVENT_FIELD_LOCATION_REF, context.getEvent().getEntityRef());

        Event.Builder eventBuilder = Event.builder()
                .name(eventName)
                .retailerId(retailerId)
                .entityRef(inventoryCatalogueRef)
                .entityType(ENTITY_TYPE_INVENTORY_CATALOGUE)
                .entitySubtype(DEFAULT)
                .rootEntityType(ENTITY_TYPE_INVENTORY_CATALOGUE)
                .rootEntityRef(inventoryCatalogueRef)
                .attributes(attributes)
                .scheduledOn(new Date());

        context.action().sendEvent(eventBuilder.build());
    }
}
