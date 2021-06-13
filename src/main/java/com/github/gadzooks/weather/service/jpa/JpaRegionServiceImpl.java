package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.commands.RegionCommand;
import com.github.gadzooks.weather.converters.*;
import com.github.gadzooks.weather.domain.jpa.AreaJpa;
import com.github.gadzooks.weather.domain.jpa.RegionJpa;
import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import com.github.gadzooks.weather.repository.jpa.RegionJpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JpaRegionServiceImpl implements JpaRegionService {
    private final RegionJpaRepository repository;
    private final RegionJpaCommandToRegionConverter toRegionConverter;
    private final RegionCommandToRegionJpaConverter toRegionJpaConverter;
    private final LocationJpaCommandToLocationConverter toLocationConverter;
    private final LocationCommandToLocationJpaConverter toLocationJpaConverter;
    private final AreaCommandToAreaJpaConverter toAreaJpaConverter;

    public JpaRegionServiceImpl(RegionJpaRepository repository, RegionJpaCommandToRegionConverter toRegionConverter,
                                RegionCommandToRegionJpaConverter toRegionJpaConverter,
                                LocationJpaCommandToLocationConverter toLocationConverter,
                                LocationCommandToLocationJpaConverter toLocationJpaConverter, AreaCommandToAreaJpaConverter toAreaJpaConverter) {
        this.repository = repository;
        this.toRegionConverter = toRegionConverter;
        this.toRegionJpaConverter = toRegionJpaConverter;
        this.toLocationConverter = toLocationConverter;
        this.toLocationJpaConverter = toLocationJpaConverter;
        this.toAreaJpaConverter = toAreaJpaConverter;
    }

    @Override
    public List<RegionCommand> findAll() {
        List<RegionCommand> results = new ArrayList<>();
        repository.findAll().forEach(jpa -> {
            RegionCommand rc = toRegionConverter.convert(jpa);
            results.add(rc);
        });
        return results;
    }

    @Override
    public RegionCommand save(RegionCommand model) {
        RegionJpa region = toRegionJpaConverter.convert(model);
        return toRegionConverter.convertRegionIncludingLocationAndArea(repository.save(region));
    }

    @Override
    public RegionCommand getById(Long aLong) {
        RegionJpa regionJpa = findById(aLong);
        return toRegionConverter.convertRegionIncludingLocationAndArea(regionJpa);
    }

    @Override
    public RegionCommand patch(Long aLong, RegionCommand updatedRegion) {
        RegionJpa region = findById(aLong);

        if (updatedRegion.getAreaCommand() != null) {
            AreaJpa areaJpa = toAreaJpaConverter.convert(updatedRegion.getAreaCommand());
            region.setAreaJpa(areaJpa);
        }
        if (updatedRegion.getIsActive() != null) {
            region.setIsActive(updatedRegion.getIsActive());
        }
        if (updatedRegion.getLocationCommandSet() != null) {
            updatedRegion.getLocationCommandSet().forEach(l -> {
                region.addLocation(toLocationJpaConverter.convert(l));

            });
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
        RegionJpa savedRegion = repository.save(region);
        return toRegionConverter.convertRegionIncludingLocationAndArea(savedRegion);
    }

    @Override
    public void delete(Long aLong) {
        RegionJpa regionJpa = findById(aLong);
        repository.delete(regionJpa);

    }

    @Override
    public List<RegionCommand> findAllActive() {
        return toRegionConverter.convertAllRegionsIncludingLocation(repository.findAllByIsActive(true));
    }

    @Override
    public List<RegionCommand> findAllByIdsIn(List<Long> ids) {
       return toRegionConverter.convertAllRegionsIncludingLocation(repository.findAllByIdIn(ids));
    }

    private RegionJpa findById(Long id) {
        return repository.findById(id).
                orElseThrow(() -> {
                    throw new ResourceNotFoundException(RegionJpa.class.getName(), id.toString());
                });
    }
}
