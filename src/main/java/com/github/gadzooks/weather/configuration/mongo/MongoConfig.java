package com.github.gadzooks.weather.configuration.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = {com.github.gadzooks.weather.repository.mongo.MongoRegionRepository.class})
public class MongoConfig {
}
