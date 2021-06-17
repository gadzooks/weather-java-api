
package com.github.gadzooks.weather.api.v1.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class DailyForecastDTO implements Serializable {

    private String datetime;
    private Integer datetimeEpoch;
    private Double tempmax;
    private Double tempmin;
    private Double temp;
    private Double precip;
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
