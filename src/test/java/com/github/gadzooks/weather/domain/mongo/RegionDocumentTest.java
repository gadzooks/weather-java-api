package com.github.gadzooks.weather.domain.mongo;

import com.github.gadzooks.weather.configuration.MongoValidationConfig;
import com.github.gadzooks.weather.repository.mongo.MongoRegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
// By default, tests annotated with @DataMongoTest will use an embedded in-memory MongoDB process (if available).
@DataMongoTest
@Import(MongoValidationConfig.class)
class RegionDocumentTest {

    @Autowired
    private MongoRegionRepository repository;

    @Test
    void shouldFailOnInvalidEntity() {
        var invalidDocument = new RegionDocument();
        invalidDocument.setSearchKey("12345");
        invalidDocument.setIsActive(false);
        invalidDocument.setName("");

        assertThatThrownBy(() -> repository.save(invalidDocument)).isInstanceOf(ConstraintViolationException.class);
    }

    //FIXME test should run against test db
    //FIXME add uniqueness constraint on name attribute
    @TestConfiguration
    static class MongoMapKeyDotReplacementConfiguration {
        @Bean
        public LocalValidatorFactoryBean localValidatorFactoryBean() {
            return new LocalValidatorFactoryBean();
        }
    }
}
