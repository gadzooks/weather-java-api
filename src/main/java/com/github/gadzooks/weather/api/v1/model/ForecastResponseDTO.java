
package com.github.gadzooks.weather.api.v1.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Forecast Response DTO")
public class ForecastResponseDTO implements Serializable {
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "latitude of the forecast location",
            example = "46.266891")
    private Double latitude;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "longitude of the forecast location",
            example = "-119.222523")
    private Double longitude;
    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "forecast summary",
            example = "cloudy throughout the week")
    private String description;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            name = "DailyForecast",
            description = "list of daily forecasts")
    private List<DailyForecastDTO> days = null;

    @Serial
    private final static long serialVersionUID = -5779468242832757870L;
}
