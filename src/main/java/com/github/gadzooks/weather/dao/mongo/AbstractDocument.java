package com.github.gadzooks.weather.dao.mongo;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString
public abstract class AbstractDocument {
    @Id
    private UUID id = UUID.randomUUID();

    //FIXME : Automatically set up Audit values
    @CreatedDate
    private LocalDateTime createdDate;

    //FIXME : use header to get created/updated by
    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;
}
