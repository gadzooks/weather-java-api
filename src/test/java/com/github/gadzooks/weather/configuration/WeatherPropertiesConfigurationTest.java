package com.github.gadzooks.weather.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = WeatherPropertiesConfiguration.class)
class WeatherPropertiesConfigurationTest {

    @Autowired
    private WeatherPropertiesConfiguration config;

    @Test
    void getRegionsFile() {
        assertThat(config.getRegionsFile(),equalTo("src/main/resources/regions.yml"));
    }

    @Test
    void getLocationsFile() {
        assertThat(config.getLocationsFile(),equalTo("src/main/resources/locations.yml"));
    }
}