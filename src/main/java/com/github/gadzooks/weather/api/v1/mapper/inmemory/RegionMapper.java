package com.github.gadzooks.weather.api.v1.mapper.inmemory;

import com.github.gadzooks.weather.api.v1.mapper.EntityMapper;
import com.github.gadzooks.weather.api.v1.model.RegionDTO;
import com.github.gadzooks.weather.domain.inmemory.Region;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {

    RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

    @Override
    RegionDTO toDto(Region entity);

    @Override
    Region toEntity(RegionDTO dto);

    @Override
    List<RegionDTO> toDto(List<Region> eList);

    @Override
    List<Region> toEntity(List<RegionDTO> dList);

}
