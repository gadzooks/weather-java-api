package com.github.gadzooks.weather.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.gadzooks.weather.domain.inmemory.Region;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor // Required by JPA
@Getter
@Setter
@ToString(exclude = {"areaJpa", "locationJpas"})
@EqualsAndHashCode(exclude = {"areaJpa", "locationJpas"}, callSuper = true)
// NOTE 1 : DO NOT use Lomboks @EqualsAndHashCode -> we want to compare based on the unique key, and we want to allow
// users to add multiple new objects to a set (lombok will count those as equal (since key is null for new objs) )

// NOTE 2 : Do NOT use Lombok's @toString -> this will load entities that may be set up as lazy fetch
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

    public RegionJpa(String name, String searchKey, String description, Boolean isActive) {
        this.name = name;
        this.searchKey = searchKey;
        this.description = description;
        this.isActive = isActive;
    }

    @JsonIgnore
    @ManyToOne
    private AreaJpa areaJpa;

    @Getter
    @ManyToMany
    @JoinTable(name = "region_location_mappings", joinColumns = @JoinColumn(name = "region_jpa_id"),
            inverseJoinColumns = @JoinColumn(name = "location_jpa_id"))
    private Set<LocationJpa> locationJpas = new HashSet<>();

}
