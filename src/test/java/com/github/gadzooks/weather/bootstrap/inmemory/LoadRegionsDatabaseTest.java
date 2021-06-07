package com.github.gadzooks.weather.bootstrap.inmemory;

import com.github.gadzooks.weather.configuration.WeatherPropertiesConfiguration;
import com.github.gadzooks.weather.domain.inmemory.Region;
import com.github.gadzooks.weather.repository.inmemory.LocationRepository;
import com.github.gadzooks.weather.repository.inmemory.RegionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//NOTE : only load components needed for this test to make tests faster and avoid
//loading entire Application Context
@SpringBootTest(classes = {RegionRepository.class, WeatherPropertiesConfiguration.class,
        LocationRepository.class, LoadRegionsDatabase.class})
//Required since LoadRegionsDatabase gets injected with Configuration Props from resources/*.yml file
@EnableConfigurationProperties
class LoadRegionsDatabaseTest {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private LoadRegionsDatabase loadRegionsDatabase;

    @Test
    void getConfigurationProperties_via_setterInjection_and_via_valueAnnotation() {
        // verify that we are loading configuration properties via both
        // 1. Setter injection
        // 2. @Value annotation

        assertEquals(loadRegionsDatabase.getRegionsFilePath(),
                loadRegionsDatabase.getWpc().getRegionsFile());

        assertEquals(loadRegionsDatabase.getLocationsFilePath(),
                loadRegionsDatabase.getWpc().getLocationsFile());

    }

    @Test
    void getRegions() {
        System.out.println(regionRepository.findAll());
        assertThat(regionRepository.size(), greaterThan(0));

        Region firstRegion = regionRepository.findAll().get(0);
        assertThat(firstRegion, is(notNullValue()));
        assertThat(firstRegion.getDescription(), is(not(Matchers.emptyString())));
        assertThat(firstRegion.getName(), is(not(Matchers.emptyString())));
        assertThat(firstRegion.getSearchKey(), is(not(Matchers.emptyString())));
    }
}