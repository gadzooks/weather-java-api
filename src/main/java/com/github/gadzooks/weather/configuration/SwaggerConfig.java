package com.github.gadzooks.weather.configuration;

import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//        SWAGGER Related docs
//http://localhost:8080/swagger-ui/index.html
//https://editor.swagger.io

@Configuration
//@SwaggerDefinition(
//        info = @Info(
//                description = "Get local WA state weather from DarkSKY/Visual crossing",
//                version = "V0.0.1",
//                title = "The Weather API",
//                termsOfService = "travelweather.co/tos",
//                contact = @Contact(
//                        name = "Amit K",
//                        email = "karwande@gmail.com",
//                        url = "http://github.com/gadzooks"
//                ),
//                license = @License(
//                        name = "Apache 2.0",
//                        url = "http://www.apache.org/licenses/LICENSE-2.0"
//                )
//        ),
//        consumes = {"application/json", "application/xml"},
//        produces = {"application/json", "application/xml"},
//        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
//        tags = {
//                @Tag(name = "Private", description = "Tag used to denote operations as private")
//        },
//        externalDocs = @ExternalDocs(value = "Meteorology", url = "http://theweatherapi.io/meteorology.html")
//)
public class SwaggerConfig {
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.github.gadzooks.weather"))
//                .build();
//    }
}
