package com.github.gadzooks.weather.mapstruct.mappers;

import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.mapstruct.dtos.LocationSlimDto;
import com.github.gadzooks.weather.mapstruct.dtos.RegionDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = {RegionJpaMapperImpl.class, LocationJpaMapperImpl.class})
class RegionJpaMapperTest {

    @Autowired
    private RegionJpaMapper regionJpaMapper;

    @Test
    void toDto() {
        RegionDto expected = RegionDto.builder().id(1L).name("r1").description("d1").searchKey("s1")
                .isActive(false)
                .build();

        LocationSlimDto expectedLocation = LocationSlimDto.builder().id(10L).name("l1").subRegion("s1").latitude(10F).
                longitude(20F).build();
        expected.setLocations(Set.of(expectedLocation));

        RegionJpa jpa = RegionJpa.builder().id(1L).name("r1").description("d1").searchKey("s1")
                .isActive(false)
                .build();
        LocationJpa locationJpa = LocationJpa.builder().name("l1").subRegion("s1").latitude(10F).
                longitude(20F).build();
        jpa.addLocation(locationJpa);

        RegionDto actual = regionJpaMapper.toDto(jpa);

        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getSearchKey(), actual.getSearchKey());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getIsActive(), actual.getIsActive());
        Assertions.assertEquals(expected.getId(), actual.getId());

        assertEquals(1, actual.getLocations().size());

        LocationSlimDto actualLocation = actual.getLocations().stream().findAny().get();

        Assertions.assertEquals(expectedLocation.getName(), actualLocation.getName());
        Assertions.assertEquals(expectedLocation.getSubRegion(), actualLocation.getSubRegion());
        Assertions.assertEquals(expectedLocation.getLatitude(), actualLocation.getLatitude());
        Assertions.assertEquals(expectedLocation.getLongitude(), actualLocation.getLongitude());
    }

    @Test
    void toEntity() {
        RegionJpa expected = RegionJpa.builder().id(1L).name("r1").description("d1").searchKey("s1")
                .isActive(false)
                .build();

        RegionDto dto = RegionDto.builder().id(1L).name("r1").description("d1").searchKey("s1")
                .isActive(false)
                .build();

        RegionJpa actual = regionJpaMapper.toEntity(dto);

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getSearchKey(), actual.getSearchKey());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getIsActive(), actual.getIsActive());
    }
}