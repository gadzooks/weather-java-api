package com.github.gadzooks.weather.bootstrap.inmemory;

import org.springframework.boot.test.context.SpringBootTest;

//FIXME : dont load other dependencies like Embedded mongo for this test
@SpringBootTest
class LoadRegionsDatabaseTest {

    /*
    @Autowired
    private RegionRepository regionRepository;

    @Test
    void getRegions() {
        System.out.println(regionRepository.findAll());
        assertThat(regionRepository.size(), greaterThan(0));

        Region firstRegion = regionRepository.findAll().get(0);
        assertThat(firstRegion, is(notNullValue()));
        assertThat(firstRegion.getDescription(), is(not(Matchers.emptyString())));
        assertThat(firstRegion.getName(), is(not(Matchers.emptyString())));
        assertThat(firstRegion.getSearchKey(), is(not(Matchers.emptyString())));
    }

     */
}