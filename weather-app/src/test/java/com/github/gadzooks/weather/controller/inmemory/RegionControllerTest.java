package com.github.gadzooks.weather.controller.inmemory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.gadzooks.weather.domain.inmemory.Region;
import com.github.gadzooks.weather.service.inmemory.RegionService;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//NOTE : test controller layer. use @SpringBootTest for integration testing
//     : controllers, parameter tells Spring Boot to only load the beans required for this controller
@WebMvcTest(controllers = RegionController.class)
class RegionControllerTest {

    //simulate HTTP requests.
    @Autowired
    private MockMvc mockMvc;

    //We use @MockBean to mock away the business logic, since we don’t want to test integration between controller and
    // business logic, but between controller and the HTTP layer.
    // @MockBean automatically replaces the bean of the same type in the application context with a Mockito mock.
    @MockBean
    private RegionService regionService;

    private ImmutableList<Region> regionList;
    private Region r1;
    private Region r2;
    private Region r3;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        r1 = new Region();
        r1.setName("r1");
        r1.setDescription("r1Desc");
        r1.setSearchKey("r1SearchKey");

        r2 = new Region();
        r2.setName("r2");
        r2.setDescription("r2Desc");
        r2.setSearchKey("r2SearchKey");

        r3 = new Region();
        r3.setName("r3");
        r3.setDescription("r3Desc");
        r3.setSearchKey("r3SearchKey");

        regionList = ImmutableList.of(r1, r2, r3);

        mapper = new ObjectMapper(new YAMLFactory()); // create once, reuse

        //Ignore HATEOAS links
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @AfterEach
    void tearDown() {
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllRegions() throws Exception {
        when(regionService.findAll()).thenReturn(regionList);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/regions").contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).
                andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        CollectionType listType = mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, Region.class);
        List<Region> jsonRegions = mapper.readValue(contentAsString, listType);
        assertEquals(regionList.size(), jsonRegions.size());
        assertEquals(jsonRegions.get(0), r1);
    }

    @Test
    void getByRegionId() throws Exception {
        when(regionService.getById(r2.getName())).thenReturn(r2);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/regions/" + r2.getName()).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).
                andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Region actualRegion = mapper.readValue(contentAsString, Region.class);
        assertEquals(r2,actualRegion);
    }

    @Test
    void patchRegion() throws Exception {
        when(regionService.patch(r3.getName(), r3)).thenReturn(r3);
        mockMvc.perform( MockMvcRequestBuilders
                .patch("/regions/" + r3.getName())
                .content(asJsonString(r3))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(r3.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.searchKey").value(r3.getSearchKey()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(r3.getDescription()));

    }

    @Test
    void putRegion() throws Exception {
        when(regionService.save(r3)).thenReturn(r3);
        mockMvc.perform( MockMvcRequestBuilders
                .put("/regions/" + r3.getName())
                .content(asJsonString(r3))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(r3.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.searchKey").value(r3.getSearchKey()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(r3.getDescription()));
    }

    @Test
    void postRegion() throws Exception {
        when(regionService.save(r3)).thenReturn(r3);
        mockMvc.perform( MockMvcRequestBuilders
                .post("/regions")
                .content(asJsonString(r3))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(r3.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.searchKey").value(r3.getSearchKey()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(r3.getDescription()));
    }

    @Test
    void deleteRegion() throws Exception {
        when(regionService.getById(r3.getName())).thenReturn(r3);
//        verify(regionService).delete(r3.getId());
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/regions/" + r3.getName() )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(r3.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.searchKey").value(r3.getSearchKey()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(r3.getDescription()));

    }
}