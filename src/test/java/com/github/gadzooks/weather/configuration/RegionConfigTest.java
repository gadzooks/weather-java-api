package com.github.gadzooks.weather.configuration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RegionConfigTest {

    @Test
    void getRegions() throws IOException {
        RegionConfig config = new RegionConfig();

        System.out.println(config.getRegions());
        assertThat(config.getRegions().size(), greaterThan(0));

        RegionConfig.Region firstRegion = config.getRegions().get(0);
        assertThat(firstRegion, is(notNullValue()));
        assertThat(firstRegion.getDescription(), is(not(Matchers.emptyString())));
        assertThat(firstRegion.getName(), is(not(Matchers.emptyString())));
        assertThat(firstRegion.getSearchKey(), is(not(Matchers.emptyString())));
    }
}