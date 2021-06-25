package com.github.gadzooks.weather.service.mongo;

import com.github.gadzooks.weather.api.v1.mapper.mongo.MongoRegionMapper;
import com.github.gadzooks.weather.api.v1.model.RegionDTO;
import com.github.gadzooks.weather.domain.mongo.RegionDocument;
import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import com.github.gadzooks.weather.repository.mongo.MongoRegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service //NOTE: @Service annotation is required HERE to create a bean.
// We cannot use the @Service annotation on the Interface
public class MongoRegionServiceImpl implements MongoRegionService {
    private final MongoRegionRepository mongoRegionRepository;
    private final MongoRegionMapper mapper;

    public MongoRegionServiceImpl(MongoRegionRepository mongoRegionRepository, MongoRegionMapper mapper) {
        this.mongoRegionRepository = mongoRegionRepository;
        this.mapper = mapper;
    }

    @Override
    public RegionDTO findById(String id) {
        RegionDocument r = mongoRegionRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(RegionDocument.class.getName(), id);
        });

        return mapper.toDto(r);
    }

    @Override
    public RegionDTO findByName(String name) {
        RegionDocument rd = mongoRegionRepository.findByName(name);
        return mapper.toDto(rd);
    }

    @Override
    public RegionDTO save(RegionDTO doc) {
        RegionDocument rd = mapper.toEntity(doc);
        RegionDocument saved = mongoRegionRepository.save(rd);
        return mapper.toDto(saved);
    }

    @Override
    public RegionDTO getById(String id) {
        return findById(id);
    }

    @Override
    public List<RegionDTO> findAll() {
        return mapper.toDto(mongoRegionRepository.findAll());
    }

    @Override
    public RegionDTO patch(String id, RegionDTO updatedRegion) {
        RegionDocument rd = mongoRegionRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(RegionDocument.class.getName(), id);
        });

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
        RegionDocument saved = mongoRegionRepository.save(rd);
        return mapper.toDto(saved);
    }

    @Override
    public void delete(String id) {
        mongoRegionRepository.deleteById(id);
    }

    @Override
    public List<RegionDTO> findAllActive(boolean isActive) {
        return mapper.toDto(mongoRegionRepository.findByIsActive(isActive));
    }
}
