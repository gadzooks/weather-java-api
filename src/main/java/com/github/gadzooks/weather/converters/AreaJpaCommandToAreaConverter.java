package com.github.gadzooks.weather.converters;

import com.github.gadzooks.weather.commands.AreaCommand;
import com.github.gadzooks.weather.domain.jpa.AreaJpa;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AreaJpaCommandToAreaConverter implements Converter<AreaJpa, AreaCommand> {

    @Synchronized
    @Override
    public AreaCommand convert(AreaJpa r) {
        if (r == null) {
            return null;
        }
        return AreaCommand.builder().id(r.getId()).
                description(r.getDescription()).
                name(r.getName()).build();
    }

}
