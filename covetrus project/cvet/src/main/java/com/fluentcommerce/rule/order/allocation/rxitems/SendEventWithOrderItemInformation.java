package com.fluentcommerce.rule.order.allocation.rxitems;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.orderitem.OrderItemDto;
import com.fluentcommerce.dto.orderitem.PharmacyAllocationForOrderItems;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.EventUtils;
import com.fluentretail.rubix.event.Event;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.EventInfoVariables;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author Nandhakumar
 */
@RuleInfo(
        name = "SendEventWithOrderItemInformation",
        description = "For order line items in status {" + PROP_ORDER_LINE_STATUS + "} fetch the item list {" + PROP_ITEM_TYPE_LIST + "} information of appoval type {" + PROP_APPROVAL_TYPE_LIST + "} and send event {" + PROP_EVENT_NAME + "} else send event {" + PROP_NO_MATCHING_EVENT_NAME + "} ",
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
        description = "Event name to be triggered"
)
@ParamString(
        name = PROP_NO_MATCHING_EVENT_NAME,
        description = "Event name to be triggered"
)
@ParamString(
        name = PROP_APPROVAL_TYPE_LIST,
        description = "Approval type list"
)
@ParamString(
        name = PROP_ORDER_LINE_STATUS,
        description = "order line status"
)
@ParamString(
        name = PROP_ITEM_TYPE_LIST,
        description = "Item type information to be collected"
)
@Slf4j
public class SendEventWithOrderItemInformation extends BaseRule {

    private static final String CLASS_NAME = SendEventWithOrderItemInformation.class.getSimpleName();

    @Override
    public void run(ContextWrapper context) {

        Event event = context.getEvent();
        log.info("[{}] - rule: {} incoming event {}", event.getAccountId(), CLASS_NAME, event);

        String eventName = context.getProp(PROP_EVENT_NAME);
        String noMatchEventName = context.getProp(PROP_NO_MATCHING_EVENT_NAME);
        String orderLineStatus = context.getProp(PROP_ORDER_LINE_STATUS);
        List<String> approvalTypeList = context.getPropList(PROP_APPROVAL_TYPE_LIST, String.class);
        List<String> itemTypeList = context.getPropList(PROP_ITEM_TYPE_LIST, String.class);

        String orderId = context.getEvent().getEntityId();
        OrderService orderService = new OrderService(context);
        GetOrderByIdQuery.OrderById orderById = orderService.getOrderWithOrderItems(orderId);

        // Construct the item allocation dto based on the criteria's
        List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList = new ArrayList<>();

        // Iterate all the order items
        for (GetOrderByIdQuery.OrderItemEdge orderItem : orderById.orderItems().orderItemEdge()) {

            Optional<Object> approvalTypeValue =
                    orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                            variantProductAttribute -> variantProductAttribute.name().equalsIgnoreCase(APPROVAL_TYPE)
                    ).map(GetOrderByIdQuery.VariantProductAttribute::value).findFirst();

            if (approvalTypeValue.isPresent() &&
                    approvalTypeList.contains(approvalTypeValue.get().toString().toUpperCase())) {

                Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemAttribute = orderItem.orderItemNode().orderItemAttributes().stream().filter(
                        orderItemAttribute -> ((orderItemAttribute.name().equalsIgnoreCase(ORDER_LINE_STATUS)) && (orderItemAttribute.value().toString().equalsIgnoreCase(orderLineStatus)))
                ).findFirst();

                // if the order item matches the line status then proceed further
                if (optionalOrderItemAttribute.isPresent()) {

                    context.addLog(String.valueOf(System.currentTimeMillis()));
                    context.addLog("line status is matching , order item id : " + orderItem.orderItemNode().id() + " and order item status value : " + optionalOrderItemAttribute.get().value());

                    boolean facilityAssigned = false;
                    Optional<GetOrderByIdQuery.OrderItemAttribute> optionalAssignedFacilityId =
                            orderItem.orderItemNode().orderItemAttributes().stream().filter(
                                    orderItemAttribute -> (orderItemAttribute.name().equalsIgnoreCase(FACILITY_ID))
                            ).findFirst();

                    // Get the prescription id
                    Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemPrescription =
                            orderItem.orderItemNode().orderItemAttributes().stream().filter(
                                    orderItemAttribute -> (orderItemAttribute.name().equalsIgnoreCase(PRESCRIPTION_ID))
                            ).findFirst();

                    // order item list with pre-assigned facility
                    if (optionalAssignedFacilityId.isPresent() &&
                            StringUtils.isNotEmpty(optionalAssignedFacilityId.get().value().toString())) {

                        facilityAssigned = true;

                        Optional<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsOptional =
                                pharmacyAllocationForOrderItemsList.stream().filter(
                                        orderItemsAllocation -> (StringUtils.isNotEmpty(orderItemsAllocation.getAssignedFacilityId()) && orderItemsAllocation.getAssignedFacilityId().equalsIgnoreCase(optionalAssignedFacilityId.get().value().toString()))
                                ).findFirst();

                        constructAllocationItemsForPreAssignedFacility(
                                pharmacyAllocationForOrderItemsList,
                                orderItem,
                                optionalAssignedFacilityId.get(),
                                optionalOrderItemPrescription,
                                pharmacyAllocationForOrderItemsOptional);
                    }

                    // If facility is not assigned proceed for finding a new facility
                    handleItemsWithNoPreAssignedFacility(
                            itemTypeList,
                            pharmacyAllocationForOrderItemsList,
                            orderItem,
                            facilityAssigned,
                            optionalOrderItemPrescription);
                }
            }
        }

        sendEventBasedOnOrderItemList(
                context,
                eventName,
                noMatchEventName,
                pharmacyAllocationForOrderItemsList
        );
    }

    private static void constructAllocationItemsForPreAssignedFacility(
            List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList,
            GetOrderByIdQuery.OrderItemEdge orderItem,
            GetOrderByIdQuery.OrderItemAttribute optionalAssignedFacilityId,
            Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemPrescription,
            Optional<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsOptional) {

        if (pharmacyAllocationForOrderItemsOptional.isPresent()) {
            // add the item to the same facility dto if it is present
            PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems = pharmacyAllocationForOrderItemsOptional.get();
            List<OrderItemDto> orderItemDtoList = pharmacyAllocationForOrderItems.getOrderItemDtoList();

            OrderItemDto orderItemDto = getOrderItemDto(
                    orderItem,
                    null,
                    optionalOrderItemPrescription);

            // add the order item dto to the list
            orderItemDtoList.add(orderItemDto);
        } else {
            // create a new allocation item object
            PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems = new PharmacyAllocationForOrderItems();

            List<OrderItemDto> orderItemDtoList = new ArrayList<>();

            OrderItemDto orderItemDto = getOrderItemDto(
                    orderItem,
                    null,
                    optionalOrderItemPrescription);

            // create a new order item
            orderItemDtoList.add(orderItemDto);
            pharmacyAllocationForOrderItems.setOrderItemDtoList(orderItemDtoList);

            // set the facility id
            pharmacyAllocationForOrderItems.setAssignedFacilityId(optionalAssignedFacilityId.value().toString());

            //add the object to the list
            pharmacyAllocationForOrderItemsList.add(pharmacyAllocationForOrderItems);

        }
    }

    private void handleItemsWithNoPreAssignedFacility(
            List<String> itemTypeList,
            List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList,
            GetOrderByIdQuery.OrderItemEdge orderItem,
            boolean facilityAssigned,
            Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemPrescription) {
        if (!facilityAssigned) {
            Map<String, String> itemTypeMap = new HashMap<>();
            for (String itemType : itemTypeList) {
                captureOrderItemTypeInformation(
                        itemType,
                        orderItem,
                        itemTypeMap
                );
            }

            // ConstructAllocationItems
            constructAllocationItems(
                    pharmacyAllocationForOrderItemsList,
                    orderItem,
                    optionalOrderItemPrescription,
                    itemTypeMap);
        }
    }

    private void captureOrderItemTypeInformation(
            String itemType,
            GetOrderByIdQuery.OrderItemEdge orderItem,
            Map<String, String> itemTypeMap) {

        Optional<GetOrderByIdQuery.VariantProductAttribute> optionalVariantProductAttribute =
                orderItem.orderItemNode().product().asVariantProduct().variantProductAttributes().stream().filter(
                        variantProductAttribute -> variantProductAttribute.name().equalsIgnoreCase(itemType)
                ).findFirst();

        if (optionalVariantProductAttribute.isPresent()) {
            itemTypeMap.put(itemType, optionalVariantProductAttribute.get().value().toString());
        }
    }

    private static void constructAllocationItems(
            List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList,
            GetOrderByIdQuery.OrderItemEdge orderItem,
            Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemPrescription,
            Map<String, String> itemTypeMap) {

        if (optionalOrderItemPrescription.isPresent()) {
            Optional<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsOptional =
                    pharmacyAllocationForOrderItemsList.stream().filter(
                            orderItemsAllocation -> (StringUtils.isNotEmpty(orderItemsAllocation.getPrescriptionId()) && orderItemsAllocation.getPrescriptionId().equalsIgnoreCase(optionalOrderItemPrescription.get().value().toString()))
                    ).findFirst();

            if (pharmacyAllocationForOrderItemsOptional.isPresent()) {
                // add the item to the same prescription id object
                PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems = pharmacyAllocationForOrderItemsOptional.get();
                List<OrderItemDto> orderItemDtoList = pharmacyAllocationForOrderItems.getOrderItemDtoList();

                OrderItemDto orderItemDto = getOrderItemDto(
                        orderItem,
                        itemTypeMap,
                        optionalOrderItemPrescription);

                orderItemDtoList.add(orderItemDto);

            }
            else {
                // create a new allocation item object
                PharmacyAllocationForOrderItems pharmacyAllocationForOrderItems = new PharmacyAllocationForOrderItems();

                List<OrderItemDto> orderItemDtoList = new ArrayList<>();

                OrderItemDto orderItemDto = getOrderItemDto(
                        orderItem,
                        itemTypeMap,
                        optionalOrderItemPrescription);

                // create a new order item
                orderItemDtoList.add(orderItemDto);
                pharmacyAllocationForOrderItems.setOrderItemDtoList(orderItemDtoList);

                // set the prescription id
                pharmacyAllocationForOrderItems.setPrescriptionId(optionalOrderItemPrescription.get().value().toString());

                //add the object to the list
                pharmacyAllocationForOrderItemsList.add(pharmacyAllocationForOrderItems);
            }
        }
    }

    private static OrderItemDto getOrderItemDto(
            GetOrderByIdQuery.OrderItemEdge orderItem,
            Map<String, String> itemTypeMap,
            Optional<GetOrderByIdQuery.OrderItemAttribute> optionalOrderItemPrescription) {

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.orderItemNode().id());
        orderItemDto.setOrderItemRef(orderItem.orderItemNode().ref());
        orderItemDto.setProductRef(orderItem.orderItemNode().product().asVariantProduct().ref());
        orderItemDto.setRequestedQuantity(orderItem.orderItemNode().quantity());
        orderItemDto.setPrescriptionId(optionalOrderItemPrescription.isPresent() ?
                optionalOrderItemPrescription.get().value().toString() : "");
        // check item type map is null or not and assign the value
        if (null != itemTypeMap) {
            orderItemDto.setItemTypeMap(itemTypeMap);
        }

        return orderItemDto;
    }


    private static void sendEventBasedOnOrderItemList(
            ContextWrapper context,
            String eventName,
            String noMatchEventName,
            List<PharmacyAllocationForOrderItems> pharmacyAllocationForOrderItemsList
    ) {
        if (CollectionUtils.isNotEmpty(pharmacyAllocationForOrderItemsList)) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.putAll(context.getEvent().getAttributes());

            attributes.put(EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS, pharmacyAllocationForOrderItemsList);

            EventUtils.forwardEventInlineWithAttributes(
                    context,
                    eventName,
                    attributes);
        } else {
            context.addLog("No match event Name has been invoked");
            EventUtils.forwardInline(
                    context,
                    noMatchEventName);
        }
    }
}