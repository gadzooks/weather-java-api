package com.github.gadzooks.weather.api.v1.mapper.inmemory;

import com.github.gadzooks.weather.api.v1.mapper.EntityMapper;
import com.github.gadzooks.weather.api.v1.model.LocationDTO;
import com.github.gadzooks.weather.domain.inmemory.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring") //Creates a Spring Bean automatically
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Override
    LocationDTO toDto(Location entity);

    @Override
    Location toEntity(LocationDTO dto);

    @Override
    List<LocationDTO> toDto(List<Location> eList);

    @Override
    List<Location> toEntity(List<LocationDTO> dList);

}
