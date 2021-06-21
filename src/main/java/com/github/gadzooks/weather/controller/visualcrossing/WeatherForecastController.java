package com.github.gadzooks.weather.controller.visualcrossing;

import com.github.gadzooks.weather.api.v1.model.ForecastResponseDTO;
import com.github.gadzooks.weather.service.visualcrossing.WeatherForecastService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(
        value = "Forecast",
        description = "",
        tags = {"REST API for getting weather forecast"} // way to group HTTP operations together in Swagger
)
@RestController
//Allow requests from other domains
@CrossOrigin(origins = "*")
//set prefix of path of /place for all requests in PlaceController
@RequestMapping(path = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class WeatherForecastController {

    private final WeatherForecastService httpComponentWeatherService;
    private final WeatherForecastService restTemplateWeatherService;

    private static final String TAG = "REST API for getting weather forecast";

    public WeatherForecastController(
            @Qualifier("http-components") WeatherForecastService httpComponentWeatherService,
            @Qualifier("weather-with-rest-template") WeatherForecastService restTemplateWeatherService) {
        this.httpComponentWeatherService = httpComponentWeatherService;
        this.restTemplateWeatherService = restTemplateWeatherService;
    }

    @GetMapping(value = "/http-component")
    @ApiOperation(value = "Get latest weather for location via HttpComponent",
            tags = {TAG},
            notes = "This method returns weather forecast for location")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = ForecastResponseDTO.class)})
    public ForecastResponseDTO findOneHttpComponent() {
        return httpComponentWeatherService.forecast();
    }

    @GetMapping(value = "/rest-template")
    @ApiOperation(value = "Get latest weather for location",
            tags = {TAG},
            notes = "This method returns weather forecast for location via RestTemplate")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = ForecastResponseDTO.class)})
    public ForecastResponseDTO findOneRestTemplate() {
        return restTemplateWeatherService.forecast();
    }
}

