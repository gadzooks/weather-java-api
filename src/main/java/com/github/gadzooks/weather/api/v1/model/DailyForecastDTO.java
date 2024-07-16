package com.github.gadzooks.weather.api.v1.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Daily Forecast DTO")
public class DailyForecastDTO implements Serializable {

    private String datetime;
    private Integer datetimeEpoch;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Maximum temperature for the day",
            example = "79.5")
    private Double tempmax;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Minimum temperature for the day",
            example = "79.5")
    private Double tempmin;
    private Double temp;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Rain in inches",
            example = ".5")
    private Double precip;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Chance of rain",
            example = "68%")
    private Double precipprob;
    private Double windgust;
    private Double cloudcover;
    private Double visibility;
    private Double severerisk;
    private String sunrise;
    private Integer sunriseEpoch;
    private String sunset;
    private Integer sunsetEpoch;
    private Double moonphase;
    private String conditions;
    private String description;
    private String icon;

}
