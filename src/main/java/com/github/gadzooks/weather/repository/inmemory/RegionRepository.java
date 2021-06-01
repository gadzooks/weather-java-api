package com.github.gadzooks.weather.repository.inmemory;

import com.github.gadzooks.weather.domain.inmemory.Location;
import com.github.gadzooks.weather.domain.inmemory.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Region repository : in-memory store for Regions.
 */
@Component
public class RegionRepository extends CrudRepository<Region, String> {

    private static final Logger log = LoggerFactory.getLogger(RegionRepository.class);

    public int addAll(final List<Region> moreRegions) {
        int initialCount = super.size();
        moreRegions.forEach(r -> {
            super.save(r,r.getName());
        });
        return super.size() - initialCount;
    }

    public int addLocations(List<Location> locations) {
        int numAdded = 0;
        for (Location l : locations) {
            if (super.containsKey(l.getRegionId())) {
                Region r = super.get(l.getRegionId());
                // NOTE : make defensive copy and add reference to parent region
                Location newLoc = Location.copyOf(l, r);
                r.getLocations().add(newLoc);
                log.debug("adding location : " + newLoc);
                numAdded++;
            } else {
                log.warn("location : " + l + " could not be added.");
            }
        }
        return numAdded;
    }

    public Region save(Region region) {
        return super.save(region, region.getName());
    }

}
