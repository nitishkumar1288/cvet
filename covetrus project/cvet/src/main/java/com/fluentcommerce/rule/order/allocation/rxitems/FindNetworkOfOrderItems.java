package com.fluentcommerce.rule.order.allocation.rxitems;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.NetworkForOrderItem;
import com.fluentcommerce.dto.orderitem.OrderItemDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.graphql.query.networks.GetNetworksByRefAndStatusQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderItemWithOrderInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.network.NetworkService;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentcommerce.util.SettingUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventAttribute;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.IOException;
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
        name = "FindNetworkOfOrderItems",
        description = "Send Event {" + PROP_EVENT_NAME + "} after finding the network information with settings {" + PROP_SETTING_NAME_FOR_NETWORK_DETERMINATION + "}for all unassigned items",
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
@ParamString(
        name = PROP_SETTING_NAME_FOR_NETWORK_DETERMINATION,
        description = "Settings name which has network information"
)


@EventAttribute(name = EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS)
@Slf4j
public class FindNetworkOfOrderItems extends BaseRule {

    private static final String CLASS_NAME = FindNetworkOfOrderItems.class.getSimpleName();
    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);
        String settingNameForNetworkDetermination = context.getProp(PROP_SETTING_NAME_FOR_NETWORK_DETERMINATION);
        List<String> networkStatusList = context.getPropList(PROP_NETWORK_STATUS,String.class);
        List<String> locationStatusList = context.getPropList(PROP_LOCATION_STATUS,String.class);

        // fetch the event attributes
        Map<String,Object> eventAttributes = context.getEvent().getAttributes();
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = (List<PharmacyAllocationForOrderItems>) eventAttributes.get(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS);

        List<PharmacyAllocationForOrderItems> remainingOrderItems =
                pharmacyAllocationForOrderItemsList.stream().filter(
                        allocationItems -> StringUtils.isEmpty(allocationItems.getAssignedFacilityId())
                ).collect(Collectors.toList());


        Object networkValue = SettingUtils.getSettingJSONValue(context,settingNameForNetworkDetermination);
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<NetworkForOrderItem> networkForOrderItemList = mapper.readValue(networkValue.toString(), new TypeReference<List<NetworkForOrderItem>>() {
            });

            context.addLog("networkForOrderItemList " +networkForOrderItemList);

            List<String> networkRefList = networkForOrderItemList.stream().map(
                    NetworkForOrderItem::getNetworkRef
            ).collect(Collectors.toList());

            NetworkService networkService = new NetworkService(context);
            GetNetworksByRefAndStatusQuery.Data getNetworkByRefAndStatusData = networkService.getNetworksByRefAndStatus(
                    networkRefList,
                    networkStatusList,
                    locationStatusList
            );

            for(NetworkForOrderItem networkForOrderItem : networkForOrderItemList) {

                for (PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems : remainingOrderItems) {

                    boolean isItemTypeMatching =  isItemTypeMatching(
                            networkForOrderItem,
                            pharmacyAllocationForOrderItems,
                            true);

                    if(isItemTypeMatching){

                        List<String> orderItemIdList = pharmacyAllocationForOrderItems.getOrderItemDtoList().stream().map(
                                OrderItemDto::getId
                        ).collect(Collectors.toList());

                        // update the network information in the attributes section
                        updateOrderItemAttributes(
                                networkForOrderItem.getNetworkRef(),
                                orderItemIdList,
                                context
                        );

                        Optional<GetNetworksByRefAndStatusQuery.Edge> optionalNetwork =
                                getNetworkByRefAndStatusData.networks().edges().stream().filter(
                                        network -> network.node().ref().equalsIgnoreCase( networkForOrderItem.getNetworkRef())
                                ).findFirst();

                        if(optionalNetwork.isPresent()) {

                            List<String> locationRefList = optionalNetwork.get().node().locations().edges().stream().map(
                                    GetNetworksByRefAndStatusQuery.Edge1::node
                            ).map(GetNetworksByRefAndStatusQuery.Node1::ref).collect(Collectors.toList());

                            // assign the network and the location ref list
                            pharmacyAllocationForOrderItems.setNetworkRef(networkForOrderItem.getNetworkRef());
                            pharmacyAllocationForOrderItems.setLocationRefList(locationRefList);
                            pharmacyAllocationForOrderItems.setDefaultLocationRefInNetwork(networkForOrderItem.getDefaultLocationInNetwork());
                        }
                    }
                }
            }

            // Logging all allocation items for which network was not assigned
            for (PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems : remainingOrderItems) {
                if(StringUtils.isEmpty(pharmacyAllocationForOrderItems.getNetworkRef())) {
                    context.addLog("No network was found for the order items : " + pharmacyAllocationForOrderItems.getOrderItemDtoList().toString());
                }
            }

            EventUtils.forwardInline(
                    context,
                    eventName);

        } catch (IOException exception) {
            context.addLog("Exception occurred when mapping network value " + exception.getMessage());
        }
    }

    private static boolean isItemTypeMatching(
            NetworkForOrderItem networkForOrderItem,
            PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems,
            boolean isItemTypeMatching) {
        // Check whether the item type is matching for the list of order item id under the object
        for (OrderItemDto orderItemDto : pharmacyAllocationForOrderItems.getOrderItemDtoList()) {
            Map<String, String> itemTypeMap = orderItemDto.getItemTypeMap();
            if (!(itemTypeMap != null && itemTypeMap.containsKey(networkForOrderItem.getItemType()) && networkForOrderItem.getItemValue().equalsIgnoreCase(itemTypeMap.get(networkForOrderItem.getItemType())))) {
                isItemTypeMatching = false;
            }
        }
        return isItemTypeMatching;
    }

    private void updateOrderItemAttributes(
            String networkRef,
            List<String> orderItemIdList,
            ContextWrapper context) {
        List<UpdateOrderItemWithOrderInput> updateOrderItems = new ArrayList<>();
        for(String orderItemId : orderItemIdList) {
            List<AttributeInput> itemAttributes = new ArrayList<>();
            itemAttributes.add(
                    AttributeInput.builder().name(SELECTED_NETWORK).type(ATTRIBUTE_TYPE_STRING).value(networkRef).build()
            );

            updateOrderItems.add(
                    UpdateOrderItemWithOrderInput
                            .builder()
                            .id(orderItemId)
                            .attributes(itemAttributes)
                            .build());
        }

        OrderService orderService = new OrderService(context);
        orderService.updateOrderItems(updateOrderItems,context.getEvent().getEntityId());
    }
}