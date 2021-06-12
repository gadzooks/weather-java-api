package com.github.gadzooks.weather.service.jpa;

import com.github.gadzooks.weather.commands.RegionCommand;

import java.util.List;

public interface PlacesService {
    /**
     * Search in area, region and location for searchString
     *
     * @param searchString the search string
     * @return the list of AreaJpa objects
     */
    List<RegionCommand> searchEveryWhere(String searchString);
}
