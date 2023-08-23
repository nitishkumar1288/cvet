package com.fluentcommerce.dto.location;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
public class LocationDto {
    String id;
    String ref;
    String type;
    Double longitude;
    Double latitude;
    Double distance;
}