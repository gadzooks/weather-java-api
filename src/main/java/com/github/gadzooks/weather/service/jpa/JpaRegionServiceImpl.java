package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import com.github.gadzooks.weather.mapstruct.dtos.LocationSlimDto;
import com.github.gadzooks.weather.mapstruct.dtos.RegionDto;
import com.github.gadzooks.weather.mapstruct.mappers.LocationJpaMapper;
import com.github.gadzooks.weather.mapstruct.mappers.RegionJpaMapper;
import com.github.gadzooks.weather.repository.jpa.RegionJpaRepository;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class JpaRegionServiceImpl implements JpaRegionService {
    private final RegionJpaRepository repository;
    private final RegionJpaMapper regionJpaMapper;
    private final LocationJpaMapper locationJpaMapper;

    public JpaRegionServiceImpl(RegionJpaRepository repository, RegionJpaMapper regionJpaMapper, LocationJpaMapper locationJpaMapper) {
        this.repository = repository;
        this.regionJpaMapper = regionJpaMapper;
        this.locationJpaMapper = locationJpaMapper;
    }

    // method to test mapstruct
    public List<RegionDto> findAllDtos() {
        List<RegionJpa> entities = findAll();
        List<RegionDto> dtos = new ArrayList<>();
        entities.forEach(en -> {
            RegionDto dto = regionJpaMapper.toDto(en);
            Set<LocationSlimDto> locs = new HashSet<>();
            en.getLocationJpas().forEach(l ->  {
                locs.add(locationJpaMapper.toDto(l));
            });
            dto.setLocations(locs);

            dtos.add(dto);
        });

        return dtos;
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
        if (updatedRegion.getName() != null) {
            region.setName(updatedRegion.getName());
        }
        if (updatedRegion.getDescription() != null) {
            region.setDescription(updatedRegion.getDescription());
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
