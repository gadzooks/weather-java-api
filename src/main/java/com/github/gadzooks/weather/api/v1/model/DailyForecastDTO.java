package com.github.gadzooks.weather.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class DailyForecastDTO implements Serializable {

    private String datetime;
    private Integer datetimeEpoch;

    @ApiModelProperty(
            required = true,
            value = "Maximum temperature for the day",
            example = "79.5")
    private Double tempmax;
    @ApiModelProperty(
            required = true,
            value = "Minimum temperature for the day",
            example = "79.5")
    private Double tempmin;
    private Double temp;
    @ApiModelProperty(
            required = true,
            value = "Rain in inches",
            example = ".5")
    private Double precip;
    @ApiModelProperty(
            required = true,
            value = "Chance of rain",
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
