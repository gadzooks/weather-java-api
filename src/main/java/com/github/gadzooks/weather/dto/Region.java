package com.github.gadzooks.weather.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
//Make class final to make it immutable
public final class Region {
    private String name;
    private String searchKey;
    private String description;
    private final List<Location> locations = new ArrayList<>();

    public void update(Region src) {
        if (!src.getDescription().isBlank()) {
            this.description = src.getDescription();
        }
        if (!src.getSearchKey().isBlank()) {
            this.searchKey = src.getSearchKey();
        }
    }
}
