
package com.github.gadzooks.weather.api.v1.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ForecastResponseDTO implements Serializable {

    private Double latitude;
    private Double longitude;
    private String description;
    private List<DailyForecastDTO> days = null;
    private final static long serialVersionUID = -5779468242832757870L;
}
