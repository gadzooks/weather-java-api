package com.github.gadzooks.weather.service;

import com.github.gadzooks.weather.dto.Region;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

@Service
public interface RegionService {
    ImmutableList<Region> findAll();
    Region save(Region region);
    void deleteRegion(String id);
    Region findOne(String id);
    Region patch(String id, Region updatedRegion);
}
