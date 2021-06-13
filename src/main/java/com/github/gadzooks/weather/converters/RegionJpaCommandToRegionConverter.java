package com.github.gadzooks.weather.converters;

import com.github.gadzooks.weather.commands.RegionCommand;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegionJpaCommandToRegionConverter implements Converter<RegionJpa, RegionCommand> {

    private final LocationJpaCommandToLocationConverter toLocationConverter;
    private final AreaJpaCommandToAreaConverter toAreaConverter;

    public RegionJpaCommandToRegionConverter(LocationJpaCommandToLocationConverter toLocationConverter, AreaJpaCommandToAreaConverter toAreaConverter) {
        this.toLocationConverter = toLocationConverter;
        this.toAreaConverter = toAreaConverter;
    }

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

    //NOTE: Keep these thread safe as per Spring Converter recommendation
    @Synchronized
    public List<RegionCommand> convertAllRegionsIncludingLocation(List<RegionJpa> regionJpas) {
        List<RegionCommand> list = new ArrayList<>();
        regionJpas.forEach(jpa -> {
            RegionCommand rc = convert(jpa);
            assert rc != null;
            rc.setLocationCommandSet(jpa.getLocationJpas().stream().map(toLocationConverter::convert).
                    collect(Collectors.toSet()));
            list.add(rc);
        });

        return list;
    }

    //NOTE: Keep these thread safe as per Spring Converter recommendation
    @Synchronized
    public RegionCommand convertRegionIncludingLocationAndArea(RegionJpa region) {
        RegionCommand rc = convert(region);
        assert rc != null;
        if(region.getLocationJpas() != null) {
            rc.setLocationCommandSet(region.getLocationJpas().stream().map(toLocationConverter::convert).
                    collect(Collectors.toSet()));
        }

        if(region.getAreaJpa() != null) {
            rc.setAreaCommand(toAreaConverter.convert(region.getAreaJpa()));
        }

        return rc;
    }

}
