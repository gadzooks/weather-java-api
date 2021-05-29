package com.github.gadzooks.weather.repository.mongo;

import com.github.gadzooks.weather.domain.mongo.LocationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MongoLocationRepository extends MongoRepository<LocationDocument, UUID> {
}
