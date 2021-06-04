package com.github.gadzooks.weather.service.mongo;

import com.github.gadzooks.weather.domain.mongo.RegionDocument;
import com.github.gadzooks.weather.repository.mongo.MongoRegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service //NOTE: @Service annotation is required HERE to create a bean.
// We cannot use the @Service annotation on the Interface
public class MongoRegionServiceImpl implements MongoRegionService {
    private final MongoRegionRepository mongoRepository;

    public MongoRegionServiceImpl(MongoRegionRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Optional<RegionDocument> findById(UUID id) {
        return mongoRepository.findById(id);
    }

    @Override
    public RegionDocument save(RegionDocument doc) {
        return mongoRepository.save(doc);
    }

    @Override
    public RegionDocument getById(UUID uuid) {
        return mongoRepository.findById(uuid).orElseThrow();
    }

    @Override
    public List<RegionDocument> findAll() {
        return mongoRepository.findAll();
    }

    @Override
    public RegionDocument patch(UUID id, RegionDocument updatedRegion) {
        RegionDocument rd = getById(id);
        if (updatedRegion.getIsActive() != null) {
            rd.setIsActive(rd.getIsActive());
        }
        if (StringUtils.isNotBlank(updatedRegion.getSearchKey())) {
            rd.setSearchKey(updatedRegion.getSearchKey());
        }
        if (StringUtils.isNotBlank(updatedRegion.getDescription())) {
            rd.setDescription(updatedRegion.getDescription());
        }
        if (StringUtils.isNotBlank(updatedRegion.getName())) {
            rd.setName(updatedRegion.getName());
        }
        if (StringUtils.isNotBlank(updatedRegion.getLastModifiedBy())) {
            rd.setLastModifiedBy(updatedRegion.getLastModifiedBy());
        }
        if (updatedRegion.getLastModifiedDate() != null) {
            rd.setLastModifiedDate(updatedRegion.getLastModifiedDate());
        }
        return mongoRepository.save(rd);
    }

    @Override
    public void delete(UUID id) {
        mongoRepository.deleteById(id);
    }

    @Override
    public List<RegionDocument> findAllActive(boolean isActive) {
        return mongoRepository.findByIsActive(isActive);
    }
}
