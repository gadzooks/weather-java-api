package com.github.gadzooks.weather.service.visualcrossing;

import com.github.gadzooks.weather.domain.visualcrossing.ForecastResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Qualifier("weather-with-rest-template")
@Slf4j
public class WeatherForecastRestTemplateService implements WeatherForecastService {
    private final RestTemplate restTemplate;
    private static final String VC_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/46.266891,-119.222523?include=obs,fcst,alerts&key=";

    //NOTE : read from ENV variable
    @Value("${VISUAL_CROSSING_API_KEY}")
    private String VisualCrossingApiKey;

    public WeatherForecastRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void forecast() {
        ForecastResponse response = restTemplate.getForObject(VC_URL + VisualCrossingApiKey, ForecastResponse.class);

        log.info("forecast from rest-template : " + response);
    }
}
