## Common How-To's

### How to set up dependencies and run the project

```shell
./mvnw clean install
./mvnw test
./mvnw spring-boot:run -Dspring.config.additional-location="file:./config/local/environment.properties"
```