package com.github.gadzooks.weather.converters;

import com.github.gadzooks.weather.commands.RegionCommand;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegionCommandToRegionJpaConverter implements Converter<RegionCommand, RegionJpa> {

    //NOTE : Keep these thread safe as per Spring Converter recommendation
    @Synchronized
    @Override
    public RegionJpa convert(RegionCommand r) {
        if (r == null) {
            return null;
        }
        return RegionJpa.builder().id(r.getId()).
                isActive(r.getIsActive()).description(r.getDescription()).
                name(r.getName()).searchKey(r.getSearchKey()).description(r.getDescription()).build();
    }

}
