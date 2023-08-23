package com.fluentcommerce.util.returnorderhelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exposed as a separate class to mock this specific static method as dependencies cannot be injected into rules.
 */
public class CreateReturnOrderFromOrderRefGenerator {

    private  CreateReturnOrderFromOrderRefGenerator(){
    }

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("Hmmss");

    public static String generateUniqueOrderRefSuffix() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }
}
