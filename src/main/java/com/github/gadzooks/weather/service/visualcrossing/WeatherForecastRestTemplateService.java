package com.github.gadzooks.weather.service.visualcrossing;

import com.github.gadzooks.weather.domain.visualcrossing.ForecastResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Qualifier("weather-with-rest-template")
@Slf4j
public class WeatherForecastRestTemplateService implements WeatherForecastService {
    private final RestTemplate restTemplate;
    //NOTE : read from ENV variable
    private final String visualCrossingApiKey;
    private final String vcUrl;

    public WeatherForecastRestTemplateService(
            RestTemplate restTemplate,
            @Value("${VISUAL_CROSSING_API_KEY}") String visualCrossingApiKey,
            @Value("${visualcrossing.api.url}") String vcUrl) {
        this.restTemplate = restTemplate;
        this.visualCrossingApiKey = visualCrossingApiKey;
        this.vcUrl = vcUrl;
    }

    @Override
    public void forecast() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(vcUrl + "46.266891,-119.222523")
                .queryParam("include", "obs,fcst,alerts")
                .queryParam("key", visualCrossingApiKey);
        ForecastResponse response = restTemplate.getForObject(uriBuilder.toUriString(), ForecastResponse.class);

        log.info("forecast from rest-template : " + response);
    }
}
