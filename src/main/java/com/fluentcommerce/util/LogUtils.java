package com.fluentcommerce.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.LogEntry;
import com.fluentretail.rubix.event.Event;

import java.util.List;

/**
 * Utils class for log related functionalities.
 */
public class LogUtils {

    private LogUtils() {}

    /**
     * Generates a standard log prefix with the account, class/rule, entityType, entityId and entityRef information
     * @param className the classname or rulename
     * @param event the event from which the information is extracted
     * @return the log prefix string
     */
    public static String buildLogPrefix(String className, final Event event) {
        final String entityId = event.getEntityId();
        final String entityRef = event.getEntityRef();
        final String entityType = event.getEntityType();
        final String accountId = event.getAccountId();
        return "[" + accountId + "] [" + className + "][" + entityType + " id=" + entityId + ", ref="
            + entityRef + "]";
    }

    /**
     * Helper method which transforms a list log LogEntry records to a json.
     *
     * @param logEntries the list of log entry records
     * @return a serialized string representation of the list of log entries.
     * @throws JsonProcessingException in case the input could not be transformed.
     */
    public static String serialize(List<LogEntry> logEntries) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(logEntries);
    }
}
