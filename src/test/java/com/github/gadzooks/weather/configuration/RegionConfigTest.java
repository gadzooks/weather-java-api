package com.github.gadzooks.weather.configuration;

import com.github.gadzooks.weather.dto.Region;
import com.github.gadzooks.weather.repository.RegionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RegionConfigTest {

    @Test
    void getRegions() {
        RegionRepository regionRepository = new RegionRepository();

        System.out.println(regionRepository.getRegions());
        assertThat(regionRepository.getRegions().size(), greaterThan(0));

        Region firstRegion = regionRepository.getRegions().get(0);
        assertThat(firstRegion, is(notNullValue()));
        assertThat(firstRegion.getDescription(), is(not(Matchers.emptyString())));
        assertThat(firstRegion.getName(), is(not(Matchers.emptyString())));
        assertThat(firstRegion.getSearchKey(), is(not(Matchers.emptyString())));
    }
}