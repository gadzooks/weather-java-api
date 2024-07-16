package com.github.gadzooks.weather.controller.mongo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.List;
import java.util.stream.Collectors;
import com.github.gadzooks.weather.api.v1.model.RegionDTO;
import com.github.gadzooks.weather.service.mongo.MongoRegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//FIXME : set up @WebMvcTest tests for this controller
@Tag(
        name = "Mongo Region",
        description = "Mongo backed : REST API for Regions" // way to group HTTP operations together in Swagger
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
    @Operation(summary = "Find all ACTIVE regions",
        tags = {TAG},
        description = "This method returns all ACTIVE the regions")
    @ApiResponse(responseCode = "200", description = "Found all Active regions",
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = RegionDTO.class))
            )
        }
    )
    public List<EntityModel<RegionDTO>> findAllActive() {
        List<RegionDTO> results = regionService.findAllActive(true);
        return results.stream().map(
                region -> EntityModel.of(region,
                        linkTo(methodOn(MongoRegionController.class).findOne(region.getId())).withSelfRel(),
                        linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"))).
                collect(Collectors.toList());
    }

    @GetMapping(value = "")
    @Operation(summary = "Find all regions",
            tags = {TAG},
            description = "This method returns all the regions")
    @ApiResponse(
        responseCode = "200",
        description = "find all regions, including inactive ones",
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = RegionDTO.class))
            )
        }
    )
    public List<EntityModel<RegionDTO>> findAll() {
        List<RegionDTO> results = regionService.findAll();
        return results.stream().map(
                region -> EntityModel.of(region,
                        linkTo(methodOn(MongoRegionController.class).findOne(region.getId())).withSelfRel(),
                        linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"))).
                collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Find region by id",
        tags = {TAG},
        description = "This method finds region by id provided")
    @ApiResponse(
        responseCode = "200",
        description = "find region by id",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RegionDTO.class)
            )
        }
    )
    @ApiResponse(
        responseCode = "404",
        description = "Region not found"
    )
    public EntityModel<RegionDTO> findOne(
            @Parameter(description = "RegionDTO Id of the region requested", example = "id") @PathVariable String id) {
        log.info("String to look up is : " + id.toString());
        RegionDTO region = regionService.getById(id);
        log.info("region document found : " + region.toString());
        return EntityModel.of(region, //
                linkTo(methodOn(MongoRegionController.class).findOne(id)).withSelfRel(),
                linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"));
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update part of a region (patch)",
            tags = {TAG},
            description = "This method allows users to update **subset** of attributes of a region")
    @ApiResponse(
        responseCode = "200",
        description = "region was updated",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RegionDTO.class)
            )
        }
    )
    @ApiResponse(
        responseCode = "404",
        description = "Region not found"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Unable to update region"
    )
    public EntityModel<RegionDTO> patchRegion(
            @Parameter(description = "RegionDTO Id of the region requested", example = "String") @PathVariable String id,
            @Parameter(description = "region object (can be partially set)") @RequestBody RegionDTO updatedRegion) {
        RegionDTO savedRegionDTO = regionService.patch(id, updatedRegion);

        // NOTE: alternate way to return HATEOAS
//        Link newlyCreatedLink = linkTo(methodOn(RegionController.class).findOne(savedRegion.getId())).withSelfRel();
//        try {
//            return ResponseEntity.noContent().location(new URI(newlyCreatedLink.getHref())).build();
//        } catch (URISyntaxException e) {
//            return ResponseEntity.badRequest().body("Unable to update " + updatedRegion);
//        }
        return EntityModel.of(savedRegionDTO,
                linkTo(methodOn(MongoRegionController.class).findOne(id)).withSelfRel(),
                linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update a region",
        tags = {TAG},
        description = "This method allows users to replace all attributes of a region")
    @ApiResponse(
        responseCode = "200",
        description = "region was updated",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RegionDTO.class)
            )
        }
    )
    @ApiResponse(
        responseCode = "404",
        description = "Region not found"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Unable to update region"
    )
    public EntityModel<RegionDTO> updateRegion(
            @Parameter(description = "RegionDTO Id of the region requested", example = "String") @PathVariable String id,
            @Parameter(description = "Valid region object") @Valid @RequestBody RegionDTO updatedRegion) {
        RegionDTO savedRegionDTO = regionService.patch(id, updatedRegion);

        // NOTE: alternate way to return HATEOAS
//        Link newlyCreatedLink = linkTo(methodOn(RegionController.class).findOne(savedRegion.getId())).withSelfRel();
//        try {
//            return ResponseEntity.noContent().location(new URI(newlyCreatedLink.getHref())).build();
//        } catch (URISyntaxException e) {
//            return ResponseEntity.badRequest().body("Unable to update " + updatedRegion);
//        }
        return EntityModel.of(savedRegionDTO,
                linkTo(methodOn(MongoRegionController.class).findOne(id)).withSelfRel(),
                linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new region",
        tags = {TAG},
        description= "This method creates a new region")
    @ApiResponse(
        responseCode = "201",
        description = "region was created",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RegionDTO.class)
            )
        }
    )
    @ApiResponse(
        responseCode = "400",
        description = "Unable to create a new region"
    )
    public EntityModel<RegionDTO> newRegion(
            @Parameter(description = "Valid region object") @Valid @RequestBody RegionDTO region) {
        RegionDTO savedRegionDTO = regionService.save(region);

        return EntityModel.of(savedRegionDTO, //
                linkTo(methodOn(MongoRegionController.class).findOne(savedRegionDTO.getId())).withSelfRel(),
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
    @Operation(summary = "Delete a region",
        tags = {TAG},
        description = "This method deletes a region")
    @ApiResponse(
        responseCode = "200",
        description = "region was deleted"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Unable to delete the region"
    )
    public EntityModel<RegionDTO> deleteRegion(
            @Parameter(description = "RegionDTO Id of the region requested", example = "String") @PathVariable String id) {
        RegionDTO region = regionService.getById(id);
        regionService.delete(id);

        return EntityModel.of(region, linkTo(methodOn(MongoRegionController.class).findAll()).withRel("regions"));
    }

}
