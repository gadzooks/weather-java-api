package com.github.gadzooks.weather.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@ApiModel
//Make class final to make it immutable
public final class Region {
    @ApiModelProperty(
            required = true,
            value = "Regions are identified by region id. These should be unique",
            example = "issaquah")
    private String id;
    @ApiModelProperty(
            required = true,
            value = "searchKey used for wta.org website",
            example = "592fcc9afd9208db3b81fdf93dada567")
    private String searchKey;
    @ApiModelProperty(
            required = false,
            value = "descriptive name. Defaults to id",
            example = "issaquah")
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
