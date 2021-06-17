package com.github.gadzooks.weather.domain.mongo;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.*;

import java.time.LocalDateTime;

@Data
@ToString
public abstract class AbstractDocument {
    @Id
    protected String id;

    //FIXME : use header to get created/updated by
    @CreatedBy
    protected String createdBy;

    @CreatedDate
    protected LocalDateTime createdDate;

    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;

    @LastModifiedBy
    protected String lastModifiedBy;
}
