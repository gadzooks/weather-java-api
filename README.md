# REST Weather API

## TODO

1. call weather API and return results
2. store weather api in Mongo
3. store weather api in PostGres/MySQL with JPA
4. store weather api data in PostGres/MySql with hibernate
5. v1 return weather api from Mongo
6. v2 return weather data from JPA, hibernate
7. POSTMAN / newman tests for CRUD operations
8. Code coverage

- /v1 path - load locations, regions from files into POJOs
- GET /forecasts -> return forecasts for all locations by calling DarkSKY API
- swagger docs
- run locally with docker-compose

Maven
Spring Boot, Spring Data, Spring Security
JPA/Hibernate, Liquibase/Flyway
JUnit, Test Containers, Mockito, Mock Server
Open API, Swagger
Jenkins, DockerAWS, ECS, EC2

## DONE : 
- CRUD REST api for Region entity
- Load Region/Location from yml using CommandLineRunner
- HATEOAS support for Region entity 
- SWAGGER docs
