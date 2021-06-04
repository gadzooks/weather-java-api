package com.github.gadzooks.weather.repository.inmemory;

import com.github.gadzooks.weather.exception.ResourceNotFoundException;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * TD repository : in-memory store for Objects.
 */
@Component
public abstract class CrudRepository<TD, ID> {

    private final Map<ID, TD> map = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(CrudRepository.class);

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

    public TD add(TD object, ID id) {
        if (map.get(id) == null) {
            map.put(id, object);
            return object;
        } else {
            return map.get(id);
        }
    }

    public TD save(TD object, ID id) {
        return map.put(id, object);
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
}
