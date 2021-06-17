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

    //Set default value for ENV variable so that tests pass on CircleCI without
    //without needing to add ENV variables there
    public VisualCrossingConfig(@Value("${VISUAL_CROSSING_API_KEY:vc.api.key.here}") String visualCrossingApiKey,
                                @Value("${visualcrossing.api.url}") String vcUrl) {
        this.visualCrossingApiKey = visualCrossingApiKey;
        this.vcUrl = vcUrl;
    }
}
