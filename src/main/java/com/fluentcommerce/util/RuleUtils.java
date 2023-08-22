package com.fluentcommerce.util;

import com.fluentretail.rubix.exceptions.PropertyNotFoundException;
import com.fluentretail.rubix.v2.context.Context;
import static com.fluentcommerce.util.Constants.*;

/**
 * Utils class for rules which provides helper methods.
 */
public class RuleUtils {

    private RuleUtils() {}

    /**
     * Utils method to validate that a rule is configured correctly.
     * @param context the current context
     * @param ruleParams the rule parameters
     * @return
     */
    public static boolean validateRulePropsIsNotEmpty(Context context, String... ruleParams) {
        if (ruleParams != null) {
            for (String ruleParam : ruleParams) {
                if (null == context.getProp(ruleParam) || context.getProp(ruleParam).isEmpty()) {
                    throw new PropertyNotFoundException(ERROR_CODE_400, ruleParam);
                }
            }
        }
        return true;
    }
}
