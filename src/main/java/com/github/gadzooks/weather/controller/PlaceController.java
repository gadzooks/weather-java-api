package com.github.gadzooks.weather.controller;

import com.github.gadzooks.weather.configuration.RegionConfig;
import com.github.gadzooks.weather.service.PlaceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Allow requests from other domains
@CrossOrigin(origins="*")
//set prefix of path of /place for all requests in PlaceController
@RequestMapping(path = "/place", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlaceController {

    private final PlaceService placeService;

    //NOTE : using constructor injection to make it easier to test
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping(value = "/regions")
    public List<RegionConfig.Region> regions() {
        return placeService.getRegions();
    }

    @GetMapping(value = "/regions/{id}")
    public RegionConfig.Region getByRegionId(@PathVariable String id) {
        return placeService.getRegionById(id);
    }

    @PatchMapping(value = "/regions/{id}")
    public RegionConfig.Region updateRegion(@PathVariable String id, @RequestBody RegionConfig.Region updatedRegion) {
        return placeService.updateRegion(id, updatedRegion);
    }

}
