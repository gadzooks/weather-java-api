package com.github.gadzooks.weather.repository.jpa;

import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationJpaRepository extends CrudRepository<LocationJpa, Long> {
    List<LocationJpa> findAllByNameContainingOrDescriptionContaining(String name, String description);
}
