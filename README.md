# REST Weather API

[![CircleCI](https://circleci.com/gh/gadzooks/weather-java-api.svg?style=svg)](https://app.circleci.com/pipelines/github/gadzooks/weather-java-api)
Circle CI test status

[![codecov](https://codecov.io/gh/gadzooks/weather-java-api/branch/master/graph/badge.svg)](https://codecov.io/gh/gadzooks/weather-java-api)

# [12-Factor compliant app](https://12factor.net) :

1. Codebase : Github for version control.
2. Dependencies (Explicitly declare and isolate dependencies) : Using Maven to manage all dependencies.
3. Config (Store config in the environment) : API keys are stored in ENV variables
4. Backing services (Treat backing services as attached resources) : Loose coupling and using interfaces allows the app
   to swap out one DB service with another.

5. Build, release, run (Strictly separate build and run stages) : Build, release managed via CircleCI
6. Processes (Execute the app as one or more stateless processes) : app itself is stateless. All state is either loaded
   into memory at boot time or stored in permanent storage like a Database
7. Port binding (Export services via port binding) : listens to 8080, creates a FAT executable jar with no dependencies
   other than JAVA
   8. Concurrency (Scale out via the process model) : Using Docker and Kubernetes would allow for easy scaling
9. Disposability (Maximize robustness with fast startup and graceful shutdown) : Application exposes idempotent
   services. No long running tasks are executed as part of a request.
10. Dev/prod parity (Keep development, staging, and production as similar as possible) : WIP. Any differences between
    dev/staging/prod are kept in ENV variables. Docker allows us to deploy the same code base with different ENVs easily
11. Logs (Treat logs as event streams) : SL4J handles logging for the app. Next steps would be to use ELK stack to
    capture and analyze logs.
12. Admin processes (Run admin/management tasks as one-off processes) : TODO - Write some sample scripts to display one
    off tasks.

# Design patterns used in this app :

## High level view

1. Organize code by functionality / layers


```
     src/main/java/com/github/gadzooks/weather/
     ├── WeatherApplication.java
     ├── api
     ├── bootstrap
     ├── configuration
     ├── controller
     ├── domain
     ├── exception
     ├── repository
     └── service
```

2. CRUD operations and REST API (with HATEOS) implementation follows REST standard.
3. Swagger for documentation
4. Junit5 for testing
5. MVC with FAT model (service layer) and skinny controllers
6. Data transfer objects(DTOs) to de-couple domain layer from service layer
7. Code to interfaces. All services, repositories, mappers implement a standard interface
8. Test at the proper abstraction level (example unit vs integration)
9. Code coverage with Jacoco

### Code to interfaces

All services implement this interface, which allows swapping them out easily
  ```java
public interface CrudService<T, ID> {
    List<T> findAll();
    T save(T model);
    T getById(ID id);
    T patch(ID id, T updatedRegion);
    void delete(ID id);
}
  ```

All mappers implement this interface :
  ```java
public interface EntityMapper<DTO, ENTITY> {
    DTO toDto(ENTITY e);
    ENTITY toEntity(DTO d);
    List<DTO> toDto(List<ENTITY> eList);
    List<ENTITY> toEntity(List<DTO> dList);
}
  ```

Custom in-memory repository implments CurdRepository to match JPA / Mongo repository pattern
  ```java
public abstract class CrudRepository<TD extends BaseEntity, ID extends Long> {
    private final Map<Long, TD> map = new HashMap<>();

    public TD save(TD object) {
        return add(object);
    }

    public void delete(ID id) {
        map.remove(id);
    }

    public TD get(ID id) {
        return map.get(id);
    }

    public boolean containsKey(ID id) {
        return map.containsKey(id);
    }
}
  ```

### Document WHY in comments clearly

```java
@NoArgsConstructor // Required by JPA
@Data
@EqualsAndHashCode(exclude = {"regionJpas"}, callSuper = true)
//RegionJpa includes reference back to LocationJpa
@ToString(exclude = {"regionJpas"})
// NOTE 1 : DO NOT use Lomboks @EqualsAndHashCode -> we want to compare based on the unique key, and we want to allow
// users to add multiple new objects to a set (lombok will count those as equal (since key is null for new objs) )

// NOTE 2 : Do NOT use Lombok's @toString -> this will load entities that may be set up as lazy fetch
@Entity
public class LocationJpa extends BaseEntity {
    private String name;
    private String description;

    //we dont want to include regionJpas in the builder so we cannot use the annotation at the class level
    @Builder
    public LocationJpa(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // NOTE : add bi-directional references
    public void addRegion(RegionJpa newRegion) {
        if (!regionJpas.contains(newRegion)) {
            regionJpas.add(newRegion);
            newRegion.addLocation(this);
        }
    }
}
```

### Functional programming

#### Return immutable copies to avoid accidental changes

```java
public ImmutableSet<RegionJpa> getRegionJpas(){
     return ImmutableSet.copyOf(regionJpas);
}
```

#### Use Lomboks @Value to create readonly objects

```java
@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDTO {
    String id;
    float latitude;
    float longitude;
    String subRegion;
}
```

#### Builder pattern used with Value objects allows creating readonly objects easily
```java
LocationJpa.builder().name("originalLoc").build();
```

### Use libraries to minimize repetitive code :

1. lombok for getters/setters/contructors/builders
2. Mapstruct for converting DTOs to Entities and back
3. Google gson for deserializing JSON
4. Apache Commons lang for string manipulations

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

<img width="645" alt="image" src="https://user-images.githubusercontent.com/340317/125152906-e307f380-e104-11eb-926e-32b082851e64.png">


### Read secrets like API Keys from ENV

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

### Break up application properties by functionality

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

### Well-designed (skinny) REST controller with HATEOAS support

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

#### Use BaseEntity (DRY common code)

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

### Testing

#### Unit test with Junit5

```java
class ForecastResponseMapperTest {

    ForecastResponseMapper mapper = ForecastResponseMapper.INSTANCE;

    @Test
    void toDtoTest() {
        ForecastResponse entity = new ForecastResponse();
        entity.setDescription("desc");
        entity.setLatitude(123.456D);
        entity.setLongitude(444.444D);

        ForecastResponseDTO dto = mapper.toDto(entity);

        assertEquals("desc", dto.getDescription());
        assertEquals(123.456D, dto.getLatitude());
        assertEquals(444.444D, dto.getLongitude());
    }
}
```

#### SpringBootTest (load selective classes to make test run faster)

```java
//NOTE : only load components needed for this test to make tests faster and avoid
//loading entire Application Context
@SpringBootTest(classes = {RegionRepository.class, WeatherPropertiesConfiguration.class,
        LocationRepository.class, LoadRegionsDatabase.class})
//Required since LoadRegionsDatabase gets injected with Configuration Props from resources/*.yml file
@EnableConfigurationProperties
class LoadRegionsDatabaseTest {
    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private LoadRegionsDatabase loadRegionsDatabase;

    @Test
    void getConfigurationProperties_via_setterInjection_and_via_valueAnnotation() {
        // verify that we are loading configuration properties via both
        // 1. Setter injection
        // 2. @Value annotation

        assertEquals(loadRegionsDatabase.getRegionsFilePath(),
                loadRegionsDatabase.getWpc().getRegionsFile());

        assertEquals(loadRegionsDatabase.getLocationsFilePath(),
                loadRegionsDatabase.getWpc().getLocationsFile());

    }
}
```

### Controller test

```java
@ExtendWith(MockitoExtension.class)
class JpaRegionControllerTest {
    @Mock
    private JpaRegionService jpaRegionService;
    @Mock
    private PlacesService placesService;
    @InjectMocks
    private JpaRegionController jpaRegionController;

    @Test
    void searchEveryWhere() {
        //given
        RegionJpa r1 = new RegionJpa();
        r1.setId(1L);
        r1.setName("r1");

        List<RegionJpa> regionJpaList = List.of(r1);
        String searchString = "issaquah";

        //when
        when(placesService.searchEveryWhere(searchString)).thenReturn(regionJpaList);
        List<EntityModel<RegionJpa>> results = jpaRegionController.searchEveryWhere(searchString);

        //then
        verify(placesService, times(1)).searchEveryWhere(searchString);

        log.debug("search everywhere results : ");
        for (EntityModel<RegionJpa> r : results) {
            log.debug(r.toString());
        }
    }
}
```

### JPA test (only load JPA slice with @DataJpaTest annotation)

```java
@DataJpaTest
class AreaJpaTest {
    @Autowired
    private AreaJpaRepository areaJpaRepository;
    @Autowired
    private RegionJpaRepository regionJpaRepository;

    @Test
    void verifyAreaIdStoredInRegionId() {
        //Given
        RegionJpa region = new RegionJpa();
        AreaJpa area = new AreaJpa();

        area.addRegionJpa(region);

        //When
        regionJpaRepository.save(region);
        areaJpaRepository.save(area);

        //Then
        assertThat(area.regionJpasSize(), equalTo(1));
        assertThat(region.getAreaJpa(), equalTo(area));
        assertThat(region.getAreaJpa().getId(), equalTo(area.getId()));
    }
}
```

### MongoDB test (load Mongo slice with @DataMongoTest)

```java
@DataMongoTest
class RegionDocumentTest {
    @Autowired
    private MongoRegionRepository repository;

    @Test
    void shouldFailOnInvalidEntity() {
        // mongo related tests here
    }
}
```

### Service layer test (verify service invokes correct method on Repository)

```java
@ExtendWith(MockitoExtension.class)
class JpaRegionServiceImplTest {

    @Mock
    private RegionJpaRepository repository;

    @InjectMocks
    private JpaRegionServiceImpl service;

    @Test
    void findAll() {
        //given
        AreaJpa area = AreaJpa.builder().name("area1").build();
        RegionJpa region = RegionJpa.builder().name("region1").build().setAreaJpa(area);

        //when
        when(repository.findAll()).thenReturn(List.of(region));
        List<RegionJpa> results = service.findAll();

        //then
        assertEquals(List.of(region), results);
        verify(repository, times(1)).findAll();
    }
}
```

### Spring MVC testing with MockMvc

```java
//NOTE : test controller layer. use @SpringBootTest for integration testing
//     : controllers, parameter tells Spring Boot to only load the beans required for this controller
//@WebMvcTest(controllers = RegionController.class)
//NOTE : Here we are mocking everything out with MockMvc so we DONT need to use @WebMvcTest which would load more spring
//application context than we need to run these tests (and which would slow down the tests)
@ExtendWith(MockitoExtension.class)
class RegionControllerTest {

    //simulate HTTP requests.
    private MockMvc mockMvc;

    @Mock
    private RegionService regionService;

    @InjectMocks
    private RegionController regionController;

    @BeforeEach
    void setUp() {
        //other setup ....

        //Ignore HATEOAS links
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mockMvc = MockMvcBuilders.standaloneSetup(regionController).build();
    }

    // deserialize response and compare to expected value
    @Test
    void getByRegionId() throws Exception {
        when(regionService.getById(r2.getId())).thenReturn(r2);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/regions/" + r2.getId()).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).
                andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Region actualRegion = mapper.readValue(contentAsString, Region.class);
        assertEquals(r2, actualRegion);
        verify(regionService).getById(r2.getId());
    }

    //use jsonPath to verify JSON response contains right values
    @Test
    void patchRegion() throws Exception {
        when(regionService.patch(r3.getId(), r3)).thenReturn(r3);
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/regions/" + r3.getId())
                .content(asJsonString(r3))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(r3.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.searchKey").value(r3.getSearchKey()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(r3.getDescription()));
        verify(regionService).patch(r3.getId(), r3);
    }
}
```

## Read articles :

- https://www.baeldung.com/maven-dependency-scopes
- https://www.programmersought.com/article/8780517523/
- Mockito mock - good examples
  here, https://github.com/gadzooks/weather-java-api/blob/master/src/test/java/com/github/gadzooks/weather/service/jpa/JpaRegionServiceImplTest.java#L94
