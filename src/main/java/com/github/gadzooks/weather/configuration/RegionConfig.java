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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "regions", ignoreUnknownFields = false)
@NoArgsConstructor
public class RegionConfig {

    private static final Logger log = LoggerFactory.getLogger(RegionConfig.class);
    private String file;
    public void setFile(final String fp) {
        this.file = fp;
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

        this.regions = ImmutableList.copyOf(jsonRegions);

        Map<String, Region> byIds = new HashMap<>();

        for (Region r: regions) {
            log.info("region is : " + r);
            byIds.put(r.name, r);
        }
        this.regionsById = ImmutableMap.copyOf(byIds);
    }

    // NOTE: using Guava's immutable collection classes
    @Getter
    private ImmutableList<Region> regions;
    private ImmutableMap<String, Region> regionsById;

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
