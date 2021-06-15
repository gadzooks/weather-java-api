package com.github.gadzooks.weather.mapstruct.mappers;

import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import com.github.gadzooks.weather.mapstruct.dtos.LocationSlimDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RegionJpaMapper.class})
public interface LocationJpaMapper extends EntityMapper<LocationSlimDto, LocationJpa> {
    @Override
    LocationSlimDto toDto(final LocationJpa regionJpa);
    @Override
    LocationJpa toEntity(final LocationSlimDto regionDto);

    @Override
    List<LocationSlimDto> toDto(List<LocationJpa> eList);
    @Override
    List<LocationJpa> toEntity(List<LocationSlimDto> dList);

}
