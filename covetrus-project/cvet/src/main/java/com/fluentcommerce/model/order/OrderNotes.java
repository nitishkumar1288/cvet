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
public class OrderNotes {
    private String noteKey;
    private String noteContext;
    private String noteUserKey;
    private String createdDate;
    private String note;
}
