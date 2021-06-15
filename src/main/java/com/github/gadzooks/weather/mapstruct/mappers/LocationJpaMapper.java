package com.github.gadzooks.weather.mapstruct.mappers;

import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.mapstruct.EntityMapper;
import com.github.gadzooks.weather.mapstruct.dtos.RegionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionJpaMapper extends EntityMapper<RegionDto, RegionJpa> {
    @Mapping(target = "locations", source = "locationJpas" )
    RegionDto toDto(final RegionJpa regionJpa);

    @Mapping(target = "locationJpas", source = "locations")
    RegionJpa toEntity(final RegionDto regionDto);

    List<RegionDto> toDto(List<RegionJpa> eList);
    List<RegionJpa> toEntity(List<RegionDto> dList);

}
