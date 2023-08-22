package com.fluentcommerce.dto.fulfillment;
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
public class TaxResponseItem {
    String ref;
    String totalTax;
    String handlingTax;
    String shippingTax;
    String transactionFeePercentage;
    String priceListCode;
    String practiceCost;
    String defaultPracticeCost;
    String itemNonRetailTax;
    String transactionFee;
}
