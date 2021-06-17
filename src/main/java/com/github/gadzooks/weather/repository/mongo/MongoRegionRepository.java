package com.github.gadzooks.weather.repository.mongo;

import com.github.gadzooks.weather.domain.mongo.RegionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MongoRegionRepository extends MongoRepository<RegionDocument, String>, CustomRegionRepository {
    RegionDocument findByName(@Param("name") String name);

    List<RegionDocument> findByIsActive(@Param("isActive") Boolean isActive);
}
