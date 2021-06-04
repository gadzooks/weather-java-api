package com.github.gadzooks.weather.controller.mongo;

import com.github.gadzooks.weather.domain.mongo.RegionDocument;
import com.github.gadzooks.weather.service.mongo.MongoRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//FIXME : set up @WebMvcTest tests for this controller
@Api(
        value = "Mongo Region",
        tags = {"Mongo backed : REST API for Regions"} // way to group HTTP operations together in Swagger
)
@RestController
//Allow requests from other domains
@CrossOrigin(origins = "*")
//set prefix of path of /place for all requests in PlaceController
@RequestMapping(path = "/mongo/regions", produces = MediaType.APPLICATION_JSON_VALUE)
public class MongoRegionController {

    private final static String TAG = "Mongo backed : REST API for Regions";
    private static final Logger log = LoggerFactory.getLogger(MongoRegionController.class);
    private final MongoRegionService regionService;

    //NOTE : using constructor injection to make it easier to test
    public MongoRegionController(MongoRegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping(value = "/active")
    @ApiOperation(value = "Find all ACTIVE regions",
            tags = {TAG},
            notes = "This method returns all ACTIVE the regions")
    public List<EntityModel<RegionDocument>> findAllActive() {
        List<RegionDocument> results = regionService.findAllActive(true);
        return results.stream().map(
                region -> EntityModel.of(region,
                        linkTo(methodOn(MongoRegionController.class).findOne(region.getId())).withSelfRel(),
                        linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"))).
                collect(Collectors.toList());
    }

    @GetMapping(value = "")
    @ApiOperation(value = "Find all regions",
            tags = {TAG},
            notes = "This method returns all the regions")
    public List<EntityModel<RegionDocument>> findAll() {
        List<RegionDocument> results = regionService.findAll();
        return results.stream().map(
                region -> EntityModel.of(region,
                        linkTo(methodOn(MongoRegionController.class).findOne(region.getId())).withSelfRel(),
                        linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"))).
                collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Find region by id",
            tags = {TAG},
            notes = "This method finds region by id provided")
    public EntityModel<RegionDocument> findOne(
            @ApiParam(value = "RegionDocument Id of the region requested", example = "uuid") @PathVariable UUID id) {
        log.info("UUID to look up is : " + id.toString());
        RegionDocument region = regionService.getById(id);
        log.info("region document found : " + region.toString());
        return EntityModel.of(region, //
                linkTo(methodOn(MongoRegionController.class).findOne(id)).withSelfRel(),
                linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"));
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "Update part of a region (patch)",
            tags = {TAG},
            notes = "This method allows users to update **subset** of attributes of a region")
    public EntityModel<RegionDocument> patchRegion(
            @ApiParam(value = "RegionDocument Id of the region requested", example = "uuid") @PathVariable UUID id,
            @ApiParam(value = "region object (can be partially set)") @RequestBody RegionDocument updatedRegion) {
        RegionDocument savedRegionDocument = regionService.patch(id, updatedRegion);

        // NOTE: alternate way to return HATEOAS
//        Link newlyCreatedLink = linkTo(methodOn(RegionController.class).findOne(savedRegion.getId())).withSelfRel();
//        try {
//            return ResponseEntity.noContent().location(new URI(newlyCreatedLink.getHref())).build();
//        } catch (URISyntaxException e) {
//            return ResponseEntity.badRequest().body("Unable to update " + updatedRegion);
//        }
        return EntityModel.of(savedRegionDocument,
                linkTo(methodOn(MongoRegionController.class).findOne(id)).withSelfRel(),
                linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"));
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Update a region",
            tags = {TAG},
            notes = "This method allows users to replace all attributes of a region")
    public EntityModel<RegionDocument> updateRegion(
            @ApiParam(value = "RegionDocument Id of the region requested", example = "uuid") @PathVariable UUID id,
            @ApiParam(value = "Valid region object") @Valid @RequestBody RegionDocument updatedRegion) {
        updatedRegion.setId(id);
        RegionDocument savedRegionDocument = regionService.save(updatedRegion);

        // NOTE: alternate way to return HATEOAS
//        Link newlyCreatedLink = linkTo(methodOn(RegionController.class).findOne(savedRegion.getId())).withSelfRel();
//        try {
//            return ResponseEntity.noContent().location(new URI(newlyCreatedLink.getHref())).build();
//        } catch (URISyntaxException e) {
//            return ResponseEntity.badRequest().body("Unable to update " + updatedRegion);
//        }
        return EntityModel.of(savedRegionDocument,
                linkTo(methodOn(MongoRegionController.class).findOne(id)).withSelfRel(),
                linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new region",
            tags = {TAG},
            notes = "This method creates a new region")
    public EntityModel<RegionDocument> newRegion(
            @ApiParam(value = "Valid region object") @Valid @RequestBody RegionDocument region) {
        RegionDocument savedRegionDocument = regionService.save(region);

        return EntityModel.of(savedRegionDocument, //
                linkTo(methodOn(MongoRegionController.class).findOne(savedRegionDocument.getId())).withSelfRel(),
                linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"));

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
    @ApiOperation(value = "Delete a region",
            tags = {TAG},
            notes = "This method deletes a region")
    public EntityModel<RegionDocument> deleteRegion(
            @ApiParam(value = "RegionDocument Id of the region requested", example = "uuid") @PathVariable UUID id) {
        RegionDocument region = regionService.getById(id);
        regionService.delete(id);

        return EntityModel.of(region, linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"));
    }

}
