package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.commands.RegionCommand;
import com.github.gadzooks.weather.service.CrudService;

import java.util.List;

public interface JpaRegionService extends CrudService<RegionCommand, Long> {
    List<RegionCommand> findAllActive();

    List<RegionCommand> findAllByIdsIn(List<Long> ids);
}
