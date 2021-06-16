package com.github.gadzooks.weather.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.github.gadzooks.weather.repository.mongo"})
@EnableMongoAuditing
public class MongoConfig {
}
