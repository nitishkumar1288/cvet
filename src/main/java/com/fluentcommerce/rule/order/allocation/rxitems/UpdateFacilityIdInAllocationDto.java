package com.fluentcommerce.rule.order.allocation.rxitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.graphql.query.location.GetLocationsByRefsQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.location.LocationService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "UpdateFacilityIdInAllocationDto",
        description = "update facility id in the allocation dto and send event {" + PROP_EVENT_NAME + "}" ,
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
        description = "Event Name"
)


@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@Slf4j
public class UpdateFacilityIdInAllocationDto extends BaseRule {

    private static final String CLASS_NAME = UpdateFacilityIdInAllocationDto.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);

        List<String> locationRefList =
        pharmacyAllocationForOrderItemsList.stream().map(
                PharmacyAllocationForOrderItems::getAssignedFacilityRef
        ).collect(Collectors.toList());

        context.addLog("locationRefList " + locationRefList);

        // Get all the location information
        List<GetLocationsByRefsQuery.Edge> locationsEdgeList = new ArrayList<>();
        LocationService locationService = new LocationService(context);
        locationsEdgeList =
                locationService.getLocations(
                        locationRefList,
                        locationsEdgeList,
                        null
                );


        // update the location id in the dto
        for(PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems : pharmacyAllocationForOrderItemsList){
            Optional<GetLocationsByRefsQuery.Edge> optionalLocation =
            locationsEdgeList.stream().filter(
                    edge -> edge.node().ref().equalsIgnoreCase(pharmacyAllocationForOrderItems.getAssignedFacilityRef())
            ).findFirst();

            if(optionalLocation.isPresent()){
                pharmacyAllocationForOrderItems.setAssignedFacilityId(optionalLocation.get().node().id());
            }
        }

        EventUtils.forwardInline(
                context,
                eventName
        );
    }
}