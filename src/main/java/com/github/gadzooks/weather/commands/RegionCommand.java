package com.github.gadzooks.weather.commands;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RegionCommand {
    private Long id;
    private Boolean isActive;
    private String name;
    private String searchKey;
    private String description;

    private AreaCommand areaCommand;
    private Set<LocationCommand> locationCommandSet;
}
