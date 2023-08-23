package com.fluentcommerce.model.order;

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
public class OrderNote {
    private String userKey;
    private String createdDate;
    private String noteText;
    private String contextType;
}
