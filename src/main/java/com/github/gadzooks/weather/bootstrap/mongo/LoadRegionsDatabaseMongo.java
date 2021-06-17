package com.github.gadzooks.weather.bootstrap.mongo;

import com.github.gadzooks.weather.domain.inmemory.Location;
import com.github.gadzooks.weather.domain.inmemory.Region;
import com.github.gadzooks.weather.domain.mongo.LocationDocument;
import com.github.gadzooks.weather.domain.mongo.RegionDocument;
import com.github.gadzooks.weather.repository.inmemory.RegionRepository;
import com.github.gadzooks.weather.repository.mongo.MongoLocationRepository;
import com.github.gadzooks.weather.repository.mongo.MongoRegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value = 10)
@Component
@Slf4j
public class LoadRegionsDatabaseMongo implements CommandLineRunner {
    private final RegionRepository inMemoryRegionRepository;
    private final MongoRegionRepository mongoRegionRepository;
    private final MongoLocationRepository locationRepository;

    public LoadRegionsDatabaseMongo(
            com.github.gadzooks.weather.repository.inmemory.RegionRepository inMemoryRegionRepository,
            MongoRegionRepository mongoRegionRepository,
            MongoLocationRepository locationRepository) {
        this.inMemoryRegionRepository = inMemoryRegionRepository;
        this.mongoRegionRepository = mongoRegionRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) {
        if (mongoRegionRepository.count() == 0) {

            for (Region region : inMemoryRegionRepository.findAll()) {
                //FIXME : check if each RegionDocument already exists before saving
                RegionDocument rd = new RegionDocument();
                rd.setName(region.getName());
                rd.setSearchKey(region.getSearchKey());
                rd.setDescription(region.getDescription());
                rd.setIsActive(true);

                for (Location l : region.getLocations()) {
                    LocationDocument ld = new LocationDocument();
                    ld.setName(l.getName());
                    ld.setDescription(l.getDescription());
                    ld.setLatitude(l.getLatitude());
                    ld.setLongitude(l.getLongitude());
                    ld.setSubRegion(l.getSubRegion());

                    locationRepository.save(ld);

                    rd.getLocations().add(ld);
                }

                mongoRegionRepository.save(rd);
            }

            log.info("RegionDocument Mongo Document : saved " + mongoRegionRepository.count() + " documents.");
        } else {
            log.info("Mongo data already loaded. Skipping this step");
        }

    }
}
