
package com.github.gadzooks.weather.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class ForecastResponseDTO implements Serializable {
    @ApiModelProperty(
            required = true,
            value = "latitude of the forecast location",
            example = "46.266891")
    private Double latitude;
    @ApiModelProperty(
            required = true,
            value = "longitude of the forecast location",
            example = "-119.222523")
    private Double longitude;
    @ApiModelProperty(
            required = true,
            value = "forecast summary",
            example = "cloudy throughout the week")
    private String description;
    private List<DailyForecastDTO> days = null;

    private final static long serialVersionUID = -5779468242832757870L;
}
