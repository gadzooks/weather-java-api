package com.github.gadzooks.weather.service.mongo;

import com.github.gadzooks.weather.api.v1.mapper.MongoRegionMapper;
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
    private final MongoRegionRepository mongoRepository;
    private final MongoRegionMapper mapper;

    public MongoRegionServiceImpl(MongoRegionRepository mongoRepository, MongoRegionMapper mapper) {
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    @Override
    public RegionDTO findById(String id) {
        RegionDocument r = mongoRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(RegionDocument.class.getName(), id);
        });

        return mapper.toDto(r);
    }

    @Override
    public RegionDTO save(RegionDTO doc) {
        RegionDocument rd = mapper.toEntity(doc);
        RegionDocument saved = mongoRepository.save(rd);
        return mapper.toDto(saved);
    }

    @Override
    public RegionDTO getById(String id) {
        return findById(id);
    }

    @Override
    public List<RegionDTO> findAll() {
        return mapper.toDto(mongoRepository.findAll());
    }

    @Override
    public RegionDTO patch(String id, RegionDTO updatedRegion) {
        RegionDocument rd = mongoRepository.findById(id).orElseThrow(() -> {
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
        RegionDocument saved = mongoRepository.save(rd);
        return mapper.toDto(saved);
    }

    @Override
    public void delete(String id) {
        mongoRepository.deleteById(id);
    }

    @Override
    public List<RegionDTO> findAllActive(boolean isActive) {
        return mapper.toDto(mongoRepository.findByIsActive(isActive));
    }
}
