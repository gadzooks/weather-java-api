package com.github.gadzooks.weather.service.inmemory;

import com.github.gadzooks.weather.domain.inmemory.Region;
import com.github.gadzooks.weather.repository.inmemory.RegionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    @Mock
    RegionRepository regionRepository;
    @InjectMocks
    private RegionServiceImpl regionService;

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