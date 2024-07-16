package com.github.gadzooks.weather.controller.visualcrossing;

import com.github.gadzooks.weather.api.v1.model.ForecastResponseDTO;
import com.github.gadzooks.weather.service.visualcrossing.WeatherForecastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Forecast",
        description = "REST API for getting weather forecast" // way to group HTTP operations together in Swagger
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
    @Operation(summary = "Get latest weather for location via HttpComponent",
            tags = {TAG},
            description = "This method returns weather forecast for location")
    @ApiResponse(
        responseCode = "200",
        description = "find region by id",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ForecastResponseDTO.class)
            )
        }
    )
    @ApiResponse(
        responseCode = "404",
        description = "Forecast not found"
    )
    public ForecastResponseDTO findOneHttpComponent() {
        return httpComponentWeatherService.forecast();
    }

    @GetMapping(value = "/rest-template")
    @Operation(summary = "Get latest weather for location",
            tags = {TAG},
            description = "This method returns weather forecast for location via RestTemplate")
    @ApiResponse(
        responseCode = "200",
        description = "find region by id",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ForecastResponseDTO.class)
            )
        }
    )
    @ApiResponse(
        responseCode = "404",
        description = "Forecast not found"
    )
    public ForecastResponseDTO findOneRestTemplate() {
        return restTemplateWeatherService.forecast();
    }
}

