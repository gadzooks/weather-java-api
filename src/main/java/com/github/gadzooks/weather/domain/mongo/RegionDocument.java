package com.github.gadzooks.weather.domain.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "regions")
public class RegionDocument extends AbstractDocument {
    //FIXME : how to implement uniqueness constraint ??
    @Indexed(unique = true)
    @NotBlank
    private String name;
    @NotBlank
    private String searchKey;
    private String description;
    @NotNull
    private Boolean isActive;

    @DBRef()
    private List<LocationDocument> locations = new ArrayList<>();
}
