package com.github.gadzooks.weather.converters;

import com.github.gadzooks.weather.commands.LocationCommand;
import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationJpaCommandToLocationConverter implements Converter<LocationJpa, LocationCommand> {

    //NOTE : Keep these thread safe as per Spring Converter recommendation
    @Synchronized
    @Override
    public LocationCommand convert(LocationJpa r) {
        if (r == null) {
            return null;
        }
        return LocationCommand.builder().id(r.getId()).
                description(r.getDescription()).
                latitude(r.getLatitude()).longitude(r.getLongitude()).
                name(r.getName()).build();
    }

}
