package com.github.gadzooks.weather.domain.jpa;

import com.google.common.collect.ImmutableSet;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @Builder
    public AreaJpa(Long id, String name, String description) {
        setId(id);
        this.name = name;
        this.description = description;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "area_jpa_id")
    private final Set<RegionJpa> regionJpas = new HashSet<>();

    public ImmutableSet<RegionJpa> getRegionJpas() {
        return ImmutableSet.copyOf(regionJpas);
    }

    public int regionJpasSize() {
        return regionJpas.size();
    }

    // NOTE : set up bi-directional references
    public AreaJpa addRegionJpa(RegionJpa newRegion) {
        if (!regionJpas.contains(newRegion)) {
            regionJpas.add(newRegion);
            newRegion.setAreaJpa(this);
        }
        return this;
    }

}
