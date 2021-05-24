package com.github.gadzooks.weather.controller;

import com.github.gadzooks.weather.configuration.RegionConfig;
import com.github.gadzooks.weather.service.RegionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceController.class)
class PlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService placeService;

    @BeforeEach
    public void setup() {
        Mockito.when(placeService.getRegions()).
                thenReturn(Arrays.asList(new Region[0]));
    }

    @Test
    void regions() throws Exception {
        mockMvc.perform(get("/place/regions")).andExpect(status().isOk());
    }

    @Test
    void getRegionById() throws Exception {
        mockMvc.perform(get("/place/regions/issaquah")).andExpect(status().isOk());
    }
}