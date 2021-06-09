package com.github.gadzooks.weather.controller.jpa;

import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.service.jpa.JpaRegionService;
import com.github.gadzooks.weather.service.jpa.PlacesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//FIXME : set up @WebMvcTest tests for this controller
@Api(
        value = "JPA Region",
        tags = {"JPA backed : REST API for Regions"} // way to group HTTP operations together in Swagger
)
@RestController
//Allow requests from other domains
@CrossOrigin(origins = "*")
//set prefix of path of /place for all requests in this controller
@RequestMapping(path = "/jpa/regions", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class JpaRegionController {
    private final static String TAG = "JPA backed : REST API for Regions";
    private final JpaRegionService regionService;
    private final PlacesService placesService;

    public JpaRegionController(JpaRegionService jpaRegionService, PlacesService placesService) {
        this.regionService = jpaRegionService;
        this.placesService = placesService;
    }

    @GetMapping(value = "/active")
    @ApiOperation(value = "Find all ACTIVE regions",
            tags = {TAG},
            notes = "This method returns all the ACTIVE regions")
    public List<EntityModel<RegionJpa>> findAllActive() {
        List<RegionJpa> results = regionService.findAllActive();
        return results.stream().map(
                region -> EntityModel.of(region,
                        linkTo(methodOn(JpaRegionController.class).findOne(region.getId())).withSelfRel(),
                        linkTo(methodOn(JpaRegionController.class).findAll()).withRel("regions"))).
                collect(Collectors.toList());
    }

    @GetMapping(value = "")
    @ApiOperation(value = "Find all regions",
            tags = {TAG},
            notes = "This method returns all the regions")
    public List<EntityModel<RegionJpa>> findAll() {
        List<RegionJpa> results = regionService.findAll();
        return results.stream().map(
                region -> EntityModel.of(region,
                        linkTo(methodOn(JpaRegionController.class).findOne(region.getId())).withSelfRel(),
                        linkTo(methodOn(JpaRegionController.class).findAll()).withRel("regions"))).
                collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find region by id",
            tags = {TAG},
            notes = "This method finds region by id provided")
    public EntityModel<RegionJpa> findOne(
            @ApiParam(value = "RegionJpa Id of the region requested", example = "1") @PathVariable Long id) {
        log.info("Id to look up is : " + id.toString());
        RegionJpa region = regionService.getById(id);
        log.info("region document found : " + region.toString());
        return EntityModel.of(region, //
                linkTo(methodOn(JpaRegionController.class).findOne(id)).withSelfRel(),
                linkTo(methodOn(JpaRegionController.class).findAll()).withRel("regions"));
    }

    @GetMapping(value = "/search/{str}")
    @ApiOperation(value = "Find region by searching everywhere",
            tags = {TAG},
            notes = "This method finds region by searching for str in area/region/location")
    public List<EntityModel<RegionJpa>> searchEveryWhere(
            @ApiParam(value = "Search string to search by", example = "hIgHwaY") @PathVariable String str) {
        List<RegionJpa> results = placesService.searchEveryWhere(str);
        return results.stream().map(
                region -> EntityModel.of(region,
                        linkTo(methodOn(JpaRegionController.class).findOne(region.getId())).withSelfRel(),
                        linkTo(methodOn(JpaRegionController.class).findAll()).withRel("regions"))).
                collect(Collectors.toList());
    }
}
