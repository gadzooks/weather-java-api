package com.github.gadzooks.weather.repository.inmemory;

import com.github.gadzooks.weather.domain.inmemory.BaseEntity;
import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TD repository : in-memory store for Objects.
 */
@Component
@Slf4j
public abstract class CrudRepository<TD extends BaseEntity, ID extends Long> {

    private final Map<Long, TD> map = new HashMap<>();

    /**
     * Gets TD by id.
     *
     * @param id the id of the TD
     * @return the TD by name
     * @throws ResourceNotFoundException if TD with id is not found
     */
    public TD findById(final ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        TD r = map.get(id);
        if (r == null) {
            log.info("Did not find resource with id : " + id );
            throw new ResourceNotFoundException("Regions", id.toString());
        }
        return r;
    }

    public int size() {
        return map.size();
    }

    /**
     * Returns Immutable copy of list of Regions.
     * We do NOT want to allow consumers of this method to directly change regions
     * list so we return an immutable copy
     *
     * @return ImmutableList of all TD objects
     */
    public ImmutableList<TD> findAll() {
        return ImmutableList.copyOf(map.values());
    }

    public TD add(TD object) {
        if(object != null) {
            if(object.getId() == null) {
                object.setId(getNextId());
            }
            map.put(object.getId(), object);
        }
        return object;
    }

    public TD save(TD object) {
        return add(object);
    }

    public int addAll(final List<TD> moreObjects) {
        int initialCount = size();
        moreObjects.forEach(this::save);
        return size() - initialCount;
    }

    public void delete(ID id) {
        map.remove(id);
    }

    public TD get(ID id) {
        return map.get(id);
    }

    public boolean containsKey(ID id) {
        return map.containsKey(id);
    }

    private Long getNextId() {
        if(map.isEmpty()) {
            return 1L;
        } else {
            Long id = Collections.max(map.keySet()) + 1L;
            return id;
        }
    }
}
