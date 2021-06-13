package com.github.gadzooks.weather.converters;

import com.github.gadzooks.weather.commands.LocationCommand;
import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationCommandToLocationJpaConverter implements Converter<LocationCommand, LocationJpa> {

    //NOTE : Keep these thread safe as per Spring Converter recommendation
    @Synchronized
    @Override
    public LocationJpa convert(LocationCommand locationCommand) {
        if (locationCommand == null) {
            return null;
        }
        return LocationJpa.builder().id(locationCommand.getId()).
                description(locationCommand.getDescription()).
                latitude(locationCommand.getLatitude()).longitude(locationCommand.getLongitude()).
                name(locationCommand.getName()).build();
    }

}
