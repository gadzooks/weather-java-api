package com.github.gadzooks.weather.api.v1.mapper;

import com.github.gadzooks.weather.api.v1.mapper.forecast.DailyForecastMapper;
import com.github.gadzooks.weather.api.v1.model.DailyForecastDTO;
import com.github.gadzooks.weather.domain.visualcrossing.DailyForecast;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DailyForecastMapperTest {

    DailyForecastMapper mapper = DailyForecastMapper.INSTANCE;

    @Test
    void toDtoTest() {
        DailyForecast entity = new DailyForecast();
        entity.setDescription("desc");
        entity.setCloudcover(1D);
        entity.setConditions("conditions");
        entity.setDatetime("10/10/2020");
        entity.setDatetimeEpoch(123);

        DailyForecastDTO dto = mapper.toDto(entity);

        assertEquals("desc", dto.getDescription());
        assertEquals(1D, dto.getCloudcover());
        assertEquals("conditions", dto.getConditions());
        assertEquals("10/10/2020", dto.getDatetime());
        assertEquals(123, dto.getDatetimeEpoch());
    }

    @Test
    void toEntityTest() {
        DailyForecastDTO dto = new DailyForecastDTO();
        dto.setCloudcover(111D);
        dto.setDatetime("12/12/1999");
        dto.setConditions("conditions");
        dto.setMoonphase(3D);
        dto.setIcon("cloudy");
        dto.setSunrise("super early");

        DailyForecast entity = mapper.toEntity(dto);

        assertEquals(111D, dto.getCloudcover());
        assertEquals("12/12/1999", dto.getDatetime());
        assertEquals("conditions", dto.getConditions());
        assertEquals(3D, dto.getMoonphase());
        assertEquals("cloudy", dto.getIcon());
        assertEquals("super early", dto.getSunrise());
    }

    @Test
    void toDtoListTest() {
        DailyForecast e1 = new DailyForecast();
        e1.setDescription("e1");
        DailyForecast e2 = new DailyForecast();
        e2.setDescription("e2");
        DailyForecast e3 = new DailyForecast();
        e3.setDescription("e3");

        List<DailyForecast> list = List.of(e1,e2,e3);

        List<DailyForecastDTO> results = mapper.toDto(list);

        assertEquals( 3, results.size());
        assertEquals("e1", results.get(0).getDescription());
        assertEquals("e2", results.get(1).getDescription());
        assertEquals("e3", results.get(2).getDescription());


    }
}