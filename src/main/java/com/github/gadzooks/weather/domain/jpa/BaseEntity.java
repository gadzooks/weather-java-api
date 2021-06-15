package com.github.gadzooks.weather.domain.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements Serializable {
    @Id
    //underlying db will provide the id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @PrePersist
    public void beforeCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
