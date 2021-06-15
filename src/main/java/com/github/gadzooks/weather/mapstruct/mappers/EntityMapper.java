package com.github.gadzooks.weather.mapstruct;

import java.util.List;

public interface EntityMapper<D, E> {
    D toDto(E e);
    E toEntity(D d);

    List<D> toDto(List<E> eList);
    List<E> toEntity(List<D> dList);

}