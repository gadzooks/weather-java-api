package com.github.gadzooks.weather.api.v1.mapper.mongo;

import com.github.gadzooks.weather.api.v1.mapper.EntityMapper;
import com.github.gadzooks.weather.api.v1.model.RegionDTO;
import com.github.gadzooks.weather.domain.mongo.RegionDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MongoRegionMapper extends EntityMapper<RegionDTO, RegionDocument> {

    MongoRegionMapper INSTANCE = Mappers.getMapper(MongoRegionMapper.class);

    @Override
    RegionDTO toDto(RegionDocument entity);

    @Override
    RegionDocument toEntity(RegionDTO dto);

    @Override
    List<RegionDTO> toDto(List<RegionDocument> eList);

    @Override
    List<RegionDocument> toEntity(List<RegionDTO> dList);

}
