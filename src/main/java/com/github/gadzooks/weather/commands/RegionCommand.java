package com.github.gadzooks.weather.commands;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(exclude = {"areaCommand", "locationCommandSet"} )
public class RegionCommand {
    private Long id;
    private Boolean isActive;
    private String name;
    private String searchKey;
    private String description;

    private AreaCommand areaCommand;
    private Set<LocationCommand> locationCommandSet = new HashSet<>();
}
