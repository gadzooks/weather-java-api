package com.github.gadzooks.weather.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//all files under src/main/resources are copied to the packaged jar/war files
//and are available in the classpath
//PropertySource is needed since properties are NOT in application.properties or application.yml
@PropertySource("classpath:in-memory.yml")
@Data
public class WeatherPropertiesConfiguration {

    private final String regionsFile;
    private final String locationsFile;

    public WeatherPropertiesConfiguration(
            @Value("${regionsFile}") String regionsFile,
            @Value("${locationsFile}") String locationsFile) {
        this.regionsFile = regionsFile;
        this.locationsFile = locationsFile;
    }
}
