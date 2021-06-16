
package com.github.gadzooks.weather.domain.visualcrossing;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.List;

@Generated("jsonschema2pojo")
@Getter
@Setter
@ToString
public class ForecastResponse implements Serializable {

    private Integer queryCost;
    private Double latitude;
    private Double longitude;
    private String resolvedAddress;
    private String address;
    private String timezone;
    private Double tzoffset;
    private String description;
    private List<DailyForecast> days = null;
    //    private List<String> stations;
    private final static long serialVersionUID = -5779468242832757870L;
}
