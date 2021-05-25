package com.github.gadzooks.weather.configuration;

import io.swagger.annotations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@SwaggerDefinition(
        info = @Info(
                description = "Get local WA state weather from DarkSKY/Visual crossing",
                version = "V0.0.1",
                title = "The Weather API",
                termsOfService = "travelweather.co/tos",
                contact = @Contact(
                        name = "Amit K",
                        email = "k@gmail.com",
                        url = "travelweather.co"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/json", "application/xml"},
        produces = {"application/json", "application/xml"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        tags = {
                @Tag(name = "Private", description = "Tag used to denote operations as private")
        },
        externalDocs = @ExternalDocs(value = "Meteorology", url = "http://theweatherapi.io/meteorology.html")
)
//http://localhost:8080/swagger-ui/index.html
public class SwaggerConfig {
    //https://springfox.github.io/springfox/docs/current/#springfox-spring-mvc-and-spring-boot
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.gadzooks.weather"))
                .build();
    }
}
