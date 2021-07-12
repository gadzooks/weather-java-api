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
    //underlying will provide the id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    //Activates optimistic locking.
    //Prevents lost updates :
    //User1 partially updated entity1
    //User2 came and updated entity2 in between
    //User1 finishes its update and its like user2's updated never happened.
    //Using @Version and optimistic locking, hibernate will throw a OptimisticLockException exception
    @Version
    private Integer version;

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
