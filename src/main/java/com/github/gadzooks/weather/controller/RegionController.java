package com.github.gadzooks.weather.controller;

import com.github.gadzooks.weather.dto.Region;
import com.github.gadzooks.weather.service.RegionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
//Allow requests from other domains
@CrossOrigin(origins = "*")
//set prefix of path of /place for all requests in PlaceController
@RequestMapping(path = "/regions", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegionController {

    private final RegionService regionService;

    //NOTE : using constructor injection to make it easier to test
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping(value = "/")
    @ApiOperation(value = "Find all regions",
            notes = "This method returns all the regions")
    public List<EntityModel<Region>> findAll() {
        return regionService.findAll().stream().map(
                region -> EntityModel.of(region,
                        linkTo(methodOn(RegionController.class).findOne(region.getId())).withSelfRel(),
                        linkTo(methodOn(RegionController.class).findAll()).withRel("regions"))).
                collect(Collectors.toList())
                ;
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find region by id",
            notes = "This method finds region by id provided")
    public EntityModel<Region> findOne(@PathVariable String id) {
        Region region = regionService.findOne(id);
        return EntityModel.of(region, //
                linkTo(methodOn(RegionController.class).findOne(id)).withSelfRel(),
                linkTo(methodOn(RegionController.class).findAll()).withRel("regions"));
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "Update part of a region",
            notes = "This method allows users to update attributes of a region")
    public EntityModel<Region> updateRegion(@PathVariable String id, @Valid @RequestBody Region updatedRegion) {
        updatedRegion.setId(id);
        Region savedRegion = regionService.save(updatedRegion);

        // NOTE: alternate way to return HATEOS
//        Link newlyCreatedLink = linkTo(methodOn(RegionController.class).findOne(savedRegion.getId())).withSelfRel();
//        try {
//            return ResponseEntity.noContent().location(new URI(newlyCreatedLink.getHref())).build();
//        } catch (URISyntaxException e) {
//            return ResponseEntity.badRequest().body("Unable to update " + updatedRegion);
//        }
        return EntityModel.of(savedRegion,
                linkTo(methodOn(RegionController.class).findOne(id)).withSelfRel(),
                linkTo(methodOn(RegionController.class).findAll()).withRel("regions"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new region",
            notes = "This method creates a new region")
    public EntityModel<Region> newRegion(@Valid @RequestBody Region region) {
        Region savedRegion = regionService.save(region);

        return EntityModel.of(savedRegion, //
                linkTo(methodOn(RegionController.class).findOne(savedRegion.getId())).withSelfRel(),
                linkTo(methodOn(RegionController.class).findAll()).withRel("regions"));
//        try {
//            return ResponseEntity //
//                    .created(new URI(employeeResource.getRequiredLink(IanaLinkRelations.SELF).getHref())) //
//                    .body(employeeResource);
//        } catch (URISyntaxException e) {
//            return ResponseEntity.badRequest().body("Unable to create " + region);
//        }
    }

}
