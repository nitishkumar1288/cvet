package com.fluentcommerce.model.billingaccount;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Setter
@Slf4j
public class AppeasementAttributes {
    private String appeasementAmount;
    private String appeasementReason;
    private String comment;
    private String userId;

}
