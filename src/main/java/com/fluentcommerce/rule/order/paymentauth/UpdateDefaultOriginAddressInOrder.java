package com.fluentcommerce.rule.order.paymentauth;

import com.fluentcommerce.common.ContextWrapper;
import com.fluentcommerce.graphql.type.AttributeInput;
import com.fluentcommerce.rule.BaseRule;
import com.fluentcommerce.service.order.OrderService;
import com.fluentcommerce.util.SettingUtils;
import com.fluentretail.rubix.rule.meta.EventInfo;
import com.fluentretail.rubix.rule.meta.ParamString;
import com.fluentretail.rubix.rule.meta.RuleInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.fluentcommerce.util.Constants.*;

/**
 * @author Nandhakumar
 */

@RuleInfo(
        name = "UpdateDefaultOriginAddressInOrder",
        description = "Update default origin address from setting {" + PROP_SETTING_NAME + "} into order attributes",
        accepts = {
                @EventInfo(entityType = "ORDER")
        }
)
@ParamString(
        name = PROP_SETTING_NAME,
        description = "name of the setting"
)
@Slf4j
public class UpdateDefaultOriginAddressInOrder extends BaseRule {
    @Override
    public void run(ContextWrapper context) {
        String settingNameForDefaultOriginAddress = context.getProp(PROP_SETTING_NAME);

        // default Origin Address is fetched from Setting
        Object defaultOriginAddress = SettingUtils.getSettingJSONValue(
                context,
                settingNameForDefaultOriginAddress);

        context.addLog("defaultOriginAddress " + defaultOriginAddress.toString());

        List<AttributeInput> attributeInputList = new ArrayList<>();
        attributeInputList.add(
        AttributeInput
                .builder()
                .name(DEFAULT_ORIGIN_ADDRESS)
                .type(ATTRIBUTE_TYPE_JSON)
                .value(defaultOriginAddress)
                .build()
        );
        OrderService orderService = new OrderService(context);
        orderService.upsertOrderAttributeList(context.getEntity().getId(),attributeInputList);

    }
}
