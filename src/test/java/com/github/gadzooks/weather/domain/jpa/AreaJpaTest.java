package com.github.gadzooks.weather.domain.jpa;

import com.github.gadzooks.weather.repository.jpa.AreaJpaRepository;
import com.github.gadzooks.weather.repository.jpa.RegionJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@DataJpaTest
class AreaJpaTest {
    @Autowired private AreaJpaRepository areaJpaRepository;
    @Autowired private RegionJpaRepository regionJpaRepository;

    @Test
    void verifyAreaIdStoredInRegionId() {
        RegionJpa region = new RegionJpa();
        AreaJpa area = new AreaJpa();

        area.addRegionJpa(region);

        regionJpaRepository.save(region);
        areaJpaRepository.save(area);

        assertThat(area.regionJpasSize(), equalTo(1));
        assertThat(region.getAreaJpa(), equalTo(area));
        assertThat(region.getAreaJpa().getId(), equalTo(area.getId()));
    }

}