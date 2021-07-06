package com.github.gadzooks.weather.api.v1.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDTO {
    //FIXME extend Location and RegionDTO from base DTO
    String id;
    String createdBy;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    String lastModifiedBy;

    String name;
    String description;
    float latitude;
    float longitude;
    String subRegion;
}
