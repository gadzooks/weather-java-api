package com.github.gadzooks.weather.repository.mongo;

import com.github.gadzooks.weather.dao.mongo.RegionDocument;

import java.util.List;
import java.util.UUID;

//FIXME : figure out how to implement custom query / criteria / criteria query
public interface MongoRegionRepositoryCustom {
    List<RegionDocument> findRegionsByIds(List<UUID> ids);

    List<RegionDocument> findActiveRegions(List<UUID> ids);
}
