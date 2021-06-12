package com.github.gadzooks.weather.commands;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AreaCommand {
    private Long id;
    private String name;
    private String description;
    private Set<RegionCommand> regionCommandSet;
}
