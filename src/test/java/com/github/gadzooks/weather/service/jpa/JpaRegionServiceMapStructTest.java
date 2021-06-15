package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.mapstruct.dtos.RegionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class JpaRegionServiceMapStructTest {

    @Autowired
    private JpaRegionServiceImpl jpaRegionService;


    @Test
    void findAllDtos() {
        //given
        RegionJpa r1 = RegionJpa.builder().name("r1").description("desc1").build();
        LocationJpa l1 = LocationJpa.builder().name("l1").subRegion("l1-subregion").
                latitude(1.234F).longitude(333.22F).description("l1-description").build();
        LocationJpa l2 = LocationJpa.builder().name("l2").subRegion("l2-subregion").
                latitude(2.234F).longitude(322.22F).description("l2-description").build();

        r1.addLocation(l1);
        r1.addLocation(l2);

        RegionJpa savedRegion = jpaRegionService.save(r1);

        //when
        List<RegionDto> allRegions = jpaRegionService.findAllDtos();

        //then
        RegionDto actual = allRegions.stream().
                filter(region -> region.getId() == savedRegion.getId()).
                findAny().orElse(null);

        assertNotNull("should not be null", actual);
        assertEquals("verify its the same region", r1.getName(),actual.getName());
        assertEquals("verify its the same region", r1.getDescription(),actual.getDescription());

        assertTrue("includes 1st location", actual.getLocations().contains(l1));
        assertTrue("includes 2st location", actual.getLocations().contains(l2));

    }
}
