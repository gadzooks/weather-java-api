package com.github.gadzooks.weather.repository.mongo;

import com.github.gadzooks.weather.domain.mongo.RegionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MongoRegionRepository extends MongoRepository<RegionDocument, UUID>, MongoRegionRepositoryCustom {
    RegionDocument findByName(@Param("name") String name);

    List<RegionDocument> findByIsActive(@Param("isActive") Boolean isActive);
}
