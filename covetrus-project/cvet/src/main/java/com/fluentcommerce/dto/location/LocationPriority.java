package com.fluentcommerce.dto.location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author nandhakumar
 */

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
public class LocationPriority {
    String locationRef;
    int sortPosition;
}
