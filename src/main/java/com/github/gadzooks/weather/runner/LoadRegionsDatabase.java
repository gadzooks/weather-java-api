package com.github.gadzooks.weather.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.gadzooks.weather.dto.Location;
import com.github.gadzooks.weather.dto.Region;
import com.github.gadzooks.weather.repository.RegionRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "file-paths", ignoreUnknownFields = false)
public class LoadRegionsDatabase implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(LoadRegionsDatabase.class);
    private final RegionRepository regionRepository;
    @Setter
    private String regionsFilePath; //being set from application.yml
    @Setter
    private String locationsFilePath; //being set from application.yml

    public LoadRegionsDatabase(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public void run(String... args) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // create once, reuse
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, Region.class);

        log.info("load regions from file : " + regionsFilePath);
        List<Region> jsonRegions = mapper.readValue(new File(regionsFilePath), listType);
        int numAdded = regionRepository.addAll(jsonRegions);
        log.info(numAdded + " regions were added");

        CollectionType locationsListType = mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, Location.class);

        log.info("load locations from file : " + locationsFilePath);
        List<Location> jsonLocations = mapper.readValue(new File(locationsFilePath), locationsListType);
        int numLocAdded = regionRepository.addLocations(jsonLocations);
        log.info(numLocAdded + " locations were added");

    }
}
