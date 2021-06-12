package com.github.gadzooks.weather.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationCommand {
    private Long id;
    private String name;
    private String description;
    private Float latitude;
    private Float longitude;
    private String subRegion;
}
