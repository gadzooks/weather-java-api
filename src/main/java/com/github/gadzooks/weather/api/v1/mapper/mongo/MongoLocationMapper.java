package com.github.gadzooks.weather.api.v1.mapper.mongo;

import com.github.gadzooks.weather.api.v1.mapper.EntityMapper;
import com.github.gadzooks.weather.api.v1.model.LocationDTO;
import com.github.gadzooks.weather.domain.mongo.LocationDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MongoLocationMapper extends EntityMapper<LocationDTO, LocationDocument> {

    MongoLocationMapper INSTANCE = Mappers.getMapper(MongoLocationMapper.class);

    @Override
    LocationDTO toDto(LocationDocument entity);

    @Override
    LocationDocument toEntity(LocationDTO dto);

    @Override
    List<LocationDTO> toDto(List<LocationDocument> eList);

    @Override
    List<LocationDocument> toEntity(List<LocationDTO> dList);

}
