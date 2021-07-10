# REST Weather API

[![CircleCI](https://circleci.com/gh/gadzooks/weather-java-api.svg?style=svg)](https://app.circleci.com/pipelines/github/gadzooks/weather-java-api)
Circle CI test status

[![codecov](https://codecov.io/gh/gadzooks/weather-java-api/branch/master/graph/badge.svg)](https://codecov.io/gh/gadzooks/weather-java-api)

# Design Patterns

Code sample to demonstrate :

0. [12-Factor compliant app](https://12factor.net) :
   1. Codebase : git
   2. Dependencies (Explicitly declare and isolate dependencies) : Using Maven to manage all dependencies.
   3. Config (Store config in the environment) : API keys are stored in ENV variables
   4. Backing services (Treat backing services as attached resources) : Loose coupling and using interfaces allows the
      app to swap out one DB service with another.
      ```java
      //All services implement this interface, which allows swapping them out easily
      public interface CrudService<T, ID> {
         List<T> findAll();
         T save(T model);
         T getById(ID id);
         T patch(ID id, T updatedRegion);
         void delete(ID id);
      }
      ```
   5. Build, release, run (Strictly separate build and run stages) : Build, release managed via CircleCI
   6. Processes (Execute the app as one or more stateless processes) : app itself is stateless. All state is either
      loaded into memory at boot time or stored in permanent storage like a Database
   7. Port binding (Export services via port binding) : listens to 8080, creates a FAT executable jar with no
      dependencies other than JAVA
   8. Concurrency (Scale out via the process model) : Using Docker and Kubernetes would allow for easy scaling
   9. Disposability (Maximize robustness with fast startup and graceful shutdown) : Application exposes idempotent
      services. No long running tasks are executed as part of a request.
   10. Dev/prod parity (Keep development, staging, and production as similar as possible) : WIP. Any differences between
       dev/staging/prod are kept in ENV variables. Docker allows us to deploy the same code base with different ENVs
       easily
   11. Logs (Treat logs as event streams) : SL4J handles logging for the app. Next steps would be to use ELK stack to
       capture and analyze logs.
   12. Admin processes (Run admin/management tasks as one-off processes) : TODO - Write some sample scripts to display
       one off tasks.

1. Code organization by functionality into packages
2. CRUD operations and REST API (with HATEOS)
   1. With In-memory model
   2. With JPA entities
   3. With Mongo documents
   4. With external API for getting weather forecast
3. How to load application state at startup via CommandLineRunner
4. Swagger for documenation
5. Junit5 for testing
6. Some of the design patterns used :
   1. MVC for REST controllers
   2. Data transfer objects to de-couple domain layer from service layer
   3. Advice controller for common controller functionality
   4. Coding to interfaces (Ex: )
   5. Abstract base classes for JPA entities, Mongo Documents to handle common functionality like auditing

## Software Engineering

### Use libraries to minimize code :

1. lombok for getters/setters/contructors/builders
2. Mapstruct for converting DTOs to Entities and back

### Organize code efficiently :

These are the packages in which code is organized :

1. mapper : MapStruct mappers to translate between DTO and Entity objects
2. model : DTOs
3. bootstrap : all code that needs to run once at startup goes here
4. configuration : all jpa/mongo/swagger etc config
5. controller : all REST controllers / advice controllers
6. domain : domain classes representing the business domains
7. exception : custom exceptions
8. repository : classes implementing the repository pattern (dealing with storing / retrieving entities)
9. service : service classes which contain business logic.

### DTOs to decouple DB layer from "view" layer

```java
// Service class
@Override
public RegionDTO findByName(String name){
        RegionDocument rd=mongoRegionRepository.findByName(name);
        return mapper.toDto(rd);
        }
```

### Use Mapstruct mapper to automate converting from DTO to Entities and back

```java

@Mapper
public interface DailyForecastMapper extends EntityMapper<DailyForecastDTO, DailyForecast> {
   DailyForecastMapper INSTANCE = Mappers.getMapper(DailyForecastMapper.class);

   @Override
   DailyForecastDTO toDto(DailyForecast entity);

   @Override
   DailyForecast toEntity(DailyForecastDTO dto);

   @Override
   List<DailyForecastDTO> toDto(List<DailyForecast> eList);

   @Override
   List<DailyForecast> toEntity(List<DailyForecastDTO> dList);
}
```

### Swagger documentation support for all APIs

```java

@Configuration
@EnableSwagger2
@SwaggerDefinition(....)
public class SwaggerConfig {
   @Bean
   public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
              .select()
              .apis(RequestHandlerSelectors.basePackage("com.github.gadzooks.weather"))
              .build();
   }
}

```

### Read sensitive values like API Keys from ENV

```java

@Configuration
public class VisualCrossingConfig {
   //NOTE : read from ENV variable
   @Getter
   private final String visualCrossingApiKey;

   @Getter
   private final String vcUrl;

   //Set default value for ENV variable so that tests pass on CircleCI without
   //without needing to add ENV variables there
   public VisualCrossingConfig(@Value("${VISUAL_CROSSING_API_KEY:vc.api.key.here}") String visualCrossingApiKey,
                               @Value("${visualcrossing.api.url}") String vcUrl) {
      this.visualCrossingApiKey = visualCrossingApiKey;
      this.vcUrl = vcUrl;
   }
}
```

## Spring related

### Use command line runner to load data after Spring boot in loaded

```java
// lower values have higher priority
@Order(value = 1)
@Component
@ConfigurationProperties(prefix = "file-paths", ignoreUnknownFields = false)
public class LoadRegionsDatabase implements CommandLineRunner {
   @Override
   public void run(String... args) {
   }
}
```

```java

@Order(value = 10) //lower values have higher priority
@Component
@Slf4j
public class LoadRegionsDatabaseJpa implements CommandLineRunner {
   @Override
   public void run(String... args) {
   }
}
```

### Break up properties by functionality

```java

@Configuration
//all files under src/main/resources are copied to the packaged jar/war files
//and are available in the classpath
//PropertySource is needed since properties are NOT in application.properties or application.yml
@PropertySource("classpath:in-memory.yml")
@Data
public class WeatherPropertiesConfiguration {

   private final String regionsFile;
   private final String locationsFile;

   public WeatherPropertiesConfiguration(
           @Value("${regionsFile}") String regionsFile,
           @Value("${locationsFile}") String locationsFile) {
      this.regionsFile = regionsFile;
      this.locationsFile = locationsFile;
   }
}
```

```yaml
regionsFile: src/main/resources/regions.yml
locationsFile: src/main/resources/locations.yml
```

### Keep common Controller code in Advice controller

```java
//Ex : return 404 for 'resource not found' errors
@RestControllerAdvice
class ExceptionHandlingAdvice extends ResponseEntityExceptionHandler {

   @ResponseBody
   @ExceptionHandler(value = {NoSuchElementException.class, ResourceNotFoundException.class})
   @ResponseStatus(HttpStatus.NOT_FOUND)
   ResponseEntity<?> noSuchElementHandler(Exception ex) {
      return ResponseEntity.notFound().build();
   }
}
```

### Well-designed (skinny) REST controller with full HATEOAS support

```java
//Swagger documentation
@Api(
        value = "Region",
        description = "",
        tags = {"REST API for Regions with locations"} // way to group HTTP operations together in Swagger
)
@RestController
//Allow requests from other domains
@CrossOrigin(origins = "*")
//set prefix of path of /place for all requests in PlaceController
@RequestMapping(path = "/regions", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegionController {

   private final RegionService regionService;

   //NOTE : using constructor injection to make it easier to test
   public RegionController(RegionService regionService) {
      this.regionService = regionService;
   }

   @GetMapping(value = "")
   @ApiOperation(value = "Find all regions",
           tags = {"REST API for Regions with locations"},
           notes = "This method returns all the regions")
   public List<EntityModel<Region>> findAll() {
      return regionService.findAll().stream().map(
              region -> EntityModel.of(region,
                      linkTo(methodOn(RegionController.class).findOne(region.getId())).withSelfRel(),
                      linkTo(methodOn(RegionController.class).findAll()).withRel("regions"))).
              collect(Collectors.toList());
   }

   @PatchMapping(value = "/{id}")
   @ApiOperation(value = "Update part of a region (patch)",
           tags = {"REST API for Regions with locations"},
           notes = "This method allows users to update **subset** of attributes of a region")
   public EntityModel<Region> patchRegion(
           @ApiParam(value = "Region Id of the region requested", example = "1") @PathVariable Long id,
           @ApiParam(value = "region object (can be partially set)") @RequestBody Region updatedRegion) {
      Region savedRegion = regionService.patch(id, updatedRegion);

      return EntityModel.of(savedRegion,
              linkTo(methodOn(RegionController.class).findOne(id)).withSelfRel(),
              linkTo(methodOn(RegionController.class).findAll()).withRel("regions"));
   }

   @PutMapping(value = "/{id}")
   @ApiOperation(value = "Update a region",
           tags = {"REST API for Regions with locations"},
           notes = "This method allows users to replace all attributes of a region")
   public EntityModel<Region> updateRegion(
           @ApiParam(value = "Region Id of the region requested", example = "1") @PathVariable Long id,
           @ApiParam(value = "Valid region object") @Valid @RequestBody Region updatedRegion) {
      Region savedRegion = regionService.save(updatedRegion);

      return EntityModel.of(savedRegion,
              linkTo(methodOn(RegionController.class).findOne(id)).withSelfRel(),
              linkTo(methodOn(RegionController.class).findAll()).withRel("regions"));
   }

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   @ApiOperation(value = "Create new region",
           tags = {"REST API for Regions with locations"},
           notes = "This method creates a new region")
   public EntityModel<Region> newRegion(
           @ApiParam(value = "Valid region object") @Valid @RequestBody Region region) {
      Region savedRegion = regionService.save(region);

      return EntityModel.of(savedRegion, //
              linkTo(methodOn(RegionController.class).findOne(savedRegion.getId())).withSelfRel(),
              linkTo(methodOn(RegionController.class).findAll()).withRel("regions"));
   }

   @DeleteMapping(value = "/{id}")
   @ApiOperation(value = "Delete a region",
           tags = {"REST API for Regions with locations"},
           notes = "This method deletes a region")
   public EntityModel<Region> deleteRegion(
           @ApiParam(value = "Region Id of the region requested", example = "1") @PathVariable Long id) {
      Region region = regionService.getById(id);
      regionService.delete(id);
      return EntityModel.of(region, //
              linkTo(methodOn(RegionController.class).findAll()).withRel("regions"));
   }
}

```

### JPA Entities

#### Use BaseEntity to capture common functionality

```java

@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements Serializable {
   @Id
   //underlying will provide the id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   protected Long id;

   protected LocalDateTime createdAt;
   protected LocalDateTime updatedAt;

   @PrePersist
   public void beforeCreate() {
      this.createdAt = LocalDateTime.now();
   }

   @PreUpdate
   public void beforeUpdate() {
      this.updatedAt = LocalDateTime.now();
   }


```

## Done - major areas

- Load Region/Location from yml using CommandLineRunner
- Basic REST API
- HATEOAS support
- Code Coverage with Jacoco
- Exception handling
- Controller Advice
- Follow MVC pattern : @Service, @Repository, @Controller
- WebMVCTest for testing REST Controllers in isolation
- SpringBootTest for testing service end to end
- Load data from yaml files
- SWAGGER
- Add CircleCI
- Mockito mock - good examples
  here, https://github.com/gadzooks/weather-java-api/blob/master/src/test/java/com/github/gadzooks/weather/service/jpa/JpaRegionServiceImplTest.java#L94

## Read articles :

- https://www.baeldung.com/maven-dependency-scopes
- https://www.programmersought.com/article/8780517523/