package com.github.gadzooks.weather.domain.jpa;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class AreaJpaTest {
    /*
    @Autowired private AreaJpaRepository areaJpaRepository;
    @Autowired private RegionJpaRepository regionJpaRepository;

    @Test
    @DirtiesContext
    void verifyAreaIdStoredInRegionId() {
        RegionJpa region = new RegionJpa();
        AreaJpa area = new AreaJpa();

        // NOTE : had to add region to area AND area to region to set up bi-directional column data
        area.getRegionJpas().add(region);
        region.setAreaJpa(area);

        regionJpaRepository.save(region);
        areaJpaRepository.save(area);

        assertThat(area.getRegionJpas().size(),equalTo(1));
        assertThat(region.getAreaJpa(), equalTo(area));
        assertThat(region.getAreaJpa().getId(), equalTo(area.getId()));
    }

     */

}