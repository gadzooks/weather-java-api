package com.github.gadzooks.weather.repository.mongo;

import com.github.gadzooks.weather.domain.mongo.LocationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoLocationRepository extends MongoRepository<LocationDocument, String> {
}
