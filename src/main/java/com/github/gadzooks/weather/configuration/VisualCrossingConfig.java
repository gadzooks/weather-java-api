package com.github.gadzooks.weather.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VisualCrossingConfig {
    //NOTE : read from ENV variable
    @Getter
    private final String visualCrossingApiKey;

    @Getter
    private final String vcUrl;

    public VisualCrossingConfig(@Value("${VISUAL_CROSSING_API_KEY}") String visualCrossingApiKey,
                                @Value("${visualcrossing.api.url}") String vcUrl) {
        this.visualCrossingApiKey = visualCrossingApiKey;
        this.vcUrl = vcUrl;
    }
}
