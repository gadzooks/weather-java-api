package com.github.gadzooks.weather.service.visualcrossing;

import com.github.gadzooks.weather.configuration.VisualCrossingConfig;
import com.github.gadzooks.weather.domain.visualcrossing.ForecastResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Qualifier("weather-with-rest-template")
@Slf4j
public class WeatherForecastRestTemplateService implements WeatherForecastService {
    private final RestTemplate restTemplate;
    private final VisualCrossingConfig vcConfig;

    public WeatherForecastRestTemplateService(
            RestTemplate restTemplate,
            VisualCrossingConfig vcConfig) {
        this.restTemplate = restTemplate;
        this.vcConfig = vcConfig;
    }

    @Override
    public void forecast() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(vcConfig.getVcUrl() + "/timeline/" + "46.266891,-119.222523")
                .queryParam("include", "obs,fcst,alerts")
                .queryParam("key", vcConfig.getVisualCrossingApiKey());
        ForecastResponse response = restTemplate.getForObject(uriBuilder.toUriString(), ForecastResponse.class);

        log.info("forecast from rest-template : " + response);
    }
}
