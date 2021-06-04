package com.github.gadzooks.weather.domain.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor //Required by JPA
@Entity // JPA entity
@Getter
@Setter
public class AreaJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    @OneToMany
    @JoinColumn(name = "area_jpa_id")
    private Set<RegionJpa> regionJpas = new HashSet<>();

    @Override
    public String toString() {
        return "AreaJpa{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaJpa areaJpa = (AreaJpa) o;

        return id != null ? id.equals(areaJpa.id) : areaJpa.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
