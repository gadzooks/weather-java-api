package com.github.gadzooks.weather.bootstrap.inmemory;

import com.github.gadzooks.weather.dto.Region;
import com.github.gadzooks.weather.repository.inmemory.RegionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

//FIXME : dont load other dependencies like Embedded mongo for this test
@SpringBootTest
class LoadRegionsDatabaseTest {

    @Autowired
    private RegionRepository regionRepository;

    @Test
    void getRegions() {
        System.out.println(regionRepository.getRegions());
        assertThat(regionRepository.getRegions().size(), greaterThan(0));

        Region firstRegion = regionRepository.getRegions().get(0);
        assertThat(firstRegion, is(notNullValue()));
        assertThat(firstRegion.getDescription(), is(not(Matchers.emptyString())));
        assertThat(firstRegion.getId(), is(not(Matchers.emptyString())));
        assertThat(firstRegion.getSearchKey(), is(not(Matchers.emptyString())));
    }
}