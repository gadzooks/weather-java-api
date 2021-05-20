package com.github.gadzooks.weather.service;

import com.github.gadzooks.weather.configuration.RegionConfig;
import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final RegionConfig regionConfig;

    public PlaceServiceImpl(RegionConfig region) {
        this.regionConfig = region;
    }

    @Override
    public List<RegionConfig.Region> getRegions() {
        return regionConfig.getRegions();
    }

    @Override
    public RegionConfig createRegion(RegionConfig region) {
        return null;
    }

    @Override
    public RegionConfig.Region updateRegion(String id, RegionConfig.Region region) {
        final RegionConfig.Region srcRegion = getById(id);
        srcRegion.update(region);
        return srcRegion;
    }

    @Override
    public void deleteRegion(String id) {
        final RegionConfig.Region region = getById(id);
        region.delete();
    }

    @Override
    public RegionConfig.Region getRegionById(final String id) {
        return getById(id);
    }

    private RegionConfig.Region getById(final String id) {
        RegionConfig.Region r = regionConfig.getRegionByName(id);
        if(r == null) {
            throw new ResourceNotFoundException("Regions",id);
        }
        return r;
    }
}
