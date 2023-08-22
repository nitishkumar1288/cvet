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
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

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
        name = "GetLocationInformationForExistingFacility",
        description = "Get Location information and send event {" + PROP_EVENT_NAME_TO_FIND_FACILITY + "} else send event {" + PROP_EVENT_NAME_FOR_FINDING_FACILITY_FOR_ALL_ITEMS +"} based on items allocation",
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
        name = PROP_EVENT_NAME_TO_FIND_FACILITY,
        description = "Event Name to find new facility"
)
@ParamString(
        name = PROP_EVENT_NAME_FOR_FINDING_FACILITY_FOR_ALL_ITEMS,
        description = "Event Name if all items facility are found"
)


@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@Slf4j
public class GetLocationInformationForExistingFacility extends BaseRule {

    private static final String CLASS_NAME = GetLocationInformationForExistingFacility.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventNameForFindingNewFacility = context.getProp(PROP_EVENT_NAME_TO_FIND_FACILITY);
        String eventNameAfterFindingFacilityForAllItems = context.getProp(PROP_EVENT_NAME_FOR_FINDING_FACILITY_FOR_ALL_ITEMS);
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);


        List<PharmacyAllocationForOrderItems> itemsWithPreAssignedFacility =
        pharmacyAllocationForOrderItemsList.stream().filter(
                allocationItems -> StringUtils.isNotEmpty(allocationItems.getAssignedFacilityId())
        ).collect(Collectors.toList());

        // Get all the location information
        List<GetLocationsByRefsQuery.Edge> locationsEdgeList = new ArrayList<>();
        LocationService locationService = new LocationService(context);
        locationsEdgeList =
                locationService.getLocations(
                        null,
                        locationsEdgeList,
                        null
                );

        for(PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems : itemsWithPreAssignedFacility){
            Optional<GetLocationsByRefsQuery.Edge> optionalLocationEdge =
                    locationsEdgeList.stream().filter(
                            edge -> edge.node().id().equalsIgnoreCase(pharmacyAllocationForOrderItems.getAssignedFacilityId())
                    ).findFirst();
            if(optionalLocationEdge.isPresent()) {
                pharmacyAllocationForOrderItems.setAssignedFacilityRef(optionalLocationEdge.get().node().ref());
            }else{
                context.addLog("Location id is not present " + pharmacyAllocationForOrderItems.getAssignedFacilityId());
            }
        }

        // All items involved in the allocation process have pre-assigned facility if size matches
        if(pharmacyAllocationForOrderItemsList.size() != itemsWithPreAssignedFacility.size()){
            EventUtils.forwardInline(
                    context,
                    eventNameForFindingNewFacility);

        }else{
            // Need to check here whether the editing attribute information is getting passed or not
            EventUtils.forwardInline(
                    context,
                    eventNameAfterFindingFacilityForAllItems);
        }

    }
}