package com.github.gadzooks.weather.controller.jpa;

import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.service.jpa.JpaRegionService;
import com.github.gadzooks.weather.service.jpa.PlacesService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityModel;

import java.util.List;

import static org.mockito.Mockito.*;

@Slf4j
class JpaRegionControllerTest {
    private AutoCloseable closeable;
    private JpaRegionController jpaRegionController;

    @Mock
    private JpaRegionService jpaRegionService;
    @Mock
    private PlacesService placesService;

    @BeforeEach
    void setUp() {
        //without openMocks the test fails as the mock object is not set up.
        closeable = MockitoAnnotations.openMocks(this);
        jpaRegionController = new JpaRegionController(jpaRegionService, placesService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void searchEveryWhere() {
        //given
        RegionJpa r1 = new RegionJpa();
        r1.setId(1L);
        r1.setName("r1");

        RegionJpa r2 = new RegionJpa();
        r2.setId(2L);
        r2.setName("r2");

        List<RegionJpa> regionJpaList = List.of(r1, r2);
        String searchString = "issaquah";

        //when
        when(placesService.searchEveryWhere(searchString)).thenReturn(regionJpaList);
        List<EntityModel<RegionJpa>> results = jpaRegionController.searchEveryWhere(searchString);

        //then
        verify(placesService, times(1)).searchEveryWhere(searchString);

        log.debug("search everywhere results : ");
        for (EntityModel<RegionJpa> r : results) {
            log.debug(r.toString());
        }

    }
}