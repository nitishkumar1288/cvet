package com.fluentcommerce.rule.order.rxservice.approval;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.location.GetLocationsByRefsQuery;
import com.fluentcommerce.model.rx.Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.location.LocationService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.RuleUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Yamini Kukreja
 */

@RuleInfo(
        name = "UpdateRxServiceInboundWithFacilityId",
        description = "Update the order items status with respect to Rx match initial response and call {" + PROP_EVENT_NAME + "}",
        produces = {
                @EventInfo(
                        eventName = "{eventName}",
                        entityType = EventInfoVariables.EVENT_TYPE,
                        entitySubtype = EventInfoVariables.EVENT_SUBTYPE,
                        status = EventInfoVariables.EVENT_STATUS
                )
        },
        accepts = {
                @EventInfo(entityType = ENTITY_TYPE_ORDER)
        }
)
@ParamString(
        name = PROP_EVENT_NAME,
        description = "event name"
)

@EventAttribute(name = ITEMS)
@Slf4j
public class UpdateRxServiceInboundWithFacilityId extends BaseRule {
    private static final String CLASS_NAME = UpdateRxServiceInboundWithFacilityId.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context,PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);
        Map<String,Object> eventAttributes = event.getAttributes();
        List<Items> items = CommonUtils.convertObjectToDto(eventAttributes.get(ITEMS),new TypeReference<List<Items>>(){});
        Map<String, String> locationRefToId = getFacilityId(context, items);
        updateFacilityId(items, locationRefToId);

        if(CollectionUtils.isNotEmpty(items)) {

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String, Object> modifiedEventAttributes = new HashMap<>(eventAttributes);

                modifiedEventAttributes.put(ITEMS, objectMapper.readValue(
                        objectMapper.writeValueAsString(items), ArrayList.class));

                EventUtils.forwardEventInlineWithAttributes(
                        context,
                        eventName,
                        modifiedEventAttributes);
            } catch (Exception e) {
                context.addLog("Exception while mapping the data to arraylist");
            }
        }else{
            context.addLog("Items are not present in the response");
        }
    }

    private Map<String, String> getFacilityId(ContextWrapper context, List<Items> items) {
        Map<String, String> locationRefToId = new HashMap<>();
        if (Objects.nonNull(items)) {
            List<GetLocationsByRefsQuery.Edge> locationsData = new ArrayList<>();
            LocationService locationService = new LocationService(context);
            HashSet<String> locationRefList = new HashSet<>();
            for (Items item : items) {
                locationRefList.add(item.getFacilityId());
            }
            locationService.getLocations(new ArrayList<>(locationRefList), locationsData, null);
            locationsData.forEach(edge -> locationRefToId.put(edge.node().ref(), edge.node().id()));
        }
        return locationRefToId;
    }

    private void updateFacilityId(List<Items> items, Map<String, String> locationRefToId) {
        if (Objects.nonNull(items)) {
            for (Items item : items) {
                if(!item.getFacilityId().isEmpty() && null != locationRefToId.get(item.getFacilityId())){
                    item.setFacilityId(locationRefToId.get(item.getFacilityId()));
                }else{
                    item.setFacilityId("");
                }
            }
        }
    }


}
