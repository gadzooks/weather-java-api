package com.github.gadzooks.weather.api.v1.mapper.jpa;

import com.github.gadzooks.weather.api.v1.mapper.EntityMapper;
import com.github.gadzooks.weather.api.v1.model.LocationDTO;
import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface JpaLocationMapper extends EntityMapper<LocationDTO, LocationJpa> {
    @Override
    LocationDTO toDto(LocationJpa e);

    @Override
    LocationJpa toEntity(LocationDTO d);

    @Override
    List<LocationDTO> toDto(List<LocationJpa> eList);

    @Override
    List<LocationJpa> toEntity(List<LocationDTO> dList);
}
