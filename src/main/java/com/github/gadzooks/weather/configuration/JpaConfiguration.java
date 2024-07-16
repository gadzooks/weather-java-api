package com.github.gadzooks.weather.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.github.gadzooks.weather.repository.jpa"})
@EntityScan("com.github.gadzooks.weather.domain.jpa")
public class JpaConfiguration {
}
