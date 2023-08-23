package com.fluentcommerce.common;

import org.junit.Test;

import static com.fluentretail.rubix.test.TestUtils.scanAndValidateAllRules;
import static org.junit.Assert.assertEquals;

public class RuleValidationTest {

    /**
     * This test scans the entire plugin and validates that each Rule
     * implemented has a valid @RuleInfo annotation (e.g. all defined
     * parameters are used in the description and no parameters
     * definitions are missing).
     *
     * It makes sense to have a test like this in every plugin project.
     */
    @Test
    public void validateRuleAnnotations() {
        scanAndValidateAllRules();

        String testActualValue = "";
        String testExpectedValue = "";
        assertEquals(testExpectedValue,testActualValue);
    }

}
