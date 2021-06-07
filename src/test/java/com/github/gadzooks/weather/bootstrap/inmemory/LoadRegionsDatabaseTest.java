package com.github.gadzooks.weather.bootstrap.inmemory;

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

//NOTE : only load components needed for this test to make tests faster and avoid
//loading entire Application Context
@SpringBootTest(classes = {RegionRepository.class, LocationRepository.class, LoadRegionsDatabase.class})
//Required since LoadRegionsDatabase gets injected with Configuration Props from resources/*.yml file
@EnableConfigurationProperties
class LoadRegionsDatabaseTest {

    @Autowired
    private RegionRepository regionRepository;

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