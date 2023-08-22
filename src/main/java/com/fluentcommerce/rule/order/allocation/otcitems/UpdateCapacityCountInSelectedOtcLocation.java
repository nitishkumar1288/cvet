package com.fluentcommerce.rule.order.allocation.otcitems;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.OtcProductsDto;
import com.fluentcommerce.graphql.mutation.location.UpdateLocationMutation;
import com.fluentcommerce.graphql.query.location.GetLocationsByRefsQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateLocationInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.location.LocationService;
import com.fluentcommerce.util.CommonUtils;
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
        name = "UpdateCapacityCountInSelectedOtcLocation",
        description = "Find the locations to fulfil the otc items from rx facilities and sends event {" + PROP_EVENT_NAME + "}.",
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

@EventAttribute(name = EVENT_OTC_ORDER_ITEMS_ALLOCATION)
@Slf4j
public class UpdateCapacityCountInSelectedOtcLocation extends BaseRule {

    @Override
    public void run(ContextWrapper context) {
        String eventName = context.getProp(PROP_EVENT_NAME);
        Map<String,Object> eventAttribute = context.getEvent().getAttributes();

        List<OtcProductsDto> otcProductsDtoList = CommonUtils.convertObjectToDto(eventAttribute.get(EVENT_OTC_ORDER_ITEMS_ALLOCATION),new TypeReference<List<OtcProductsDto>>(){});

        List<String> locationRefList =
                otcProductsDtoList.stream().map(
                        OtcProductsDto::getFoundLocation
                ).collect(Collectors.toList());

        context.addLog("locationRefList " + locationRefList);

        if(CollectionUtils.isNotEmpty(locationRefList)){

            List<GetLocationsByRefsQuery.Edge> locationsList = new ArrayList<>();

            LocationService locationService = new LocationService(context);
            locationService.getLocations(
                    locationRefList,
                    locationsList,
                    null
            );

            Map<String,Integer> locWithFulfilmentCount = new HashMap<>();
            findLocWithFulfilmentCount(
                    context,
                    otcProductsDtoList,
                    locationsList,
                    locWithFulfilmentCount);

            context.addLog("locWithFulfilmentCount " + locWithFulfilmentCount);

            for(Map.Entry<String,Integer> locWithCount : locWithFulfilmentCount.entrySet()){

                Optional<GetLocationsByRefsQuery.Edge> optionalLocation =
                        locationsList.stream().filter(
                                edge -> edge.node().ref().equalsIgnoreCase(locWithCount.getKey())
                        ).findFirst();

                if(optionalLocation.isPresent()){
                    GetLocationsByRefsQuery.Edge locationEdge = optionalLocation.get();

                    DateTime currentDate = locationEdge.node().primaryAddress() != null ?
                            DateUtils.getNowWithTimeZone(locationEdge.node().primaryAddress().timeZone()) : new DateTime();

                    Optional<Object> optionalLastFulfilmentDate =
                            locationEdge.node().attributes().stream().filter(
                                    attribute -> attribute.name().equalsIgnoreCase(LAST_ALLOCATED_DATE)
                            ).map(GetLocationsByRefsQuery.Attribute::value).findFirst();

                    DateTime lastFulfilmentDate = null;
                    if(optionalLastFulfilmentDate.isPresent() && StringUtils.isNotEmpty(optionalLastFulfilmentDate.get().toString())){
                        lastFulfilmentDate = DateTime.parse(optionalLastFulfilmentDate.get().toString());
                    }

                    context.addLog("currentDate " + currentDate);
                    context.addLog("lastFulfilmentDate " + lastFulfilmentDate);

                    updateLocation(
                            context,
                            locWithCount,
                            locationEdge,
                            currentDate,
                            lastFulfilmentDate);
                }
            }

            EventUtils.forwardInline(
                    context,
                    eventName
            );


        }
    }

    private static void findLocWithFulfilmentCount(
            ContextWrapper context,
            List<OtcProductsDto> otcProductsDtoList,
            List<GetLocationsByRefsQuery.Edge> locationsList,
            Map<String, Integer> locWithFulfilmentCount) {

        for(OtcProductsDto otcProductsDto : otcProductsDtoList){
            Optional<GetLocationsByRefsQuery.Edge> optionalLocation =
                    locationsList.stream().filter(
                            edge -> edge.node().ref().equalsIgnoreCase(otcProductsDto.getFoundLocation())
                    ).findFirst();
            if(optionalLocation.isPresent()){
                if(locWithFulfilmentCount.containsKey(otcProductsDto.getFoundLocation())){
                    int count = locWithFulfilmentCount.get(otcProductsDto.getFoundLocation());
                    locWithFulfilmentCount.put(
                            otcProductsDto.getFoundLocation(),
                            count + 1 );
                }else{
                    locWithFulfilmentCount.put(otcProductsDto.getFoundLocation() , 1);
                }
            }
            else{
                context.addLog("Location information can not be fetched for location " + otcProductsDto.getFoundLocation());
            }
        }
    }

    private static void updateLocation(
            ContextWrapper context,
            Map.Entry<String, Integer> locWithCount,
            GetLocationsByRefsQuery.Edge locationEdge,
            DateTime currentDate,
            DateTime lastFulfilmentDate) {
        UpdateLocationInput updateLocation = null;
        if ( (null != lastFulfilmentDate) && DateUtils.isSameDay(lastFulfilmentDate, currentDate)) {
            context.addLog("last fulfilment date and the current date are present");
            int fulfilmentCount = locWithCount.getValue();

            Optional<Object> optionalFulfilmentCount =
                    locationEdge.node().attributes().stream().filter(
                            attribute -> attribute.name().equalsIgnoreCase(ACTIVE_CAPACITY)
                    ).map(GetLocationsByRefsQuery.Attribute::value).findFirst();

            if(optionalFulfilmentCount.isPresent()){
                fulfilmentCount = Integer.parseInt(optionalFulfilmentCount.get().toString()) + fulfilmentCount ;
            }

            context.addLog("fulfilmentCount : " + fulfilmentCount);

            List<AttributeInput> attributeInputList = new ArrayList<>();
            attributeInputList.add(AttributeInput.builder().name(LAST_ALLOCATED_DATE).type(ATTRIBUTE_TYPE_STRING).value(currentDate.toString(UTC_DATE_FORMATTER)).build());
            attributeInputList.add(AttributeInput.builder().name(ACTIVE_CAPACITY).type(ATTRIBUTE_TYPE_INTEGER).value(fulfilmentCount).build());

            updateLocation = UpdateLocationInput.builder().id(locationEdge.node().id())
                    .attributes(attributeInputList)
                    .build();

        }
        else{
            List<AttributeInput> attributeInputList = new ArrayList<>();
            attributeInputList.add(AttributeInput.builder().name(LAST_ALLOCATED_DATE).type(ATTRIBUTE_TYPE_STRING).value(currentDate.toString(UTC_DATE_FORMATTER)).build());
            attributeInputList.add(AttributeInput.builder().name(ACTIVE_CAPACITY).type(ATTRIBUTE_TYPE_INTEGER).value(locWithCount.getValue()).build());

            updateLocation = UpdateLocationInput.builder().id(locationEdge.node().id())
                    .attributes(attributeInputList)
                    .build();
        }

        if (null != updateLocation) {
            context.action().mutation(UpdateLocationMutation.builder().input(updateLocation).build());
        }
    }
}

