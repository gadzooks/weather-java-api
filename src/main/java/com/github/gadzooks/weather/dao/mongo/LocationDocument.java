package com.github.gadzooks.weather.dao.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "locations")
public class LocationDocument extends AbstractDocument {
    @NotNull
    private String name;
    private String description;
    private float latitude;
    private float longitude;
    private String subRegion;
}
