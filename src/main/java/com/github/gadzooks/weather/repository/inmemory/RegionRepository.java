package com.github.gadzooks.weather.repository.inmemory;

import com.github.gadzooks.weather.domain.inmemory.Location;
import com.github.gadzooks.weather.domain.inmemory.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Region repository : in-memory store for Regions.
 */
@Component
@Slf4j
public class RegionRepository extends CrudRepository<Region, Long> {

    public int addLocations(List<Location> locations) {
        final Map<String, Region> regionsByName = new HashMap<>();
        findAll().forEach((k) -> regionsByName.put(k.getName(), k));
        regionsByName.forEach((k,v) -> log.debug("key = " + k + " value = " + v));
        int numAdded = 0;
        for (Location l : locations) {
            if (regionsByName.containsKey(l.getRegionName())) {
                Region r = regionsByName.get(l.getRegionName());
                r.getLocations().add(l);
                l.setRegionId(r.getId());
                log.debug("adding location : " + l);
                numAdded++;
            } else {
                log.warn("location : " + l + " could not be added.");
            }
        }
        return numAdded;
    }

}
