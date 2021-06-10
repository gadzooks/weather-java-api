package com.github.gadzooks.weather.controller.jpa;

import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.service.jpa.JpaRegionService;
import com.github.gadzooks.weather.service.jpa.PlacesService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class JpaRegionControllerTest {

    @Mock
    private JpaRegionService jpaRegionService;
    @Mock
    private PlacesService placesService;
    @InjectMocks
    private JpaRegionController jpaRegionController;

    // NOTE : since we are using : 1) @ExtendWith(MockitoExtension.class) 2) @InjectMocks
    // we do NOT need to manually setup mocks, close mocks and set up jpaController class
//    private AutoCloseable closeable;
//    @BeforeEach
//    void setUp() {
//        //without openMocks the test fails as the mock object is not set up.
//        closeable = MockitoAnnotations.openMocks(this);
//        jpaRegionController = new JpaRegionController(jpaRegionService, placesService);
//    }
//
//    @AfterEach
//    void tearDown() throws Exception {
//        closeable.close();
//    }

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