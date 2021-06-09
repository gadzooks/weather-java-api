package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.domain.jpa.AreaJpa;
import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.repository.jpa.AreaJpaRepository;
import com.github.gadzooks.weather.repository.jpa.LocationJpaRepository;
import com.github.gadzooks.weather.repository.jpa.RegionJpaRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * The type Places service.
 */
@Service
public class PlacesServiceImpl implements PlacesService {
    private final AreaJpaRepository areaJpaRepository;
    private final RegionJpaRepository regionJpaRepository;
    private final LocationJpaRepository locationJpaRepository;

    /**
     * Instantiates a new Places service.
     *
     * @param areaJpaRepository     the area jpa repository
     * @param regionJpaRepository   the region jpa repository
     * @param locationJpaRepository the location jpa repository
     */
    public PlacesServiceImpl(AreaJpaRepository areaJpaRepository, RegionJpaRepository regionJpaRepository, LocationJpaRepository locationJpaRepository) {
        this.areaJpaRepository = areaJpaRepository;
        this.regionJpaRepository = regionJpaRepository;
        this.locationJpaRepository = locationJpaRepository;
    }

    @Override
    public List<RegionJpa> searchEveryWhere(String searchString) {
        List<RegionJpa> regionJpas = regionJpaRepository.
                findAllByNameContainingOrDescriptionContaining(searchString, searchString);
        Set<RegionJpa> regionJpaSet = Sets.newHashSet(regionJpas);
        List<LocationJpa> locationJpas = locationJpaRepository.
                findAllByNameContainingOrDescriptionContaining(searchString, searchString);
        Set<LocationJpa> locationJpaSet = Sets.newHashSet(locationJpas);
        locationJpaSet.forEach(l -> regionJpaSet.addAll(l.getRegionJpas()));

        List<AreaJpa> areaJpas = areaJpaRepository.
                findAllByNameContainingOrDescriptionContaining(searchString, searchString);
        // add all the regions for those areas :
        areaJpas.forEach(l -> regionJpaSet.addAll(l.getRegionJpas()));

        return Lists.newArrayList(regionJpaSet);
    }
}
