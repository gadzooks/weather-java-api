package com.github.gadzooks.weather.service;

import com.github.gadzooks.weather.dto.Region;
import com.github.gadzooks.weather.repository.RegionRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final RegionRepository regionRepository;

    public PlaceServiceImpl(RegionRepository repo) {
        this.regionRepository = repo;
    }

    @Override
    public ImmutableList<Region> getRegions() {
        return regionRepository.getRegions();
    }

    @Override
    public Region createRegion(Region region) {
        return regionRepository.add(region);
    }

    @Override
    public Region updateRegion(String id, Region region) {
        final Region srcRegion = getById(id);
        srcRegion.update(region);
        return srcRegion;
    }

    @Override
    public void deleteRegion(String id) {
        regionRepository.remove(regionRepository.getRegionByName(id));
    }

    @Override
    public Region getRegionById(final String id) {
        return getById(id);
    }

    private Region getById(final String id) {
        return regionRepository.getRegionByName(id);
    }
}
