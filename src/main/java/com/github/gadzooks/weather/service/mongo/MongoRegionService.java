package com.github.gadzooks.weather.service.mongo;

import com.github.gadzooks.weather.domain.mongo.RegionDocument;
import com.github.gadzooks.weather.service.CrudService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// @Service annotation here does not create a Bean of the implemented class
public interface MongoRegionService extends CrudService<RegionDocument, UUID> {
    Optional<RegionDocument> findById(UUID id);
    List<RegionDocument> findAllActive(boolean isActive);
}
