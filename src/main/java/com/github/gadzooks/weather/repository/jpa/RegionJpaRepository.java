package com.github.gadzooks.weather.repository.jpa;

import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import org.springframework.data.repository.CrudRepository;

public interface RegionJpaRepository extends CrudRepository<RegionJpa, Long> {
}
