package com.github.gadzooks.weather.service;

import com.github.gadzooks.weather.dto.Region;
import com.github.gadzooks.weather.repository.RegionRepository;
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
    public Region createRegion(Region region) {
        return regionRepository.add(region);
    }

    @Override
    public Region save(Region region) {
        return regionRepository.save(region);
    }

    @Override
    public void deleteRegion(String id) {
        regionRepository.remove(regionRepository.getRegionByName(id));
    }

    @Override
    public Region findOne(final String id) {
        return getById(id);
    }

    private Region getById(final String id) {
        return regionRepository.getRegionByName(id);
    }
}