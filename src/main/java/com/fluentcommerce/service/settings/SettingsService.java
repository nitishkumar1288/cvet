package com.fluentcommerce.service.settings;

import com.fluentcommerce.graphql.query.settings.GetSettingsByNameQuery;
import com.fluentretail.rubix.v2.context.Context;
import com.google.common.collect.ImmutableList;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static com.fluentcommerce.util.Constants.DEFAULT_PAGE_SIZE;

/**
 * @author nandhakumar
 */
public class SettingsService {

    private Context context;

    public SettingsService(Context context) {
        this.context = context;
    }

    public List<GetSettingsByNameQuery.Edge> getSettingsByName(Context context, List<String> settingsNameList) {
        List<GetSettingsByNameQuery.Edge> edges = getSettingData(context, "RETAILER", context.getEvent().getRetailerId(),
                settingsNameList).settings().edges();

        if (CollectionUtils.isNotEmpty(edges)) {
            return edges;
        }
        return Collections.emptyList();
    }

    public GetSettingsByNameQuery.Data getSettingData(Context context, String settingContext, String contextId,
                                                             List<String> settingsNameList) {
        final GetSettingsByNameQuery querySettings = GetSettingsByNameQuery.builder().name(settingsNameList)
                .context(ImmutableList.of(settingContext)).contextId(ImmutableList.of(Integer.parseInt(contextId)))
                .includeSettigns(true)
                .settingCount(DEFAULT_PAGE_SIZE)
                .build();

        return (GetSettingsByNameQuery.Data) context.api().query(querySettings);
    }

}
