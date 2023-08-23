package com.fluentcommerce.dto.fulfillment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
public class TaxResponse {
    String ref;
    String taxStatus;
    String taxDate;
    String totalTax;
    String tax;
    String handlingTax;
    String shippingTax;
    String platformAccountKey;
    ArrayList<TaxResponseItem> items;
}
