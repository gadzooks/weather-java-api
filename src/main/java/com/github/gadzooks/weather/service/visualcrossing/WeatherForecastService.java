package com.github.gadzooks.weather.service.visualcrossing;

import com.github.gadzooks.weather.api.v1.model.ForecastResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface WeatherForecastService {
    ForecastResponseDTO forecast();
}
