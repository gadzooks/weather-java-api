package com.github.gadzooks.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString(exclude = "region")
//NOTE : we dont want an infinite loop of region including location and location in turn including region
// while serializing
@JsonIgnoreProperties(value = {"region"})
public class Location {
    private String name;
    private String regionId;
    private String description;
    private float latitude;
    private float longitude;
    private String subRegion;
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
