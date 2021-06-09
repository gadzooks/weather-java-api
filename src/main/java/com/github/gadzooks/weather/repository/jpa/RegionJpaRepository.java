package com.github.gadzooks.weather.repository.jpa;

import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegionJpaRepository extends CrudRepository<RegionJpa, Long> {
    List<RegionJpa> findAllByNameContainingOrDescriptionContaining(String name, String description);

    List<RegionJpa> findAllByIsActive(Boolean isActive);

    List<RegionJpa> findAllByIdIn(List<Long> ids);
}
