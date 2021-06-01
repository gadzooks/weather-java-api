package com.github.gadzooks.weather.domain.mongo;

import com.github.gadzooks.weather.configuration.MongoValidationConfig;
import com.github.gadzooks.weather.repository.mongo.MongoRegionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

// By default, tests annotated with @DataMongoTest will use an embedded in-memory MongoDB process (if available).
@DataMongoTest
//@ExtendWith(SpringExtension.class)
@Import(MongoValidationConfig.class)
class RegionDocumentTest {

    @Autowired
    private MongoRegionRepository repository;

    @Test
    @DirtiesContext
    void shouldFailOnInvalidEntity() {
        var invalidDocument = new RegionDocument();
        invalidDocument.setSearchKey("12345");
        invalidDocument.setIsActive(false);
        invalidDocument.setName("1234");

//        assertThatThrownBy(() -> repository.save(invalidDocument)).isInstanceOf(ConstraintViolationException.class);
        repository.save(invalidDocument);

//        var doc = repository.findByName("1234");

//        System.out.println(doc);
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