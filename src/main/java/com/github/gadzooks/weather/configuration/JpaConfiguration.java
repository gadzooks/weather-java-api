package com.github.gadzooks.weather.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.github.gadzooks.weather.repository.jpa"})
public class JpaConfiguration {
}
