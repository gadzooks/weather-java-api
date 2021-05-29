package com.github.gadzooks.weather.service.mongo;

import com.github.gadzooks.weather.domain.mongo.RegionDocument;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// @Service annotation here does not create a Bean of the implemented class
public interface MongoRegionService {
    Optional<RegionDocument> findById(UUID id);

    RegionDocument save(RegionDocument doc);

    List<RegionDocument> findAll();

    RegionDocument findOne(UUID id);

    RegionDocument patch(UUID id, RegionDocument updatedRegion);

    void delete(UUID id);

    List<RegionDocument> findAllActive(boolean isActive);
}
