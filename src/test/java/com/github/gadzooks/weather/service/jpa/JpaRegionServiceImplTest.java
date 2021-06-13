package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.commands.AreaCommand;
import com.github.gadzooks.weather.commands.LocationCommand;
import com.github.gadzooks.weather.commands.RegionCommand;
import com.github.gadzooks.weather.converters.*;
import com.github.gadzooks.weather.domain.jpa.AreaJpa;
import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import com.github.gadzooks.weather.repository.jpa.RegionJpaRepository;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    private AreaCommandToAreaJpaConverter toAreaJpaConverter;
    private AreaJpaCommandToAreaConverter toAreaConverter;
    private LocationJpaCommandToLocationConverter toLocationConverter;
    private RegionJpaCommandToRegionConverter toRegionConverter ;
    private LocationCommandToLocationJpaConverter toLocationJpaConverter ;
    private RegionCommandToRegionJpaConverter toRegionJpaConverter ;

    private JpaRegionServiceImpl service ;

    @BeforeEach
    void setUp() {
        toAreaJpaConverter = new AreaCommandToAreaJpaConverter();
        toAreaConverter = new AreaJpaCommandToAreaConverter();
        toLocationConverter = new LocationJpaCommandToLocationConverter();
        toRegionConverter = new RegionJpaCommandToRegionConverter(toLocationConverter, toAreaConverter);
        toLocationJpaConverter = new LocationCommandToLocationJpaConverter();
        toRegionJpaConverter = new RegionCommandToRegionJpaConverter(toLocationJpaConverter);

        service = new JpaRegionServiceImpl(repository,toRegionConverter,toRegionJpaConverter,toLocationConverter,
                toLocationJpaConverter, toAreaJpaConverter);
    }

    @Test
    void findAll() {
        //given
        AreaJpa area = AreaJpa.builder().name("area1").id(1L).build();
        RegionJpa region = RegionJpa.builder().id(1L).name("region1").build().setAreaJpa(area);
        RegionCommand regionCommand = toRegionConverter.convertRegionIncludingLocationAndArea(region);

        //when
        when(repository.findAll()).thenReturn(List.of(region));
        List<RegionCommand> results = service.findAll();

        //then
        assertEquals(List.of(regionCommand), results);
        verify(repository, times(1)).findAll();
    }

    @Test
    void save() {
        //given
        AreaJpa area = AreaJpa.builder().name("area1").build();
        RegionJpa region = RegionJpa.builder().name("region1").build().setAreaJpa(area);
        RegionCommand rc = toRegionConverter.convert(region);

        //when
        when(repository.save(any())).thenReturn(region);
        RegionCommand savedRegion = service.save(rc);

        //then
        assertEquals(savedRegion, rc);
        verify(repository, times(1)).save(any());

    }

    @Test
    void getById() {
        //given
        Long regionId = 35L;
        AreaJpa area = AreaJpa.builder().name("area1").build();
        RegionJpa region = RegionJpa.builder().name("region1").build().setAreaJpa(area);
        RegionCommand rc = toRegionConverter.convert(region);

        //when
        when(repository.findById(regionId)).thenReturn(java.util.Optional.ofNullable(region));
        RegionCommand results = service.getById(regionId);

        //then
        assertEquals(rc, results);
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
        LocationCommand originalLocationCommand = LocationCommand.builder().name("originalLoc").build();
        RegionJpa originalRegion = RegionJpa.builder().name("originalRegion").description("originalDesc").build().
                addLocation(originalLocation);
        AreaJpa area = AreaJpa.builder().name("newArea").build();
        originalRegion.setAreaJpa(area);

        RegionJpa region = RegionJpa.builder().name("newRegion").build().setAreaJpa(area);
        RegionCommand rc = toRegionConverter.convertRegionIncludingLocationAndArea(region);
//        AreaCommand ac = toAreaConverter.convert(area);
//        rc.setAreaCommand(ac);

        RegionJpa regionToBeSaved = RegionJpa.builder().name("newRegion").description("originalDesc").build();

        //when
        when(repository.findById(regionId)).thenReturn(java.util.Optional.of(originalRegion));
        // NOTE simulate this code that is being mocked out : return repository.save(region);
        when(repository.save(any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);

        RegionCommand patchedRegion = service.patch(regionId, rc);

        //then
        assertEquals("newArea", patchedRegion.getAreaCommand().getName(), "area will get updated");
        assertEquals("newRegion", patchedRegion.getName(), "region name will get updated");
        assertEquals("originalDesc", patchedRegion.getDescription(), "region desc will NOT get updated");
        ImmutableSet<LocationCommand> expectedLocations = ImmutableSet.of(originalLocationCommand);
        assertEquals(expectedLocations, patchedRegion.getLocationCommandSet(), "location will not be updated");

        ArgumentCaptor<RegionJpa> regionToBePatched = ArgumentCaptor.forClass(RegionJpa.class);
        verify(repository).findById(regionId);
        verify(repository).save(regionToBePatched.capture());
        assertEquals("newRegion", regionToBeSaved.getName());
        assertEquals("originalDesc", regionToBeSaved.getDescription());
//        assertEquals("newArea", regionToBeSaved.getAreaJpa().getName());
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
        AreaCommand ac = toAreaConverter.convert(area);
        RegionCommand rc = toRegionConverter.convert(region);
        rc.setAreaCommand(ac);

        //when
        when(repository.findAllByIsActive(true)).thenReturn(List.of(region));
        List<RegionCommand> results = service.findAllActive();

        //then
        assertEquals(List.of(rc), results);
        verify(repository, times(1)).findAllByIsActive(true);
    }

    @Test
    void findAllByIdsIn() {
        //given
        List<Long> ids = List.of(1L, 2L, 3L);
        AreaJpa area = AreaJpa.builder().name("area1").build();
        RegionJpa region = RegionJpa.builder().name("region1").build().setAreaJpa(area);
        AreaCommand ac = toAreaConverter.convert(area);
        RegionCommand rc = toRegionConverter.convert(region);
        rc.setAreaCommand(ac);

        //when
        when(repository.findAllByIdIn(ids)).thenReturn(List.of(region));
        List<RegionCommand> results = service.findAllByIdsIn(ids);

        //then
        assertEquals(List.of(rc), results);
        verify(repository, times(1)).findAllByIdIn(ids);
    }
}