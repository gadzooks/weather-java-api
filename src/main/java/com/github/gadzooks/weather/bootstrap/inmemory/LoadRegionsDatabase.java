package com.github.gadzooks.weather.bootstrap.inmemory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.gadzooks.weather.configuration.WeatherPropertiesConfiguration;
import com.github.gadzooks.weather.domain.inmemory.Location;
import com.github.gadzooks.weather.domain.inmemory.Region;
import com.github.gadzooks.weather.repository.inmemory.LocationRepository;
import com.github.gadzooks.weather.repository.inmemory.RegionRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// lower values have higher priority
@Order(value = 1)
@Component
@ConfigurationProperties(prefix = "file-paths", ignoreUnknownFields = false)
@Slf4j
public class LoadRegionsDatabase implements CommandLineRunner {
    private final RegionRepository regionRepository;
    private final LocationRepository locationRepository;

    // several ways to load configuration properties :
    // 1. wpc loads via the @Value annotation
    @Getter
    private final WeatherPropertiesConfiguration wpc;
    // 2. field injection via setters and @ConfigurationProperties annotation
    @Setter
    @Getter
    private String regionsFilePath; //being set from application.yml
    @Setter
    @Getter
    private String locationsFilePath; //being set from application.yml

    public LoadRegionsDatabase(RegionRepository regionRepository, LocationRepository locationRepository, WeatherPropertiesConfiguration wpc) {
        this.regionRepository = regionRepository;
        this.locationRepository = locationRepository;
        this.wpc = wpc;
    }

    @Override
    public void run(String... args) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // create once, reuse
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, Region.class);

        log.info("load regions from file WPC : " + wpc.getRegionsFile());
        List<Region> jsonRegions = mapper.readValue(new File(wpc.getRegionsFile()), listType);
        int numAdded = regionRepository.addAll(jsonRegions);
        log.info(numAdded + " regions were added");

        CollectionType locationsListType = mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, Location.class);

        log.info("load locations from file : " + wpc.getLocationsFile());
        List<Location> jsonLocations = mapper.readValue(new File(wpc.getLocationsFile()), locationsListType);
        int numLocSaved = locationRepository.addAll(jsonLocations);
        log.info(numLocSaved + " locations were saved");
        int numLocAdded = regionRepository.addLocations(jsonLocations);
        log.info(numLocAdded + " locations were added");

    }
}
