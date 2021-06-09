package com.github.gadzooks.weather.domain.jpa;

import com.github.gadzooks.weather.repository.jpa.LocationJpaRepository;
import com.github.gadzooks.weather.repository.jpa.RegionJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class RegionJpaTest {

    @Autowired private RegionJpaRepository regionJpaRepository;
    @Autowired private LocationJpaRepository locationJpaRepository;

    @Test
    void verifyRegionLocationSavedCorrectly() {
        RegionJpa r1 = new RegionJpa(); // r1 includes l1 and l2
        RegionJpa r2 = new RegionJpa(); // r2 includes l2

        LocationJpa l1 = new LocationJpa();
        l1.setName("l1");

        LocationJpa l2 = new LocationJpa();
        l2.setName("l2");

        // Need to save earlier so that both can be added to RegionJpa
        // saving will generate Id for each
        locationJpaRepository.save(l1);
        locationJpaRepository.save(l2);

        r1.addLocation(l1);
        r1.addLocation(l2);
        r2.addLocation(l2);

        regionJpaRepository.save(r1);
        regionJpaRepository.save(r2);

        // add saved Regions to location and then save locations again
        l1.addRegion(r1);
        l2.addRegion(r2);
        l2.addRegion(r1);
        locationJpaRepository.save(l1);
        locationJpaRepository.save(l2);

        assertThat(regionJpaRepository.count(), equalTo(2L));
        assertThat(locationJpaRepository.count(), equalTo(2L));

        Optional<RegionJpa> optionalFromDbR1 = regionJpaRepository.findById(r1.getId());
        Optional<RegionJpa> optionalFromDbR2 = regionJpaRepository.findById(r2.getId());
        assertNotNull(optionalFromDbR1.get());
        assertNotNull(optionalFromDbR2.get());

        RegionJpa fromDbR1 = optionalFromDbR1.get();
        RegionJpa fromDbR2 = optionalFromDbR2.get();

        assertThat(fromDbR1.getLocationJpas(), hasItem(l1));
        assertThat(fromDbR1.getLocationJpas(), hasItem(l2));

        assertThat(fromDbR2.getLocationJpas(), hasItem(l2));

        Optional<LocationJpa> optionalFromDbl1 = locationJpaRepository.findById(l1.getId());
        Optional<LocationJpa> optionalFromDbl2 = locationJpaRepository.findById(l2.getId());
        assertNotNull(optionalFromDbl1.get());
        assertNotNull(optionalFromDbl2.get());

        LocationJpa fromDbL1 = optionalFromDbl1.get();
        LocationJpa fromDbL2 = optionalFromDbl2.get();

        assertThat(fromDbL1.getRegionJpas(), hasItem(r1));
        assertThat(fromDbL2.getRegionJpas(), hasItem(r1));

        assertThat(fromDbL2.getRegionJpas(), hasItem(r2));
    }

}