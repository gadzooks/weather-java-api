package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import com.github.gadzooks.weather.repository.jpa.RegionJpaRepository;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaRegionServiceImpl implements JpaRegionService {
    private final RegionJpaRepository repository;

    public JpaRegionServiceImpl(RegionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RegionJpa> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public RegionJpa save(RegionJpa model) {
        return repository.save(model);
    }

    @Override
    public RegionJpa getById(Long aLong) {
        return repository.findById(aLong).
                orElseThrow(() -> {
                    throw new ResourceNotFoundException(RegionJpa.class.getName(), aLong.toString());
                });
    }

    @Override
    public RegionJpa patch(Long aLong, RegionJpa updatedRegion) {
        RegionJpa region = getById(aLong);
        if (updatedRegion.getAreaJpa() != null) {
            region.setAreaJpa(updatedRegion.getAreaJpa());
        }
        if (updatedRegion.getIsActive() != null) {
            region.setIsActive(updatedRegion.getIsActive());
        }
        if (updatedRegion.getLocationJpas() != null) {
            region.addAllLocations(updatedRegion.getLocationJpas());
        }
        if (updatedRegion.getSearchKey() != null) {
            region.setSearchKey(updatedRegion.getSearchKey());
        }
        return repository.save(region);
    }

    @Override
    public void delete(Long aLong) {
        RegionJpa region = getById(aLong);
        repository.delete(region);

    }

    @Override
    public List<RegionJpa> findAllActive() {
        return repository.findAllByIsActive(true);
    }

    @Override
    public List<RegionJpa> findAllByIdsIn(List<Long> ids) {
        return repository.findAllByIdIn(ids);
    }
}
