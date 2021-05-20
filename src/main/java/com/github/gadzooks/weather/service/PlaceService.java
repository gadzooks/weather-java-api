package com.github.gadzooks.weather.service;

import com.github.gadzooks.weather.configuration.RegionConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceService {
    List<RegionConfig.Region> getRegions();
    RegionConfig createRegion(RegionConfig region);
    RegionConfig.Region updateRegion(String id, RegionConfig.Region region);
    void deleteRegion(String id);
    RegionConfig.Region getRegionById(String id);
}
