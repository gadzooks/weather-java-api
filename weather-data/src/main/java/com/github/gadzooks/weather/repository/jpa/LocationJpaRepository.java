package com.github.gadzooks.weather.repository.jpa;

import com.github.gadzooks.weather.domain.jpa.LocationJpa;
import org.springframework.data.repository.CrudRepository;

public interface LocationJpaRepository extends CrudRepository<LocationJpa, Long> {
}
