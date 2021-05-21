package com.github.gadzooks.weather.repository;

import com.github.gadzooks.weather.dto.Region;
import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegionRepository {

    private final List<Region> regions = new ArrayList<>();
    private final Map<String, Region> regionsById = new HashMap<>();

    public int addAll(final List<Region> moreRegions) {
        int initialCount = regions.size();
        moreRegions.forEach(r -> {
            if (!regionsById.containsKey(r.getName())) {
                regionsById.put(r.getName(), r);
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
        if (regionsById.get(region.getName()) == null) {
            regionsById.put(region.getName(), region);
            regions.add(region);
        }
        return region;
    }

    public void remove(Region region) {
        if (regionsById.containsKey(region.getName())) {
            regionsById.remove(region.getName());
            regions.remove(region);
        }
    }

    public void update(Region region) {
        if (regionsById.containsKey(region.getName())) {
            regionsById.put(region.getName(), region);
        } else {
            regionsById.put(region.getName(), region);
            regions.add(region);
        }

    }
}
