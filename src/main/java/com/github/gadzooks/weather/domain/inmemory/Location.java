package com.github.gadzooks.weather.domain.inmemory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString(exclude = "region")
//NOTE : we dont want an infinite loop of region including location and location in turn including region
// while serializing
@JsonIgnoreProperties({"region"})
@Schema(description = "Description of Location entity")
public class Location extends BaseEntity {
    @Schema(
        requiredMode = Schema.RequiredMode.REQUIRED,
        title = "unique name of the location",
        example = "north bend")
    private String name;
    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Should match existing regionId. Either regionId or regionName are required",
            example = "1")
    @Setter
    private Long regionId;
    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Should match existing regionId. Either regionId or regionName are required",
            example = "snowqualmie_region")
    private String regionName;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "descriptive name of location. Defaults to location name",
            example = "north bend")
    private String description;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "latitude of location",
            example = "47.497428")
    private float latitude;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "longitude of location",
            example = "-121.786648")
    private float longitude;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "subRegion as specified by wta.org",
            example = "db086e5e85941a02ae188f726f7e9e2c")
    private String subRegion;
    @Schema(hidden = true)
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
