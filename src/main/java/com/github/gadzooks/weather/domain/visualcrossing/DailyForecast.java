
package com.github.gadzooks.weather.domain.visualcrossing;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Generated;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Generated("jsonschema2pojo")
public class DailyForecast implements Serializable {

    private String datetime;
    private Integer datetimeEpoch;
    private Double tempmax;
    private Double tempmin;
    private Double temp;
    private Double feelslikemax;
    private Double feelslikemin;
    private Double feelslike;
    private Double dew;
    private Double humidity;
    private Double precip;
    private Double precipprob;
    private Object precipcover;
    private Object preciptype;
    private Double snow;
    private Double snowdepth;
    private Double windgust;
    private Double windspeed;
    private Double winddir;
    private Double pressure;
    private Double cloudcover;
    private Double visibility;
    private Double solarradiation;
    private Double solarenergy;
    private Double uvindex;
    private Double severerisk;
    private String sunrise;
    private Integer sunriseEpoch;
    private String sunset;
    private Integer sunsetEpoch;
    private Double moonphase;
    private String conditions;
    private String description;
    private String icon;
    private Object stations;
    private String source;
    private final static long serialVersionUID = -2490424160510576664L;

}
