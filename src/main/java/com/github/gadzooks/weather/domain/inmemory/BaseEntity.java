package com.github.gadzooks.weather.domain.inmemory;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

    @Getter
    @Setter
    private Long id;

    public boolean isNew() {
        return this.id == null;
    }
}
