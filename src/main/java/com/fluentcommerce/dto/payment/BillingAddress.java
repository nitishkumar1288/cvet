package com.fluentcommerce.dto.payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 @author nitishKumar
 */

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
public class BillingAddress {
        String town;
        String region;
        String country;
        String lastName;
        String firstName;
        String postalCode;
        String phone1;
        String addressLine1;
}
