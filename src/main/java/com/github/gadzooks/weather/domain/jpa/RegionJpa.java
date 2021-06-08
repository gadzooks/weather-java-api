package com.github.gadzooks.weather.domain.jpa;

import com.github.gadzooks.weather.domain.inmemory.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor // Required by JPA
@Entity
@Getter
@Setter
// NOTE 1 : DO NOT use Lomboks @EqualsAndHashCode -> we want to compare based on the unique key, and we want to allow
// users to add multiple new objects to a set (lombok will count those as equal (since key is null for new objs) )

// NOTE 2 : Do NOT use Lombok's @toString -> this will load entities that may be set up as lazy fetch
// Dont use @Data for the same reason
public class RegionJpa {
    @Id
    //underlying will provide the id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
        this.id = id;
        this.name = name;
        this.searchKey = searchKey;
        this.description = description;
        this.isActive = isActive;
    }

    @ManyToOne
    private AreaJpa areaJpa;

    @Getter
    @ManyToMany
    @JoinTable(name = "region_location_mappings", joinColumns = @JoinColumn(name = "region_jpa_id"),
            inverseJoinColumns = @JoinColumn(name = "location_jpa_id"))
    private Set<LocationJpa> locationJpas = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionJpa regionJpa = (RegionJpa) o;

        // NOTE : id could be null, if we are adding multiple new objects
        return id != null ? id.equals(regionJpa.id) : regionJpa.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RegionJpa{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", searchKey='" + searchKey + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
