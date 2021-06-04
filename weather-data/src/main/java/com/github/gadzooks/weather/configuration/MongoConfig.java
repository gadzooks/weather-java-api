package com.github.gadzooks.weather.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = {"com.github.gadzooks.weather.repository.mongo"})
@EnableMongoRepositories(basePackages = {"com.github.gadzooks.weather.repository.mongo"})
public class MongoConfig {
}
