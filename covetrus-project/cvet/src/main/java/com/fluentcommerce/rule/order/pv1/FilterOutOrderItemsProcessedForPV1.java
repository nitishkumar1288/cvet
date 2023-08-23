package com.fluentcommerce.rule.order.pv1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.query.location.GetLocationsByRefsQuery;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.PV1Items;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.location.LocationService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.CommonUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author Yamini Kukreja
 */

@RuleInfo(
        name = "FilterOutOrderItemsProcessedForPV1",
        description = "Filter out the order items which are already processed and not in the accepted line status respective " +
                "to the pv1 response status as {" + PROP_ACCEPTED_STATUSES + "}. Send event {" + PROP_EVENT_NAME + "}.",
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
        description = "forward to next Event name"
)
@ParamString(
        name = PROP_ACCEPTED_STATUSES,
        description = "accepted response status: item line status"
)
@Slf4j
public class FilterOutOrderItemsProcessedForPV1 extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        String eventName = context.getProp(PROP_EVENT_NAME);
        Object acceptedStatuses = context.getProp(PROP_ACCEPTED_STATUSES, Object.class);
        Map<String, List<String>> acceptedStatusMap = CommonUtils.convertObjectToDto(acceptedStatuses,
                new TypeReference<Map<String, List<String>>>(){});
        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        List<PV1Items> items = event.getAttributeList(ITEMS, PV1Items.class);
        List<PV1Items> toForward = new ArrayList<>();

        if(!items.isEmpty()){
            List<GetLocationsByRefsQuery.Edge> locationsData = new ArrayList<>();
            LocationService locationService = new LocationService(context);
            HashSet<String> locationRefList = new HashSet<>();
            Map<String, String> locationRefToId = new HashMap<>();
            for(PV1Items item: items){
                locationRefList.add(item.getFacilityId());
            }
            locationService.getLocations(new ArrayList<>(locationRefList), locationsData, null);
            locationsData.stream().forEach(edge -> locationRefToId.put(edge.node().ref(), edge.node().id()));

            for(PV1Items item: items){
                order.getItems().stream().forEach(orderItem -> {
                    if(StringUtils.equalsIgnoreCase(orderItem.getRef(), item.getRef())
                                && acceptedStatusMap.containsKey(item.getStatus())
                                        &&  acceptedStatusMap.get(item.getStatus()).contains(orderItem.getAttributes().get(ORDER_LINE_STATUS).toString())) {
                        item.setFacilityId(locationRefToId.get(item.getFacilityId()));
                        toForward.add(item);
                    }
                });
            }
        }

        if(!toForward.isEmpty()){
            Map<String, Object> attributes = new HashMap<>(event.getAttributes());
            attributes.put(ITEMS, toForward);
            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    attributes);
        }
    }
}
