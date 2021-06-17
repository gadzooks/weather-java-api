package com.github.gadzooks.weather.repository.inmemory;

import com.github.gadzooks.weather.domain.inmemory.Location;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier(LocationRepository.QUALIFIER)
public class LocationRepository extends CrudRepository<Location, Long>{
    public static final String QUALIFIER = "in-memory";
}
