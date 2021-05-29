package com.github.gadzooks.weather.bootstrap.jpa;

import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.dto.Location;
import com.github.gadzooks.weather.dto.Region;
import com.github.gadzooks.weather.repository.inmemory.RegionRepository;
import com.github.gadzooks.weather.repository.jpa.LocationJpaRepository;
import com.github.gadzooks.weather.repository.jpa.RegionJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Order(value = 10) //lower values have higher priority
@Component
public class LoadRegionsDatabaseJpa implements CommandLineRunner {
    private final RegionJpaRepository regionJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final RegionRepository srcRepository;
    private static final Logger log = LoggerFactory.getLogger(LoadRegionsDatabaseJpa.class);

    public LoadRegionsDatabaseJpa(RegionJpaRepository regionJpaRepository, LocationJpaRepository locationJpaRepository, RegionRepository srcRepository) {
        this.regionJpaRepository = regionJpaRepository;
        this.locationJpaRepository = locationJpaRepository;
        this.srcRepository = srcRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<RegionJpa> regionJpaList = new ArrayList<>();
        List<LocationJpa> locationJpaList = new ArrayList<>();

        for(Region region : srcRepository.getRegions()) {
            RegionJpa regionJpa = new RegionJpa(region);
            regionJpaList.add(regionJpa);
            for(Location location : region.getLocations()) {
                LocationJpa locationJpa = new LocationJpa(location);
                regionJpa.getLocationJpas().add(locationJpa);
                locationJpa.getRegionJpas().add(regionJpa);
                locationJpaList.add(locationJpa);
            }
        }

        locationJpaRepository.saveAll(locationJpaList);
        regionJpaRepository.saveAll(regionJpaList);

        log.info("Region JPA repository : " + regionJpaRepository.count() + " regions saved.");
        log.info("Location JPA repository : " + locationJpaRepository.count() + " locations saved.");
    }
}
