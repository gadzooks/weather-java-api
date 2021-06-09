package com.github.gadzooks.weather.service.inmemory;

import com.github.gadzooks.weather.domain.inmemory.Region;
import com.github.gadzooks.weather.repository.inmemory.RegionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class RegionServiceImplTest {
    private AutoCloseable closeable;
    private RegionService regionService;

    @Mock
    RegionRepository regionRepository;

    @BeforeEach
    void setUp() {
        //without openMocks the test fails as the mock object is not set up.
        closeable = MockitoAnnotations.openMocks(this);
        regionService = new RegionServiceImpl(regionRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void save() {
        //Given
        Region region = new Region();
        region.setSearchKey("search");
        region.setDescription("desc");
        region.setName("name");

        //When we call service.save
        when(regionService.save(region)).thenReturn(region);
        regionService.save(region);

        //Then the service will call repo.save exactly 1 time
        verify(regionRepository, times(1)).save(region);
    }
}