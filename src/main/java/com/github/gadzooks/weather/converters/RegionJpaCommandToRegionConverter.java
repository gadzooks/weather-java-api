package com.github.gadzooks.weather.converters;

import com.github.gadzooks.weather.commands.RegionCommand;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegionJpaCommandToRegionConverter implements Converter<RegionJpa, RegionCommand> {

    //NOTE: Keep these thread safe as per Spring Converter recommendation
    @Synchronized
    @Override
    public RegionCommand convert(RegionJpa r) {
        if (r == null) {
            return null;
        }
        return RegionCommand.builder().id(r.getId()).
                isActive(r.getIsActive()).description(r.getDescription()).
                name(r.getName()).searchKey(r.getSearchKey()).description(r.getDescription()).build();
    }

}
