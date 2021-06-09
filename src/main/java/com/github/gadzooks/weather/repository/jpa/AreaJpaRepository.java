package com.github.gadzooks.weather.repository.jpa;

import com.github.gadzooks.weather.domain.jpa.AreaJpa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
 * NOTE 1 :
 * Each of these defines its own functionality:
 *
 * CrudRepository provides CRUD functions
 * PagingAndSortingRepository provides methods to do pagination and sort records
 * JpaRepository provides JPA related methods such as flushing the persistence context and delete records in a batch
 * And so, because of this inheritance relationship, the JpaRepository contains the full API of CrudRepository and PagingAndSortingRepository.
 *
 * When we don't need the full functionality provided by JpaRepository and PagingAndSortingRepository, we can simply use the CrudRepository.
 *
 * NOTE 2 :
 * AreaJpaRepository uses AreaJpa which is annotated by @Entity annotation.
 * This tells Spring that AreaJpaRepository is a JPA repository and not any other kind
 */
public interface AreaJpaRepository extends CrudRepository<AreaJpa, Long> {
    List<AreaJpa> findAllByNameContainingOrDescriptionContaining(String name, String description);
}
