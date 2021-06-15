package com.github.gadzooks.weather.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
//NOTE cannot use @Value with lombok currently due to mapstruct limitations
//https://stackoverflow.com/questions/62709647/value-lombok-and-mapstruct-to-another-value-object
public class RegionDto implements Serializable {

    @Builder
    public RegionDto(Long id, String name, String searchKey, String description, Boolean isActive, Set<LocationSlimDto> locations) {
        this.id = id;
        this.name = name;
        this.searchKey = searchKey;
        this.description = description;
        this.isActive = isActive;
        this.locations = locations;
    }

    @JsonProperty("id")
    Long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("searchKey")
    String searchKey;

    @JsonProperty("description")
    String description;

    @JsonProperty("isActive")
    Boolean isActive;

    @JsonProperty("locations")
    Set<LocationSlimDto> locations;
}
