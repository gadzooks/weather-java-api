package com.github.gadzooks.weather.repository.mongo;

import com.github.gadzooks.weather.dao.mongo.LocationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoLocationRepository extends MongoRepository<LocationDocument, UUID> {
}
