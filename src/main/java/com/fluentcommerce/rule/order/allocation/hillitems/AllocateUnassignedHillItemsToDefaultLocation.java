package com.fluentcommerce.rule.order.allocation.hillitems;


import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.HillProductsDto;
import com.fluentcommerce.graphql.query.location.GetLocationsByRefsQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.location.LocationService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "AllocateUnassignedHillItemsToDefaultLocation",
        description = "Allocate unassigned hill items to default location {" +
                PROP_DEFAULT_HILL_LOCATION + "}. Sends event {" + PROP_EVENT_NAME + "}.",
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

@ParamString(name = PROP_EVENT_NAME, description = "The event name triggered by this rule")
@ParamString(name = PROP_DEFAULT_HILL_LOCATION, description = "Default hill location to be assigned")


@EventAttribute(name = EVENT_HILL_ORDER_ITEMS_ALLOCATION)
@Slf4j

public class AllocateUnassignedHillItemsToDefaultLocation extends BaseRule {

    @Override
    public void run(ContextWrapper context) {

        String eventName = context.getProp(PROP_EVENT_NAME);
        String defaultHillLocation = context.getProp(PROP_DEFAULT_HILL_LOCATION);

        Map<String,Object> eventAttribute = context.getEvent().getAttributes();
        List<HillProductsDto> hillProductsDtoList = (List<HillProductsDto>) eventAttribute.get(EVENT_HILL_ORDER_ITEMS_ALLOCATION);

        List<String> locationList = new ArrayList<>();
        locationList.add(defaultHillLocation);

        LocationService locationService = new LocationService(context);
        List<GetLocationsByRefsQuery.Edge> locationsList = new ArrayList<>();

        locationsList =
        locationService.getLocations(
                locationList,
                locationsList,
                null
        );

        if(CollectionUtils.isNotEmpty(locationsList)) {
            context.addLog("defaultLocationRef " + locationsList.get(0).node().ref());
            for (HillProductsDto hillProductsDto : hillProductsDtoList) {
                if (StringUtils.isEmpty(hillProductsDto.getFoundLocation())) {
                    // assign the default location to this item
                    hillProductsDto.setFoundLocation(locationsList.get(0).node().ref());
                    hillProductsDto.setLocationId(locationsList.get(0).node().id());
                    hillProductsDto.setDefaultLocationSelected(true);
                }
            }
        }

        EventUtils.forwardInline(
                context,
                eventName
        );
    }
}
