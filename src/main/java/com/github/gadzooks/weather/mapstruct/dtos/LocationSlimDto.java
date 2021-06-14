package com.github.gadzooks.weather.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationSlimDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("subRegion")
    private String subRegion;

    @JsonProperty("description")
    private String description;

    @JsonProperty("latitude")
    private float latitude;

    @JsonProperty("longitude")
    private float longitude;
}
