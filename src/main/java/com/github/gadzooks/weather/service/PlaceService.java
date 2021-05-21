package com.github.gadzooks.weather.service;

import com.github.gadzooks.weather.dto.Region;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

@Service
public interface PlaceService {
    ImmutableList<Region> getRegions();

    Region createRegion(Region region);

    Region updateRegion(String id, Region region);

    void deleteRegion(String id);

    Region getRegionById(String id);
}
