package com.github.gadzooks.weather.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.gadzooks.weather.domain.inmemory.Location;
import com.github.gadzooks.weather.mapstruct.Default;
import com.google.common.collect.ImmutableSet;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor // Required by JPA
@Entity
@Data
@EqualsAndHashCode(exclude = {"regionJpas"}, callSuper = true)
@ToString(exclude = {"regionJpas"})
// NOTE 1 : DO NOT use Lomboks @EqualsAndHashCode -> we want to compare based on the unique key, and we want to allow
// users to add multiple new objects to a set (lombok will count those as equal (since key is null for new objs) )

// NOTE 2 : Do NOT use Lombok's @toString -> this will load entities that may be set up as lazy fetch
// Dont use @Data for the same reason
public class LocationJpa extends BaseEntity {
    private String name;
    private String description;
    private String subRegion;
    private Float latitude;
    private Float longitude;

    //we dont want to include regionJpas in the builder so we cannot use the annotation at the class level
    @Builder
    @Default
    public LocationJpa(String name, String description, String subRegion, Float latitude, Float longitude) {
        this.name = name;
        this.description = description;
        this.subRegion = subRegion;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToMany(mappedBy = "locationJpas")
    private final Set<RegionJpa> regionJpas = new HashSet<>();

    public ImmutableSet<RegionJpa> getRegionJpas() {
        return ImmutableSet.copyOf(regionJpas);
    }

    // NOTE : add bi-directional references
    public void addRegion(RegionJpa newRegion) {
        if (!regionJpas.contains(newRegion)) {
            regionJpas.add(newRegion);
            newRegion.addLocation(this);
        }
    }

    public LocationJpa(final Location locationDto) {
        this.name = locationDto.getName();
        this.description = locationDto.getDescription();
        this.subRegion = locationDto.getSubRegion();
        this.latitude = locationDto.getLatitude();
        this.longitude = locationDto.getLongitude();
    }

}