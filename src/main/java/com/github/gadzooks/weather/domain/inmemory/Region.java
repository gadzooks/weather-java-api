package com.github.gadzooks.weather.domain.inmemory;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@ApiModel
@EqualsAndHashCode//(callSuper = true)
//Make class final to make it immutable
public final class Region extends BaseEntity {
    @ApiModelProperty(
            required = true,
            value = "Regions are identified by region id. These should be unique",
            example = "issaquah")
    private String name;

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

    /**
     * Update all the attributes of a region
     *
     * @param src the source region from which to update. This method updates all attributes without any checks
     */
    public void update(Region src) {
        this.description = src.getDescription();
        this.searchKey = src.getSearchKey();

        this.locations.clear();
        this.locations.addAll(src.getLocations());
    }

    /**
     * Patch a region
     *
     * @param src the source region from which to patch. This method only updates the non-null attributes of the src region
     */
    public void patch(Region src) {
        if (StringUtils.isNotBlank(src.getDescription())) {
            this.description = src.getDescription();
        }
        if (StringUtils.isNotBlank(src.getSearchKey())) {
            this.searchKey = src.getSearchKey();
        }
        if(!src.getLocations().isEmpty()) {
            this.locations.clear();
            this.locations.addAll(src.getLocations());
        }
    }
}
