package com.github.gadzooks.weather.api.v1.mapper;

import com.github.gadzooks.weather.api.v1.model.ForecastResponseDTO;
import com.github.gadzooks.weather.domain.visualcrossing.ForecastResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ForecastResponseMapper extends EntityMapper<ForecastResponseDTO, ForecastResponse>{

    ForecastResponseMapper INSTANCE = Mappers.getMapper(ForecastResponseMapper.class);

    @Override
    ForecastResponseDTO toDto(ForecastResponse entity);
    @Override
    ForecastResponse toEntity(ForecastResponseDTO dto);

    @Override
    List<ForecastResponseDTO> toDto(List<ForecastResponse> eList);
    @Override
    List<ForecastResponse> toEntity(List<ForecastResponseDTO> dList);

}
