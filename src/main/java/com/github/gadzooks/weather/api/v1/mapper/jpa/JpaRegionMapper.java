package com.github.gadzooks.weather.api.v1.mapper.jpa;

import com.github.gadzooks.weather.api.v1.mapper.EntityMapper;
import com.github.gadzooks.weather.api.v1.model.RegionDTO;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface JpaRegionMapper extends EntityMapper<RegionDTO, RegionJpa> {
    @Override
    RegionDTO toDto(RegionJpa e);

    @Override
    RegionJpa toEntity(RegionDTO d);

    @Override
    List<RegionDTO> toDto(List<RegionJpa> eList);

    @Override
    List<RegionJpa> toEntity(List<RegionDTO> dList);
}
