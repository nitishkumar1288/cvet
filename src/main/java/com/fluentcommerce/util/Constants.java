package com.fluentcommerce.util;

/**
 * Use this class to define all the constants you will use in your rules.
 */
public final class Constants {

    private Constants() {
    }

    public static final String PROP_WEBHOOK_SETTING_NAME = "webhookSettingName";
    public static final String PROP_EVENT_NAME = "eventName";
    public static final String PROP_ALL_MATCH_EVENT_NAME = "allMatchEventName";
    public static final String PROP_NO_MATCHING_EVENT_NAME = "noMatchEventName";
    public static final String PROP_PARTIAL_MATCH_EVENT_NAME = "partialMatchEventName";
    public static final String PROP_ORDER_LINE_STATUS = "orderLineStatus";
    public static final String ITEMS = "items";
    public static final String ORDER_LINE_STATUS = "lineStatus";
    public static final String ORDERED_LINE_ITEM_QUANTITY = "orderedLineItemQuantity";
    public static final String ATTRIBUTE_TYPE_STRING = "STRING";
    public static final String ORDER_ITEM_STATUS_ACTIVE = "ACTIVE";
    public static final String RX_MATCH_ORDER_STATUS_RX_ACTIVE = "RX_ACTIVE";
    public static final String ORDER_ITEM_STATUS_PENDING = "PENDING";
    public static final String RX_MATCH_ORDER_STATUS_RX_PENDING = "RX_PENDING";
    public static final String TIME_MEASURMENT_UNIT = "unit";
    public static final String IN_DAYS = "D";
    public static final String IN_MINUTE = "M";
    public static final String IN_HOUR = "H";
    public static final String IN_SECONDS = "S";
    public static final String UTC_DATE_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_TIME_FORMATTER_EXCLUDING_MILLISECONDS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String ORDER_ITEM_STATUS_APPROVED = "APPROVED";
    public static final String ORDER_ITEM_STATUS_DECLINED = "DECLINED";
    public static final String PRESCRIPTION_ID = "prescriptionId";
    public static final String FACILITY_ID = "facilityId";
    public static final String IS_PRESCRIPTION_REQUIRED = "isPrescriptionRequired";
    public static final String REFILLS_REMAINING = "refillsRemaining";
    public static final String REASON_FOR_CANCELLATION = "reasonForCancellation";

    public static final Integer DEFAULT_PAGE_SIZE = 100;
    public static final String DATE_FORMAT = "dd/MM/yyyy hh:mm:ss";
    public static final Integer DEFAULT_PAGINATION_PAGE_SIZE = 100;

    public static final Integer ERROR_CODE_400 = 400;

    public static final String ENTITY_TYPE_ORDER = "ORDER";
    public static final String ENTITY_TYPE_FULFILMENT = "FULFILMENT";
    public static final String ENTITY_TYPE_RETURN_ORDER = "RETURN_ORDER";
    public static final String ENTITY_TYPE_CREDIT_MEMO = "CREDIT_MEMO";

    public static final String ENTITY_TYPE_INVENTORY_CATALOGUE = "INVENTORY_CATALOGUE";
    public static final String ENTITY_TYPE_INVENTORY_POSITION = "INVENTORY_POSITION";
    public static final String PROP_ATTRIBUTE_NAME = "attributeName";

    public static final String UTC_TIME_ZONE = "UTC";
    public static final String COLON = ":";
    public static final String DEFAULT = "DEFAULT";

    public static final String HANDLING_FEE = "handlingFee";
    public static final String HANDLING_FEE_TAX = "handlingFeeTax";
    public static final String ENTITY_TYPE_LOCATION = "LOCATION";
    public static final String DEFAULT_ORIGIN_ADDRESS = "originAddress";


    public static final String WEBHOOK_UUID = "WEBHOOK_UUID";
    public static final String ORDER_REF = "orderRef";
    public static final String UPDATE_ON = "updateOn";
    public static final String ORDER_LINE_NO = "orderLineNo";
    public static final String LINE_STATUS = "lineStatus";
    public static final String CANCELLATION_REASON_TEXT = "cancellationReasonText";
    public static final String CANCELLATION_REASON_CODE = "cancellationReasonCode";
    public static final String STATUS = "status";
    public static final String REF = "ref";
    public static final String ID = "id";
    public static final String AUTO_SHIP_KEY = "autoshipKey";


    public static final String DEFAULT_RETURN_DESTINATION_LOCATION = "DEFAULT_RETURN_DESTINATION_LOCATION";
    public static final String DEFAULT_TAX_TYPE = "DEFAULT_TAX_TYPE";
    public static final String COUNTRY = "country";
    public static final String GROUP = "group";
    public static final String TARIFF = "tariff";
    public static final String TAX_TYPE = "taxType";
    public static final String AMOUNT = "amount";
    public static final String SUB_TOTAL_AMOUNT = "subTotalAmount";
    public static final String TOTAL_TAX = "totalTax";
    public static final String TOTAL_AMOUNT = "totalAmount";
    public static final String RETURN_VERIFICATIONS = "returnVerifications";
    public static final String RETURN_AUTHORISATION_DISPOSITION = "returnAuthorisationDisposition";
    public static final String USER_ID = "userId";
    public static final String RETURN_ORDER_CREATED_USER_ID = "returnOrderCreatedUserId";


    public static final String ATTRIBUTE_TYPE_FLOAT = "FLOAT";


    public static final String PROP_SCHEDULED_EVENT_NAME = "scheduledEventName";
    public static final String PROP_SETTING_NAME = "settingName";

    public static final String PROP_RX_SERVICE_RESPONSE_ORDER_LINE_STATUS = "rxServRespOrderLineStatus";
    public static final String PROP_FROM_LINE_ITEM_STATUS = "fromLineItemStatus";
    public static final String PROP_TO_LINE_ITEM_STATUS = "toLineItemStatus";
    public static final String USER_KEY = "userKey";
    public static final String CREATED_DATE = "createdDate";
    public static final String NOTE_TEXT = "noteText";
    public static final String CONTEXT_TYPE = "contextType";
    public static final String NOTE = "note";
    public static final String NOTE_CONTEXT = "noteContext";
    public static final String NOTE_KEY = "noteKey";
    public static final String PROP_EVENT_ATTRIBUTE_LIST = "eventAttributesList";
    public static final String CANCELLATION_REASON = "cancellationReason";
    public static final String PROP_APPROVAL_TYPE_LIST = "approvalTypeList";
    public static final String PROP_EXCLUDE_STATUS_LIST = "excludeStatusList";
    public static final String PROP_MATCHING_STATUS_LIST = "matchingStatusList";
    public static final String IGNORE_RX_ITEMS_ON_STATUS_LIST = "ignoreRxItemsOnStatusList";
    public static final String PROP_ITEM_TYPE_LIST = "itemTypeList";
    public static final String PROP_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS = "pharmacyAllocationForOrderItems";
    public static final String PROP_EVENT_NAME_TO_FIND_FACILITY = "eventNameToFindNewFacilityForItems";
    public static final String PROP_EVENT_NAME_FOR_FINDING_FACILITY_FOR_ALL_ITEMS = "eventNameAfterFindingFacilityForAllItems";
    public static final String PROP_SETTING_NAME_FOR_NETWORK_DETERMINATION = "settingNameForNetworkDetermination";
    public static final String PROP_NETWORK_STATUS = "networkStatus";

    public static final String PROP_LOCATION_STATUS = "locationStatus";
    public static final String PROP_VIRTUAL_CATALOGUE_REF = "virtualCatalogueRef";
    public static final String PROP_RETAILER_ID = "retailerId";
    public static final String PROP_INVENTORY_CATALOGUE_REF = "inventoryCatalogueRef";
    public static final String PROP_OPERATION_NAME = "operation";
    public static final String PROP_INVENTORY_QUANTITY_TYPES = "inventoryQuantityTypes";
    public static final String PROP_STATUS_LIST = "statusList";
    public static final String PROP_EVENT_NAME_IF_NO_LOCATIONS_ARE_FOUND = "eventNameIfNoLocationsAreFound";
    public static final String PROP_IGNORE_ORDER_ITEM_STATUS_LIST = "ignoreOrderItemStatusList";
    public static final String PROP_PV1_RESPONSE_CANCEL_STATUS = "pv1ResponseCancelStatus";
    public static final String OTC_ITEM = "otcItem";
    public static final String HILL_ITEM = "hillItem";
    public static final String DROP_SHIP_ITEM = "dropShipItem";
    public static final String PROP_DEFAULT_HILL_LOCATION = "defaultHillLocation";
    public static final String PROP_DEFAULT_DROP_SHIP_LOCATION = "defaultDropShipLocation";
    public static final String PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_EXCLUDED = "eventNameIfAllLocationsAreExcluded";
    public static final String PROP_EVENT_NAME_IF_ALL_LOCATIONS_ARE_NOT_EXCLUDED = "eventNameIfAllLocationsAreNotExcluded";
    public static final String PROP_LOCATION_TYPE_WAREHOUSE = "WAREHOUSE";
    public static final String PROP_ASSIGNED_STATUS = "assignedStatus";
    public static final String PROP_EXCEPTION_STATUS = "exceptionStatus";
    public static final String ATTRIBUTE_NAME = "attributeName";
    public static final String ATTRIBUTE_VALUE = "attributeValue";
    public static final String IGNORE_ORDER_ITEM_STATUS_LIST = "ignoreOrderItemStatusList";
    public static final String PROP_APPROVAL_STATUS_RESPONSE = "approvalStatusResponse";
    public static final String PROP_CANCEL_STATUS = "cancelStatus";
    public static final String PROP_EXCEPTION_STATUS_RESPONSE = "exceptionStatusResponse";
    public static final String PROP_EXCEPTION_RESOLUTION_STATUS_RESPONSE = "exceptionResolutionStatusResponse";
    public static final String PROP_CANCEL_STATUS_RESPONSE = "cancelStatusResponse";
    public static final String PROP_CANCELLED_ORDER_ITEM_STATUS = "cancelledOrderItemStatus";
    public static final String PROP_PV1_VERIFIED_ORDER_ITEM_STATUS = "pv1VerifiedOrderItemStatus";
    public static final String PROP_ACCEPTED_STATUSES = "acceptedStatuses";
    public static final String RESERVE = "RESERVE";
    public static final String UNRESERVE = "UNRESERVE";
    public static final String ORDER_LINE_STATUS_LIST = "orderLineStatusList";
    public static final String PROP_EVENT_NAME_FOR_RESOLUTION_WHILE_PICK_PACK = "eventNameForResolutionWhilePickPack";
    public static final String PROP_EVENT_NAME_FOR_RESOLUTION_WHILE_FULFILMENT_CREATION = "eventNameForResolutionWhileFulfilmentCreation";
    public static final String PROP_EVENT_NAME_FOR_REJECTED_FULFILMENT = "eventNameForRejectedFulfilment";
    public static final String PROP_FILLED_STATUS = "filledStatus";
    public static final String PROP_REJECTED_STATUS = "rejectedStatus";
    public static final String PROP_EVENT_NAME_FOR_REJECTED_FULFILMENT_AFTER_PICK_READY = "eventNameForRejectedFulfilmentAfterPickReady";
    public static final String AUTHORIZED_FULFILMENT_STATUS = "authorizedFulfilmentStatus";
    public static final String FAILED_FULFILMENT_STATUS = "failedFulfilmentStatus";
    public static final String CURRENCY = "currency";
    public static final String TYPE = "type";
    public static final String RESET_RESERVE = "RESET_RESERVE";
    public static final String PAYMENT_STATUS = "paymentStatus";
    public static final String IS_PAYMENT_SETTLEMENT_COMPLETED = "isPaymentSettlementCompleted";
    public static final String EXCLUDE_ORDER_STATUS_LIST = "excludeOrderStatusList";

    public static final String SCHEDULER_WINDOW = "schedulerWindow";
    public static final String CANCELLED_ORDER_NOTES = "cancelledOrderNotes";
    public static final String CANCELLED_ORDER_NOTE_MESSAGE = "cancelledOrderNoteMessage";
    public static final String CANCELLED_ORDER_NOTE = "cancelledOrderNote";
    public static final String ORDER_STATUS_ACTIVITY = "orderStatusActivity";
    public static final String ORDER_ACTIVITY_MESSAGE = "Order status moved from ";
    public static final String TO = " to ";
    public static final String DOT = " .";
    public static final String CANCELLED = "CANCELLED";
    public static final String DECLINED = "DECLINED";

    // order tax re-calculation attributes
    public static final String COSMETIC_SUB_TOTAL_ORDER_LEVEL = "cosmeticOrderSubtotal";
    public static final String SUB_TOTAL_ORDER_LEVEL = "subTotal";
    public static final String PRODUCT_DISCOUNTS_ORDER_LEVEL = "productDiscounts";
    public static final String ORDER_DISCOUNTS_ORDER_LEVEL = "orderDiscounts";
    public static final String TOTAL_DISCOUNTS_AT_ORDER_LEVEL = "totalDiscounts";
    public static final String DELIVER_COST_ORDER_LEVEL = "deliveryCost";
    public static final String DELIVER_COST_TAX_ORDER_LEVEL = "deliveryCostTax";
    public static final String TOTAL_LINE_ITEM_TAX_ORDER_LEVEL = "totalLineItemTax";
    public static final String ORDER_KEY = "orderKey";
    public static final String UPDATED_ON = "updatedOn";
    public static final String PAYMENT_INFO = "paymentInfo";
    public static final String PLATFORM_ACCOUNT_KEY = "platformAccountKey";
    public static final String TAX_STATUS = "taxStatus";
    public static final String ANIMAL_CARE_BUSINESS = "animalCareBusiness";


    public static final String ATTRIBUTE_TYPE_OBJECT = "OBJECT";
    public static final String CANCEL_REASON = "cancelReason";
    public static final String CANCELLED_BY = "cancelledBy";
    public static final String ORDER_HD_SUBTYPE = "HD";
    public static final String ORDER_CANCELLED_REASON_PAYLOAD = "orderCancelledReasonPayload";
    public static final String ORDER_ITEM_CANCELLED_REASON_PAYLOAD = "orderItemCancellationReasonPayload";
    public static final String ORDER_STATUS_PAYLOAD = "orderStatusPayload";
    public static final String ORDER_ITEM_STATUS_PAYLOAD = "orderItemStatusPayload";
    public static final String ORDER_ITEM_MAP_BEFORE_UPDATE = "orderItemMapBeforeUpdate";
    public static final String ORDER_ITEM_MAP_AFTER_UPDATE = "orderItemMapAfterUpdate";
    public static final String OLD_LINE_STATUS = "oldLineStatus";
    public static final String ATTRIBUTE_TYPE_JSON = "JSON";
    public static final String ORDER_ITEM_STATUS_CANCEL_PENDING = "CANCEL_PENDING";
    public static final String ATTRIBUTE_TYPE_INTEGER = "INTEGER";
    public static final String FULFILLMENT_REQUEST_PAYLOAD = "fulfillmentRequestPayload";
    public static final String EXCEPTION_RESOLUTION_TYPE = "exceptionResolutionType";
    public static final String EXCEPTION_RESOLUTION_CODE = "exceptionResolutionCode";
    public static final String SUCCESS_PAYMENT_STATUS = "successPaymentStatus";
    public static final String FAILURE_PAYMENT_STATUS = "failurePaymentStatus";
    public static final String ORDER_NOTE_FOR_COMMENT = "orderNoteForComment";
    public static final String NOTE_CONTEXT_FOR_COMMENT = "noteContextForComment";
    //exception occurred at
    public static final String PICK_PACK = "PICK_PACK";
    public static final String FULFILMENT_CREATION = "FULFILMENT_CREATION";
    public static final String NONE = "NONE";
    public static final String FAILED_TRANSACTION_STATUS = "failedTransactionStatus";
    public static final String AUTHORIZED_TRANSACTION_STATUS = "authorizedTransactionStatus";


    public static final String FRAUD_CHECK_APPROVED_RESPONSE_EVENT = "fraudCheckApprovedEvent";
    public static final String FRAUD_CHECK_APPROVED_DECLINED_EVENT = "fraudCheckDeclinedEvent";
    public static final String FRAUD_CHECK_APPROVED_IN_REVIEW_EVENT = "fraudCheckInReviewEvent";
    public static final String FRAUD_CHECK_RESPONSE_APPROVED = "A";
    public static final String FRAUD_CHECK_RESPONSE_DECLINED = "D";
    public static final String FRAUD_CHECK_RESPONSE_IN_REVIEW = "R";
    public static final String ORDER_ATTRIBUTE_SUB_TYPE = "subtype";


    public static final String DECLINED_REASON_CODE_SETTING_NAME = "declinedReasonCodeSettingName";


    public static final String EMPTY = "";
    public static final String UNDERSCORE = "_";
    public static final String PFDC = "PFDC";
    public static final String TRUE = "true";
    public static final String ATTRIBUTES = "attributes";
    public static final String FALSE = "false";
    public static final String DATE_FORMAT_TYPE = "E MMM dd HH:mm:ss z yyyy";
    public static final String DATE_FORMAT_TYPE_1 = "yyyy-MM-yy'T'HH:mm:ss.sss'Z'";


    public static final String EVENT_CANCELLATION_REASON = "cancelReason";
    public static final String EVENT_CANCELLED_BY = "cancelledBy";
    public static final String EVENT_PHARMACY_ALLOCATION_FOR_ORDER_ITEMS = "pharmacyAllocationForOrderItems";
    public static final String EVENT_OTC_ORDER_ITEMS_ALLOCATION = "otcOrderItemsAllocation";
    public static final String EVENT_HILL_ORDER_ITEMS_ALLOCATION = "hillOrderItemsAllocation";
    public static final String PRODUCT_REF_LIST_OTC_ITEMS = "productRefListOtcItems";
    public static final String PRODUCT_REF_LIST_HILL_ITEMS = "productRefListOtcItems";
    public static final String EVENT_DROP_SHIP_ORDER_ITEMS_ALLOCATION = "dropShipOrderItemsAllocation";
    public static final String EVENT_ALL_VP_INFORMATION = "allVpInformation";
    public static final String EVENT_FIELD_LOCATIONS = "locations";
    public static final String EVENT_FIELDS_LOCATIONS_DTO_LIST = "locationsDtoList";
    public static final String EVENT_FIELD_VIRTUAL_POSITIONS = "virtualPositions";
    public static final String EVENT_CAPACITY_FOR_LOCATION = "capacityForLocation";
    public static final String ORDER_NOTES = "orderNotes";
    public static final String EVENT_LOCATION_DATA_LIST = "locationDataList";
    public static final String WAITING_FULFILMENT = "WAITING_FULFILMENT";
    public static final String EXCEPTION = "EXCEPTION";
    public static final String FULFILLED_ITEM_REF_LIST = "fulfilledItemRefList";
    public static final String EVENT_DISPENSE_REQUEST_ID = "dispenseRequestId";
    public static final String EVENT_EXCEPTION_REASON = "exceptionReason";
    public static final String EVENT_NOTES = "notes";
    public static final String PAYMENT_AUTH_STATUS = "paymentAuthStatus";
    public static final String CONFIRMED_ITEMS = "confirmedItem";
    public static final String FULFILMENT = "fulfilment";
    public static final String PAYMENT_AUTH_REVERSAL_REQUEST = "paymentAuthReversalRequest";
    public static final String PAYMENT_AUTH_REVERSAL_RESPONSE = "paymentAuthReversalResponse";
    public static final String PAYMENT_AUTH_RESPONSE = "paymentAuthResponse";
    public static final String PAYMENT_SETTLEMENT_RESPONSE = "paymentSettlementResponse";
    public static final String CAPTURED_PAYMENT_DATA = "capturedPaymentData";
    public static final String PAYMENT_AUTH_REQUEST_FOR_ORDER = "paymentAuthRequestForOrder";
    public static final String PAYMENT_AUTH_REVERSAL_REQUEST_FOR_ORDER = "paymentAuthReversalRequestForOrder";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String EVENT_FIELD_LOCATION_REF = "locationRef";


    public static final String LINE_ITEM_CANCELLED = "CANCELLED";
    public static final String APPROVAL_TYPE = "approvalType";
    public static final String FULFILMENT_REQUESTED = "FULFILMENT_REQUESTED";
    public static final String DELETED_STATUS = "DELETED";


    public static final String SELECTED_NETWORK = "selectedNetwork";
    public static final String YES_VALUE = "Y";
    public static final String NO_VALUE = "N";
    public static final String KIT_PRODUCT = "kitProduct";
    public static final String IS_KIT = "isKit";
    public static final String SELECTED_LOCATION = "selectedLocation";
    public static final String DEFAULT_SELECTED_LOCATION = "selectedDefaultLocation";
    public static final String FULFILMENT_VENDOR_SHORT_NAME = "fulfilmentVendorShortName";
    public static final String RX = "Rx";
    public static final String PRESCRIPTION_KEY = "prescriptionKey";

    // order item tax re-calculation attributes
    public static final String LINE_ITEM_TOTAL_PAID_PRICE = "totalPaidPrice";
    public static final String LINE_ITEM_TOTAL_PRORATED_ORDER_DISCOUNT = "totalProratedOrderDiscount";
    public static final String LINE_ITEM_DELIVERY_COST = "proratedDeliveryCost";
    public static final String LINE_ITEM_DELIVERY_COST_TAX = "proratedDeliveryCostTax";

    public static final String IS_PV1_VERIFIED_AND_REQUESTED = "isPV1RequestedAndVerified";
    public static final String PV1_VERIFIED = "pv1Verified";
    public static final String TRANSACTION_FEE_PERCENTAGE = "transactionFeePercentage";
    public static final String PRICE_LIST_CODE = "priceListCode";
    public static final String PRACTICE_COST = "practiceCost";
    public static final String DEFAULT_PRACTICE_COST = "defaultPracticeCost";
    public static final String ITEM_NON_RETAIL_TAX = "itemNonRetailTax";
    public static final String AUTO_SHIP_ID = "autoshipId";
    public static final String TRANSACTION_FEE = "transactionFee";


    public static final String IS_COMPOUND = "isCompound";
    public static final String IS_COMMERCIAL = "isCommercial";


    public static final String RESERVED = "RESERVED";
    public static final String SALE = "SALE";
    public static final String CORRECTION = "CORRECTION";





    public static final String MAX_CAPACITY = "maxCapacity";
    public static final String ACTIVE_CAPACITY = "activeCapacity";
    public static final String LAST_ALLOCATED_DATE = "lastAllocatedDate";
    public static final String DEFAULT_MAXIMUM_CAPACITY_PER_DAY = "100";


    public static final String INITIAL_FULFILMENT_STATUS = "initialFulfillmentStatus";
    public static final String GRAND_TOTAL = "grandTotal";
    public static final String SHIPPING_INFO = "shippingInfo";
    public static final String FULFILLMENT_ID = "fulfillmentId";
    public static final String FULFILLMENT_REF = "fulfilmentRef";
    public static final String FULFILLMENT = "fulfilment";
    public static final String PAYMENT_AUTH_REQUEST = "paymentAuthRequest";
    public static final String PAYMENT_TRANSACTION = "paymentTransaction";
    public static final String SHIPPING_ORDER_KEY = "shippingOrderKey";
    public static final String FULFILMENT_LINE_STATUS = "fulfilmentLineStatus";
    public static final String SHIP_CONFIRMATION = "shipConfirmation";
    public static final String DELETED = "DELETED";
    public static final String SHIP_CONFIRMED_ITEM_REF_LIST = "shipConfirmedItemRefList";
    public static final String SHIP_CANCELLED_ITEM_REF_LIST = "shipCancelledItemRefList";
    public static final String CARRIER_CODE = "carrierCode";
    public static final String TRACKING_NUMBER = "trackingNumber";
    public static final String SHIPPING_METHOD = "shippingMethod";
    public static final String SHIPPED = "SHIPPED";
    public static final String PAYMENT_SETTLEMENT_REQUEST = "paymentSettlementRequest";
    public static final String PAYMENT_SETTLED_STATUS_SUCCESS = "SETTLED";
    public static final String PAYMENT_SETTLED_STATUS_FAILED = "SETTLEMENT_FAILED";
    public static final String SETTLEMENT = "SETTLEMENT";
    public static final String IS_EXCEPTION = "IsException";
    public static final String EXCEPTION_REASON = "exceptionReason";
    public static final String COSMETIC_ORDER_SUB_TOTAL_AT_FULFILMENT_LEVEL = "cosmeticOrderSubtotal";
    public static final String PRODUCT_DISCOUNTS_AT_FULFILMENT_LEVEL = "productDiscounts";
    public static final String ORDER_DISCOUNTS_AT_FULFILMENT_LEVEL = "orderDiscounts";
    public static final String DELIVERY_COST_AT_FULFILMENT_LEVEL = "deliveryCost";
    public static final String DELIVERY_COST_TAX_AT_FULFILMENT_LEVEL = "deliveryCostTax";
    public static final String TOTAL_TAX_AT_FULFILMENT_LEVEL = "totalTax";
    public static final String TOTAL_PRICE_AT_FULFILMENT_LEVEL = "totalPrice";
    public static final String TOTAL_DISCOUNTS_AT_FULFILMENT_LEVEL = "totalDiscounts";
    public static final String SUB_TOTAL_AT_FULFILMENT_LEVEL = "subTotal";
    public static final String TRACKING_URL = "trackingURL";
    public static final String SHIPPED_DATE = "shippedDate";
    public static final String FULFILLED = "FULFILLED";


    public static final String PAYMENT_TOKEN = "paymentToken";
    public static final String PAYMENT_TYPE = "paymentType";
    public static final String PAYMENT_AUTH_STATUS_AUTHORIZED = "AUTHORIZED";
    public static final String PAYMENT_AUTH_STATUS_NOT_ALLOWED = "NOT_ALLOWED";
    public static final String PAYMENT_AUTH_STATUS_AUTH_FAILED = "AUTH_FAILED";
    public static final String PAYMENT_AUTH_REVISED = "PAYMENT_AUTH_REVISED";
    public static final String PAYMENT_AUTH_REVISED_FAILED = "PAYMENT_AUTH_REVISED_FAILED";
    public static final String AUTH_REVERSAL = "AUTH_REVERSAL";
    public static final String AUTH = "AUTH";
    public static final String TRANSACTION_KEY = "transactionKey";
    public static final String TRANSACTION_TYPE = "transactionType";
    public static final String TOTAL_PRICE = "totalPrice";
    public static final String CREATED_ON = "createdOn";
    public static final String AUTH_NUMBER = "authNumber";
    public static final String PROCESSOR_ID = "processorId";


    public static final String RETURNABLE_QTY_ATTR = "returnableQty";
    public static final String RESPONSE_STATUS = "responseStatus";
    public static final String AMOUNT_TO_BE_REFUND = "amountToBeRefund";
    public static final String TOTAL_REFUND_AMOUNT_REQUESTED = "totalRefundAmountRequested";
    public static final String AVAILABLE_AMOUNT_TO_REFUND = "availableAmountToRefund";
    public static final String SUCCESSFUL = "SUCCESSFUL";
    public static final String CREDITED = "CREDITED";
    public static final String FAILED = "FAILED";
    public static final String REFUND_CREDIT_REQUEST_PAYLOAD = "refundCreditRequestPayload";
    public static final String RETURN = "Return";
    public static final String CREDIT_MEMO = "CREDIT_MEMO";
    public static final String RETURN_ORDER_DETAILS = "returnOrderDetails";
    public static final String INITIATED = "INITIATED";
    public static final String RETURN_REASON = "returnReason";
    public static final String RETURN_ITEMS = "returnItems";
    public static final String ORDER_ITEM_REF = "orderItemRef";
    public static final String UNIT_QUANTITY = "unitQuantity";
    public static final String QUANTITY = "quantity";
    public static final String REFUND_KEY = "refundKey";
    public static final String PROCESSED_REFUND_DATA = "processedRefundData";
    public static final String CARD_NUMBER_LAST_FOUR = "cardNumberLastFour";
    public static final String REFUND_REASON = "refundReason";
    public static final String APPEASEMENT_AMOUNT = "appeasementAmount";
    public static final String REQUESTED_REFUND_AMOUNT = "requestedRefundAmount";
    public static final String CARD_NUMBER_FIRST_SIX = "cardNumberFirstSix";
    public static final String QUANTITY_CAPS = "QUANTITY";
    public static final String INVENTORY_POSITION_ATTRIBUTE = "inventoryPosition";
    public static final String INVENTORY_POSITION_REF = "ref";
    public static final String PROP_TYPE = "type";
    public static final String PRODUCT_REF = "productRef";
    public static final String LOCATION_REF = "locationRef";
    public static final String INVENTORY_QUANTITIES = "inventoryQuantities";
    public static final String SKU_REF = "skuRef";
    public static final String ORDER_ITEM_ID = "orderItemId";
    public static final String RX_CANCELLATION_REASON_PREFIX = "rxCancellationReasonPrefix";
    public static final Integer DEFAULT_PAGINATION_PAGE_SIZE_FOR_FULFILMENT = 30;
    public static final String CREDIT_MEMO_TYPE = "creditMemoType";
    public static final String CREDIT_MEMO_ITEM_TYPE = "creditMemoItemType";
    public static final String EVENT_ATTR_ENTITY_REFERENCE = "entityReference";
    public static final String EVENT_ATTR_APPEASEMENT_INFO = "appeasementInfo";
    public static final String PROP_BILLING_ACCOUNT_EXISTS_EVENT_NAME = "BillingAccountExistsEventName";
    public static final String PROP_NO_BILLING_ACCOUNT_EXISTS_EVENT_NAME = "NoBillingAccountExistsEventName";
    public static final String ENTITY_TYPE_BILLING_ACCOUNT = "BILLING_ACCOUNT";
    public static final String APPEASEMENT_REASON = "appeasementReason";
    public static final String APPEASEMENT_COMMENT = "comment";
    public static final String CREDIT_MEMO_ITEM = "CREDIT_MEMO_ITEM";
    public static final String BILLING_ACCOUNT = "BILLING_ACCOUNT";
    public static final String RULE_STATEMENT = "] - rule: ";
    public static final String CARD_TYPE = "cardType";
    public static final String PAYMENT_TYPE_LIST = "paymentTypeList";
    public static final String EXCEPTION_MESSAGE = "exceptionMessage";

}
