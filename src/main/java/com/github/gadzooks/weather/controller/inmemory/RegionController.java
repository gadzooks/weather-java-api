package com.github.gadzooks.weather.controller.inmemory;

import com.github.gadzooks.weather.domain.inmemory.Region;
import com.github.gadzooks.weather.service.inmemory.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(
        name = "Region",
        description = "REST API for Regions with locations"  // way to group HTTP operations together in Swagger
)
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

    @GetMapping(value = "")
    @Operation(summary = "Find all regions",
            tags = { "REST API for Regions with locations" },
            description = "This method returns all the regions")
    public List<EntityModel<Region>> findAll() {
        return regionService.findAll().stream().map(
                region -> EntityModel.of(region,
                        linkTo(methodOn(RegionController.class).findOne(region.getId())).withSelfRel(),
                        linkTo(methodOn(RegionController.class).findAll()).withRel("regions"))).
                collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Find region by id",
            tags = { "REST API for Regions with locations" },
            description = "This method finds region by id provided")
    public EntityModel<Region> findOne(
            @Parameter(description = "Region Id of the region requested", example = "1") @PathVariable Long id) {
        Region region = regionService.getById(id);
        return EntityModel.of(region, //
                linkTo(methodOn(RegionController.class).findOne(id)).withSelfRel(),
                linkTo(methodOn(RegionController.class).findAll()).withRel("regions"));
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update part of a region (patch)",
            tags = { "REST API for Regions with locations" },
            description = "This method allows users to update **subset** of attributes of a region")
    public EntityModel<Region> patchRegion(
            @Parameter(description = "Region Id of the region requested", example = "1") @PathVariable Long id,
            @Parameter(description = "region object (can be partially set)") @RequestBody Region updatedRegion) {
        Region savedRegion = regionService.patch(id, updatedRegion);

        // NOTE: alternate way to return HATEOAS
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

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update a region",
            tags = { "REST API for Regions with locations" },
            description = "This method allows users to replace all attributes of a region")
    public EntityModel<Region> updateRegion(
            @Parameter(description = "Region Id of the region requested", example = "1") @PathVariable Long id,
            @Parameter(description = "Valid region object") @Valid @RequestBody Region updatedRegion) {
//        updatedRegion.setName(id);
        Region savedRegion = regionService.save(updatedRegion);

        // NOTE: alternate way to return HATEOAS
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
    @Operation(summary = "Create new region",
            tags = { "REST API for Regions with locations" },
            description = "This method creates a new region")
    public EntityModel<Region> newRegion(
            @Parameter(description = "Valid region object") @Valid @RequestBody Region region) {
        Region savedRegion = regionService.save(region);

        return EntityModel.of(savedRegion, //
                linkTo(methodOn(RegionController.class).findOne(savedRegion.getId())).withSelfRel(),
                linkTo(methodOn(RegionController.class).findAll()).withRel("regions"));

        // NOTE: alternate way to return HATEOAS
//        try {
//            return ResponseEntity //
//                    .created(new URI(employeeResource.getRequiredLink(IanaLinkRelations.SELF).getHref())) //
//                    .body(employeeResource);
//        } catch (URISyntaxException e) {
//            return ResponseEntity.badRequest().body("Unable to create " + region);
//        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a region",
            tags = { "REST API for Regions with locations" },
            description = "This method deletes a region")
    public EntityModel<Region> deleteRegion(
            @Parameter(description = "Region Id of the region requested", example = "1") @PathVariable Long id) {
        Region region = regionService.getById(id);
        regionService.delete(id);
        return EntityModel.of(region, //
                linkTo(methodOn(RegionController.class).findAll()).withRel("regions"));
    }

}
