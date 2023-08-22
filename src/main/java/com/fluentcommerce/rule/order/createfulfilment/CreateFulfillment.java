package com.fluentcommerce.rule.order.createfulfilment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.fulfillment.FulfillmentResponse;
import com.fluentcommerce.graphql.mutation.fulfillment.CreateFulfillmentMutation;
import com.fluentcommerce.graphql.query.fulfilment.GetFulfillmentByRefQuery;
import com.fluentcommerce.graphql.query.location.GetLocationsByRefsQuery;
import com.fluentcommerce.graphql.type.*;
import com.fluentcommerce.model.order.Order;
import com.fluentcommerce.model.order.OrderItem;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.fulfilment.FulfilmentService;
import com.fluentcommerce.service.location.LocationService;
import com.fluentcommerce.service.order.OrderService;
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
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author nitishKumar
 */

@RuleInfo(
        name = "CreateFulfillment",
        description = "create the fulfillment based on the DVM response and call to next {" + PROP_EVENT_NAME + "}",
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

@EventAttribute(name = FULFILMENT)
@Slf4j
public class CreateFulfillment extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        RuleUtils.validateRulePropsIsNotEmpty(context, PROP_EVENT_NAME);
        String eventName = context.getProp(PROP_EVENT_NAME);
        Event event = context.getEvent();
        String orderId = event.getEntityId();
        OrderService orderService = new OrderService(context);
        Order order = orderService.getOrderById(orderId);
        Map<String, Object> eventAttributes = context.getEvent().getAttributes();
        List<FulfillmentResponse> fulfillmentResponses = CommonUtils.convertObjectToDto(eventAttributes.get(FULFILLMENT),
                new TypeReference<List<FulfillmentResponse>>() {
                });
        List<String> fulfilledItemRefList = createFulfillmentData(fulfillmentResponses, order, context);
        if (CollectionUtils.isNotEmpty(fulfilledItemRefList)) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put(FULFILLED_ITEM_REF_LIST, fulfilledItemRefList);
            attributes.putAll(context.getEvent().getAttributes());
            EventUtils.forwardEventInlineWithAttributes(context, eventName, attributes);
        }
    }

    private List<String> createFulfillmentData(List<FulfillmentResponse> fulfillmentResponses, Order order, ContextWrapper context) {
        LocationService locationService = new LocationService(context);
        List<String> fulfilledItemRefList = new ArrayList<>();
        HashMap<String, String> fulfilmentMap = loadExistingFulfillmentData(fulfillmentResponses, context);
        List<GetLocationsByRefsQuery.Edge> locationsData = new ArrayList<>();
        HashSet<String> locationRefList = new HashSet<>();
        fetchLocationRefList(fulfillmentResponses, locationRefList);
        locationService.getLocations(new ArrayList<>(locationRefList), locationsData, null);

        for (FulfillmentResponse responseItem : fulfillmentResponses) {
            List<CreateFulfilmentItemWithFulfilmentInput> fulfilledItems = new ArrayList<>();
            if (!fulfilmentMap.containsKey(responseItem.getRef()) && (responseItem.getStatus().equalsIgnoreCase(WAITING_FULFILMENT) || responseItem.getStatus().equalsIgnoreCase(EXCEPTION))) {
                    for (com.fluentcommerce.dto.fulfillment.FulfilmentOrderItem responseOrderItem : responseItem.getItems()) {
                        fetchFulfilmentItemRefList(order, context, fulfilledItemRefList, fulfilledItems, responseOrderItem);
                    }
                    String fulfillmentType = null;
                    String fromAddressId = null;
                    Optional<GetLocationsByRefsQuery.Edge> location = locationsData.stream().filter(loc -> StringUtils.equalsIgnoreCase(loc.node().ref(),
                            responseItem.getFacilityId())).findFirst();
                    if (location.isPresent() && Objects.nonNull(location.get().node()) && null != location.get().node().type()) {
                        String locationType = location.get().node().type();
                        fulfillmentType = getDeliveryType(order, locationType);
                        fromAddressId = location.get().node().primaryAddress().id();
                    }
                    String fulfillmentRef = responseItem.getRef();

                    boolean isValidated = validateInputData(fromAddressId, fulfillmentType, fulfilledItems,
                            order, fulfillmentRef);
                   createFulfilment(order, context, responseItem, fulfilledItems, fulfillmentType, fromAddressId, isValidated);
            } else {
                context.addLog("fulfillment is already present with fulfillmentRef:: " + responseItem.getRef() + " , hence not going to create with this ref .");
            }
        }
        return fulfilledItemRefList;
    }

    private void createFulfilment(Order order, ContextWrapper context, FulfillmentResponse responseItem, List<CreateFulfilmentItemWithFulfilmentInput> fulfilledItems, String fulfillmentType,
                                  String fromAddressId, boolean isValidated) {
        if (isValidated) {
            List<AttributeInput> fulfillmentAttributes = createFulfillmentAttributeData(responseItem.getStatus(), responseItem.getExceptionReason());
            CreateFulfilmentInput
                    createFulfilmentInput = CreateFulfilmentInput.builder()
                    .fromAddress(AddressId.builder().id(fromAddressId).build()).toAddress(AddressId.builder().id(order.getDeliveryAddress().getId()).build())
                    .type(fulfillmentType)
                    .items(fulfilledItems)
                    .order(OrderId.builder().id(order.getId()).build())
                    .ref(responseItem.getRef())
                    .deliveryType(order.getDeliveryType())
                    .attributes(fulfillmentAttributes)
                    .customer(CustomerId.builder().id(order.getCustomer().getId()).build())
                    .build();
            CreateFulfillmentMutation createFulfillmentMutation = CreateFulfillmentMutation.builder().input(createFulfilmentInput).build();
            context.action().mutation(createFulfillmentMutation);
        } else {
            context.addLog("Unable to create fulfillment for fulfillmentRef:: " + responseItem.getRef() + " with requested data as fromAddressId::" + fromAddressId +
                    " fulfillmentType::" + fulfillmentType);
        }
    }

    private void fetchFulfilmentItemRefList(Order order, ContextWrapper context, List<String> fulfilledItemRefList, List<CreateFulfilmentItemWithFulfilmentInput> fulfilledItems, com.fluentcommerce.dto.fulfillment.FulfilmentOrderItem responseOrderItem) {
        Optional<OrderItem> itemData = order.getItems().stream().filter(orderItem -> orderItem.getRef().equalsIgnoreCase(responseOrderItem.getRef())).findFirst();
        if (itemData.isPresent() && itemData.get().getQuantity() > 0) {
            Map<String, Object> itemAttributes = itemData.get().getAttributes();
            String lineItemStatus = (String) itemAttributes.get(ORDER_LINE_STATUS);
            if (null != lineItemStatus && lineItemStatus.equalsIgnoreCase(FULFILMENT_REQUESTED)) {
                context.addLog("itemData::" + "getRef()::" + itemData.get().getRef() + "id::" + itemData.get().getId() + "quantity::" + itemData.get().getQuantity());
                fulfilledItems.add(CreateFulfilmentItemWithFulfilmentInput.builder()
                        .ref(itemData.get().getRef())
                        .orderItem(OrderItemId.builder().id(itemData.get().getId()).build())
                        .requestedQuantity(itemData.get().getQuantity()).build());
                fulfilledItemRefList.add(itemData.get().getRef());
            } else {
                context.addLog("oder line item with id :: " + itemData.get().getId() + " status is not in desired state");
            }
        } else {
            context.addLog("line item is not eligible for to become a fulfillment items with lineItemRef::" + responseOrderItem.getRef());
        }
    }

    private void fetchLocationRefList(List<FulfillmentResponse> fulfillmentResponses, HashSet<String> locationRefList) {
        for (FulfillmentResponse responseItem : fulfillmentResponses) {
            locationRefList.add(responseItem.getFacilityId());
        }
    }

    private HashMap<String, String> loadExistingFulfillmentData(List<FulfillmentResponse> fulfillmentResponses, ContextWrapper context) {
        HashMap<String, String> fulfilmentMap = new HashMap<>();
        List<String> fulfilmentRefList = new ArrayList<>();
        for (FulfillmentResponse responseItem : fulfillmentResponses) {
            if (null != responseItem.getRef()) {
                fulfilmentRefList.add(responseItem.getRef());
            }
        }
        context.addLog("fulfilment ref list for querying::" + fulfilmentRefList);
        FulfilmentService fulfilmentService = new FulfilmentService(context);
        GetFulfillmentByRefQuery.Fulfilments fulfilmentsData = fulfilmentService.getFulfillments(fulfilmentRefList);
        if (Objects.nonNull(fulfilmentsData) && null != fulfilmentsData.edges() && CollectionUtils.isNotEmpty(fulfilmentsData.edges())) {
            for (GetFulfillmentByRefQuery.Edge edge : fulfilmentsData.edges()) {
                fulfilmentMap.put(edge.node().ref(), edge.node().id());
            }
        }
        context.addLog("loaded data after querying ::" + fulfilmentMap);
        return fulfilmentMap;
    }

    private List<AttributeInput> createFulfillmentAttributeData(String status, String exceptionReason) {
        List<AttributeInput> fulfillmentAttributes = new ArrayList<>();
        fulfillmentAttributes.add(AttributeInput.builder()
                .name(INITIAL_FULFILMENT_STATUS)
                .type(ATTRIBUTE_TYPE_STRING)
                .value(status)
                .build());
        if (StringUtils.equalsIgnoreCase(status, EXCEPTION)) {
            fulfillmentAttributes.add(AttributeInput.builder()
                    .name(EXCEPTION_REASON)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(exceptionReason)
                    .build());
            fulfillmentAttributes.add(AttributeInput.builder()
                    .name(IS_EXCEPTION)
                    .type(ATTRIBUTE_TYPE_STRING)
                    .value(FULFILMENT_CREATION)
                    .build());
        }
        return fulfillmentAttributes;
    }

    private boolean validateInputData(String fromAddressId, String fulfillmentType,
                                      List<CreateFulfilmentItemWithFulfilmentInput> fulfilledItems, Order order,
                                      String fulfillmentRef) {
        boolean isValidated = false;

        String deliveryType = order.getDeliveryType();
        String customerId = order.getCustomer().getId();
        String toAddress = order.getDeliveryAddress().getId();
        if (null != fromAddressId && null != toAddress && null != fulfillmentType
                && CollectionUtils.isNotEmpty(fulfilledItems) && null != order.getId()
                && null != fulfillmentRef && null != deliveryType && null != customerId) {
            isValidated = true;
        }
        return isValidated;
    }

    private String getDeliveryType(Order order, String locationType) {
        return order.getType() + "_" + getSuffixAsPerLocationType(locationType);
    }

    private static String getSuffixAsPerLocationType(String locationType) {
        if (locationType.equalsIgnoreCase(PROP_LOCATION_TYPE_WAREHOUSE)) {
            return PFDC;
        }
        return null;
    }
}