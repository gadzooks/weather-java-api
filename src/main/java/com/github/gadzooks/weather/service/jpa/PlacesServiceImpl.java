package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.commands.RegionCommand;
import com.github.gadzooks.weather.converters.LocationJpaCommandToLocationConverter;
import com.github.gadzooks.weather.converters.RegionJpaCommandToRegionConverter;
import com.github.gadzooks.weather.domain.jpa.AreaJpa;
import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.repository.jpa.AreaJpaRepository;
import com.github.gadzooks.weather.repository.jpa.LocationJpaRepository;
import com.github.gadzooks.weather.repository.jpa.RegionJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Places service.
 */
@Service
@Slf4j
public class PlacesServiceImpl implements PlacesService {
    private final AreaJpaRepository areaJpaRepository;
    private final RegionJpaRepository regionJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final RegionJpaCommandToRegionConverter regionJpaCommandToRegionConverter;
    private final LocationJpaCommandToLocationConverter locationJpaCommandToLocationConverter;


    /**
     * Instantiates a new Places service.
     * @param areaJpaRepository     the area jpa repository
     * @param regionJpaRepository   the region jpa repository
     * @param locationJpaRepository the location jpa repository
     * @param regionJpaCommandToRegionConverter convert from JPA entity to command object
     * @param locationJpaCommandToLocationConverter convert from JPA entity to command object
     */
    public PlacesServiceImpl(AreaJpaRepository areaJpaRepository, RegionJpaRepository regionJpaRepository, LocationJpaRepository locationJpaRepository, RegionJpaCommandToRegionConverter regionJpaCommandToRegionConverter, LocationJpaCommandToLocationConverter locationJpaCommandToLocationConverter) {
        this.areaJpaRepository = areaJpaRepository;
        this.regionJpaRepository = regionJpaRepository;
        this.locationJpaRepository = locationJpaRepository;
        this.regionJpaCommandToRegionConverter = regionJpaCommandToRegionConverter;
        this.locationJpaCommandToLocationConverter = locationJpaCommandToLocationConverter;
    }

    @Override
    public List<RegionCommand> searchEveryWhere(String searchString) {
        List<RegionJpa> regionJpas = regionJpaRepository.
                findAllByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(searchString, searchString);
        log.debug("found " + regionJpas.size() + " regions for search string : " + searchString);

        List<LocationJpa> locationJpas = locationJpaRepository.
                findAllByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(searchString, searchString);
        log.debug("found " + locationJpas.size() + " locations for search string : " + searchString);
        locationJpas.forEach(l -> regionJpas.addAll(l.getRegionJpas()));

        List<AreaJpa> areaJpas = areaJpaRepository.
                findAllByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(searchString, searchString);
        log.debug("found " + areaJpas.size() + " areas for search string : " + searchString);
        // add all the regions for those areas :
        areaJpas.forEach(a -> regionJpas.addAll(a.getRegionJpas()));

        List<RegionCommand> results = new ArrayList<>();
        regionJpas.forEach(region -> {
            RegionCommand rc = regionJpaCommandToRegionConverter.convert(region);
            rc.setLocationCommandSet(region.getLocationJpas().stream().map(locationJpaCommandToLocationConverter::convert).
                    collect(Collectors.toSet()));
            results.add(rc);
        });

//        return regionJpas.stream().map(regionJpaCommandToRegionConverter::convert).collect(Collectors.toList());
        return results;
    }
}
