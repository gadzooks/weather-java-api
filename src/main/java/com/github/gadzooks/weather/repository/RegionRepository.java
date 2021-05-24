package com.github.gadzooks.weather.repository;

import com.github.gadzooks.weather.dto.Location;
import com.github.gadzooks.weather.dto.Region;
import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegionRepository {

    private final List<Region> regions = new ArrayList<>();
    private final Map<String, Region> regionsById = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(RegionRepository.class);

    public int addAll(final List<Region> moreRegions) {
        int initialCount = regions.size();
        moreRegions.forEach(r -> {
            if (!regionsById.containsKey(r.getId())) {
                regionsById.put(r.getId(), r);
                regions.add(r);
            }
        });
        return regions.size() - initialCount;
    }

    public Region getRegionByName(final String id) {
        Region r = regionsById.get(id);
        if (r == null) {
            throw new ResourceNotFoundException("Regions", id);
        }
        return r;
    }


    /**
     * Returns Immutable copy of list of Regions.
     * We do NOT want to allow consumers of this method to directly change regions
     * list so we return an immutable copy
     *
     * @return the regions
     */
    public ImmutableList<Region> getRegions() {
        return ImmutableList.copyOf(regions);
    }

    public Region add(Region region) {
        if (regionsById.get(region.getId()) == null) {
            regionsById.put(region.getId(), region);
            regions.add(region);
        }
        return region;
    }

    public void remove(Region region) {
        if (regionsById.containsKey(region.getId())) {
            regionsById.remove(region.getId());
            regions.remove(region);
        }
    }

    public void update(Region region) {
        if (regionsById.containsKey(region.getId())) {
            regionsById.put(region.getId(), region);
        } else {
            regionsById.put(region.getId(), region);
            regions.add(region);
        }

    }

    public int addLocations(List<Location> locations) {
        int numAdded = 0;
        for (Location l : locations) {
            if (regionsById.containsKey(l.getRegionId())) {
                Region r = regionsById.get(l.getRegionId());
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
        if (regionsById.containsKey(region.getId())) {
            Region currentRegion = regionsById.get(region.getId());
            currentRegion.update(region);
            return currentRegion;
        } else {
            regionsById.put(region.getId(), region);
            regions.add(region);
            return region;
        }
    }
}
