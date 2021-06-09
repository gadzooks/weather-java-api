package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.domain.jpa.RegionJpa;

import java.util.List;

public interface PlacesService {
    /**
     * Search in area, region and location for searchString
     *
     * @param searchString the search string
     * @return the list of AreaJpa objects
     */
    List<RegionJpa> searchEveryWhere(String searchString);
}
