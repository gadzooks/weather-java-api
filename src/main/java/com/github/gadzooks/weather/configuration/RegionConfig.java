package com.github.gadzooks.weather.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.gadzooks.weather.dto.Region;
import com.github.gadzooks.weather.repository.RegionRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "regions", ignoreUnknownFields = false)
public class RegionConfig {

    private static final Logger log = LoggerFactory.getLogger(RegionConfig.class);
    @Setter
    private String file;
    private final RegionRepository regionRepository;

    public RegionConfig(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

//    @Bean
//    CommandLineRunner initDatabase() {
//        return args -> {
//            log.info("running initDatabase");
//        };
//    }

    @PostConstruct
    public void init() throws IOException {
        List<Region> jsonRegions;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // create once, reuse
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, Region.class);

        log.info("load regions from file : " + file);
        jsonRegions = mapper.readValue(new File(file), listType);
        int numAdded = regionRepository.addAll(jsonRegions);
        log.info(numAdded + " regions were added");
    }
}
