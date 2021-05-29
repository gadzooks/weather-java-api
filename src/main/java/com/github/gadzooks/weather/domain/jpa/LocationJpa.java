package com.github.gadzooks.weather.domain.jpa;

import com.github.gadzooks.weather.dto.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor // Required by JPA
@Entity
// NOTE 1 : DO NOT use Lomboks @EqualsAndHashCode -> we want to compare based on the unique key, and we want to allow
// users to add multiple new objects to a set (lombok will count those as equal (since key is null for new objs) )

// NOTE 2 : Do NOT use Lombok's @toString -> this will load entities that may be set up as lazy fetch
// Dont use @Data for the same reason
public class LocationJpa {
    @Id
    //underlying will provide the id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String subRegion;
    private Float latitude;
    private Float longitude;

    @Getter
    @ManyToMany(mappedBy = "locationJpas")
    private Set<RegionJpa> regionJpas = new HashSet<>();

    public LocationJpa(final Location locationDto) {
        this.name = locationDto.getName();
        this.description = locationDto.getDescription();
        this.subRegion = locationDto.getSubRegion();
        this.latitude = locationDto.getLatitude();
        this.longitude = locationDto.getLongitude();
    }

    public LocationJpa(String name, String description, String subRegion, Float latitude, Float longitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subRegion = subRegion;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationJpa that = (LocationJpa) o;

        // NOTE : id could be null, if we are adding multiple new objects
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "LocationJpa{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", subRegion='" + subRegion + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", regionJpas=" + regionJpas +
                '}';
    }
}
