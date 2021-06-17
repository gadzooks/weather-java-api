package com.github.gadzooks.weather.api.v1.mapper;

import java.util.List;

public interface EntityMapper<DTO, ENTITY> {
    DTO toDto(ENTITY e);

    ENTITY toEntity(DTO d);

    List<DTO> toDto(List<ENTITY> eList);

    List<ENTITY> toEntity(List<DTO> dList);

}