package com.github.gadzooks.weather.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
public class LocationSlimDto implements Serializable {
    @JsonProperty("id")
    private Long id;

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
