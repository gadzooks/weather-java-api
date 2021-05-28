package com.github.gadzooks.weather.repository.mongo;

import com.github.gadzooks.weather.dao.mongo.RegionDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.UUID;

/**
 * Mongo Spring will create an instance of this because its name is <YourRepository>Impl
 * NOTE : name *MUST* be <YourRepository>Impl
 * This is Mongo Spring's convention over configuration
 * https://docs.spring.io/spring-data/data-document/docs/current/reference/html/#repositories.custom-implementations
 */
public class MongoRegionRepositoryImpl implements MongoRegionRepositoryCustom {
    private final MongoTemplate mongoTemplate;
    private static final Logger log = LoggerFactory.getLogger(MongoRegionRepositoryImpl.class);

    public MongoRegionRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<RegionDocument> findRegionsByIds(List<UUID> ids) {
//        mongoTemplate.find();
//        Query query = new Query(
//        return mongoTemplate.findAll(query, "regions");
        //FIXME todo

        log.info("In MongoRegionRepositoryImpl:findRegionsByIds");
        return null;
    }

    @Override
    public List<RegionDocument> findActiveRegions(List<UUID> ids) {
//        Query
//        List<RegionDocument> result = mongoTemplate.query(RegionDocument.class).matching()

        //FIXME todo
        return null;
    }
}
