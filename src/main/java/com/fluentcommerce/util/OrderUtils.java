package com.fluentcommerce.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.dto.CancelledOrderItem;
import com.fluentcommerce.dto.CancelledOrderItemReasonPayload;
import com.fluentcommerce.dto.CancelledOrderReasonPayload;
import com.fluentcommerce.dto.order.status.OrderItemStatus;
import com.fluentcommerce.dto.order.status.OrderItemStatusPayload;
import com.fluentcommerce.dto.order.status.OrderStatusPayload;
import com.fluentcommerce.dto.payment.PaymentInfo;
import com.fluentcommerce.graphql.mutation.order.UpdateOrderMutation;
import com.fluentcommerce.graphql.query.order.GetOrderByIdQuery;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.graphql.type.UpdateOrderInput;
import com.fluentcommerce.model.order.OrderActivity;
import com.fluentcommerce.model.order.OrderNotes;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */


public class OrderUtils {

    private OrderUtils(){}

    public static OrderNotes checkAndUpdateOrderNotesAttribute(Optional<GetOrderByIdQuery.Attribute> orderNoteAttribute,
                                                              String note, String userKey, String orderId,
                                                               ContextWrapper context) {
        OrderNotes orderNote = null;
        if(orderNoteAttribute.isPresent() && StringUtils.isNotEmpty(orderNoteAttribute.get().value().toString())) {
            List<OrderNotes> cancelledOrderNoteList = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                cancelledOrderNoteList = mapper.readValue(orderNoteAttribute.get().value().toString(), new TypeReference<List<OrderNotes>>() {});
                if (CollectionUtils.isNotEmpty(cancelledOrderNoteList)){
                    context.addLog("orderNotesFound attribute found");
                    orderNote = createCancelledOrderNote(note,userKey);
                    cancelledOrderNoteList.add(orderNote);
                    updateOrderNotesAttribute(context,orderId,cancelledOrderNoteList);
                }
            } catch (IOException exception) {
                context.addLog("Exception Occurred when fetching cancelledOrderNote order attribute " + exception.getMessage());
                return null;
            }
        }else{
            context.addLog("orderNotesFound attribute not found. proceeding for the create orderNotesFound attribute");
            orderNote = createCancelledOrderNote(note,userKey);
            List<OrderNotes> orderNoteList = new ArrayList<>();
            orderNoteList.add(orderNote);
            updateOrderNotesAttribute(context,orderId,orderNoteList);
        }
        return orderNote;
    }

    public static void updateOrderNotesAttribute(ContextWrapper context,String orderId, List<OrderNotes> cancelledOrderNoteList) {
        List<AttributeInput> attributeInputList = createNewAttributeInputAsList(
                ORDER_NOTES,
                ATTRIBUTE_TYPE_JSON,
                cancelledOrderNoteList);

        UpdateOrderInput updateOrderInput =  UpdateOrderInput.builder()
                .id(orderId)
                .attributes(attributeInputList)
                .build();
        UpdateOrderMutation updateOrderAttributesMutation = UpdateOrderMutation.builder().input(updateOrderInput).build();

        context.action().mutation(updateOrderAttributesMutation);
    }

    public static OrderNotes createCancelledOrderNote(String note,String userKey) {
        OrderNotes orderNote = new OrderNotes();
        orderNote.setNoteKey(UUID.randomUUID().toString());
        orderNote.setNoteUserKey(userKey);
        orderNote.setNoteContext(NONE);
        orderNote.setNote(note);
        orderNote.setCreatedDate(Instant.now().toString());
        return orderNote;
    }

    public static List<AttributeInput> createNewAttributeInputAsList(String attributeName, String type, List<OrderNotes> orderNoteList) {
        AttributeInput attrInput = AttributeInput.builder().name(attributeName).type(type).value(orderNoteList).build();
        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(attrInput);
        return attributeInputList;
    }

    public static void checkAndUpdateOrderActivityAttribute(Optional<GetOrderByIdQuery.Attribute> orderActivityAttribute,
                                                            String toOrderStatus, String fromOrderStatus,
                                                            String orderId, ContextWrapper context) {
        OrderActivity orderActivity = null;
        if(orderActivityAttribute.isPresent()) {
            List<OrderActivity> orderActivityList = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                orderActivityList = mapper.readValue(orderActivityAttribute.get().value().toString(), new TypeReference<List<OrderActivity>>() {});
                if (CollectionUtils.isNotEmpty(orderActivityList)){
                    orderActivity = createOrderActivity(fromOrderStatus,toOrderStatus);
                    orderActivityList.add(orderActivity);
                    updateOrderActivityAttribute(context,orderId,orderActivityList);
                }
            } catch (IOException exception) {
                context.addLog("Exception Occurred when fetching orderActivity attribute" + exception.getMessage());

            }
        }else{
            orderActivity = createOrderActivity(fromOrderStatus,toOrderStatus);
            List<OrderActivity> orderActivityList = new ArrayList<>();
            orderActivityList.add(orderActivity);
            updateOrderActivityAttribute(context,orderId,orderActivityList);
        }
    }

    private static OrderActivity createOrderActivity(String fromOrderStatus, String toOrderStatus) {
        OrderActivity orderActivity = new OrderActivity();
        orderActivity.setStatus(toOrderStatus);
        orderActivity.setMessage(ORDER_ACTIVITY_MESSAGE+fromOrderStatus+TO+toOrderStatus+ DOT);
        orderActivity.setUpdatedTime(Instant.now().toString());
        return orderActivity;
    }

    public static void updateOrderActivityAttribute(ContextWrapper context,String orderId, List<OrderActivity> orderActivityList) {
        List<AttributeInput> attributeInputList = createNewAttributeInputAsListData(ORDER_STATUS_ACTIVITY, ATTRIBUTE_TYPE_JSON,
                orderActivityList);
        UpdateOrderInput updateOrderInput =  UpdateOrderInput.builder()
                .id(orderId)
                .attributes(attributeInputList)
                .build();
        UpdateOrderMutation updateOrderAttributesMutation = UpdateOrderMutation.builder().input(updateOrderInput).build();
        context.action().mutation(updateOrderAttributesMutation);
    }
    public static List<AttributeInput> createNewAttributeInputAsListData(String attributeName, String type, List<OrderActivity> orderActivityList) {
        AttributeInput attrInput = AttributeInput.builder().name(attributeName).type(type).value(orderActivityList).build();
        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(attrInput);
        return attributeInputList;
    }

    public static CancelledOrderReasonPayload createPayload(String cancellationReason,String orderRef) {
        CancelledOrderReasonPayload payload = new CancelledOrderReasonPayload();
        payload.setOrderRef(orderRef);
        payload.setStatus(CANCELLED);
        payload.setCancellationReasonText(cancellationReason);
        payload.setUpdateOn(Instant.now().toString());
        return payload;
    }

    public static CancelledOrderItemReasonPayload createPayloadForCancelledOrderItem(GetOrderByIdQuery.OrderById order,
                                                                               String cancellationReason,String orderRef) {
        CancelledOrderItemReasonPayload orderItemPayload = new CancelledOrderItemReasonPayload();
        orderItemPayload.setUpdateOn(Instant.now().toString());
        orderItemPayload.setOrderRef(orderRef);
        ArrayList<CancelledOrderItem> cancelledOrderItemList = new ArrayList<>();
        for (GetOrderByIdQuery.OrderItemEdge itemEdge:order.orderItems().orderItemEdge()) {
            if (Objects.nonNull(itemEdge.orderItemNode()) && Objects.nonNull(itemEdge.orderItemNode().orderItemAttributes())) {
                for (GetOrderByIdQuery.OrderItemAttribute itemAttributes : itemEdge.orderItemNode().orderItemAttributes()) {
                    if (itemAttributes.name().equalsIgnoreCase(ORDER_LINE_STATUS) &&
                            itemAttributes.value().toString().equalsIgnoreCase(CANCELLED)) {
                        CancelledOrderItem cancelledOrderItem = getCancelledOrderItem(cancellationReason, itemEdge, itemAttributes);
                        cancelledOrderItemList.add(cancelledOrderItem);
                    }
                }
            }
        }
        orderItemPayload.setItems(cancelledOrderItemList);
        return orderItemPayload;
    }

    private static CancelledOrderItem getCancelledOrderItem(String cancellationReason, GetOrderByIdQuery.OrderItemEdge itemEdge, GetOrderByIdQuery.OrderItemAttribute itemAttributes) {
        CancelledOrderItem cancelledOrderItem = new CancelledOrderItem();
        cancelledOrderItem.setOrderItemKey(itemEdge.orderItemNode().ref());
        cancelledOrderItem.setCancellationReasonText(cancellationReason);
        cancelledOrderItem.setLineStatus(itemAttributes.value().toString());
        Optional<GetOrderByIdQuery.OrderItemAttribute> autoShipId = itemEdge.orderItemNode().orderItemAttributes().stream()
                .filter(itemAttribute -> itemAttribute.name().equalsIgnoreCase(AUTO_SHIP_ID)).findFirst();
        if (autoShipId.isPresent()) {
            cancelledOrderItem.setAutoshipKey(autoShipId.get().value().toString());
        }

        // This logic will differ when the partial line item cancellation is introduced
        Optional<GetOrderByIdQuery.OrderItemAttribute> cancelledLineItemQuantity = itemEdge.orderItemNode().orderItemAttributes().stream()
                .filter(itemAttribute -> itemAttribute.name().equalsIgnoreCase(ORDERED_LINE_ITEM_QUANTITY)).findFirst();
        if (cancelledLineItemQuantity.isPresent()) {
            cancelledOrderItem.setOriginalOrderQty(cancelledLineItemQuantity.get().value().toString());
            cancelledOrderItem.setCancelledQty(cancelledLineItemQuantity.get().value().toString());
        }
        return cancelledOrderItem;
    }


    public static OrderNotes upsertOrderNotesAttribute(Optional<GetOrderByIdQuery.Attribute> orderNotesAttribute,
                                                 List<OrderNotes> orderNotesList, String orderId, ContextWrapper context) {
        OrderNotes orderNote = null;
        if (orderNotesAttribute.isPresent()
                && StringUtils.isNotEmpty(orderNotesAttribute.get().value().toString())){
            List<OrderNotes> orderNoteList = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                orderNoteList = mapper.readValue(orderNotesAttribute.get().value().toString(), new TypeReference<List<OrderNotes>>() {});
                if (CollectionUtils.isNotEmpty(orderNoteList)){
                    for (OrderNotes note:orderNotesList){
                        orderNote = createdOrderNote(note);
                        orderNoteList.add(orderNote);
                    }
                    updateOrderNotesAttributeData(context,orderId,orderNoteList);
                }
            } catch (IOException exception) {
                context.addLog("Exception Occurred when fetching orderNotes order attribute " + exception.getMessage());
                return null;
            }
        }else{
            List<OrderNotes> orderNoteList = new ArrayList<>();
            for (OrderNotes note:orderNotesList){
                orderNote = createdOrderNote(note);
                orderNoteList.add(orderNote);
            }
            updateOrderNotesAttributeData(context,orderId,orderNoteList);
        }
        return orderNote;
    }

    public static OrderNotes createdOrderNote(OrderNotes note) {
            OrderNotes orderNotes = new OrderNotes();
            orderNotes.setNoteKey(note.getNoteKey());
            orderNotes.setNoteUserKey(note.getNoteUserKey());
            orderNotes.setNoteContext(note.getNoteContext());
            orderNotes.setNote(note.getNote());
            orderNotes.setCreatedDate(note.getCreatedDate());
            return orderNotes;
    }

    public static void updateOrderNotesAttributeData(ContextWrapper context,String orderId, List<OrderNotes> orderNoteList) {
        List<AttributeInput> attributeInputList = createNewAttributeInputAsList(
                ORDER_NOTES,
                ATTRIBUTE_TYPE_JSON,
                orderNoteList);

        UpdateOrderInput updateOrderInput =  UpdateOrderInput.builder()
                .id(orderId)
                .attributes(attributeInputList)
                .build();
        UpdateOrderMutation updateOrderAttributesMutation = UpdateOrderMutation.builder().input(updateOrderInput).build();

        context.action().mutation(updateOrderAttributesMutation);
    }

    public static boolean isItemStatusChanged(GetOrderByIdQuery.OrderById orderData) {
        boolean isItemStatusChanged = false;
        if (orderData != null && orderData.orderItems() != null
                && CollectionUtils.isNotEmpty(orderData.orderItems().orderItemEdge())
        ) {
            for (GetOrderByIdQuery.OrderItemEdge itemEdge : orderData.orderItems().orderItemEdge()) {
                Optional<GetOrderByIdQuery.OrderItemAttribute> orderLineAtt = itemEdge.orderItemNode().orderItemAttributes().stream().filter(
                        itemAttr -> itemAttr.name().equalsIgnoreCase(ORDER_LINE_STATUS)).findFirst();
                Optional<GetOrderByIdQuery.OrderItemAttribute> orderLineAtt1 = itemEdge.orderItemNode().orderItemAttributes().stream().filter(
                        itemAttr -> itemAttr.name().equalsIgnoreCase(OLD_LINE_STATUS)).findFirst();
                if (orderLineAtt.isPresent() && orderLineAtt1.isPresent() && orderLineAtt.get().value().toString().equalsIgnoreCase(CANCELLED)
                && !orderLineAtt.get().value().toString().equalsIgnoreCase(orderLineAtt1.get().value().toString())) {
                    isItemStatusChanged = true;
                    break;
                }
            }
        }
        return isItemStatusChanged;
    }

    public static boolean isItemStatusChanged(GetOrderByIdQuery.OrderById orderData,String lineNUmber) {
        boolean isItemStatusChanged = false;
        if (orderData != null && orderData.orderItems() != null
                && CollectionUtils.isNotEmpty(orderData.orderItems().orderItemEdge())
        ) {
            for (GetOrderByIdQuery.OrderItemEdge itemEdge : orderData.orderItems().orderItemEdge()) {
                if(itemEdge.orderItemNode().ref().equalsIgnoreCase(lineNUmber)){
                    Optional<GetOrderByIdQuery.OrderItemAttribute> orderLineAtt = itemEdge.orderItemNode().orderItemAttributes().stream().filter(
                            itemAttr -> itemAttr.name().equalsIgnoreCase(ORDER_LINE_STATUS)).findFirst();
                    Optional<GetOrderByIdQuery.OrderItemAttribute> orderLineAtt1 = itemEdge.orderItemNode().orderItemAttributes().stream().filter(
                            itemAttr -> itemAttr.name().equalsIgnoreCase(OLD_LINE_STATUS)).findFirst();
                    if (orderLineAtt.isPresent() && orderLineAtt1.isPresent() &&
                            !orderLineAtt.get().value().toString().equalsIgnoreCase(orderLineAtt1.get().value().toString())) {
                        isItemStatusChanged = true;
                        break;
                    }
                }
            }
        }
        return isItemStatusChanged;
    }

    public static OrderStatusPayload createOrderStatusPayload(String orderId,String orderRef,String status) {
        OrderStatusPayload payload = new OrderStatusPayload();
        payload.setOrderId(orderId);
        payload.setOrderRef(orderRef);
        payload.setStatus(status);
        payload.setUpdateOn(Instant.now().toString());
        return payload;
    }

    public static OrderItemStatusPayload createPayloadForOrderItemStatus(GetOrderByIdQuery.OrderById order,
                                                                         String orderRef) {
        OrderItemStatusPayload orderItemPayload = new OrderItemStatusPayload();
        orderItemPayload.setUpdateOn(Instant.now().toString());
        orderItemPayload.setOrderRef(orderRef);
        ArrayList<OrderItemStatus> orderItemList = new ArrayList<>();
        for (GetOrderByIdQuery.OrderItemEdge itemEdge:order.orderItems().orderItemEdge()) {
            OrderItemStatus orderItemStatus = new OrderItemStatus();
            if (Objects.nonNull(itemEdge.orderItemNode()) && Objects.nonNull(itemEdge.orderItemNode().orderItemAttributes())) {
                for (GetOrderByIdQuery.OrderItemAttribute itemAttributes : itemEdge.orderItemNode().orderItemAttributes()) {
                    if (itemAttributes.name().equalsIgnoreCase(ORDER_LINE_STATUS)) {
                        orderItemStatus.setOrderItemKey(itemEdge.orderItemNode().ref());
                        orderItemStatus.setLineStatus(itemAttributes.value().toString());
                    }
                }
            }
            orderItemList.add(orderItemStatus);
        }
        orderItemPayload.setItems(orderItemList);
        return orderItemPayload;
    }

    public static OrderNotes checkAndUpdateOrderNotesAttributeFromConsole(Optional<GetOrderByIdQuery.Attribute> orderNoteAttribute,
                                                               String note, String userKey, String orderId,
                                                               ContextWrapper context,String noteContext) {
        OrderNotes orderNote = null;
        if(orderNoteAttribute.isPresent()) {
            List<OrderNotes> cancelledOrderNoteList = null;
            ObjectMapper mapper = new ObjectMapper();
            try {
                cancelledOrderNoteList = mapper.readValue(orderNoteAttribute.get().value().toString(), new TypeReference<List<OrderNotes>>() {});
                if (CollectionUtils.isNotEmpty(cancelledOrderNoteList)){
                    context.addLog("orderNotesFound attribute found");
                    orderNote = createOrderNote(note,userKey,noteContext);
                    cancelledOrderNoteList.add(orderNote);
                    updateOrderNotesAttribute(context,orderId,cancelledOrderNoteList);
                }
            } catch (IOException exception) {
                context.addLog("Exception Occurred when fetching cancelledOrderNote order attribute " + exception.getMessage());
                return null;
            }
        }else{
            context.addLog("orderNotesFound attribute not found. proceeding for the create orderNotesFound attribute");
            orderNote = createOrderNote(note,userKey,noteContext);
            List<OrderNotes> orderNoteList = new ArrayList<>();
            orderNoteList.add(orderNote);
            updateOrderNotesAttribute(context,orderId,orderNoteList);
        }
        return orderNote;
    }

    public static OrderNotes createOrderNote(String note,String userKey,String noteContext) {
        OrderNotes orderNote = new OrderNotes();
        orderNote.setNoteKey(UUID.randomUUID().toString());
        orderNote.setNoteUserKey(userKey);
        if (StringUtils.isNotEmpty(noteContext)){
            orderNote.setNoteContext(noteContext);
        }else{
            orderNote.setNoteContext(NONE);
        }
        orderNote.setNote(note);
        orderNote.setCreatedDate(Instant.now().toString());
        return orderNote;
    }

    public static CancelledOrderItemReasonPayload fetchAndPayloadForCancelledOrderItem(GetOrderByIdQuery.OrderById order,
                                                                                     String cancellationReason,String orderRef,String orderItemStatus) {
        CancelledOrderItemReasonPayload orderItemPayload = new CancelledOrderItemReasonPayload();
        orderItemPayload.setUpdateOn(Instant.now().toString());
        orderItemPayload.setOrderRef(orderRef);
        ArrayList<CancelledOrderItem> cancelledOrderItemList = new ArrayList<>();
        for (GetOrderByIdQuery.OrderItemEdge itemEdge:order.orderItems().orderItemEdge()) {
            if (Objects.nonNull(itemEdge.orderItemNode()) && Objects.nonNull(itemEdge.orderItemNode().orderItemAttributes())) {
                for (GetOrderByIdQuery.OrderItemAttribute itemAttributes : itemEdge.orderItemNode().orderItemAttributes()) {
                    if (itemAttributes.name().equalsIgnoreCase(ORDER_LINE_STATUS) &&
                            itemAttributes.value().toString().equalsIgnoreCase(orderItemStatus)) {
                        CancelledOrderItem cancelledOrderItem = getCancelledOrderItem(cancellationReason, itemEdge, itemAttributes);
                        cancelledOrderItemList.add(cancelledOrderItem);
                    }
                }
            }
        }
        orderItemPayload.setItems(cancelledOrderItemList);
        return orderItemPayload;
    }
    public static PaymentInfo fetchPaymentInfoAttribute(Optional<GetOrderByIdQuery.Attribute> optionalPaymentInfo, ContextWrapper context) {
        PaymentInfo paymentInfo = null;
        if (optionalPaymentInfo.isPresent()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                paymentInfo = mapper.readValue(optionalPaymentInfo.get().value().toString(),
                        new TypeReference<PaymentInfo>() {});
            } catch (IOException exception) {
                context.addLog("Exception Occurred when fetching payment info attribute " + exception.getMessage());
                return null;
            }
        }
        return paymentInfo;

    }

    public static OrderStatusPayload createOrderStatusPayload(String orderKey,String animalBusinessCare) {
        OrderStatusPayload payload = new OrderStatusPayload();
        if (null != orderKey){
            payload.setOrderKey(orderKey);
        }
        if (null != animalBusinessCare){
            payload.setAnimalCareBusiness(animalBusinessCare);
        }
        return payload;
    }
}
