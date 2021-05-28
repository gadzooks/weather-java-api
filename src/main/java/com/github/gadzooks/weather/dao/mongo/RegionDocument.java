package com.github.gadzooks.weather.dao.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "regions")
public class RegionDocument extends AbstractDocument {
    //FIXME : how to implement uniqueness constraint ??
    @Indexed(unique = true)
    //FIXME validations not working
    @NotNull
    @Min(5)
    private String name;
    @NotNull
    @Min(5)
    private String searchKey;
    private String description;
    @NotNull
    private Boolean isActive;
}
