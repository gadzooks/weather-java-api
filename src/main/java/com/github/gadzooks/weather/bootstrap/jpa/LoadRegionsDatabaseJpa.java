package com.github.gadzooks.weather.bootstrap.jpa;

import com.github.gadzooks.weather.domain.inmemory.Location;
import com.github.gadzooks.weather.domain.inmemory.Region;
import com.github.gadzooks.weather.domain.jpa.AreaJpa;
import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.repository.inmemory.RegionRepository;
import com.github.gadzooks.weather.repository.jpa.AreaJpaRepository;
import com.github.gadzooks.weather.repository.jpa.LocationJpaRepository;
import com.github.gadzooks.weather.repository.jpa.RegionJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value = 10) //lower values have higher priority
@Component
@Slf4j
public class LoadRegionsDatabaseJpa implements CommandLineRunner {
    private final RegionJpaRepository regionJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final AreaJpaRepository areaJpaRepository;
    private final RegionRepository srcRepository;

    public LoadRegionsDatabaseJpa(RegionJpaRepository regionJpaRepository, LocationJpaRepository locationJpaRepository, AreaJpaRepository areaJpaRepository, RegionRepository srcRepository) {
        this.regionJpaRepository = regionJpaRepository;
        this.locationJpaRepository = locationJpaRepository;
        this.areaJpaRepository = areaJpaRepository;
        this.srcRepository = srcRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        AreaJpa areaJpa = new AreaJpa();
        areaJpa.setName("Pacific North West");
        areaJpa.setDescription("Pacific North West");
        areaJpaRepository.save(areaJpa);

        for(Region region : srcRepository.findAll()) {
            RegionJpa regionJpa = new RegionJpa(region);
            regionJpaRepository.save(regionJpa);
            areaJpa.getRegionJpas().add(regionJpa);

            for(Location location : region.getLocations()) {
                LocationJpa locationJpa = new LocationJpa(location);
                locationJpa.getRegionJpas().add(regionJpa);
                locationJpaRepository.save(locationJpa);
                regionJpa.getLocationJpas().add(locationJpa);
            }
            regionJpaRepository.save(regionJpa);
        }
        areaJpaRepository.save(areaJpa);

        log.info("Area JPA repository : " + areaJpaRepository.count() + " areas saved.");
        log.info("Region JPA repository : " + regionJpaRepository.count() + " regions saved.");
        log.info("Location JPA repository : " + locationJpaRepository.count() + " locations saved.");

        // FIXME : lazy load from area to regin to location
    }
}
