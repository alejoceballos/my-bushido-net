# My Bushido NET - Martial Arts Gym Management System

A Study Guide to Many Java and Microservices Related Technologies

## What is expected to work with (for now):

- Java:
    - Core:
        - Lambda functions & Functional Interfaces
        - Streams API
        - Default static methods on interfaces
        - Optional
        - Everything else from 8 to 19!
    - Spring:
        - Boot:
            - Active-MQ
            - Mockito
            - Cache
            - Test
            - Web
            - Actuator
            - Data JPA
            - Validation
        - Cache
        - JMS
        - HTTP
        - KAFKA
        - Lots of other features!
    - Testing:
        - Mockito:
            - Mock
            - Spy
            - Captor
        - Awaitility
        - Wiremock
        - Cucumber:
            - JSON Path
        - JUnit 5
        - Pitest Mutations
        - Hamcrest
        - Logback Events
    - Other Libraries:
        - Lombok:
            - Data
            - Builder
            - SLF4J
        - Map Struct
        - Guava Event Bus
        - Jackson Object Mapping
        - SLF4J
- Infra:
    - Docker
        - Docker Compose
    - Kafka
    - GraphQL
    - IBM MQ
    - Swagger (Stalled because is not compatible with Spring Boot's last version)
    - Microservices
        - Kubernetes
    - EhCache
- Others:
    - Jira
    - Jenkins
    - Git
    - GitHub / GitLab
    - Kibana
    - SonarQube
    - UML

## What have been done (so far):

### Docker

- Docker Compose
- Images:
    - PostgreSQL (with volume path)

### Java

- 1:
    - FileInputStream
- 1.1:
    - SimpleDateFormat
- 7:
    - try-with-resources
- 8:
    - Optional
    - Streams (map, sorted, concat, distinct, collect, toList, ...)
- 10:
    - Type inference of local variables with initializers (var keyword)
- 14:
    - Records
    - Pattern Matching for instanceof
- 15:
    - Text Blocks

### Testing

- JUnit Jupiter
    - @Test
    - @BeforeAll, @BeforeEach, @AfterEach
- Mockito
    - @Mock / @MockBean
    - reset, when, ...
- Hamcrest (assertThat, equalTo, ...)

### JDBC

- java.sql.Date
- Statement:
    - execute & executeQuery
    - ResultSet in loop
- PreparedStatement:
    - Add images (setBinaryStream)
    - Batch updates (addBatch, executeBatch)
    - execute vs. executeUpdate

### Spring

- ResourceUtils:
    - Read file
- Basic dependency injection:
    - @Service, @Component, @Autowired
    - ApplicationContext & implements ApplicationContextAware
- Security
    - SecurityContextHolder
    - UserDetails
- Spring Boot
    - @SpringBootApplication, @Configuration
    - Spring Boot Test
        - @SpringBootTest
        - @AutoConfigureMockMvc vs. @WebMvcTest
        - MockMvc
            - MockMvcRequestBuilders (get)
            - MockMvcResultMatchers (content, status)
            - SecurityMockMvcRequestPostProcessors (httpBasic)
        - Mockito: @MockBean
    - Spring Boot Web MVC
        - Config: @EnableWebMvc
        - Controllers: @RestController, @RequestMapping, @GetMapping, @PathVariable
        - Exception Handling: @ResponseStatus
    - Spring Boot Data:
        - JpaRepository
        - JdbcTemplate
    - Spring Boot Security:
        - Basic Authentication
    - Spring Boot Actuator

### Apache Commons

- Codec:
    - Encode Base64 string to embed in a JSON file

### Lombok

- Constructors: @RequiredArgsConstructor, @NoArgsConstructor, @AllArgsConstructor
- Accessors & Equality: @Data
- Builder design pattern: @Builder

### Mapstruct

- @Mapper (with Spring ComponentModel)
- @Mapping (child objects, date format, implicit casting)

### Jackson

- Exclude null attributes:
    - @JsonInclude(JsonInclude.Include.NON_NULL)

### Jakarta JPA

- ORM
    - @Entity, @Id, @GeneratedValue, @UuidGenerator, @Embedded
    - @Column:
        - length, nullable
    - @ManyToOne:
        - Lazy loading fetch
        - Cascade Type (PERSIST, MERGE)
    - Image mapping (byte array)
    - Date mapping (@Temporal)
- Validators: @NotBlank, @NotNull, @Valid
- Transactions: @Transactional

### Hibernate

- Auto generate UUID
    - @UuidGenerator
- LazyInitializationException
- Dialect: PostgreSQLDialect
- Envers:
    - @Audited
    - Create data structures (PostgreSQL)
        - Create revision table & sequence
        - Create audit tables named tablename_aud
        - Create sequences named tablename_seq
- Revision Entity & Listener
    - extends DefaultRevisionEntity
    - implements RevisionListener

### Flyway

- Java migrations

### Maven

### UML

- Use Case Diagram
- Class Diagram

### Postgres

- DDL:
    - CREATE TABLE
    - ALTER TABLE
    - CREATE SEQUENCE ... OWNED BY
- DML:
    - SELECT (IN, LIKE, INNER JOIN, ...)
    - INSERT
    - UPDATE
    - DELETE
- Data types:
    - UUID
    - SERIAL vs. sequences (SERIAL implicitly creates a default sequence named <table>_<column>_seq)
    - BYTEA (for images)