package com.github.gadzooks.weather.service.mongo;

import com.github.gadzooks.weather.api.v1.model.RegionDTO;
import com.github.gadzooks.weather.service.CrudService;

import java.util.List;

// @Service annotation here does not create a Bean of the implemented class
public interface MongoRegionService extends CrudService<RegionDTO, String> {
    RegionDTO findById(String id);

    List<RegionDTO> findAllActive(boolean isActive);
}
