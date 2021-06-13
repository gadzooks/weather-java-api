package com.github.gadzooks.weather.commands;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class LocationCommand {
    private Long id;
    private String name;
    private String description;
    private Float latitude;
    private Float longitude;
    private String subRegion;
}
