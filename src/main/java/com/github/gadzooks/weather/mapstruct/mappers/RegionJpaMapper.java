package com.github.gadzooks.weather.mapstruct.mappers;

import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.mapstruct.dtos.LocationSlimDto;
import com.github.gadzooks.weather.mapstruct.dtos.RegionDto;
import org.mapstruct.Mapper;

@Mapper
public interface RegionJpaMapper {
    LocationSlimDto locationJpaToLocationSlimDtoMapper(LocationJpa locationJpa);

    LocationJpa locationSlimDtoToLocationJpaMapper(LocationSlimDto locationSlimDto);

    RegionDto regionJpaToRegionDtoMapper(RegionJpa regionJpa);

    RegionJpa regionDtoToRegionJpaMapper(RegionDto regionDto);
}
