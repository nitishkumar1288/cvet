package com.fluentcommerce.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.common.LogCollection;
import com.fluentretail.api.model.attribute.Attribute;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import static com.fluentcommerce.util.Constants.*;

@Slf4j
public class CommonUtils {

    private CommonUtils() {}

    /**
     * Generates a log action with the current context.
     * @param context the context.
     */
    public static void generateLog(ContextWrapper context) {
        LogCollection logs = context.getLogCollection();
        if(logs.isEmpty()) return;

        String ruleName = context.getRuleName();

        Objects.requireNonNull(context);
        Objects.requireNonNull(logs);

        String utcDate = getTimeAsText();
        Attribute dateAttribute = Attribute.builder()
            .name("LogDate")
            .attributeType(Attribute.Type.STRING)
            .value(utcDate)
            .build();
        Attribute logCollectionAttribute = Attribute.builder()
            .name("LogCollection")
            .attributeType(Attribute.Type.OBJECT)
            .value(logs)
            .build();

        List<Attribute> attributes = new ArrayList<>(Arrays.asList(dateAttribute, logCollectionAttribute));
        if (context.getEvent() != null) {
            Attribute eventAttribute = Attribute.builder()
                .name("EventAttribute")
                .attributeType(Attribute.Type.OBJECT)
                .value(context.getEvent().getAttributes())
                .build();
            attributes.add(eventAttribute);
        }
        context.action().log(ruleName, utcDate, attributes);
    }

    /**
     * Helper method which generates the current time.
     * @return the current time.
     */
    private static String getTimeAsText(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return dateFormat.format(now);
    }

    public static <T> T convertMapToDto(Map<String, Object> map, Class<T> cls) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(map, cls);
    }

    public static Map<String, Object> convertDtoToMap(Object obj) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(obj, Map.class);
    }

    public static <T> T convertObjectToDto(Object obj, Class<T> cls) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(obj, cls);
    }

    public static <T> T convertObjectToDto(Object obj, TypeReference<T> typeReference) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(obj, typeReference);
    }

    public static Map<String,Object> convertJsonToMap(Object jsonData){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = null;
        try {
            map = objectMapper.readValue(jsonData.toString(),HashMap.class);
        } catch (IOException e) {
            log.info("Exception " + e);
        }
        return map;
    }

    public static String getScheduledTime(Instant currentTime ,String waitingTime,String unit){
        Instant scheduledTime = null;
        if (unit.equalsIgnoreCase(IN_DAYS)){
            scheduledTime = currentTime.plus(Integer.parseInt(waitingTime), ChronoUnit.DAYS);
        }else if (unit.equalsIgnoreCase(IN_HOUR)){
            scheduledTime = currentTime.plus(Integer.parseInt(waitingTime), ChronoUnit.HOURS);
        }else if(unit.equalsIgnoreCase(IN_MINUTE)){
            scheduledTime = currentTime.plus(Integer.parseInt(waitingTime), ChronoUnit.MINUTES);
        }else if (unit.equalsIgnoreCase(IN_SECONDS)){
            scheduledTime = currentTime.plus(Integer.parseInt(waitingTime), ChronoUnit.SECONDS);
        }

        if(scheduledTime != null)
            return getTimeInUtcStringFormat(scheduledTime);
        else
            return null;
    }

    public static String getTimeInUtcStringFormat(Instant time){
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter
                        .ofPattern(DATE_TIME_FORMATTER_EXCLUDING_MILLISECONDS, Locale.US)
                        .withZone(ZoneId.of(UTC_TIME_ZONE));

        return dateTimeFormatter.format(time) + "." + String.format("%03d", time.getNano() / 1_000_000) + "Z";
    }

    public static Date formatDate(String date ,String formatterPattern){
        SimpleDateFormat formatter = new SimpleDateFormat(formatterPattern);
        formatter.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));
        Date formattedDate = null;
        try {
            formattedDate = formatter.parse(date);
        } catch (ParseException e) {
            log.info("Exception " + e);
        }
        return formattedDate;
    }
    public static String getFormatedDate(String date ,String inputFormat ,String outputFormat){
        String strDateTime = null;
        if (null != date){
            DateFormat iFormatter = new SimpleDateFormat(inputFormat);
            DateFormat oFormatter = new SimpleDateFormat(outputFormat);
            try {
                strDateTime = oFormatter.format(iFormatter.parse(date));
            } catch (ParseException e) {
               log.info("parse Exception" + e);
            }
        }
        return strDateTime;
    }

}
