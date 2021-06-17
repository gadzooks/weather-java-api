package com.github.gadzooks.weather.api.v1.mapper;

import com.github.gadzooks.weather.api.v1.model.DailyForecastDTO;
import com.github.gadzooks.weather.domain.visualcrossing.DailyForecast;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DailyForecastMapper extends EntityMapper<DailyForecastDTO, DailyForecast>{

    DailyForecastMapper INSTANCE = Mappers.getMapper(DailyForecastMapper.class);

    @Override
    DailyForecastDTO toDto(DailyForecast entity);
    @Override
    DailyForecast toEntity(DailyForecastDTO dto);

    @Override
    List<DailyForecastDTO> toDto(List<DailyForecast> eList);
    @Override
    List<DailyForecast> toEntity(List<DailyForecastDTO> dList);

}
