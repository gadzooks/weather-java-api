package com.github.gadzooks.weather.service;

import com.github.gadzooks.weather.dto.Region;
import com.google.common.collect.ImmutableList;

// @Service annotation here does not create a Bean of the implemented class
public interface RegionService {
    ImmutableList<Region> findAll();
    Region save(Region region);
    Region findOne(String id);
    Region patch(String id, Region updatedRegion);
    void delete(String id);
}
