package com.github.gadzooks.weather.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.github.gadzooks.weather.repository.mongo"})
public class MongoConfig {
}
