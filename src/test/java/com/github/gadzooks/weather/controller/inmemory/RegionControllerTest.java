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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//NOTE : test controller layer. use @SpringBootTest for integration testing
//     : controllers, parameter tells Spring Boot to only load the beans required for this controller
//@WebMvcTest(controllers = RegionController.class)
//Here we are mocking everything out with MockMvc so we dont need to use @WebMvcTest which would load more spring
//application context than we need to run these tests (and which would slow down the tests)
class RegionControllerTest {

    //simulate HTTP requests.
    private MockMvc mockMvc;

    @Mock
    private RegionService regionService;

    private ImmutableList<Region> regionList;
    private Region r1;
    private Region r2;
    private Region r3;

    private ObjectMapper mapper = new ObjectMapper(new YAMLFactory()); // create once, reuse
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        r1 = new Region();
        r1.setId(1L);
        r1.setName("r1");
        r1.setDescription("r1Desc");
        r1.setSearchKey("r1SearchKey");

        r2 = new Region();
        r2.setId(2L);
        r2.setName("r2");
        r2.setDescription("r2Desc");
        r2.setSearchKey("r2SearchKey");

        r3 = new Region();
        r3.setId(3L);
        r3.setName("r3");
        r3.setDescription("r3Desc");
        r3.setSearchKey("r3SearchKey");

        regionList = ImmutableList.of(r1, r2, r3);


        //Ignore HATEOAS links
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        RegionController regionController = new RegionController(regionService);
        mockMvc = MockMvcBuilders.standaloneSetup(regionController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
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

//    @Test
//    @Ignore("not working.")
//        //TODO
//    void getByRegionId_with404() throws Exception {
//        Long regionId = 333L;
//        ResourceNotFoundException rsne = new ResourceNotFoundException("Region", regionId.toString());
//        when(regionService.getById(regionId)).thenThrow(rsne);
//        mockMvc.perform(MockMvcRequestBuilders.get("/regions/" + regionId).
//                contentType(MediaType.APPLICATION_JSON_VALUE)).
//                andExpect(status().is4xxClientError()).
//                andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException)).
//                andExpect(result -> assertEquals("Could not find Region resource with id " + regionId,
//                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
//    }

    @Test
    void getByRegionId() throws Exception {
        when(regionService.getById(r2.getId())).thenReturn(r2);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/regions/" + r2.getId()).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).
                andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Region actualRegion = mapper.readValue(contentAsString, Region.class);
        assertEquals(r2,actualRegion);
    }

    @Test
    void patchRegion() throws Exception {
        when(regionService.patch(r3.getId(), r3)).thenReturn(r3);
        mockMvc.perform( MockMvcRequestBuilders
                .patch("/regions/" + r3.getId())
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
                .put("/regions/" + r3.getId())
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
        when(regionService.getById(r3.getId())).thenReturn(r3);
//        verify(regionService).delete(r3.getId());
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/regions/" + r3.getId() )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(r3.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.searchKey").value(r3.getSearchKey()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(r3.getDescription()));

    }
}