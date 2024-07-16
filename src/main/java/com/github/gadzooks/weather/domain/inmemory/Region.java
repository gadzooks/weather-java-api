package com.github.gadzooks.weather.domain.inmemory;

import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@ToString
@Schema(description = "Description of Region entity")
//Make class final to make it immutable
public final class Region extends BaseEntity {
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            title = "Regions are identified by region id. These should be unique",
            example = "issaquah")
    private String name;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            title = "searchKey used for wta.org website",
            example = "592fcc9afd9208db3b81fdf93dada567")
    private String searchKey;

    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            title = "descriptive name. Defaults to id",
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
