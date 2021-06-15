package com.github.gadzooks.weather.mapstruct.mappers;

import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.mapstruct.dtos.RegionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {LocationJpaMapper.class})
public interface RegionJpaMapper extends EntityMapper<RegionDto, RegionJpa> {
//    @Mapping(target = "id", source = "id")
//    @Mapping(target = "name", source = "name")
//    @Mapping(target = "searchKey", source = "searchKey")
//    @Mapping(target = "description", source = "description")
//    @Mapping(target = "isActive", source = "isActive")
//    @Mapping(target = "locations", source = "locationJpas")
    @Override
    RegionDto toDto(final RegionJpa regionJpa);

    @Override
    //    @Mapping(source = "locations", target = "locationJpas")
    // This mapping will not work since locatinJpas has a private settter method
    RegionJpa toEntity(final RegionDto regionDto);

    @Override
    List<RegionDto> toDto(List<RegionJpa> eList);
    @Override
    List<RegionJpa> toEntity(List<RegionDto> dList);

}
