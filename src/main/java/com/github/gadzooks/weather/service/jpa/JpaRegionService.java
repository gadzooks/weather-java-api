package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.service.CrudService;

import java.util.List;

public interface JpaRegionService extends CrudService<RegionJpa, Long> {
    List<RegionJpa> findAllActive();

    List<RegionJpa> findAllByIdsIn(List<Long> ids);
}
