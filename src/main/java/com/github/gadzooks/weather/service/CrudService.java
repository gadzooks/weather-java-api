package com.github.gadzooks.weather.service;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> findAll();
    T save(T model);
    T findOne(ID id);
    T patch(ID id, T updatedRegion);
    void delete(ID id);
}
