package com.github.gadzooks.weather.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RegionConfig {

    private static final Logger log = LoggerFactory.getLogger(RegionConfig.class);

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("running initDatabase");
        };
    }

    public RegionConfig() throws IOException {
        List<Region> jsonRegions;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // create once, reuse
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, Region.class);

        //FIXME read from application.properties
        jsonRegions = mapper.readValue(new File("src/main/resources/regions.yml"), listType);

        this.regions = ImmutableList.copyOf(jsonRegions);

        Map<String, Region> byIds = new HashMap<>();

        for (Region r: regions) {
            log.debug("region is : " + r);
            byIds.put(r.name, r);
        }
        this.regionsById = ImmutableMap.copyOf(byIds);
    }

    // NOTE: using Guava's immutable collection classes
    @Getter
    private final ImmutableList<Region> regions;
    private final ImmutableMap<String, Region> regionsById;

    public Region getRegionByName(final String id) {
        return regionsById.get(id);
    }

    @Getter
    @NoArgsConstructor
    @ToString
    //Make class final to make it immutable
    public static final class Region {
        private String name;
        private String searchKey;
        private String description;

        public void update(Region src) {
            if(!src.getDescription().isBlank()) {
                this.description = src.getDescription();
            }
            if(!src.getSearchKey().isBlank()) {
                this.searchKey = src.getSearchKey();
            }
        }

        public void delete() {
            throw new RuntimeException("delete not implemented yet");
        }
    }

}
