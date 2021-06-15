package com.github.gadzooks.weather.controller.visualcrossing;

import com.github.gadzooks.weather.service.visualcrossing.WeatherForecastService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(
        value = "Forecast",
        description = "",
        tags = { "REST API for getting weather forecast" } // way to group HTTP operations together in Swagger
)
@RestController
//Allow requests from other domains
@CrossOrigin(origins = "*")
//set prefix of path of /place for all requests in PlaceController
@RequestMapping(path = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class WeatherForecastController {

    private final WeatherForecastService weatherForecastService;
    private static final String TAG = "REST API for getting weather forecast";

    public WeatherForecastController(WeatherForecastService weatherForecastService) {
        this.weatherForecastService = weatherForecastService;
    }

    @GetMapping(value = "")
    @ApiOperation(value = "Get latest weather for location",
            tags = { TAG },
            notes = "This method returns weather forecast for location")
    public void findAll() {
        weatherForecastService.forecast();
    }
}

