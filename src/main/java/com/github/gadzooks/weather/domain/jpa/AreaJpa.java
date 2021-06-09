package com.github.gadzooks.weather.domain.jpa;

import com.google.common.collect.ImmutableSet;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor //Required by JPA
@Entity // JPA entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"regionJpas"}, callSuper = true)
@ToString(exclude = {"regionJpas"})
public class AreaJpa extends BaseEntity {
    private String name;
    private String description;

    @OneToMany
    @JoinColumn(name = "area_jpa_id")
    private Set<RegionJpa> regionJpas = new HashSet<>();

    public ImmutableSet<RegionJpa> getRegionJpas() {
        return ImmutableSet.copyOf(regionJpas);
    }

    public int regionJpasSize() {
        return regionJpas.size();
    }

    // NOTE : set up bi-directional references
    public void addRegionJpa(RegionJpa newRegion) {
        if (!regionJpas.contains(newRegion)) {
            regionJpas.add(newRegion);
            newRegion.setAreaJpa(this);
        }
    }

}
