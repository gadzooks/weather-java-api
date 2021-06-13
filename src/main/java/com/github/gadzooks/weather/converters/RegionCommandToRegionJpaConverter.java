package com.github.gadzooks.weather.converters;

import com.github.gadzooks.weather.commands.RegionCommand;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RegionCommandToRegionJpaConverter implements Converter<RegionCommand, RegionJpa> {

    private final LocationCommandToLocationJpaConverter toLocationJpaConverter;

    public RegionCommandToRegionJpaConverter(LocationCommandToLocationJpaConverter toLocationJpaConverter) {
        this.toLocationJpaConverter = toLocationJpaConverter;
    }

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

    //NOTE: Keep these thread safe as per Spring Converter recommendation
    @Synchronized
    public RegionJpa convertRegionIncludingLocation(RegionCommand region) {
        RegionJpa rc = convert(region);
        assert rc != null;
        rc.addAllLocations(region.getLocationCommandSet().stream().map(toLocationJpaConverter::convert).
                collect(Collectors.toSet()));

        return rc;
    }

}
