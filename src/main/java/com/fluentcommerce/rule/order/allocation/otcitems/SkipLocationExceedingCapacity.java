package com.fluentcommerce.rule.order.allocation.otcitems;


import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.location.LocationDto;
import com.fluentcommerce.graphql.query.location.GetLocationsByRefsQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.location.LocationService;
import com.fluentcommerce.util.DateUtils;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.*;
import java.util.stream.Collectors;
import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "SkipLocationExceedingCapacity",
        description = "Skip the location exceeding capacity on the day and send event {" + PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_EXCLUDED + "} if all locations are excluded else send event " +
                "{" + PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_NOT_EXCLUDED + "}",
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

@ParamString(name = PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_EXCLUDED, description = "The event name triggered by this rule if all locations are excluded")
@ParamString(name = PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_NOT_EXCLUDED, description = "The event name triggered by this rule if all locations are not excluded")

@EventAttribute(name = EVENT_FIELD_LOCATIONS)
@Slf4j
public class SkipLocationExceedingCapacity extends BaseRule {

    @Override
    public void run(ContextWrapper context) {

        String eventNameIfAllLocationsAreExcluded = context.getProp(PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_EXCLUDED);
        String eventNameIfAllLocationsAreNotExcluded = context.getProp(PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_NOT_EXCLUDED);


        Map<String,Object> eventAttribute = context.getEvent().getAttributes();
        Map<String, LocationDto> locations = (Map<String, LocationDto>) eventAttribute.get(EVENT_FIELD_LOCATIONS);

        List<String> locationRefList =
                locations.entrySet().stream()
                        .map(Map.Entry::getValue)
                        .map(LocationDto::getRef)
                        .collect(Collectors.toList());

        if(CollectionUtils.isNotEmpty(locationRefList)){

            Map<String,Integer> locationRefWithCapacity = new HashMap<>();
            List<GetLocationsByRefsQuery.Edge> locationsList = new ArrayList<>();

            LocationService locationService = new LocationService(context);
            locationService.getLocations(
                    locationRefList,
                    locationsList,
                    null
            );

            List<String> excludedLocationList = new ArrayList<>();
            for(GetLocationsByRefsQuery.Edge locationEdge : locationsList){
                Optional<Object> optionalLastFulfilmentDate =
                        locationEdge.node().attributes().stream().filter(
                                attribute -> attribute.name().equalsIgnoreCase(LAST_ALLOCATED_DATE)
                        ).map(GetLocationsByRefsQuery.Attribute::value).findFirst();

                Optional<Object> optionalMaximumFulfilmentPerDay =
                        locationEdge.node().attributes().stream().filter(
                                attribute -> attribute.name().equalsIgnoreCase(MAX_CAPACITY)
                        ).map(GetLocationsByRefsQuery.Attribute::value).findFirst();


                if(optionalMaximumFulfilmentPerDay.isPresent()){
                    if(optionalLastFulfilmentDate.isPresent() && StringUtils.isNotEmpty(optionalLastFulfilmentDate.get().toString())){
                        context.addLog("Location : " + locationEdge.node().ref() + " LastFulfilmentDate is present : " + optionalLastFulfilmentDate.get());

                        checkLastFulfilmentDateInLocation(
                                context,
                                locations,
                                locationRefWithCapacity,
                                excludedLocationList,
                                locationEdge,
                                optionalLastFulfilmentDate.get().toString(),
                                optionalMaximumFulfilmentPerDay.get().toString());
                    }
                    else{
                        // enter the capacity as max capacity
                        locationRefWithCapacity.put(locationEdge.node().ref(),Integer.parseInt(optionalMaximumFulfilmentPerDay.get().toString()));
                        context.addLog("For location : " + locationEdge.node().ref() + " LastFulfilmentDate is not present");
                    }
                }
                else{
                    // enter the capacity as the default capacity since the attribute is not present
                    locationRefWithCapacity.put(locationEdge.node().ref(),Integer.parseInt(DEFAULT_MAXIMUM_CAPACITY_PER_DAY));
                    context.addLog("maximumFulfilmentPerDay attribute is not present");
                }
            }

            context.addLog("locationRefWithCapacity " +locationRefWithCapacity);
            context.addLog("modified locations " + locations);

            Map<String, Object> attributes = new HashMap<>();
            attributes.putAll(context.getEvent().getAttributes());

            attributes.put(EVENT_CAPACITY_FOR_LOCATION, locationRefWithCapacity);

            sendEventBasedOnExcludedLocationList(
                    context,
                    eventNameIfAllLocationsAreExcluded,
                    eventNameIfAllLocationsAreNotExcluded,
                    locationRefList,
                    excludedLocationList,
                    attributes);
        }
        else{
            context.addLog("Locations Ref list is empty , so can not proceed further");
        }
    }
    private static void checkLastFulfilmentDateInLocation(
            ContextWrapper context,
            Map<String, LocationDto> locations,
            Map<String, Integer> locationRefWithCapacity,
            List<String> excludedLocationList,
            GetLocationsByRefsQuery.Edge locationEdge,
            String optionalLastFulfilmentDate,
            String optionalMaximumFulfilmentPerDay) {
        // last fulfilment date should be specific to location zone when inserting it
        DateTime lastFulfilmentDate = DateTime.parse(optionalLastFulfilmentDate);
        DateTime currentDate = locationEdge.node().primaryAddress() != null ?
                DateUtils.getNowWithTimeZone(locationEdge.node().primaryAddress().timeZone()) : new DateTime();

        context.addLog("lastFulfilmentDate" + lastFulfilmentDate);
        context.addLog("currentDate " + currentDate);

        // if the date is same then check for the capacity
        if (DateUtils.isSameDay(lastFulfilmentDate, currentDate)) {
            sameDayCriteria(
                    context,
                    locations,
                    locationRefWithCapacity,
                    excludedLocationList,
                    locationEdge,
                    optionalMaximumFulfilmentPerDay);
        }
        else{
            // enter the capacity as max capacity
            locationRefWithCapacity.put(locationEdge.node().ref(),Integer.parseInt(optionalMaximumFulfilmentPerDay));
            context.addLog("For location : " + locationEdge.node().ref() + " last fulfilment and current day is different");
        }
    }

    private static void sameDayCriteria(
            ContextWrapper context,
            Map<String, LocationDto> locations,
            Map<String, Integer> locationRefWithCapacity,
            List<String> excludedLocationList,
            GetLocationsByRefsQuery.Edge locationEdge,
            String optionalMaximumFulfilmentPerDay) {
        Optional<Object> optionalFulfilmentCount =
                locationEdge.node().attributes().stream().filter(
                        attribute -> attribute.name().equalsIgnoreCase(ACTIVE_CAPACITY)
                ).map(GetLocationsByRefsQuery.Attribute::value).findFirst();

        if(optionalFulfilmentCount.isPresent()){
            int fulfilmentCount = Integer.parseInt(optionalFulfilmentCount.get().toString());
            context.addLog("fulfilmentCount : " + fulfilmentCount);

            int maximumFulfilmentPerDay = Integer.parseInt(optionalMaximumFulfilmentPerDay);
            context.addLog("maximumFulfilmentPerDay " + maximumFulfilmentPerDay);

            checkLocationExclusionCriteria(
                    context,
                    locations,
                    excludedLocationList,
                    locationEdge,
                    fulfilmentCount,
                    maximumFulfilmentPerDay);
            // enter the remaining value for the day
            locationRefWithCapacity.put(locationEdge.node().ref(),maximumFulfilmentPerDay - fulfilmentCount);
        }
        else{
            // enter the capacity as max capacity
            locationRefWithCapacity.put(locationEdge.node().ref(),Integer.parseInt(optionalMaximumFulfilmentPerDay));
            context.addLog("fulfilment count attribute is not present , this scenario should not happen ");
        }
    }

    private static void checkLocationExclusionCriteria(
            ContextWrapper context,
            Map<String, LocationDto> locations,
            List<String> excludedLocationList,
            GetLocationsByRefsQuery.Edge locationEdge,
            int fulfilmentCount,
            int maximumFulfilmentPerDay) {
        if(fulfilmentCount >= maximumFulfilmentPerDay){
            context.addLog("location : " +  locationEdge.node().ref() + "  is excluded");
            excludedLocationList.add(locationEdge.node().ref());

            // remove the location that is not eligible for fulfilment because of capacity
            locations.remove(locationEdge.node().ref());
        }
    }

    private static void sendEventBasedOnExcludedLocationList(
            ContextWrapper context,
            String eventNameIfAllLocationsAreExcluded,
            String eventNameIfAllLocationsAreNotExcluded,
            List<String> locationRefList,
            List<String> excludedLocationList,
            Map<String, Object> attributes) {
        if(excludedLocationList.size() == locationRefList.size()){
            context.addLog("All locations are excluded");
            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventNameIfAllLocationsAreExcluded,
                    attributes
            );
        }else{
            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventNameIfAllLocationsAreNotExcluded,
                    attributes
            );
        }
    }

}

