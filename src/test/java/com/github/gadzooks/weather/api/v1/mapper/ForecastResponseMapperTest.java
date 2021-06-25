package com.github.gadzooks.weather.api.v1.mapper;

import com.github.gadzooks.weather.api.v1.mapper.forecast.ForecastResponseMapper;
import com.github.gadzooks.weather.api.v1.model.ForecastResponseDTO;
import com.github.gadzooks.weather.domain.visualcrossing.ForecastResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForecastResponseMapperTest {

    ForecastResponseMapper mapper = ForecastResponseMapper.INSTANCE;

    @Test
    void toDtoTest() {
        ForecastResponse entity = new ForecastResponse();
        entity.setDescription("desc");
        entity.setLatitude(123.456D);
        entity.setLongitude(444.444D);

        ForecastResponseDTO dto = mapper.toDto(entity);

        assertEquals("desc", dto.getDescription());
        assertEquals(123.456D, dto.getLatitude());
        assertEquals(444.444D, dto.getLongitude());

    }
}