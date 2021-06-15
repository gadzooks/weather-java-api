package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.domain.jpa.AreaJpa;
import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import com.github.gadzooks.weather.mapstruct.mappers.LocationJpaMapper;
import com.github.gadzooks.weather.mapstruct.mappers.RegionJpaMapper;
import com.github.gadzooks.weather.repository.jpa.RegionJpaRepository;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JpaRegionServiceImplTest {

    @Mock
    private RegionJpaRepository repository;

    @InjectMocks
    private JpaRegionServiceImpl service;

    @Test
    void findAll() {
        //given
        AreaJpa area = AreaJpa.builder().name("area1").build();
        RegionJpa region = RegionJpa.builder().name("region1").build().setAreaJpa(area);

        //when
        when(repository.findAll()).thenReturn(List.of(region));
        List<RegionJpa> results = service.findAll();

        //then
        assertEquals(List.of(region), results);
        verify(repository, times(1)).findAll();
    }

    @Test
    void save() {
        //given
        AreaJpa area = AreaJpa.builder().name("area1").build();
        RegionJpa region = RegionJpa.builder().name("region1").build().setAreaJpa(area);

        //when
        when(repository.save(region)).thenReturn(region);
        RegionJpa savedRegion = service.save(region);

        //then
        assertEquals(savedRegion, region);
        verify(repository, times(1)).save(region);

    }

    @Test
    void getById() {
        //given
        Long regionId = 35L;
        AreaJpa area = AreaJpa.builder().name("area1").build();
        RegionJpa region = RegionJpa.builder().name("region1").build().setAreaJpa(area);

        //when
        when(repository.findById(regionId)).thenReturn(java.util.Optional.ofNullable(region));
        RegionJpa results = service.getById(regionId);

        //then
        assertEquals(region, results);
        verify(repository, times(1)).findById(regionId);
    }

    @Test
    void getById_NotFound() {
        //given
        Long regionId = 35L;

        //when
        when(repository.findById(regionId)).thenReturn(java.util.Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getById(regionId);
        });

        //then
        verify(repository, times(1)).findById(regionId);
    }

    @Test
    void patch() {
        //given
        Long regionId = 33L;
        LocationJpa originalLocation = LocationJpa.builder().name("originalLoc").build();
        RegionJpa originalRegion = RegionJpa.builder().name("originalRegion").description("originalDesc").build().
                addLocation(originalLocation);

        AreaJpa area = AreaJpa.builder().name("newArea").build();
        RegionJpa region = RegionJpa.builder().name("newRegion").build().setAreaJpa(area);

        //when
        when(repository.findById(regionId)).thenReturn(java.util.Optional.ofNullable(originalRegion));
        // NOTE simulate this code that is being mocked out : return repository.save(region);
        when(repository.save(any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);

        RegionJpa patchedRegion = service.patch(regionId, region);

        //then
        assertEquals("newArea", patchedRegion.getAreaJpa().getName(), "area will get updated");
        assertEquals("newRegion", patchedRegion.getName(), "region name will get updated");
        assertEquals("originalDesc", patchedRegion.getDescription(), "region desc will NOT get updated");
        ImmutableSet<LocationJpa> expectedLocations = ImmutableSet.of(originalLocation);
        assertEquals(expectedLocations, patchedRegion.getLocationJpas(), "location will not be updated");
        verify(repository, times(1)).findById(regionId);
        verify(repository, times(1)).save(patchedRegion);
    }

    @Test
    void delete() {
        //given
        Long regionId = 35L;
        AreaJpa area = AreaJpa.builder().name("area1").build();
        RegionJpa region = RegionJpa.builder().name("region1").build().setAreaJpa(area);

        //when
        when(repository.findById(regionId)).thenReturn(java.util.Optional.ofNullable(region));

        service.delete(regionId);

        //then
        verify(repository, times(1)).findById(regionId);
        verify(repository, times(1)).delete(region);
    }

    @Test
    void findAllActive() {
        //given
        AreaJpa area = AreaJpa.builder().name("area1").build();
        RegionJpa region = RegionJpa.builder().name("region1").build().setAreaJpa(area);

        //when
        when(repository.findAllByIsActive(true)).thenReturn(List.of(region));
        List<RegionJpa> results = service.findAllActive();

        //then
        assertEquals(List.of(region), results);
        verify(repository, times(1)).findAllByIsActive(true);
    }

    @Test
    void findAllByIdsIn() {
        //given
        List<Long> ids = List.of(1L, 2L, 3L);
        AreaJpa area = AreaJpa.builder().name("area1").build();
        RegionJpa region = RegionJpa.builder().name("region1").build().setAreaJpa(area);

        //when
        when(repository.findAllByIdIn(ids)).thenReturn(List.of(region));
        List<RegionJpa> results = service.findAllByIdsIn(ids);

        //then
        assertEquals(List.of(region), results);
        verify(repository, times(1)).findAllByIdIn(ids);
    }


}