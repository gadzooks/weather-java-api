package com.github.gadzooks.weather.repository.mongo;

import com.github.gadzooks.weather.dao.mongo.RegionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MongoRegionRepository extends MongoRepository<RegionDocument, UUID>, MongoRegionRepositoryCustom {
    RegionDocument findByName(@Param("name") String name);

    List<RegionDocument> findByIsActive(@Param("isActive") Boolean isActive);
}
