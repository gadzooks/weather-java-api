package com.github.gadzooks.weather.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.gadzooks.weather.domain.inmemory.Region;
import com.google.common.collect.ImmutableSet;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor // Required by JPA
@Getter
@Setter
// NOTE : Exclude related entities from ToString
@ToString(exclude = {"areaJpa", "locationJpas"})
@EqualsAndHashCode(exclude = {"areaJpa", "locationJpas"}, callSuper = true)
// NOTE 1 : DO NOT use Lomboks @EqualsAndHashCode -> we want to compare based on the unique key, and we want to allow
// users to add multiple new objects to a set (lombok will count those as equal (since key is null for new objs) )

// Dont use @Data for the same reason
public class RegionJpa extends BaseEntity {
    private String name;
    private String searchKey;
    private String description;
    private Boolean isActive;

    public RegionJpa(final Region region) {
        this.name = region.getName();
        this.description = region.getDescription();
        this.isActive = true;
        this.searchKey = region.getSearchKey();
    }

    //we dont want to include areaJpa, locationJpas in the builder so we cannot use the annotation at the class level
    @Builder
    public RegionJpa(Long id, String name, String searchKey, String description, Boolean isActive) {
        this.name = name;
        this.searchKey = searchKey;
        this.description = description;
        this.isActive = isActive;
    }

    @Setter(AccessLevel.NONE)
    @JsonIgnore
    @ManyToOne
    private AreaJpa areaJpa;

    public RegionJpa setAreaJpa(AreaJpa newAreaJpa) {
        if (areaJpa == null || areaJpa != newAreaJpa) {
            areaJpa = newAreaJpa;
            areaJpa.addRegionJpa(this);
        }
        return this;
    }

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "region_location_mappings", joinColumns = @JoinColumn(name = "region_jpa_id"),
            inverseJoinColumns = @JoinColumn(name = "location_jpa_id"))
    private final Set<LocationJpa> locationJpas = new HashSet<>();

    // we dont want to allow clients to update locationJpa outside of our addLocation method
    public ImmutableSet<LocationJpa> getLocationJpas() {
        return ImmutableSet.copyOf(locationJpas);
    }

    // helper method to add a bunch of locations at once
    public int addAllLocations(final Collection<LocationJpa> locations) {
        int startingCount = locationJpas.size();
        locations.forEach(this::addLocation);

        return (locationJpas.size() - startingCount);
    }

    // NOTE : add bi-directional references
    public RegionJpa addLocation(LocationJpa newLoc) {
        if (!locationJpas.contains(newLoc)) {
            locationJpas.add(newLoc);
            newLoc.addRegion(this);
        }
        return this;
    }
}
