# Spring Testing

Spring Testing Framework, is the efficient way to test our beans and classes. To test a simple class we can use plain JUnit5,
and mock up the required beans.
But if we want to test Beans interactions and the ApplicationContext, the STF gets very handy.

# Questions

## 1. How can we configure the application context when running tests with `@SpringBootTest` annotation?
When we annotate a test class with `@SpringBootTest`, Spring Boot will search for our main
configuration (`@SpringBootApplication`) and load a full application context. But usually, when we are in a
testing area, we often need to customize that context. 
So these are the ways we can configure the application context.

### Selecting Configuration Classes
We can select which configuration classes we want to bring up if we do not want the full conf classes. Such as:
```java
@SpringBootTest(classes = { MyApp.class, TestConfig.class })
public class MyIntegrationTest { ... }
```
This is very useful for when we want to limit the context to just certain beans or add a special `@TestConfiguration`.

### Activating Profiles
We can select which profiles would be active in our class by stating `@ActiveProfiles("test")`.
```java
@ActiveProfiles("test")
@SpringBootTest
public class MyServiceTest { ... }
```

### Overriding Properties
We can set custom properties for our test via `@SpringBootTest`:
```java
@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "app.featureX.enabled=false"
})
public class FeatureXIntegrationTest { ... }
```

Or via `@TestPropertySource`:
```java
@SpringBootTest
@TestPropertySource(locations = "classpath:test-db.properties")
public class RepositoryTest { ... }
```

## 2. How can we exclude autoconfiguration from a test?
Like in our regular Spring Boot app, we can also exclude autoconfiguration for testing.
```java
@SpringBootTest(
  excludeAutoConfiguration = { DataSourceAutoConfiguration.class }
)
public class NoDatasourceTest { ... }
```

## 3. a) How many application contexts can be cached when running tests? b) What are the side effects if we increase the cache size? c) What would be the side effects if there was no caching?
### a) Default Cache Size
Spring’s TestContext framework caches up to 32 distinct ApplicationContext instances by default. 
Once the cache reaches this limit, it evicts the least recently used context to make room for a new one.
We can change this value on the application.properties:
```properties
spring.test.context.cache.maxSize=50
```

### b) Side Effects of Increasing the Cache Size
#### Pros
- **Improved Test Throughput:** More contexts stay ready for reuse, reducing the number of cold starts.

#### Cons
- **Higher Memory Footprint:** Each cached context holds beans, resource handles, thread pools, DB connections, etc. Which can lead to an out‑of‑memory error.
- **Longer GC Pauses:** More live objects can increase garbage‐collection pressure and pause times.
- **Delayed Cleanup of External Resources.**

### b) Side Effects of No Caching
#### Pros
- **Lower Memory Usage:** Contexts are closed immediately after use, freeing up beans and resources.
- **Stronger Isolation:** Each test truly gets a clean context. No chance of shared state or resource leakage across tests.

#### Cons
- **Slower Test Execution:** Every test class triggers a new application-context startup. 
  - This means, bean definition scanning, dependency wiring, and resource initialization all repeat.
  - Which ends up in a dramatic increase total test time.
- **Greater I/O and CPU Overhead.** 

## 4. Can @MockBean be used if the bean is not already defined in the application context?
`@MockBeans` is used not only to mock existing beans, but also for mocking a bean that is not currently
defined in your application context.
- A bean of that type **does not** already exist → the mock is added. 
- A bean of that type **does** exist → the mock replaces it.