package com.fluentcommerce.util;


import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentretail.rubix.v2.context.Context;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import static com.fluentcommerce.util.Constants.*;

/**
 @author nitishKumar
 */

@Slf4j
public class SettingUtils {

    private SettingUtils() {}

    /**
     * The method returns value for setting value.
     *
     * @param context
     * @param settingName
     * @return Setting's value
     */
    public static Optional<String> getSettingValue(Context context, String settingName) {
        try {
            final GetSettingsByNameQuery.Edge settingEdge = getSetting(context, settingName);
            if (settingEdge != null) {
                return Optional.ofNullable(settingEdge.node().value());
            }
        } catch (final Exception e) {
            log.error("Exception occured while executing SettingsUtils - {}", e);
        }
        return Optional.empty();
    }

    /**
     * The method returns value for setting value.
     *
     * @param context
     * @param settingName
     * @return Setting's json value
     */
    public static Object getSettingJSONValue(Context context, String settingName) {
        try {
            final GetSettingsByNameQuery.Edge settingEdge = getSetting(context, settingName);
            if (settingEdge != null) {
                return  settingEdge.node().lobValue();
            }
        } catch (final Exception e) {
            log.error("Exception occured while executing SettingsUtils - {}", e);
        }
        return null;
    }

    private static GetSettingsByNameQuery.Edge getSetting(Context context, String settingName) {
        List<GetSettingsByNameQuery.Edge> edges = getSettingData(context, "RETAILER", context.getEvent().getRetailerId(),
                settingName).settings().edges();

        if (CollectionUtils.isNotEmpty(edges)) {
            Optional<GetSettingsByNameQuery.Edge> optionalSettingValue = edges.stream().findFirst();
            if(optionalSettingValue.isPresent()){
                return optionalSettingValue.get();
            }
        }

        return null;
    }

    public static GetSettingsByNameQuery.Data getSettingData(Context context, String settingContext, String contextId,
                                                   String settingName) {
        final GetSettingsByNameQuery querySettings = GetSettingsByNameQuery.builder().name(ImmutableList.of(settingName))
                .context(ImmutableList.of(settingContext)).contextId(ImmutableList.of(Integer.parseInt(contextId)))
                .includeSettigns(true)
                .build();

        return (GetSettingsByNameQuery.Data) context.api().query(querySettings);
    }

    public static Integer getWaitingTimePeriod(String measurementUnit, Integer waitingTime) {
        Integer delayInSeconds = null;
        if (measurementUnit.equalsIgnoreCase(IN_DAYS)){
            delayInSeconds = (int) TimeUnit.DAYS.toSeconds(waitingTime);
        }else if(measurementUnit.equalsIgnoreCase(IN_HOUR)){
            delayInSeconds = (int)TimeUnit.HOURS.toSeconds(waitingTime);
        }else if(measurementUnit.equalsIgnoreCase(IN_MINUTE)){
            delayInSeconds = (int)TimeUnit.MINUTES.toSeconds(waitingTime);
        }else if (measurementUnit.equalsIgnoreCase(IN_SECONDS)){
            delayInSeconds = waitingTime;
        }
        return delayInSeconds;
    }
}
