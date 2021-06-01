package com.github.gadzooks.weather.service.inmemory;

import com.github.gadzooks.weather.domain.inmemory.Region;
import com.github.gadzooks.weather.service.CrudService;

// @Service annotation here does not create a Bean of the implemented class
public interface RegionService extends CrudService<Region, String> {
}
