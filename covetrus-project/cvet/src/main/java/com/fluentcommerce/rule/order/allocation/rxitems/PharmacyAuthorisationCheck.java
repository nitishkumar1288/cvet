package com.fluentcommerce.rule.order.allocation.rxitems;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.OrderItemDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.service.settings.SettingsService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.exceptions.RubixException;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "PharmacyAuthorisationCheck",
        description = "send event {" + PROP_EVENT_NAME + "} if authorisation matches",
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
public class PharmacyAuthorisationCheck extends BaseRule {

    private static final String CLASS_NAME = PharmacyAuthorisationCheck.class.getSimpleName();
    static final String ERROR_MESSAGE = "Mapping Data Failed";

    @Override
    public void run(ContextWrapper context) {
        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);

        List<PharmacyAllocationForOrderItems> remainingOrderItems =
                pharmacyAllocationForOrderItemsList.stream().filter(
                        allocationItems -> StringUtils.isEmpty(allocationItems.getAssignedFacilityId())
                ).collect(Collectors.toList());
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderById = orderService.getOrderWithFulfilmentItems(context.getEvent().getEntityId());

        // Get the delivery address and from the settings get the authorisation
        String deliveryState =  orderById.fulfilmentChoice().deliveryAddress().state();
        context.addLog("deliveryState " + deliveryState);

        if(StringUtils.isNotEmpty(deliveryState)) {

            for (PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems : remainingOrderItems) {

                if(CollectionUtils.isNotEmpty(pharmacyAllocationForOrderItems.getLocationRefList())) {

                    // Get the list of settings name
                    List<String> settingsNameList = new ArrayList<>();
                    Map<String, String> settingNameWithLocationMap = new HashMap<>();


                    List<String> locationRefList = pharmacyAllocationForOrderItems.getLocationRefList();
                    locationRefList.stream().forEach(
                            locationRef -> {
                                settingsNameList.add(locationRef + UNDERSCORE + deliveryState);
                                settingNameWithLocationMap.put(locationRef + UNDERSCORE + deliveryState, locationRef);
                            }
                    );

                    context.addLog("settingsName " + settingsNameList);
                    context.addLog("settingNameWithLocationMap " + settingNameWithLocationMap);

                    SettingsService settingsService = new SettingsService(context);
                    List<GetSettingsByNameQuery.Edge> settingsList =
                            settingsService.getSettingsByName(
                                    context,
                                    settingsNameList
                            );


                    for (GetSettingsByNameQuery.Edge setting : settingsList) {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            checkPharmacyAuthCondition(pharmacyAllocationForOrderItems, settingNameWithLocationMap, setting, mapper);

                        } catch (IOException exception) {
                            context.addLog("Exception Occurred when mapping hashmap " + exception.getMessage());
                            throw new RubixException(500, ERROR_MESSAGE);
                        }
                    }
                }
            }

            EventUtils.forwardInline(
                    context,
                    eventName
            );
        }

        emptyDeliveryStateCheck(
                context,
                deliveryState);

    }

    private static void emptyDeliveryStateCheck(
            ContextWrapper context,
            String deliveryState) {
        if(StringUtils.isEmpty(deliveryState)) {
            context.addLog("Delivery state value is empty so can not proceed further");
        }
    }

    private static void checkPharmacyAuthCondition(
            PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems,
            Map<String, String> settingNameWithLocationMap,
            GetSettingsByNameQuery.Edge setting, ObjectMapper mapper) throws IOException {
        HashMap<String, String> itemTypeMapFromSetting = mapper.readValue(setting.node().lobValue().toString(), new TypeReference<HashMap<String, String>>() {
        });

        boolean valueMatched = true;

        for (OrderItemDto orderItemDto : pharmacyAllocationForOrderItems.getOrderItemDtoList()) {

            // from the order type map fetch all the yes values
            Map<String, String> itemTypeMap = orderItemDto.getItemTypeMap();

            // construct a list which has all the yes value list along with the
            List<String> itemTypeList =
                    itemTypeMap.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue().equalsIgnoreCase(YES_VALUE))
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());

            // if the value of isCompound is No then it is a commercial product
            if (itemTypeMap.containsKey(IS_COMPOUND) && itemTypeMap.get(IS_COMPOUND)
                    .equalsIgnoreCase(NO_VALUE)) {
                itemTypeList.add(IS_COMMERCIAL);
            }

            if (CollectionUtils.isNotEmpty(itemTypeList)) {
                // if any of the criteria fails , mark it as false
                for (String itemType : itemTypeList) {
                    if (!(itemTypeMapFromSetting.containsKey(itemType) && itemTypeMapFromSetting.get(itemType).equalsIgnoreCase(YES_VALUE))) {
                        valueMatched = false;
                        break;
                    }
                }
            }
        }

        // if the value is false then remove the location information frm the allocation object
        if (!valueMatched) {
            pharmacyAllocationForOrderItems.getLocationRefList().remove(settingNameWithLocationMap.get(setting.node().name()));
        }
    }
}