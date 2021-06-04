package com.github.gadzooks.weather.service.inmemory;

import com.github.gadzooks.weather.domain.inmemory.Region;
import com.github.gadzooks.weather.repository.inmemory.RegionRepository;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j //automatically creates private static final log attribute
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository repo) {
        this.regionRepository = repo;
    }

    @Override
    public ImmutableList<Region> findAll() {
        return regionRepository.findAll();
    }

    @Override
    public Region save(Region region) {
        return regionRepository.save(region);
    }

    @Override
    public Region getById(String s) {
        return regionRepository.findById(s);
    }

    @Override
    public Region patch(String id, Region updatedRegion) {
        Region region = getById(id);
        region.patch(updatedRegion);
        return region;
    }

    @Override
    public void delete(String id) {
        Region region = getById(id);
        log.info("region to be deleted : " + region);
        regionRepository.delete(id);
        log.info("lookup region that was deleted : " + regionRepository.containsKey(id));
    }

}
