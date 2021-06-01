package com.github.gadzooks.weather.domain.inmemory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString(exclude = "region")
//NOTE : we dont want an infinite loop of region including location and location in turn including region
// while serializing
@JsonIgnoreProperties(value = {"region"})
@ApiModel
public class Location extends BaseEntity {
    @ApiModelProperty(
            required = true,
            value = "unique name of the location",
            example = "north bend")
    private String name;
    @ApiModelProperty(
            required = true,
            value = "Should match existing regionId",
            example = "snowqualmie_region")
    private String regionId;
    @ApiModelProperty(
            required = true,
            value = "descriptive name of location. Defaults to location name",
            example = "north bend")
    private String description;
    @ApiModelProperty(
            required = true,
            value = "latitude of location",
            example = "47.497428")
    private float latitude;
    @ApiModelProperty(
            required = true,
            value = "longitude of location",
            example = "-121.786648")
    private float longitude;
    @ApiModelProperty(
            required = true,
            value = "subRegion as specified by wta.org",
            example = "db086e5e85941a02ae188f726f7e9e2c")
    private String subRegion;
    @ApiModelProperty(hidden = true)
    private Region region;

    public static Location copyOf(Location l1, Region region) {
        Location newLoc = new Location();
        newLoc.name = l1.name;
        newLoc.regionId = l1.regionId;
        newLoc.description = l1.description;
        newLoc.latitude = l1.latitude;
        newLoc.longitude = l1.longitude;
        newLoc.subRegion = l1.subRegion;
        newLoc.region = region;

        return newLoc;
    }
}
