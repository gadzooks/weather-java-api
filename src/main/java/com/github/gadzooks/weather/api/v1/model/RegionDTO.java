package com.github.gadzooks.weather.api.v1.model;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class RegionDTO {

    String id;
    String createdBy;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    String lastModifiedBy;

    String name;
    String searchKey;
    String description;
    Boolean isActive;

    List<LocationDTO> locations;

}
