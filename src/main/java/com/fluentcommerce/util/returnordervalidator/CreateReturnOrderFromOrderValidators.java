/**
 * Copyright (C) Fluent Retail Pty Ltd - 2019 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * ------------------
 * Setup instructions
 * ------------------
 *
 * 1. Unzip generated sources jar file in the lib folder eg: 'rubix-plugin-foundation-1.0.0-SNAPSHOT-sources.jar'
 * 2. Copy and paste desired rules from 'src/main/java/com' into the corresponding sdk java package (repeat for tests)
 * 3. Copy and paste the GQL queries/mutations related to the rule/s within 'src/main/graphql' into the corresponding sdk package
 * 4. Copy and paste any other dependencies including the graphql, helper or utils packages from 'src/main/java/com/fluentretail/rubix/foundation/' into the corresponding sdk package
 * 5. Copy and paste maven related dependencies from 'sdk-1.0.0-SNAPSHOT/fluent-base-deps.xml' into sdk pom.xml
 * 6. Do 'mvn clean package' on sdk
 */
package com.fluentcommerce.util.returnordervalidator;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

import static java.lang.String.format;

public class CreateReturnOrderFromOrderValidators {

    private CreateReturnOrderFromOrderValidators(){
    }

    public static <T> T tryCastNonNull(Object o, Class<T> clazz, String path) {
        if (o == null) {
            return null;
        }
        if (clazz.isInstance(o)) {
            return clazz.cast(o);
        }
        throw new IllegalArgumentException(format("Expect '%s' to be type '%s' but got '%s'", path, clazz.getSimpleName(), o.getClass().getSimpleName()));
    }

    public static boolean isEmptyOrBlank(String value) {
        return StringUtils.isEmpty(value) || StringUtils.isBlank(value);
    }

    /**
     * Try casting the associated key value to the {@code castTo} type, otherwise throw an {@code IllegalArgumentException}.
     * {@code null} is returned if no key exists.
     */
    @Nullable
    public static <T> T tryGetValueForKeyOrNull(Map<String, Object> payload, String key, String rootPath, Class<T> castTo) {
        String path = StringUtils.isEmpty(rootPath) ? key : (rootPath + "." + key);
        return tryCastNonNull(payload.get(key), castTo, path);
    }

    /**
     * Try casting the associated key value to the {@code castTo} type, otherwise throw an {@code IllegalArgumentException}
     * if the value is null or there is a cast error.
     */
    @Nonnull
    public static <T> T tryGetMandatoryValueForKeyOrThrow(Map<String, Object> payload, String key, String rootPath, Class<T> castTo) {
        String path = StringUtils.isEmpty(rootPath) ? key : (rootPath + "." + key);
        T value = tryCastNonNull(payload.get(key), castTo, path);
        if (value == null) {
            throw new IllegalArgumentException(format("Required attribute at path '%s' is missing", path));
        }
        return value;
    }

    /**
     * Try casting the associated key value to a String otherwise an {@code IllegalArgumentException} is thrown if no key exists
     * or the value is null.
     */
    public static String tryGetMandatoryStringOrThrow(Map<String, Object> payload, String key, String rootPath) {
        return tryGetMandatoryValueForKeyOrThrow(payload, key, rootPath, String.class);
    }

    /**
     * Try casting the associated key value to a String otherwise an {@code IllegalArgumentException} is thrown if no key exists
     * or the value is empty or blank.
     */
    public static String tryGetMandatoryValidatedStringOrThrow(Map<String, Object> payload, String key, String rootPath) {
        String path = StringUtils.isEmpty(rootPath) ? key : (rootPath + "." + key);
        String value = tryGetMandatoryValueForKeyOrThrow(payload, key, rootPath, String.class);

        if (isEmptyOrBlank(value)) {
            throw new IllegalArgumentException(format("Required attribute at path '%s' cannot contain empty or blank string", path));
        }
        return value;
    }

    /**
     * Try casting the associated key value to a Double otherwise an {@code IllegalArgumentException} is thrown if no key exists
     * or the value is negative.
     */
    public static Double tryGetMoneyValue(Map<String, Object> payload, String key, String rootPath) {
        String path = StringUtils.isEmpty(rootPath) ? key : (rootPath + "." + key);
        Double value = tryCastNonNull(payload.get(key), Double.class, path);

        if (value != null && value < 0) {
            throw new IllegalArgumentException(format("Money amount at path '%s' cannot be negative", path));
        }
        return value;
    }
}