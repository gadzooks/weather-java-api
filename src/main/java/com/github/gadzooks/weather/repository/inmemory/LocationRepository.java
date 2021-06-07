package com.github.gadzooks.weather.repository.inmemory;

import com.github.gadzooks.weather.domain.inmemory.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationRepository extends CrudRepository<Location, Long>{
}
