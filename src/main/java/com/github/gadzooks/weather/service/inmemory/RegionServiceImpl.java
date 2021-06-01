package com.github.gadzooks.weather.service.inmemory;

import com.github.gadzooks.weather.domain.inmemory.Region;
import com.github.gadzooks.weather.repository.inmemory.RegionRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository repo) {
        this.regionRepository = repo;
    }

    @Override
    public ImmutableList<Region> findAll() {
        return regionRepository.getRegions();
    }

    @Override
    public Region save(Region region) {
        return regionRepository.save(region);
    }

    @Override
    public Region findOne(final String id) {
        return getById(id);
    }

    @Override
    public Region patch(String id, Region updatedRegion) {
        Region region = findOne(id);
        region.patch(updatedRegion);
        return region;
    }

    @Override
    public void delete(String id) {
        Region region = findOne(id);
        regionRepository.delete(region);
    }

    private Region getById(final String id) {
        return regionRepository.getRegionByName(id);
    }
}
