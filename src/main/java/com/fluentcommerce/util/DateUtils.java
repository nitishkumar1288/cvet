package com.fluentcommerce.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import java.util.TimeZone;
import static com.fluentcommerce.util.Constants.*;

@Slf4j
public class DateUtils {
    private DateUtils() {}

    public static DateTime getNowWithTimeZone(String timeZone) {
        if (StringUtils.isEmpty(timeZone)) {
            return getTimeZoneDateTime(TimeZone.getTimeZone(UTC_TIME_ZONE));
        }
        return getTimeZoneDateTime(TimeZone.getTimeZone(timeZone));
    }

    public static DateTime getTimeZoneDateTime(TimeZone timeZone) {
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(timeZone);
        return new DateTime(dateTimeZone);
    }

    public static boolean isSameDay(DateTime date1, DateTime date2) {
        return date1.toLocalDate().equals(date2.toLocalDate());
    }
}

