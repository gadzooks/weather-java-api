# TODO

- https://www.udemy.com/course/microservices-with-spring-boot-and-spring-cloud/learn/lecture/8005642#overview
- https://www.logicbig.com/tutorials/spring-framework/spring-core/spring-profiles.html

## Smaller areas :

- set flag in test to avoid reloading Mongo db data, useful for integration testing

## Cover these broad areas :

- Profiles (test, dev, qa, prod) - need different resources for data storage etc
- MongoDB
    - Validations
    - Test end to end
    - Implement custom method in RepoImpl with query
    - move username / pass to ENV or something

    - Do a transactional operation : move location from 1 region to another
    - Auditing
      using : https://docs.spring.io/spring-data/jpa/docs/1.7.0.DATAJPA-580-SNAPSHOT/reference/html/auditing.html

- Class generators
    - Use XSD to create POJO
    - Use JSON to create another POJO
- JPA
- Hibernate
- LiquidBase/Flyway : https://www.liquibase.org/liquibase-vs-flyway (DB Migrations)
- FEIGN REST Client
- Actuator : https://spring.io/guides/gs/actuator-service/
- Spring Security / spring session
    - https://medium.com/@gtommee97/rest-authentication-with-spring-security-and-mongodb-28c06da25fb1
- Spring Integration / messaging
- JMX
- Docker compose
    - Build executable jar/war and deploy
- Spring Apache Kafka
- Spring Batch
- Jenkins
- Versioning of APIs
- Caching

## IF time permits

- Spock testing framework : https://spockframework.org/spock/docs/2.0/spock_primer.html
- Java test containers : https://www.testcontainers.org