# My Bushido NET - Martial Arts Gym Management System

A Study Guide to Many Java and Microservices Related Technologies


## Docker

- Docker Compose
- Images:
    - PostgreSQL (with volume path)


## Postgres

- DDL:
    - CREATE TABLE
    - ALTER TABLE
    - CREATE SEQUENCE ... OWNED BY
- DML:
    - SELECT
    - INSERT
    - UPDATE
- Data types:
    - UUID
    - SERIAL vs. sequences (SERIAL implicitly creates a default sequence named <table>_<column>_seq)
    - BYTEA (for images)


## Java

- 1:
    - FileInputStream
- 1.1:
    - SimpleDateFormat
- 7:
    - try-with-resources
- 8:
    - Optional
    - Streams (map, sorted, collect, ...)
- 10:
    - Type inference of local variables with initializers (var keyword)
- 14:
    - Records
    - Pattern Matching for instanceof
- 15:
    - Text Blocks


### testing

- JUnit Jupiter
    - @Test
    - @BeforeAll, @BeforeEach, @AfterAll, @AfterEach
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
    - @Entity, @Id, @GeneratedValue, @Column, @ManyToOne, @Embedded
    - Lazy loading
    - Image mapping (byte array)
    - Date mapping (@Temporal)
- Validators: @NotBlank, @Valid
- Transactions: @Transactional


### Hibernate

- Auto generate UUID
    - @UuidGenerator
- LazyInitializationException
- Dialect: PostgreSQLDialect
- Envers:
    - Create data structures (PostgreSQL)
        - Create revision table & sequence
        - Create audit tables named <table>_aud
        - Create sequences named <table>_seq
- Revision Entity & Listener
    - extends DefaultRevisionEntity
    - implements RevisionListener


### Flyway

- Java migrations
